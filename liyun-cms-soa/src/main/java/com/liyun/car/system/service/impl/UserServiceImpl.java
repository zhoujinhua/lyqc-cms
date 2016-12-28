package com.liyun.car.system.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;

import com.liyun.car.common.entity.Page;
import com.liyun.car.common.enums.OperMode;
import com.liyun.car.common.utils.Md5Util;
import com.liyun.car.common.utils.StringUtils;
import com.liyun.car.hibernate.service.impl.HibernateServiceSupport;
import com.liyun.car.param.entity.SyDepartment;
import com.liyun.car.remote.utils.JHSendSmsUtil;
import com.liyun.car.system.entity.SyItem;
import com.liyun.car.system.entity.SyPermSet;
import com.liyun.car.system.entity.SyUser;
import com.liyun.car.system.entity.SyUserDealer;
import com.liyun.car.system.entity.SyUserDeparment;
import com.liyun.car.system.enums.SexEnum;
import com.liyun.car.system.enums.UserStatusEnum;
import com.liyun.car.system.enums.UserTypeEnum;
import com.liyun.car.system.service.UserService;

@Service
public class UserServiceImpl extends HibernateServiceSupport implements UserService {
	
	private static String PASSWORD_PLAINTEXT = "123456";
	
	@Override
	public Page<SyUser> pageList(SyUser user, int pn) {
		if(user!=null){
			return getSession().getCriteria(SyUser.class).addRestriction(user, OperMode.EQ, "userStatus","userName","trueName","userType").getResultList(pn);
		} else {
			return getSession().getCriteria(SyUser.class).getResultList(pn);
		}
	}

	@Override
	public List<SyUser> getEntitysByParams(SyUser user,String... params) {
		if(user!=null){
			return getSession().getCriteria(SyUser.class).addRestriction(user, OperMode.EQ, params).getResultList();
		} else {
			return getSession().getCriteria(SyUser.class).getResultList();
		}
	}

	@Override
	public void updateEntity(SyUser user,boolean check, String... params) {
		if(check){
			checkParms(user);
		}
		user.setLastTime(new Date());

		updateEntity(user,"userName","address","trueName","sex","phone","email","cardType","cardId","birthday","postalCode","userType","lastTime","userDeparment");
		// 要启用事务
		SyUser oldSyUser = getEntityById(SyUser.class,user.getUserId(),true);
		oldSyUser.getSyUserDealers().clear();
		
		if (user.getUserType()!=null && user.getUserType().getValue().equals("DS")) {
			List<SyUserDealer> dealers = user.getSyUserDealers();
			for(SyUserDealer dealer : dealers){
				dealer.setUser(user);
				oldSyUser.getSyUserDealers().add(dealer);
			}
		}
	}
	
	private Integer getUserId(SyUser user){
		String sql = "SELECT MAX(USER_ID) AS USER_ID FROM SY_USER WHERE SUBSTRING(USER_ID,1,7) = ";
		String prefix = "";
		if(user.getUserType() == UserTypeEnum.M){
			prefix = user.getUserDeparment().getDepartmentId().toString();
			sql += "'"+ prefix +"'";
		} else {
			SyUserDealer userDealer = user.getSyUserDealers().get(0);
			prefix = userDealer.getDealer().getDealerCode().toString();
			sql += "'" + prefix +"'";
		}
		List<?> list = getSession().createNativeQuery(sql).setResultTransfer(Transformers.ALIAS_TO_ENTITY_MAP).getResultList();
		if(list!=null && !list.isEmpty()){
			Map<?,?> map = (Map<?,?>) list.get(0);
			if(map.get("USER_ID")==null){
				return Integer .parseInt(prefix + "001");
			}
			String userId = map.get("USER_ID").toString();
			String id = prefix + userId.substring(7,10);
			return Integer.parseInt(id)+1;
		}
		return Integer .parseInt(prefix + "001");
	}

	@Override
	public void saveEntity(SyUser user) {
		checkParms(user); //校验
		
		if(user.getUserId() == null){
			
			Integer id = getUserId(user);
			if(id == null){
				throw new RuntimeException("用户ID生成失败.");
			}
			user.setUserId(id);
			String pwd = PASSWORD_PLAINTEXT;
			String str = Md5Util.getMD5Str(pwd);
			user.setPassword(str);
			user.setUserStatus(UserStatusEnum.N);
			
			user.setCreateTime(new Date());
			user.setLastTime(new Date());
			
			if(user.getUserType() == UserTypeEnum.DS){
				user.setUserDeparment(null);
			}
			getSession().persist(user);
			
			if (user.getUserType().getValue().equals("M")) {
				SyUserDeparment syUserDeparment = user.getUserDeparment();
				SyDepartment deparment = getEntityById(SyDepartment.class,syUserDeparment.getDepartmentId(),false);
				if (deparment == null || !"1".equals(deparment.getStatus().getValue())) {
					throw new RuntimeException( "此部门不可用");
				}
				syUserDeparment.setUserId(user.getUserId());
				saveEntity(syUserDeparment);
			} else if (user.getUserType().getValue().equals("DS")) {
				List<SyUserDealer> dealers = user.getSyUserDealers();
				for (SyUserDealer syUserDealer : dealers) {
					syUserDealer.setUser(user);
				}
			}
			
			String person = user.getTrueName() + (user.getSex()== SexEnum.M?"先生":"女士");
			String userName = user.getUserName();
			String password = pwd;
			String phone = user.getPhone();
			String param = "#person#="+person+"&#userName#="+userName+"&#pwd#="+password;
			new JHSendSmsUtil(phone, "10715", param).run();
		} else {
			updateEntity(user, true, "");
		}
	}

	@Override
	public List<SyUser> getEntitysByIds(int...ids) {
		return (List<SyUser>) getSession().findEntitys(SyUser.class, ids);
	}
	
	@Override
	public SyUser getEntityById(int id) {
		return (SyUser) getSession().find(SyUser.class, id);
	}
	
	@Override
	public void updateUserPassword(SyUser user) {
		user = getEntityById(user.getUserId());
		if (user.getPhone() == null || "".equals(user.getPhone())) {
			throw new RuntimeException("用户未填写手机号！");
		}
		String password = Md5Util.getMD5Str(StringUtils.mathRandom(8));
		user.setPassword(password);
		updateEntity(user, false, "password");
		
		//发送短信
	}
	
	@Override
	public void updateSyUserPermSets(SyUser user, String[] permIds) {
		List<SyPermSet> permList = new ArrayList<SyPermSet>();
		for (String id : permIds) {
			SyPermSet permSet = getSession().find(SyPermSet.class, Integer.parseInt(id));
			permList.add(permSet);
		}
		user.setPermSets(permList);
		updateEntity(user, "permSets");
	}
	
	private void checkParms(SyUser user) {
		List<SyUser> list = getEntitysByParams(user, "userName");
		// 用户名
		if (user.getUserName() == null || ((user.getUserId() == null && list!=null && !list.isEmpty()) || (user.getUserId()!=null && list != null && list.size()>1 ))) {
			throw new RuntimeException("用户名重复或者为空!");
		}
		list = getEntitysByParams(user, "phone");
		// 手机
		if (user.getPhone() == null || ((user.getUserId() == null && list!=null && !list.isEmpty()) || (user.getUserId()!=null && list != null && list.size()>1 ))) {
			throw new RuntimeException("手机重复或者为空!");
		}
		list = getEntitysByParams(user, "email");
		// 邮箱
		if (user.getEmail() == null || ((user.getUserId() == null && list!=null && !list.isEmpty()) || (user.getUserId()!=null && list != null && list.size()>1 ))) {
			throw new RuntimeException("邮箱重复或者为空!");
		}

		if (user == null || user.getUserType() == null) {
			throw new RuntimeException("保存失败，用户类型不存在");
		}
		int userNameSize = user.getUserName().getBytes().length;
		if (userNameSize > 20) {
			throw new RuntimeException("保存失败，用户名超了20字符");
		}

		if (user.getUserType().getValue().equals("M")) {
			if (user.getUserDeparment() == null || user.getUserDeparment().getDepartmentId() == null) {
				throw new RuntimeException("保存失败，所属部门参数不存在");
			}
			if (StringUtils.isBlank(user.getUserDeparment().getUserPostion())) {
				throw new RuntimeException("保存失败，用户角色参数不存在");
			}
		} else if (user.getUserType().getValue().equals("DS")) {
			if (user.getSyUserDealers().isEmpty()) {
				throw new RuntimeException("保存失败，所属门店参数不存在");
			}
			for (SyUserDealer syUserDealer : user.getSyUserDealers()) {
				if (StringUtils.isBlank(syUserDealer.getUserPostion()) || syUserDealer.getDealer().getDealerCode() == null) {
					throw new RuntimeException("保存失败，门店职责参数不存在");
				}
			}
		} else {
			throw new RuntimeException("保存失败，用户类型不存在");
		}
	}

	@Override
	public List<Integer> getUsersByPerm(String role, String itemId) {
		String sql = "SELECT DISTINCT U.USER_ID FROM SY_USER U INNER JOIN SY_USER_DEPARMENT UD ON U.USER_ID = UD.USER_ID " +
				"AND UD.USER_POSTION='"+role+"'" +
				"INNER JOIN SY_USER_PERMSET UP ON U.USER_ID = UP.USER_ID INNER JOIN SY_PERM_ITEM PI ON " +
				"PI.PERM_ID = UP.PERM_ID AND PI.ITEM_ID='"+itemId+"'";
		List<Object> list = getSession().createNativeQuery(sql).getResultList();
		List<Integer> idList = new ArrayList<Integer>();
		if(list!=null && !list.isEmpty()){
			for(Object obj : list){
				int userId = Integer.parseInt(obj.toString());
				idList.add(userId);
			}
		}
		return idList;
	}

	@Override
	public SyItem userHasItem(SyUser user, String itemId) {
		//user = getEntityById(SyUser.class, user.getUserId(), true);
		List<SyPermSet> permSets = user.getPermSets();
		if(permSets!=null && !permSets.isEmpty()){
			for(SyPermSet permSet : permSets){
				List<SyItem> list = permSet.getItems()	;
				if(list!=null && !list.isEmpty()){
					for(SyItem item : list){
						if(item.getItemId().equals(itemId)){
							return item;
						}
					}
				}
			}
		}
		return null;
	}

}
