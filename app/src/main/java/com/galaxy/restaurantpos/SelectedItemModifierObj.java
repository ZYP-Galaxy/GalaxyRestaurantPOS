package com.galaxy.restaurantpos;

public class SelectedItemModifierObj {
	private String _itemsr;
	private String _itemid;
	private String _name;	
	private String _qty;
	private String _price;
	private String _amount;
	private String _status;
	private int _sr;
	private String _max_price;
	private String _maincode;

    private String unit_type; //added WHM [2020-06-02] self order
	
	public String get_maincode() {
		return _maincode;
	}
	public void set_maincode(String _maincode) {
		this._maincode = _maincode;
	}
	public String get_max_price() {
		return _max_price;
	}
	public void set_max_price(String _max_price) {
		this._max_price = _max_price;
	}
	public int getsr() {
        return _sr;
    }
    public void setsr(int sr) {
        this._sr = sr;
    }
		
	public String getitemsr() {
        return _itemsr;
    }
    public void setitemsr(String itemsr) {
        this._itemsr = itemsr;
    }    
    public String getitemid() {
        return _itemid;
    }
    public void setitemid(String itemid) {
        this._itemid = itemid;
    }        
    public String getname() {
	    return _name;
    }
    public void setname(String name) {
        this._name = name;
    }    
    public String getqty() {
        return _qty;
    }
    public void setqty(String qty) {
        this._qty = qty;
    }
    public String getprice() {
        return _price;
    }
    public void setprice(String price) {
        this._price = price;
    }
    
    public String getamount() {
        return _amount;
    }
    public void setamount(String amount) {
        this._amount = amount;
    }
    public String getstatus() {
        return _status;
    }
    public void setstatus(String status) {
        this._status = status;
    }

    public String getUnit_type() {
        return unit_type;
    }

    public void setUnit_type(String unit_type) {
        this.unit_type = unit_type;
    }
}
