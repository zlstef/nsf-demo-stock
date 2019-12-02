package com.netease.cloud.nsf.demo.stock.predictor.web.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.netease.cloud.nsf.demo.stock.predictor.web.entity.PredictPrice;
import com.netease.cloud.nsf.demo.stock.predictor.web.entity.Stock;
import com.netease.cloud.nsf.demo.stock.predictor.web.service.IPredictorService;
import com.netease.cloud.nsf.demo.stock.predictor.web.util.CastKit;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @author Yang Yujuan (yangyujuan@corp.netease.com) at 2018/8/2
 */
@Path("stocks")
public class PredictorServiceImpl implements IPredictorService {

    private static Logger log = LoggerFactory.getLogger(PredictorServiceImpl.class);

    RestTemplate restTemplate = new RestTemplate();

    static final Object[] EMPTY_PARAMS = {};

    @Value("${stock_provider_url}")
    String stockProviderUrl;

    @Override
    @Path("spread")
    @GET
    @Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
    public HashMap<String, String> getMaxSpreadStockName() throws Exception {
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

    @Override
    @Path("predictPrice/{stockId}")
    @GET
    @Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
    public List<PredictPrice> getPredictPriceById(@PathParam("stockId") String stockId) throws Exception {
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
}