package com.netease.cloud.nsf.demo.service.impl;

import com.netease.cloud.nsf.demo.service.IRandomService;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Service
public class RandomServiceImpl implements IRandomService{

	Random random = new Random();
	
	@Override
	public int getRandomNumber() {
		return random.nextInt();
	}

	@Override
	public String getRanomString() {
		return UUID.randomUUID().toString();
	}

}
