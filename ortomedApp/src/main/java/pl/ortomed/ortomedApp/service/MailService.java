package pl.ortomed.ortomedApp.service;

import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import pl.ortomed.ortomedApp.model.Patient;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Random;

@Service
public class MailService {

    private JavaMailSender javaMailSender;

    public MailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendMail(Patient patient, boolean isHtmlContent) throws MessagingException {
        String text = "Witaj " + patient.getFirstName() + ",<br> potwierdzamy rejestrację Twojego terminu.<br>" +
                "Wizyta odbędzię się dnia: <b>" + patient.getDateOfVisit().toString() + "</b>, o godzinie: <b>" + patient.getTimeOfVisit() + "</b>"
                + ".<br> Wkrótce otrzymasz następną wiadomość z hasłem, umożliwiającym sprawdzenie terminu wizyty na naszej stronie internetowej.<br> Pozdrawiamy,<br>Zespół OrtoMED." +
                "<br>===========================================================================<br>" +
                "===========================================================================<br>Ta wiadomość została wygenerowana automatycznie. Prosimy na nią nie odpowiadać";
        String subject = "OrtoMED - Potwierdzenie Rejestracji";
        String to = patient.getEmail();


        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(text, isHtmlContent);
        mimeMessageHelper.setReplyTo("noreply@OrtoMED.pl");
        javaMailSender.send(mimeMessage);
    }

    public void sendMailPass(Patient patient, boolean isHtmlContent) throws MessagingException {
        String text = "Witaj " + patient.getFirstName() + ",<br> Twoje unikalkne hasło to: <b>" + patient.getPassword() + "</b>.<br> Pozdrawiamy,<br>Zespół OrtoMED."
        + "<br>===========================================================================<br>" +
                "===========================================================================<br>Ta wiadomość została wygenerowana automatycznie. Prosimy na nią nie odpowiadać";;
        String subject = "OrtoMED - Hasło do serwisu";
        String to = patient.getEmail();

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(text, isHtmlContent);
        mimeMessageHelper.setReplyTo("noreply@OrtoMED.pl");
        javaMailSender.send(mimeMessage);
    }

    public void sendMailReminder(Patient patient, boolean isHtmlContent) throws MessagingException {
        String text = "Witaj " + patient.getFirstName() + ",<br> Przypominamy o Twojej nadchodzącej wizycie w Orto-MED. <br><br> " +
                "<table width=\"80%\" style=\"background-color: deepskyblue; border: solid\">" +
                "    <tr>" +
                "<th>Data</th><th>Godzina</th><th>Lekarz</th>" +
                "    </tr>" +
                "<tr style=\"text-align: center\">" +
                "  <td>" + patient.getDateOfVisit() + "</td><td>" + patient.getTimeOfVisit() + "</td><td>" + patient.getDoctor() + "</td>" +
                "    </tr>" +
                "</table> <br>" +
                "Pozdrawiamy,<br>Zespół OrtoMED."
                + "<br>===========================================================================<br>" +
                "===========================================================================<br>Ta wiadomość została wygenerowana automatycznie. Prosimy na nią nie odpowiadać";

        String subject = "OrtoMED - Przypomnienie o zaplanowanej wizycie";
        String to = patient.getEmail();

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(text, isHtmlContent);
        mimeMessageHelper.setReplyTo("noreply@OrtoMED.pl");
        javaMailSender.send(mimeMessage);
    }

}
