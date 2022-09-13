package com.galaxy.restaurantpos;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MenuOrder extends BaseAdapter {

	private ArrayList<MenuOrderObj> mmenuorder;
	private Context mContext;
	private DatabaseHelper dbhelper;
	private MenuOrderObj menuorderobj;
	
	public MenuOrder(Context context,ArrayList<MenuOrderObj> mmenu){
		mContext=context;
		mmenuorder=mmenu;
		
	}
	
	public int getCount() {
		// TODO Auto-generated method stub
		return mmenuorder.size();
	}

	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	public View getView(int i, View view, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		final View rootView;
		if (view==null) {
			LayoutInflater inflater=(LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rootView=inflater.inflate(R.layout.menuitemlayout, null);
		}else{
			rootView=view;
		}
		
		dbhelper=new DatabaseHelper(mContext);
		menuorderobj=new MenuOrderObj();
		final MenuOrderObj menuorder=(MenuOrderObj)getItem(i);
		final int deleteid=i;
		
		final TextView txtorderid=(TextView)rootView.findViewById(R.id.txtorderid);
		TextView txtSr=(TextView)rootView.findViewById(R.id.txtOrdersr);
		TextView txtDescription=(TextView)rootView.findViewById(R.id.txtOrderdes);
		final TextView txtQty=(TextView)rootView.findViewById(R.id.txtOrderqty);
		TextView txtAmount=(TextView)rootView.findViewById(R.id.txtOrderamount);
		
		Button btnQtyPlus=(Button)rootView.findViewById(R.id.btnOrderQtyPlus);
		Button btntQtyMinus=(Button)rootView.findViewById(R.id.btnOrderQtyMinus);
		Button btnCancel=(Button)rootView.findViewById(R.id.btnOrderCancel);
		
	
		txtDescription.setTag(i);
		txtQty.setTag(i);
		txtAmount.setTag(i);
		
		txtorderid.setText(mmenuorder.get(i).getOrderid());
		txtSr.setText(String.valueOf(i+1)+".");
		txtDescription.setText(mmenuorder.get(i).getDescription());	
		txtQty.setText(mmenuorder.get(i).getQty());	
		
//		double price=Double.parseDouble(mmenuorder.get(i).getSale_price());
		
		double amount_price=Double.parseDouble(mmenuorder.get(i).getQty())* Double.parseDouble(mmenuorder.get(i).getSale_price());
		
		txtAmount.setText(NumberFormat.getNumberInstance(Locale.US)
				.format(amount_price));
	//	txtAmount.setText(mmenuorder.get(i).getSale_price());	
		
		
		btnQtyPlus.setOnClickListener(new OnClickListener() {		
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int qty = Integer.parseInt(txtQty
						.getText().toString());
				qty = qty + 1;
				txtQty.setText(Integer
						.toString(qty));
				dbhelper.updateOrderQty(Integer.parseInt(txtorderid.getText().toString()), Integer.toString(qty));
				mmenuorder.remove(deleteid);
				menuorderobj=dbhelper.getMenuObjectById(Integer.parseInt(txtorderid.getText().toString()));
				mmenuorder.add(deleteid, menuorderobj);
				notifyDataSetChanged();
				getSummary();
				getPopup();
			}
		});
		
		btntQtyMinus.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int qty = Integer.parseInt(txtQty
						.getText().toString());
				if (qty > 1) {
					qty = qty - 1;
					txtQty.setText(Integer
							.toString(qty));
					
				dbhelper.updateOrderQty(Integer.parseInt(txtorderid.getText().toString()), Integer.toString(qty));
				mmenuorder.remove(deleteid);
				menuorderobj=dbhelper.getMenuObjectById(Integer.parseInt(txtorderid.getText().toString()));
				mmenuorder.add(deleteid, menuorderobj);
				notifyDataSetChanged();
				getSummary();
				getPopup();
				}
			}
		});
			
		btnCancel.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dbhelper=new DatabaseHelper(mContext);	
				dbhelper.deleteOrderbyId(Integer.parseInt(txtorderid.getText().toString()));
				
				mmenuorder.remove(deleteid);
				notifyDataSetChanged();
				getSummary();
				getPopup();
			}
		});
		
		return rootView;
		
	}
	
	public void refreshEvents(ArrayList<MenuOrderObj> arraylist){
		this.mmenuorder.clear();
		this.mmenuorder.addAll(arraylist);		
		this.mmenuorder=arraylist;
		notifyDataSetChanged();
		notifyDataSetInvalidated();

	}
	
	public void getSummary(){
		int qty=dbhelper.getTotalOrderQty(NewMenuActivity.tranid, NewMenuActivity.userid);
		if(qty > 0){
			NewMenuActivity.txtmenutoalqty.setText(String.valueOf(qty));
		}else{
			NewMenuActivity.txtmenutoalqty.setText("");
		}
		double amount=dbhelper.getTotalOrderAmount(NewMenuActivity.tranid, NewMenuActivity.userid);
		if(amount != 0.0){
			NewMenuActivity.txtmenutotalamount.setText(String.valueOf(amount));
		}else{
			NewMenuActivity.txtmenutotalamount.setText("");
		}
	}
	
	public void getPopup(){
		int qty=dbhelper.getPopupQty(NewMenuActivity.tranid, NewMenuActivity.userid);
		if(qty > 0){
			NewMenuActivity.popupqty.setText(String.valueOf(qty));
		}else{
			NewMenuActivity.popupqty.setText("0");
		}
	}

}
