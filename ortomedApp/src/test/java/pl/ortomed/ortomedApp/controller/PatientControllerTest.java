package pl.ortomed.ortomedApp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.assertj.core.internal.bytebuddy.description.annotation.AnnotationList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.ortomed.ortomedApp.model.Patient;
import pl.ortomed.ortomedApp.service.PatientService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PatientControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;


    @Autowired
    PatientService patientService;

    Patient patientTest;



    @Test
    void showMainPage() throws Exception {
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    void showAbout() throws Exception {
        mockMvc.perform(get("/about"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("aboutPage"));
    }

    @Test
    void shouldShowContact() throws Exception {
        mockMvc.perform(get("/about"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("aboutPage"));
    }

    @Test
    void shouldShowDateRegisterPage() throws Exception {
        mockMvc.perform(get("/registration"))
                .andDo(print())
                .andExpect(model().attribute("patient", new Patient()))
                .andExpect(status().isOk())
                .andExpect(view().name("dateRegisterPage"));
    }

    @Test
    void shouldShowDateRegisterPageUpdate() throws Exception {
        //given
        patientTest = new Patient(LocalDate.now().plusDays(3), "dr. Gargula", "11:30", "test",
                "test", 99080955104L, 505666123,
                "testowymail@gmail.com", 1);

        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        //
        mockMvc.perform(post("/registration")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(patientTest)))
                .andDo(print())
                .andExpect(model().attribute("patient", patientTest))
                .andExpect(status().isOk())
                .andExpect(view().name("dateRegisterPage"));
    }

    @Test
    void shouldShowRegisterPage() throws Exception {
        //given
        patientTest = new Patient();
        patientTest.setDateOfVisit(LocalDate.now().plusDays(3));
        patientTest.setDoctor("dr. Gargula");

        List<String> listTest = patientService.showFreeHours(patientTest.getDateOfVisit(), patientTest.getDoctor());
        //

        mockMvc.perform(post("/registration/step2")
                .param("dateOfVisit", patientTest.getDateOfVisit().toString())
                .param("doctor", patientTest.getDoctor()))
                .andDo(print())
                .andExpect(model().attribute("patient", patientTest))
                .andExpect(model().attribute("hours", listTest))
                .andExpect(status().isOk())
                .andExpect(view().name("registerPage"));
    }

    @Test
    void shouldShowDateRegisterPageAgain() throws Exception {
        //given
        patientTest = new Patient();
        //

        mockMvc.perform(post("/registration/step2")
                .param("doctor", patientTest.getDoctor()))
                .andDo(print())
                .andExpect(model().attribute("patient", patientTest))
                .andExpect(status().isOk())
                .andExpect(view().name("dateRegisterPage"));
    }

    @Test
    void shouldShowNoFreeHours() throws Exception {
        //given
        for(int i = 8; i < 14; i++){
            Patient patientSavedTest = new Patient();
            patientSavedTest.setDateOfVisit(LocalDate.now().plusDays(3));
            patientSavedTest.setDoctor("dr. Gargula");
            Patient patientSavedTest2 = new Patient();
            patientSavedTest2.setDateOfVisit(LocalDate.now().plusDays(3));
            patientSavedTest2.setDoctor("dr. Gargula");

            if (i<10){
                patientSavedTest.setTimeOfVisit("0" + i + ":00");
                patientService.savePatient(patientSavedTest);
                patientSavedTest2.setTimeOfVisit("0" + i + ":30");
                patientService.savePatient(patientSavedTest2);
            }
            else{
                patientSavedTest.setTimeOfVisit(i + ":00");
                patientService.savePatient(patientSavedTest);
                patientSavedTest2.setTimeOfVisit(i + ":30");
                patientService.savePatient(patientSavedTest2);
            }
        }
        patientTest = new Patient();
        patientTest.setDateOfVisit(LocalDate.now().plusDays(3));
        patientTest.setDoctor("dr. Gargula");
//        when(patientService.showFreeHours(patientTest.getDateOfVisit(),
//                patientTest.getDoctor())).thenReturn(Arrays.asList());
        //

        mockMvc.perform(post("/registration/step2")
                .param("dateOfVisit", patientTest.getDateOfVisit().toString())
                .param("doctor", patientTest.getDoctor()))
                .andDo(print())
                .andExpect(model().attribute("patient", patientTest))
                .andExpect(status().isOk())
                .andExpect(view().name("noFreeHoursPage"));
    }

    @Test
    void shouldShowSuccessPage() throws Exception {
        //given
        patientTest = new Patient(LocalDate.now().plusDays(3), "dr. Gargula", "11:30", "test",
                "test", 99080955104L, 505666123,
                "testowymail@gmail.com", 1);
        //

        mockMvc.perform(post("/registration/success")
                .param("dateOfVisit", patientTest.getDateOfVisit().toString())
                .param("doctor", patientTest.getDoctor())
                .param("timeOfVisit", patientTest.getTimeOfVisit())
                .param("firstName", patientTest.getFirstName())
                .param("lastName", patientTest.getLastName())
                .param("pesel", patientTest.getPesel().toString())
                .param("phoneNumber", patientTest.getPhoneNumber().toString())
                .param("email", patientTest.getEmail()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("successPage"));
    }

    @Test
    void shouldShowErrorPage() throws Exception {
        //given
        patientTest = new Patient(LocalDate.now().plusDays(3), "dr. Gargula", "11:30", "test",
                "test", 99080955104L, 505666123,
                "testowymail@gmail.com", 1);

        Patient patientSaveTest = new Patient(LocalDate.now().plusDays(3), "dr. Gargula", "11:30", "test",
                "test", 99080955104L, 505666123,
                "testowymail@gmail.com", 1);
        patientService.savePatient(patientSaveTest);
        //

        mockMvc.perform(post("/registration/success")
                .param("dateOfVisit", patientTest.getDateOfVisit().toString())
                .param("doctor", patientTest.getDoctor())
                .param("timeOfVisit", patientTest.getTimeOfVisit())
                .param("firstName", patientTest.getFirstName())
                .param("lastName", patientTest.getLastName())
                .param("pesel", patientTest.getPesel().toString())
                .param("phoneNumber", patientTest.getPhoneNumber().toString())
                .param("email", patientTest.getEmail()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("errorPage"));
    }

    @Test
    void shouldShowVisitPage() throws Exception {
        mockMvc.perform(get("/visit"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("visitPage"));
    }
}