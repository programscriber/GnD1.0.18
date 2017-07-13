package com.indutech.gnd.enumTypes;

public enum DCMS {

	FSS("1"),
	ELECTRA("2");
	
	private final String dcms;
	
	DCMS(String dcms)
	{
		this.dcms = dcms;
	}
	
	public String getDCMS()
	{
		return this.dcms;
	}
}
