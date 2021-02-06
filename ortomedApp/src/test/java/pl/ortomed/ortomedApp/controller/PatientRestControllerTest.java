package pl.ortomed.ortomedApp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import pl.ortomed.ortomedApp.model.Patient;
import pl.ortomed.ortomedApp.service.PatientService;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PatientRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PatientService patientService;

    @Autowired
    ObjectMapper objectMapper;

    Patient patientTest;

    @BeforeEach
    void setUp(){
        patientService.clearAll();
        patientTest = new Patient(LocalDate.now().plusDays(3), "dr. Gargula", "11:30", "test",
                "test", 99080955104L, 505666123,
                "testowymail@gmail.com", 1);
        patientService.savePatient(patientTest);


        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }


    @Test
    void shouldShowPatient() throws Exception {
        String listJSON = objectMapper.writeValueAsString(patientService.showAll());
        mockMvc.perform(get("/api")).
                andDo(print()).
                andExpect(status().isOk()).
                andExpect(content().string(listJSON));
    }

    @Test
    void shouldFindByPesel() throws Exception {
        String listJSON = objectMapper.writeValueAsString(patientService.findByPesel(patientTest.getPesel()));
        mockMvc.perform(get("/api/{pesel}", 99080955104L))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(listJSON));
    }

    @Test
    void shouldNotFindByPesel() throws Exception {
        mockMvc.perform(get("/api/{pesel}", 89080955104L))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldShowVisit() throws Exception {
        mockMvc.perform(post("/api/showVisit")
                .contentType("application/x-www-form-urlencoded")
                .param("email", patientTest.getEmail())
                .param("pass", patientTest.getPassword().toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(patientTest)));
    }

    @Test
    void shouldCancelVisit() throws Exception {
        mockMvc.perform(post("/api/delete")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(patientTest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void shouldDeleteById() throws Exception{
        mockMvc.perform(post("/api/delete/{id}", 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void shouldNotDeleteById() throws Exception{
        mockMvc.perform(post("/api/delete/{id}", 2L))
                .andDo(print())
                .andExpect(status().isNotFound());
    }


}