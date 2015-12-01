package com.yihui.picker.capture;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import com.yihui.picker.PickerApplication;
import com.yihui.picker.config.PickerProperties;
import com.yihui.picker.utils.HttpRequestUtils;

@Component
public class PricePickerSinaImpl extends AbstractionPricePicker {
	protected static final Logger logger = LoggerFactory.getLogger(PricePickerSinaImpl.class);
	protected static final String OUNCE_GRAM = "31.1034768";
	
	@Autowired
	private PickerProperties config;
	private boolean isRun = true;

	private ScriptEngine scriptEngine;

	public PricePickerSinaImpl() {
		this.scriptEngine = new ScriptEngineManager().getEngineByName("JavaScript");
	}

	@Override
	public void initPrice(Map<String, Price> priceMap) {
		String origin = "新浪网财经频道";
		String originUrl = config.getSgeUrl();

		Price price = new Price(PricePicker.PriceType.LONAU.toString(), origin, originUrl);
		priceMap.put(price.getName(), price);

		price = new Price(PricePicker.PriceType.NYCAU.toString(), origin, originUrl);
		priceMap.put(price.getName(), price);

		price = new Price(PricePicker.PriceType.AUTD.toString(), origin, originUrl);
		priceMap.put(price.getName(), price);
	}

	@Override
	public void pick(Map<String, Price> priceMap) {

		String url = config.getSinaUrl();
		String content = null;
		String price = null;
		BigDecimal priceBigDecimal = null;
		String rate = null;
		while (isRun) {
			logger.info("start price ...\n\n\n\n\n\n\n");
			content = HttpRequestUtils.get(url);
			if (content != null && !content.isEmpty()) {

				try {
					logger.info(content+"\n\n");
					
					/*执行javascript脚本*/
					scriptEngine.eval(content);

					/*获取汇率*/
					rate = scriptEngine.get("hq_str_USDCNY").toString();
					rate = rate.split(",")[1];
					logger.info("美元汇率:" + rate);

					/*获取纽约黄金价格*/
					price = scriptEngine.get("hq_str_hf_GC").toString();
					price = price.split(",")[0];

					priceBigDecimal = new BigDecimal(price).multiply(new BigDecimal(rate))
							.divide(new BigDecimal(OUNCE_GRAM), 2, BigDecimal.ROUND_HALF_EVEN);
					priceMap.get(PricePicker.PriceType.NYCAU.toString()).setPrice(priceBigDecimal);
					priceMap.get(PricePicker.PriceType.NYCAU.toString()).setUpdatetime(new Date());
					
					logger.info(priceMap.get(PricePicker.PriceType.NYCAU.toString()).toString());

					/*获取伦敦黄金价格*/
					price = scriptEngine.get("hq_str_hf_XAU").toString();
					price = price.split(",")[0];
					
					priceBigDecimal = new BigDecimal(price).multiply(new BigDecimal(rate))
							.divide(new BigDecimal(OUNCE_GRAM), 2, BigDecimal.ROUND_HALF_EVEN);
					priceMap.get(PricePicker.PriceType.LONAU.toString()).setPrice(priceBigDecimal);
					priceMap.get(PricePicker.PriceType.LONAU.toString()).setUpdatetime(new Date());
					
					logger.info(priceMap.get(PricePicker.PriceType.LONAU.toString()).toString());

					/*获取现货价格*/
					price = scriptEngine.get("hq_str_hf_AUTD").toString();
					price = price.split(",")[0];

					priceMap.get(PricePicker.PriceType.AUTD.toString()).setPrice(new BigDecimal(price));
					priceMap.get(PricePicker.PriceType.AUTD.toString()).setUpdatetime(new Date());
					
					logger.info(priceMap.get(PricePicker.PriceType.AUTD.toString()).toString());


				} catch (ScriptException e) {
					logger.error("script error ", e);
					e.printStackTrace();
				}
			}

			try {
				Thread.sleep(Integer.parseInt(config.getSinaInterval()));
			} catch (InterruptedException e) {
				logger.debug("thread sleep error ", e);
			}
		}
	}
	
	public void stop() {
		this.isRun = false;
	}

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext= SpringApplication.run(PickerApplication.class, args);
		
		PricePicker htmlParser = applicationContext.getBean(PricePickerSinaImpl.class);
		htmlParser.start();

	}

}
