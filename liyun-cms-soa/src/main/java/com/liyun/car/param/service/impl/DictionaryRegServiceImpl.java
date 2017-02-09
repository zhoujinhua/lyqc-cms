package com.liyun.car.param.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.liyun.car.common.enums.OperMode;
import com.liyun.car.hibernate.service.impl.HibernateServiceSupport;
import com.liyun.car.param.entity.SyDictionaryReg;
import com.liyun.car.param.service.DictionaryRegService;

@Service
public class DictionaryRegServiceImpl extends HibernateServiceSupport implements DictionaryRegService {

	@Override
	public List<String> getCityTree() {
		List<String> treeList = new ArrayList<String>();
		SyDictionaryReg reg = new SyDictionaryReg();
		reg.setRegCodePar("0");
		List<SyDictionaryReg> provinces = getEntitysByParams(reg, OperMode.EQ, "regCodePar");
		for(int i=0;i<provinces.size();i++){
			treeList.add("{id:\""+provinces.get(i).getRegCode()+"\",pId:\"999999\",name:\""+provinces.get(i).getRegName()+"\"}");
		}
		
		reg.setRegLevel("1");
		List<SyDictionaryReg> citys = getEntitysByParams(reg, OperMode.EQ, "regLevel");
		for(int i=0;i<citys.size();i++){
			treeList.add("{id:\""+citys.get(i).getRegCode()+"\",pId:\""+citys.get(i).getRegCodePar()+"\",name:\""+citys.get(i).getRegName()+"\",code:\""+citys.get(i).getRegName()+"\"}");
		}
		return treeList;
	}

}
