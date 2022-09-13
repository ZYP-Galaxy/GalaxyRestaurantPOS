package com.galaxy.restaurantpos;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SendNote extends Activity {

	private EditText txtnote;
	private Button btnsendtonote,btnOkPrinter;
	final DatabaseHelper dbhelper = new DatabaseHelper(this);
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_send_note);
		
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_send_note, menu);

		txtnote = ((EditText) findViewById(R.id.txtnote));

		txtnote.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if ((event.getAction() == KeyEvent.ACTION_DOWN)
						&& (keyCode == KeyEvent.KEYCODE_ENTER)) {
				
					
					return true;
				}
				return false;
			}
		});
		
		btnsendtonote = ((Button) findViewById(R.id.btnsendtonote));
		btnsendtonote.setOnKeyListener(new OnKeyListener() {			
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				 if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
				            (keyCode == KeyEvent.KEYCODE_ENTER)) {
				          // Perform action on key press				          
					  
					// btnsendtonote_click();
					
				          return true;
				        }
				        return false;
			}
		});
		
		btnOkPrinter = ((Button) findViewById(R.id.btnOkPrinter));
		btnOkPrinter.setOnKeyListener(new OnKeyListener() {			
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				 if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
				            (keyCode == KeyEvent.KEYCODE_ENTER)) {
				          // Perform action on key press				          
					  
					// btnsendtonote_click();
					
				          return true;
				        }
				        return false;
			}
		});

 	return true;
	}

	
		// }
	}



	

