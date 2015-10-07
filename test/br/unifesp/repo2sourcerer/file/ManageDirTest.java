package br.unifesp.repo2sourcerer.file;

import static org.junit.Assert.*;

import org.junit.Test;

public class ManageDirTest {

	@Test
	public void testRegExpr() {
		
		String pattern = "\\d+";
		
		assertTrue("0".matches(pattern));
		assertTrue("56".matches(pattern));
		assertTrue("056".matches(pattern));
		assertFalse("_56".matches(pattern));
		assertFalse("5_6".matches(pattern));
		assertFalse("56_".matches(pattern));
		
	}
}
