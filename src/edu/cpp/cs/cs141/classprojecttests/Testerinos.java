package edu.cpp.cs.cs141.classprojecttests;

import edu.cpp.cs.cs141.classproject.*;

public class Testerinos {
	
	public static void main(String[] args) {
		testMapPrint();
	}
	
	public static void testMapPrint(){
		Map m = new Map();
		m.addObject((int) (Math.random() * 9), (int) (Math.random() * 9), ((Object)new Ninja()));
		m.addObject((int) (Math.random() * 9), (int) (Math.random() * 9), ((Object)new Ninja()));
		m.addObject((int) (Math.random() * 9), (int) (Math.random() * 9), ((Object)new Ninja()));
		m.addObject((int) (Math.random() * 9), (int) (Math.random() * 9), ((Object)new Ninja()));
		System.out.println(m.toString());
	}
}
