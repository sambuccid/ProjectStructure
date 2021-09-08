package com.sambccd.ProjectStructure.selection;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;

import com.sambccd.ProjectStructure.Tag;
import com.sambccd.ProjectStructure.utils.HashMapWithSet;

public class SelectionUtils {
	private static SelectionUtils instance = new SelectionUtils();
	static void setInstance(SelectionUtils ins){ //visible for testing purposes
		instance=ins;
	}
	static void resetInstance(){
		instance = new SelectionUtils();
	}
	private static Map<String,Set<Class>> cacheOfTaggedClasses = null;
	private static void populateCache(){
		if(cacheOfTaggedClasses == null){
			Set<Class<?>> classes = getAllTaggedClasses();
			cacheOfTaggedClasses = getMapClassesByTag(classes);
		}
	}
	
	public static Set<Object> selectByTag(String tag){
		populateCache();
		return new HashSet<>(cacheOfTaggedClasses.get(tag));
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
	
	public static Set<Class<?>> getAllTaggedClasses(){
		Reflections r = new Reflections(instance.getMainPackage());
		Set<Class<?>> classes = r.getTypesAnnotatedWith(Tag.class);
		return classes;
	}
	
	
	public String getMainPackage(){
		return null;
	}
}
