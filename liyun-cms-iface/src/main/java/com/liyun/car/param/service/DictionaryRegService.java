package com.liyun.car.param.service;

import java.util.List;

import com.liyun.car.hibernate.service.HibernateService;

public interface DictionaryRegService extends HibernateService{
	
	public List<String> getCityTree();
}
