package com.netease.cloud.nsf.demo.service;

import com.netease.cloud.nsf.demo.entity.Stock;

import java.util.List;


public interface IStockService {

	public List<Stock> getStockList(int delay) throws Exception;
	
	public Stock getStockById(String stockId) throws Exception;
	
	public List<Stock> getHotStockAdvice() throws Exception;
	
	public String echoAdvisor();

	public String echoProvider();

	public String deepInvoke(int times);

	public String getMaxSpreadStock();

	public String getPredictPriceById(String stockId);

	public String getConfigString();
}
