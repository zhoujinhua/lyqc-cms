package com.liyun.car.system.service.impl;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.liyun.car.hibernate.service.impl.HibernateServiceSupport;
import com.liyun.car.system.entity.SyUser;
import com.liyun.car.system.entity.vo.VUserRole;
import com.liyun.car.system.service.VUserRoleService;

@Service
public class VUserRoleServiceImpl extends HibernateServiceSupport implements VUserRoleService {

	@Override
	public boolean hasRole(SyUser user, String role) {
		List<VUserRole> list = getSession().getCriteria(VUserRole.class).add(Restrictions.eq("userPosition", role)).add(Restrictions.eq("userId", user.getUserId())).getResultList();
		if(list!=null && !list.isEmpty()){
			return true;
		}
		return false;
	}

}
