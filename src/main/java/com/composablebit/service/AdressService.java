package com.composablebit.service;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;

import com.composablebit.dao.AdressRepository;
import com.composablebit.dto.AdressRequestDTO;
import com.composablebit.dto.AdressResponseDTO;
import com.composablebit.dto.ApiErrorDTO;
import com.composablebit.dto.ResponseDTO;
import com.composablebit.model.Adress;

public class AdressService {

	public Adress createNoValidade(Long customerId, AdressRequestDTO dto) {
		if (dto != null) {
			DozerBeanMapper beanMapper = new DozerBeanMapper();
			
			Adress address = beanMapper.map(dto, Adress.class);
			address.setCustomerId(customerId);
			address.setMain(Boolean.TRUE);
			
			address = new AdressRepository().insert(address);
			
			return address;
		}
		return null;
	}
	
	public ResponseDTO create(String id, AdressRequestDTO dto) {
		ApiErrorDTO error = validate(dto);
		
		if (error == null) {
			Long customerId = Long.parseLong(id);
			
			DozerBeanMapper beanMapper = new DozerBeanMapper();
			
			Adress adress = beanMapper.map(dto, Adress.class);
			adress.setCustomerId(customerId);
			
			if (adress.getMain()) {
				new AdressRepository().changeAllMainStatus(customerId);
			}
			
			adress = new AdressRepository().insert(adress);
			
			return beanMapper.map(adress, AdressResponseDTO.class);
		} else {
			return error;
		}
	}
	
	public ApiErrorDTO validate(AdressRequestDTO dto) {
		ApiErrorDTO error = null;
		StringBuilder errorMsg = new StringBuilder();
		
		if (dto != null) {
			if (dto.getState() == null) {
				errorMsg.append("Estado não informado!");
			}
			if (dto.getCity() == null) {
				errorMsg.append("Cidade não informada!");
			}
			if (dto.getNeighborhood() == null) {
				errorMsg.append("Bairro não informado!");
			}
			if (dto.getZipCode() == null) {
				errorMsg.append("CEP não informado!");
			}
			if (dto.getStreet() == null) {
				errorMsg.append("Logradouro não informado!");
			}
			if (dto.getMain() == null) {
				errorMsg.append("Não informado se endereço principal ou secundário!");
			}
		} else {
			errorMsg.append("Dados do endereço não informados!");
		}
		
		if (!errorMsg.toString().isEmpty()) {
			error = new ApiErrorDTO("create_customer", errorMsg.toString());
		}
		return error;
	}
	
	public Adress updateNoValidate(Long customerId, AdressRequestDTO dto) {		
		DozerBeanMapper beanMapper = new DozerBeanMapper();
		
		Adress adress = beanMapper.map(dto, Adress.class);
		
		if (adress.getMain()) {
			new AdressRepository().changeAllMainStatus(customerId);
		}
		
		adress = new AdressRepository().update(adress);
		
		return adress;
	}
	
	public ResponseDTO update(String customerId, String addressId, AdressRequestDTO dto) {
		ApiErrorDTO error = validate(dto);
		
		if (error == null) {
			Long custId = Long.parseLong(customerId);
			Long id = Long.parseLong(addressId);
			
			DozerBeanMapper beanMapper = new DozerBeanMapper();
			
			Adress adress = beanMapper.map(dto, Adress.class);
			adress.setId(id);
			
			if (adress.getMain()) {
				new AdressRepository().changeAllMainStatus(custId);
			} else {
				if (new AdressRepository().selectMainStatus(custId, id) != null) {
					return new ApiErrorDTO("create_customer", "O cliente obrigatóriamente deve ter um endereço como principal");
				}
			}
			
			adress = new AdressRepository().update(adress);
			
			return beanMapper.map(adress, AdressResponseDTO.class);
		} else {
			return error;
		}
	}
	
	public List<AdressResponseDTO> findByCustomerId(String id) {
		Long customerId = Long.parseLong(id);
		
		List<Adress> adresses = new AdressRepository().select(customerId);
		
		List<AdressResponseDTO> adressDto = new ArrayList<AdressResponseDTO>();
		DozerBeanMapper beanMapper = new DozerBeanMapper();
		
		adresses.forEach(adress -> {
			adressDto.add(beanMapper.map(adress, AdressResponseDTO.class));
		});
		
		return adressDto;
	}
	
	public List<Adress> findByCustomerId(Long customerId) {
		return new AdressRepository().selectByCustomerId(customerId);
	}
	
	public List<AdressResponseDTO> findAll(String customerId, String addressId) {
		Long custId = Long.parseLong(customerId);
		Long id = Long.parseLong(addressId);
		
		List<Adress> adresses = new AdressRepository().select(custId, id);
		
		List<AdressResponseDTO> adressDto = new ArrayList<AdressResponseDTO>();
		DozerBeanMapper beanMapper = new DozerBeanMapper();
		
		adresses.forEach(adress -> {
			adressDto.add(beanMapper.map(adress, AdressResponseDTO.class));
		});
		
		return adressDto;
	}
	
	public int delete(String customerId, String addressId) throws Exception {
		Long custId = Long.parseLong(customerId);
		Long id = Long.parseLong(addressId);
		
		if (new AdressRepository().selectMainStatus(custId, id) != null) {
			throw new Exception("O cliente obrigatóriamente deve ter um endereço como principal");
		}
		return new AdressRepository().delete(custId, id);
	}
}
