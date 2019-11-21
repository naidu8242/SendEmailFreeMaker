package springbootEmail;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;


@Service
public class SendingEmailServiceImpl implements SendingEmailService {

    private static Logger log = LoggerFactory.getLogger(SendingEmailServiceImpl.class);

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    @Qualifier("emailConfigBean")
    private Configuration emailConfig;

    @Override
    public void sendEmail(MailModel mailModel) throws MessagingException, IOException, TemplateException {

    	System.out.println("Helo This is email");
        MailModel mailModels = new MailModel();
        mailModels.setFrom("naidu8242@gmail.com");
        mailModels.setTo("plakshunnaidu@gmail.com");
        mailModels.setSubject("Sending Email with Thymeleaf HTML Template Example");
        
        Map<String, String> mode = new HashMap<String, String>();
        mode.put("name", "Memorynotfound.com");
        mode.put("location", "Belgium");
        mode.put("signature", "http://memorynotfound.com");
        mailModels.setModel(mode);
        
        /**
         * Add below line if you need to create a token to verification emails and uncomment line:32 in "email.ftl"
         * model.put("token",UUID.randomUUID().toString());
         */



        log.info("Sending Email to: " + mailModels.getTo());


        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
        mimeMessageHelper.addInline("logo.png", new ClassPathResource("classpath:/techmagisterLogo.png"));

        Template template = emailConfig.getTemplate("email.ftl");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, mailModels.getModel());

        mimeMessageHelper.setTo("plakshunnaidu@gmail.com");
        mimeMessageHelper.setText(html, true);
        mimeMessageHelper.setSubject("Hello");
        mimeMessageHelper.setFrom("naidu8242@gmail.com");


        emailSender.send(message);

    }
}
