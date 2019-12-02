package com.netease.cloud.nsf.demo.service;


import com.netease.cloud.nsf.demo.entity.Stock;

import java.util.List;

public interface IAdvisorService {
	public List<Stock> getHotStocks() throws Exception;
	
//	public List<String> batchHi();
	
	public String deepInvoke(int times);
}
