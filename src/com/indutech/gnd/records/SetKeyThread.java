package com.indutech.gnd.records;

import com.google.common.collect.Multimap;
import com.indutech.gnd.dao.GNDDAOImpl;
import com.indutech.gnd.dto.CreditCardDetails;

public class SetKeyThread implements Runnable{
	
	private CreditCardDetails details;
	private GNDDAOImpl gndDao;
	private Multimap<String, CreditCardDetails> map ;
	
	public SetKeyThread(CreditCardDetails details,GNDDAOImpl gndDao,Multimap<String, CreditCardDetails> map )
	{
		this.details = details;
		this.gndDao = gndDao;
		this.map = map;
	}
	
	public void run()
	{
		String securityPrinters  = gndDao.getSecurityPrinters(details.getLcpcBranch());
		synchronized(this.getClass())
		{
		map.put(securityPrinters, details);
		}
	}

}
