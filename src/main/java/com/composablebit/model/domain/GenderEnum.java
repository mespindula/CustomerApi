package com.composablebit.model.domain;

public enum GenderEnum {

	MASCULINO ("M", "Masculino"),
	FEMININO  	("F", "Feminino");
	
	private final String value;

	private final String description;

	private GenderEnum(String value, String description) {
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
