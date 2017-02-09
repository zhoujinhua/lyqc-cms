package com.liyun.car.activity.service;

import com.liyun.car.activity.entity.CmsActivityRuleGroup;
import com.liyun.car.hibernate.service.HibernateService;

public interface ActivityRuleExService extends HibernateService {

	void saveActivityRuleEx(CmsActivityRuleGroup ruleEx);

	void deleteGroup(CmsActivityRuleGroup group);

}
