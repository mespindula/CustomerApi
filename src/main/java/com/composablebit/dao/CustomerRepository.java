package com.composablebit.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.reflect.BeanMapper;

import com.composablebit.model.Customer;
import com.composablebit.model.filter.CustomerFilter;

public class CustomerRepository {

	public Customer insert(Customer customer) {
		Jdbi jdbi = Connector.getJdbiConnection();
		
		customer.setCreatedAt(LocalDateTime.now());
		
		return jdbi.withHandle(handle -> {
			Long id = handle.createUpdate("INSERT INTO customer(name, email, birthDate, cpf, gender, createdAt, uuid) "
					+ "VALUES (:name, :email, :birthDate, :cpf, :gender, :createdAt, :uuid)")
				.bindBean(customer)
				.executeAndReturnGeneratedKeys("id")
				.mapTo(Long.class)
				.findOnly();
			customer.setId(id);
			return customer;
		});
	}
	
	public Customer update(Customer customer) {
		Jdbi jdbi = Connector.getJdbiConnection();
		
		customer.setUpdateAt(LocalDateTime.now());
		
		jdbi.useHandle(handle ->
			handle.createUpdate("UPDATE customer SET name = :name, email = :email, birthDate = :birthDate, cpf = :cpf, gender = :gender, updateAt = :updateAt "
					+ "WHERE id = :id")
				.bindBean(customer)
				.execute()
			);
		
		return customer;
	}
	
	public List<Customer> select(CustomerFilter customerFilter) {
		Jdbi jdbi = Connector.getJdbiConnection();
		
		String query = "SELECT * FROM customer WHERE (:name IS NULL OR name LIKE '% :name %') AND (:birthDate IS NULL OR birthDate = :birthDate)";
		
		List<Customer> customers = jdbi.withHandle(handle -> 
        handle.createQuery(query)
        		.bindBean(customerFilter)
                .mapToBean(Customer.class)
                .list()
                );
		
		return customers;
	}
	
	public Customer selectById(Long id) {
		Jdbi jdbi = Connector.getJdbiConnection();
		jdbi.registerRowMapper(BeanMapper.factory(Customer.class));
		
		Customer customer = null;
		
		try {
			customer = jdbi.withHandle(handle -> 
	        handle.createQuery("SELECT * FROM customer WHERE id = :id")
	                .bind("id", id)
	                .mapToBean(Customer.class)
	                .findOnly()
	                );
		} catch (Exception e) {
			// TODO: handle exception
		}

		return customer;
	}
	
	public List<Customer> selectByCPF(String cpf) {
		Jdbi jdbi = Connector.getJdbiConnection();
		jdbi.registerRowMapper(BeanMapper.factory(Customer.class));
		
		List<Customer> customers = jdbi.withHandle(handle -> 
        handle.createQuery("SELECT * FROM customer WHERE cpf = :cpf")
                .bind("cpf", cpf)
                .mapToBean(Customer.class)
                .list()
                );
		
		return customers;
	}
	
	public int delete(Long id) {
		Jdbi jdbi = Connector.getJdbiConnection();
		
		return jdbi.withHandle(handle -> {
		int count = handle.createUpdate("DELETE FROM customer WHERE id = :id")
			.bind("id", id)
			.execute();
			return count;
		});
	}
}
