package com.liyun.car.activity.service.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import com.liyun.car.activity.entity.CmsActivityInfo;
import com.liyun.car.activity.entity.CmsActivityPropDicDealer;
import com.liyun.car.activity.entity.CmsActivityRule;
import com.liyun.car.activity.entity.CmsActivityRuleEx;
import com.liyun.car.activity.entity.CmsActivityRuleGroup;
import com.liyun.car.activity.entity.CmsContrRuleProp;
import com.liyun.car.activity.entity.CmsDealerRuleProp;
import com.liyun.car.activity.entity.CmsParamRuleProp;
import com.liyun.car.activity.enums.ReachTypeEnum;
import com.liyun.car.activity.enums.RuleCycleEnum;
import com.liyun.car.activity.enums.RuleLevelEnum;
import com.liyun.car.activity.service.ActivityRuleService;
import com.liyun.car.common.enums.BooleanEnum;
import com.liyun.car.common.enums.OperMode;
import com.liyun.car.common.utils.BeanInvokeUtils;
import com.liyun.car.hibernate.service.impl.HibernateServiceSupport;
import com.liyun.car.hibernate.utils.EnumUtils;

@Service
public class ActivityRuleServiceImpl extends HibernateServiceSupport implements ActivityRuleService {

	private static List<String> props = new ArrayList<String>();
	static{
		props.add("rGPSFee");
		props.add("rcarloanAmountMax");
		props.add("rcarloanAmountMin");
		props.add("initScaleMax");
		props.add("initScaleMin");
		props.add("rloanAmountMax");
		props.add("rloanAmountMin");
		props.add("rLoanRate");
		props.add("salePriceMax");
		props.add("salePriceMin");
		props.add("rComFeeMax");
		props.add("rComFeeMin");
		props.add("rAccountFeeMin");
		props.add("rAccountFeeMax");
		props.add("rDisTrueEMin");
		props.add("rDisTrueEMax");
		props.add("rYanbaoFee");
		props.add("comRate");
		props.add("gpsRebate");
	}
	
	@Override
	public void saveActivityRule(CmsActivityRule rule) throws Exception {
		if(rule.getRuleId()!=null){
			updateEntity(rule,"ruleNm","ruleLvl","ruleDesc","reTyp","reMode","reRst");
			CmsActivityRule activityRule = getEntityById(CmsActivityRule.class, rule.getRuleId(), true);
			activityRule.getProps().clear();
			activityRule.getDealerProps().clear();
			
			if(rule.getRuleLvl() == RuleLevelEnum.APP){
				updateEntity(rule.getContrProp(),"isGPS","rGPSFee","rcarloanAmountMax",
						"rcarloanAmountMin","courseMax","courseMin","initScaleMax","initScaleMin","isHouse","isLcv",
						"isOld","carAgeMin","carAgeMax","rloanAmountMax","rloanAmountMin","rloanPeriods","rLoanRate",
						"salePriceMax","salePriceMin","loanTimeBegin","loanTimeEnd","productCode","productName","paymentChEn",
						"carBrand","carSeries","appTimeMax","appTimeMin","rComFeeMax","rComFeeMin","rAccountFeeMin"
						,"rAccountFeeMax","rDisTrueEMin","rDisTrueEMax","rYanbaoFee","comRate","gpsRebate","workCity","contrSTT");
				for(Field field : CmsContrRuleProp.class.getDeclaredFields()){
					if(!field.getName().equals("ruleId") && !field.getName().equals("productName")&& !field.getName().substring(field.getName().length()-2, field.getName().length()).toLowerCase().equals("zh")){
						Object obj = BeanInvokeUtils.invokeMethod(rule.getContrProp(), field.getName());
						String fieldValue = obj == null?null : obj.toString();
						System.out.println(field.getName() + "   --------"+props.contains(field.getName()));
						if(fieldValue!=null && !"".equals(fieldValue.toString())){
							String formatValue = fieldValue;
							if(props.contains(field.getName())){
								formatValue = "";
								String[] parts = fieldValue.split(",");
								for(String part : parts){
									formatValue += String.format("%.2f", Double.parseDouble(part))+",";
								}
								formatValue = formatValue.substring(0, formatValue.length()-1);
							}
							if(obj!=null && obj instanceof BooleanEnum){
								formatValue = ((BooleanEnum) obj).getValue();
							}
							CmsParamRuleProp paramProp = new CmsParamRuleProp(field.getName(), "", RuleLevelEnum.APP, formatValue);
							paramProp.setRule(rule);
							activityRule.getProps().add(paramProp);
						}
					}
				}
			} else {
				String isOldValue = rule.getDealerProps().get(0).getIsOld();
				String carTypValue = rule.getDealerProps().get(0).getCarTyp();
				String cycValue = rule.getDealerProps().get(0).getCyc();
				
				String[] isOldPart = isOldValue.split(",");
				String[] carTypPart = carTypValue.split(",");
				String[] cycPart = cycValue.split(",");
				
				int i = 1;
				for(CmsDealerRuleProp dealerProp : rule.getDealerProps()){
					if(dealerProp.getPropValue()!=null && !"".equals(dealerProp.getPropValue())){
						dealerProp.setIsOld(isOldValue);
						dealerProp.setCarTyp(carTypValue);
						dealerProp.setCyc(cycValue);
						
						String reachTyp = dealerProp.getReachTyp();
						for(String isOld : isOldPart){
							for(String carTyp : carTypPart){
								for(String cyc : cycPart){
									CmsActivityPropDicDealer dicDealer = new CmsActivityPropDicDealer();
									dicDealer.setCarTyp(carTyp);
									dicDealer.setIsOld(EnumUtils.valueOf(BooleanEnum.class, isOld));
									dicDealer.setCyc(EnumUtils.valueOf(RuleCycleEnum.class, cyc));
									dicDealer.setReachTyp(EnumUtils.valueOf(ReachTypeEnum.class, reachTyp));
									List<CmsActivityPropDicDealer> dealers = getEntitysByParams(dicDealer, OperMode.EQ, "isOld", "carTyp", "cyc", "reachTyp");
									if(dealers!=null && !dealers.isEmpty()){
										CmsActivityPropDicDealer dealer = dealers.get(0);
										CmsParamRuleProp prop = new CmsParamRuleProp(dealer.getPropCode(),dealer.getPropName(),rule.getRuleLvl(),dealerProp.getPropValue());
										prop.setRuleNum(i);
										prop.setRule(rule);
										activityRule.getProps().add(prop);
									} else {
										throw new RuntimeException("在CmsActivityPropDicDealer表中没有找到对应的经销商指标.");
									}
								}
							}
						}
						dealerProp.setRule(rule);
						activityRule.getDealerProps().add(dealerProp);
					} 
					i++;
				}
				activityRule.setRulePropCnt(i - 1);
			}
		} else {
			rule.setCrtTime(new Date());
			//rule.getDealerProps().clear();
			saveEntity(rule);
			
			if(rule.getRuleLvl() == RuleLevelEnum.APP){
				for(Field field : CmsContrRuleProp.class.getDeclaredFields()){
					if(!field.getName().equals("ruleId") && !field.getName().equals("productName")&& !field.getName().substring(field.getName().length()-2, field.getName().length()).toLowerCase().equals("zh")){
						Object obj = BeanInvokeUtils.invokeMethod(rule.getContrProp(), field.getName());
						String fieldValue = obj == null?null : obj.toString();
						if(fieldValue!=null && !"".equals(fieldValue.toString())){
							String formatValue = fieldValue;
							if(props.contains(field.getName())){
								formatValue = "";
								String[] parts = fieldValue.split(",");
								for(String part : parts){
									formatValue += String.format("%.2f", Double.parseDouble(part))+",";
								}
								formatValue = formatValue.substring(0, formatValue.length()-1);
							}
							if(obj!=null && obj instanceof BooleanEnum){
								formatValue = ((BooleanEnum) obj).getValue();
							}
							CmsParamRuleProp paramProp = new CmsParamRuleProp(field.getName(), "", RuleLevelEnum.APP, formatValue);
							paramProp.setRule(rule);
							saveEntity(paramProp);
						}
					}
				}
				rule.getContrProp().setRuleId(rule.getRuleId());
				saveEntity(rule.getContrProp());
			} else {
				String isOldValue = rule.getDealerProps().get(0).getIsOld();
				String carTypValue = rule.getDealerProps().get(0).getCarTyp();
				String cycValue = rule.getDealerProps().get(0).getCyc();
				
				String[] isOldPart = isOldValue.split(",");
				String[] carTypPart = carTypValue.split(",");
				String[] cycPart = cycValue.split(",");
				
				int i = 1;
				for(CmsDealerRuleProp dealerProp : rule.getDealerProps()){
					if(dealerProp.getPropValue()!=null && !"".equals(dealerProp.getPropValue())){
						dealerProp.setIsOld(isOldValue);
						dealerProp.setCarTyp(carTypValue);
						dealerProp.setCyc(cycValue);
						
						String reachTyp = dealerProp.getReachTyp();
						for(String isOld : isOldPart){
							for(String carTyp : carTypPart){
								for(String cyc : cycPart){
									CmsActivityPropDicDealer dicDealer = new CmsActivityPropDicDealer();
									dicDealer.setCarTyp(carTyp);
									dicDealer.setIsOld(EnumUtils.valueOf(BooleanEnum.class, isOld));
									dicDealer.setCyc(EnumUtils.valueOf(RuleCycleEnum.class, cyc));
									dicDealer.setReachTyp(EnumUtils.valueOf(ReachTypeEnum.class, reachTyp));
									List<CmsActivityPropDicDealer> dealers = getEntitysByParams(dicDealer, OperMode.EQ, "isOld", "carTyp", "cyc", "reachTyp");
									if(dealers!=null && !dealers.isEmpty()){
										CmsActivityPropDicDealer dealer = dealers.get(0);
										CmsParamRuleProp prop = new CmsParamRuleProp(dealer.getPropCode(),dealer.getPropName(),rule.getRuleLvl(),dealerProp.getPropValue());
										prop.setRuleNum(i);
										prop.setRule(rule);
										saveEntity(prop);
									} else {
										throw new RuntimeException("在CmsActivityPropDicDealer表中没有找到对应的经销商指标.");
									}
								}
							}
						}
						dealerProp.setRule(rule);
					} 
					i++;
				}
				rule.setRulePropCnt(i - 1);
			}
		}
	}

	@Override
	public void deleteActivityRule(CmsActivityRule rule) {
		rule = getEntityById(CmsActivityRule.class, rule.getRuleId(), true);
		
		rule.getDealerProps().clear();
		rule.getProps().clear();
		deleteEntity(rule);
	}

	@Override
	public List<CmsActivityRule> getAvliaList(CmsActivityRule rule, String ruleIds) {
		List<CmsActivityRule> list = getList(rule, "ruleLvl","activityInfo.acttCode");
		if(list!=null && !list.isEmpty()){
			Set<Integer> ids = new HashSet<>();
			Set<Integer> inputIds = new HashSet<>();
			if(ruleIds!=null && !"".equals(ruleIds)){
				for(String ruleId : ruleIds.split(",")){
					inputIds.add(Integer.parseInt(ruleId));
				}
				ids.addAll(inputIds);
				getAssoRuleIds(ids);
				List<CmsActivityRule> delList = new ArrayList<>();
				for(CmsActivityRule activityRule : list){
					for(Integer ruleId : ids){
						if(activityRule.getRuleId() == ruleId && !inputIds.contains(ruleId)){
							delList.add(activityRule);
						}
					}
				}
				list.removeAll(delList);
			}
		}
		
		return list;
	}

	private void getAssoRuleIds(Set<Integer> ruleIds){
		Map<String,Object> map = new HashMap<>();
		map.put("ruleId", ruleIds);
		List<CmsActivityRuleEx> exProps = getEntitysByParamMap(CmsActivityRuleEx.class, OperMode.IN, map);
		if(exProps != null && !exProps.isEmpty()){
			Set<Integer> exIds = new HashSet<>();
			for(CmsActivityRuleEx prop : exProps){
				exIds.add(prop.getGroup().getId());
			}
			
			map.clear();
			map.put("id", exIds);
			//map.put("status", new ParamStatusEnum[]{ParamStatusEnum.ON});
			List<CmsActivityRuleGroup> ruleExs = getEntitysByParamMap(CmsActivityRuleGroup.class, OperMode.IN, map);
			Set<Integer> tempRuleIds = new HashSet<>();
			for(CmsActivityRuleGroup ruleEx : ruleExs){
				Hibernate.initialize(ruleEx.getExProps());
				for(CmsActivityRuleEx prop : ruleEx.getExProps()){
					tempRuleIds.add(prop.getRuleId());
				}
			}
			tempRuleIds.removeAll(ruleIds);
			if(tempRuleIds.size()!=0){
				ruleIds.addAll(tempRuleIds);
				getAssoRuleIds(ruleIds);
			}
		}
	}

	@Override
	public List<String> getActivityTree(String acttCode) {
		List<CmsActivityInfo> list = getList(new CmsActivityInfo(), null);
		if(list!=null && !list.isEmpty()){
			List<String> treeList = new ArrayList<String>();
			for(CmsActivityInfo activityInfo : list){
				if(!acttCode.equals(activityInfo.getActtCode())){
					treeList.add("{id:\""+activityInfo.getActtCode()+"\",pId:\"999999\",name:\""+activityInfo.getActtNm()+"\"}");
					Hibernate.initialize(activityInfo.getActivityRules());
					if(activityInfo.getActivityRules()!=null && !activityInfo.getActivityRules().isEmpty()){
						for(CmsActivityRule activityRule : activityInfo.getActivityRules()){
							treeList.add("{id:\""+activityRule.getRuleId()+"\",pId:\""+activityInfo.getActtCode()+"\",name:\""+activityRule.getRuleNm()+"\",desc:\""+activityRule.getRuleDesc()+"\"}");
						}
					}
				}
			}
			return treeList;
		}
		return null;
	}

	@Override
	public void saveActivityRuleCopy(String acttCode, String ids) throws Exception {
		CmsActivityInfo activityInfo = new CmsActivityInfo();
		activityInfo.setActtCode(acttCode);
		if(ids!=null && !"".equals(ids)){
			for(String id : ids.split(",")){
				CmsActivityRule activityRule = getEntityById(CmsActivityRule.class, Integer.parseInt(id), true);
				CmsActivityRule rule = (CmsActivityRule) BeanInvokeUtils.cloneObject(activityRule, OperMode.OR, "ruleId","props","dealerProps","activityInfo","contrProp");
				
				rule.setActivityInfo(activityInfo);
				CmsContrRuleProp prop = (CmsContrRuleProp) BeanInvokeUtils.cloneObject(activityRule.getContrProp(), null, null);
				rule.setContrProp(prop);
				saveActivityRule(rule);
				
			}
		}
	}
}
