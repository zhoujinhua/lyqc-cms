package com.liyun.car.activity.service.impl;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.liyun.car.activity.entity.CmsActivityRuleGroup;
import com.liyun.car.activity.entity.CmsActivityRuleEx;
import com.liyun.car.activity.service.ActivityRuleExService;
import com.liyun.car.common.utils.DateUtil;
import com.liyun.car.hibernate.service.impl.HibernateServiceSupport;

@Service
public class ActivityRuleExServiceImpl extends HibernateServiceSupport implements ActivityRuleExService {

	@Override
	public void saveActivityRuleEx(CmsActivityRuleGroup group) {
		if(group.getId()!=null){
			updateEntity(group, "exName","remark","status");
			CmsActivityRuleGroup ruleGroup = getEntityById(CmsActivityRuleGroup.class, group.getId(), true);
			ruleGroup.getExProps().clear();
			
			for(CmsActivityRuleEx prop : group.getExProps()){
				prop.setGroup(group);
				prop.setExCode(group.getExCode());
				prop.setExName(group.getExName());
				prop.setStatus(group.getStatus());
				ruleGroup.getExProps().add(prop);
			}
		} else {
			group.setCreateTime(new Date());
			group.setExCode("HC"+DateUtil.getDateFormatAll_(new Date()));
			saveEntity(group);
			for(CmsActivityRuleEx prop : group.getExProps()){
				prop.setGroup(group);
				prop.setExCode(group.getExCode());
				prop.setExName(group.getExName());
				prop.setStatus(group.getStatus());
			}
		}
	}

	@Override
	public void deleteGroup(CmsActivityRuleGroup group) {
		group = getEntityById(CmsActivityRuleGroup.class, group.getId(), true);
		group.getExProps().clear();
		deleteEntity(group);
	}

}
