package com.composablebit.dao;

import org.jdbi.v3.core.Jdbi;

public class Connector {
	
	private static final String URL = "jdbc:mysql://localhost/customer_api?useTimezone=true&serverTimezone=UTC";
	private static final String USER_NAME = "root";
	private static final String PASSOWRD = "!Rivets@0000!@#";
	
	private static Jdbi jdbi = null;
    
	private Connector() {
	}
	
	public static Jdbi getJdbiConnection() {
		if (jdbi == null) {
			jdbi = Jdbi.create(URL, USER_NAME, PASSOWRD);
		}
		return jdbi;
	}
	
}
