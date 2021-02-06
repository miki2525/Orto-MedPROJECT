package pl.ortomed.ortomedApp.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import pl.ortomed.ortomedApp.model.Patient;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
public class MailServiceTest {

    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private MailService mailService;

    private Patient patientTest;
    private MimeMessage mimeMessage;

    @BeforeEach
    void setUp() {
        patientTest = new Patient(LocalDate.now().plusDays(2), "dr. Gargula", "11:30", "test",
                "test", 99080955104L, 505666123,
                "testowymail@gmail.com", 1);
        mimeMessage = new MimeMessage((Session) null);
    }


    @Test
    void shouldSendMail() throws MessagingException {
        //given
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
        //when
        mailService.sendMail(patientTest, true);
        //then
        verify(javaMailSender, times(1)).send(mimeMessage);
    }


    @Test
    void shouldSendMailPass() throws MessagingException {
        //given
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
        //when
        mailService.sendMailPass(patientTest, true);
        //then
        assertThat(mimeMessage.getRecipients(Message.RecipientType.TO)[0].toString()).isEqualTo("testowymail@gmail.com");
    }


    @Test
    void shouldSendMailReminder() throws MessagingException{
        //given
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
        //when
        mailService.sendMailReminder(patientTest, true);
        //then
        verify(javaMailSender, times(1)).send(mimeMessage);
        assertThat(mimeMessage.getSubject()).isEqualTo("OrtoMED - Przypomnienie o zaplanowanej wizycie");
    }
}
