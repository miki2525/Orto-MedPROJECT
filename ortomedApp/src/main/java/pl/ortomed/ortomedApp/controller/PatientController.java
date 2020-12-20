package pl.ortomed.ortomedApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.ortomed.ortomedApp.model.Patient;
import pl.ortomed.ortomedApp.service.PatientService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/web")
public class PatientController {

private PatientService patientService;

public PatientController(PatientService patientService){this.patientService = patientService; }

@GetMapping
    public String showMainPage(){
    return "mainWeb";
}

@GetMapping("/registration")
    public String showDateRegisterPage(Model model){
        Patient tempPatient = new Patient();
        model.addAttribute("patient", tempPatient);
    return "dateRegisterPage";
}

@PostMapping("/registration")
  public String showRegisterPage(@ModelAttribute Patient patient, Model model){
        model.addAttribute("patient", patient);
    /*LocalDate tempLocalDate = LocalDate.parse(date);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    String tempDate = tempLocalDate.format(formatter);
    DateTimeFormatter convert = DateTimeFormatter.ofPattern("d-MM-yyyy");
    System.out.println(tempDate);
    LocalDate localDate = LocalDate.parse(tempDate, convert);
    System.out.println("localdate: " + localDate);
    Patient patient = new Patient();
    patient.setDateOfVisit(localDate);
    System.out.println(patient.getDateOfVisit());*/
    return "registerPage";
}

}
