package com.galaxy.restaurantpos;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.graphics.Color;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.Settings;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.TextureView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainScreen extends Activity {

    private ProgressDialog progressBar;    // Progress
    private int progressBarStatus = 0;
    private Handler progressBarHandler = new Handler();
    final DatabaseHelper dbhelper = new DatabaseHelper(this);
    public static Typeface font;
    private CheckBox chkoffline;

    LocationManager locationManager;
    String latitude, longitude;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GlobalClass.setCurrentscreen(2);
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        if (dbhelper.getwaiterid().equals("")) {
            finish();
            return;
        }
        setContentView(R.layout.activity_main_screen);

        if (android.os.Build.VERSION.SDK_INT > 9) {//for check connection
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

//        LoginActivity.isUnicode = dbhelper.isUnicode();
        //LoginActivity.isUnicode=false;
        //font = Typeface.createFromAsset(getAssets(), "fonts/Zawgyi-One.ttf");
//		if(LoginActivity.isUnicode)		//added by ZYP 17-12-2019
//			font = Typeface.createFromAsset(getAssets(), "fonts/Pyidaungsu.ttf");
//		else
//			font = Typeface.createFromAsset(getAssets(), "fonts/Zawgyi-One.ttf");

        GlobalClass.ChangeLanguage((ViewGroup) findViewById(R.id.uplayout), this, 18, font);


        bindstatus();
        //GetDatawithoutprogress();
        //LinearLayout linearLayout=(LinearLayout)findViewById(R.id.butdinein);
        //(GradientDrawable) linearLayout.getBackground().setColor(Color.parseColor("#00C853"));
        //(GradientDrawable) (R.id.butorders).getBackground().setColor(Color.parseColor("#00C853"));

        //GradientDrawable sd = (GradientDrawable) linearLayout.getBackground();
        //sd.setColor(Color.parseColor("#00C853"));

        //added by ZYP [06-01-2021]
        GlobalClass.use_foodtruck = dbhelper.use_foodtruck(dbhelper.getwaiterid());
        GlobalClass.tmpOffline = dbhelper.getOfflineFlag();

        if (GlobalClass.use_foodtruck) {
            LinearLayout switchuserLayout = (LinearLayout) findViewById(R.id.butswitchuser);
            switchuserLayout.setVisibility(View.GONE);

            ImageView imgDineIn = (ImageView) findViewById(R.id.img_dinein);
            imgDineIn.setImageDrawable(getResources().getDrawable(R.drawable.foodtruck));
            ViewGroup.LayoutParams params = imgDineIn.getLayoutParams();
            params.width = GlobalClass.getDPsize(100, this);
            params.height = GlobalClass.getDPsize(100, this);
            imgDineIn.setLayoutParams(params);

            TextView tvDineIn = (TextView) findViewById(R.id.textView1);
            tvDineIn.setText("Sale");

            TextView tvOrder = (TextView) findViewById(R.id.textOrder);
            tvOrder.setText("Sale History");


        }

    }

    public void bindstatus() {
        TextView txtusername = (TextView) findViewById(R.id.txtusername);
        txtusername.setTypeface(font);

        PosUser posuser = dbhelper.getPosUserByUserID(Integer.parseInt(dbhelper.getwaiterid()));
        String posusername = dbhelper.getwaitername() + " - " + posuser.getName();

        txtusername.setText(posusername);
        txtusername.setTag(dbhelper.getwaiterid());

        if (dbhelper.getOfflineFlag().equals(false))//added by WaiWL on 10/06/2015
            checkConnectionStatus();

        if (dbhelper.getOfflineFlag().equals(false) && GlobalClass.CheckConnectionForSubmit(MainScreen.this)) {
            dbhelper.LoadCurrencyTable(dbhelper.getServiceURL());//added WHM [2020-05-27] load currency
        }
    }

    public void checkconnection_onClick(View v) {
        checkConnectionStatus();
    }

    public void checkConnectionStatus() {

        boolean isInternetPresent = GlobalClass.CheckConnection(this);
        // check for Internet status
        ((ImageView) findViewById(R.id.constatus)).setImageResource(0);
        if (isInternetPresent) {

            ((ImageView) findViewById(R.id.constatus)).setImageResource(R.drawable.greenstatus);
        } else {
            ((ImageView) findViewById(R.id.constatus)).setImageResource(R.drawable.redstatus);

        }

    }

    public void GetImage() {
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
        String usr_code = Json_class.getString(dbhelper.getServiceURL() + "/Data/Getusr_codeorderbydate");
        final String[] user_codelist = usr_code.split(",");
        progressBar.setMax(60 + user_codelist.length);
        progressBar.show();
        //reset progress bar status
        progressBarStatus = 0;

        try {
            new Thread(new Runnable() {
                public void run() {

                    for (int i = 0; i < user_codelist.length; i++) {
                        //dbhelper.LoadItemImage(dataurl, Itemlist.get(i).getusr_code());
                        downloadImage(dataurl + "/RestaurantImage" + "/" + user_codelist[i] + ".jpg", user_codelist[i]);
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
        } catch (Exception ex) {
        } finally {
        }
    }

    private void downloadImage(String url, String usr_code) {
        Bitmap bitmap = null;
        InputStream stream = null;
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        //bmOptions.inSampleSize = 1;
        try {
            stream = Json_class.getHttpConnection(url);
            if (stream == null)
                return;
            bitmap = BitmapFactory.
                    decodeStream(stream, null, bmOptions);
            stream.close();

            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);


            // create a File object for the parent directory
            File Directory = new File(GlobalClass.GetImageStorageLocation(this));
            // have the object build the directory structure, if needed.

            if (!Directory.exists())
                Directory.mkdirs();

            //you can create a new file name "test.jpg" in sdcard folder.
            File f = new File(GlobalClass.GetImageStorageLocation(this)
                    + File.separator + usr_code + ".jpg");

            if (f.exists()) {
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

    public void GetDatawithoutprogress() {
        // prepare for a progress bar dialog
        final String dataurl = new DatabaseHelper(this).getServiceURL();
        try {
            new Thread(new Runnable() {
                public void run() {
                    try {
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
                    } catch (Exception ex) {

                    }

                }
            }).start();
        } catch (Exception ex) {
        } finally {
        }
    }

    public void setting_dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.activity_menulist, null));
        final Dialog dialog = builder.create();

        WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.TOP;
        Button btnother = (Button) findViewById(R.id.butsetting);
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

        final TextView txtregister = (TextView) dialog.findViewById(R.id.txtregister);
        final TextView txtunregister = (TextView) dialog.findViewById(R.id.txtunregister);
        final TextView txtloaddata = (TextView) dialog.findViewById(R.id.txtloaddata);
        //final TextView txtloadimage = (TextView) dialog.findViewById(R.id.txtloadimage);
        final TextView txtSetURL = (TextView) dialog.findViewById(R.id.txtseturl);
        final TextView txtchangeLanguage = (TextView) dialog.findViewById(R.id.txtChangeLanguage);
        final TextView txtresetdata = (TextView) dialog.findViewById(R.id.txtResetData);
        final TextView txtitemview = (TextView) dialog.findViewById(R.id.txtItemViewStyle);
        final TextView txttestprint = (TextView) dialog.findViewById(R.id.txttestprint);
        final TextView txtitemdescription = (TextView) dialog.findViewById(R.id.txtItemDescription);
        final TextView txtTableRowCount = (TextView) dialog.findViewById(R.id.txtTableRowCount);
        final TextView txtItemRowCount = (TextView) dialog.findViewById(R.id.txtItemRowCount);
        final TextView txtMIFinisheditems = (TextView) dialog.findViewById(R.id.txtFinishedItems);
        txtMIFinisheditems.setVisibility(8);

        //added WHM [2020-05-28] tablet menu
        final TextView txtMenuItemViewCount = (TextView) dialog.findViewById(R.id.txtMenuItemViewCount);
        final TextView txtMenuItemViewCountLine = (TextView) dialog.findViewById(R.id.txtMenuItemViewCountLine);
        txtMenuItemViewCount.setVisibility(View.VISIBLE);
        txtMenuItemViewCountLine.setVisibility(View.VISIBLE);
        //end whm

        //added by WaiWL on 09/06/2015
        chkoffline = (CheckBox) dialog.findViewById(R.id.chkOffline);

        if (dbhelper.getOfflineFlag().equals(false))
            chkoffline.setChecked(false);
        else
            chkoffline.setChecked(true);
        //////////////

        if (dbhelper.getRegisterFlag().equals(false)) {
            txtunregister.setVisibility(8);
            txtloaddata.setVisibility(8);
        } else {
            txtregister.setVisibility(8);
            txtunregister.setVisibility(8);
            txtloaddata.setVisibility(0);
        }

        txtregister.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                MenuItemClick(txtregister);
                dialog.dismiss();
            }
        });

        txtunregister.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                MenuItemClick(txtunregister);
                dialog.dismiss();
            }
        });

        txtloaddata.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                MenuItemClick(txtloaddata);
                dialog.dismiss();
            }
        });

//        txtloadimage.setOnClickListener(new OnClickListener() {
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                MenuItemClick(txtloadimage);
//                dialog.dismiss();
//            }
//        });

        txtSetURL.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                MenuItemClick(txtSetURL);
                dialog.dismiss();
            }
        });

        txtchangeLanguage.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                MenuItemClick(txtchangeLanguage);
                dialog.dismiss();
            }
        });

        txtresetdata.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                MenuItemClick(txtresetdata);
                dialog.dismiss();
            }
        });

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

        txtTableRowCount.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                MenuItemClick(txtTableRowCount);
                //dialog.dismiss();
            }
        });

        txtItemRowCount.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                MenuItemClick(txtItemRowCount);
            }
        });

        txttestprint.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                MenuItemClick(txttestprint);
                //dialog.dismiss();
            }
        });

        //added by WaiWL on 09/06/2015
        chkoffline.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                MenuItemClick(chkoffline);
            }
        });

        //added WHM [2020-05-28] tablet menu
        txtMenuItemViewCount.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                MenuItemClick(txtMenuItemViewCount);
            }
        });
    }

    public void Select_User_dialog(final Dialog maindialog) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.activity_customer, null));
        final Dialog dialog = builder.create();

        WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.CENTER_VERTICAL;
        wmlp.y = GlobalClass.getDPsize(30, this);

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(GlobalClass.getDPsize(400, this), GlobalClass.getDPsize(350, this));

        dialog.show();

        List<PosUser> userlist = dbhelper.getPosUserlistForSwitchuser();

        TextView txtTitle = (TextView) dialog.findViewById(R.id.txtSelectCustomer);
        txtTitle.setText("Select User");

        AutoCompleteTextView txtCustomer = (AutoCompleteTextView) dialog.findViewById(R.id.txtautocompletecustomer);
        txtCustomer.setVisibility(View.GONE);    //Invisible

        ListView customerlistview = (ListView) dialog.findViewById(R.id.custlist);


        final List<RowItem> rowItems = new ArrayList<RowItem>();
        for (int j = 0; j < userlist.size(); j++) {
            RowItem item = new RowItem(j, userlist.get(j).getUserId(), "", userlist.get(j).getShort() + " - " + userlist.get(j).getName(), "");
            rowItems.add(item);
        }
        CustomerCustomListViewAdapter oddadapter = new CustomerCustomListViewAdapter(this, R.layout.customerlist_item, rowItems);
        customerlistview.setAdapter(oddadapter);

        customerlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                ((EditText) maindialog.findViewById(R.id.txtusername)).setTag(rowItems.get(position).getheaderid());
                ((EditText) maindialog.findViewById(R.id.txtusername)).setText(rowItems.get(position).getdetail());
                dialog.dismiss();
            }
        });
        //checkConnectionStatus();
    }

    public boolean MenuItemClick(TextView item) {

        boolean isInternetPresent = false;
        switch (item.getId()) {
            case R.id.txtloaddata:

                isInternetPresent = GlobalClass.CheckConnection(this);

                // check for Internet status
                if (isInternetPresent) {
                    GetData();
                } else {
                    showAlertDialog(this, "No Server Connection",
                            "You don't have connection with Server.", false);
                }
                break;

//            case R.id.txtloadimage:
//
//                isInternetPresent = GlobalClass.CheckConnection(this);
//
//                // check for Internet status
//                if (isInternetPresent) {
//
//                    GetImage();
//                } else {
//                    showAlertDialog(this, "No Server Connection",
//                            "You don't have connection with Server.", false);
//                }
//                break;


            case R.id.txtseturl:
                final AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("Server IP Address & Port No.");

                final LinearLayout iplayout = new LinearLayout(this);
                iplayout.setOrientation(LinearLayout.VERTICAL);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
                params.setMargins(10, 10, 10, 10);
                final EditText input = new EditText(this);
                input.setPadding(5, 5, 5, 5);
                input.setLayoutParams(params);
                input.setHint("192.168.0.1:80");

                final DatabaseHelper dbhelper = new DatabaseHelper(this);
                input.setText(dbhelper.getServerIP());
                input.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                //input.setBackgroundResource(R.drawable.edittext);
                iplayout.addView(input);
                alert.setView(iplayout);
                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String value = input.getText().toString().trim();
                        dbhelper.AddServiceAddress(value, "", "");
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.cancel();
                    }
                });

                AlertDialog dialog = alert.create();
                dialog.show();
                dialog.getWindow().setLayout(500, 350);

                break;

            case R.id.txtregister:
                try {
                    Register();
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;


            case R.id.txtunregister:
                try {
                    UnRegister();
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            //added by WaiWL on 10/06/2015
            case R.id.chkOffline:
                try {
                    final DatabaseHelper dbhelp = new DatabaseHelper(this);
                    String isOffline = "";
                    if (chkoffline.isChecked() == true) {
                        isOffline = "1";
                    } else
                        isOffline = "0";

                    dbhelp.AddOfflineMode(isOffline);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                break;
            /////////////////////////////
            case R.id.txtChangeLanguage:      //modified by EKK on 10/07/2019
                final AlertDialog.Builder languagedialog = new AlertDialog.Builder(this);
                languagedialog.setTitle("Language");
                final DatabaseHelper dbhelper1 = new DatabaseHelper(this);

//                final TextView tv = new TextView(this);
//                tv.setTextColor(Color.parseColor("#00bcd4"));
//                tv.setText("Theme Language");
//                tv.setTextSize(20f);

                final RadioGroup rdogroup = new RadioGroup(this);
                rdogroup.setPadding(30, 20, 100, 20);
                final RadioButton rdobutmyanmar = new RadioButton(this);
                rdobutmyanmar.setText("Myanmar");
                rdobutmyanmar.setPadding(0, 10, 0, 10);
//				   final RadioButton rdobutmyanmarUni = new RadioButton(this);
//				   rdobutmyanmarUni.setText("Myanmar(Unicode)");
                final RadioButton rdodefault = new RadioButton(this);
                rdodefault.setText("English");
                rdodefault.setPadding(0, 10, 0, 10);
                final RadioButton rdochinese = new RadioButton(this);
                rdochinese.setText("Chinese");
                rdochinese.setPadding(0, 10, 0, 10);

//                rdogroup.addView(tv);
                rdogroup.addView(rdodefault);
                rdogroup.addView(rdobutmyanmar);
//				    rdogroup.addView(rdobutmyanmarUni);
                rdogroup.addView(rdochinese);

                if (dbhelper1.getLanguage().equals("language2")) {
                    rdobutmyanmar.setChecked(true);
                } else if (dbhelper1.getLanguage().equals("language3")) {
                    rdochinese.setChecked(true);
                } else
                    rdodefault.setChecked(true);

                View verticalView = new View(this);
                verticalView.setLayoutParams(new ViewGroup.LayoutParams(2, ViewGroup.LayoutParams.MATCH_PARENT));
                verticalView.setBackgroundColor(Color.parseColor("#00bcd4"));

//                final TextView tv2 = new TextView(this);
//                tv2.setTextColor(Color.parseColor("#00bcd4"));
//                tv2.setText("Font");
//                tv2.setTextSize(20f);

//                final RadioGroup ZUrdogroup = new RadioGroup(this);
//                ZUrdogroup.setPadding(30, 20, 30, 20);
//                final RadioButton rdoZg = new RadioButton(new ContextThemeWrapper(this, R.style.AppTheme));
//                rdoZg.setText("Zawgyi");
//                rdoZg.setPadding(0, 10, 0, 10);
//                final RadioButton rdoUni = new RadioButton(new ContextThemeWrapper(this, R.style.AppTheme));
//                rdoUni.setText("Unicode");
//                rdoUni.setPadding(0, 10, 0, 10);
//
//                ZUrdogroup.addView(tv2);
//                ZUrdogroup.addView(rdoZg);
//                ZUrdogroup.addView(rdoUni);

//                if (dbhelper1.isUnicode()) rdoUni.setChecked(true);
//                else rdoZg.setChecked(true);

                final LinearLayout layout = new LinearLayout(this);
                layout.setOrientation(LinearLayout.HORIZONTAL);

                layout.addView(rdogroup);
                //layout.addView(verticalView);
//                layout.addView(ZUrdogroup);

                //input.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                languagedialog.setView(layout);

                final Context ctx = this;
                languagedialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if (rdobutmyanmar.isChecked()) {
                            dbhelper1.AddServiceAddress("", "language2", "");
                        } else if (rdodefault.isChecked()) {
                            dbhelper1.AddServiceAddress("", "language1", "");
                        } else if (rdochinese.isChecked()) {
                            dbhelper1.AddServiceAddress("", "language3", "");
                        } else
                            dbhelper1.AddServiceAddress("", "language1", "");
                        //dbhelper.AddServiceAddress(value, "");

//                        if (rdoUni.isChecked()) dbhelper1.setUnicode("1");
//                        else dbhelper1.setUnicode("0");

                        finish();
                        Intent intent = new Intent(ctx, LoginActivity.class);
                        startActivity(intent);
                    }
                });
                languagedialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.cancel();
                    }
                });
                languagedialog.show();
                break;


            case R.id.txtResetData:
                this.deleteDatabase(new DatabaseHelper(this).getDatabaseName());
                showAlertDialog(this, "Message",
                        "Complete Reset Data.", false);
                break;

            case R.id.txtItemViewStyle:
                itemviewstyle_dialog();
                break;

            case R.id.txtItemDescription:
                itemdescription_dialog();
                break;

            case R.id.txtTableRowCount:
                final AlertDialog.Builder tablerowstyledialog = new AlertDialog.Builder(
                        this);
                tablerowstyledialog.setTitle("Table Row Style");
                final DatabaseHelper dbhelper2 = new DatabaseHelper(this);
                final RadioGroup rdogroup1 = new RadioGroup(this);
                final RadioButton rdobutTwo = new RadioButton(this);
                rdobutTwo.setText("Two");
                final RadioButton rdobutThree = new RadioButton(this);
                rdobutThree.setText("Three");
                final RadioButton rdobutFour = new RadioButton(this);
                rdobutFour.setText("Four");

                rdogroup1.addView(rdobutTwo);
                rdogroup1.addView(rdobutThree);
                rdogroup1.addView(rdobutFour);

                rdogroup1.setOrientation(rdogroup1.HORIZONTAL);
                if (dbhelper2.getTableRowStyle().equals("Two")) {
                    rdobutTwo.setChecked(true);
                } else if (dbhelper2.getTableRowStyle().equals("Three")) {
                    rdobutThree.setChecked(true);
                } else
                    rdobutFour.setChecked(true);
                // input.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                tablerowstyledialog.setView(rdogroup1);

                final Context ctxs = this;
                tablerowstyledialog.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                if (rdobutTwo.isChecked()) {
                                    dbhelper2.AddTableRowStyle("", "Two");
                                } else if (rdobutThree.isChecked()) {
                                    dbhelper2.AddTableRowStyle("", "Three");
                                } else if (rdobutFour.isChecked()) {
                                    dbhelper2.AddTableRowStyle("", "Four");
                                } else
                                    dbhelper2.AddTableRowStyle("", "Three");
                                // dbhelper.AddServiceAddress(value, "");
                                finish();
                                Intent intent = new Intent(ctxs, LoginActivity.class);
                                startActivity(intent);
                            }
                        });

                tablerowstyledialog.show();
                break;
            // Item Row Count
            case R.id.txtItemRowCount:
                final AlertDialog.Builder itemrowstyledialog = new AlertDialog.Builder(
                        this);
                itemrowstyledialog.setTitle("Item Row Style");
                final DatabaseHelper dbhelper3 = new DatabaseHelper(this);
                final RadioGroup rdogroup2 = new RadioGroup(this);
                final RadioButton rdobutTwoItem = new RadioButton(this);
                rdobutTwoItem.setText("Two");
                final RadioButton rdobutThreeItem = new RadioButton(this);
                rdobutThreeItem.setText("Three");
                final RadioButton rdobutFourItem = new RadioButton(this);
                rdobutFourItem.setText("Four");

                rdogroup2.addView(rdobutTwoItem);
                rdogroup2.addView(rdobutThreeItem);
                rdogroup2.addView(rdobutFourItem);

                rdogroup2.setOrientation(rdogroup2.HORIZONTAL);
                if (dbhelper3.getItemRowStyle().equals("Four")) {
                    rdobutFourItem.setChecked(true);
                } else if (dbhelper3.getItemRowStyle().equals("Three")) {
                    rdobutThreeItem.setChecked(true);
                } else
                    rdobutTwoItem.setChecked(true);
                // input.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                itemrowstyledialog.setView(rdogroup2);

                final Context ctxi = this;
                itemrowstyledialog.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                if (rdobutTwoItem.isChecked()) {
                                    dbhelper3.AddItemRowStyle("", "Two");
                                } else if (rdobutThreeItem.isChecked()) {
                                    dbhelper3.AddItemRowStyle("", "Three");
                                } else if (rdobutFourItem.isChecked()) {
                                    dbhelper3.AddItemRowStyle("", "Four");
                                } else
                                    dbhelper3.AddItemRowStyle("", "Three");
                                // dbhelper.AddServiceAddress(value, "");
                                finish();
                                Intent intent = new Intent(ctxi, LoginActivity.class);
                                startActivity(intent);
                            }
                        });

                itemrowstyledialog.show();
                break;


            case R.id.txttestprint:
                Intent intent = new Intent(this, TestPrintActivity.class);
                startActivity(intent);

                break;

            //region menu item view count  //added WHM [2020-05-28] tablet menu
            case R.id.txtMenuItemViewCount:
                final AlertDialog.Builder menuItemDialog = new AlertDialog.Builder(this);
                menuItemDialog.setTitle("Menu Item Column Style");
                final DatabaseHelper dbhelper_menu = new DatabaseHelper(this);

                final TextView tv_menuItemRow = new TextView(this);
                tv_menuItemRow.setTextColor(Color.parseColor("#00bcd4"));
                tv_menuItemRow.setText("Row Style");
                tv_menuItemRow.setTextSize(20f);

                final RadioGroup rdoMenu_RowGroup = new RadioGroup(this);
                rdoMenu_RowGroup.setPadding(30, 20, 300, 20);

                final RadioButton rdomenuRow2 = new RadioButton(this);
                rdomenuRow2.setText("Two");
                rdomenuRow2.setPadding(0, 10, 0, 10);

                final RadioButton rdomenuRow3 = new RadioButton(this);
                rdomenuRow3.setText("Three");
                rdomenuRow3.setPadding(0, 10, 0, 10);

                final RadioButton rdoMenuRow4 = new RadioButton(this);
                rdoMenuRow4.setText("Four");
                rdoMenuRow4.setPadding(0, 10, 0, 10);

                rdoMenu_RowGroup.addView(tv_menuItemRow);
                rdoMenu_RowGroup.addView(rdomenuRow2);
                rdoMenu_RowGroup.addView(rdomenuRow3);
                rdoMenu_RowGroup.addView(rdoMenuRow4);

                if (dbhelper_menu.getmenuItemViewCount("row").equals("3")) {
                    rdomenuRow3.setChecked(true);
                } else if (dbhelper_menu.getmenuItemViewCount("row").equals("4")) {
                    rdoMenuRow4.setChecked(true);
                } else
                    rdomenuRow2.setChecked(true);

                View verticalView_menuitem = new View(this);
                verticalView_menuitem.setLayoutParams(new ViewGroup.LayoutParams(2, ViewGroup.LayoutParams.MATCH_PARENT));
                verticalView_menuitem.setBackgroundColor(Color.parseColor("#00bcd4"));

                final TextView tv_menuItemColumn = new TextView(this);
                tv_menuItemColumn.setTextColor(Color.parseColor("#00bcd4"));
                tv_menuItemColumn.setText("Column Style");
                tv_menuItemColumn.setTextSize(20f);

                final RadioGroup rdoMenu_ColumnGroup = new RadioGroup(this);
                rdoMenu_ColumnGroup.setPadding(30, 20, 30, 20);
                rdoMenu_ColumnGroup.setOrientation(rdoMenu_ColumnGroup.HORIZONTAL);


                final RadioButton rdomenuCol2 = new RadioButton(new ContextThemeWrapper(this, R.style.AppTheme));
                rdomenuCol2.setText("Two");
                rdomenuCol2.setPadding(0, 10, 0, 10);

                final RadioButton rdomenuCol3 = new RadioButton(new ContextThemeWrapper(this, R.style.AppTheme));
                rdomenuCol3.setText("Three");
                rdomenuCol3.setPadding(0, 10, 0, 10);

                final RadioButton rdomenuCol4 = new RadioButton(new ContextThemeWrapper(this, R.style.AppTheme));
                rdomenuCol4.setText("Four");
                rdomenuCol4.setPadding(0, 10, 0, 10);

                //rdoMenu_ColumnGroup.addView(tv_menuItemColumn);
                rdoMenu_ColumnGroup.addView(rdomenuCol2);
                rdoMenu_ColumnGroup.addView(rdomenuCol3);
                rdoMenu_ColumnGroup.addView(rdomenuCol4);

                if (dbhelper_menu.getmenuItemViewCount("column").equals("3")) {
                    rdomenuCol3.setChecked(true);
                } else if (dbhelper_menu.getmenuItemViewCount("column").equals("4")) {
                    rdomenuCol4.setChecked(true);
                } else
                    rdomenuCol2.setChecked(true);

                final LinearLayout layout_menuitem = new LinearLayout(this);
                layout_menuitem.setOrientation(LinearLayout.HORIZONTAL);

                // layout_menuitem.addView(rdoMenu_RowGroup);
                layout_menuitem.addView(rdoMenu_ColumnGroup);

                menuItemDialog.setView(layout_menuitem);


                final Context ctx_menuitem = this;
                menuItemDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //row
//						   if(rdomenuRow2.isChecked())
//						   {
//							   dbhelper_menu.Add_MenuItemViewCount("row","2");
//						   }
//						   else if(rdomenuRow3.isChecked())
//						   {
//							   dbhelper_menu.Add_MenuItemViewCount("row","3");
//						   }else if(rdoMenuRow4.isChecked())
//						   {
//							   dbhelper_menu.Add_MenuItemViewCount("row","4");
//						   }
//						   else
//							   dbhelper_menu.Add_MenuItemViewCount("row","2");

                        //column
                        if (rdomenuCol2.isChecked()) {
                            dbhelper_menu.Add_MenuItemViewCount("column", "2");
                        } else if (rdomenuCol3.isChecked()) {
                            dbhelper_menu.Add_MenuItemViewCount("column", "3");
                        } else if (rdomenuCol4.isChecked()) {
                            dbhelper_menu.Add_MenuItemViewCount("column", "4");
                        } else
                            dbhelper_menu.Add_MenuItemViewCount("column", "2");


                        //  finish();
                        //Intent intent = new Intent(ctx_menuitem, LoginActivity.class);
                        // startActivity(intent);
                    }
                });
                menuItemDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.cancel();
                    }
                });
                menuItemDialog.show();
                break;

            //endregion menu item view count

        }
        return true;
    }

    public void butsetting_OnClick(View v) {
        setting_dialog();
        //checkConnectionStatus();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_screen, menu);

        if (dbhelper.getRegisterFlag().equals(false)) {
            menu.getItem(1).setVisible(false); //UnRegister
            menu.getItem(3).setVisible(false); //Load Data
        } else {
            menu.getItem(0).setVisible(false); //Register
            menu.getItem(1).setVisible(true);  //UnRegister
            menu.getItem(3).setVisible(true);  //Load Data
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.setloaddata:


                boolean isInternetPresent = GlobalClass.CheckConnection(this);

                // check for Internet status
                if (isInternetPresent) {
                    // Internet Connection is Present
                    // make HTTP requests
                    //showAlertDialog(MainActivity.this, "Server Connection","You have connection with Server", true);
                    GetData();
                } else {
                    // Internet connection is not present
                    // Ask user to connect to Internet
                    showAlertDialog(this, "No Server Connection",
                            "You don't have connection with Server.", false);
                }
                break;

            case R.id.register:
                try {
                    Register();
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;


            case R.id.unregister:
                try {
                    UnRegister();
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.setdataurl:
                final AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("Data Service URL");
                final EditText input = new EditText(this);
                final DatabaseHelper dbhelper = new DatabaseHelper(this);
                input.setText(dbhelper.getServiceURL());
                input.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                alert.setView(input);
                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String value = input.getText().toString().trim();
                        dbhelper.AddServiceAddress(value, "", "");
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.cancel();
                    }
                });
                alert.show();
                break;
            case R.id.changelanguage:
                final AlertDialog.Builder languagedialog = new AlertDialog.Builder(this);
                languagedialog.setTitle("Language");
                final DatabaseHelper dbhelper1 = new DatabaseHelper(this);
                final RadioGroup rdogroup = new RadioGroup(this);
                final RadioButton rdobutmyanmar = new RadioButton(this);
                rdobutmyanmar.setText("Myanmar");
                final RadioButton rdodefault = new RadioButton(this);
                rdodefault.setText("English");
                final RadioButton rdochinese = new RadioButton(this);
                rdodefault.setText("Chinese");


                rdogroup.addView(rdobutmyanmar);
                rdogroup.addView(rdodefault);
                rdogroup.addView(rdochinese);

                if (dbhelper1.getLanguage().equals("Myanmar")) {
                    rdobutmyanmar.setChecked(true);
                } else if (dbhelper1.getLanguage().equals("Chinese")) {
                    rdochinese.setChecked(true);
                } else
                    rdodefault.setChecked(true);
                //input.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                languagedialog.setView(rdogroup);

                final Context ctx = this;
                languagedialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if (rdobutmyanmar.isChecked()) {
                            dbhelper1.AddServiceAddress("", "Myanmar", "");
                        } else if (rdochinese.isChecked()) {
                            dbhelper1.AddServiceAddress("", "Chinese", "");
                        } else if (rdodefault.isChecked()) {
                            dbhelper1.AddServiceAddress("", "English", "");
                        } else
                            dbhelper1.AddServiceAddress("", "English", "");
                        //dbhelper.AddServiceAddress(value, "");
                        finish();
                        Intent intent = new Intent(ctx, MainScreen.class);
                        startActivity(intent);


                    }
                });
                languagedialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.cancel();
                    }
                });
                languagedialog.show();
                break;
        }
        return true;
    }

    public void Register() throws NumberFormatException, JSONException {

        boolean isInternetPresent = GlobalClass.CheckConnectionForSubmit(this);
        // check for Internet status
        JSONArray salejsonarray = new JSONArray();
        JSONObject jsonobj = new JSONObject();
        //sale_head_main
        jsonobj.put("DeviceID", GlobalClass.GetTabletID());
        jsonobj.put("DeviceBuildNumber", Build.DISPLAY);

        salejsonarray.put(jsonobj);

        if (isInternetPresent) {
            Json_class jsonclass = new Json_class();
            JSONArray jsonmessage = jsonclass.getJson(new DatabaseHelper(this).getServiceURL() + "/Data/Register?orderdata=" + java.net.URLEncoder.encode(salejsonarray.toString()));

            if (jsonmessage.length() > 0) {
                if (Integer.parseInt(jsonmessage.get(0).toString()) == 0) {
                    dbhelper.updateRegisterFlag(true, "");
                    showAlertDialog(this, "Message", jsonmessage.get(1).toString(), false);
                    //this.finish();
                } else {
                    //showAlertDialog(this, "Error Message", jsonmessage.get(1).toString(), false);
                    showAlertDialog(this, "Error Message", jsonmessage.get(2).toString(), false);
                }
            }
        } else {
            showAlertDialog(this, "Warning", "No Connection with Server!", false);
        }
    }

    public void UnRegister() throws NumberFormatException, JSONException {

        // check for Internet status
        if (GlobalClass.CheckConnectionForSubmit(this)) {
            Json_class jsonclass = new Json_class();
            final JSONArray jsonmessage = jsonclass.getJson(new DatabaseHelper(this).getServiceURL() + "/Data/UnRegister?DeviceID=" + java.net.URLEncoder.encode(GlobalClass.GetTabletID()));

            if (jsonmessage.length() > 0) {
                if (Integer.parseInt(jsonmessage.get(0).toString()) == 0) {
                    dbhelper.updateRegisterFlag(false, "");
                    showAlertDialog(this, "Message", jsonmessage.get(1).toString(), false);
                    //this.finish();
                } else {
                    //showAlertDialog(this, "Error Message", jsonmessage.get(1).toString(), false);
                    showAlertDialog(this, "Error Message", jsonmessage.get(2).toString(), false);
                }
            }
        } else {
            showAlertDialog(this, "Warning", "No Connection with Server!", false);
        }
    }

    public void switchuser_dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.activity_switchuser, null));
        final Dialog dialog = builder.create();

        WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.CENTER;

        dialog.show();
        Window window = dialog.getWindow();
        //int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300, getResources().getDisplayMetrics());
        window.setLayout(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

        final Context ctx = this;
        final Button butclose = (Button) dialog.findViewById(R.id.btncancel);
        butclose.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });

        final Button butselecteduser = (Button) dialog.findViewById(R.id.btnselectuser);
        butselecteduser.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Select_User_dialog(dialog);
            }
        });

        final TextView txterrormsg = (TextView) dialog.findViewById(R.id.txterrormsg);
        final Button butconfirm = (Button) dialog.findViewById(R.id.btnconfirm);
        butconfirm.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                EditText txtUserName = (EditText) dialog.findViewById(R.id.txtusername);
                EditText txtPassword = (EditText) dialog.findViewById(R.id.txtpassword);
                if (txtUserName.getTag() == null) {
                    txterrormsg.setText("Please Select UserName!");
                    txterrormsg.setVisibility(0);
                } else {
                    int pos = Integer.parseInt(txtUserName.getTag().toString());
                    PosUser SelectedUserobj = dbhelper.getPosUserByUserID(pos);
                    if (txtPassword.getText().toString().equals(SelectedUserobj.getPassword().toString())) {

                        // check for Internet status
                        if (GlobalClass.CheckConnection(ctx)) {
                            new DatabaseHelper(ctx).LoadSystemSetting(new DatabaseHelper(ctx).getServiceURL());

                            Json_class jsonclass = new Json_class();

                            JSONArray jsonmessage = jsonclass.getJson(new DatabaseHelper(ctx).getServiceURL() + "/Data/switchuser?olduserid=" + java.net.URLEncoder.encode(dbhelper.getwaiterid()) + "&newuserid=" + java.net.URLEncoder.encode(SelectedUserobj.getUserId().toString()));

                            if (jsonmessage.length() > 0) {
                                try {
                                    if (Integer.parseInt(jsonmessage.get(0).toString()) == 0) {
                                        txtPassword.setText("");
                                        dbhelper.DeleteDeviceOwner();
                                        dbhelper.AddDeviceOwner(SelectedUserobj.getShort(), SelectedUserobj.getUserId().toString());
                                        Intent intent = new Intent(ctx, MainScreen.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        //showAlertDialog(this, "Error Message", jsonmessage.get(1).toString(), false);
                                        //showAlertDialog(this, "Error Message", jsonmessage.get(2).toString(), false);
                                        txterrormsg.setText(jsonmessage.get(2).toString());
                                        txterrormsg.setVisibility(0);
                                    }
                                } catch (NumberFormatException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                } catch (JSONException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            }


                        } else {
                            //showAlertDialog(ctx, "No Server Connection",
                            //        "You don't have connection with Server!", false);
                            txterrormsg.setText("You don't have connection with Server!");
                            txterrormsg.setVisibility(0);
                        }
                    } else {
                        //showAlertDialog(ctx, "Galaxy Restaurant","Incorrect Password.", false);
                        txterrormsg.setText("Incorrect Password!");
                        txterrormsg.setVisibility(0);
                    }
                }
            }
        });
    }

    public void itemviewstyle_dialog() {
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
        //window.setLayout(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

        final Button btnsave = (Button) dialog.findViewById(R.id.btnsave);
        final RadioGroup rdoitemviewstyle = (RadioGroup) dialog.findViewById(R.id.rdoitemviewstyle);

        //added WHM [2020-09-30] YGN2-200983
        final RadioButton rdowithclassbutton = (RadioButton) dialog.findViewById(R.id.rdowithclass);
        if (GlobalClass.use_menucategorygrouptype != 0) {
            rdowithclassbutton.setVisibility(View.GONE);
        }

        int checkitem = dbhelper.getitemviewstyle();
        switch (checkitem) {
            case 0:
                rdoitemviewstyle.check(R.id.rdowithoutclass);
                break;
            case 1:
                rdoitemviewstyle.check(R.id.rdowithclass);
                break;
            case 2:
                rdoitemviewstyle.check(R.id.rdofullcategory);
                break;
        }

        btnsave.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                int i = rdoitemviewstyle.getCheckedRadioButtonId();
                switch (i) {
                    case R.id.rdowithoutclass:
                        i = 0;
                        break;
                    case R.id.rdowithclass:
                        i = 1;
                        break;
                    case R.id.rdofullcategory:
                        i = 2;
                        break;
                }

                dbhelper.updateitemviewstyle(i);
                dialog.dismiss();
            }
        });
    }


    public void itemdescription_dialog() {
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

        final Button btndescription1 = (Button) dialog.findViewById(R.id.btndescription1);
        final Button btndescription2 = (Button) dialog.findViewById(R.id.btndescription2);
        final Button btndescription3 = (Button) dialog.findViewById(R.id.btndescription3);

        int checkitem = dbhelper.getitemdescriptionstyle();
        switch (checkitem) {
            case 0:
                btndescription1.setBackgroundColor(Color.parseColor("#d9522c"));
                break;
            case 1:
                btndescription2.setBackgroundColor(Color.parseColor("#d9522c"));
                break;
            case 2:
                btndescription3.setBackgroundColor(Color.parseColor("#d9522c"));
                break;
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
        //return (keyCode == KeyEvent.KEYCODE_BACK ? true : super.onKeyDown(keyCode, event));
    }
    
    /*
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
	    if(!hasFocus) {
	        Intent closeDialog = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
	        sendBroadcast(closeDialog);
	    }
	    checkConnectionStatus();
    }
    
    
    @Override
    public void onStop() {
       super.onStop();
       finish();
    }
    */


    public void GetData() {
        // prepare for a progress bar dialog
        final String dataurl = new DatabaseHelper(this).getServiceURL();
        progressBar = new ProgressDialog(this);
        progressBar.setMessage("File downloading ...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressBar.setCancelable(false);
        progressBar.setProgress(0);
        final String ItemCount = dbhelper.LoadItemCount(dataurl);// Item
        final String ModifierItemCount = dbhelper.LoadModifierItemCount(dataurl); // modifieritem
        progressBar.setMax(65 + Integer.parseInt(ItemCount.trim()) + Integer.parseInt(ModifierItemCount.trim()));
        progressBar.show();
        //reset progress bar status
        progressBarStatus = 0;

        try {
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

                    if (dbhelper.getLastLoadingTimeLog().equals("")) // first time
                    {
                        for (int i = 1; i <= Integer.parseInt(ItemCount.trim()); i++) {
                            dbhelper.LoadItemFirstTime(dataurl, Integer.toString(i));
                            progressBarStatus += 1;
                            progressBarHandler.post(new Runnable() {
                                public void run() {
                                    progressBar.setProgress(progressBarStatus);
                                }
                            });
                        }

                        for (int j = 1; j <= Integer.parseInt(ModifierItemCount.trim()); j++) {
                            dbhelper.LoadModifiedItemFirstTime(dataurl, Integer.toString(j));
                            progressBarStatus += 1;
                            progressBarHandler.post(new Runnable() {
                                public void run() {
                                    progressBar.setProgress(progressBarStatus);
                                }
                            });
                        }
                    } else {
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

                    //added by WaiWL on 27/05/2015
                    dbhelper.LoadSpecialMenu(dataurl);
                    progressBarStatus += 5;
                    progressBarHandler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(progressBarStatus);
                        }
                    });
                    ////////////

                    //added by WaiWL on 27/05/2015
                    dbhelper.LoadSpecialMenu_code(dataurl);
                    progressBarStatus += 5;
                    progressBarHandler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(progressBarStatus);
                        }
                    });
                    ///////////////

                    //added by WAiWL on 15/09/2015 --add CustomerCountSetUp
                    dbhelper.Load_CustomerCountSetUp(dataurl);
                    progressBarStatus += 5;
                    progressBarHandler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(progressBarStatus);
                        }
                    });
                    ////

                    //added by EKK on 14-12-2019
                    if (dbhelper.getuse_Promotion() == true)
                        dbhelper.LoadPromotionItem(dataurl);

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
                    if (dbhelper.use_deliverymanagement() == true) {
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


                    //region usemenucategrorygroup //added WHM [2020-09-30] YGN2-200983
                    if (dbhelper.use_MenuCategoryGroupType() != 0) {
                        dbhelper.LoadMenuCategory_GroupByArea(dataurl);
                        dbhelper.LoadMenuCategory_GroupByLoc(dataurl);
                        progressBarStatus += 20;
                        progressBarHandler.post(new Runnable() {
                            public void run() {
                                progressBar.setProgress(progressBarStatus);
                            }
                        });
                    }

                    //region userarea //added WHM [2020-11-11]
                    if (dbhelper.use_areagroupbyuser() == true) {
                        dbhelper.LoadArea_GroupByUser(dataurl);
                        progressBarStatus += 20;
                        progressBarHandler.post(new Runnable() {
                            public void run() {
                                progressBar.setProgress(progressBarStatus);
                            }
                        });
                    }
                    //endregion userarea

                    progressBar.dismiss();

                    //Date d = new Date();
                    //CharSequence s  = DateFormat.format("yyyy-MM-dd kk:mm:ss", d.getTime());
                    dbhelper.UpdateTimeLog(loaddate);
                }
            }).start();
        } catch (Exception ex) {
        } finally {
        }
    }

    public void butclick_click(View v) {
        Intent intent;

        switch (v.getId()) {
            case R.id.butdinein:
                if (!GlobalClass.tmpOffline) {
                    if (!GlobalClass.CheckConnection(this)) {
                        checkConnectionStatus();
                        showAlertDialog(this, "Warning!", "No connection with Server.", false);
                        return;
                    }

                }

                if (GlobalClass.use_foodtruck) {
                    Intent dineintent = new Intent(this, OrderTaking.class);
                    Bundle dineb = new Bundle();
                    dineb.putString("TableID", "Parcel");
                    dineb.putString("TableName", "Parcel");
                    dineb.putString("SaleID", "0");
                    dineintent.putExtras(dineb);
                    startActivityForResult(dineintent, 100);
                    finish();

                } else {
                    intent = new Intent(this, TableScreenActivity.class);
                    startActivity(intent);
                    finish();
                }

                break;

//    							intent = new Intent(this, TableScreenActivity.class);
//								startActivity(intent);
//								finish();
//								break;

            case R.id.butparcel:
                finish();
                intent = new Intent(this, OrderTaking.class);
                Bundle b = new Bundle();
                b.putString("TableID", "Parcel");
                b.putString("TableName", "Parcel");
                intent.putExtras(b);
                startActivity(intent);
                break;

            case R.id.butorders:
                if (!GlobalClass.tmpOffline) {
                    if (!GlobalClass.CheckConnection(this)) {
                        checkConnectionStatus();
                        showAlertDialog(this, "Warning!", "No connection with Server.", false);
                        return;
                    }
                }
                finish();
                intent = new Intent(this, Orderedprogress.class);
                startActivity(intent);
                break;

            case R.id.butswitchuser:
                switchuser_dialog();
                break;

            case R.id.butmenu:
//    							finish();
                intent = new Intent(this, NewMenuActivity.class);
                startActivity(intent);
                break;

            case R.id.butexit:
                Json_class jsonclass = new Json_class();
                jsonclass.getString(new DatabaseHelper(this).getServiceURL() + "/Data/UnLockUser?UserID=" + java.net.URLEncoder.encode(dbhelper.getwaiterid()));
                dbhelper.DeleteDeviceOwner();

                if (GlobalClass.CheckConnection(this)) { //added by ZYP for login user
                    Json_class jsclass = new Json_class();
                    jsclass.getString(dbhelper
                            .getServiceURL()
                            + "/Data/ClearLoginUser?userid="
                            + java.net.URLEncoder.encode(String.valueOf(LoginActivity.pos)));

                    Intent exitintent = new Intent(this, LoginActivity.class);
                    startActivity(exitintent);
                    this.finish();
                } else {
                    Toast.makeText(this, "Check your connection and try again!", Toast.LENGTH_SHORT).show();
                }


                //exitpassword_dialog();
                //Application.
                break;

        }
    }

    public void exitpassword_dialog() {
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

        Button butOK = (Button) dialog.findViewById(R.id.butOK);
        final TextView txtpassword = (TextView) dialog.findViewById(R.id.txtpassword);

        butOK.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                String strpassword = txtpassword.getText().toString();
                if (!strpassword.equals("")) {
                    String okflag = Json_class.getString(dbhelper.getServiceURL() + "/Data/CheckExitPassword?Password=" + strpassword);
                    try {
                        if (okflag.trim().equals("True")) {
                            dialog.dismiss();
                            dbhelper.DeleteDeviceOwner();
                            finish();
                        }
                    } catch (Exception ex) {


                    }

                }

            }
        });

    }

    public void showAlertDialog(Context context, final String title, String message, Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(message);

        // Setting alert dialog icon
        alertDialog.setIcon((status) ? 0 : 0);

        final Context ctx = this;

        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (title == "Message") {
                    finish();
                    Intent intent = new Intent(ctx, LoginActivity.class);
                    startActivity(intent);
                }

            }
        });

        // Showing Alert Message
        alertDialog.show();
        //checkConnectionStatus();
    }

}
