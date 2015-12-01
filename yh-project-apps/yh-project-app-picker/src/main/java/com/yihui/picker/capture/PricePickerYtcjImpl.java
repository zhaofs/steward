package com.yihui.picker.capture;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import com.yihui.picker.PickerApplication;
import com.yihui.picker.config.PickerProperties;

@Component
public class PricePickerYtcjImpl extends AbstractionPricePicker {
	protected static final Logger logger = LoggerFactory.getLogger(PricePickerYtcjImpl.class);

	@Autowired
	private PickerProperties config;
	private CloseableHttpResponse response;

	@Override
	public void initPrice(Map<String, Price> priceMap) {
		String origin = "倚天财经";
		String originUrl = config.getSgeUrl();

		Price price = new Price(PricePicker.PriceType.NYCAU.toString(), origin, originUrl);
		priceMap.put(price.getName(), price);
		
	}

	@Override
	public void pick(Map<String, Price> priceMap) {

		String url = config.getYtcjUrl();
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		httpGet.addHeader("User-Agent",
				"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; .NET CLR 2.0.50727; CIBA)");
		httpGet.addHeader("Accept-Language", "zh-cn");
		httpGet.addHeader("Connection", "Keep-Alive");
		httpGet.addHeader("Referer",
				"http://3.baring.cn/quo/bin/quotation.dll/page/pageinfo.html?page=http://3.baring.cn/quo/bin/quotation.dll/page/ytcj.com.currhome.htm");

		try {
			response = httpclient.execute(httpGet);
			int statusCode = response.getStatusLine().getStatusCode();

			/*正常状态处理*/
			if (statusCode == 200) {
				char[] buffer = new char[1024 * 10];
				InputStream stream = response.getEntity().getContent();
				BufferedReader in = new BufferedReader(new InputStreamReader(stream, "GBK"));

				int len = in.read(buffer);
				String str = null;
				while (len > 0) {
					str = new String(buffer, 0, len);
					String[] temp = str.split(",");

					logger.info(str+"\n\n\n");

					/* 获取最新价格*/
					if (str.indexOf("CRAUSKY") >= 0) {
						str = StringUtils.left(temp[3], temp[3].length() - 2) + "."+ StringUtils.right(temp[3], 2);
						
						priceMap.get(PricePicker.PriceType.NYCAU.toString()).setPrice(new BigDecimal(str));
						priceMap.get(PricePicker.PriceType.NYCAU.toString()).setUpdatetime(new Date());

					} else {
						if (temp.length >= 3) {
							str = temp[1].indexOf("]") >= 0 ? StringUtils.left(temp[1], temp[1].indexOf("]")): temp[1];
							if (!str.isEmpty()) {
								Price price=priceMap.get(PricePicker.PriceType.NYCAU.toString());
								price.setPrice(price.getPrice().add((new BigDecimal(str)).divide(new BigDecimal(100))));
								price.setUpdatetime(new Date());
								
							}
						}
					}
					logger.info(priceMap.get(PricePicker.PriceType.NYCAU.toString()).toString());
					len = in.read(buffer);
				}
			} else {
				logger.info("无法请求数据  其请求状态编码为  [" + statusCode + "] !!!");
			}

		} catch (IOException e) {
			logger.error("The connection [" + url + "] fails", e);
		} finally {
			try {
				if(response!=null){
					response.close();	
				}
			} catch (IOException e) {
				logger.error("close response  error ", e);
			}
		}
		
	}

	public void stop() {
		if(response!=null){
			try {
				response.close();
			} catch (IOException e) {
				logger.error("close response  error ", e);
			}
			response=null;
		}
	}
	
	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext= SpringApplication.run(PickerApplication.class, args);
		
		PricePicker htmlParser = applicationContext.getBean(PricePickerYtcjImpl.class);
		htmlParser.start();
	}


}
