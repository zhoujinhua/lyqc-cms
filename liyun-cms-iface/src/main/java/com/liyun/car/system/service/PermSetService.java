package com.liyun.car.system.service;

import com.liyun.car.hibernate.service.HibernateService;
import com.liyun.car.system.entity.SyPermSet;
import com.liyun.car.system.entity.SyUser;

public interface PermSetService extends HibernateService{
	
	public String getPermSetTree(SyUser user);

	public void updatePermSet(SyPermSet permSet, String[] split);
	
	public String getMenuTree(SyPermSet permSet);

	public String getUserMenuTree(SyUser user);

	public void savePermSet(SyPermSet permSet, SyUser user);
}
