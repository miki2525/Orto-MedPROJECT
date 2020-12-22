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
    private String timeOfVisit;

    @NotBlank
    @Size(min = 2)
    private String firstName;

    @NotBlank
    @Size(min = 2)
    private String lastName;

    @NotBlank
    private Integer pesel;

    @NotNull
    private Integer phoneNumber;

    private String email;

    public Patient() {
    }

    public Patient(LocalDate dateOfVisit, String timeOfVisit, String firstName, String lastName, Integer pesel, Integer phoneNumber, String email) {

        this.dateOfVisit = dateOfVisit;
        this.timeOfVisit = timeOfVisit;
        this.firstName = firstName;
        this.lastName = lastName;
        this.pesel = pesel;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public LocalDate getDateOfVisit() {
        return dateOfVisit;
    }

    public void setDateOfVisit(LocalDate dateOfVisit) {
        this.dateOfVisit = dateOfVisit;
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

    public Integer getPesel() {
        return pesel;
    }

    public void setPesel(Integer pesel) {
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

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", dateOfVisit=" + dateOfVisit +
                ", timeOfVisit='" + timeOfVisit + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", pesel=" + pesel +
                ", phoneNumber=" + phoneNumber +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return  Objects.equals(dateOfVisit, patient.dateOfVisit) &&
                Objects.equals(timeOfVisit, patient.timeOfVisit) &&
                Objects.equals(firstName, patient.firstName) &&
                Objects.equals(lastName, patient.lastName) &&
                Objects.equals(pesel, patient.pesel) &&
                Objects.equals(phoneNumber, patient.phoneNumber) &&
                Objects.equals(email, patient.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateOfVisit, timeOfVisit, firstName, lastName, pesel, phoneNumber, email);
    }
}
