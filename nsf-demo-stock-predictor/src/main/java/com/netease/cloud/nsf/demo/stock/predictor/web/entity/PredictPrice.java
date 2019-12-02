package com.netease.cloud.nsf.demo.stock.predictor.web.entity;

/**
 * @author Yang Yujuan (yangyujuan@corp.netease.com) at 2018/8/2
 */

public class PredictPrice {
    private String date;
    /**
     * 预测开盘价
     */
    private String predictOpeningPrice;
    /**
     * 预测昨日收盘价
     */
    private String predictClosingPrice;
    /**
     * 预测今日最高价
     */
    private String predictTopTodayPrice;
    /**
     * 预测今日最低价
     */
    private String predictBottomTodayPrice;

    public void setPredictPrice(String date, String predictOpeningPrice, String predictClosingPrice,
                                String predictTopTodayPrice, String predictBottomTodayPrice) {
        this.date = date;
        this.predictOpeningPrice = predictOpeningPrice;
        this.predictClosingPrice = predictClosingPrice;
        this.predictTopTodayPrice = predictTopTodayPrice;
        this.predictBottomTodayPrice = predictBottomTodayPrice;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPredictOpeningPrice() {
        return predictOpeningPrice;
    }

    public void setPredictOpeningPrice(String predictOpeningPrice) {
        this.predictOpeningPrice = predictOpeningPrice;
    }

    public String getPredictClosingPrice() {
        return predictClosingPrice;
    }

    public void setPredictClosingPrice(String predictClosingPrice) {
        this.predictClosingPrice = predictClosingPrice;
    }

    public String getPredictTopTodayPrice() {
        return predictTopTodayPrice;
    }

    public void setPredictTopTodayPrice(String predictTopTodayPrice) {
        this.predictTopTodayPrice = predictTopTodayPrice;
    }

    public String getPredictBottomTodayPrice() {
        return predictBottomTodayPrice;
    }

    public void setPredictBottomTodayPrice(String predictBottomTodayPrice) {
        this.predictBottomTodayPrice = predictBottomTodayPrice;
    }
}
