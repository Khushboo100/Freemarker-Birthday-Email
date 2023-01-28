package mailsending.freemarkerproject.service;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import mailsending.freemarkerproject.dto.MailRequest;
import mailsending.freemarkerproject.dto.MailResponse;

@Service
public class EmailService
{

	@Autowired
	private JavaMailSender sender;
	
	@Autowired
	private Configuration config;
	
	
	public MailResponse sendMail(MailRequest request, Map<String,Object> model) {
	
		MailResponse reponse=new MailResponse();
		MimeMessage message = sender.createMimeMessage();
		
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message,MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,StandardCharsets.UTF_8.name());
			
//			helper.addAttachment("logo.png", new ClassPathResource("logo.png"));
			
			Template template = config.getTemplate("email-template.ftl");
			String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
			
			helper.setTo(request.getTo());
			helper.setText(html,true);
			helper.setSubject(request.getSubject());
			helper.setFrom(request.getFrom());
			sender.send(message);

			reponse.setMessage("mail send to :"+request.getTo());
			reponse.setStatus(true);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return reponse;	
	}
}
