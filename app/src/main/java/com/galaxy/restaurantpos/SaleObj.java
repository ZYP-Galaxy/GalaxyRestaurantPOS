package com.galaxy.restaurantpos;

import android.R.bool;


public class SaleObj {
	private String saleid;
	private String invoiceno;
	private String DocID;
	private String tablenameid;
	private String totalamount;
	private String totalqty;
	private String roomid; //Added by ArkarMoe on [15/12/2016]
	private String customerid;
	private String date;
	private String customercount;
	private bool itemcancel;
	private int userid;
	private int salesmen_id;
	private Boolean submit_flag;
	private String Ref_no;
	private int delivery_type ; //added WHM [2020-05-08]
    private boolean taxfree;
    private String membercard;

    private int selforder_approve;//added WHM [2020-05-18] selfOrder
	
    public String getsaleid() {
        return saleid;
    }
    public void setsaleid(String saleid) {
        this.saleid = saleid;
    }
    
    public String getinvoiceno() {
        return invoiceno;
    }
    public void setinvoiceno(String invoiceno) {
        this.invoiceno = invoiceno;
    }
    
    public String getDocID() {
        return DocID;
    }
    public void setDocID(String DocID) {
        this.DocID = DocID;
    }
    
    public String getRef_no() {
        return Ref_no;
    }
    public void setRef_no(String Ref_no) {
        this.Ref_no = Ref_no;
    }
    
    public String gettablenameid() {
        return tablenameid;
    }
    public void settablenameid(String tablenameid) {
        this.tablenameid = tablenameid;
    }
    
    public String getamount() {
        return totalamount;
    }
    public void setamount(String amount) {
        this.totalamount = amount;
    }
    
    public String getqty() {
        return totalqty;
    }
    public void setqty(String qty) {
        this.totalqty = qty;
    }
    
    //Added by Arkar Moe on [15/12/2016]
    public String getroomid() {
        return roomid;
    }
    public void setroomid(String roomid) {
        this.roomid = roomid;
    }
    /////
    
    public String getcustomerid() {
        return customerid;
    }
    public void setcustomerid(String customerid) {
        this.customerid = customerid;
    }
    
    public String getdate() {
        return date;
    }
    public void setdate(String date) {
        this.date = date;
    }
    
    public String getcustcount() {
        return customercount;
    }
    public void setcustcount(String custcount) {
        this.customercount = custcount;
    }
    
    public bool getitemcancel() {
        return itemcancel;
    }
    public void setitemcancel(bool itemcancel) {
        this.itemcancel = itemcancel;
    }
    
    public int getuserid() {
        return userid;
    }
    public void setuserid(int userid) {
        this.userid = userid;
    }
    
    public int getsalesmen_id() {
        return salesmen_id;
    }
    public void setsalesmen_id(int salesmenid) {
        this.salesmen_id = salesmenid;
    }
    
    
    public Boolean getsubmitflag() {
        return submit_flag;
    }
    public void setsubmitflag(Boolean submitflag) {
        this.submit_flag = submitflag;
    }

    public int getDelivery_type() {
        return delivery_type;
    }

    public void setDelivery_type(int delivery_type) {
        this.delivery_type = delivery_type;
    }

    public int getSelforder_approve() {
        return selforder_approve;
    }

    public void setSelforder_approve(int selforder_approve) {
        this.selforder_approve = selforder_approve;
    }

    public boolean isTaxfree() {
        return taxfree;
    }

    public void setTaxfree(boolean taxfree) {
        this.taxfree = taxfree;
    }

    public String getMembercard() {
        return membercard;
    }

    public void setMembercard(String membercard) {
        this.membercard = membercard;
    }
}
