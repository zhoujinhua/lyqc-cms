package com.liyun.car.param.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.liyun.car.common.enums.BooleanEnum;
import com.liyun.car.common.serializer.EnumSerializer;
import com.liyun.car.param.enums.AnnouncementTypeEnum;
import com.liyun.car.system.entity.SyUser;


/**
 * 公告
 * @author zhoufei
 *
 */
public class SyAnnouncement implements Serializable{

	private Integer id;
	private String headline;
	private String content;
	private BooleanEnum isPublish;
	private BooleanEnum isTop;
	private Integer topDay;
	private BooleanEnum isAttach;
	private AnnouncementTypeEnum postType;
	private Date createTime;
	private Date publishTime;
	private Integer userId;
	private String userName;
	private String filename;
	private String fileAddr;
	private BooleanEnum isAll;
	
	private String userIds;
	private String userNames;
	
	@JsonIgnore
	private List<SyUser> users;
	private String isNew;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getHeadline() {
		return headline;
	}
	public void setHeadline(String headline) {
		this.headline = headline;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@JsonSerialize(using = EnumSerializer.class) 
	public BooleanEnum getIsPublish() {
		return isPublish;
	}
	public void setIsPublish(BooleanEnum isPublish) {
		this.isPublish = isPublish;
	}
	@JsonSerialize(using = EnumSerializer.class) 
	public BooleanEnum getIsTop() {
		return isTop;
	}
	public void setIsTop(BooleanEnum isTop) {
		this.isTop = isTop;
	}
	@JsonSerialize(using = EnumSerializer.class) 
	public AnnouncementTypeEnum getPostType() {
		return postType;
	}
	public void setPostType(AnnouncementTypeEnum postType) {
		this.postType = postType;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getFileAddr() {
		return fileAddr;
	}
	public void setFileAddr(String fileAddr) {
		this.fileAddr = fileAddr;
	}
	@JsonSerialize(using = EnumSerializer.class) 
	public BooleanEnum getIsAttach() {
		return isAttach;
	}
	public void setIsAttach(BooleanEnum isAttach) {
		this.isAttach = isAttach;
	}
	@JsonSerialize(using = EnumSerializer.class) 
	public BooleanEnum getIsAll() {
		return isAll;
	}
	public void setIsAll(BooleanEnum isAll) {
		this.isAll = isAll;
	}
	public List<SyUser> getUsers() {
		return users;
	}
	public void setUsers(List<SyUser> users) {
		this.users = users;
	}
	
	public Integer getTopDay() {
		return topDay;
	}
	public void setTopDay(Integer topDay) {
		this.topDay = topDay;
	}
	public String getUserIds() {
		if(this.users!=null && !this.users.isEmpty()){
			String part = "";
			for(SyUser user : this.users){
				part += user.getUserId()+",";
			}
			return part;
		}
		return userIds;
	}
	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}
	public String getUserNames() {
		if(this.users!=null && !this.users.isEmpty()){
			String part = "";
			for(SyUser user : this.users){
				part += user.getUserName()+"_"+user.getTrueName()+",";
			}
			return part;
		}
		return userNames;
	}
	public void setUserNames(String userNames) {
		this.userNames = userNames;
	}
	public String getIsNew() {
		if(this.publishTime!=null){
			Long between = new Date().getTime() - this.publishTime.getTime();
			Long hours = between/(1000*60*60);
			if(hours>0l && hours<(24*3)){
				return "1";
			}
		}
		return "0";
	}
}
