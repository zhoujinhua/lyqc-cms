package com.liyun.car.param.service;

import com.liyun.car.hibernate.service.HibernateService;
import com.liyun.car.param.entity.SyModel;
import com.liyun.car.system.entity.SyUser;

public interface ModelService extends HibernateService {

	void saveModel(SyModel model, SyUser user);

}
