package springbootEmail;

import java.io.IOException;

import javax.mail.MessagingException;

import freemarker.template.TemplateException;

public interface SendingEmailService {

    void sendEmail(MailModel mailModel) throws MessagingException, IOException, TemplateException;
}
