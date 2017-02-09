package com.liyun.car.activity.service;

import java.util.List;

import com.liyun.car.activity.entity.CmsActivityRule;
import com.liyun.car.hibernate.service.HibernateService;

public interface ActivityRuleService extends HibernateService {

	void saveActivityRule(CmsActivityRule rule) throws Exception;

	void deleteActivityRule(CmsActivityRule rule);

	List<CmsActivityRule> getAvliaList(CmsActivityRule rule, String ruleIds);

	List<String> getActivityTree(String acttCode);

	void saveActivityRuleCopy(String acttCode, String ids) throws Exception;

}
