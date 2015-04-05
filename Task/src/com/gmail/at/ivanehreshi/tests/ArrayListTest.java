package com.gmail.at.ivanehreshi.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.gmail.at.ivanehreshi.ArrayList;
import com.gmail.at.ivanehreshi.LinkedList;

public class ArrayListTest {

	ArrayList<Integer> list;
	
	@Test
	public void test() {
		list = new ArrayList<>(200);
		list.add(1);
		assertTrue(list.get(0) == 1);
		assertTrue(list.contains(new Integer(1)));
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertFalse(list.contains(new Integer(0)));
	}

}
