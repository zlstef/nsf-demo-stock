package com.netease.cloud.nsf.demo.service;

import com.netease.cloud.nsf.demo.entity.Stock;

import java.util.List;

public interface IProviderService {

	public List<Stock> getAllStocks();
	
	public Stock getStockById(String stockId);

	public List<Stock> getStocksByIds(String stockIds);
}
