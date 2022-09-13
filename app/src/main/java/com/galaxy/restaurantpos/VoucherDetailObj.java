package com.galaxy.restaurantpos;
public class VoucherDetailObj {
	private int tranid;
	private String invoice_amount;
	private String paid_amount;
	private String discount;
	private String itemdiscount;
	private String date;
	private String userid;
	private String GTaxamount;
	private String STaxamount; 
	private String RTaxamount; //Added by Arkar Moe on [20/07/2016] fo radding Room Tax in Tablet Voucher [Res-0208]
	private String GovernmentTaxpercent;
	private String ServiceTaxpercent;
    private String memdiscount;
	private String memamount;
	private String focamount;
	private String netamount;
    private String delivery_charges;//added WHM [2020-05-07]
	
	
	public int gettranid() {
	   return tranid;
	}
	public void settranid(int tranid) {
	   this.tranid = tranid;
	}
	
    public String getinvoice_amount() {
        return invoice_amount;
    }
    public void setinvoice_amount(String value) {
        this.invoice_amount = value;
    }
    
    public String getpaid_amount() {
        return paid_amount;
    }
    public void setpaid_amount(String value) {
        this.paid_amount = value;
    }
    public String getdiscount() {
        return discount;
    }
    public void setdiscount(String value) {
        this.discount = value;
    }
    
    public String getitemdiscount() {
        return itemdiscount;
    }
    public void setitemdiscount(String value) {
        this.itemdiscount = value;
    }
    
    public String getdate() {
        return date;
    }
    public void setdate(String value) {
        this.date = value;
    }
    public String getuserid() {
        return userid;
    }
    public void setuserid(String value) {
        this.userid = value;
    }
    public String getgtaxamount() {
        return GTaxamount;
    }
    public void setgtaxamount(String value) {
        this.GTaxamount = value;
    }
    
    public String getstaxamount() {
        return STaxamount;
    }
    public void setstaxamount(String value) {
        this.STaxamount = value;
    }
    
    public String getgtaxpercent() {
        return GovernmentTaxpercent;
    }
    public void setgtaxpercent(String value) {
        this.GovernmentTaxpercent = value;
    }
    
    public String getstaxpercent() {
        return ServiceTaxpercent;
    }
    public void setstaxpercent(String value) {
        this.ServiceTaxpercent = value;
    }
    //-------//Added by Arkar Moe on [20/07/2016] for adding Room Tax in Tablet Voucher [Res-0208]
    public String getrtaxamount() {
        return RTaxamount;
    }
    public void setrtaxamount(String value) {
        this.RTaxamount = value;
    }
    //-------

    public String getMemdiscount() {
        return memdiscount;
    }

    public void setMemdiscount(String memdiscount) {
        this.memdiscount = memdiscount;
    }

    public String getmemamount() {
        return memamount;
    }
    public void setmemamount(String value) {
        this.memamount = value;
    }
    public String getfocamount() {
        return focamount;
    }
    public void setfocamount(String value) {
        this.focamount = value;
    }
    public String getnetamount() {
        return netamount;
    }
    public void setnetamount(String value) {
        this.netamount = value;
    }

    public String getDelivery_charges() {
        return delivery_charges;
    }

    public void setDelivery_charges(String delivery_charges) {
        this.delivery_charges = delivery_charges;
    }
}
