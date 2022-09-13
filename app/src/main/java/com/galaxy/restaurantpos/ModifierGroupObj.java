package com.galaxy.restaurantpos;

public class ModifierGroupObj {
	private Integer _modifiergroupid;
	private String _name;	
	private String _short;
	
	public Integer getmodifiergroupid() {
        return _modifiergroupid;
    }
    public void setmodifiergroupid(Integer modifiergroupid) {
        this._modifiergroupid = modifiergroupid;
    }
    public String getname() {

        //return (LoginActivity.isUnicode)?Dictionary.Uniconverter.convert(_name):_name;
	    return _name;
    }
    public void setname(String name) {
        this._name = name;
    }
    
    public String getshort() {
        return _short;
    }
    public void setshort(String shortname) {
        this._short = shortname;
    }
}
