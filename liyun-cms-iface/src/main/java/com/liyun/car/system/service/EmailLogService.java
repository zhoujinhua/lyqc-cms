package com.liyun.car.system.service;

import com.liyun.car.common.entity.Page;
import com.liyun.car.hibernate.service.HibernateService;
import com.liyun.car.system.entity.SyEmailLog;

public interface EmailLogService extends HibernateService {

	Page<Object[]> pageList(int pn, SyEmailLog log);

	Page<SyEmailLog> pageIList(SyEmailLog log, int pn, String companyCode, String companyName, String feeMon);

}
