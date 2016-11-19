package com.jpmorgan.simplestockmarket;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


/**
 * This is a singleton that holds our queues 
 * and the main methods of the "app"
 * It was made a singleton cause in real world implementation i think it would be more suitable to be one
 */
public class StockMarket {
	
	//This is the queue that will hold all trades
	//It was made ConcurrentLinkedQueue so that if it was used for code with threads it's thread safe
	private ConcurrentLinkedQueue<Trade> tradeQueue = new ConcurrentLinkedQueue<Trade>();
	
	//This is the queue that will hold about all trades made in the last 15 minutes. 
	//It refreshes and updates its contents regularly so performance is better
	//It was made ConcurrentLinkedQueue so that if it was used for code with threads it's thread safe
	//and it a queue cause we assume the trades come sorted by date. If that wasn't the case then 
	//something else would be better like a treemap sorted by date.
	private ConcurrentLinkedQueue<Trade> last15MinsTradeQueue = new ConcurrentLinkedQueue<Trade>();

	private static final int FIFTEEN_MINUTES = 15 * 60 * 1000;
	private static final Logger LOGGER = Logger.getLogger( StockMarket.class.getName() );

	private static FileHandler fh = null;
	
	//This is the hashtable that holds the example stocks.
	//It was made a hashtable instead of hashmap so that if it was used for code with threads it's thread safe
	private static final Hashtable<String,Stock> STOCKS = new Hashtable<String,Stock>() {/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	{
	    put("TEA", new Stock("TEA", StockTypes.Common, new BigDecimal(0), null, new BigDecimal(100)));
	    put("POP", new Stock("POP", StockTypes.Common, new BigDecimal(8), null, new BigDecimal(100)));
	    put("ALE", new Stock("ALE", StockTypes.Common, new BigDecimal(23), null, new BigDecimal(60)));
	    put("GIN", new Stock("GIN", StockTypes.Preferred, new BigDecimal(8), 0.02, new BigDecimal(100)));
	    put("JOE", new Stock("JOE", StockTypes.Common, new BigDecimal(13), null, new BigDecimal(250)));
	 }};
	
    private StockMarket(){
    	initLogger();
    	autogenerateTradeQueue();
    }
    
    private static class SingletonHelper{
        private static final StockMarket INSTANCE = new StockMarket();
    }
    
    public static StockMarket getInstance(){
        return SingletonHelper.INSTANCE;
    }
	 
	public void initLogger(){
		 try {
			 fh=new FileHandler("smLogger.log", false);
		 } catch (IOException e) {
			 e.printStackTrace();
		 }
		 fh.setFormatter(new SimpleFormatter());
		 LOGGER.addHandler(fh);
	}

	
	/**
	 * This is the method that return the answer for iv
	 */
	public BigDecimal getVolumeWeightedStockPrice(){
		Date now = new Date();
		BigDecimal tradePricesMultQuantities = new BigDecimal(0);
		int quantities = 0;
		long difference;
		for(Trade trade : last15MinsTradeQueue){
			difference = now.getTime() - (trade.getTimeStamp()).getTime();
		    if (difference > FIFTEEN_MINUTES){
		    	last15MinsTradeQueue.remove();
		    }
		    else{
		    	tradePricesMultQuantities = tradePricesMultQuantities.add(( new BigDecimal(trade.getPrice() * trade.getQuantityOfShares())));
		    	quantities += trade.getQuantityOfShares();
		    }
		}
		BigDecimal vwsp = tradePricesMultQuantities.divide(new BigDecimal(quantities), 5, RoundingMode.HALF_UP);
		return vwsp;
	}
	
	
	/**
	 * This is the method that return the answer for v
	 * We could use BigDecimal if we imported an external library to compute the fractional root of a BigDecimal or maybe implement it.
	 * For simplicity i used double
	 */
	public double getGBCE(){
		double product = 1.0;
		for(Trade trade : tradeQueue){
			product = product * trade.getPrice();
		}
		double geoMean = Math.pow(product, (double)(1.0 / tradeQueue.size()));
		return geoMean;
	}

	
	/**
	 * This method is used any time possible to refresh the 15minute queue 
	 * so it is closer to holding all trades that were made in the last 15 minutes
	 */
	private void refresh15minsqueue(){
		Date now = new Date();
		long difference;
		for(Trade trade : last15MinsTradeQueue){
			difference = now.getTime() - (trade.getTimeStamp()).getTime();
		    if (difference > FIFTEEN_MINUTES){
		    	last15MinsTradeQueue.remove();
		    }
		    else{
		    	break;
		    }
		}
	}
	
	
	/**
	 * This method logs a trade like it was required in iii and adds it to the appropriate queues
	 */
	public void addTradeToQueues(Trade trade){
		Date now = new Date();
		tradeQueue.add(trade);
		trade.getStock().getTrades().add(trade);
		long difference = now.getTime() - (trade.getTimeStamp()).getTime();
		refresh15minsqueue();
	    if (difference < FIFTEEN_MINUTES){
	    	last15MinsTradeQueue.add(trade);
	    }
	    LOGGER.info(trade.toString());
	}
	
	/**
	 * This method is just a helper for the initial setup
	 */
	private void addTradeToQueuesInitially(Trade trade){
		Date now = new Date();
		tradeQueue.add(trade);
		trade.getStock().getTrades().add(trade);
		long difference = now.getTime() - (trade.getTimeStamp()).getTime();
	    if (difference < FIFTEEN_MINUTES){
	    	last15MinsTradeQueue.add(trade);
	    }
	}
	
	
	/**
	 * This method bootstraps the queues so the app has some random generated
	 * initial data
	 */
	public void autogenerateTradeQueue(){
		Random r =new Random();
		Date now = new Date();
		List<TradeTypes> VALUES = Collections.unmodifiableList(Arrays.asList(TradeTypes.values()));
		int SIZE = VALUES.size();
		long unixtime;
		Date d;
		Object[] keys;
		Stock stock;
		TradeTypes type;
		int quantity;
		double price;
		Trade trade;
		ArrayList<Trade> al = new ArrayList<Trade>();
		
		for (int i=0;i<100;i++){
			//timestamp
			unixtime=(long) (now.getTime()-r.nextDouble()*30 * 60 * 1000);
			d = new Date(unixtime);
			//stock
	        keys = StockMarket.getStocks().keySet().toArray();
	        Object key = keys[r.nextInt(keys.length)];
	        stock = StockMarket.getStocks().get(key);
			//type
	        type = VALUES.get(r.nextInt(SIZE));
			//quantity
	        quantity = r.nextInt(1000);
			//price
	        price = r.nextDouble() * 1000;
	        trade = new Trade(d, stock, type, quantity, price);
	        al.add(trade);
	        LOGGER.info(trade.toString());
		}
		Collections.sort(al, new CustomComparator());
		for (Trade trade2: al){
			addTradeToQueuesInitially(trade2);
		}
	}
	
	
	//Just getters and setters below this line
	public static Hashtable<String,Stock> getStocks() {
		return STOCKS;
	}
	
	public ConcurrentLinkedQueue<Trade> getTradeQueue() {
		return tradeQueue;
	}

	public void setTradeQueue(ConcurrentLinkedQueue<Trade> tradeQueue) {
		this.tradeQueue = tradeQueue;
	}
	
	public static Logger getLogger() {
		return LOGGER;
	}
	
	public ConcurrentLinkedQueue<Trade> getLast15MinsTradeQueue() {
		return last15MinsTradeQueue;
	}

	public void setLast15MinsTradeQueue(
			ConcurrentLinkedQueue<Trade> last15MinsTradeQueue) {
		this.last15MinsTradeQueue = last15MinsTradeQueue;
	}

}
