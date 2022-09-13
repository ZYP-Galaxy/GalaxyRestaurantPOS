package com.galaxy.restaurantpos;

public class SelectedItemObj {
	private String _srno;
	private String _itemid;
	private String _name;	
	private String _qty;
	private String _amount;
	private String _status;
		
	public String getsrno() {
        return _srno;
    }
    public void setsrno(String sr) {
        this._srno = sr;
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
}
