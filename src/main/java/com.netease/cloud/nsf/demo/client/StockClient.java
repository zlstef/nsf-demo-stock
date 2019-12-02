package com.netease.cloud.nsf.demo.client;

import com.netease.cloud.nsf.demo.entity.Stock;

import java.util.List;

public interface StockClient {

	public Stock getStockById(String stockId) throws Exception;
	
	public List<Stock> getStockBatchByIds(String stockIds) throws Exception;
	
}
