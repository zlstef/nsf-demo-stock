package com.netease.cloud.nsf.demo.service.impl;

import com.netease.cloud.nsf.demo.entity.Stock;
import com.netease.cloud.nsf.demo.service.IPredictorService;
import com.netease.cloud.nsf.demo.service.IStockService;
import com.netease.cloud.nsf.demo.service.IProviderService;
import com.netease.cloud.nsf.demo.service.IAdvisorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class StockServiceImpl implements IStockService {

    private static Logger log = LoggerFactory.getLogger(StockServiceImpl.class);
    
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    IStockService stockService;

    @Autowired
    IProviderService providerService;

    @Autowired
    IAdvisorService advisorService;

    @Autowired
    IPredictorService predictorService;

    @Value("${stock_list_size}")
    int stockListSize;

	// 超时
    @Override
    public List<Stock> getStockList(int delay) throws Exception {

        log.info("start to get stock list ...");

        List<Stock> stocks;

        String finalUrl = "providerService.getAllStocks()";
        stocks = providerService.getAllStocks();
        if (stocks.size() > stockListSize) {//判断list长度
            stocks = stocks.subList(0, stockListSize);
        }

        log.info("get all stocks from {} successful : {}", finalUrl, stocks);
        return stocks;
    }


    @Override
    public Stock getStockById(String stockId) throws Exception {

        Stock stock = null;
        String finalUrl = "providerService.getStockById";
        stock = providerService.getStockById(stockId);
        log.info("get stock from {} by {} successful : {}", finalUrl, stockId, stock);
        return stock;
    }


    @Override
    public List<Stock> getHotStockAdvice() throws Exception {

        log.info("start to get hot stock advice ...");
        List<Stock> stocks;
        String finalUrl = "advisorService.getHotStocks()";
        stocks = advisorService.getHotStocks();

        log.info("get hot stock advice from {} successful : {}", finalUrl, stocks);

        return stocks;
    }
    
    @Override
    public String echoAdvisor() {
    	
    	int times = 10;
    	StringBuilder sBuilder = new StringBuilder();
        String url = advisorService.toString();
        sBuilder.append(url);
    	return sBuilder.toString();
    }
    
    @Override
    public String echoProvider() {
    	
    	int times = 10;
    	StringBuilder sBuilder = new StringBuilder();
        String url = providerService.toString();
        sBuilder.append(url);
    	return sBuilder.toString();
    }


	@Override
	public String deepInvoke(int times) {
		if(times --> 0) {
            return advisorService.deepInvoke(times);
		} 
		return "finish";
	}

    @Override
    public String getMaxSpreadStock() {
        log.info("start to get max spread stock ...");
        String finalUrl = "predictorService.getMaxSpreadStockName()";
        try {
            String spread = predictorService.getMaxSpreadStockName().toString();
            log.info("get max spread stock from {} successful : {}", finalUrl, spread);
            return spread;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "getMaxSpreadStock error";
    }

    @Override
    public String getPredictPriceById(String stockId) {
        log.info("start to get predict stock price...");
        String finalUrl = "predictorService.getPredictPriceById(stockId)";
        try {
            String predictPrice = predictorService.getPredictPriceById(stockId).toString();

            log.info("get predict stock price from {} successful : {}", finalUrl, predictPrice);
            return predictPrice;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "getPredictPriceById error";
    }


//
    @Override
    public String getConfigString() {
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append(stockListSize);
        return sBuilder.toString();
    }
}
