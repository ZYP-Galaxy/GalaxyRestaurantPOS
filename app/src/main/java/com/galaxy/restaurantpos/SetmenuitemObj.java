package com.galaxy.restaurantpos;

public class SetmenuitemObj {

	private String main_code;	
	private String code;
	private String qty;
	private String unit_qty;
	private String unit_type;
	private String max_price;
    private String category2;
	
	
	public String getMax_price() {
		return max_price;
	}
	public void setMax_price(String max_price) {
		this.max_price = max_price;
	}
	public String getmaincode() {
        return main_code;
    }
    public void setmaincode(String maincode) {
        this.main_code = maincode;
    }
	
    public String getcode() {
        return code;
    }
    public void setcode(String code) {
        this.code = code;
    }
    
     
    public String getqty() {
        return this.qty;
    }
    public void setqty(String qty) {
        this.qty = qty;
    }
    
    public String getunitqty() {
        return this.unit_qty;
    }
    public void setunitqty(String unitqty) {
        this.unit_qty = unitqty;
    }
    
    public String getunittype() {
        return this.unit_type;
    }
    public void setunittype(String unitype) {
        this.unit_type = unitype;
    }

    public String getCategory2() {
        return category2;
    }

    public void setCategory2(String category2) {
        this.category2 = category2;
    }
}
