package com.liyun.car.fee.service.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.antlr.stringtemplate.StringTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.liyun.car.common.enums.OperMode;
import com.liyun.car.common.utils.DateUtil;
import com.liyun.car.core.spring.SpringContextBeanFactory;
import com.liyun.car.dealer.entity.SyAmDealerCompany;
import com.liyun.car.dealer.entity.SyDealerCompany;
import com.liyun.car.dealer.service.DealerCompanyService;
import com.liyun.car.fee.entity.CmsDealerSerfeeMonBill;
import com.liyun.car.fee.enums.SerfeeBillStatusEnum;
import com.liyun.car.fee.service.DealerSerfeeMonBillService;
import com.liyun.car.param.entity.SyMailSender;
import com.liyun.car.param.entity.SyModel;
import com.liyun.car.report.utils.MailSendUtil;
import com.liyun.car.system.entity.SyUser;
import com.liyun.car.system.entity.vo.VUserRole;

/**
 * 发送账单
 * @author zhoufei
 *
 */
public class BillSendUtil implements Runnable {

	public static Logger logger = LoggerFactory.getLogger(BillSendUtil.class);
	public static DealerCompanyService dealerCompanyService ;
	public static DealerSerfeeMonBillService dealerSerfeeMonBillService ;
	private SyDealerCompany company;
	private SyModel model;
	private String feeMons;
	private SyUser user;
	private List<SyMailSender> mailSenders;
	private String batchNo;
	
	static {
		dealerCompanyService = SpringContextBeanFactory.getBean("dealerCompanyServiceImpl");
		dealerSerfeeMonBillService = SpringContextBeanFactory.getBean("dealerSerfeeMonBillServiceImpl");
	}
	
	public BillSendUtil(SyDealerCompany company, SyModel model, String feeMons, SyUser user,
			List<SyMailSender> mailSenders, String batchNo) {
		super();
		this.company = company;
		this.model = model;
		this.feeMons = feeMons;
		this.user = user;
		this.mailSenders = mailSenders;
		this.batchNo = batchNo;
	}
	public BillSendUtil() {
		super();
	}
	
	@Override
	public void run() {
		sendDealerSerfeeMonBills(company,model,feeMons,user,mailSenders.get((int) (Math.random()*mailSenders.size())),batchNo);		
		try {
			Thread.sleep(5*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void sendDealerSerfeeMonBills(SyDealerCompany company, SyModel model, String feeMons, SyUser user, SyMailSender mailSender, String batchNo) {
		Map<String,Object> map = new HashMap<>();
		map.put("companyCode", new Integer[]{company.getCompanyCode()});
		map.put("feeMon", feeMons.split(","));
		List<CmsDealerSerfeeMonBill> bills = dealerSerfeeMonBillService.getEntitysByParamMap(CmsDealerSerfeeMonBill.class, OperMode.IN, map);
		if(bills!=null && !bills.isEmpty()){
			StringTemplate st = new StringTemplate(model.getContent());
			st.setAttribute("bills", bills);
			st.setAttribute("feeMons", feeMons);
			st.setAttribute("today", DateUtil.formatDay(new Date()));
			
			try {
				String subject = company.getCompanyName()+"["+feeMons+"]月 服务费账单";
				MailSendUtil sendUtil = new MailSendUtil(subject, st.toString(), null, null, null, mailSender.getEmail(), mailSender.getPassword());
				setMailParam(company, sendUtil);
				
				SerfeeBillStatusEnum status = SerfeeBillStatusEnum.ZDAS;
				try{
					sendUtil.send();
				} catch(Exception e){
					status = SerfeeBillStatusEnum.ZDSE;
				} finally{
					dealerSerfeeMonBillService.saveSendBillsHis(bills,batchNo,user,sendUtil.getMailAddress(),sendUtil.getMailCCAddress(),status, company, feeMons);
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("封装邮件发送参数异常.",e);
			}
		}
	}
	public void setMailParam(SyDealerCompany company, MailSendUtil sendUtil){
		List<String> mailList = new ArrayList<String>();
		List<String> ccMailList = new ArrayList<String>();
		Map<String,Object> map = new HashMap<>();
		
		VUserRole userRole = new VUserRole(null,company.getCompanyCode());
		List<VUserRole> userCompanys = dealerSerfeeMonBillService.getEntitysByParams(userRole, OperMode.EQ, "orgCode");
		if(userCompanys!=null && !userCompanys.isEmpty()){
			for(VUserRole vUserRole : userCompanys){
				mailList.add(vUserRole.getEmail());
			}
		} else {
			throw new RuntimeException("经销商代码："+company.getCompanyCode()+",经销商名称："+company.getCompanyName()+",请先设置可接受账单的用户.");
		}
		List<Integer> userIds = new ArrayList<>();
		company = dealerCompanyService.getEntityById(SyDealerCompany.class, company.getCompanyCode(), true);
		if(company.getAmDealerCompanys()!=null && !company.getAmDealerCompanys().isEmpty()){
			for(SyAmDealerCompany amDealerCompany : company.getAmDealerCompanys()){
				userIds.add(amDealerCompany.getUserId());
			}
			map.put("userId", userIds);
			List<SyUser> users = dealerSerfeeMonBillService.getEntitysByParamMap(SyUser.class, OperMode.IN, map);
			for(SyUser syUser : users){
				ccMailList.add(syUser.getEmail());//抄送客户经理
			}
		} 
		sendUtil.setMailAddress(mailList);
		sendUtil.setMailCCAddress(ccMailList);
	}
	
}
