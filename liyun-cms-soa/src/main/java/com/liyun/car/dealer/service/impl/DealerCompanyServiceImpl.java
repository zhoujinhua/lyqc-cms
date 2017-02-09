package com.liyun.car.dealer.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liyun.car.common.entity.Page;
import com.liyun.car.common.enums.BooleanEnum;
import com.liyun.car.common.enums.OperMode;
import com.liyun.car.dealer.entity.CmsAmDealerCompany;
import com.liyun.car.dealer.entity.CmsDealer;
import com.liyun.car.dealer.entity.CmsDealerApproval;
import com.liyun.car.dealer.entity.CmsDealerCompany;
import com.liyun.car.dealer.entity.CmsDealerFile;
import com.liyun.car.dealer.entity.SyAmDealerCompany;
import com.liyun.car.dealer.entity.SyDealer;
import com.liyun.car.dealer.entity.SyDealerCompany;
import com.liyun.car.dealer.entity.vo.VDealerCompany;
import com.liyun.car.dealer.enums.BillPayStatusEnum;
import com.liyun.car.dealer.enums.DealerActionEnum;
import com.liyun.car.dealer.enums.DealerStatusEnum;
import com.liyun.car.dealer.enums.DealerTypeEnum;
import com.liyun.car.dealer.service.DealerCompanyService;
import com.liyun.car.dealer.service.DealerService;
import com.liyun.car.hibernate.service.impl.HibernateServiceSupport;
import com.liyun.car.system.entity.SyUser;
import com.liyun.car.system.enums.UserTypeEnum;
import com.liyun.car.workflow.service.WorkflowService;

@Service
public class DealerCompanyServiceImpl extends HibernateServiceSupport implements DealerCompanyService {

	@Autowired
	private WorkflowService workflowService;
	
	@Autowired
	private DealerService dealerService;
	
	private String genPageHql(VDealerCompany company, boolean am, SyUser user,
			DealerStatusEnum... enums){
		String hql = "select c";
		if(user.getUserType() == UserTypeEnum.M){
			if(am){
				hql += " from VDealerCompany c,VAmDealerCompany ac where ac.userId="+user.getUserId()+" and ac.companyCode=c.companyCode";
				if(enums != null){
					String append = "";
					for(DealerStatusEnum dealerStatusEnum : enums){
						append += "'"+dealerStatusEnum.getValue()+"',";
					}
					hql += " and c.status in ("+append.substring(0,append.length()-1)+")";
				}
			} else {
				hql += " from VDealerCompany c where 1=1 ";
				String append = "";
				for(DealerStatusEnum dealerStatusEnum : DealerStatusEnum.getOnOrPreLineStatus()){
					append += "'"+dealerStatusEnum.getValue()+"',";
				}
				hql += " and c.status in ("+append.substring(0,append.length()-1)+")";
			}
		} else{
			hql += " from VDealerCompany c,VUserRole vu where c.companyCode = vu.orgCode and vu.userId="+user.getUserId();
		}
		
		if(company.getCompanyName()!=null && !"".equals(company.getCompanyName())){
			hql += " and c.companyName like '%"+company.getCompanyName()+"%'";
		}
		if(company.getCompanyCode()!=null){
			hql += " and c.companyCode = '"+company.getCompanyCode()+"'";
		}
		if(company.getStatus()!=null){
			hql += " and c.status = '"+company.getStatus().getValue()+"'";
		}
		hql += " order by c.updateTime desc" ;
		return hql;
	}
	private String genSyHql(boolean am, SyUser user){
		String hql = "select c";
		if(user.getUserType() == UserTypeEnum.M){
			if(am){
				hql += " from SyDealerCompany c,SyAmDealerCompany ac where ac.userId="+user.getUserId()+" and ac.company.companyCode=c.companyCode";
				hql += " and c.status <> '19' and status <> '16'";
			} else {
				hql += " from SyDealerCompany c where c.status <> '19' ";
			}
		} else{
			hql += " from SyDealerCompany c,VUserRole vu where c.companyCode = vu.orgCode and vu.userId="+user.getUserId();
		}
		
		hql += " order by c.updateTime desc" ;
		return hql;
	}
	
	@Override
	public Page<VDealerCompany> pageList(VDealerCompany company, int pn, boolean am, SyUser user,
			DealerStatusEnum... enums) {
		String hql = this.genPageHql(company, am, user, enums);
		
		return getSession().createQuery(hql).getResultList(pn);
	}
	
	@Override
	public Page<SyDealerCompany> pageSyList(int pn ) {
		String hql = "select c from SyDealerCompany c where c.status <> '19'";
		
		return getSession().createQuery(hql).getResultList(pn);
	}
	
	@Override
	public List<SyDealerCompany> getSyList(boolean am, SyUser user){
		String hql = genSyHql(am, user);
		return getSession().createQuery(hql).getResultList();
	}
	
	@Override
	public List<VDealerCompany> getList(VDealerCompany company, boolean am, SyUser user,
			DealerStatusEnum... enums){
		String hql = this.genPageHql(company, am, user, enums);
		return getSession().createQuery(hql).getResultList();
	}

	@Override
	public void updateCmsDealerCompany(CmsDealerCompany dealerCompany, SyUser user) {
		dealerCompany.setUpdateTime(new Date());
		this.updateEntity(dealerCompany, "companyName","accountType","province","city","companyType","appNo","isVip"
				,"saleArea","accountName","accountIdno","accountBank","accountSubBank","bankProvince","bankCity","accountNo"
				,"remarks","status","updateTime");
		
		CmsDealerCompany cmsDealerCompany = getEntityById(CmsDealerCompany.class, dealerCompany.getCompanyCode(), true);
		cmsDealerCompany.getAmDealerCompanys().clear();
		cmsDealerCompany.getDealers().clear();

		boolean flag = true;
		if(dealerCompany.getAmUserIds()!=null && !"".equals(dealerCompany.getAmUserIds())){
			String[] userIds = dealerCompany.getAmUserIds().split(",");
			String[] userNames = dealerCompany.getAmUserNames().split(",");
			
			for(int i=0;i<userIds.length;i++){
				if(user!=null && userIds[i].equals(user.getUserId()+"")){
					flag = false;
				}
				CmsAmDealerCompany cmsAmDealerCompany = new CmsAmDealerCompany();
				cmsAmDealerCompany.setUserId(Integer.parseInt(userIds[i]));
				cmsAmDealerCompany.setUserName(userNames[i]);
				cmsAmDealerCompany.setCompany(dealerCompany);
				if(dealerCompany.getAmDealerCompany()!=null && dealerCompany.getAmDealerCompany().getUserId()!=null && dealerCompany.getAmDealerCompany().getUserId() == Integer.parseInt(userIds[i])){
					cmsAmDealerCompany.setIsPrimary(BooleanEnum.YES);
				} else {
					cmsAmDealerCompany.setIsPrimary(BooleanEnum.NO);
				}
				cmsDealerCompany.addAmDealerCompanys(cmsAmDealerCompany);
			}
		}
		if(flag &&user!=null){
			CmsAmDealerCompany cmsAmDealerCompany = new CmsAmDealerCompany();
			cmsAmDealerCompany.setUserId(user.getUserId());
			cmsAmDealerCompany.setUserName(user.getTrueName());
			cmsAmDealerCompany.setIsPrimary(BooleanEnum.NO);
			cmsDealerCompany.addAmDealerCompanys(cmsAmDealerCompany);
		}
		
		if(dealerCompany.getDealers()!=null && !dealerCompany.getDealers().isEmpty()){
			for(CmsDealer cmsDealer : dealerCompany.getDealers()){
				cmsDealer.setCompanyCode(dealerCompany.getCompanyCode());
				cmsDealer.setStatus(DealerStatusEnum.NEW);
				cmsDealer.setCreateUser(user.getUserId()+"");
				cmsDealer.setUpdateTime(new Date());
				
				cmsDealerCompany.addCmsDealer(cmsDealer);
			}
		}
	}
	public void updateBatchSyDealerCompany(SyDealerCompany company,SyUser user){
		company.setUpdateTime(new Date());
		updateEntity(company, "companyName","accountType","province","city","companyType","appNo","isVip"
				,"saleArea","accountName","accountIdno","accountBank","accountSubBank","bankProvince","bankCity","accountNo"
				,"remarks","status","isDeposit","depositAmt","updateTime");
		
		SyDealerCompany dealerCompany = getEntityById(SyDealerCompany.class, company.getCompanyCode(), true);
		dealerCompany.getAmDealerCompanys().clear();
		
		if(company.getAmUserIds()!=null && !"".equals(company.getAmUserIds())){
			String[] userIds = company.getAmUserIds().split(",");
			String[] userNames = company.getAmUserNames().split(",");
			
			for(int i=0;i<userIds.length;i++){
				SyAmDealerCompany syAmDealerCompany = new SyAmDealerCompany();
				syAmDealerCompany.setCompany(company);
				syAmDealerCompany.setUserId(Integer.parseInt(userIds[i]));
				syAmDealerCompany.setUserName(userNames[i]);
				if(company.getAmDealerCompany()!=null && company.getAmDealerCompany().getUserId()!=null && company.getAmDealerCompany().getUserId() == Integer.parseInt(userIds[i])){
					syAmDealerCompany.setIsPrimary(BooleanEnum.YES);
				} else {
					syAmDealerCompany.setIsPrimary(BooleanEnum.NO);
				}
				dealerCompany.addAmDealerCompanys(syAmDealerCompany);
			}
		}
	}
	@Override
	public void saveCmsDealerCompany(CmsDealerCompany dealerCompany, SyUser user) {
		dealerCompany.setStatus(DealerStatusEnum.NEW);
		dealerCompany.setUpdateTime(new Date());
		saveEntity(dealerCompany);
		boolean flag = true;
		if(dealerCompany.getAmUserIds()!=null && !"".equals(dealerCompany.getAmUserIds())){
			String[] userIds = dealerCompany.getAmUserIds().split(",");
			String[] userNames = dealerCompany.getAmUserNames().split(",");
			
			for(int i=0;i<userIds.length;i++){
				if(user!=null && userIds[i].equals(user.getUserId()+"")){
					flag = false;
				}
				CmsAmDealerCompany syAmDealerCompany = new CmsAmDealerCompany();
				syAmDealerCompany.setCompany(dealerCompany);
				syAmDealerCompany.setUserId(Integer.parseInt(userIds[i]));
				syAmDealerCompany.setUserName(userNames[i]);
				if(dealerCompany.getAmDealerCompany().getUserId() == Integer.parseInt(userIds[i])){
					syAmDealerCompany.setIsPrimary(BooleanEnum.YES);
				} else {
					syAmDealerCompany.setIsPrimary(BooleanEnum.NO);
				}
				syAmDealerCompany.setCompany(dealerCompany);
				
			}
		}
		//经销商上线将自己默认添加到am中,后续的查询是根据am查询的，更新也要这么做
		if(flag&&user!=null){
			CmsAmDealerCompany syAmDealerCompany = new CmsAmDealerCompany();
			syAmDealerCompany.setCompany(dealerCompany);
			syAmDealerCompany.setUserId(user.getUserId());
			syAmDealerCompany.setUserName(user.getTrueName());
			syAmDealerCompany.setIsPrimary(BooleanEnum.NO);
			syAmDealerCompany.setCompany(dealerCompany);
			
			saveEntity(syAmDealerCompany);
		}
		
		if(dealerCompany.getDealers()!=null && !dealerCompany.getDealers().isEmpty()){
			for(CmsDealer cmsDealer : dealerCompany.getDealers()){
				cmsDealer.setStatus(DealerStatusEnum.NEW);
				cmsDealer.setCreateUser(user.getUserId()+"");
				cmsDealer.setUpdateTime(new Date());
				cmsDealer.setCompanyCode(dealerCompany.getCompanyCode());
			}
		}

	}

	@Override
	public void saveStartProcess(CmsDealerCompany dealerCompany, SyDealerCompany syDealerCompany, CmsDealerApproval approval, SyUser user) {
		String businessKey = "";
		DealerStatusEnum preStatus = null;
		DealerStatusEnum nextStauts = null;
		//门店下线
		if(syDealerCompany!=null){
			syDealerCompany.setCompanyCode(approval.getCode());
			nextStauts = DealerStatusEnum.OFFLINEPREDEPTLEADER;
			preStatus = DealerStatusEnum.ONLINE;
			businessKey = syDealerCompany.getCompanyCode().toString();

			syDealerCompany.setStatus(nextStauts);
			this.updateEntity(syDealerCompany, "status");
		} 
		//门店上线
		if(dealerCompany!=null){
			businessKey = dealerCompany.getCompanyCode().toString();
			preStatus = DealerStatusEnum.NEW;
			nextStauts = DealerStatusEnum.ONLINEPREDEPTLEADER;
			
			dealerCompany.setUpdateTime(new Date());
			dealerCompany.setStatus(nextStauts);
			updateCmsDealerCompany(dealerCompany,user);
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
		workflowService.startProcess(businessKey, VDealerCompany.FLOW_NAME, user.getUserId()+"", variables);
	}

	@Override
	public void saveSyDealerCompany(SyDealerCompany company, Integer code, SyUser user) {
		if(company.getCompanyCode().intValue() == code.intValue()){
			updateBatchSyDealerCompany(company, user);
		} else {
			SyDealerCompany syDealerCompany = getEntityById(SyDealerCompany.class, company.getCompanyCode(), true);
			if(syDealerCompany!=null){
				throw new RuntimeException("已存在相同经销商编码.");
			}
			company.setTempCompanyCode(code);
			company.setUpdateTime(new Date());
			saveEntity(company);
			if(company.getAmUserIds()!=null && !"".equals(company.getAmUserIds())){
				String[] userIds = company.getAmUserIds().split(",");
				//String[] userNames = company.getAmUserNames().split(",");
				
				for(int i=0;i<userIds.length;i++){
					SyAmDealerCompany syAmDealerCompany = new SyAmDealerCompany();
					syAmDealerCompany.setCompany(company);
					syAmDealerCompany.setUserId(Integer.parseInt(userIds[i]));
					//syAmDealerCompany.setUserName(userNames[i]);
					if(company.getAmDealerCompany().getUserId() == Integer.parseInt(userIds[i])){
						syAmDealerCompany.setIsPrimary(BooleanEnum.YES);
					} else {
						syAmDealerCompany.setIsPrimary(BooleanEnum.NO);
					}
					saveEntity(syAmDealerCompany);
				}
			}
			if(company.getDealers()!=null && !company.getDealers().isEmpty()){
				for(SyDealer dealer : company.getDealers()){
					//SyDealer syDealer = dealerService.getEntityById(SyDealer.class, dealer.getDealerCode(), false);
					/*if(syDealer!=null){
						throw new RuntimeException("存在相同的门店编码.");
					}*/
					if(dealer.getAm().getUserId()==null){
						dealer.setAm(null);
					}
					dealer.setAccountType(company.getAccountType());
					dealer.setStatus(DealerStatusEnum.ONLINEPRE);
					dealer.setCompany(company);
					dealer.setCreateUser(user.getUserId()+"");
					dealer.setUpdateTime(new Date());
				}
			}
			
			//销毁
			onOffLineBatch(new CmsDealerCompany(code, null), DealerStatusEnum.OFFLINE);
		}
	}

	@Override
	public CmsDealerFile saveCmsDealerFile(CmsDealerFile file) {
		List<CmsDealerFile> cdfs = this.getEntitysByParams(file, OperMode.EQ, "code","fileType");
		if(cdfs!=null && !cdfs.isEmpty()){
			cdfs.get(0).setCrtTime(new Date());
			cdfs.get(0).setFileDir(file.getFileDir());
			cdfs.get(0).setFileName(file.getFileName());
			
			return cdfs.get(0);
		} else {
			getSession().persist(file);
			return file;
		}
	}
	
	@Override
	public void deleteCmsDealerCompany(CmsDealerCompany dealerCompany) {
		dealerCompany = getEntityById(CmsDealerCompany.class, dealerCompany.getCompanyCode(), true);
		dealerCompany.getAmDealerCompanys().clear();
		dealerCompany.getDealerFiles().clear();
		dealerCompany.getDealers().clear();
		
		workflowService.endProcess(dealerCompany.getCompanyCode()+"", VDealerCompany.FLOW_NAME );
		deleteEntity(dealerCompany);
	}

	@Override
	public void saveCompleteTask(VDealerCompany dealerCompany, String taskId, CmsDealerApproval approval, SyUser user) {
		String role = user.getUserDeparment().getUserPostion();
		int companyCode = dealerCompany.getCompanyCode();
		DealerStatusEnum nextStatus = null;
		Map<String, Object> variables = new HashMap<String, Object>();

		String elString = getActivitiElString(approval, role, variables, companyCode);
		String status = workflowService.nextTaskDocumentation(taskId, elString);
		nextStatus = Enum.valueOf(DealerStatusEnum.class, (approval.getPreStt().getOnLineFlag().equals("1")?"ONLINE":"OFFLINE")+(status==null?"":status).toUpperCase());
		if(nextStatus == null){
			throw new RuntimeException("流程图有误，请检查.");
		}
		
		if(nextStatus == DealerStatusEnum.ONLINE){
			if(approval.getPreStt().getOnLineFlag().equals("1")){
				onOffLineBatch(companyCode, DealerStatusEnum.ONLINE);
			} else {
				onOffLineBatch(companyCode, DealerStatusEnum.OFFLINE);
			}
		} 
		approval.setAction(DealerActionEnum.CONLINE);
		approval.setNextStt(nextStatus);
		approval.setCrtTime(new Date());
		approval.setUserName(user.getUserId()+"");
		approval.setTrueName(user.getTrueName());
        getSession().persist(approval);
        
        workflowService.completeTask(taskId, variables);
        //更新company
        if(approval.getPreStt().getOnLineFlag().equals("1") && approval.getPreStt()!=DealerStatusEnum.ONLINEPRE){
        	CmsDealerCompany cmsDealerCompany = new CmsDealerCompany(companyCode,nextStatus);
        	getSession().update(cmsDealerCompany,"status");
        }
        //更新syCompany
        if(!approval.getPreStt().getOnLineFlag().equals("1")){
        	SyDealerCompany syDealerCompany = new SyDealerCompany(companyCode,nextStatus);
        	getSession().update(syDealerCompany,"status");
        }
	}
	
	private String getActivitiElString(CmsDealerApproval approval, String role, Map<String, Object> variables, int companyCode){
		String elString = "${!pass}";
		if(approval.getTyp()!=null){
			variables.put("pass", false);
			variables.put("online", true);
			variables.put("sp", true);
			if("SO".equals(role)){
				VDealerCompany vDealerCompany = new VDealerCompany(companyCode,companyCode);
				vDealerCompany = getSession().getCriteria(VDealerCompany.class).addRestriction(vDealerCompany, OperMode.OR, "companyCode","tempCompanyCode").getSingleResult();
				if(approval.getTyp() == BooleanEnum.YES){
					variables.put("pass", true);
					if(vDealerCompany.getCompanyType() == DealerTypeEnum.SP && approval.getPreStt().getOnLineFlag().equals("1")){
						elString = "${pass&&sp&&online}";
					} else {
						if(vDealerCompany.getCompanyType() != DealerTypeEnum.SP){
							variables.put("sp", false);
						}
						if(!approval.getPreStt().getOnLineFlag().equals("1")){
							variables.put("online", false);
						}
						elString = "${(pass&&!sp&&online)||(pass&&!online)}";
					}
				}
			} else {
				if(approval.getTyp() == BooleanEnum.YES){
					variables.put("pass", true);
					elString = "${pass}";
				}
			}
		}
		return elString;
	}
	public void onOffLineBatch(Integer companyCode,DealerStatusEnum dealerStatusEnum){
		SyDealerCompany syDealerCompany = new SyDealerCompany(companyCode, dealerStatusEnum);
		syDealerCompany.setOnlineTime(new Date());
    	getSession().update(syDealerCompany, "status","onlineTime");
    	
    	@SuppressWarnings("unchecked")
		List<SyDealer> syDealers = getSession().createQuery("from SyDealer where company.companyCode = "+companyCode).getResultList();
    	if(syDealers!=null && !syDealers.isEmpty()){
			for(SyDealer dealer : syDealers){
				dealer.setStatus(dealerStatusEnum);
			}
		}
	}
	//停用cms表的经销商和同时上线的门店
	public void onOffLineBatch(CmsDealerCompany cmsDealerCompany,DealerStatusEnum dealerStatusEnum){
		cmsDealerCompany = getEntityById(CmsDealerCompany.class, cmsDealerCompany.getCompanyCode(), false);
		cmsDealerCompany.setStatus(dealerStatusEnum);
		
    	if(cmsDealerCompany.getDealers()!=null && !cmsDealerCompany.getDealers().isEmpty()){
			for(CmsDealer dealer : cmsDealerCompany.getDealers()){
				dealer.setStatus(dealerStatusEnum);
			}
		}
	}

	@Override
	public void updateSuspendBill(SyDealerCompany dc) {
		dc = getEntityById(SyDealerCompany.class, dc.getCompanyCode(), false);
		if(dc.getPayStt() == BillPayStatusEnum.STFF){
			dc.setPayStt(BillPayStatusEnum.NMFF);
		} else {
			dc.setPayStt(BillPayStatusEnum.STFF);
		}
	}

	@Override
	public void updateEndProcess(String taskId, Integer companyCode) throws Exception {
		CmsDealerApproval approval = new CmsDealerApproval();
		VDealerCompany company = getEntityById(VDealerCompany.class, companyCode, false);
		approval.setCode(companyCode);
		approval.setCrtTime(new Date());
		approval.setPreStt(company.getStatus());
		approval.setAction(DealerActionEnum.CONLINE);
		approval.setRemark("强制结束流程，状态退回.");
		
		if(company.getStatus().getOnLineFlag().equals("1")){
			if(company.getStatus() == DealerStatusEnum.ONLINEPRE && company.getCompanyCode().toString().length()==5){
				throw new RuntimeException("该经销商已经补充经销商代码，不允许强制结束流程.");
			} else {
				CmsDealerCompany cmsDealerCompany = getEntityById(CmsDealerCompany.class, companyCode, false);
				cmsDealerCompany.setStatus(DealerStatusEnum.NEW);
				
				approval.setNextStt(DealerStatusEnum.NEW);
				updateEntity(cmsDealerCompany, "status");
			}
		} else {
			SyDealerCompany syDealerCompany = getEntityById(SyDealerCompany.class, companyCode, false);
			syDealerCompany.setStatus(DealerStatusEnum.ONLINE);
			
			approval.setNextStt(DealerStatusEnum.ONLINE);
			updateEntity(syDealerCompany, "status");
		}
		
		saveEntity(approval);
		workflowService.endProcess(taskId);
	}
}
