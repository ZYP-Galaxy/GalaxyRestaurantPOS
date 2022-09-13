package com.galaxy.restaurantpos;


public class SaleItemObj {
	private String srno;
	private String sr;
	private String saleitemid;
	private String saleid;
	private String code;
	private String price;
	private String qty;
	private String amount;
	private String modifiedrowsr;
	private String remark;
	private String unittype;
	private String taway;
	private Boolean isFinishedItem;//added by WaiWL on 23/07/2015
	public String getSetmenurowsr() {
		return setmenurowsr;
	}
	public void setSetmenurowsr(String setmenurowsr) {
		this.setmenurowsr = setmenurowsr;
	}
	private Boolean cancelflag;
	private String Status; // "P-Printed" "C-Cancel" "N-Not Printed" "Q - ChangeQty"
	private Boolean submitflag;
	//added by WaiWL on 12/08/2015
	private String KTV_StartTime;
	private String KTV_EndTime;
	private String fire_sr;
	private String fired;
	private String setmenurowsr;
	private String org_curr; //added by EKK on 24-02-2020
	/////////////
	
	public String getFire_sr() {
		return fire_sr;
	}
	public String getFired() {
		return fired;
	}
	public void setFired(String fired) {
		this.fired = fired;
	}
	public void setFire_sr(String fire_sr) {
		this.fire_sr = fire_sr;
	}
	public String getsrno() {
	   return srno;
	}
	public void setsrno(String sr) {
	   this.srno = sr;
	}
	
	public String getsr() {
		   return sr;
		}
		public void setsr(String sr) {
		   this.sr = sr;
		}
	
    public String getsaleid() {
        return saleid;
    }
    public void setsaleid(String saleid) {
        this.saleid = saleid;
    }
    
    public String getsaleitemid() {
        return saleitemid;
    }
    public void setsaleitemid(String saleitemid) {
        this.saleitemid = saleitemid;
    }
    
    public String getcode() {
        return code;
    }
//    public String getcode() {
//        return (LoginActivity.isUnicode)?Dictionary.Uniconverter.convert(code):code;
//    }
    public void setcode(String code) {
        this.code = code;
    }
    
    public String getprice() {
        return price;
    }
    public void setprice(String price) {
        this.price = price;
    }
    
    public String getqty() {
        return qty;
    }
    public void setqty(String qty) {
        this.qty = qty;
    }
    
    public String getamount() {
        return amount;
    }
    public void setamount(String amount) {
        this.amount = amount;
    }
    
    public String getmodifiedrowsr() {
        return modifiedrowsr;
    }
    public void setmodifiedrowsr(String modifiedrowsr) {
        this.modifiedrowsr = modifiedrowsr;
    }
    
    public String getremark() {

	    //return (LoginActivity.isUnicode)?Dictionary.Uniconverter.convert(remark):remark;
	    return remark;
    }
    public void setremark(String remark) {

        //this.remark = (LoginActivity.isUnicode)?Dictionary.Uniconverter.convert(remark):remark;
	    this.remark = remark;
    }
    
    public String getunittype() {
        return unittype;
    }
    public void setunittype(String unittype) {
        this.unittype = unittype;
    }
    
    public String gettaway() {
        return taway;
    }
    public void settaway(String taway) {
        this.taway = taway;
    }
    
    //added by WaiWL on 23/07/2015
    public Boolean getisFinishedItem() {
        return isFinishedItem;
    }
    public void setisFinishedItem(Boolean isFinishedItem) {
        this.isFinishedItem = isFinishedItem;
    }
    ///////////////////
    
    public Boolean getcancelflag() {
        return cancelflag;
    }
    public void setcancelflag(Boolean cancel) {
        this.cancelflag = cancel;
    }

    public String getstatus() {
        return Status;
    }
    public void setstatus(String status) {
        this.Status = status;
    }
   
    public Boolean getSubmitflag() {
        return submitflag;
    }
    public void setsubmitflag(Boolean submitflag) {
        this.submitflag = submitflag;
    }
    
    //added by WaiWL on 12/08/2015
    public String getKTV_StartTime() {
        return KTV_StartTime;
    }
    public void setKTV_StartTime(String KTV_StartTime) {
        this.KTV_StartTime = KTV_StartTime;
    }
    
    public String getKTV_EndTime() {
        return KTV_EndTime;
    }
    public void setKTV_EndTime(String KTV_EndTime) {
        this.KTV_EndTime = KTV_EndTime;
    }

    public String getOrg_curr() {
        return org_curr;
    }

    public void setOrg_curr(String org_curr) {
        this.org_curr = org_curr;
    }

    //////////
}
