package com.sambccd.ProjectStructure.selection;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Sets;
import com.sambccd.ProjectStructure.Tag;
import com.sambccd.ProjectStructure.utils.ClassCacheMap;
import com.sambccd.ProjectStructure.utils.HashMapWithSet;

public class SelectionUtils {
	private static String mainPackage = null;
	//--Just for testing purposes--
	static void setMainPackage(String mp){
		mainPackage=mp;
		newCacheOfAllClasses();
	}
	static void resetMainPackage(){
		mainPackage=null;
		newCacheOfAllClasses();
	}
	//------------------------------

	//cache that holds all classes and is possible to query by tag or package
	private static ClassCacheMap cacheOfAllClasses;
	private static void newCacheOfAllClasses(){ cacheOfAllClasses = new ClassCacheMap(mainPackage); };
	static { newCacheOfAllClasses(); }
	
	public static Set<Class<?>> selectByTag(String tag){
		cacheOfAllClasses.populate();
		return Collections.unmodifiableSet(cacheOfAllClasses.getByTag(tag));
	}
	
	public static Set<Class<?>> selectByPackage(String pkg){
		cacheOfAllClasses.populate();
		return Collections.unmodifiableSet(cacheOfAllClasses.getByPackage(pkg));
	}
	
	public static Set<Class<?>> selectByPackageAndTag(String pkg, String tag){
		cacheOfAllClasses.populate();
		Set<Class<?>> byTag = cacheOfAllClasses.getByTag(tag);
		Set<Class<?>> byPackage = cacheOfAllClasses.getByPackage(pkg);
		if(byTag.size()<byPackage.size()){
			return Sets.intersection(byTag, byPackage);
		} else {
			return Sets.intersection(byPackage, byTag);
		}
	}
	
	
	public static Map<String,Set<Class>> getMapClassesByTag(Set<Class<?>> taggedClasses){
		HashMapWithSet<String,Class> map=new HashMapWithSet<>();
		for(Class<?> c:taggedClasses){
			if(!c.isAnnotationPresent(Tag.class)) throw new RuntimeException("Tag not present in class "+c.getName());
			String tagName=c.getAnnotation(Tag.class).value();
			map.putInSet(tagName,c);
		}
		return map;
	}
}
