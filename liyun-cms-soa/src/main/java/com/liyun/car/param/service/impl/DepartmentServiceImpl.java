package com.liyun.car.param.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liyun.car.hibernate.service.impl.HibernateServiceSupport;
import com.liyun.car.param.entity.SyDepartment;
import com.liyun.car.param.service.DepartmentService;
import com.liyun.car.param.service.DictService;

@Service
public class DepartmentServiceImpl extends HibernateServiceSupport implements DepartmentService {

	@Autowired
	private DictService dictService;
	
	@Override
	public void saveDepartment(SyDepartment department) {
		department.setCrtTime(new Date());
		if(department.getParDepartment() == null || department.getParDepartment().getDepartmentId() == null){
			department.setParDepartment(null);
		}
		if(department.getDepartmentId()!=null){
			updateEntity(department, "parDepartment","departmentName","departmentLevel","status","remark","role");
		} else {
			saveEntity(department);
		}
	}
	
	@Override
	public void insertDepartment(){
		SyDepartment department = new SyDepartment();
		department.setDepartmentName("测试1");
		department.setCrtTime(new Date());
		dictService.inserthuluanxiede();
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		saveEntity(department);
	}
	@Override
	@Transactional
	public void inserthuluanxiede(){
		try {
			Thread.sleep(10000);
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
