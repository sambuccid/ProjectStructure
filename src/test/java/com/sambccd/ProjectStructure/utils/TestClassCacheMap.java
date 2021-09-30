package com.sambccd.ProjectStructure.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedMap;

import org.junit.Test;

public class TestClassCacheMap {
	
	@Test
	public void testGetSubpackagesFromThreeMap(){
		ClassCacheMap cache = new ClassCacheMap(null);
		ThreeMapWithSet<String, Class<?>> threeMapWithPackages = new ThreeMapWithSet<>();
		cache.setCacheByPackageSorted(threeMapWithPackages);
		threeMapWithPackages.put("aa.bb.cc", new HashSet<>());
		threeMapWithPackages.put("aa.bb", new HashSet<>());
		threeMapWithPackages.put("aa.bba", new HashSet<>());
		threeMapWithPackages.put("aa.bc", new HashSet<>());
		threeMapWithPackages.put("aa.ba", new HashSet<>());
		threeMapWithPackages.put("aa.bbA", new HashSet<>());
		threeMapWithPackages.put("aa.bb_", new HashSet<>());
		threeMapWithPackages.put("aa.bb.cc.s", new HashSet<>());
		threeMapWithPackages.put("aa.bb.cc.e", new HashSet<>());
		SortedMap<String, Set<Class<?>>> mapWithSubPackages = cache.getSubMapContainingSubPackages("aa.bb");
		
		assertEquals(mapWithSubPackages.size(), 3);
		assertTrue(mapWithSubPackages.containsKey("aa.bb.cc"));
		assertTrue(mapWithSubPackages.containsKey("aa.bb.cc.s"));
		assertTrue(mapWithSubPackages.containsKey("aa.bb.cc.e"));
	}
}
