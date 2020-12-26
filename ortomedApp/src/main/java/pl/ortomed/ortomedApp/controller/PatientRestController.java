package pl.ortomed.ortomedApp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.ortomed.ortomedApp.model.Patient;

import pl.ortomed.ortomedApp.service.PatientService;


import javax.servlet.annotation.ServletSecurity;
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
    }
////////////////////////////////////////////////////////////////REST////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////REST////////////////////////////////////////////////////////////


