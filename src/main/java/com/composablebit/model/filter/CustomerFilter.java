package com.composablebit.model.filter;

import java.util.Date;

public class CustomerFilter {

	private String name;
	
	private Date birthDate;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	
}
