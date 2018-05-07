import java.awt.*;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.mail.Transport.send;


public class ActionSend implements ActionListener {

    public void actionPerformed(ActionEvent e) {

        EmailClient ec = new EmailClient();

        // setting up the server properties
        Properties props = new Properties();
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props);

        try {

            // if the message is sent successfully

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(ec.getFromText()));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(ec.getToText()));
            message.setSubject(ec.getSubject());
            message.setText(ec.getMailText());

            // providing user information to log into Google
            send(message, ec.getUsername(), ec.getPass());


            // creating "e-mail sent" dialog
            JOptionPane optionPane = new JOptionPane("The E-mail was sent successfully!", JOptionPane.INFORMATION_MESSAGE);
            JPanel buttonPanel = (JPanel)optionPane.getComponent(1);
            JButton buttonOk = (JButton)buttonPanel.getComponent(0);
            buttonOk.setPreferredSize(new Dimension(43, 25));
            // setting "ok" button background color, depending on the current theme
            buttonOk.setBackground(ec.getTheme().equals(Theme.BEIGE) ? new Color(0xF0E09B) : ( ec.getTheme().equals(Theme.BLUE) ? new Color(0x4BA2C7) : ( ec.getTheme().equals(Theme.PINK) ? new Color(0xD67A98) : new Color(0x1F1F1F) ) ) );
            if (ec.getTheme().equals(Theme.BLACK)) buttonOk.setForeground(Color.WHITE);
            else buttonOk.setForeground(Color.BLACK);
            buttonOk.setBorder(BorderFactory.createLineBorder(new Color(0x858585)));
            buttonOk.setFocusPainted(false);
            JDialog dialog = optionPane.createDialog(null, "E-mail sent");
            dialog.setVisible(true);


        } catch (MessagingException ex) {

            ex.printStackTrace();

            // if there was a problem while sending the message

            // creating "error" dialog
            JOptionPane optionPane = new JOptionPane("   Something went wrong...", JOptionPane.ERROR_MESSAGE);
            JPanel buttonPanel = (JPanel)optionPane.getComponent(1);
            JButton buttonOk = (JButton)buttonPanel.getComponent(0);
            buttonOk.setPreferredSize(new Dimension(43, 25));
            // setting "ok" button background color, depending on the current theme
            buttonOk.setBackground(ec.getTheme().equals(Theme.BEIGE) ? new Color(0xF0E09B) : ( ec.getTheme().equals(Theme.BLUE) ? new Color(0x4BA2C7) : ( ec.getTheme().equals(Theme.PINK) ? new Color(0xD67A98) : new Color(0x1F1F1F) ) ) );
            if (ec.getTheme().equals(Theme.BLACK)) buttonOk.setForeground(Color.WHITE);
            else buttonOk.setForeground(Color.BLACK);
            buttonOk.setBorder(BorderFactory.createLineBorder(new Color(0x858585)));
            buttonOk.setFocusPainted(false);
            JDialog dialog = optionPane.createDialog(null, "Error");
            dialog.setVisible(true);
        }

    }

}