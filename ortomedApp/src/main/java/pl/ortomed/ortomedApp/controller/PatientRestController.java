package pl.ortomed.ortomedApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
        this.patientService = patientService;
    }

    @GetMapping
    public ResponseEntity<List<Patient>> showPatient(){
   /* Patient patient = new Patient();
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

    @GetMapping("/{pesel}")
    public ResponseEntity<List<Patient>> findByPesel(@PathVariable Integer pesel){
        if (!patientService.findByPesel(pesel).isEmpty()){
            return ResponseEntity.ok(patientService.findByPesel(pesel));
        }
        else {
            return ResponseEntity.notFound().build();
        }

    }
////////////////////////////////////////////////////////////////REST////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////REST////////////////////////////////////////////////////////////

}
