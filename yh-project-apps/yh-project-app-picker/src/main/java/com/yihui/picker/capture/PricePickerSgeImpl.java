package com.yihui.picker.capture;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import com.yihui.picker.PickerApplication;
import com.yihui.picker.config.PickerProperties;
import com.yihui.picker.utils.HttpRequestUtils;
import com.yihui.picker.utils.JsonMapper;

@Component
public class PricePickerSgeImpl extends AbstractionPricePicker {
	protected static final Logger logger = LoggerFactory.getLogger(PricePickerSgeImpl.class);

	@Autowired
	private PickerProperties config;
	private boolean isRun=true;

	@Override
	public void initPrice(Map<String, Price> priceMap) {
		String origin = "上海黄金交易所";
		String originUrl = config.getSgeUrl();

		Price price = new Price(PricePicker.PriceType.AUTD.toString(), origin, originUrl);
		priceMap.put(price.getName(), price);

		price = new Price(PricePicker.PriceType.AU95.toString(), origin, originUrl);
	    priceMap.put(price.getName(), price);

		price = new Price(PricePicker.PriceType.AU99.toString(), origin, originUrl);
		priceMap.put(price.getName(), price);

		price = new Price(PricePicker.PriceType.AUTN1.toString(), origin, originUrl);
		priceMap.put(price.getName(), price);

		price = new Price(PricePicker.PriceType.AUTN2.toString(), origin, originUrl);
		priceMap.put(price.getName(), price);
		
	}

	
	@Override
	@SuppressWarnings("unchecked")
	public void pick(Map<String, Price> priceMap) {
		while (isRun) {
			logger.info("start price ...\n\n\n\n\n\n\n");
			String url = config.getSgeUrl();
			String content = HttpRequestUtils.get(url);
			if (content != null && !content.isEmpty()) {
				
				logger.info(content+"\n\n");
				
				List<Map<String, String>> list = JsonMapper.nonDefaultMapper().fromJson(content, List.class);
				String price = null;
				String priceName = null;

				for (Map<String, String> map : list) {

					if (map.get("VRT_CODE").trim().equals("Au(T+D)")) {

						price = StringUtils.substringBetween(map.get("LAST_PRICE"), ">", "<");
						priceName = PricePicker.PriceType.AUTD.toString();

						priceMap.get(priceName).setPrice(new BigDecimal(price));
						priceMap.get(priceName).setUpdatetime(new Date());
						
						logger.info(priceMap.get(priceName).toString());

					}
					if (map.get("VRT_CODE").trim().equals("Au99.95")) {
						price = StringUtils.substringBetween(map.get("LAST_PRICE"), ">", "<");
						priceName = PricePicker.PriceType.AU95.toString();

						priceMap.get(priceName).setPrice(new BigDecimal(price));
						priceMap.get(priceName).setUpdatetime(new Date());
						
						logger.info(priceMap.get(priceName).toString());
					}
					if (map.get("VRT_CODE").trim().equals("Au99.99")) {
						price = StringUtils.substringBetween(map.get("LAST_PRICE"), ">", "<");
						priceName = PricePicker.PriceType.AU99.toString();

						priceMap.get(priceName).setPrice(new BigDecimal(price));
						priceMap.get(priceName).setUpdatetime(new Date());
						
						logger.info(priceMap.get(priceName).toString());
					}
					if (map.get("VRT_CODE").trim().equals("Au(T+N1)")) {
						price = StringUtils.substringBetween(map.get("LAST_PRICE"), ">", "<");
						priceName = PricePicker.PriceType.AUTN1.toString();

						priceMap.get(priceName).setPrice(new BigDecimal(price));
						priceMap.get(priceName).setUpdatetime(new Date());
						
						logger.info(priceMap.get(priceName).toString());
					}
					if (map.get("VRT_CODE").trim().equals("Au(T+N2)")) {
						price = StringUtils.substringBetween(map.get("LAST_PRICE"), ">", "<");
						priceName = PricePicker.PriceType.AUTN2.toString();

						priceMap.get(priceName).setPrice(new BigDecimal(price));
						priceMap.get(priceName).setUpdatetime(new Date());
						
						logger.info(priceMap.get(priceName).toString());
					}

				}

			}

			try {
				Thread.sleep(Integer.parseInt(config.getSgeInterval()));
			} catch (InterruptedException e) {
				logger.debug("thread sleep error ", e);
			}
		}
		
	}

	public void stop() {
		this.isRun=false;
	}

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext= SpringApplication.run(PickerApplication.class, args);
		
		PricePicker htmlParser = applicationContext.getBean(PricePickerSgeImpl.class);
		htmlParser.start();
	}


}
