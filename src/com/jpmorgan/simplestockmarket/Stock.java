package com.jpmorgan.simplestockmarket;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * This is a class that represents stocks (unsurprisingly)
 */
public class Stock {
	
	//Just initalizing
	private String stockSymbol = "";
	private StockTypes type = StockTypes.Common; 
	private BigDecimal lastDividend = new BigDecimal(0);
	private Double fixedDividend = 0.0;
	private BigDecimal parValue = new BigDecimal(0);
	//Here we make a synchronized list of the trades for this stock
	//We use a synchronized list because in a real application we would want this list to be thread safe
	//Also, it is a list because we assume the trades come sorted by date. If that wasn't the case then 
	//something else would be better like a treemap sorted by date.
	private List<Trade> trades = Collections.synchronizedList(new ArrayList<Trade>());

	public Stock(String stockSymbol, StockTypes type, BigDecimal lastDividend, Double fixedDividend, BigDecimal parValue){
		this.stockSymbol = stockSymbol;
		this.type = type;
		this.lastDividend = lastDividend;
		this.fixedDividend = fixedDividend;
		this.parValue = parValue;
	}
	
	/**
	 * This method is used to get the result required in i
	 */
	public BigDecimal getDividendYield(BigDecimal tickerPrice){
		BigDecimal dividend = null;
		switch (type){
			case Common:
				dividend = lastDividend.divide(tickerPrice, new MathContext(5,RoundingMode.HALF_UP));
				break;
			case Preferred:
				dividend = (BigDecimal)(parValue.multiply(new BigDecimal(fixedDividend))).divide(tickerPrice, new MathContext(5,RoundingMode.HALF_UP));
				break;
			default:
		         throw new RuntimeException("Invalid value for enum types");
		}
		return dividend;
	}
	
	
	/**
	 * This method is used to get the result required in ii
	 */
	public BigDecimal getPERatio(BigDecimal tickerPrice){
		
		BigDecimal peRatio = null;
		
		if(tickerPrice.compareTo(BigDecimal.ZERO) != 0){
			peRatio = tickerPrice.divide(getDividendYield(tickerPrice), new MathContext(5,RoundingMode.HALF_UP));	
		}
		
		return peRatio;
	}
	
	
	/**
	 * Overriding toString so logging is easier
	 */
	@Override
	public String toString() {
		String format = "%s";
		return String.format(format, stockSymbol);
	}
	
	
	//Just getters and setters below this line
	public StockTypes getType() {
		return type;
	}

	public void setType(StockTypes type) {
		this.type = type;
	}
	
	public String getStockSymbol() {
		return stockSymbol;
	}
	public void setStockSymbol(String stockSymbol) {
		this.stockSymbol = stockSymbol;
	}

	public BigDecimal getLastDividend() {
		return lastDividend;
	}
	public void setLastDividend(BigDecimal lastDividend) {
		this.lastDividend = lastDividend;
	}
	public Double getFixedDividend() {
		return fixedDividend;
	}
	public void setFixedDividend(Double fixedDividend) {
		this.fixedDividend = fixedDividend;
	}
	public BigDecimal getParValue() {
		return parValue;
	}
	public void setParValue(BigDecimal parValue) {
		this.parValue = parValue;
	}
	
	public List<Trade> getTrades() {
		return trades;
	}

	public void setTrades(List<Trade> trades) {
		this.trades = trades;
	}
}
