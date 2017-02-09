package com.liyun.car.workflow.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;

import com.liyun.car.core.spring.SpringContextBeanFactory;
import com.liyun.car.param.service.TaskService;


/**
 * activity监听器，处理待办任务池
 * @author zhoufei
 *
 */

@SuppressWarnings("serial")
public class ManagerTaskListener implements TaskListener,ExecutionListener {

	private TaskService taskService = SpringContextBeanFactory.getBean("taskServiceImpl");
	
	@Override
	public void notify(DelegateTask task) {
		taskService.saveTask(task);
	}

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		System.out.println(execution);
	}

}
