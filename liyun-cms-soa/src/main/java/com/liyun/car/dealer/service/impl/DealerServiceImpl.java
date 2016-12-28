package com.liyun.car.dealer.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liyun.car.common.entity.Page;
import com.liyun.car.common.enums.BooleanEnum;
import com.liyun.car.common.enums.OperMode;
import com.liyun.car.dealer.entity.CmsDealer;
import com.liyun.car.dealer.entity.CmsDealerApproval;
import com.liyun.car.dealer.entity.CmsSalesManageInfoHis;
import com.liyun.car.dealer.entity.SyDealer;
import com.liyun.car.dealer.entity.vo.VDealer;
import com.liyun.car.dealer.enums.DealerActionEnum;
import com.liyun.car.dealer.enums.DealerStatusEnum;
import com.liyun.car.dealer.service.DealerService;
import com.liyun.car.hibernate.service.impl.HibernateServiceSupport;
import com.liyun.car.system.entity.SyUser;
import com.liyun.car.system.enums.UserTypeEnum;
import com.liyun.car.workflow.service.WorkflowService;

@Service
public class DealerServiceImpl extends HibernateServiceSupport implements DealerService {

	@Autowired
	private WorkflowService workflowService;
	
	private String genPageHql(VDealer dealer, boolean am, SyUser user, DealerStatusEnum... enums){
		String hql = "select c ";
		if(user.getUserType() == UserTypeEnum.M){
			hql += " from VDealer c where 1=1 ";
			if(am){
				hql += " and c.am = '"+user.getUserId()+"'";
			}
		} else {
			hql += " from VDealer c,SyUserDealer ud where ud.dealer.dealerCode = c.dealerCode and ud.user.userId = "+user.getUserId();
		}
		if(!am && enums!=null){
			String append = "";
			for(DealerStatusEnum dealerStatusEnum : enums){
				append += "'"+dealerStatusEnum.getValue()+"',";
			}
			hql += " and c.status in ("+append.substring(0,append.length()-1)+")";
		}
		if(dealer.getDealerName()!=null && !"".equals(dealer.getDealerName())){
			hql += " and c.dealerName like '%"+dealer.getDealerName()+"%'";
		}
		if(dealer.getDealerCode()!=null){
			hql += " and c.dealerCode = "+dealer.getDealerCode();
		}
		if(dealer.getStatus()!=null){
			hql += " and c.status = '"+dealer.getStatus().getValue()+"'";
		}
		hql += " order by c.updateTime desc";
		return hql;
	}
	
	@Override
	public Page<VDealer> pageList(VDealer dealer, int pn, boolean am, SyUser user, DealerStatusEnum... enums) {
		String hql = genPageHql(dealer, am, user, enums);
		
		return getSession().createQuery(hql).getResultList(pn);
	}

	public List<VDealer> getList(VDealer dc, boolean am, SyUser user, DealerStatusEnum... onOrPreLineStatus){
		String hql = genPageHql(dc, am, user, onOrPreLineStatus);
		
		return getSession().createQuery(hql).getResultList();
	}
	
	private String genSyHql(boolean am, SyUser user){
		String hql = "select c ";
		if(user.getUserType() == UserTypeEnum.M){
			hql += " from SyDealer c where 1=1 ";
			if(am){
				hql += " and c.am = '"+user.getUserId()+"'";
			}
		} else {
			hql += " from SyDealer c,SyUserDealer ud where ud.dealer.dealerCode = c.dealerCode and ud.user.userId = "+user.getUserId();
		}
		hql += " and c.status <> '19' order by c.updateTime desc";
		return hql;
	}
	@Override
	public List<SyDealer> getSyList(boolean am, SyUser user) {
		String hql = genSyHql(am, user);
		return getSession().createQuery(hql).getResultList();
	}
	@Override
	public void updateCmsDealer(CmsDealer dealer) {
		dealer.setUpdateTime(new Date());
		if(dealer.getCompany()!=null){
			dealer.setCompanyCode(dealer.getCompany().getCompanyCode());
			dealer.setCompany(null);
		}
		dealer.setCompanyCode(dealer.getCompanyCode());
		if(dealer.getAm()!=null && dealer.getAm().getUserId()==null){
			dealer.setAm(null);
		}
		this.updateEntity(dealer, "dealerName",
				"companyCode", "accountType", "province", "city", "dealerType",
				"saleArea", "recAccountName", "recAccountCard",
				"recAccountBank2", "recAccountBank", "recAccountNo",
				"bankProvince", "bankCity", "remarks", "am", "status", "updateTime");		
	}

	@Override
	public void saveCmsDealer(CmsDealer dealer, SyUser user) {
		dealer.setCompanyCode(dealer.getCompany().getCompanyCode());
		dealer.setUpdateTime(new Date());
		dealer.setCreateUser(user.getUserId()+"");
		dealer.setStatus(DealerStatusEnum.NEW);
		saveEntity(dealer);
	}

	@Override
	public void saveStartProcess(CmsDealer cmsDealer, SyDealer syDealer, CmsDealerApproval approval, SyUser user) {
		String businessKey = "";
		DealerStatusEnum preStatus = null;
		DealerStatusEnum nextStauts = null;
		//门店下线
		if(syDealer!=null){
			syDealer.setDealerCode(approval.getCode());
			nextStauts = DealerStatusEnum.OFFLINEPREDEPTLEADER;
			preStatus = DealerStatusEnum.ONLINE;
			businessKey = syDealer.getDealerCode().toString();

			syDealer.setStatus(nextStauts);
			this.updateEntity(syDealer, "status");
			
		} 
		//门店上线
		if(cmsDealer!=null){
			businessKey = cmsDealer.getDealerCode().toString();
			preStatus = DealerStatusEnum.NEW;
			nextStauts = DealerStatusEnum.ONLINEPREDEPTLEADER;
			
			cmsDealer.setUpdateTime(new Date());
			cmsDealer.setStatus(nextStauts);
			cmsDealer.setAm(user);
			//cmsDealer.setCompany(null);
			updateCmsDealer(cmsDealer);
		}
		
		
		approval.setCode(Integer.parseInt(businessKey));
		approval.setCrtTime(new Date());
		approval.setPreStt(preStatus);
		approval.setNextStt(nextStauts);
		approval.setUserName(user.getUserId()+"");
		approval.setTrueName(user.getTrueName());
		
		Map<String, Object> variables = new HashMap<String, Object>();
		//保存修改并启动流程
		
		approval.setAction(DealerActionEnum.DONLINE);
		this.saveEntity(approval);
		workflowService.startProcess(businessKey, VDealer.FLOW_NAME, user.getUserId()+"", variables);
	}

	private void saveSalesManageHis(CmsSalesManageInfoHis his, SyDealer dealer) {

		SyUser user = getSession().find(SyUser.class, dealer.getAm().getUserId());
		his.setStartTime(new Date());
		his.setManageDealer(dealer.getDealerCode());
		his.setSalesId(user.getUserId());
		his.setSalesName(user.getUserName());
		his.setPost(user.getUserDeparment().getUserPostion());
		
		getSession().persist(his);
	}
	
	private void mergeSalesManageHis(SyDealer dealer){
		CmsSalesManageInfoHis his = new CmsSalesManageInfoHis(null, dealer.getDealerCode());
		List<CmsSalesManageInfoHis> hiss = this.getEntitysByParams(his, OperMode.EQ, "ManageDealer");
		SyDealer dealerDB = this.getEntityById(SyDealer.class, dealer.getDealerCode(), false);
		if(dealerDB!=null && dealerDB.getAm() != null ){
			if(dealer.getAm()!=null){
				if(dealerDB.getAm().getUserId()!=dealer.getAm().getUserId()){
					if(hiss!=null && hiss.size()!=0){
						hiss.get(0).setEndTime(new Date());
					}
					saveSalesManageHis(his, dealer);
				}
			} else {
				if(hiss!=null && hiss.size()!=0){
					hiss.get(0).setEndTime(new Date());
				}
			}
		} else {
			if(dealer.getAm()!=null){
				saveSalesManageHis(his, dealer);
			}
		}
	}
	
	@Override
	public void saveSyDealer(SyDealer dealer, Integer code, SyUser user) {
		if(dealer.getAm()!=null && dealer.getAm().getUserId()==null){
			dealer.setAm(null);
		}
		dealer.setUpdateTime(new Date());
		if(dealer.getDealerCode().intValue() == code){
			mergeSalesManageHis(dealer);
			this.updateEntity(dealer, "dealerName","company", "accountType", "province", "city", "dealerType",
					"saleArea", "recAccountName", "recAccountCard",
					"recAccountBank2", "recAccountBank", "recAccountNo",
					"bankProvince", "bankCity", "remarks", "am","updateTime");
		} else {
			SyDealer syDealer = getEntityById(SyDealer.class, code, false);
			if(syDealer!=null){
				throw new RuntimeException("门店编码重复.");
			}
			dealer.setTempDealerCode(code);
			if(dealer.getAm()!=null){
				mergeSalesManageHis(dealer);
			}
			getSession().persist(dealer);
			
			CmsDealer cmsDealer = new CmsDealer(code, DealerStatusEnum.OFFLINE);
			this.updateEntity(cmsDealer, "status");
		}
	}

	@Override
	public void deleteCmsDealer(CmsDealer dealer) {
		dealer = getEntityById(CmsDealer.class, dealer.getDealerCode(), true);
		dealer.getDealerFiles().clear();
		
		workflowService.endProcess(dealer.getDealerCode()+"", VDealer.FLOW_NAME );
		deleteEntity(dealer);
	}

	@Override
	public void saveCompleteTask(VDealer vDealer, String taskId, CmsDealerApproval approval, SyUser user) {
		approval.setCode(vDealer.getDealerCode());
    	approval.setAction(DealerActionEnum.DONLINE);
    	
		DealerStatusEnum dealerStatusEnum = null;
		Map<String, Object> variables = new HashMap<String, Object>();

		String elString = "${!pass}";
		if(approval.getTyp()!=null){
			variables.put("pass", false);
			if(approval.getTyp() == BooleanEnum.YES){
				variables.put("pass", true);
				elString = "${pass}";
			}
		}
		String statusString = workflowService.nextTaskDocumentation(taskId, elString);
		dealerStatusEnum = Enum.valueOf(DealerStatusEnum.class, (approval.getPreStt().getOnLineFlag().equals("1")?"ONLINE":"OFFLINE")+(statusString==null?"":statusString).toUpperCase());
		if(dealerStatusEnum == null){
			throw new RuntimeException("流程图有误，请检查.");
		}
		approval.setAction(DealerActionEnum.CONLINE);
		approval.setNextStt(dealerStatusEnum);
		approval.setCrtTime(new Date());
		approval.setUserName(user.getUserId()+"");
		approval.setTrueName(user.getTrueName());
        saveEntity(approval);
        
        if(approval.getPreStt().getOnLineFlag().equals("1") && approval.getPreStt() != DealerStatusEnum.ONLINEPRE){
        	CmsDealer cmsDealer = new CmsDealer(approval.getCode(),dealerStatusEnum);
        	updateEntity(cmsDealer, "status");
        }  else {
        	SyDealer syDealer = new SyDealer(approval.getCode(),dealerStatusEnum);
        	updateEntity(syDealer, "status");
        }

        workflowService.completeTask(taskId, variables);
	}

	@Override
	public List<String> getDealerTree() {
		List<String> treeList = new ArrayList<String>();
		treeList.add("{id:\"999999\",pId:\"0\",name:\"经销商列表\",isParent:true}");
		DealerStatusEnum[] statusEnums = DealerStatusEnum.getOnlineStatus();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", statusEnums);
		List<SyDealer> list = getEntitysByParamMap(SyDealer.class, OperMode.IN, map);
		
		Map<String,String> cityMap = new HashMap<String, String>();
		int i = 1000000;
		if(list!=null && !list.isEmpty()){
			for(SyDealer dealer : list){
				String province = ((dealer.getProvince()==null||dealer.getProvince().equals(""))?"其它":dealer.getProvince());
				if(!cityMap.containsKey(province)){
					i++;
					cityMap.put(province, i+"");
					treeList.add("{id:\""+i+"\",pId:\"999999\",name:\""+province+"\"}");
				}
				
				treeList.add("{id:\""+dealer.getDealerCode()+"\",pId:\""+map.get(province)+"\",name:\""+dealer.getDealerName()+"\"}");
			}
		}
		return treeList;
	}
	
	@Override
	public void updateEndProcess(String taskId, Integer dealerCode) throws Exception {
		CmsDealerApproval approval = new CmsDealerApproval();
		VDealer dealer = getEntityById(VDealer.class, dealerCode, false);
		approval.setCode(dealerCode);
		approval.setCrtTime(new Date());
		approval.setPreStt(dealer.getStatus());
		approval.setAction(DealerActionEnum.CONLINE);
		approval.setRemark("强制结束流程，状态退回.");
		
		DealerStatusEnum nextStatus = null ;
		if(dealer.getStatus().getOnLineFlag().equals("1")){
			if(dealer.getStatus() == DealerStatusEnum.ONLINEPRE && dealer.getCompanyCode().toString().length()==5){
				throw new RuntimeException("该经销商已经补充经销商代码，不允许强制结束流程.");
			} else {
				CmsDealer cmsDealer = getEntityById(CmsDealer.class, dealerCode, false);
				cmsDealer.setStatus(DealerStatusEnum.NEW);
				nextStatus = DealerStatusEnum.NEW;
			}
		} else {
			SyDealer syDealer = getEntityById(SyDealer.class, dealerCode, false);
			syDealer.setStatus(DealerStatusEnum.ONLINE);
			nextStatus = DealerStatusEnum.ONLINE;
		}
		
		approval.setNextStt(nextStatus);
		saveEntity(approval);
		workflowService.endProcess(taskId);
	}

}
