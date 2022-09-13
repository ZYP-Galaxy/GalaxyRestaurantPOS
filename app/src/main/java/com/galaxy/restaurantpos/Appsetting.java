package com.galaxy.restaurantpos;

public class Appsetting {

	private String _Setting_No;
	private String _Setting_Name;
	private String _Setting_Value;
	private String _Remark;
	private String _AddedOn;
	private String _AddedBy;

	public String getSetting_No() {
		return _Setting_No;
	}

	public void setSetting_No(String SettingNo) {
		this._Setting_No = SettingNo;
	}
	
	
	public String getSetting_Name() {
		return _Setting_Name;
	}

	public void setSetting_Name(String SettingName) {
		this._Setting_Name = SettingName;
	}
	
	
	public String getSetting_Value() {
		return _Setting_Value;
	}

	public void setSetting_Value(String SettingValue) {
		this._Setting_Value = SettingValue;
	}
	

	public String getRemark() {
		return _Remark;
	}

	public void setRemark(String Remark) {
		this._Remark = Remark;
	}
	
	public String getAddedon() {
		return _AddedOn;
	}

	public void setAddedOn(String Addedon) {
		this._AddedOn = Addedon;
	}
	
	public String getAddedBy() {
		return _AddedBy;
	}

	public void setAddedBy(String AddedBy) {
		this._AddedOn = AddedBy;
	}
}
