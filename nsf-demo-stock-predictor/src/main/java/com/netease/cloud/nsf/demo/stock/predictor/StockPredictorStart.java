package com.netease.cloud.nsf.demo.stock.predictor;

import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * @author Yang Yujuan (yangyujuan@corp.netease.com) at 2018/8/2
 */

public class StockPredictorStart {
    public static void main(String[] args) throws Exception{
        FileSystemXmlApplicationContext context=new FileSystemXmlApplicationContext("classpath:spring.xml");
        context.start();
        System.in.read();
    }
}
