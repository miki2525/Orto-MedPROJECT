package pl.ortomed.ortomedApp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.ortomed.ortomedApp.model.Patient;

import pl.ortomed.ortomedApp.service.PatientService;


import javax.servlet.annotation.ServletSecurity;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

////////////////////////////////////////////////////////////////REST////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////REST////////////////////////////////////////////////////////////

/////dla admina
@RequestMapping("/api")
@RestController
public class PatientRestController {


    PatientService patientService;

    public PatientRestController(PatientService patientService){
        this.patientService = patientService; }

    @GetMapping
    public ResponseEntity<List<Patient>> showPatient(){

        return ResponseEntity.ok(patientService.showAll());
    }

    @GetMapping("/{pesel}")
    public ResponseEntity<List<Patient>> findByPesel(@PathVariable Integer pesel) {
        if (!patientService.findByPesel(pesel).isEmpty()) {
            return ResponseEntity.ok(patientService.findByPesel(pesel));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/dodaj")
    public ResponseEntity<String> Dodaj(){
        Patient patient1 = new Patient();
//        Patient patient2 = new Patient();
//        Patient patient3 = new Patient();
        patient1.setFirstName("Miko");
        patient1.setEmail("walka@o2.pl");
        patient1.setPassword(1);
        patient1.setDateOfVisit(LocalDate.now());
        patient1.setTimeOfVisit("10:00");
/*        patient2.setFirstName("Miko");
        patient2.setEmail("walka@o2.pl");
        patient2.setPassword(1);
        patient2.setDateOfVisit(LocalDate.now());
        patient1.setTimeOfVisit("18:30");
        patient3.setFirstName("Miko");
        patient3.setEmail("walka@o2.pl");
        patient3.setPassword(1);
        patient3.setDateOfVisit(LocalDate.now());
        patient3.setTimeOfVisit("11:00");
        */patientService.savePatient(patient1);
//        patientService.savePatient(patient2);
//        patientService.savePatient(patient3);
        return ResponseEntity.ok("Dodalem");
    }
    @PostMapping("/showVisit")
        public ResponseEntity<Patient> showVisit(@RequestParam ("email") String email, @RequestParam ("pass") Integer pass){
        Patient visit = new Patient();
        for (Patient tempPatient : patientService.showAll()){
            if (tempPatient.getEmail().equals(email)){
                if (tempPatient.getPassword().equals(pass)){
                    visit = tempPatient;
                }
            }
        }
        return ResponseEntity.ok(visit);
        }
    }
////////////////////////////////////////////////////////////////REST////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////REST////////////////////////////////////////////////////////////


