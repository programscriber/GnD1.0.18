package com.indutech.gnd.emailService;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Scheduled;

import com.indutech.gnd.dao.NotificationDao;
import com.indutech.gnd.dto.DailyNotification;
import com.indutech.gnd.jasper.reports.JasperReportGenerator;

public class DailyEmailService {
	
	@Autowired
	private JasperReportGenerator jasperReport;
	
	@Autowired
    private JavaMailSender mailSender;
	
	@Autowired
	private NotificationDao notification;
	

	
	public NotificationDao getNotification() {
		return notification;
	}



	public void setNotification(NotificationDao notification) {
		this.notification = notification;
	}



	public JasperReportGenerator getJasperReport() {
		return jasperReport;
	}



	public void setJasperReport(JasperReportGenerator jasperReport) {
		this.jasperReport = jasperReport;
	}



	/*@Scheduled(cron="0 24 18 * * ?")*/
	public void mailService()
	{
		Date todayDate = new Date();
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		String date = df.format(todayDate);
		Long bankId = (long)0;
		System.out.println("To date and from date" + date);
		String filePath = getJasperReport().generateReports(date,date,bankId);
		System.out.println(filePath);
		
		List<DailyNotification> list = getNotification().getDailyMailConfig();
		DailyNotification notify = list.get(0);
		final String emailTo = notify.getTo();
		final String splitTo[] = emailTo.split("\\,");
		final String cc = notify.getCc();
		final String splitCc[] = cc.split("\\,");
		final String bcc = notify.getBcc();
		final String splitBcc[] = bcc.split("\\,");
		String message = notify.getMessage();
		String subject = notify.getSubject();
		
		if(filePath!=null)
		{
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
		     
		               /* Path path = Paths.get("filePath");
		                byte[] content = Files.readAllBytes(path);*/
		            
		                	
		                	messageHelper.addAttachment("MyTestFile.txt", new File(filePath));
		                   
		              System.out.println("Your daily Schedule Mail has been sent");
		                 
		            }
		        });
		        }catch (Exception e)
		        {
		        	e.printStackTrace();
		        }
		}
		
	}
	
}
