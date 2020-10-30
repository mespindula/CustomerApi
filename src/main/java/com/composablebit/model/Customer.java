package com.composablebit.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.composablebit.model.domain.GenderEnum;

public class Customer implements Serializable {

	private static final long serialVersionUID = 1017095406380005474L;

	private Long id;
	
	private String uuid;
	
	private String name;
	
	private String email;
	
	private Date birthDate;
	
	private String cpf;
	
	private GenderEnum gender;
	
	private List<Adress> adresses;
	
	private LocalDateTime createdAt;
	
	private LocalDateTime updateAt;

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
	
	public List<Adress> getAdresses() {
		return adresses;
	}
	
	public void setAdresses(List<Adress> adresses) {
		this.adresses = adresses;
	}
	
	public void addAdress(Adress adress) {
		if (adresses == null) {
			adresses = new ArrayList<Adress>();
		}
		adresses.add(adress);
	}
	
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	
	public LocalDateTime getUpdateAt() {
		return updateAt;
	}
	
	public void setUpdateAt(LocalDateTime updateAt) {
		this.updateAt = updateAt;
	}
	
}
