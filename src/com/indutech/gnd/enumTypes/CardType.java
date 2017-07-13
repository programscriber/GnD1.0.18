package com.indutech.gnd.enumTypes;

public enum CardType {

	CHIP("1"),
	NONCHIP("2"),
	MAGSTRIP("3");
	
	private final String cardType;
	
	CardType(String cardType){
		this.cardType = cardType;
	}
	
	public String getCardType()
	{
		return this.cardType;
	}
}
