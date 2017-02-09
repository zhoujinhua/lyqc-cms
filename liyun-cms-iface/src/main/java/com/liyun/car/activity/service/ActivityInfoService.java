package com.liyun.car.activity.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.liyun.car.activity.entity.CmsActivityInfo;
import com.liyun.car.hibernate.service.HibernateService;

public interface ActivityInfoService extends HibernateService {

	void saveActivityInfo(CmsActivityInfo activityInfo);

	void deleteActivityInfo(CmsActivityInfo activityInfo);

	List<String> getUploadTree(CommonsMultipartFile file) throws IOException;

	List<String> getDealerTree(CmsActivityInfo activityInfo);

}
