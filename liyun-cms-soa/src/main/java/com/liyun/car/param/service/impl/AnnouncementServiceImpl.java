package com.liyun.car.param.service.impl;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.liyun.car.common.entity.Page;
import com.liyun.car.common.enums.BooleanEnum;
import com.liyun.car.common.utils.DateUtil;
import com.liyun.car.hibernate.service.impl.HibernateServiceSupport;
import com.liyun.car.param.entity.SyAnnouncement;
import com.liyun.car.param.service.AnnouncementService;
import com.liyun.car.system.entity.SyUser;

@Service
public class AnnouncementServiceImpl extends HibernateServiceSupport implements AnnouncementService {

	@Override
	public SyAnnouncement saveAnnouncement(SyAnnouncement announcement, SyUser user) {
		if(announcement.getId()!=null){
			updateEntity(announcement, "headline","content","postType");
		} else {
			
			announcement.setUserId(user.getUserId());
			announcement.setUserName(user.getTrueName());
			announcement.setCreateTime(new Date());
			
			saveEntity(announcement);
		}
		return announcement;
	}

	private String genHql(SyAnnouncement announcement, SyUser user){
		StringBuffer sb = new StringBuffer();
		if(user != null){
			sb.append("select u from SyAnnouncement u left join u.users as user where (u.isAll = '1' or user.id = " + user.getUserId()+")");
			sb.append(" and u.isPublish = '1' and u.publishTime <= '"+DateUtil.formatDay(new Date())+"'");
		} else {
			sb.append(" from SyAnnouncement u where 1=1 ");
		}
		if(announcement!=null ){
			if(announcement.getHeadline()!=null && !"".equals(announcement.getHeadline()))	{
				sb.append(" and u.headline = '"+announcement.getHeadline()+"'");
			}
			if(announcement.getPostType()!=null){
				sb.append(" and u.postType='"+announcement.getPostType().getValue()+"'");
			}
			if(announcement.getIsTop()!=null){
				sb.append(" and u.isTop='"+announcement.getIsTop().getValue()+"'");
			}
			if(announcement.getIsPublish()!=null){
				sb.append(" and u.isPublish='"+announcement.getIsPublish().getValue()+"'");
			}
		}
		sb.append(" order by u.publishTime desc");
		return sb.toString();
	}
	
	@Override
	public Page<SyAnnouncement> pageList(SyAnnouncement announcement, SyUser user, int pn) {
		String hql = genHql(announcement, user);
		return pageList(pn, hql);
	}

	@Override
	public void updateAnnouncement(SyAnnouncement announcement) {
		updateEntity(announcement, "isAll","isPublish","isTop","isAttach","publishTime","topDay");
		SyAnnouncement syAnnouncement = getEntityById(SyAnnouncement.class, announcement.getId(), true);
		syAnnouncement.getUsers().clear();
		
		if(announcement.getIsAll() == BooleanEnum.NO){
			String userIds = announcement.getUserIds();
			for(String userId : userIds.split(",")){
				SyUser user = getEntityById(SyUser.class, Integer.parseInt(userId), false);
				
				syAnnouncement.getUsers().add(user);
			}
		}
		
	}

}
