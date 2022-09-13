package com.galaxy.restaurantpos;


import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class testaungmyotun  extends BaseAdapter{

	Context context;
	 
	 ArrayList<SaleItemObj> arrayitemobj=new ArrayList<SaleItemObj>();
	 
	 public testaungmyotun(Context c,ArrayList<SaleItemObj> arrayitemobj){
		 this.context=c;
		 this.arrayitemobj=arrayitemobj;
		 
	 }
	 
	 
	public int getCount() {
		// TODO Auto-generated method stub
		return arrayitemobj.size();
	}

	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		
		 LayoutInflater inflater = ((Activity)context).getLayoutInflater();
         arg1 = inflater.inflate(R.layout.activity_split_bill, null, false);
		
		return arg1;
	}

}
