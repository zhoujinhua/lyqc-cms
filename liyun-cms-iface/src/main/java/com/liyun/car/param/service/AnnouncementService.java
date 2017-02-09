package com.liyun.car.param.service;

import com.liyun.car.common.entity.Page;
import com.liyun.car.hibernate.service.HibernateService;
import com.liyun.car.param.entity.SyAnnouncement;
import com.liyun.car.system.entity.SyUser;

public interface AnnouncementService extends HibernateService{

	SyAnnouncement saveAnnouncement(SyAnnouncement announcement, SyUser user);

	Page<SyAnnouncement> pageList(SyAnnouncement announcement, SyUser user, int pn);

	void updateAnnouncement(SyAnnouncement announcement);

}
