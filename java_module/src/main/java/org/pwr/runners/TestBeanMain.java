package org.pwr.runners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

public class TestBeanMain {

	@Autowired
	public TestBean tb;

	@Autowired
	public Environment environment;

	public void zzz() {
		String s = this.tb.findOutXyz();
		System.out.println(s);
		String bbb = environment.getProperty("aaa");
		System.out.println(bbb);
	}
}
