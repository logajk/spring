package com.abalia.utils;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class ComunCollection {

	public static Map sortByValue(Map unsortedMap) {
		Map sortedMap = new TreeMap(new ValueComparator(unsortedMap));
		sortedMap.putAll(unsortedMap);
		return sortedMap;
	}
	
	public static TreeMap<String, Long> sortMapByValue(HashMap<String, Long> map){
		Comparator<String> comparator = new ValueComparator2(map);
		
		TreeMap<String, Long> result = new TreeMap<>(comparator);
		result.putAll(map);
		return result;
	}
}
