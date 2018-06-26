package org.zerock.web;

import org.junit.Test;

class A { 
	
	int a=10;
	int b=20;
	public int getA() {
		return a;
	}
	public void setA(int a) {
		this.a = a;
	}
	public int getB() {
		return b;
	}
	public void setB(int b) {
		this.b = b;
	}
	
}


class B extends A{
	int c = 30;

	public int getC() {
		return c;
	}

	public void setC(int c) {
		this.c = c;
	}
	
	
}


public class ExtendsTest {

	@Test
	public void calc() {
		/*	*/
		B bb = new B();
		A aa = bb;
		
		System.out.println("aa : " + aa);
		System.out.println("bb : " + bb);
		
		bb = (B)aa;
		

	
		

	}
}
