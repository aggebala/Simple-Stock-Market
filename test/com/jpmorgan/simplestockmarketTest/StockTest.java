package com.jpmorgan.simplestockmarketTest;
import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.Test;

import com.jpmorgan.simplestockmarket.Stock;
import com.jpmorgan.simplestockmarket.StockTypes;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class StockTest {
	Stock stock = new Stock("POP", StockTypes.Common, new BigDecimal(8), null, new BigDecimal(100));
	
	@Test
	public void testGetDividendYield(){
		BigDecimal dividend = stock.getDividendYield(new BigDecimal(100));
		assertTrue(dividend.compareTo(new BigDecimal(0.08).setScale(5, RoundingMode.HALF_UP)) == 0); 
	}
	
	@Test (expected = ArithmeticException.class)
	public void testGetDividendYieldException(){
		BigDecimal dividend = stock.getDividendYield(new BigDecimal(0));
		assertTrue(dividend.compareTo(new BigDecimal(0.08).setScale(5, RoundingMode.HALF_UP)) == 0); 
	}
	
	@Test
	public void testGetPERatio(){
		BigDecimal PERatio = stock.getPERatio(new BigDecimal(100));
		assertTrue( new BigDecimal(1250).setScale(5, RoundingMode.HALF_UP).compareTo(PERatio.setScale(5, RoundingMode.HALF_UP)) ==0);
	}
	
	@Test
	public void testGetPERatioException(){
		BigDecimal PERatio = stock.getPERatio(new BigDecimal(0));
		assertNull( PERatio);
	}
}
