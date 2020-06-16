package BlueMatch;

import javafx.scene.control.PasswordField;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SendEmailOffice365 {

    private static final Logger LOGGER = Logger.getAnonymousLogger();
    private static final String SERVER_SMTP = "smtp.office365.com";
    private static final int PORT_SERVER_SMTP = 587;
    private static final String username = "rm@newspark.nl";


    private final String from = "rm@newspark.nl";

    private final String subject = "Test mail";
    private final String messageContent = "Test message";

    public void sendEmail(PasswordField password, String destination) {
        final Session session = Session.getInstance(this.getEmailProperties(), new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                String passwordfield=password.getText();
                return new PasswordAuthentication(username, passwordfield);
            }
        });

        try {
            final Message message = new MimeMessage(session);
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(destination));
            message.setFrom(new InternetAddress(from));
            message.setSubject(subject);
            message.setText(messageContent);
            message.setSentDate(new Date());
            Transport.send(message);
        } catch (final MessagingException ex) {
            LOGGER.log(Level.WARNING, "Error sending message: " + ex.getMessage(), ex);
        }
    }

    public Properties getEmailProperties() {
        final Properties config = new Properties();
        config.put("mail.smtp.auth", "true");
        config.put("mail.smtp.starttls.enable", "true");
        config.put("mail.smtp.host", SERVER_SMTP);
        config.put("mail.smtp.port", PORT_SERVER_SMTP);
        return config;
    }
}
