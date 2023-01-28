package mailsending.freemarkerproject.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import mailsending.freemarkerproject.dto.MailRequest;
import mailsending.freemarkerproject.dto.MailResponse;
import mailsending.freemarkerproject.service.EmailService;

@RestController
public class Controller
{

	@Autowired
	private EmailService service;
	
	@PostMapping("/sendingEMail")
	public MailResponse sendMail(@RequestBody MailRequest request) {
		Map<String,Object>model = new HashMap(); 
		model.put("Name",request.getName());
		model.put("location", "Banglore,India");
		
		return service.sendMail(request, model);
		
	}
}
