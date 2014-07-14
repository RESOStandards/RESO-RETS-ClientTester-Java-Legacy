package org.realtor.rets.compliance.client.util;
import java.io.File;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.StringTokenizer;

public class Mailer {


    static public void sendMail (String to, String from, String subject, String body,
                          String mailHost, String filename[]) throws javax.mail.MessagingException {
        java.util.Properties properties = new java.util.Properties();
        properties.put("mail.smtp.host", mailHost);
        javax.mail.Session session = javax.mail.Session.getDefaultInstance(properties, null);
        session.setDebug(true);

        // Define message
        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(from));
        } catch (Exception e) {
            message.setFrom(new InternetAddress("unknown@unknown.com"));
        }

		StringTokenizer st = new StringTokenizer(to, ";");
		        while (st.hasMoreTokens()) {
		            String recipient = (String) st.nextToken();
        			message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));

				}

        message.setSubject(subject);

        // Create the message part
        BodyPart messageBodyPart = new MimeBodyPart();

        // Fill the message
        messageBodyPart.setText(body);

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        for (int i=0; i<filename.length; i++) {
            if (filename[i] != null) {
                // Part two is attachment
                messageBodyPart = new MimeBodyPart();
                DataSource source = new FileDataSource(filename[i]);
                messageBodyPart.setDataHandler(new DataHandler(source));
                messageBodyPart.setFileName(filename[i]);
                multipart.addBodyPart(messageBodyPart);
            }
        }

        // Put parts in message
        message.setContent(multipart);

        // Send the message
        try {
            Transport.send(message);
        } catch (Exception e) {
            message.setFrom(new InternetAddress("unknown@unknown.com"));
            Transport.send(message);
        }
    }

    static public void sendMail (String to, String from, String subject, String body,
                          String mailHost, File f) throws javax.mail.MessagingException {
      sendMail(to, from, subject, body, mailHost, new String[] {f.getAbsolutePath()});
    }

    static public void sendMail (String to, String from, String subject, String body,
                          String mailHost, File f[]) throws javax.mail.MessagingException {
        String names[] = new String[f.length];
        for (int i=0; i<names.length; i++) {
            if (f[i] != null) {
                names[i] = f[i].getAbsolutePath();
            } else {
                f[i] = null;
            }
        }
        sendMail(to, from, subject, body, mailHost, names);
    }


    static public void sendMail (String to, String from, String subject, String message,
                          String mailHost ) throws javax.mail.MessagingException {

        // Construct a mail session.
        java.util.Properties properties = new java.util.Properties();
        properties.put("mail.smtp.host", mailHost);
        javax.mail.Session mailSession
            = javax.mail.Session.getDefaultInstance(properties, null);
        mailSession.setDebug(true);

        // Construct the mime message container
        javax.mail.Message emailMessage
            = new javax.mail.internet.MimeMessage(mailSession);

        // Now set the attibutes of the container to the
        // values passed.
        emailMessage.setFrom(new javax.mail.internet.InternetAddress(from));
        javax.mail.internet.InternetAddress[] address
            = javax.mail.internet.InternetAddress.parse(to);
        emailMessage.setRecipients ( javax.mail.Message.RecipientType.TO, address );
        emailMessage.setSubject(subject);
        emailMessage.setSentDate(new java.util.Date());
        emailMessage.setText(message);

        // Dispatch the message
        javax.mail.Transport.send(emailMessage);
    }

    public static final void main(String args[]) {
        try {
            Mailer.sendMail("jthomas@theelectricmind.com", "theelectricmind@yahoo.com", "test", "hello", "theelectricmind.com");
//            Mailer.sendMail("jthomas@theelectricmind.com", "theelectricmind@yahoo.com", "test", "hello", "theelectricmind.com", new String[] {"c:/x.txt"});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
