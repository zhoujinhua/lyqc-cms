package com.liyun.car.fee.service;

import com.liyun.car.fee.entity.CmsSerfeeMonInfo;
import com.liyun.car.hibernate.service.HibernateService;
import com.liyun.car.system.entity.SyUser;

public interface DealerSerfeeMonFileService extends HibernateService {

	void saveFileAUpdateBill(String feeMons, String fileName, String filePath, SyUser user);

	void saveSerfeeMonFile(CmsSerfeeMonInfo info, String filePath);

}
