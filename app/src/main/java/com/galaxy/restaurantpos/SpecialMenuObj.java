package com.galaxy.restaurantpos;

public class SpecialMenuObj {
	
	private Integer MenuID;	
	private String MenuName;
	private String Remark;
	private Boolean ActiveMenus;
	private Boolean Rmt_Copy;
	private String Rmt_Branch;
	private Boolean mon;
	private Boolean tue;
	private Boolean wed;
	private Boolean thu;
	private Boolean fri;
	private Boolean sat;
	private Boolean sun;
	
	public Integer getMenuID() {
        return MenuID;
    }
    public void setMenuID(Integer MenuID) {
        this.MenuID = MenuID;
    }
    
    public String getMenuName() {

	    return MenuName;
    }

    public void setMenuName(String MenuName) {
        this.MenuName = MenuName;
    }
    
    public String getRemark() {
        return Remark;
    }
    
    
    public void setRemark(String Remark) {
        this.Remark = Remark;
    }
    
    public Boolean getActiveMenus() {
        return ActiveMenus;
    }
    public void setActiveMenus(Boolean ActiveMenus) {
        this.ActiveMenus = ActiveMenus;
    }
    
    public Boolean getRmt_Copy() {
        return Rmt_Copy;
    }
    public void setRmt_Copy(Boolean Rmt_Copy) {
        this.Rmt_Copy = Rmt_Copy;
    }
    
    public String getRmt_Branch() {
        return Rmt_Branch;
    }
    public void setRmt_Branch(String Rmt_Branch) {
        this.Rmt_Branch = Rmt_Branch;
    }
    
    public Boolean getmon() {
        return mon;
    }
    public void setmon(Boolean mon) {
        this.mon = mon;
    }
    
    public Boolean gettue() {
        return tue;
    }
    public void settue(Boolean tue) {
        this.tue = tue;
    }
    
    public Boolean getwed() {
        return wed;
    }
    public void setwed(Boolean wed) {
        this.wed = wed;
    }
    
    public Boolean getthu() {
        return thu;
    }
    public void setthu(Boolean thu) {
        this.thu = thu;
    }
    
    public Boolean getfri() {
        return fri;
    }
    public void setfri(Boolean fri) {
        this.fri = fri;
    }
    
    public Boolean getsat() {
        return sat;
    }
    public void setsat(Boolean sat) {
        this.sat = sat;
    }
    
    public Boolean getsun() {
        return sun;
    }
    public void setsun(Boolean sun) {
        this.sun = sun;
    }
}
