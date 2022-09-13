package com.galaxy.restaurantpos;

import android.R.string;

public class Customer_analysisObj {	
	private String _customertype = "P";
	private int _malecount = 0;
	private int _femalecount = 0;
	private int _adultcount = 0;
	private int _childcount = 0;
	private int _westerncount = 0;
	private int _asiancount = 0;
	private int _myanmarcount = 0;

	public String getcustomertype()
	{			
		return _customertype;
	}
	public void setcustomertype(String customertype)
	{
		_customertype = customertype;
	}
	
	public int getmalecount()
	{			
		return _malecount;
	}
	public void setmalecount(int malecount)
	{
		_malecount = malecount;
	}
	
	public int getfemalecount()
	{			
		return _femalecount;
	}
	public void setfemalecount(int femalecount)
	{
		_femalecount = femalecount;
	}

	public int getadultcount() {
		return _adultcount;
	}

	public void setadultcount(int _adultcount) {
		this._adultcount = _adultcount;
	}

	public int getchildcount() {
		return _childcount;
	}

	public void setchildcount(int _childcount) {
		this._childcount = _childcount;
	}


	public int getwesterncount()
	{			
		return _westerncount;
	}
	public void setwesterncount(int westerncount)
	{
		_westerncount = westerncount;
	}
	
	public int getasiancount()
	{			
		return _asiancount;
	}
	public void setasiancount(int asiancount)
	{
		_asiancount = asiancount;
	}
	
	public int getmyanmarcount()
	{			
		return _myanmarcount;
	}
	public void setmyanmarcount(int myanmarcount)
	{
		_myanmarcount = myanmarcount;
	}
	
	
}
