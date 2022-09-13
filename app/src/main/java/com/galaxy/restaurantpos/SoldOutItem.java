package com.galaxy.restaurantpos;

public class SoldOutItem {

	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getUsr_code() {
		return usr_code;
	}
	public void setUsr_code(String usr_code) {
		this.usr_code = usr_code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	private Integer code;
	private String usr_code;	
	private String description;
	
}
