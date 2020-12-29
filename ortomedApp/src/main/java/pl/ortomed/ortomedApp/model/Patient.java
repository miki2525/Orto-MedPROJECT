package pl.ortomed.ortomedApp.model;

import com.sun.istack.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Objects;


@Entity
public class Patient {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfVisit;

    @NotBlank
    private String doctor;

    @NotBlank
    private String timeOfVisit;

    @NotBlank
    @Size(min = 2)
    private String firstName;

    @NotBlank
    @Size(min = 2)
    private String lastName;

    @NotBlank
    private Long pesel;

    @NotNull
    private Integer phoneNumber;

    @NotBlank
    private String email;

    private Integer password;

    public Patient() {
    }

    public Patient(LocalDate dateOfVisit, String doctor, String timeOfVisit, String firstName,
                   String lastName, Long pesel, Integer phoneNumber, String email, Integer password) {

        this.dateOfVisit = dateOfVisit;
        this.doctor = doctor;
        this.timeOfVisit = timeOfVisit;
        this.firstName = firstName;
        this.lastName = lastName;
        this.pesel = pesel;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
    }

    public LocalDate getDateOfVisit() {
        return dateOfVisit;
    }

    public void setDateOfVisit(LocalDate dateOfVisit) {
        this.dateOfVisit = dateOfVisit;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getTimeOfVisit() {
        return timeOfVisit;
    }

    public void setTimeOfVisit(String timeOfVisit) {
        ;
        this.timeOfVisit = timeOfVisit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getPesel() {
        return pesel;
    }

    public void setPesel(Long pesel) {
        this.pesel = pesel;
    }

    public Integer getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Integer phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getPassword() {
        return password;
    }

    public void setPassword(Integer password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "dateOfVisit=" + dateOfVisit +
                ", doctor='" + doctor + '\'' +
                ", timeOfVisit='" + timeOfVisit + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", pesel=" + pesel +
                ", phoneNumber=" + phoneNumber +
                ", email='" + email + '\'' +
                ", password=" + password +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return Objects.equals(id, patient.id) &&
                Objects.equals(dateOfVisit, patient.dateOfVisit) &&
                Objects.equals(doctor, patient.doctor) &&
                Objects.equals(timeOfVisit, patient.timeOfVisit) &&
                Objects.equals(firstName, patient.firstName) &&
                Objects.equals(lastName, patient.lastName) &&
                Objects.equals(pesel, patient.pesel) &&
                Objects.equals(phoneNumber, patient.phoneNumber) &&
                Objects.equals(email, patient.email) &&
                Objects.equals(password, patient.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateOfVisit, doctor, timeOfVisit, firstName, lastName, pesel*13, phoneNumber*11, email, password*7);
    }
}
