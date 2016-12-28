package com.liyun.car.system.service.impl;

import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;

import com.liyun.car.common.entity.Page;
import com.liyun.car.hibernate.hibernate.CrapCriteria;
import com.liyun.car.hibernate.service.impl.HibernateServiceSupport;
import com.liyun.car.system.entity.SyEmailLog;
import com.liyun.car.system.service.EmailLogService;

@Service
public class EmailLogServiceImpl extends HibernateServiceSupport implements EmailLogService {

	@Override
	public Page<Object[]> pageList(int pn, SyEmailLog log) {
		String sql = "SELECT batch_no BATCH_NO,count(1) TOTAL_COUNT,sum(case when status = '1' then 1 else 0 end) SUCC_COUNT,sum(case" +
				"  when status = '0' then 1 else 0 end) ERROR_COUNT," +
				"  (max(crt_time)-min(crt_time)) TIME_BETWEEN" +
				"  from Sy_Email_log";
		if(log!=null && log.getBatchNo()!=null && !"".equals(log.getBatchNo())){
			sql += " where batch_No = '"+log.getBatchNo()+"'";
		}
		sql += " group by batch_no desc";
		return getSession().createNativeQuery(sql).setResultTransfer(Transformers.ALIAS_TO_ENTITY_MAP).getResultList(pn);
	}

	@Override
	public Page<SyEmailLog> pageIList(SyEmailLog log, int pn, String companyCode, String companyName, String feeMon) {
		CrapCriteria<SyEmailLog> cri = getSession().getCriteria(SyEmailLog.class);
		if(log!=null && log.getStatus()!=null && !"".equals(log.getStatus())){
			cri.add(Restrictions.eq("status", log.getStatus()));
		}
		if(log!=null && log.getBatchNo()!=null && !"".equals(log.getBatchNo())){
			cri.add(Restrictions.eq("batchNo", log.getBatchNo()));
		}
		if(companyCode!=null && !"".equals(companyCode)){
			cri.add(Restrictions.like("remark", "%"+companyCode+"%"));
		}
		if(companyName!=null && !"".equals(companyName)){
			cri.add(Restrictions.like("remark", "%"+companyName+"%"));
		}
		if(feeMon!=null && !"".equals(feeMon)){
			cri.add(Restrictions.like("remark", "%"+feeMon+"%"));
		}
		return cri.getResultList(pn);
	}

}
