package com.yihui.picker.service;

import java.util.List;

import com.yihui.picker.capture.PricePicker;

public interface HistoryPriceService {
	public void save(List<PricePicker> list);
}
