package com.galaxy.restaurantpos;

import java.util.List;

import org.json.JSONArray;

import android.R.color;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

public class TestPrintActivity extends Activity {
	LinearLayout framelayout;
	
	DatabaseHelper dbhelper;
	String dataurl,tmpPrinterId;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_print);
        
        framelayout = (LinearLayout)findViewById(R.id.detaillayout);
        
        dbhelper = new DatabaseHelper(this);
        dbhelper.getWritableDatabase();
        dataurl = new DatabaseHelper(this).getServiceURL();
        bindData();
        
        
    }
    
    protected void  bindData() {
    	
		List<Printer> printList = dbhelper.getAllPrinterlist(dataurl);
		
		for(int i = 0; i< printList.size() ; i++)
		{
			Printer printobj =printList.get(i);
			adddata(printobj.getPrinterID().toString(),printobj.getPrinterName(),printobj.getPrinter());
		}
		
	}

	private void adddata(String printerid, String printerName, String printer) {
		// TODO Auto-generated method stub
		
		
		LinearLayout bLayout = new LinearLayout(this);
	    bLayout.setOrientation(LinearLayout.HORIZONTAL);
	    bLayout.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
	    //bLayout.setLayoutParams(new LinearLayout.LayoutParams(150,150));
	    
	    //LinearLayout.LayoutParams buttonLayoutParams = new  LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	    LinearLayout.LayoutParams buttonLayoutParams = new  LinearLayout.LayoutParams(150, 150);
	    buttonLayoutParams.setMargins(0, 0, 10, 0);
	    
	    bLayout.setLayoutParams(buttonLayoutParams);
	    
	    bLayout.setBackgroundResource(R.drawable.window8_printer);
	    
	    final LayoutParams itemwidth = new LayoutParams(GlobalClass.getDPsize(150, this),LayoutParams.WRAP_CONTENT);

	    TextView txt = new TextView(this);
	    
	    txt.setText(printerName);
	    
	    txt.setTextColor(Color.parseColor("#FFFFFF"));
	    
	    LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	    llp.setMargins(0, 0, 0, 5); 
	    txt.setLayoutParams(llp);
	    txt.setTextAppearance(this,android.R.attr.textAppearanceMedium);
	    txt.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER);
	    
	    bLayout.setTag(printerid);

	    bLayout.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				PrinterClick(v);
			}
		});
	    
	    bLayout.addView(txt);
	    framelayout.addView(bLayout);
	}

	public void PrinterClick(View arg0)
	{
		String a = getLastDocID(dataurl, arg0.getTag().toString());	
	}
	
	String getLastDocID(String url,String printerid)
	 {
		 return Json_class.getString(url + "/Data/TestPrint?printerid="+java.net.URLEncoder.encode(printerid).trim());		 
	 }	
	
}
