package com.indutech.gnd.controller;

import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.List;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.indutech.gnd.bo.DailyNotificationBO;
import com.indutech.gnd.bo.EmailConfigarationBO;
import com.indutech.gnd.dto.DailyNotification;
import com.indutech.gnd.service.MasteDBService;
import com.indutech.gnd.service.NotificationService;

@Controller
@RequestMapping(value = "/emailcont")
public class EmailController {
	  
	  
	@Autowired
	private MasteDBService masterdbService;
	
	@Autowired
    private JavaMailSender mailSender;
	
	@Autowired
	private NotificationService notificationService;
	
	
	
	public NotificationService getNotificationService() {
		return notificationService;
	}

	public void setNotificationService(NotificationService notificationService) {
		this.notificationService = notificationService;
	}

	public MasteDBService getMasterdbService() {
		return masterdbService;
	}

	public void setMasterdbService(MasteDBService masterdbService) {
		this.masterdbService = masterdbService;
	}
	
	//@ModelAttribute("fileDir")
	@RequestMapping(value = "/editemailTable")
	public @ResponseBody ModelAndView editemailTable(ModelMap model,
			Principal principal) {
		
		String username = null;
		try {
			username = principal.getName();
			model.addAttribute("username", username);
		} catch (NullPointerException ne) {
			return new ModelAndView("login");
		}
		List<EmailConfigarationBO> listemailBo=getMasterdbService().getEmailFields();
		ModelAndView view=new ModelAndView("editemailTable");
		//view.addObject("emailConfig", listemailBo.get(0));
		//view.addObject("fileDir",fileDir);
		return view;
	}
	
	@RequestMapping(value = "/editemailui")
	public @ResponseBody ModelAndView editemail(@RequestParam("id") String id,
			@RequestParam("userName") String userName,@RequestParam("password") String password,
			@RequestParam("subject") String subject,@RequestParam("fromemail") String fromemail,
			@RequestParam("ccemail") String ccemail,@RequestParam("bccemail") String bccemail,
			@RequestParam("destinationpath") String destinationpath,@RequestParam("host") String host,
			@RequestParam("port") String port) {
		
			boolean result=getMasterdbService().geteditEmailConfig(id,userName,password,subject,fromemail,ccemail,bccemail,destinationpath,host,port);
		return new ModelAndView();
	}
	
	@RequestMapping(value = "/email")
	public @ResponseBody ModelAndView email(ModelMap model,
			Principal principal) {
		
		String username = null;
		try {
			username = principal.getName();
			model.addAttribute("username", username);
		} catch (NullPointerException ne) {
			return new ModelAndView("login");
		}
		ModelAndView view=new ModelAndView("email");
		//view.addObject("emailConfig", listemailBo.get(0));
		//view.addObject("fileDir",fileDir);
		return view;
	}
	
	@RequestMapping(value = "/dailyEmail")
	public @ResponseBody ModelAndView dailyEmail(ModelMap model,
			Principal principal) {
		
		String username = null;
		try {
			username = principal.getName();
			model.addAttribute("username", username);
		} catch (NullPointerException ne) {
			return new ModelAndView("login");
		}
		DailyNotificationBO config = getNotificationService().getConfigDetails();
		ModelAndView view=new ModelAndView("dailynotifications");
		//view.addObject("emailConfig", listemailBo.get(0));
		view.addObject("config",config);
		return view;
	}
	
	    
	 
	    @RequestMapping(value = "/sendEmail")
	    public @ResponseBody ModelAndView sendEmail(HttpServletRequest request,
	            final @RequestParam CommonsMultipartFile attachFile) {
	 
	        // reads form input
	        final String emailTo = request.getParameter("mailTo").toLowerCase().replace(" ","");
	        final String splitTo[] = emailTo.split("\\,");
	        final String cc = request.getParameter("cc").toLowerCase().replace(" ","");
	        final String splitCc[] = cc.split("\\,");
	        final String bcc = request.getParameter("bcc").toLowerCase().replace(" ","");
	        final String splitBcc[] = bcc.split("\\,");
	        final String subject = request.getParameter("subject");
	        final String message = request.getParameter("message");
	        boolean result = true;
	        // for logging
	        System.out.println("emailTo: " + emailTo);
	        System.out.println("subject: " + subject);
	        System.out.println("message: " + message);
	        System.out.println("attachFile: " + attachFile.getOriginalFilename());
	        ModelAndView view = new ModelAndView("email");
	        try{
	        mailSender.send(new MimeMessagePreparator() {
	 
	            @Override
	            public void prepare(MimeMessage mimeMessage) throws Exception {
	                MimeMessageHelper messageHelper = new MimeMessageHelper(
	                        mimeMessage, true, "UTF-8");
	                if(!emailTo.equals("")){
	   
	                	messageHelper.setTo(splitTo);
	                }
	                if (!cc.equals("")) {
	                	
	                messageHelper.setCc(splitCc);
	                	
	                }
	                if (!bcc.equals("")) {
	                	
	                messageHelper.setBcc(splitBcc);
	                	
	                }
	                messageHelper.setSubject(subject);
	                messageHelper.setText(message);
	                 
	                // determines if there is an upload file, attach it to the e-mail
	                String attachName = attachFile.getOriginalFilename();
	                if (!attachFile.equals("")) {
	                	
	                    messageHelper.addAttachment(attachName, new InputStreamSource() {
	                      
	                        @Override
	                        public InputStream getInputStream() throws IOException {
	                            return attachFile.getInputStream();
	                        }
	                    });
	                }
	                 
	            }
	        });
	        }catch (Exception e)
	        {
	        	result = false;
	        	e.printStackTrace();
	        }
	        view.addObject("result",result);
	        return view;
	    }
	    @RequestMapping(value = "/saveDailyEmail")
	    public @ResponseBody ModelAndView sendDailyEmail(ModelMap model,
				Principal principal,HttpServletRequest request) {
	    	String username = null;
			try {
				username = principal.getName();
				model.addAttribute("username", username);
			} catch (NullPointerException ne) {
				return new ModelAndView("login");
			}
	        // reads form input
	        final String emailTo = request.getParameter("mailTo").toLowerCase().replace(" ","");
	        final String cc = request.getParameter("cc").toLowerCase().replace(" ","");
	        final String bcc = request.getParameter("bcc").toLowerCase().replace(" ","");
	        final String subject = request.getParameter("subject");
	        final String message = request.getParameter("message");
	        final Long id = Long.parseLong(request.getParameter("id"));
	        // for logging
	        System.out.println("emailTo: " + emailTo);
	        System.out.println("subject: " + subject);
	        System.out.println("message: " + message);
	        boolean success = getNotificationService().saveDailyMailConfig(id,emailTo,cc,bcc,message,subject,username);
	        DailyNotificationBO config = getNotificationService().getConfigDetails();
	        ModelAndView view = new ModelAndView("dailynotifications");
	        view.addObject("success",success);
	        view.addObject("config",config);
	        return view;
	    }
	 
	
}