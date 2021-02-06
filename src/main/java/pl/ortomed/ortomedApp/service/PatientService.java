package pl.ortomed.ortomedApp.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.ortomed.ortomedApp.model.Patient;
import pl.ortomed.ortomedApp.repository.PatientRepository;

import javax.mail.MessagingException;
import java.time.LocalDate;
import java.util.*;

@Service
public class PatientService {

    private PatientRepository patientRepository;
    private MailService mailService;

    public PatientService(PatientRepository patientRepository, MailService mailService) {
        this.patientRepository = patientRepository;
        this.mailService = mailService;
    }

    public List<Patient> showAll(){
        return patientRepository.findAll();
    }

    public Optional<Patient> findById(Long id){return patientRepository.findById(id);}

    public List<Patient> findByPesel(Long pesel){ //can be more than one visit per patient
            List list = new ArrayList();
        for (Patient tempPatient : patientRepository.findAll()){
            if(tempPatient.getPesel().equals(pesel)){
              list.add(tempPatient);
            }
        }
        return list;
    }
    public Patient savePatient(Patient patient){
        return patientRepository.save(patient);
    }


    public Boolean deletePatient(Patient patient){
        patientRepository.delete(patient);
        return !patientRepository.findAll().contains(patient);
    }

    @Scheduled(cron = "0 0 10 * * ?")   //check every day at 10AM
    public void sendMailReminder() throws MessagingException {
        for (Patient remindPatient : patientRepository.findAll()) {
            if (remindPatient.getDateOfVisit().getDayOfMonth() - LocalDate.now().getDayOfMonth() == 3) { //remind 3 days before
                mailService.sendMailReminder(remindPatient, true);
            }
            else if (remindPatient.getDateOfVisit().getDayOfMonth() - LocalDate.now().getDayOfMonth() == -29) {
                mailService.sendMailReminder(remindPatient, true);
            }
            else if (remindPatient.getDateOfVisit().getDayOfMonth() - LocalDate.now().getDayOfMonth() == -28) {
                mailService.sendMailReminder(remindPatient, true);
            }
            else if (remindPatient.getDateOfVisit().getDayOfMonth() - LocalDate.now().getDayOfMonth() == -27) {
                mailService.sendMailReminder(remindPatient, true);
            }
            else if (remindPatient.getDateOfVisit().getDayOfMonth() - LocalDate.now().getDayOfMonth() == -26) {
                mailService.sendMailReminder(remindPatient, true);
            }
            else if (remindPatient.getDateOfVisit().getDayOfMonth() - LocalDate.now().getDayOfMonth() == -25) {
                mailService.sendMailReminder(remindPatient, true);
            }
        }
    }
    //free hours for visits depended on chosen day
    public List<String> showFreeHours(LocalDate currentDay, String doctor){
        List<String> freeHoursList = new ArrayList<>();
        TreeMap<String, Boolean> tempMap = new TreeMap<String, Boolean>();
        String morningDoc = "dr. Gargula";
        String afternoonDoc = "dr. Tarman";

        for(int i = 8; i <= 19; i++) {               ///office open 8:00 - 19:30
            if (doctor.toLowerCase().equals(morningDoc.toLowerCase()) && i < 14){
                if (i < 10) {
                    tempMap.put("0" + i + ":00", true);  ///free full hours
                    tempMap.put("0" + i + ":30", true);  ///free half hours
                } else {
                    tempMap.put(i + ":00", true);        ///free full hours
                    tempMap.put(i + ":30", true);        ///free half hours
                }
            }
            else if(doctor.toLowerCase().equals(afternoonDoc.toLowerCase()) && i >= 14){
                tempMap.put(i + ":00", true);        ///free full hours
                tempMap.put(i + ":30", true);        ///free half hours
            }

        }

        for (Patient tempPatient: patientRepository.findAll())
        {   if (tempPatient.getDateOfVisit().isEqual(currentDay)){
                tempMap.put(tempPatient.getTimeOfVisit(), false);
            }
        }

        for (Map.Entry<String, Boolean> entry:tempMap.entrySet()){
            if(entry.getValue()){
                freeHoursList.add(entry.getKey());
            }
        }

            return freeHoursList;

}
        public void clearAll(){
        patientRepository.deleteAll();
        }
}
