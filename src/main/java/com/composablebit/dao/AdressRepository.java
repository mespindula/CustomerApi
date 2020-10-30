package com.composablebit.dao;

import java.util.List;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.reflect.BeanMapper;

import com.composablebit.model.Adress;
import com.composablebit.model.Customer;
import com.composablebit.model.filter.AdressFilter;

public class AdressRepository {

	public Adress insert(Adress adress) {
		Jdbi jdbi = Connector.getJdbiConnection();
		
		return jdbi.withHandle(handle -> {
			Long id = handle.createUpdate("INSERT INTO adress(state, city, neighborhood, zipCode, street, number, additionalInformation, main, customerId) "
					+ "VALUES (:state, :city, :neighborhood, :zipCode, :street, :number, :additionalInformation, :main, :customerId)")
				.bindBean(adress)
				.executeAndReturnGeneratedKeys("id")
				.mapTo(Long.class)
				.findOnly();
			adress.setId(id);
			return adress;
		});
	}
	
	public Adress update(Adress adress) {
		Jdbi jdbi = Connector.getJdbiConnection();
		
		jdbi.useHandle(handle ->
			handle.createUpdate("UPDATE adress SET state = :state, city = :city, neighborhood = :neighborhood, zipCode = :zipCode, street = :street, number = :number, "
					+ "additionalInformation = :additionalInformation, main = :main WHERE id = :id")
				.bindBean(adress)
				.execute()
			);
		
		return adress;
	}
	
	public void changeAllMainStatus(Long customerId) {
		Jdbi jdbi = Connector.getJdbiConnection();
		
		jdbi.useHandle(handle ->
			handle.createUpdate("UPDATE adress SET main = false WHERE customerId = :customerId")
				.bind("customerId", customerId)
				.execute()
		);
	}
	
	public Adress selectMainStatus(Long customerId, Long adressId) {
		Jdbi jdbi = Connector.getJdbiConnection();
		jdbi.registerRowMapper(BeanMapper.factory(Customer.class));
		
		Adress adress = null;
		
		try {
			adress = jdbi.withHandle(handle -> 
	        handle.createQuery("SELECT * FROM adress WHERE id = :id AND customerId = :customerId AND main = true")
			        .bind("id", adressId)
		    		.bind("customerId", customerId)
	                .mapToBean(Adress.class)
	                .findOnly()
	                );
		} catch (Exception e) {
			// TODO: handle exception
		}

		return adress;
	}
	
	public List<Adress> selectByCustomerId(Long customerId) {
		Jdbi jdbi = Connector.getJdbiConnection();
		jdbi.registerRowMapper(BeanMapper.factory(Customer.class));
		
		List<Adress> adresses = jdbi.withHandle(handle -> 
        handle.createQuery("SELECT * FROM adress WHERE customerId = :customerId")
        		.bind("customerId", customerId)
                .mapToBean(Adress.class)
                .list()
                );
		
		return adresses;
	}
	
	public List<Adress> select(AdressFilter adressFilter) {
		Jdbi jdbi = Connector.getJdbiConnection();
		
		String query = "SELECT * FROM adress WHERE (:state IS NULL OR state = :state) AND (:city IS NULL OR city = :city) AND customerId IN (<customerId>)";
		
		List<Adress> adresses = jdbi.withHandle(handle -> 
        handle.createQuery(query)
        		.bindBean(adressFilter)
        		.bindList("customerId", adressFilter.getCustomerId())
                .mapToBean(Adress.class)
                .list()
                );
		
		return adresses;
	}
	
	public List<Adress> select(Long customerId) {
		Jdbi jdbi = Connector.getJdbiConnection();
		jdbi.registerRowMapper(BeanMapper.factory(Customer.class));
		
		List<Adress> adresses = jdbi.withHandle(handle -> 
        handle.createQuery("SELECT * FROM adress WHERE :customerId IS NULL OR customerId = :customerId")
        		.bind("customerId", customerId)
                .mapToBean(Adress.class)
                .list()
                );
		
		return adresses;
	}
	
	public List<Adress> select(Long customerId, Long adressId) {
		Jdbi jdbi = Connector.getJdbiConnection();
		jdbi.registerRowMapper(BeanMapper.factory(Customer.class));
		
		List<Adress> adresses = jdbi.withHandle(handle -> 
        handle.createQuery("SELECT * FROM adress WHERE id = :id AND customerId = :customerId")
        		.bind("id", adressId)
        		.bind("customerId", customerId)
                .mapToBean(Adress.class)
                .list()
                );
		
		return adresses;
	}
	
	public int delete(Long customerId, Long adressId) {
		Jdbi jdbi = Connector.getJdbiConnection();
		
		return jdbi.withHandle(handle -> {
			int count = handle.createUpdate("DELETE FROM adress WHERE id = :id AND customerId = :customerId")
			.bind("id", adressId)
			.bind("customerId", customerId)
			.execute();
			return count;
		});
	}
}
