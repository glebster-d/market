package com.marketclient.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.marketclient.logic.Buyer;

class BuyerTest {

	static Buyer buyer;
	
	@BeforeEach
	void setUp() throws Exception {
		
		buyer = new Buyer();
	}
	
	@Test
	void testSetIdWithString() {

		buyer.setId("IDTEST");
		assertEquals("IDTEST", buyer.getId());
	}
	
	@Test
	void testSetIdWithNull() {

		buyer.setId(null);
		assertEquals("", buyer.getId());
	}


	@Test
	void testSetFirstNameWithString() {

		buyer.setFirstName("FNAMETEST");
		assertEquals("FNAMETEST", buyer.getFirstName());
	}

	@Test
	void testSetFirstNameWithNull() {

		buyer.setFirstName(null);
		assertEquals("", buyer.getFirstName());
	}
	

	@Test
	void testSetLastNameWithString() {

		buyer.setLastName("LNAMETEST");
		assertEquals("LNAMETEST", buyer.getLastName());
	}

	@Test
	void testSetLastNameWithNull() {

		buyer.setLastName(null);
		assertEquals("", buyer.getLastName());
	}

	@Test
	void testSetTelephoneWithString() {

		buyer.setTelephone("TELTEST");
		assertEquals("TELTEST", buyer.getTelephone());
	}
	
	@Test
	void testSetTelephoneWithNull() {

		buyer.setTelephone(null);
		assertEquals("", buyer.getTelephone());
	}

	@Test
	void testSetDiscountWithString() {
		
		buyer.setDiscount("DISTEST");
		assertEquals("DISTEST", buyer.getDiscount());
	}
	
	@Test
	void testSetDiscountWithNull() {
		
		buyer.setDiscount(null);
		assertEquals("", buyer.getDiscount());
	}

	@Test
	void testCalculatePrice() {
		
		buyer.setDiscount("0.5");		
		assertEquals("60.00", buyer.calculatePrice("120"));
	}
}
