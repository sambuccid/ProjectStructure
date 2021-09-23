package com.sambccd.ProjectStructure.utils;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;

public class UtilForCollenctions {
	
	
	public static int indexOfShorterCollectionInList(List<? extends Collection> list){
		if(list.size()==0) throw new RuntimeException("the list passed should contain at least one collection");
		int minSizeIdx = 0;
		int minSize = list.get(minSizeIdx).size();
		for(int i=0; i<list.size(); i++){
			if(list.get(i).size() < minSize){
				minSizeIdx = i;
				minSize = list.get(i).size();
			}
		}
		return minSizeIdx;
	}
	
	public static <E> Set<E> intersectionOfSets(Set<E> set1, Set<E> set2){
		if(set1.size()<set2.size()){
			return Sets.intersection(set1, set2);
		} else {
			return Sets.intersection(set2, set1);
		}
	}
}
