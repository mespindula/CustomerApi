package com.composablebit.controller;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.put;

import java.text.SimpleDateFormat;
import java.util.List;

import com.composablebit.cors.CorsFilter;
import com.composablebit.dto.AdressRequestDTO;
import com.composablebit.dto.AdressResponseDTO;
import com.composablebit.dto.ApiErrorDTO;
import com.composablebit.dto.CustomerRequestDTO;
import com.composablebit.dto.CustomerResponseDTO;
import com.composablebit.dto.ResponseDTO;
import com.composablebit.model.filter.AdressFilter;
import com.composablebit.model.filter.CustomerFilter;
import com.composablebit.service.AdressService;
import com.composablebit.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

public class CustomerController {
	
	@Inject
	CustomerService customerService;
	
	@Inject
	AdressService adressService;
	
	@Inject
	ObjectMapper mapper;
    
	public void publishEndpoints() {
		
		port(8080);
		CorsFilter.enableCORS();
		
		post("customers", (req, res) -> {

			CustomerRequestDTO customerRequest = mapper.readValue(req.body(), CustomerRequestDTO.class);

			ResponseDTO customerResponse = customerService.create(customerRequest);

			res.type("application/json");
			res.status(200);
			
			return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(customerResponse);

		});
		
		
		get("customers", (req, res) -> {			
			CustomerFilter customerFilter = new CustomerFilter();
			customerFilter.setName(req.queryParams("name"));
			if (req.queryParams("birthDate") != null && !req.queryParams("birthDate").isEmpty()) {
				customerFilter.setBirthDate(new SimpleDateFormat("yyyy-MM-dd").parse(req.queryParams("birthDate")));
			}
			
			AdressFilter addressFilter = new AdressFilter();
			addressFilter.setState(req.queryParams("state"));
			addressFilter.setCity(req.queryParams("city"));
			
			List<CustomerResponseDTO> customerResponse = customerService.find(customerFilter, addressFilter);
			
			res.type("application/json");
			res.status(200);
			
			return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(customerResponse);
		});
        
        get("customers/:id", (req,res) -> {
        	CustomerResponseDTO customerResponse = customerService.findById(req.params(":id"));

            res.type("application/json");
			res.status(200);
			
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(customerResponse);
        });
        
        put("customers/:id", (req, res) -> {		
			CustomerRequestDTO customerRequest = mapper.readValue(req.body(), CustomerRequestDTO.class);
			
			ResponseDTO customerResponse = customerService.update(req.params(":id"), customerRequest);

			res.type("application/json");
			res.status(200);
			
			return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(customerResponse);
		});
        
        delete("customers/:id", (req, res) -> {
        	int count = customerService.delete(req.params(":id"));
        	if (count > 0) {
        		res.status(200);
    			return "Cliente removido com sucesso";
        	} else {
        		res.status(404);
    			return "Cliente não encontrado";
        	}
		});
        
        post("customers/:id/adresses", (req, res) -> {
			AdressRequestDTO adressRequest = mapper.readValue(req.body(), AdressRequestDTO.class);

			ResponseDTO adressResponse = adressService.create(req.params(":id"), adressRequest);

			res.type("application/json");
			res.status(200);
			
			return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(adressResponse);
		});
        
        get("customers/:id/adresses", (req, res) -> {					
        	List<AdressResponseDTO> adresses = adressService.findByCustomerId(req.params(":id"));
			
			res.type("application/json");
			res.status(200);
			
			return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(adresses);
		});
        
        get("customers/:id/adresses/:address_id", (req, res) -> {			
        	List<AdressResponseDTO> adresses = adressService.findAll(req.params(":id"), req.params(":address_id"));
			
			res.type("application/json");
			res.status(200);
			
			return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(adresses);
		});
        
        put("customers/:id/adresses/:address_id", (req, res) -> {			
			AdressRequestDTO adressRequest = mapper.readValue(req.body(), AdressRequestDTO.class);
			ResponseDTO adressResponse = adressService.update(req.params(":id"), req.params(":address_id"), adressRequest);

			res.type("application/json");
			res.status(200);
			
			return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(adressResponse);
		});
        
        delete("customers/:id/adresses/:address_id", (req, res) -> {
        	try {
        		int count = adressService.delete(req.params(":id"), req.params(":address_id"));
            	if (count > 0) {
            		res.status(200);
        			return "Enderço removido com sucesso";
            	} else {
            		res.status(404);
        			return "Enderço não encontrado";
            	}
        	} catch (Exception e) {
        		res.status(500);
    			return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(
    					new ApiErrorDTO("create_customer", "Não foi possível realizar a exclusão: " + e.getMessage()));
			}
        	
		});
        
	}
}
