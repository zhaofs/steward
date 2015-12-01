package com.yihui.common.dubbo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = DubboProperties.PREFIX)
public class DubboProperties{
	public static final String PREFIX="dubbo";
	
	private String shutdownHook;

	public String getShutdownHook() {
		return shutdownHook;
	}

	public void setShutdownHook(String shutdownHook) {
		this.shutdownHook = shutdownHook;
	}

}
