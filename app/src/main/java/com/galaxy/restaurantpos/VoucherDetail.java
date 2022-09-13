package com.galaxy.restaurantpos;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class VoucherDetail extends Activity {

    public static Typeface font;
    public static int tranid;
    public static int userid;
    public static int test;

    public static String tableID;//added WHM [2020-01-03]
    public static String show_message;//added WHM [2020-10-19] reservation_advpay

    final Context ctx = this;
    CheckBox chkBillPrint;
    CheckBox chkHideComName; //added by EKK on 13-07-2020

    DatabaseHelper dbhelper;

    String printerName, printerID;
    int printerid;

    String submitflag = "false";

    VoucherDetailObj vouchersummary = new VoucherDetailObj();
    List<SaleItemObj> saleitemobjlist = new ArrayList<SaleItemObj>();
    SaleObj saleobj = new SaleObj();

    // android built in classes for bluetooth operations
    BluetoothAdapter bluetoothAdapter = null;
    BluetoothSocket bluetoothSocket = null;
    BluetoothDevice bluetoothDevice = null;

    // needed for communication to bluetooth device / network
    OutputStream outputStream = null;
    InputStream inputStream = null;
    Thread workerThread = null;
    Bitmap bmp;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (LoginActivity.isUnicode)
            font = Typeface.createFromAsset(getAssets(), "fonts/Pyidaungsu.ttf");
        else
            font = Typeface.createFromAsset(getAssets(), "fonts/Zawgyi-One.ttf");

        // Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_voucher);

        if (android.os.Build.VERSION.SDK_INT > 9) {// for check connection
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Bundle b = this.getIntent().getExtras(); // Get Parameter from table
        // screen
        tranid = Integer.parseInt(b.getString("tranid"));
        userid = Integer.parseInt(b.getString("userid"));
        tableID = b.getString("TableID"); //added WHM [2020-01-03]

        //whm
        //if (new DatabaseHelper(this).getBillPrintFromTablet().equals("Y")) {  //org
        //((Button) findViewById(R.id.butSubmit)).setVisibility(0);

        dbhelper = new DatabaseHelper(ctx);//added WHM [2019-08-12]
        chkHideComName = (CheckBox) findViewById(R.id.chkHideComName);
        Button btnSubmit = (Button) findViewById(R.id.butSubmit);

        if (dbhelper.Allow_btnPrintBill(dbhelper.getwaiterid()) == true) {
            btnSubmit.setVisibility(View.VISIBLE);
            ((Spinner) findViewById(R.id.cashierPrinters)).setVisibility(View.VISIBLE);
            ((CheckBox) findViewById(R.id.chkBillPrint)).setVisibility(View.VISIBLE);

            if (dbhelper.show_billnotprint(dbhelper.getwaiterid()) == true) {         //added by ZYP 05-11-2019
                ((CheckBox) findViewById(R.id.chkBillPrint)).setVisibility(View.VISIBLE);
            } else {
                ((CheckBox) findViewById(R.id.chkBillPrint)).setVisibility(View.INVISIBLE);
            }

        }

        if (dbhelper.use_deliverymanagement() == false) {
            ((LinearLayout) findViewById(R.id.linelayout_delivery)).setVisibility(View.GONE);
            ((TextView) findViewById(R.id.tv_lableDeliveryType)).setVisibility(View.GONE);
            ((TextView) findViewById(R.id.tv_deliverytype_name)).setVisibility(View.GONE);
        }

        binditemdata(tranid);
        GlobalClass.ChangeLanguage((ViewGroup) findViewById(R.id.voulayout),
                this, 18, font);


        // added by TTA on [01-02-2019] for CashierPrinters
        // dbhelper=new DatabaseHelper(ctx);
        Spinner cashierSpinner = (Spinner) findViewById(R.id.cashierPrinters);
        if (GlobalClass.use_foodtruck) {
            String[] names = findBT();
            if (names.length > 0) {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, names);
                cashierSpinner.setAdapter(adapter);

                for (int i = 0; i < names.length; i++) {
                    if (GlobalClass.btprinter_name.equals(names[i])) {
                        cashierSpinner.setSelection(i);
                        break;
                    }
                }

                cashierSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        GlobalClass.btprinter_name = (String) parent.getItemAtPosition(position);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

        } else {

            List<CashierPrinter> printerlist = new ArrayList<CashierPrinter>();
            printerlist = dbhelper.getAllCashierPrinteList();
            String[] names = new String[printerlist.size()];

            int[] printerIds = new int[printerlist.size()];
            if (printerlist.size() > 0) {
                for (int i = 0; i < printerlist.size(); i++) {
                    CashierPrinter p = printerlist.get(i);
                    names[i] = p.get_PrinterName();
                    printerIds[i] = p.get_PrinterID();
                }
            }

            PosUser posuser = dbhelper.getPosUserByUserID(Integer.parseInt(dbhelper.getwaiterid()));
            printerid = posuser.get_cashierprinterid(); // set default printer

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, names);
            cashierSpinner.setAdapter(adapter);

            for (int i = 0; i < printerIds.length; i++) {
                if (printerid == printerIds[i]) {
                    cashierSpinner.setSelection(i);
                    break;
                }
            }

            cashierSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                public void onItemSelected(AdapterView<?> parent, View arg1,
                                           int position, long arg3) {
                    // TODO Auto-generated method stub
                    printerName = (String) parent.getItemAtPosition(position);
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.BLUE);
                }

                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub
                }

            });
        }

        cashierSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View arg1,
                                       int position, long arg3) {
                // TODO Auto-generated method stub
                printerName = (String) parent.getItemAtPosition(position);
                ((TextView) parent.getChildAt(0)).setTextColor(Color.BLUE);
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }

        });

        if (GlobalClass.use_foodtruck) {
            ((LinearLayout) findViewById(R.id.layoutheader1)).setVisibility(View.GONE);
            ((LinearLayout) findViewById(R.id.layoutheader2)).setVisibility(View.GONE);
            ((LinearLayout) findViewById(R.id.roomlayout)).setVisibility(View.GONE);
            ((LinearLayout) findViewById(R.id.memlayout)).setVisibility(View.GONE);
            //((Spinner) findViewById(R.id.cashierPrinters)).setVisibility(View.GONE);

            chkHideComName.setVisibility(View.GONE);
            //cashierSpinner.setVisibility(View.INVISIBLE);
            btnSubmit.setText("Confirm");

            printView();
        }

    }

    public void binditemdata(int tranid) {
        //VoucherDetailObj vouchersummary = new VoucherDetailObj();
        if (GlobalClass.CheckConnection(this)) {
            new DatabaseHelper(this).LoadSaleHeader(
                    new DatabaseHelper(this).getServiceURL(), "",
                    Integer.toString(tranid));
            // commented and added by WaiWL on 09/07/2015
            // new DatabaseHelper(this).LoadSaleItem(new
            // DatabaseHelper(this).getServiceURL(), Integer.toString(tranid));
            new DatabaseHelper(this).LoadSaleItemForVoucher(new DatabaseHelper(
                    this).getServiceURL(), Integer.toString(tranid));
            // ///////
            vouchersummary = new DatabaseHelper(this).LoadSaleSummary(
                    new DatabaseHelper(this).getServiceURL(),
                    Integer.toString(tranid));
        } else {

        }
        final TextView txtinvoiceno = (TextView) findViewById(R.id.txtinvoiceno);
        final TextView txttableno = (TextView) findViewById(R.id.txttableno);
        final TextView txtcustomer = (TextView) findViewById(R.id.txtCustomer);
        final TextView txtgtax = (TextView) findViewById(R.id.txtgtax);
        final TextView txtstax = (TextView) findViewById(R.id.txtstax);
        final TextView txtrtax = (TextView) findViewById(R.id.txtrtax);
        // Added by Arkar Moe on [20/07/2016] adding Room Tax in Tablet Voucher [Res-0209]
        //added WHM [2020-05-14]
        final TextView tv_lableDeliveryType = (TextView) findViewById(R.id.tv_lableDeliveryType);
        final TextView tv_deliverytype_name = (TextView) findViewById(R.id.tv_deliverytype_name);

        final TextView txtdiscount = (TextView) findViewById(R.id.txtdiscount);
        final TextView txtmemdiscount = (TextView) findViewById(R.id.txtMemDiscount);
        final TextView txtmemamount = (TextView) findViewById(R.id.txtMemAmount);
        final TextView txtnettotal = (TextView) findViewById(R.id.txtNetTotal);
        final TextView txtfocamount = (TextView) findViewById(R.id.txtfocamount);
        final TextView txtitemdiscount = (TextView) findViewById(R.id.txtitemdiscount);
        chkBillPrint = (CheckBox) findViewById(R.id.chkBillPrint);
        final TextView txtdeliverycharges = (TextView) findViewById(R.id.tv_deliverycharges_amt);//added WHM [2020-05-07]
        //chkHideComName = (CheckBox) findViewById(R.id.chkHideComName); //added by EKK on 13-07-2020


        if (vouchersummary.getgtaxamount().equals(""))
            vouchersummary.setgtaxamount("0");
        if (vouchersummary.getstaxamount().equals(""))
            vouchersummary.setstaxamount("0");

        if (vouchersummary.getrtaxamount().equals("")) // Added by
            // Arkar Moe
            // on
            // [20/07/2016]
            // for
            // adding
            // Room Tax
            // in Tablet
            // Voucher
            // [Res-0209]
            vouchersummary.setrtaxamount("0");

        if (vouchersummary.getdiscount().equals(""))
            vouchersummary.setdiscount("0");
        if (vouchersummary.getmemamount().equals(""))
            vouchersummary.setmemamount("0");
        if (vouchersummary.getnetamount().equals(""))
            vouchersummary.setnetamount("0");
        if (vouchersummary.getfocamount().equals(""))
            vouchersummary.setfocamount("0");

        if (vouchersummary.getDelivery_charges().equals(""))//added WHM [2020-05-07]
            vouchersummary.setDelivery_charges("0");

        // txtgtax.setText(Double.toString(Double.parseDouble(vouchersummary.getgtaxamount())).replace(".0",
        // "")); //Commented by Arkar Moe [19/09/2016]
        // txtstax.setText(Double.toString(Double.parseDouble(vouchersummary.getstaxamount())).replace(".0",
        // ""));


        txtgtax.setText(dbhelper.getCurrencyFormat(Double.toString(
                Double.parseDouble(vouchersummary.getgtaxamount())).replace(
                ".0", "")));
        txtstax.setText(dbhelper.getCurrencyFormat(Double.toString(
                Double.parseDouble(vouchersummary.getstaxamount())).replace(
                ".0", "")));

        txtrtax.setText(dbhelper.getCurrencyFormat(Double.toString(
                Double.parseDouble(vouchersummary.getrtaxamount())).replace(
                ".0", ""))); // Added by Arkar Moe on [20/07/2016] for adding
        // Room Tax in Tablet Voucher [Res-0209]

        txtdiscount.setText(dbhelper.getCurrencyFormat(Double.toString(
                Double.parseDouble(vouchersummary.getdiscount())).replace(".0",
                "")));
        if (Double.parseDouble(vouchersummary.getMemdiscount()) > 0.0) {
            txtmemdiscount.setText("Mem.Dis  (" + vouchersummary.getMemdiscount().split("\\.")[0] + " %) :");
        }
        txtmemamount.setText(dbhelper.getCurrencyFormat(Double.toString(
                Double.parseDouble(vouchersummary.getmemamount())).replace(
                ".0", "")));
        txtnettotal.setText(dbhelper.getCurrencyFormat(Double.toString(
                Double.parseDouble(vouchersummary.getnetamount())).replace(
                ".0", "")));
        txtfocamount.setText(dbhelper.getCurrencyFormat(Double.toString(
                Double.parseDouble(vouchersummary.getfocamount())).replace(
                ".0", "")));
        txtitemdiscount.setText(dbhelper.getCurrencyFormat(Double.toString(
                Double.parseDouble(vouchersummary.getitemdiscount())).replace(
                ".0", "")));

        //added WHM [2020-05-07]
        txtdeliverycharges.setText(dbhelper.getCurrencyFormat(Double.toString(
                Double.parseDouble(vouchersummary.getDelivery_charges())).replace(
                ".0", "")));

        final DatabaseHelper dbhelper = new DatabaseHelper(this);

        saleobj = dbhelper.getSaledataBySaleID(tranid);
        txtinvoiceno.setText(saleobj.getRef_no());
        txtinvoiceno.setTag(tranid);

        if (GlobalClass.use_foodtruck) {
            txttableno.setText(tableID);

        } else {
            //txttableno.setText(dbhelper.getTableNameByTableID(Integer.parseInt(saleobj.gettablenameid())));
            txttableno.setText(dbhelper.getTableNameByTableID(Integer.parseInt(saleobj.gettablenameid()))); //added by MPPA [12-07-2021]

        }

        //txttableno.setText(dbhelper.getTableNameByTableID(Integer.parseInt(saleobj.gettablenameid())));
        txtcustomer.setText(dbhelper.getCustomerNameByCustomerID(saleobj.getcustomerid()));
        //added WHM [2020-05-14]

        String customerid = saleobj.getcustomerid();
        txtcustomer.setText(dbhelper.getCustomerNameByCustomerID(customerid)); //added by MPPA [12-07-2021]

        String deliver_name = saleobj.getDelivery_type() == 0 ? "Normal" :
                (saleobj.getDelivery_type() == 1 ? "Delivery" : "Self Pickup");
        tv_deliverytype_name.setText(deliver_name);

        //List<SaleItemObj> saleitemobjlist = new ArrayList<SaleItemObj>();
        saleitemobjlist = dbhelper.getSaleItemdataBySaleID(tranid);

        final LayoutParams tablerowlayout = new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        final LayoutParams itemmainlayoutpara = new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        itemmainlayoutpara.setMargins(0, 0, 0, 5);
        // int width = (int)
        // TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40,
        // getResources().getDisplayMetrics());
        final LayoutParams srlayoutpara = new LayoutParams(
                GlobalClass.getDPsize(50, this), LayoutParams.WRAP_CONTENT);

        final LayoutParams namelayoutpara = new LayoutParams(
                GlobalClass.getDPsize(200, this), LayoutParams.WRAP_CONTENT);

        final LayoutParams pricelayoutpara = new LayoutParams(
                GlobalClass.getDPsize(100, this), LayoutParams.WRAP_CONTENT);

        final LayoutParams qtylayoutpara = new LayoutParams(
                GlobalClass.getDPsize(50, this), LayoutParams.WRAP_CONTENT);

        final LayoutParams amountlayoutpara = new LayoutParams(
                GlobalClass.getDPsize(100, this), LayoutParams.WRAP_CONTENT);

        final LinearLayout tbllayout = (LinearLayout) findViewById(R.id.itemlayout);

        final LayoutParams dividerlayout = new LayoutParams(LayoutParams.MATCH_PARENT, 1);
        tbllayout.removeAllViews();
        Double totalamount = 0.0;
        Double grandtotal = 0.0;

        TextView txtline = new TextView(this);
        txtline.setBackgroundColor(Color.parseColor("#FFFFFF"));
        tbllayout.addView(txtline, dividerlayout);

        for (SaleItemObj saleItemObj : saleitemobjlist) {
            totalamount = 0.0;
            LinearLayout itemmainlayout = new LinearLayout(this);
            itemmainlayout.setOrientation(LinearLayout.VERTICAL);

            LinearLayout tr = new LinearLayout(this);

            TextView txtsr = new TextView(this);
            txtsr.setText(saleItemObj.getsrno());
            txtsr.setVisibility(View.INVISIBLE);// added by WaiWL on 09/07/2015
            // ///////////////
            // txtsr.setTextColor(Color.parseColor("#FFFFFF"));

            TextView txtname = new TextView(this);
            txtname.setText(dbhelper.getItemNamebyitemid(saleItemObj.getcode()));
            txtname.setTypeface(font);
            // txtname.setTextColor(Color.parseColor("#FFFFFF"));

            TextView txtprice = new TextView(this);
            //txtprice.setText(saleItemObj.getprice().replace(".0", ""));
            txtprice.setText(dbhelper.getCurrencyFormat(saleItemObj.getprice()));
            txtprice.setGravity(Gravity.RIGHT);
            // txtprice.setTextColor(Color.parseColor("#FFFFFF"));

            TextView txtqty = new TextView(this);
            txtqty.setText(saleItemObj.getqty().replace(".0", ""));
            txtqty.setGravity(Gravity.RIGHT);
            // txtqty.setTextColor(Color.parseColor("#FFFFFF"));

            String unitname = "";
            if (dbhelper.getuse_unit().equals("true")) {
                List<UnitCodeObj> unitcodeobjlist = dbhelper
                        .getunitcodebyunittype(saleItemObj.getcode(),
                                saleItemObj.getunittype());

                if (unitcodeobjlist.size() > 0) {
                    unitname = unitcodeobjlist.get(0).getunitname();

                }
            }

            TextView txtunit = new TextView(this);
            txtunit.setText(unitname);
            txtunit.setGravity(Gravity.LEFT);
            // txtunit.setTextColor(Color.parseColor("#FFFFFF"));

            TextView txtamount = new TextView(this);
            //txtamount.setText(saleItemObj.getamount().replace(".0", ""));
            txtamount.setText(dbhelper.getCurrencyFormat(saleItemObj.getamount()));
            txtamount.setTag(saleItemObj.getamount());
            txtamount.setGravity(Gravity.LEFT);
            // txtamount.setTextColor(Color.parseColor("#FFFFFF"));

            TextView txttotalamount = new TextView(this);
            txttotalamount.setGravity(Gravity.RIGHT);
            // txttotalamount.setTextColor(Color.parseColor("#FFFFFF"));

            totalamount = totalamount
                    + Double.parseDouble(txtamount.getTag().toString());

            if (saleItemObj.getcancelflag().equals(true)) {
                txtsr.setPaintFlags(txtsr.getPaintFlags()
                        | Paint.STRIKE_THRU_TEXT_FLAG);
                txtname.setPaintFlags(txtname.getPaintFlags()
                        | Paint.STRIKE_THRU_TEXT_FLAG);
                txtprice.setPaintFlags(txtprice.getPaintFlags()
                        | Paint.STRIKE_THRU_TEXT_FLAG);
                txtqty.setPaintFlags(txtqty.getPaintFlags()
                        | Paint.STRIKE_THRU_TEXT_FLAG);
                txtamount.setPaintFlags(txtamount.getPaintFlags()
                        | Paint.STRIKE_THRU_TEXT_FLAG);
                txttotalamount.setPaintFlags(txttotalamount.getPaintFlags()
                        | Paint.STRIKE_THRU_TEXT_FLAG);
            }
            tr.addView(txtsr, srlayoutpara);
            tr.addView(txtname, namelayoutpara);
            tr.addView(txtprice, pricelayoutpara);
            tr.addView(txtqty, qtylayoutpara);
            tr.addView(txtunit, qtylayoutpara);
            // tr.addView(txtamount , amountlayoutpara);
            tr.addView(txttotalamount, amountlayoutpara);
            itemmainlayout.addView(tr, tablerowlayout);

            List<SelectedItemModifierObj> modifierobjlist = dbhelper
                    .getSaleItemModifierdataBySaleID(Integer.toString(tranid),
                            saleItemObj.getsr());

            for (SelectedItemModifierObj selectedItemModifierObj : modifierobjlist) {
                LinearLayout modifiertr = new LinearLayout(this);
                TextView txtmodifiersr = new TextView(this);
                txtmodifiersr.setText("");

                TextView txtmodifiername = new TextView(this);
                txtmodifiername.setText(" * " + selectedItemModifierObj.getname());
                txtmodifiername.setTextColor(Color.BLUE);
                txtmodifiername.setTypeface(font);

                ItemsObj itemobj = dbhelper.getItemsbyitemid(saleItemObj
                        .getcode());
                if (Boolean.parseBoolean(itemobj.getissetmenu())) {
                    selectedItemModifierObj.setprice("0.00");
                    selectedItemModifierObj.setamount("0.00");
                }
                TextView txtmodifierprice = new TextView(this);
                txtmodifierprice.setText(selectedItemModifierObj.getprice()
                        .replace(".0", ""));
                txtmodifierprice.setGravity(Gravity.RIGHT);
                txtmodifierprice.setTextColor(Color.BLUE);

                TextView txtmodifierqty = new TextView(this);
                txtmodifierqty.setText(selectedItemModifierObj.getqty()
                        .replace(".0", ""));
                txtmodifierqty.setGravity(Gravity.RIGHT);
                txtmodifierqty.setTextColor(Color.BLUE);

                TextView txtmodifieramount = new TextView(this);
                //txtmodifieramount.setText(selectedItemModifierObj.getamount().replace(".0", ""));
                txtmodifieramount.setText(dbhelper.getCurrencyFormat(selectedItemModifierObj.getamount()));
                txtmodifieramount.setTag(selectedItemModifierObj.getamount());
                txtmodifieramount.setGravity(Gravity.LEFT);
                txtmodifieramount.setTextColor(Color.BLUE);

                totalamount = totalamount
                        + Double.parseDouble(txtmodifieramount.getTag()
                        .toString());

                modifiertr.addView(txtmodifiersr, srlayoutpara);
                modifiertr.addView(txtmodifiername, namelayoutpara);
                modifiertr.addView(txtmodifierprice, pricelayoutpara);
                modifiertr.addView(txtmodifierqty, qtylayoutpara);

                if (Double.parseDouble(selectedItemModifierObj.getprice()) > 0.0) {

                } else
                    modifiertr.setVisibility(View.GONE);

                itemmainlayout.addView(modifiertr, tablerowlayout);
            }

            //txttotalamount.setText(Double.toString(totalamount).replace(".0",""));
            txttotalamount.setText(dbhelper.getCurrencyFormat(Double.toHexString(totalamount)));

            if (!saleItemObj.getcancelflag().equals(true)) {
                grandtotal = grandtotal + totalamount;
            }

            tbllayout.addView(itemmainlayout, itemmainlayoutpara);
        }

        TextView txtlastline = new TextView(this);
        txtlastline.setBackgroundColor(Color.parseColor("#000000"));
        tbllayout.addView(txtlastline, dividerlayout);

        LinearLayout tr = new LinearLayout(this);

        TextView txtsr = new TextView(this);
        txtsr.setText("");
        txtsr.setVisibility(View.INVISIBLE);// added by WaiWL on 09/07/2015

        // txtsr.setTextColor(Color.parseColor("#FFFFFF"));

        TextView txtname = new TextView(this);
        txtname.setText("Total Amount");
        // txtname.setTextColor(Color.parseColor("#FFFFFF"));

        TextView txtprice = new TextView(this);
        txtprice.setText("");
        txtprice.setGravity(Gravity.RIGHT);
        // txtprice.setTextColor(Color.parseColor("#FFFFFF"));

        TextView txtqty = new TextView(this);
        txtqty.setText("");
        txtqty.setGravity(Gravity.RIGHT);
        // txtqty.setTextColor(Color.parseColor("#FFFFFF"));

        TextView txtunit = new TextView(this);
        txtunit.setText("");
        txtunit.setGravity(Gravity.RIGHT);
        // txtunit.setTextColor(Color.parseColor("#FFFFFF"));

        TextView txtamount = new TextView(this);
        txtamount.setText("");
        txtamount.setGravity(Gravity.RIGHT);
        // txtamount.setTextColor(Color.parseColor("#FFFFFF"));

        TextView txttotalamount = new TextView(this);
        //txttotalamount.setText(Double.toString(grandtotal).replace(".0", ""));
        txttotalamount.setText(dbhelper.getCurrencyFormat(Double.toHexString(grandtotal)));
        txttotalamount.setGravity(Gravity.RIGHT);
        txttotalamount.setTypeface(txttotalamount.getTypeface(), Typeface.BOLD);
        // txttotalamount.setTextColor(Color.parseColor("#FFFFFF"));

        tr.addView(txtsr, srlayoutpara);
        tr.addView(txtname, namelayoutpara);
        tr.addView(txtprice, pricelayoutpara);
        tr.addView(txtqty, qtylayoutpara);
        tr.addView(txtunit, qtylayoutpara);
        // tr.addView(txtamount , amountlayoutpara);
        tr.addView(txttotalamount, amountlayoutpara);
        tbllayout.addView(tr, tablerowlayout);


    }

    public void butSubmit_click(View v) throws NumberFormatException,
            JSONException {

        //added by ZYP 18-02-2021 for bt print
        if (GlobalClass.use_foodtruck) {
            FrameLayout frameLayout = (FrameLayout) findViewById(R.id.frame);
            View linearLayout = findViewById(R.id.root_view);
            bmp = createBitmapFromView(linearLayout, 500, 2000);
            frameLayout.setVisibility(View.GONE);
        }


        final ProgressDialog progressBar; // Progress
        final Handler progressBarHandler = new Handler();
        // prepare for a progress bar dialog
        progressBar = new ProgressDialog(ctx);
        progressBar.setCancelable(false);
        progressBar.setMessage("Please Wait ...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        /*
         * progressBar.setButton("Close", new DialogInterface.OnClickListener()
         * { public void onClick(DialogInterface dialog, int which) {
         * progressBar.dismiss(); } });
         */
        progressBar.show();
        // reset progress bar status
        try {
            Thread t = new Thread() {
                public void run() {
                    if (GlobalClass.tmpOffline && GlobalClass.use_foodtruck) {
                        try {

                            progressBarHandler.post(new Runnable() {
                                public void run() {
                                    if (dbhelper.ConfirmSaledata(tranid)) {
                                        submitflag = "true";
                                        progressBar.dismiss();
                                        showAlertDialog(ctx, "Success",
                                                "Confirm Successful!", false);
                                    } else {
                                        showAlertDialog(ctx, "Message",
                                                "Error occur while saving!", false);
                                    }
                                }
                            });

                        } catch (Exception ex) {
                            progressBar.dismiss();
                            showAlertDialog(ctx, "Message",
                                    "Error occur while saving!", false);
                        }

                    } else {
                        try {
                            Json_class jsonclass = new Json_class();
                            // String URL = ( new
                            // DatabaseHelper(this).getServiceURL()+
                            // "/Data/dbhelper_Entry?orderdata="+java.net.URLEncoder.encode(salejsonarray.toString())+"&orderitemdata="+java.net.URLEncoder.encode(saleitemjsonarray.toString()));
                            int checkBillPrint;
                            if (chkBillPrint.isChecked()) {
                                checkBillPrint = 1;
                            } else {
                                checkBillPrint = 0;
                            }

                            //added by EKK on 13-07-2020
                            int hideComName;
                            if (chkHideComName.isChecked()) {
                                hideComName = 1;
                            } else {
                                hideComName = 0;
                            }

                            if (GlobalClass.use_foodtruck) {
                                printerID = "0";
                            } else {

                                printerID = dbhelper.getPrinterIDByName(printerName); // added by TTA for cashier printer
                            }

//						JSONArray jsonmessage = jsonclass
//								.getJson(new DatabaseHelper(ctx)
//										.getServiceURL()
//										+ "/Data/voucher_confirm?tranid="
//										+ java.net.URLEncoder.encode(Integer
//												.toString(tranid))
//										+ "&userid="
//										+ java.net.URLEncoder.encode(Integer
//												.toString(userid))
//										+ "&checkBillPrint="
//										+ java.net.URLEncoder.encode(Integer
//												.toString(checkBillPrint)));
//
//						if (jsonmessage.length() > 0) {
//							if (Integer.parseInt(jsonmessage.get(0).toString()) == 0) {
//								test = 1;
//							} else if (Integer.parseInt(jsonmessage.get(0)
//									.toString()) == 2) {
//								test = 2;
//							} else {
//								test = 0;
//							}
//						}

                            // comment by TTA for cashier printer

                            //modified by EKK on 12-12-2019
                            JSONArray jsonmessage = jsonclass.getJson(new DatabaseHelper(ctx).getServiceURL() + "/Data/voucher_confirm?tranid=" + java.net.URLEncoder.encode(Integer.toString(tranid)) + "&userid=" + java.net.URLEncoder.encode(dbhelper.getwaiterid()) + "&checkBillPrint=" + java.net.URLEncoder.encode(Integer.toString(checkBillPrint)) + "&cashierPrinterID=" + java.net.URLEncoder.encode(printerID) + "&hideComName=" + java.net.URLEncoder.encode(Integer.toString(hideComName))); //modified by EKK on 13-07-2020
                            {
                                if (Integer.parseInt(jsonmessage.get(0).toString()) == 0) {
                                    test = 1;
                                } else if (Integer.parseInt(jsonmessage.get(0).toString()) == 2) {
                                    test = 2;
                                }
                                //added WHM [2020-10-19] //reservation_advpay
                                else if (Integer.parseInt(jsonmessage.get(0).toString()) == 5) {
                                    test = 5;
                                    show_message = jsonmessage.get(1).toString();
                                } else {
                                    test = 0;
                                }
                            }
                            if (test == 1 && GlobalClass.use_foodtruck && checkBillPrint == 0) {

//								findBTDevice();
//								try {
//									connectBTDevice();
//								} catch (IOException e) {
//									e.printStackTrace();
//								}
//								if (bluetoothSocket != null && bluetoothSocket.isConnected()) {
//									PrinterUtil printerUtil = new PrinterUtil(bluetoothDevice);
//									byte[] command = printerUtil.getPrintImage(bmp);
//									if (command != null) {
//										printUnicode(command);
//
//									}
//									closeSocket();
//								}

                                Printama printama = new Printama(VoucherDetail.this, GlobalClass.btprinter_name);
                                printama.connect(new Printama.OnConnected() {
                                    @Override
                                    public void onConnected(Printama printama) {
                                        printama.printImage(bmp);
                                    }
                                }, new Printama.OnFailed() {
                                    @Override
                                    public void onFailed(String message) {
                                        Toast.makeText(VoucherDetail.this, "Print fail!", Toast.LENGTH_SHORT).show();
                                        printama.close();
                                    }
                                });

                            }
                            progressBarHandler.post(new Runnable() {
                                public void run() {
                                    progressBar.dismiss();

                                    if (test == 1) {
                                        showAlertDialog(ctx, "Success", "Print Successful!", false);

                                    } else if (test == 2) {
                                        showAlertDialog(ctx, "Success", "This Table is handled by another user",
                                                false);
                                    }
                                    //added WHM [2020-10-19]
                                    else if (test == 5) {
                                        showAlertDialog(ctx, "Message", show_message, false);
                                    } else {
                                        showAlertDialog(ctx, "Message", "Some Error occour!", false);
                                    }

                                }
                            });
                        } catch (NumberFormatException e) {
                        } catch (JSONException e) {
                        } finally {
                        }
                    }
                }
            };
            t.start();// start thread
        } catch (Exception ex) {

        } finally {
        }

    }

    public void printVoucher(Bitmap bitmap) {

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

        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        final Context ctx = this;
        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (title == "Success") {
                    if (GlobalClass.use_foodtruck) {

                        Intent intent = new Intent(VoucherDetail.this, OrderTaking.class);
                        Bundle b = new Bundle();
                        b.putString("TableID", tableID);
                        b.putString("SaleID", "0");
                        b.putString("SubmitFlag", submitflag);
                        intent.putExtras(b);
                        startActivity(intent);

                    } else {
                        Bundle conData = new Bundle();
                        conData.putString("param_result", "Thanks Thanks");
                        Intent intent = new Intent();
                        intent.putExtras(conData);
                        setResult(RESULT_OK, intent);

                    }
                } else {

                }
                finish();
            }
        });
        // Showing Alert Message
        alertDialog.show();

        TextView textView = (TextView) alertDialog.findViewById(android.R.id.message);
        textView.setTypeface(font);
    }

    //added WHM [2020-01-03]
    //@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        //table screen
//		Intent intent = new Intent(ctx,
//				TableScreenActivity.class);
//		startActivity(intent);
//		if (GlobalClass.CheckConnection(ctx)) {
//			Json_class jsonclass = new Json_class();
//			jsonclass.getString(dbhelper
//					.getServiceURL()
//					+ "/Data/ReleaseTable?tableid="
//					+ java.net.URLEncoder
//					.encode(tableID.toString())
//					+ "&userid="
//					+ java.net.URLEncoder
//					.encode(dbhelper
//							.getwaiterid()));
//		}
//		finish();

        if (GlobalClass.use_foodtruck) {
            Intent intent = new Intent(ctx, OrderTaking.class);
            Bundle b = new Bundle();
            b.putString("TableID", tableID);
            b.putString("SaleID", Integer.toString(tranid));
            b.putString("SubmitFlag", submitflag);
            intent.putExtras(b);
            startActivityForResult(intent, 100);
            finish();

        } else {
            //dbhelper
            //modified by EKK on 10-01-2020
            String UserID = dbhelper.getwaiterid();
            boolean checkprintbill = dbhelper.CheckPrintBill(
                    new DatabaseHelper(this).getServiceURL(),
                    Integer.toString(tranid));
            boolean allowedit_afterprint = dbhelper.Allow_edit_after_insert(UserID);


            if ((allowedit_afterprint == true) ||
                    (allowedit_afterprint == false && checkprintbill == false)) {
                Intent intent = new Intent(ctx, OrderTaking.class);
                Bundle b = new Bundle();
                b.putString("TableID", tableID);
                b.putString("SaleID", Integer.toString(tranid));
                intent.putExtras(b);
                startActivityForResult(intent, 100);
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
    //end whm

    public void printView() {
        //View view = findViewById(R.id.root_view);
        FrameLayout frame = (FrameLayout) findViewById(R.id.frame);
        LinearLayout rootView = (LinearLayout) findViewById(R.id.root_view);
        frame.setVisibility(View.VISIBLE);
        ((TextView) findViewById(R.id.btn_test_printer)).setVisibility(View.VISIBLE);
//       LinearLayout listLayout=findViewById(R.id.list_layout);
        //  rootView.setVisibility(View.VISIBLE);
//      listLayout.setVisibility(View.GONE);
/*
       	LinearLayout liner=new LinearLayout(this);

        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        AlertDialog.Builder change = new AlertDialog.Builder(sale_entry.this);
        change.setCancelable(false);
        View bluttoothvoucher = getLayoutInflater().inflate(R.layout.bluttoothvoucherprint,null);
        liner.setMinimumWidth(250);
        liner.addView(bluttoothvoucher,params);
        change.setView(liner);
        salechange = change.create();
        salechange.show();

 */

/*		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		LayoutInflater inflater = this.getLayoutInflater();
		View resultView = inflater.inflate(R.layout.bluetoothvoucherprint, null);
		builder.setView(resultView);

		final Dialog dialog = builder.create();
		WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
		wmlp.gravity = Gravity.CENTER;

		Window window = dialog.getWindow();
		window.setLayout(GlobalClass.getDPsize(250, this),
				LinearLayout.LayoutParams.MATCH_PARENT);

		if (!dialog.isShowing()) {
			dialog.show();
		}*/

        //View voucher_view = getLayoutInflater().inflate(R.layout.bluetoothvoucherprint, null);

        TextView custname = (TextView) findViewById(R.id.txtcustomer);
        TextView tvdate = (TextView) findViewById(R.id.txtdate);
        TextView tvinvoice = (TextView) findViewById(R.id.txtinvoice);
        TextView tvtotalamount = (TextView) findViewById(R.id.txttotalamount);
        TextView tvtotaldiscount = (TextView) findViewById(R.id.txttotaldisamount);
        TextView tvtotalfocamount = (TextView) findViewById(R.id.txttotalfocamount);
        TextView tvgtaxamount = (TextView) findViewById(R.id.txt_gtax_amount);
        TextView txtgtaxpercent = (TextView) findViewById(R.id.txt_gtax_percent);
        TextView tvstaxamount = (TextView) findViewById(R.id.txt_stax_amount);
        TextView txtstaxpercent = (TextView) findViewById(R.id.txt_stax_percent);
        TextView tvtotalnetamount = (TextView) findViewById(R.id.txtnetamount);

        TextView tvcompanyname = (TextView) findViewById(R.id.txtcompanyname);
        TextView tvheader1 = (TextView) findViewById(R.id.txtheadertitle1);
        TextView tvheader2 = (TextView) findViewById(R.id.txtheadertitle2);
        TextView tvheader3 = (TextView) findViewById(R.id.txtheadertitle3);
        TextView tvfooter1 = (TextView) findViewById(R.id.txtfootertitle1);
        TextView tvfooter2 = (TextView) findViewById(R.id.txtfootertitle2);
        TextView tvfooter3 = (TextView) findViewById(R.id.txtfootertitle3);

//		LinearLayout tax_layout = (LinearLayout) dialog.findViewById(R.id.layout_tax);
//		if (frmlogin.isusetax == 0) {
//			tax_layout.setVisibility(View.GONE);
//		}
//		if (sh.get(0).getTax_per() > 0) {
//			txttaxpercent.setText("Tax(" + CurrencyFormat(sh.get(0).getTax_per()) + "%)");
//		}

        //Title
        List<String> headerfooter = dbhelper.RecipeHeaderFooter();

        tvcompanyname.setText(dbhelper.get_title());
        tvheader1.setText(headerfooter.get(0));
        tvheader2.setText(headerfooter.get(1));
        tvheader3.setText(headerfooter.get(2));
        tvfooter1.setText(headerfooter.get(3));
        tvfooter2.setText(headerfooter.get(4));
        tvfooter3.setText(headerfooter.get(5));

        if (tvheader1.getText().toString().equals("null"))
            tvheader1.setVisibility(View.GONE);
        else
            tvheader1.setVisibility(View.VISIBLE);

        if (tvheader2.getText().toString().equals("null"))
            tvheader2.setVisibility(View.GONE);
        else
            tvheader2.setVisibility(View.VISIBLE);

        if (tvheader3.getText().toString().equals("null"))
            tvheader3.setVisibility(View.GONE);
        else
            tvheader3.setVisibility(View.VISIBLE);

        if (tvfooter1.getText().toString().equals("null"))
            tvfooter1.setVisibility(View.INVISIBLE);
        else
            tvfooter1.setVisibility(View.VISIBLE);

        if (tvfooter2.getText().toString().equals("null"))
            tvfooter2.setVisibility(View.INVISIBLE);
        else
            tvfooter2.setVisibility(View.VISIBLE);

        if (tvfooter3.getText().toString().equals("null"))
            tvfooter3.setVisibility(View.INVISIBLE);
        else
            tvfooter3.setVisibility(View.VISIBLE);

        ((TextView) findViewById(R.id.txtfootertitle4)).setVisibility(View.INVISIBLE);
        ((TextView) findViewById(R.id.txtfootertitle5)).setVisibility(View.INVISIBLE);
//		((TextView) findViewById(R.id.txtfootertitle6)).setVisibility(View.INVISIBLE);
//		((TextView) findViewById(R.id.txtfootertitle7)).setVisibility(View.INVISIBLE);
//		((TextView) findViewById(R.id.txtfootertitle8)).setVisibility(View.INVISIBLE);
//		((TextView) findViewById(R.id.txtfootertitle9)).setVisibility(View.INVISIBLE);
//		((TextView) findViewById(R.id.txtfootertitle0)).setVisibility(View.INVISIBLE);

        //Header
        custname.setText("");
        tvdate.setText(vouchersummary.getdate());
        tvinvoice.setText(saleobj.getinvoiceno());

        //Detail
        String text = "";
        String stamount = null;
        String qtyprice = null;
        String item = null;
        double amt = 0.0;
        LinearLayout detailLayout = (LinearLayout) findViewById(R.id.detail);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        for (SaleItemObj saleItemObj : saleitemobjlist) {
            Double totalamount = 0.0;
            View voucheritem = this.getLayoutInflater().inflate(R.layout.voucher_item, null);
            TextView tvdescription = (TextView) voucheritem.findViewById(R.id.txtDescription);
            TextView tvqty = (TextView) voucheritem.findViewById(R.id.txtQty);
            TextView tvprice = (TextView) voucheritem.findViewById(R.id.txtPrice);
            TextView tvamount = (TextView) voucheritem.findViewById(R.id.txtAmount);

//			item = sale_entry.sd.get(i).getDesc();
//			amt = sale_entry.sd.get(i).getUnit_qty() * sale_entry.sd.get(i).getSale_price();
//			int len = item.length();
//			if (len > 20) {
//				item = item.substring(0, 20);
//			}
//			stamount = CurrencyFormat(amt);
//			qtyprice = "(" + CurrencyFormat(sale_entry.sd.get(i).getUnit_qty()) + "  " + sale_entry.sd.get(i).getUnit_short() + "x"
//					+ CurrencyFormat(sale_entry.sd.get(i).getSale_price()) + ")";

            String unitname = "";
            if (dbhelper.getuse_unit().equals("true")) {
                List<UnitCodeObj> unitcodeobjlist = dbhelper.getunitcodebyunittype(saleItemObj.getcode(),
                        saleItemObj.getunittype());

                if (unitcodeobjlist.size() > 0) {
                    unitname = unitcodeobjlist.get(0).getunitname();

                }
            }

            tvdescription.setText(dbhelper.getItemNamebyitemid(saleItemObj.getcode()));
            tvqty.setText(String.format("%s %s", saleItemObj.getqty().replace(".0", ""), unitname));
            tvprice.setText(dbhelper.getCurrencyFormat(saleItemObj.getprice()));
            tvamount.setText(dbhelper.getCurrencyFormat(saleItemObj.getamount()));
            if (!saleItemObj.getcancelflag())
                detailLayout.addView(voucheritem);

            totalamount += Double.parseDouble(saleItemObj.getamount());

            List<SelectedItemModifierObj> modifierobjlist = dbhelper
                    .getSaleItemModifierdataBySaleID(Integer.toString(tranid), saleItemObj.getsr());

            for (SelectedItemModifierObj selectedItemModifierObj : modifierobjlist) {
                View vouchermodifyitem = this.getLayoutInflater().inflate(R.layout.voucher_item, null);
                TextView tvmodifydescription = (TextView) vouchermodifyitem.findViewById(R.id.txtDescription);
                TextView tvmodifyqty = (TextView) vouchermodifyitem.findViewById(R.id.txtQty);
                TextView tvmodifyprice = (TextView) vouchermodifyitem.findViewById(R.id.txtPrice);
                TextView tvmodifyamount = (TextView) vouchermodifyitem.findViewById(R.id.txtAmount);

                tvmodifydescription.setText(String.format(" * %s", selectedItemModifierObj.getname()));
                tvmodifyqty.setText(selectedItemModifierObj.getqty().replace(".0", ""));
                tvmodifyprice.setText(dbhelper.getCurrencyFormat(selectedItemModifierObj.getprice()));
                tvmodifyamount.setText("");

                if (!saleItemObj.getcancelflag())
                    detailLayout.addView(vouchermodifyitem);

                totalamount += Double.parseDouble(selectedItemModifierObj.getamount());

            }

            tvamount.setText(dbhelper.getCurrencyFormat(String.valueOf(totalamount)));

        }

        //Summary


        String TotalAmount = dbhelper.getCurrencyFormat(vouchersummary.getinvoice_amount());
        String DisAmount = dbhelper.getCurrencyFormat(vouchersummary.getdiscount());
        String FocAmount = dbhelper.getCurrencyFormat(vouchersummary.getfocamount());
        String NetAmount = dbhelper.getCurrencyFormat(vouchersummary.getnetamount());

        tvtotalamount.setText(TotalAmount);
        tvtotaldiscount.setText(DisAmount);
        tvtotalfocamount.setText(FocAmount);
        tvtotalnetamount.setText(NetAmount);
        tvgtaxamount.setText(dbhelper.getCurrencyFormat(vouchersummary.getgtaxamount()));
        tvstaxamount.setText(dbhelper.getCurrencyFormat(vouchersummary.getstaxamount()));

        //rootView.addView(voucher_view);

//		LinearLayout linearLayout = new LinearLayout(this);
//		linearLayout = (LinearLayout) voucher_view;
        //View v = getWindow().getDecorView().getRootView();
        //final Bitmap bmp1 = printFromView(rootView);
//		LinearLayout vouLayout = (LinearLayout) voucher_view.findViewById(R.id.vou_view);
//		if(vouLayout.getParent() != null) {
//			((ViewGroup)vouLayout.getParent()).removeView(vouLayout); // <- fix
//		}
//		rootView.removeAllViews();


        //frame.setVisibility(View.GONE);


        //LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        tv.setText("Descrption");
//       rootView.addView(tv,params);
        //TextView tv=voucher.findViewById(R.id.txtdisplay);
        // tv.setText("မုန့်စိန်လား ရွှေဘ ငွေဘ အမွေရ");
//       LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        for(int i=0;i<20;i++){
//            TextView txt=new TextView(this);
//            txt.setWidth(200);
//            txt.setTextSize(20);
//            txt.setHeight(80);
//            txt.setText("မုန့်စိန်လား ရွှေဘ ငွေဘ အမွေရ" +i);
//            rootView.addView(txt,params);
//        }TotalAmount

        //rootView.addView(voucher);
        //liner.addView(voucher);

//		View v = getWindow().getDecorView().getRootView();
//		Printama.with(this).connect(printama -> {
//
//			// for (int i = 0; i < billprintcount; i++) {
//			printama.printFromView(voucher);
//			// }
//
//			billprintcount = 1;
//			// new Handler().postDelayed(printama::close, 2000); // comment by T2A 08-12-2020
//			frame.setVisibility(View.GONE);
//		}, this::showToast);

        //BindingCategory();


    }

    public String[] findBT() {
        String[] pairedDevice = new String[0];
        try {
            BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

            if (mBluetoothAdapter == null) {
                Toast.makeText(this, "No bluetooth adapter available!", Toast.LENGTH_SHORT).show();
            }

            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBluetooth, 0);
            }

            Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
            pairedDevice = new String[pairedDevices.size()];
            if (pairedDevices.size() > 0) {
                int i = 0;
                for (BluetoothDevice device : pairedDevices) {
                    // RPP300 is the name of the bluetooth printer device
                    // we got this name from the list of paired devices
//					if (device.getName().equals("RPP300")) {
//						mmDevice = device;
//						break;
//					}
                    pairedDevice[i] = String.valueOf(device.getName());
                    i++;
                }

//				if (mmDevice != null)
//					Toast.makeText(this, "Bluetooth device found.", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return pairedDevice;
    }

    public void connectBT(View view) {

        Printama printama = new Printama(VoucherDetail.this, GlobalClass.btprinter_name);

        printama.connect(new Printama.OnConnected() {
            @Override
            public void onConnected(Printama printama) {
                printama.printText(Printama.CENTER,
                        "----------------------\n" +
                                " Printer is connected.\n" +
                                "----------------------\n");
                printama.feedPaper();
                printama.close();

            }
        }, new Printama.OnFailed() {
            @Override
            public void onFailed(String message) {
                Toast.makeText(VoucherDetail.this, "Print fail!", Toast.LENGTH_SHORT).show();
                printama.close();
            }
        });

    }

    public static Bitmap createBitmapFromView(View view, int width, int height) {
//		int w = view.getLayoutParams().width;
//		int h = view.getLayoutParams().height;
//		if (width > 0 && height > 0) {
//			view.measure(View.MeasureSpec.makeMeasureSpec(convertDpToPixels(width), View.MeasureSpec.AT_MOST),
//					View.MeasureSpec.makeMeasureSpec(convertDpToPixels(height), View.MeasureSpec.UNSPECIFIED));
//		}
//		view.layout(0, 0, view.getWidth(), view.getHeight());
//		view.buildDrawingCache(true);
//		final Bitmap bm = view.getDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Drawable background = view.getBackground();
        //Drawable background = new ColorDrawable(Color.parseColor("#ffffff"));

        if (background != null) {
            background.draw(canvas);
        }
        view.draw(canvas);

        return bitmap;

    }

    public void findBTDevice() {
        try {
            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

            if (bluetoothAdapter == null) {
                Toast.makeText(this, "No bluetooth adapter available!", Toast.LENGTH_SHORT).show();
            }

            if (!bluetoothAdapter.isEnabled()) {
                Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBluetooth, 0);
            }

            Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
            if (pairedDevices.size() > 0) {
                for (BluetoothDevice device : pairedDevices) {
                    // RPP300 is the name of the bluetooth printer device
                    // we got this name from the list of paired devices
                    if (device.getName().equals(GlobalClass.btprinter_name)) {
                        bluetoothDevice = device;
                        break;
                    }
                }

                if (bluetoothDevice != null)
                    Toast.makeText(this, "Bluetooth device found.", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void connectBTDevice() throws IOException {
        try {

            // Standard SerialPortService ID
            UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
            bluetoothSocket = bluetoothDevice.createRfcommSocketToServiceRecord(uuid);
            bluetoothSocket.connect();
            outputStream = bluetoothSocket.getOutputStream();
            inputStream = bluetoothSocket.getInputStream();

            //beginListenForData();

            //myLabel.setText("Bluetooth Opened");
            //Toast.makeText(this,"Bluetooth Opened!", Toast.LENGTH_SHORT).show();
//			if (bluetoothSocket.isConnected())
//				Toast.makeText(this, "Bluetooth Connected!", Toast.LENGTH_SHORT).show();

            //mmSocket.close();
        } catch (Exception e) {
            bluetoothSocket.close();
            e.printStackTrace();
        }
    }

    private boolean printUnicode(byte[] data) {
        try {
            outputStream.write(data);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void closeSocket() {
        if (bluetoothSocket != null) {
            try {
                outputStream.close();
                bluetoothSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            bluetoothSocket = null;
        }
    }

    public void btnMemDis_Click(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.member_discount, null));
        final AlertDialog dialog = builder.create();
        Window window = dialog.getWindow();
        window.setLayout(GlobalClass.getDPsize(300, this),
                LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.show();

        final EditText edtMemDis = dialog.findViewById(R.id.txtMemDis);
        final Button btnAddMemDis = dialog.findViewById(R.id.btnOKMemDis);
        final Button btnCancel = dialog.findViewById(R.id.btnCancelMemDis);

        edtMemDis.setText(saleobj.getMembercard());

        btnAddMemDis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String memDis = edtMemDis.getText().toString().trim();
                if (memDis.equals("")) {
                    return;
                }
                if (GlobalClass.CheckConnection(VoucherDetail.this)) {
                    String message = dbhelper.AddMemberDiscount(memDis, tranid);
                    if (message.equals("success")) {
                        binditemdata(tranid);
                        showMessageDialog("Message", "Save Successful.");
                        dialog.dismiss();
                    } else {
                        showMessageDialog("Message", message);
                    }
                } else {
                    showMessageDialog("Message", "No Connection with Server!");
                }

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    public void showMessageDialog(String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(ctx).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }
}
