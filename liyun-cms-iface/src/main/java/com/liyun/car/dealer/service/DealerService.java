package com.liyun.car.dealer.service;

import java.util.List;

import com.liyun.car.common.entity.Page;
import com.liyun.car.dealer.entity.CmsDealer;
import com.liyun.car.dealer.entity.CmsDealerApproval;
import com.liyun.car.dealer.entity.SyDealer;
import com.liyun.car.dealer.entity.vo.VDealer;
import com.liyun.car.dealer.enums.DealerStatusEnum;
import com.liyun.car.hibernate.service.HibernateService;
import com.liyun.car.system.entity.SyUser;

public interface DealerService extends HibernateService{

	Page<VDealer> pageList(VDealer dealer, int pn, boolean am, SyUser user, DealerStatusEnum... enums);
	
	void updateCmsDealer(CmsDealer dealer);

	void saveCmsDealer(CmsDealer dealer, SyUser user);

	void saveStartProcess(CmsDealer dealer, SyDealer syDealer, CmsDealerApproval approval, SyUser user);

	void saveSyDealer(SyDealer dealer, Integer code, SyUser user);
	
	void deleteCmsDealer(CmsDealer dealer);

	void saveCompleteTask(VDealer vDealer, String taskId, CmsDealerApproval approval, SyUser user);

	List<VDealer> getList(VDealer dc, boolean am, SyUser user, DealerStatusEnum... onOrPreLineStatus);

	List<String> getDealerTree();

	void updateEndProcess(String taskId, Integer dealerCode) throws Exception;

	List<SyDealer> getSyList(boolean am, SyUser user);
}
