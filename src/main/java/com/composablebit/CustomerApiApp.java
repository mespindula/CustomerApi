package com.composablebit;

import com.composablebit.controller.CustomerController;
import com.composablebit.module.AppModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class CustomerApiApp {

	private static Injector injector;
	
	public static void main(String[] args) {
		CustomerApiApp app = new CustomerApiApp();
		injector = app.createInjector();
		app.loadCustomerEndpoints();
	}
	
	private Injector createInjector() {
		return Guice.createInjector(new AppModule());
	}
	
	private void loadCustomerEndpoints() {
		CustomerController customerController = injector.getInstance(CustomerController.class);
		customerController.publishEndpoints();
	}
	
}
