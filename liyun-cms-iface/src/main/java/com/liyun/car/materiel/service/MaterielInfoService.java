package com.liyun.car.materiel.service;

import com.liyun.car.hibernate.service.HibernateService;
import com.liyun.car.materiel.entity.CmsMaterielInfo;

public interface MaterielInfoService extends HibernateService{

	void saveMaterielInfo(CmsMaterielInfo info);

	void deleteMaterielInfo(CmsMaterielInfo info);
	
}
