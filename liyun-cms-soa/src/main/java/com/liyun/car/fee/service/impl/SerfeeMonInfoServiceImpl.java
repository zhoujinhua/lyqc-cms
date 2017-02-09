package com.liyun.car.fee.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.activiti.engine.task.Task;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liyun.car.activity.entity.CmsActivityInfo;
import com.liyun.car.activity.entity.CmsActivityRule;
import com.liyun.car.activity.enums.ActivityStatusEnum;
import com.liyun.car.activity.enums.RuleLevelEnum;
import com.liyun.car.common.enums.BooleanEnum;
import com.liyun.car.common.enums.OperMode;
import com.liyun.car.common.utils.ArithUtil;
import com.liyun.car.common.utils.DateUtil;
import com.liyun.car.common.utils.PropertyUtil;
import com.liyun.car.common.utils.StringUtils;
import com.liyun.car.core.utils.ExcelUtils;
import com.liyun.car.fee.entity.CmsDealerSerfeeAdjFile;
import com.liyun.car.fee.entity.CmsDealerSerfeeFlow;
import com.liyun.car.fee.entity.CmsDealerSerfeeMonBill;
import com.liyun.car.fee.entity.CmsDealerSerfeeMonBillDtl;
import com.liyun.car.fee.entity.CmsSerfeeMonInfo;
import com.liyun.car.fee.enums.SerfeeActionEnum;
import com.liyun.car.fee.enums.SerfeeStatusEnum;
import com.liyun.car.fee.service.DealerSerfeeMonFileService;
import com.liyun.car.fee.service.SerfeeMonInfoService;
import com.liyun.car.hibernate.service.impl.HibernateServiceSupport;
import com.liyun.car.system.entity.SyUser;
import com.liyun.car.system.utils.FileUtils;
import com.liyun.car.system.utils.ZipUtil;
import com.liyun.car.workflow.service.WorkflowService;

@Service
public class SerfeeMonInfoServiceImpl extends HibernateServiceSupport implements SerfeeMonInfoService {

	/**
	 * 事务都太大，考虑优化，使用编程式事务管理，用不了啊,换个办法，先结束上一事务，再异步另一个方法
	 */
	
	@Autowired
	private DealerSerfeeMonFileService dealerSerfeeMonFileService;
	@Autowired
	private WorkflowService workflowService;
	
	@Override
	public void saveStartProcess(CmsDealerSerfeeFlow flow, SyUser user, Map<String, Object> variables) {
			CmsSerfeeMonInfo info = getEntityByCode(CmsSerfeeMonInfo.class, flow.getFeeMon(), false);
			info.setStt(SerfeeStatusEnum.PREAPP);
			info.setSubmitUser(user.getUserId());
			
			flow.setAction(SerfeeActionEnum.SUBMIT);
			flow.setCrtTime(new Date());
			flow.setPreStt(info.getStt());
			flow.setNextStt(SerfeeStatusEnum.PREAPP);
			flow.setUserName(user.getUserId()+"");
			flow.setTrueName(user.getTrueName());
			saveEntity(flow);

			workflowService.startProcess(info.getFeeMon(), CmsSerfeeMonInfo.FLOW_NAME, user.getUserId()+"", variables);
	}

	@Override
	public void saveCompleteTask(CmsSerfeeMonInfo info, String taskId, CmsDealerSerfeeFlow flow, SyUser user) {
		flow.setPreStt(info.getStt());
		
		Map<String, Object> variables = new HashMap<String, Object>();
		SerfeeStatusEnum status = null;
		
		String elString = "${!pass}";
		if(flow.getTyp()!=null){
			variables.put("pass", false);
			if(flow.getTyp() == BooleanEnum.YES){
				variables.put("pass", true);
				elString = "${pass}";
			}
		}
		
		String statusString = workflowService.nextTaskDocumentation(taskId, elString);
		status = Enum.valueOf(SerfeeStatusEnum.class, (statusString==null?"":statusString).toUpperCase());
		if(status == null){
			throw new RuntimeException("流程图有误，请检查.");
		}
		
		flow.setNextStt(status);
		flow.setCrtTime(new Date());
		flow.setUserName(user.getUserId()+"");
		flow.setTrueName(user.getTrueName());
        saveEntity(flow);
        
        info.setStt(status);
        updateEntity(info, "stt");
        workflowService.completeTask(taskId, variables);
	}
	
	@Override
	@Transactional
	public File genSerfeeCalFile(CmsSerfeeMonInfo info) throws Exception {
		List<File> fileList = new ArrayList<File>();
		List<String> sqlList = new ArrayList<String>();
		List<String[]> headList = new ArrayList<String[]>();
		List<String> sheetNameList = new ArrayList<String>();
		String path = PropertyUtil.getPropertyValue("SER_FEE_TEMP_PATH");
		List<Thread> threadList = new ArrayList<>();
		
		//生成统计文件
		String sqlFeeMon = "SELECT FEE_MON,COMPANY_CNT,CONTR_CNT,CONTR_AMT,SERFEE,SERFEE_RATIO,CRT_TIME FROM CMS_SERFEE_MON_INFO WHERE FEE_MON='"+info.getFeeMon()+"'";
		String[] sqlFeeMonHead = new String[]{"服务费批次","经销商数量","申请单数量","总合同金额","服务费金额","服务费占比","生成时间"};
		String sqlDealerBill = "SELECT FEE_MON,COMPANY_CODE,COMPANY_NAME,PROVINCE,CITY,SALE_AREA,CONTR_CNT,SERFEE,SERFEE_RATIO,CASE WHEN PAY_STT = '2' THEN '正常发放' ELSE '暂缓发放' END,CRT_TIME FROM CMS_DEALER_SERFEE_MON_BILL WHERE FEE_MON='"+info.getFeeMon()+"'";
		String[] sqlDealerBillHead = new String[]{"服务费批次","经销商代码","经销商名称","经销商省份","经销商城市","销售区域","申请单数量","服务费金额","服务费占比","服务费付款状态","生成时间"};
		String sqlDealerBillDtl = "SELECT FEE_MON,COMPANY_CODE,COMPANY_NAME,SUB_PARAM_EN,SUB_PARAM_NM,PARAM_AMT,CRT_TIME FROM CMS_DEALER_SERFEE_MON_BILL_DTL WHERE FEE_MON='"+info.getFeeMon()+"' ORDER BY COMPANY_CODE";
		String[] sqlDealerBillDtlHead = new String[]{"服务费批次","经销商代码","经销商名称","二级科目代码","二级科目名称","二级科目金额","创建时间"};
		sqlList.add(sqlFeeMon);
		sqlList.add(sqlDealerBill);
		sqlList.add(sqlDealerBillDtl);
		
		headList.add(sqlFeeMonHead);
		headList.add(sqlDealerBillHead);
		headList.add(sqlDealerBillDtlHead);
		
		sheetNameList.add("服务费汇总表");
		sheetNameList.add("经销商服务费明细表");
		sheetNameList.add("经销商账单明细表");
		
		String fileName = path + info.getFeeMon()+"批次服务费计算汇总.xls";
		File file = new FileUtils(sqlList, headList, sheetNameList, fileName).getFileByBuildSql();
		fileList.add(file);
		
		//生成活动细分文件
		String sqlContrDtl = "SELECT CS.APP_CODE,CS.LOAN_DT,CS.SALE_AREA,CS.COMPANY_NAME,CS.COMPANY_TYPE,"
				+ "CS.PROPPSER_NAME,CS.CAR_BRAND,CS.CAR_SERIES,CASE WHEN CS.IS_OLD='1' THEN '二手车' ELSE '新车' END,CS.PAYMENT_CH_EN,"
				+ "CS.R_CARLOAN_AMOUNT,CS.R_DISCOUNT_TRUE_E,CS.R_LOAN_PERIODS,CS.R_ACCOUNT_FEE,CASE WHEN CS.IS_GPS = '1' THEN '是' ELSE '否' END,CS.R_GPS_FEE,CS.R_LOAN_RATE,"
				+ "CS.R_LOAN_AMOUNT,SA.CON_ARG_NAME,CS.ACTT_NM,CS.ACTT_CODE,CS.RULE_NAME,CASE WHEN CS.RULE_AMT >= 0 THEN '奖励' ELSE '惩罚' END,"
				+ "CS.RULE_AMT,CS.RULE_DESC FROM CMS_DEALER_SERFEE_MON_CONTR CS LEFT JOIN SY_ARG_CONTROL SA "
				+ "ON CS.IS_LCV = SA.CON_ARG_VALUE AND SA.CON_ARG_TYPE = 'fwcl'"
				+ " where CS.FEE_MON='"+info.getFeeMon()+"'";
		String[] sqlContrDtlHead = new String[]{"申请单编号","放款日期","销售区域","门店名称","单位性质","申请人姓名","品牌","款式","是否二手车","放款渠道","车辆贷款金额(元)","贴息金额","贷款期限","账户管理费","是否安装GPS","GPS费用(元)","贷款利率","总贷款金额(贷款金额+GPS费用)","是否LCV",/*"经销商服务费金额",*/"活动编码","活动名称","规则名称","规则奖罚方式","申请单规则金额","规则描述"};
		
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, Integer.parseInt(info.getFeeMon().substring(0,4)));
		c.set(Calendar.MONTH, Integer.parseInt(info.getFeeMon().substring(4,6)));
		c.set(Calendar.DAY_OF_MONTH, 0);
		
		String hql = "from CmsActivityInfo c where c.stt = '1' and ((c.acttCyc = '1' and DATE_FORMAT(c.acttEnd,'%Y%m') = '"+info.getFeeMon()+"' and date(c.acttEnd) <= '"+DateUtil.getDateFormatE(c.getTime())+"')) or ((c.acttCyc = '0' and '"+DateUtil.getDateFormatE(c.getTime())+"' = LAST_DAY('"+DateUtil.getDateFormatE(c.getTime())+"')))";
		List<CmsActivityInfo> activityInfos = getList(hql);
		if(activityInfos!=null && !activityInfos.isEmpty()){
			for(CmsActivityInfo activityInfo : activityInfos){
				Hibernate.initialize(activityInfo.getActivityRules());
				sqlList.clear();
				headList.clear();
				sheetNameList.clear();
				//汇总sheet
				String activitySql = "SELECT CST.FEE_MON,CST.COMPANY_CODE,CST.COMPANY_NAME,CST.ACTT_CODE,CAI.ACTT_NM,CST.SUB_PARAM_EN,CST.SUB_PARAM_NM,CST.PARAM_AMT,CAI.ACTT_DESC FROM CMS_DEALER_SERFEE_MON_DTL CST INNER JOIN CMS_ACTIVITY_INFO CAI ON CST.ACTT_CODE = CAI.ACTT_CODE AND CAI.ACTT_CODE='"+activityInfo.getActtCode()+"' AND CST.FEE_MON='"+info.getFeeMon()+"'";
				String[] activitySqlHead = new String[]{"服务费批次","经销商编号","经销商名称","活动编号","活动名称","二级科目代码","二级科目名称","服务费金额","活动描述"};
				
				sqlList.add(activitySql);
				headList.add(activitySqlHead);
				sheetNameList.add(activityInfo.getActtNm()+"汇总");
				
				int sheetCount = 1;
				for(CmsActivityRule activityRule : activityInfo.getActivityRules()){
					//分规则sheet
					if(activityRule.getRuleLvl() == RuleLevelEnum.APP){
						String sqlRuleContrDtl = sqlContrDtl + " AND CS.RULE_ID="+activityRule.getRuleId();
						sqlList.add(sqlRuleContrDtl);
						headList.add(sqlContrDtlHead);
					} else {
						String sqlDealerDtl = "SELECT CSD.FEE_MON,CSD.COMPANY_CODE,CSD.COMPANY_NAME,CSD.RULE_NAME,CSD.RULE_AMT,RULE_DESC FROM CMS_DEALER_SERFEE_MON_DEALER CSD INNER JOIN CMS_ACTIVITY_RULE CAR ON CSD.RULE_ID = CAR.RULE_ID AND CSD.ACTT_CODE='"+activityInfo.getActtCode()+"' AND CSD.FEE_MON='"+info.getFeeMon()+"'";
						String[] sqlDealerDtlHead = new String[]{"服务费批次","经销商编号","经销商名称","规则名称","规则金额","规则描述"};
						sqlList.add(sqlDealerDtl);
						headList.add(sqlDealerDtlHead);
					}
					sheetNameList.add(StringUtils.exprExcelWord("规则"+sheetCount+":"+activityRule.getRuleNm()));
					sheetCount ++ ;
				}
				
				String activityFileName = path+info.getFeeMon() + "批次" + activityInfo.getActtNm() + "活动计算明细.xls";
				fileList.add(new File(activityFileName));
				Thread t = new FileUtils(sqlList, headList, sheetNameList, activityFileName);
				threadList.add(t);
				t.start();
			}
		}
		for(Thread t : threadList){
			t.join();
		}
		{
			ZipUtil.zipFiles(fileList, new File(path+info.getFeeMon()+"批次服务费计算结果.zip"));
			dealerSerfeeMonFileService.saveSerfeeMonFile(info, path+info.getFeeMon()+"批次服务费计算结果.zip");
			
			return new File(path+info.getFeeMon()+"批次服务费计算结果.zip");
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public File genSerfeeModelFile(CmsSerfeeMonInfo info) throws Exception {
		String sql = "SELECT DISTINCT CONCAT(CPS.SUB_PARAM_NM,CONCAT(CONCAT('(科目代码:',CPS.SUB_PARAM_EN),')')) FROM CMS_ACTIVITY_INFO CAI INNER JOIN CMS_PARAM_SUB CPS ON CAI.SUB_PARAM_EN = CPS.SUB_PARAM_EN WHERE CAI.STT = '1'";
		List<Object[]> list = (List<Object[]>) getObjByNativeSql(sql, null, null);
		List<String[]> headArrayList = new ArrayList<String[]>();
		if(list!=null && !list.isEmpty()){
			String[] headArray = new String[2+list.size()];
			headArray[0] = "经销商代码(*)";
			headArray[1] = "经销商名称(*)";
			for(int i=0;i<list.size();i++){
				Object head = list.get(i);
				headArray[i+2] = head.toString();
			}
			headArrayList.add(headArray);
			List<String> sheetNameList = new ArrayList<String>();
			sheetNameList.add("model");
			
			return new FileUtils(null, headArrayList, sheetNameList, "调整文件.xlsx").getFileByBuildSql();
		}
		return null;
	}

	@Override
	public void saveFaConfirm(CmsDealerSerfeeFlow flow, SyUser user) {
		Task task = workflowService.getTask(user.getUserId()+"", flow.getFeeMon());
		if(task == null){
			throw new RuntimeException("必须有财务人员权限的用户才可以进行此操作.");
		}
		
		workflowService.claimTask(task.getId(), user.getUserId());
		
		CmsSerfeeMonInfo info = new CmsSerfeeMonInfo();
		info.setFeeMon(flow.getFeeMon());
		info.setTask(task);
		info.setStt(SerfeeStatusEnum.GENZH);
		saveCompleteTask(info, task.getId(), flow, user);
	}

	@Override
	public void updateFeeMonFreeze(String feeMons, SyUser user) {
		for(String feeMon : feeMons.split(",")){
			CmsSerfeeMonInfo info = getEntityByCode(CmsSerfeeMonInfo.class, feeMon, false);
			if(info == null){
				throw new RuntimeException(feeMon+"服务费批次不存在。");
			}
			CmsDealerSerfeeFlow flow = new CmsDealerSerfeeFlow();
			flow.setAction(SerfeeActionEnum.FREEZE);
			flow.setFeeMon(info.getFeeMon());
			flow.setPreStt(info.getStt());
			flow.setNextStt(info.getStt());
			flow.setTyp(BooleanEnum.YES);
			flow.setUserName(user.getUserId()+"");
			flow.setTrueName(user.getTrueName());
			
			info.setIsFreeze(BooleanEnum.YES);
			saveEntity(flow);
		}
	}
	@Override
	public void updateEndProcess(String taskId, String feeMon)  throws Exception{
		CmsSerfeeMonInfo cmsSerfeeMonInfo = getEntityByCode(CmsSerfeeMonInfo.class, feeMon, false);
		CmsDealerSerfeeFlow flow = new CmsDealerSerfeeFlow();
		flow.setFeeMon(feeMon);
		flow.setCrtTime(new Date());
		flow.setPreStt(cmsSerfeeMonInfo.getStt());
		flow.setRemark("强制结束流程，状态退回.");
		flow.setAction(SerfeeActionEnum.FOREND);
		
		cmsSerfeeMonInfo.setStt(SerfeeStatusEnum.CALMIT);
		flow.setNextStt(SerfeeStatusEnum.CALMIT);
		saveEntity(flow);
		workflowService.endProcess(taskId);
	}

	@Override
	public void updateSerfeeByModiFile(File file, String feeMon, SyUser user) throws FileNotFoundException {
		List<List<String>> contentList = new ExcelUtils(new FileInputStream(file), "model").getSheetDataMap();
		//存储excel内容,CompanyCode,<En,value>
		Map<String,Map<String,String>> map = new HashMap<String, Map<String,String>>();
		//存储subParam列位置和En 列，En
		Map<Integer,String> subParamMap = new HashMap<Integer, String>();
		//存储company的行位置和Code 行,Code
		Map<Integer,String> codeMap = new HashMap<Integer, String>();
		if(contentList!=null && !contentList.isEmpty()){
			for(int i=0;i< contentList.size();i++){
				if(i == 0){
					for(int j=2;j<contentList.get(i).size();j++){
						String key = contentList.get(i).get(j);
						subParamMap.put(j, key.substring(key.indexOf(":")+1, key.length()-1));
					}
				} else {
					for(int j=0;j<contentList.get(i).size();j++){
						String key = contentList.get(i).get(j);
						//第一列并且非第1行是经销商代码,companyCode,null
			    		if(j == 0){ //处理第一列
			    			if(key == null || "".equals(key)){
			    				throw new RuntimeException("经销商代码为必输项,不可为空.");
			    			}
			    			if(map.get(key) == null){
			    				map.put(key, new HashMap<String, String>());
			    			}
			    			if(codeMap.get(i)==null){
			    				codeMap.put(i, key);
			    			}
			    		} else if(j == 1){
			    			//经销商名称
			    		} else {
			    			if(subParamMap.get(j)!=null){
			    				map.get(codeMap.get(i)).put(subParamMap.get(j), key);
			    			}
			    		}
					}
				}
			}
		}
		Double bAmount = 0.00d;
		for(Iterator<String> iter = map.keySet().iterator();iter.hasNext();){
			Double cAmount = 0.00d;
			String key = iter.next();
			int companyCode = (int)Double.parseDouble(key);
			Map<String,String> paramMap = map.get(key);
			for(Iterator<String> paramIter = paramMap.keySet().iterator();paramIter.hasNext();){
				String paramEn = paramIter.next();
				if(paramMap.get(paramEn)!=null && !"".equals(paramMap.get(paramEn))){
					CmsDealerSerfeeMonBillDtl billDtl = new CmsDealerSerfeeMonBillDtl(feeMon, companyCode, paramEn);
					List<CmsDealerSerfeeMonBillDtl> dtls = getEntitysByParams(billDtl, OperMode.EQ, "feeMon","companyCode","subParamEn");
					if(dtls!=null && !dtls.isEmpty()){
						billDtl = dtls.get(0);
						cAmount += ArithUtil.sub((Double.parseDouble(paramMap.get(paramEn))) , billDtl.getParamAmt());
					} else {
						throw new RuntimeException("根据经销商代码,服务费批次,二级科目代码未找到相应的账单明细.");
					}
					
					if(cAmount != 0.00d ){
						billDtl.setParamAmt(Double.parseDouble(paramMap.get(paramEn)));
						billDtl.setIsAdj(BooleanEnum.YES);
						
						updateEntity(billDtl, "paramAmt","isAdj");
						CmsDealerSerfeeAdjFile adjFile = new CmsDealerSerfeeAdjFile(feeMon,companyCode,"",paramEn,Double.parseDouble(paramMap.get(paramEn)),new Date(),file.getAbsolutePath());
						saveEntity(adjFile);
					}
				}
			}
			bAmount += cAmount;
			//在CmsDealerSerfeeMonBill中重新设置总金额
			CmsDealerSerfeeMonBill bill = new CmsDealerSerfeeMonBill(feeMon, companyCode);
			bill = getEntitysByParams(bill, OperMode.EQ, "feeMon","companyCode").get(0);
			bill.setSerfee(ArithUtil.add(bill.getSerfee(),cAmount));
			updateEntity(bill, "serfee");
		}
		
		//处理流程
		CmsSerfeeMonInfo info = getEntityByCode(CmsSerfeeMonInfo.class, feeMon, false);
		info.setSerfee(info.getSerfee() + bAmount);
		info.setStt(SerfeeStatusEnum.CALMIT);
		
		//保存日志
		CmsDealerSerfeeFlow flow = new CmsDealerSerfeeFlow();
		flow.setFeeMon(feeMon);
		flow.setCrtTime(new Date());
		flow.setAction(SerfeeActionEnum.MODIFY);
		flow.setFileDir(file.getPath());
		flow.setPreStt(info.getStt());
		flow.setNextStt(SerfeeStatusEnum.CALMIT);
		flow.setUserName(user.getUserId()+"");
		flow.setTrueName(user.getTrueName());
		saveEntity(flow);
	}
}
