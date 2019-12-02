package com.netease.cloud.nsf.demo.service;

import com.netease.cloud.nsf.demo.entity.PredictPrice;
import com.netease.cloud.nsf.demo.entity.Stock;
import com.netease.cloud.nsf.demo.util.CastKit;
import com.netease.cloud.nsf.demo.util.StringKit;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

@Service
public class StockService {

    private static Logger log = LoggerFactory.getLogger(StockService.class);

    static final Object[] EMPTY_PARAMS = {};

    RestTemplate restTemplate = new RestTemplate();


    @Value("${stock_list_size}")
    int stockListSize;

    Random random = new Random();

    @Autowired
    NeteaseStockClient stockClient;


    //mock
    @Value("${stock_ids}")
    private String stockIds;


    @Value("${stock_provider_url}")
    String stockProviderUrl;


    @Value("${hot_stock_ids}")
    String hotStockIds;


    private int retryCount = 0;

	// 超时
//    @Override
    public List<Stock> getStockList(int delay) throws Exception {

        log.info("start to get stock list ...");

        List<Stock> stocks;

        String finalUrl = "providerService.getAllStocks()";
        stocks = this.getAllStocks();
        if (stocks.size() > stockListSize) {//判断list长度
            stocks = stocks.subList(0, stockListSize);
        }

        log.info("get all stocks from {} successful : {}", finalUrl, stocks);
        return stocks;
    }


//    @Override
    public Stock getStockById(String stockId) throws Exception {

        Stock stock = null;
        String finalUrl = "providerService.getStockById";
//        stock = this.getStockById(stockId);
        if(StringKit.isEmpty(stockId)) return stock;
        try {
            stock = stockClient.getStockById(stockId);
        } catch (Exception e) {
            log.warn("get stock by id failed ",e);
        }
        log.info("get stock from {} by {} successful : {}", finalUrl, stockId, stock);
        return stock;
    }


//    @Override
    public List<Stock> getHotStockAdvice() throws Exception {

        log.info("start to get hot stock advice ...");
        List<Stock> stocks;
        String finalUrl = "advisorService.getHotStocks()";
        stocks = this.getHotStocks();

        log.info("get hot stock advice from {} successful : {}", finalUrl, stocks);

        return stocks;
    }
    
//    @Override
    public String echoAdvisor() {
    	
    	int times = 10;
    	StringBuilder sBuilder = new StringBuilder();
        String url = this.toString();
        sBuilder.append(url);
    	return sBuilder.toString();
    }
    
//    @Override
    public String echoProvider() {
    	
    	int times = 10;
    	StringBuilder sBuilder = new StringBuilder();
        String url = this.toString();
        sBuilder.append(url);
    	return sBuilder.toString();
    }


//	@Override
	public String deepInvoke(int times) {
		if(times --> 0) {
            return this.deepInvoke(times);
		} 
		return "finish";
	}

//    @Override
    public String getMaxSpreadStock() {
        log.info("start to get max spread stock ...");
        String finalUrl = "predictorService.getMaxSpreadStockName()";
        try {
            String spread = this.getMaxSpreadStockName().toString();
            log.info("get max spread stock from {} successful : {}", finalUrl, spread);
            return spread;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "getMaxSpreadStock error";
    }

//    @Override
    public String getPredictPriceById(String stockId) {
        log.info("start to get predict stock price...");
        String finalUrl = "predictorService.getPredictPriceById(stockId)";
        try {
            String predictPrice = this.getPredictPriceListById(stockId).toString();

            log.info("get predict stock price from {} successful : {}", finalUrl, predictPrice);
            return predictPrice;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "getPredictPriceById error";
    }


//
//    @Override
    public String getConfigString() {
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append(stockListSize);
        return sBuilder.toString();
    }

//    @Override
    public int getRandomNumber() {
        return random.nextInt();
    }

//    @Override
    public String getRanomString() {
        return UUID.randomUUID().toString();
    }

    private List<Stock> getAllStocks() {

        List<Stock> stocks = new ArrayList<>();
        if(!StringKit.isEmpty(stockIds)) {
            for(String id : stockIds.split(",")) {
//                Stock s = getStockById(id);
                Stock s = null;
                List<Stock> stock = null;
                if(StringKit.isEmpty(stockIds)) return stock;
                try {
                    s = stockClient.getStockById(id);
                } catch (Exception e) {
                    log.warn("get stock by id failed ",e);
                }
//                return stock;
                if(s != null) {
                    stocks.add(s);
                }
            }
        }
        return stocks;
    }

//    @Override
//    public Stock getStockById(String stockId) {
//
//        Stock stock = null;
//        if(StringKit.isEmpty(stockId)) return stock;
//        try {
//            stock = stockClient.getStockById(stockId);
//        } catch (Exception e) {
//            log.warn("get stock by id failed ",e);
//        }
//        return stock;
//    }

    private List<Stock> getStocksByIds(String stockIds) {

        List<Stock> stocks = null;
        if(StringKit.isEmpty(stockIds)) return stocks;
        try {
            stocks = stockClient.getStockBatchByIds(stockIds);
        } catch (Exception e) {
            log.warn("get stocks by ids failed ",e);
        }
        return stocks;
    }


    private HashMap<String, String> getMaxSpreadStockName() throws Exception {
        HashMap<String, String> MaxSpreadStocks = new HashMap<>();
        List<Stock> stocks = null;
        String finalUrl = stockProviderUrl + "/stocks";
        String stockStrs = restTemplate.getForObject(finalUrl, String.class, EMPTY_PARAMS);
        stocks = CastKit.str2StockList(stockStrs);

        double maxSpread = 0;
        double minSpread = Integer.MAX_VALUE;
        String maxSpreadStock = null;
        String minSpreadStock = null;

        for (Stock s : stocks) {
            BigDecimal TopTodayPrice = new BigDecimal(s.getTopTodayPrice());
            BigDecimal BottomTodayPrice = new BigDecimal(s.getBottomTodayPrice());
            double spread = TopTodayPrice.subtract(BottomTodayPrice).doubleValue();
            if (spread > maxSpread) {
                maxSpread = spread;
                maxSpreadStock = s.getName();
            }
            if (spread < minSpread) {
                minSpread = spread;
                minSpreadStock = s.getName();
            }
        }
        MaxSpreadStocks.put("今日最小价差", minSpreadStock + "(" + minSpread + ")");
        MaxSpreadStocks.put("今日最大价差", maxSpreadStock + "(" + maxSpread + ")");
        return MaxSpreadStocks;
    }

    private List<PredictPrice> getPredictPriceListById(String stockId) throws Exception {
        List<PredictPrice> predictPrices = new ArrayList<>();
        LinkedHashMap<String, PredictPrice> predictPriceHashMap = new LinkedHashMap<>();
        Stock stock = null;
        try {
            //获得当前stock数据
            String finalUrl = stockProviderUrl + "/stocks/" + stockId;
            String stockStr = restTemplate.getForObject(finalUrl, String.class, EMPTY_PARAMS);
            stock = CastKit.str2Stock(stockStr);
            if (stock == null) {
                return predictPrices;
            }
            log.info("get stock by id from {} successful : {}", finalUrl, stock);
        } catch (Exception e) {
            log.warn("get stock by id failed", e);
        }

        //计算今日涨幅
        Double openingPrice = Double.parseDouble(stock.getOpeningPrice());
        Double closingPrice = Double.parseDouble(stock.getClosingPrice());
        Double topPrice = Double.parseDouble(stock.getTopTodayPrice());
        Double bottomPrice = Double.parseDouble(stock.getBottomTodayPrice());
        Double openInc = 1.0 + (openingPrice - closingPrice) / closingPrice;
        Double topInc = 1.0 + (topPrice - openingPrice) / openingPrice;
        DecimalFormat df = new DecimalFormat("#.0000");
        DateTime dateTime = new DateTime(new Date());
        //计算未来7日的预测数据
        for (int i = 1; i <= 7; i++) {
            String date = dateTime.plusDays(i).toString("yyyy-MM-dd");
            PredictPrice predictStockPrice = new PredictPrice();
            double currentOpenInc = Math.pow(openInc, i);
            double currentTopInc = Math.pow(topInc, i);
            predictStockPrice.setPredictPrice(
                    date,
                    df.format(openingPrice * currentOpenInc),
                    df.format(closingPrice * currentOpenInc),
                    df.format(topPrice * currentTopInc),
                    df.format(bottomPrice * currentTopInc));
            predictPriceHashMap.put(date, predictStockPrice);
            predictPrices.add(predictStockPrice);
        }
        return predictPrices;
    }


    private List<Stock> getHotStocks() throws Exception {

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
            stocks = this.getStocksByIds(hotIds);
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

//    @Override
//    public String deepInvoke(int times) {
//
//        if(times --> 0) {
//            return this.deepInvoke(times);
//        }
//        return "finish";
//    }
}
