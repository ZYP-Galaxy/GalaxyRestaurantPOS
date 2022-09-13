package com.galaxy.restaurantpos;


import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Looper;
import androidx.core.app.NotificationCompat;

public class MyIntentService extends IntentService {
				
	public static final String ACTION_MyIntentService = "com.galaxy.restaurantpos.kitchen";

	public  Context ctx;
	String msgFromActivity;
	String extraOut;

	public MyIntentService() {
		super("com.galaxy.restaurantpos");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		
		 new CountDownTimer(5000000, 5000) {
   	     public void onTick(long millisUntilFinished) {
   	    	 try
   	    	 {
   	    		new DatabaseHelper(ctx).LoadKitchenData(new DatabaseHelper(ctx).getServiceURL());  
   	    		   	    		   	    		   	    		 
	   	    	//return result
	   	  		Intent intentResponse = new Intent();
	   	  		intentResponse.setAction(ACTION_MyIntentService);
	   	  		intentResponse.addCategory(Intent.CATEGORY_DEFAULT);		
	   	  		sendBroadcast(intentResponse);
   	    	 }
   	    	 catch(Exception ex)
   	    	 {   	    		 
   	    	 }   	    	 
   	     }
   	     public void onFinish() {
   	    	//OnAlarmReceiver.isSendingDataOn = false;
   	    	Looper.myLooper().quit();
   	     }
   	  }.start();    
		Looper.loop();		  		
				
		
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();		
		ctx = getApplicationContext();
	}
}

