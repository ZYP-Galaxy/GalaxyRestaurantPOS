package com.galaxy.restaurantpos;

public class Salesmen {
	private Integer _salesmen_id;
	private String _salesmen_name;	
	private String _short;
	private String _posuserid;
	private Integer salesmen_type;//added WHM [2020-05-11]
		
	public Integer getsalesmen_id() {
        return _salesmen_id;
    }
    public void setsalesmen_id(Integer salesmenid) {
        this._salesmen_id = salesmenid;
    }
    
    public String getsalesmen_name() {
        return _salesmen_name;
    }
    public void setsalesmen_name(String salesmenname) {
        this._salesmen_name = salesmenname;
    }
    
    public String getshortcode() {
        return _short;
    }
    public void setshortcode(String shortcode) {
        this._short = shortcode;
    }
    
    public String getposuserid() {
        return _posuserid;
    }
    public void setposuserid(String posuserid) {
        this._posuserid = posuserid;
    }

    public Integer getSalesmen_type() {
        return salesmen_type;
    }

    public void setSalesmen_type(Integer salesmen_type) {
        this.salesmen_type = salesmen_type;
    }
}
