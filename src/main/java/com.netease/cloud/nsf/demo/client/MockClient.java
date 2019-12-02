package com.netease.cloud.nsf.demo.client;

import com.netease.cloud.nsf.demo.entity.Stock;
import com.netease.cloud.nsf.demo.util.CastKit;
import com.netease.cloud.nsf.demo.util.StringKit;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ResourceUtils;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class MockClient implements StockClient, InitializingBean {

	private Map<String, Stock> stockMap = new HashMap<>();
	
	@Override
	public Stock getStockById(String stockId) throws Exception {
		
		if(StringKit.isEmpty(stockId)) return null;
		return stockMap.get(stockId.toLowerCase());
	}

	@Override
	public List<Stock> getStockBatchByIds(String stockIds) throws Exception {
		
		List<Stock> stocks = new ArrayList<>();
		if(StringKit.isEmpty(stockIds)) return stocks;
		
		Arrays.stream(stockIds.split(",")).forEach(id -> {
			stocks.add(stockMap.get(id.toLowerCase()));
		});
		
		return stocks;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		
		String stockStr = new String(Files.readAllBytes(Paths.get(
				ResourceUtils.getFile("classpath:stock_data.json").toURI())));
		List<Stock> stocks = CastKit.str2StockList(stockStr);
		if(!CollectionUtils.isEmpty(stocks)) {
			stocks.forEach(s -> {
				stockMap.put(s.getId().toLowerCase(), s);
			});
		}
	}

}
