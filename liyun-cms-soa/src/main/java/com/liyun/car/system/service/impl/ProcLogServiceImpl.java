package com.liyun.car.system.service.impl;

import org.springframework.stereotype.Service;

import com.liyun.car.common.entity.Page;
import com.liyun.car.common.utils.DateUtil;
import com.liyun.car.hibernate.service.impl.HibernateServiceSupport;
import com.liyun.car.system.entity.SyProcLog;
import com.liyun.car.system.service.ProcLogService;

@Service
public class ProcLogServiceImpl extends HibernateServiceSupport implements ProcLogService {

	private String genHql(SyProcLog log){
		StringBuffer hql = new StringBuffer("select sp from SyProcLog sp where 1=1 ");
		if(log!=null){
			if(log.getRcdDt()!=null){
				hql.append(" and date_format(sp.rcdDt,'%Y-%m-%d')='"+DateUtil.formatDay(log.getRcdDt())+"'");
			}
			if(log.getStrDt()!=null){
				hql.append(" and date_format(sp.strDt,'%Y-%m-%d')>='"+DateUtil.formatDay(log.getStrDt())+"'");
			}
			if(log.getEndDt()!=null){
				hql.append(" and date_format(sp.endDt,'%Y-%m-%d')<='"+DateUtil.formatDay(log.getEndDt())+"'");	
			}
			if(log.getRtnSqlcode()!=null && !"".equals(log.getRtnSqlcode())){
				hql.append(" and sp.rtnSqlcode='"+log.getRtnSqlcode()+"'");	
			}
			if(log.getProcEnglishName()!=null && !"".equals(log.getProcEnglishName())){
				hql.append(" and sp.procEnglishName='"+log.getProcEnglishName()+"'");
			}
		}
		hql.append(" order by sp.strDt desc,sp.procEnglishName asc,sp.stepNum asc");
		return hql.toString();
	}
	
	@Override
	public Page<SyProcLog> pageList(int pn, SyProcLog log) {
		String hql = genHql(log);
		return pageList(pn, hql);
	}

}
