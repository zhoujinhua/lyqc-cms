package com.liyun.car.workflow.entity.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import org.activiti.engine.task.DelegationState;
import org.activiti.engine.task.Task;

public class CmsTask implements Task,Serializable{

	private String id;
	private String name;
	private String assignee;
	private String suspensionState;
	private String processInstanceId;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAssignee() {
		return assignee;
	}
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}
	public String getSuspensionState() {
		return suspensionState;
	}
	public void setSuspensionState(String suspensionState) {
		this.suspensionState = suspensionState;
	}
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	@Override
	public String getDescription() {
		return null;
	}
	@Override
	public int getPriority() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public String getOwner() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getExecutionId() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getProcessDefinitionId() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Date getCreateTime() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getTaskDefinitionKey() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Date getDueDate() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getCategory() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getParentTaskId() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getTenantId() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getFormKey() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Map<String, Object> getTaskLocalVariables() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Map<String, Object> getProcessVariables() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setLocalizedName(String name) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setDescription(String description) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setLocalizedDescription(String description) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setPriority(int priority) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setOwner(String owner) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public DelegationState getDelegationState() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setDelegationState(DelegationState delegationState) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setDueDate(Date dueDate) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setCategory(String category) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void delegate(String userId) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setParentTaskId(String parentTaskId) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setTenantId(String tenantId) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setFormKey(String formKey) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean isSuspended() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
