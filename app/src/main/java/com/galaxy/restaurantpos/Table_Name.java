package com.galaxy.restaurantpos;

public class Table_Name {
	private Integer _TableArea_ID;
	private Integer _Table_Name_ID;	
	private String _Table_Name;
	private String _TableDescription;
	private String _Table_Code;
	private String _TableRemark;
	private Integer _tranid;
	private String _docid;
	private String _reserved;
	private int _userid;
	private int _longminute;
	private String _checkbill;
	private String selforder_table;//added WHM [2020-05-19] self order
    //private String sort_code;
    private int sort_code; //modifed WHM [2020-11-10]


	public String get_checkbill() {
		return _checkbill;
	}
	public void set_checkbill(String _checkbill) {
		this._checkbill = _checkbill;
	}
	public Integer getTableArea_ID() {
        return _TableArea_ID;
    }
    public void setTableArea_ID(Integer TableArea_ID) {
        this._TableArea_ID = TableArea_ID;
    }
    public Integer getTable_Name_ID() {
        return _Table_Name_ID;
    }
    public void setTable_Name_ID(Integer Table_Name_ID) {
        this._Table_Name_ID = Table_Name_ID;
    }
    public String getTable_Name() {

	    return _Table_Name;
    }
    public void setTable_Name(String Table_Name) {
        this._Table_Name = Table_Name;
    }
    public String getTableDescription() {

	    return _TableDescription;
    }
    public void setTableDescription(String TableDescription) {
        this._TableDescription = TableDescription;
    }
    public String getTable_Code() {

	    return _Table_Code;
    }
    public void setTable_Code(String Table_Code) {
        this._Table_Code = Table_Code;
    }
    public String getTableRemark() {
        return _TableRemark;
    }
    public void setTableRemark(String TableRemark) {
        this._TableRemark = TableRemark;
    }
    
    public Integer getTranid() {
        return _tranid;
    }
    public void setTranid(Integer tranid) {
        this._tranid = tranid;
    }
    
    public String getDocID() {
        return _docid;
    }
    public void setDocID(String docid) {
        this._docid = docid;
    }
    
    public String getreserved() {
        return _reserved;
    }
    public void setreserved(String reserved) {
        this._reserved = reserved;
    }
    
    public int getuserid() {
        return _userid;
    }
    public void setuserid(int userid) {
        this._userid = userid;
    }
    
    public int getlongminute() {
        return _longminute;
    }
    public void setlongminute(int minute) {
        this._longminute = minute;
    }

    public String getSelforder_table() {
        return selforder_table;
    }

    public void setSelforder_table(String selforder_table) {
        this.selforder_table = selforder_table;
    }

//    public String getSort_code() {
//        return sort_code;
//    }
//
//    public void setSort_code(String sort_code) {
//        this.sort_code = sort_code;
//    }


    public int getSort_code() {
        return sort_code;
    }

    public void setSort_code(int sort_code) {
        this.sort_code = sort_code;
    }
}
