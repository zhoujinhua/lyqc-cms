package com.liyun.car.workflow.utils;

import org.activiti.engine.task.Task;

import com.liyun.car.common.utils.BeanInvokeUtils;
import com.liyun.car.workflow.entity.vo.CmsTask;

public class TaskSwitchUtil {

	public static CmsTask getTask(Task task){
		CmsTask cmsTask = new CmsTask();
		BeanInvokeUtils.cloneMethod(task, cmsTask, "id","name","assignee","suspensionState","processInstanceId");
		cmsTask.setSuspensionState(task.isSuspended()?"0":"1");
		
		return cmsTask;
	}
}
