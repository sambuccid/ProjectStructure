package com.sambccd.ProjectStructure.utils;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

public class ThreeMapWithSet<K,LV> extends TreeMap<K,Set<LV>>{
	public void putInSet(K key, LV value){
		if(!containsKey(key)){
			put(key,new HashSet<LV>());
		}
		get(key).add(value);
	}
}
