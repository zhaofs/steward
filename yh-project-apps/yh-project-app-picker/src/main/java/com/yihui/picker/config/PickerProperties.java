package com.yihui.picker.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = PickerProperties.PREFIX)
public class PickerProperties{
	public static final String PREFIX="picker";
	
	private String ytcjUrl;
	private String sgeUrl;
	private String sgeInterval;
	private String sinaUrl;
	private String sinaInterval;
	private String priceThreshold;

	private ClassLoader classLoader;
	public String getYtcjUrl() {
		return ytcjUrl;
	}
	public void setYtcjUrl(String ytcjUrl) {
		this.ytcjUrl = ytcjUrl;
	}
	public String getSgeUrl() {
		return sgeUrl;
	}
	public void setSgeUrl(String sgeUrl) {
		this.sgeUrl = sgeUrl;
	}
	public String getSgeInterval() {
		return sgeInterval;
	}
	public void setSgeInterval(String sgeInterval) {
		this.sgeInterval = sgeInterval;
	}
	public String getSinaUrl() {
		return sinaUrl;
	}
	public void setSinaUrl(String sinaUrl) {
		this.sinaUrl = sinaUrl;
	}
	public String getSinaInterval() {
		return sinaInterval;
	}
	public void setSinaInterval(String sinaInterval) {
		this.sinaInterval = sinaInterval;
	}
	
	public ClassLoader getClassLoader() {
		return classLoader;
	}
	public void setClassLoader(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}
	public String getPriceThreshold() {
		return priceThreshold;
	}
	public void setPriceThreshold(String priceThreshold) {
		this.priceThreshold = priceThreshold;
	}

}
