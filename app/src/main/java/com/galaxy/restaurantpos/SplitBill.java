package com.galaxy.restaurantpos;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SplitBill extends BaseAdapter {
	private ArrayList<SaleItemObj> msaleitems;
	private Context mContext;
	private DatabaseHelper dbhelper;

	public SplitBill(Context context,ArrayList<SaleItemObj> msaleitem){
		mContext=context;
		msaleitems=msaleitem;
		
	}
	
	public int getCount() {
		// TODO Auto-generated method stub
		return msaleitems.size();
	}
	
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	public View getView(int i, View view, ViewGroup viewGroup) {
		// TODO Auto-generated method stub
		View rootView;
		if (view==null) {
			LayoutInflater inflater=(LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rootView=inflater.inflate(R.layout.itemlayout, null);
		}else{
			rootView=view;
		}
		
		TextView txtSr=(TextView)rootView.findViewById(R.id.txtLayoutItemSr);
		TextView txtDescription=(TextView)rootView.findViewById(R.id.txtLayoutItemDes);
		TextView txtQty=(TextView)rootView.findViewById(R.id.txtLayoutItemQty);
		TextView txtUnit=(TextView)rootView.findViewById(R.id.txtLayoutItemUnit); //added by EKK on 24-03-2020
		TextView txtAmount=(TextView)rootView.findViewById(R.id.txtLayoutItemAmount);
		
		txtDescription.setTag(i);
		txtQty.setTag(i);
		txtUnit.setTag(i); //added by EKK on 24-03-2020
		txtAmount.setTag(i);
		
		dbhelper=new DatabaseHelper(mContext);
			List<ItemsObj> itemobj = dbhelper
					.getItemslistbyitemid(msaleitems.get(i).getcode());
																	
		int j=i+1;
		//txtSr.setText(msaleitems.get(i).getsrno());
		txtSr.setText(String.valueOf(j));
		txtDescription.setText(itemobj.get(0).getdescription());	
		//txtQty.setText(msaleitems.get(i).getqty());	
		txtQty.setText(Double.toString(Double.parseDouble(msaleitems.get(i).getqty())).replace(".0", ""));

		//added by EKK on 24-03-2020
		if (dbhelper.getuse_unit().equals("true")) {
			List<UnitCodeObj> unitcodeobjlist = dbhelper
					.getunitcodebyitemid(msaleitems.get(i).getcode());
			if (unitcodeobjlist.size() > 0) {
				String  unitcodeshort = unitcodeobjlist.get(0).getshortname();
				String unit = unitcodeobjlist.get(0).getunit();
				txtUnit.setText(unitcodeshort); //added by EKK on 24-03-2020


			}
		}

		txtUnit.setVisibility(View.INVISIBLE);//added WHM [2020-03-31]

		txtAmount.setText(dbhelper.getCurrencyFormat(msaleitems.get(i).getamount()));
		
		return rootView;
	}

}
