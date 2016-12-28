package com.liyun.car.materiel.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.liyun.car.common.enums.OperMode;
import com.liyun.car.common.enums.ParamStatusEnum;
import com.liyun.car.hibernate.service.impl.HibernateServiceSupport;
import com.liyun.car.materiel.entity.CmsMaterielInfo;
import com.liyun.car.materiel.service.MaterielInfoService;

@Service
public class MaterielInfoServiceImpl extends HibernateServiceSupport implements MaterielInfoService {

	@Override
	public void saveMaterielInfo(CmsMaterielInfo info) {
		info.setUpTime(new Date());
		if(info.getId()!=null){
			updateEntity(info, "mtrlNm","mtrlUnit","mtrlTyp","remarks","price","mtrlBrand");
		} else {
			List<CmsMaterielInfo> list = getEntitysByParams(info, OperMode.EQ, "mtrlCode");
			if(list!=null && !list.isEmpty()){
				throw new RuntimeException("物料代码已存在.");
			}
			saveEntity(info);
		}
	}

	@Override
	public void deleteMaterielInfo(CmsMaterielInfo info) {
		info = getEntityById(CmsMaterielInfo.class, info.getId(), false);
		if(info.getStatus() == ParamStatusEnum.ON){
			info.setStatus(ParamStatusEnum.OFF);
		} else {
			info.setStatus(ParamStatusEnum.ON);
		}
	}

}
