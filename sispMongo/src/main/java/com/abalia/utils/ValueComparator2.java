package com.abalia.utils;

import java.util.Comparator;
import java.util.HashMap;

public class ValueComparator2 implements Comparator<String> {

	HashMap<String, Long> map =  new HashMap<String, Long>();

	public ValueComparator2(HashMap<String, Long> map) {
		// TODO Auto-generated constructor stub
		this.map.putAll(map);
	}
	
	@Override
	public int compare(String s1, String s2) {
		// TODO Auto-generated method stub
		if(map.get(s1) >= map.get(s2))
			return -1;
		else
			return 1;
	}

}
