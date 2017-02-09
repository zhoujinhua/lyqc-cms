package com.liyun.car.system.entity;

import java.util.List;

/**
 * 菜单父表
 * @author zhoufei
 *
 */

public class SyMenu {


    private String menuId;
    private String menuTitle;
    private Long menuIndex;
    private String menuIcon;
    private List<SyItem> items;
    
    public SyMenu() {
    }

    public String getMenuId() {
        return this.menuId;
    }
    
    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getMenuTitle() {
        return this.menuTitle;
    }
    
    public void setMenuTitle(String menuTitle) {
        this.menuTitle = menuTitle;
    }

    public Long getMenuIndex() {
        return this.menuIndex;
    }
    
    public void setMenuIndex(Long menuIndex) {
        this.menuIndex = menuIndex;
    }

	public String getMenuIcon() {
		return menuIcon;
	}

	public void setMenuIcon(String menuIcon) {
		this.menuIcon = menuIcon;
	}

	public List<SyItem> getItems() {
		return items;
	}

	public void setItems(List<SyItem> items) {
		this.items = items;
	}

}