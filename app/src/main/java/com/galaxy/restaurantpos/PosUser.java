package com.galaxy.restaurantpos;

public class PosUser {
	private Integer _userid;
	private String _short;	
	private String _name;
	private String _password;
	private Boolean _alluser;
	private Boolean _use_touchscreen;
    private Boolean _allow_itemcancel;	
    private Boolean _btnClearAll;
    private Boolean _btnBill;
    private Boolean _btnDetail;
    private Boolean _btnOthers;
    private Boolean _btnSplit;
	private Boolean _allow_edit_after_insert;
	private Integer _cashierprinterid;
	private Boolean _btnPrintBill;
	private Boolean _billnotprint;
	private Boolean _select_customer; //added by EKK on 15-01-2020
	private Boolean _create_customer; //added by EKK on 15-01-2020
	private Boolean _allow_itemtransfer;	//added by ZYP on 16-03-2020
	private Boolean _use_menuonly; //added by EKK on 18-09-2020
	private Boolean _use_foodtruck;
	private Boolean _use_foodtrucklocation;

	public Boolean get_allow_itemtransfer() {
		return _allow_itemtransfer;
	}

	public void set_allow_itemtransfer(Boolean _allow_itemtransfer) {
		this._allow_itemtransfer = _allow_itemtransfer;
	}
    
    public Boolean get_allow_edit_after_insert() {
		return _allow_edit_after_insert;
	}
	public void set_allow_edit_after_insert(Boolean _allow_edit_after_insert) {
		this._allow_edit_after_insert = _allow_edit_after_insert;
	}
    
	public Boolean get_btnClearAll() {
		return _btnClearAll;
	}
	public void set_btnClearAll(Boolean _btnClearAll) {
		this._btnClearAll = _btnClearAll;
	}
	public Boolean get_btnBill() {
		return _btnBill;
	}
	public void set_btnBill(Boolean _btnBill) {
		this._btnBill = _btnBill;
	}
	public Boolean get_btnDetail() {
		return _btnDetail;
	}
	public void set_btnDetail(Boolean _btnDetail) {
		this._btnDetail = _btnDetail;
	}
	public Boolean get_btnOthers() {
		return _btnOthers;
	}
	public void set_btnOthers(Boolean _btnOthers) {
		this._btnOthers = _btnOthers;
	}
	public Boolean get_btnSplit() {
		return _btnSplit;
	}
	public void set_btnSplit(Boolean _btnSplit) {
		this._btnSplit = _btnSplit;
	}
	public Integer getUserId() {
        return _userid;
    }
    public void setUserId(Integer userid) {
        this._userid = userid;
    }
    public String getShort() {
        return _short;
    }
    public void setShort(String user_short) {
        this._short = user_short;
    }
    public String getName() {
        return _name;
    }
    public void setName(String Name) {
        this._name = Name;
    }
    public String getPassword() {
        return _password;
    }
    public void setPassword(String Password) {
        this._password = Password;
    }
    
    public Boolean getAlluser() {
        return _alluser;
    }
    public void setAlluser(Boolean alluser) {
        this._alluser = alluser;
    }
    
    
    public Boolean getuse_touchscreen() {
        return _use_touchscreen;
    }
    public void setuse_touchscreen(Boolean usetouchscreen) {
        this._use_touchscreen = usetouchscreen;
    }
    
    public Boolean getallow_itemcancel() {
        return _allow_itemcancel;
    }
    public void setallow_itemcancel(Boolean allow_itemcancel) {
        this._allow_itemcancel = allow_itemcancel;
    }
	public Integer get_cashierprinterid() {
		return _cashierprinterid;
	}
	public void set_cashierprinterid(Integer _cashierprinterid) {
		this._cashierprinterid = _cashierprinterid;
	}
	public Boolean get_btnPrintBill() {
		return _btnPrintBill;
	}
	public void set_btnPrintBill(Boolean _btnPrintBill) {
		this._btnPrintBill = _btnPrintBill;
	}
	public Boolean get_billnotprint() {
		return _billnotprint;
	}
	public void set_billnotprint(Boolean _billnotprint) {
		this._billnotprint=_billnotprint;
	}

	public Boolean get_select_customer() {
		return _select_customer;
	}

	public void set_select_customer(Boolean _select_customer) {
		this._select_customer = _select_customer;
	}

	public Boolean get_create_customer() {
		return _create_customer;
	}

	public void set_create_customer(Boolean _create_customer) {
		this._create_customer = _create_customer;
	}

	public Boolean get_use_menuonly() {
		return _use_menuonly;
	}

	public void set_use_menuonly(Boolean _use_menuonly) {
		this._use_menuonly = _use_menuonly;
	}

	public Boolean get_use_foodtruck() {
		return _use_foodtruck;
	}

	public void set_use_foodtruck(Boolean _use_foodtruck) {
		this._use_foodtruck = _use_foodtruck;
	}

	public Boolean get_use_foodtrucklocation() {
		return _use_foodtrucklocation;
	}

	public void set_use_foodtrucklocation(Boolean _use_foodtrucklocation) {
		this._use_foodtrucklocation = _use_foodtrucklocation;
	}
}


