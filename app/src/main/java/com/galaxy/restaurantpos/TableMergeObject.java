package com.galaxy.restaurantpos;

public class TableMergeObject {

		private Integer _tranid;
		private Integer _totranid;	
		private Integer _totableid;
		private Integer _userid;
		
		
		public Integer gettranid() {
	        return _tranid;
	    }
	    public void settranid(Integer value) {
	        this._tranid = value;
	    }
	    
	    public Integer gettotranid() {
	        return _totranid;
	    }
	    public void settotranid(Integer value) {
	        this._totranid = value;
	    }
	    
	    public Integer gettotableid() {
	        return _totableid;
	    }
	    public void settotableid(Integer value) {
	        this._totableid = value;
	    }
	    
	    public Integer getuserid() {
	        return _userid;
	    }
	    public void setuserid(Integer value) {
	        this._userid = value;
	    }		
}
