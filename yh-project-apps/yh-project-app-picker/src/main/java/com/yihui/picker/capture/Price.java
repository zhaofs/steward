package com.yihui.picker.capture;

import java.math.BigDecimal;
import java.util.Date;

public class Price {
	/**
	 * 价格名称
	 */
	private String name;

	/**
	 * 价格
	 */
	private BigDecimal price;

	/**
	 * 更新日期
	 */
	private Date updatetime;

	/**
	 * 来源
	 */
	private String origin;

	/**
	 * 来源网址
	 */
	private String originUrl;

	public Price() {
		this.price = new BigDecimal(0);
	}

	public Price(String name, String origin, String originUrl) {
		this.name = name;
		this.price = new BigDecimal(0);
		this.origin = origin;
		this.originUrl = originUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getOriginUrl() {
		return originUrl;
	}

	public void setOriginUrl(String originUrl) {
		this.originUrl = originUrl;
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Name:[");
		buffer.append(this.name);
		buffer.append("] ");

		buffer.append("Price:[");
		buffer.append(this.price);
		buffer.append("] ");

		buffer.append("Origin:[");
		buffer.append(this.origin);
		buffer.append("] ");

		buffer.append("OriginURL:[");
		buffer.append(this.originUrl);
		buffer.append("] ");

		return buffer.toString();

	}
	
	public static void main(String[] args) {
		System.out.println((new BigDecimal("112.58").subtract(new BigDecimal("112.98")).abs()).compareTo(new BigDecimal("0.05")));
	}
}
