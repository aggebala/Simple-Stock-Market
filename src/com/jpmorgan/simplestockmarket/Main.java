package com.jpmorgan.simplestockmarket;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


/**
 * Our main class of the "app".
 * It initializes the "app",
 * inserts a trade,
 * calculates Volume Weighted Stock Price based on trades in past 15 minutes,
 * calculates the GBCE All Share Index using the geometric mean of prices for all stocks
 */
public class Main {
	public static void main(String[] args){
		final Logger LOGGER = Logger.getLogger( StockMarket.class.getName());
		FileHandler fh;
		try {
			fh=new FileHandler("mainLogger.log", false);
			fh.setFormatter(new SimpleFormatter());
			LOGGER.addHandler(fh);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			System.exit(0);
		}

		StockMarket sm = StockMarket.getInstance();
	
		Date d = new Date();
		Stock stock = StockMarket.getStocks().get("TEA");
		TradeTypes type = TradeTypes.BUY;
		int quantity = 10000;
		double tickerPrice = 100.0;
		
		try{
			sm.addTradeToQueues(new Trade(d, stock, type, quantity, tickerPrice));
			
			//The return values are not used we can see the results in the log file
			BigDecimal bc = sm.getVolumeWeightedStockPrice();
			double gbce = sm.getGBCE();
		}
		catch (Exception e){
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			System.exit(0);
		}
	}
}
