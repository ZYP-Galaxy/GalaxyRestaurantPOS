package com.galaxy.restaurantpos;

public class MenuOrderObj {
	private String tranid;
	private String userid;
	private String usr_code;
	private String code;
	private String description;
	private String qty;
	private String sale_price;
	private Boolean isnew;
	private String orderid;

	//added WHM [2020-06-01] self order
	private String isSetMenu;
	private String srno;
	private String sr;
	private String modifiedrowsr;
	private String setmenurowsr;
	
	public String getTranid() {
		return tranid;
	}
	public void setTranid(String tranid) {
		this.tranid = tranid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsr_code() {
		return usr_code;
	}
	public void setUsr_code(String usr_code) {
		this.usr_code = usr_code;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getQty() {
		return qty;
	}
	public void setQty(String qty) {
		this.qty = qty;
	}
	public String getSale_price() {
		return sale_price;
	}
	public void setSale_price(String sale_price) {
		this.sale_price = sale_price;
	}
	public Boolean getIsnew() {
		return isnew;
	}
	public void setIsnew(Boolean isnew) {
		this.isnew = isnew;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getIsSetMenu() {
		return isSetMenu;
	}

	public void setIsSetMenu(String isSetMenu) {
		this.isSetMenu = isSetMenu;
	}

	public String getSrno() {
		return srno;
	}

	public void setSrno(String srno) {
		this.srno = srno;
	}

	public String getSr() {
		return sr;
	}

	public void setSr(String sr) {
		this.sr = sr;
	}

	public String getModifiedrowsr() {
		return modifiedrowsr;
	}

	public void setModifiedrowsr(String modifiedrowsr) {
		this.modifiedrowsr = modifiedrowsr;
	}

	public String getSetmenurowsr() {
		return setmenurowsr;
	}

	public void setSetmenurowsr(String setmenurowsr) {
		this.setmenurowsr = setmenurowsr;
	}
}
