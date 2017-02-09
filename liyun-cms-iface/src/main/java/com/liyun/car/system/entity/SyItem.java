package com.liyun.car.system.entity;




/**
 * SyItem entity. @author MyEclipse Persistence Tools
 */

public class SyItem  implements java.io.Serializable {


    // Fields    

	private static final long serialVersionUID = -3157971678094687125L;
	private String itemId;
	private String itemTitle;
	private String itemLocation;
	private String menuId;
	private Long wndWidth;
	private Long wndHeight;
	private String itemIcon;

    /** default constructor */
    public SyItem() {
    }

    public String getItemId() {
        return this.itemId;
    }
    
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemTitle() {
        return this.itemTitle;
    }
    
    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public String getItemLocation() {
        return this.itemLocation;
    }
    
    public void setItemLocation(String itemLocation) {
        this.itemLocation = itemLocation;
    }

    public String getMenuId() {
        return this.menuId;
    }
    
    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public Long getWndWidth() {
        return this.wndWidth;
    }
    
    public void setWndWidth(Long wndWidth) {
        this.wndWidth = wndWidth;
    }

    public Long getWndHeight() {
        return this.wndHeight;
    }
    
    public void setWndHeight(Long wndHeight) {
        this.wndHeight = wndHeight;
    }

    public String getItemIcon() {
        return this.itemIcon;
    }
    
    public void setItemIcon(String itemIcon) {
        this.itemIcon = itemIcon;
    }
}