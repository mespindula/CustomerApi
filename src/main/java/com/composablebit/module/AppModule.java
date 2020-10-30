package com.composablebit.module;

import org.dozer.DozerBeanMapper;

import com.composablebit.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
	
public class AppModule extends AbstractModule{

	@Override
	protected void configure() {
		bind(ObjectMapper.class);
		bind(DozerBeanMapper.class);
		bind(CustomerService.class);
	}
}
