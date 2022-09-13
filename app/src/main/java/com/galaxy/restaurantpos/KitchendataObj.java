package com.galaxy.restaurantpos;

import android.R.bool;
import android.R.string;

public class KitchendataObj {
	private int tranid;
	private int sr;
	private int code;
	private double unit_qty;
	private int Table_Name_ID;
	private int Ref_No;
	private String Salesmen_ID;
	private String Cook_Status;
	private int userid;
	private String sudden_fix;
	private String description;
	
	public int gettranid() {
	   return tranid;
	}
	public void settranid(int tranid) {
	   this.tranid = tranid;
	}
	
    public int getsr() {
        return sr;
    }
    public void setsr(int value) {
        this.sr = value;
    }
    
    public int getcode() {
        return code;
    }
    public void setcode(int value) {
        this.code = value;
    }
    
    public double getunit_qty() {
        return unit_qty;
    }
    public void setunit_qty(double value) {
        this.unit_qty = value;
    }
    
    public int getTable_Name_ID() {
        return Table_Name_ID;
    }
    public void setTable_Name_ID(int value) {
        this.Table_Name_ID = value;
    }
    public int getRef_No() {
        return Ref_No;
    }
    public void setRef_No(int value) {
        this.Ref_No = value;
    }
    public String getSalesmen_id() {
        return Salesmen_ID;
    }
    public void setSalesmen_id(String value) {
        this.Salesmen_ID = value;
    }
    
    public String getcook_status() {
        return Cook_Status;
    }
    public void setcook_status(String value) {
        this.Cook_Status = value;
    }
    
    public int getuserid() {
        return userid;
    }
    public void setuserid(int value) {
        this.userid = value;
    }
    
    public String getsudden_fix() {
        return sudden_fix;
    }
    public void setsudden_fix(String value) {
        this.sudden_fix = value;
    }
    
    public String getdescription() {
        return description;
    }
    public void setdescription(String value) {
        this.description = value;
    }
}
