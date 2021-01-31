package pl.ortomed.ortomedApp.controller;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.ortomed.ortomedApp.model.Patient;
import pl.ortomed.ortomedApp.service.MailService;
import pl.ortomed.ortomedApp.service.PatientService;

import javax.mail.MessagingException;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("/")
public class PatientController {

private PatientService patientService;
private MailService mailService;

public PatientController(PatientService patientService, MailService mailService){this.patientService = patientService;
this.mailService = mailService;}

@GetMapping
    public String showMainPage(){
    return "index";
}


@GetMapping("/about")
    public String showAbout(){
    return "aboutPage";
}

@GetMapping("/contact")
    public String showContact(){ return "contactPage";}

@GetMapping("/registration")
    public String showDateRegisterPage(Model model){

        Patient patient = new Patient();
        model.addAttribute("patient", patient);
        return "dateRegisterPage";
    }

@PostMapping("/registration")
    public String showDateRegisterPage(@RequestBody Patient patient, Model model){
    model.addAttribute("patient", patient);
    return "dateRegisterPage";
}

@PostMapping("/registration/step2")
  public String showRegisterPage(@ModelAttribute Patient patient, Model model){

        if(patient.getDateOfVisit() == null || patient.getDoctor() == "" || patient.getDoctor() == null){
            return "dateRegisterPage";
        }
        model.addAttribute("patient", patient);
        List<String> hoursList = patientService.showFreeHours(patient.getDateOfVisit(), patient.getDoctor());
    if(hoursList.isEmpty()){
        return "noFreeHoursPage";
    }
        model.addAttribute("hours", hoursList);
    return "registerPage";
}

@PostMapping("/registration/success")
    public String showSuccessPage(@ModelAttribute Patient patient) throws MessagingException {

    for (Patient tempPatient : patientService.showAll()){
        if (patient.equals(tempPatient)){
            return "errorPage";
        }
    }

    //              new || updated visit means new password
    Random random = new Random();
    int generateNumber = random.nextInt((1000000 - 100000 + 1) + 100000);
    patient.setPassword(generateNumber);
    //}


        mailService.sendMail(patient, true);
        mailService.sendMailPass(patient, true);


        patientService.savePatient(patient);
        System.out.println(patient);
        return "successPage";

    }

    @GetMapping("/visit")
    public String showVisitPage(){
    return "visitPage";
    }

    /////////////////////////
    /////////////////////////// ENG VERSION
    //////////////////////////

        @GetMapping("/en")
        public String showMainPageEN(){
            return "indexEN";
        }

        @GetMapping("/en/about")
        public String showAboutEN(){
            return "aboutPageEN";
        }

        @GetMapping("/en/contact")
        public String showContactEN(){ return "contactPageEN";}

        @GetMapping("/en/registration")
        public String showDateRegisterPageEN(Model model){

            Patient patient = new Patient();
            model.addAttribute("patient", patient);
            return "dateRegisterPageEN";
        }

        @PostMapping("/en/registration")
        public String showDateRegisterPageEN(@RequestBody Patient patient, Model model){
            model.addAttribute("patient", patient);
            return "dateRegisterPageEN";
        }

        @PostMapping("/en/registration/step2")
        public String showRegisterPageEN(@ModelAttribute Patient patient, Model model){

            if(patient.getDateOfVisit()==null || patient.getDoctor() == ""){
                return "dateRegisterPageEN";
            }
            model.addAttribute("patient", patient);
            List<String> hoursList = patientService.showFreeHours(patient.getDateOfVisit(), patient.getDoctor());
            if(hoursList.isEmpty()){
                return "noFreeHoursPageEN";
            }
            model.addAttribute("hours", hoursList);
            return "registerPageEN";
        }

        @PostMapping("/en/registration/success")
        public String showSuccessPageEN(@ModelAttribute Patient patient) throws MessagingException {

            for (Patient tempPatient : patientService.showAll()){
                if (patient.equals(tempPatient)){
                    return "errorPageEN";
                }
            }
            Random random = new Random();
            int generateNumber = random.nextInt((1000000 - 100000 + 1) + 100000);
            patient.setPassword(generateNumber);


            mailService.sendMail(patient, true);
            mailService.sendMailPass(patient, true);



            patientService.savePatient(patient);
            System.out.println(patient);
            return "successPageEN";

        }

        @GetMapping("/en/visit")
        public String showVisitPageEN(){
            return "visitPageEN";
        }
}


