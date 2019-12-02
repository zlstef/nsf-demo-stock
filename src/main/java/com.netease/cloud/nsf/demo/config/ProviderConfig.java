package com.netease.cloud.nsf.demo.config;

import com.netease.cloud.nsf.demo.client.MockClient;
import com.netease.cloud.nsf.demo.client.NeteaseStockClient;
import com.netease.cloud.nsf.demo.client.StockClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ProviderConfig {

	@Bean(name="remoteRestTemplate")
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
//	@Bean
//	@ConditionalOnProperty(name="offline", havingValue="true")
//	public StockClient mockStockClient() {
//		return new MockClient();
//	}
	
	@Bean
//	@ConditionalOnMissingBean
	public StockClient neteaseStockClient() {
		return new NeteaseStockClient();
	}
}
