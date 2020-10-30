package com.composablebit.dto;

import java.sql.Date;

import com.composablebit.model.domain.GenderEnum;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerRequestDTO {

	private String name;
	
	private String email;
	
	private Date birthDate;
	
	private String cpf;
	
	private GenderEnum gender;
	
	private AdressRequestDTO address;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public GenderEnum getGender() {
		return gender;
	}

	public void setGender(GenderEnum gender) {
		this.gender = gender;
	}

	public AdressRequestDTO getAddress() {
		return address;
	}
	
	public void setAddress(AdressRequestDTO address) {
		this.address = address;
	}
	
}
