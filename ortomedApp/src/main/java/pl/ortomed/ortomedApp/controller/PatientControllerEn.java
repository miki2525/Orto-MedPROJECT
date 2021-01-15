package pl.ortomed.ortomedApp.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pl.ortomed.ortomedApp.model.Patient;
import pl.ortomed.ortomedApp.service.MailService;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.ortomed.ortomedApp.model.Patient;
import pl.ortomed.ortomedApp.service.MailService;
import pl.ortomed.ortomedApp.service.PatientService;

import javax.mail.MessagingException;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Controller
@RequestMapping("/en")
public class PatientControllerEn {
    private PatientService patientService;
    private MailService mailService;

    public PatientControllerEn(PatientService patientService, MailService mailService){this.patientService = patientService;
        this.mailService = mailService;}

    @GetMapping
    public String showMainPage(){
        return "/en/indexEN";
    }

    @GetMapping("/about")
    public String showAbout(){
        return "/en/aboutPageEN";
    }

    @GetMapping("/contact")
    public String showContact(){ return "/en/contactPageEN";}

    @GetMapping("/registration")
    public String showDateRegisterPage(Model model){

        Patient patient = new Patient();
        model.addAttribute("patient", patient);
        return "/en/dateRegisterPageEN";
    }

    @PostMapping("/registration")
    public String showDateRegisterPage(@RequestBody Patient patient, Model model){
        model.addAttribute("patient", patient);
        return "/en/dateRegisterPageEN";
    }

    @PostMapping("/registration/step2")
    public String showRegisterPage(@ModelAttribute Patient patient, Model model){

        if(patient.getDateOfVisit()==null || patient.getDoctor() == ""){
            return "/en/dateRegisterPageEN";
        }
        model.addAttribute("patient", patient);
        List<String> hoursList = patientService.showFreeHours(patient.getDateOfVisit(), patient.getDoctor());
        if(hoursList.isEmpty()){
            return "/en/noFreeHoursPageEN";
        }
        model.addAttribute("hours", hoursList);
        return "/en/registerPageEN";
    }

    @PostMapping("/registration/success")
    public String showSuccessPage(@ModelAttribute Patient patient) throws MessagingException {

        for (Patient tempPatient : patientService.showAll()){
            if (patient.equals(tempPatient)){
                return "/en/errorPageEN";
            }
        }
      /*zrobic klase walidujaca zwracajaca bool if(!patient){
        return "errorPage";
    }*/
        ////zrobic walidacje po str frontu jak i backu
//    if ok -> SuccessPage, else -> "Nie mozna zarejestrowac, sprobuj ponownie"
        ///sprawdzic czy podany mail dotyczy jednej osoby, jesli nie to
        mailService.sendMail(patient, true);
        mailService.sendMailPass(patient, true);

//        if(patient.getPassword() == null) { ////      new || updated visit means new password
        Random random = new Random();
        int generateNumber = random.nextInt((1000000 - 100000 + 1) + 100000);
        patient.setPassword(generateNumber);
        //}
        patientService.savePatient(patient);
        System.out.println(patient);
        return "/en/successPageEN";

    }

    @GetMapping("/visit")
    public String showVisitPage(){
        return "/en/visitPageEN";
    }
}

