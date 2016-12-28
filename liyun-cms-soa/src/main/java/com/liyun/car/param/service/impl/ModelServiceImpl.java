package com.liyun.car.param.service.impl;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.liyun.car.hibernate.service.impl.HibernateServiceSupport;
import com.liyun.car.param.entity.SyModel;
import com.liyun.car.param.service.ModelService;
import com.liyun.car.system.entity.SyUser;

@Service
public class ModelServiceImpl extends HibernateServiceSupport implements ModelService {

	@Override
	public void saveModel(SyModel model, SyUser user) {
		if(model.getId()!=null){
			updateEntity(model, "name","content","modelType","status","remark");
		} else {
			model.setUserId(user.getUserId());
			model.setCreateTime(new Date());
			
			saveEntity(model);
		}
	}

}
