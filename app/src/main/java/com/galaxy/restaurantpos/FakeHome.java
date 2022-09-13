package com.galaxy.restaurantpos;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class FakeHome extends Activity implements OnClickListener {
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);	        
	      //Remove title bar
	        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	        //Remove notification bar
	        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	       //set content view AFTER ABOVE sequence (to avoid crash)
	        	       	       
	        setContentView(R.layout.activity_fakehome);
	        
	        Button btnyes = (Button)findViewById(R.id.butyes);
	        Button btnno = (Button)findViewById(R.id.butno);
	        Button btnhome = (Button)findViewById(R.id.buthome);
	        
	        btnyes.setOnClickListener(this);
	        btnno.setOnClickListener(this);
	        btnhome.setOnClickListener(this);
	    }

	 
	 @Override
	    public boolean onKeyDown(int keyCode, KeyEvent event) 
	    {
	    	if(keyCode == KeyEvent.KEYCODE_BACK)
	    	{
	    		return true;
	    	}
	    	  	
	    	return super.onKeyDown(keyCode, event);
	    	
	        //return (keyCode == KeyEvent.KEYCODE_BACK ? true : super.onKeyDown(keyCode, event));
	    }
	 
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
			case R.id.butyes:
				new DatabaseHelper(this).DeleteDeviceOwner();	
				Intent i = new Intent(this, LoginActivity.class);
			    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
			    startActivity(i);
			    finish();
				//exitpassword_dialog();
				break;
			case R.id.butno:
				loadpreviousscreen();
				this.finish();
				break;
			case R.id.buthome:
				android.os.Process.killProcess(android.os.Process.myPid());
				Intent intent = new Intent(this, MainScreen.class);
				startActivity(intent);
				break;
		
		}
	}
	

	private void loadpreviousscreen()
	{
		Intent i = new Intent(this, LoginActivity.class);
		switch(GlobalClass.getCurrentscreen())
		{
			case 1 :i = new Intent(this, LoginActivity.class);
		    		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
		    		startActivity(i); break;
			case 2 :i = new Intent(this, MainScreen.class);
		    		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
		    		startActivity(i); break;
			case 3 :i = new Intent(this, MainScreen.class);
		    		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
		    		startActivity(i); break;
			case 4 :i = new Intent(this, MainScreen.class);
		    		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
		    		startActivity(i); break;    		
			case 5 :i = new Intent(this, MainScreen.class);
		    		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
		    		startActivity(i); break;		    				   	
		}				
	}
	
	public void exitpassword_dialog()
	{
	    AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    // Get the layout inflater
	    LayoutInflater inflater = this.getLayoutInflater();
	  
	    builder.setView(inflater.inflate(R.layout.activity_exitpassword, null));      
	    final Dialog dialog = builder.create();
	   
	    WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
	    wmlp.gravity = Gravity.CENTER;	    
	    dialog.show();	  
	    Window window = dialog.getWindow();
	    window.setLayout(GlobalClass.getDPsize(400, this), GlobalClass.getDPsize(200, this));   
	    dialog.setCanceledOnTouchOutside(true);	    	   
	    dialog.show();
	     	
	     Button butOK = (Button)dialog.findViewById(R.id.butOK);
	    final TextView txtpassword = (TextView)dialog.findViewById(R.id.txtpassword);
	    
	    final Context ctx = this;
	    
	    butOK.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
				String strpassword = txtpassword.getText().toString(); 
				if(!strpassword.equals(""))
				{
					String okflag = Json_class.getString(new DatabaseHelper(ctx).getServiceURL()+ "/Data/CheckExitPassword?Password=" + strpassword);
					try
					{
						if(okflag.trim().equals("True"))
						{
							dialog.dismiss();							
							/*
							PackageManager p = ctx.getPackageManager();							
						    ComponentName cN = new ComponentName(ctx, FakeHome.class);
						    p.setComponentEnabledSetting(cN, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
						    
						    Intent selector = new Intent(Intent.ACTION_MAIN);
						    selector.addCategory(Intent.CATEGORY_HOME);  						    
						    ctx.startActivity(selector);

						    p.setComponentEnabledSetting(cN, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
							*/
							finish();
							exitApp();
							//finish();
						}						
					}
					catch(Exception ex)
					{
						
						
					}
					
				}
					
			}
		});	
	   
	}
	    
	public void exitApp()
	{
	    //call this method to exit _CLEARLY_,
	    //and prompt the user which launcher to use next

	    //clear the default for your app (to show the prompt when exiting)
	    final PackageManager pm = getPackageManager();
	    pm.clearPackagePreferredActivities(getApplicationContext().getPackageName());

	    //exit _CLEARLY_
	    //calling finish(); would be ok also,
	    //but there would stay a 'zombie' in the dalvik cache
	    //and 'zombies' only use up your memory, so kill your entire app:
	    finish();
	    android.os.Process.killProcess(android.os.Process.myPid());
	    
	}
		

	
}
