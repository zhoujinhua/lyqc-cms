package com.liyun.car.system.service;

import com.liyun.car.common.entity.Page;
import com.liyun.car.hibernate.service.HibernateService;
import com.liyun.car.system.entity.SyProcLog;

public interface ProcLogService extends HibernateService {

	Page<SyProcLog> pageList(int pn, SyProcLog log);

}
