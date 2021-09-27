package com.sambccd.ProjectStructure.utils;

import java.util.HashSet;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;

import com.sambccd.ProjectStructure.Tag;
import com.sambccd.ProjectStructure.selection.ObjectType;

public class ClassCacheMap {
	private HashMapWithSet<String, Class<?>> cacheByTag;
	private HashMapWithSet<String, Class<?>> cacheByPackage;
	//private HashMapWithSet<ObjectType, Class<?>> cacheByType;
	private String mainPackage;
	public ClassCacheMap(String pkg){
		mainPackage=pkg;
	}
	
	//-------------Internal Working--------
	public void populate(){
		if(cacheByTag != null && cacheByPackage != null){
			return;
		}
		resetCaches();
		
		Set<Class<?>> classes = getAllClasses(mainPackage);
		for(Class<?> clss:classes){
			//cache tagged classes
			if(clss.isAnnotationPresent(Tag.class)){
				String[] tagNames = clss.getAnnotation(Tag.class).value();
				for(String tagName:tagNames){
					cacheByTag.putInSet(tagName, clss);
				}
			}
			
			//cache packages
			cacheByPackage.putInSet(clss.getPackage().getName(), clss);
		}
	}
	
	private void resetCaches(){
		cacheByTag = new HashMapWithSet<>();
		cacheByPackage = new HashMapWithSet<>();
		//cacheByType = new HashMapWithSet<>();
	}
	
	//TODO this needs some investigation in large projects to check if it needs to be replaced, as it loads all the classes in the project, it might be really bad for memory usage(PermGen)
	private static Set<Class<?>> getAllClasses(String mainPackage){
		Reflections r = new Reflections(ClasspathHelper.forPackage(mainPackage), new SubTypesScanner(false));
		Set<Class<?>> classes = r.getSubTypesOf(Object.class);
		return classes;
	}
	//---------------------------------
	
	//---------Interface for External Use-------------
	
	public Set<Class<?>> getByTag(String tagName){
		Set<Class<?>> classes = cacheByTag.get(tagName);
		if(classes==null){
			return new HashSet<>();
		} else {
			return classes;
		}
	}
	
	public Set<Class<?>> getByPackage(String packageName){
		Set<Class<?>> classes = cacheByPackage.get(packageName);
		if(classes==null){
			return new HashSet<>();
		} else {
			return classes;
		}
	}
	
	//----------------------------------------------
}
