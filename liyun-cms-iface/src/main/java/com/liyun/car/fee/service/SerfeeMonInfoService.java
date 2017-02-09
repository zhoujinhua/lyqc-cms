package com.liyun.car.fee.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;

import com.liyun.car.fee.entity.CmsDealerSerfeeFlow;
import com.liyun.car.fee.entity.CmsSerfeeMonInfo;
import com.liyun.car.hibernate.service.HibernateService;
import com.liyun.car.system.entity.SyUser;

public interface SerfeeMonInfoService extends HibernateService{

	void saveStartProcess(CmsDealerSerfeeFlow flow, SyUser user, Map<String, Object> variables);

	void saveCompleteTask(CmsSerfeeMonInfo info, String taskId, CmsDealerSerfeeFlow flow, SyUser user);

	File genSerfeeCalFile(CmsSerfeeMonInfo info) throws Exception;

	File genSerfeeModelFile(CmsSerfeeMonInfo info) throws Exception;

	void saveFaConfirm(CmsDealerSerfeeFlow flow, SyUser user);

	void updateFeeMonFreeze(String feeMons, SyUser user);

	void updateEndProcess(String taskId, String feeMon) throws Exception;

	void updateSerfeeByModiFile(File file, String feeMon, SyUser user) throws FileNotFoundException;

}
