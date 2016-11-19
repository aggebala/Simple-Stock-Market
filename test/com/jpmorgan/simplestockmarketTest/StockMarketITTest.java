package com.jpmorgan.simplestockmarketTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.jpmorgan.simplestockmarket.Stock;
import com.jpmorgan.simplestockmarket.StockMarket;
import com.jpmorgan.simplestockmarket.Trade;
import com.jpmorgan.simplestockmarket.TradeTypes;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StockMarketITTest {
	StockMarket sm = StockMarket.getInstance();
	
	@Test
	public void test1AutogenerateTradeQueue(){
		//sm.autogenerateTradeQueue();
		assertEquals(100, sm.getTradeQueue().size());
	}
	
	
	@Test
	public void test2AddTrade(){
		Date d = new Date();
		Stock stock = StockMarket.getStocks().get("TEA");
		TradeTypes type = TradeTypes.BUY;
		int quantity = 10000;
		double price = 100.0;
		sm.addTradeToQueues(new Trade(d, stock, type, quantity, price));
		ConcurrentLinkedQueue<Trade> last15mins = sm.getLast15MinsTradeQueue();
		ConcurrentLinkedQueue<Trade> tradequeue = sm.getTradeQueue();
		assertTrue((last15mins.size() <= tradequeue.size()) && (tradequeue.size() == 101));
	}
}
