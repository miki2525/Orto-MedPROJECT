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
                "Wizyta odbędzię się dnia: " + "<b>" + patient.getDateOfVisit().toString() + "</b>" + ", o godzinie: " + "<b>" + patient.getTimeOfVisit() + "</b>"
                + ".<br> Wkrótce otrzymasz następną wiadomość z hasłem, umożliwiającym sprawdzenie terminu wizyty na naszej stronie internetowej.";
        String subject = "OrtoMED - Potwierdzenie Rejestracji";
        String to = patient.getEmail();
        Random random = new Random();
        int generateNumber = random.nextInt((1000000 - 100000 + 1) + 100000);

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(text, isHtmlContent);
        javaMailSender.send(mimeMessage);
        if (patient.getPassword() == null) {
            patient.setPassword(generateNumber);
        }
    }

    public void sendMailPass(Patient patient, boolean isHtmlContent) throws MessagingException {
        String text = "Witaj " + patient.getFirstName() + ",<br> Twoje unikalkne hasło to: " + patient.getPassword() + ".<br> Pozdrawiamy,<br>Zespół OrtoMED.";
        String subject = "OrtoMED - Hasło do serwisu";
        String to = patient.getEmail();

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(text, isHtmlContent);
        javaMailSender.send(mimeMessage);
    }
}
