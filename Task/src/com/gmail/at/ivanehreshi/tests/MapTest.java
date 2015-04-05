package com.gmail.at.ivanehreshi.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.gmail.at.ivanehreshi.ArrayList;
import com.gmail.at.ivanehreshi.LinkedList;
import com.gmail.at.ivanehreshi.Map;

public class MapTest {

	Map<Integer, Integer> list;
	
	@Test
	public void test() {
		list = new Map<Integer, Integer>(200l);
		list.put(1, 1);
		assertTrue(list.get(1) == 1);
		assertTrue(list.containsKey(1));
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertFalse(list.containsKey(1));
	}

}
