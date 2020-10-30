package com.composablebit.dto;

import java.util.ArrayList;
import java.util.List;

import com.composablebit.model.domain.GenderEnum;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerResponseDTO extends ResponseDTO {

	private Long id;
	
	private String uuid;
	
	private String name;
	
	private String email;
	
	private String birthDate;
	
	private String cpf;
	
	private GenderEnum gender;
	
	private AdressResponseDTO mainAddress;
	
	private List<AdressResponseDTO> adresses;
	
	private String createdAt;
	
	private String updateAt;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

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

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
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

	public AdressResponseDTO getMainAddress() {
		return mainAddress;
	}
	
	public void setMainAddress(AdressResponseDTO mainAddress) {
		this.mainAddress = mainAddress;
	}
	
	public List<AdressResponseDTO> getAdresses() {
		return adresses;
	}
	
	public void setAdresses(List<AdressResponseDTO> adresses) {
		this.adresses = adresses;
	}
	
	public void addAdress(AdressResponseDTO adress) {
		if (adresses == null) {
			adresses = new ArrayList<AdressResponseDTO>();
		}
		adresses.add(adress);
	}
	
	public String getCreatedAt() {
		return createdAt;
	}
	
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	
	public String getUpdateAt() {
		return updateAt;
	}
	
	public void setUpdateAt(String updateAt) {
		this.updateAt = updateAt;
	}
	
}
