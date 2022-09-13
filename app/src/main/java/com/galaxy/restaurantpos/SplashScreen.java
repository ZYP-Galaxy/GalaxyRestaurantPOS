package com.galaxy.restaurantpos;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

public class SplashScreen extends Activity {
	 final String URL = "http://172.16.10.96/dss";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
      //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
       //set content view AFTER ABOVE sequence (to avoid crash)
        setContentView(R.layout.activity_splashscreen);
        
        ConnectionDetector cd = new ConnectionDetector(this);        
        final Boolean isInternetPresent = GlobalClass.CheckConnection(this);
        
        final DatabaseHelper dbhelper = new DatabaseHelper(this);        
        //dbhelper.DeleteItemTalbe();       
        //dbhelper.AddItems("111","CB005" , "ၾကက္ကုန္းေဘာင္", "4500.00", null, null, null, null, null, null, "2", "တရုတ္(ပြဲၾကီး)", "3", "class1");                                                                           
        final Context ctx=this;  
                              	            
        dbhelper.getWritableDatabase();
        Thread t=new Thread(){
        	public void run(){
        		try{
        			sleep(1500); 
        			//Load the Area & Table Lists.        			
        			         		
        		}
        		catch(InterruptedException e){        			
        		}
        		finally{
        			finish();
        			
        			Intent intent=new Intent(ctx,LoginActivity.class);        			
        			//Intent intent=new Intent(ctx,MainScreen.class);	
        			startActivity(intent);        		
        		}
        	}
        };                      
        // check for Internet status
        /*
        if (isInternetPresent) {
       
        } else {
            // Internet connection is not present
            // Ask user to connect to Internet
            showAlertDialog(this, "No Server Connection",
                    "You don't have connection with Server.", false);
        }
        */
      
        t.start();//start thread
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
 
        // Showing Alert Message
        alertDialog.show();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

}
