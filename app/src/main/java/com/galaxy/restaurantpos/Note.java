package com.galaxy.restaurantpos;

public class Note {

	private Integer _tranid;
	private Integer _sr;
	private Integer _userid;
	private String _Date;
	private String _Notes;

	public Integer getTranid() {
		return _tranid;
	}

	  public void setTranid(Integer tranid) {
	        this._tranid = tranid;
	    }
	public Integer getsr() {
		return _sr;
	}
	
	  public void setsr(Integer sr) {
	        this._sr = sr;
	    }

	public Integer getuserid() {
		return _userid;
	}
	
	 public void setuserid(Integer userid) {
	        this._userid = userid;
	    }

	public String getDate() {
		return _Date;
	}
	
	 public void setDate(String Date) {
	        this._Date = Date;
	    }

	public String getNotes() {
		return _Notes;
	}
	
	 public void setNotes(String Notes) {
	        this._Notes = Notes;
	    }
}
