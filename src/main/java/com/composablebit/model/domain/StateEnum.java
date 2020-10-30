package com.composablebit.model.domain;

public enum StateEnum {

	AC ("AC", "Acre"),
	SP	("SP", "São Paulo"),
	RJ	("RJ", "Rio de Janeiro"),
	BA	("BA", "Bahia"),
	SC	("SC", "Santa Catarina"),
	RS	("RS", "Rio Grande do Sul"),
	TO 	("TO", "Tocantins");
	
	private final String value;

	private final String description;

	private StateEnum(String value, String description) {
		this.value = value;
		this.description = description;
	}
	
	public String getValue() {
		return value;
	}

	public String getDescription() {
		return description;
	}
}
