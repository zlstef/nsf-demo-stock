package com.netease.cloud.nsf.demo.service.impl;

import com.netease.cloud.nsf.demo.entity.Stock;
import com.netease.cloud.nsf.demo.service.IAdvisorService;
import com.netease.cloud.nsf.demo.service.IProviderService;
import com.netease.cloud.nsf.demo.service.IStockService;
import com.netease.cloud.nsf.demo.util.StringKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class AdvisorServiceImpl implements IAdvisorService{

	private static Logger log = LoggerFactory.getLogger(AdvisorServiceImpl.class);


	@Autowired
    RestTemplate restTemplate;

	@Value("${hot_stock_ids}")
	String hotStockIds;

	@Autowired
	IProviderService providerService;

	@Autowired
	IStockService stockService;

	private int retryCount = 0;

	@Override
	public List<Stock> getHotStocks() throws Exception {

		log.info("getHotStocks is invoked with retry count = " + retryCount );

		Thread.sleep(1000);
		if(retryCount ++ % 3 == 0){
			//throw new Exception();
		}

		List<Stock> stocks = null;
		Object[] params = {};
		if(StringKit.isEmpty(hotStockIds)) return stocks;

		String hotIds = getRecommendStockIds();
		try {
			String finalUrl = "providerService.getStocksByIds(hotIds)";
			stocks = providerService.getStocksByIds(hotIds);
			Stock debugInfo = new Stock();
			debugInfo.setId("");
			debugInfo.setName("(第" + retryCount + "次请求)");
			stocks.add(debugInfo);
			log.info("get hot stocks from {} successful : {}", finalUrl, stocks);
		} catch (Exception e) {
			log.warn("get hot stocks failed", e);
		}

		return stocks;
	}

	/**
	 * @return  ids separated by comma 
	 *  e.g. xx,yy,zz
	 */
	private String getRecommendStockIds() {
		return hotStockIds;
	}

//	@Override
//	public List<String> batchHi() {
//
//		List<String> results = new ArrayList<>();
//
//		for(int i = 0; i < 20; i++) {
//			String result = restTemplate.getForObject(stockProviderUrl + "/hi?p=" + i, String.class);
//			results.add(result);
//		}
//		return results;
//	}

	@Override
	public String deepInvoke(int times) {
		
		if(times --> 0) {
			return stockService.deepInvoke(times);
		} 
		return "finish";
	}
}
