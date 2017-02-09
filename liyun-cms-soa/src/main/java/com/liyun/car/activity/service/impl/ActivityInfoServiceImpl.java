package com.liyun.car.activity.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.liyun.car.activity.entity.CmsActivityDealer;
import com.liyun.car.activity.entity.CmsActivityInfo;
import com.liyun.car.activity.enums.ActivityStatusEnum;
import com.liyun.car.activity.service.ActivityInfoService;
import com.liyun.car.common.enums.BooleanEnum;
import com.liyun.car.common.enums.OperMode;
import com.liyun.car.common.utils.DateUtil;
import com.liyun.car.core.utils.ExcelUtils;
import com.liyun.car.dealer.entity.SyDealerCompany;
import com.liyun.car.dealer.enums.DealerStatusEnum;
import com.liyun.car.hibernate.service.impl.HibernateServiceSupport;

@Service
public class ActivityInfoServiceImpl extends HibernateServiceSupport implements ActivityInfoService {

	@Override
	public void saveActivityInfo(CmsActivityInfo activityInfo) {

		if(activityInfo.getActtCode()!=null && !"".equals(activityInfo.getActtCode())){
			updateEntity(activityInfo, "acttNm","subParamNm","topParamEn","subParamEn","acttCyc","acttTyp","acttBegin","acttEnd","isDealer","acttDesc");
			CmsActivityInfo info = getEntityByCode(CmsActivityInfo.class, activityInfo.getActtCode(), true);
			info.getDealers().clear();
			
			if(activityInfo.getIsDealer().getValue().equals(BooleanEnum.YES.getValue())){
				String[] codes = activityInfo.getCompanyCodes().split(",");
				String[] names = activityInfo.getCompanyNames().split(",");
				
				for(int i=0;i<codes.length;i++){
					CmsActivityDealer cad = new CmsActivityDealer();
					cad.setCompanyCode(Integer.parseInt(codes[i]));
					cad.setCompanyName(names[i]);
					cad.setCrtTime(new Date());
					cad.setActivityInfo(activityInfo);
					
					info.getDealers().add(cad);
				}
			}
		} else {
			activityInfo.setStt(ActivityStatusEnum.PRE);
			Integer id = getMaxActivityCode(new Date())+1;
			if(id<10)
				activityInfo.setActtCode("AD"+DateUtil.getgetDateFormat4(new Date())+"0"+id);
			else
				activityInfo.setActtCode("AD"+DateUtil.getgetDateFormat4(new Date())+id);
			saveEntity(activityInfo);
			if(activityInfo.getIsDealer().getValue().equals(BooleanEnum.YES.getValue())){
				String[] codes = activityInfo.getCompanyCodes().split(",");
				String[] names = activityInfo.getCompanyNames().split(",");
				
				for(int i=0;i<codes.length;i++){
					CmsActivityDealer cad = new CmsActivityDealer();
					cad.setCompanyCode(Integer.parseInt(codes[i]));
					cad.setCompanyName(names[i]);
					cad.setCrtTime(new Date());
					cad.setActivityInfo(activityInfo);
					saveEntity(cad);
				}
			}
		}		
	}

	private int getMaxActivityCode(Date date) {
		String sql = "SELECT CASE WHEN MAX(SUBSTRING(ACTT_CODE,9,2)) IS NULL THEN 0 ELSE MAX(SUBSTRING(ACTT_CODE,9,2)) END MID FROM CMS_ACTIVITY_INFO WHERE SUBSTRING(ACTT_CODE,3,6)='"+DateUtil.getgetDateFormat4(date)+"'";
		List<?> list = getSession().createNativeQuery(sql).setResultTransfer(Transformers.ALIAS_TO_ENTITY_MAP).getResultList();
		if(list!=null&&!list.isEmpty()){
			Map<?,?> map = (Map<?,?>) list.get(0);
			return Integer.parseInt((String) map.get("MID"));
		}
		return 0;
	}

	@Override
	public void deleteActivityInfo(CmsActivityInfo activityInfo) {
		CmsActivityInfo info = getEntityByCode(CmsActivityInfo.class, activityInfo.getActtCode(), true);
		info.getDealers().clear();
		info.getActivityRules().clear();
		
		deleteEntity(info);
	}

	@Override
	public List<String> getDealerTree(CmsActivityInfo activityInfo) {
		List<SyDealerCompany> dealerList = new ArrayList<SyDealerCompany>();
		activityInfo = getEntityByCode(CmsActivityInfo.class, activityInfo.getActtCode(), true);
		if(activityInfo!=null && activityInfo.getDealers()!=null){
			for(CmsActivityDealer dealer : activityInfo.getDealers()){
				SyDealerCompany company = new SyDealerCompany();
				company.setCompanyCode(dealer.getCompanyCode());
				company.setCompanyName(dealer.getCompanyName());
				
				dealerList.add(company);
			}
		}
		
		return getTree(dealerList);
	}

	private List<String> getTree(List<SyDealerCompany> dealerList){
		List<String> treeList = new ArrayList<String>();
		treeList.add("{id:\"999999\",pId:\"0\",name:\"经销商列表\",isParent:true}");
		
		DealerStatusEnum[] statusEnums = DealerStatusEnum.getOnlineStatus();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", statusEnums);
		List<SyDealerCompany> list = getEntitysByParamMap(SyDealerCompany.class, OperMode.IN, map);
		
		List<Integer> codeList = new ArrayList<Integer>();
		if(dealerList!=null && !dealerList.isEmpty()){
			for(SyDealerCompany company : dealerList){
				codeList.add(company.getCompanyCode());
			}
		}
		Map<String,String> cityMap = new HashMap<String, String>();
		int i = 1000000;
		if(list!=null && !list.isEmpty()){
			for(SyDealerCompany dealer : list){
				String province = ((dealer.getProvince()==null||dealer.getProvince().equals(""))?"其它":dealer.getProvince());
				if(!cityMap.containsKey(province)){
					i++;
					cityMap.put(province, i+"");
					treeList.add("{id:\""+i+"\",pId:\"999999\",name:\""+province+"\"}");
				}
				
				if(codeList.contains(dealer.getCompanyCode())){
					treeList.add("{id:\""+dealer.getCompanyCode()+"\",pId:\""+cityMap.get(province)+"\",name:\""+dealer.getCompanyName()+"\",checked:true}");
				} else {
					treeList.add("{id:\""+dealer.getCompanyCode()+"\",pId:\""+cityMap.get(province)+"\",name:\""+dealer.getCompanyName()+"\"}");
				}
			}
		}
		return treeList;
	}
	
	@Override
	public List<String> getUploadTree(CommonsMultipartFile file) throws IOException {
		List<List<String>> contentList = new ExcelUtils(file.getInputStream(), "sheet1").getSheetDataMap();
		if(contentList!=null && !contentList.isEmpty()){
			List<SyDealerCompany> dealerList = new ArrayList<SyDealerCompany>();
			for(List<String> list : contentList){
				SyDealerCompany company = new SyDealerCompany();
				try{
					company.setCompanyCode(Integer.parseInt(list.get(0)));
					company.setCompanyName(list.get(1));
					dealerList.add(company);
				} catch(Exception e){
					continue;
				}
			}
			return getTree(dealerList);
		}
		return getTree(null);
	}

}
