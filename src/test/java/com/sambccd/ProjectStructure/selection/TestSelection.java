package com.sambccd.ProjectStructure.selection;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sambccd.ProjectStructure.testcode.testselection.ClassWithDifferentTag;
import com.sambccd.ProjectStructure.testcode.testselection.ClassWithTag;
import com.sambccd.ProjectStructure.testcode.testselection.ClassWithoutTag;


public class TestSelection {
	
	@Before
	public void before(){
		SelectionUtils.setInstance(new MockedSelectionutils());
	}
	
	@After
	public void after(){
		SelectionUtils.resetInstance();
	}
	
	@Test
	public void testSelectionByTag(){
		//look at testcode.testselection to see the code being scan
		Set<Object> selectedObjects = SelectionUtils.selectByTag("tag");
		
		assertTrue(selectedObjects.contains(ClassWithTag.class));
		assertFalse(selectedObjects.contains(ClassWithoutTag.class));
		assertFalse(selectedObjects.contains(ClassWithDifferentTag.class));
	}
	
	public class MockedSelectionutils extends SelectionUtils{
		@Override
		public String getMainPackage() {
			return "com.sambccd.ProjectStructure.testcode.testselection";
		}
	}
}
