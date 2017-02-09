package com.liyun.car.param.service;

import com.liyun.car.hibernate.service.HibernateService;
import com.liyun.car.param.entity.SyDict;

public interface DictService extends HibernateService {

	void saveArgControl(SyDict arg);

	void deleteArgControl(SyDict arg);

	void inserthuluanxiede();

}
