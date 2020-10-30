package com.composablebit.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

import org.dozer.DozerBeanMapper;

import com.composablebit.dao.AdressRepository;
import com.composablebit.dao.CustomerRepository;
import com.composablebit.dto.AdressResponseDTO;
import com.composablebit.dto.ApiErrorDTO;
import com.composablebit.dto.CustomerRequestDTO;
import com.composablebit.dto.CustomerResponseDTO;
import com.composablebit.dto.ResponseDTO;
import com.composablebit.model.Adress;
import com.composablebit.model.Customer;
import com.composablebit.model.filter.AdressFilter;
import com.composablebit.model.filter.CustomerFilter;

public class CustomerService {
	
	public ResponseDTO create(CustomerRequestDTO dto) {
		ApiErrorDTO error = validateCreateCustomerRequest(dto);
		
		if (error == null) {
			error = new AdressService().validate(dto.getAddress());	
			if (error == null) {
				DozerBeanMapper beanMapper = new DozerBeanMapper();
				Customer customer = beanMapper.map(dto, Customer.class);
				
				customer.setUuid(UUID.randomUUID().toString());
				
				customer = new CustomerRepository().insert(customer);
	
				new AdressService().createNoValidade(customer.getId(), dto.getAddress());		
				
				return loadDataCustomerResponse(customer);
			} else {
				return error;
			}
		} else {
			return error;
		}
	}
	
	private ApiErrorDTO validateCreateCustomerRequest(CustomerRequestDTO dto) {
		ApiErrorDTO error = null;
		StringBuilder errorMsg = new StringBuilder();
		
		if (dto != null) {
			
			if (dto.getCpf() == null) {
				errorMsg.append("CPF não informado!");
			} else {
				dto.setCpf(dto.getCpf().replaceAll("\\.", "").replace("-", ""));
				List<Customer> customers = new CustomerRepository().selectByCPF(dto.getCpf());
				if (!customers.isEmpty()) {
					errorMsg.append("Cliente já existente para o CPF informado!");
				}
			}
			
			if (dto.getBirthDate() == null) {
				errorMsg.append("Data de nascimento não informada!");
			} else if (getAge(dto.getBirthDate()) > 100) {
				errorMsg.append("Idade informada maior que 100 anos!");
			}
			
			if (dto.getName() == null) {
				errorMsg.append("Nome não informado!");
			}
			if (dto.getEmail() == null) {
				errorMsg.append("E-mail não informado!");
			}
			if (dto.getGender() == null) {
				errorMsg.append("Genero não informado!");
			}
			
		} else {
			errorMsg.append("Dados do cliente não informados!");
		}
		
		if (!errorMsg.toString().isEmpty()) {
			error = new ApiErrorDTO("create_customer", errorMsg.toString());
		}
		return error;
	}
	
	public ResponseDTO update(String id, CustomerRequestDTO dto) {
		ApiErrorDTO error = validateUpdateCustomerRequest(id, dto);
		
		if (error != null) {
			error = new AdressService().validate(dto.getAddress());
			if (error != null) {
				Long customerId = Long.parseLong(id);
				
				DozerBeanMapper beanMapper = new DozerBeanMapper();
				Customer customer = beanMapper.map(dto, Customer.class);
				customer.setId(customerId);
				
				customer = new CustomerRepository().update(customer);
				
				new AdressService().updateNoValidate(customerId, dto.getAddress());
				
				return loadDataCustomerResponse(customer);
			} else {
				return error;
			}
		} else {
			return error;
		}
		
	}
	
	private ApiErrorDTO validateUpdateCustomerRequest(String id, CustomerRequestDTO dto) {
		ApiErrorDTO error = null;
		StringBuilder errorMsg = new StringBuilder();
		
		if (id == null) {
			errorMsg.append("Id do cliente não informado!");
		}
		
		if (dto != null) {
			dto.setCpf(dto.getCpf().replaceAll("\\.", "").replace("-", ""));
			List<Customer> customers = new CustomerRepository().selectByCPF(dto.getCpf());

			if (!customers.isEmpty()) {
				customers.forEach(customer -> {
					if (customer.getId() != Long.parseLong(id)) {
						errorMsg.append("Cliente já existente para o CPF informado!");
					}
				}); 
			}
			
			if (dto.getName() == null) {
				errorMsg.append("Nome não informado!");
			}
			if (dto.getEmail() == null) {
				errorMsg.append("E-mail não informado!");
			}
			if (dto.getGender() == null) {
				errorMsg.append("Genero não informado!");
			}
		
			if (dto.getBirthDate() == null) {
				errorMsg.append("Data de nascimento não informada!");
			} else if (getAge(dto.getBirthDate()) > 100) {
				errorMsg.append("Data de nascimento não informada!");
			}
		
		} else {
			errorMsg.append("Dados do cliente não informados!");
		}

		if (!errorMsg.toString().isEmpty()) {
			error = new ApiErrorDTO("create_customer", errorMsg.toString());
		}
		return error;
	}
	
	private int getAge(Date date) {
		GregorianCalendar now = new GregorianCalendar();
		GregorianCalendar birthDate = new GregorianCalendar();
		birthDate.setTime(date);	
		int yearNow = now.get(Calendar.YEAR);
		int yearBirthDate = birthDate.get(Calendar.YEAR);
		return yearNow - yearBirthDate;
	}
	
	public List<CustomerResponseDTO> find(CustomerFilter customerFilter, AdressFilter adressFilter) {		
		List<Customer> customers = new CustomerRepository().select(customerFilter);
		
		if (!customers.isEmpty()) {
			List<Long> ids = new ArrayList<Long>();
			
			customers.forEach(customer -> {
				ids.add(customer.getId());
			});
			
			adressFilter.setCustomerId(ids);
			
			List<Adress> adresses = new AdressRepository().select(adressFilter);
			
			customers.forEach(customer -> {
				adresses.forEach(adress -> {
					if (customer.getId() == adress.getCustomerId()) {
						customer.addAdress(adress);
					}
				});
			});
		}
		
		List<CustomerResponseDTO> customerResponseDTOs = new ArrayList<CustomerResponseDTO>();
		customers.forEach(customer -> {
			DozerBeanMapper beanMapper = new DozerBeanMapper();
			final CustomerResponseDTO customerResponse = beanMapper.map(customer, CustomerResponseDTO.class);
			
			if (customerResponse.getAdresses() != null) {
				customerResponse.getAdresses().forEach(address -> {
					if (address.getMain()) {
						customerResponse.setMainAddress(address);
					}
				});
			}
			
			customerResponseDTOs.add(customerResponse);
		});
		
		return customerResponseDTOs;
	}
	
	public CustomerResponseDTO findById(String id) {
		Long customerId = Long.parseLong(id);
		
		Customer customer = new CustomerRepository().selectById(customerId);
		
		if (customer != null) {
			return loadDataCustomerResponse(customer);
		} else {
			return new CustomerResponseDTO();
		}
	}
	
	public int delete(String id) {
		Long customerId = Long.parseLong(id);
		return new CustomerRepository().delete(customerId);
	}
	
	private CustomerResponseDTO loadDataCustomerResponse(Customer customer) {
		List<Customer> customers = new ArrayList<Customer>();
		customers.add(customer);
		List<CustomerResponseDTO> customerResponseDTOs = loadDataCustomerResponse(customers);
		if (!customerResponseDTOs.isEmpty()) {
			return customerResponseDTOs.get(0);
		}
		return null;
	}
	
	private List<CustomerResponseDTO> loadDataCustomerResponse(List<Customer> customers) {
		List<CustomerResponseDTO> customerResponseDTOs = new ArrayList<CustomerResponseDTO>();
		List<String> mappingFiles = new ArrayList<String>();
		mappingFiles.add("dozer_mapping.xml");
		
		customers.forEach(customer -> {
			DozerBeanMapper beanMapper = new DozerBeanMapper();
			beanMapper.setMappingFiles(mappingFiles);
			final CustomerResponseDTO customerResponse = beanMapper.map(customer, CustomerResponseDTO.class);
			
			new AdressService().findByCustomerId(customer.getId()).forEach(adress -> {
				
				AdressResponseDTO adressResponseDTO = beanMapper.map(adress, AdressResponseDTO.class);
				
				if (adressResponseDTO.getMain()) {
					customerResponse.setMainAddress(adressResponseDTO);
				}
				
				customerResponse.addAdress(adressResponseDTO);
			});
			
			customerResponseDTOs.add(customerResponse);
		});

		return customerResponseDTOs;
	}
}
