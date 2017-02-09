package com.liyun.car.fee.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.hibernate.Hibernate;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liyun.car.common.entity.Page;
import com.liyun.car.common.enums.BooleanEnum;
import com.liyun.car.common.enums.OperMode;
import com.liyun.car.common.enums.ParamStatusEnum;
import com.liyun.car.common.utils.DateUtil;
import com.liyun.car.common.utils.PropertyUtil;
import com.liyun.car.dealer.entity.SyDealerCompany;
import com.liyun.car.dealer.enums.BillPayStatusEnum;
import com.liyun.car.dealer.service.DealerCompanyService;
import com.liyun.car.fee.entity.CmsDealerSerfeeFlow;
import com.liyun.car.fee.entity.CmsDealerSerfeeMonBill;
import com.liyun.car.fee.entity.CmsDealerSerfeeMonBillFlow;
import com.liyun.car.fee.entity.CmsSerfeeMonInfo;
import com.liyun.car.fee.enums.SerfeeActionEnum;
import com.liyun.car.fee.enums.SerfeeBillActionEnum;
import com.liyun.car.fee.enums.SerfeeBillStatusEnum;
import com.liyun.car.fee.enums.SerfeeStatusEnum;
import com.liyun.car.fee.service.DealerSerfeeMonBillService;
import com.liyun.car.fee.service.DealerSerfeeMonFileService;
import com.liyun.car.fee.service.utils.BillSendUtil;
import com.liyun.car.hibernate.hibernate.CrapCriteria;
import com.liyun.car.hibernate.service.impl.HibernateServiceSupport;
import com.liyun.car.param.entity.SyMailSender;
import com.liyun.car.param.entity.SyModel;
import com.liyun.car.system.entity.SyEmailLog;
import com.liyun.car.system.entity.SyUser;
import com.liyun.car.system.enums.UserTypeEnum;
import com.liyun.car.system.utils.FileUtils;

@Service
public class DealerSerfeeMonBillServiceImpl extends HibernateServiceSupport implements DealerSerfeeMonBillService {

	private static Logger logger = LoggerFactory.getLogger(DealerSerfeeMonBillServiceImpl.class);
	@Autowired
	private DealerSerfeeMonFileService dealerSerfeeMonFileService;
	public static ExecutorService pool = null;
	
	
	@Autowired
	private DealerCompanyService dealerCompanyService;
	
	private String genPageHql(CmsDealerSerfeeMonBill bill, boolean am, SyUser user){
		StringBuffer hql = new StringBuffer("select c ");
		SerfeeBillStatusEnum[] status = null;
		//经销商用户 查个人账单
		if(user.getUserType() == UserTypeEnum.DS){
			hql.append("from CmsDealerSerfeeMonBill c,VUserRole vc where vc.orgCode = c.companyCode and" +
					" vc.userId="+user.getUserId());
			status = SerfeeBillStatusEnum.getBillOwnerBillStatus();
		}
		//如果是美丽用户，要区分是否是AM，AM只可以查看自己名下的经销商账单明细
		if(user.getUserType() == UserTypeEnum.M){
			if(!am){
				hql.append("from CmsDealerSerfeeMonBill c where 1 = 1 ");
				status = SerfeeBillStatusEnum.getBillManagBillStatus();
			} else {
				hql.append(" from CmsDealerSerfeeMonBill c,SyAmDealerCompany sd where c.companyCode = sd.company.companyCode and sd.userId="+user.getUserId());
				status = SerfeeBillStatusEnum.getBillOwnerBillStatus();
			}
		}
		if(!getStatusFormat(status).equals("")){
			hql.append(" and c.stt in "+getStatusFormat(status));
		}
		if(bill!=null){
			if(bill.getFeeMon()!=null && !"".equals(bill.getFeeMon())){
				hql.append(" and c.feeMon='"+bill.getFeeMon()+"'");
			}
			if(bill.getCompanyCode()!=null && !"".equals(bill.getCompanyCode())){
				hql.append(" and c.companyCode='"+bill.getCompanyCode()+"'");
			}
			if(bill.getCompanyName()!=null && !"".equals(bill.getCompanyName())){
				hql.append(" and c.companyName='"+bill.getCompanyName()+"'");
			}
		}
		return hql.toString();
	}
	
	private String getStatusFormat(SerfeeBillStatusEnum[] status){
		if(status != null && status.length != 0){
			String stts = "(";
			for (SerfeeBillStatusEnum serfeeBillStatusEnum : status) {
				stts += "'"+serfeeBillStatusEnum.getValue()+"',";
			}
			return stts.substring(0,stts.length()-1) + ")";
		}
		return "";
	}
	
	@Override
	public Page<CmsDealerSerfeeMonBill> pageList(CmsDealerSerfeeMonBill bill, boolean am, SyUser user, int pn) {
		String hql = genPageHql(bill, am, user);
		return getSession().createQuery(hql).getResultList(pn);
	}

	@Override
	public void sendBills(String feeMons, CmsDealerSerfeeMonBill bill, SyUser user) {
		String[] feeMonArray = feeMons.split(",");
		for(String feeMon : feeMonArray){
			CmsSerfeeMonInfo info = getEntityByCode(CmsSerfeeMonInfo.class, feeMon, false);
			if(info == null){
				throw new RuntimeException(feeMon + "服务费批次不存在.");
			}
			if(info.getStt() != SerfeeStatusEnum.CWYES){
				throw new RuntimeException("邮件发送失败，服务费批次必须财务确认后才可以进行账单发送操作.");
			}
		}
		
		SyMailSender mailSender = new SyMailSender(null, ParamStatusEnum.ON);
		List<SyMailSender> mailSenders = getEntitysByParams(mailSender, OperMode.EQ, "status");
		if(mailSenders==null || mailSenders.isEmpty()){
			throw new RuntimeException("发送邮件前请添加发件人账户.");
		}
		SyModel model = new SyModel();
		model.setCode("serfee.model");
		List<SyModel> models = getEntitysByParams(model, OperMode.EQ, "code");
		if(models!=null && !models.isEmpty()){
			model = models.get(0);
		}
		String batchNo = DateUtil.getgetDateFormat5(new Date());
		int pn = 1;
		pool = Executors.newFixedThreadPool(10);
		
		controller(feeMons, bill.getCompanyCode(), user, pn, model, mailSenders, batchNo);
	}
	
	/**
	 * 节拍器
	 */
	public void controller(String feeMons, Integer companyCode, SyUser user, int pn, SyModel model, List<SyMailSender> mailSenders, String batchNo){
		if(companyCode!=null && !"".equals(companyCode)){  //申请单个发送
			SyDealerCompany company = getEntityById(SyDealerCompany.class, companyCode, false);
			pool.execute(new BillSendUtil(company,model,feeMons,user,mailSenders,batchNo));
		} else {
			Page<SyDealerCompany> page = dealerCompanyService.pageSyList(pn); //获取经销商列表
			
			if(page!=null && page.getItems()!=null && !page.getItems().isEmpty()){
				for(SyDealerCompany company : page.getItems()){
					if(company.getPayStt() != BillPayStatusEnum.STFF){ //如果暂缓发放，则不考虑
						pool.execute(new BillSendUtil(company,model,feeMons,user,mailSenders,batchNo));
					}
				}
				pn ++;
				controller(feeMons, companyCode, user, pn, model, mailSenders, batchNo); 
			}
		}
		pool.shutdown();
	}
	
	@Override
	synchronized public void saveSendBillsHis(List<CmsDealerSerfeeMonBill> bills, String batchNo, SyUser user, List<String> mailList,
		List<String> mailCcList, SerfeeBillStatusEnum status, SyDealerCompany company, String feeMons) {
		try {
			for(CmsDealerSerfeeMonBill bill : bills){
				CmsDealerSerfeeMonBillFlow flow = new CmsDealerSerfeeMonBillFlow(bill.getStt(),status,null,SerfeeBillActionEnum.CODE2);
				flow.setCompanyCode(bill.getCompanyCode());
				flow.setFeeMon(bill.getFeeMon());
				flow.setCrtTime(new Date());
				flow.setUserName(user.getUserId()+"");
				flow.setTrueName(user.getTrueName());
				
				if(flow.getPreStt()==SerfeeBillStatusEnum.INIT || flow.getPreStt() == SerfeeBillStatusEnum.ZDSE){
					flow.setNextStt(status);
					bill.setStt(flow.getNextStt());
					updateEntity(bill, "stt");
					saveEntity(flow);
				}
				
				String remark = "经销商代码："+company.getCompanyCode()+",经销商名称："+company.getCompanyName()+",服务费批次："+feeMons;
				SyEmailLog his = new SyEmailLog(batchNo,status == SerfeeBillStatusEnum.ZDAS?"1":"0",
								mailList.toString(),mailCcList.toString(),remark, new Date());
				his.setRemark(remark);
				saveEntity(his);
			}
		} catch (Exception e) {
			logger.error("未能成功保存邮件发送记录,",e);
		}		
	}

	@Override
	public void updateBillSucc(String feeMons, SyUser user) {
		for(String feeMon : feeMons.split(",")){
			CmsSerfeeMonInfo info = getEntityByCode(CmsSerfeeMonInfo.class, feeMon, false);
			if(info.getStt() != SerfeeStatusEnum.CWYES){
				throw new RuntimeException(info.getFeeMon()+"批次服务费状态为："+info.getStt()+",不允许放款确认操作.");
			}
			info.setIsFreeze(BooleanEnum.NO);
			CmsDealerSerfeeFlow flow = new CmsDealerSerfeeFlow();
			flow.setAction(SerfeeActionEnum.UNFREEZE);
			flow.setFeeMon(info.getFeeMon());
			flow.setPreStt(info.getStt());
			flow.setNextStt(info.getStt());
			flow.setTyp(BooleanEnum.YES);
			flow.setUserName(user.getUserId()+"");
			flow.setTrueName(user.getTrueName());
			
			updateSerfeeBillBatch(info,user,SerfeeBillStatusEnum.FKWA,SerfeeBillStatusEnum.FKWC,SerfeeBillActionEnum.CODE6);
			
			updateEntity(info, "isFreeze");
			saveEntity(flow);
		}		
	}

	private void updateSerfeeBillBatch(CmsSerfeeMonInfo info, SyUser user, SerfeeBillStatusEnum preStatus,
			SerfeeBillStatusEnum nextStatusEnum, SerfeeBillActionEnum action) {
		String hql = " from CmsDealerSerfeeMonBill where feeMon='"+info.getFeeMon()+"' and stt='"+preStatus.getValue()+"'";
		int pn = 1	;
		updateSerfeeBillFlowBatch(pn,hql,user,nextStatusEnum,action);
		
	}
	
	@Override
	public void updateSerfeeBillBatch(String feeMons,SyUser user,SerfeeBillStatusEnum nextStatusEnum,SerfeeBillActionEnum action){
		for(String feeMon : feeMons.split(",")){
			//批量修改并批量保存放款成功流水
			String hql = "select b from CmsDealerSerfeeMonBill b,SyDealerCompany dc where b.companyCode = dc.companyCode and (b.payStt is null or b.payStt != '"+BillPayStatusEnum.STFF.getValue()+"') " +
					"and (dc.payStt is null or dc.payStt != '"+BillPayStatusEnum.STFF.getValue()+"') and b.stt = '"+SerfeeBillStatusEnum.FPAR.getValue()+"' and b.feeMon='"+feeMon+"'";
			int pn = 1	;
			updateSerfeeBillFlowBatch(pn,hql,user,nextStatusEnum,action);
		}
	}
	
	public void updateSerfeeBillFlowBatch(int pn,String hql,SyUser user, SerfeeBillStatusEnum nextStatusEnum, SerfeeBillActionEnum action){
		Page<CmsDealerSerfeeMonBill> page = pageList(pn,hql);
		if(page.getItems()!=null && !page.getItems().isEmpty()){
			for(CmsDealerSerfeeMonBill bill : page.getItems()){
				bill.setStt(nextStatusEnum);
			}
			pn += 1;
			updateSerfeeBillFlowBatch(pn, hql, user, nextStatusEnum, action);
		}
	}

	@Override
	@Transactional
	public File generSerfeeMonEbank(String feeMons, SyUser user) throws Exception {
		String sqlFt = "";
		if(feeMons!=null && !"".equals(feeMons)){
			for(String feeMon : feeMons.split(",")){
				CmsSerfeeMonInfo info = getEntityByCode(CmsSerfeeMonInfo.class, feeMon, false);
				if(info == null){
					throw new RuntimeException(feeMon+"服务费批次不存在.");
				}
				if(info.getIsFreeze() != BooleanEnum.YES){
					throw new RuntimeException("SO尚未冻结对该批次服务费的操作，不可进行放款文件下载操作.");
				}
				sqlFt += "'"+feeMon +"',";
			}
			sqlFt = sqlFt.substring(0, sqlFt.length()-1);
		}

		//缺少经销商暂缓发放判断,账单暂缓发放的排除
		String sql = "SELECT "+
					 "concat(@ROWNM,DATE_FORMAT(CURRENT_TIMESTAMP(),'%Y%m%d%H%i%s')) ROW_RANK"+
					 ",@ROWNM:=@ROWNM+1 RANK"+
					 ",QSC.ACCOUNT_NO"+
					 ",QSC.ACCOUNT_NAME"+
					 ",QSC.ACCOUNT_SUB_BANK"+
					 ",QSC.BANK_PROVINCE"+
					 ",QSC.BANK_CITY"+
					 ",QSC.DEALER_EMAIL"+
					 ",QSC.TEL"+
					 ",'人民币' PAY_CNY"+
					 ",'上海' PAY_CITY"+
					 ",'快速' PAY_TYPE"+
					 ",'普通汇兑' PAY_OTH"+
					 ",QSC.PAY_ACCT"+
					 ",CURDATE() PAY_DAT"+
					 ",'' PAY_TIME"+
					 ",CONCAT('力蕴代付手续费','') PAY_REMARK"+
					 ",QSC.PAY_AMT"+
					 ",'' PAY_NOO"+
					 ",QSC.ACCOUNT_BANK"+
					 ",'KSJL0911_175888_740179D8'"+
					 ",'力蕴汽车' PAY_COM"+
					 " FROM (SELECT SC.ACCOUNT_NO,SC.ACCOUNT_NAME,SC.ACCOUNT_SUB_BANK,SC.BANK_CITY," +
					 "SC.BANK_PROVINCE,SC.DEALER_EMAIL,SC.TEL,QUM.PAY_ACCT,QUM.PAY_AMT,SC.ACCOUNT_BANK FROM (" +
					 "SELECT QU.COMPANY_CODE,QU.PAY_ACCT,SUM(QU.PARAM_AMT) PAY_AMT FROM ("+
					 "SELECT CSD.FEE_MON,CSD.COMPANY_CODE,CSD.PARAM_AMT,SAC.CON_ARG_VALUE PAY_ACCT FROM " +
					 "CMS_DEALER_SERFEE_MON_BILL CSB INNER JOIN CMS_DEALER_SERFEE_MON_BILL_DTL CSD ON CSB.FEE_MON in ("+sqlFt+") AND " +
					 "(CSB.PAY_STT IS NULL OR CSB.PAY_STT != '"+BillPayStatusEnum.STFF.getValue()+"') AND CSB.STT = '"+SerfeeBillStatusEnum.FPAR.getValue()+"' AND CSB.FEE_MON = CSD.FEE_MON " +
					 "AND CSB.COMPANY_CODE = CSD.COMPANY_CODE INNER JOIN CMS_PARAM_SUB CPS ON CSD.SUB_PARAM_EN = CPS.SUB_PARAM_EN " +
					 "INNER JOIN SY_ARG_CONTROL SAC ON SAC.CON_ARG_TYPE='fkzz' AND CPS.PAY_ACCT_IDN=SAC.CON_ARG_CODE " +
					 ") QU GROUP BY QU.COMPANY_CODE,QU.PAY_ACCT) QUM " +
					 "INNER JOIN SY_DEALER_COMPANY SC ON (SC.PAY_STT IS NULL OR SC.PAY_STT != '"+BillPayStatusEnum.STFF.getValue()+"') AND QUM.COMPANY_CODE = SC.COMPANY_CODE) QSC " + 
					 ",(SELECT @ROWNM:=0) ROWNM "	;
		
		sql = sql.replaceAll(":", "\\\\:"); //;特殊处理
		String[] sqlHead = new String[]{"业务参考号","收款人编号","收款人帐号","收款人名称","收方开户支行","收款人所在省","收款人所在市","收方邮件地址","收方移动电话","币种","付款分行","结算方式","业务种类","付方帐号","期望日","期望时间","用途","金额","收方联行号","收方开户银行","业务摘要","贷款公司名称"};
		List<String> sqlList = new ArrayList<String>();
		List<String[]> headList = new ArrayList<String[]>();
		List<String> sheetNameList = new ArrayList<String>();
		sqlList.add(sql);
		headList.add(sqlHead);
		sheetNameList.add("银行信息");
		
		String fileName = "["+feeMons+"]服务费放款文件"+DateUtil.getDateFormatE(new Date())+".xlsx";
		String filePath = PropertyUtil.getPropertyValue("E_BANK_FILE_PATH") + fileName;
		File file = new FileUtils(sqlList, headList, sheetNameList, filePath).getFileByBuildSql();
		
		dealerSerfeeMonFileService.saveFileAUpdateBill(feeMons, fileName, filePath, user);
		return file;
	}

	@Override
	public void updateSerfeeMonBill(CmsDealerSerfeeMonBill bill, SyUser user) {
		CmsDealerSerfeeMonBillFlow flow = new CmsDealerSerfeeMonBillFlow(bill.getStt(),SerfeeBillStatusEnum.QRWC,BooleanEnum.YES,SerfeeBillActionEnum.CODE7);
		flow.setCompanyCode(bill.getCompanyCode());
		flow.setFeeMon(bill.getFeeMon());
		flow.setCrtTime(new Date());
		flow.setUserName(user.getUserId()+"");
		flow.setTrueName(user.getTrueName());
		
		if(flow.getPreStt()!=SerfeeBillStatusEnum.INIT &&( flow.getNextStt() == SerfeeBillStatusEnum.ZDAS || flow.getNextStt() == SerfeeBillStatusEnum.ZDSE)){
			//不是账单待发送，不修改状态
		} else {
			bill.setStt(flow.getNextStt());
			updateEntity(bill, "stt","payStt");
		}
		saveEntity(flow);
	}

	@Override
	public CmsDealerSerfeeMonBill getSerfeeMonBillById(String feeMon, Integer companyCode, boolean b) {
		CmsDealerSerfeeMonBill bill = new CmsDealerSerfeeMonBill(feeMon, companyCode);
		List<CmsDealerSerfeeMonBill> bills = getEntitysByParams(bill, OperMode.EQ, "feeMon","companyCode");
		if(bills!=null && !bills.isEmpty()){
			bill = bills.get(0);
			if(b){
				Hibernate.initialize(bill.getDtls());
			}
			return bill;
		}
		return null;
	}
	
	@Override
	public CmsDealerSerfeeMonBill updateBillInvoiceRegister(CmsDealerSerfeeMonBill bill, SyUser user) {
		CmsSerfeeMonInfo info = getEntityByCode(CmsSerfeeMonInfo.class, bill.getFeeMon(), false);
		if(user.getUserType() == UserTypeEnum.M && info.getIsFreeze() == BooleanEnum.YES){
			throw new RuntimeException(bill.getFeeMon()+"批次服务费操作已被冻结，请等待财务服务费放款后解冻.");
		}
		bill = getSerfeeMonBillById(bill.getFeeMon(), bill.getCompanyCode(), false);
		SerfeeBillStatusEnum status = null;
		SerfeeBillActionEnum action = null;
		if(user.getUserType() == UserTypeEnum.M){
			status = SerfeeBillStatusEnum.FPAR;
			action = SerfeeBillActionEnum.CODE4;
			if(bill.getStt() == SerfeeBillStatusEnum.FPAR){
				status = SerfeeBillStatusEnum.FPAS;
			}
		} else {
			status = SerfeeBillStatusEnum.FPAS;
			action = SerfeeBillActionEnum.CODE3;
		}
		
		CmsDealerSerfeeMonBillFlow flow = new CmsDealerSerfeeMonBillFlow(bill.getStt(),status,BooleanEnum.YES,action);
		flow.setCompanyCode(bill.getCompanyCode());
		flow.setFeeMon(bill.getFeeMon());
		flow.setCrtTime(new Date());
		flow.setUserName(user.getUserId()+"");
		flow.setTrueName(user.getTrueName());
		
		bill.setStt(status);
		saveEntity(flow);		
		return bill;
	}

	@Override
	public CmsDealerSerfeeMonBill updateSuspendBill(CmsDealerSerfeeMonBill bill, SyUser user) {
		CmsSerfeeMonInfo info = getEntityByCode(CmsSerfeeMonInfo.class, bill.getFeeMon(), false);
		if(info.getIsFreeze() == BooleanEnum.YES){
			throw new RuntimeException(bill.getFeeMon()+"批次服务费操作已被冻结，请等待财务服务费放款后解冻.");
		}
		
		bill = getSerfeeMonBillById(bill.getFeeMon(), bill.getCompanyCode(), false);
		bill.setPayStt((bill.getPayStt() == null || bill.getPayStt() == BillPayStatusEnum.NMFF)?BillPayStatusEnum.STFF:BillPayStatusEnum.NMFF);
		
		CmsDealerSerfeeMonBillFlow flow = new CmsDealerSerfeeMonBillFlow(bill.getStt(),bill.getStt(),BooleanEnum.YES,SerfeeBillActionEnum.CODE4);
		flow.setRemark(bill.getPayStt().getName()+"服务费");
		flow.setCompanyCode(bill.getCompanyCode());
		flow.setFeeMon(bill.getFeeMon());
		flow.setCrtTime(new Date());
		flow.setUserName(user.getUserId()+"");
		flow.setTrueName(user.getTrueName());
		
		saveEntity(flow);
		return bill;
	}

	@Override
	public List<CmsDealerSerfeeMonBill> getSerfeeMonBills(SyDealerCompany dc, SerfeeBillStatusEnum[] status, int i) {
		CrapCriteria<CmsDealerSerfeeMonBill> cri = getSession().getCriteria(CmsDealerSerfeeMonBill.class).add(Restrictions.eq("companyCode", dc.getCompanyCode()))
													.add(Restrictions.in("stt", status))
													.addOrder(Order.desc("feeMon"))
													.setMaxResults(i);
		return cri.getResultList();
	}
	
}

