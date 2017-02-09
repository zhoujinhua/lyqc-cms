package com.liyun.car.param.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.task.IdentityLink;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liyun.car.common.entity.Page;
import com.liyun.car.common.enums.BooleanEnum;
import com.liyun.car.common.enums.OperMode;
import com.liyun.car.common.service.Enum;
import com.liyun.car.common.utils.DateUtil;
import com.liyun.car.dealer.enums.DealerActionEnum;
import com.liyun.car.dealer.enums.DealerStatusEnum;
import com.liyun.car.fee.enums.SerfeeActionEnum;
import com.liyun.car.fee.enums.SerfeeBillActionEnum;
import com.liyun.car.fee.enums.SerfeeBillStatusEnum;
import com.liyun.car.fee.enums.SerfeeStatusEnum;
import com.liyun.car.hibernate.service.impl.HibernateServiceSupport;
import com.liyun.car.hibernate.utils.EnumUtils;
import com.liyun.car.param.entity.SyTask;
import com.liyun.car.param.enums.TaskStatusEnum;
import com.liyun.car.param.enums.TaskTypeEnum;
import com.liyun.car.param.service.TaskService;
import com.liyun.car.system.entity.SyUser;
import com.liyun.car.system.service.UserService;

@Service
public class TaskServiceImpl extends HibernateServiceSupport implements TaskService {

	@Autowired
	private UserService userService;
	
	private String genHql(SyTask task, SyUser user) {
		StringBuffer sb = new StringBuffer(" from SyTask u where 1=1 ");
		if(task!=null){
			if(task.getType()!=null){
				sb.append(" and u.type = '"+task.getType().getValue()+"'");
			}
			if(task.getCreateTime()!=null){
				sb.append(" and date_format(u.createTime,'%Y-%m-%d') = '"+DateUtil.formatDay(task.getCreateTime())+"'");
			}
		}
		if(user!=null){
			sb.append(" and u.userId = "+user.getUserId());
			sb.append(" and (u.status = '"+TaskStatusEnum.PRE.getValue()+"' or (u.status = '"+TaskStatusEnum.RUN.getValue()+"' and isHandler = '"+BooleanEnum.YES.getValue()+"'))");
		}
		sb.append(" order by createTime desc");
		return sb.toString();
	}
	
	@Override
	public Page<SyTask> pageList(SyTask task, SyUser user, int pn) {
		String sql = genHql(task, user);
		return pageList(pn, sql);
	}

	private String genSql(SyUser user, String subject, String crtTime){
		String sql = "SELECT QW.FEE_MON,QW.COMPANY_CODE,QW.PRE_STT,QW.NEXT_STT,QW.ACTION,QW.TYP,QW.REMARK,QW.TRUE_NAME,QW.CRT_TIME,QW.SUBJECT";
		sql += " FROM ("+
				 " SELECT Q1.FEE_MON,Q1.COMPANY_CODE,Q1.PRE_STT,Q1.NEXT_STT,Q1.ACTION,Q1.TYP,Q1.REMARK,Q1.TRUE_NAME,Q1.CRT_TIME,3 SUBJECT FROM CMS_DEALER_SERFEE_MON_BILL_FLOW Q1" +
				 " WHERE Q1.USER_NAME='"+user.getUserId()+"'"+
				 " UNION "+
				 " SELECT Q2.FEE_MON,'',Q2.PRE_STT,Q2.NEXT_STT,Q2.ACTION,Q2.TYP,Q2.REMARK,Q2.TRUE_NAME,Q2.CRT_TIME,2 SUBJECT FROM CMS_DEALER_SERFEE_FLOW Q2"+
				 " WHERE Q2.USER_NAME='"+user.getUserId()+"'"+
				 " UNION "+
				 " SELECT '',Q3.CODE,Q3.PRE_STT,Q3.NEXT_STT,Q3.ACTION,Q3.TYP,Q3.REMARK,Q3.TRUE_NAME,Q3.CRT_TIME,1 SUBJECT FROM CMS_DEALER_APPROVAL Q3" +
				 " WHERE Q3.USER_NAME='"+user.getUserId()+"'"+
				 ") QW WHERE 1=1";
		if(subject!=null && !"".equals(subject)){
			sql += " AND QW.SUBJECT = '"+subject+"'";
		}
		if(crtTime!=null && !"".equals(crtTime)){
			sql += " AND DATE_FORMAT(QW.CRT_TIME,'%Y-%m-%d') = '"+crtTime+"'";
		}
		sql += " ORDER BY QW.CRT_TIME DESC";
		return sql;
	}
	
	@Override
	public Page<?> pageList(SyUser user, String subject, String crtTime, int pn) {
		String sql = genSql(user, subject, crtTime);
		
		Page<?> page = getSession().createNativeQuery(sql).setResultTransfer(Transformers.ALIAS_TO_ENTITY_MAP).getResultList(pn);
		if(page != null && page.getItems()!=null){
			for(int i = 0;i<page.getItems().size();i++){
				Map<String,?> map = (Map<String, ?>) page.getItems().get(i);
				//经销商上下线流水
				if(map.get("SUBJECT").toString().equals("1")){
					mapEnumSetName(map, "PRE_STT", DealerStatusEnum.class);
					mapEnumSetName(map, "NEXT_STT", DealerStatusEnum.class);
					mapEnumSetName(map, "ACTION", DealerActionEnum.class);
					mapEnumSetName(map, "TYP", BooleanEnum.class);
				}
				//服务费审核流水
				if(map.get("SUBJECT").toString().equals("2")){
					mapEnumSetName(map, "PRE_STT", SerfeeStatusEnum.class);
					mapEnumSetName(map, "NEXT_STT", SerfeeStatusEnum.class);
					mapEnumSetName(map, "ACTION", SerfeeActionEnum.class);
					mapEnumSetName(map, "TYP", BooleanEnum.class);
				}
				//账单操作流水
				if(map.get("SUBJECT").toString().equals("3")){
					mapEnumSetName(map, "PRE_STT", SerfeeBillStatusEnum.class);
					mapEnumSetName(map, "NEXT_STT", SerfeeBillStatusEnum.class);
					mapEnumSetName(map, "ACTION", SerfeeBillActionEnum.class);
					mapEnumSetName(map, "TYP", BooleanEnum.class);
				}
			}
		}
		return page;
	}

	@SuppressWarnings("unchecked")
	public void mapEnumSetName(@SuppressWarnings("rawtypes") Map map,String index,@SuppressWarnings("rawtypes") Class clazz){
		
		if(map.get(index)!=null && !"".equals(map.get(index)) && !"null".equals(map.get(index))){
			Object enumObj = EnumUtils.valueOf(clazz, map.get(index).toString());
			if(enumObj!=null){
				if(enumObj instanceof Enum){
					map.put(index, ((Enum)enumObj).getName()) ;
				}
			}
		}
	}
	
	@Override
	public void saveTask(DelegateTask task) {
		String processDefinitionId = task.getProcessDefinitionId();
		String flowName = processDefinitionId.split(":")[0];
		TaskTypeEnum taskType = EnumUtils.valueOf(TaskTypeEnum.class, flowName);
		String businessKey = task.getExecution().getProcessBusinessKey();
		
		if(task.getEventName().equals("create")){
			Set<IdentityLink> set = task.getCandidates();
			if(set!=null && !set.isEmpty()){
				for(java.util.Iterator<IdentityLink> iter = set.iterator();iter.hasNext();){
					IdentityLink identityLink = (IdentityLink) iter.next();
					String groupId = identityLink.getGroupId();
					List<Integer> userIdList = userService.getUsersByPerm(groupId,taskType.getItemId());
					if(userIdList!=null && !userIdList.isEmpty()){
						for(Integer userId : userIdList){
							SyTask cmsTask = new SyTask("编号(批次):"+businessKey+" "+taskType.getName(), taskType, TaskStatusEnum.PRE, "您有待处理的"+taskType.getName()+"任务,任务编号(批次):"+businessKey+",目前阶段："+task.getExecution().getCurrentActivityName()+",请查看并及时处理.", new Date(), businessKey, userId);
							saveEntity(cmsTask);
						}
					}
				}
			}
			String assignee = task.getAssignee();
			if(assignee!=null && !"".equals(assignee)){
				SyTask cmsTask = new SyTask("编号(批次):"+businessKey+" "+taskType.getName(), taskType, TaskStatusEnum.PRE, "您有待处理的"+taskType.getName()+"任务,任务编号(批次):"+businessKey+",目前阶段："+task.getExecution().getCurrentActivityName()+",请查看并及时处理.", new Date(), businessKey, Integer.parseInt(assignee));
				saveEntity(cmsTask);
			}
		}
		//任务分配时,修改所有相关任务为处理中
		SyTask syTask = new SyTask();
		syTask.setAssoCode(businessKey);
		if(task.getEventName().equals("assignment")){
			syTask.setStatus(TaskStatusEnum.PRE);
			List<SyTask> tasks = getEntitysByParams(syTask, OperMode.EQ, "assoCode","status");
			if(tasks!=null && !tasks.isEmpty()){
				for(SyTask cmsTask : tasks){
					if(cmsTask.getUserId() == Integer.parseInt(task.getAssignee())){
						cmsTask.setIsHandler(BooleanEnum.YES);
					} else {
						cmsTask.setIsHandler(BooleanEnum.NO);
					}
					cmsTask.setStatus(TaskStatusEnum.RUN);
				}
			}
		}
		if(task.getEventName().equals("complete")){
			syTask.setStatus(TaskStatusEnum.RUN);
			List<SyTask> tasks = getEntitysByParams(syTask, OperMode.EQ, "assoCode","status");
			if(tasks!=null && !tasks.isEmpty()){
				for(SyTask cmsTask : tasks){
					cmsTask.setStatus(TaskStatusEnum.END);
					cmsTask.setFinishTime(new Date());
				}
			}
		}
	}
}
