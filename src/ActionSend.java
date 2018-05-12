import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.mail.Transport.send;


public class ActionSend implements ActionListener {

    public void actionPerformed(ActionEvent e) {

        EmailClient ec = new EmailClient();

        // setting up the server properties
        Properties props = new Properties();

        // using TLS, works on Ubuntu and Windows
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // using SSL, only works on Windows
//        props.put("mail.smtp.host", "smtp.gmail.com");
//        props.put("mail.smtp.socketFactory.port", "465");
//        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.port", "465");

        Session session = Session.getInstance(props);

        try {

            // if the message is sent successfully

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(ec.getUsername()));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(ec.getToText()));
            message.setSubject(ec.getSubject());
            message.setText(ec.getMailText());

            // providing user information to log into Google
            send(message, ec.getUsername(), ec.getPass());

            ec.successDialog("The E-mail was sent successfully!", "E-mail sent");

        } catch (MessagingException ex) {

            ex.printStackTrace();

            // if there was a problem while sending the message
            ec.errorDialog("Something went wrong...");
        }

    }

}