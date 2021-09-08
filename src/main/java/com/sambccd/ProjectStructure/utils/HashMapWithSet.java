package com.sambccd.ProjectStructure.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class HashMapWithSet<K,LV> extends HashMap<K,Set<LV>>{
	public void putInSet(K key, LV value){
		if(!containsKey(key)){
			put(key,new HashSet<LV>());
		}
		get(key).add(value);
	}
}
