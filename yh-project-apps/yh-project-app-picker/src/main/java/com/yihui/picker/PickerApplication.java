package com.yihui.picker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.yihui.picker.capture.PricePicker;
import com.yihui.picker.service.HistoryPriceService;

@SpringBootApplication
public class PickerApplication {
	private static List<PricePicker> list = new ArrayList<PricePicker>();

	public static void main(String[] args) {

		ConfigurableApplicationContext applicationContext = SpringApplication.run(PickerApplication.class, args);

		/* 启动分析功能,并初始化相关价格 */
		Map<String, PricePicker> beans = applicationContext.getBeansOfType(PricePicker.class);
		for (PricePicker pricePicker : beans.values()) {
			list.add(pricePicker);
			pricePicker.start();
		}
		
		/*保存价格*/
		applicationContext.getBean(HistoryPriceService.class).save(list);
	}
}
