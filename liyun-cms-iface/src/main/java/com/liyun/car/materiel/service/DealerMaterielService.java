package com.liyun.car.materiel.service;

import java.util.List;

import com.liyun.car.common.entity.Page;
import com.liyun.car.hibernate.service.HibernateService;
import com.liyun.car.materiel.entity.CmsDealerMateriel;
import com.liyun.car.materiel.entity.CmsDealerMaterielApproval;
import com.liyun.car.materiel.entity.CmsMaterielExpressHis;
import com.liyun.car.system.entity.SyUser;

public interface DealerMaterielService extends HibernateService {

	Page<CmsDealerMateriel> pageList(CmsDealerMateriel materiel, int pn, String mtrlTyp ,SyUser user);

	List<CmsDealerMateriel> getList(CmsDealerMateriel materiel, SyUser user);

	void saveStartProcess(CmsDealerMateriel materiel, SyUser user);

	void saveCompleteTask(CmsDealerMateriel materiel, String taskId, CmsDealerMaterielApproval approval, SyUser user);

	void saveDealerMateriel(CmsDealerMateriel materiel);

	CmsMaterielExpressHis getAvailExpressHis(CmsMaterielExpressHis his);

	void updateEndProcess(String taskId, int mtrlAppCode) throws Exception;
}
