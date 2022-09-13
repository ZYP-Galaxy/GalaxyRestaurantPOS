package com.galaxy.restaurantpos;


public class SetmenuItem {
    private int tranid;
    private String header;
    private String qty;
 
    public SetmenuItem(int tranid,String header ,String qty) {
        this.tranid = tranid;       
        this.header = header;
        this.qty = qty;
    }
    public int gettranid() {
        return tranid;
    }
    public void settranid(int tranid) {
        this.tranid = tranid;
    }
  
    
    public String getheader() {
        return header;
    }
    public void setheader(String header) {
        this.header = header;
    } 
    
    public String getqty() {
        return qty;
    }
    public void setTime(String qty) {
        this.qty = qty;
    }
    
}