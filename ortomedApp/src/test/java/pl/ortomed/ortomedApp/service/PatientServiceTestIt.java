package pl.ortomed.ortomedApp.service;

import org.assertj.core.api.Assert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.ortomed.ortomedApp.model.Patient;

import javax.mail.MessagingException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
class PatientServiceTestIt {

    @Autowired
    PatientService patientService;

    Patient patientTest;

    @BeforeEach
    void setUp(){
        patientService.clearAll();
        patientTest = new Patient(LocalDate.now().plusDays(3), "dr. Gargula", "11:30", "test",
                "test", 99080955104L, 505666123,
                "testowymail@gmail.com", 1);
        patientService.savePatient(patientTest);
    }

    @Test
    void shouldShowAll() {
        //
        //
        List<Patient> listTemp = patientService.showAll();
        //
        assertThat(listTemp).isNotEmpty();
        assertThat(listTemp).contains(patientTest);

    }

    @Test
    void shouldFindById() {
        //
        //
        Optional<Patient> patientTemp = patientService.findById(patientTest.getId());
        //
        assertThat(patientTemp).isPresent();
    }

    @Test
    void findByPesel() {
        //
        Patient patientTest2 = new Patient(LocalDate.now().plusDays(4), "dr. Gargula", "10:30", "test",
                "test", 99080955104L, 505666123,
                "testowymail@gmail.com", 2);
        patientService.savePatient(patientTest2);
        //
        List<Patient> listTemp = patientService.findByPesel(patientTest2.getPesel());
        //
        assertThat(listTemp.size()).isEqualTo(2);
    }

    @Test
    void savePatient() {
        //
        Patient patientTest2 = new Patient(LocalDate.now().plusDays(4), "dr. Gargula", "10:30", "test",
                "test", 99080955104L, 505666123,
                "testowymail@gmail.com", 2);
        //
        patientService.savePatient(patientTest2);
        //
        assertThat(patientTest2.getId()).isGreaterThan(0L);
    }

    @Test
    void deletePatient() {
        //
        //
        patientService.deletePatient(patientTest);
        //
        assertThat(patientService.showAll()).doesNotContain(patientTest);
    }

    @Test
    void sendMailReminder() throws MessagingException {
        //
        //
        //
        try {

            patientService.sendMailReminder();
            assertTrue(true);

        }
        catch (Exception e){
            org.junit.jupiter.api.Assertions.fail(e.getMessage());
        }
    }

    @Test
    void showFreeHours() {
        //
        Patient patientTest2 = new Patient(LocalDate.now().plusDays(4), "dr. Gargula", "10:30", "test",
                "test", 99080955104L, 505666123,
                "testowymail@gmail.com", 2);
        patientService.savePatient(patientTest2);
        //
        List<String> listTemp = patientService.showFreeHours(patientTest.getDateOfVisit(), patientTest.getDoctor());
        //
        assertThat(listTemp).doesNotContain(patientTest.getTimeOfVisit());
        assertThat(listTemp).isNotEmpty();
    }
}