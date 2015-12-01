package com.yihui.picker.capture;

public interface PricePicker {

	/**
	 * 价格类型
	 *
	 */
	public enum PriceType {
		/**
		 * 来源于上海交易所AU(T+D)
		 */
		AUTD, /**
				 * 来源于上海交易所AU99.95
				 */
		AU95, /**
				 * 来源于上海交易所AU99.99
				 */
		AU99, /**
				 * 来源于上海交易所Au(T+N1)
				 */
		AUTN1, /**
				 * 来源于上海交易所Au(T+N2)
				 */
		AUTN2, /**
				 * 来源于新浪网财经频道 伦敦黄金
				 */
		LONAU, /**
				 * 来源于新浪网财经频道 纽约期货黄金
				 */
		NYCAU
	}

	/**
	 * 根据价格类型获取相应的价格，没有测返回NULL
	 * @param priceType
	 * @return
	 */
	Price getPrice(PriceType priceType);

	/**
	 * 开始价格分析
	 */
	void start();

	/**
	 *  停止价格分析
	 */
	void stop();
}
