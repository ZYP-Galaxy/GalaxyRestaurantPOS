package com.galaxy.restaurantpos;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class ItemsObj {
	private String code;
	private String usr_code;
	private String description;
	private String description2;
	private String description3;
	private String sale_price;
	private String sale_price1;
	private String sale_price2;
	private String sale_price3;
	private String caldiscount;
	private String calGTax;
	private String calSTax;
	private String categoryid;
	private String categoryname;
	private String classid;
	private String classname;
	private Bitmap Photo;
	private String ModifierGroupID;
	private String modifieritem;
	private String remark;
	private String colorRBG;
	private String itemcolorRGB;
	private String unit;
	private String categorysortcode;
	private String classsortcode;
	private String upd_datetime;
	private String issetmenu;
	private String unitqty;
	private String inactive;//added by WaiWL on 06/08/2015
	private String valueitem;//added by WaiWL on 11/08/2015
	private String MealType1;
	private String MealType2;
	private String MealType3;
	private Drawable imagedrawable;
	private String Parcel_Price;
	private String sortid; // added by TTA
    private String org_curr; //added by EKK on 24-02-2020
    private String Itemrating;
    private String Ratingcount;
    private String category2;
	
    public String getcode() {
        return code;
    }
    public void setcode(String code) {
        this.code = code;
    }
    
    public String getusr_code() {
        return usr_code;
    }
    public void setusr_code(String usr_code) {
        this.usr_code = usr_code;
    }
    
    public String getdescription() {
        return description;
    }
    public void setdescription(String description) {
        this.description = description;
    }
    
    public String getdescription2() {
        return description2;
    }
    public void setdescription2(String description2) {
        this.description2 = description2;
    }
    
    public String getdescription3() {
        return description3;
    }
    public void setdescription3(String description3) {
        this.description3 = description3;
    }
    
    public String getsale_price() {
        return sale_price;
    }
    public void setsale_price(String saleprice) {
        this.sale_price = saleprice;
    }
    
    public String getsale_price1() {
        return sale_price1;
    }
    public void setsale_price1(String saleprice1) {
        this.sale_price1 = saleprice1;
    }
    
    public String getsale_price2() {
        return sale_price2;
    }
    public void setsale_price2(String saleprice2) {
        this.sale_price2 = saleprice2;
    }
    
    public String getsale_price3() {
        return sale_price3;
    }
    public void setsale_price3(String saleprice3) {
        this.sale_price3 = saleprice3;
    }
    
    public String getcaldiscount() {
        return caldiscount;
    }
    public void setcaldiscount(String caldiscount) {
        this.caldiscount = caldiscount;
    }
    
    public String getgtax() {
        return calGTax;
    }
    public void setgtax(String gtax) {
        this.calGTax = gtax;
    }
    
    public String getstax() {
        return calSTax;
    }
    public void setstax(String stax) {
        this.calSTax = stax;
    }
    
    public String getcategoryid() {
        return categoryid;
    }
    public void setcategoryid(String categoryid) {
        this.categoryid = categoryid;
    }
    
//    public String getcategoryname() {
//        return categoryname;
//    }
    public String getcategoryname() {
        return categoryname;
    }
    public void setcategoryname(String categoryname) {
        this.categoryname = categoryname;
    }
    
    public String getclassid() {
        return classid;
    }
    public void setclassid(String classid) {
        this.classid = classid;
    }
    
    public String getclassname() {
        return classname;
    }
    public void setclassname(String classname) {
        this.classname = classname;
    }
    
    public String getmodifiergroupid() {
        return ModifierGroupID;
    }
    public void setmodifiergroupid(String modifiergroupid) {
        this.ModifierGroupID = modifiergroupid;
    }
    
    public String getmodifieditem() {
        return modifieritem;
    }
    public void setmodifiedItem(String modifieditem) {
        this.modifieritem = modifieditem;
    }
    
    public String getremark() {

        //return (LoginActivity.isUnicode)?Dictionary.Uniconverter.convert(remark):remark;
        return remark;
    }
    public void setremark(String remark) {
        this.remark = remark;
    }
    
    public Bitmap getphoto() {
        return this.Photo;
    }
    public void setphoto(Bitmap photo) {
        this.Photo = photo;
    }
    
    public String getcolorRGB() {
        return this.colorRBG;
    }
    public void setcolorRGB(String colorRGB) {
        this.colorRBG = colorRGB;
    }
    
    public String getitemcolorRGB() {
        return this.itemcolorRGB;
    }
    public void setitemcolorRGB(String itemcolorRGB) {
        this.itemcolorRGB = itemcolorRGB;
    }
    
    public String getunit() {
        return this.unit;
    }
    public void setunit(String unit) {
        this.unit = unit;
    }
    
    public String getcategorysortcode() {
        return this.categorysortcode;
    }
    public void setcategorysortcode(String categorysortcode) {
        this.categorysortcode = categorysortcode;
    }
    
    public String getclasssortcode() {
        return this.classsortcode;
    }
    public void setclasssortcode(String classsortcode) {
        this.classsortcode = classsortcode;
    }
    
    public String getupd_datetime() {
        return this.classsortcode;
    }
    public void setupd_datetime(String classsortcode) {
        this.upd_datetime = classsortcode;
    }
    
    public String getissetmenu() {
        return this.issetmenu;
    }
    public void setissetmenu(String issetmenu) {
        this.issetmenu = issetmenu;
    }
    
    //added by WaiWL on 06/08/2015
    public String getInactive()
    {
    	return this.inactive;
    }
    
    public void setInactive(String inactive)
    {
    	this.inactive = inactive;
    }
    ////////////////
    
  //added by WaiWL on 06/08/2015
    public String getvalueitem()
    {
    	return this.valueitem;
    }
    public String getMealType1()
    {
    	return this.MealType1;
    }
    
    public String getMealType2()
    {
    	return this.MealType2;
    }
    
    public String getMealType3()
    {
    	return this.MealType3;
    }
    
    
    public void setvalueitem(String valueitem)
    {
    	this.valueitem = valueitem;
    }
    public void setMealType1(String MealType1)
    {
    	this.MealType1 = MealType1;
    }
    
    public void setMealType2(String MealType2)
    {
    	this.MealType2 = MealType2;
    }
    
    public void setMealType3(String MealType3)
    {
    	this.MealType3 = MealType3;
    }
    ////////////////
    
    public String getunitqty() {
        return this.unitqty;
    }
    public void setunitqty(String unitqty) {
        this.unitqty = unitqty;
    }
    

    
    public Drawable getimgdrawable() {
        return this.imagedrawable;
    }
    public void setimgdrawable(Drawable imgdrawable) {
        this.imagedrawable = imgdrawable;
    }
    
    public String getParcel_Price() {
        return this.Parcel_Price;
    }
    public void setParcel_Price(String Parcel_Price) {
        this.Parcel_Price = Parcel_Price;
    }
	public String getSortid() {
		return sortid;
	}
	public void setSortid(String sortid) {
		this.sortid = sortid;
	}


    public String getOrg_curr() {
        return org_curr;
    }

    public void setOrg_curr(String org_curr) {
        this.org_curr = org_curr;
    }

    public String getItemrating() {
        return Itemrating;
    }

    public void setItemrating(String itemrating) {
        Itemrating = itemrating;
    }

    public String getRatingcount() {
        return Ratingcount;
    }

    public void setRatingcount(String ratingcount) {
        Ratingcount = ratingcount;
    }

    public String getCategory2() {
        return category2;
    }

    public void setCategory2(String category2) {
        this.category2 = category2;
    }
}
