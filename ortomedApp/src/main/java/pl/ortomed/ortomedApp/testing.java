package pl.ortomed.ortomedApp;

import pl.ortomed.ortomedApp.model.Patient;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class testing   {
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public static void main(String[] args) {
        List<testing> list = new ArrayList<>();

        testing test1 = new testing();
        testing test2 = new testing();
        testing test3 = new testing();
        testing test4 = new testing();
        testing test5 = new testing();
        testing test6 = new testing();
        test1.setTime("8:00");
        test2.setTime("9:30");
        test3.setTime("9:00");
        test4.setTime("10:30");
        test5.setTime("12:00");
        test6.setTime("11:00");

        list.add(test1);
        list.add(test2);
        list.add(test3);
        list.add(test4);
        list.add(test5);
        list.add(test6);

        List<String> tempList = new ArrayList<>();

        Map<String, Boolean> tempMap = new TreeMap<String, Boolean>() {
        };

        for (int i = 8; i <= 19; i++) {
            if (i < 10) {
                tempMap.put("0" + i + ":00", true);  ///empty full hours
                tempMap.put("0" + i + ":30", true);  ///empty half hours
            } else {
                tempMap.put(i + ":00", true);  ///empty full hours
                tempMap.put(i + ":30", true);  ///empty half hours
            }
        }

        for (testing tempPatient : list) {
            tempMap.put(tempPatient.getTime(), false);
        }

        for (Map.Entry<String, Boolean> entry : tempMap.entrySet()) {

            if (entry.getValue()) {
                tempList.add(entry.getKey());
                System.out.println("Mapa2: " + entry.getKey());
            }
        }


        System.out.println(tempList);

    }


}


