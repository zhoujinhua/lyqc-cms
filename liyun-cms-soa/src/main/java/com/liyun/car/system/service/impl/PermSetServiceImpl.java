package com.liyun.car.system.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.liyun.car.common.enums.OperMode;
import com.liyun.car.common.enums.ParamStatusEnum;
import com.liyun.car.hibernate.service.impl.HibernateServiceSupport;
import com.liyun.car.system.entity.SyItem;
import com.liyun.car.system.entity.SyMenu;
import com.liyun.car.system.entity.SyPermSet;
import com.liyun.car.system.entity.SyUser;
import com.liyun.car.system.service.PermSetService;

import net.sf.json.JSONArray;

@Service
public class PermSetServiceImpl extends HibernateServiceSupport implements PermSetService {

	@Override
	public String getPermSetTree(SyUser user) {
		user = getEntityById(SyUser.class,user.getUserId(),true);
		SyPermSet permset = new SyPermSet();
		permset.setPermStatus(ParamStatusEnum.ON);
		
		List<String> list = getPermTreeformat(permset, user);
		return JSONArray.fromObject(list).toString();
	}
	
	private List<String> getPermTreeformat(SyPermSet permset , SyUser user) {
		List<String> list = new ArrayList<String>();
		list.add("{id:\"999999\",pId:\"0\",name:\"权限集列表\"}");

		List<Integer> permSetIdList = new ArrayList<Integer>();
		if (user.getPermSets() != null && !user.getPermSets().isEmpty()) {
			for (SyPermSet permSet : user.getPermSets()) {
				permSetIdList.add(permSet.getId());
			}
		}
		List<SyPermSet> permSets = getEntitysByParams(permset,OperMode.EQ, "permStatus");
		if (permSets != null && !permSets.isEmpty()) {
			for (SyPermSet permSet : permSets) {
				if (permSetIdList.contains(permSet.getId())) {
					list.add("{id:\"" + permSet.getId() + "\",pId:\"999999\",name:\"" + permSet.getPermName()
							+ "\",checked:true}");
				} else {
					list.add("{id:\"" + permSet.getId() + "\",pId:\"999999\",name:\"" + permSet.getPermName() + "\"}");
				}
			}
		}
		return list;
	}

	@Override
	public void updatePermSet(SyPermSet permSet, String[] split) {
		List<SyItem> itemList = new ArrayList<SyItem>();
		for(String part : split){
			itemList.add(getSession().find(SyItem.class, part));
		}
		permSet.setItems(itemList);
		updateEntity(permSet,"items");
	}

	@Override
	public String getMenuTree(SyPermSet permSet) {
		permSet = getSession().find(SyPermSet.class, permSet.getId());
		List<String> list = getMenuTreeformat(permSet);
		return JSONArray.fromObject(list).toString();
	}
	
	private List<String> getMenuTreeformat(SyPermSet permSet) {
		List<String> list = new ArrayList<String>();
		
		List<String> itemIdList = new ArrayList<String>();
		if(permSet.getItems()!=null && !permSet.getItems().isEmpty()){
			for(SyItem item : permSet.getItems()){
				itemIdList.add(item.getItemId());
			}
		}
		List<SyMenu> menuList = getSession().getCriteria(SyMenu.class).getResultList();
		if(menuList!=null && !menuList.isEmpty()){
			for(SyMenu menu : menuList){
				list.add("{id:\""+menu.getMenuId()+"\",pId:\""+0+"\",name:\""+menu.getMenuTitle()+"\"}");
			}
		}
		List<SyItem> itemList = getSession().getCriteria(SyItem.class).getResultList();
		if(itemList!=null && !itemList.isEmpty()){
			for(SyItem item : itemList){
				if(itemIdList.contains(item.getItemId())){
					list.add("{id:\""+item.getItemId()+"\",pId:\""+item.getMenuId()+"\",name:\""+item.getItemTitle()+"\",checked:true}");
				} else {
					list.add("{id:\""+item.getItemId()+"\",pId:\""+item.getMenuId()+"\",name:\""+item.getItemTitle()+"\"}");
				}
			}
		}
		
		return list;
	}

	@SuppressWarnings("unchecked")
	public Map<String,List<SyItem>> getAvaliableUserPermItem(SyUser user) {
		Map<String,List<SyItem>> map = new HashMap<String, List<SyItem>>();
		
		String sql = "SELECT DISTINCT ITEM_ID FROM SY_PERM_ITEM WHERE PERM_ID IN (SELECT PERM_ID FROM SY_USER_PERMSET WHERE USER_ID="+user.getUserId()+") ORDER BY ITEM_ID ASC";
		List<Object> list = getSession().createNativeQuery(sql).getResultList();
		if(list!=null && !list.isEmpty()){
			for(Object obj : list){
				String itemId = obj.toString();
				SyItem syItem = getSession().find(SyItem.class, itemId);
				
				if(syItem!=null){
					if(map.get(syItem.getMenuId())==null){
						map.put(syItem.getMenuId(), new ArrayList<SyItem>());
					}
					map.get(syItem.getMenuId()).add(syItem);
				}
			}
		}
		return map;
	}
	
	@Override
	public String getUserMenuTree(SyUser user) {
		Map<String,List<SyItem>> map = getAvaliableUserPermItem(user);
		List<SyMenu> menuList =  getSession().getCriteria(SyMenu.class).getResultList();
		List<SyMenu> tempList = new ArrayList<SyMenu>();
		
		for(SyMenu menu : menuList){
			if(map.get(menu.getMenuId())!=null){
				menu.setItems(map.get(menu.getMenuId()));
			} else {
				tempList.add(menu);
			}
		}
		
		menuList.removeAll(tempList);
		return JSONArray.fromObject(menuList).toString();
	}

	@Override
	public void savePermSet(SyPermSet permSet, SyUser user) {
		if(permSet.getId()!=null){
			permSet.setUpdateTime(new Date());
			updateEntity(permSet, "permStatus","permName","updateTime","permDesc");
		} else {
			permSet.setCreateTime(new Date());
			permSet.setCreateUser(user.getTrueName());
			
			saveEntity(permSet);
		}		
	}

}
