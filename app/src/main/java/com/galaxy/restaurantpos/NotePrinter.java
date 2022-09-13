package com.galaxy.restaurantpos;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;

public class NotePrinter extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_printer);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_note_printer, menu);
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final Dialog dialog = builder.create();
    
    	Button btnOkPrinter;
    	btnOkPrinter = ((Button) dialog
				.findViewById(R.id.btnOkPrinter));
    	btnOkPrinter.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				;
	            
				dialog.dismiss();
			}

		});
        
        
        return true;
        
        
        
    
}
    

}
    


