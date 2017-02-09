package com.liyun.car.workflow.service;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Map;

import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import com.liyun.car.common.entity.Page;
import com.liyun.car.hibernate.service.HibernateService;
import com.liyun.car.system.entity.SyUser;

public interface WorkflowService extends HibernateService{

	<T extends Serializable> Page<T> pageTasks(Class<T> clazz,SyUser user, int pn, String flowName, String...params) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, InstantiationException;

	void claimTask(String taskId, int userId);
	
	void completeTask(String taskId, Map<String, Object> map);
	
	Task getTask(String taskId);

	String nextTaskDocumentation(String taskId, String elString);

	void startProcess(String businessKey, String fLOW_NAME, String userId, Map<String, Object> variables);

	void endProcess(String businessKey, String fLOW_NAME);
	
	Task getTask(String userId, String bussinessKey);

	void saveDeployNewProcess(InputStream in);

	void deleteProcessDef(String deploymentId);

	InputStream getResourceAsStream(String deploymentId, String resourceName);

	InputStream getActivityImageByTaskId(String taskId);

	ActivityImpl getActivityImplByTaskId(String taskId);

	void endProcess(String taskId) throws Exception;

	void updateSuspendProcess(String processInstanceId);

	void updateActivateProcess(String processInstanceId);

	Page<ProcessDefinition> getProcessDefinitions(String resourceName, int pn);

	Page<HistoricProcessInstance> findFinishedProcessInstaces(String key, int pn);

	Page<ProcessInstance> findRunningProcessInstaces(String key, int pn);

	<T extends Serializable> Page<T> getFinishedList(Class<T> clazz, String key, int pn);

	<T extends Serializable> Page<T> getRunningList(Class<T> clazz, String key, int pn) throws Exception;

	<T extends Serializable> Page<T> getMonitorList(String status, String clazzName, int pn) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, Exception;

	void updateForceEnd(String clazzName, String taskId, String code) throws NumberFormatException, Exception;
}
