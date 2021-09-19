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
		Set<Object> selectedObjects = SelectionUtils.selectByTag("tag");
		
		assertTrue(selectedObjects.contains(ClassWithTag.class));
		assertFalse(selectedObjects.contains(ClassWithoutTag.class));
		assertFalse(selectedObjects.contains(ClassWithDifferentTag.class));
	}
	
	@Test
	public void testSelectionByPackage(){
		//look at testcode.testselection.bypackage to see the code being scan
		setSelectionSubPackage("bypackage");
		String packagePath = mainPkg + ".bypackage.specialpackage";
		Set<Object> selectedObjects = SelectionUtils.selectByPackage(packagePath);
		
		assertTrue(selectedObjects.contains(ClassInPackage.class));
		assertFalse(selectedObjects.contains(ClassOutsidePackage.class));
	}
	
	public void setSelectionSubPackage(String subPkg){
		SelectionUtils.setMainPackage(mainPkg + "." + subPkg);
	}
	public final static String mainPkg = "com.sambccd.ProjectStructure.testcode.testselection";
	
}
