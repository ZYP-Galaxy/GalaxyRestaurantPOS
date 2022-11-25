package com.galaxy.restaurantpos;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

public class Orderedprogress extends Activity {
	ListView listorder;
	List<RowItem> oddrowItems;
	View v ;
	Context ctx;
	public static Typeface font;
	int selectedindex = -1;
	int selecteddata = 0; // 0 for DineIn , 1 for Parcel
	int maxsr = 0;

	final DatabaseHelper dbhelper = new DatabaseHelper(this);

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		GlobalClass.setCurrentscreen(5);
		//Remove title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		//Remove notification bar
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_orderedprogress);

		ctx = this;
		//font = Typeface.createFromAsset(getAssets(), "fonts/Zawgyi-One.ttf");
		if(!LoginActivity.isUnicode)
			font = Typeface.createFromAsset(getAssets(), "fonts/Pyidaungsu.ttf");
		else
			font = Typeface.createFromAsset(getAssets(), "fonts/Zawgyi-One.ttf");

		((LinearLayout)findViewById(R.id.butdinein)).setSelected(true);
		((LinearLayout)findViewById(R.id.butparcel)).setSelected(false);

		// check for Internet status
		if (GlobalClass.CheckConnection(this))
		{
			new DatabaseHelper(this).LoadActivetable(new DatabaseHelper(this).getServiceURL());
		}
		else
		{

		}

		Bind_DineInData();
		bindstatus();
		GlobalClass.ChangeLanguage((ViewGroup)findViewById(R.id.mainlayout),this,14,font);
		GlobalClass.ChangeLanguage((ViewGroup)findViewById(R.id.statusbarlayout), this, 14, font);

		((Button)findViewById(R.id.butfilterarea)).setTypeface(font);
		((Button)findViewById(R.id.butfilteruser)).setTypeface(font);
	}

	public void bindstatus()
	{
		TextView txtusername = (TextView)findViewById(R.id.txtusername);
		txtusername.setTypeface(font);

		PosUser posuser = new DatabaseHelper(this).getPosUserByUserID(Integer.parseInt(new DatabaseHelper(this).getwaiterid()));

		txtusername.setText(new DatabaseHelper(this).getwaitername()+" - "+posuser.getName());
		txtusername.setTag(new DatabaseHelper(this).getwaiterid());

		ConnectionStatus();
	}



	public void checkconnection_onClick(View v)
	{
		ConnectionStatus();
	}

	public void ConnectionStatus()
	{
		((ImageView)findViewById(R.id.constatus)).setImageResource(0);
		if (GlobalClass.CheckConnection(this))
		{
			((ImageView)findViewById(R.id.constatus)).setImageResource(R.drawable.greenstatus);
		}
		else
		{
			((ImageView)findViewById(R.id.constatus)).setImageResource(R.drawable.redstatus);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if(keyCode == KeyEvent.KEYCODE_BACK)
		{
			Intent intent = new Intent(this, MainScreen.class);
			startActivity(intent);
			this.finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void Combobox(int buttonid , final List<RowItem> rowItems)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		// Get the layout inflater
		LayoutInflater inflater = this.getLayoutInflater();

		// Inflate and set the layout for the dialog
		// Pass null as the parent view because its going in the dialog layout
		builder.setView(inflater.inflate(R.layout.activity_combolist, null));
		final Dialog dialog = builder.create();

		WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
		wmlp.gravity = Gravity.LEFT|Gravity.TOP;
		final Button butfilteruser = (Button)findViewById(buttonid);  // id of clicked button
		int[] location = new int[2];
		butfilteruser.getLocationOnScreen(location);
		wmlp.x = location[0]-GlobalClass.getDPsize(15, ctx);
		wmlp.y = location[1]+GlobalClass.getDPsize(20, ctx);
		dialog.show();
		Window window = dialog.getWindow();
		window.setLayout(GlobalClass.getDPsize(200, this), LayoutParams.WRAP_CONTENT);
		dialog.setCanceledOnTouchOutside(true);
		dialog.show();


		ListView customerlistview = (ListView)dialog.findViewById(R.id.custlist);


		ComboCustomListViewAdapter oddadapter = new ComboCustomListViewAdapter(this,R.layout.combolist_item, rowItems);
		customerlistview.setAdapter(oddadapter);


		customerlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				butfilteruser.setText(rowItems.get(position).getdetail());
				butfilteruser.setTag(rowItems.get(position).getheaderid());

				ClearItem();

				if(selecteddata == 0) // dine in
				{
					Bind_DineInData();
				}
				else
				{
					Bind_ParcelData();
				}

				dialog.dismiss();
			}
		});
	}

	public void Combo_dialog(View v)
	{
		final List<RowItem> rowItems = new ArrayList<RowItem>();
		switch(v.getId())
		{
			case R.id.butfilteruser:
				List<PosUser> userlist = new ArrayList<PosUser>();
				if(new DatabaseHelper(this).getPosUserByUserID(Integer.parseInt(new DatabaseHelper(this).getwaiterid())).getAlluser().equals(false))
				{
					PosUser posuser =new DatabaseHelper(this).getPosUserByUserID(Integer.parseInt(new DatabaseHelper(this).getwaiterid()));
					userlist.add(posuser);
				}
				else
				{
					userlist = new DatabaseHelper(this).getAllPosUserlist();
				}

				for (int j = 0; j < userlist.size(); j++) {
					RowItem item = new RowItem(j,userlist.get(j).getUserId(),"",userlist.get(j).getShort() + " - " +userlist.get(j).getName(),"");
					rowItems.add(item);
				}
				Combobox(R.id.butfilteruser,rowItems);
				break;

			case R.id.butfilterarea:
				List<Area> arealist = new DatabaseHelper(this).getArealist();
				for (int j = 0; j < arealist.size(); j++) {
					RowItem item = new RowItem(j,arealist.get(j).getArea_ID(),"",arealist.get(j).getArea_Name(),"");
					rowItems.add(item);
				}
				Combobox(R.id.butfilterarea,rowItems);
				break;
		}
	}

	public void butfilterclear_onClick(View v)
	{

		Button butfilteruser = (Button)findViewById(R.id.butfilteruser);
		Button butfilterarea = (Button)findViewById(R.id.butfilterarea);

		ClearItem();
		butfilteruser.setText("USER");
		butfilterarea.setText("AREA");
		if(selecteddata == 0) // dine in
		{
			Bind_DineInData();
		}
		else
		{
			Bind_ParcelData();
		}
	}

	public void selectFrag(View view) {
		if(view == findViewById(R.id.butdinein)) {
			Bind_DineInData();
			selecteddata = 0;
			((LinearLayout)findViewById(R.id.butparcel)).setSelected(true);
			((LinearLayout)findViewById(R.id.butdinein)).setSelected(false);
			ClearItem();
		}else {
			Bind_ParcelData();
			selecteddata = 1;
			((LinearLayout)findViewById(R.id.butdinein)).setSelected(true);
			((LinearLayout)findViewById(R.id.butparcel)).setSelected(false);
			ClearItem();
		}
	}
	public void Bind_DineInData()
	{
		Button ButFilterArea = (Button)findViewById(R.id.butfilterarea);
		Button ButFilterUser = (Button)findViewById(R.id.butfilteruser);
		TextView Label = (TextView)findViewById(R.id.txtlabel);

		String AreaString = " 1 = 1 ";
		String UserString = " 1 = 1 ";
		String FilterString = "";
		if(!ButFilterArea.getText().equals("AREA"))
		{
			AreaString = " Area_ID = " + ButFilterArea.getTag();
		}
		if(!ButFilterUser.getText().equals("USER"))
		{
			UserString = " userid = " + ButFilterUser.getTag();
		}


		if(new DatabaseHelper(this).getPosUserByUserID(Integer.parseInt(new DatabaseHelper(this).getwaiterid())).getAlluser().equals(false))
		{
			UserString = " userid = " + new DatabaseHelper(this).getwaiterid();
		}

		if(GlobalClass.use_foodtruck){
			Label.setText("Sale History");
			ButFilterArea.setVisibility(View.INVISIBLE);
			ButFilterUser.setVisibility(View.INVISIBLE);
			((TextView)findViewById(R.id.txtfilter)).setVisibility(View.INVISIBLE);
			((Button)findViewById(R.id.butfilterclear)).setVisibility(View.INVISIBLE);

			((LinearLayout)findViewById(R.id.layout_tableno)).setVisibility(View.GONE);

			List<SaleObj> invoiceList = new DatabaseHelper(this).SaleHistoryList(dbhelper.getServiceURL(), dbhelper.getwaiterid());
			oddrowItems = new ArrayList<RowItem>();
			for (SaleObj obj : invoiceList) {
				RowItem item = new RowItem(Integer.parseInt(obj.getsaleid()), 0,  obj.getinvoiceno(),"","");
				oddrowItems.add(item);
			}

		}
		else {

			Label.setText("DINE IN");
			FilterString = AreaString + " and " + UserString;


			List<Table_Name> tablelist = new DatabaseHelper(this).getActiveTableByFilter(FilterString);
			oddrowItems = new ArrayList<RowItem>();
			for (int i = 0; i < tablelist.size(); i++) {
				String time = "";

				if (tablelist.get(i).getlongminute() > 60) {
					time = Integer.toString(tablelist.get(i).getlongminute() / 60) + "hrs " + Integer.toString(tablelist.get(i).getlongminute() % 60) + "min Ago";
				} else {
					time = Integer.toString(tablelist.get(i).getlongminute() <= 0 ? 1 : tablelist.get(i).getlongminute()) + "min Ago";
				}
				RowItem item = new RowItem(tablelist.get(i).getTranid(), tablelist.get(i).getTable_Name_ID(), tablelist.get(i).getTable_Name(), tablelist.get(i).getDocID(), time);
				oddrowItems.add(item);
			}
		}

		listorder = (ListView) findViewById(R.id.listorder);
		CustomListViewAdapter oddadapter = new CustomListViewAdapter(this, R.layout.list_item, oddrowItems);
		listorder.setAdapter(oddadapter);

		listorder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				RowItem rowitem = (RowItem) listorder.getItemAtPosition(position);
				int tranid = rowitem.gettranid();
				// check for Internet status

				binditemdata(tranid);
				if (selectedindex >= 0) {
					//parent.getChildAt(selectedindex).setBackgroundColor(Color.TRANSPARENT);
				}

				//parent.getChildAt(position).setBackgroundColor(Color.LTGRAY);
				selectedindex = position;
				maxsr = 0;
			}
		});



	}
	public void Bind_ParcelData()
	{
		((TextView)findViewById(R.id.txtlabel)).setText("PARCEL");
		//((CustomListViewAdapter)listorder.getAdapter()).clear();
		List<Table_Name> tablelist= new DatabaseHelper(this).getActiveParcel();
		oddrowItems = new ArrayList<RowItem>();
		for (int i = 0; i < tablelist.size(); i++) {

			String time = "";

			if(tablelist.get(i).getlongminute() > 60)
			{
				time = Integer.toString(tablelist.get(i).getlongminute()/60)+ "hrs " +  Integer.toString(tablelist.get(i).getlongminute()%60) + "min Ago" ;
			}
			else
				time = Integer.toString(tablelist.get(i).getlongminute()) + "min Ago" ;

			RowItem item = new RowItem(tablelist.get(i).getTranid(),tablelist.get(i).getTable_Name_ID(),tablelist.get(i).getTable_Name(),tablelist.get(i).getDocID(), time);
			oddrowItems.add(item);
		}

		listorder = (ListView)findViewById(R.id.listorder);
		CustomListViewAdapter oddadapter = new CustomListViewAdapter(this,R.layout.list_item, oddrowItems);
		listorder.setAdapter(oddadapter);

		listorder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				RowItem rowitem = (RowItem)listorder.getItemAtPosition(position);
				int tranid = rowitem.gettranid();

				//new DatabaseHelper(ctx).LoadSaleHeader(new DatabaseHelper(ctx).getServiceURL(),"" , Integer.toString(tranid));
				//new DatabaseHelper(ctx).LoadSaleItem(new DatabaseHelper(ctx).getServiceURL(), Integer.toString(tranid));

				binditemdata(tranid);
				if(selectedindex>=0)
				{
					parent.getChildAt(selectedindex).setBackgroundColor(Color.TRANSPARENT);
				}

				parent.getChildAt(position).setBackgroundColor(Color.LTGRAY);
				selectedindex = position;
			}
		});
	}
	public void binditemdata(int tranid)
	{
		VoucherDetailObj vouchersummary = new VoucherDetailObj();
		if (GlobalClass.CheckConnection(ctx))
		{
			new DatabaseHelper(ctx).LoadSaleHeader(new DatabaseHelper(ctx).getServiceURL(),"", Integer.toString(tranid));
			new DatabaseHelper(ctx).LoadSaleItem(new DatabaseHelper(ctx).getServiceURL(), Integer.toString(tranid));
			vouchersummary = new DatabaseHelper(ctx).LoadSaleSummary(new DatabaseHelper(ctx).getServiceURL(), Integer.toString(tranid));
		}
		else
		{

		}
		final TextView txtinvoiceno = (TextView)findViewById(R.id.txtinvoiceno);
		final TextView txttableno = (TextView)findViewById(R.id.txttableno);
		final TextView txtcustomer = (TextView)findViewById(R.id.txtcustomer);
		final TextView txtgtax = (TextView)findViewById(R.id.txtgtax);
		final TextView txtstax = (TextView)findViewById(R.id.txtstax);
		final TextView txtrtax = (TextView)findViewById(R.id.txtrtax);//Added by Arkar Moe on [21/07/2016] for adding room tax in tablet voucher [Res-0209]
		final TextView txtdelicharges = (TextView)findViewById(R.id.txtdelicharges);
		final TextView txtdiscount = (TextView)findViewById(R.id.txtdiscount);
		final TextView txtmemdiscount = (TextView)findViewById(R.id.txtMemDiscount);
		final TextView txtmemamount = (TextView)findViewById(R.id.txtMemAmount);
		final TextView txtnettotal = (TextView)findViewById(R.id.txtNetTotal);
		final TextView txtfocamount = (TextView)findViewById(R.id.txtfocamount);
		final TextView txtitemdiscount = (TextView)findViewById(R.id.txtitemdiscount);

		txtgtax.setText(dbhelper.getCurrencyFormat(Double.toString(Double.parseDouble(vouchersummary.getgtaxamount()))));
		//txtstax.setText(Double.toString(Double.parseDouble(vouchersummary.getstaxamount())));
		//txtrtax.setText(Double.toString(Double.parseDouble(vouchersummary.getrtaxamount())));

		//txtgtax.setText(dbhelper.getCurrencyFormat(Double.toString((int)Double.parseDouble(vouchersummary.getgtaxamount())).replace("", ""))); //Added by Arkar Moe on [04/10/2016]-[Res-0437]
		txtstax.setText(dbhelper.getCurrencyFormat(Double.toString((int)Double.parseDouble(vouchersummary.getstaxamount())).replace("", "")));
		txtrtax.setText(dbhelper.getCurrencyFormat(Double.toString((int)Double.parseDouble(vouchersummary.getrtaxamount())).replace("", "")));
		txtdelicharges.setText(dbhelper.getCurrencyFormat(vouchersummary.getDelivery_charges())); //added by ZYP 24-08-2020

		if (vouchersummary.getdiscount().toString().equals(""))
		{

		}else
		{
			txtdiscount.setText(dbhelper.getCurrencyFormat(Double.toString(Double.parseDouble(vouchersummary.getdiscount()))));
		}

		if (vouchersummary.getitemdiscount().toString().equals(""))
		{

		}else
		{
			txtitemdiscount.setText(dbhelper.getCurrencyFormat(Double.toString(Double.parseDouble(vouchersummary.getitemdiscount()))));
		}


		if (vouchersummary.getmemamount().toString().equals(""))
		{

		}
		else
		{
			if (Double.parseDouble(vouchersummary.getMemdiscount()) > 0.0) {
				txtmemdiscount.setText("Mem.Dis  (" + vouchersummary.getMemdiscount().split("\\.")[0] + " %) :");
			}
			txtmemamount.setText(dbhelper.getCurrencyFormat(Double.toString(Double.parseDouble(vouchersummary.getmemamount()))));
		}
		if (vouchersummary.getnetamount().toString().equals(""))
		{}
		else
		{
			txtnettotal.setText(dbhelper.getCurrencyFormat(Double.toString(Double.parseDouble(vouchersummary.getnetamount()))));
		}
		if (vouchersummary.getfocamount().toString().equals(""))
		{

		}else
		{
			txtfocamount.setText(dbhelper.getCurrencyFormat(Double.toString(Double.parseDouble(vouchersummary.getfocamount()))));
		}




		SaleObj saleobj = dbhelper.getSaledataBySaleID(tranid);
		txtinvoiceno.setText((saleobj.getRef_no().equals("null") ? "":saleobj.getRef_no()));
		txtinvoiceno.setTag(tranid);
		txttableno.setText(
				dbhelper.getTableNameByTableID(Integer.parseInt(saleobj.gettablenameid()))); //added by ZYP for unicode [06-01-2020]

		txtcustomer.setText(dbhelper.getCustomerNameByCustomerID(saleobj.getcustomerid()));  //added by ZYP for unicode [06-01-2020]

		List<SaleItemObj> saleitemobjlist = new ArrayList<SaleItemObj>();
		saleitemobjlist = dbhelper.getSaleItemdataBySaleID(tranid);

		final LayoutParams tablerowlayout = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		final LayoutParams itemmainlayoutpara = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		itemmainlayoutpara.setMargins(0, 0, 0, 5);
		//int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics());
		final LayoutParams srlayoutpara = new LayoutParams(GlobalClass.getDPsize(25, this),LayoutParams.WRAP_CONTENT);

		final LayoutParams namelayoutpara = new LayoutParams(GlobalClass.getDPsize(150, this) , LayoutParams.WRAP_CONTENT);

		final LayoutParams pricelayoutpara = new LayoutParams(GlobalClass.getDPsize(63, this) , LayoutParams.WRAP_CONTENT);

		final LayoutParams qtylayoutpara = new LayoutParams(GlobalClass.getDPsize(50, this) , LayoutParams.WRAP_CONTENT);

		final LayoutParams amountlayoutpara = new LayoutParams(GlobalClass.getDPsize(90, this) , LayoutParams.WRAP_CONTENT);

		final LinearLayout tbllayout = (LinearLayout)findViewById(R.id.itemlayout);

		final LayoutParams dividerlayout = new LayoutParams(GlobalClass.getDPsize(520, this), 1);
		tbllayout.removeAllViews();
		Double totalamount = 0.0;
		Double grandtotal = 0.0;

		TextView txtline = new TextView(this);
		txtline.setBackgroundColor(Color.parseColor("#FFFFFF"));
		tbllayout.addView(txtline,dividerlayout);



		for (SaleItemObj saleItemObj : saleitemobjlist) {
			totalamount = 0.0;
			LinearLayout itemmainlayout = new LinearLayout(this);
			itemmainlayout.setOrientation(LinearLayout.VERTICAL);

			LinearLayout tr = new LinearLayout(this);

			TextView txtsr = new TextView(this);
			//txtsr.setText(saleItemObj.getsrno());
			maxsr++;
			txtsr.setText(String.valueOf(maxsr));
			txtsr.setTextColor(Color.parseColor("#FFFFFF"));

			TextView txtname = new TextView(this);
			txtname.setText(
					dbhelper.getItemNamebyitemid(saleItemObj.getcode())); 		//added by ZYP for unicode [06-01-2020]
			txtname.setTypeface(font);
			txtname.setTextColor(Color.parseColor("#FFFFFF"));

			TextView txtprice = new TextView(this);
			txtprice.setText(dbhelper.getCurrencyFormat(saleItemObj.getprice()));
			txtprice.setGravity(Gravity.RIGHT);
			txtprice.setTextColor(Color.parseColor("#FFFFFF"));

			TextView txtqty = new TextView(this);
			txtqty.setText(saleItemObj.getqty());
			txtqty.setGravity(Gravity.RIGHT);
			txtqty.setTextColor(Color.parseColor("#FFFFFF"));

			String unitname = "";
			if(dbhelper.getuse_unit().equals("true"))
			{
				List<UnitCodeObj> unitcodeobjlist = dbhelper.getunitcodebyunittype(saleItemObj.getcode(), saleItemObj.getunittype());

				if(unitcodeobjlist.size() > 0)
				{
					unitname = unitcodeobjlist.get(0).getunitname();

				}
			}

			TextView txtunit = new TextView(this);
			txtunit.setText(unitname);
			txtunit.setGravity(Gravity.RIGHT);
			txtunit.setTextColor(Color.parseColor("#FFFFFF"));

			TextView txtamount = new TextView(this);
			txtamount.setText(dbhelper.getCurrencyFormat(saleItemObj.getamount()));
			txtamount.setTag(saleItemObj.getamount());
			txtamount.setGravity(Gravity.RIGHT);
			txtamount.setTextColor(Color.parseColor("#FFFFFF"));

			TextView txttotalamount = new TextView(this);
			txttotalamount.setGravity(Gravity.RIGHT);
			txttotalamount.setTextColor(Color.parseColor("#FFFFFF"));

			totalamount = totalamount + Double.parseDouble(txtamount.getTag().toString());

			if(saleItemObj.getcancelflag().equals(true))
			{
				txtsr.setPaintFlags(txtsr.getPaintFlags()|Paint.STRIKE_THRU_TEXT_FLAG);
				txtname.setPaintFlags(txtname.getPaintFlags()|Paint.STRIKE_THRU_TEXT_FLAG);
				txtprice.setPaintFlags(txtprice.getPaintFlags()|Paint.STRIKE_THRU_TEXT_FLAG);
				txtqty.setPaintFlags(txtqty.getPaintFlags()|Paint.STRIKE_THRU_TEXT_FLAG);
				txtamount.setPaintFlags(txtamount.getPaintFlags()|Paint.STRIKE_THRU_TEXT_FLAG);
				txttotalamount.setPaintFlags(txttotalamount.getPaintFlags()|Paint.STRIKE_THRU_TEXT_FLAG);
			}
			tr.addView(txtsr,srlayoutpara);
			tr.addView(txtname,namelayoutpara);
			tr.addView(txtprice,pricelayoutpara);
			tr.addView(txtqty,qtylayoutpara);
			tr.addView(txtunit,qtylayoutpara);
			tr.addView(txtamount , amountlayoutpara);
			tr.addView(txttotalamount,amountlayoutpara);
			itemmainlayout.addView(tr , tablerowlayout);

			List<SelectedItemModifierObj> modifierobjlist = dbhelper.getSaleItemModifierdataBySaleID(Integer.toString(tranid), saleItemObj.getsrno());

			for (SelectedItemModifierObj selectedItemModifierObj : modifierobjlist){
				LinearLayout modifiertr = new LinearLayout(this);
				TextView txtmodifiersr = new TextView(this);
				txtmodifiersr.setText("");

				TextView txtmodifiername = new TextView(this);
				txtmodifiername.setText( " * "+selectedItemModifierObj.getname());
				txtmodifiername.setTextColor(Color.BLUE);
				txtmodifiername.setTypeface(font);

				ItemsObj itemobj = dbhelper.getItemsbyitemid(saleItemObj.getcode());
				if (Boolean.parseBoolean(itemobj.getissetmenu()))
				{
					selectedItemModifierObj.setprice("0.00");
					selectedItemModifierObj.setamount("0.00");
				}
				TextView txtmodifierprice = new TextView(this);
				txtmodifierprice.setText(dbhelper.getCurrencyFormat(selectedItemModifierObj.getprice()));
				txtmodifierprice.setGravity(Gravity.RIGHT);
				txtmodifierprice.setTextColor(Color.BLUE);

				TextView txtmodifierqty = new TextView(this);
				txtmodifierqty.setText(selectedItemModifierObj.getqty());
				txtmodifierqty.setGravity(Gravity.RIGHT);
				txtmodifierqty.setTextColor(Color.BLUE);

				TextView txtmodifieramount = new TextView(this);
				txtmodifieramount.setText(dbhelper.getCurrencyFormat(selectedItemModifierObj.getamount()));
				txtmodifieramount.setTag(selectedItemModifierObj.getamount());
				txtmodifieramount.setGravity(Gravity.RIGHT);
				txtmodifieramount.setTextColor(Color.BLUE);

				totalamount = totalamount + Double.parseDouble(txtmodifieramount.getTag().toString());

				modifiertr.addView(txtmodifiersr,srlayoutpara);
				modifiertr.addView(txtmodifiername,namelayoutpara);
				modifiertr.addView(txtmodifierprice,pricelayoutpara);
				modifiertr.addView(txtmodifierqty,qtylayoutpara);
				modifiertr.addView(txtmodifieramount , amountlayoutpara);
				itemmainlayout.addView(modifiertr , tablerowlayout);
			}

			txttotalamount.setText(dbhelper.getCurrencyFormat(Double.toString(totalamount)));

			if(!saleItemObj.getcancelflag().equals(true))
			{
				grandtotal = grandtotal + totalamount;
			}

			tbllayout.addView(itemmainlayout,itemmainlayoutpara);
		}

		TextView txtlastline = new TextView(this);
		txtlastline.setBackgroundColor(Color.parseColor("#FFFFFF"));
		tbllayout.addView(txtlastline,dividerlayout);

		LinearLayout tr = new LinearLayout(this);

		TextView txtsr = new TextView(this);
		txtsr.setText("");
		txtsr.setTextColor(Color.parseColor("#FFFFFF"));

		TextView txtname = new TextView(this);
		txtname.setText("Total Amount");
		txtname.setTextColor(Color.parseColor("#FFFFFF"));

		TextView txtprice = new TextView(this);
		txtprice.setText("");
		txtprice.setGravity(Gravity.RIGHT);
		txtprice.setTextColor(Color.parseColor("#FFFFFF"));

		TextView txtqty = new TextView(this);
		txtqty.setText("");
		txtqty.setGravity(Gravity.RIGHT);
		txtqty.setTextColor(Color.parseColor("#FFFFFF"));

		TextView txtunit = new TextView(this);
		txtunit.setText("");
		txtunit.setGravity(Gravity.RIGHT);
		txtunit.setTextColor(Color.parseColor("#FFFFFF"));

		TextView txtamount = new TextView(this);
		txtamount.setText("");
		txtamount.setGravity(Gravity.RIGHT);
		txtamount.setTextColor(Color.parseColor("#FFFFFF"));

		TextView txttotalamount = new TextView(this);
		txttotalamount.setText(dbhelper.getCurrencyFormat(Double.toString(grandtotal)));
		txttotalamount.setGravity(Gravity.RIGHT);
		txttotalamount.setTypeface(txttotalamount.getTypeface(), Typeface.BOLD);
		txttotalamount.setTextColor(Color.parseColor("#FFFFFF"));

		tr.addView(txtsr,srlayoutpara);
		tr.addView(txtname,namelayoutpara);
		tr.addView(txtprice,pricelayoutpara);
		tr.addView(txtqty,qtylayoutpara);
		tr.addView(txtunit,qtylayoutpara);
		tr.addView(txtamount , amountlayoutpara);
		tr.addView(txttotalamount,amountlayoutpara);
		tbllayout.addView(tr , tablerowlayout);

	}

	public void ClearItem()
	{
		LinearLayout itemlayout = (LinearLayout)findViewById(R.id.itemlayout);
		itemlayout.removeAllViews();

		final TextView txtinvoiceno = (TextView)findViewById(R.id.txtinvoiceno);
		final TextView txttableno = (TextView)findViewById(R.id.txttableno);
		final TextView txtcustomer = (TextView)findViewById(R.id.txtcustomer);

		txtinvoiceno.setText("InvoiceNo");
		txtinvoiceno.setTag(null);
		txttableno.setText("TableNo");
		txttableno.setTag(-1);// listview position for changing color
		txtcustomer.setText("Customer");
		selectedindex = -1;

	}
}