package com.liyun.car.materiel.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liyun.car.common.entity.Page;
import com.liyun.car.common.enums.BooleanEnum;
import com.liyun.car.common.enums.OperMode;
import com.liyun.car.common.utils.DateUtil;
import com.liyun.car.hibernate.service.impl.HibernateServiceSupport;
import com.liyun.car.materiel.entity.CmsDealerMateriel;
import com.liyun.car.materiel.entity.CmsDealerMaterielApproval;
import com.liyun.car.materiel.entity.CmsMaterielExpressHis;
import com.liyun.car.materiel.enums.DealerMaterielActionEnum;
import com.liyun.car.materiel.enums.DealerMaterielStatusEnum;
import com.liyun.car.materiel.service.DealerMaterielService;
import com.liyun.car.system.entity.SyUser;
import com.liyun.car.system.enums.UserTypeEnum;
import com.liyun.car.workflow.service.WorkflowService;

@Service
public class DealerMaterielServiceImpl extends HibernateServiceSupport implements DealerMaterielService {
	
	@Autowired
	private WorkflowService workflowService;
	
	private String getPageHql(CmsDealerMateriel materiel, String mtrlTyp, SyUser user){
		String hql = "select u ";
		if(user.getUserType() == UserTypeEnum.M){
			hql += "from CmsDealerMateriel u where u.status not in ('"+DealerMaterielStatusEnum.NEW.getValue()+"','"+DealerMaterielStatusEnum.PRECHECK.getValue()+"','"+DealerMaterielStatusEnum.SONO.getValue()+"')";
		} else {
			hql += "from CmsDealerMateriel u,SyUserDealer ud where u.dealerCode = ud.dealer.dealerCode and ud.user.userId="+user.getUserId();
		}
		if(materiel!=null){
			if(materiel.getDealerCode()!=null){
				hql += " and u.dealerCode = "+materiel.getDealerCode();
			}
			if(materiel.getDealerName()!=null && !"".equals(materiel.getDealerName())){
				hql += " and u.dealerName = '"+materiel.getDealerName()+"'";
			}
			if(mtrlTyp!=null && !"".equals(mtrlTyp)){
				hql += " and u.info.mtrlTyp = '"+mtrlTyp+"'";
			}
		}
		hql += " order by u.appDt desc";
		return hql;
	}
	
	@Override
	public Page<CmsDealerMateriel> pageList(CmsDealerMateriel materiel, int pn, String mtrlTyp, SyUser user) {
		String hql = getPageHql(materiel, mtrlTyp, user);
		return pageList(pn, hql);
	}

	@Override
	public List<CmsDealerMateriel> getList(CmsDealerMateriel materiel, SyUser user) {
		String hql = getPageHql(materiel, null, user);
		return getList(hql);
	}

	@Override
	public void saveStartProcess(CmsDealerMateriel materiel, SyUser user) {
		materiel.setStatus(DealerMaterielStatusEnum.PRECHECK);
		saveDealerMateriel(materiel);
		
		CmsDealerMaterielApproval approval = new CmsDealerMaterielApproval();
		approval.setAction(DealerMaterielActionEnum.SUBMIT);
		approval.setPreStt(DealerMaterielStatusEnum.NEW);
		approval.setNextStt(DealerMaterielStatusEnum.PRECHECK);
		approval.setUserName(user.getUserId()+"");
		approval.setTrueName(user.getTrueName());
		approval.setCrtTime(new Date());
		approval.setTyp(BooleanEnum.YES);
		approval.setMtrlAppCode(materiel.getMtrlAppCode());
		
		saveEntity(approval);
		workflowService.startProcess(materiel.getMtrlAppCode()+"", CmsDealerMateriel.FLOW_NAME, user.getUserId()+"", null);
	}

	@Override
	public void saveCompleteTask(CmsDealerMateriel materiel, String taskId, CmsDealerMaterielApproval approval, SyUser user) {
		CmsDealerMateriel dealerMateriel = getEntityById(CmsDealerMateriel.class, materiel.getMtrlAppCode(), false);
		approval.setMtrlAppCode(materiel.getMtrlAppCode());
		approval.setPreStt(materiel.getStatus());
		
		Map<String, Object> variables = new HashMap<String, Object>();
		DealerMaterielStatusEnum status = null;
		String elString = "${!pass}";
		if(approval.getTyp()!=null){
			variables.put("pass", false);
			if(approval.getTyp() == BooleanEnum.YES){
				variables.put("pass", true);
				elString = "${pass}";
			}
		}
		String statusString = workflowService.nextTaskDocumentation(taskId, elString);
		status = Enum.valueOf(DealerMaterielStatusEnum.class, (statusString==null?"":statusString).toUpperCase());
		if(status == null){
			throw new RuntimeException("流程图有误，请检查.");
		}
		
		dealerMateriel.setStatus(status);
		if(status == DealerMaterielStatusEnum.SOYES || status == DealerMaterielStatusEnum.SONO){
			approval.setAction(DealerMaterielActionEnum.APPROVAL);
			if(status == DealerMaterielStatusEnum.SOYES){
				dealerMateriel.setrMtrlCnt(materiel.getrMtrlCnt());
				dealerMateriel.setrMtrlAmt(materiel.getrMtrlCnt() * dealerMateriel.getInfo().getPrice());
			}
		}
		if(status == DealerMaterielStatusEnum.SOSEND){
			dealerMateriel.setGrantDt(new Date());
			dealerMateriel.setTrackNum(materiel.getTrackNum());
			approval.setAction(DealerMaterielActionEnum.SEND);
		}
		if(status == DealerMaterielStatusEnum.CONFIRM){
			approval.setAction(DealerMaterielActionEnum.CONFIRM);
			dealerMateriel.setCofimDt(new Date());
		}
		
		approval.setCrtTime(new Date());
		approval.setUserName(user.getUserId()+"");
		approval.setTrueName(user.getTrueName());
		approval.setMtrlAppCode(materiel.getMtrlAppCode());
		approval.setNextStt(status);
		saveEntity(approval);
		
		workflowService.completeTask(taskId, variables);
	}

	@Override
	public void saveDealerMateriel(CmsDealerMateriel materiel) {
		if(materiel.getMtrlAppCode()!=null){
			updateEntity(materiel, "dealerCode","dealerName","companyName","companyCode","mtrlNm","mtrlCode","remarks","aMtrlCnt","status");
		} else {
			materiel.setAppDt(new Date());
			if(materiel.getStatus()==null){
				materiel.setStatus(DealerMaterielStatusEnum.NEW);
			}
			saveEntity(materiel);
		}
	}

	@Override
	public CmsMaterielExpressHis getAvailExpressHis(CmsMaterielExpressHis his) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.MINUTE, -30);
		
		List<CmsMaterielExpressHis> hiss = getSession().getCriteria(CmsMaterielExpressHis.class).addRestriction(his, OperMode.EQ, "no")
														.add(Restrictions.or(Restrictions.sqlRestriction("crt_time >= str_to_date('"+DateUtil.getDateFormat(c.getTime())+"','%Y-%m-%d %H:%i:%s')")
														,Restrictions.like("recContent","%\\\"status\\\":\\\"1\\\"%")))
														.addOrder(Order.desc("crtTime"))
														.getResultList();
		if(hiss!=null && !hiss.isEmpty()){
			return hiss.get(0);
		}
		return null;
	}

	@Override
	public void updateEndProcess(String taskId, int mtrlAppCode)  throws Exception{
		CmsDealerMateriel materiel = getEntityById(CmsDealerMateriel.class, mtrlAppCode, false);
		CmsDealerMaterielApproval approval = new CmsDealerMaterielApproval();
		approval.setCrtTime(new Date());
		approval.setMtrlAppCode(materiel.getMtrlAppCode());
		approval.setPreStt(materiel.getStatus());
		approval.setRemark("强制结束流程，状态退回.");
		approval.setAction(DealerMaterielActionEnum.FOREND);
		
		materiel.setStatus(DealerMaterielStatusEnum.NEW);
		approval.setNextStt(DealerMaterielStatusEnum.NEW);
		saveEntity(approval);
		workflowService.endProcess(taskId);
	}
}
