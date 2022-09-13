package com.galaxy.restaurantpos;


public class RowItem {
    private int tranid;
    private int headerid;
    private String header;
    private String detail;
    private String time;
    private String qty;
 
    public RowItem(int tranid,int headerid , String header ,String detail, String time) {
        this.tranid = tranid;
        this.headerid = headerid;
        this.header = header;
        this.detail = detail;
        this.time = time;    
    }
    public String getQty() {
		return qty;
	}
	public void setQty(String qty) {
		this.qty = qty;
	}
	public int gettranid() {
        return tranid;
    }
    public void settranid(int tranid) {
        this.tranid = tranid;
    }
    
    public int getheaderid() {
        return headerid;
    }
    public void setheaderid(int headerid) {
        this.headerid = headerid;
    } 
    
    public String getheader() {
        return header;
    }
    public void setheader(String header) {
        this.header = header;
    } 
    
    public String getdetail() {
        return detail;
    }
    public void setdetail(String detail) {
        this.detail = detail;
    }
    
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    
}