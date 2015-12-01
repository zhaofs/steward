package com.yihui.picker.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class HttpRequestUtils {
	protected static final Logger logger = LoggerFactory.getLogger(HttpRequestUtils.class);

	/**
	 * 用get提交相应请求，并返回相应数据
	 * @param url
	 * @return
	 */
	public static String get(String url) {
		HttpGet httpGet = new HttpGet(url);
		httpGet.addHeader("User-Agent",
				"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; .NET CLR 2.0.50727; CIBA)");
		httpGet.addHeader("Accept-Language", "zh-cn");
		httpGet.addHeader("Connection", "Keep-Alive");
		CloseableHttpResponse response = null;
		try {
			response = HttpClients.createDefault().execute(httpGet);
			int statusCode = response.getStatusLine().getStatusCode();

			/*正常状态处理*/
			if (statusCode == 200) {
				char[] buffer = new char[1024 * 10];
				InputStream stream = response.getEntity().getContent();
				BufferedReader in = new BufferedReader(new InputStreamReader(stream, "GBK"));

				int len = in.read(buffer);
				String str = "";
				while (len > 0) {
					str = str + new String(buffer, 0, len);
					len = in.read(buffer);
				}
				
				return str;
			} else {
				logger.info("无法请求数据  其请求状态编码为  [" + statusCode + "] !!!");
			}

		} catch (IOException e) {
			logger.error("The connection [" + url + "] fails", e);
		} finally {
			try {
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				logger.error("close response  error ", e);
			}
		}
		return null;
	}
}
