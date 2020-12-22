package pl.ortomed.ortomedApp.service;

import org.springframework.stereotype.Service;
import pl.ortomed.ortomedApp.model.Patient;
import pl.ortomed.ortomedApp.repository.PatientRepository;

import java.time.LocalDate;
import java.util.*;

@Service
public class PatientService {

    private PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<Patient> showAll(){
        return patientRepository.findAll();
    }

    public List<Patient> findByPesel(Integer pesel){ //can be more than one visit per patient
            List list = new ArrayList();
        for (Patient tempPatient : patientRepository.findAll()){
            if(tempPatient.getPesel() == pesel){
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

    //free hours for visits depended on chosen day
    public List<String> freeHours(LocalDate currentDay){
        List<String> freeHoursList = new ArrayList<>();
        TreeMap<String, Boolean> tempMap = new TreeMap<String, Boolean>();

        for(int i = 8; i <= 19; i++) {               ///office open 8:00 - 19:30
            if (i < 10) {
                tempMap.put("0" + i + ":00", true);  ///free full hours
                tempMap.put("0" + i + ":30", true);  ///free half hours
            } else {
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
}
