package com.gmail.at.ivanehreshi.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.gmail.at.ivanehreshi.LinkedList;

public class LinkedListTest {

	LinkedList<Integer> list;
	
	@Test
	public void test() {
		list = new LinkedList<>(200l);
		list.verbose = true;
		list.add(1);
		assertTrue(list.getFirst() == 1);
		assertTrue(list.contains(new Integer(1)));
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertFalse(list.contains(new Integer(1)));
	}

}
