package BlueMatch;

import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

class SendEmailOffice365 {

    private static final Logger LOGGER = Logger.getAnonymousLogger();
    private static final String SERVER_SMTP = "smtp.office365.com";
    private static final int PORT_SERVER_SMTP = 587;

    boolean sendEmail(String from, PasswordField password, String destination, String subject, String messagebody) {
        if (from != null && password!=null) {
            final Session session = Session.getInstance(this.getEmailProperties(), new Authenticator() {

                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    String passwordfield = password.getText();
                    return new PasswordAuthentication(from, passwordfield);
                }
            });

            try {
                final Message message = new MimeMessage(session);
                message.setRecipient(Message.RecipientType.TO, new InternetAddress(destination));
                message.setFrom(new InternetAddress(from));
                message.setSubject(subject);
                message.setContent(messagebody,"text/html");
                message.setSentDate(new Date());
                System.out.println("Sending message:" + subject + " to " + destination);
                Transport.send(message);


            } catch (javax.mail.AuthenticationFailedException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setHeaderText("");
                alert.setContentText("Sorry je mail adres / wachtwoord is niet correct. \n\nEr is geen mail verstuurd naar " + destination + " ");

                alert.showAndWait();//display connection error to the user, maybe allow them to retry
                return false;

            } catch (final MessagingException ex) {
                LOGGER.log(Level.WARNING, "Error sending message: " + ex.getMessage(), ex);
                return false;
            }
            return true;
        }
        else{
            return false;
        }
    }


    private Properties getEmailProperties() {
        final Properties config = new Properties();
        config.put("mail.smtp.auth", "true");
        config.put("mail.smtp.starttls.enable", "true");
        config.put("mail.smtp.host", SERVER_SMTP);
        config.put("mail.smtp.port", PORT_SERVER_SMTP);
        return config;
    }
}
