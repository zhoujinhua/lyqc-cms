package com.liyun.car.activity.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.liyun.car.activity.entity.CmsParamSub;
import com.liyun.car.activity.entity.CmsParamTop;
import com.liyun.car.activity.service.ParamService;
import com.liyun.car.common.enums.OperMode;
import com.liyun.car.hibernate.service.impl.HibernateServiceSupport;

@Service
public class ParamServiceImpl extends HibernateServiceSupport implements ParamService{

	@Override
	public void saveParamTop(CmsParamTop top) {
		if(top.getId()!=null){
			updateEntity(top, "topParamNm","stt");
		} else {
			CmsParamTop paramTop = new CmsParamTop();
			paramTop.setTopParamEn(top.getTopParamEn());
			List<CmsParamTop> list = getEntitysByParams(paramTop, OperMode.EQ, "topParamEn");
			if(list!=null && !list.isEmpty()){
				throw new RuntimeException("重复的一级科目代码.");
			}
			
			top.setCrtTime(new Date());
			saveEntity(top);
		}
	}

	@Override
	public void saveParamSub(CmsParamSub sub) {
		if(sub.getId()!=null){
			updateEntity(sub, "subParamNm","stt","paramDesc","isReceipt","payAcctIdn");
		} else {
			CmsParamSub paramSub = new CmsParamSub();
			paramSub.setSubParamEn(sub.getSubParamEn());
			List<CmsParamSub> list = getEntitysByParams(paramSub, OperMode.EQ, "subParamEn");
			if(list!=null && !list.isEmpty()){
				throw new RuntimeException("重复的二级科目代码.");
			}
			sub.setCrtTime(new Date());
			saveEntity(sub);
		}
	}

}
