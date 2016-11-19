package com.jpmorgan.simplestockmarketTest;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.junit.runners.MethodSorters;

import static org.junit.Assert.assertNotNull;





import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;

import com.jpmorgan.simplestockmarket.Stock;
import com.jpmorgan.simplestockmarket.StockMarket;
import com.jpmorgan.simplestockmarket.Trade;
import com.jpmorgan.simplestockmarket.TradeTypes;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StockMarketTest {
   static StockMarket sm;
	
   @BeforeClass
   public static void beforeClass() {
	    sm = StockMarket.getInstance();
		ConcurrentLinkedQueue<Trade> tradeQueue = new ConcurrentLinkedQueue<Trade>();
		Date d = new Date();
		Stock stock = StockMarket.getStocks().get("TEA");
		TradeTypes type = TradeTypes.BUY;
		int quantity = 10000;
		double tickerPrice = 100.0;
		tradeQueue.add(new Trade(d, stock, type, quantity, tickerPrice));
		stock = StockMarket.getStocks().get("POP");
		type = TradeTypes.SELL;
		quantity = 1000;
		tickerPrice = 120.0;
		tradeQueue.add(new Trade(d, stock, type, quantity, tickerPrice));
		sm.setTradeQueue(tradeQueue);
		sm.setLast15MinsTradeQueue(tradeQueue);
   }
	
	
	@Test
	public void test1getGBCE(){
		assertTrue(109.54451150103323 == sm.getGBCE());
	}
	
	@Test
	public void test2getVolumeWeightedStockPrice(){
		assertTrue( new BigDecimal(101.81818).setScale(5, RoundingMode.HALF_UP).compareTo(sm.getVolumeWeightedStockPrice().setScale(5, RoundingMode.HALF_UP)) ==0);
	}
	
}
