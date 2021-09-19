package com.sambccd.ProjectStructure.selection;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
	
	public static Set<Object> selectByTag(String tag){
		cacheOfAllClasses.populate();
		return new HashSet<>(cacheOfAllClasses.getByTag(tag));
	}
	
	public static Set<Object> selectByPackage(String pkg){
		cacheOfAllClasses.populate();
		return new HashSet<>(cacheOfAllClasses.getByPackage(pkg));
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
