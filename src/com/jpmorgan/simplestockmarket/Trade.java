package com.jpmorgan.simplestockmarket;

import java.util.Date;


/**
 * This is a class that represents trades
 */
public class Trade {
	
	//Just initalizing
	private Date timeStamp = null;
	private Stock stock = null;
	private TradeTypes tradeType = TradeTypes.BUY;
	private int quantityOfShares = 0;
	private double price = 0.0;

	public Trade(Date timeStamp, Stock stock, TradeTypes tradeType, int quantityOfShares, double price){
		this.timeStamp = timeStamp;
		this.stock = stock;
		this.tradeType = tradeType;
		this.quantityOfShares = quantityOfShares;
		this.price = price;
	}
	
	
	/**
	 * Overriding toString so logging is easier
	 */
	@Override
	public String toString() {
		String format = "Logging of trade -- trade time: %tF %tT, stock: %s, type: %s, quantity of shares: %7d, price: %8.2f";
		return String.format(format, timeStamp,timeStamp, stock, tradeType, quantityOfShares, price);
	}

	//Just getters and setters below this line
	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	public TradeTypes getTradeType() {
		return tradeType;
	}

	public void setTradeType(TradeTypes tradeType) {
		this.tradeType = tradeType;
	}

	public int getQuantityOfShares() {
		return quantityOfShares;
	}

	public void setQuantityOfShares(int quantityOfShares) {
		this.quantityOfShares = quantityOfShares;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
}
