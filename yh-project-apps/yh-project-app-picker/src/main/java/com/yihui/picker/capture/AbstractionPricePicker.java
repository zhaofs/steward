package com.yihui.picker.capture;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractionPricePicker implements PricePicker,Runnable {

	private Thread thread;
	private Map<String, Price> priceMap;
	
	public AbstractionPricePicker(){
		this.priceMap=new HashMap<String, Price>();
		this.thread=new Thread(this);
	}
	
	public Price getPrice(PriceType priceType) {
		return this.priceMap.get(priceType.toString());
	}

	public void start() {
		this.initPrice(this.priceMap);
		this.thread.start();
	}

	public void stop() {
		// TODO Auto-generated method stub
	}
	
	public void run() {
		this.pick(this.priceMap);
	}
	
	/**
	 * 初始化价格
	 */
	public abstract void initPrice(Map<String, Price> priceMap);

	/**
	 * 进行抓取
	 */
	public abstract void pick(Map<String, Price> priceMap);
	
	
	public Map<String, Price> getPriceMap() {
		return priceMap;
	}

	public void setPriceMap(Map<String, Price> priceMap) {
		this.priceMap = priceMap;
	}

}
