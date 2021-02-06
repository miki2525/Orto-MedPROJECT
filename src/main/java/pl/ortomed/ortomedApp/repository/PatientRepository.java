package pl.ortomed.ortomedApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.ortomed.ortomedApp.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}
