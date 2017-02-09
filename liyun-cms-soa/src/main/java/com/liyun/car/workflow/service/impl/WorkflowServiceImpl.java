package com.liyun.car.workflow.service.impl;

import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liyun.car.common.contants.Constants;
import com.liyun.car.common.entity.Page;
import com.liyun.car.common.enums.OperMode;
import com.liyun.car.common.utils.BeanInvokeUtils;
import com.liyun.car.dealer.entity.CmsDealer;
import com.liyun.car.dealer.entity.CmsDealerApproval;
import com.liyun.car.dealer.entity.CmsDealerCompany;
import com.liyun.car.dealer.entity.SyDealer;
import com.liyun.car.dealer.entity.SyDealerCompany;
import com.liyun.car.dealer.entity.vo.VDealer;
import com.liyun.car.dealer.entity.vo.VDealerCompany;
import com.liyun.car.dealer.enums.DealerActionEnum;
import com.liyun.car.dealer.enums.DealerStatusEnum;
import com.liyun.car.fee.entity.CmsDealerSerfeeFlow;
import com.liyun.car.fee.entity.CmsSerfeeMonInfo;
import com.liyun.car.fee.enums.SerfeeActionEnum;
import com.liyun.car.fee.enums.SerfeeStatusEnum;
import com.liyun.car.hibernate.hibernate.CrapCriteria;
import com.liyun.car.hibernate.service.impl.HibernateServiceSupport;
import com.liyun.car.materiel.entity.CmsDealerMateriel;
import com.liyun.car.materiel.entity.CmsDealerMaterielApproval;
import com.liyun.car.materiel.enums.DealerMaterielActionEnum;
import com.liyun.car.materiel.enums.DealerMaterielStatusEnum;
import com.liyun.car.system.entity.SyUser;
import com.liyun.car.workflow.entity.vo.CmsMonitorBean;
import com.liyun.car.workflow.entity.vo.CmsTask;
import com.liyun.car.workflow.service.WorkflowService;
import com.liyun.car.workflow.utils.TaskSwitchUtil;

@Service
public class WorkflowServiceImpl extends HibernateServiceSupport implements WorkflowService {

	@Autowired
	private TaskService taskService;
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired
	private IdentityService identityService;
	
	@Autowired
	private HistoryService historyService;
	
	@Override
	public <T extends Serializable> Page<T> pageTasks(Class<T> clazz,SyUser user, int pn, String flowName, String...params) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, InstantiationException {
		// 根据当前人的ID查询
        TaskQuery taskQuery = taskService.createTaskQuery().taskCandidateOrAssigned(user.getUserId()+"").processDefinitionKey(flowName);
        Long count = taskQuery.count();
        List<Task> tasks = taskQuery.listPage(pn==1?0:(pn-1)*Constants.DEFAULT_PAGE_SIZE, pn*Constants.DEFAULT_PAGE_SIZE);
        List<T> list = new ArrayList<T>();
        
        for (Task task : tasks) {
            String processInstanceId = task.getProcessInstanceId();
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId)
            		.processDefinitionKey(flowName).active().singleResult();
            String businessKey = processInstance.getBusinessKey();
            T t = clazz.newInstance();
			CrapCriteria<T> cri = (CrapCriteria<T>) getSession().getCriteria(clazz);
            if(params.length == 2){
        		cri.add(Restrictions.or(Restrictions.eq(params[0], Integer.parseInt(businessKey)), Restrictions.eq(params[1], Integer.parseInt(businessKey)))).getSingleResult();
            } else {
            	if(clazz == CmsSerfeeMonInfo.class){
            		cri.add(Restrictions.eq(params[0], businessKey));
            	} else {
            		cri.add(Restrictions.eq(params[0], Integer.parseInt(businessKey)));
            	}
            }
            List<T> queryList = cri.getResultList();
            if(queryList!=null && !queryList.isEmpty()){
            	t = queryList.get(0);
            	Field field = clazz.getDeclaredField("task");
            	field.setAccessible(true);
            	
            	CmsTask cmsTask = TaskSwitchUtil.getTask(task);
            	field.set(t, cmsTask);
            	list.add(t);
            }
        }
        
        Page<T> page = new Page<T>();
        page.setCount(count.intValue());
        page.setItems(list);
        page.setNum(pn);
        
		return page;
	}

	@Override
	@Transactional
	public void claimTask(String taskId, int userId) {
		taskService.claim(taskId, userId+"");
	}

	@Override
	public void completeTask(String taskId, Map<String, Object> map) {
		taskService.complete(taskId, map);
	}

	@Override
	public Task getTask(String taskId) {
		return taskService.createTaskQuery().taskId(taskId).singleResult();
	}

	@Override
	public String nextTaskDocumentation(String taskId, String elString) {
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((RepositoryServiceImpl)repositoryService).getDeployedProcessDefinition(task.getProcessDefinitionId());  
        ExecutionEntity execution = (ExecutionEntity) runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();  
        String activitiId = execution.getActivityId(); 
        List<ActivityImpl> activitiList = def.getActivities();  
        String id = null;  
        for(ActivityImpl activityImpl:activitiList){    
            id = activityImpl.getId();   
            if(activitiId.equals(id)){  
                return nextTaskDocumentation(activityImpl, activityImpl.getId(),elString);  
            }  
        }  
        return null;
	}

	private String nextTaskDocumentation(ActivityImpl activityImpl, String activityId, String elString){  
        List<PvmTransition> outTransitions = activityImpl.getOutgoingTransitions();  
        List<PvmTransition> outTransitionsTemp = null;  
        for(PvmTransition tr:outTransitions){    
            PvmActivity ac = tr.getDestination(); //获取线路的终点节点    
            outTransitionsTemp = ac.getOutgoingTransitions();
            if(outTransitionsTemp.size() == 0){
            	//到终点
        		return (String) tr.getProperty("documentation");
        	} else {
        		if("exclusiveGateway".equals(ac.getProperty("type"))){  
        			if(outTransitionsTemp.size() == 1){  
        				return (String) outTransitionsTemp.get(0).getProperty("documentation");  
        			}else if(outTransitionsTemp.size() > 1){  
        				for(PvmTransition tr1 : outTransitionsTemp){  
        					Object s = tr1.getProperty("conditionText");  
        					if(elString.equals(s.toString().trim())){  
        						return (String) tr1.getProperty("documentation"); 
        					}  
        				}  
        			}  
        		}else {
        			//默认除了exclusiveGateway  没有其它的可能出现分支的情况，且流程流转必将导致状态转换
        			return (String) outTransitionsTemp.get(0).getProperty("documentation"); 
        		}  
        	}
        }   
        return null;  
    }

	@Override
	public void startProcess(String businessKey, String fLOW_NAME, String userId, Map<String, Object> variables) {
		try{
			//判断是不是重新提交申请
			TaskQuery taskQuery = taskService.createTaskQuery().processDefinitionKey(fLOW_NAME).processInstanceBusinessKey(businessKey).taskAssignee(userId);
			List<Task> tasks = taskQuery.list();
			//流程未结束，重新申请，对下线流程不影响
			if(tasks!=null && !tasks.isEmpty()){
				variables.put("reSubmit", true);
				this.completeTask(tasks.get(0).getId(), variables);
			} else {
				// 用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
				identityService.setAuthenticatedUserId(userId);
				runtimeService.startProcessInstanceByKey(fLOW_NAME, businessKey, variables);
			}
		}catch(Exception e){
			throw new RuntimeException(e.getMessage());
		}finally {
	        identityService.setAuthenticatedUserId(null);
	    }
	}

	@Override
	public void endProcess(String businessKey, String fLOW_NAME) {
		TaskQuery taskQuery = taskService.createTaskQuery().processDefinitionKey(fLOW_NAME).processInstanceBusinessKey(businessKey);
		List<Task> tasks = taskQuery.list();
		//流程未结束，结束流程
		if(tasks!=null && !tasks.isEmpty()){
			Map<String,Object> vars = new HashMap<String, Object>();
			vars.put("reSubmit", false);
			completeTask(tasks.get(0).getId(), vars);
		}
	}

	@Override
	public Task getTask(String userId, String bussinessKey) {
		return taskService.createTaskQuery().taskCandidateOrAssigned(userId).processInstanceBusinessKey(bussinessKey).singleResult();
	} 
	
	@Override
	public void saveDeployNewProcess(InputStream in) {
		DeploymentBuilder db = repositoryService.createDeployment();
		ZipInputStream zipInputStream = new ZipInputStream(in);
		
		db.addZipInputStream(zipInputStream);
		db.deploy();
	}
	
	@Override
	public void deleteProcessDef(String deploymentId) {
		repositoryService.deleteDeployment(deploymentId, true);
	}
	
	@Override
	public InputStream getResourceAsStream(String deploymentId,
			String resourceName) {
		return repositoryService.getResourceAsStream(deploymentId, resourceName);
	}
	
	@Override
	public InputStream getActivityImageByTaskId(String taskId) {
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String pdid = task.getProcessDefinitionId();
		ProcessDefinitionEntity pd = (ProcessDefinitionEntity) repositoryService
				.getProcessDefinition(pdid);
		System.out.println(pdid);
		System.out.println(pd.getDiagramResourceName());
		InputStream in = repositoryService.getResourceAsStream(pd.getDeploymentId(), pd.getDiagramResourceName());
		return in;
	}

	@Override
	public ActivityImpl getActivityImplByTaskId(String taskId) {
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String excId = task.getExecutionId();
		ExecutionEntity execution = (ExecutionEntity) runtimeService.createExecutionQuery().executionId(excId).singleResult();
		String activityId = execution.getActivityId();

		// taskID获取流程定义
		String pdid = task.getProcessDefinitionId();
		ProcessDefinitionEntity pd = (ProcessDefinitionEntity) repositoryService
				.getProcessDefinition(pdid);

		ActivityImpl acti = pd.findActivity(activityId);
		return acti;
	}
	
	/** 
     * 取回流程 
     *  
     * @param taskId 
     *            当前任务ID 
     * @param activityId 
     *            取回节点ID 
     * @throws Exception 
     */  
    public void callBackProcess(String taskId, String activityId)  
            throws Exception {  
        if (activityId !=null && !"".equals(activityId)) {  
            throw new Exception("目标节点ID为空！");  
        }  
  
        // 查找所有并行任务节点，同时取回  
        List<Task> taskList = findTaskListByKey(findProcessInstanceByTaskId(  
                taskId).getId(), findTaskById(taskId).getTaskDefinitionKey());  
        for (Task task : taskList) {  
            commitProcess(task.getId(), null, activityId);  
        }  
    }  
  
    /** 
     * 中止流程(特权人直接审批通过等) 
     *  
     * @param taskId 
     */  
    @Override
    public void endProcess(String taskId) throws Exception {  
        ActivityImpl endActivity = findActivitiImpl(taskId, "end");  
        commitProcess(taskId, null, endActivity.getId());  
    }  
  
  
    /** 
     * 转办流程 
     *  
     * @param taskId 
     *            当前任务节点ID 
     * @param userCode 
     *            被转办人Code 
     */  
    public void transferAssignee(String taskId, String userCode) {  
        taskService.setAssignee(taskId, userCode);  
    } 
    
    /** 
     * @param taskId 
     *            当前任务ID 
     * @param variables 
     *            流程变量 
     * @param activityId 
     *            流程转向执行任务节点ID<br> 
     *            此参数为空，默认为提交操作 
     * @throws Exception 
     */  
    private void commitProcess(String taskId, Map<String, Object> variables,  
            String activityId) throws Exception {  
        if (variables == null) {  
            variables = new HashMap<String, Object>();  
        }  
        // 跳转节点为空，默认提交操作  
        if (activityId ==null || "".equals(activityId)) {  
            taskService.complete(taskId, variables);  
        } else {// 流程转向操作  
            turnTransition(taskId, activityId, variables);  
        }  
    }  
  
    /** 
     * 清空指定活动节点流向 
     *  
     * @param activityImpl 
     *            活动节点 
     * @return 节点流向集合 
     */  
    private List<PvmTransition> clearTransition(ActivityImpl activityImpl) {  
        // 存储当前节点所有流向临时变量  
        List<PvmTransition> oriPvmTransitionList = new ArrayList<PvmTransition>();  
        // 获取当前节点所有流向，存储到临时变量，然后清空  
        List<PvmTransition> pvmTransitionList = activityImpl  
                .getOutgoingTransitions();  
        for (PvmTransition pvmTransition : pvmTransitionList) {  
            oriPvmTransitionList.add(pvmTransition);  
        }  
        pvmTransitionList.clear();  
  
        return oriPvmTransitionList;  
    }  
  
    /** 
     * 还原指定活动节点流向 
     *  
     * @param activityImpl 
     *            活动节点 
     * @param oriPvmTransitionList 
     *            原有节点流向集合 
     */  
    private void restoreTransition(ActivityImpl activityImpl,  
            List<PvmTransition> oriPvmTransitionList) {  
        // 清空现有流向  
        List<PvmTransition> pvmTransitionList = activityImpl  
                .getOutgoingTransitions();  
        pvmTransitionList.clear();  
        // 还原以前流向  
        for (PvmTransition pvmTransition : oriPvmTransitionList) {  
            pvmTransitionList.add(pvmTransition);  
        }  
    }  
  
    /** 
     * 流程转向操作 
     *  
     * @param taskId 
     *            当前任务ID 
     * @param activityId 
     *            目标节点任务ID 
     * @param variables 
     *            流程变量 
     * @throws Exception 
     */  
    private void turnTransition(String taskId, String activityId,  
            Map<String, Object> variables) throws Exception {  
        // 当前节点  
        ActivityImpl currActivity = findActivitiImpl(taskId, null);  
        // 清空当前流向  
        List<PvmTransition> oriPvmTransitionList = clearTransition(currActivity);  
  
        // 创建新流向  
        TransitionImpl newTransition = currActivity.createOutgoingTransition();  
        // 目标节点  
        ActivityImpl pointActivity = findActivitiImpl(taskId, activityId);  
        // 设置新流向的目标节点  
        newTransition.setDestination(pointActivity);  
  
        // 执行转向任务  
        taskService.complete(taskId, variables);  
        // 删除目标节点新流入  
        pointActivity.getIncomingTransitions().remove(newTransition);  
  
        // 还原以前流向  
        restoreTransition(currActivity, oriPvmTransitionList);  
    }  
    
    /** 
     * 根据任务ID获得任务实例 
     */  
    private TaskEntity findTaskById(String taskId) throws Exception {  
        TaskEntity task = (TaskEntity) taskService.createTaskQuery().taskId(  
                taskId).singleResult();  
        if (task == null) {  
            throw new Exception("任务实例未找到!");  
        }  
        return task;  
    }  
  
    /** 
     * 根据流程实例ID和任务key值查询所有同级任务集合 
     */  
    private List<Task> findTaskListByKey(String processInstanceId, String key) {  
        return taskService.createTaskQuery().processInstanceId(  
                processInstanceId).taskDefinitionKey(key).list();  
    }  
  
    /** 
     * 根据任务ID获取对应的流程实例 
     */  
    private ProcessInstance findProcessInstanceByTaskId(String taskId)  
            throws Exception {  
        // 找到流程实例  
        ProcessInstance processInstance = runtimeService  
                .createProcessInstanceQuery().processInstanceId(  
                        findTaskById(taskId).getProcessInstanceId())  
                .singleResult();  
        if (processInstance == null) {  
            throw new Exception("流程实例未找到!");  
        }  
        return processInstance;  
    }
  
    /** 
     * 根据任务ID获取流程定义 
     */  
    private ProcessDefinitionEntity findProcessDefinitionEntityByTaskId(  
            String taskId) throws Exception {  
        // 取得流程定义  
        ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)  
                .getDeployedProcessDefinition(findTaskById(taskId)  
                        .getProcessDefinitionId());  
  
        if (processDefinition == null) {  
            throw new Exception("流程定义未找到!");  
        }  
  
        return processDefinition;  
    }  
  
    /** 
     * 根据任务ID和节点ID获取活动节点 <br> 
     */  
    private ActivityImpl findActivitiImpl(String taskId, String activityId)  
            throws Exception {  
        // 取得流程定义  
        ProcessDefinitionEntity processDefinition = findProcessDefinitionEntityByTaskId(taskId);  
  
        // 获取当前活动节点ID  
        if (activityId==null || "".equals(activityId)) {  
            activityId = findTaskById(taskId).getTaskDefinitionKey();  
        }  
  
        // 根据流程定义，获取该流程实例的结束节点  
        if (activityId.toUpperCase().equals("END")) {  
            for (ActivityImpl activityImpl : processDefinition.getActivities()) {  
                List<PvmTransition> pvmTransitionList = activityImpl  
                        .getOutgoingTransitions();  
                if (pvmTransitionList.isEmpty()) {  
                    return activityImpl;  
                }  
            }  
        }  
  
        // 根据节点ID，获取对应的活动节点  
        ActivityImpl activityImpl = ((ProcessDefinitionImpl) processDefinition)  
                .findActivity(activityId);  
  
        return activityImpl;  
    }
    
    @Override
	public void updateSuspendProcess(String processInstanceId) {
		runtimeService.suspendProcessInstanceById(processInstanceId);
	}
	
	@Override
	public void updateActivateProcess(String processInstanceId) {
		runtimeService.activateProcessInstanceById(processInstanceId);
	}

	@Override
	public Page<ProcessDefinition> getProcessDefinitions(String resourceName, int pn) {
		ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery().orderByProcessDefinitionKey().desc().orderByProcessDefinitionVersion().desc();
        if(resourceName!=null && !"".equals(resourceName)){
        	query.processDefinitionResourceNameLike(resourceName);
        }
        Long totalCount = query.count();
        List<ProcessDefinition> list = query.listPage(pn==1?0:(pn-1)*Constants.DEFAULT_PAGE_SIZE, pn*Constants.DEFAULT_PAGE_SIZE);
        
        Page<ProcessDefinition> page = new Page<ProcessDefinition>(totalCount.intValue(), pn, list);
        
		return page;
	}
	
	/**
     * 读取运行中的流程
     *
     * @return
     */
	@Override
    public Page<ProcessInstance> findRunningProcessInstaces(String key,int pn) {
        ProcessInstanceQuery query = runtimeService.createProcessInstanceQuery().processDefinitionKey(key).active().orderByProcessInstanceId().desc();
        Long totalCount = query.count();
        List<ProcessInstance> list = query.listPage(pn==1?0:(pn-1)*Constants.DEFAULT_PAGE_SIZE, pn*Constants.DEFAULT_PAGE_SIZE);
        Page<ProcessInstance> page = new Page<ProcessInstance>(totalCount.intValue(), Constants.DEFAULT_PAGE_SIZE, list);

        return page;
    }

    /**
     * 读取已结束中的流程
     *
     * @return
     */
	@Override
    public Page<HistoricProcessInstance> findFinishedProcessInstaces(String key,int pn) {
        HistoricProcessInstanceQuery query = historyService.createHistoricProcessInstanceQuery().processDefinitionKey(key).finished().orderByProcessInstanceEndTime().desc();
        Long totalCount = query.count();
        List<HistoricProcessInstance> list = query.listPage(pn==1?0:(pn-1)*Constants.DEFAULT_PAGE_SIZE, pn*Constants.DEFAULT_PAGE_SIZE);
        Page<HistoricProcessInstance> page = new Page<HistoricProcessInstance>(totalCount.intValue(), Constants.DEFAULT_PAGE_SIZE, list);
        
        return page;
    }

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Serializable> Page<T> getFinishedList(Class<T> clazz, String key, int pn){
		Page<HistoricProcessInstance> page = findFinishedProcessInstaces(key, pn);
		List<HistoricProcessInstance> list = page.getItems();
		List<T> entityList = new ArrayList<T>();
		Page<T> ePage = null;
		
		if (list != null && !list.isEmpty()) {
			for (HistoricProcessInstance historicProcessInstance : list) {
				String businessKey = historicProcessInstance.getBusinessKey();
				if (businessKey == null) {
					continue;
				}
				T t = null;
				T t2 = null;
				if(clazz == CmsSerfeeMonInfo.class){
					t = (T) getEntityByCode(clazz, businessKey, false);
					entityList.add(t);
				} else if(clazz == CmsDealerMateriel.class){
					t = (T) getEntityById(clazz, Integer.parseInt(businessKey), false);
					entityList.add(t);
				} else {
					t = (T) getEntityById(clazz, Integer.parseInt(businessKey), false);
					if(t == null){
						String clazzName = clazz.getName();
						String clazzPath = clazzName.substring(0, clazzName.lastIndexOf(".",clazzName.lastIndexOf(".")-1));
						
						String simpleName = clazz.getSimpleName();
						simpleName = "Cms"+simpleName.substring(1,simpleName.length());
						
						clazzName = clazzPath +"."+ simpleName;
						try {
							Class<T> clazc = (Class<T>) Class.forName(clazzName);
							t = (T) getEntityById(clazc, Integer.parseInt(businessKey), false);
							
							t2 = clazz.newInstance();
							t2 = (T) BeanInvokeUtils.cloneMethodAll(t, t2, "amDealerCompanys");
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					entityList.add(t2);
				}
			}
			ePage = new Page<T>(page.getCount(), Constants.DEFAULT_PAGE_SIZE, entityList);
		}
		return ePage;
	}
	public static void main(String[] args) {
		String clazzName = VDealer.class.getName();
		String clazzPath = clazzName.substring(0, clazzName.lastIndexOf(".",clazzName.lastIndexOf(".")-1));
		
		String simpleName = VDealer.class.getSimpleName();
		simpleName = "Cms"+simpleName.substring(1,simpleName.length());
		
		clazzName = clazzPath +"."+ simpleName;
		System.out.println(clazzName);
	}
	@Override
	public <T extends Serializable> Page<T> getRunningList(Class<T> clazz, String key, int pn) throws Exception {
		Page<ProcessInstance> page = findRunningProcessInstaces(key, pn);
		List<ProcessInstance> list = page.getItems();
		List<T> entityList = new ArrayList<T>();
		Page<T> ePage = null;
		
		if (list != null && !list.isEmpty()) {
			for (ProcessInstance processInstance : list) {
				String businessKey = processInstance.getBusinessKey();
				if (businessKey == null) {
					continue;
				}
				// 设置当前任务信息
				List<Task> tasks = taskService.createTaskQuery()
						.processInstanceId(processInstance.getId()).active()
						.orderByTaskCreateTime().desc().list();
				T t = null;
				if(clazz == CmsSerfeeMonInfo.class){
					t = (T) getEntityByCode(clazz, businessKey, false);
				} else if(clazz == VDealer.class){
					List<VDealer> vList = getSession().getCriteria(VDealer.class).add(Restrictions.or(Restrictions.eq("dealerCode", Integer.parseInt(businessKey)), Restrictions.eq("tempDealerCode", Integer.parseInt(businessKey)))).getResultList();
					t = (T) vList.get(0);
				} else if(clazz == VDealerCompany.class){
					List<VDealerCompany> vList = getSession().getCriteria(VDealerCompany.class).add(Restrictions.or(Restrictions.eq("companyCode", Integer.parseInt(businessKey)), Restrictions.eq("tempCompanyCode", Integer.parseInt(businessKey)))).getResultList();
					t = (T) vList.get(0);
				} else {
					t = (T) getEntityById(clazz, Integer.parseInt(businessKey), false);
				}
				
				Field field = clazz.getDeclaredField("task");
				field.setAccessible(true);
				
				if(tasks!=null && !tasks.isEmpty()){
					CmsTask cmsTask = new CmsTask();
					BeanInvokeUtils.cloneMethod(tasks.get(0), cmsTask, "id","name","assignee","suspensionState","processInstanceId");
					cmsTask.setSuspensionState(tasks.get(0).isSuspended()?"0":"1");
					field.set(t, cmsTask);
				}
				entityList.add(t);
			}
			ePage = new Page<T>(page.getCount(), Constants.DEFAULT_PAGE_SIZE, entityList);
		}
		return ePage;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends Serializable> Page<T> getMonitorList(String status, String clazzName, int pn) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, Exception {
		Class<T> clazz = null;
		Page<T> page = null;
		
		if(clazzName!=null && clazzName.equals("VDealerCompany")){
			clazz = (Class<T>) VDealerCompany.class;
		}
		if(clazzName!=null && clazzName.equals("VDealer")){
			clazz = (Class<T>) VDealer.class;
		}
		if(clazzName!=null && clazzName.equals("CmsDealerMateriel")){
			clazz = (Class<T>) CmsDealerMateriel.class;
		}
		if(clazzName!=null && clazzName.equals("CmsSerfeeMonInfo")){
			clazz = (Class<T>) CmsSerfeeMonInfo.class;
		}
		if(status!=null && !status.equals("")){
			if(status.equals("running")){
				page = getRunningList(clazz,String.valueOf(BeanInvokeUtils.invokeStaticField(clazz, "FLOW_NAME")), pn);
			} else {
				page = getFinishedList(clazz,String.valueOf(BeanInvokeUtils.invokeStaticField(clazz, "FLOW_NAME")), pn);
			}
		}
		List<CmsMonitorBean> list = new ArrayList<>();
		if(page!=null && page.getItems()!=null && !page.getItems().isEmpty()){
			for(T t : page.getItems()){
				if(t != null){
					Task task = (Task) BeanInvokeUtils.invokeMethod(t, "task");
					CmsTask cmsTask = new CmsTask();
					
					if(task!=null){
						BeanInvokeUtils.cloneMethod(task, cmsTask, "id","name","assignee","suspensionState","processInstanceId");
						cmsTask.setSuspensionState(task.isSuspended()?"0":"1");
					}
					
					Field field = clazz.getDeclaredField("task");
					field.setAccessible(true);
					field.set(t, null);
					CmsMonitorBean bean = new CmsMonitorBean(t, cmsTask);
					list.add(bean);
				}
			}
			page.setItems( (List<T>) list);
		} else {
			page = new Page<>();
			page.setItems((List<T>) new ArrayList<>());
			page.setCount(0);
		}
		return page;
	}

	@Override
	public void updateForceEnd(String clazzName, String taskId, String code) throws NumberFormatException, Exception {
		if(clazzName!=null && clazzName.equals("VDealerCompany")){
			updateEndCompanyProcess(taskId,Integer.parseInt(code));
		}
		if(clazzName!=null && clazzName.equals("VDealer")){
			updateEndDealerProcess(taskId,Integer.parseInt(code));
		}
		if(clazzName!=null && clazzName.equals("CmsDealerMateriel")){
			updateEndMaterielProcess(taskId,Integer.parseInt(code));
		}
		if(clazzName!=null && clazzName.equals("CmsSerfeeMonInfo")){
			updateEndSerfeeProcess(taskId,code);
		}
	}
	
	public void updateEndCompanyProcess(String taskId, int companyCode) throws Exception {
		VDealerCompany vDealerCompany = new VDealerCompany(companyCode);
		vDealerCompany.setTempCompanyCode(companyCode);
		vDealerCompany = getEntitysByParams(vDealerCompany, OperMode.OR, "companyCode","tempCompanyCode").get(0);
		
		CmsDealerApproval approval = new CmsDealerApproval();
		approval.setCode(companyCode);
		approval.setCrtTime(new Date());
		approval.setPreStt(vDealerCompany.getStatus());
		approval.setAction(DealerActionEnum.CONLINE);
		approval.setRemark("强制结束流程，状态退回.");
		
		if(vDealerCompany.getStatus().getOnLineFlag().equals("1")){
			if(vDealerCompany.getStatus() == DealerStatusEnum.ONLINEPRE && vDealerCompany.getCompanyCode().toString().length()==5){
				throw new RuntimeException("该经销商已经补充经销商代码，不允许强制结束流程.");
			} else {
				CmsDealerCompany cmsDealerCompany = getEntityById(CmsDealerCompany.class, companyCode, false);
				cmsDealerCompany.setStatus(DealerStatusEnum.NEW);
				
				approval.setNextStt(DealerStatusEnum.NEW);
			}
		} else {
			SyDealerCompany syDealerCompany = getEntityById(SyDealerCompany.class, companyCode, false);
			syDealerCompany.setStatus(DealerStatusEnum.ONLINE);
			
			approval.setNextStt(DealerStatusEnum.ONLINE);
		}
		
		saveEntity(approval);
		endProcess(taskId);
	}

	public void updateEndDealerProcess(String taskId, int dealerCode)  throws Exception{
		VDealer vDealer = new VDealer(dealerCode);
		vDealer.setTempDealerCode(dealerCode);
		vDealer = getEntitysByParams(vDealer, OperMode.OR, "dealerCode","tempDealerCode").get(0);
		
		CmsDealerApproval approval = new CmsDealerApproval();
		approval.setCode(dealerCode);
		approval.setCrtTime(new Date());
		approval.setPreStt(vDealer.getStatus());
		approval.setAction(DealerActionEnum.DONLINE);
		approval.setRemark("强制结束流程，状态退回.");
		
		if(vDealer.getStatus().getOnLineFlag().equals("1")){
			if(vDealer.getStatus() == DealerStatusEnum.ONLINEPRE && vDealer.getDealerCode().toString().length()==8){
				throw new RuntimeException("该经销商门店已经补充经销商代码，不允许强制结束流程.");
			} else {
				CmsDealer cmsDealer = getEntityById(CmsDealer.class, dealerCode, false);
				cmsDealer.setStatus(DealerStatusEnum.NEW);
				
				approval.setNextStt(DealerStatusEnum.NEW);
			}
		} else {
			SyDealer syDealer = getEntityById(SyDealer.class, dealerCode, false);
			syDealer.setStatus(DealerStatusEnum.ONLINE);
			
			approval.setNextStt(DealerStatusEnum.ONLINE);
		}
		
		saveEntity(approval);
		endProcess(taskId);
	}

	public void updateEndMaterielProcess(String taskId, int mtrlAppCode)  throws Exception{
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
		endProcess(taskId);
	}

	public void updateEndSerfeeProcess(String taskId, String feeMon)  throws Exception{
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
		endProcess(taskId);
	}
 }
