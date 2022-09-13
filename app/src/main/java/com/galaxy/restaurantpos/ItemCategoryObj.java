package com.galaxy.restaurantpos;

public class ItemCategoryObj {
	private String categoryid;
	private String categoryname;
	private String colorRGB;
	private String categorysortcode;
	public String getcategoryid()
	{
		return this.categoryid;		
	}
	public void setcategoryid(String categoryid)
	{		
		this.categoryid = categoryid;
	}
	
//	public String getcategoryname()
//	{
//		return this.categoryname;
//	}

	public String getcategoryname(){
		return this.categoryname;
	}

	public void setcategoryname(String categoryname)
	{		
		this.categoryname = categoryname;
	}
	
	public String getcolorRGB()
	{
		return this.colorRGB;		
	}
	public void setcolorRGB(String colorRGB)
	{		
		this.colorRGB = colorRGB;
	}
	
	public String getcategorysortcode()
	{
		return this.categorysortcode;		
	}
	public void setcategorysortcode(String categorysortcode)
	{		
		this.categorysortcode = categorysortcode;
	}
	

}
