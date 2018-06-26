package org.zerock.web;

import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.Test;

public class CalcTest {
	
	int totalCount=13;
	int pagePerCount = 10;
	
	@Test
	public void calc() {
		
		
		System.out.println((int)Math.ceil(totalCount/(double)pagePerCount));
	}
	
}
