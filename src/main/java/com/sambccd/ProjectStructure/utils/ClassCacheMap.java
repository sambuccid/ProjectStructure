package com.sambccd.ProjectStructure.utils;

import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;

import com.sambccd.ProjectStructure.Tag;
import com.sambccd.ProjectStructure.selection.ObjectType;

public class ClassCacheMap {
	private HashMapWithSet<String, Class<?>> cacheByTag;
	private ThreeMapWithSet<String, Class<?>> cacheByPackageSorted;
	//private HashMapWithSet<ObjectType, Class<?>> cacheByType;
	private String mainPackage;
	public ClassCacheMap(String pkg){
		mainPackage=pkg;
	}
	
	//-------------Internal Working--------
	public void populate(){
		if(cacheByTag != null && cacheByPackageSorted != null){
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
			cacheByPackageSorted.putInSet(clss.getPackage().getName(), clss);
		}
	}
	
	private void resetCaches(){
		cacheByTag = new HashMapWithSet<>();
		cacheByPackageSorted = new ThreeMapWithSet<>();
		//cacheByType = new HashMapWithSet<>();
	}
	
	//TODO this needs some investigation in large projects to check if it needs to be replaced, as it loads all the classes in the project, it might be really bad for memory usage(PermGen)
	private static Set<Class<?>> getAllClasses(String mainPackage){
		Reflections r = new Reflections(ClasspathHelper.forPackage(mainPackage), new SubTypesScanner(false));
		Set<Class<?>> classes = r.getSubTypesOf(Object.class);
		return classes;
	}
	
	//should be visible just for testing
	SortedMap<String, Set<Class<?>>> getSubMapContainingSubPackages(String packageName){
		//get the packages from the package with the . to the package with the /, however as the / is not valid of a package there will no be any packages with that name, and it is the next character after ".", so no other entries can be between "packageName." and "packageName/" except for the sub packages, that is what we are searching for
		return cacheByPackageSorted.subMap(packageName+".", packageName+"/");
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
		//TODO use getSubMapContainingSubPackages and then get actual package, i think it is the quickest way
		SortedMap<String, Set<Class<?>>> mapWithSubpackages = getSubMapContainingSubPackages(packageName);
		Set<Class<?>> classesInPackage = cacheByPackageSorted.get(packageName);
		
		if(mapWithSubpackages.isEmpty()){ //shortcut for selecting just one package, we don't need to do the addAll operator that takes O(n) time
			if(classesInPackage==null){
				return new HashSet<>();
			} else {
				return classesInPackage;
			}
		}
		
		//this can really be a bottleneck in terms of performances, when we deploy things we really need to find a better way, this takes O(n) time
		//	have a look to Iterables(i think they can be chained) and Guava, if the set is not ordered and it just needs to be iterated then should be fine
		Set<Class<?>> classInPkgAndSubPkg = new HashSet<>();
		if(classesInPackage != null){
			classInPkgAndSubPkg.addAll(classesInPackage);
		}
		
		for(Set<Class<?>> classesInSubPkg:mapWithSubpackages.values()){
			classInPkgAndSubPkg.addAll(classesInSubPkg);
		}
		
		return classInPkgAndSubPkg;
	}
	
	
	//----------------------------------------------
	
	//--------for testing----------
	
	void setCacheByPackageSorted(ThreeMapWithSet<String, Class<?>> cacheByPackageSorted) {
		this.cacheByPackageSorted = cacheByPackageSorted;
	}
	
	//-----------------------------
}
