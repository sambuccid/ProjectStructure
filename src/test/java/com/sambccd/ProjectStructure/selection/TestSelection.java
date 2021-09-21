package com.sambccd.ProjectStructure.selection;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sambccd.ProjectStructure.testcode.testselection.bypackage.ClassOutsidePackage;
import com.sambccd.ProjectStructure.testcode.testselection.bypackage.specialpackage.ClassInPackage;
import com.sambccd.ProjectStructure.testcode.testselection.bypackageandtag.ClassOutsidePackageWithTag;
import com.sambccd.ProjectStructure.testcode.testselection.bypackageandtag.ClassOutsidePackageWithDifferentTag;
import com.sambccd.ProjectStructure.testcode.testselection.bypackageandtag.ClassOutsidePackageWithoutTag;
import com.sambccd.ProjectStructure.testcode.testselection.bypackageandtag.thepackage.ClassInsidePackageWithTag;
import com.sambccd.ProjectStructure.testcode.testselection.bypackageandtag.thepackage.ClassInsidePackageWithDifferentTag;
import com.sambccd.ProjectStructure.testcode.testselection.bypackageandtag.thepackage.ClassInsidePackageWithoutTag;
import com.sambccd.ProjectStructure.testcode.testselection.bytag.ClassWithDifferentTag;
import com.sambccd.ProjectStructure.testcode.testselection.bytag.ClassWithTag;
import com.sambccd.ProjectStructure.testcode.testselection.bytag.ClassWithoutTag;


public class TestSelection {
	
	@After
	public void after(){
		SelectionUtils.resetMainPackage();
	}
	@Test
	public void testSelectionByTag(){
		//look at testcode.testselection.bytag to see the code being scan
		setSelectionSubPackage("bytag");
		//TODO there is still an error, seems that the subtypescanner in the library is not indexing the classes, when i do getAllSubTypesOf(Object.class);
		Set<Class<?>> selectedObjects = SelectionUtils.selectByTag("tag");
		
		assertTrue(selectedObjects.contains(ClassWithTag.class));
		assertFalse(selectedObjects.contains(ClassWithoutTag.class));
		assertFalse(selectedObjects.contains(ClassWithDifferentTag.class));
	}
	
	@Test
	public void testSelectionByPackage(){
		//look at testcode.testselection.bypackage to see the code being scan
		setSelectionSubPackage("bypackage");
		String packagePath = mainPkg + ".bypackage.specialpackage";
		Set<Class<?>> selectedObjects = SelectionUtils.selectByPackage(packagePath);
		
		assertTrue(selectedObjects.contains(ClassInPackage.class));
		assertFalse(selectedObjects.contains(ClassOutsidePackage.class));
	}
	
	@Test
	public void testSelectionByPackageAndTag(){
		//look at testcode.testselection.bypackageandtag to see the code being scan
		setSelectionSubPackage("bypackageandtag");
		String packagePath = mainPkg + ".bypackageandtag.thepackage";
		String tagName = "tag";
		Set<Class<?>> selectedObjects = SelectionUtils.selectByPackageAndTag(packagePath, tagName);
		
		assertTrue(selectedObjects.contains(ClassInsidePackageWithTag.class));
		assertFalse(selectedObjects.contains(ClassInsidePackageWithDifferentTag.class));
		assertFalse(selectedObjects.contains(ClassInsidePackageWithoutTag.class));
		assertFalse(selectedObjects.contains(ClassOutsidePackageWithTag.class));
		assertFalse(selectedObjects.contains(ClassOutsidePackageWithDifferentTag.class));
		assertFalse(selectedObjects.contains(ClassOutsidePackageWithoutTag.class));
	}
	
	public void setSelectionSubPackage(String subPkg){
		SelectionUtils.setMainPackage(mainPkg + "." + subPkg);
	}
	public final static String mainPkg = "com.sambccd.ProjectStructure.testcode.testselection";
	
}
