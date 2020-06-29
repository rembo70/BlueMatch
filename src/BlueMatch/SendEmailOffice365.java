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
    //private static final String username = "rm@newspark.nl";


    //private final String from = "rm@newspark.nl";

    private final String subject = "Test mail";
    private final String messagebody = "Test message";

    boolean sendEmail(String from, PasswordField password, String destination, String subject, String messagebody) {
        System.out.println("in sendemail");
        if (from != null && password!=null) {
            System.out.println("username and password present");
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
                alert.setContentText("Sorry je mail adres / wachtwoord is niet correct of er is geen verbinding met mail server, er is geen mail verstuurd naar " + destination + "\n \n Zonder correcte mail gegevens kunnen vanuit BlueMatch geen automatische mails worden verstuurd");

                alert.showAndWait();//display connection error to the user, maybe allow them to retry
                return false;
                //System.out.println("Sorry je wachtwoord is niet correct, er is geen mail verstuurd");
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
