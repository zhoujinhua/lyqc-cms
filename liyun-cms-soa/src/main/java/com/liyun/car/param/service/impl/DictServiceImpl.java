package com.liyun.car.param.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.liyun.car.common.enums.OperMode;
import com.liyun.car.hibernate.service.impl.HibernateServiceSupport;
import com.liyun.car.param.entity.SyDepartment;
import com.liyun.car.param.entity.SyDict;
import com.liyun.car.param.service.DictService;

@Service
public class DictServiceImpl extends HibernateServiceSupport implements DictService {

	@Override
	public void saveArgControl(SyDict arg) {
		if(arg.getId()!=null){
			updateEntity(arg,"name","status","value");
		} else {
			SyDict dict = new SyDict();
			dict.setCode(arg.getCode());
			List<SyDict> list = getEntitysByParams(dict, OperMode.EQ, "code");
			if(list!=null && !list.isEmpty()){
				throw new RuntimeException("参数编码必须唯一.");
			}
			
			arg.setConArgStartdate(new Date());
			saveEntity(arg);
		}
	}

	@Override
	public void deleteArgControl(SyDict arg) {
		deleteEntity(arg);
	}

	@Override
	@Async
	public void inserthuluanxiede(){
		try {
			Thread.sleep(5000);
			System.out.println("这里是异步方法."+new Date());
			SyDepartment department = new SyDepartment();
			department.setDepartmentName("测试2");
			department.setCrtTime(new Date());
			this.saveEntity(department);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
