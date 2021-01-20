package pl.ortomed.ortomedApp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import pl.ortomed.ortomedApp.model.Patient;

import pl.ortomed.ortomedApp.service.PatientService;


import javax.servlet.annotation.ServletSecurity;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

////////////////////////////////////////////////////////////////REST////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////REST////////////////////////////////////////////////////////////

/////dla admina
@RequestMapping("/api")
@RestController
public class PatientRestController {

    PatientService patientService;
    PatientController patientController;

    public PatientRestController(PatientService patientService, PatientController patientController) {
        this.patientService = patientService;
        this.patientController = patientController;
    }

    @GetMapping
    public ResponseEntity<List<Patient>> showPatient() {
        return ResponseEntity.ok(patientService.showAll());
    }

    @GetMapping("/{pesel}")
    public ResponseEntity<List<Patient>> findByPesel(@PathVariable Long pesel) {
        if (!patientService.findByPesel(pesel).isEmpty()) {
            return ResponseEntity.ok(patientService.findByPesel(pesel));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/dodaj")
    public ResponseEntity<String> Dodaj() {
        Patient patient1 = new Patient();
        patient1.setFirstName("Miko");
        patient1.setEmail("mikolaj_miki05@o2.pl");
        patient1.setPassword(1);
        patient1.setDateOfVisit(LocalDate.now().plusDays(3));
        patient1.setLastName("Lajk");
        patient1.setDoctor("dr. Gargula");
        patient1.setPesel(88122121875L);
        patient1.setPhoneNumber(505601232);
        patient1.setTimeOfVisit("10:00");
        patientService.savePatient(patient1);

        return ResponseEntity.ok("Dodalem");
    }

    @PostMapping("/showVisit")
    public ResponseEntity<Patient> showVisit(@RequestParam("email") String email, @RequestParam("pass") Integer pass) {
        Patient visit = new Patient();
        for (Patient tempPatient : patientService.showAll()) {
            if (tempPatient.getEmail().equals(email)) {
                if (tempPatient.getPassword().equals(pass)) {
                    visit = tempPatient;
                }
            }
        }
        return ResponseEntity.ok(visit);
    }

    @PostMapping("/delete")
    ResponseEntity<Boolean> cancelVisit(@RequestBody Patient patient) {
        return ResponseEntity.ok(patientService.deletePatient(patient));
    }

    @PostMapping("/delete/{id}")
    ResponseEntity<Boolean> deleteById(@RequestParam Long id) {

        if (patientService.findById(id).isPresent()) {
            Optional<Patient> patient = patientService.findById(id);
            return ResponseEntity.ok(patientService.deletePatient(patient.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

////////////////////////////////////////////////////////////////REST////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////REST////////////////////////////////////////////////////////////


