package pl.ortomed.ortomedApp.controller;

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


@GetMapping("/registration")
    public String showDateRegisterPage(@RequestParam (value = "id", required = false, defaultValue = "0") Long id ,  Model model){
    Patient patient = null;
    if (id > 0L) {          /////////////////UPDATE OLD PATIENT
        for(Patient temppatient : patientService.showAll()) {
            if (temppatient.getId() == id) {
                patient = temppatient;
                model.addAttribute("patient", patient);
            }
        }
    } else {               /////////////////CREATE NEW PATIENT
        patient = new Patient();
        model.addAttribute("patient", patient);
    }
    return "dateRegisterPage";
}

@PostMapping("/registration")
  public String showRegisterPage(@ModelAttribute Patient patient, Model model){

        //////walidacja po w js
        if(patient.getDateOfVisit()==null || patient.getDoctor() == ""){
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
      /*zrobic klase walidujaca zwracajaca bool if(!patient){
        return "errorPage";
    }*/
    ////zrobic walidacje po str frontu jak i backu
//    if ok -> SuccessPage, else -> "Nie mozna zarejestrowac, sprobuj ponownie"
    ///sprawdzic czy podany mail dotyczy jednej osoby, jesli nie to
//        mailService.sendMail(patient, true);
//        mailService.sendMailPass(patient, true);
        Random random = new Random();
        int generateNumber = random.nextInt((1000000 - 100000 + 1) + 100000);
        patient.setPassword(generateNumber);
        patientService.savePatient(patient);
        System.out.println(patient);
        return "successPage";

    }

    @GetMapping("/visit")
    public String showVisitPage(){
    return "visitPage";
    }


}

