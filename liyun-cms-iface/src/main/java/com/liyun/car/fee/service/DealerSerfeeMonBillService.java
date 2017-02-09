package com.liyun.car.fee.service;

import java.io.File;
import java.util.List;

import com.liyun.car.common.entity.Page;
import com.liyun.car.dealer.entity.SyDealerCompany;
import com.liyun.car.fee.entity.CmsDealerSerfeeMonBill;
import com.liyun.car.fee.enums.SerfeeBillActionEnum;
import com.liyun.car.fee.enums.SerfeeBillStatusEnum;
import com.liyun.car.hibernate.service.HibernateService;
import com.liyun.car.system.entity.SyUser;

public interface DealerSerfeeMonBillService extends HibernateService {

	Page<CmsDealerSerfeeMonBill> pageList(CmsDealerSerfeeMonBill bill, boolean am, SyUser user, int pn);

	void sendBills(String feeMons, CmsDealerSerfeeMonBill bill, SyUser user);

	void saveSendBillsHis(List<CmsDealerSerfeeMonBill> bills, String batchNo, SyUser user, List<String> mailList,
			List<String> mailCcList, SerfeeBillStatusEnum status, SyDealerCompany company, String feeMons);

	void updateBillSucc(String feeMons, SyUser user);

	File generSerfeeMonEbank(String feeMons, SyUser user) throws Exception;

	void updateSerfeeBillBatch(String feeMons, SyUser user, SerfeeBillStatusEnum nextStatusEnum,
			SerfeeBillActionEnum action);

	void updateSerfeeMonBill(CmsDealerSerfeeMonBill bill, SyUser user);

	CmsDealerSerfeeMonBill updateBillInvoiceRegister(CmsDealerSerfeeMonBill bill, SyUser user);

	CmsDealerSerfeeMonBill updateSuspendBill(CmsDealerSerfeeMonBill bill, SyUser user);

	CmsDealerSerfeeMonBill getSerfeeMonBillById(String feeMon, Integer companyCode, boolean b);

	List<CmsDealerSerfeeMonBill> getSerfeeMonBills(SyDealerCompany dc, SerfeeBillStatusEnum[] status, int i);

}
