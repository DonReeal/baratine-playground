package eu.dons.pollbus.base;

import static org.junit.Assert.*;

import org.junit.Test;

import eu.dons.pollbus.base.data.immutable.EntityBase;

public class DataBaseImplementationTest {

	@Test
	public void entityIsEmptyUsesAllFields() {
		
		AnEntity entity = new AnEntity("", "", 1);
		assertTrue(!entity.isEmpty());		
		AnEntity exactlyEmpty = new AnEntity("", "", 0);
		assertTrue(exactlyEmpty.isEmpty());

	}
	
	@Test
	public void valueObjectIsEmptyUsesAllFields() {
		AValueObject a = new AValueObject("", 0);
		assertTrue(a.isEmpty());
		
	}

}
