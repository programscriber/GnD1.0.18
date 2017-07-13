package com.indutech.gnd.service.emboss;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;












import com.indutech.gnd.bo.CreditCardDetailsBO;
import com.indutech.gnd.dao.GNDDAOImpl;
import com.indutech.gnd.dto.CreditCardDetails;
import com.indutech.gnd.dto.RecordEvent;
import com.indutech.gnd.service.CardStateManagerService;
import com.indutech.gnd.service.PropertiesLoader;

public class EmbossMdWorkerThread implements Runnable{
	
	private GNDDAOImpl gndDao;
	private CreditCardDetails creditCardDetails;
	private Logger logger = Logger.getLogger(EmbossWrokerThread.class);
	private String filename;
	private BufferedWriter bw;
	private FileWriter fw;
	private List<CreditCardDetails> list;
	private Properties properties = PropertiesLoader.getInstance().loadProperties();
	private StringBuilder rsn;
	private StringBuilder product;
	private StringBuilder bankshortCode;
	private StringBuilder firstname;
	private StringBuilder lastname;
	private StringBuilder phonenumber;
	private StringBuilder cardStatus;
	
	public EmbossMdWorkerThread(GNDDAOImpl gndDao,List<CreditCardDetails> list,String filename)
	{
		this.gndDao =gndDao;
		this.list =list;
		this.filename = filename;
	}
	
	public void run()
	{

		try {
			int request = 1;
			for(CreditCardDetails creditCardDetails : list) {
				CreditCardDetailsBO details = buildCreditCardDetailsBO(creditCardDetails);				
				if(request == 1) {
					generateFileName(filename);
				}
				body(details, bw, filename);							
				request++;
				creditCardDetails.setStatus((long)CardStateManagerService.CARD_STATUS_MD_GENERATED);
				creditCardDetails.setRuleStatus(CardStateManagerService.CARD_STATUS_MD_GENERATED_STRING);
				creditCardDetails.setCreatedDate(new Date());
				gndDao.saveCardDetailsQc2(creditCardDetails);
				saveRecordEvent(creditCardDetails.getCreditCardDetailsId(),(long)CardStateManagerService.CARD_STATUS_MD_GENERATED, CardStateManagerService.CARD_STATUS_MD_GENERATED_STRING);
							
				
				}
		    }catch(Exception e)
		    {
					
		     }
		 finally {
				try {
					if(bw != null) {
						bw.close();
					}
				}catch(Exception e) {
					logger.error(e);
					e.printStackTrace();
				}
	
	}
}
	
private void saveRecordEvent(Long recordId, Long eventId, String desc) {
		
		RecordEvent event = new RecordEvent();
		event.setEventDate(new Date());
		event.setDescription(desc);
		event.setEventId(eventId);
		event.setRecordId(recordId);
		
		gndDao.saveRecordEventQc2(event);
	}

public CreditCardDetailsBO buildCreditCardDetailsBO(CreditCardDetails details) {
	CreditCardDetailsBO detailsBO = new CreditCardDetailsBO();
	detailsBO.setFildId(details.getFileId());
	detailsBO.setInstitutionId(details.getInstitutionId());
	detailsBO.setRsn(details.getRsn());
	detailsBO.setProduct(details.getProduct());
	detailsBO.setCustomerFirstName(details.getCustomerFirstName());
	detailsBO.setCustomerSurName(details.getCustomerSurName());
	detailsBO.setMobileNo(details.getMobileNo());
	detailsBO.setCardStatus(details.getCardStatus());
	return detailsBO;
}

public void generateFileName(String filename) {
	try {
//		properties.load(inputStream);
		String filePath = properties.getProperty("csv.embossMDGenerated");
		
		String split[] = filename.split("\\.");
		StringBuilder embfile = new StringBuilder(split[0]);
		embfile.append("_MD.");
		embfile.append(split[1]);
		embfile.append(".csv");
		File file = new File(filePath+"/"+embfile);
		if (!file.exists()) 			
			file.createNewFile();						
		 fw = new FileWriter(file.getAbsoluteFile());
		bw = new BufferedWriter(fw); // increased buffer size
	} catch(Exception e) {
		logger.error(e);
		e.printStackTrace();
	}
	
}

public void body(CreditCardDetailsBO details, BufferedWriter bw, String embfilename) {
	String bankcode = embfilename.substring(3, 9);
	String seperator = ",";
	try {
		rsn = new StringBuilder(getPattern(details.getRsn().toString(),10));
		rsn.setLength(10);
		bw.write(rsn.toString());
		
		bw.write(seperator);
		
		product = new StringBuilder(details.getProduct());
		product.setLength(3);
		bw.write(product.toString());
		
		bw.write(seperator);
		String split1[] = embfilename.split("\\.");
		if(split1[0].length() == 25) {
			bankshortCode = new StringBuilder(split1[0].substring(3, 6));
		}
		else {
			String bankCode = gndDao.getBankCode(bankcode);
			bankshortCode = new StringBuilder(bankCode != null?bankCode : "");
		}
		bankshortCode.setLength(10);
		bw.write(bankshortCode.toString());				
		
		bw.write(seperator);
		
		String filename = gndDao.getFileName(details.getFildId());
		String split[] = filename.split("\\.");
		if(!split[1].equalsIgnoreCase("ncf")) {
				
			firstname = new StringBuilder(details.getCustomerFirstName());				
			lastname = new StringBuilder(details.getCustomerSurName());					
		}
		else {
			firstname = new StringBuilder("");
			lastname = new StringBuilder("");
		}
		
		firstname.setLength(40);
		bw.write(firstname.toString());
		
		bw.write(seperator);
		
		lastname.setLength(40);
		bw.write(lastname.toString());
		
		bw.write(seperator);
		
		phonenumber = new StringBuilder(details.getMobileNo());
		phonenumber.setLength(12);
		bw.write(phonenumber.toString());
		
		bw.write(seperator);
		
		cardStatus = new StringBuilder(details.getCardStatus());
		cardStatus.setLength(3);
		bw.write(cardStatus.toString());
		
		bw.write(seperator);
		
		bw.newLine();
	}
	catch(Exception e) {
		logger.error(e);
		e.printStackTrace();
	}
	
	
	
}

public String getPattern(String text, int length) {
	String sample = "";
	for(int i = 0;i<length;i++) {
		sample = sample+"0";
	}
	int len1 = sample.length();
	int len2 = text.length();
	char c[] = new char[length];
	int i = 0;
	int j = 0;
	while(i < len1) {
		if( i == (len1-len2)) {
			c[i] = text.charAt(j++);
			len2--;
		}
		else {
			c[i] = '0';
		}
		i++;
	}
	return new String(c);
}
}
