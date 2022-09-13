package com.galaxy.restaurantpos;

public class Area {
	private Integer _Area_ID;
	private String _Area_Name;	
	private String _Description;
	private String _Area_Code;
	private String _Remark;
	
	public Integer getArea_ID() {
        return _Area_ID;
    }
    public void setArea_ID(Integer Area_ID) {
        this._Area_ID = Area_ID;
    }
    public String getArea_Name() {

	    return _Area_Name;
    }
    public void setArea_Name(String Area_Name) {
        this._Area_Name = Area_Name;
    }
    public String getDescription() {

	    return _Description;
    }
    public void setDescription(String Description) {
        this._Description = Description;
    }
    public String getArea_Code() {

	    return _Area_Code;
    }
    public void setArea_Code(String Area_Code) {
        this._Area_Code = Area_Code;
    }
    public String getRemark() {
        return _Remark;
    }
    public void setRemark(String Remark) {
        this._Remark = Remark;
    }
}
