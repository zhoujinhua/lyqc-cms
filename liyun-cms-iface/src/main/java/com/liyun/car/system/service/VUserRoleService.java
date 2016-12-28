package com.liyun.car.system.service;

import com.liyun.car.hibernate.service.HibernateService;
import com.liyun.car.system.entity.SyUser;

public interface VUserRoleService extends HibernateService{
	
	public boolean hasRole(SyUser user, String role);
}
