package com.indutech.gnd.enumTypes;

public enum BankDetail {

	SBI("0"),
	SBBJ("1"),
	SBH("2"),
	NSB("3"),
	SBM("4"),
	SBP("5"),
	BB("6"),
	SBT("7"),
	OMR("8"),
	HK("9");
	
	private final String bank;
	
	BankDetail(String bank){
		this.bank = bank;
	}
	
	public String getBank()
	{
		return this.bank;
	}
	
	
}

