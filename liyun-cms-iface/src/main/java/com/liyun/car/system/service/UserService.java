package com.liyun.car.system.service;

import java.util.List;

import com.liyun.car.common.entity.Page;
import com.liyun.car.hibernate.service.HibernateService;
import com.liyun.car.system.entity.SyItem;
import com.liyun.car.system.entity.SyUser;

public interface UserService extends HibernateService{
	
	public Page<SyUser> pageList(SyUser user, int pn);
	
	public List<SyUser> getEntitysByParams(SyUser user,String...params);
	
	public void updateEntity(SyUser user, boolean check ,String...params);
	
	public void saveEntity(SyUser user);
	
	public List<SyUser> getEntitysByIds(int...ids);
	
	public SyUser getEntityById(int id);

	public void updateUserPassword(SyUser user);

	public void updateSyUserPermSets(SyUser user, String[] permIds);
	
	List<Integer> getUsersByPerm(String role, String itemId);

	public SyItem userHasItem(SyUser user, String itemId);
	
}
