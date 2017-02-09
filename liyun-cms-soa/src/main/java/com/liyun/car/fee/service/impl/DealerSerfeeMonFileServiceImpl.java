package com.liyun.car.fee.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liyun.car.fee.entity.CmsDealerSerfeeMonFile;
import com.liyun.car.fee.entity.CmsSerfeeMonInfo;
import com.liyun.car.fee.enums.SerfeeBillActionEnum;
import com.liyun.car.fee.enums.SerfeeBillStatusEnum;
import com.liyun.car.fee.service.DealerSerfeeMonBillService;
import com.liyun.car.fee.service.DealerSerfeeMonFileService;
import com.liyun.car.hibernate.service.impl.HibernateServiceSupport;
import com.liyun.car.system.entity.SyUser;

/**
 * 主动加事务的一些方法
 * @author zhoufei
 *
 */
@Service
public class DealerSerfeeMonFileServiceImpl extends HibernateServiceSupport implements DealerSerfeeMonFileService {

	@Autowired
	private DealerSerfeeMonBillService dealerSerfeeMonFileService;
	
	@Override
	@Transactional
	public void saveFileAUpdateBill(String feeMons, String fileName, String filePath, SyUser user) {
		CmsDealerSerfeeMonFile serfeeMonFile = new CmsDealerSerfeeMonFile(feeMons, fileName, 
				filePath, new Date(), user.getUserId()+"", user.getTrueName());
		saveEntity(serfeeMonFile);
		
		//修改状态并保存流水
		dealerSerfeeMonFileService.updateSerfeeBillBatch(feeMons, user, SerfeeBillStatusEnum.FKWA, SerfeeBillActionEnum.CODE6);
	}

	@Override
	@Transactional
	public void saveSerfeeMonFile(CmsSerfeeMonInfo info, String filePath){
		info.setSerfeeDir(filePath);
		updateEntity(info, "serfeeDir");
	}
}
