package com.liyun.car.param.service;

import org.activiti.engine.delegate.DelegateTask;

import com.liyun.car.common.entity.Page;
import com.liyun.car.hibernate.service.HibernateService;
import com.liyun.car.param.entity.SyTask;
import com.liyun.car.system.entity.SyUser;

public interface TaskService extends HibernateService {

	Page<SyTask> pageList(SyTask task, SyUser user, int pn);

	Page<?> pageList(SyUser user, String subject, String crtTime, int pn);

	void saveTask(DelegateTask task);

}
