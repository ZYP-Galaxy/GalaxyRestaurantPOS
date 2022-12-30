package com.galaxy.restaurantpos;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.AlertDialog;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Build;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class GlobalClass {

    public static boolean tmpOffline = false;
    public static boolean tmpLoadData = false;

    //added by ZYP [06-01-2021]
    public static boolean use_foodtruck = false;
    public static String btprinter_name = "RPP300";

    public static int MIFinishedItemscount = 0;

    private static int Currentscreen = 1;

    public static int getCurrentscreen() {
        return Currentscreen;
    }

    public static void setCurrentscreen(int currentscreen) {
        Currentscreen = currentscreen;
    }


    private static Boolean servicestarted = false;

    public static Boolean getServicestarted() {
        return servicestarted;
    }

    public static void setServicestarted(Boolean servicestarted) {
        GlobalClass.servicestarted = servicestarted;
    }

    public static void ChangeLanguage(ViewGroup viewgroup, Context ctx, int fontsize, Typeface font) //modified by EKK on 10/07/2019
    {
        final DatabaseHelper dbhelper = new DatabaseHelper(ctx);

        List<Dictionary> dictlist = new ArrayList<Dictionary>();

        if (dbhelper.getLanguage().equals("language2")) {
            for (int i = 0; i < viewgroup.getChildCount(); i++) {
                View v = (View) viewgroup.getChildAt(i);
                if (v instanceof Button) {
                    dictlist = dbhelper.getMyanmarByEnglish(((Button) v).getText().toString());
                    if (dictlist.size() > 0) {
                        ((Button) v).setText(dictlist.get(0).getLanguage2());
                        ((Button) v).setTextSize(TypedValue.COMPLEX_UNIT_SP, fontsize);
                    }
                    ((Button) v).setTypeface(font);

                } else if (v instanceof TextView) {
                    dictlist = dbhelper.getMyanmarByEnglish(((TextView) v).getText().toString());
                    if (dictlist.size() > 0) {
                        ((TextView) v).setText(dictlist.get(0).getLanguage2());
                        ((TextView) v).setTextSize(TypedValue.COMPLEX_UNIT_SP, fontsize);
                    }
                    ((TextView) v).setTypeface(font);
                } else if (v instanceof EditText) {
                    dictlist = dbhelper.getMyanmarByEnglish(((EditText) v).getText().toString());
                    if (dictlist.size() > 0) {
                        ((EditText) v).setText(dictlist.get(0).getLanguage2());
                        ((EditText) v).setTextSize(TypedValue.COMPLEX_UNIT_SP, fontsize);
                    }
                } else if (v instanceof ViewGroup) {
                    ChangeLanguage((ViewGroup) v, ctx, fontsize, font);

                }
            }

        } else if (dbhelper.getLanguage().equals("language3")) //added by EKK
        {
            for (int i = 0; i < viewgroup.getChildCount(); i++) {
                View v = (View) viewgroup.getChildAt(i);
                if (v instanceof Button) {
                    dictlist = dbhelper.getChineseByEnglish(((Button) v).getText().toString());
                    if (dictlist.size() > 0) {
                        ((Button) v).setText(dictlist.get(0).getLanguage3());
                        ((Button) v).setTextSize(TypedValue.COMPLEX_UNIT_SP, fontsize);
                    }
                    ((Button) v).setTypeface(font);

                } else if (v instanceof TextView) {
                    dictlist = dbhelper.getChineseByEnglish(((TextView) v).getText().toString());
                    if (dictlist.size() > 0) {
                        ((TextView) v).setText(dictlist.get(0).getLanguage3());
                        ((TextView) v).setTextSize(TypedValue.COMPLEX_UNIT_SP, fontsize);
                    }
                    ((TextView) v).setTypeface(font);
                } else if (v instanceof EditText) {
                    dictlist = dbhelper.getChineseByEnglish(((EditText) v).getText().toString());
                    if (dictlist.size() > 0) {
                        ((EditText) v).setText(dictlist.get(0).getLanguage3());
                        ((EditText) v).setTextSize(TypedValue.COMPLEX_UNIT_SP, fontsize);
                    }
                } else if (v instanceof ViewGroup) {
                    ChangeLanguage((ViewGroup) v, ctx, fontsize, font);

                }
            }
        }
    }

    public static int getDPsize(int size, Context ctx) {
        int i = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, size, ctx.getResources().getDisplayMetrics());
        return i;
    }

    public static int getSPsize(int size, Context ctx) {
        int i = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, size, ctx.getResources().getDisplayMetrics());
        return i;
    }

    public static Boolean CheckConnection(Context ctx) {
        //added by WaiWL on 12/06/2015
        //if(new DatabaseHelper(ctx).getOfflineFlag().equals(false))
        //{
        //ConnectionDetector cd = new ConnectionDetector(ctx);
        boolean isInternetPresent = ConnectionDetector.Checkconnection(new DatabaseHelper(ctx).getServiceURL());
        // check for Internet status
        return isInternetPresent;
        //}
        //else
        //	return false;
    }

    public static Boolean CheckConnectionForSubmit(Context ctx) {
        //ConnectionDetector cd = new ConnectionDetector(ctx);
        boolean isInternetPresent = ConnectionDetector.CheckConnectionForSubmit(new DatabaseHelper(ctx).getServiceURL());
        // check for Internet status
        return isInternetPresent;
    }

    public static String GetImageStorageLocation(Context ctx) {
        final String path = ctx.getApplicationInfo().dataDir;
        return path;
    }

    private static Button _SelectedAreaButton;

    public static Button GetSelectedAreaButton() {
        return _SelectedAreaButton;
    }

    public static void SetSelectedAreaButton(Button AreaButton) {
        _SelectedAreaButton = AreaButton;
    }

    public static String Identifier = "";

    public static String GetTabletID() {
        String ID = "35" + //we make this look like a valid IMEI
                Build.BOARD.length() % 10 + Build.BRAND.length() % 10 +
                Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 +
                Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 +
                Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 +
                Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 +
                Build.TAGS.length() % 10 + Build.TYPE.length() % 10 +
                Build.USER.length() % 10 + //Build.SERIAL; //13 digits 0rginal
                Build.SERIAL+Identifier ;//added by KLM to unique device id 25102022
                //((Build.SERIAL.equals("unknown")) ? Identifier : Build.SERIAL); //modified by ZYP [19-06-2020] for Device ID unique
        return ID;
    }


    //region menucategorygroup WHM [2020-09-29] YGN2-200983
    //sale deflocaitonid
    private static int sale_deflocationID;

    public static int getSale_deflocationID() {
        return sale_deflocationID;
    }

    public static void setSale_deflocationID(int sale_deflocationID) {
        GlobalClass.sale_deflocationID = sale_deflocationID;
    }


    //strmenucategorygroup
    public static String strmenucategory_grouptype = "  ";
    public static String strcategoryIn = "";

    public static int use_menucategorygrouptype = 0;


    public static void Bind_MenuCategoryGroup() {
        if (use_menucategorygrouptype == 1 && !strcategoryIn.equals("")) {
            strmenucategory_grouptype = " and category in (" + strcategoryIn + ") ";
        } else if (use_menucategorygrouptype == 2 && !strcategoryIn.equals("")) {
            strmenucategory_grouptype = " and category in (" + strcategoryIn + ") ";
        } else {
            strmenucategory_grouptype = "  ";
        }

    }

    //endregion YGN2-200983

    //region userarea //added WHM [2020-11-11]
    public static String strgetarea_groupbyuser = " ";
    //endregion

    public static void showToast(Context ctx, String message) {
        Toast.makeText(ctx, message, Toast.LENGTH_LONG).show();
    }

    public static void showAlertDialog(Context context, String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", (dialog, which) -> {
        });
        alertDialog.show();
    }

    static ProgressDialog progressDialog;

    public static void showProgressDialog(Context context, String message) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle(R.string.app_name);
        progressDialog.setMessage(message);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public static void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }


}
