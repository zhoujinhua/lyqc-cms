package com.liyun.car.dealer.service;

import java.util.List;

import com.liyun.car.common.entity.Page;
import com.liyun.car.dealer.entity.CmsDealerApproval;
import com.liyun.car.dealer.entity.CmsDealerCompany;
import com.liyun.car.dealer.entity.CmsDealerFile;
import com.liyun.car.dealer.entity.SyDealer;
import com.liyun.car.dealer.entity.SyDealerCompany;
import com.liyun.car.dealer.entity.vo.VDealerCompany;
import com.liyun.car.dealer.enums.DealerStatusEnum;
import com.liyun.car.hibernate.service.HibernateService;
import com.liyun.car.system.entity.SyUser;

public interface DealerCompanyService extends HibernateService{
	
	Page<VDealerCompany> pageList(VDealerCompany dealerCompany, int pn, boolean am, SyUser user, DealerStatusEnum... enums);
	
	Page<SyDealerCompany> pageSyList(int pn );
	
	void saveCmsDealerCompany(CmsDealerCompany dealerCompany, SyUser user);

	void saveStartProcess(CmsDealerCompany dealerCompany, SyDealerCompany syDealerCompany, CmsDealerApproval approval, SyUser user);

	void saveSyDealerCompany(SyDealerCompany dealerCompany, Integer code, SyUser user);
	
	void deleteCmsDealerCompany(CmsDealerCompany dealerCompany);

	void saveCompleteTask(VDealerCompany dealerCompany, String taskId, CmsDealerApproval approval, SyUser user);

	CmsDealerFile saveCmsDealerFile(CmsDealerFile file);

	void updateCmsDealerCompany(CmsDealerCompany dealerCompany, SyUser user);

	List<VDealerCompany> getList(VDealerCompany company, boolean am, SyUser user, DealerStatusEnum... enums);

	void updateSuspendBill(SyDealerCompany dc);
	
	void updateEndProcess(String taskId, Integer companyCode) throws Exception;

	List<SyDealerCompany> getSyList(boolean am, SyUser user);
}
