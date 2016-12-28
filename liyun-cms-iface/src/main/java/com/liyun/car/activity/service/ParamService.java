package com.liyun.car.activity.service;

import com.liyun.car.activity.entity.CmsParamSub;
import com.liyun.car.activity.entity.CmsParamTop;
import com.liyun.car.hibernate.service.HibernateService;

public interface ParamService extends HibernateService {

	void saveParamTop(CmsParamTop top);

	void saveParamSub(CmsParamSub sub);

}
