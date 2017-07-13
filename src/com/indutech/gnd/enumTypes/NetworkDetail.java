package com.indutech.gnd.enumTypes;

public enum NetworkDetail {
	
	MASTER("1"),
	VISA("2"),
	RUPAY("3");
	
	private final String network;
	
	NetworkDetail(String network)
	{
		this.network = network;
	}
	
	public String getNetwork()
	{
		return this.network;
	}
	

}
