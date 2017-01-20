package org.pwr.conf;

import javax.annotation.Resource;

import org.springframework.core.env.Environment;

import examples.Main;

public class Test {

	@Resource
	private Environment environment;
	
	public static void main(String[] a) {
		Test t = new Test();
		String myPXropValue = t.environment.getProperty("myprop");
		System.out.println(myPXropValue);
	}
	
}
