
package com.galaxy.restaurantpos;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import androidx.core.app.NotificationCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class TableScreenActivity extends Activity implements OnClickListener {

	private static final int MY_NOTIFICATION_ID=1;
	NotificationManager notificationManager;
	Notification myNotification;
	final DatabaseHelper dbhelper = new DatabaseHelper(this);
    static CountDownTimer countdowntimer;

	private MyBroadcastReceiver myBroadcastReceiver;//commented by WaiWL on 26/08/2015
	final Context ctx = this;
	static String splited_saleid= null;
	//added by WaiWL on 15/09/2015
	private CheckBox chkoffline;
	private ProgressDialog progressBar;	// Progress
	private int progressBarStatus = 0;
	private Handler progressBarHandler = new Handler();
	/////////////
	
	private int TableNameId = 0;//added by WaiWL on 16/09/2015
	

	Button SelectedAreaButton,SelectedTableButton;
	static Typeface font;
	static Intent intentMyIntentService;
	
	public static int id = 0 ;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        GlobalClass.setCurrentscreen(3);
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);        
        setContentView(R.layout.activity_table_screen);
               
        //Tablelayout= (LinearLayout)findViewById(R.id.lintablelayout);        
//		LoginActivity.isUnicode=dbhelper.isUnicode();
		//font = Typeface.createFromAsset(getAssets(), "fonts/Zawgyi-One.ttf");
		if(LoginActivity.isUnicode)		//added by ZYP 17-12-2019
			font = Typeface.createFromAsset(getAssets(), "fonts/Pyidaungsu.ttf");
		else
			font = Typeface.createFromAsset(getAssets(), "fonts/Zawgyi-One.ttf");

    	//added by EKK on 12-12-2019
    	if (GlobalClass.CheckConnection(ctx)) {
			Json_class jsonclass = new Json_class();
			jsonclass.getString(dbhelper
					.getServiceURL()
					+ "/Data/ClearUserLog?userid="
					+ java.net.URLEncoder
							.encode(dbhelper
									.getwaiterid()));
		}

		//added WHM [2020-05-13]
		if(dbhelper.use_deliverymanagement()==true) {
			dbhelper.ClearTable("Delivery_Entry_Detail_Tmp");
		}
    	
    	//if(GlobalClass.getServicestarted() == true)
    	//{
    	if (dbhelper.getusemonitorinterface().equals("Y"))
    	{
    		//commented by WaiWL on 26/08/2015
    		//Start MyIntentService
        	intentMyIntentService = new Intent(this, MyIntentService.class);     	
    		intentMyIntentService.putExtra("loaddataintent", "loadData");					
    		startService(intentMyIntentService); 
    		
    		notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);		
    		myBroadcastReceiver = new MyBroadcastReceiver();
    		//register BroadcastReceiver
    		IntentFilter intentFilter = new IntentFilter(MyIntentService.ACTION_MyIntentService);
    		intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
    		registerReceiver(myBroadcastReceiver, intentFilter);
    		
    	}
    		    		    	
   		//GlobalClass.setServicestarted(true);
   	//}	
    	
    	List<KitchendataObj> kitchendatalist = dbhelper.getkitchenfinishlist();
    	if(kitchendatalist.size() > 0 )
    	{
    		GlobalClass.MIFinishedItemscount = 0;
    		MIFinishedItemsDialog();
    	}
    		
    	GlobalClass.tmpOffline = dbhelper.getOfflineFlag();
		new DatabaseHelper(this).Delete_Sale_Head();
		new DatabaseHelper(this).Delete_Sale_Det();
		
		if (dbhelper.hasShift) {
			dbhelper.hasShift=false;
			showAlertDialog(ctx, "Warring!!","User need to end of Shift" , false);
		
		}
		
		if (dbhelper.tabletuserlock) {
			dbhelper.tabletuserlock=false;
			final String dataurl = new DatabaseHelper(this).getServiceURL();
			dbhelper.LoadUserLog(dataurl);
			String user = dbhelper.getUser(id);
			showAlertDialog(ctx, "Warring!!","This table is handled by [" + user+"]", false);
		
		}
		
			bindAreaButtonlist();
	        bindstatus();
	        
	        ///added by WaiWL on 29/06/2015
		    final Button butLoadTable=(Button)findViewById(R.id.butLoadTable);
		    if(GlobalClass.tmpOffline==false)
		    	butLoadTable.setVisibility(View.GONE);
		    else
		    	butLoadTable.setVisibility(View.VISIBLE);
		
    }   
    

    //commented by WaiWL on 26/08/2015
    public class MyBroadcastReceiver extends BroadcastReceiver {


		@Override
		public void onReceive(Context context, Intent intent) {	
			
			TextView myText = (TextView) findViewById(R.id.txtready);
			List<KitchendataObj> kitchendatalist = dbhelper.getkitchenfinishlist();
			
			//commented by WaiWL on 16/09/2015
			if(kitchendatalist.size() > 0 )
			{
				//generate notification
				//if(GlobalClass.MIFinishedItemscount < kitchendatalist.size())
				//{
					String notificationText = "Ready Some Dishes!" ;
					myNotification = new NotificationCompat.Builder(getApplicationContext())
					.setContentTitle("Ready")
					.setContentText(notificationText)
					.setTicker("Notification!")
					.setWhen(System.currentTimeMillis())
					.setDefaults(Notification.DEFAULT_SOUND)
					.setAutoCancel(true)
					.setSmallIcon(R.drawable.ic_launcher)
					.build();
					
					notificationManager.notify(MY_NOTIFICATION_ID, myNotification);

			    	Animation anim = new AlphaAnimation(0.0f, 1.0f);
			    	anim.setDuration(600); //You can manage the time of the blink with this parameter
			    	anim.setStartOffset(20);
			    	anim.setRepeatMode(Animation.REVERSE);
			    	anim.setRepeatCount(Animation.INFINITE);
			    	myText.setText("READY FOR SOME DISHES!");
			    	myText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
			    	myText.startAnimation(anim);
				//}
				
				/////////
				
				/*for(int i= 0; i< kitchendatalist.size(); i++)
				{									
					AlertDialog alertDialog = new AlertDialog.Builder(context).create();
			        alertDialog.setTitle("Ready for Table " + dbhelper.getTableNameByTableID(kitchendatalist.get(i).getTable_Name_ID()));						       
			        alertDialog.setMessage(kitchendatalist.get(i).getdescription());			        			       
			        alertDialog.setIcon(0);	
			        alertDialog.setCanceledOnTouchOutside(false);
			        alertDialog.setCancelable(false);
			        // Setting OK Button
			        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			            public void onClick(DialogInterface dialog, int which) {
			            }
			        });    
			        alertDialog.show();							
				}*/
			}
			else
			{
				myText.setText("");
			}
			
			myText.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
//					List<KitchendataObj> kitchendatalist = dbhelper.getkitchenfinishlist();
//			    	if(kitchendatalist.size() > 0 )
//			    	{
//			    		GlobalClass.MIFinishedItemscount = 0;
//			    		MIFinishedItemsDialog();
//			    	}
				}
			});
			
			////////////
		}

		
	}

	
    //commented by WaiWL on 26/08/2015
    @Override
	protected void onDestroy() {
		super.onDestroy();
		//un-register BroadcastReceiver

		try
		{
			if (dbhelper.getusemonitorinterface().equals("Y"))
	    	{
				unregisterReceiver(myBroadcastReceiver);
			//	stopService(intentMyIntentService);
	    	}
			
		}
		catch(Exception ex)
		{
			String error = ex.getMessage();
			
		}

	}

    
    public void bindstatus()
    {
    	TextView txtusername = (TextView)findViewById(R.id.txtusername);
        txtusername.setTypeface(font);
        
        PosUser posuser = dbhelper.getPosUserByUserID(Integer.parseInt(dbhelper.getwaiterid()));
        String posusername = dbhelper.getwaitername()+" - "+posuser.getName();
        txtusername.setText(posusername);
        txtusername.setTag(dbhelper.getwaiterid());
        
    	checkConnectionStatus();
    }
    
    public void GetDatawithoutprogress()
    {
    	// prepare for a progress bar dialog
    	final String dataurl = new DatabaseHelper(this).getServiceURL();  						
		try
		{
			new Thread(new Runnable() {
			  public void run() {		
				  try
				  {
					  dbhelper.LoadArea(dataurl);	    				 		  			  
					  dbhelper.LoadTable(dataurl);	    				 				  				  
					  dbhelper.LoadItemSecondTime(dataurl);	    				 					  						  					  					  
					  dbhelper.LoadModifiedItemSecondTime(dataurl);	 				  
					  dbhelper.LoadModifierGroup(dataurl);	    				 				  				  
					  dbhelper.LoadDictionary(dataurl);	    				 				  				 
					  dbhelper.LoadCustomer(dataurl);	    				 				  				  
					  dbhelper.LoadPosUser(dataurl);	    				 				  				  			  						  				
					  //Date d = new Date();
					  //CharSequence s  = DateFormat.format("yyyy-MM-dd kk:mm:ss", d.getTime());			  
					  dbhelper.UpdateTimeLog(dbhelper.LoadServerDate(dataurl));						  
				  }
				  catch(Exception ex)
				  {
					  
				  }
				  			  			 
			  }
		       }).start(); 
		}
		catch(Exception ex)
		{		
		}
		finally
		{						
		}		      
    }      
    public void checkconnection_onClick(View v)
    {
    	checkConnectionStatus();    	
    }
    
    //added by WaiWL on 17/09/2015
    /*public void callMIFinishedItems_onClick()
    {
    	List<KitchendataObj> kitchendatalist = dbhelper.getkitchenfinishlist();
    	if(kitchendatalist.size() > 0 )
    	{
    		GlobalClass.MIFinishedItemscount = 0;
    		MIFinishedItemsDialog();
    	}
    }*/
    /////////////
    
    public void checkConnectionStatus()
    {
    	    
		// check for Internet status		
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
    
    public void bindAreaButtonlist()
    {
		//added WHM [2020-11-11] userarea
		dbhelper.get_strgetarea_groupbyuser(dbhelper.getwaiterid()) ;

    	final LinearLayout Area = (LinearLayout)findViewById(R.id.AreaLayout);
    	Area.removeAllViews();
    	//Bind Area List
        List<Area> AreaList = dbhelper.getArealist_TableScreen();//modified WHM [2020-11-11] userarea //orgd bhelper.getArealist()
        for(int i=0;i<AreaList.size();i++)
        {
        	LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(GlobalClass.getDPsize(185, this),GlobalClass.getDPsize(60, this));
    		params.setMargins(6, 6, 0, 4);		
        	Area.addView(CreateAreaButton(AreaList.get(i)),params);
        }
        
        //Set the Area 1 pressed.
                 
        if(AreaList.size()>0)
        {
			if(GlobalClass.use_menucategorygrouptype == 1) //added WHM [2020-09-30] menucategory  YGN2-200983
			{
				dbhelper.str_category(GlobalClass.getSale_deflocationID());
				GlobalClass.Bind_MenuCategoryGroup();
			}

        	Integer iDefaultArea = 0;
        	if(!(GlobalClass.GetSelectedAreaButton() == null))
        	{
        		iDefaultArea = Integer.parseInt(GlobalClass.GetSelectedAreaButton().getTag().toString().split("-")[1]);
        		BindTableList(iDefaultArea);
        		SelectedAreaButton = GlobalClass.GetSelectedAreaButton();        		
        		SelectedAreaButton =(Button)Area.findViewWithTag(SelectedAreaButton.getTag());

    			SelectedAreaButton.setBackgroundColor(Color.parseColor("#a100a9"));

				//added WHM menucategory[2020-09-30] YGN2-200983
				if(GlobalClass.use_menucategorygrouptype == 2)
				{
					dbhelper.str_category(iDefaultArea);
					GlobalClass.Bind_MenuCategoryGroup();
				}
        	}
        	else
        	{
        		 iDefaultArea = AreaList.get(0).getArea_ID();
        		 BindTableList(iDefaultArea);
            	if(Area.getChildAt(0) != null)
            	{
            		if(Area.getChildAt(0) instanceof Button)
            		{
            			SelectedAreaButton = (Button)Area.getChildAt(0);
            			SelectedAreaButton.setBackgroundColor(Color.parseColor("#a100a9"));
            		}
            	}

				//added WHM [2020-09-30] YGN2-200983
				if(GlobalClass.use_menucategorygrouptype == 2)
				{
					dbhelper.str_category(iDefaultArea);
					GlobalClass.Bind_MenuCategoryGroup();
				}
        		
        	}                        	        	        	       
        } 

        //checkConnectionStatus();
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
        //return (keyCode == KeyEvent.KEYCODE_BACK ? true : super.onKeyDown(keyCode, event));
    }
    
    @Override  
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_table_screen, menu);
        return true;
    }
    
    private Button CreateAreaButton(Area A)
    {
    	Button p1 = new Button(this);
    	p1.setTypeface(font);
    	
		p1.setText(A.getArea_Name());
		p1.setTag("Area-" + A.getArea_ID());
		p1.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
		p1.setTextColor(Color.WHITE);
		p1.setBackgroundColor(Color.parseColor("#0a59c1"));
		//p1.setTextAppearance(this, R.style.areabutton_text);
		p1.setOnClickListener(this);
		
    	return p1;    	
    }
    
    private Button CreateTableButton(Table_Name A)
    { 
    	Button p1 = new Button(this);
    	p1.setTypeface(font);
		p1.setText(A.getTable_Name());
		p1.setTag("Table-" + A.getTable_Name_ID());
		p1.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
		p1.setTextColor(Color.WHITE);
		p1.setSingleLine(true);
	
	
		List<Table_Name> tableobjlist = dbhelper.getActiveTableByTableID(Integer.toString(A.getTable_Name_ID()));

		//added by EKK on 08-01-2020
		if (dbhelper.check_isBusy(A.getTable_Name_ID()))
		{
			p1.setBackgroundResource(R.drawable.busytable);
			p1.setEnabled(false);

		}
		else if(tableobjlist.size() > 0 )
		{


			if(dbhelper.check_isreserved(A.getTable_Name_ID()))
			{
				p1.setBackgroundColor(Color.parseColor("#009f00")); //green
			}

			//added WHM [2020-05-19] self order
			else if(dbhelper.check_selfordertable((A.getTable_Name_ID())))
			{
				p1.setBackgroundColor(Color.parseColor("#005064"));
				if(dbhelper.check_Bill(A.getTable_Name_ID())){

					p1.setBackgroundResource(R.drawable.selforder_bill);
				}
			}

			else
			{

				if(dbhelper.check_Bill(A.getTable_Name_ID())){
				
					p1.setBackgroundResource(R.drawable.customerbill);
				}
				else{
					if(dbhelper.getwaiterid().equals(Integer.toString(tableobjlist.get(0).getuserid())))
						p1.setBackgroundResource(R.drawable.mycustomertable);
					else
						p1.setBackgroundResource(R.drawable.customertable);
				}

			}			
		}
		else
		{

			p1.setBackgroundColor(Color.parseColor("#008ad2")); // blue

		}

		
		p1.setOnClickListener(this);				
    	return p1;    	
    }
    
    
    public void butback_onClick(View v)
    {
    	finish();
    	Intent intent = new Intent(this, MainScreen.class);
    	startActivity(intent);
    	
    }
        
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v instanceof Button)
		{
			Button menubutton = (Button)v;
			String buttonTag = menubutton.getTag().toString();
			if(buttonTag.contains("Area-"))
			{
				Integer AreaId = Integer.parseInt(buttonTag.replace("Area-", ""));
				//showAlertDialog(this, buttonTag, buttonTag, false);
				GlobalClass.SetSelectedAreaButton(menubutton);				
				BindTableList(AreaId);

				//added WHM [2020-09-30] menucategory YGN2-200983
				if(GlobalClass.use_menucategorygrouptype == 2)
				{
					dbhelper.str_category(AreaId);
					GlobalClass.Bind_MenuCategoryGroup();
				}
				
				if(SelectedAreaButton != null)
					SelectedAreaButton.setBackgroundColor(Color.parseColor("#0a59c1"));
					//SelectedAreaButton.setBackgroundResource(R.drawable.areabutton);
				//menubutton.setBackgroundResource(R.drawable.areabutton_selected);
				menubutton.setBackgroundColor(Color.parseColor("#a100a9"));
				SelectedAreaButton = menubutton;
			}
			else if(buttonTag.contains("Table-"))
			{
				if(!GlobalClass.CheckConnection(ctx)){
					checkConnectionStatus();
					showAlertDialog(ctx, "Warning!","No connection with Server.", false);
					return;
				}
				if(SelectedTableButton != null)
					//SelectedTableButton.setBackgroundColor(Color.parseColor("#008ad2"));
												
				SelectedTableButton = menubutton;				
				Integer TableID = Integer.parseInt(buttonTag.replace("Table-", ""));
				
				id = TableID; //added by EKK

				List<Table_Name> tableobjlist = dbhelper.getActiveTableByTableID(Integer.toString(TableID));
				
				Json_class jsonclass = new Json_class();

				String tranid = jsonclass.getString(
						new DatabaseHelper(this).getServiceURL()
								+ "/Data/CheckAllowEditAfterInsert?tablenameid=" +TableID.toString());
				  
				
				if(tableobjlist.size() > 0)
				{
					if(dbhelper.getwaiterid().equals(Integer.toString(tableobjlist.get(0).getuserid())))
					{
						menubutton.setBackgroundResource(R.drawable.mycustomertable);
					}
					else
					{
						if(dbhelper.getPosUserByUserID(Integer.parseInt(dbhelper.getwaiterid())).getAlluser().equals(false))
						{							
							String tableusername = dbhelper.getPosUserByUserID(tableobjlist.get(0).getuserid()).getName();
							
							menubutton.setBackgroundResource(R.drawable.customertable);					
							showAlertDialog(this,"Warning!!", "This Table ["+ dbhelper.getTableNameByTableID(TableID) +"] is by another user [" +tableusername+ "]!" , false);
							return;						
						}						
					}
				}
				else
				{
					menubutton.setBackgroundResource(R.drawable.customertable);								
				}
					
				//menubutton.setBackgroundColor(Color.parseColor("#800000"));
				
				String UserID = dbhelper.getwaiterid();
				if (dbhelper.Allow_edit_after_insert(UserID) == false && tranid!=""){
					
					Intent intent = new Intent(this, VoucherDetail.class);

					Bundle b = new Bundle();
					b.putString("tranid", tranid.trim());
					b.putString("userid",UserID.trim());
					intent.putExtras(b);
					startActivityForResult(intent, 100);

					
				}else{
					if(tableobjlist.size() > 1)
					{
						SplitedVouchers(Integer.toString(TableID));
					}
					else
					{
						String TableName = ((Button)v).getText().toString();
							Intent intent = new Intent(this, OrderTaking.class);						
							Bundle b = new Bundle();
							b.putString("TableID", Integer.toString(TableID));
							b.putString("TableName",TableName);
							intent.putExtras(b);				
						    startActivityForResult(intent, 100);
							finish();
//						}
											
					}
				}
				
			
/*				String TableName = ((Button)v).getText().toString();
				Intent intent = new Intent(this, OrderTaking.class);
				
				Bundle b = new Bundle();
				b.putString("TableID", Integer.toString(TableID));
				b.putString("TableName",TableName);
				intent.putExtras(b);				
				startActivityForResult(intent, 100);
				finish();
			*/
				
				/*
				if(tableobjlist.size() > 1)
				{
					String TableName = ((Button)v).getText().toString();
					Intent intent = new Intent(this, SplitedVouchersAcitvity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					
					Bundle b = new Bundle();
					b.putString("TableID", Integer.toString(TableID));
					b.putString("TableName",TableName);
					intent.putExtras(b);				
					startActivityForResult(intent, 100);
					//finish();
				}
				else
				{
					String TableName = ((Button)v).getText().toString();
					Intent intent = new Intent(this, OrderTaking.class);
					
					Bundle b = new Bundle();
					b.putString("TableID", Integer.toString(TableID));
					b.putString("TableName",TableName);
					intent.putExtras(b);				
					startActivityForResult(intent, 100);
					finish();
				}*/
			}
		}
		//checkConnectionStatus();
	}
	
	private void SplitedVouchers(String tableID){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		// Get the layout inflater
		LayoutInflater inflater = this.getLayoutInflater();
		// Inflate and set the layout for the dialog
		// Pass null as the parent view because its going in the dialog layout
		builder.setView(inflater.inflate(
				R.layout.activity_splitedvoucher, null));

		final Dialog dialog = builder.create();
		WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
		wmlp.gravity = Gravity.CENTER;

/*		Bundle b = this.getIntent().getExtras(); // Get Parameter from table
		tableID = b.getString("TableID");*/
		
		Window window = dialog.getWindow();
		window.setLayout(GlobalClass.getDPsize(800, this),GlobalClass.getDPsize(800, this));
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();

		final String dataurl = new DatabaseHelper(this).getServiceURL();


		if (GlobalClass.tmpOffline == false) {
			if (GlobalClass.CheckConnectionForSubmit(ctx)) {
				
				Integer iColCount = 0;
				LinearLayout row = new LinearLayout(this);
				
				LinearLayout SplitedVouchers = (LinearLayout) dialog
						.findViewById(R.id.splitedvoucherlayout);
			//	ItemRemark.removeAllViews();
				
				
				//((ScrollView)findViewById(R.id.ScrollView)).scrollTo(0, 0);
				    //dbhelper.LoadItemRemark(dataurl);
				    dbhelper.LoadSplitedVouchers(dataurl, tableID);
				    
				    List<SplitedVouchers> SplitedVouchersList = dbhelper.getSplitedVouchers();
				    
					
					for (int i = 0; i < SplitedVouchersList.size(); i++) {
						
						LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
								GlobalClass.getDPsize(250, this),
								GlobalClass.getDPsize(90, this));
						params.setMargins(2, 6, 0, 4);
						row.addView(CreateSplitedVouchers(SplitedVouchersList.get(i), dialog, tableID,i+1),params);
						
					
						 iColCount++;
				            
				            if(iColCount == 3)
				            {
				            	SplitedVouchers.addView(row);
				            	row = new LinearLayout(this);
								iColCount = 0;				
				            } 
				            
					}
					SplitedVouchers.addView(row);	
					
					
			}
		}
	}
	private TextView CreateSplitedVouchers(final SplitedVouchers SV, final Dialog dialog,final String tableID,int i) { //Added by Arkar Moe on [2017-06-11]
		TextView p1 = new TextView(this);
		p1.setTypeface(font);
		
		
		//p1.setText( "["+ SV.get_docid() + "]-["+ SV.get_net_amount() + "]");
		//p1.setText( "["+ SV.get_docid() + "]-["+  String.format("%.2f", Double.parseDouble(SV.get_net_amount())) + "]");

		//String table_name = dbhelper.getTableNameByTableID(Integer.parseInt(tableID));
		String table_name =  dbhelper.getTableNameByTableID(Integer.parseInt(tableID)); //added by MPPA [12-07-2021]

		p1.setText(table_name +"-("+i+")\n[Qty - "+Double.parseDouble(SV.get_qty())+"]\n["+ String.format("%.2f", Double.parseDouble(SV.get_net_amount())) +"]"); //added by EKK on 24-03-2020

		p1.setTag(SV.get_tranid());//added WHM [2020-04-06]

		p1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		p1.setBackgroundResource(R.drawable.rounded_button);
		p1.setTextColor(Color.parseColor("#FFFFFF"));
		p1.setHeight(35);
		p1.setWidth(80);
		p1.setPadding(40,0,0,0);

	
		p1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				splited_saleid = v.getTag().toString();
				//GetDataByTranid(tablename,splited_saleid);
				
				//String TableName = ((Button)v).getText().toString();
				Intent intent = new Intent(ctx, OrderTaking.class);
				
				Bundle b = new Bundle();
				b.putString("TableID", tableID);
				b.putString("SaleID",splited_saleid);
				intent.putExtras(b);				
				startActivityForResult(intent, 100);
				finish();
				
			     dialog.dismiss();
			}
		});
	
				
		return p1;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
	  		super.onActivityResult(requestCode, resultCode, data);
	    	if (resultCode == RESULT_OK ) {
	    		bindAreaButtonlist();
	        }
	    }    	
	    
	/*
	 @Override
     public void onStop() {
        super.onStop();
        finish();
     }
	*/
	private void BindTableList(final Integer AreaId)
	{	
		//added by WaiWL on 11/06/2015
		if(GlobalClass.tmpOffline==true)//(dbhelper.getOfflineFlag().equals(true))
		{
			dbhelper.deleteActivetable();
		}
		else
		{//////////////////
			if (GlobalClass.CheckConnection(this)) 
			{
				dbhelper.LoadActivetable(new DatabaseHelper(this).getServiceURL(),Integer.toString(AreaId));
				dbhelper.LoadBusyTable(new DatabaseHelper(this).getServiceURL());
			}
			else
			{
				//added by WaiWL on 15/06/2015
				showAlertDialog(this, "No Server Connection",
                        "You don't have connection with Server.", false);
				/////
			}
					
		}
		
		Integer iRowCount = 0;
		Integer iColCount = 0;
		String rowstyle =dbhelper.getTableRowStyle();
		if (rowstyle.contains("Four"))
		{
			iRowCount = 4;
		}else if (rowstyle.contains("Two"))
		{
			iRowCount = 2;
		} else
		{
			iRowCount = 3;
		}
		LinearLayout row = new LinearLayout(this);
		LinearLayout Tablelayout= (LinearLayout)findViewById(R.id.lintablelayout);
		Tablelayout.removeAllViews();
		((HorizontalScrollView)findViewById(R.id.horizontalScrollView2)).scrollTo(0, 0);
		List<Table_Name> TableList = dbhelper.getTableListByArea(AreaId);
		Integer TotalColCount = 0;   
		//iRowCount = 3;
        TotalColCount = TableList.size() / iRowCount;
		if (TableList.size() % iRowCount != 0) {
			TotalColCount = TotalColCount + 1;
		}
		for(int i=0;i<TableList.size();i++)
		{
			
			Button b = CreateTableButton(TableList.get(i));
			//Button b = new Button(this);

			//for custom width and height of table
			Integer [] tablewidth = new Integer[1];
			tablewidth=dbhelper.getTableSize();
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(GlobalClass.getDPsize(tablewidth[0], this),GlobalClass.getDPsize(90, this));
			params.setMargins(6, 4, 0, 4);
			row.addView(b,params);
            iColCount++;
            if (iColCount == TotalColCount) {
    			Tablelayout.addView(row);
				row = new LinearLayout(this);
				iColCount = 0;	;
			} 
//            if(iColCount == 9)
//            {
//            	Tablelayout.addView(row);
//				row = new LinearLayout(this);
//				iColCount = 0;				
//            }             
		}
			
		Tablelayout.addView(row);		
	}
	public void showAlertDialog(Context context, String title, String message, Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
 
        // Setting Dialog Title
        alertDialog.setTitle(title);
 
        // Setting Dialog Message
        alertDialog.setMessage(message);
              

        // Setting alert dialog icon
        alertDialog.setIcon((status) ? 0 : 0);

        
        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        
        //textView.setTypeface(font);
        // Showing Alert Message        
        alertDialog.show();
        TextView textView = (TextView) alertDialog.findViewById(android.R.id.message);
        textView.setTypeface(font);
    }
	
	//added by WaiWL on 12/06/2015
	public void butLoadTable_OnClick(View v)
	{
		GlobalClass.tmpOffline=false;
		bindAreaButtonlist();
		GlobalClass.tmpOffline=dbhelper.getOfflineFlag();
	}
	//////
	
	//added by WaiWL on 15/09/2015
	public void butsetting_OnClick(View v)
    {    	
    	setting_dialog();
    	//checkConnectionStatus();
    }
	
	public void setting_dialog()
   	{
   	    AlertDialog.Builder builder = new AlertDialog.Builder(this);
   	    // Get the layout inflater
   	    LayoutInflater inflater = this.getLayoutInflater();

   	    // Inflate and set the layout for the dialog
   	    // Pass null as the parent view because its going in the dialog layout
   	    builder.setView(inflater.inflate(R.layout.activity_menulist, null));      
   	    final Dialog dialog = builder.create();
   	   
   	    WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
   	    wmlp.gravity = Gravity.TOP;	    
   	    Button btnother = (Button)findViewById(R.id.butsetting);
   	    int[] location = new int[2];
   	    btnother.getLocationOnScreen(location);	    
    	    wmlp.x = location[0];
   	    wmlp.y = location[1];	 
   	    dialog.show();
   	    Window window = dialog.getWindow();
   	    //int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300, getResources().getDisplayMetrics());
   	    window.setLayout(GlobalClass.getDPsize(200, this), LayoutParams.WRAP_CONTENT);
   	    dialog.setCanceledOnTouchOutside(true);	    	 
   	    dialog.show();	
   	    
   	    final TextView txtloaddata = (TextView)dialog.findViewById(R.id.txtloaddata);
   	    //final TextView txtloadimage = (TextView)dialog.findViewById(R.id.txtloadimage);
   	    final TextView txtitemview = (TextView)dialog.findViewById(R.id.txtItemViewStyle);
   	    final TextView txtMIFinisheditems = (TextView)dialog.findViewById(R.id.txtFinishedItems);
   	    
   	    final TextView txtregister = (TextView)dialog.findViewById(R.id.txtregister);
	    final TextView txtunregister = (TextView)dialog.findViewById(R.id.txtunregister);
	    final TextView txtSetURL = (TextView)dialog.findViewById(R.id.txtseturl);
	    final TextView txtchangeLanguage = (TextView)dialog.findViewById(R.id.txtChangeLanguage);
	    final TextView txtresetdata = (TextView)dialog.findViewById(R.id.txtResetData);
	    final TextView txttestprint = (TextView)dialog.findViewById(R.id.txttestprint);
	    final TextView txtitemdescription = (TextView)dialog.findViewById(R.id.txtItemDescription);	    
	    final TextView txtTableRowCount = (TextView)dialog.findViewById(R.id.txtTableRowCount);
	    final TextView txtItemRowCount = (TextView)dialog.findViewById(R.id.txtItemRowCount);
	    
	    txtregister.setVisibility(8);
	    txtunregister.setVisibility(8);
	    txtSetURL.setVisibility(8);
	    txtchangeLanguage.setVisibility(8);
	    txtresetdata.setVisibility(8);
	    txttestprint.setVisibility(8);
	    txtTableRowCount.setVisibility(8);
	    txtItemRowCount.setVisibility(8);
	    
   	    //added by WaiWL on 09/06/2015
   	    chkoffline =(CheckBox)dialog.findViewById(R.id.chkOffline);
   	 
   	    if(dbhelper.getOfflineFlag().equals(false))
   	    	chkoffline.setChecked(false);
	    else
	    	chkoffline.setChecked(true);
   	    //////////////
   	    
   	    txtloaddata.setOnClickListener(new OnClickListener() {			
   			public void onClick(View v) {
   				// TODO Auto-generated method stub
   				MenuItemClick(txtloaddata);
   				dialog.dismiss();
   			}
   		});
   	    
//   	    txtloadimage.setOnClickListener(new OnClickListener() {
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				MenuItemClick(txtloadimage);
//				dialog.dismiss();
//			}
//		});
   	    
   	    txtitemview.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MenuItemClick(txtitemview);
				dialog.dismiss();
			}
		});
   	    
    	 txtitemdescription.setOnClickListener(new OnClickListener() {			
 			public void onClick(View v) {
 				// TODO Auto-generated method stub
 				MenuItemClick(txtitemdescription);
 				dialog.dismiss();
 			}
 		});
	    
   	    //added by WaiWL on 09/06/2015
   	    chkoffline.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MenuItemClick(chkoffline);
			}
		});
   	    
   	    txtMIFinisheditems.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MenuItemClick(txtMIFinisheditems);
			}
		});
   	}
	
	public boolean MenuItemClick(TextView item) {
	      
       	boolean isInternetPresent = false;
           switch (item.getId()) 
           {  
           	case R.id.txtloaddata:
           		
           		isInternetPresent = GlobalClass.CheckConnection(this);
               
           		// check for Internet status
           		if (isInternetPresent) 
           		{           			
           			GetData();        			        		
           		} 
           		else 
           		{
           			showAlertDialog(this, "No Server Connection",
                           "You don't have connection with Server.", false);
   	            }            	                                             
   				break;
   				
//           	case R.id.txtloadimage:
//
//           		isInternetPresent = GlobalClass.CheckConnection(this);
//
//           		// check for Internet status
//           		if (isInternetPresent)
//           		{
//           			GetImage();
//           		}
//           		else
//           		{
//           			showAlertDialog(this, "No Server Connection",
//                           "You don't have connection with Server.", false);
//   	            }
//   				break;
           	
               case R.id.chkOffline:
            	   try{
            		    final DatabaseHelper dbhelp = new DatabaseHelper(this);
            	   		String isOffline = "";
		   				if(chkoffline.isChecked()==true)
		   				{
		   					isOffline="1";
		   				}
		   				else
		   					isOffline="0";
	   				
		   				dbhelp.AddOfflineMode(isOffline);
            	   	} catch (NumberFormatException e) {
      					e.printStackTrace();
      				} 
            	   break;
            	   
               case R.id.txtItemViewStyle :
            	   itemviewstyle_dialog();            	   
            	   break;
            	   
               case R.id.txtItemDescription :
            	   itemdescription_dialog();            	   
            	   break;
            	   
               case R.id.txtFinishedItems :
            	   List<KitchendataObj> kitchendatalist = dbhelper.getkitchenfinishlist();
	               if(kitchendatalist.size() > 0 )
	               {
	            	   MIFinishedItemsDialog();
	               }
	               else
	               {
	            	   	AlertDialog alertDialog = new AlertDialog.Builder(this).create();
				        alertDialog.setTitle("Cooking Items Warning!");						       
				        alertDialog.setMessage("No Finished Items!");			        			       
				        alertDialog.setIcon(0);	
				        alertDialog.setCanceledOnTouchOutside(false);
				        alertDialog.setCancelable(false);
				        // Setting OK Button
				        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
				            public void onClick(DialogInterface dialog, int which) {
				            }
				        });    
				        alertDialog.show();	
	               }
            	   break;
           }
           return true;
       }
	
	private void itemdescription_dialog() {
		 AlertDialog.Builder builder = new AlertDialog.Builder(this);   	  
	  	    LayoutInflater inflater = this.getLayoutInflater();
	  	    builder.setView(inflater.inflate(R.layout.activity_itemdescription, null));      
	  	    final Dialog dialog = builder.create(); 	   
	  	    WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();    
	  	    dialog.show();
	  	    Window window = dialog.getWindow();
	  	    window.setLayout(GlobalClass.getDPsize(300, this), LayoutParams.WRAP_CONTENT);
	  	    dialog.setCanceledOnTouchOutside(true);	    	 
	  	    dialog.show();	
	  	    	    	   	    
	 final Button btndescription1 = (Button)dialog.findViewById(R.id.btndescription1);
	   	final Button btndescription2 = (Button)dialog.findViewById(R.id.btndescription2);
	   	final Button btndescription3 = (Button)dialog.findViewById(R.id.btndescription3);
	  	 
	  	    int checkitem = dbhelper.getitemdescriptionstyle();
	  	    switch(checkitem)
	  	    {
	  	    case 0: btndescription1.setBackgroundColor(Color.parseColor("#d9522c"));break;
	  	    case 1: btndescription2.setBackgroundColor(Color.parseColor("#d9522c"));break;
	  	    case 2: btndescription3.setBackgroundColor(Color.parseColor("#d9522c"));break;	    
	  	    }
	  	    
	    
	  	   btndescription1.setOnClickListener(new OnClickListener() {			
				public void onClick(View v) {
					// TODO Auto-generated method stub	
					dbhelper.updateitemdescriptionstyle(0);
					dialog.dismiss();
				}
			});
	  	    
	  	 btndescription2.setOnClickListener(new OnClickListener() {			
				public void onClick(View v) {
					// TODO Auto-generated method stub	
					dbhelper.updateitemdescriptionstyle(1);
					dialog.dismiss();
				}
			});
	  	    
	  	btndescription3.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
				// TODO Auto-generated method stub	
				dbhelper.updateitemdescriptionstyle(2);
				dialog.dismiss();
			}
		});
		
	}
	
	public void GetData()
    {
    	// prepare for a progress bar dialog
    	final String dataurl = new DatabaseHelper(this).getServiceURL();
		progressBar = new ProgressDialog(this);
		progressBar.setMessage("File downloading ...");
		progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progressBar.setCancelable(false);
		progressBar.setProgress(0);
		final String ItemCount = dbhelper.LoadItemCount(dataurl);// Item
		final String ModifierItemCount = dbhelper.LoadModifierItemCount(dataurl); // modifieritem
		progressBar.setMax(65 + Integer.parseInt(ItemCount.trim())+ Integer.parseInt(ModifierItemCount.trim()));
		progressBar.show();
		//reset progress bar status
		progressBarStatus = 0;    
							
		try
		{
			new Thread(new Runnable() {
			  public void run() {
				  String loaddate = dbhelper.LoadServerDate(dataurl);
				    				                              				  			 			 			  			      				  				  						  			  	
				  dbhelper.LoadArea(dataurl);	    				 
				  progressBarStatus += 10;   
				  progressBarHandler.post(new Runnable() {
						public void run() {
						  progressBar.setProgress(progressBarStatus);
						}
					  });
				  			  
				  dbhelper.LoadTable(dataurl);	    				 
				  progressBarStatus += 10;   
				  progressBarHandler.post(new Runnable() {
						public void run() {
						  progressBar.setProgress(progressBarStatus);
						}
					  });					  
				  
				  if(dbhelper.getLastLoadingTimeLog().equals("")) // first time
				  {
					  for(int i = 1; i <= Integer.parseInt(ItemCount.trim()) ; i++ )
					  {
						  dbhelper.LoadItemFirstTime(dataurl,Integer.toString(i));						 
						  progressBarStatus += 1;   
						  progressBarHandler.post(new Runnable() {
								public void run() {
								  progressBar.setProgress(progressBarStatus);
								}
							  });							  
					  }		  					  						  
							  
					  for(int j = 1; j <= Integer.parseInt(ModifierItemCount.trim()) ; j++ )
					  {
						  dbhelper.LoadModifiedItemFirstTime(dataurl,Integer.toString(j));	    				 
						  progressBarStatus += 1;   
						  progressBarHandler.post(new Runnable() {
								public void run() {
								  progressBar.setProgress(progressBarStatus);
								}
							  });							  
					  }						  
				  }
				  else
				  {
					  dbhelper.LoadItemSecondTime(dataurl);	    				 
					  progressBarStatus += Integer.parseInt(ItemCount.trim());   
					  progressBarHandler.post(new Runnable() {
							public void run() {
							  progressBar.setProgress(progressBarStatus);
							}
						  });	
					  
					  
					  
					  dbhelper.LoadModifiedItemSecondTime(dataurl);	    				 
					  progressBarStatus += Integer.parseInt(ModifierItemCount.trim());   
					  progressBarHandler.post(new Runnable() {
							public void run() {
							  progressBar.setProgress(progressBarStatus);
							}
						  });
					  
				  }
						  
				  
				  dbhelper.LoadModifierGroup(dataurl);	    				 
				  progressBarStatus += 10;   
				  progressBarHandler.post(new Runnable() {
						public void run() {
						  progressBar.setProgress(progressBarStatus);
						}
					  });
				  
				  dbhelper.LoadDictionary(dataurl);	    				 
				  progressBarStatus += 10;   
				  progressBarHandler.post(new Runnable() {
						public void run() {
						  progressBar.setProgress(progressBarStatus);
						}
					  });
				  
				  dbhelper.LoadCustomer(dataurl);	    				 
				  progressBarStatus += 10;   
				  progressBarHandler.post(new Runnable() {
						public void run() {
						  progressBar.setProgress(progressBarStatus);
						}
					  });
				  
				  dbhelper.LoadPosUser(dataurl);	    				 
				  progressBarStatus += 5;   
				  progressBarHandler.post(new Runnable() {
						public void run() {
						  progressBar.setProgress(progressBarStatus);
						}
					  });
				  
				  dbhelper.LoadSalesMen(dataurl);	   	    				 
				  progressBarStatus += 5;   
				  progressBarHandler.post(new Runnable() {
						public void run() {
						  progressBar.setProgress(progressBarStatus);
						}
					  });
				  
				  
				  dbhelper.LoadSetMenuItem(dataurl);	   	    				 
				  progressBarStatus += 5;   
				  progressBarHandler.post(new Runnable() {
						public void run() {
						  progressBar.setProgress(progressBarStatus);
						}
					  });

				  dbhelper.LoadSpecialMenu(dataurl);	   	    				 
				  progressBarStatus += 5;   
				  progressBarHandler.post(new Runnable() {
						public void run() {
						  progressBar.setProgress(progressBarStatus);
						}
					  });
				  
				  dbhelper.LoadSpecialMenu_code(dataurl);	   	    				 
				  progressBarStatus += 5;   
				  progressBarHandler.post(new Runnable() {
						public void run() {
						  progressBar.setProgress(progressBarStatus);
						}
					  });
				  
				  dbhelper.Load_CustomerCountSetUp(dataurl);	   	    				 
				  progressBarStatus += 5;   
				  progressBarHandler.post(new Runnable() {
						public void run() {
						  progressBar.setProgress(progressBarStatus);
						}
					  });
				  
				// added by PPH on 27/07/2016
					dbhelper.SetLoadedDate(dataurl);
					progressBarStatus += 5;
					progressBarHandler.post(new Runnable() {
						public void run() {
							progressBar.setProgress(progressBarStatus);
						}
					});
				  
//					added by TTA on [28-01-2019]
					dbhelper.LoadCashierPrinter(dataurl);
					progressBarStatus += 5;
					progressBarHandler.post(new Runnable() {
						public void run() {
							progressBar.setProgress(progressBarStatus);
						}
					});

				  //region deliverymanagement //added WHM [2020-05-11]
				  if(dbhelper.use_deliverymanagement() == true)
				  {
					  dbhelper.LoadTownship(dataurl);
					  dbhelper.LoadAgent(dataurl);
					  dbhelper.LoadDeliverySetup(dataurl);
					  dbhelper.LoadDeliveryMan(dataurl);
					  progressBarStatus += 40;
					  progressBarHandler.post(new Runnable() {
						  public void run() {
							  progressBar.setProgress(progressBarStatus);
						  }
					  });
				  }

				  //endregion
					
				  progressBar.dismiss();
				  dbhelper.UpdateTimeLog(loaddate);				  			 
			  }
		       }).start(); 
		}
		catch(Exception ex)
		{		
		}
		finally
		{						
		}		      
    }
	
	public void GetImage()
    {
    	// prepare for a progress bar dialog
    	final String dataurl = new DatabaseHelper(this).getServiceURL();
		progressBar = new ProgressDialog(this);
		progressBar.setMessage("File downloading ...");
		progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progressBar.setCancelable(false);
		progressBar.setProgress(0);
		
		
		progressBar.setButton("CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            	progressBar.cancel();
            }
        });	
		
		//final List<ItemsObj> Itemlist = dbhelper.getItemslist();// Item
		String usr_code = Json_class.getString(dbhelper.getServiceURL()+"/Data/Getusr_codeorderbydate");
		final String[] user_codelist = usr_code.split(",");
		progressBar.setMax(60 + user_codelist.length);
		progressBar.show();
		//reset progress bar status
		progressBarStatus = 0;    
							
		try
		{
			new Thread(new Runnable() {
			  public void run() {		
				  
				  for(int i = 0; i < user_codelist.length ; i++ )
				  {
					  //dbhelper.LoadItemImage(dataurl, Itemlist.get(i).getusr_code());  
					  downloadImage(dataurl + "/RestaurantImage"+ "/" + user_codelist[i] + ".jpg" ,user_codelist[i]);
					  progressBarStatus += 1;   
					  progressBarHandler.post(new Runnable() {
							public void run() {
							  progressBar.setProgress(progressBarStatus);
							}
						  });							  
				  }		  					  						  									 
				  progressBar.dismiss();		  			 
			  }
		       }).start(); 
		}
		catch(Exception ex)
		{		
		}
		finally
		{						
		}		      
    }
	
	private void downloadImage(String url,String usr_code) {
		Bitmap bitmap = null;
		InputStream stream = null;
		BitmapFactory.Options bmOptions = new BitmapFactory.Options();
		//bmOptions.inSampleSize = 1;
		try {
			stream = Json_class.getHttpConnection(url);
			if(stream == null)
				return;
			bitmap = BitmapFactory.
					decodeStream(stream, null, bmOptions);						
			stream.close();
			
			ByteArrayOutputStream bytes = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

			
			
			// create a File object for the parent directory
			File Directory = new File(GlobalClass.GetImageStorageLocation(this));
			// have the object build the directory structure, if needed.
			
			if(!Directory.exists())
				Directory.mkdirs();
						
			//you can create a new file name "test.jpg" in sdcard folder.
			File f = new File(GlobalClass.GetImageStorageLocation(this)
			                        + File.separator + usr_code +".jpg");
			
			if(f.exists())
			{
				f.delete();
			}
			
			f.createNewFile();
			//write the bytes in file
			FileOutputStream fo = new FileOutputStream(f);
			fo.write(bytes.toByteArray());

			// remember close de FileOutput
			fo.close();
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		
	}
	
	public void MIFinishedItemsDialog()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		LayoutInflater inflater = this.getLayoutInflater();	  
		builder.setView(inflater.inflate(R.layout.activity_monitor_interface__finished_items, null));
		final Dialog dialog =  builder.create();	    
	    WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
	    wmlp.gravity = Gravity.CENTER;
	    
	    dialog.show();	  	  
	    Window window = dialog.getWindow();
	    window.setLayout(GlobalClass.getDPsize(650, this), GlobalClass.getDPsize(480, this));	
	    dialog.setCancelable(false);	    	   
	    dialog.setCanceledOnTouchOutside(true);
	    	
	    final LinearLayout itemlistlayout = (LinearLayout)dialog.findViewById(R.id.monitorinterface_finisheditemslistslayout);
	    
	    Button butClose = (Button)dialog.findViewById(R.id.butClose);
	    butClose.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
	    
	    List<KitchendataObj> MIkitchendatalist = dbhelper.getkitchenfinishlist();
		
		if(MIkitchendatalist.size() > 0 )
		{
			String finishedtable_lists = "";
			
			for(int i= 0; i< MIkitchendatalist.size(); i++)
			{	
				GlobalClass.MIFinishedItemscount = i + 1;
		        finishedtable_lists = "   " + dbhelper.getTableNameByTableID(MIkitchendatalist.get(i).getTable_Name_ID()) + " : " + MIkitchendatalist.get(i).getdescription();		
		        TableNameId = MIkitchendatalist.get(i).getTable_Name_ID();
		   //     String Status =MIkitchendatalist.get(i).getcook_status().toString();
		        
		    	final LinearLayout itemlayout = new LinearLayout(this);
		    	final LayoutParams itemlayoutpara = new LayoutParams(GlobalClass.getDPsize(650, this),LayoutParams.WRAP_CONTENT);
		    	    
		    	final LayoutParams FinishedItemsListslayoutpara = new LayoutParams(GlobalClass.getDPsize(445, this),LayoutParams.WRAP_CONTENT);
		    	final LayoutParams FinishedItemslayoutpara = new LayoutParams(GlobalClass.getDPsize(150, this),LayoutParams.WRAP_CONTENT);
		    	FinishedItemsListslayoutpara.setMargins(2, 10, 2, 10);
		    	FinishedItemslayoutpara.setMargins(2, 10, 2, 10);
		    	
		    	final LayoutParams linelayoutpara = new LayoutParams(LayoutParams.FILL_PARENT,GlobalClass.getDPsize(1, this));
		    	linelayoutpara.setMargins(3, 0, 3, 0);
		    	
		    	TextView txtMIFItemsLists = new TextView(this); 
//		    	if (Status.equals("S"))
//		    	{
		    	txtMIFItemsLists.setText(finishedtable_lists);   
		    	
		    	txtMIFItemsLists.setPadding(0, 15, 0, 15);
		    	txtMIFItemsLists.setTypeface(font,Typeface.BOLD);
		    	txtMIFItemsLists.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		    	txtMIFItemsLists.setGravity(Gravity.LEFT);
		      	txtMIFItemsLists.setTextColor(Color.parseColor("#000000"));

		    
		    	
		    	Button butFinishedItems = new Button(this);    	    	    	
		    	butFinishedItems.setText("Finished Item");    
		    	butFinishedItems.setTag(TableNameId);
		    	butFinishedItems.setPadding(0, 15, 0, 15);
		    	butFinishedItems.setTypeface(font);
		    	butFinishedItems.setGravity(Gravity.RIGHT);
		    	Resources resources = this.getResources();  
		    	Drawable drawable = resources.getDrawable(R.drawable.valueitembutton);
		    	butFinishedItems.setBackgroundDrawable(drawable);
		    	butFinishedItems.setTextColor(Color.parseColor("#FFFFFF"));
		    	butFinishedItems.setOnClickListener(new OnClickListener() {
					
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dbhelper.updateMIFinishedItems(Integer.parseInt(v.getTag().toString()));
						final LinearLayout mainparent =(LinearLayout)v.getParent().getParent();
						LinearLayout ln = (LinearLayout)mainparent.getParent();
						TextView txtLine = (TextView)((LinearLayout)ln).findViewWithTag(mainparent.getId());
						((LinearLayout)ln).removeView(txtLine);
						((LinearLayout)ln).removeView(mainparent);
						
						
						if(ln.getChildCount() <= 0)
						{
							dialog.dismiss();
						}
					}
				});
		    	
		    	itemlayout.addView(txtMIFItemsLists,FinishedItemsListslayoutpara);
		    	itemlayout.addView(butFinishedItems,FinishedItemslayoutpara);
		    	
		    	LinearLayout itemlistwithmodifier = new LinearLayout(this);
		    	itemlistwithmodifier.setOrientation(LinearLayout.VERTICAL);
		    	itemlistwithmodifier.addView(itemlayout);
		    	
		    	itemlistlayout.addView(itemlistwithmodifier,itemlayoutpara); 
		    	
		    	TextView txtLine = new TextView(this);
		    	txtLine.setBackgroundColor(Color.parseColor("#c6c6c6"));
		    	txtLine.setTag(itemlistwithmodifier.getId());
		    	
		    	itemlistlayout.addView(txtLine,linelayoutpara);
		    	
			}
		}
		
		
		
	}
	
	public void itemviewstyle_dialog()
	{
	    AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    //Get the layout inflater
	    LayoutInflater inflater = this.getLayoutInflater();

	    // Inflate and set the layout for the dialog
	    // Pass null as the parent view because its going in the dialog layout
	    builder.setView(inflater.inflate(R.layout.activity_itemviewstyle, null));      
	    final Dialog dialog = builder.create();
	   
	    WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
	    wmlp.gravity = Gravity.CENTER;	    
	    
	    dialog.show();
	    Window window = dialog.getWindow();
	    //int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300, getResources().getDisplayMetrics());
	    window.setLayout(GlobalClass.getDPsize(300, this), LayoutParams.WRAP_CONTENT);
	    dialog.setCanceledOnTouchOutside(true);	    	 
	    dialog.show();	
	    	    	   	    
	    final Button btnsave = (Button)dialog.findViewById(R.id.btnsave);	    	    	    	    
	    final RadioGroup rdoitemviewstyle = (RadioGroup)dialog.findViewById(R.id.rdoitemviewstyle);


		//added WHM [2020-09-30] YGN2-200983
		final RadioButton rdowithclassbutton = (RadioButton) dialog.findViewById(R.id.rdowithclass);
		if(GlobalClass.use_menucategorygrouptype != 0 )
		{
			rdowithclassbutton.setVisibility(View.GONE);
		}
	    
	    int checkitem = dbhelper.getitemviewstyle();
	    switch(checkitem)
	    {
	    case 0: rdoitemviewstyle.check(R.id.rdowithoutclass);break;
	    case 1: rdoitemviewstyle.check(R.id.rdowithclass);break;
	    case 2: rdoitemviewstyle.check(R.id.rdofullcategory);break;	    
	    }
	    	    
	    btnsave.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int i = rdoitemviewstyle.getCheckedRadioButtonId();
				switch(i)
				{
					case R.id.rdowithoutclass: i=0;break;
					case R.id.rdowithclass: i = 1; break;
					case R.id.rdofullcategory: i = 2; break;				
				}
				
				dbhelper.updateitemviewstyle(i);
				dialog.dismiss();
			}
		});	    
	}
	////
}
