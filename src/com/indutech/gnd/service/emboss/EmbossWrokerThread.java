package com.indutech.gnd.service.emboss;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;

import com.indutech.gnd.dao.FileDAOImpl;
import com.indutech.gnd.dao.GNDDAOImpl;
import com.indutech.gnd.dto.CoreFiles;
import com.indutech.gnd.dto.CreditCardDetails;
import com.indutech.gnd.dto.RecordEvent;
import com.indutech.gnd.service.CardStateManagerService;


public class EmbossWrokerThread implements Runnable {
	
	
	private GNDDAOImpl gndDao;
	private FileDAOImpl fileDAO;
	private String maskedPan;
	private String recordReferenceNumber;
	private Long fileId;
	private String filename;
	private String duplicateEmbossPath;
	private String line;
	private Logger logger = Logger.getLogger(EmbossWrokerThread.class);
	
	
	
	
	public EmbossWrokerThread(GNDDAOImpl gndDao,FileDAOImpl fileDAO,String maskedPan,String recordReferenceNumber,Long fileId,String filename,String duplicateEmbossPath,String line)
	{
		this.gndDao =gndDao;
		this.fileDAO =fileDAO;
		this.maskedPan = maskedPan;
		this.recordReferenceNumber = recordReferenceNumber;
		this.fileId = fileId;
		this.filename = filename;
		this.duplicateEmbossPath = duplicateEmbossPath;
		this.line = line;
		
	}
	
	
	public void run()
	{

		try {
			
			if (recordReferenceNumber != null && recordReferenceNumber.trim().isEmpty() == false
					&& Pattern.compile("\\d+").matcher(recordReferenceNumber.trim()).matches()) {
				
				List<CreditCardDetails> list = gndDao.getRecordRSN(Long.parseLong(recordReferenceNumber.trim()));
				/*List<CreditCardDetails> duplicateList = gndDao
						.getDuplicateRecordRSN(Long.parseLong(recordReferenceNumber.trim()));*/
				if (list.size()>0 && list.get(0).getStatus()==(long)CardStateManagerService.CARD_STATUS_AUFCONVERTED) {
					CreditCardDetails details = (CreditCardDetails) list.get(0);
					details.setStatus((long) CardStateManagerService.CARD_STATUS_EMBOSSA_RECEIVED);
					details.setPanMasked(maskedPan);
					details.setCreatedDate(new Date());
					details.setRuleStatus(CardStateManagerService.CARD_STATUS_EMBOSSA_RECEIVED_STRING);
					details.setEmbossaId(fileId);
					/* cr no 42 */ details.setEmboBatchName(getEmboBatchNameService(filename));
					gndDao.saveCardDetailsQc2(details);
					saveRecordEvent(details.getCreditCardDetailsId(),
							(long) CardStateManagerService.CARD_STATUS_EMBOSSA_RECEIVED,
							CardStateManagerService.CARD_STATUS_EMBOSSA_RECEIVED_STRING);
					
				
				}
				/* CR no 36 starts */
				else if (list.size()>0 && list.get(0).getStatus()>(long)CardStateManagerService.CARD_STATUS_AUFCONVERTED) {
					synchronized (this.getClass()) {
						
					
					try {
						logger.info("duplicate rsn occured in emboss file please check ...");
						String duplicateFileLine = null;
						File duplicateRecFile = new File(duplicateEmbossPath);
						if (!duplicateRecFile.exists()) {
							duplicateRecFile.mkdirs();
						}
						File duplicateFile = new File(duplicateRecFile, filename);
						if (!duplicateFile.exists()) {
							duplicateFile.createNewFile();
						}
						BufferedReader br1 = new BufferedReader(new FileReader(duplicateFile));
						BufferedWriter bw = new BufferedWriter(new FileWriter(duplicateFile, true));
						int duplicateCheck = 0;
						while ((duplicateFileLine = br1.readLine()) != null) {
							if (duplicateFileLine.equals(line)) {
								duplicateCheck++;
								break;
							}

						}
						if (duplicateCheck == 0) {
							bw.write(line);
							bw.newLine();
							bw.flush();
						}
						br1.close();
						bw.close();
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
				/* CR no 36 ends */
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
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
	
	private String getEmboBatchNameService(String fileName) throws ParseException
	{
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMy");
	 	Date date = DateUtils.addDays(sdf.parse(fileName.substring(14,22)), -1);
	 	String qcDate = sdf.format(date);
	 	
		List<CoreFiles> file = fileDAO.getEmbossFile(fileName,qcDate);
		
		String emboBatchName = null;
		if(file.size()>0 && !(file == null))
		{
			 emboBatchName = file.get(file.size()-1).getFilename();
		}
		return emboBatchName;
	}

}
