package com.galaxy.restaurantpos;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import android.telephony.TelephonyManager;
import android.text.InputType;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
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
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.location.Location;
import android.location.LocationManager;

import me.myatminsoe.mdetect.MDetect;

import static java.time.temporal.ChronoUnit.DAYS;

public class LoginActivity extends Activity {

    private ProgressDialog progressBar; // Progres
    private int progressBarStatus = 0;
    private Handler progressBarHandler = new Handler();
    private EditText txtUserName, txtPassword, txtusercode;
    private Button button1, button2;
    private View View1;
    private TextView txtPrint, txtsetuserline, txtTrial;
    private CheckBox chkoffline;
    final DatabaseHelper dbhelper = new DatabaseHelper(this);
    public Typeface font;
    List<PosUser> userlist;
    private String devicename;

    private boolean isTrialEnd = false;
    //    public static boolean isUnicode=false;
    public static boolean isUnicode = false;
    private static final int REQUEST_LOCATION = 1;
    //public static Boolean isUnicode;
    LocationManager locationManager;
    String latitude, longitude;
    public static SharedPreferences deviceid;

    @TargetApi(11)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GlobalClass.setCurrentscreen(1);
        // Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (android.os.Build.VERSION.SDK_INT > 9) {// for check connection
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        if (dbhelper.getknockcode() == true) {
            setContentView(R.layout.knockcode);
            txtUserName = ((EditText) findViewById(R.id.txtusername));
            txtPassword = ((EditText) findViewById(R.id.txtpassword));
            txtPassword.setText("");
            txtUserName.setFocusable(false);
            txtPassword.setFocusable(false);

            button1 = ((Button) findViewById(R.id.Button01));

            // txtPassword.requestFocus();

            txtPassword.setOnKeyListener(new OnKeyListener() {
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((event.getAction() == KeyEvent.ACTION_DOWN)
                            && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        // Perform action on key press
                        btnconfirm_click(null);
                        return true;
                    }
                    return false;
                }
            });
        } else {
            setContentView(R.layout.activity_login);

            txtTrial = findViewById(R.id.txtTrial);

            txtUserName = ((EditText) findViewById(R.id.txtusername));
            txtPassword = ((EditText) findViewById(R.id.txtpassword));

            txtPassword.setOnKeyListener(new OnKeyListener() {
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((event.getAction() == KeyEvent.ACTION_DOWN)
                            && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        // Perform action on key press
                        btnconfirm_click(null);
                        return true;
                    }
                    return false;
                }
            });

            isUnicode = MDetect.INSTANCE.isUnicode();
            txtUserName.setFocusable(false);
            txtUserName.setTypeface(font);

            GlobalClass.ChangeLanguage(
                    (ViewGroup) findViewById(R.id.loginlayout), this, 18, font);

            if (dbhelper.getOfflineFlag().equals(false))// added by WaiWL on
                // 10/06/2015
                checkConnectionStatus();

            killProcess(this.getPackageName());
        }

        String deviceUniqueIdentifier = null; ////modified by ZYP [22-09-2020] for Device ID unique
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (null != tm) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            deviceUniqueIdentifier = tm.getDeviceId();
        }
        if (null == deviceUniqueIdentifier || 0 == deviceUniqueIdentifier.length()) {
            deviceUniqueIdentifier = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        GlobalClass.Identifier = deviceUniqueIdentifier;

        CheckTrialDate();

        /*
         * int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION ;
         * this.getWindow().getDecorView().setSystemUiVisibility(uiOptions);
         */
        // Toast.makeText(this,MDetect.INSTANCE.isUnicode()?"မင်္ဂလာပါ": Rabbit.uni2zg("မင်္ဂလာပါ"),Toast.LENGTH_SHORT).show();
    }

    private void checkPermissions() {
        int permission1 = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        //int permission2 = ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN);
        if (permission1 != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    this,
                    PERMISSIONS_STORAGE,
                    1
            );
        }
    }

    public static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_PHONE_STATE,
    };

    public void btnNo_OnClick(View v) {

        Button b = (Button) v;
        String buttonText = b.getText().toString();
        txtPassword.append(buttonText);

    }

    public void btnBack_OnClick(View v) {

        if (txtPassword.getText().length() > 0) {
            int length = txtPassword.getText().length() - 1;
            StringBuilder strB = new StringBuilder(txtPassword.getText());
            txtPassword.setText(strB.deleteCharAt(length));
        }

    }

    public void butsetting_OnClick(View v) {
        setting_dialog();
        // checkConnectionStatus();
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
        wmlp.y = location[1] + GlobalClass.getDPsize(20, this);
        dialog.show();
        Window window = dialog.getWindow();
        // int width = (int)
        // TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300,
        // getResources().getDisplayMetrics());
        window.setLayout(GlobalClass.getDPsize(200, this),
                LayoutParams.WRAP_CONTENT);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

        final TextView txtregister = (TextView) dialog
                .findViewById(R.id.txtregister);
        final TextView txtunregister = (TextView) dialog
                .findViewById(R.id.txtunregister);
        final TextView txtloaddata = (TextView) dialog
                .findViewById(R.id.txtloaddata);
//        final TextView txtloadimage = (TextView) dialog
//                .findViewById(R.id.txtloadimage);
        final TextView txtSetURL = (TextView) dialog
                .findViewById(R.id.txtseturl);
        final TextView txtSetUser = (TextView) dialog
                .findViewById(R.id.txtsetUser);
        final TextView txtchangeLanguage = (TextView) dialog
                .findViewById(R.id.txtChangeLanguage);
        final TextView txtresetdata = (TextView) dialog
                .findViewById(R.id.txtResetData);
        final TextView txtTableRowCount = (TextView) dialog
                .findViewById(R.id.txtTableRowCount);

        final TextView txtItemRowCount = (TextView) dialog.
                findViewById(R.id.txtItemRowCount);

        final TextView txtitemviewstyle = (TextView) dialog
                .findViewById(R.id.txtItemViewStyle);
        final TextView txtTestPrint = (TextView) dialog
                .findViewById(R.id.txttestprint);
        final TextView txtitemdescription = (TextView) dialog
                .findViewById(R.id.txtItemDescription);

        final TextView txtMIFinisheditems = (TextView) dialog
                .findViewById(R.id.txtFinishedItems);
        txtMIFinisheditems.setVisibility(View.GONE);

        // added by WaiWL on 09/06/2015
        chkoffline = (CheckBox) dialog.findViewById(R.id.chkOffline);

        if (dbhelper.getOfflineFlag().equals(false))
            chkoffline.setChecked(false);
        else
            chkoffline.setChecked(true);
        // ////////////

        txtTestPrint.setVisibility(View.GONE);
        txtTableRowCount.setVisibility(View.VISIBLE);

        txtItemRowCount.setVisibility(View.GONE);
        if (dbhelper.getRegisterFlag().equals(false)) {
            txtunregister.setVisibility(View.GONE);
            txtloaddata.setVisibility(View.GONE);
            // txtloadimage.setVisibility(View.GONE);
        } else {
            txtregister.setVisibility(View.GONE);
            txtunregister.setVisibility(View.GONE);
            txtloaddata.setVisibility(View.VISIBLE);
            // txtloadimage.setVisibility(View.VISIBLE);
        }

        txtitemviewstyle.setVisibility(View.GONE);
        txtitemdescription.setVisibility(View.GONE);

        txtregister.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                MenuItemClick(txtregister);
                // dialog.dismiss();
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

        txtSetUser.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog.dismiss();
                btnAdd_click();

            }
        });

        txtchangeLanguage.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                MenuItemClick(txtchangeLanguage);
                dialog.dismiss();
            }
        });

        txtTableRowCount.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                MenuItemClick(txtTableRowCount);
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

        // added by WaiWL on 09/06/2015
        chkoffline.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                MenuItemClick(chkoffline);
            }
        });

        // ////////
    }

    public void btnAdd_click() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.setuser, null));
        final Dialog dialog = builder.create();
        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(GlobalClass.getDPsize(500, this),
                LayoutParams.WRAP_CONTENT);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        txtusercode = ((EditText) dialog.findViewById(R.id.txtusercode));
        txtusercode.setInputType(InputType.TYPE_CLASS_NUMBER);
        txtsetuserline = ((TextView) dialog.findViewById(R.id.txtsetuserline));
        final LinearLayout userlayout = (LinearLayout) dialog.findViewById(R.id.userlayout);
        final LinearLayout userlayout2 = new LinearLayout(this);
        final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                GlobalClass.getDPsize(600, this), GlobalClass.getDPsize(30,
                this));
        final DatabaseHelper dbhelps = new DatabaseHelper(this);

        final List<Setuser> userlist = dbhelps.getuserlist(1);
        userlayout.addView(userlayout2);
        for (int i = 0; i < userlist.size(); i++) {
            txtsetuserline.setVisibility(View.VISIBLE);

            params.setMargins(6, 6, 0, 4);
            createcontrol(i, userlayout, userlist, dialog);

        }

        Button btnAdd;
        btnAdd = ((Button) dialog.findViewById(R.id.btnAdd));
        btnAdd.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                String str = txtusercode.getText().toString();
                if (!str.isEmpty()) {
                    Integer userid = Integer.parseInt(txtusercode.getText()
                            .toString());
                    if (userid != null) {
                        Add(userid);
                    }
                }
                List<Setuser> userlist = dbhelps.getuserlist(1);
                userlayout.removeAllViews();
                for (int i = 0; i < userlist.size(); i++) {
                    txtsetuserline.setVisibility(View.VISIBLE);
                    params.setMargins(6, 6, 0, 4);
                    createcontrol(i, userlayout, userlist, dialog);
                }
            }

        });

    }

    public void createcontrol(int i, final LinearLayout userlayout,
                              List<Setuser> userlist, final Dialog dialog) {
        final DatabaseHelper dbhelps = new DatabaseHelper(this);
        final LinearLayout layout = (LinearLayout) dialog
                .findViewById(R.id.userlayout);
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.HORIZONTAL);

        final Setuser s = userlist.get(i);
        TextView t = new TextView(this);
        t.setText("  " + (i + 1) + " .     " + s.getsetusername());
        t.setTag(s.getsetuserid());
        t.setWidth(371);
        ll.addView(t);

        final Button btn = new Button(this);
        btn.setId(s.getsetuserid());
        btn.setText("Delete");
        btn.setWidth(80);
        btn.setHeight(10);

        btn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                dbhelper.deleteAddeduser(btn.getId());
                List<Setuser> userlist = dbhelps.getuserlist(1);
                userlayout.removeAllViews();
                for (int i = 0; i < userlist.size(); i++) {
                    txtsetuserline.setVisibility(View.VISIBLE);
                    createcontrol(i, layout, userlist, dialog);
                }
            }
        });

        ll.addView(btn);
        userlayout.addView(ll);
    }

    public void Add(int userid) {
        if (dbhelper.updatetabletuser(userid) == true) {
            showAlertDialog(this, "Galaxy Restaurant", "Added user", false);
        } else {
            showAlertDialog(this, "Galaxy Restaurant", "Incorrect Userid",
                    false);
        }
        txtusercode.setText("");

    }

    public boolean MenuItemClick(TextView item) {
        ConnectionDetector cd = new ConnectionDetector(this);
        boolean isInternetPresent = false;
        switch (item.getId()) {
            case R.id.txtloaddata:

                // isInternetPresent = cd.isConnectedToServer(new
                // DatabaseHelper(this).getServiceURL());

                // check for Internet status
                if (GlobalClass.CheckConnection(this)) {
                    // GetDatawithoutprogress();
                    GetData();
                    new DatabaseHelper(this).LoadSystemSetting(new DatabaseHelper(this).getServiceURL()); //Added by Arkar Moe on [15/09/2016]-[Res-0390]


                } else {
                    showAlertDialog(this, "No Server Connection",
                            "You don't have connection with Server.", false);
                }
                break;

//            case R.id.txtloadimage:
//
//                // isInternetPresent = cd.isConnectedToServer(new
//                // DatabaseHelper(this).getServiceURL());
//
//                // check for Internet status
//                if (GlobalClass.CheckConnection(this)) {
//                    // GetDatawithoutprogress();
//                    GetImage();
//                } else {
//                    showAlertDialog(this, "No Server Connection",
//                            "You don't have connection with Server.", false);
//                }
//                break;

            case R.id.txtseturl:
                final AlertDialog.Builder alert = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.DarkAppTheme));
                alert.setTitle("Server IP Address & Port No.");
                final EditText input = new EditText(new ContextThemeWrapper(this, R.style.DarkAppTheme));
                input.setHint("192.168.0.1:80");
                final DatabaseHelper dbhelper = new DatabaseHelper(this);
                input.setText(dbhelper.getServerIP());
                input.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                alert.setView(input);
                alert.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                String value = input.getText().toString().trim();
                                dbhelper.AddServiceAddress(value, "", "");
                            }
                        });

                alert.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                dialog.cancel();
                            }
                        });
                alert.show();
                break;

            case R.id.txtregister:
                try {
                    // showAlertDialogforRigster(this, "Enter your device name",
                    // false);
                    devicename = null;
                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.DarkAppTheme));

                    alertDialog.setTitle("Enter your device name");

                    final EditText txt = new EditText(new ContextThemeWrapper(this, R.style.DarkAppTheme));
                    txt.setInputType(InputType.TYPE_CLASS_TEXT);
                    alertDialog.setView(txt);
                    //

                    alertDialog.setIcon((false) ? 0 : 0);

                    final Context ctx = this;
                    final DatabaseHelper dbhelp = new DatabaseHelper(this);

                    alertDialog.setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {

                                    String d_name = txt.getText().toString();
                                    devicename = d_name;
                                    //	if (!d_name.matches("")) {
                                    try {

                                        Register(devicename);
                                    } catch (NumberFormatException e) {

                                        e.printStackTrace();
                                    } catch (JSONException e) {

                                        e.printStackTrace();
                                    }
                                    String isOffline = "";
                                    if (chkoffline.isChecked() == true) {
                                        isOffline = "1";
                                    } else
                                        isOffline = "0";

                                    dbhelp.AddOfflineMode(isOffline);
                                    //}
                                }
                            });


                    alertDialog.show();
                    if (devicename != null) {
                        Register(devicename);
                        String isOffline = "";
                        if (chkoffline.isChecked() == true) {
                            isOffline = "1";
                        } else
                            isOffline = "0";

                        dbhelp.AddOfflineMode(isOffline);
                    }

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
            // added by WaiWL on 10/06/2015
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
            // ///////////////////////////
            case R.id.txtChangeLanguage: //modified by EKK on 10/07//2019
                final AlertDialog.Builder languagedialog = new AlertDialog.Builder(new ContextThemeWrapper(this,
                        R.style.AppTheme));
                languagedialog.setTitle("Language");
                final DatabaseHelper dbhelper1 = new DatabaseHelper(this);


//                final TextView tv = new TextView(this);
//                tv.setTextColor(Color.parseColor("#00bcd4"));
//                tv.setText("Theme Language");
//                tv.setTextSize(20f);

                final RadioGroup rdogroup = new RadioGroup(this);
                rdogroup.setPadding(30, 20, 100, 20);
                final RadioButton rdobutmyanmar = new RadioButton(new ContextThemeWrapper(this, R.style.AppTheme));
                rdobutmyanmar.setText("Myanmar");
                rdobutmyanmar.setPadding(0, 10, 0, 10);
//			final RadioButton rdobutmyanmarUni = new RadioButton(this);
//			rdobutmyanmarUni.setText("Myanmar(Unicode)");
                final RadioButton rdodefault = new RadioButton(new ContextThemeWrapper(this, R.style.AppTheme));
                rdodefault.setText("English");
                rdodefault.setPadding(0, 10, 0, 10);
                final RadioButton rdochinese = new RadioButton(new ContextThemeWrapper(this, R.style.AppTheme));
                rdochinese.setText("Chinese");
                rdochinese.setPadding(0, 10, 0, 10);

                // rdogroup.addView(tv);
                rdogroup.addView(rdodefault);
                rdogroup.addView(rdobutmyanmar);
//			rdogroup.addView(rdobutmyanmarUni);
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
//
//                final RadioGroup rdogroup2 = new RadioGroup(this);
//                rdogroup2.setPadding(30, 20, 30, 20);
//                final RadioButton rdoZg = new RadioButton(new ContextThemeWrapper(this, R.style.AppTheme));
//                rdoZg.setText("Zawgyi");
//                rdoZg.setPadding(0, 10, 0, 10);
//                final RadioButton rdoUni = new RadioButton(new ContextThemeWrapper(this, R.style.AppTheme));
//                rdoUni.setText("Unicode");
//                rdoUni.setPadding(0, 10, 0, 10);
//
//                rdogroup2.addView(tv2);
//                rdogroup2.addView(rdoZg);
//                rdogroup2.addView(rdoUni);

//                if (dbhelper1.isUnicode()) rdoUni.setChecked(true);
//                else rdoZg.setChecked(true);

                final LinearLayout layout = new LinearLayout(this);
                layout.setOrientation(LinearLayout.HORIZONTAL);

                layout.addView(rdogroup);
                //layout.addView(verticalView);
//                layout.addView(rdogroup2);

                // input.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                languagedialog.setView(layout);

                final Context ctx = this;
                languagedialog.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                if (rdobutmyanmar.isChecked()) {
                                    dbhelper1.AddServiceAddress("", "language2", "");  //modified by EKK
                                } else if (rdodefault.isChecked()) {
                                    dbhelper1.AddServiceAddress("", "language1", "");
                                } else if (rdochinese.isChecked()) {
                                    dbhelper1.AddServiceAddress("", "language3", "");
                                } else
                                    dbhelper1.AddServiceAddress("", "language1", "");
                                // dbhelper.AddServiceAddress(value, "");


//                                if (rdoUni.isChecked()) dbhelper1.setUnicode("1");
//                                else dbhelper1.setUnicode("0");

                                finish();
                                Intent intent = new Intent(ctx, LoginActivity.class);
                                startActivity(intent);
                            }
                        });
                languagedialog.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                dialog.cancel();
                            }
                        });
                languagedialog.show();
                break;

            case R.id.txtTableRowCount:
                final AlertDialog.Builder tablerowstyledialog = new AlertDialog.Builder(
                        this);
                tablerowstyledialog.setTitle("TableRow Style");
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

            case R.id.txtResetData:
                this.deleteDatabase(new DatabaseHelper(this).getDatabaseName());
                showAlertDialog(this, "Message", "Complete Reset Data.", false);
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.activity_login, menu);

        if (dbhelper.getRegisterFlag().equals(false)) {
            menu.getItem(1).setVisible(false); // UnRegister
            menu.getItem(3).setVisible(false); // Load Data
        } else {
            menu.getItem(0).setVisible(false); // Register
            menu.getItem(1).setVisible(true); // UnRegister
            menu.getItem(3).setVisible(true); // Load Data
        }

        return true;

    }

    /*
     * public void onWindowFocusChanged(boolean hasFocus) {
     * super.onWindowFocusChanged(hasFocus); if(!hasFocus) { Intent closeDialog
     * = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
     * sendBroadcast(closeDialog); } checkConnectionStatus(); }
     */

    private void killProcess(String process_name) {
        ActivityManager manager = (ActivityManager) this
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> listOfProcesses = manager
                .getRunningAppProcesses();

        for (ActivityManager.RunningAppProcessInfo process : listOfProcesses) {
            if (!process.processName.equals(process_name)) {
                // manager.restartPackage(process.pkgList[0]);
                manager.killBackgroundProcesses(process.pkgList[0]);
            }
        }
        List<ActivityManager.RunningAppProcessInfo> listOfProcessesss = manager
                .getRunningAppProcesses();
    }

    public void checkconnection_onClick(View v) {
        checkConnectionStatus();

    }

    public void checkConnectionStatus() {

        // check for Internet status
        ((ImageView) findViewById(R.id.constatus)).setImageResource(0);
        if (GlobalClass.CheckConnection(this)) {

            ((ImageView) findViewById(R.id.constatus))
                    .setImageResource(R.drawable.greenstatus);
            CheckTrialDate();
        } else {
            ((ImageView) findViewById(R.id.constatus))
                    .setImageResource(R.drawable.redstatus);

        }

    }

    @SuppressLint("SimpleDateFormat")
    private void CheckTrialDate() {
        if (GlobalClass.CheckConnection(this)) {
            if (dbhelper.getAppSetting("use_trial").equals("Y")) {
                try {
                    txtTrial.setVisibility(View.VISIBLE);
                    String currentDateString = dbhelper.getCurrentDateTime();     //13/09/2022 14:52
                    Date currentDate = new SimpleDateFormat("dd/MM/yyyy").parse(currentDateString);
                    String expDateString = dbhelper.getAppSetting("trial_expire_date");
                    Date expDate = new SimpleDateFormat("yyyy-MM-dd").parse(expDateString);
                    System.out.println(currentDate + "\t" + expDate);
                    if (currentDate != null && expDate != null && currentDate.compareTo(expDate) < 0) {
                        long diff = expDate.getTime() - currentDate.getTime();
                        String trialDay = String.valueOf((int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
                        txtTrial.setText("Trial " + trialDay + " day left!");
                        isTrialEnd = false;
                    } else {
                        txtTrial.setText("Trial Period End!");
                        isTrialEnd = true;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    txtTrial.setVisibility(View.GONE);
                }
            } else {
                txtTrial.setVisibility(View.GONE);
            }
        }
    }

    public void Register(String DeviceName) throws NumberFormatException,
            JSONException {

        // check for Internet status
        if (DeviceName.matches("")) {
            showAlertDialog(this, "Warning", "Please, Enter your device name",
                    false);
            return;
        }

        JSONArray salejsonarray = new JSONArray();
        JSONObject jsonobj = new JSONObject();
        // sale_head_main
//        String deviceUniqueIdentifier = null; ////modified by ZYP [19-06-2020] for Device ID unique
//        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//        if (null != tm) {
//            deviceUniqueIdentifier = tm.getDeviceId();
//        }
//        if (null == deviceUniqueIdentifier || 0 == deviceUniqueIdentifier.length()) {
//            deviceUniqueIdentifier = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
//        }
//        GlobalClass.Identifier = deviceUniqueIdentifier;

        jsonobj.put("DeviceID", GlobalClass.GetTabletID());
        jsonobj.put("DeviceBuildNumber", Build.DISPLAY);
        jsonobj.put("DeviceName", DeviceName);

        salejsonarray.put(jsonobj);

        if (GlobalClass.CheckConnectionForSubmit(this)) {
            try {

                Json_class jsonclass = new Json_class();
                JSONArray jsonmessage = jsonclass.getJson(new DatabaseHelper(this)
                        .getServiceURL()
                        + "/Data/Register?orderdata="
                        + java.net.URLEncoder.encode(salejsonarray.toString()));

                if (jsonmessage.length() > 0) {
                    if (Integer.parseInt(jsonmessage.get(0).toString()) == 0) {
                        dbhelper.updateRegisterFlag(true, DeviceName);

                        showAlertDialog(this, "Message", jsonmessage.get(1)
                                .toString(), false);
                        // this.finish();
                    } else {
                        // showAlertDialog(this, "Error Message",
                        // jsonmessage.get(1).toString(), false);
                        showAlertDialog(this, "Error Message", jsonmessage.get(2)
                                .toString(), false);
                    }
                }
            } catch (Exception ex) {
                showAlertDialog(this, "Error Message!", "Invalid Server IP address or Port No.",
                        false);
            }

        } else {
            //progressBar.dismiss();
            showAlertDialog(this, "Warning", "No Connection with Server!",
                    false);
        }
        checkConnectionStatus();
    }

    public void UnRegister() throws NumberFormatException, JSONException {

        // check for Internet status
        if (GlobalClass.CheckConnectionForSubmit(this)) {
            Json_class jsonclass = new Json_class();
            JSONArray jsonmessage = jsonclass.getJson(new DatabaseHelper(this)
                    .getServiceURL()
                    + "/Data/UnRegister?DeviceID="
                    + java.net.URLEncoder.encode(GlobalClass.GetTabletID()));

            if (jsonmessage.length() > 0) {
                if (Integer.parseInt(jsonmessage.get(0).toString()) == 0) {
                    dbhelper.updateRegisterFlag(false, "");
                    showAlertDialog(this, "Message", jsonmessage.get(1)
                            .toString(), false);
                    // this.finish();
                } else {
                    // showAlertDialog(this, "Error Message",
                    // jsonmessage.get(1).toString(), false);
                    showAlertDialog(this, "Error Message", jsonmessage.get(2)
                            .toString(), false);
                }
            }
        } else {
            showAlertDialog(this, "Warning", "No Connection with Server!",
                    false);
        }
        checkConnectionStatus();
    }

    public void GetData() {
        // prepare for a progress bar dialog
        final String dataurl = new DatabaseHelper(this).getServiceURL();
        progressBar = new ProgressDialog(new ContextThemeWrapper(this, R.style.AppTheme));
        progressBar.setMessage("File downloading ...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressBar.setCancelable(false);
        progressBar.setProgress(0);
        final String ItemCount = dbhelper.LoadItemCount(dataurl);// Item
        final String ModifierItemCount = dbhelper
                .LoadModifierItemCount(dataurl); // modifieritem
        progressBar.setMax(65 + Integer.parseInt(ItemCount.trim())
                + Integer.parseInt(ModifierItemCount.trim()));
        progressBar.show();

        // reset progress bar status
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
                            dbhelper.LoadItemFirstTime(dataurl,
                                    Integer.toString(i));
                            progressBarStatus += 1;
                            progressBarHandler.post(new Runnable() {
                                public void run() {
                                    progressBar.setProgress(progressBarStatus);
                                }
                            });
                        }

                        for (int j = 1; j <= Integer.parseInt(ModifierItemCount
                                .trim()); j++) {
                            dbhelper.LoadModifiedItemFirstTime(dataurl,
                                    Integer.toString(j));
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
                        progressBarStatus += Integer.parseInt(ModifierItemCount
                                .trim());
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

                    // added by WaiWL on 27/05/2015
                    dbhelper.LoadSpecialMenu_code(dataurl);
                    progressBarStatus += 5;
                    progressBarHandler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(progressBarStatus);
                        }
                    });
                    // /////////////

                    // added by WAiWL on 15/09/2015 --add CustomerCountSetUp
                    dbhelper.Load_CustomerCountSetUp(dataurl);
                    progressBarStatus += 5;
                    progressBarHandler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(progressBarStatus);
                        }
                    });
                    // added by SMH on 03/11/2015
                    dbhelper.LoadAppsetting(dataurl);
                    progressBarStatus += 10;
                    progressBarHandler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(progressBarStatus);
                        }
                    });

                    // added by SMH on 31/05/2016
                    dbhelper.LoadItemRemark(dataurl, "0");
                    progressBarStatus += 10;
                    progressBarHandler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(progressBarStatus);
                        }
                    });

                    // //

                    // added by SMH on 29/05/2017
                    dbhelper.LoadMealType(dataurl);
                    progressBarStatus += 10;
                    progressBarHandler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(progressBarStatus);
                        }
                    });

                    dbhelper.LoadUserLog(dataurl);
                    progressBarStatus += 10;
                    progressBarHandler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(progressBarStatus);
                        }
                    });

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

                    dbhelper.LoadSetMenuChagedItem(dataurl);
                    progressBarStatus += 10;
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

                    //added WHM [2020-05-28] load Currency
                    dbhelper.LoadCurrencyTable(dataurl);
                    progressBarStatus += 3;
                    progressBarHandler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(progressBarStatus);
                        }
                    });


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

                    //endregion usemenucategorygroup

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

                    //added by ZYP soldout on loaddata
                    dbhelper.LoadSoldOut(dataurl);
                    progressBarStatus += 3;
                    progressBarHandler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(progressBarStatus);
                        }
                    });


                    progressBar.dismiss();

                    // Date d = new Date();
                    // CharSequence s = DateFormat.format("yyyy-MM-dd kk:mm:ss",
                    // d.getTime());
                    dbhelper.UpdateTimeLog(loaddate);
                }
            }).start();
        } catch (
                Exception ex) {
        } finally {
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
        // final List<ItemsObj> Itemlist = dbhelper.getItemslist();// Item
        String usr_code = Json_class.getString(dbhelper.getServiceURL()
                + "/Data/Getusr_codeorderbydate");
        final String[] user_codelist = usr_code.split(",");
        progressBar.setMax(60 + user_codelist.length);
        progressBar.show();
        // reset progress bar status
        progressBarStatus = 0;

        try {
            new Thread(new Runnable() {
                public void run() {

                    for (int i = 0; i < user_codelist.length; i++) {
                        // dbhelper.LoadItemImage(dataurl,
                        // Itemlist.get(i).getusr_code());

                        if (user_codelist[i] == "00101") {
                            String www = "";

                        }

                        downloadImage(dataurl + "/RestaurantImage" + "/"
                                + user_codelist[i] + ".jpg", user_codelist[i]);
                        progressBarStatus += 1;
                        progressBarHandler.post(new Runnable() {
                            public void run() {
                                progressBar.setProgress(progressBarStatus);
                            }
                        });
                    }

                    downloadImage(dataurl + "/RestaurantImage" + "/" //Added by ArkarMoe on [06/01/2016] for adding default image
                            + "Default_Image.jpg", "Default_Image");
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
        // bmOptions.inSampleSize = 2;
        try {
            stream = Json_class.getHttpConnection(url);
            if (stream == null)
                return;
            bitmap = BitmapFactory.decodeStream(stream, null, bmOptions);
            stream.close();

            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

            // create a File object for the parent directory
            File Directory = new File(GlobalClass.GetImageStorageLocation(this));
            // have the object build the directory structure, if needed.

            if (!Directory.exists())
                Directory.mkdirs();

            // you can create a new file name "test.jpg" in sdcard folder.
            File f = new File(GlobalClass.GetImageStorageLocation(this)
                    + File.separator + usr_code + ".jpg");

            if (f.exists()) {
                f.delete();
            }

            f.createNewFile();
            // write the bytes in file
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
        final String ItemCount = dbhelper.LoadItemCount(dataurl);// Item
        final String ModifierItemCount = dbhelper
                .LoadModifierItemCount(dataurl); // modifieritem
        try {
            new Thread(new Runnable() {
                public void run() {

                    dbhelper.LoadArea(dataurl);

                    dbhelper.LoadTable(dataurl);
                    for (int i = 0; i < Integer.parseInt(ItemCount.trim()); i++) {
                        dbhelper.LoadItemFirstTime(dataurl, Integer.toString(i));
                        progressBarStatus += 1;
                        progressBarHandler.post(new Runnable() {
                            public void run() {
                                progressBar.setProgress(progressBarStatus);
                            }
                        });
                    }

                    for (int j = 0; j < Integer.parseInt(ModifierItemCount
                            .trim()); j++) {
                        dbhelper.LoadModifiedItemFirstTime(dataurl,
                                Integer.toString(j));
                        progressBarStatus += 10;
                        progressBarHandler.post(new Runnable() {
                            public void run() {
                                progressBar.setProgress(progressBarStatus);
                            }
                        });
                    }

                    dbhelper.LoadModifierGroup(dataurl);

                    dbhelper.LoadDictionary(dataurl);

                    dbhelper.LoadCustomer(dataurl);

                    dbhelper.LoadPosUser(dataurl);

                    // Date d = new Date();
                    // CharSequence s = DateFormat.format("yyyy-MM-dd kk:mm:ss",
                    // d.getTime());
                    dbhelper.UpdateTimeLog(dbhelper.LoadServerDate(dataurl));
                }
            }).start();
        } catch (Exception ex) {
        } finally {
        }
    }

    public void GetMenuItemImages() {
        // prepare for a progress bar dialog
        final String dataurl = new DatabaseHelper(this).getServiceURL();
        final List<String> usrcodelist = new DatabaseHelper(this)
                .getusrcodelist();
        progressBar = new ProgressDialog(this);
        progressBar.setCancelable(true);
        progressBar.setMessage("Image downloading ...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressBar.setCancelable(false);
        progressBar.setProgress(0);
        progressBar.setMax(usrcodelist.size());
        progressBar.show();
        // reset progress bar status
        progressBarStatus = 0;
        try {
            new Thread(new Runnable() {
                public void run() {

                    for (String usrcode : usrcodelist) {
                        dbhelper.LoadItemImage(dataurl, usrcode);
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

    public void showAlertDialog(Context context, final String title,
                                String message, Boolean status) {
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

        TextView textView = (TextView) alertDialog
                .findViewById(android.R.id.message);
        textView.setTypeface(font);
    }

    public void showAlertDialogforRigster(Context context, final String title,
                                          Boolean status) {

        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        alertDialog.setTitle(title);

        final EditText txt = new EditText(this);
        txt.setInputType(InputType.TYPE_CLASS_TEXT);
        alertDialog.setView(txt);
        devicename = txt.getText().toString();

        alertDialog.setIcon((status) ? 0 : 0);

        final Context ctx = this;

        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (title == "Message") {
                    finish();
                    Intent intent = new Intent(ctx, LoginActivity.class);
                    startActivity(intent);
                }

            }
        });

        alertDialog.show();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        checkConnectionStatus();
        return super.onKeyDown(keyCode, event);

        // return (keyCode == KeyEvent.KEYCODE_BACK ? true :
        // super.onKeyDown(keyCode, event));
    }

    public void Select_User_dialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, AlertDialog.THEME_HOLO_DARK));
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.activity_customer, null));
        final Dialog dialog = builder.create();

        WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.CENTER_VERTICAL;
        wmlp.y = GlobalClass.getDPsize(100, this);
        //wmlp.x = GlobalClass.getDPsize(300, this);

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(GlobalClass.getDPsize(400, this),
                GlobalClass.getDPsize(350, this));
        //window.setAttributes(wmlp);
        dialog.show();

        userlist = dbhelper.getPosUserlist();

        TextView txtTitle = (TextView) dialog
                .findViewById(R.id.txtSelectCustomer);
        txtTitle.setText("Select User");

        AutoCompleteTextView txtCustomer = (AutoCompleteTextView) dialog
                .findViewById(R.id.txtautocompletecustomer);
        txtCustomer.setVisibility(View.GONE); // Invisible

        ListView customerlistview = (ListView) dialog
                .findViewById(R.id.custlist);

        final List<RowItem> rowItems = new ArrayList<RowItem>();
        for (int j = 0; j < userlist.size(); j++) {
            RowItem item = new RowItem(j, userlist.get(j).getUserId(), "",
                    userlist.get(j).getShort() + " - "
                            + (userlist.get(j).getName()), "");
            rowItems.add(item);
        }

        CustomerCustomListViewAdapter oddadapter = new CustomerCustomListViewAdapter(
                this, R.layout.customerlist_item, rowItems);
        customerlistview.setAdapter(oddadapter);

        // final ArrayAdapter<String> listadapter = new
        // ArrayAdapter<String>(this,
        // android.R.layout.simple_list_item_checked, Userarray);
        // customerlistview.setAdapter(listadapter);

        customerlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                txtUserName.setTag(rowItems.get(position).getheaderid());
                txtUserName.setText(rowItems.get(position).getdetail());
                dialog.dismiss();
            }
        });

        if (dbhelper.getOfflineFlag().equals(false)) // added by WaiWL on
            // 10/06/2015
            checkConnectionStatus();
    }

    public void btnselectuser_click(View v) {
        Select_User_dialog();
    }

    public void btncancel_click(View v) {
        // finish();
        exitpassword_dialog();

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
        window.setLayout(GlobalClass.getDPsize(300, this),
                GlobalClass.getDPsize(300, this));
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

        Button butOK = (Button) dialog.findViewById(R.id.butOK);
        final TextView txtpassword = (TextView) dialog
                .findViewById(R.id.txtpassword);

        final Context ctx = this;

        butOK.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                String strpassword = txtpassword.getText().toString();
                if (!strpassword.equals("")) {
                    if (GlobalClass.CheckConnection(ctx)) {
                        String exitpassword = Json_class.getString(dbhelper
                                .getServiceURL()
                                + "/Data/GetExitPassword?Password="
                                + strpassword);
                        dbhelper.updateExitPassword(exitpassword);
                    }
                    try {
                        if (strpassword.equals(dbhelper.GetExitPassword()
                                .trim())) {
                            dialog.dismiss();
                            exitApp();
                        } else if (strpassword.equals("galaxy015427")) {
                            dialog.dismiss();
                            exitApp();
                        }

                    } catch (Exception ex) {
                        if (strpassword.equals("galaxy015427")) {
                            dialog.dismiss();
                            exitApp();

                        }

                    }

                }
            }
        });

    }

    public void exitApp() {
        // call this method to exit _CLEARLY_,
        // and prompt the user which launcher to use next

        // clear the default for your app (to show the prompt when exiting)
        final PackageManager pm = getPackageManager();
        pm.clearPackagePreferredActivities(getApplicationContext().getPackageName());
        this.finish();
        // exit _CLEARLY_
        // calling finish(); would be ok also,
        // but there would stay a 'zombie' in the dalvik cache
        // and 'zombies' only use up your memory, so kill your entire app:
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    public static int pos;

    public void btnconfirm_click(View v) {

        if (isTrialEnd) {
            AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
            alertDialog.setTitle("Trial Period End!");
            ImageView image = new ImageView(this);
            image.setImageResource(R.drawable.contactus);
            alertDialog.setView(image);
            //alertDialog.setMessage(message);
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", (dialog, which) -> {
            });
            alertDialog.show();

            return;
        }

        if (txtUserName.getTag() == null) {
            showAlertDialog(this, "Galaxy Restaurant",
                    "Please select user first.", false);
        } else {

            pos = Integer.parseInt(txtUserName.getTag().toString());

            //added by ZYP for already login user
//            String user = "";
//            final String dataurl = new DatabaseHelper(this).getServiceURL();
//            user = dbhelper.getLoginUser(String.valueOf(pos));
//
//            if(!user.equals("")) {
//
//                showAlertDialog(this, "Warring!!","User already login!" , false);
//                //Toast.makeText(this, "User already login!", Toast.LENGTH_LONG).show();
//                return;
//            }

            PosUser SelectedUserobj = dbhelper.getPosUserByUserID(pos);
            if (txtPassword.getText().toString().equals(SelectedUserobj.getPassword())) {
                // ConnectionDetector cd = new ConnectionDetector(this);
                // boolean isInternetPresent = cd.isConnectedToServer(new
                // DatabaseHelper(this).getServiceURL());

                // added by WaiWL on 10/06/2015 --checking for offline mode
                if (dbhelper.getOfflineFlag().equals(true)) {
                    dbhelper.DeleteDeviceOwner();
                    dbhelper.AddDeviceOwner(SelectedUserobj.getShort(),
                            SelectedUserobj.getUserId().toString());

                    Intent intent = new Intent(this, MainScreen.class);
                    startActivity(intent);
                    this.finish();
                } else {
                    // ////--check for offline mode/////////
                    // check for Internet status
                    if (GlobalClass.CheckConnectionForSubmit(this)) {
                        new DatabaseHelper(this).LoadSystemSetting(new DatabaseHelper(this).getServiceURL());

                        Json_class jsonclass = new Json_class();

                        String jsonmessage = jsonclass
                                .getString(new DatabaseHelper(this).getServiceURL()
                                        + "/Data/CheckRegistration?DeviceID="
                                        + java.net.URLEncoder.encode(GlobalClass.GetTabletID()));

                        if (jsonmessage.trim().equals("True")) {
                            ((TextView) findViewById(R.id.txtpassword)).setText("");


                            JSONArray loginjsonarray = new JSONArray();
                            JSONObject jsonobjlogin = new JSONObject();
                            //sale_head_main
                            try {
                                jsonobjlogin.put("DeviceID", GlobalClass.GetTabletID());
                            } catch (JSONException e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            }
                            try {
                                jsonobjlogin.put("UserID", SelectedUserobj.getUserId().toString());
                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }

                            loginjsonarray.put(jsonobjlogin);

                            /*	  jsonmessage = jsonclass.getString( new
									  DatabaseHelper(this).getServiceURL()+"/Data/LockUser?orderdata="+java.net.URLEncoder.encode(loginjsonarray.toString()));														
									  if(jsonmessage.trim().equals("True")) {
										  
									  } else { 
								  showAlertDialog(this, "Message","This user is already Login!", false);
							  }*/

                            dbhelper.DeleteDeviceOwner();
                            dbhelper.AddDeviceOwner(SelectedUserobj.getShort(),
                                    SelectedUserobj.getUserId().toString());

                            // added by WaiWL on 04/06/2015
                            final String daurl = new DatabaseHelper(this).getServiceURL();
                            dbhelper.LoadSpecialMenu(daurl);
                            dbhelper.LoadSpecialMenu_code(daurl);
                            // /////////////

                            String saveUser = dbhelper.SaveLoginUser(SelectedUserobj.getUserId().toString());
                            if (saveUser.trim().equals("successful")) {
                                String def_locationID = Json_class.getString_LOCID(dbhelper
                                        .getServiceURL()
                                        + "/Data/Get_Posuser_saledefLocationID?userID="
                                        + SelectedUserobj.getUserId().toString());

                                //region menucategoryadded WHM [2020-09-29]
                                if (dbhelper.use_MenuCategoryGroupType() == 1) {
                                    if (GlobalClass.CheckConnection(this)) {


                                        GlobalClass.setSale_deflocationID(Integer.parseInt(def_locationID));


                                    }
                                } else {
                                    GlobalClass.strcategoryIn = "";
                                    GlobalClass.strmenucategory_grouptype = " ";
                                }
                                //endregion menucategory whm

                                if (dbhelper.use_foodtruck(SelectedUserobj.getUserId().toString()) &&
                                        dbhelper.use_foodtrucklocation(SelectedUserobj.getUserId().toString())) {

                                    locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
                                    if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) &&
                                            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                                        getLocation();
                                        String response = "";
                                        if (latitude != null && longitude != null) {
                                            response = Json_class.getString(dbhelper.getServiceURL()
                                                    + "/Data/SetFoodTruckLocations?locationid="
                                                    + def_locationID
                                                    + "&point="
                                                    + java.net.URLEncoder.encode(latitude + "," + longitude));
                                        }

                                        if (response.contains("success")) {
                                            Intent intent = new Intent(this, MainScreen.class);
                                            startActivity(intent);
                                            this.finish();
                                        } else {
                                            showAlertDialog(this, "Warning!",
                                                    "Unable to set location.", false);

                                        }

                                    } else {
                                        OnGPS();
                                    }

                                } else {
                                    Intent intent = new Intent(this, MainScreen.class);
                                    startActivity(intent);
                                    this.finish();
                                }

                            } else {
                                Toast.makeText(this, "Error saving user...", Toast.LENGTH_LONG).show();
                            }

                            //Intent intent = new Intent(this, MainScreen.class);
                            //startActivity(intent);
                            //this.finish();
                            // GetDatawithoutprogress();


                        } else {
                            dbhelper.updateRegisterFlag(false, "");
                            showAlertDialog(
                                    this,
                                    "Message",
                                    "Your Device is not registered,please Register!",
                                    false);
                        }
                    } else {
                        showAlertDialog(this, "No Server Connection",
                                "You don't have connection with Server!", false);
                    }
                }

            } else {
                showAlertDialog(this, "Galaxy Restaurant",
                        "Incorrect Password.", false);
            }
        }
    }

    public void showInfo(View view) {
//        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//        String ids = "CafePOS Default : \n" + GlobalClass.GetTabletID() + "\n" +
//                    "\nIMEI : " + tm.getDeviceId() +
//                    "\nIMSI : " + tm.getSubscriberId() +
//                    "\nSim Serial : " + tm.getSimSerialNumber() +
//                    "\nSecure Id : " + Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID) +
//                    "\nUUID : " + UUID.randomUUID().toString();
//
//        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Unique ID").setCancelable(false);
//        builder.setMessage(ids);
//        builder.setPositiveButton("OK", new  DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//            }
//        });
//        final AlertDialog alertDialog = builder.create();
//        alertDialog.show();
//        Intent intent = new Intent(this, VoucherDetailActivity.class);
//        startActivity(intent);
    }

    public void showLocation(View v) {
        locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            OnGPS();
        } else {
            getLocation();
        }
    }

    private void OnGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please Enable GPS").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void getLocation() {
        Location locationGPS = getLastKnownLocation();
        if (locationGPS != null) {
            double lat = locationGPS.getLatitude();
            double longi = locationGPS.getLongitude();
            latitude = String.valueOf(lat);
            longitude = String.valueOf(longi);
            Toast.makeText(this, "Your Location: " + "\n" + "Latitude: " + latitude + "\n" + "Longitude: " + longitude, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Unable to find location!", Toast.LENGTH_SHORT).show();
        }
    }


    private Location getLastKnownLocation() {
        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                //return TODO;
            } else {
                Location l = locationManager.getLastKnownLocation(provider);
                if (l == null) {
                    continue;
                }
                if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                    // Found best last known location: %s", l);
                    bestLocation = l;
                }
            }

        }
        return bestLocation;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
