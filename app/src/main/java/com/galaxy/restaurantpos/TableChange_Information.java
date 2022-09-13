package com.galaxy.restaurantpos;

public class TableChange_Information {
	private Integer _OldUserID;
	private Integer _NewUserID;	
	private Integer _OldTableID;
	private Integer _NewTableID;
	
	
	public Integer getOldUserID() {
        return _OldUserID;
    }
    public void setOldUserID(Integer OldUserID) {
        this._OldUserID = OldUserID;
    }
    
    public Integer getNewUserID() {
        return _NewUserID;
    }
    public void setNewUserID(Integer NewUserID) {
        this._NewUserID = NewUserID;
    }
    
    public Integer getOldTableID() {
        return _OldTableID;
    }
    public void setOldTableID(Integer OldTableID) {
        this._OldTableID = OldTableID;
    }
    
    public Integer getNewTableID() {
        return _NewTableID;
    }
    public void setNewTableID(Integer NewTableID) {
        this._NewTableID = NewTableID;
    }
}