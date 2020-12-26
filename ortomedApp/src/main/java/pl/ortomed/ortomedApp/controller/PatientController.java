package pl.ortomed.ortomedApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.ortomed.ortomedApp.model.Patient;
import pl.ortomed.ortomedApp.service.MailService;
import pl.ortomed.ortomedApp.service.PatientService;

import javax.mail.MessagingException;
import java.util.List;

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
    public String showDateRegisterPage(Model model){
        Patient patient = new Patient();
        model.addAttribute("patient", patient);
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
        model.addAttribute("hours", hoursList);
    return "registerPage";
}

@PostMapping("/registration/success")
    public String showSuccessPage(@ModelAttribute Patient patient) throws MessagingException {

    for (Patient temp : patientService.showAll()){
        if (patient.equals(temp)){
            return "errorPage";
        }
    }
      /*zrobic klase walidujaca zwracajaca bool if(!patient){
        return "errorPage";
    }*/
    ////zrobic walidacje po str frontu jak i backu
//    if ok -> SuccessPage, else -> "Nie mozna zarejestrowac, sprobuj ponownie"
        mailService.sendMail(patient, true);
        mailService.sendMailPass(patient, true);
        patientService.savePatient(patient);
        System.out.println(patient);
        return "successPage";

    }


}

