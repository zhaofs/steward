package com.yihui.picker.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yihui.picker.capture.Price;
import com.yihui.picker.capture.PricePicker;
import com.yihui.picker.capture.PricePicker.PriceType;
import com.yihui.picker.config.PickerProperties;
import com.yihui.picker.model.HistoryPrice;
import com.yihui.picker.repository.HistoryPriceRepository;

@Component
public class HistoryPriceServiceImpl implements HistoryPriceService {
	protected static final Logger logger = LoggerFactory.getLogger(HistoryPriceServiceImpl.class);
	
	@Autowired
	HistoryPriceRepository repository;
	@Autowired
	private PickerProperties config;
	/**
	 * 定义各种类型的当前价格
	 */
	private Map<PriceType, BigDecimal> currentPrice;

	public HistoryPriceServiceImpl() {
		this.currentPrice = new HashMap<PriceType, BigDecimal>();
		for (PriceType priceType : PriceType.values()) {
			currentPrice.put(priceType, new BigDecimal(0));
		}
	}

	@Override
	public void save(List<PricePicker> list) {
		Set<Entry<PriceType, BigDecimal>> currentPriceSet = currentPrice.entrySet();
		while (true) {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			try {
				for (Entry<PriceType, BigDecimal> entry : currentPriceSet) {
					BigDecimal newPrice = getAvgPrice(entry.getKey(), list);

					/* 如果相差值大于阈值便保存到数据库 */
					if (this.isSave(newPrice, entry.getKey())) {
						
						HistoryPrice historyPrice = new HistoryPrice();
						historyPrice.setName(entry.getKey().toString());
						historyPrice.setPrice(newPrice.multiply(new BigDecimal(100)).longValue());
						historyPrice.setLastTime(new Date());
						historyPrice.setStartTime(new Date());
						historyPrice.setSource("AVG");
						repository.save(historyPrice);
						
						entry.setValue(newPrice);
						logger.info("save price "+historyPrice);
					}
				}
			} catch (Exception e) {
				logger.error(" 保存数据错误，稍后将重试...",e);
			}


		}
	}

	private boolean isSave(BigDecimal newPrice, PriceType priceType) {
		if ((newPrice.subtract(this.currentPrice.get(priceType)).abs()).compareTo(new BigDecimal(config.getPriceThreshold())) >= 0) {
			return true;
		}
		return false;
	}

	/**
	 * 获取平均值
	 * @param priceType
	 * @return
	 */
	private static BigDecimal getAvgPrice(PricePicker.PriceType priceType, List<PricePicker> list) {
		int count = 0;
		BigDecimal price = new BigDecimal(0);
		Price entity = null;

		for (PricePicker pricePicker : list) {
			entity = pricePicker.getPrice(priceType);
			if (entity != null && (!entity.getPrice().equals(new BigDecimal(0)))) {
				count++;
				price = price.add(entity.getPrice());
			}
		}

		if (count != 0) {
			price = price.divide(new BigDecimal(count), 2, BigDecimal.ROUND_HALF_EVEN);
		}

		return price;
	}
	
}
