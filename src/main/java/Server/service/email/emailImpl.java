package Server.service.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import Server.model.EmailModel;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMessage.RecipientType;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMultipart;
import jakarta.mail.util.ByteArrayDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


import java.util.Base64;
@Service
public class emailImpl implements emailInterface {

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendEmailWithImage(String to, String subject, String body, String imageData) {
        System.out.println("Preparing to send email with image");
    
        

        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);

            System.out.println("here 1");
            // Attach inline image
            MimeMultipart content = new MimeMultipart("related");
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText(body, "UTF-8", "html");
            System.out.println("here 2");

            MimeBodyPart imagePart = new MimeBodyPart();
            System.out.println("here 33");
            System.out.println(imageData);
            String base64Data = imageData.split(",")[1];
            byte[] imageBytes = Base64.getDecoder().decode(base64Data);
            System.out.println("here 4");
            imagePart.setDataHandler(new jakarta.activation.DataHandler(new ByteArrayDataSource(imageBytes, "image/jpeg")));
            imagePart.setHeader("Content-ID", "<image>");
            System.out.println("here 3");

            content.addBodyPart(textPart);
            content.addBodyPart(imagePart);

            message.setContent(content);
            System.out.println("here 4");

            System.out.println("Sending email...");
            javaMailSender.send(message);
            System.out.println("Email sent successfully");
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to send email: " + e.getMessage());
        
    }
}}