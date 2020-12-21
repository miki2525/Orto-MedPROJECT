package pl.ortomed.ortomedApp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.ortomed.ortomedApp.model.Patient;
import pl.ortomed.ortomedApp.service.PatientService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/web")
public class PatientController {

private PatientService patientService;

public PatientController(PatientService patientService){this.patientService = patientService; }

@GetMapping
    public String showMainPage(){
    return "mainWeb";
}
////////////////////////////////////////////////////////////////REST////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////REST////////////////////////////////////////////////////////////

@GetMapping("/api")
@ResponseBody
    ResponseEntity<List<Patient>> showPatient(){
/*  Patient patient = new Patient();
    Patient patient1 = new Patient();
    Patient patient2 = new Patient();
    Patient patient3 = new Patient();
    patient.setDateOfVisit(LocalDate.now());
    patient1.setDateOfVisit(LocalDate.now());
    patient2.setDateOfVisit(LocalDate.now());
    patient3.setDateOfVisit(LocalDate.now());
    patient.setTimeOfVisit("11:00");
    patient1.setTimeOfVisit("12:00");
    patient2.setTimeOfVisit("08:00");
    patient3.setTimeOfVisit("09:30");
    patientService.savePatient(patient);
    patientService.savePatient(patient1);
    patientService.savePatient(patient2);
    patientService.savePatient(patient3);*/
    return ResponseEntity.ok(patientService.showAll());
}

////////////////////////////////////////////////////////////////REST////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////REST////////////////////////////////////////////////////////////

@GetMapping("/registration")
    public String showDateRegisterPage(Model model){
        Patient patient = new Patient();
        model.addAttribute("patient", patient);
    return "dateRegisterPage";
}

@PostMapping("/registration")
  public String showRegisterPage(@ModelAttribute Patient patient, Model model){
        model.addAttribute("patient", patient);
        List<String> hoursList = patientService.freeHours(patient.getDateOfVisit());
        model.addAttribute("hours", hoursList);
    return "registerPage";
}

@PostMapping("/registration/success")
    public String showSuccessPage(@ModelAttribute Patient patient){
    patientService.savePatient(patient);
    System.out.println(patientService.showAll());
    ////zrobic walidacje po str frontu jak i backu
//    if ok -> SuccessPage, else -> "Nie mozna zarejestrowac, sprobuj ponownie"
    return "SuccessPage";
}

}
