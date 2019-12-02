package com.netease.cloud.nsf.demo.service;

import com.netease.cloud.nsf.demo.entity.PredictPrice;

import java.util.HashMap;
import java.util.List;

/**
 * @author Yang Yujuan (yangyujuan@corp.netease.com) at 2018/8/2
 */

public interface IPredictorService {

    public HashMap<String, String> getMaxSpreadStockName() throws Exception;

    public List<PredictPrice> getPredictPriceById(String stockId) throws Exception;
}
