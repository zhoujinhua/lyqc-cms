package com.liyun.car.param.service;

import com.liyun.car.hibernate.service.HibernateService;
import com.liyun.car.param.entity.SyDepartment;

public interface DepartmentService extends HibernateService {

	void saveDepartment(SyDepartment department);

	void insertDepartment();

	void inserthuluanxiede();

}
