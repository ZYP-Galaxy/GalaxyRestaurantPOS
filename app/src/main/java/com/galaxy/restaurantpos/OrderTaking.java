package com.galaxy.restaurantpos;

// import android.support.v7.ActionBar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.ViewAnimator;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import me.myatminsoe.mdetect.MDetect;
import me.myatminsoe.mdetect.Rabbit;


public class OrderTaking extends Activity {

    //region Variables
    static String saleid = null;
    static String splited_saleid = null;
    static Boolean use_unit;
    static int use_unit_type; //Added by Arkar Moe on [26/06/2016]
    static String tableID;
    Button selectedclassbutton, selecteditembutton, btnOk;
    LinearLayout selecteditemlayout;
    LinearLayout v;// moved by WaiWL on 11/08/2015--from BindData
    LinearLayout itemlayout;// moved by WaiWL on 11/08/2015 --from
    private EditText txtUserName; // CreateItemButton
    LinearLayout valueitem;// added by WaiWL on 11/08/2015
    boolean menuOpen = false;
    public static Typeface font;
    final DatabaseHelper dbhelper = new DatabaseHelper(this);
    String Qty = "1";
    boolean viewdetail = false;
    int maxsr = 0;
    String modifierrowsr = "0";
    int toggleview = 0; // 0 for list , 1 for image list
    int modifierpop = 0; // 0 for use , 1 for not
    int editpop = 0; //0 for use , 1 for not
    int singleimageitem = 0; //0 for use , 1 for not //Added by ArkarMoe on [23/12/2016]
    Button activecategory, btnitemcancel;
    String ClassOrCategory = "Class";
    String[] saveddata = new String[2];
    final Context ctx = this;
    static int initialitemcount;
    String selectedtosetmenuitemid = "";
    String selectedfromsetmenuitemid = "";
    int useitemclass = 0; // 0 for no class button , 1 for with class button , 2
    private EditText txtnote; // for full screen category
    Customer_analysisObj customer_analysisobj;
    private TableChange_Information tablechangeinfo = new TableChange_Information();
    String chkprinter = "";
    String value; //Added by smh
    String CompanyName, CustomerCode, CustomerName; //Added by Arkar Moe
    String CustomerCredit = "0"; // Added by TTA on 16/05/2019
    Integer[] itemwidth = new Integer[1];
    boolean MealType1 = false; //Added by SMH on 29/05/2017
    boolean MealType2 = false; //Added by SMH on 29/05/2017
    boolean MealType3 = false; //Added by SMH on 29/05/2017
    LinearLayout mealtype1layout;
    ViewPager viewPager;
    PagerAdapter adapter;
    boolean clickChecked = true;
    boolean reprint = false;
    SplitBill billadapter;
    SplitBill billadapterRight;
    ListView listviewFrom;
    ListView listviewTo;
    TextView msplitDes;
    TextView msplitQty;
    TextView msplitUnit;
    TextView msplitAmount;
    ArrayList<SaleItemObj> arraysaleobjlist;
    ArrayList<SaleItemObj> arraysaleobjlistright;
    SaleItemObj tmpSaleItemObj;
    SaleItemObj tmpSaleItemObjright;
    boolean leftClick = false;
    boolean rightClick = false;
    boolean itemtransfer = false;   //added by ZYP [03-03-2020]
    TextView txtDocID;
    int qty;
    public String org_curr = "";  //added by EKK on 24-02-2020
    public int totranid = 0;
    public String codeforRemark = ""; //added by ZYP [14-07-2020]

    int tranid;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String menu_tranid = "tranid";

    public static int current_unit = 0;//added WHM [2020-02-05] MDY1-20023


    //added WHM [2020-05-08]
    int deliverytype_id = 0, sale_head_deliverytype_id = 0;
    String deliverytype_name;


    //region declare variable for delivery added WHM [2020-05-11]
    String delivery_tranid = "0";
    public int agent_id = 0, township_id = 0, deliveryman_id = 0, estimate_time = 0, delivery_payment = 1;
    public String order_time, delivery_cust_detail, delivery_remark, org_deliverycharges, org_delivery_freerange;
    public boolean delivery_use_calc_delivery_charges = false, delivery_isDeliver = false, delivery_updated = false;

    public boolean delivery_anychange = false;
    public int org_agent_id_position = 0, org_deliveryman_id_position = 0, tmp_deliveryman_id = 0, tmp_estimatetime = 0;

    List<TownshipObj> townshiplist = new ArrayList<TownshipObj>();
    List<Delivery_StringWithTags> township_itemlist = new ArrayList<Delivery_StringWithTags>();

    List<Salesmen_TypeObj> salementype_list = new ArrayList<Salesmen_TypeObj>();
    List<Delivery_StringWithTags> agent_itemlist = new ArrayList<Delivery_StringWithTags>();

    List<Salesmen> deliveryman_list = new ArrayList<Salesmen>();
    List<Delivery_StringWithTags> deliveryman_itemlist = new ArrayList<Delivery_StringWithTags>();

    List<DeliverySetupObj> deliSetup_list = new ArrayList<DeliverySetupObj>();

    DeliveryEntryObj deliveryentryobj;
    List<DeliveryEntryObj> deliveryEntryObjList = new ArrayList<DeliveryEntryObj>();

    ArrayAdapter<Delivery_StringWithTags> township_adapter;
    ArrayAdapter<Delivery_StringWithTags> agent_adapter;
    ArrayAdapter<Delivery_StringWithTags> deliveryman_adapter;

    SimpleDateFormat ordertime_TimeFormat1 = new SimpleDateFormat("hh:mm:ss");
    SimpleDateFormat ordertime_TimeFormat2 = new SimpleDateFormat("hh:mm a");
    Date dt_ordertime;
    Calendar calendar_time;
    //endregion

    //added by ZYP [16/09/2020] for itemdetail dialog currency
    List<CurrencyObj> currencyObjs_list = new ArrayList<CurrencyObj>();
    Boolean currency_exists = false;

    List<SaleObj> invoiceList = new ArrayList<SaleObj>();
    TextView txtinvoiceno;
    TextView txtremark; //added by MPPA on 02-09-2021

    Boolean isNewVoucher = false;

    static String mainCode = "";
    String orgqty = "";
    String qtytmp = "";
    public static boolean isprint_order = false;
    //endregion Variables

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GlobalClass.setCurrentscreen(4);
        // region Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // set content view AFTER ABOVE sequence (to avoid crash)
        // endregion Remove title bar
        this.setContentView(R.layout.activity_order_taking);
        //MDetect.INSTANCE.init(this);
//        Toast.makeText(this, MDetect.INSTANCE.getText("မင်္ဂလာပါ"),Toast.LENGTH_LONG).show();

        // region Get Parameter from table screen
        Bundle b = this.getIntent().getExtras(); // Get Parameter from table screen
        ((TextView) findViewById(R.id.txtTableNo)).setText(b.getString("TableName"));
        ((TextView) findViewById(R.id.txtTableNo)).setTag(b.getString("TableID"));
        tableID = b.getString("TableID");
        splited_saleid = b.getString("SaleID");

        if (!GlobalClass.tmpOffline && !GlobalClass.use_foodtruck) {

            //modified by ZYP [04-06-2021] #YGN3-21062
            if (dbhelper.getAppSetting("use_shift").equals("Y")) {
                Json_class jsonclass = new Json_class();
                //String URL = ( new DatabaseHelper(this).getServiceURL()+ "/Data/OrderTaking_Entry?orderdata="+java.net.URLEncoder.encode(salejsonarray.toString())+"&orderitemdata="+java.net.URLEncoder.encode(saleitemjsonarray.toString()));
                JSONArray jsonmessage = jsonclass.getJson(new DatabaseHelper(ctx).getServiceURL() + "/Data/ShiftCheck");
                if (jsonmessage.length() > 0) {
                    try {
                        if (Integer.parseInt(jsonmessage.get(0).toString()) == 1) {

                            Intent intentshift;
                            intentshift = new Intent(this, TableScreenActivity.class);
                            startActivity(intentshift);
                            finish();
                            dbhelper.hasShift = true;
                            return;
                        } else {

                        }
                    } catch (NumberFormatException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }

            //modified by ZYP [04-06-2021] #YGN3-21062
            if (dbhelper.getAppSetting("use_tabletuserlog").equals("Y")) {
                Json_class jsonuserlock = new Json_class();
                //String URL = ( new DatabaseHelper(this).getServiceURL()+ "/Data/OrderTaking_Entry?orderdata="+java.net.URLEncoder.encode(salejsonarray.toString())+"&orderitemdata="+java.net.URLEncoder.encode(saleitemjsonarray.toString()));
                JSONArray jsonmessageuserlock = jsonuserlock.getJson(new DatabaseHelper(ctx).getServiceURL() + "/Data/CheckTabletUserLock?tableid="
                        + java.net.URLEncoder.encode(tableID) + "&Userid="
                        + java.net.URLEncoder.encode(dbhelper.getwaiterid()));

                if (jsonmessageuserlock.length() > 0) {
                    try {
                        if (Integer.parseInt(jsonmessageuserlock.get(0).toString()) == 1) {

                            Intent intentshift;
                            intentshift = new Intent(this, TableScreenActivity.class);
                            startActivity(intentshift);
                            finish();
                            dbhelper.tabletuserlock = true;
                            return;
                        } else {

                        }
                    } catch (NumberFormatException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

            }

            //modified by ZYP [06-01-2021]
            dbhelper.LoadSoldOut(new DatabaseHelper(this).getServiceURL());

            dbhelper.LoadCurrencyTable(new DatabaseHelper(this).getServiceURL());

        }

//        LoginActivity.isUnicode = dbhelper.isUnicode();

//		if(LoginActivity.isUnicode)		//added by ZYP for Unicode
//			txtcustomer.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Pyidaungsu.ttf"));
//		else
//			txtcustomer.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Zawgyi-One.ttf"));

        txtinvoiceno = (TextView) findViewById(R.id.txtInvoiceno);


        TextView txtcustomer = (TextView) findViewById(R.id.txtcustomer);
        txtcustomer.setPaintFlags(txtcustomer.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        txtcustomer.setTypeface(font);
        txtcustomer.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //customer_dialog();
                //added by EKK on 15-01-2020
                String UserID = dbhelper.getwaiterid();
                if (dbhelper.Allow_select_Customer(UserID) && !GlobalClass.use_foodtruck) {
                    customer_dialog();
                }
            }
        });

        //region delivery //added WHM [2020-05-08]
        deliveryentryobj = new DeliveryEntryObj();
        final Spinner spinner_delivery = (Spinner) findViewById(R.id.spinner_deliverytype);
        final Button but_deliverytype = (Button) findViewById(R.id.butdeliverytype);

        if (!dbhelper.use_deliverymanagement() || GlobalClass.use_foodtruck || GlobalClass.tmpOffline) {
            spinner_delivery.setVisibility(View.GONE);
            but_deliverytype.setVisibility(View.GONE);
        } else {

            HashMap<Integer, String> m_deliverytype = new HashMap<Integer, String>() {{
                put(0, "Normal");
                put(1, "Delivery");
                put(2, "SelfPickup");
            }};
            List<Delivery_StringWithTags> itemList = new ArrayList<Delivery_StringWithTags>();
            for (int i = 0; i < m_deliverytype.size(); i++) {
                Integer key = i;
                String value = m_deliverytype.get(i).toString();

                /* Build the StringWithTag List using these keys and values. */
                itemList.add(new Delivery_StringWithTags(value, key));
            }
            ArrayAdapter<Delivery_StringWithTags> spinnerAdapter = new ArrayAdapter<Delivery_StringWithTags>(OrderTaking.this, android.R.layout.simple_spinner_item, itemList);
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_delivery.setAdapter(spinnerAdapter);
            spinner_delivery.setSelection(0);
            spinner_delivery.setTag(0);

            spinner_delivery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    deliverytype_id = position;
                    spinner_delivery.setTag(deliverytype_id);
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                    if (deliverytype_id == 1 || deliverytype_id == 2) {
                        // but_deliverytype.setVisibility(View.VISIBLE);
                        but_deliverytype.setEnabled(true);
                    } else {
                        //but_deliverytype.setVisibility(View.INVISIBLE);
                        but_deliverytype.setEnabled(false);
                    }

                    if (deliverytype_id != sale_head_deliverytype_id) {
                        if (GlobalClass.CheckConnection(ctx)) {
                            Json_class jsonclass = new Json_class();
                            jsonclass.getString(dbhelper
                                    .getServiceURL()
                                    + "/Data/Change_DeliveryType?tranid="
                                    + java.net.URLEncoder
                                    .encode(delivery_tranid)
                                    + "&delivery_type="
                                    + java.net.URLEncoder
                                    .encode(String.valueOf(deliverytype_id)));
                        }
                    }
                }

                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            but_deliverytype.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Perform action on click
                    delivery_entry_dialog();
                }
            });
            //endregion //whm

        }

//		if(LoginActivity.isUnicode)		//added by ZYP for Unicode
//			txtHeaderRemark.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Pyidaungsu.ttf"));
//		else
//			txtHeaderRemark.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Zawgyi-One.ttf"));

        //txtHeaderRemark.setPaintFlags(txtHeaderRemark.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

        final TextView txtHeaderRemark = (TextView) findViewById(R.id.txtHeaderRemark);
        txtHeaderRemark.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                HeaderRemark(txtHeaderRemark);
            }
        });

        final TextView txtRestName = (TextView) findViewById(R.id.txtRestName);
        if (GlobalClass.use_foodtruck) {
            txtRestName.setText("FOOD TRUCK");
        } else {

        }

        modifierpop = dbhelper.getmodifierpop(); // get modifier pop up flag
        // from systemsetting
        toggleview = dbhelper.getitemimageflag();// get itemimage flag from
        // systemsetting
        editpop = dbhelper.geteditboxflag();

        singleimageitem = dbhelper.getsigleimageitemflag(); //Added by ArkarMoe on [23/12/2016]

        if (dbhelper.getuse_unit().equals("true"))// get useunit flag from systemsetting
            use_unit = true;
        else
            use_unit = false;

        //Added by Arkar Moe on [24/06/2016] for (Res-0085)
        //Adding Default UnitType in Appsetting
        if (dbhelper.getuse_unit_type().equals("1"))
            use_unit_type = 1;
        else if (dbhelper.getuse_unit_type().equals("2"))
            use_unit_type = 2;
        else if (dbhelper.getuse_unit_type().equals("3"))
            use_unit_type = 3;
        //(Res-0085)

        if (dbhelper.getUseHotel().equals("true")) //Added by ArkarMoe on [19/12/2016]
        {
            ((Button) findViewById(R.id.butroomSelect)).setVisibility(View.VISIBLE);
            ((TextView) findViewById(R.id.txtRoom)).setVisibility(View.VISIBLE);
        } else {
            ((Button) findViewById(R.id.butroomSelect)).setVisibility(View.GONE);
            ((TextView) findViewById(R.id.txtRoom)).setVisibility(View.GONE);
        }


        if (dbhelper.getuse_Fire().equals("Y")) //Added by AungMyoTun
        {
            ((Button) findViewById(R.id.butfire)).setVisibility(View.VISIBLE);
        } else {
            ((Button) findViewById(R.id.butfire)).setVisibility(View.GONE);
        }

        String UserID = dbhelper.getwaiterid();
        if (dbhelper.Allow_btnBill(UserID)) {
            ((Button) findViewById(R.id.butbill)).setVisibility(View.VISIBLE);
        } else {
            ((Button) findViewById(R.id.butbill)).setVisibility(View.GONE);
        }

        if (dbhelper.Allow_btnClearAll(UserID)) {
            ((Button) findViewById(R.id.butclearall)).setVisibility(View.VISIBLE);
        } else {
            ((Button) findViewById(R.id.butclearall)).setVisibility(View.GONE);
        }

        FrameLayout btndetail = (FrameLayout) findViewById(R.id.butdetail);
        if (dbhelper.Allow_btnDetail(UserID) == true) {
            btndetail.setVisibility(View.VISIBLE);
        } else {
            btndetail.setVisibility(View.GONE);
        }


        if (dbhelper.Allow_btnOthers(UserID) == true) {
            ((Button) findViewById(R.id.butothers)).setVisibility(View.VISIBLE);
        } else {
            ((Button) findViewById(R.id.butothers)).setVisibility(View.GONE);
        }


        if (dbhelper.Allow_btnSplit(UserID) == true) {
            ((Button) findViewById(R.id.butsplitbill)).setVisibility(View.VISIBLE);
        } else {
            ((Button) findViewById(R.id.butsplitbill)).setVisibility(View.GONE);
        }

//		if(LoginActivity.isUnicode)		//added by ZYP for Unicode
//			font = Typeface.createFromAsset(getAssets(), "fonts/Pyidaungsu.ttf");
//		else
//			font = Typeface.createFromAsset(getAssets(), "fonts/Zawgyi-One.ttf");

        CheckBox chk_taxfree = (CheckBox) findViewById(R.id.chkTaxfree);
        chk_taxfree.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                CalculateTotal();
            }
        });

        if (GlobalClass.use_foodtruck) {
            ((Button) findViewById(R.id.butfire)).setVisibility(View.GONE);
            ((Button) findViewById(R.id.butbill)).setVisibility(View.GONE);
            ((Button) findViewById(R.id.butsplitbill)).setVisibility(View.GONE);
            ((Button) findViewById(R.id.butCodeFind)).setVisibility(View.GONE);
            ((Button) findViewById(R.id.butothers)).setVisibility(View.GONE);
            ((Button) findViewById(R.id.butconfirm)).setVisibility(View.GONE);
            ((Button) findViewById(R.id.butclearall)).setVisibility(View.GONE);

            txtcustomer.setVisibility(View.INVISIBLE);
            ((TextView) findViewById(R.id.txtTableNo)).setVisibility(View.GONE);
            ((TextView) findViewById(R.id.txtcustcount)).setVisibility(View.INVISIBLE);
            ((TextView) findViewById(R.id.txttext)).setVisibility(View.INVISIBLE);

            ((Button) findViewById(R.id.butNewVoucher)).setVisibility(View.VISIBLE);

            ((Button) findViewById(R.id.butsubmit)).setText("Confirm");

            txtinvoiceno.setTextSize(22f);
            txtinvoiceno.setPadding(2, 0, 0, 0);
            txtinvoiceno.setBackgroundResource(R.drawable.combobutton);

            LinearLayout custlayout = (LinearLayout) findViewById(R.id.customerlayout);
            custlayout.setVisibility(View.GONE);

            chk_taxfree.setVisibility(View.VISIBLE);

        } else {

            chk_taxfree.setVisibility(View.GONE);

            ((TextView) findViewById(R.id.txttotal)).setTextAppearance(this, android.R.style.TextAppearance_Medium);
            ((LinearLayout) findViewById(R.id.layout_tax)).setVisibility(View.GONE);
            ((LinearLayout) findViewById(R.id.layout_netamount)).setVisibility(View.GONE);

        }

        customer_analysisobj = new Customer_analysisObj();
        if (!tableID.equals("Parcel")) {
            // if(dbhelper.getOfflineFlag().equals(false))//added by WaiWL on
            // 12/06/2015
            if (GlobalClass.tmpOffline == false) {
                dbhelper.LoadActivetableByTableID(dbhelper.getServiceURL(),
                        b.getString("TableID"), dbhelper.getwaiterid());
            }

            List<Table_Name> tablename = dbhelper.getActiveTableByTableID(b.getString("TableID"));

            saleid = "0";

            // if(dbhelper.getOfflineFlag().equals(false))//added by WaiWL on
            // 12/06/2015
            if (GlobalClass.tmpOffline == false) {

                if (GlobalClass.CheckConnection(ctx) && tablename.size() > 1) {
                    GetDataByTranid(tablename, splited_saleid);
                } else if (GlobalClass.CheckConnection(ctx) && tablename.size() == 1) {
                    GetData(tablename);
                }

/*				if (GlobalClass.CheckConnection(ctx) && tablename.size() > 0) {
						GetData(tablename);
				}*/
            }
            if (tablename.size() < 1) {
                dbhelper.DeleteSaleDataByTableID(tableID);
            }

        } else {
            saleid = "0";
            if (GlobalClass.use_foodtruck) {
//                if(submit_flag){
//                    foodtruck_saleid = dbhelper.getSaleIDForfoodtruck();
//                    foodtruck_invNo = dbhelper.getNewInvoice(foodtruck_saleid);
//                }
//                else{
//                    foodtruck_saleid = Integer.parseInt(splited_saleid);
//                }

                if (splited_saleid.equals("0")) {
                    invoiceList = dbhelper.LoadInvoicesList(dbhelper.getServiceURL(), dbhelper.getwaiterid());
                    if (invoiceList.size() > 0) {
                        saleid = invoiceList.get(0).getsaleid();
                        for (SaleObj obj : invoiceList) {
                            dbhelper.LoadSaleHeader(dbhelper.getServiceURL(), "", obj.getsaleid());
//                            List<SaleItemObj> saleitemobjlist = dbhelper.getSaleItemdataBySaleID(obj.getsaleid());
//
//                            if (saleitemobjlist.size() == 0)
                            dbhelper.LoadSaleItem(dbhelper.getServiceURL(), obj.getsaleid());
                        }

                        //Toast.makeText(OrderTaking.this,invoiceList.size(),Toast.LENGTH_LONG).show();

                    } else {
                        saleid = "0";
                    }

                } else
                    saleid = splited_saleid;

//                List<Table_Name> tablename = new ArrayList<Table_Name>();
//                Table_Name table_name = new Table_Name();
//                table_name.setTable_Name_ID(Integer.parseInt(tableID));
//                table_name.setTranid(foodtruck_saleid);
//                tablename.add(table_name);
//                GetData(tablename);

            } else {

            }
        }

        if (GlobalClass.use_menucategorygrouptype == 1 && GlobalClass.use_foodtruck) {
            dbhelper.str_category(GlobalClass.getSale_deflocationID());
            GlobalClass.Bind_MenuCategoryGroup();
        }

        useitemclass = dbhelper.getitemviewstyle();
        Bind_Data();
        BindClasscategoryButtons();
        //BindCategoryFullScreen();
        bindstatus();

        // if(dbhelper.getOfflineFlag().equals(false))//added by WaiWL on
        // 12/06/2015
        if (!GlobalClass.tmpOffline) {
            ConnectionStatus();
        }
        GlobalClass.ChangeLanguage((ViewGroup) findViewById(R.id.cashlayout),
                this, 14, font);
        GlobalClass.ChangeLanguage((ViewGroup) findViewById(R.id.layoutbutton),
                this, 14, font);
        GlobalClass.ChangeLanguage(
                (ViewGroup) findViewById(R.id.customerlayout), this, 14, font);


        // /added by WaiWL on 23/06/2015
        final TextView txtLoadData = (TextView) findViewById(R.id.txtLoadOrderData);
        final ImageView imgRefresh = (ImageView) findViewById(R.id.imgRefresh);
        if (!GlobalClass.tmpOffline) {
            txtLoadData.setVisibility(View.GONE);
            imgRefresh.setVisibility(View.GONE);
        } else {
            txtLoadData.setVisibility(View.VISIBLE);
            imgRefresh.setVisibility(View.VISIBLE);
        }

        //added by ZYP for item detail dialog currency
        currencyObjs_list = dbhelper.GetCurrency();
        if (currencyObjs_list.size() > 0) {
            currency_exists = true;
        }


    }

    private void GetData(final List<Table_Name> tablename) {
        saleid = Integer.toString(tablename.get(0).getTranid());
        dbhelper.LoadSaleHeader(new DatabaseHelper(ctx).getServiceURL(),
                tableID, tablename.get(0).getTranid().toString());
        dbhelper.LoadSaleItem(new DatabaseHelper(ctx).getServiceURL(),
                tablename.get(0).getTranid().toString());
        customer_analysisobj = dbhelper.LoadCustomerAnalysis(
                new DatabaseHelper(ctx).getServiceURL(), tablename.get(0)
                        .getTranid().toString());

        //delivery management //added WHM [2020-05-12]
        if (dbhelper.use_deliverymanagement() == true) {
            dbhelper.LoadDeliveryEntryTmp(
                    new DatabaseHelper(ctx).getServiceURL(), tablename.get(0)
                            .getTranid().toString());
            delivery_tranid = tablename.get(0)
                    .getTranid().toString();
        }

    }

    private void GetDataByTranid(final List<Table_Name> tablename, String tranid) {
        saleid = tranid;
        dbhelper.LoadSaleHeader(new DatabaseHelper(ctx).getServiceURL(),
                tableID, tranid);
        dbhelper.LoadSaleItem(new DatabaseHelper(ctx).getServiceURL(), tranid);
        customer_analysisobj = dbhelper.LoadCustomerAnalysis(
                new DatabaseHelper(ctx).getServiceURL(), tranid);
        //delivery management //added WHM [2020-05-12]
        if (dbhelper.use_deliverymanagement() == true) {
            dbhelper.LoadDeliveryEntryTmp(
                    new DatabaseHelper(ctx).getServiceURL(), tablename.get(0)
                            .getTranid().toString());
            delivery_tranid = tablename.get(0)
                    .getTranid().toString();
        }

    }


    public void SplitedVoucher(String Tableid, List tablename) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();

        View view = inflater.inflate(R.layout.activity_split_bill, null);

        listviewFrom = (ListView) view.findViewById(R.id.splitFrom);
        listviewTo = (ListView) view.findViewById(R.id.splitTo);
        msplitDes = (TextView) view.findViewById(R.id.txtLayoutItemDes);
        msplitQty = (TextView) view.findViewById(R.id.txtLayoutItemQty);
        msplitUnit = (TextView) view.findViewById(R.id.txtLayoutItemUnit);
        msplitAmount = (TextView) view.findViewById(R.id.txtLayoutItemAmount);
        Button btnMoveLeft = (Button) view.findViewById(R.id.btnMoveLeft);
        Button btnMoveRight = (Button) view.findViewById(R.id.btnMoveRight);
        Button btnDoubleMoveLeft = (Button) view.findViewById(R.id.btnDoubleMoveLeft);
        Button btnDoubleMoveRight = (Button) view.findViewById(R.id.btnDoubleMoveRight);
        Button btnSplitBill = (Button) view.findViewById(R.id.btnSplitbill);
        if (itemtransfer)
            btnSplitBill.setText("Transfer");   //added by ZYP [03-03-2020]
        else
            btnSplitBill.setText("Split Bill");
        TextView header = (TextView) view.findViewById(R.id.tvHeader);
        if (itemtransfer)
            header.setText("ITEM TRANSFER");
        else
            header.setText("SPLIT BILL");

        btnMoveLeft.setText("<<");
        btnDoubleMoveLeft.setText("<");


        arraysaleobjlist = dbhelper.getSaleItemdataBySaleIDArrayList(saleid); // get Sale Item Data

        arraysaleobjlistright = new ArrayList<SaleItemObj>();
        tmpSaleItemObj = new SaleItemObj();
        tmpSaleItemObjright = new SaleItemObj();

        billadapter = new SplitBill(this, arraysaleobjlist);
        listviewFrom.setAdapter(billadapter);


        billadapterRight = new SplitBill(this, arraysaleobjlistright);

        listviewTo.setAdapter(billadapterRight);


        listviewFrom.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int arg2, long arg3) {
                // TODO Auto-generated method stub
                //saleitemobj=(SaleItemObj) billadapter.getItem(arg2);
                leftClick = true;
                tmpSaleItemObj = arraysaleobjlist.get(arg2);

                //arraysaleobjlistright.add(arraysaleobjlist.get(arg2));
                //billadapterRight.notifyDataSetChanged();

                ///arraysaleobjlist.remove(arg2);
                //billadapter.notifyDataSetChanged();

            }
        });


        listviewTo.setOnItemClickListener(new OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int arg2, long arg3) {
                // TODO Auto-generated method stub
                rightClick = true;
                tmpSaleItemObjright = arraysaleobjlistright.get(arg2);
            }
        });

        btnMoveRight.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (leftClick == true) {
                    arraysaleobjlistright.add(tmpSaleItemObj);
                    arraysaleobjlist.remove(tmpSaleItemObj);
                    billadapterRight.notifyDataSetChanged();
                    billadapter.notifyDataSetChanged();
                    tmpSaleItemObj = null;
                    leftClick = false;
                }
            }
        });

        btnMoveLeft.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub

                Boolean unitcode = false;

                if (rightClick) {
                    Double sale_price = Double.valueOf(dbhelper.getItempricebyitemid(tmpSaleItemObjright.getcode()));
                    SaleItemObj allitemLeft = new SaleItemObj();
                    //allitemLeft= dbhelper.getSaleItemdataBySaleIDCode(saleid,tmpSaleItemObjright.getcode(),tmpSaleItemObjright.getsr(),sale_price);
                    //arraysaleobjlist.add(tmpSaleItemObjright);
                    arraysaleobjlistright.remove(tmpSaleItemObjright);
                    //billadapterRight.notifyDataSetChanged();
                    //billadapter.notifyDataSetChanged();

                    rightClick = false;


                    for (SaleItemObj saleItemObj : arraysaleobjlist) {

                        if (saleItemObj.getcode().equals(tmpSaleItemObjright.getcode()) && saleItemObj.getsr().equals(tmpSaleItemObjright.getsr())) {
                            int qtytest = Integer.valueOf(saleItemObj.getqty()) + Integer.valueOf(tmpSaleItemObjright.getqty());
                            saleItemObj.setqty(String.valueOf(qtytest));
                            Double amount = Double.valueOf(saleItemObj.getamount()) + Double.valueOf(tmpSaleItemObjright.getamount());
                            saleItemObj.setamount(String.valueOf(amount));
                            unitcode = true;

                        }
                    }

                    if (unitcode == false) {
                        arraysaleobjlist.add(tmpSaleItemObjright);
                    }
                    billadapter.notifyDataSetChanged();
                    billadapterRight.notifyDataSetChanged();
                    tmpSaleItemObjright = null;
                }
            }
        });

        btnDoubleMoveRight.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub

                if (leftClick) {


                    qty = Integer.valueOf(tmpSaleItemObj.getqty().replace(".0", ""));
                    Double sale_price = Double.valueOf(dbhelper.getItempricebyitemid(tmpSaleItemObj.getcode()));
                    Boolean unitcode = false;
                    SaleItemObj saleitemobjleft = new SaleItemObj();
                    SaleItemObj saleitemobjright = new SaleItemObj();
                    saleitemobjleft = tmpSaleItemObj;
                    saleitemobjright = dbhelper.getSaleItemdataBySaleIDCode(saleid, tmpSaleItemObj.getcode(), tmpSaleItemObj.getsr(), sale_price);


                    if (qty == 1) {
                        arraysaleobjlist.remove(tmpSaleItemObj);
                        tmpSaleItemObj = null;

                    } else {


                        saleitemobjleft.setqty(String.valueOf(qty - 1));
                        saleitemobjleft.setamount(String.valueOf(sale_price * (qty - 1)));

                    }
                    leftClick = false;

                    for (SaleItemObj saleItemObj : arraysaleobjlistright) {

                        if (saleItemObj.getcode().equals(saleitemobjright.getcode()) && saleItemObj.getsr().equals(saleitemobjright.getsr())) {
                            int qtytest = Integer.valueOf(saleItemObj.getqty()) + Integer.valueOf(saleitemobjright.getqty());
                            saleItemObj.setqty(String.valueOf(qtytest));
                            Double amount = Double.valueOf(saleItemObj.getamount()) + Double.valueOf(saleitemobjright.getamount());
                            saleItemObj.setamount(String.valueOf(amount));
                            unitcode = true;
                        }
                    }
                    if (unitcode == false) {
                        arraysaleobjlistright.add(saleitemobjright);
                    }
                    billadapter.notifyDataSetChanged();
                    billadapterRight.notifyDataSetChanged();
                }
            }
        });


        btnDoubleMoveLeft.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (rightClick) {


                    qty = Integer.valueOf(tmpSaleItemObjright.getqty().replace(".0", ""));
                    Double sale_price = Double.valueOf(dbhelper.getItempricebyitemid(tmpSaleItemObjright.getcode()));
                    Boolean unitcode = false;
                    SaleItemObj amtmyotun = new SaleItemObj();
                    SaleItemObj smhmonhtwe = new SaleItemObj();
                    amtmyotun = tmpSaleItemObjright;
                    smhmonhtwe = dbhelper.getSaleItemdataBySaleIDCode(saleid, tmpSaleItemObjright.getcode(), tmpSaleItemObjright.getsr(), sale_price);


                    if (qty == 1) {
                        //arraysaleobjlistright.add(tmpSaleItemObj);
                        arraysaleobjlistright.remove(tmpSaleItemObjright);
                        //	billadapterRight.notifyDataSetChanged();
                        //billadapter.notifyDataSetChanged();
                        tmpSaleItemObjright = null;

                    } else {

                        amtmyotun.setqty(String.valueOf(qty - 1));
                        amtmyotun.setamount(String.valueOf(sale_price * (qty - 1)));

                    }
                    rightClick = false;

                    for (SaleItemObj saleItemObj : arraysaleobjlist) {

                        if (saleItemObj.getcode().equals(smhmonhtwe.getcode()) && saleItemObj.getsr().equals(smhmonhtwe.getsr())) {
                            int qtytest = Integer.valueOf(saleItemObj.getqty()) + Integer.valueOf(smhmonhtwe.getqty());
                            saleItemObj.setqty(String.valueOf(qtytest));
                            Double amount = Double.valueOf(saleItemObj.getamount()) + Double.valueOf(smhmonhtwe.getamount());
                            saleItemObj.setamount(String.valueOf(amount));
                            unitcode = true;
                        }
                    }
                    if (unitcode == false) {
                        arraysaleobjlist.add(smhmonhtwe);
                    }
                    billadapter.notifyDataSetChanged();
                    billadapterRight.notifyDataSetChanged();


                }
            }
        });


        btnSplitBill.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub

                if (itemtransfer) {       //ADDED BY ZYP [04-03-2020]
                    ItemTransfer();
                } else {
                    //modified by EKK on 28-02-2020
                    if (arraysaleobjlist.size() > 0) {

                        SplitBill();

                    } else {
                        AlertDialog alertDialog = new AlertDialog.Builder(
                                ctx).create();
                        alertDialog.setTitle("Message");
                        alertDialog.setMessage("No Items left to split the bill!");
                        alertDialog.setIcon(0);

                        alertDialog.setButton2("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(
                                            DialogInterface dialog,
                                            int which) {
                                        //Note_dialog();
                                    }
                                });

                        alertDialog.show();
                    }

                }


            }
        });


        builder.setView(view);


        final Dialog dialog = builder.create();

        WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.CENTER;
        //wmlp.y = GlobalClass.getDPsize(400, this);

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(GlobalClass.getDPsize(730, this),
                GlobalClass.getDPsize(450, this));
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();


        final String dataurl = new DatabaseHelper(this).getServiceURL();


        if (GlobalClass.tmpOffline == false) {/*
				if (GlobalClass.CheckConnectionForSubmit(ctx)) {

					Integer iColCount = 0;
					LinearLayout row = new LinearLayout(this);

					LinearLayout SplitedVouchers = (LinearLayout) dialog
							.findViewById(R.id.splitedvoucherlayout);

					    dbhelper.LoadSplitedVouchers(dataurl, Tableid);

					    List<SplitedVouchers> SplitedVouchersList = dbhelper.getSplitedVouchers();


						for (int i = 0; i < SplitedVouchersList.size(); i++) {

							LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
									GlobalClass.getDPsize(250, this),
									GlobalClass.getDPsize(50, this));
							params.setMargins(2, 6, 0, 4);
							row.addView(CreateSplitedVouchers(SplitedVouchersList.get(i), dialog,tablename),params);


							 iColCount++;

					            if(iColCount == 3)
					            {
					            	SplitedVouchers.addView(row);
					            	row = new LinearLayout(this);
									iColCount = 0;
					            }

						}
						SplitedVouchers.addView(row);


				}
			*/
        }
    }


    public void hello(String Tableid, List tablename) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();

        View view = inflater.inflate(R.layout.activity_split_bill, null);


        listviewFrom = (ListView) view.findViewById(R.id.splitFrom);
        listviewTo = (ListView) view.findViewById(R.id.splitTo);
        msplitDes = (TextView) view.findViewById(R.id.txtLayoutItemDes);
        msplitQty = (TextView) view.findViewById(R.id.txtLayoutItemQty);
        msplitAmount = (TextView) view.findViewById(R.id.txtLayoutItemAmount);


        ArrayList<SaleItemObj> arraysaleobjlist = dbhelper
                .getSaleItemdataBySaleIDArrayList(saleid); // get Sale Item Data

        billadapter = new SplitBill(this, arraysaleobjlist);

        listviewFrom.setAdapter(billadapter);
        listviewTo.setAdapter(billadapter);


        builder.setView(view);

        final Dialog dialog = builder.create();
        WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.CENTER;
        Window window = dialog.getWindow();
        //window.setLayout(GlobalClass.getDPsize(800, this),GlobalClass.getDPsize(800, this));
        //dialog.setCanceledOnTouchOutside(true);
        dialog.show();


    }

    private void getBatteryPercentage() {
        BroadcastReceiver batteryLevelReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                context.unregisterReceiver(this);
                int currentLevel = intent.getIntExtra(
                        BatteryManager.EXTRA_LEVEL, -1);
                int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
                int level = -1;
                if (currentLevel >= 0 && scale > 0) {
                    level = (currentLevel * 100) / scale;
                }

                ((TextView) findViewById(R.id.txtbatterypercent)).setText(level
                        + "%");

                ImageView imgbattery = (ImageView) findViewById(R.id.imgbattery);
                if (level > 100 - (100 / 7)) {
                    imgbattery.setImageResource(R.drawable.battery1);
                } else if (level > 100 - (2 * (100 / 7))) {
                    imgbattery.setImageResource(R.drawable.battery2);
                } else if (level > 100 - (3 * (100 / 7))) {
                    imgbattery.setImageResource(R.drawable.battery3);
                } else if (level > 100 - (4 * (100 / 7))) {
                    imgbattery.setImageResource(R.drawable.battery4);
                } else if (level > 100 - (5 * (100 / 7))) {
                    imgbattery.setImageResource(R.drawable.battery5);
                } else if (level > 100 - (6 * (100 / 7))) {
                    imgbattery.setImageResource(R.drawable.battery6);
                } else {
                    imgbattery.setImageResource(R.drawable.battery7);
                }
            }
        };

        IntentFilter batteryLevelFilter = new IntentFilter(
                Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryLevelReceiver, batteryLevelFilter);
    }

    public void Bind_Data() {
        List<SaleObj> saleobjlist = new ArrayList<SaleObj>();

        if (GlobalClass.use_foodtruck) {
            maxsr = 0;
            //saleid = "" + foodtruck_saleid;
            txtinvoiceno.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if (GlobalClass.CheckConnection(getApplicationContext())) {
                        List<RowItem> rowItems = new ArrayList<RowItem>();
                        invoiceList = dbhelper.LoadInvoicesList(dbhelper.getServiceURL(), dbhelper.getwaiterid());
                        for (SaleObj obj : invoiceList) {
                            RowItem item = new RowItem(Integer.parseInt(obj.getsaleid()), 0, "", obj.getinvoiceno(), "");
                            rowItems.add(item);
                        }
                        Combobox(v, rowItems);
                    } else {
                        showAlertDialog(ctx, "Warning!", "No connection with Server.", false);
                    }

                }
            });
        }

        saleobjlist = dbhelper.getSaledataByTableID(Integer.parseInt(saleid)); // get Sale Data
        final TextView txtinvoiceno = (TextView) findViewById(R.id.txtInvoiceno);
        txtDocID = (TextView) findViewById(R.id.txtDocID);
        final TextView txtcustcount = (TextView) findViewById(R.id.txtcustcount);
        final TextView txtroom = (TextView) findViewById(R.id.txtRoom); //Added by ArkarMoe on [16/12/2016]
        final TextView txtcustomer = (TextView) findViewById(R.id.txtcustomer);
        LinearLayout valueitemlistslayout = (LinearLayout) findViewById(R.id.valueitem_itemlistlayout);
        //added WHM [2020-05-08]
        final Spinner spinner_deliverytype = (Spinner) findViewById(R.id.spinner_deliverytype);//added WHM [2020-05-08]
        final Button butdelivery_type = (Button) findViewById(R.id.butdeliverytype);
        final CheckBox chkTaxfree = (CheckBox) findViewById(R.id.chkTaxfree);
        chkTaxfree.setEnabled(true);
        if ((saleobjlist.size() > 0 && !tableID.equals("Parcel")) ||
                (saleobjlist.size() > 0 && GlobalClass.use_foodtruck && !saleobjlist.get(0).getsubmitflag())) {
            // region bindheader

            //modified by ZYP [06-01-2020]
//            if (saleobjlist.get(0).getRef_no().equals("null")) {
//                txtinvoiceno.setText(saleobjlist.get(0).getinvoiceno());
//            }

            isNewVoucher = false;
            txtinvoiceno.setText(saleobjlist.get(0).getinvoiceno());
            txtinvoiceno.setTag(saleid);

            txtDocID.setText(saleobjlist.get(0).getinvoiceno());
            txtDocID.setTag(saleobjlist.get(0).getuserid());

            if (GlobalClass.use_foodtruck) {
                chkTaxfree.setChecked(saleobjlist.get(0).isTaxfree());

                txtcustcount.setVisibility(View.INVISIBLE);
                ((TextView) findViewById(R.id.txttext)).setVisibility(View.INVISIBLE);
            } else {
                txtcustcount.setText(saleobjlist.get(0).getcustcount() == null ? "1" : saleobjlist.get(0).getcustcount());

                //region bind delivery_type
                deliverytype_id = sale_head_deliverytype_id = saleobjlist.get(0).getDelivery_type();
                spinner_deliverytype.setSelection(saleobjlist.get(0).getDelivery_type());//added WHM [2020-05-08]
                spinner_deliverytype.setTag(saleobjlist.get(0).getDelivery_type());
                if (spinner_deliverytype.getSelectedItemPosition() == 1) {
                    // butdelivery_type.setVisibility(View.VISIBLE);
                    butdelivery_type.setEnabled(true);

                } else {
                    // butdelivery_type.setVisibility(View.INVISIBLE);
                    butdelivery_type.setEnabled(false);

                }
                //endregion delivery_type
            }

            txtroom.setTag(saleid);//Added by ArkarMoe on [16/12/2016]
            List<Rooms> Roomobjlist = dbhelper.getRoomCodebyRoomID(saleobjlist.get(0).getroomid());
            if (Roomobjlist.size() > 0)
                txtroom.setText(Roomobjlist.get(0).getRoom_Code());

            txtcustomer.setTag(saleobjlist.get(0).getcustomerid());
            List<CustomerObj> customerobjlist = dbhelper.getCustomerByCustomerID(saleobjlist.get(0).getcustomerid());
            if (customerobjlist.size() > 0)
                txtcustomer.setText(customerobjlist.get(0).getcustomercode()
                        + " - " + customerobjlist.get(0).getcustomername());

            // endregion bindheader

            saleid = saleobjlist.get(0).getsaleid();
            List<SaleItemObj> saleitemobjlist = dbhelper.getSaleItemdataBySaleID(saleid); // get Sale Item Data
            initialitemcount = saleitemobjlist.size();
            // region bind saleItemData
            double itemamount = 0.0; // for item total amount

            // added by WaiWL on 23/06/2015
            if (GlobalClass.tmpLoadData || GlobalClass.use_foodtruck) {
                LinearLayout tmpitemlistlayout = (LinearLayout) findViewById(R.id.itemlistlayout);
                tmpitemlistlayout.removeAllViews();
            }
            // ////////

            boolean modifierSubmitflag = false;
            for (SaleItemObj saleItemObj : saleitemobjlist) {

                // get item information by saleitemcode
                List<ItemsObj> itemobj = dbhelper.getItemslistbyitemid(saleItemObj.getcode());

                List<SelectedItemModifierObj> modifiersaleitemobjlist = new ArrayList<SelectedItemModifierObj>();

                if (saleitemobjlist.get(0).getSubmitflag()) {
                    chkTaxfree.setEnabled(false);
                } else {
                    chkTaxfree.setEnabled(true);
                }

                if (itemobj.size() > 0) {
                    if (Boolean.parseBoolean(itemobj.get(0).getissetmenu())) {
                        modifiersaleitemobjlist = dbhelper
                                .getSetMenudataBySaleID(saleid, saleItemObj.getsr());// get modifier items by srno
                    } else {
                        modifiersaleitemobjlist = dbhelper
                                .getSaleItemModifierdataBySaleID(saleid, saleItemObj.getsr());// get modifier items by srno
                    }

                    final TextView txtDiff = (TextView) findViewById(R.id.txtlineDifference);
                    // added by WaiWL on 11/08/2015
                    if (dbhelper.isValueItem(itemobj.get(0).getcode())) {

                        String description = null;
                        int checkitem = dbhelper.getitemdescriptionstyle();
                        switch (checkitem) {
                            case 0:
                                description = itemobj.get(0).getdescription();
                                break;
                            case 1:
                                description = itemobj.get(0).getdescription2();
                                break;
                            case 2:
                                description = itemobj.get(0).getdescription3();
                                break;
                        }
                        valueitem = selectvalueItemtolist(itemobj.get(0).getcode(), description, saleItemObj.getsr());
                        ((TextView) valueitem.getChildAt(0)).setTag("P");
                        // added by WaiWL on 12/08/2015

                    } else {

                        String description = null;
                        int checkitem = dbhelper.getitemdescriptionstyle();
                        switch (checkitem) {
                            case 0:
                                description = itemobj.get(0).getdescription();
                                break;
                            case 1:
                                description = itemobj.get(0).getdescription2();
                                break;
                            case 2:
                                description = itemobj.get(0).getdescription3();
                                break;
                        }

                        v = selectItemtolist(itemobj.get(0).getcode(), description, saleItemObj.getsr(), false);

                        itemamount = Double.parseDouble(saleItemObj.getamount().equals("null") ? "0" : saleItemObj.getamount());

                        ((TextView) v.getChildAt(3)).setTag(itemamount);
//
//                        String defcurrency = dbhelper.getDefCurrency();
//                        String defexgrate = dbhelper.LoadExgRate(dbhelper.getServiceURL(), defcurrency);
//
//                        org_curr = dbhelper.getsaleCurrbyitemid(saleItemObj.getcode()); //added by EKK on 24-02-2020
//                        String orgExgRate = dbhelper.LoadExgRate(dbhelper.getServiceURL(), org_curr);
//
//                        itemamount = itemamount * Double.parseDouble(orgExgRate) / Double.parseDouble(defexgrate);

                        //itemamount = Double.parseDouble(((TextView) v.getChildAt(3).getTag()).toString());
                        // region bind modifier items and remark

                        // if remark include bind remark
                        if (!saleItemObj.getremark().equals("null") && !saleItemObj.getremark().equals("")) {
                            SelectedItemModifierObj modifieritemobj = new SelectedItemModifierObj(); // for remark
                            modifieritemobj.setstatus("remark");
                            //modifieritemobj.setname(saleItemObj.getremark());
                            modifieritemobj.setname(saleItemObj.getremark());
                            modifieritemobj.setitemsr("");
                            modifiersaleitemobjlist.add(modifieritemobj);
                        }

                        modifierSubmitflag = saleItemObj.getSubmitflag();

                        if (modifiersaleitemobjlist.size() > 0) {
                            BindModifieritemtolist(v, modifiersaleitemobjlist, false);
                            LinearLayout parentlayout = (LinearLayout) v.getParent();
                            int i = 1;
                            for (SelectedItemModifierObj selectedItemModifierObj : modifiersaleitemobjlist) {

                                if (!(selectedItemModifierObj.getstatus().equals("remark"))) {
                                    if (selectedItemModifierObj.getstatus().equals("setmenuitem")) {

                                        Double max_price, setmenuitemamount = 0.0;

                                        String setmenusr = String.valueOf(selectedItemModifierObj.getsr());
                                        Json_class jsonclass = new Json_class();
					/*					String real_max_pric = jsonclass
												.getString(dbhelper
														.getServiceURL()
														+ "/Data/Get_MaxPrice?tableid="
														+ java.net.URLEncoder.encode(saleid)
														+ "&sr="
														+ java.net.URLEncoder.encode(setmenusr));*/

                                        String max_pricetemp = jsonclass.getString(new DatabaseHelper(ctx).getServiceURL() + "/Data/Get_MaxPrice?tranid=" + java.net.URLEncoder.encode(saleid) + "&sr=" + java.net.URLEncoder.encode(setmenusr));
                                        //String max_pricetemp=dbhelper.getSetMenuMaxPricebyitemid(selectedItemModifierObj.getitemid());

                                        Double setmenuamount = Double.valueOf(selectedItemModifierObj.getamount().equals("null") ? "0" : selectedItemModifierObj.getamount());

                                        max_price = Double.valueOf(max_pricetemp == "" ? "0.0" : max_pricetemp);

                                        //if (max_price<=setmenuamount) {
                                        String txtqty = saleItemObj.getqty();
                                        setmenuitemamount = ((max_price) * (int) (Double.parseDouble(txtqty)));

                                        //}else{
                                        //txtamount.setText("0");
                                        //}
                                        itemamount = itemamount + setmenuitemamount;

                                    } else {
                                        itemamount = itemamount + Double.parseDouble(selectedItemModifierObj.getamount());
                                    }

                                    LinearLayout childlayout = (LinearLayout) parentlayout.getChildAt(i++);
                                    TextView txtmodifiersr = (TextView) childlayout.getChildAt(0);
                                    TextView txtmodifierqty = (TextView) childlayout.getChildAt(2);
                                    TextView txtsr = (TextView) childlayout.getChildAt(6);

                                    txtmodifierqty.setTag(Integer.toString((int) Double.parseDouble(selectedItemModifierObj.getqty())));

                                    if (dbhelper.GetModifierOrgQty() == true)//added by SMH on 2017-08-15
                                    {
                                        txtmodifierqty.setText(Integer.toString((int)
                                                ((Double.parseDouble(selectedItemModifierObj.getqty()) / Double.parseDouble(saleItemObj.getqty())))));
                                    } else {
                                        txtmodifierqty.setText(Integer.toString((int)
                                                (Double.parseDouble(selectedItemModifierObj.getqty()))));
                                    }
                                    txtsr.setTag(selectedItemModifierObj.getsr());
                                    if (GlobalClass.use_foodtruck) {
                                        if (modifierSubmitflag)
                                            txtmodifiersr.setTag("P");
                                    } else {
                                        txtmodifiersr.setTag("P");
                                    }
                                }
                            }
                        }
                        // endregion bind modifier items and remark

                        // bind item amount after calculate modifier amount
                        ((TextView) v.getChildAt(3)).setText(getCurrencyFormat(Double.toString(itemamount)));

                        List<UnitCodeObj> unitcodeobjlist = dbhelper.getunitcodebyunittype(itemobj.get(0).getcode(), saleItemObj.getunittype());

                        if (unitcodeobjlist.size() > 0) {
                            ((TextView) v.getChildAt(2)).setText((Integer.toString((int)
                                    (Double.parseDouble(saleItemObj.getqty())))) + " " + unitcodeobjlist.get(0).getshortname());
                            ((TextView) v.getChildAt(4)).setTag(unitcodeobjlist.get(0).getunit());
                        } else {
                            ((TextView) v.getChildAt(2)).setText(saleItemObj.getqty().replace(".0", "").trim());
                        }

                        //((TextView) v.getChildAt(0)).setText(saleItemObj.getsrno() + ".");    //modified by ZYP [04-03-2020]
                        ((TextView) v.getChildAt(2)).setTag(Double.parseDouble(saleItemObj.getqty()));
                        ((TextView) v.getChildAt(6)).setTag(Integer.parseInt(saleItemObj.getsr()));

                        // bind for cancel item with strike through
                        if (saleItemObj.getcancelflag()) {
                            ((TextView) v.getChildAt(0)).setTag("CP");
                            ((TextView) v.getChildAt(1))
                                    .setPaintFlags(((TextView) v.getChildAt(1))
                                            .getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                            ((TextView) v.getChildAt(2))
                                    .setPaintFlags(((TextView) v.getChildAt(2))
                                            .getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                            ((TextView) v.getChildAt(3))
                                    .setPaintFlags(((TextView) v.getChildAt(3))
                                            .getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        } else {
                            //modified by ZYP [06-01-2021]
                            if (GlobalClass.use_foodtruck) {
                                if (saleItemObj.getSubmitflag()) {
                                    ((TextView) v.getChildAt(0)).setTag("P");
                                    ((TextView) v.getChildAt(8)).setText(saleItemObj.getFire_sr());//added by AungMyoTun
                                    ((TextView) v.getChildAt(9)).setText(saleItemObj.getFired());

                                } else {
                                    for (int i = 0; i < v.getChildCount(); i++) {
                                        ((TextView) v.getChildAt(i)).setTextColor(Color.parseColor("#2254EE"));
                                    }
                                }

                            } else {
                                ((TextView) v.getChildAt(0)).setTag("P");
                                ((TextView) v.getChildAt(8)).setText(saleItemObj.getFire_sr());//added by AungMyoTun
                                ((TextView) v.getChildAt(9)).setText(saleItemObj.getFired());
                            }
                        }
                        // ((TextView)v.getChildAt(0)).setTag("P");
                        //maxsr = Integer.parseInt(saleItemObj.getsrno()); //modified by ZYP [04-02-2020] for sr skip

                        if (saleItemObj.gettaway().equals("true")) {
                            v.setBackgroundColor(Color.parseColor("#a10099"));
                            ((TextView) v.getChildAt(5)).setTag(true);
                        }
                        // added by WaiWL on 24/07/2015
                        if (saleItemObj.getisFinishedItem().equals(true)) {
                            v.setBackgroundColor(Color.parseColor("#dddbdb"));
                            ((TextView) v.getChildAt(7)).setTag(true);
                        }
                        // ////
                    }
                    // added by WaiWL on 18/08/2015
                    if (valueitemlistslayout.getChildCount() > 0)
                        txtDiff.setVisibility(View.VISIBLE);
                    else
                        txtDiff.setVisibility(View.INVISIBLE);
                    // ////
                }
            }
            // endregion bind saleItemData
            CalculateTotal();

        } else {
            // new voucher
//            txtDocID.setText("docid");
//            txtDocID.setTag(dbhelper.getwaiterid());
//
//            String item = "------";
//
//            ((Button) findViewById(R.id.butconfirm)).setVisibility(View.GONE);
//            ((Button) findViewById(R.id.butbill)).setVisibility(View.GONE);
//            ((Button) findViewById(R.id.butsplitbill)).setVisibility(View.GONE);
//            txtinvoiceno.setText(item);

            txtDocID.setText("docid");
            txtDocID.setTag(dbhelper.getwaiterid());
            txtinvoiceno.setText("------");
            chkTaxfree.setEnabled(true);

            isNewVoucher = true;

            ((Button) findViewById(R.id.butconfirm)).setVisibility(View.GONE);
            ((Button) findViewById(R.id.butbill)).setVisibility(View.GONE); //added by MPPA [11-06-2021]

            try {
                GetTable(txtinvoiceno.getText().toString());
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        /// Editing Section(....)
        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        if (sharedpreferences.contains(menu_tranid)) {
            tranid = Integer.parseInt(sharedpreferences.getString(menu_tranid, "0"));
        }

        if (tranid != 0) {
            PosUser posuser = dbhelper.getPosUserByUserID(Integer.parseInt(dbhelper.getwaiterid()));
            int userid = posuser.getUserId();
//			do something
            ArrayList<MenuOrderObj> menuorderlist = new ArrayList<MenuOrderObj>();
            menuorderlist = dbhelper.checkOrder(tranid, userid);

            if (menuorderlist.size() > 0) {
                for (MenuOrderObj item : menuorderlist) {
                    Button b = new Button(ctx);
                    b.setTag(item.getCode());
                    b.setText(item.getDescription());

                    final TextView txtDiff = (TextView) findViewById(R.id.txtlineDifference);

                    if (dbhelper.isValueItem(((TextView) b).getTag().toString())) {
                        txtDiff.setVisibility(View.VISIBLE);
                        LinearLayout vItemLayout = selectMenuvalueItemtolist(
                                ((TextView) b).getTag().toString(), ((TextView) b)
                                        .getText().toString(), "0", item.getQty());
                    } else {
                        txtDiff.setVisibility(View.INVISIBLE);
                        itemlayout = selectMenuItemtolist(((TextView) b).getTag()
                                        .toString(), ((TextView) b).getText().toString(),
                                "0", true, item.getQty());
                    }

                    Button butitem = new Button(ctx);
                    butitem.setText(((TextView) b).getText().toString());
                    butitem.setTag(((TextView) b).getTag().toString());

                    List<SelectedItemModifierObj> itemsobjlist = dbhelper
                            .getsetmenuItemslistbyitemid(((TextView) b).getTag().toString());

                    if (itemsobjlist.size() > 0) {
                        BindModifieritemtolist(itemlayout, itemsobjlist, false);
                    }
                    if (modifierpop == 1) {
                        extraitemdialog(butitem, itemlayout);
                    }

                }

                CalculateTotal();

                dbhelper.updateMenuOrder(tranid, userid);
                dbhelper.insert_menutable(tranid, userid, tableID);

                dbhelper.deleteOrder(tranid, userid); // delete order

                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(menu_tranid, Integer.toString(0));
                editor.commit();
            }
        }
    }

    public void bindstatus() {
        TextView txtusername = (TextView) findViewById(R.id.txtusername);
        txtusername.setTypeface(font);

        PosUser posuser = dbhelper.getPosUserByUserID(Integer.parseInt(dbhelper.getwaiterid()));
        String posusername = dbhelper.getwaitername() + " - " + posuser.getName();

        txtusername.setText(posusername);
        txtusername.setTag(dbhelper.getwaiterid());
        getBatteryPercentage();
        // ConnectionStatus();
    }

    public void checkconnection_onClick(View v) {
        ConnectionStatus();
    }

    public void ConnectionStatus() {
        ((ImageView) findViewById(R.id.constatus)).setImageResource(0);
        if (GlobalClass.CheckConnection(this)) {
            ((ImageView) findViewById(R.id.constatus))
                    .setImageResource(R.drawable.greenstatus);
        } else {
            ((ImageView) findViewById(R.id.constatus))
                    .setImageResource(R.drawable.redstatus);
        }

        getBatteryPercentage();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && GlobalClass.CheckConnection(ctx)) {

            boolean needsubmit = false;
            LinearLayout itemlistlayout = (LinearLayout) findViewById(R.id.itemlistlayout); // added by WaiWL on 12/08/2015
            LinearLayout valueitemlistlayout = (LinearLayout) findViewById(R.id.valueitem_itemlistlayout);

            for (int i = 0; i < valueitemlistlayout.getChildCount(); i++) {
                LinearLayout linearlayout = (LinearLayout) valueitemlistlayout.getChildAt(i);
                LinearLayout mainrow = (LinearLayout) linearlayout.getChildAt(0);// Get main item row
                TextView txtsrrow = (TextView) mainrow.getChildAt(0);
                if (!txtsrrow.getTag().toString().contains("P")) {
                    needsubmit = true;
                }
            }
            // /////////////////////////

            for (int i = 0; i < itemlistlayout.getChildCount(); i++) {
                LinearLayout linearlayout = (LinearLayout) itemlistlayout.getChildAt(i);
                LinearLayout mainrow = (LinearLayout) linearlayout.getChildAt(0);// Get main item row
                TextView txtsrrow = (TextView) mainrow.getChildAt(0);
                if (!txtsrrow.getTag().toString().contains("P")) {
                    needsubmit = true;
                }
            }

            if (initialitemcount < itemlistlayout.getChildCount() || needsubmit == true) {
                AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setTitle("Warning!");
                alertDialog.setMessage("Are you sure?\nYou've ordered some items, not submited yet!");
                alertDialog.setIcon(0);
                final Context ctx = this;
                // Setting OK Button
                alertDialog.setButton2("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // TODO Auto-generated method stub

                                dbhelper.DeleteSaleDataBySaleID(saleid);
                                dbhelper.DeleteSaleItemDataBySaleID(saleid);

                                //added by ZYP [26-10-2020]
                                if (GlobalClass.use_foodtruck) {
                                    Intent intent = new Intent(ctx, MainScreen.class);
                                    startActivity(intent);
                                } else {
                                    Intent intent = new Intent(ctx, TableScreenActivity.class);
                                    startActivity(intent);
                                }

                                if (GlobalClass.CheckConnection(ctx)) {
                                    Json_class jsonclass = new Json_class();
                                    jsonclass.getString(dbhelper
                                            .getServiceURL()
                                            + "/Data/ReleaseTable?tableid="
                                            + java.net.URLEncoder.encode(tableID.equals("Parcel") ? "0" : tableID)//added by ZYP [21-10-2020]
                                            + "&userid="
                                            + java.net.URLEncoder.encode(dbhelper.getwaiterid()));
                                }

                                //added WHM [2020-05-13]
                                if (dbhelper.use_deliverymanagement() == true) {
                                    dbhelper.ClearTable("Delivery_Entry_Detail_Tmp");
                                }

                                finish();
                            }
                        });
                alertDialog.setButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                            }
                        });
                alertDialog.show();

            } else {
                //added by ZYP [26-10-2020]
                if (GlobalClass.use_foodtruck) {
                    Intent intent = new Intent(ctx, MainScreen.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(ctx, TableScreenActivity.class);
                    startActivity(intent);
                }

                if (GlobalClass.CheckConnection(ctx)) {
                    Json_class jsonclass = new Json_class();
                    jsonclass.getString(dbhelper
                            .getServiceURL()
                            + "/Data/ReleaseTable?tableid="
                            + java.net.URLEncoder.encode(tableID.equals("Parcel") ? "0" : tableID)//added by ZYP [21-10-2020]
                            + "&userid="
                            + java.net.URLEncoder.encode(dbhelper.getwaiterid()));
                }
                //added WHM [2020-05-13]
                if (dbhelper.use_deliverymanagement() == true) {
                    dbhelper.ClearTable("Delivery_Entry_Detail_Tmp");
                }
                this.finish();
            }
            return true;
        } else {
            ConnectionStatus();
            showAlertDialog(ctx, "Warning!", "No connection with Server.", false);
        }
        // ConnectionStatus();
        return super.onKeyDown(keyCode, event);

    }

    /*
     * public void onWindowFocusChanged(boolean hasFocus) {
     * super.onWindowFocusChanged(hasFocus); if(!hasFocus) { Intent closeDialog
     * = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
     * sendBroadcast(closeDialog); } checkConnectionStatus(); }
     */
    public String GetMealType() //Added by SMH on 29/05/2017
    {
        String str = "";

        if (MealType1 == true && MealType2 == false && MealType3 == false) {
            str = " And MealType1 ='true' ";
        } else if (MealType1 == true && MealType2 == true && MealType3 == false) {
            str = " And (MealType1 ='true' or MealType2 ='true') ";
        } else if (MealType1 == true && MealType2 == true && MealType3 == true) {
            str = " And (MealType1 ='true' or MealType2 ='true' or MealType3 ='true') ";
        } else if (MealType1 == false && MealType2 == true && MealType3 == false) {
            str = " And ( MealType2 ='true') ";
        } else if (MealType1 == false && MealType2 == true && MealType3 == true) {
            str = " And ( MealType2 ='true' or MealType3 ='true') ";
        } else if (MealType1 == false && MealType2 == false && MealType3 == true) {
            str = " And (MealType3 ='true') ";
        } else if (MealType1 == true && MealType2 == false && MealType3 == true) {
            str = " And (MealType1 ='true' or  MealType3 ='true') ";
        }
        return str;
    }

    public void BindClasscategoryButtons() {
        if (MealType1 == true) {
        }
        String str = GetMealType();

        if (singleimageitem == 1) //Added by Arkar Moe on [22/02/2017]
        {
            ((Button) findViewById(R.id.butitemselect)).setVisibility(View.VISIBLE);

        } else {

            ((Button) findViewById(R.id.butitemselect)).setVisibility(View.GONE);
        }

        itemwidth = dbhelper.getTableSize();
        if (useitemclass == 1) {
            List<ItemClassObj> classobjlist;
//			if (MealType1 ==true)
//			{
//		    classobjlist = dbhelper.getItemMealType1();
//			}else
            //{
            classobjlist = dbhelper.getItemClasslist(str); // get
            //}															// all
            // item
            // class

            LinearLayout classlayout = (LinearLayout) findViewById(R.id.classcategorylayout); // get
            // layout
            classlayout.removeAllViews();// remove all view in layout

            //added by EKK on 14-12-2019
            List<Promotion> promoList = dbhelper.getPromotionList();

            if (promoList.size() > 0) {
                List<ItemClassObj> promoObjlist = dbhelper.getPromotionItemClasslist();

                for (ItemClassObj itemClassObj : promoObjlist) {

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            GlobalClass.getDPsize(itemwidth[1], this),
                            LayoutParams.FILL_PARENT);
                    params.weight = 1.0f;
                    params.setMargins(0, GlobalClass.getDPsize(2, ctx),
                            GlobalClass.getDPsize(3, ctx), 0);

                    classlayout.addView(CreateClassButton(itemClassObj),
                            params);
                }
            }

            // added by WaiWL on 03/06/2015
            List<SpecialMenuObj> smenucategorylist = dbhelper.getSpecialMenuItemCategorylist(); // get all special menu
            // for counting

            if (smenucategorylist.size() > 0) {
                List<ItemClassObj> specialmenuclassobjlist = dbhelper
                        .getSpecialmenuItemClasslist();// get class for special
                // menu category

                for (ItemClassObj itemClassObj : specialmenuclassobjlist) {// itemclass
                    // in
                    // itemclasslist
                    LinearLayout.LayoutParams par = new LinearLayout.LayoutParams(
                            GlobalClass.getDPsize(itemwidth[1], this),
                            LayoutParams.FILL_PARENT);
                    par.weight = 1.0f;
                    par.setMargins(0, GlobalClass.getDPsize(2, ctx),
                            GlobalClass.getDPsize(3, ctx), 0);
                    classlayout.addView(CreateClassButton(itemClassObj), par);
                }
            }
            ///////////////

            for (ItemClassObj itemClassObj : classobjlist) {// itemclass in itemclasslist
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        GlobalClass.getDPsize(itemwidth[1], this),
                        LayoutParams.FILL_PARENT);
                params.weight = 1.0f;
                params.setMargins(0, GlobalClass.getDPsize(2, ctx),
                        GlobalClass.getDPsize(3, ctx), 0);
                classlayout.addView(CreateClassButton(itemClassObj), params);
            }

            //added by EKK on 14-12-2019
            if (promoList.size() > 0 && dbhelper.getuse_Promotion() == true) //modified WHM [2020-03-31]
            {
                Button btn = (Button) classlayout.getChildAt(0);
                activecategory = new Button(this);
                BindItemButton(btn, "Today Promotion", str);
            }
            // added by WaiWL on 02/06/2015
            else if (smenucategorylist.size() > 0) {
                Button btn = (Button) classlayout.getChildAt(0);
                activecategory = new Button(this);
                BindItemButton(btn, "Specialmenu Category", str);
            } else {// ///////
                if (classobjlist.size() > 0) // Click First Category
                {
                    Button btn = (Button) classlayout.getChildAt(0);
                    activecategory = new Button(this);
                    BindItemButton(btn, "Category", str);
                }
            }
        } else if (useitemclass == 0) {
            // added by WaiWL on 27/05/2015
            LinearLayout classlayout = (LinearLayout) findViewById(R.id.classcategorylayout); // get
            // layout
            classlayout.removeAllViews();// remove all view in layout

            //added by EKK on 14-12-2019
            List<Promotion> promoList = dbhelper.getPromotionList();

            if (promoList.size() > 0) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        GlobalClass.getDPsize(itemwidth[1], this),
                        LayoutParams.FILL_PARENT);
                params.weight = 1.0f;
                params.setMargins(0, GlobalClass.getDPsize(2, ctx),
                        GlobalClass.getDPsize(3, ctx), 0);


                classlayout.addView(CreatePromotionButton(),
                        params);
            }

            List<SpecialMenuObj> specialmenuobjlist;

            //specialmenuobjlist = dbhelper.getItemCategorylistbyMealType1();

            specialmenuobjlist = dbhelper.getSpecialMenuItemCategorylist(); // get


//			List<SpecialMenuObj> specialmenuobjlist = dbhelper
//					.getSpecialMenuItemCategorylist(); // get all special menu

            for (SpecialMenuObj specialmenuObj : specialmenuobjlist) {// itemclass
                // in
                // itemclasslist
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        GlobalClass.getDPsize(itemwidth[1], this),
                        LayoutParams.FILL_PARENT);
                params.weight = 1.0f;
                params.setMargins(0, GlobalClass.getDPsize(2, ctx),
                        GlobalClass.getDPsize(3, ctx), 0);
                classlayout.addView(CreateSpecialmenuButton(specialmenuObj),
                        params);
            }

            // /////////////////////

            List<ItemCategoryObj> classobjlist = dbhelper.getItemCategorylist(str); // get
            // all
            // item
            // class

            // commented and moved above by WaiWL on 27/05/2015
            // LinearLayout classlayout =
            // (LinearLayout)findViewById(R.id.classcategorylayout); //get
            // layout
            // classlayout.removeAllViews();//remove all view in layout

            for (ItemCategoryObj itemClassObj : classobjlist) {// itemclass in
                // itemclasslist
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        GlobalClass.getDPsize(itemwidth[1], this),
                        LayoutParams.FILL_PARENT);
                params.weight = 1.0f;
                params.setMargins(0, GlobalClass.getDPsize(2, ctx),
                        GlobalClass.getDPsize(3, ctx), 0);
                classlayout.addView(CreateCategoryButton(itemClassObj), params);
            }

            //added by EKK on 14-12-2019
            if (promoList.size() > 0 && dbhelper.getuse_Promotion() == true) //modified WHM [2020-03-31])
            {
                Button btn = (Button) classlayout.getChildAt(0);
                activecategory = new Button(this);
                BindItemButton(btn, "Today Promotion", str);
            }
            // added by WaiWL on 03/06/2015
            else if (specialmenuobjlist.size() > 0) // Click First Category
            {
                Button btn = (Button) classlayout.getChildAt(0);
                activecategory = new Button(this);
                BindItemButton(btn, "Specialmenu Category", str);
            } else {// /////////////
                if (classobjlist.size() > 0) // Click First Category
                {
                    Button btn = (Button) classlayout.getChildAt(0);
                    activecategory = new Button(this);
                    BindItemButton(btn, "Category", str);
                }
            }
        } else if (useitemclass == 2) {

            List<ItemCategoryObj> categoryitemlist;
//			if (MealType1 ==true)
//			{
//				categoryitemlist = dbhelper.getItemCategorylistbyMealType("");
//			}else
//			{
            categoryitemlist = dbhelper.getItemCategorylist(str); // get
            //}

            //added by EKK on 14-12-2019
            List<Promotion> promoList = dbhelper.getPromotionList();


            // get all item class
            // added by WaiWL on 03/06/2015
            List<SpecialMenuObj> specialmenuobjlist = dbhelper
                    .getSpecialMenuItemCategorylist(); // get all special menu
            // ///////////

            LinearLayout classlayout = (LinearLayout) findViewById(R.id.itemlayout); // get
            // layout
            classlayout.removeAllViews();// remove all view in layout

            HorizontalScrollView categoryschollview = (HorizontalScrollView) findViewById(R.id.CategoryScrollView);
            categoryschollview.setLayoutParams(new LayoutParams(
                    LayoutParams.WRAP_CONTENT, 1));

            final LinearLayout.LayoutParams layoutparams = new LinearLayout.LayoutParams(
                    LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
            layoutparams.weight = 1.0f;

            final RelativeLayout singleimagelayout = (RelativeLayout) findViewById(R.id.singleimagelayout); //added by ZYP [18-02-2020]
            final HorizontalScrollView itemscrolllayout = (HorizontalScrollView) findViewById(R.id.ItemScrollView);

            if (singleimageitem == 1) {
                itemscrolllayout.setVisibility(View.GONE);
                singleimagelayout.setVisibility(View.VISIBLE);

            } else {
                singleimagelayout.setVisibility(View.GONE);
                itemscrolllayout.setVisibility(View.VISIBLE);
            }

            final LinearLayout.LayoutParams butparams = new LinearLayout.LayoutParams(
                    GlobalClass.getDPsize(itemwidth[1], this),
                    LayoutParams.FILL_PARENT);
            butparams.setMargins(0, GlobalClass.getDPsize(2, ctx),
                    GlobalClass.getDPsize(3, ctx), 0);
            int rowcount = 6; // how many item row on screen
            int colcount = (categoryitemlist.size() + specialmenuobjlist.size())
                    / rowcount;
            int k = 0;
            int l = 0;
            int m = 0;
            if ((categoryitemlist.size() + specialmenuobjlist.size())
                    % rowcount != 0)// + specialmenuobjlist.size()
            {
                colcount = colcount + 1;
            }

            if ((categoryitemlist.size() + specialmenuobjlist.size() + promoList.size()) <= 4)// how
            // many
            // button
            // do
            // u
            // wish
            // to
            // apply
            // in
            // a
            // row
            {
                LinearLayout lay = new LinearLayout(ctx);
                lay.setOrientation(LinearLayout.HORIZONTAL);

                //added by EKK on 14-12-2019
                if (promoList.size() > 0) {
                    lay.addView(CreatePromotionButton(),
                            butparams);
                }

                // added by WaiWL on 03/06/2015
                for (SpecialMenuObj specialmenuObj : specialmenuobjlist) {// itemclass
                    // in
                    // itemclasslist
                    lay.addView(CreateSpecialmenuButton(specialmenuObj),
                            butparams);
                }
                // ///////
                for (ItemCategoryObj itemsObj : categoryitemlist) {
                    lay.addView(CreateCategoryButton(itemsObj), butparams);
                }

                final LinearLayout.LayoutParams para = new LinearLayout.LayoutParams(
                        LayoutParams.FILL_PARENT,
                        GlobalClass.getDPsize(90, ctx));
                classlayout.addView(lay, para);
            } else {
                //added by EKK on 14-12-2019


                for (int i = 0; i < rowcount; i++) {
                    LinearLayout layout = new LinearLayout(ctx);
                    layout.setOrientation(LinearLayout.HORIZONTAL);


                    for (int j = 0; j < colcount; j++) {

                        if (m == 0 & promoList.size() > 0) {
                            layout.addView(CreatePromotionButton(), butparams);
                            m++;
                        }


                        // added by WaiWL on 03/06/2015
                        if (l < specialmenuobjlist.size()) {
                            SpecialMenuObj specialmenuObj = specialmenuobjlist
                                    .get(l++);
                            layout.addView(
                                    CreateSpecialmenuButton(specialmenuObj),
                                    butparams);
                        }
                        // ///////////
                        else if (k < categoryitemlist.size()) {
                            ItemCategoryObj itemobj = categoryitemlist.get(k++);
                            layout.addView(CreateCategoryButton(itemobj),
                                    butparams);
                        }
                    }
                    classlayout.addView(layout, layoutparams);
                }
            }
        }
    }

    public Button CreateClassButton(ItemClassObj itemClassObj) {
        Button button = new Button(this);// get new button
        button.setText(itemClassObj.getclassName().toUpperCase());
        button.setTag(itemClassObj.getclassid());
        button.setTextAppearance(this, R.style.categorybutton_text);
        button.setBackgroundResource(R.drawable.category_button);

        button.setSingleLine(true);

        button.setTypeface(font);
        button.setPadding(0, 0, 0, 0);
        button.setGravity(Gravity.CENTER);

        button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                String str = GetMealType();
                List<ItemCategoryObj> categorylist = dbhelper
                        .getItemCategorylistByClassid(((Button) v).getTag()
                                .toString(), str);

                //added by EKK on 14-12-2019
                List<Promotion> promoList = dbhelper.getPromotionList();

                // added by WaiWL on 03/06/2015
                List<SpecialMenuObj> specialmenucategorylist = dbhelper
                        .getSpecialMenuItemCategorylist(); // get all special
                // menu
                // //////////

                LinearLayout classlayout = (LinearLayout) findViewById(R.id.classcategorylayout);
                classlayout.removeAllViews();

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        GlobalClass.getDPsize(170, ctx),
                        LayoutParams.FILL_PARENT);
                params.weight = 1.0f;
                params.setMargins(0, 2, 3, 0);

                // region bind class back button
                ImageButton button = new ImageButton(ctx);

                button.setBackgroundColor(Color.parseColor("#008000"));
                button.setImageResource(R.drawable.back);
                button.setScaleType(ScaleType.FIT_CENTER);

                button.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        BindClasscategoryButtons();
                    }
                });
                classlayout.addView(button, params);
                // endregion bind class back button

                //added by EKK on 14-12-2019
                if (Integer.valueOf(((Button) v).getTag().toString()) == -1) {

                    // category
                    // button
                    classlayout
                            .addView(
                                    CreatePromotionButton(),
                                    params);
                }

                // added by WaiWL on 03/06/2015
                else if (Integer.valueOf(((Button) v).getTag().toString()) == 0) {
                    for (SpecialMenuObj specialmenuitemcategoryobj : specialmenucategorylist) { // bind
                        // category
                        // button
                        classlayout
                                .addView(
                                        CreateSpecialmenuButton(specialmenuitemcategoryobj),
                                        params);
                    }
                } else {// /////
                    for (ItemCategoryObj itemcategoryobj : categorylist) { // bind
                        // category
                        // button
                        classlayout.addView(
                                CreateCategoryButton(itemcategoryobj), params);
                    }
                }

                //added by EKK on 14-12-2019
                if (Integer.valueOf(((Button) v).getTag().toString()) == -1) {
                    Button btn = (Button) classlayout.getChildAt(1);
                    BindItemButton(btn, "Today Promotion", str);
                }
                // /////////added by WaiWL on 03/06/2015
                else if (Integer.valueOf(((Button) v).getTag().toString()) == 0) {
                    Button btn = (Button) classlayout.getChildAt(1); // Click
                    // for
                    // first
                    // special
                    // menu
                    // category
                    BindItemButton(btn, "Specialmenu Category", str);
                } else {// ////////
                    Button btn = (Button) classlayout.getChildAt(1); // Click
                    // for
                    // first
                    // category
                    BindItemButton(btn, "Category", str);
                }

            }
        });
        return button;
    }

    public Button CreateCategoryButton(ItemCategoryObj itemcategoryobj) {
        Button butcategory = new Button(ctx);
        butcategory.setText(itemcategoryobj.getcategoryname());
        butcategory.setTag(itemcategoryobj.getcategoryid());
        butcategory.setTextAppearance(ctx, R.style.categorybutton_text);
        // butcategory.setBackgroundResource(R.drawable.category_button);
        if (itemcategoryobj.getcolorRGB().equals("null")
                || itemcategoryobj.getcolorRGB().equals(null)) {
            butcategory.setBackgroundColor(Color.parseColor("#a10099"));
        } else
            butcategory.setBackgroundColor(Color.parseColor(itemcategoryobj.getcolorRGB()));

        butcategory.setTypeface(font);
        butcategory.setSingleLine(true);
        butcategory.setPadding(0, 0, 0, 0);
        butcategory.setGravity(Gravity.CENTER);
        butcategory.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                String str = GetMealType();
                BindItemButton(((Button) v), "Category", str);
            }
        });

        return butcategory;
    }

    //added by EKK on 14-12-2019
    public Button CreatePromotionButton() {


        Button butcategory = new Button(ctx);
        butcategory.setText("Today Promotion");
        butcategory.setTag(1);
        butcategory.setTextAppearance(ctx, R.style.categorybutton_text);
        butcategory.setBackgroundColor(Color.parseColor("#008000"));
        butcategory.setTypeface(font);
        butcategory.setSingleLine(true);
        butcategory.setPadding(0, 0, 0, 0);
        butcategory.setGravity(Gravity.CENTER);
        butcategory.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                String str = GetMealType();
                BindItemButton(((Button) v), "Today Promotion", str);
            }
        });

        return butcategory;
    }

    // added by WaiWL on 27/05/2015
    public Button CreateSpecialmenuButton(SpecialMenuObj specialmenuobj) {
        Button butcategory = new Button(ctx);
        butcategory.setText(specialmenuobj.getMenuName());
        butcategory.setTag(specialmenuobj.getMenuID());
        butcategory.setTextAppearance(ctx, R.style.categorybutton_text);
        // butcategory.setBackgroundResource(R.drawable.category_button);
        // if(specialmenuobj.getcolorRGB().equals("null")||
        // specialmenuobj.getcolorRGB().equals(null))
        // {
        butcategory.setBackgroundColor(Color.parseColor("#008000"));
        // }
        // else
        // butcategory.setBackgroundColor(Color.parseColor(itemcategoryobj.getcolorRGB()));

        butcategory.setTypeface(font);
        butcategory.setSingleLine(true);
        butcategory.setPadding(0, 0, 0, 0);
        butcategory.setGravity(Gravity.CENTER);
        butcategory.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                String str = GetMealType();
                BindItemButton(((Button) v), "Specialmenu Category", str);
            }
        });

        return butcategory;
    }

    // ///////////////

    public void BindItemButton(Button clickbutton, String classorcategory, String str)// id
    // for
    // classid
    // and
    // categoryid
    // ,
    // classorcateogry
    // for
    // is
    // class
    // or
    // category
    // flag
    {
        final LinearLayout itemlayout = (LinearLayout) findViewById(R.id.itemlayout);
        final RelativeLayout singleimagelayout = (RelativeLayout) findViewById(R.id.singleimagelayout);
        final HorizontalScrollView itemscrolllayout = (HorizontalScrollView) findViewById(R.id.ItemScrollView);
        final LinearLayout.LayoutParams layoutparams = new LinearLayout.LayoutParams(
                LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
        layoutparams.weight = 1.0f;
        // for custom width and height of item
        itemwidth = dbhelper.getTableSize();

        //Edited by ArkarMoe on [23/12/2016]
        final LinearLayout.LayoutParams butparams = new LinearLayout.LayoutParams(
                GlobalClass.getDPsize(itemwidth[1], this),
                LayoutParams.FILL_PARENT);

        //final LinearLayout.LayoutParams butparams = new LinearLayout.LayoutParams(
        //953,
        //LayoutParams.FILL_PARENT);

        butparams.setMargins(0, GlobalClass.getDPsize(2, ctx),
                GlobalClass.getDPsize(3, ctx), 0);
        itemlayout.removeAllViews();

        List<ItemsObj> itemobjlist = new ArrayList<ItemsObj>();

        if (!(activecategory == null)) {
            activecategory.setCompoundDrawablesWithIntrinsicBounds(null, null,
                    null, null);
            activecategory.setGravity(Gravity.CENTER);
        }

        if (singleimageitem == 1) {
            classorcategory = "Category";
            itemscrolllayout.setVisibility(View.GONE);
            singleimagelayout.setVisibility(View.VISIBLE);
            ((Button) findViewById(R.id.butitemselect)).setVisibility(View.VISIBLE);

        } else {

            singleimagelayout.setVisibility(View.GONE);
            itemscrolllayout.setVisibility(View.VISIBLE);

            ((Button) findViewById(R.id.butitemselect)).setVisibility(View.GONE);
        }


        if (classorcategory.equals("Class")) {

            itemobjlist = dbhelper.getItemslistbyclassid(clickbutton.getTag().toString(), str);
            activecategory = clickbutton;
            ClassOrCategory = classorcategory;
        } else if (classorcategory.equals("Category")) {
            itemobjlist = dbhelper.getItemslistbycategoryid(clickbutton.getTag().toString(), str);
            activecategory = clickbutton;
            ClassOrCategory = classorcategory;
        }
        // added by WaiWL on 02/06/2015
        else if (classorcategory.equals("Specialmenu Category")) {
            itemobjlist = dbhelper
                    .getSpecialmenuitemlistbycategoryid(clickbutton.getTag().toString());
            activecategory = clickbutton;
            ClassOrCategory = classorcategory;
        }
        //added by EKK on 14-12-2019
        else if (classorcategory.equals("Today Promotion")) {
            itemobjlist = dbhelper.getPromotionItem();
            activecategory = clickbutton;
            ClassOrCategory = classorcategory;
        }
        // //////////////

        if (useitemclass == 2) {
            ItemsObj itemobj = new ItemsObj();
            itemobj.setdescription("BackCategory");
            itemobjlist.add(0, itemobj);
        }

        Drawable top = getResources().getDrawable(R.drawable.selected);
        activecategory.setCompoundDrawablesWithIntrinsicBounds(null, top, null,
                null); // set checkmark on category button
        activecategory.setGravity(Gravity.TOP | Gravity.CENTER);

        int rowcount = 0;
        int colcount = 0;
        int k = 0;

        if (singleimageitem == 1) //Added by ArkarMoe on [23/12/2016]
        {

            CreateViewPagerImage(itemobjlist);
        } else {
            if (toggleview == 0) // use item list with no image
            {

                rowcount = 5; // how many item row on screen
                colcount = itemobjlist.size() / rowcount;
                if (itemobjlist.size() % rowcount != 0) {
                    colcount = colcount + 1;
                }
                if (itemobjlist.size() <= 4)// how many button do u wish to apply in
                // a row
                {

                    LinearLayout lay = new LinearLayout(ctx);
                    lay.setOrientation(LinearLayout.HORIZONTAL);
                    for (ItemsObj itemsObj : itemobjlist) {
                        if (itemsObj.getdescription().equals("BackCategory")) {
                            // region bind class back button
                            ImageButton button = new ImageButton(ctx);

                            button.setBackgroundColor(Color.parseColor("#008000"));
                            button.setImageResource(R.drawable.back);
                            button.setScaleType(ScaleType.FIT_CENTER);
                            button.setOnClickListener(new OnClickListener() {
                                public void onClick(View v) {
                                    BindClasscategoryButtons();
                                }
                            });
                            lay.addView(button, butparams);
                        } else
                            lay.addView(CreateItemButton(itemsObj), butparams);
                    }

                    final LinearLayout.LayoutParams para = new LinearLayout.LayoutParams(
                            LayoutParams.FILL_PARENT,
                            GlobalClass.getDPsize(90, ctx));
                    itemlayout.addView(lay, para);
                } else {
                    for (int i = 0; i < rowcount; i++) {
                        LinearLayout layout = new LinearLayout(ctx);
                        layout.setOrientation(LinearLayout.HORIZONTAL);
                        for (int j = 0; j < colcount; j++) {
                            if (k < itemobjlist.size()) {
                                ItemsObj itemsObj = itemobjlist.get(k++);
                                if (itemsObj.getdescription().equals("BackCategory")) {
                                    // region bind class back button
                                    ImageButton button = new ImageButton(ctx);

                                    button.setBackgroundColor(Color
                                            .parseColor("#008000"));
                                    button.setImageResource(R.drawable.back);
                                    button.setScaleType(ScaleType.FIT_CENTER);
                                    button.setOnClickListener(new OnClickListener() {
                                        public void onClick(View v) {
                                            BindClasscategoryButtons();
                                        }
                                    });
                                    layout.addView(button, butparams);
                                } else
                                    layout.addView(CreateItemButton(itemsObj),
                                            butparams);
                            }
                        }

                        itemlayout.addView(layout, layoutparams);
                    }
                }
            } else if (toggleview == 1) // use item list with image
            {
                if (tabletSize() > 8)
                    rowcount = 3;// how many item row on screen
                else
                    rowcount = 2;// how many item row on screen

                colcount = itemobjlist.size() / rowcount;

                if (itemobjlist.size() % rowcount != 0) {
                    colcount = colcount + 1;
                }

                if (itemobjlist.size() <= 4)// how many button do u wish to apply in
                // a row
                {
                    LinearLayout lay = new LinearLayout(ctx);
                    lay.setOrientation(LinearLayout.HORIZONTAL);
                    for (ItemsObj itemsObj : itemobjlist) {

                        if (itemsObj.getdescription().equals("BackCategory")) {
                            // region bind class back button
                            ImageButton button = new ImageButton(ctx);

                            button.setBackgroundColor(Color.parseColor("#008000"));
                            button.setImageResource(R.drawable.back);
                            button.setScaleType(ScaleType.FIT_CENTER);
                            button.setOnClickListener(new OnClickListener() {
                                public void onClick(View v) {
                                    BindClasscategoryButtons();
                                }
                            });
                            lay.addView(button, butparams);
                        } else
                            lay.addView(CreateItemButtonwithImage(itemsObj),
                                    butparams);
                    }
                    LinearLayout.LayoutParams layoutpara = new LinearLayout.LayoutParams(
                            LayoutParams.FILL_PARENT, GlobalClass.getDPsize(150,
                            ctx));

                    itemlayout.addView(lay, layoutpara);
                } else {
                    for (int i = 0; i < rowcount; i++) {
                        LinearLayout layout = new LinearLayout(ctx);
                        layout.setOrientation(LinearLayout.HORIZONTAL);
                        for (int j = 0; j < colcount; j++) {
                            if (k < itemobjlist.size()) {
                                ItemsObj itemobj = itemobjlist.get(k++);
                                if (itemobj.getdescription().equals("BackCategory")) {
                                    // region bind class back button
                                    ImageButton button = new ImageButton(ctx);

                                    button.setBackgroundColor(Color
                                            .parseColor("#008000"));
                                    button.setImageResource(R.drawable.back);
                                    button.setScaleType(ScaleType.FIT_CENTER);
                                    button.setOnClickListener(new OnClickListener() {
                                        public void onClick(View v) {
                                            BindClasscategoryButtons();
                                        }
                                    });
                                    layout.addView(button, butparams);
                                } else
                                    layout.addView(
                                            CreateItemButtonwithImage(itemobj),
                                            butparams);
                            }
                        }

                        itemlayout.addView(layout, layoutparams);
                    }
                }
            }

        }

        // ConnectionStatus();
    }

    public TextView CreateItemButton(ItemsObj itemsObj) {
        TextView itembutton = new TextView(ctx);

        int checkitem = dbhelper.getitemdescriptionstyle();
        switch (checkitem) {
            case 0:
                itembutton.setText(itemsObj.getdescription());
                break;
            case 1:
                itembutton.setText(itemsObj.getdescription2());
                break;
            case 2:
                itembutton.setText(itemsObj.getdescription3());
                break;
        }
        //itembutton.setText(itemsObj.getdescription());
        itembutton.setTag(itemsObj.getcode());
        itembutton.setTextAppearance(ctx, R.style.itembutton_text);
        itembutton.setBackgroundResource(R.drawable.item_button);

        itembutton.setTypeface(font);
        //itembutton.setSingleLine(true);
        itembutton.setGravity(Gravity.CENTER);

        //added by EKK on 14-12-2019
        if (ClassOrCategory.equals("Today Promotion")) {
            if (dbhelper.isPromotionItem(itemsObj.getcode())) {
                itembutton.setBackgroundColor(Color.parseColor("#008000"));
                String type = dbhelper.getPromotionType(itemsObj.getcode());
                switch (checkitem) {
                    case 0:
                        itembutton.setText(itemsObj.getdescription() + "\n(" + type + ")");
                        break;
                    case 1:
                        itembutton.setText(itemsObj.getdescription2() + "\n(" + type + ")");
                        break;
                    case 2:
                        itembutton.setText(itemsObj.getdescription3() + "\n(" + type + ")");
                        break;
                }
            }
        } else if (dbhelper.isSoldOutItem(itemsObj.getcode())) {
            itembutton.setBackgroundColor(Color.parseColor("#FFFF0000"));
            switch (checkitem) {
                case 0:
                    itembutton.setText(itemsObj.getdescription() + "(Sold Out)");
                    break;
                case 1:
                    itembutton.setText(itemsObj.getdescription2() + "(Sold Out)");
                    break;
                case 2:
                    itembutton.setText(itemsObj.getdescription3() + "(Sold Out)");
                    break;
            }
        } else {
            if (itemsObj.getitemcolorRGB() == null || itemsObj.getitemcolorRGB().equals("")
                    || itemsObj.getitemcolorRGB().equals(null)) {
                itembutton.setBackgroundColor(Color.parseColor("#008000"));
                //itembutton.setBackgroundResource(R.drawable.roundbutton7);
            } else
                itembutton.setBackgroundColor(Color.parseColor(itemsObj.getitemcolorRGB()));

        }


        itembutton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                ConnectionStatus();
                if (!GlobalClass.CheckConnection(ctx)) {

                    showAlertDialog(ctx, "Warning!",
                            "No connection with Server.", false);
                    return;
                }
                if (dbhelper.isSoldOutItem(((TextView) v).getTag().toString())) {


                    AlertDialog alertDialog = new AlertDialog.Builder(
                            ctx).create();
                    alertDialog.setTitle("Message");
                    alertDialog
                            .setMessage("This is SoldOut Item!");
                    alertDialog.setIcon(0);

                    alertDialog.setButton2("OK", new DialogInterface.OnClickListener() {
                        public void onClick(
                                DialogInterface dialog,
                                int which) {
                            //customer_setup(txtGuideName);
                        }
                    });

                    alertDialog.show();


                } else {
                    if (viewdetail) {
                        Itemdetail_dialog(v);
                        return;
                    }

                    // added by WaiWL on 11/08/2015
                    final TextView txtDiff = (TextView) findViewById(R.id.txtlineDifference);
                    String code = ((TextView) v).getTag().toString();
                    String type = dbhelper.getPromotionType(code);
                    String description = ((TextView) v).getText().toString().replace("(" + type + ")", "");

                    if (dbhelper.isValueItem(((TextView) v).getTag().toString())) {
                        // new layout for valueitem
                        txtDiff.setVisibility(View.VISIBLE);

                        //modified by EKK on 14-12-2019
                        if (dbhelper.isPromotionItem(code)) {
                            LinearLayout vItemLayout = selectvalueItemtolist(
                                    ((TextView) v).getTag().toString(), description, "0");
                        } else {
                            LinearLayout vItemLayout = selectvalueItemtolist(
                                    ((TextView) v).getTag().toString(), ((TextView) v)
                                            .getText().toString(), "0");
                        }

                    } else {// ////////
                        txtDiff.setVisibility(View.INVISIBLE);

                        //modified by EKK on 14-12-2019

                        if (dbhelper.isPromotionItem(code)) {
                            itemlayout = selectItemtolist(((TextView) v).getTag().toString(), description,
                                    "0", true);
                        } else {
                            itemlayout = selectItemtolist(((TextView) v).getTag().toString(), ((TextView) v).getText().toString(),
                                    "0", true);
                        }


                    }

                    Button butitem = new Button(ctx);
                    butitem.setText(((TextView) v).getText().toString());
                    butitem.setTag(((TextView) v).getTag().toString());

                    List<SelectedItemModifierObj> itemsobjlist = dbhelper
                            .getsetmenuItemslistbyitemid(((TextView) v).getTag().toString());

                    if (itemsobjlist.size() > 0) {
                        BindModifieritemtolist(itemlayout, itemsobjlist, true);
                    }
                    if (modifierpop == 1) {
                        extraitemdialog(butitem, itemlayout);
                    }
                    CalculateTotal();
                }
            }

        });

        itembutton.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View view) {
                Itemdetail_dialog(view);
                return false;
            }
        });

        return itembutton;
    }

    public LinearLayout CreateItemButtonwithImage(final ItemsObj itemsObj) {
        final LinearLayout.LayoutParams layoutImage = new LinearLayout.LayoutParams(
                LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);

        layoutImage.weight = 1.0f;

        final LinearLayout.LayoutParams layouttext = new LinearLayout.LayoutParams(
                GlobalClass.getDPsize(170, ctx), GlobalClass.getDPsize(30, ctx));
        //final LinearLayout.LayoutParams layouttext = new LinearLayout.LayoutParams(200,200);

        LinearLayout linlayout = new LinearLayout(ctx);
        linlayout.setOrientation(LinearLayout.VERTICAL);

        TextView txt = new TextView(ctx);
        ImageView image = new ImageView(ctx);

        if (itemsObj.getusr_code() == null)
            itemsObj.setusr_code("null");

        String photoPath = GlobalClass.GetImageStorageLocation(this) + "/"
                + itemsObj.getusr_code().trim() + ".jpg";
        File file = new File(photoPath);

        if (file.exists()) {
            try {
                long length = file.length();
                if (((length / 1024) / 1024) <= 1) {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    //options.inSampleSize = 8;
                    final Bitmap bitmap = BitmapFactory.decodeFile(photoPath,
                            options);
                    Drawable d = new BitmapDrawable(getResources(), bitmap);
                    image.setImageDrawable(d);
                } else {
                    image.setVisibility(View.GONE);
                }

            } catch (Exception ex) {
            }
        } else
            image.setVisibility(View.GONE);

        int checkitem = dbhelper.getitemdescriptionstyle();
        //image.setScaleType(ScaleType.FIT_XY);
        image.setScaleType(ScaleType.FIT_CENTER);
        txt.setText(itemsObj.getdescription());
        txt.setTag(itemsObj.getcode());
        txt.setTextAppearance(ctx, R.style.itembutton_text);
        txt.setTypeface(font);
        txt.setSingleLine(true);
        txt.setGravity(Gravity.CENTER);
        linlayout.setBackgroundResource(R.drawable.item_button);

//        if (itemsObj.getitemcolorRGB() == null || itemsObj.getitemcolorRGB().equals("")
//                || itemsObj.getitemcolorRGB().equals(null)) {
//            linlayout.setBackgroundColor(Color.parseColor("#008000"));
//        } else
//            linlayout.setBackgroundColor(Color.parseColor(itemsObj.getitemcolorRGB()));

        //added by ZYP [10-12-2020]
        if (ClassOrCategory.equals("Today Promotion")) {
            if (dbhelper.isPromotionItem(itemsObj.getcode())) {
                linlayout.setBackgroundColor(Color.parseColor("#008000"));
                String type = dbhelper.getPromotionType(itemsObj.getcode());
                switch (checkitem) {
                    case 0:
                        txt.setText(itemsObj.getdescription() + "\n(" + type + ")");
                        break;
                    case 1:
                        txt.setText(itemsObj.getdescription2() + "\n(" + type + ")");
                        break;
                    case 2:
                        txt.setText(itemsObj.getdescription3() + "\n(" + type + ")");
                        break;
                }
            }
        } else if (dbhelper.isSoldOutItem(itemsObj.getcode())) {
            //itembutton.setBackgroundColor(Color.parseColor("#FFFF0000"));
            linlayout.setBackgroundColor(Color.parseColor("#FFFF0000"));
            switch (checkitem) {
                case 0:
                    txt.setText(itemsObj.getdescription() + "(Sold Out)");
                    break;
                case 1:
                    txt.setText(itemsObj.getdescription2() + "(Sold Out)");
                    break;
                case 2:
                    txt.setText(itemsObj.getdescription3() + "(Sold Out)");
                    break;
            }
        } else {
            if (itemsObj.getitemcolorRGB() == null || itemsObj.getitemcolorRGB().equals("")
                    || itemsObj.getitemcolorRGB().equals(null)) {
                linlayout.setBackgroundColor(Color.parseColor("#008000"));
                //itembutton.setBackgroundResource(R.drawable.roundbutton7);
            } else
                linlayout.setBackgroundColor(Color.parseColor(itemsObj.getitemcolorRGB()));

        }

        linlayout.setGravity(Gravity.CENTER);
        layoutImage.setMargins(1, 1, 1, 1);
        linlayout.addView(image, layoutImage);
        linlayout.addView(txt, layouttext);


        linlayout.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                ConnectionStatus();
                if (!GlobalClass.CheckConnection(ctx)) {

                    showAlertDialog(ctx, "Warning!",
                            "No connection with Server.", false);
                    return;
                }

                if (dbhelper.isSoldOutItem(itemsObj.getcode())) {


                    AlertDialog alertDialog = new AlertDialog.Builder(ctx).create();
                    alertDialog.setTitle("Message");
                    alertDialog.setMessage("This is SoldOut Item!");
                    alertDialog.setIcon(0);

                    alertDialog.setButton2("OK", new DialogInterface.OnClickListener() {
                        public void onClick(
                                DialogInterface dialog,
                                int which) {
                            //customer_setup(txtGuideName);
                        }
                    });

                    alertDialog.show();


                } else {
                    TextView txtitem = (TextView) ((LinearLayout) v).getChildAt(1);

                    if (viewdetail) {
                        Itemdetail_dialog(txtitem);
                        return;
                    }
                    Button butitem = new Button(ctx);
                    butitem.setText(txtitem.getText().toString());
                    butitem.setTag(txtitem.getTag());
                    LinearLayout itemlayout = selectItemtolist(butitem.getTag()
                            .toString(), butitem.getText().toString(), "0", true);
                    if (modifierpop == 1)
                        extraitemdialog(butitem, itemlayout);

                    List<SelectedItemModifierObj> itemsobjlist = dbhelper
                            .getsetmenuItemslistbyitemid(itemsObj.getcode());

                    if (itemsobjlist.size() > 0) {
                        BindModifieritemtolist(itemlayout, itemsobjlist, true);
                    }
                }
            }
        });

        linlayout.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View view) {
                Itemdetail_dialog(view);
                return false;
            }
        });
        return linlayout;
    }

    public void CreateViewPagerImage(List<ItemsObj> itemsObjlist) //Added by ArkarMoe on [28/12/2016]
    {


        for (ItemsObj itemsObj : itemsObjlist) {
            String photoPath = GlobalClass.GetImageStorageLocation(this) + "/"
                    + itemsObj.getusr_code().trim() + ".jpg";
            File file = new File(photoPath);
            if (!file.exists()) {
                photoPath = GlobalClass.GetImageStorageLocation(this) + "/" + "Default_Image.jpg";

            }
            BitmapFactory.Options options = new BitmapFactory.Options();
            //options.inSampleSize = 8;
            //final Bitmap bitmap = BitmapFactory.decodeFile(photoPath,options);

            final Bitmap bitmap = decodeFile(photoPath);

            Drawable d = new BitmapDrawable(getResources(), bitmap);
            itemsObj.setimgdrawable(d);

        }


        viewPager = (ViewPager) findViewById(R.id.pager);

        adapter = new ViewPageAdapter(OrderTaking.this, itemsObjlist);

        viewPager.setAdapter(adapter);

    }

    public static Bitmap decodeFile(String pathName) {
        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        for (options.inSampleSize = 1; options.inSampleSize <= 32; options.inSampleSize++) {
            try {
                bitmap = BitmapFactory.decodeFile(pathName, options);
                //Log.d(TAG_LOG, "Decoded successfully for sampleSize " + options.inSampleSize);
                break;
            } catch (OutOfMemoryError outOfMemoryError) {
                // If an OutOfMemoryError occurred, we continue with for loop and next inSampleSize value
                //Log.e(TAG_LOG, "outOfMemoryError while reading file for sampleSize " + options.inSampleSize
                //+ " retrying with higher value");
            }
        }
        return bitmap;
    }

    public LinearLayout selectItemtolist(String itemid, String itemname) {


        LinearLayout itemlistlayout = (LinearLayout) findViewById(R.id.itemlistlayout);
        final LinearLayout itemlayout = new LinearLayout(this);
        final LayoutParams itemlayoutpara = new LayoutParams(
                LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);

        final LayoutParams srlayoutpara = new LayoutParams(
                GlobalClass.getDPsize(20, this), LayoutParams.WRAP_CONTENT);

        final LayoutParams itemnamelayoutpara = new LayoutParams(
                GlobalClass.getDPsize(140, this), LayoutParams.WRAP_CONTENT);

        final LayoutParams qtylayoutpara = new LayoutParams(
                GlobalClass.getDPsize(40, this), LayoutParams.WRAP_CONTENT);

        final LayoutParams amountlayoutpara = new LayoutParams(
                GlobalClass.getDPsize(90, this), LayoutParams.WRAP_CONTENT);

        TextView txtsrno = new TextView(this);
        maxsr = maxsr + 1;
        txtsrno.setText((Integer.toString(maxsr)) + ".");
        txtsrno.setTag("N");
        txtsrno.setPadding(0, 2, 0, 2);

        txtsrno.setTypeface(font);
        txtsrno.setGravity(Gravity.LEFT);
        txtsrno.setTextColor(Color.parseColor("#000000"));
        txtsrno.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (selecteditemlayout != null) {
                    // added by WaiWL on 24/07/2015
                    TextView txttaway = (TextView) selecteditemlayout
                            .getChildAt(5);
                    if (txttaway.getTag().equals(false))
                        selecteditemlayout
                                .setBackgroundColor(Color.TRANSPARENT);
                }
                if (clickChecked) {
                    selecteditemlayout = itemlayout;
                    itemlayout.setBackgroundColor(Color.parseColor("#d9522c"));
                    CreatingedititemDialog(v);
                    // CalculateTotal();
                }

            }
        });

        TextView txtitemname = new TextView(this);
        txtitemname.setText(itemname);
        txtitemname.setTag(itemid);
        txtitemname.setPadding(0, 2, 0, 2);
        // txtitemname.setTypeface(font,Typeface.BOLD);
        txtitemname.setTypeface(font);
        txtitemname.setGravity(Gravity.LEFT);
        txtitemname.setTextColor(Color.parseColor("#000000"));
        txtitemname.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (selecteditemlayout != null) {
                    // commented by WaiWL on 24/07/2015
                    TextView txttaway = (TextView) selecteditemlayout
                            .getChildAt(5);
                    if (txttaway.getTag().equals(false))
                        selecteditemlayout
                                .setBackgroundColor(Color.TRANSPARENT);
                }
                if (clickChecked) {
                    selecteditemlayout = itemlayout;
                    itemlayout.setBackgroundColor(Color.parseColor("#d9522c"));
                    CreatingedititemDialog(v);
                    // CalculateTotal();bb
                }

            }
        });

        String unitcodeshort = "";
        String unit = " ";
        String saleprice = "0";

        if (use_unit == true) {
            List<UnitCodeObj> unitcodeobjlist = dbhelper
                    .getunitcodebyitemid(itemid);
            if (unitcodeobjlist.size() > 0) {
                unitcodeshort = unitcodeobjlist.get(0).getshortname();
                unit = unitcodeobjlist.get(0).getunit();

            }
        }
        saleprice = dbhelper.getItempricebyitemid(itemid);

        if (saleprice.equals(null) || saleprice.equals("null"))
            saleprice = "0";

        TextView txtqtyname = new TextView(this);
        txtqtyname.setText(Qty + " " + unitcodeshort);

        txtqtyname.setPadding(0, 2, 0, 2);
        txtqtyname.setTag(Qty);
        Qty = "1";
        txtqtyname.setTextColor(Color.parseColor("#000000"));
        txtqtyname.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                if (selecteditemlayout != null) {
                    // commented by WaiWL on 24/07/2015
                    TextView txttaway = (TextView) selecteditemlayout
                            .getChildAt(5);
                    if (txttaway.getTag().equals(false))
                        selecteditemlayout
                                .setBackgroundColor(Color.TRANSPARENT);

                }
                if (clickChecked) {
                    selecteditemlayout = itemlayout;
                    itemlayout.setBackgroundColor(Color.parseColor("#d9522c"));
                    CreatingedititemDialog(v);
                    // CalculateTotal();
                }

            }
        });
        txtqtyname.setGravity(Gravity.RIGHT);
        txtqtyname.setTypeface(font, Typeface.BOLD);

        TextView txtamount = new TextView(this);
        txtamount.setText(getCurrencyFormat(Double.toString(Double.parseDouble(saleprice)
                * Double.parseDouble(txtqtyname.getTag().toString()))));
        txtamount.setTag(Double.toString(Double.parseDouble(saleprice)
                * Double.parseDouble(txtqtyname.getTag().toString())));
        txtamount.setPadding(0, 2, 0, 2);
        txtamount.setTextColor(Color.parseColor("#000000"));
        txtamount.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (selecteditemlayout != null) {
                    // commented by WaiWL on 24/07/2015
                    TextView txttaway = (TextView) selecteditemlayout
                            .getChildAt(5);
                    if (txttaway.getTag().equals(false))
                        selecteditemlayout
                                .setBackgroundColor(Color.TRANSPARENT);

                    // itemlayout.refreshDrawableState();
                }
                if (clickChecked) {
                    selecteditemlayout = itemlayout;
                    itemlayout.setBackgroundColor(Color.parseColor("#d9522c"));
                    CreatingedititemDialog(v);
                }


            }
        });
        txtamount.setGravity(Gravity.RIGHT);

        TextView txtunit = new TextView(this);
        txtunit.setTag(unit);
        txtunit.setVisibility(View.GONE);

        TextView txttaway = new TextView(this);
        txttaway.setTag(false);
        txttaway.setVisibility(View.GONE);

        TextView txtsr = new TextView(this);
        txtsr.setTag(false);
        txtsr.setVisibility(View.GONE);

        // added by WaiWL on 23/07/2015
        TextView txtisFinishedItem = new TextView(this);
        txtisFinishedItem.setTag(false);
        txtisFinishedItem.setVisibility(View.GONE);
        // /////////////////////

        itemlayout.addView(txtsrno, srlayoutpara);
        itemlayout.addView(txtitemname, itemnamelayoutpara);
        itemlayout.addView(txtqtyname, qtylayoutpara);
        itemlayout.addView(txtamount, amountlayoutpara);
        itemlayout.addView(txtunit);
        itemlayout.addView(txttaway);
        itemlayout.addView(txtsr);
        itemlayout.addView(txtisFinishedItem);// added by WaiWL on 23/07/2015

        LinearLayout itemlistwithmodifier = new LinearLayout(this);
        itemlistwithmodifier.setOrientation(LinearLayout.VERTICAL);
        itemlistwithmodifier.addView(itemlayout);

        itemlistlayout.addView(itemlistwithmodifier, itemlayoutpara);

        final ScrollView scrollamount = (ScrollView) findViewById(R.id.amountscroll);
        scrollamount.post(new Runnable() {
            public void run() {
                scrollamount.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
        CalculateTotal();
        // ConnectionStatus();
        return itemlayout;

    }

    public LinearLayout selectItemtolist(String itemid, String itemname, String sr, Boolean isnew) {

        LinearLayout itemlistlayout = (LinearLayout) findViewById(R.id.itemlistlayout);

        final LinearLayout itemlayout = new LinearLayout(this);
        final LayoutParams itemlayoutpara = new LayoutParams(
                LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);

        final LayoutParams srlayoutpara = new LayoutParams(
                GlobalClass.getDPsize(20, this), LayoutParams.WRAP_CONTENT);

        final LayoutParams itemnamelayoutpara = new LayoutParams(
                GlobalClass.getDPsize(140, this), LayoutParams.WRAP_CONTENT);

        final LayoutParams qtylayoutpara = new LayoutParams(
                GlobalClass.getDPsize(40, this), LayoutParams.WRAP_CONTENT);

        final LayoutParams amountlayoutpara = new LayoutParams(
                GlobalClass.getDPsize(90, this), LayoutParams.WRAP_CONTENT);


        //added WHM [2020-01-28] MDY2-200141
        String CustomerID = (String) findViewById(R.id.txtcustomer).getTag().toString();      //added WHM [2020-01-03]
        if (CustomerID == null || CustomerID.equals("null") || CustomerID.equals("0") || CustomerID.equals("")) {
            CustomerID = "1";
        }
        int customer_pricelevel = dbhelper.checkCustomerPriceLevel(Integer.parseInt(CustomerID));
        dbhelper.cust_pricelevel = customer_pricelevel;
        // Toast.makeText(OrderTaking.this,CustomerID + "-"+ customer_pricelevel, Toast.LENGTH_SHORT).show();
        //end WHM


        TextView txtsrno = new TextView(this);  //child - 0
        maxsr = maxsr + 1;
        txtsrno.setText((Integer.toString(maxsr)) + ".");
        txtsrno.setTag("N");
        txtsrno.setPadding(0, 2, 0, 2);

        clickChecked = true;
        txtsrno.setTypeface(font);
        txtsrno.setGravity(Gravity.LEFT);
//		txtsrno.setTextColor(Color.parseColor("#000000"));
        if (isnew) //added by ZYP [30-12-2019] for new item color
            txtsrno.setTextColor(Color.parseColor("#2254EE"));
        else
            txtsrno.setTextColor(Color.parseColor("#000000"));
        txtsrno.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (selecteditemlayout != null) {
                    // commented by WaiWL on 24/07/2015
                    TextView txttaway = (TextView) selecteditemlayout
                            .getChildAt(5);
                    if (txttaway.getTag().equals(false))
                        selecteditemlayout
                                .setBackgroundColor(Color.TRANSPARENT);
                }
                if (clickChecked) {
                    selecteditemlayout = itemlayout;
                    final TextView txtsr = (TextView) selecteditemlayout.getChildAt(0);
                    final TextView txtitemcode = (TextView) selecteditemlayout.getChildAt(1);
                    final LinearLayout mainparent = (LinearLayout) selecteditemlayout.getParent();

                    String modifierrowsr = txtsr.getText().toString().replace(".", "").trim();


                    if (txtsr.getTag().toString().equals("P")) {
                        if (Boolean.parseBoolean(dbhelper.getItemsbyitemid(txtitemcode.getTag().toString()).getissetmenu()) == true) {
                            String codearray = null;
                            int count = 0;
                            for (int i = 1; i < mainparent.getChildCount() - 1; i++) {
                                count++;
                                LinearLayout layout = (LinearLayout) mainparent.getChildAt(i);
                                if (codearray != null) {
                                    codearray += " ," + ((TextView) layout.getChildAt(1)).getTag().toString();
                                } else {
                                    codearray = ((TextView) layout.getChildAt(1)).getTag().toString();
                                }
                            }
                            Json_class jsonclass = new Json_class();
                            String changecount = jsonclass.getString(new DatabaseHelper(ctx).getServiceURL() + "/Data/Get_SemenuCount?tranid=" + java.net.URLEncoder.encode(saleid) + "&codearray=" + java.net.URLEncoder.encode(codearray) + "&modifierrowsr=" + java.net.URLEncoder.encode(modifierrowsr));

                            if (count == Integer.parseInt(changecount.trim())) {
                                itemlayout.setBackgroundColor(Color.parseColor("#d9522c"));
                                CreatingedititemDialog(v);
                            } else {
                                showAlertDialogBox(ctx, "Message", "This Item is changed.You can't modify!.", false);
                            }
                        } else {
                            itemlayout.setBackgroundColor(Color.parseColor("#d9522c"));
                            CreatingedititemDialog(v);
                        }

                    } else {
                        itemlayout.setBackgroundColor(Color.parseColor("#d9522c"));
                        CreatingedititemDialog(v);
                        // CalculateTotal();cc
                    }
                }

            }
        });

        TextView txtitemname = new TextView(this); //child - 1
        txtitemname.setText(itemname);
        txtitemname.setTag(itemid);
        txtitemname.setPadding(0, 15, 0, 15);// modified by WaiWL on 22/07/2015
        // txtitemname.setTypeface(font,Typeface.BOLD);
        txtitemname.setTypeface(font);
        txtitemname.setGravity(Gravity.LEFT);
        if (isnew)
            txtitemname.setTextColor(Color.parseColor("#2254EE"));
        else
            txtitemname.setTextColor(Color.parseColor("#000000"));

        txtitemname.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (selecteditemlayout != null) {
                    // commented by WaiWL on 24/07/2015
                    TextView txttaway = (TextView) selecteditemlayout
                            .getChildAt(5);
                    if (txttaway.getTag().equals(false))
                        selecteditemlayout
                                .setBackgroundColor(Color.TRANSPARENT);
                }
                if (clickChecked) {
                    selecteditemlayout = itemlayout;
                    final TextView txtsr = (TextView) selecteditemlayout.getChildAt(0);
                    final TextView txtitemcode = (TextView) selecteditemlayout.getChildAt(1);
                    final LinearLayout mainparent = (LinearLayout) selecteditemlayout.getParent();

                    String modifierrowsr = txtsr.getText().toString().replace(".", "").trim();


                    if (txtsr.getTag().toString().equals("P")) {
                        if (Boolean.parseBoolean(dbhelper.getItemsbyitemid(txtitemcode.getTag().toString()).getissetmenu()) == true) {
                            String codearray = null;
                            int count = 0;
                            for (int i = 1; i < mainparent.getChildCount() - 1; i++) {
                                count++;
                                LinearLayout layout = (LinearLayout) mainparent.getChildAt(i);
                                if (codearray != null) {
                                    codearray += " ," + ((TextView) layout.getChildAt(1)).getTag().toString();
                                } else {
                                    codearray = ((TextView) layout.getChildAt(1)).getTag().toString();
                                }
                            }
                            Json_class jsonclass = new Json_class();
                            String changecount = jsonclass.getString(new DatabaseHelper(ctx).getServiceURL() + "/Data/Get_SemenuCount?tranid=" + java.net.URLEncoder.encode(saleid) + "&codearray=" + java.net.URLEncoder.encode(codearray) + "&modifierrowsr=" + java.net.URLEncoder.encode(modifierrowsr));

                            if (count == Integer.parseInt(changecount.trim())) {
                                itemlayout.setBackgroundColor(Color.parseColor("#d9522c"));
                                CreatingedititemDialog(v);
                            } else {
                                showAlertDialogBox(ctx, "Message", "This Item is changed.You can't modify!.", false);
                            }
                        } else {
                            itemlayout.setBackgroundColor(Color.parseColor("#d9522c"));
                            CreatingedititemDialog(v);
                        }

                    } else {
                        itemlayout.setBackgroundColor(Color.parseColor("#d9522c"));
                        CreatingedititemDialog(v);
                        // CalculateTotal();cc
                    }
                }

            }
        });

        String unitcodeshort = "";
        String unit = " ";
        String saleprice = "0";

        String defcurrency = dbhelper.getDefCurrency();
        org_curr = dbhelper.getsaleCurrbyitemid(itemid); //added by EKK on 24-02-2020
        //modified by ZYP [17/11/2020] offline exg rate
        String defexgrate, orgExgRate;
//        if (GlobalClass.tmpOffline || GlobalClass.use_foodtruck) {
//            defexgrate = dbhelper.getExgRateByCurrency(defcurrency);
//            orgExgRate = dbhelper.getExgRateByCurrency(org_curr);
//        } else {
//            //added by EKK on 24-02-2020
//            defexgrate = dbhelper.LoadExgRate(dbhelper.getServiceURL(), defcurrency);
//            orgExgRate = dbhelper.LoadExgRate(dbhelper.getServiceURL(), org_curr);
//        }

        //modified by ZYP [04-06-2021] #YGN3-21062
        defexgrate = dbhelper.getExgRateByCurrency(defcurrency);
        orgExgRate = dbhelper.getExgRateByCurrency(org_curr);

        //Added by Arkar Moe on [24/06/2016] for (Res-0085)
        //Choosing default UnitType in AppSetting
        List<UnitCodeObj> unitcodeobjlist = dbhelper.getunitcodebyitemid(itemid);
        if (use_unit && unitcodeobjlist.size() > 0) {
            if (use_unit_type == 2) {
                if (unitcodeobjlist.size() >= use_unit_type) {
                    unitcodeshort = unitcodeobjlist.get(1).getshortname();
                    unit = unitcodeobjlist.get(1).getunit();
                    saleprice = dbhelper.getItemUnitpricebyitemid(itemid, Integer.toString(use_unit_type));
                } else if (unitcodeobjlist.size() >= use_unit_type - 1) {
                    unitcodeshort = unitcodeobjlist.get(0).getshortname();
                    unit = unitcodeobjlist.get(0).getunit();
                    saleprice = dbhelper.getItemUnitpricebyitemid(itemid, Integer.toString(use_unit_type - 1));
                } else
                    saleprice = dbhelper.getItempricebyitemid(itemid);
            } else if (use_unit_type == 3) {
                if (unitcodeobjlist.size() >= use_unit_type) {
                    unitcodeshort = unitcodeobjlist.get(2).getshortname();
                    unit = unitcodeobjlist.get(2).getunit();
                    saleprice = dbhelper.getItemUnitpricebyitemid(itemid, Integer.toString(use_unit_type));
                } else if (unitcodeobjlist.size() >= use_unit_type - 1) {
                    //saleprice = dbhelper.getItempricebyitemid(itemid);
                    unitcodeshort = unitcodeobjlist.get(1).getshortname();
                    unit = unitcodeobjlist.get(1).getunit();
                    saleprice = dbhelper.getItemUnitpricebyitemid(itemid, Integer.toString(use_unit_type - 1));
                } else
                    saleprice = dbhelper.getItempricebyitemid(itemid);
            } else {
                unitcodeshort = unitcodeobjlist.get(0).getshortname();
                unit = unitcodeobjlist.get(0).getunit();
                saleprice = dbhelper.getItemUnitpricebyitemid(itemid, Integer.toString(1));
            }
        } else {

            saleprice = dbhelper.getItempricebyitemid(itemid);

        }

        //added by EKK on 25-02-2020
        double price = (Double.parseDouble(saleprice) * Double.parseDouble(orgExgRate) / Double.parseDouble(defexgrate));
        saleprice = price + "";
        //(Res-0085)
//        if (saleprice.equals(null) || saleprice.equals("null"))
//            saleprice = "0";


        Qty = Qty.replace(".0", "").trim();
        TextView txtqtyname = new TextView(this);       //child - 2
        txtqtyname.setText(Qty + " " + unitcodeshort);

        txtqtyname.setPadding(0, 2, 0, 2);
        txtqtyname.setTag(Qty);
        txtqtyname.setGravity(Gravity.RIGHT);
        txtqtyname.setTypeface(font, Typeface.BOLD);
        Qty = "1";
        //txtqtyname.setTextColor(Color.parseColor("#000000"));
        if (isnew) //new item color by ZYP [30-12-2019]
            txtqtyname.setTextColor(Color.parseColor("#2254EE"));
        else
            txtqtyname.setTextColor(Color.parseColor("#000000"));
        txtqtyname.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                if (selecteditemlayout != null) {
                    // commented by WaiWL on 24/07/2015
                    TextView txttaway = (TextView) selecteditemlayout.getChildAt(5);
                    if (txttaway.getTag().equals(false))
                        selecteditemlayout.setBackgroundColor(Color.TRANSPARENT);
                }
                if (clickChecked) {
                    selecteditemlayout = itemlayout;
                    final TextView txtsr = (TextView) selecteditemlayout.getChildAt(0);
                    final TextView txtitemcode = (TextView) selecteditemlayout.getChildAt(1);
                    final LinearLayout mainparent = (LinearLayout) selecteditemlayout.getParent();

                    String modifierrowsr = txtsr.getText().toString().replace(".", "").trim();

                    if (txtsr.getTag().toString().equals("P")) {
                        if (Boolean.parseBoolean(dbhelper.getItemsbyitemid(txtitemcode.getTag().toString()).getissetmenu()) == true) {
                            String codearray = null;
                            int count = 0;
                            for (int i = 1; i < mainparent.getChildCount(); i++) {
                                count++;
                                LinearLayout layout = (LinearLayout) mainparent.getChildAt(i);
                                if (codearray != null) {
                                    codearray += " ," + ((TextView) layout.getChildAt(1)).getTag().toString();
                                } else {
                                    codearray = ((TextView) layout.getChildAt(1)).getTag().toString();
                                }
                            }
                            Json_class jsonclass = new Json_class();
                            String changecount = jsonclass.getString(new DatabaseHelper(ctx).getServiceURL() + "/Data/Get_SemenuCount?tranid=" + java.net.URLEncoder.encode(saleid) + "&codearray=" + java.net.URLEncoder.encode(codearray) + "&modifierrowsr=" + java.net.URLEncoder.encode(modifierrowsr));

                            if (count == Integer.parseInt(changecount.trim())) {
                                itemlayout.setBackgroundColor(Color.parseColor("#d9522c"));
                                CreatingedititemDialog(v);
                            } else {
                                showAlertDialogBox(ctx, "Message", "This Item is changed.You can't modify!.", false);
                            }
                        } else {
                            itemlayout.setBackgroundColor(Color.parseColor("#d9522c"));
                            CreatingedititemDialog(v);
                        }

                    } else {
                        itemlayout.setBackgroundColor(Color.parseColor("#d9522c"));
                        CreatingedititemDialog(v);
                        // CalculateTotal();cc
                    }
                }
                // CalculateTotal();
            }
        });

        String amount = Double.toString(Double.parseDouble(saleprice) * Double.parseDouble(txtqtyname.getTag().toString()));

        TextView txtamount = new TextView(this);    //child - 3
        txtamount.setText(getCurrencyFormat(amount));
        txtamount.setTag(amount);
        txtamount.setPadding(0, 2, 0, 2);
        txtamount.setGravity(Gravity.RIGHT);
        //txtamount.setTextColor(Color.parseColor("#000000"));
        if (isnew)
            txtamount.setTextColor(Color.parseColor("#2254EE"));
        else
            txtamount.setTextColor(Color.parseColor("#000000"));
        txtamount.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (selecteditemlayout != null) {
                    // commented by WaiWL on 24/07/2015
                    TextView txttaway = (TextView) selecteditemlayout
                            .getChildAt(5);
                    if (txttaway.getTag().equals(false))
                        selecteditemlayout
                                .setBackgroundColor(Color.TRANSPARENT);

                    // itemlayout.refreshDrawableState();
                }
                if (clickChecked) {
                    selecteditemlayout = itemlayout;
                    final TextView txtsr = (TextView) selecteditemlayout.getChildAt(0);
                    final TextView txtitemcode = (TextView) selecteditemlayout.getChildAt(1);
                    final LinearLayout mainparent = (LinearLayout) selecteditemlayout.getParent();

                    String modifierrowsr = txtsr.getText().toString().replace(".", "").trim();


                    if (txtsr.getTag().toString().equals("P")) {
                        if (Boolean.parseBoolean(dbhelper.getItemsbyitemid(txtitemcode.getTag().toString()).getissetmenu()) == true) {
                            String codearray = null;
                            int count = 0;
                            for (int i = 1; i < mainparent.getChildCount(); i++) {
                                count++;
                                LinearLayout layout = (LinearLayout) mainparent.getChildAt(i);
                                if (codearray != null) {
                                    codearray += " ," + ((TextView) layout.getChildAt(1)).getTag().toString();
                                } else {
                                    codearray = ((TextView) layout.getChildAt(1)).getTag().toString();
                                }
                            }
                            Json_class jsonclass = new Json_class();
                            String changecount = jsonclass.getString(new DatabaseHelper(ctx).getServiceURL() + "/Data/Get_SemenuCount?tranid=" + java.net.URLEncoder.encode(saleid) + "&codearray=" + java.net.URLEncoder.encode(codearray) + "&modifierrowsr=" + java.net.URLEncoder.encode(modifierrowsr));

                            if (count == Integer.parseInt(changecount.trim())) {
                                itemlayout.setBackgroundColor(Color.parseColor("#d9522c"));
                                CreatingedititemDialog(v);
                            } else {
                                showAlertDialogBox(ctx, "Message", "This Item is changed.You can't modify!.", false);
                            }
                        } else {
                            itemlayout.setBackgroundColor(Color.parseColor("#d9522c"));
                            CreatingedititemDialog(v);
                        }

                    } else {
                        itemlayout.setBackgroundColor(Color.parseColor("#d9522c"));
                        CreatingedititemDialog(v);
                        // CalculateTotal();cc
                    }
                }
            }
        });

        TextView txtunit = new TextView(this);
        txtunit.setTag(unit);
        txtunit.setVisibility(View.GONE);

        TextView txttaway = new TextView(this);
        txttaway.setTag(false);
        txttaway.setVisibility(View.GONE);

        TextView txtsr = new TextView(this);
        txtsr.setText(sr);
        txtsr.setTag(false);
        txtsr.setVisibility(View.GONE);

        // added by WaiWL on 23/07/2015
        TextView txtisFinishedItem = new TextView(this);
        txtisFinishedItem.setTag(false);
        txtisFinishedItem.setVisibility(View.GONE);
        // ///////////

        //added by AungMyoTun fire
        TextView txtFire = new TextView(this);
        txtFire.setTag(false);
        txtFire.setVisibility(View.GONE);

        TextView txtFired = new TextView(this);
        txtFired.setTag(false);
        txtFired.setVisibility(View.GONE);


        itemlayout.addView(txtsrno, srlayoutpara);          //child - 0
        itemlayout.addView(txtitemname, itemnamelayoutpara);//child - 1
        itemlayout.addView(txtqtyname, qtylayoutpara);      //child - 2
        itemlayout.addView(txtamount, amountlayoutpara);    //child - 3
        itemlayout.addView(txtunit);                        //child - 4
        itemlayout.addView(txttaway);                       //child - 5
        itemlayout.addView(txtsr);                          //child - 6
        itemlayout.addView(txtisFinishedItem);              //child - 7
        itemlayout.addView(txtFire);                        //child - 8
        itemlayout.addView(txtFired);                       //child - 9

        LinearLayout itemlistwithmodifier = new LinearLayout(this);
        itemlistwithmodifier.setOrientation(LinearLayout.VERTICAL);
        itemlistwithmodifier.addView(itemlayout);
        itemlistlayout.addView(itemlistwithmodifier, itemlayoutpara);

        final ScrollView scrollamount = (ScrollView) findViewById(R.id.amountscroll);
        scrollamount.post(new Runnable() {
            public void run() {
                scrollamount.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
        CalculateTotal();

        if (editpop == 1 && isnew == true) //Added by ArkarMoe on [21/12/2016]
        {
            CreatingedititemDialog(txtitemname);
        }
        // ConnectionStatus();
        //added by ZYP [23/11/2020] save item foodtruck
        if (isnew && GlobalClass.use_foodtruck) {
            Submit_Voucher(0);
        }

        return itemlayout;

    }


    public LinearLayout selectMenuItemtolist(String itemid, String itemname,
                                             String sr, Boolean isnew, String qty) {
        LinearLayout itemlistlayout = (LinearLayout) findViewById(R.id.itemlistlayout);

        final LinearLayout itemlayout = new LinearLayout(this);
        final LayoutParams itemlayoutpara = new LayoutParams(
                LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);

        final LayoutParams srlayoutpara = new LayoutParams(
                GlobalClass.getDPsize(20, this), LayoutParams.WRAP_CONTENT);

        final LayoutParams itemnamelayoutpara = new LayoutParams(
                GlobalClass.getDPsize(140, this), LayoutParams.WRAP_CONTENT);

        final LayoutParams qtylayoutpara = new LayoutParams(
                GlobalClass.getDPsize(40, this), LayoutParams.WRAP_CONTENT);

        final LayoutParams amountlayoutpara = new LayoutParams(
                GlobalClass.getDPsize(90, this), LayoutParams.WRAP_CONTENT);

        TextView txtsrno = new TextView(this);
        maxsr = maxsr + 1;
        txtsrno.setText((Integer.toString(maxsr)) + ".");
        txtsrno.setTag("N");
        txtsrno.setPadding(0, 2, 0, 2);

        clickChecked = true;
        txtsrno.setTypeface(font);
        txtsrno.setGravity(Gravity.LEFT);
        //txtsrno.setTextColor(Color.parseColor("#000000"));
        if (isnew) //added by ZYP [22-06-2020] for menu new item color
            txtsrno.setTextColor(Color.parseColor("#2254EE"));
        else
            txtsrno.setTextColor(Color.parseColor("#000000"));
        txtsrno.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (selecteditemlayout != null) {
                    // commented by WaiWL on 24/07/2015
                    TextView txttaway = (TextView) selecteditemlayout
                            .getChildAt(5);
                    if (txttaway.getTag().equals(false))
                        selecteditemlayout
                                .setBackgroundColor(Color.TRANSPARENT);
                }
                if (clickChecked) {
                    selecteditemlayout = itemlayout;
                    final TextView txtsr = (TextView) selecteditemlayout.getChildAt(0);
                    final TextView txtitemcode = (TextView) selecteditemlayout.getChildAt(1);
                    final LinearLayout mainparent = (LinearLayout) selecteditemlayout.getParent();

                    String modifierrowsr = txtsr.getText().toString().replace(".", "").trim();


                    if (txtsr.getTag().toString().equals("P")) {
                        if (Boolean.parseBoolean(dbhelper.getItemsbyitemid(txtitemcode.getTag().toString()).getissetmenu()) == true) {
                            String codearray = null;
                            int count = 0;
                            for (int i = 1; i < mainparent.getChildCount(); i++) {
                                count++;
                                LinearLayout layout = (LinearLayout) mainparent.getChildAt(i);
                                if (codearray != null) {
                                    codearray += " ," + ((TextView) layout.getChildAt(1)).getTag().toString();
                                } else {
                                    codearray = ((TextView) layout.getChildAt(1)).getTag().toString();
                                }
                            }
                            Json_class jsonclass = new Json_class();
                            String changecount = jsonclass.getString(new DatabaseHelper(ctx).getServiceURL() + "/Data/Get_SemenuCount?tranid=" + java.net.URLEncoder.encode(saleid) + "&codearray=" + java.net.URLEncoder.encode(codearray) + "&modifierrowsr=" + java.net.URLEncoder.encode(modifierrowsr));

                            if (count == Integer.parseInt(changecount.trim())) {
                                itemlayout.setBackgroundColor(Color.parseColor("#d9522c"));
                                CreatingedititemDialog(v);
                            } else {
                                showAlertDialogBox(ctx, "Message", "This Item is changed.You can't modify!.", false);
                            }
                        } else {
                            itemlayout.setBackgroundColor(Color.parseColor("#d9522c"));
                            CreatingedititemDialog(v);
                        }

                    } else {
                        itemlayout.setBackgroundColor(Color.parseColor("#d9522c"));
                        CreatingedititemDialog(v);
                        // CalculateTotal();cc
                    }
                }

            }
        });

        TextView txtitemname = new TextView(this);
        txtitemname.setText(itemname);
        txtitemname.setTag(itemid);
        txtitemname.setPadding(0, 15, 0, 15);// modified by WaiWL on 22/07/2015
        // txtitemname.setTypeface(font,Typeface.BOLD);
        txtitemname.setTypeface(font);
        txtitemname.setGravity(Gravity.LEFT);
        //txtitemname.setTextColor(Color.parseColor("#000000"));
        if (isnew) //added by ZYP [22-06-2020] for menu new item color
            txtitemname.setTextColor(Color.parseColor("#2254EE"));
        else
            txtitemname.setTextColor(Color.parseColor("#000000"));
        txtitemname.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (selecteditemlayout != null) {
                    // commented by WaiWL on 24/07/2015
                    TextView txttaway = (TextView) selecteditemlayout
                            .getChildAt(5);
                    if (txttaway.getTag().equals(false))
                        selecteditemlayout
                                .setBackgroundColor(Color.TRANSPARENT);
                }
                if (clickChecked) {
                    selecteditemlayout = itemlayout;
                    final TextView txtsr = (TextView) selecteditemlayout.getChildAt(0);
                    final TextView txtitemcode = (TextView) selecteditemlayout.getChildAt(1);
                    final LinearLayout mainparent = (LinearLayout) selecteditemlayout.getParent();

                    String modifierrowsr = txtsr.getText().toString().replace(".", "").trim();


                    if (txtsr.getTag().toString().equals("P")) {
                        if (Boolean.parseBoolean(dbhelper.getItemsbyitemid(txtitemcode.getTag().toString()).getissetmenu()) == true) {
                            String codearray = null;
                            int count = 0;
                            for (int i = 1; i < mainparent.getChildCount(); i++) {
                                count++;
                                LinearLayout layout = (LinearLayout) mainparent.getChildAt(i);
                                if (codearray != null) {
                                    codearray += " ," + ((TextView) layout.getChildAt(1)).getTag().toString();
                                } else {
                                    codearray = ((TextView) layout.getChildAt(1)).getTag().toString();
                                }
                            }
                            Json_class jsonclass = new Json_class();
                            String changecount = jsonclass.getString(new DatabaseHelper(ctx).getServiceURL() + "/Data/Get_SemenuCount?tranid=" + java.net.URLEncoder.encode(saleid) + "&codearray=" + java.net.URLEncoder.encode(codearray) + "&modifierrowsr=" + java.net.URLEncoder.encode(modifierrowsr));

                            if (count == Integer.parseInt(changecount.trim())) {
                                itemlayout.setBackgroundColor(Color.parseColor("#d9522c"));
                                CreatingedititemDialog(v);
                            } else {
                                showAlertDialogBox(ctx, "Message", "This Item is changed.You can't modify!.", false);
                            }
                        } else {
                            itemlayout.setBackgroundColor(Color.parseColor("#d9522c"));
                            CreatingedititemDialog(v);
                        }

                    } else {
                        itemlayout.setBackgroundColor(Color.parseColor("#d9522c"));
                        CreatingedititemDialog(v);
                        // CalculateTotal();cc
                    }
                }

            }
        });

        String unitcodeshort = "";
        String unit = " ";
        String saleprice = "0";

        //Added by Arkar Moe on [24/06/2016] for (Res-0085)
        //Choosing default UnitType in AppSetting
        List<UnitCodeObj> unitcodeobjlist = dbhelper
                .getunitcodebyitemid(itemid);
        if (use_unit == true && unitcodeobjlist.size() > 0) {
            if (use_unit_type == 2) {
                if (unitcodeobjlist.size() >= use_unit_type) {
                    unitcodeshort = unitcodeobjlist.get(1).getshortname();
                    unit = unitcodeobjlist.get(1).getunit();
                    saleprice = dbhelper.getItemUnitpricebyitemid(itemid, Integer.toString(use_unit_type));
                } else if (unitcodeobjlist.size() >= use_unit_type - 1) {
                    unitcodeshort = unitcodeobjlist.get(0).getshortname();
                    unit = unitcodeobjlist.get(0).getunit();
                    saleprice = dbhelper.getItemUnitpricebyitemid(itemid, Integer.toString(use_unit_type - 1));
                } else
                    saleprice = dbhelper.getItempricebyitemid(itemid);
            } else if (use_unit_type == 3) {
                if (unitcodeobjlist.size() >= use_unit_type) {
                    unitcodeshort = unitcodeobjlist.get(2).getshortname();
                    unit = unitcodeobjlist.get(2).getunit();
                    saleprice = dbhelper.getItemUnitpricebyitemid(itemid, Integer.toString(use_unit_type));
                } else if (unitcodeobjlist.size() >= use_unit_type - 1) {
                    //saleprice = dbhelper.getItempricebyitemid(itemid);
                    unitcodeshort = unitcodeobjlist.get(1).getshortname();
                    unit = unitcodeobjlist.get(1).getunit();
                    saleprice = dbhelper.getItemUnitpricebyitemid(itemid, Integer.toString(use_unit_type - 1));
                } else
                    saleprice = dbhelper.getItempricebyitemid(itemid);
            } else {
                unitcodeshort = unitcodeobjlist.get(0).getshortname();
                unit = unitcodeobjlist.get(0).getunit();
                saleprice = dbhelper.getItemUnitpricebyitemid(itemid, Integer.toString(1));
            }
        } else
            saleprice = dbhelper.getItempricebyitemid(itemid);
        //(Res-0085)

        if (saleprice.equals(null) || saleprice.equals("null"))
            saleprice = "0";


        Qty = qty;
        Qty = Qty.toString().replace(".0", "").trim();
        TextView txtqtyname = new TextView(this);
        txtqtyname.setText(Qty + " " + unitcodeshort);

        txtqtyname.setPadding(0, 2, 0, 2);
        txtqtyname.setTag(Qty);
        Qty = "1.0";
        //txtqtyname.setTextColor(Color.parseColor("#000000"));
        if (isnew) //added by ZYP [22-06-2020] for menu new item color
            txtqtyname.setTextColor(Color.parseColor("#2254EE"));
        else
            txtqtyname.setTextColor(Color.parseColor("#000000"));
        txtqtyname.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                if (selecteditemlayout != null) {
                    // commented by WaiWL on 24/07/2015
                    TextView txttaway = (TextView) selecteditemlayout
                            .getChildAt(5);
                    if (txttaway.getTag().equals(false))
                        selecteditemlayout
                                .setBackgroundColor(Color.TRANSPARENT);
                }
                if (clickChecked) {
                    selecteditemlayout = itemlayout;
                    final TextView txtsr = (TextView) selecteditemlayout.getChildAt(0);
                    final TextView txtitemcode = (TextView) selecteditemlayout.getChildAt(1);
                    final LinearLayout mainparent = (LinearLayout) selecteditemlayout.getParent();

                    String modifierrowsr = txtsr.getText().toString().replace(".", "").trim();


                    if (txtsr.getTag().toString().equals("P")) {
                        if (Boolean.parseBoolean(dbhelper.getItemsbyitemid(txtitemcode.getTag().toString()).getissetmenu()) == true) {
                            String codearray = null;
                            int count = 0;
                            for (int i = 1; i < mainparent.getChildCount(); i++) {
                                count++;
                                LinearLayout layout = (LinearLayout) mainparent.getChildAt(i);
                                if (codearray != null) {
                                    codearray += " ," + ((TextView) layout.getChildAt(1)).getTag().toString();
                                } else {
                                    codearray = ((TextView) layout.getChildAt(1)).getTag().toString();
                                }
                            }
                            Json_class jsonclass = new Json_class();
                            String changecount = jsonclass.getString(new DatabaseHelper(ctx).getServiceURL() + "/Data/Get_SemenuCount?tranid=" + java.net.URLEncoder.encode(saleid) + "&codearray=" + java.net.URLEncoder.encode(codearray) + "&modifierrowsr=" + java.net.URLEncoder.encode(modifierrowsr));

                            if (count == Integer.parseInt(changecount.trim())) {
                                itemlayout.setBackgroundColor(Color.parseColor("#d9522c"));
                                CreatingedititemDialog(v);
                            } else {
                                showAlertDialogBox(ctx, "Message", "This Item is changed.You can't modify!.", false);
                            }
                        } else {
                            itemlayout.setBackgroundColor(Color.parseColor("#d9522c"));
                            CreatingedititemDialog(v);
                        }

                    } else {
                        itemlayout.setBackgroundColor(Color.parseColor("#d9522c"));
                        CreatingedititemDialog(v);
                        // CalculateTotal();cc
                    }
                }
                // CalculateTotal();
            }
        });
        txtqtyname.setGravity(Gravity.RIGHT);
        txtqtyname.setTypeface(font, Typeface.BOLD);

        TextView txtamount = new TextView(this);
        txtamount.setText(getCurrencyFormat(Double.toString(Double.parseDouble(saleprice)
                * Double.parseDouble(txtqtyname.getTag().toString()))));
        txtamount.setTag(Double.toString(Double.parseDouble(saleprice)
                * Double.parseDouble(txtqtyname.getTag().toString())));
        txtamount.setPadding(0, 2, 0, 2);
        //txtamount.setTextColor(Color.parseColor("#000000"));
        if (isnew) //added by ZYP [22-06-2020] for menu new item color
            txtamount.setTextColor(Color.parseColor("#2254EE"));
        else
            txtamount.setTextColor(Color.parseColor("#000000"));
        txtamount.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (selecteditemlayout != null) {
                    // commented by WaiWL on 24/07/2015
                    TextView txttaway = (TextView) selecteditemlayout
                            .getChildAt(5);
                    if (txttaway.getTag().equals(false))
                        selecteditemlayout
                                .setBackgroundColor(Color.TRANSPARENT);

                    // itemlayout.refreshDrawableState();
                }
                if (clickChecked) {
                    selecteditemlayout = itemlayout;
                    final TextView txtsr = (TextView) selecteditemlayout.getChildAt(0);
                    final TextView txtitemcode = (TextView) selecteditemlayout.getChildAt(1);
                    final LinearLayout mainparent = (LinearLayout) selecteditemlayout.getParent();

                    String modifierrowsr = txtsr.getText().toString().replace(".", "").trim();


                    if (txtsr.getTag().toString().equals("P")) {
                        if (Boolean.parseBoolean(dbhelper.getItemsbyitemid(txtitemcode.getTag().toString()).getissetmenu()) == true) {
                            String codearray = null;
                            int count = 0;
                            for (int i = 1; i < mainparent.getChildCount(); i++) {
                                count++;
                                LinearLayout layout = (LinearLayout) mainparent.getChildAt(i);
                                if (codearray != null) {
                                    codearray += " ," + ((TextView) layout.getChildAt(1)).getTag().toString();
                                } else {
                                    codearray = ((TextView) layout.getChildAt(1)).getTag().toString();
                                }
                            }
                            Json_class jsonclass = new Json_class();
                            String changecount = jsonclass.getString(new DatabaseHelper(ctx).getServiceURL() + "/Data/Get_SemenuCount?tranid=" + java.net.URLEncoder.encode(saleid) + "&codearray=" + java.net.URLEncoder.encode(codearray) + "&modifierrowsr=" + java.net.URLEncoder.encode(modifierrowsr));

                            if (count == Integer.parseInt(changecount.trim())) {
                                itemlayout.setBackgroundColor(Color.parseColor("#d9522c"));
                                CreatingedititemDialog(v);
                            } else {
                                showAlertDialogBox(ctx, "Message", "This Item is changed.You can't modify!.", false);
                            }
                        } else {
                            itemlayout.setBackgroundColor(Color.parseColor("#d9522c"));
                            CreatingedititemDialog(v);
                        }

                    } else {
                        itemlayout.setBackgroundColor(Color.parseColor("#d9522c"));
                        CreatingedititemDialog(v);
                        // CalculateTotal();cc
                    }
                }
            }
        });
        txtamount.setGravity(Gravity.RIGHT);

        TextView txtunit = new TextView(this);
        txtunit.setTag(unit);
        txtunit.setVisibility(View.GONE);

        TextView txttaway = new TextView(this);
        txttaway.setTag(false);
        txttaway.setVisibility(View.GONE);

        TextView txtsr = new TextView(this);
        txtsr.setText(sr);
        txtsr.setTag(false);
        txtsr.setVisibility(View.GONE);

        // added by WaiWL on 23/07/2015
        TextView txtisFinishedItem = new TextView(this);
        txtisFinishedItem.setTag(false);
        txtisFinishedItem.setVisibility(View.GONE);
        // ///////////

        //added by AungMyoTun fire
        TextView txtFire = new TextView(this);
        txtFire.setTag(false);
        txtFire.setVisibility(View.GONE);

        TextView txtFired = new TextView(this);
        txtFired.setTag(false);
        txtFired.setVisibility(View.GONE);


        itemlayout.addView(txtsrno, srlayoutpara);
        itemlayout.addView(txtitemname, itemnamelayoutpara);
        itemlayout.addView(txtqtyname, qtylayoutpara);
        itemlayout.addView(txtamount, amountlayoutpara);
        itemlayout.addView(txtunit);
        itemlayout.addView(txttaway);
        itemlayout.addView(txtsr);
        itemlayout.addView(txtisFinishedItem);// added by WaiWL on 23/07/2015
        itemlayout.addView(txtFire);
        itemlayout.addView(txtFired);

        LinearLayout itemlistwithmodifier = new LinearLayout(this);
        itemlistwithmodifier.setOrientation(LinearLayout.VERTICAL);
        itemlistwithmodifier.addView(itemlayout);
        itemlistlayout.addView(itemlistwithmodifier, itemlayoutpara);

        final ScrollView scrollamount = (ScrollView) findViewById(R.id.amountscroll);
        scrollamount.post(new Runnable() {
            public void run() {
                scrollamount.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
        CalculateTotal();
        if (editpop == 1 && isnew == true) //Added by ArkarMoe on [21/12/2016]
        {
            CreatingedititemDialog(txtitemname);
        }
        // ConnectionStatus();
        return itemlayout;

    }


    public LinearLayout selectvalueItemtolist(String itemid, String itemname,
                                              String sr) {
        LinearLayout valueitemlistlayout = (LinearLayout) findViewById(R.id.valueitem_itemlistlayout);

        final LinearLayout itemlayout = new LinearLayout(this);
        final LayoutParams itemlayoutpara = new LayoutParams(
                LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
        final LayoutParams srlayoutpara = new LayoutParams(
                GlobalClass.getDPsize(20, this), LayoutParams.WRAP_CONTENT);
        final LayoutParams itemnamelayoutpara = new LayoutParams(
                GlobalClass.getDPsize(140, this), LayoutParams.WRAP_CONTENT);
        final LayoutParams qtylayoutpara = new LayoutParams(
                GlobalClass.getDPsize(40, this), LayoutParams.WRAP_CONTENT);
        final LayoutParams amountlayoutpara = new LayoutParams(
                GlobalClass.getDPsize(90, this), LayoutParams.WRAP_CONTENT);

        TextView txtsrno = new TextView(this);
        // maxsr = maxsr + 1;
        // txtsrno.setText((Integer.toString(maxsr)) + ".");
        txtsrno.setTag("N");
        txtsrno.setPadding(0, 2, 0, 2);
        txtsrno.setVisibility(View.INVISIBLE);

        TextView txtitemname = new TextView(this);
        txtitemname.setText(itemname);
        txtitemname.setTag(itemid);
        txtitemname.setPadding(0, 15, 0, 15);// modified by WaiWL on 22/07/2015
        // txtitemname.setTypeface(font,Typeface.BOLD);
        txtitemname.setTypeface(font);
        txtitemname.setGravity(Gravity.LEFT);
        txtitemname.setTextColor(Color.parseColor("#000000"));
        txtitemname.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                selecteditemlayout = itemlayout;
                itemlayout.setBackgroundColor(Color.parseColor("#d9522c"));
                CreatingvalueitemDialog(v);
                // CalculateTotal();value itemname
            }
        });

        String saleprice = "0";

        saleprice = dbhelper.getItempricebyitemid(itemid);

        if (saleprice.equals(null) || saleprice.equals("null"))
            saleprice = "0";

        TextView txtqtyname = new TextView(this);
        txtqtyname.setText(Qty);

        txtqtyname.setPadding(0, 2, 0, 2);
        txtqtyname.setTag(Qty);
        Qty = "1";
        txtqtyname.setVisibility(View.INVISIBLE);
        txtqtyname.setTextColor(Color.parseColor("#000000"));
        txtqtyname.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                selecteditemlayout = itemlayout;
                itemlayout.setBackgroundColor(Color.parseColor("#d9522c"));
                CreatingvalueitemDialog(v);
                //value qtyname
            }
        });
        txtqtyname.setGravity(Gravity.RIGHT);
        txtqtyname.setTypeface(font, Typeface.BOLD);

        TextView txtamount = new TextView(this);
        if (sr != "0") {
            Object[] objAmt = new Object[4];
            objAmt = dbhelper.getVaueItemAmt(saleid, sr, itemid);

            String tmpAmount;
            tmpAmount = objAmt[3].toString().replace('$', ' ');
            txtamount.setText(tmpAmount);
            txtamount.setTag(tmpAmount);
        } else {
            txtamount.setText(getCurrencyFormat(Double.toString(Double.parseDouble(saleprice)
                    * Double.parseDouble(txtqtyname.getTag().toString()))));
            txtamount.setTag(Double.toString(Double.parseDouble(saleprice)
                    * Double.parseDouble(txtqtyname.getTag().toString())));
        }
        txtamount.setPadding(0, 2, 0, 2);
        txtamount.setTextColor(Color.parseColor("#000000"));
        txtamount.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                selecteditemlayout = itemlayout;
                itemlayout.setBackgroundColor(Color.parseColor("#d9522c"));
                CreatingvalueitemDialog(v);

            }
        });
        txtamount.setGravity(Gravity.RIGHT);

        TextView txtsr = new TextView(this);
        txtsr.setText(sr);
        txtsr.setTag(false);
        txtsr.setVisibility(View.GONE);

        TextView txtKTV_StartTime = new TextView(this);
        txtKTV_StartTime.setTag(false);
        txtKTV_StartTime.setVisibility(View.GONE);

        TextView txtKTV_EndTime = new TextView(this);
        txtKTV_EndTime.setTag(false);
        txtKTV_EndTime.setVisibility(View.GONE);

        itemlayout.addView(txtsrno, srlayoutpara);
        itemlayout.addView(txtitemname, itemnamelayoutpara);
        itemlayout.addView(txtqtyname, qtylayoutpara);
        itemlayout.addView(txtamount, amountlayoutpara);
        itemlayout.addView(txtsr);
        itemlayout.addView(txtKTV_StartTime);
        itemlayout.addView(txtKTV_EndTime);

        LinearLayout itemlistwithmodifier = new LinearLayout(this);
        itemlistwithmodifier.setOrientation(LinearLayout.VERTICAL);
        itemlistwithmodifier.addView(itemlayout);
        valueitemlistlayout.addView(itemlistwithmodifier, itemlayoutpara);

        final ScrollView scrollamount = (ScrollView) findViewById(R.id.amountscroll);
        scrollamount.post(new Runnable() {
            public void run() {
                scrollamount.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
        // CalculateTotal();
        return itemlayout;
    }


    // added by TTA
    public LinearLayout selectMenuvalueItemtolist(String itemid, String itemname,
                                                  String sr, String qty) {
        LinearLayout valueitemlistlayout = (LinearLayout) findViewById(R.id.valueitem_itemlistlayout);

        final LinearLayout itemlayout = new LinearLayout(this);
        final LayoutParams itemlayoutpara = new LayoutParams(
                LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
        final LayoutParams srlayoutpara = new LayoutParams(
                GlobalClass.getDPsize(20, this), LayoutParams.WRAP_CONTENT);
        final LayoutParams itemnamelayoutpara = new LayoutParams(
                GlobalClass.getDPsize(140, this), LayoutParams.WRAP_CONTENT);
        final LayoutParams qtylayoutpara = new LayoutParams(
                GlobalClass.getDPsize(40, this), LayoutParams.WRAP_CONTENT);
        final LayoutParams amountlayoutpara = new LayoutParams(
                GlobalClass.getDPsize(90, this), LayoutParams.WRAP_CONTENT);

        TextView txtsrno = new TextView(this);
        // maxsr = maxsr + 1;
        // txtsrno.setText((Integer.toString(maxsr)) + ".");
        txtsrno.setTag("N");
        txtsrno.setPadding(0, 2, 0, 2);
        txtsrno.setVisibility(View.INVISIBLE);

        TextView txtitemname = new TextView(this);
        txtitemname.setText(itemname);
        txtitemname.setTag(itemid);
        txtitemname.setPadding(0, 15, 0, 15);// modified by WaiWL on 22/07/2015
        // txtitemname.setTypeface(font,Typeface.BOLD);
        txtitemname.setTypeface(font);
        txtitemname.setGravity(Gravity.LEFT);
        txtitemname.setTextColor(Color.parseColor("#000000"));
        txtitemname.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                selecteditemlayout = itemlayout;
                itemlayout.setBackgroundColor(Color.parseColor("#d9522c"));
                CreatingvalueitemDialog(v);
                // CalculateTotal();value itemname
            }
        });

        String saleprice = "0";

        saleprice = dbhelper.getItempricebyitemid(itemid);

        if (saleprice.equals(null) || saleprice.equals("null"))
            saleprice = "0";

        Qty = qty;
        TextView txtqtyname = new TextView(this);
        txtqtyname.setText(Qty);

        txtqtyname.setPadding(0, 2, 0, 2);
        txtqtyname.setTag(Qty);
        Qty = "1";
        txtqtyname.setVisibility(View.INVISIBLE);
        txtqtyname.setTextColor(Color.parseColor("#000000"));
        txtqtyname.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                selecteditemlayout = itemlayout;
                itemlayout.setBackgroundColor(Color.parseColor("#d9522c"));
                CreatingvalueitemDialog(v);
                //value qtyname
            }
        });
        txtqtyname.setGravity(Gravity.RIGHT);
        txtqtyname.setTypeface(font, Typeface.BOLD);

        TextView txtamount = new TextView(this);
        if (sr != "0") {
            Object[] objAmt = new Object[4];
            objAmt = dbhelper.getVaueItemAmt(saleid, sr, itemid);

            String tmpAmount;
            tmpAmount = objAmt[3].toString().replace('$', ' ');
            txtamount.setText(getCurrencyFormat(tmpAmount));
            txtamount.setTag(tmpAmount);
        } else {
            txtamount.setText(getCurrencyFormat(Double.toString(Double.parseDouble(saleprice)
                    * Double.parseDouble(txtqtyname.getTag().toString()))));
            txtamount.setTag(Double.toString(Double.parseDouble(saleprice)
                    * Double.parseDouble(txtqtyname.getTag().toString())));
        }
        txtamount.setPadding(0, 2, 0, 2);
        txtamount.setTextColor(Color.parseColor("#000000"));
        txtamount.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                selecteditemlayout = itemlayout;
                itemlayout.setBackgroundColor(Color.parseColor("#d9522c"));
                CreatingvalueitemDialog(v);

            }
        });
        txtamount.setGravity(Gravity.RIGHT);

        TextView txtsr = new TextView(this);
        txtsr.setText(sr);
        txtsr.setTag(false);
        txtsr.setVisibility(View.GONE);

        TextView txtKTV_StartTime = new TextView(this);
        txtKTV_StartTime.setTag(false);
        txtKTV_StartTime.setVisibility(View.GONE);

        TextView txtKTV_EndTime = new TextView(this);
        txtKTV_EndTime.setTag(false);
        txtKTV_EndTime.setVisibility(View.GONE);

        itemlayout.addView(txtsrno, srlayoutpara);
        itemlayout.addView(txtitemname, itemnamelayoutpara);
        itemlayout.addView(txtqtyname, qtylayoutpara);
        itemlayout.addView(txtamount, amountlayoutpara);
        itemlayout.addView(txtsr);
        itemlayout.addView(txtKTV_StartTime);
        itemlayout.addView(txtKTV_EndTime);

        LinearLayout itemlistwithmodifier = new LinearLayout(this);
        itemlistwithmodifier.setOrientation(LinearLayout.VERTICAL);
        itemlistwithmodifier.addView(itemlayout);
        valueitemlistlayout.addView(itemlistwithmodifier, itemlayoutpara);

        final ScrollView scrollamount = (ScrollView) findViewById(R.id.amountscroll);
        scrollamount.post(new Runnable() {
            public void run() {
                scrollamount.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
        // CalculateTotal();
        return itemlayout;
    }

    public double tabletSize() {

        double size = 0;
        try {

            // Compute screen size

            DisplayMetrics dm = this.getResources().getDisplayMetrics();

            float screenWidth = dm.widthPixels / dm.xdpi;

            float screenHeight = dm.heightPixels / dm.ydpi;

            size = Math.sqrt(Math.pow(screenWidth, 2) +

                    Math.pow(screenHeight, 2));

        } catch (Throwable t) {

        }

        return size;

    }

    public void butoption_click(View v) throws NumberFormatException,
            JSONException {
        switch (v.getId()) {
            case R.id.butclearall:
                final LinearLayout itemlistlayout = (LinearLayout) findViewById(R.id.itemlistlayout);
                boolean status = false;
                if (itemlistlayout.getChildCount() > 0) {
                    int i = itemlistlayout.getChildCount() - 1;
                    while (i > 0) {

                        LinearLayout itemlayout = (LinearLayout) itemlistlayout
                                .getChildAt(i);
                        if (((LinearLayout) (itemlayout.getChildAt(0)))
                                .getChildAt(0).getTag().equals("N")) {
                            status = true;
                            break;
                        }
                        i--;
                    }

                    if (status) {
                        clearAll_withnew_dialog();
                    } else {
                        clearAll_withoutnew_dialog();
                    }

                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                    alertDialog.setTitle("Information");
                    alertDialog.setMessage("No item to clear!");
                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub

                        }
                    });
                    alertDialog.show();
                }

//			AlertDialog alertDialog = new AlertDialog.Builder(this).create();
//			alertDialog.setTitle("Confirm");
//			alertDialog.setMessage("Are you sure to remove!");
//
//			alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
//				public void onClick(DialogInterface dialog, int which) {
//					if (itemlistlayout.getChildCount() > 0) {
//						int i = itemlistlayout.getChildCount() - 1;
//						maxsr = 0;
//						while (itemlistlayout.getChildCount() > 0) {
//
//							LinearLayout itemlayout = (LinearLayout) itemlistlayout
//									.getChildAt(i);
//							if (!((LinearLayout) (itemlayout.getChildAt(0)))
//									.getChildAt(0).getTag().equals("N")) {
//								TextView txtsr = (TextView) ((LinearLayout) (itemlayout
//										.getChildAt(0))).getChildAt(0);
//								maxsr = Integer.parseInt(txtsr.getText()
//										.toString().replace(".", "").trim());
//								break;
//							}
//
//							if (((LinearLayout) (itemlayout.getChildAt(0)))
//									.getChildAt(0).getTag().equals("N")) {
//								itemlistlayout.removeViewAt(i);
//							}
//							i--;
//						}
//					}
//					CalculateTotal();
//				}
//			});
//
//			alertDialog.setButton2("CANCEL",
//					new DialogInterface.OnClickListener() {
//						public void onClick(DialogInterface dialog, int which) {
//						}
//					});
//			alertDialog.show();

                break;
            //case R.id.butqty: //Commented by ArkarMoe on [19/12/2016]
            //CreatingqtyDialog();
            //break;
            case R.id.butroomSelect: //Added by ArkarMoe on [19/12/2016]
                Room_Dialog();
                break;
            case R.id.butNewVoucher:
                getNewVoucher();
                break;
            case R.id.butsubmit:
                salesmen_dialog();
                break;
            case R.id.butbill:
                Billing_Data();
                break;
            case R.id.butCodeFind:
                Codefind_dialog();
                break;
            case R.id.butdetail:
                butdetail_click(v);
                break;
            case R.id.butconfirm:
                butconfirm_click();
                break;
            case R.id.butvoudetail:
                break;
            case R.id.butothers:
                otherpopup_dialog();
                break;
            case R.id.butitemselect:
                singleimageitem();
                break;
            case R.id.butfire:
                Fire_withProgressbar();
                break;
            case R.id.butsplitbill:
                itemtransfer=false;//added by KLM to prevent display transfer while itemtransfer is true while clicking split bill 30112022
                //added WHM [2020-05-14]
                if (deliverytype_id != 0) {
                    showAlertDialogBox(ctx, "Warning!", "This voucher is delivery or self pickup voucher!", false);
                } else {
                    Bundle b = this.getIntent().getExtras();
                    ((TextView) findViewById(R.id.txtTableNo)).setTag(b.getString("TableID"));
                    String tableID = b.getString("TableID");

                    List<Table_Name> tablename = dbhelper.getActiveTableByTableID(b
                            .getString("TableID"));

                    SplitedVoucher(tableID, tablename);
                }
                break;

        }
    }

    public void clearAll_withnew_dialog() {
        final LinearLayout itemlistlayout = (LinearLayout) findViewById(R.id.itemlistlayout);

        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Confirm");
        alertDialog.setMessage("Are you sure to remove!");

        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (itemlistlayout.getChildCount() > 0) {
                    int i = itemlistlayout.getChildCount() - 1;
                    maxsr = 0;
                    while (itemlistlayout.getChildCount() > 0) {

                        LinearLayout itemlayout = (LinearLayout) itemlistlayout
                                .getChildAt(i);
                        if (!((LinearLayout) (itemlayout.getChildAt(0)))
                                .getChildAt(0).getTag().equals("N")) {
                            TextView txtsr = (TextView) ((LinearLayout) (itemlayout
                                    .getChildAt(0))).getChildAt(0);
                            maxsr = Integer.parseInt(txtsr.getText()
                                    .toString().replace(".", "").trim());
                            break;
                        }

                        if (((LinearLayout) (itemlayout.getChildAt(0)))
                                .getChildAt(0).getTag().equals("N")) {
                            itemlistlayout.removeViewAt(i);
                        }
                        i--;
                    }
                }
                CalculateTotal();
            }
        });

        alertDialog.setButton2("CANCEL",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        alertDialog.show();
    }

    public void clearAll_withoutnew_dialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Information");
        alertDialog.setMessage("All items are printed.");
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

            }
        });
        alertDialog.show();
    }

    public void butconfirm_click() {

        if (!GlobalClass.CheckConnection(ctx)) {
            ConnectionStatus();
            showAlertDialog(ctx, "Warning!", "No connection with Server.", false);
            return;
        }

        if (GlobalClass.use_foodtruck) {
            Intent intent = new Intent(this, VoucherDetail.class);

            Bundle b = new Bundle();
            b.putString("tranid", saleid);
            b.putString("userid", ((TextView) findViewById(R.id.txtDocID)).getTag().toString());
            b.putString("TableID", tableID);//added WHM [2020-01-03]
            intent.putExtras(b);
            startActivityForResult(intent, 100);

        } else {
            if (GlobalClass.CheckConnection(ctx)) {
                Intent intent = new Intent(this, VoucherDetail.class);

                Bundle b = new Bundle();
                b.putString("tranid", saleid);
                b.putString("userid", ((TextView) findViewById(R.id.txtDocID)).getTag().toString());
                b.putString("TableID", tableID);//added WHM [2020-01-03]
                intent.putExtras(b);
                startActivityForResult(intent, 100);


            } else {
                ConnectionStatus();
                showAlertDialog(ctx, "Warning!", "No connection with Server.", false);
            }
        }
//        Intent intent = new Intent(this, VoucherDetail.class);
//
//        Bundle b = new Bundle();
//        b.putString("tranid", saleid);
//        b.putString("userid", ((TextView) findViewById(R.id.txtDocID)).getTag()
//                .toString());
//        b.putString("TableID", tableID);//added WHM [2020-01-03]
//        intent.putExtras(b);
//        startActivityForResult(intent, 100);

        // this.finish();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Intent intent = new Intent(this, TableScreenActivity.class);
            startActivity(intent);
            this.finish();
        }
    }

    public void Codefind_dialog() {            //added by ZYP [07-07-2020] for code find

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = this.getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.activity_codefind, null));

        final Dialog dialog = builder.create();
        dialog.show();

        Window window = dialog.getWindow();
        window.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        dialog.show();

        final ListView codefindListView = (ListView) dialog.findViewById(R.id.codefindlist);
        EditText txtcodeSearch = (EditText) dialog.findViewById(R.id.txtcodeSearch);
        EditText txtdesSearch = (EditText) dialog.findViewById(R.id.txtdesSearch);

        final List<ItemsObj> itemList = dbhelper.SearchItemslistbyDescription("");
        final List<ItemsObj> tempArrayList = new ArrayList<ItemsObj>();
        tempArrayList.addAll(itemList);

        final CodeFindListViewAdapter codefindadapter = new CodeFindListViewAdapter(
                getApplicationContext(), R.layout.search_item, tempArrayList);
        codefindListView.setAdapter(codefindadapter);

        txtcodeSearch.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //Toast.makeText(getApplicationContext(),charSequence.toString(),Toast.LENGTH_SHORT).show();
                tempArrayList.clear();
                String searchText = charSequence.toString().replaceAll("%", "").toLowerCase().trim();
                int textlength = searchText.length();
                //int textlength = charSequence.length();
                if (charSequence.toString().startsWith("%") && charSequence.toString().endsWith("%")) {

                    for (ItemsObj item : itemList) {
                        if (textlength <= item.getusr_code().length()) {
                            if (item.getusr_code().toLowerCase().contains(searchText) &&
                                    !item.getusr_code().toLowerCase().startsWith(searchText) &&
                                    !item.getusr_code().toLowerCase().endsWith(searchText)) {
                                tempArrayList.add(item);
                            }
                        }
                    }
                } else if (charSequence.toString().startsWith("%")) {

                    for (ItemsObj item : itemList) {
                        if (textlength <= item.getusr_code().length()) {
                            if (item.getusr_code().toLowerCase().endsWith(searchText)) {
                                tempArrayList.add(item);
                            }
                        }
                    }
                } else {

                    for (ItemsObj item : itemList) {
                        if (textlength <= item.getusr_code().length()) {
                            if (item.getusr_code().toLowerCase().startsWith(searchText)) {
                                tempArrayList.add(item);
                            }
                        }
                    }

                }

                CodeFindListViewAdapter oddadapter = new CodeFindListViewAdapter(
                        getApplicationContext(), R.layout.search_item, tempArrayList);
                codefindListView.setAdapter(oddadapter);
            }

            public void afterTextChanged(Editable editable) {

            }

        });

        txtdesSearch.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //oddadapter.getFilter().filter(charSequence);
                tempArrayList.clear();
                String searchText = charSequence.toString().replaceAll("%", "").toLowerCase().trim();
                int textlength = searchText.length();

                if (!charSequence.toString().contains("%")) {

                    for (ItemsObj item : itemList) {
                        if (textlength <= item.getdescription().length()) { //modified by MPPA [07-05-2021]
                            if (item.getdescription().toLowerCase().contains(searchText)) {
                                tempArrayList.add(item);
                            }
                        }
                    }
                } else if (charSequence.toString().startsWith("%") && charSequence.toString().endsWith("%")) {

                    for (ItemsObj item : itemList) {
                        if (textlength <= item.getdescription().length()) { //modified by MPPA [07-05-2021]
                            if (item.getdescription().toLowerCase().contains(searchText) &&
                                    !item.getdescription().toLowerCase().startsWith(searchText) &&
                                    !item.getdescription().toLowerCase().endsWith(searchText)) {
                                tempArrayList.add(item);
                            }
                        }
                    }
                } else if (charSequence.toString().startsWith("%")) {

                    for (ItemsObj item : itemList) {
                        if (textlength <= item.getdescription().length()) { //modified by MPPA [07-05-2021]
                            if (item.getdescription().toLowerCase().endsWith(searchText)) {
                                tempArrayList.add(item);
                            }
                        }
                    }
                } else if (charSequence.toString().endsWith("%")) {

                    for (ItemsObj item : itemList) {
                        if (textlength <= item.getdescription().length()) { //modified by MPPA [07-05-2021]
                            if (item.getdescription().toLowerCase().startsWith(searchText)) {
                                tempArrayList.add(item);
                            }
                        }
                    }
                }

                CodeFindListViewAdapter oddadapter = new CodeFindListViewAdapter(
                        getApplicationContext(), R.layout.search_item, tempArrayList);
                codefindListView.setAdapter(oddadapter);

            }

            public void afterTextChanged(Editable editable) {

            }
        });

        codefindListView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                ConnectionStatus();
                if (!GlobalClass.CheckConnection(ctx)) {

                    showAlertDialog(ctx, "Warning!",
                            "No connection with Server.", false);
                    return;
                }
                //tempArrayList.get(position).getdescription();
                //Toast.makeText(OrderTaking.this,tempArrayList.get(position).getdescription(),Toast.LENGTH_SHORT).show();

                String code = tempArrayList.get(position).getcode();
                String description = tempArrayList.get(position).getdescription();

                if (dbhelper.isValueItem(code)) {
                    //txtDiff.setVisibility(View.VISIBLE);

                    if (dbhelper.isPromotionItem(code)) {
                        LinearLayout vItemLayout = selectvalueItemtolist(code, description, "0");
                    } else {
                        LinearLayout vItemLayout = selectvalueItemtolist(code, description, "0");
                    }

                } else {
                    //txtDiff.setVisibility(View.INVISIBLE);

                    if (dbhelper.isPromotionItem(code)) {
                        itemlayout = selectItemtolist(code, description, "0", true);
                    } else {
                        itemlayout = selectItemtolist(code, description, "0", true);
                    }


                }

//                Button butitem = new Button(ctx);
//                butitem.setText(((TextView) v).getText().toString());
//                butitem.setTag(((TextView) v).getTag().toString());

                List<SelectedItemModifierObj> itemsobjlist = dbhelper.getsetmenuItemslistbyitemid(code);

                if (itemsobjlist.size() > 0) {
                    BindModifieritemtolist(itemlayout, itemsobjlist, true);
                }
//                if (modifierpop == 1) {
//                    extraitemdialog(butitem, itemlayout);
//                }
                CalculateTotal();

                dialog.dismiss();
            }
        });

        Button btnClose = (Button) dialog.findViewById(R.id.btnClose);
        btnClose.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    public void butdetail_click(View v) {
        if (viewdetail == true) {
            viewdetail = false;
            ImageView imgview = (ImageView) findViewById(R.id.detailimageselected);
            imgview.setImageDrawable(null);
        } else {
            viewdetail = true;
            Drawable top = getResources().getDrawable(R.drawable.selected);
            ImageView imgview = (ImageView) findViewById(R.id.detailimageselected);
            imgview.setImageDrawable(top);
        }
        // ConnectionStatus();
    }

    /**
     * @param v
     */
    public void Itemdetail_dialog(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.activity_itemdetail, null));
        final Dialog dialog = builder.create();

        // wmlp.gravity = Gravity.CENTER;
        dialog.show();
        Window window = dialog.getWindow();
        // int width = (int)
        // TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 600,
        // getResources().getDisplayMetrics());
        window.setLayout(GlobalClass.getDPsize(550, this),
                LayoutParams.MATCH_PARENT);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

        viewdetail = false;
        ImageView imgview = (ImageView) findViewById(R.id.detailimageselected);
        imgview.setImageDrawable(null);

        // btndetail.setBackgroundResource(R.drawable.saleoptionbutton);

        TextView txtusercode = (TextView) dialog.findViewById(R.id.txtusercode);
        TextView txtclass = (TextView) dialog.findViewById(R.id.txtclass);
        TextView txtcagegory = (TextView) dialog.findViewById(R.id.txtcategory);
        TextView txtitemname = (TextView) dialog.findViewById(R.id.txtitemname);
        TextView txtdescription = (TextView) dialog.findViewById(R.id.txtdesc);
        TextView txtprice = (TextView) dialog.findViewById(R.id.txtprice);
        TextView txtsetItems = (TextView) dialog.findViewById(R.id.txtsetItems);

        txtdescription.setTypeface(font);
        txtitemname.setTypeface(font);
        txtcagegory.setTypeface(font);
        txtsetItems.setTypeface(font);

        final ItemsObj itemobj = dbhelper.getItemsbyitemid(((TextView) v).getTag().toString());

        //added by ZYP on 14-10-2020 item detail setmenu
        List<SelectedItemModifierObj> itemsobjlist = dbhelper.getsetmenuItemslistbyitemid(((TextView) v).getTag()
                .toString());

        if (itemsobjlist.size() > 0) {
            txtsetItems.setVisibility(View.VISIBLE);
            int i = 1;
            for (SelectedItemModifierObj item : itemsobjlist) {
                txtsetItems.setText(txtsetItems.getText().toString() + i++ + ". " + item.getname() + "\n");
            }
        } else {
            txtsetItems.setVisibility(View.GONE);
        }

        txtusercode.setText("" + itemobj.getusr_code());
        txtclass.setText("class - " + itemobj.getclassname());
        txtcagegory.setText("category - " + itemobj.getcategoryname());
        txtitemname.setText(itemobj.getdescription());
        txtdescription.setText(itemobj.getremark());

        String curr_short = Get_Curr_Short(Integer.parseInt(itemobj.getOrg_curr()));

        txtprice.setText(curr_short + " " + getCurrencyFormat(itemobj.getsale_price()));

//       CustomImageVIew mImageView = (CustomImageVIew) dialog
//               .findViewById(R.id.customImageview);
        //Commented by KNO (31-05-2022)
//        String photoPath = GlobalClass.GetImageStorageLocation(this) + "/"
//                + itemobj.getusr_code().trim() + ".jpg";
//        File file = new File(photoPath);
//        if (file.exists()) {
//            try {
//                if (((file.length() / 1024) / 1024) <= 1) {
//                    BitmapFactory.Options options = new BitmapFactory.Options();
//                    options.inSampleSize = 1;
//                    final Bitmap bitmap = BitmapFactory.decodeFile(photoPath,
//                            options);
//
//                    mImageView.setImageBitmap(bitmap);
//                }
//
//            } catch (Exception ex) {
//
//            }
//        } else {
//            //added ZYP [2020-09-15] default image
//            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.default_menuimage);
//            mImageView.setImageBitmap(bitmap);
//            mImageView.setScaleType(ScaleType.FIT_XY);
//
//        }

        // if(itemobj.getphoto() != null)
        // mImageView.setImageBitmap(itemobj.getphoto());
        ImageView mImageView = (ImageView) dialog.findViewById(R.id.imgview);
        final String dataurl = new DatabaseHelper(this).getServiceURL();
        Picasso.get().load(dataurl + "/Data/GetImage?usr_code="
                + itemobj.getusr_code().trim())
                .placeholder(R.drawable.default_menuimage)
                .error(R.drawable.default_menuimage)
                .into(mImageView);

        List<UnitCodeObj> unitobjlist = dbhelper.getunitcodebyitemid(((TextView) v).getTag().toString());

        if (unitobjlist.size() > 0) {
            TextView txtunit1 = (TextView) dialog.findViewById(R.id.txtunit1);
            txtunit1.setText("1" + (unitobjlist.get(0).getshortname()) + " - "
                    + unitobjlist.get(0).getsaleprice
                    ());
            txtunit1.setVisibility(View.VISIBLE);
            txtprice.setVisibility(View.GONE);
        }

        if (unitobjlist.size() > 1) {
            TextView txtunit2 = (TextView) dialog.findViewById(R.id.txtunit2);
            txtunit2.setText("1" + (unitobjlist.get(1).getshortname()) + " - "
                    + unitobjlist.get(1).getsaleprice());
            txtunit2.setVisibility(View.VISIBLE);
        }

        if (unitobjlist.size() > 2) {
            TextView txtunit3 = (TextView) dialog.findViewById(R.id.txtunit3);
            txtunit3.setText("1" + (unitobjlist.get(2).getshortname()) + " - "
                    + unitobjlist.get(2).getsaleprice());
            txtunit3.setVisibility(View.VISIBLE);
        }
        // ConnectionStatus();

        //added by MPPA [27-01-2021]
        final Button btn_soldout = (Button) dialog.findViewById(R.id.btnsoldout);
        //if (GlobalClass.use_foodtruck) {
        if (dbhelper.isSoldOutItem(itemobj.getcode())) {
            btn_soldout.setText("Unsold Out");
            btn_soldout.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    dbhelper.unSoldoutItem(itemobj.getcode());
                    if (!GlobalClass.use_foodtruck) {
                        dbhelper.updateunSoldoutItem(itemobj.getusr_code());
                    }

                    BindClasscategoryButtons();
                    dialog.dismiss();
                }
            });
        } else {
            btn_soldout.setText("Sold Out");
            btn_soldout.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    SoldOutItem additem = new SoldOutItem();
                    additem.setCode(Integer.parseInt(itemobj.getcode()));
                    additem.setUsr_code(itemobj.getusr_code());
                    additem.setDescription(itemobj.getdescription());
                    dbhelper.AddSoldOutitem(additem);
                    if (!GlobalClass.use_foodtruck) {
                        dbhelper.updatesoldoutitem(itemobj.getusr_code());
                    }

                    BindClasscategoryButtons();
                    dialog.dismiss();
                }
            });
            //}
        }


    }

    public void extraitemdialog(View v, final LinearLayout itemlayout) {
        List<ItemsObj> extraitemlist = dbhelper
                .getmodifierItemslistbyitemid(((TextView) v).getTag()
                        .toString());
        if (extraitemlist.size() > 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            // Get the layout inflater
            LayoutInflater inflater = this.getLayoutInflater();

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog
            // layout
            builder.setView(inflater.inflate(R.layout.activity_extraitemlist, null));
            final Dialog dialog = builder.create();

            WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
            wmlp.gravity = Gravity.CENTER;
            dialog.show();

            Window window = dialog.getWindow();
            window.setLayout(GlobalClass.getDPsize(550, this),
                    GlobalClass.getDPsize(400, this));
            dialog.setCanceledOnTouchOutside(true);
            dialog.show();

            final LinearLayout extraitemlayout = (LinearLayout) dialog
                    .findViewById(R.id.extraitemlist);

            extraitemlayout.removeAllViews();
            // int height = (int)
            // TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60,
            // getResources().getDisplayMetrics());
            LinearLayout.LayoutParams modifieditemlayout = new LayoutParams(
                    LayoutParams.MATCH_PARENT, GlobalClass.getDPsize(60, this));

            // int width = (int)
            // TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200,
            // getResources().getDisplayMetrics());
            LayoutParams modifieritemlayout = new LayoutParams(
                    GlobalClass.getDPsize(190, this), LayoutParams.MATCH_PARENT);

            modifieritemlayout.setMargins(5, 0, 0, 0);
            // width = (int)
            // TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30,
            // getResources().getDisplayMetrics());
            LayoutParams modifierqtylayout = new LayoutParams(
                    GlobalClass.getDPsize(30, this), LayoutParams.MATCH_PARENT);


            // width = (int)
            // TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60,
            // getResources().getDisplayMetrics());
            LayoutParams buttonlayout = new LayoutParams(GlobalClass.getDPsize(
                    50, this), GlobalClass.getDPsize(50, this));

            LayoutParams checkboxlayout = new LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
            LayoutParams dividerlayout = new LayoutParams(
                    LayoutParams.MATCH_PARENT, 1);

            // width = (int)
            // TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 170,
            // getResources().getDisplayMetrics());
            LayoutParams pricelayout = new LayoutParams(GlobalClass.getDPsize(
                    100, this), LayoutParams.MATCH_PARENT);

            buttonlayout.setMargins(10, 0, 10, 0);

            TextView txtitemname = (TextView) dialog.findViewById(R.id.txtItemname);

            txtitemname.setText("Modifier Items ( "
                    + ((Button) v).getText().toString() + " )");
            txtitemname.setTag(((Button) v).getTag().toString());
            txtitemname.setTypeface(font);

            for (ItemsObj itemsObj : extraitemlist) {

                LinearLayout itemlist = new LinearLayout(this);

                TextView txtmodifieritem = new TextView(this);
                txtmodifieritem.setText(itemsObj.getdescription());
                txtmodifieritem.setTag(itemsObj.getcode());
                txtmodifieritem.setGravity(Gravity.CENTER | Gravity.LEFT);
                txtmodifieritem.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                txtmodifieritem.setTypeface(font);

                final TextView txtmodifierqty = new TextView(this);
                txtmodifierqty.setText("1");
                txtmodifierqty.setGravity(Gravity.CENTER);
                txtmodifierqty.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                txtmodifierqty.setMinWidth(30);

                Button btnplus = new Button(this);

                btnplus.setBackgroundResource(R.drawable.squareplus);

                btnplus.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        int qty = Integer.parseInt(txtmodifierqty.getText()
                                .toString());
                        qty = qty + 1;
                        txtmodifierqty.setText(Integer.toString(qty));
                    }
                });

                Button btnminus = new Button(this);

                btnminus.setBackgroundResource(R.drawable.squareminus);
                btnminus.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        int qty = Integer.parseInt(txtmodifierqty.getText()
                                .toString());
                        if (qty > 1) {
                            qty = qty - 1;
                            txtmodifierqty.setText(Integer.toString(qty));
                        }
                    }
                });

                CheckBox chkbox = new CheckBox(this);

                TextView txtprice = new TextView(this);

                String price = itemsObj.getsale_price();
                if (price.equals("") || price.equals("null")) {
                    price = "0";
                }

                price = Integer.toString((int) (Double.parseDouble(price)));

                txtprice.setText("Price - " + getCurrencyFormat(price));
                txtprice.setGravity(Gravity.CENTER_VERTICAL);

                itemlist.addView(txtmodifieritem, modifieritemlayout);
                itemlist.addView(btnminus, buttonlayout);
                itemlist.addView(txtmodifierqty, modifierqtylayout);
                itemlist.addView(btnplus, buttonlayout);
                itemlist.addView(txtprice, pricelayout);
                itemlist.addView(chkbox, checkboxlayout);

                extraitemlayout.addView(itemlist, modifieditemlayout);

                TextView txtline = new TextView(this);
                txtline.setBackgroundColor(Color.parseColor("#767c78"));
                extraitemlayout.addView(txtline, dividerlayout);

            }
            Button btnOK = (Button) dialog.findViewById(R.id.butOK);
            final List<SelectedItemModifierObj> modifierobjlist = new ArrayList<SelectedItemModifierObj>();
            btnOK.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    for (int i = 0; i < extraitemlayout.getChildCount(); i++) {
                        View view = (View) extraitemlayout.getChildAt(i);

                        if (view instanceof LinearLayout) {
                            LinearLayout layout = (LinearLayout) view;
                            if (layout.getChildCount() > 1) // don't consider
                            // for dividerline
                            {
                                CheckBox chkitem = (CheckBox) layout
                                        .getChildAt(5);
                                TextView txtitem = (TextView) layout
                                        .getChildAt(0);
                                TextView txtqty = (TextView) layout
                                        .getChildAt(2);
                                if (chkitem.isChecked()) {
                                    SelectedItemModifierObj modifierobj = new SelectedItemModifierObj();
                                    modifierobj.setitemid(txtitem.getTag()
                                            .toString());
                                    modifierobj.setname(txtitem.getText()
                                            .toString());
                                    modifierobj.setqty(txtqty.getText()
                                            .toString());

                                    String price = dbhelper
                                            .getModifierpricebyitemid(txtitem
                                                    .getTag().toString());
                                    Double modifieramount = ((Integer
                                            .parseInt(txtqty.getText()
                                                    .toString())) * Double
                                            .parseDouble(price == "" ? "0"
                                                    : price));
                                    modifierobj.setamount(Double
                                            .toString(modifieramount));

                                    modifierobj.setstatus("modifier");
                                    modifierobjlist.add(modifierobj);
                                }
                            }
                        }
                    }

                    if (modifierobjlist.size() > 0) {
                        BindModifieritemtolist(itemlayout, modifierobjlist, true);
                    }

                    if (dbhelper.getAppSetting("QtyChangeKitchenPrint").equals("Y")) {
                        CalculateTotal();
                    }
                    dialog.dismiss();
                }
            });
            Button butcancel = (Button) dialog.findViewById(R.id.butCancel);
            butcancel.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    dialog.cancel();
                }
            });
        }
        // ConnectionStatus();
    }

    public void customer_dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.activity_customer, null));
        final Dialog dialog = builder.create();

        WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.CENTER;
        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(GlobalClass.getDPsize(420, this),
                LayoutParams.MATCH_PARENT);
        //GlobalClass.getDPsize(500, this));
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

        final AutoCompleteTextView actvCustomer = (AutoCompleteTextView) dialog
                .findViewById(R.id.txtautocompletecustomer);
        List<CustomerObj> customerlist = dbhelper.getCustomer();

        String[] customernamearray = new String[customerlist.size()];
        int i = 0;
        for (CustomerObj customerobj : customerlist) {
            customernamearray[i] = customerobj.getcustomercode() + " - "
                    + customerobj.getcustomername();
            i++;
        }

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.select_dialog_item, customernamearray);

        final TextView txtcust = (TextView) findViewById(R.id.txtcustomer);
        final TextView txtcustcount = (TextView) dialog.findViewById(R.id.txtcustcount);
        final Button butclear = (Button) dialog.findViewById(R.id.butclearcustcount);
        final Button butok = (Button) dialog.findViewById(R.id.butok);

        // ///////added by WaiWL on 28/07/2015
        final Button butCustCountPlus = (Button) dialog
                .findViewById(R.id.butCustCountplus);
        final Button butCustCountMinus = (Button) dialog
                .findViewById(R.id.butCustCountminus);
        // /////////

        //Added by Arkar Moe on [01/09/2016]
        final Button butNewCustomer = (Button) dialog.findViewById(R.id.butNewCustomer);
        //

        final LinearLayout customerlayout = (LinearLayout) dialog
                .findViewById(R.id.customercountlayout);
        customerlayout.setVisibility(View.VISIBLE);

        //region memberdiscount
        final LinearLayout memdislayout = (LinearLayout) dialog.findViewById(R.id.layout_memdis);
        memdislayout.setVisibility(View.VISIBLE);
        final EditText edtMemDis = dialog.findViewById(R.id.txtMemDis);
        final Button btnAddMemDis = dialog.findViewById(R.id.btnAddMemDis);

        if (isNewVoucher) {
            btnAddMemDis.setVisibility(View.INVISIBLE);

        } else {
            //edtMemDis.setText(saleobj.get(0).getMembercard());

            btnAddMemDis.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    btnMemDis_Click(v);

                }
            });
        }
        //endregion memberdiscount

        butok.setVisibility(View.VISIBLE);

        txtcustcount.setText(((TextView) findViewById(R.id.txtcustcount)).getText());

        actvCustomer.setThreshold(1);

        actvCustomer.setAdapter(adapter);

        // added by WaiWL on 28/07/2015
        butclear.setVisibility(View.GONE);

        butCustCountPlus.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                txtcustcount.setText(Integer.toString(Integer
                        .parseInt(txtcustcount.getText().toString()) + 1));
            }
        });

        butCustCountMinus.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (Integer.parseInt(txtcustcount.getText().toString()) > 1)
                    txtcustcount.setText(Integer.toString(Integer
                            .parseInt(txtcustcount.getText().toString()) - 1));
            }
        });
        // //////////////

        //Added by Arkar Moe on [01/09/2016]-[Res-0355]-Customized
        butNewCustomer.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                //added by EKK on 15-01-2020
                String UserID = dbhelper.getwaiterid();
                if (dbhelper.Allow_create_Customer(UserID) == true) {
                    dialog.dismiss();
                    customer_setup();
                } else {
                    showMessageDialog("Message", "This user hasn't permission to create customer.");
                }
            }
            //[Res-0355]-Customized

        });
        /////
        actvCustomer.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {

                String[] customer = adapter.getItem(position).split(" - ");
                txtcust.setTag(dbhelper.getCustomerIDByCustomerCode(customer[0]));
                txtcust.setText(adapter.getItem(position));

                ((TextView) findViewById(R.id.txtcustcount)).setText("");
                ((TextView) findViewById(R.id.txtcustcount)).setText(txtcustcount.getText());

                dialog.dismiss();
            }
        });

        ListView customerlistview = (ListView) dialog.findViewById(R.id.custlist);

        final List<RowItem> rowItems = new ArrayList<RowItem>();
        for (int j = 0; j < customernamearray.length; j++) {
            RowItem item = new RowItem(j, j, "", customernamearray[j], "");
            rowItems.add(item);
        }

        CustomerCustomListViewAdapter oddadapter = new CustomerCustomListViewAdapter(
                this, R.layout.customerlist_item, rowItems);
        customerlistview.setAdapter(oddadapter);

        customerlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                String[] customer = rowItems.get(position).getdetail().split(" - ");

                txtcust.setTag(dbhelper.getCustomerIDByCustomerCode(customer[0]));
                txtcust.setText(rowItems.get(position).getdetail());

                ((TextView) findViewById(R.id.txtcustcount)).setText("");
                ((TextView) findViewById(R.id.txtcustcount)).setText(txtcustcount.getText());

                dialog.dismiss();
                if (dbhelper.getUseCustomer_analysisFlag().equals("Y"))
                    customer_analysis_dialog();
            }
        });

        butclear.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                txtcustcount.setText("1");
            }
        });

        butok.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                ((TextView) findViewById(R.id.txtcustcount)).setText("");
                ((TextView) findViewById(R.id.txtcustcount)).setText(txtcustcount.getText());
                dialog.dismiss();
                if (dbhelper.getUseCustomer_analysisFlag().equals("Y"))
                    customer_analysis_dialog();
            }
        });

        LinearLayout customerlistlayout = (LinearLayout) dialog
                .findViewById(R.id.customerlistlayout);
        GlobalClass.ChangeLanguage(customerlistlayout, this, 18, font);
        // ConnectionStatus();
    }

    //Added by Arkar Moe on [14/09/2016]-[Res-0398]
    public void customer_setup() {
        // if (saleid != "0") {
        String tranid = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.activity_customer_setup, null));
        final Dialog dialog = builder.create();

        WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();


        Window window = dialog.getWindow();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        window.setLayout(GlobalClass.getDPsize(800, this), GlobalClass.getDPsize(800, this));
        dialog.setCanceledOnTouchOutside(true);
        wmlp.gravity = Gravity.CENTER;
        dialog.show();

        Button btnAdd;
        btnAdd = ((Button) dialog
                .findViewById(R.id.btnAdd));

        btnAdd.setOnClickListener(new OnClickListener() {

                                      public void onClick(View v) {
                                          ;

                                          EditText text1 = (EditText) dialog
                                                  .findViewById(R.id.txtCompanyName);
                                          EditText text2 = (EditText) dialog
                                                  .findViewById(R.id.txtCustomerCode);
                                          EditText text3 = (EditText) dialog
                                                  .findViewById(R.id.txtCustomerName);

                                          CheckBox chkCredit = (CheckBox) dialog.
                                                  findViewById(R.id.chkCustomerCredit);

                                          CompanyName = text1.getText().toString(); //added by ZYP for unicode
                                          CustomerCode = text2.getText().toString();
                                          CustomerName = text3.getText().toString();
                                          if (chkCredit.isChecked()) {
                                              CustomerCredit = "1";
                                          } else {
                                              CustomerCredit = "0";
                                          }

                                          if (!CustomerName.equals("") && !CustomerCode.equals("")) {

                                              if (btnAddCustomer_click(CompanyName, text1, CustomerCode, text2, CustomerName, text3, CustomerCredit, chkCredit)) {
                                                  dbhelper.LoadCustomer(dbhelper.getServiceURL());
                                                  final TextView txtcust = (TextView) findViewById(R.id.txtcustomer);


                                                  txtcust.setTag(dbhelper.getCustomerIDByCustomerCode(text2.getText().toString()));
                                                  txtcust.setText(CustomerCode + "-" + CustomerName);
                                                  dialog.dismiss();
                                              }

                                          } else {
                                              AlertDialog alertDialog = new AlertDialog.Builder(
                                                      ctx).create();
                                              alertDialog.setTitle("Message");
                                              alertDialog
                                                      .setMessage("Please, Enter Customer Information!");
                                              alertDialog.setIcon(0);

                                              alertDialog.setButton2("OK", new DialogInterface.OnClickListener() {
                                                  public void onClick(
                                                          DialogInterface dialog,
                                                          int which) {
                                                      //customer_setup(txtGuideName);
                                                  }
                                              });

                                              alertDialog.show();
                                          }

                                          // btnsendtonote_click(value,1);
                                          dialog.dismiss();

                                      }

                                  }
        );

        GlobalClass.ChangeLanguage((LinearLayout) dialog.findViewById(R.id.customersetuplayout), this, 18, font);

    }

    //[Res-0398]
    public void setmenuitemlist_dialog(final Dialog editdialog,
                                       final List<SelectedItemModifierObj> modifierobjlist) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.activity_setmenuitem, null));
        final Dialog dialog = builder.create();

        WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.CENTER;

        dialog.show();
        Window window = dialog.getWindow();
        // int width = (int)
        // TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300,
        // getResources().getDisplayMetrics());
        window.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

        //Added by Arkar Moe on [29/09/2016]

        final ListView setmenuitemlist = (ListView) dialog.findViewById(R.id.listsetmenuitem);
        final CheckBox chksameprice = (CheckBox) dialog.findViewById(R.id.chksameprice);
        final Button butchangeitem = (Button) dialog.findViewById(R.id.butchange);
        final Button butsave = (Button) dialog.findViewById(R.id.butsave);
        final Button butqtyplus = (Button) dialog.findViewById(R.id.butQtyplus);
        final Button butqtyminus = (Button) dialog.findViewById(R.id.butQtyminus);
        final EditText inputSearch = (EditText) dialog.findViewById(R.id.inputSearch);
        // /////////

        setmenuitemlist.setTag(modifierobjlist);

        //added by MPPA on 02-09-2021
        setmenuitemlist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id) {
                ItemRemark(txtremark);
                return true;
            }
        });

        inputSearch.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                TextView txtfromitem = (TextView) dialog.findViewById(R.id.txtfromitem);

                ListView allmenuitemlist = (ListView) dialog.findViewById(R.id.listallmenu);
                allmenuitemlist.setAdapter(null);
                List<ItemsObj> itemlist = new ArrayList<ItemsObj>();
                ItemsObj itemobj = new ItemsObj();
                if (chksameprice.isChecked() && txtfromitem.getTag() != null) {
                    itemobj = dbhelper.getItemsbyitemid(txtfromitem.getTag().toString());
//                    itemlist = dbhelper.SearchItemslistbyDescriptionandprice(s.toString(), itemobj.getsale_price());
                    itemlist = dbhelper.SearchItemslistbyDescriptionandprice(s.toString(), itemobj.getsale_price(), txtfromitem.getTag().toString(), mainCode);
                } else {
                    if (txtfromitem.getTag() != null) {

                        itemlist = dbhelper.SearchItemslistbyDescriptionandprice(s.toString(), "-", txtfromitem.getTag().toString(), mainCode);
                    } else {
                        itemlist = dbhelper.SearchItemslistbyDescription(s.toString());
                    }

                }

                // itemlist = dbhelper.SearchItemslistbyDescription(s.toString());

                final List<RowItem> rowItems1 = new ArrayList<RowItem>();
                for (int j = 0; j < itemlist.size(); j++) {
                    RowItem item = new RowItem(j,
                            Integer.parseInt(itemlist.get(j)
                                    .getcode()), itemlist
                            .get(j).getcode(), itemlist
                            .get(j).getdescription(), "");
                    rowItems1.add(item);
                }

                CustomerCustomListViewAdapter allitemadapter = new CustomerCustomListViewAdapter(ctx, R.layout.customerlist_item, rowItems1);
                allmenuitemlist.setAdapter(allitemadapter);

                allmenuitemlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(
                            AdapterView<?> parent,
                            View view, int position,
                            long id) {
                        ViewGroup vg = (ViewGroup) view;
                        TextView txtheader = (TextView) vg.getChildAt(2);
                        TextView txtdetail = (TextView) vg.getChildAt(4);
                        TextView txttoitem = (TextView) dialog.findViewById(R.id.txttoitem);

                        txttoitem.setText(txtdetail.getText());
                        txttoitem.setTag(txtheader.getText());
                    }
                });


            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }
        });


        butqtyplus.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                TextView txtfromitem = (TextView) dialog.findViewById(R.id.txtfromitem);
                if (txtfromitem.getTag() != null) {
                    List<SelectedItemModifierObj> modifierobjlist = (List<SelectedItemModifierObj>) setmenuitemlist.getTag();
                    TextView txtsetitemqty = ((TextView) setmenuitemlist.findViewWithTag(txtfromitem.getTag() + "-Qty"));
                    txtsetitemqty.setText(Integer.toString(Integer.parseInt(txtsetitemqty.getText().toString()) + 1));
                    modifierobjlist.get(Integer.parseInt(txtfromitem.getText().toString())).setqty(txtsetitemqty.getText().toString());
                }

            }
        });

        butqtyminus.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                TextView txtfromitem = (TextView) dialog.findViewById(R.id.txtfromitem);
                if (txtfromitem.getTag() != null) {
                    List<SelectedItemModifierObj> modifierobjlist = (List<SelectedItemModifierObj>) setmenuitemlist.getTag();
                    TextView txtsetitemqty = ((TextView) setmenuitemlist.findViewWithTag(txtfromitem.getTag() + "-Qty"));
                    if (Integer.parseInt(txtsetitemqty.getText().toString()) > 1) {
                        txtsetitemqty.setText(Integer.toString(Integer.parseInt(txtsetitemqty.getText().toString()) - 1));
                        modifierobjlist.get(Integer.parseInt(txtfromitem.getText().toString())).setqty(txtsetitemqty.getText().toString());
                    }

                }
            }
        });

        butsave.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                BindSelectedModifiertoedititemlist(editdialog, (List<SelectedItemModifierObj>) setmenuitemlist.getTag());
                selectedfromsetmenuitemid = "";
                dialog.dismiss();
            }
        });

        butchangeitem.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                TextView txtfromitem = (TextView) dialog
                        .findViewById(R.id.txtfromitem);
                TextView txttoitem = (TextView) dialog
                        .findViewById(R.id.txttoitem);

                List<SelectedItemModifierObj> selectedmodifier = (List<SelectedItemModifierObj>) setmenuitemlist.getTag();
                if (txtfromitem.getTag() != null && txttoitem.getTag() != null) {
                    SetmenuItem rowitem = (SetmenuItem) setmenuitemlist.getItemAtPosition(Integer.parseInt(txtfromitem.getText().toString()));


                    selectedmodifier.get(Integer.parseInt(txtfromitem.getText().toString())).setitemid(txttoitem.getTag().toString());
                    selectedmodifier.get(Integer.parseInt(txtfromitem.getText().toString())).setname(txttoitem.getText().toString());


                    rowitem.setheader(txttoitem.getText().toString());
                    rowitem.settranid(Integer.parseInt(txttoitem.getTag().toString()));
                    ListView Setmenuitemlisttest = (ListView) dialog.findViewById(R.id.listsetmenuitem);
                    ((TextView) setmenuitemlist.findViewWithTag(txtfromitem.getTag())).setText(txttoitem.getTag().toString());
                    ((TextView) setmenuitemlist.findViewWithTag(txtfromitem.getTag() + "-detail")).setText(txttoitem.getText().toString());

                    setmenuitemlist.setTag(selectedmodifier);

                }
            }
        });

        final List<RowItem> rowItems = new ArrayList<RowItem>();

        for (int j = 0; j < modifierobjlist.size(); j++) {
            RowItem item = new RowItem(j, Integer.parseInt(modifierobjlist.get(j).getitemid()), modifierobjlist.get(j).getitemid(), modifierobjlist.get(j).getname(), "");
            rowItems.add(item);
        }

        CustomerCustomListViewAdapter oddadapter = new CustomerCustomListViewAdapter(
                this, R.layout.customerlist_item, rowItems);
        setmenuitemlist.setAdapter(oddadapter);


        setmenuitemlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                ViewGroup vg = (ViewGroup) view;

                TextView txtheader = (TextView) vg.getChildAt(1);
                TextView txtid = (TextView) vg.getChildAt(0);
                TextView txtqty = (TextView) vg.getChildAt(2);
                TextView txtfromitem = (TextView) dialog.findViewById(R.id.txtfromitem);

                txtid.setTag(txtid.getText());
                txtheader.setTag(txtid.getText() + "-detail");
                txtqty.setTag(txtid.getText() + "-Qty");

                txtfromitem.setText(Integer.toString(position));
                txtfromitem.setTag(txtid.getText());


                if (chksameprice.isChecked()) {
                    if (txtfromitem.getTag() != null) {
                        ListView allmenuitemlist = (ListView) dialog
                                .findViewById(R.id.listallmenu);
                        allmenuitemlist.setAdapter(null);
                        List<ItemsObj> itemlist = new ArrayList<ItemsObj>();
                        ItemsObj itemobj = dbhelper.getItemsbyitemid(txtfromitem.getTag().toString());
                        itemlist = dbhelper.getItemslistbysaleprice(itemobj.getsale_price(), txtfromitem.getTag().toString(), mainCode);

                        final List<RowItem> rowItems1 = new ArrayList<RowItem>();
                        for (int j = 0; j < itemlist.size(); j++) {
                            RowItem item = new RowItem(j,
                                    Integer.parseInt(itemlist.get(j)
                                            .getcode()), itemlist
                                    .get(j).getcode(), itemlist
                                    .get(j).getdescription(),
                                    "");
                            rowItems1.add(item);
                        }

                        CustomerCustomListViewAdapter allitemadapter = new CustomerCustomListViewAdapter(
                                ctx, R.layout.customerlist_item,
                                rowItems1);
                        allmenuitemlist.setAdapter(allitemadapter);

                        allmenuitemlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            public void onItemClick(
                                    AdapterView<?> parent,
                                    View view, int position,
                                    long id) {
                                ViewGroup vg = (ViewGroup) view;
                                TextView txtheader = (TextView) vg
                                        .getChildAt(2);
                                TextView txtdetail = (TextView) vg
                                        .getChildAt(4);
                                TextView txttoitem = (TextView) dialog
                                        .findViewById(R.id.txttoitem);

                                txttoitem.setText(txtdetail
                                        .getText());
                                txttoitem.setTag(txtheader
                                        .getText());
                            }
                        });
                    }

                } else {
                    if (txtfromitem.getTag() != null) {
                        ListView allmenuitemlist = (ListView) dialog
                                .findViewById(R.id.listallmenu);
                        allmenuitemlist.setAdapter(null);
                        List<ItemsObj> itemlist = new ArrayList<ItemsObj>();
                        // ItemsObj itemobj = dbhelper.getItemsbyitemid(txtfromitem.getTag().toString(),mainCode);
                        itemlist = dbhelper.getItemslist(txtfromitem.getTag().toString(), mainCode);

                        final List<RowItem> rowItems1 = new ArrayList<RowItem>();
                        for (int j = 0; j < itemlist.size(); j++) {
                            RowItem item = new RowItem(j,
                                    Integer.parseInt(itemlist.get(j)
                                            .getcode()), itemlist
                                    .get(j).getcode(), itemlist
                                    .get(j).getdescription(),
                                    "");
                            rowItems1.add(item);
                        }

                        CustomerCustomListViewAdapter allitemadapter = new CustomerCustomListViewAdapter(
                                ctx, R.layout.customerlist_item,
                                rowItems1);
                        allmenuitemlist.setAdapter(allitemadapter);

                        allmenuitemlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            public void onItemClick(
                                    AdapterView<?> parent,
                                    View view, int position,
                                    long id) {
                                ViewGroup vg = (ViewGroup) view;
                                TextView txtheader = (TextView) vg
                                        .getChildAt(2);
                                TextView txtdetail = (TextView) vg
                                        .getChildAt(4);
                                TextView txttoitem = (TextView) dialog
                                        .findViewById(R.id.txttoitem);

                                txttoitem.setText(txtdetail
                                        .getText());
                                txttoitem.setTag(txtheader
                                        .getText());
                            }
                        });
                    }
                }

                //added by klm


            }
        });

        //Added by Arkar Moe on [29/09/2016]
        final List<SetmenuItem> rowItemsQty = new ArrayList<SetmenuItem>();

        for (int j = 0; j < modifierobjlist.size(); j++) {
            SetmenuItem item = new SetmenuItem(Integer.parseInt(modifierobjlist.get(j).getitemid()), modifierobjlist.get(j).getname(), modifierobjlist.get(j).getqty());
            rowItemsQty.add(item);
        }

        SetMenuCustomListViewAdapter setmenuadapter = new SetMenuCustomListViewAdapter(
                this, R.layout.setmenu_item, rowItemsQty);
        setmenuitemlist.setAdapter(setmenuadapter);
        //Added by Arkar Moe on [29/09/2016]

        List<ItemsObj> itemlist = new ArrayList<ItemsObj>();
        itemlist = dbhelper.getItemslist();


        if (modifierobjlist.size() > 0) {
            itemlist = dbhelper.getItemslistbycode(modifierobjlist.get(0).getitemid());
        }


        final List<SetmenuItem> rowItems1 = new ArrayList<SetmenuItem>();
        for (int j = 0; j < itemlist.size(); j++) {
            SetmenuItem item = new SetmenuItem(Integer.parseInt(itemlist.get(j).getcode()), itemlist.get(j).getdescription(), itemlist.get(j).getunitqty().replace(".0", "").trim());
            rowItems1.add(item);
        }
        final ListView allmenuitemlist = (ListView) dialog.findViewById(R.id.listallmenu);
        SetMenuCustomListViewAdapter allitemadapter = new SetMenuCustomListViewAdapter(
                this, R.layout.setmenu_item, rowItems1);
        allmenuitemlist.setAdapter(allitemadapter);

        allmenuitemlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                ViewGroup vg = (ViewGroup) view;
                TextView txtheader = (TextView) vg.getChildAt(2);
                TextView txtdetail = (TextView) vg.getChildAt(4);
                txtdetail.setTag(txtheader.getText());
                TextView txttoitem = (TextView) dialog
                        .findViewById(R.id.txttoitem);

                txttoitem.setText(txtdetail.getText());
                txttoitem.setTag(txtheader.getText());

            }
        });

        chksameprice.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                TextView txtfromitem = (TextView) dialog
                        .findViewById(R.id.txtfromitem);
                if (isChecked) {
                    ListView allmenuitemlist = (ListView) dialog
                            .findViewById(R.id.listallmenu);
                    allmenuitemlist.setAdapter(null);

                    if (txtfromitem.getTag() != null) {
                        List<ItemsObj> itemlist = new ArrayList<ItemsObj>();
                        ItemsObj itemobj = dbhelper
                                .getItemsbyitemid(txtfromitem.getTag()
                                        .toString());
                        itemlist = dbhelper.getItemslistbysaleprice(itemobj
                                .getsale_price(), txtfromitem.getTag()
                                .toString(), mainCode);

                        final List<RowItem> rowItems1 = new ArrayList<RowItem>();
                        for (int j = 0; j < itemlist.size(); j++) {
                            RowItem item = new RowItem(j, Integer
                                    .parseInt(itemlist.get(j).getcode()),
                                    itemlist.get(j).getcode(), itemlist.get(j)
                                    .getdescription(), "");
                            rowItems1.add(item);
                        }

                        CustomerCustomListViewAdapter allitemadapter = new CustomerCustomListViewAdapter(
                                ctx, R.layout.customerlist_item, rowItems1);
                        allmenuitemlist.setAdapter(allitemadapter);

                        allmenuitemlist
                                .setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    public void onItemClick(
                                            AdapterView<?> parent, View view,
                                            int position, long id) {
                                        ViewGroup vg = (ViewGroup) view;
                                        TextView txtheader = (TextView) vg
                                                .getChildAt(2);
                                        TextView txtdetail = (TextView) vg
                                                .getChildAt(4);
                                        TextView txttoitem = (TextView) dialog
                                                .findViewById(R.id.txttoitem);

                                        txttoitem.setText(txtdetail.getText());
                                        txttoitem.setTag(txtheader.getText());
                                    }
                                });
                    }

                } else {
                    List<ItemsObj> itemlist = new ArrayList<ItemsObj>();
                    if (txtfromitem.getTag() != null) {
                        itemlist = dbhelper.getItemslist(txtfromitem.getTag().toString(), mainCode);

                        final List<RowItem> rowItems1 = new ArrayList<RowItem>();
                        for (int j = 0; j < itemlist.size(); j++) {
                            RowItem item = new RowItem(j, Integer.parseInt(itemlist
                                    .get(j).getcode()), itemlist.get(j).getcode(),
                                    itemlist.get(j).getdescription(), "");
                            rowItems1.add(item);
                        }
                        ListView allmenuitemlist = (ListView) dialog
                                .findViewById(R.id.listallmenu);
                        CustomerCustomListViewAdapter allitemadapter = new CustomerCustomListViewAdapter(
                                ctx, R.layout.customerlist_item, rowItems1);
                        allmenuitemlist.setAdapter(allitemadapter);

                        allmenuitemlist
                                .setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    public void onItemClick(AdapterView<?> parent,
                                                            View view, int position, long id) {
                                        ViewGroup vg = (ViewGroup) view;
                                        TextView txtheader = (TextView) vg
                                                .getChildAt(2);
                                        TextView txtdetail = (TextView) vg
                                                .getChildAt(4);
                                        TextView txttoitem = (TextView) dialog
                                                .findViewById(R.id.txttoitem);

                                        txttoitem.setText(txtdetail.getText());
                                        txttoitem.setTag(txtheader.getText());
                                    }
                                });

                    }
                }

            }
        });
    }

    public void customer_analysis_dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.activity_customer_analysis,
                null));
        final Dialog dialog = builder.create();

        WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.CENTER;

        dialog.show();
        Window window = dialog.getWindow();
        // int width = (int)
        // TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300,
        // getResources().getDisplayMetrics());
        window.setLayout(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

        // added by WaiWL on 15/09/2015
        RadioButton rdoprivate = (RadioButton) dialog
                .findViewById(R.id.rdoprivate);
        RadioButton rdobusiness = (RadioButton) dialog
                .findViewById(R.id.rdobusiness);
        RadioButton rdotravel = (RadioButton) dialog
                .findViewById(R.id.rdotravel);
        TextView txtMale = (TextView) dialog.findViewById(R.id.lblMale);
        TextView txtFemale = (TextView) dialog.findViewById(R.id.lblFemale);
        TextView txtAdult = (TextView) dialog.findViewById(R.id.lblAdult);
        TextView txtChild = (TextView) dialog.findViewById(R.id.lblChild);
        TextView txtWestern = (TextView) dialog.findViewById(R.id.lblWestern);
        TextView txtAsian = (TextView) dialog.findViewById(R.id.lblAsian);
        TextView txtMyanmar = (TextView) dialog.findViewById(R.id.lblMyanmar);

        Object[] objCustCountSetuplable = new Object[10];
        objCustCountSetuplable = dbhelper.getCustomerCountSetup();

        rdoprivate.setText(objCustCountSetuplable[0].toString());
        rdobusiness.setText(objCustCountSetuplable[1].toString());
        rdotravel.setText(objCustCountSetuplable[2].toString());
        txtMale.setText(objCustCountSetuplable[3].toString());
        txtFemale.setText(objCustCountSetuplable[4].toString());
        txtAdult.setText(objCustCountSetuplable[5].toString());
        txtChild.setText(objCustCountSetuplable[6].toString());
        txtWestern.setText(objCustCountSetuplable[7].toString());
        txtAsian.setText(objCustCountSetuplable[8].toString());
        txtMyanmar.setText(objCustCountSetuplable[9].toString());
        // ////////////

        final Button butclose = (Button) dialog.findViewById(R.id.butclose);
        butclose.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });

        final Button butconfirm = (Button) dialog.findViewById(R.id.butconfirm);
        butconfirm.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        final RadioGroup rdogrouptype = (RadioGroup) dialog
                .findViewById(R.id.rdogrouptype);
        if (customer_analysisobj.getcustomertype().equals("P")) {
            rdogrouptype.check(R.id.rdoprivate);
        } else if (customer_analysisobj.getcustomertype().equals("B")) {
            rdogrouptype.check(R.id.rdobusiness);
        } else
            rdogrouptype.check(R.id.rdotravel);

        rdogrouptype.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rdoprivate)
                    customer_analysisobj.setcustomertype("P");
                else if (checkedId == R.id.rdobusiness)
                    customer_analysisobj.setcustomertype("B");
                else if (checkedId == R.id.rdotravel)
                    customer_analysisobj.setcustomertype("T");
            }
        });

        ((TextView) dialog.findViewById(R.id.txtmalecount)).setText(Integer
                .toString(customer_analysisobj.getmalecount()));
        final Button butmaleplus = (Button) dialog
                .findViewById(R.id.butmaleplus);
        butmaleplus.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                int count = Integer.parseInt(((TextView) dialog
                        .findViewById(R.id.txtmalecount)).getText().toString());
                ((TextView) dialog.findViewById(R.id.txtmalecount))
                        .setText(Integer.toString(++count));
                customer_analysisobj.setmalecount(count);
            }
        });
        final Button butmaleminus = (Button) dialog
                .findViewById(R.id.butmaleminus);
        butmaleminus.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                int count = Integer.parseInt(((TextView) dialog
                        .findViewById(R.id.txtmalecount)).getText().toString());
                if (count > 0)
                    count--;
                ((TextView) dialog.findViewById(R.id.txtmalecount))
                        .setText(Integer.toString(count));
                customer_analysisobj.setmalecount(count);
            }
        });

        ((TextView) dialog.findViewById(R.id.txtfemalecount)).setText(Integer
                .toString(customer_analysisobj.getfemalecount()));
        final Button butfemaleplus = (Button) dialog
                .findViewById(R.id.butfemaleplus);
        butfemaleplus.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                int count = Integer.parseInt(((TextView) dialog
                        .findViewById(R.id.txtfemalecount)).getText()
                        .toString());
                ((TextView) dialog.findViewById(R.id.txtfemalecount))
                        .setText(Integer.toString(++count));
                customer_analysisobj.setfemalecount(count);
            }
        });
        final Button butfemaleminus = (Button) dialog
                .findViewById(R.id.butfemaleminus);
        butfemaleminus.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                int count = Integer.parseInt(((TextView) dialog
                        .findViewById(R.id.txtfemalecount)).getText()
                        .toString());
                if (count > 0)
                    count--;
                ((TextView) dialog.findViewById(R.id.txtfemalecount))
                        .setText(Integer.toString(count));
                customer_analysisobj.setfemalecount(count);
            }
        });

        ((TextView) dialog.findViewById(R.id.txtadultcount)).setText(Integer        //added by ZYP [05-03-2020]
                .toString(customer_analysisobj.getadultcount()));
        final Button butadultplus = (Button) dialog
                .findViewById(R.id.butadultplus);
        butadultplus.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                int count = Integer.parseInt(((TextView) dialog
                        .findViewById(R.id.txtadultcount)).getText()
                        .toString());
                ((TextView) dialog.findViewById(R.id.txtadultcount))
                        .setText(Integer.toString(++count));
                customer_analysisobj.setadultcount(count);
            }
        });
        final Button butadultminus = (Button) dialog
                .findViewById(R.id.butadultminus);
        butadultminus.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                int count = Integer.parseInt(((TextView) dialog
                        .findViewById(R.id.txtadultcount)).getText()
                        .toString());
                if (count > 0)
                    count--;
                ((TextView) dialog.findViewById(R.id.txtadultcount))
                        .setText(Integer.toString(count));
                customer_analysisobj.setadultcount(count);
            }
        });

        ((TextView) dialog.findViewById(R.id.txtchildcount)).setText(Integer        //added by ZYP [05-03-2020]
                .toString(customer_analysisobj.getchildcount()));
        final Button butchildplus = (Button) dialog
                .findViewById(R.id.butchildplus);
        butchildplus.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                int count = Integer.parseInt(((TextView) dialog
                        .findViewById(R.id.txtchildcount)).getText()
                        .toString());
                ((TextView) dialog.findViewById(R.id.txtchildcount))
                        .setText(Integer.toString(++count));
                customer_analysisobj.setchildcount(count);
            }
        });
        final Button butchildminus = (Button) dialog
                .findViewById(R.id.butchildminus);
        butchildminus.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                int count = Integer.parseInt(((TextView) dialog
                        .findViewById(R.id.txtchildcount)).getText()
                        .toString());
                if (count > 0)
                    count--;
                ((TextView) dialog.findViewById(R.id.txtchildcount))
                        .setText(Integer.toString(count));
                customer_analysisobj.setchildcount(count);
            }
        });

        ((TextView) dialog.findViewById(R.id.txtwesterncount)).setText(Integer
                .toString(customer_analysisobj.getwesterncount()));
        final Button butwesternplus = (Button) dialog
                .findViewById(R.id.butwesternplus);
        butwesternplus.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                int count = Integer.parseInt(((TextView) dialog
                        .findViewById(R.id.txtwesterncount)).getText()
                        .toString());
                ((TextView) dialog.findViewById(R.id.txtwesterncount))
                        .setText(Integer.toString(++count));
                customer_analysisobj.setwesterncount(count);
            }
        });
        final Button butwesternminus = (Button) dialog
                .findViewById(R.id.butwesternminus);
        butwesternminus.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                int count = Integer.parseInt(((TextView) dialog
                        .findViewById(R.id.txtwesterncount)).getText()
                        .toString());
                if (count > 0)
                    count--;
                ((TextView) dialog.findViewById(R.id.txtwesterncount))
                        .setText(Integer.toString(count));
                customer_analysisobj.setwesterncount(count);
            }
        });

        ((TextView) dialog.findViewById(R.id.txtasiancount)).setText(Integer
                .toString(customer_analysisobj.getasiancount()));
        final Button butasianplus = (Button) dialog
                .findViewById(R.id.butasianplus);
        butasianplus.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                int count = Integer.parseInt(((TextView) dialog
                        .findViewById(R.id.txtasiancount)).getText().toString());
                ((TextView) dialog.findViewById(R.id.txtasiancount))
                        .setText(Integer.toString(++count));
                customer_analysisobj.setasiancount(count);
            }
        });
        final Button butasianminus = (Button) dialog
                .findViewById(R.id.butasianminus);
        butasianminus.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                int count = Integer.parseInt(((TextView) dialog
                        .findViewById(R.id.txtasiancount)).getText().toString());
                if (count > 0)
                    count--;
                ((TextView) dialog.findViewById(R.id.txtasiancount))
                        .setText(Integer.toString(count));
                customer_analysisobj.setasiancount(count);
            }
        });

        ((TextView) dialog.findViewById(R.id.txtmyanmarcount)).setText(Integer
                .toString(customer_analysisobj.getmyanmarcount()));
        final Button butmyanmarplus = (Button) dialog
                .findViewById(R.id.butmyanmarplus);
        butmyanmarplus.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                int count = Integer.parseInt(((TextView) dialog
                        .findViewById(R.id.txtmyanmarcount)).getText()
                        .toString());
                ((TextView) dialog.findViewById(R.id.txtmyanmarcount))
                        .setText(Integer.toString(++count));
                customer_analysisobj.setmyanmarcount(count);
            }
        });
        final Button butmyanmarminus = (Button) dialog
                .findViewById(R.id.butmyanmarminus);
        butmyanmarminus.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                int count = Integer.parseInt(((TextView) dialog
                        .findViewById(R.id.txtmyanmarcount)).getText()
                        .toString());
                if (count > 0)
                    count--;
                ((TextView) dialog.findViewById(R.id.txtmyanmarcount))
                        .setText(Integer.toString(count));
                customer_analysisobj.setmyanmarcount(count);
            }
        });
    }

    public void otherpopup_dialog() //Edited by ArkarMoe on [20/12/2016]
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.activity_otherpopup, null));
        final Dialog dialog = builder.create();

        WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.CENTER;
//        InsetDrawable inset=new InsetDrawable(new ColorDrawable(Color.WHITE),0);
//        dialog.getWindow().setBackgroundDrawable(inset);

        Button btnother = (Button) findViewById(R.id.butothers);
        int[] location = new int[2];
        btnother.getLocationOnScreen(location);
        wmlp.x = location[0];
        wmlp.y = location[1] - 100;
        dialog.show();
        Window window = dialog.getWindow();
        //int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300, getResources().getDisplayMetrics());
        window.setLayout(GlobalClass.getDPsize(280, this), LayoutParams.MATCH_PARENT);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

        ToggleButton tgluseitemimage = (ToggleButton) dialog.findViewById(R.id.tglimageitem);
        tgluseitemimage.setChecked(toggleview == 1 ? true : false);

        ToggleButton tglmodifierpop = (ToggleButton) dialog.findViewById(R.id.tglmodifierpop);
        tglmodifierpop.setChecked(modifierpop == 1 ? true : false);

        ToggleButton tgleditpop = (ToggleButton) dialog.findViewById(R.id.tgleditpop);
        tgleditpop.setChecked(editpop == 1 ? true : false);

        ToggleButton tglsingleimageitem = (ToggleButton) dialog.findViewById(R.id.tglsingleimageitem); //Added by ArkarMoe on [23/12/2016]
        tglsingleimageitem.setChecked(singleimageitem == 1 ? true : false);


        if (dbhelper.getuse_Mealtype() == true)//Added by SMH on 30/05/2017
        {
            final LinearLayout linearlayoutMealType1 = (LinearLayout) dialog.findViewById(R.id.MealType1layout);
            linearlayoutMealType1.setVisibility(View.VISIBLE);
            final LinearLayout linearlayoutMealType2 = (LinearLayout) dialog.findViewById(R.id.MealType2layout);
            linearlayoutMealType2.setVisibility(View.VISIBLE);
            final LinearLayout linearlayoutMealType3 = (LinearLayout) dialog.findViewById(R.id.MealType3layout);
            linearlayoutMealType3.setVisibility(View.VISIBLE);

            List<MealType> MealTypelist = dbhelper.getMealType();

            final TextView MealType1view = (TextView) dialog.findViewById(R.id.MealType1View);
            final TextView MealType2view = (TextView) dialog.findViewById(R.id.MealType2View);
            final TextView MealType3view = (TextView) dialog.findViewById(R.id.MealType3View);

            MealType mt1 = MealTypelist.get(0);
            MealType1view.setText(mt1.getMealType());
            MealType mt2 = MealTypelist.get(1);
            MealType2view.setText(mt2.getMealType());
            MealType mt3 = MealTypelist.get(2);
            MealType3view.setText(mt3.getMealType());

        } else {
            final LinearLayout linearlayoutMealType1 = (LinearLayout) dialog.findViewById(R.id.MealType1layout);
            linearlayoutMealType1.setVisibility(View.GONE);
            final LinearLayout linearlayoutMealType2 = (LinearLayout) dialog.findViewById(R.id.MealType2layout);
            linearlayoutMealType2.setVisibility(View.GONE);
            final LinearLayout linearlayoutMealType3 = (LinearLayout) dialog.findViewById(R.id.MealType3layout);
            linearlayoutMealType3.setVisibility(View.GONE);
        }
        final CheckBox chkMealType1 = (CheckBox) dialog.findViewById(R.id.chkMealType); //Added by SMH on 26/05/2017
        chkMealType1.setChecked(MealType1 == true ? true : false);

        final CheckBox chkMealType2 = (CheckBox) dialog.findViewById(R.id.chkMealType2); //Added by SMH on 26/05/2017
        chkMealType2.setChecked(MealType2 == true ? true : false);

        final CheckBox chkMealType3 = (CheckBox) dialog.findViewById(R.id.chkMealType3); //Added by SMH on 26/05/2017
        chkMealType3.setChecked(MealType3 == true ? true : false);


        Button btnprintStatus = (Button) dialog.findViewById(R.id.btnprintStatus);
        Button btnchangetable = (Button) dialog.findViewById(R.id.btnchangetable);
        Button btnmergetable = (Button) dialog.findViewById(R.id.btnmergetable);
        Button btnsendnote = (Button) dialog.findViewById(R.id.btnsendnote);

        if (((TextView) findViewById(R.id.txtInvoiceno)).getText() == "------" || GlobalClass.use_foodtruck) {
            btnmergetable.setVisibility(View.GONE);
            btnchangetable.setVisibility(View.GONE);

            //window.setLayout(GlobalClass.getDPsize(300, this), GlobalClass.getDPsize(540, this));
            window.setLayout(GlobalClass.getDPsize(280, this), LayoutParams.MATCH_PARENT);

        } else if (dbhelper.CheckPrintBill(new DatabaseHelper(ctx).getServiceURL(), saleid)
                && dbhelper.Allow_edit_after_insert(dbhelper.getwaiterid()) == false) {

            btnchangetable.setVisibility(View.GONE);
            btnmergetable.setVisibility(View.GONE);
            window.setLayout(GlobalClass.getDPsize(280, this), LayoutParams.MATCH_PARENT);
        }

        btnprintStatus.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                printerStatus_dialog();
            }
        });

        btnmergetable.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
                //added WHM [2020-05-14]
                if (deliverytype_id != 0) {
                    showAlertDialogBox(ctx, "Warning!", "This voucher is delivery or self pickup voucher!", false);
                } else {
                    mergetable_dialog();
                }
            }
        });

        btnchangetable.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
                //added WHM [2020-05-14]
                if (deliverytype_id != 0) {
                    showAlertDialogBox(ctx, "Warning!", "This voucher is delivery or self pickup voucher!", false);
                } else {
                    switchtable_dialog();
                }
            }
        });

        btnsendnote.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
                Note_dialog();
            }
        });

        tgluseitemimage.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                toggleview = isChecked == true ? 1 : 0;
                dbhelper.updateuseitemimage(toggleview);
                String str = GetMealType();
                BindItemButton(activecategory, ClassOrCategory, str);
            }
        });

        tglmodifierpop.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                modifierpop = isChecked == true ? 1 : 0;
                dbhelper.updateusemodifierpop(modifierpop);
            }
        });

        tgleditpop.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editpop = isChecked == true ? 1 : 0;
                dbhelper.updateuseeditboxpop(editpop);
            }
        });


        chkMealType1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                MealType1 = chkMealType1.isChecked() == true ? true : false;

                BindClasscategoryButtons();


            }
        });

        chkMealType2.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                MealType2 = chkMealType2.isChecked() == true ? true : false;

                BindClasscategoryButtons();


            }
        });

        chkMealType3.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                MealType3 = chkMealType3.isChecked() == true ? true : false;

                BindClasscategoryButtons();


            }
        });

        tglsingleimageitem.setOnCheckedChangeListener(new OnCheckedChangeListener() { //Added by ArkarMoe on [23/12/2016]

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                singleimageitem = isChecked == true ? 1 : 0;
                dbhelper.updateusesingleimageitem(singleimageitem);

                if (dbhelper.getsigleimageitemflag() == 1) //Added by ArkarMoe on [23/02/2017]
                {
                    dbhelper.updateitemviewstyle(0);
                }

                AlertDialog alertDialog = new AlertDialog.Builder(
                        ctx).create();
                alertDialog.setTitle("Message");
                alertDialog
                        .setMessage("Please, Login again!");
                alertDialog.setIcon(0);

                alertDialog.setButton2("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialog,
                                    int which) {
                                finish();
                            }
                        });

                alertDialog.show();

            }
        });

        //ConnectionStatus();
    }

    // region switch table
    private void switchtable_dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.activity_switchtable, null));
        final Dialog dialog = builder.create();

        WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.CENTER;

        dialog.show();
        Window window = dialog.getWindow();
        // int width = (int)
        // TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300,
        // getResources().getDisplayMetrics());
        window.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

        final TextView txtDocID = (TextView) findViewById(R.id.txtDocID);
        tablechangeinfo.setOldTableID(Integer.parseInt(tableID));
        tablechangeinfo.setNewTableID(Integer.parseInt(tableID));
        tablechangeinfo.setOldUserID(Integer.parseInt(txtDocID.getTag()
                .toString()));
        tablechangeinfo.setNewUserID(Integer.parseInt(txtDocID.getTag()
                .toString()));

        PosUser olduser = dbhelper.getPosUserByUserID(tablechangeinfo
                .getOldUserID());
        final TextView txtcurrentwaiter = (TextView) dialog
                .findViewById(R.id.txtcurrentwaiter);
        txtcurrentwaiter.setTypeface(font);
        final TextView txtnewwaiter = (TextView) dialog
                .findViewById(R.id.txtchangewaiter);
        txtnewwaiter.setTypeface(font);

        txtcurrentwaiter.setText(olduser.getName());
        txtcurrentwaiter.setTag(olduser.getUserId());

        txtnewwaiter.setText(olduser.getName());
        txtnewwaiter.setTag(olduser.getUserId());

        List<PosUser> userlist = dbhelper.getPosUserlist();

        ListView waiterlist = (ListView) dialog.findViewById(R.id.waiterlist);

        final List<RowItem> rowItems = new ArrayList<RowItem>();
        for (int j = 0; j < userlist.size(); j++) {
            RowItem item = new RowItem(j, userlist.get(j).getUserId(), "",
                    userlist.get(j).getShort() + " - "
                            + userlist.get(j).getName(), "");
            rowItems.add(item);
        }

        CustomerCustomListViewAdapter oddadapter = new CustomerCustomListViewAdapter(
                this, R.layout.customerlist_item, rowItems);
        waiterlist.setAdapter(oddadapter);

        waiterlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                ViewGroup vg = (ViewGroup) view;
                TextView txtheader = (TextView) vg.getChildAt(2);
                TextView txtdetail = (TextView) vg.getChildAt(4);
                txtnewwaiter.setText(txtdetail.getText().toString()
                        .split("-")[1].trim());
                txtnewwaiter.setTag(txtheader.getTag());
                tablechangeinfo.setNewUserID(Integer.parseInt(txtheader
                        .getTag().toString()));
            }
        });

        final Button btnNext = (Button) dialog.findViewById(R.id.btnNext);

        final Button btnChange = (Button) dialog.findViewById(R.id.btnSave);

        final RadioButton rdoTabletransfer = (RadioButton) dialog.findViewById(R.id.rdotransfertable);//added by ZYP [03-03-2020] for itemtranster
        itemtransfer = false;
        rdoTabletransfer.setChecked(true);
        final RadioButton rdoItemtransfer = (RadioButton) dialog.findViewById(R.id.rdotransferitem);

        if (!dbhelper.Allow_item_transfer(dbhelper.getwaiterid())) {
            rdoTabletransfer.setVisibility(View.INVISIBLE);
            rdoItemtransfer.setVisibility(View.INVISIBLE);
        }

        final ViewAnimator viewanimator = (ViewAnimator) dialog
                .findViewById(R.id.vwAnimator);
        Animation Slide_In_Left = AnimationUtils.loadAnimation(this,
                android.R.anim.slide_in_left);
        Animation Slide_Out_Right = AnimationUtils.loadAnimation(this,
                android.R.anim.slide_out_right);
        viewanimator.setInAnimation(Slide_In_Left);
        viewanimator.setOutAnimation(Slide_Out_Right);


        btnNext.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (viewanimator.getCurrentView().equals(
                        dialog.findViewById(R.id.rl1))) {
                    viewanimator.showNext();
                    btnNext.setText("Back");
                } else if (viewanimator.getCurrentView().equals(
                        dialog.findViewById(R.id.rl2))) {
                    viewanimator.showPrevious();
                    btnNext.setText("NEXT");
                    btnNext.setVisibility(View.GONE);
                    btnChange.setVisibility(View.GONE);
                }
            }
        });

        btnChange.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // check for Internet status

                if (((TextView) findViewById(R.id.txtInvoiceno)).getText()
                        .equals("------")) {
                    findViewById(R.id.txtTableNo).setTag(
                            tablechangeinfo.getNewTableID());
                    ((TextView) findViewById(R.id.txtTableNo)).setText(dbhelper
                            .getTableNameByTableID(tablechangeinfo
                                    .getNewTableID()));
                    dialog.dismiss();
                    return;
                }

                if (GlobalClass.CheckConnectionForSubmit(ctx)) {
                    new DatabaseHelper(ctx)
                            .LoadSystemSetting(new DatabaseHelper(ctx)
                                    .getServiceURL());
                    Json_class jsonclass = new Json_class();
                    JSONArray jsonmessage = jsonclass.getJson(new DatabaseHelper(
                            ctx).getServiceURL()
                            + "/Data/switchtable?tranid="
                            + java.net.URLEncoder.encode(saleid)
                            + "&newtableid="
                            + java.net.URLEncoder.encode(tablechangeinfo
                            .getNewTableID().toString())
                            + "&newuserid="
                            + java.net.URLEncoder.encode(tablechangeinfo
                            .getNewUserID().toString()));

                    if (jsonmessage.length() > 0) {
                        try {
                            if (Integer.parseInt(jsonmessage.get(0).toString()) == 0) {
                                showAlertDialog(ctx, "Message",
                                        "Switch Successful!", false);

                            } else {
                                showAlertDialog(ctx, "Message",
                                        jsonmessage.get(2).toString(),
                                        false);
                            }
                        } catch (NumberFormatException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    } else {

                    }
                } else {
                    showAlertDialog(ctx, "Warning!",
                            "You don't have connection with Server!", false);
                }
            }
        });

        bindAreaButtonlist(dialog, true);

        rdoItemtransfer.setOnClickListener(new OnClickListener() {      //added by ZYP [03-03-2020]
            public void onClick(View view) {
                itemtransfer = true;
                bindAreaButtonlist(dialog, true);

            }
        });

        rdoTabletransfer.setOnClickListener(new OnClickListener() {     //added by ZYP [03-03-2020]
            public void onClick(View view) {
                itemtransfer = false;
                bindAreaButtonlist(dialog, true);
            }
        });

    }

    // added by SMH on 26/10/2015
    public void Note_dialog() {
        // if (saleid != "0") {
        String tranid = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.activity_send_note, null));
        final Dialog dialog = builder.create();

        WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.CENTER;

        dialog.show();

        Window window = dialog.getWindow();

        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

        final String dataurl = new DatabaseHelper(this).getServiceURL();

        Bundle b = this.getIntent().getExtras();
        ((TextView) findViewById(R.id.txtTableNo)).setTag(b
                .getString("TableID"));
        final String tableID = b.getString("TableID");
        List<Table_Name> tablename = dbhelper.getActiveTableByTableID(tableID);
        if (GlobalClass.CheckConnection(this) && tablename.size() > 0) {
            tranid = Integer.toString(tablename.get(0).getTranid());
            dbhelper.LoadNote(dataurl, tranid);
        }

        if (GlobalClass.tmpOffline == false) {
            if (GlobalClass.CheckConnectionForSubmit(ctx)) {
                //added by MPPA [16-06-2021]
                final LinearLayout sendnotelayout = (LinearLayout) dialog.findViewById(R.id.sendnotelayout);
                sendnotelayout.setVisibility(View.GONE);


                LinearLayout Note = (LinearLayout) dialog
                        .findViewById(R.id.notelayout);
                if (tranid != null) {
                    // sendnotelayout.setVisibility(View.VISIBLE);

                    List<Note> notelist = dbhelper.getnotelist(Integer
                            .parseInt(tranid));

                    for (int i = 0; i < notelist.size(); i++) {
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                GlobalClass.getDPsize(600, this),
                                GlobalClass.getDPsize(30, this));
                        params.setMargins(6, 6, 0, 4);
                        if (notelist.size() > 0) { //modified by MPPA on [24.06.2021]
                            sendnotelayout.setVisibility(View.VISIBLE);
                        }

                        Note.addView(CreateNotelist(notelist.get(i), dialog),
                                params);
                    }

                }
                // EditText text = (EditText) dialog
                // .findViewById(R.id.txtnote);
                // value = text.getText().toString();
                Button btnsendtonote;
                btnsendtonote = ((Button) dialog
                        .findViewById(R.id.btnsendtonote));
                btnsendtonote.setOnClickListener(new OnClickListener() {

                    public void onClick(View v) {
                        ;
                        chkprinter = "";
                        // TODO Auto-generated method stub
                        EditText text = (EditText) dialog
                                .findViewById(R.id.txtnote);
//						value = text.getText().toString();
                        value = text.getText().toString();
                        // added by Zyp for uni to zg node [06-01-2020]
                        if (!value.equals("")) {
                            Choose_Printer();
                        } else {
                            AlertDialog alertDialog = new AlertDialog.Builder(
                                    ctx).create();
                            alertDialog.setTitle("Message");
                            alertDialog
                                    .setMessage("Please, Enter your message!");
                            alertDialog.setIcon(0);
                            // final Context ctx = ctx;
                            // Setting OK Button
                            alertDialog.setButton2("OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(
                                                DialogInterface dialog,
                                                int which) {
                                            Note_dialog();
                                        }
                                    });

                            alertDialog.show();
                        }

                        // btnsendtonote_click(value,1);
                        dialog.dismiss();
                    }

                });

                //added by ZYP for note to monitors [06-01-2020]
                Button btnNotemonitor;
                btnNotemonitor = ((Button) dialog.findViewById(R.id.btnNotemonitor));
                if (!dbhelper.getusemonitorinterface().equals("Y"))
                    btnNotemonitor.setVisibility(View.GONE);
                btnNotemonitor.setOnClickListener(new OnClickListener() {

                    public void onClick(View v) {

                        //chkprinter = "";
                        // TODO Auto-generated method stub
                        EditText text = (EditText) dialog.findViewById(R.id.txtnote);
                        value = text.getText().toString();
                        // added by Zyp for uni to zg node [06-01-2020]

                        if (value.equals("")) {
                            AlertDialog alertDialog = new AlertDialog.Builder(
                                    ctx).create();
                            alertDialog.setTitle("Message");
                            alertDialog
                                    .setMessage("Please, Enter your message!");
                            alertDialog.setIcon(0);

                            alertDialog.setButton2("OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(
                                                DialogInterface dialog,
                                                int which) {
                                            Note_dialog();
                                        }
                                    });

                            alertDialog.show();
                        } else {
                            String jsonmsg = "";
                            jsonmsg = dbhelper.SendnodeToMonitor(value, tableID);

                            AlertDialog alertDialog = new AlertDialog.Builder(
                                    ctx).create();
                            alertDialog.setTitle("Message");
                            alertDialog.setMessage(jsonmsg.equals("successful") ? "Submit Successful!" : jsonmsg);
                            alertDialog.setIcon(0);

                            alertDialog.setButton2("OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(
                                                DialogInterface dialog,
                                                int which) {
                                            //Note_dialog();
                                        }
                                    });

                            alertDialog.show();
                        }

                        // btnsendtonote_click(value,1);
                        dialog.dismiss();
                    }
                });

            } else {
                showAlertDialog(this, "No Server Connection",
                        "You don't have connection with Server", false);
            }
        } else {
            showAlertDialog(this, "No Server Connection",
                    "You don't have connection with Server", false);

        }
        // }
    }

    public void ItemRemark(final TextView txtremark) {
        // if (saleid != "0") {
        String tranid = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.activity_add_remark, null));
        final Dialog dialog = builder.create();

        WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.CENTER;

        dialog.show();

        Window window = dialog.getWindow();
        window.setLayout(GlobalClass.getDPsize(750, this), GlobalClass.getDPsize(450, this));
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

        final String dataurl = new DatabaseHelper(this).getServiceURL();
        final String categoryid = dbhelper.getCategoryidbycode(codeforRemark);

        if (GlobalClass.tmpOffline == false) {
            if (GlobalClass.CheckConnectionForSubmit(ctx)) {

                Integer iColCount = 0;
                LinearLayout row = new LinearLayout(this);

                LinearLayout ItemRemark = (LinearLayout) dialog
                        .findViewById(R.id.ItemRemarklayout1);
                //	ItemRemark.removeAllViews();


                //((ScrollView)findViewById(R.id.ScrollView)).scrollTo(0, 0);
                dbhelper.LoadItemRemark(dataurl, categoryid);
                List<ItemRemark> ItemRemarklist = dbhelper.getItemRemarks();


                for (int i = 0; i < ItemRemarklist.size(); i++) {

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            GlobalClass.getDPsize(233, this),
                            GlobalClass.getDPsize(50, this));
                    params.setMargins(3, 5, 0, 5);
                    row.addView(CreateItemRemark(ItemRemarklist.get(i), dialog, txtremark), params);


                    iColCount++;

                    if (iColCount == 3) {
                        ItemRemark.addView(row);
                        row = new LinearLayout(this);
                        iColCount = 0;
                    }

                }
                ItemRemark.addView(row);
            }
        }


        Button btnAdd;
        btnAdd = ((Button) dialog
                .findViewById(R.id.btnAdd));
        btnAdd.setOnClickListener(new OnClickListener() {

                                      public void onClick(View v) {
                                          ;

                                          EditText text = (EditText) dialog
                                                  .findViewById(R.id.txtRemark);
                                          value = text.getText().toString();
                                          if (!value.equals("")) {
                                              btnAdd_click(value, txtremark, categoryid);

                                          } else {
                                              AlertDialog alertDialog = new AlertDialog.Builder(
                                                      ctx).create();
                                              alertDialog.setTitle("Message");
                                              alertDialog
                                                      .setMessage("Please, Enter your message!");
                                              alertDialog.setIcon(0);

                                              alertDialog.setButton2("OK",
                                                      new DialogInterface.OnClickListener() {
                                                          public void onClick(
                                                                  DialogInterface dialog,
                                                                  int which) {
                                                              ItemRemark(txtremark);
                                                          }
                                                      });

                                              alertDialog.show();
                                          }

                                          // btnsendtonote_click(value,1);
                                          dialog.dismiss();

                                      }

                                  }
        );


    }
//				else {
//				showAlertDialog(this, "No Server Connection",
//						"You don't have connection with Server", false);
//			}
//		}
//
//		else {
//			showAlertDialog(this, "No Server Connection",
//					"You don't have connection with Server", false);
//
//		}
//
//	}


    public void HeaderRemark(final TextView txtremark) {

        // if (saleid != "0") {
        String tranid = saleid;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.activity_add_headerremark, null));
        final Dialog dialog = builder.create();

        WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.CENTER;

        dialog.show();

        Window window = dialog.getWindow();
        window.setLayout(GlobalClass.getDPsize(700, this), GlobalClass.getDPsize(500, this));
        //window.setLayout(LayoutParams.WRAP_CONTENT, GlobalClass.getDPsize(700, this));
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

        final String dataurl = new DatabaseHelper(this).getServiceURL();
        final EditText txtHeaderRemark = (EditText) dialog.findViewById(R.id.txtHeaderRemark);


        if (GlobalClass.tmpOffline == false) {
            if (GlobalClass.CheckConnectionForSubmit(ctx)) {

                Integer iColCount = 0;
                LinearLayout row = new LinearLayout(this);
                String remark = dbhelper.LoadHeaderRemark(dataurl, tranid);
                txtHeaderRemark.setText(
                        remark.trim()
                );        //modified by ZYP for Unicode


                Selection.setSelection(
                        txtHeaderRemark.getText(),
                        txtHeaderRemark.getText().length());
            }
        }


        Button btnAdd;
        btnAdd = ((Button) dialog.findViewById(R.id.btnAdd));
        btnAdd.setOnClickListener(new View.OnClickListener() {
                                      public void onClick(View v) {
                                          EditText text = (EditText) dialog.findViewById(R.id.txtHeaderRemark);
                                          value = !LoginActivity.isUnicode ? Rabbit.zg2uni(text.getText().toString()) : text.getText().toString();
                                          if (!value.equals("")) {
                                              dbhelper.SaveHeaderRemark(value, saleid);
                                              dialog.dismiss();

                                          } else {
                                              AlertDialog alertDialog = new AlertDialog.Builder(ctx).create();
                                              alertDialog.setTitle("Message");
                                              alertDialog.setMessage("Please, Enter your message!");
                                              alertDialog.setIcon(0);

                                              alertDialog.setButton2("OK",
                                                      new DialogInterface.OnClickListener() {
                                                          public void onClick(
                                                                  DialogInterface dialog,
                                                                  int which) {
                                                              //ItemRemark(txtremark);
                                                          }
                                                      });

                                              alertDialog.show();
                                          }

                                          // btnsendtonote_click(value,1);
                                          //dialog.dismiss();

                                      }

                                  }
        );


    }


    public void Choose_Printer() {// Added by SMH on 04/03/2016
        // if (saleid != "0") {
        String tranid = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.activity_note_printer, null));
        final Dialog dialog = builder.create();

        WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.CENTER;

        dialog.show();

        Window window = dialog.getWindow();
        window.setLayout(GlobalClass.getDPsize(350, this),
                GlobalClass.getDPsize(510, this));
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

        final String dataurl = new DatabaseHelper(this).getServiceURL();
        final List<Printer> printList = dbhelper.getAllPrinterlist(dataurl);
        // int count=0;

        if (GlobalClass.tmpOffline == false) {
            if (GlobalClass.CheckConnectionForSubmit(ctx)) {

                final LinearLayout Printer = (LinearLayout) dialog
                        .findViewById(R.id.LinearPrinter);

                for (int i = 0; i < printList.size(); i++) {
                    final Printer P;
//                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                            GlobalClass.getDPsize(300, this),
//                            GlobalClass.getDPsize(30, this));
//                    // params.gravity = Gravity.CENTER;
//                    params.setMargins(4, 4, 4, 4);

                    LinearLayout printerlayout = new LinearLayout(this);

//                    LayoutParams contentParams = (LinearLayout.LayoutParams) printerlayout.getLayoutParams();
//                    contentParams.width = LayoutParams.MATCH_PARENT;
//                    contentParams.height = LayoutParams.WRAP_CONTENT;
                    printerlayout.setLayoutParams(new LayoutParams(
                            LayoutParams.MATCH_PARENT,
                            LayoutParams.WRAP_CONTENT
                    ));

                    final TextView printerName = new TextView(this);
                    printerName.setId(printList.get(i).getPrinterID());
                    printerName.setText(printList.get(i).getPrinterName());

                    printerName.setTypeface(font);

                    printerName.setTag(printList.get(i).getPrinterID());

                    printerName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                    printerName.setPadding(20, 5, 1, 5);
                    printerName.setBackgroundColor(Color.parseColor("#FFFFFF"));

                    printerName.setLayoutParams(new LayoutParams(345, LayoutParams.WRAP_CONTENT));

                    final CheckBox checkBox = new CheckBox(this);
                    // checkBox.setOnCheckedChangeListener(this);
                    checkBox.setId(printList.get(i).getPrinterID());
                    //checkBox.setText(printList.get(i).getPrinterName());

                    checkBox.setTypeface(font);

                    checkBox.setTag(printList.get(i).getPrinterID());

                    checkBox.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                    checkBox.setPadding(1, 5, 1, 5);
                    checkBox.setBackgroundColor(Color.parseColor("#FFFFFF"));

                    printerlayout.addView(printerName);
                    printerlayout.addView(checkBox);

                    Printer.addView(printerlayout);
                    Printer.setBackgroundColor(Color.parseColor(("#FFFFFF")));

                    checkBox.setOnClickListener(new OnClickListener() {
                        public void onClick(View v) {

                            if (checkBox.isChecked()) {

                                int chkprinterid = checkBox.getId();
                                chkprinter += chkprinterid;
                            }
                        }
                    });
                    // count = printList.size();
                }

                Button btnOkPrinter;
                btnOkPrinter = ((Button) dialog.findViewById(R.id.btnOkPrinter));
                btnOkPrinter.setOnClickListener(new OnClickListener() {

                    public void onClick(View v) {
                        ;
                        // EditText text = (EditText) dialog
                        // .findViewById(R.id.txtnote);
                        // String value = text.getText().toString();
                        // if (!value.equals("")) {
                        if (!chkprinter.equals("")) {
                            btnsendtonote_click(value, chkprinter);
                        } else {
                            AlertDialog alertDialog = new AlertDialog.Builder(
                                    ctx).create();
                            alertDialog.setTitle("Message");
                            alertDialog
                                    .setMessage("Please, Choose One or More Printer!");
                            alertDialog.setIcon(0);
                            // final Context ctx = ctx;
                            // Setting OK Button
                            alertDialog.setButton2("OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(
                                                DialogInterface dialog,
                                                int which) {
                                            Choose_Printer();
                                        }
                                    });

                            alertDialog.show();
                        }

                        // } else {
                        // AlertDialog alertDialog = new AlertDialog.Builder(
                        // ctx).create();
                        // alertDialog.setTitle("Message");
                        // alertDialog.setMessage("Please, Enter your message!");
                        // alertDialog.setIcon(0);
                        // //final Context ctx = ctx;
                        // // Setting OK Button
                        // alertDialog.setButton2("OK",
                        // new DialogInterface.OnClickListener() {
                        // public void onClick(
                        // DialogInterface dialog,
                        // int which) {
                        // Note_dialog();
                        // }
                        // });
                        //
                        // alertDialog.show();
                        //
                        //
                        // }
                        dialog.dismiss();

                    }

                });

                Button btnBack;
                btnBack = ((Button) dialog.findViewById(R.id.btnBack));
                btnBack.setOnClickListener(new OnClickListener() {

                    public void onClick(View v) {
                        dialog.dismiss();
                        Note_dialog();
                    }
                });

            } else {
                // showAlertDialog(this, "No Server Connection",
                // "You don't have connection with Server", false);
            }
        } else {
            // showAlertDialog(this, "No Server Connection",
            // "You don't have connection with Server", false);

        }
        // }
    }

    private TextView Createprinterlist(final Printer P, final Dialog dialog) {

        CheckBox checkBox = new CheckBox(this);

        // checkBox.setOnCheckedChangeListener(this);
        checkBox.setId(P.getPrinterID());
        checkBox.setText(P.getPrinterName());

        checkBox.setTypeface(font);

        checkBox.setTag(P.getPrinterID());

        checkBox.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);

        checkBox.setBackgroundColor(Color.parseColor("#FFFFFF"));

        return checkBox;
    }

    // added by SMH on 26/10/2015
    public String[] btnsendtonote_click(String textvalue, String Printerid) {
        String[] Msg = {"", ""};
        if (GlobalClass.CheckConnectionForSubmit(ctx)) {

            Integer id = Integer.parseInt(saleid);
            if (id != 0) {
                Note NoteObj = new Note();

                String UserID = ((TextView) findViewById(R.id.txtDocID))
                        .getTag().toString();

                NoteObj.setTranid(Integer.parseInt(saleid));
                NoteObj.setsr(0);
                NoteObj.setuserid(Integer.parseInt(dbhelper.getwaiterid()));

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
                Date now = new Date();
                String Date = formatter.format(now);
                NoteObj.setDate(Date);

                NoteObj.setNotes(textvalue);

                // int tranid = Integer.parseInt(jsonmessage.get(2).toString());
                JSONArray notejsonarray = new JSONArray();
                JSONObject jsonobj = new JSONObject();
                try {
                    // sale_Det
                    jsonobj.put("tranid", NoteObj.getTranid());
                    jsonobj.put("sr", NoteObj.getsr());
                    jsonobj.put("userid", NoteObj.getuserid());
                    jsonobj.put("Date", NoteObj.getDate());
                    jsonobj.put("Notes", NoteObj.getNotes());

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    // e.printStackTrace();
                }
                notejsonarray.put(jsonobj);
                Json_class jsonclass = new Json_class();

                JSONArray jsonmessage = jsonclass.POST(
                        new DatabaseHelper(this).getServiceURL()
                                + "/Data/InsertNote?Printerid=" + (chkprinter),
                        notejsonarray);

                if (jsonmessage.length() > 0) {
                    try {

                        if (Integer.parseInt(jsonmessage.get(0).toString()) == 0) {

                            // Intent data = new Intent(ctx,SendNote.class);

                            AlertDialog alertDialog = new AlertDialog.Builder(
                                    this).create();
                            alertDialog.setTitle("Message");
                            alertDialog.setMessage("Submit Successful!");
                            alertDialog.setIcon(0);
                            final Context ctx = this;
                            // Setting OK Button
                            alertDialog.setButton2("OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(
                                                DialogInterface dialog,
                                                int which) {

                                        }
                                    });

                            alertDialog.show();

                        } else {

                            AlertDialog alertDialog = new AlertDialog.Builder(
                                    this).create();
                            alertDialog.setTitle("Message");
                            alertDialog.setMessage(jsonmessage.getString(2));
                            alertDialog.setIcon(0);
                            final Context ctx = this;
                            // Setting OK Button
                            alertDialog.setButton2("OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(
                                                DialogInterface dialog,
                                                int which) {

                                        }
                                    });

                            alertDialog.show();

                        }
                    } catch (NumberFormatException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                // return Msg;
                return Msg;

            } else {
                AlertDialog alertDialog = new AlertDialog.Builder(this)
                        .create();
                alertDialog.setTitle("Message");
                alertDialog.setMessage("Submit Unsuccessful!");
                alertDialog.setIcon(0);
                final Context ctx = this;
                // Setting OK Button
                alertDialog.setButton2("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {

                            }
                        });

                alertDialog.show();
            }
        } else
            showAlertDialog(
                    this,
                    "No Server Connection",
                    "You don't have connection with Server and can't get updated information!",
                    false);

        return Msg;
    }

    public Boolean btnAddCustomer_click(final String CompanyName, final TextView txtCompanyName,
                                        final String CustomerCode, final TextView txtCustomerCode,
                                        final String CustomerName, final TextView txtCustomerName,
                                        final String CustomerCredit, final CheckBox chkCustomerCredit) {
        Boolean successflag = false;
        if (GlobalClass.CheckConnectionForSubmit(ctx)) {


            CustomerObj NewCustomerObj = new CustomerObj();

            NewCustomerObj.setcustomerid(0);
            NewCustomerObj.setcompanyname(CompanyName);
            NewCustomerObj.setcustomercode(CustomerCode);
            NewCustomerObj.setcustomername(CustomerName);
            NewCustomerObj.setcustomercredit(CustomerCredit);

            JSONArray notejsonarray = new JSONArray();
            JSONObject jsonobj = new JSONObject();
            try {
                // sale_Det
                jsonobj.put("customerid", NewCustomerObj.getcustomerid());
//					jsonobj.put("company_name", NewCustomerObj.getcompanyname());
//					jsonobj.put("customercode", NewCustomerObj.getcustomercode());
//					jsonobj.put("name", NewCustomerObj.getcustomername());
                jsonobj.put("company_name",
                        NewCustomerObj.getcompanyname());
                jsonobj.put("customercode",
                        NewCustomerObj.getcustomercode());
                jsonobj.put("name",
                        NewCustomerObj.getcustomername());
                jsonobj.put("tranid", saleid);
                jsonobj.put("userid", dbhelper.getwaiterid());
                jsonobj.put("customer_crdit", NewCustomerObj.getcustomercredit());

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                // e.printStackTrace();
            }
            notejsonarray.put(jsonobj);
            Json_class jsonclass = new Json_class();

//            JSONArray jsonmessage = jsonclass.POST(
//                    new DatabaseHelper(this).getServiceURL()
//                            + "/Data/InsertCustomer",
//                    notejsonarray);
//            Added by KLM to Save with Zawgyi to Database 23022022
            JSONArray jsonmessage = null;
            try {
                jsonmessage = jsonclass.POST(
                        new DatabaseHelper(this).getServiceURL()
                                + "/Data/InsertCustomer",
                        !LoginActivity.isUnicode ? new JSONArray(Rabbit.uni2zg(notejsonarray.toString())) : notejsonarray);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (jsonmessage.length() > 0) {
                try {

                    if (Integer.parseInt(jsonmessage.get(0).toString()) == 0) {

                        // Intent data = new Intent(ctx,SendNote.class);

                        AlertDialog alertDialog = new AlertDialog.Builder(
                                this).create();
                        alertDialog.setTitle("Message");
                        alertDialog.setMessage("Submit Successful!");
                        alertDialog.setIcon(0);
                        final Context ctx = this;
                        // Setting OK Button
                        alertDialog.setButton2("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(
                                            DialogInterface dialog,
                                            int which) {
                                        //String remarkdata =txtremark.getText().toString();

                                    }
                                });

                        alertDialog.show();
                        successflag = true;

                    } else {

                        AlertDialog alertDialog = new AlertDialog.Builder(
                                this).create();
                        alertDialog.setTitle("Message");
                        alertDialog.setMessage(jsonmessage.getString(2));
                        alertDialog.setIcon(0);
                        final Context ctx = this;
                        // Setting OK Button
                        alertDialog.setButton2("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(
                                            DialogInterface dialog,
                                            int which) {


                                    }
                                });

                        alertDialog.show();
                        successflag = false;
                    }
                } catch (NumberFormatException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            return successflag;

        } else
            showAlertDialog(
                    this,
                    "No Server Connection",
                    "You don't have connection with Server and can't get updated information!",
                    false);

        return successflag;
    }

    public String[] btnAdd_click(final String textvalue, final TextView txtremark, String categoryid) {
        String[] Msg = {"", ""};
        if (GlobalClass.CheckConnectionForSubmit(ctx)) {

            ItemRemark ItemRemarkObj = new ItemRemark();

            ItemRemarkObj.setID(0);
            ItemRemarkObj.setItemRemark(textvalue);
            JSONArray notejsonarray = new JSONArray();
            JSONObject jsonobj = new JSONObject();
            try {
                // sale_Det
                jsonobj.put("ID", ItemRemarkObj.getID());
                Toast.makeText(OrderTaking.this, LoginActivity.isUnicode + " Unicode", Toast.LENGTH_LONG).show();
                //Added by KLM to Save with Zawgyi to Database 23022022
                jsonobj.put("ItemRemark", !LoginActivity.isUnicode ? Rabbit.uni2zg(ItemRemarkObj.getItemRemark()) : ItemRemarkObj.getItemRemark());


            } catch (JSONException e) {
                // TODO Auto-generated catch block
                // e.printStackTrace();
            }
            notejsonarray.put(jsonobj);
            Json_class jsonclass = new Json_class();

            JSONArray jsonmessage = jsonclass.POST(
                    new DatabaseHelper(this).getServiceURL()
                            + "/Data/InsertItemRemark?categoryid=" + categoryid,
                    notejsonarray);

            if (jsonmessage.length() > 0) {
                try {

                    if (Integer.parseInt(jsonmessage.get(0).toString()) == 0) {

                        // Intent data = new Intent(ctx,SendNote.class);

                        AlertDialog alertDialog = new AlertDialog.Builder(
                                this).create();
                        alertDialog.setTitle("Message");
                        alertDialog.setMessage("Submit Successful!");
                        alertDialog.setIcon(0);
                        final Context ctx = this;
                        // Setting OK Button
                        alertDialog.setButton2("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(
                                            DialogInterface dialog,
                                            int which) {
                                        String remarkdata = txtremark.getText().toString();
                                        txtremark.setText(remarkdata + value);

                                    }
                                });

                        alertDialog.show();

                    } else {

                        AlertDialog alertDialog = new AlertDialog.Builder(
                                this).create();
                        alertDialog.setTitle("Message");
                        alertDialog.setMessage(jsonmessage.getString(2));
                        alertDialog.setIcon(0);
                        final Context ctx = this;
                        // Setting OK Button
                        alertDialog.setButton2("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(
                                            DialogInterface dialog,
                                            int which) {


                                    }
                                });

                        alertDialog.show();

                    }
                } catch (NumberFormatException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            return Msg;

        } else
            showAlertDialog(
                    this,
                    "No Server Connection",
                    "You don't have connection with Server and can't get updated information!",
                    false);

        return Msg;
    }

    private void printStatus_dialog() {     //added by ZYP for printer Status [15-01-2020]
        final AlertDialog.Builder statusDialog = new AlertDialog.Builder(new ContextThemeWrapper(this,
                R.style.AppTheme));
        statusDialog.setTitle("Printer Status");


    }

    private void printerStatus_dialog() {
        if (!GlobalClass.CheckConnection(ctx)) {
            ConnectionStatus();
            showAlertDialog(ctx, "Warning!", "No connection with Server.", false);
            return;
        }
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = this.getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.activity_printer_status, null));

        final Dialog dialog = builder.create();

        WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.CENTER;
        wmlp.y = GlobalClass.getDPsize(100, this);

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(GlobalClass.getDPsize(400, this),
                GlobalClass.getDPsize(500, this));

        dialog.show();

        bindStatuslist(dialog);

        Button btnReload = (Button) dialog.findViewById(R.id.btnstatusReload);
        btnReload.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                bindStatuslist(dialog);
            }
        });

        Button btnClose = (Button) dialog.findViewById(R.id.btnstatusClose);
        btnClose.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    public void bindStatuslist(Dialog dialog) {
        final String dataurl = new DatabaseHelper(this).getServiceURL();
        final List<Printer> printList = dbhelper.getAllPrinterlist(dataurl);

        ListView printerListView = (ListView) dialog.findViewById(R.id.printlist);

        PrinterStatusListViewAdapter oddadapter = new PrinterStatusListViewAdapter(
                this, R.layout.printer_status_list, printList);
        printerListView.setAdapter(oddadapter);
    }

    //region switch table
    private void mergetable_dialog() //Added by ArkarMoe on [20/12/2016]
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.activity_switchtable, null));
        final Dialog dialog = builder.create();

        WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.CENTER;

        dialog.show();
        Window window = dialog.getWindow();
        //int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300, getResources().getDisplayMetrics());
        window.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

        TextView txttitle = (TextView) dialog.findViewById(R.id.txttitle);
        txttitle.setText("   Merge Table");

        ((Button) dialog.findViewById(R.id.btnNext)).setVisibility(View.GONE);

        ((Button) dialog.findViewById(R.id.btnSave)).setVisibility(View.GONE);

        ((RadioButton) dialog.findViewById(R.id.rdotransfertable)).setVisibility(View.INVISIBLE);

        ((RadioButton) dialog.findViewById(R.id.rdotransferitem)).setVisibility(View.INVISIBLE);

        bindAreaButtonlist(dialog, false);
    }

    public void bindAreaButtonlist(Dialog dialog, Boolean fortransfer) //Edited by ArkarMoe on [20/12/2016]
    {
        LinearLayout Area = (LinearLayout) dialog.findViewById(R.id.arealayout);

        Area.removeAllViews();
        //Bind Area List
        List<Area> AreaList = dbhelper.getArealist();
        for (int i = 0; i < AreaList.size(); i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(GlobalClass.getDPsize(185, this), GlobalClass.getDPsize(60, this));
            params.setMargins(6, 6, 0, 4);

            Area.addView(CreateAreaButton(AreaList.get(i), dialog, fortransfer), params);
        }

        //Set the Area 1 pressed.
        if (AreaList.size() > 0) {
            Integer iDefaultArea = 0;
            iDefaultArea = AreaList.get(0).getArea_ID();

            if (fortransfer == true)

                BindTableList(iDefaultArea, dialog);
            else
                BindTableListForMerge(iDefaultArea, dialog);
            if (Area.getChildAt(0) != null) {
                if (Area.getChildAt(0) instanceof Button) {
                    ((TextView) dialog.findViewById(R.id.txtareaid)).setText(((Button) Area.getChildAt(0)).getTag().toString());
                    ((Button) Area.getChildAt(0)).setBackgroundColor(Color.parseColor("#a100a9"));
                }
            }
        }
    }

    private Button CreateAreaButton(final Area A, final Dialog dialog, final Boolean fortransfer) //Edited by ArkarMoe on [20/12/2016]
    {
        Button p1 = new Button(this);
        p1.setTypeface(font);

        p1.setText(A.getArea_Name());
        p1.setTag("Area-" + A.getArea_ID());
        p1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        p1.setTextColor(Color.WHITE);
        p1.setBackgroundColor(Color.parseColor("#0a59c1"));
        p1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                String Tag = ((TextView) dialog.findViewById(R.id.txtareaid)).getText().toString();
                LinearLayout Arealayout = (LinearLayout) dialog.findViewById(R.id.arealayout);
                Button selectedbutton = (Button) Arealayout.findViewWithTag(Tag);
                selectedbutton.setBackgroundColor(Color.parseColor("#0a59c1"));

                ((TextView) dialog.findViewById(R.id.txtareaid)).setText(((Button) v).getTag().toString());
                ((Button) v).setBackgroundColor(Color.parseColor("#a100a9"));
                if (fortransfer == true)
                    BindTableList(A.getArea_ID(), dialog);
                else
                    BindTableListForMerge(A.getArea_ID(), dialog);
            }
        });
        return p1;
    }

    private void BindTableListForMerge(Integer AreaId, Dialog dialog) //Added by ArkarMoe on [20/12/2016]
    {
        boolean isInternetPresent = GlobalClass.CheckConnection(this);
        TableLayout Table = (TableLayout) dialog.findViewById(R.id.TableLayout);
        HorizontalScrollView hArea = (HorizontalScrollView) dialog.findViewById(R.id.harea);
        // check for Internet status
        if (isInternetPresent) {
            dbhelper.LoadActivetable(new DatabaseHelper(this).getServiceURL(), Integer.toString(AreaId));
        } else {
            // Internet connection is not present
            // Ask user to connect to Internet
            showAlertDialog(this, "No Server Connection",
                    "You don't have connection with Server and can't get updated information!", false);
        }
        Integer iColCount = 0;
        Integer iRowCount = 0;
        String rowstyle = dbhelper.getTableRowStyle();
        if (rowstyle.contains("Four")) {
            iRowCount = 4;
        } else if (rowstyle.contains("Three")) {
            iRowCount = 3;
        } else {
            iRowCount = 2;
        }
        Integer TotalColCount = 0;
        TableRow row = new TableRow(this);
        Table.removeAllViews();
        hArea.scrollTo(0, 0);
        List<Table_Name> TableList = dbhelper.getmergeTableListByArea(AreaId, Integer.parseInt(saleid));

        //iRowCount = 3;
        TotalColCount = TableList.size() / iRowCount;
        if (TableList.size() % iRowCount != 0) {
            TotalColCount = TotalColCount + 1;
        }

        for (int i = 0; i < TableList.size(); i++) {

            Button b = CreateTableButtonForMerge(TableList.get(i), dialog);
            TableRow.LayoutParams params = new TableRow.LayoutParams(GlobalClass.getDPsize(90, this), GlobalClass.getDPsize(85, this));
            params.setMargins(6, 4, 0, 4);
            row.addView(b, params);
            iColCount++;

            if (iColCount == TotalColCount) {
                Table.addView(row);
                row = new TableRow(this);
                iColCount = 0;
            }
        }

        Table.addView(row);
    }

    private TextView CreateNotelist(final Note N, final Dialog dialog) {
        TextView p1 = new TextView(this);
        p1.setTypeface(font);

        PosUser posuser = dbhelper.getPosUserByUserID(N.getuserid());//added by MPPA on 06-10-2021
        //p1.setText(N.getsr() + "                    " + N.getuserid()
        //       + "                " + N.getNotes());
        p1.setText(N.getsr() + "                    " + posuser.getName()
                + "                " + N.getNotes());
        p1.setTag(N.getTranid());


        p1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        // p1.setTextColor(Color.WHITE);
        p1.setBackgroundColor(Color.parseColor("#FFFFFF"));

        return p1;
    }

    private TextView CreateItemRemark(final ItemRemark IR, final Dialog dialog, final TextView txtremark) {
        TextView p1 = new TextView(this);
        p1.setTypeface(font);

        p1.setText(IR.getItemRemark());
        p1.setTag(IR.getID());

        p1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        p1.setBackgroundResource(R.drawable.rounded_button);
        p1.setTextColor(Color.parseColor("#FFFFFF"));
        p1.setGravity(Gravity.CENTER);
        p1.setHeight(45);
        p1.setWidth(80);

        p1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                String remarkdata = txtremark.getText().toString();
                //txtremark.setText(remarkdata + IR.getItemRemark());

                //modified by EKK on 07-01-2020 (EKK0007)
                if (remarkdata.equals("")) {
                    txtremark.setText(remarkdata + IR.getItemRemark());
                } else {
                    txtremark.setText(remarkdata + " ," + IR.getItemRemark());
                }
                dialog.dismiss();
            }
        });


        return p1;
    }

    private TextView CreateSplitedVouchers(final SplitedVouchers SV, final Dialog dialog, final List tablename) { //Added by Arkar Moe on [2017-06-11]
        TextView p1 = new TextView(this);
        p1.setTypeface(font);

        //p1.setText( "["+ SV.get_docid() + "]-["+ SV.get_net_amount() + "]");
        p1.setText("[" + SV.get_docid() + "]-[" + String.format("%.2f", Double.parseDouble(SV.get_net_amount())) + "]");
        p1.setTag(SV.get_tranid());

        p1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        p1.setBackgroundResource(R.drawable.rounded_button);
        p1.setTextColor(Color.parseColor("#FFFFFF"));
        p1.setHeight(45);
        p1.setWidth(80);

        p1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                splited_saleid = v.getTag().toString();
                //GetDataByTranid(tablename,splited_saleid);
                dialog.dismiss();
            }
        });


        return p1;
    }

    //Added by ArkarMoe on [14/12/2016]
    private TextView CreateRooms(final Rooms RS, final Dialog dialog, final TextView txtRoom) {

        TextView p1 = new TextView(this);
        p1.setTypeface(font);

        p1.setText(RS.getRoom_Code());
        p1.setTag(RS.getRoom_ID());

        p1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        p1.setBackgroundResource(R.drawable.rounded_button);
        p1.setTextColor(Color.parseColor("#FFFFFF"));
        p1.setHeight(45);
        p1.setWidth(80);

        p1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                String roomcode = RS.getRoom_Code().toString();
                String roomid = RS.getRoom_ID().toString();
                String ref_tranid = RS.getRef_TranID().toString();

                dbhelper.UpdateRoomInformation(dbhelper.getServiceURL(), saleid, ref_tranid, roomid, roomcode, "1");

                txtRoom.setText(RS.getRoom_Code());

                dialog.dismiss();
            }
        });


        return p1;
    }
    //

    private void BindTableList(Integer AreaId, Dialog dialog) {
        boolean isInternetPresent = GlobalClass.CheckConnection(this);
        TableLayout Table = (TableLayout) dialog.findViewById(R.id.TableLayout);
        HorizontalScrollView hArea = (HorizontalScrollView) dialog
                .findViewById(R.id.harea);
        // check for Internet status
        if (isInternetPresent) {
            dbhelper.LoadActivetable(new DatabaseHelper(this).getServiceURL(),
                    Integer.toString(AreaId));
        } else {
            // Internet connection is not present
            // Ask user to connect to Internet
            showAlertDialog(
                    this,
                    "No Server Connection",
                    "You don't have connection with Server and can't get updated information!",
                    false);
        }
        Integer iColCount = 0;
        Integer iRowCount = 0;
        String rowstyle = dbhelper.getTableRowStyle();
        if (rowstyle.contains("Four")) {
            iRowCount = 4;
        } else if (rowstyle.contains("Three")) {
            iRowCount = 3;
        } else {
            iRowCount = 2;
        }
        Integer TotalColCount = 0;
        TableRow row = new TableRow(this);
        Table.removeAllViews();
        hArea.scrollTo(0, 0);
        List<Table_Name> TableList = null;

        if (itemtransfer)
            TableList = dbhelper.getItemTransferableTable(AreaId);          //added by ZYP [03-03-2020]
        else
            TableList = dbhelper.getTransferableTableListByArea(AreaId);

        //iRowCount = 3;
        TotalColCount = TableList.size() / iRowCount;
        if (TableList.size() % iRowCount != 0) {
            TotalColCount = TotalColCount + 1;
        }

        for (int i = 0; i < TableList.size(); i++) {

            Button b = CreateTableButton(TableList.get(i), dialog);
            TableRow.LayoutParams params = new TableRow.LayoutParams(
                    GlobalClass.getDPsize(90, this), GlobalClass.getDPsize(85,
                    this));
            params.setMargins(6, 4, 0, 4);
            row.addView(b, params);
            iColCount++;

            if (iColCount == TotalColCount) {
                Table.addView(row);
                row = new TableRow(this);
                iColCount = 0;
            }
        }

        Table.addView(row);
    }

    private Button CreateTableButton(Table_Name A, final Dialog dialog) {
        Button p1 = new Button(this);
        p1.setTypeface(font);
        p1.setText(A.getTable_Name());
        p1.setTag("Table-" + A.getTable_Name_ID());
        p1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        p1.setTextColor(Color.WHITE);
        p1.setSingleLine(true);
        final List<Table_Name> tableobjlist = dbhelper.getActiveTableByTableID(Integer.toString(A.getTable_Name_ID()));

        if (tableobjlist.size() > 0) {
            // p1.setBackgroundResource(R.drawable.selected_button);
            if (dbhelper.check_isreserved(A.getTable_Name_ID())) {
                p1.setBackgroundColor(Color.parseColor("#009f00")); // green
            } else {
                // p1.setBackgroundColor(Color.parseColor("#800000")); // red
                if (dbhelper.getwaiterid().equals(
                        Integer.toString(tableobjlist.get(0).getuserid())))
                    p1.setBackgroundResource(R.drawable.mycustomertable);
                else
                    p1.setBackgroundResource(R.drawable.customertable);
            }

        } else
            p1.setBackgroundColor(Color.parseColor("#008ad2")); // blue

        // p1.setTextAppearance(this, R.style.tablebutton_text);
        p1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (itemtransfer) {     //added by ZYP [03-03-2020]
                    totranid = tableobjlist.get(0).getTranid();
                    SplitedVoucher(tableID, tableobjlist);
                } else {
                    int AreaID = Integer.parseInt(v.getTag().toString().split("-")[1]);

                    tablechangeinfo.setNewTableID(AreaID);
                    ViewAnimator viewanimator = (ViewAnimator) dialog.findViewById(R.id.vwAnimator);
                    viewanimator.showNext();
                    Button changeBtn = (Button) dialog.findViewById(R.id.btnSave);
                    changeBtn.setVisibility(View.VISIBLE);
                    Button nextBtn = (Button) dialog.findViewById(R.id.btnNext);
                    nextBtn.setVisibility(View.VISIBLE);
                    nextBtn.setText("Back");
                }

            }
        });

        return p1;
    }

    private Button CreateTableButtonForMerge(Table_Name A, final Dialog dialog) //Added by ArkarMoe on [20/12/2016]
    {
        Button p1 = new Button(this);
        p1.setTypeface(font);
        p1.setText(A.getTable_Name());
        p1.setTag("Table-" + A.getTable_Name_ID());
        p1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        p1.setTextColor(Color.WHITE);
        p1.setSingleLine(true);
        List<Table_Name> tableobjlist = dbhelper.getActiveTableByTableID(Integer.toString(A.getTable_Name_ID()));

        if (tableobjlist.size() > 0) {
            //p1.setBackgroundResource(R.drawable.selected_button);
            if (dbhelper.check_isreserved(A.getTable_Name_ID())) {
                p1.setBackgroundColor(Color.parseColor("#009f00")); //green
            } else {
                //p1.setBackgroundColor(Color.parseColor("#800000")); // red
                if (dbhelper.getwaiterid().equals(Integer.toString(tableobjlist.get(0).getuserid())))
                    p1.setBackgroundResource(R.drawable.mycustomertable);
                else
                    p1.setBackgroundResource(R.drawable.customertable);
            }

        } else
            p1.setBackgroundColor(Color.parseColor("#008ad2")); // blue


        //p1.setTextAppearance(this, R.style.tablebutton_text);
        p1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(ctx).create();
                alertDialog.setTitle("Confirm");
                alertDialog.setMessage("Are you sure you want to merge with '" + ((Button) v).getText() + "'");
                alertDialog.setIcon(0);
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.setCancelable(false);
                final String tableid = (((Button) v).getTag().toString()).replace("Table-", "");
                // Setting OK Button
                final String tranid = saleid;
                alertDialog.setButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //pyaephyohein
                        TableMergeObject mergeobj = new TableMergeObject();
                        mergeobj.settranid(Integer.parseInt(tranid));
                        mergeobj.settotranid(dbhelper.getActiveTableByTableID(tableid).get(0).getTranid());
                        mergeobj.settotableid(Integer.parseInt(tableid));
                        mergeobj.setuserid(Integer.parseInt(dbhelper.getwaiterid()));

                        if (GlobalClass.CheckConnectionForSubmit(ctx)) {
                            Json_class jsonclass = new Json_class();
                            try {
                                Submit_Data(0, 0);
                            } catch (NumberFormatException e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            } catch (JSONException e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            }
                            JSONArray jsonmessage = jsonclass.getJson(new DatabaseHelper(ctx).getServiceURL() + "/Data/mergetable?tranid=" + java.net.URLEncoder.encode(saleid) + "&totranid=" + java.net.URLEncoder.encode(mergeobj.gettotranid().toString()) + "&totableid=" + java.net.URLEncoder.encode(mergeobj.gettotableid().toString()) + "&userid=" + java.net.URLEncoder.encode(mergeobj.getuserid().toString()));

                            if (jsonmessage.length() > 0) {
                                try {
                                    if (Integer.parseInt(jsonmessage.get(0).toString()) == 0) {
                                        showAlertDialog(ctx, "Message", "Merge Successful!", false);

                                    } else {
                                        showAlertDialog(ctx, "Message", "Please Submit Before Merge Table!", false);
                                    }
                                } catch (NumberFormatException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                } catch (JSONException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            } else {


                            }
                        } else {
                            showAlertDialog(ctx, "Warning!", "You don't have connection with Server!", false);
                        }


                    }
                });
                alertDialog.setButton2("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                // Showing Alert Message
                alertDialog.show();
            }
        });

        return p1;
    }

    public void Select_User_dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.activity_customer, null));
        final Dialog dialog = builder.create();

        WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.CENTER;
        wmlp.y = GlobalClass.getDPsize(100, this);

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(GlobalClass.getDPsize(420, this),
                GlobalClass.getDPsize(500, this));

        dialog.show();

        List<PosUser> userlist = dbhelper.getPosUserlist();

        TextView txtTitle = (TextView) dialog
                .findViewById(R.id.txtSelectCustomer);
        txtTitle.setText("Select User For This Table");

        AutoCompleteTextView txtCustomer = (AutoCompleteTextView) dialog
                .findViewById(R.id.txtautocompletecustomer);
        txtCustomer.setVisibility(4); // Invisible

        ListView customerlistview = (ListView) dialog
                .findViewById(R.id.custlist);

        final List<RowItem> rowItems = new ArrayList<RowItem>();
        for (int j = 0; j < userlist.size(); j++) {
            RowItem item = new RowItem(j, userlist.get(j).getUserId(), "",
                    userlist.get(j).getShort() + " - "
                            + userlist.get(j).getName(), "");
            rowItems.add(item);
        }

        CustomerCustomListViewAdapter oddadapter = new CustomerCustomListViewAdapter(
                this, R.layout.customerlist_item, rowItems);
        customerlistview.setAdapter(oddadapter);

        customerlistview
                .setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        tablechangeinfo.setNewUserID(rowItems.get(position)
                                .getheaderid());
                        dialog.dismiss();
                    }
                });
    }

    // endregion

    private void salesmen_dialog()
    // throws NumberFormatException, JSONException
    {
        int itemcount = checkItemCancel();//added WHM [2020-04-06]
        if (itemcount >= 1) {

            if (GlobalClass.use_foodtruck) {

                boolean needsubmit = false;
                LinearLayout itemlistlayout = (LinearLayout) findViewById(R.id.itemlistlayout);

                for (int i = 0; i < itemlistlayout.getChildCount(); i++) {
                    LinearLayout linearlayout = (LinearLayout) itemlistlayout.getChildAt(i);
                    LinearLayout mainrow = (LinearLayout) linearlayout.getChildAt(0);// Get main item row
                    TextView txtsrrow = (TextView) mainrow.getChildAt(0);
                    if (!txtsrrow.getTag().toString().contains("P")) {
                        needsubmit = true;
                    }
                }

                if (initialitemcount < itemlistlayout.getChildCount() || needsubmit == true) {
                    final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                    dialog.setMessage("Are you sure want to confirm!");
                    dialog.setTitle("Warning!");

                    dialog.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Submit_withProgressbar(0, 0);
                                    // dismiss the dialog
                                }
                            });
                    dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    dialog.create().show();
                } else {
                    butconfirm_click();
                }


            } else {
                if (dbhelper.getSalesmen_commission().equals("Y")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    // Get the layout inflater
                    LayoutInflater inflater = this.getLayoutInflater();

                    builder.setView(inflater.inflate(R.layout.activity_salemens_select,
                            null));
                    final Dialog salesmendialog = builder.create();

                    WindowManager.LayoutParams wmlp = salesmendialog.getWindow()
                            .getAttributes();
                    wmlp.gravity = Gravity.CENTER;

                    salesmendialog.show();
                    Window window = salesmendialog.getWindow();
                    // int width = (int)
                    // TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300,
                    // getResources().getDisplayMetrics());
                    window.setLayout(GlobalClass.getDPsize(400, ctx),
                            LayoutParams.MATCH_PARENT);
                    salesmendialog.setCanceledOnTouchOutside(true);
                    salesmendialog.show();

                    final List<Salesmen> salesmenlist = dbhelper
                            .getsalemen_ListByUserID(dbhelper.getwaiterid());

                    ListView waiterlist = (ListView) salesmendialog
                            .findViewById(R.id.salesmenlist);

                    final List<RowItem> rowItems = new ArrayList<RowItem>();
                    for (int j = 0; j < salesmenlist.size(); j++) {
                        RowItem item = new RowItem(j, salesmenlist.get(j)
                                .getsalesmen_id(), "", salesmenlist.get(j)
                                .getsalesmen_name(), "");
                        rowItems.add(item);
                    }

                    CustomerCustomListViewAdapter oddadapter = new CustomerCustomListViewAdapter(
                            this, R.layout.customerlist_item, rowItems);
                    waiterlist.setAdapter(oddadapter);

                    waiterlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> parent,
                                                View view, int position, long id) {

                            final Salesmen salesmen = (Salesmen) salesmenlist
                                    .get(position);
                            AlertDialog alertDialog = new AlertDialog.Builder(
                                    ctx).create();
                            alertDialog.setTitle("Confirm");
                            alertDialog
                                    .setMessage("Are you sure that the saleman is \n"
                                            + salesmen.getsalesmen_name() + " ?");
                            alertDialog.setIcon(0);
                            alertDialog.setCanceledOnTouchOutside(false);
                            alertDialog.setCancelable(false);
                            // Setting OK Button
                            alertDialog.setButton("Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(
                                                DialogInterface dialog,
                                                int which) {
                                            try {
                                                if (dbhelper.getmultiplelanguageprint().equals("Y")) {
                                                    itemdescription_dialog(salesmen.getsalesmen_id());
                                                } else {
                                                    Submit_withProgressbar(salesmen.getsalesmen_id(), 0);
                                                }

                                            } catch (Exception e) {
                                                // TODO Auto-generated catch
                                                // block
                                                e.printStackTrace();
                                            }
                                        }
                                    });

                            alertDialog.setButton2("No",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(
                                                DialogInterface dialog,
                                                int which) {
                                            try {

                                                salesmendialog.dismiss();
                                                salesmen_dialog();

                                            } catch (Exception e) {
                                                // TODO Auto-generated catch
                                                // block
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                            // Showing Alert Message
                            alertDialog.show();
                        }
                    });
                } else {

                    final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                    dialog.setMessage("Are you sure want to confirm!");
                    dialog.setTitle("Warning!");

                    dialog.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Submit_withProgressbar(0, 0);
                                    // dismiss the dialog
                                }
                            });
                    dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    dialog.create().show();
                    //Submit_withProgressbar(0, 0);
                }
            }

        } else //added WHM [2020-04-06]
        {
            final AlertDialog.Builder builders = new AlertDialog.Builder(this);
            builders.setMessage("At least one item in voucher to confirm!");
            builders.setTitle("Galaxy Restaurant");

            builders.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(
                                DialogInterface dialog,
                                int which) {
                            // dismiss the dialog
                        }
                    });
            builders.create().show();
        }

    }


    public void Submit_withProgressbar(final int salesmenid, final int languagedesc)// throws NumberFormatException, JSONException
    {
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
                    try {
                        if (GlobalClass.tmpOffline && dbhelper.use_foodtruck(dbhelper.getwaiterid())) {
                            final String[] msg = Submit_Voucher(salesmenid);
                            progressBarHandler.post(new Runnable() {
                                public void run() {
                                    progressBar.dismiss();
                                    showAlertDialog(ctx, msg[0], msg[1], false);
                                }
                            });
                        } else {
                            final String[] msg = Submit_Data(salesmenid, languagedesc);
                            progressBarHandler.post(new Runnable() {
                                public void run() {
                                    progressBar.dismiss();
                                    showAlertDialog(ctx, msg[0], msg[1], false);
                                }
                            });
                        }

                    } catch (NumberFormatException e) {
                    } catch (JSONException e) {
                    } finally {
                    }
                }
            };
            t.start();// start thread
        } catch (Exception ex) {

        } finally {
        }
    }

    public String[] Submit_Data(int salesmenid, int languagedesc)
            throws NumberFormatException, JSONException {

        // region Get SaleObj
        SaleObj saleobj = new SaleObj();
        saleobj.setinvoiceno(((TextView) findViewById(R.id.txtInvoiceno)).getText().toString());
        saleobj.setDocID(((TextView) findViewById(R.id.txtDocID)).getText().toString());
        saleobj.settablenameid(((TextView) findViewById(R.id.txtTableNo)).getTag().toString());
        saleobj.setcustomerid(((TextView) findViewById(R.id.txtcustomer)).getTag().toString());
        saleobj.setamount(((TextView) findViewById(R.id.txttotalamount)).getText().toString());
        saleobj.setqty(((TextView) findViewById(R.id.txttotalqty)).getText().toString());
        saleobj.setcustcount(((TextView) findViewById(R.id.txtcustcount)).getText().toString());
        ///saleobj.set(((TextView)findViewById(R.id.txtcustcount)).getText().toString());
        saleobj.setuserid(Integer.parseInt(((TextView) findViewById(R.id.txtDocID)).getTag().toString()));

        if (GlobalClass.use_foodtruck || !dbhelper.use_deliverymanagement()) {
            saleobj.setDelivery_type(0);
        } else {
            saleobj.setDelivery_type(Integer.parseInt(((Spinner) findViewById(R.id.spinner_deliverytype)).getTag().toString()));
        }

        if (GlobalClass.use_foodtruck) {
            saleobj.setTaxfree(((CheckBox) findViewById(R.id.chkTaxfree)).isChecked());  //added by MPPA [27-01-2021]
        } else {
            saleobj.setTaxfree(false);
        }

        List<Salesmen> salesmenlist = (dbhelper.getsalemen_ListByUserID(Integer.toString(saleobj.getuserid())));

        if (salesmenlist.size() > 0) {
            if (dbhelper.getSalesmen_commission().equals("Y")) {
                saleobj.setsalesmen_id(salesmenid);
            } else
                saleobj.setsalesmen_id(salesmenlist.get(0).getsalesmen_id());
        } else
            saleobj.setsalesmen_id(0);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date now = new Date();
        String Date = formatter.format(now);
        saleobj.setdate(Date);

        // endregion

        String[] Msg = {"", ""};

        LinearLayout itemlistlayout = (LinearLayout) findViewById(R.id.itemlistlayout);
        LinearLayout linearlayout = new LinearLayout(this);

        // added by WaiWL on 12/08/2015 --for value item
        LinearLayout valueitem_itemlistlayout = (LinearLayout) findViewById(R.id.valueitem_itemlistlayout);
        LinearLayout valueitemlinearlayout = new LinearLayout(this);
        // //

        List<SaleItemObj> saleitemobjlist = new ArrayList<SaleItemObj>();

        JSONArray salejsonarray = new JSONArray();

        JSONArray saleitemjsonarray = new JSONArray();
        JSONObject jsonobj = new JSONObject();
        // sale_head_main
        if (saleid != "0") {
            jsonobj.put("tranid", saleid);
        }
        jsonobj.put("salemanid", saleobj.getsalesmen_id());
        jsonobj.put("userid", saleobj.getuserid());
        jsonobj.put("invoiceno", saleobj.getinvoiceno());
        jsonobj.put("customerid", saleobj.getcustomerid());
        jsonobj.put("invoice_amount", saleobj.getamount());
        jsonobj.put("invoice_qty", saleobj.getqty());
        jsonobj.put("tablenameid", saleobj.gettablenameid());
        jsonobj.put("custcount", saleobj.getcustcount());
        jsonobj.put("docid", saleobj.getDocID());
        jsonobj.put("delivery_type", saleobj.getDelivery_type());//added WHM [2020-05-08]
        jsonobj.put("taxfree", saleobj.isTaxfree());
        // salejsonarray.put(jsonobj);
        salejsonarray.put(jsonobj);

        JSONArray analysisjsonarray = new JSONArray();
        jsonobj = new JSONObject();
        jsonobj.put("PBT", customer_analysisobj.getcustomertype());
        jsonobj.put("malecount", customer_analysisobj.getmalecount());
        jsonobj.put("femalecount", customer_analysisobj.getfemalecount());
        jsonobj.put("adultcount", customer_analysisobj.getadultcount());
        jsonobj.put("childcount", customer_analysisobj.getchildcount());
        jsonobj.put("westerncount", customer_analysisobj.getwesterncount());
        jsonobj.put("asiancount", customer_analysisobj.getasiancount());
        jsonobj.put("myanmarcount", customer_analysisobj.getmyanmarcount());
        salejsonarray.put(jsonobj);
        // analysisjsonarray.put(jsonobj);
        if (GlobalClass.CheckConnectionForSubmit(ctx)) {
            if (saveddata[0] == saleobj.getinvoiceno()
                    && saveddata[1] == Integer.toString(itemlistlayout.getChildCount())) {
                // finish();
                // showAlertDialog(this, "Warning",
                // "Please Wait & Don't Click twice!", false);
                Msg[0] = "Warning!";
                Msg[1] = "Please Wait & Don't Click twice!";
                return Msg;
            }
            saveddata[0] = saleobj.getinvoiceno();
            saveddata[1] = Integer.toString(itemlistlayout.getChildCount());

            SaleItemObj saleitemobj = new SaleItemObj();

            for (int i = 0; i < itemlistlayout.getChildCount(); i++) {
                linearlayout = (LinearLayout) itemlistlayout.getChildAt(i);
                LinearLayout mainrow = (LinearLayout) linearlayout.getChildAt(0);// Get main item row
                TextView txtsrrow = (TextView) mainrow.getChildAt(0);
                //Modified by KNO for printed change qty
                if (!txtsrrow.getTag().toString().contains("P") || reprint == true) {
                    String Remark = "";
                    if (linearlayout.getChildCount() > 1) // get remark text
                    {
                        LinearLayout layout = (LinearLayout) linearlayout.getChildAt(linearlayout.getChildCount() - 1);
                        TextView txtstatus = (TextView) layout.getChildAt(3);
                        TextView txtitem = (TextView) layout.getChildAt(1);
                        String Status = "";
                        if (!txtstatus.equals(null))
                            Status = txtstatus.getText().toString();

                        if (Status.equals("remark")) {
                            Remark = txtitem.getText().toString(); //added by ZYP for unicode
                        }
                    }

                    for (int j = 0; j < linearlayout.getChildCount(); j++) {
                        LinearLayout layout = (LinearLayout) linearlayout.getChildAt(j);
                        // linearlayout =
                        // (LinearLayout)linearlayout.getChildAt(j);
                        TextView txtsrno = (TextView) layout.getChildAt(0);
                        TextView txtitem = (TextView) layout.getChildAt(1);
                        TextView txtqty = (TextView) layout.getChildAt(2);
                        TextView txtamount = (TextView) layout.getChildAt(3);
                        TextView txtunit = (TextView) layout.getChildAt(4);
                        TextView txttaway = (TextView) layout.getChildAt(5);
                        TextView txtsr = (TextView) layout.getChildAt(6);
                        TextView txtisFinishedItem = (TextView) layout.getChildAt(7);// added by WaiWL on 23/07/2015
                        TextView txtfiresr = (TextView) layout.getChildAt(8);

                        saleitemobj = new SaleItemObj();
                        if (!txtamount.getText().toString().equals("remark")) {
                            // SaleItemObj saleitemobj = new SaleItemObj();
                            saleitemobj.setsrno(txtsrno.getText().toString());
                            saleitemobj.setcode(txtitem.getTag().toString());
                            // saleprice = dbhelper.getItempricebyitemid(itemid);
                            // double price = (Double.parseDouble(saleprice) * Double.parseDouble(orgExgRate) / Double.parseDouble(defexgrate));
                            // saleprice = price + "";

                            //String defcurrency = dbhelper.getDefCurrency();
                            //String defexgrate = dbhelper.LoadExgRate(dbhelper.getServiceURL(), defcurrency);
                            //double price = Double.parseDouble(dbhelper.getItempricebyitemid(saleitemobj.getcode())) * Double.parseDouble(dbhelper.LoadExgRate(dbhelper.getServiceURL(), org_curr))  /
                            //               Double.parseDouble(defexgrate);

                            //   saleitemobj.setprice(price + "");

                            saleitemobj.setprice(dbhelper.getItempricebyitemid(saleitemobj.getcode()));
                            saleitemobj.setprice(new DatabaseHelper(this).getItempricebyitemid(saleitemobj.getcode()));
                            saleitemobj.setqty(txtqty.getTag().toString());
                            saleitemobj.setamount(txtamount.getTag().toString());
                            saleitemobj.setstatus(txtsrno.getTag().toString());
                            if (txtsrno.getTag().toString().equals("C")) {
                                saleitemobj.setcancelflag(true);
                            } else
                                saleitemobj.setcancelflag(false);
                            if (txtsrno.getText().toString().trim().equals("")) // modifier
                            {
                                //	String test=dbhelper.getItemsbyitemid(txtitem.getTag().toString()).getissetmenu()==null? "":dbhelper.getItemsbyitemid(txtitem.getTag().toString()).getissetmenu();
                                //	String checksetmenu=String.valueOf(dbhelper.getItemsbyitemid(txtitem.getTag().toString()).getissetmenu());

                                if (Boolean.parseBoolean(dbhelper.getItemsbyitemid(txtitem.getTag().toString()) == null ? "true" : dbhelper.getItemsbyitemid(txtitem.getTag().toString()).getissetmenu()) == false) {

                                    saleitemobj.setSetmenurowsr(txtsrrow.getText().toString().replace(".", "").trim());
                                } else {
                                    saleitemobj.setSetmenurowsr("0");
                                }
                                saleitemobj.setmodifiedrowsr(txtsrrow.getText().toString().replace(".", "").trim());
                                saleitemobj.setunittype("1");

                                if (!saleitemobj.getstatus().equals("N")) {
                                    // String sr =
                                    // dbhelper.getSaleItemQtyBySaleIDandSRNo(saleid,
                                    // saleitemobj.getsrno().replace(".",
                                    // "").trim()).get(0).getsr();
                                    saleitemobj.setsr("0");
                                }
                                saleitemobj.settaway("false");
                            } else {
                                if (!saleitemobj.getstatus().equals("N")) {
                                    // String sr =
                                    // dbhelper.getSaleItemQtyBySaleIDandSRNo(saleid,
                                    // saleitemobj.getsrno().replace(".",
                                    // "").trim()).get(0).getsr();
                                    saleitemobj.setsr(txtsr.getTag().toString());
                                }
                                if (use_unit == true && !txtunit.getTag().toString().equals(" ")) {
                                    List<UnitCodeObj> unitcodeobjlist = dbhelper.getunitcodebyunit(
                                            saleitemobj.getcode(), txtunit.getTag().toString());

                                    saleitemobj.setunittype(unitcodeobjlist.get(0).getunit_type());
                                } else
                                    saleitemobj.setunittype("1");

                                saleitemobj.settaway(txttaway.getTag().toString());
                                saleitemobj.setremark(Remark);
                                saleitemobj.setisFinishedItem(Boolean.valueOf(txtisFinishedItem.getTag().toString())); // added by WaiWL
                                // on 23/07/2015

                                // saleitemobj.setOrg_curr(org_curr); //added by EKK on 24-02-2020
                                saleitemobj.setOrg_curr(dbhelper.getsaleCurrbyitemid(saleitemobj.getcode()));
                            }
                            saleitemobjlist.add(saleitemobj);

                            if (dbhelper.getusemonitorinterface().equals("Y")) {
                                for (int k = 0; k < saleitemobjlist.size(); k++) {
                                    SaleItemObj obj = saleitemobjlist.get(k);
                                    if (obj.getcancelflag() == true) {
                                        Json_class jsonclass = new Json_class();
                                        String cook_status = jsonclass
                                                .getString(dbhelper
                                                        .getServiceURL()
                                                        + "/Data/CheckalreadyCooking?tableid="
                                                        + java.net.URLEncoder.encode(saleobj.gettablenameid())
                                                        + "&sr="
                                                        + java.net.URLEncoder.encode(obj.getsr()));
                                        if (cook_status.trim().equals("Y")) {
                                            Msg[0] = "Message";
                                            Msg[1] = "Cannot Cancel, Already Cooking "
                                                    + dbhelper.getItemNamebyitemid(obj.getcode())
                                                    + "!";
                                            return Msg;
                                        }
                                    }
                                }
                            }

                            jsonobj = new JSONObject();
                            try {
                                // sale_Det
                                jsonobj.put("sr", saleitemobj.getsr());
                                jsonobj.put("srno", saleitemobj.getsrno().replace(".", "").trim());
                                jsonobj.put("code", saleitemobj.getcode());
                                jsonobj.put("unit_qty", saleitemobj.getqty());
                                jsonobj.put("takeaway", saleitemobj.gettaway());
                                jsonobj.put("printed", false);
                                jsonobj.put("modifiedrowsr", saleitemobj.getmodifiedrowsr());
                                jsonobj.put("unittype", saleitemobj.getunittype());
                                String detremark = saleitemobj.getremark();
                                if (!LoginActivity.isUnicode) {
                                    detremark = Rabbit.zg2uni(detremark);
                                }
                                jsonobj.put("remark", detremark);
                                jsonobj.put("itemcancel", saleitemobj.getcancelflag());
                                jsonobj.put("status", saleitemobj.getstatus());
                                if (salesmenid == 0) {
                                    if (dbhelper.getsalemen_ListByUserID(dbhelper.getwaiterid()).size() > 0)
                                        salesmenid = (dbhelper.getsalemen_ListByUserID(dbhelper.getwaiterid()).get(0).getsalesmen_id());
                                    else
                                        salesmenid = 0;
                                }
                                jsonobj.put("salesmenuserid", dbhelper.getwaiterid());
                                jsonobj.put("salesmenid", salesmenid);
                                jsonobj.put("languagedesc", languagedesc);
                                jsonobj.put("isFinishedItem", saleitemobj.getisFinishedItem());// added

                                jsonobj.put("KTV_StartTime", "");
                                jsonobj.put("KTV_EndTime", "");
                                jsonobj.put("setmenurowsr", saleitemobj.getSetmenurowsr());
                                jsonobj.put("org_curr", saleitemobj.getOrg_curr()); //added by EKK on 24-02-2020

                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            salejsonarray.put(jsonobj);
                            // saleitemjsonarray.put(jsonobj);

                            //dbhelper.setPrinted(saleitemobj.getcode()); //added by ZYP for printed

                        }
                    }
                }
            }

            for (int i = 0; i < valueitem_itemlistlayout.getChildCount(); i++) {
                valueitemlinearlayout = (LinearLayout) valueitem_itemlistlayout
                        .getChildAt(i);
                LinearLayout mainrow = (LinearLayout) valueitemlinearlayout
                        .getChildAt(0);// Get main item row
                TextView txtsrrow = (TextView) mainrow.getChildAt(0);

                if (!txtsrrow.getTag().toString().contains("P") || reprint == true) {
                    for (int j = 0; j < valueitemlinearlayout.getChildCount(); j++) {
                        LinearLayout layout = (LinearLayout) valueitemlinearlayout
                                .getChildAt(j);
                        TextView txtsrno = (TextView) layout.getChildAt(0);
                        TextView txtitem = (TextView) layout.getChildAt(1);
                        TextView txtqty = (TextView) layout.getChildAt(2);
                        TextView txtamount = (TextView) layout.getChildAt(3);
                        TextView txtsr = (TextView) layout.getChildAt(4);
                        TextView txtKTV_StartTime = (TextView) layout
                                .getChildAt(5);
                        TextView txtKTV_EndTime = (TextView) layout
                                .getChildAt(6);
                        saleitemobj = new SaleItemObj();
                        if (!txtamount.getTag().toString().equals("remark")) {
                            saleitemobj.setsrno(txtsr.getText().toString());
                            saleitemobj.setcode(txtitem.getTag().toString());
                            saleitemobj
                                    .setprice(new DatabaseHelper(this)
                                            .getItempricebyitemid(saleitemobj
                                                    .getcode()));
                            saleitemobj.setqty(txtqty.getTag().toString());
                            saleitemobj.setamount(txtamount.getTag()
                                    .toString());
                            saleitemobj.setunittype("1");
                            saleitemobj.setstatus(txtsrno.getTag().toString());

                            if (!saleitemobj.getstatus().equals("N")) {
                                saleitemobj.setsr(txtsr.getTag().toString());
                            }

                            saleitemobjlist.add(saleitemobj);

                            jsonobj = new JSONObject();
                            try {
                                // sale_Det
                                jsonobj.put("sr", saleitemobj.getsr());
                                jsonobj.put("srno", saleitemobj.getsrno()
                                        .replace(".", "").trim());
                                jsonobj.put("code", saleitemobj.getcode());
                                jsonobj.put("unit_qty", saleitemobj.getqty());
                                jsonobj.put("takeaway", false);
                                jsonobj.put("printed", false);
                                jsonobj.put("modifiedrowsr", null);
                                jsonobj.put("unittype",
                                        saleitemobj.getunittype());
                                jsonobj.put("remark", "");
                                jsonobj.put("itemcancel", false);
                                jsonobj.put("status", saleitemobj.getstatus());
                                if (salesmenid == 0) {
                                    if (dbhelper.getsalemen_ListByUserID(
                                            dbhelper.getwaiterid()).size() > 0)
                                        salesmenid = (dbhelper
                                                .getsalemen_ListByUserID(
                                                        dbhelper.getwaiterid())
                                                .get(0).getsalesmen_id());
                                    else
                                        salesmenid = 0;
                                }
                                jsonobj.put("salesmenuserid",
                                        dbhelper.getwaiterid());
                                jsonobj.put("salesmenid", salesmenid);
                                jsonobj.put("languagedesc", languagedesc);
                                jsonobj.put("isFinishedItem", false);
                                jsonobj.put("KTV_StartTime", saleitemobj
                                        .getKTV_StartTime() == null ? ""
                                        : saleitemobj.getKTV_StartTime());// added
                                // by
                                // WaiWL
                                // on
                                // 12/08/2015
                                jsonobj.put("KTV_EndTime", saleitemobj
                                        .getKTV_EndTime() == null ? ""
                                        : saleitemobj.getKTV_EndTime());// added
                                // by
                                // WaiWL
                                // on
                                // 12/08/2015

                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            salejsonarray.put(jsonobj);
                        }
                    }
                }
            }

            /*
             * if(saleitemobjlist.size() > 0) {
             */
            Json_class jsonclass = new Json_class();
            // String URL = ( new DatabaseHelper(this).getServiceURL()+
            // "/Data/OrderTaking_Entry?orderdata="+java.net.URLEncoder.encode(salejsonarray.toString())+"&orderitemdata="+java.net.URLEncoder.encode(saleitemjsonarray.toString()));
            // JSONArray jsonmessage = jsonclass.getJson( new
            // DatabaseHelper(this).getServiceURL()+
            // "/Data/OrderTaking_Entry?orderdata="+java.net.URLEncoder.encode(salejsonarray.toString())+"&orderitemdata="+java.net.URLEncoder.encode(saleitemjsonarray.toString())+"&analysisdata="+java.net.URLEncoder.encode(analysisjsonarray.toString()));
            JSONArray jsonmessage = jsonclass.POST(
                    new DatabaseHelper(this).getServiceURL()
                            + "/Data/OrderTaking_Entrywithparameter", salejsonarray);


            if (jsonmessage.length() > 0) {
                if (Integer.parseInt(jsonmessage.get(0).toString()) == 0) {
                    int tranid = Integer
                            .parseInt(jsonmessage.get(2).toString());
                    dbhelper.AddSaledata(tranid, saleobj, saleitemobjlist);
                    // showAlertDialog(this, "Message", "Submit Successful!",
                    // false);
                    Msg[0] = "Message";
                    Msg[1] = "Submit Successful!";
                    return Msg;
                    // return new String[]{"Message","Submit Successful!"};
                }
                // added by WaiWL on 03/07/2015
                else if (Integer.parseInt(jsonmessage.get(0).toString()) == 2) {
                    Msg[0] = "Warning!";
                    Msg[1] = jsonmessage.get(2).toString();
                    isprint_order = true;
                    saveddata[0] = "";
                    saveddata[1] = "";
                    return Msg;

                } else if (Integer.parseInt(jsonmessage.get(0).toString()) == 1) {
                    if (GlobalClass.use_foodtruck) {
                        Msg[0] = "Message";
                        Msg[1] = jsonmessage.get(2).toString();
                    } else {
                        Msg[0] = "Warning!";
                        Msg[1] = jsonmessage.get(2).toString();
                    }

                    saveddata[0] = "";
                    saveddata[1] = "";
                    return Msg;
                }
                // ////////////
				/*//commented by Arkar Moe on [04/04/2017]
				else {
					// showAlertDialog(this, "Error",
					// jsonmessage.get(1).toString(), false);
					// showAlertDialog(this, "Error",
					// jsonmessage.get(2).toString(), false);
					Msg[0] = "Error";
					Msg[1] = "Current tablet user has no salesmenid! Please configure at Salesmen Setup"; //Added by ArkarMoe on [17/01/2017]-[Res-0552]
					//Msg[1] = jsonmessage.get(2).toString();

					saveddata[0] = "";
					saveddata[1] = "";
					return Msg;
					// return new
					// String[]{"Error",jsonmessage.get(2).toString()};
				}
				*/

            }

            /*
             * } else { showAlertDialog(this, "Warning", "No Data to Update!",
             * false); }
             */
        } else {
            // showAlertDialog(this, "Warning", "No Connection with Server!",
            // false);
            Msg[0] = "Warning!";
            Msg[1] = "No Connection with Server!";
            return Msg;
            // return new String[]{"Warning","No Connection with Server!"};
        }
        return Msg;
    }


    //added by ZYP [17/11/2020] for offline voucher save
    public String[] Submit_Voucher(int salesmenid) {

        SaleObj saleobj = new SaleObj();
        // region Sale Head

        saleobj.setsaleid(String.valueOf(saleid));
        saleobj.setinvoiceno(((TextView) findViewById(R.id.txtInvoiceno)).getText().toString());
//        saleobj.setDocID(((TextView) findViewById(R.id.txtDocID)).getText().toString());
        saleobj.settablenameid(((TextView) findViewById(R.id.txtTableNo)).getTag().toString());
        saleobj.setamount(((TextView) findViewById(R.id.txttotalamount)).getText().toString());
        saleobj.setcustomerid(((TextView) findViewById(R.id.txtcustomer)).getTag().toString());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date now = new Date();
        String Date = formatter.format(now);
        saleobj.setdate(Date);
        saleobj.setqty(((TextView) findViewById(R.id.txttotalqty)).getText().toString());
        saleobj.setcustcount(((TextView) findViewById(R.id.txtcustcount)).getText().toString());
        saleobj.setuserid(Integer.parseInt(((TextView) findViewById(R.id.txtDocID)).getTag().toString()));
        saleobj.setDelivery_type(0);//added WHM [2020-05-08]

        List<Salesmen> salesmenlist = (dbhelper.getsalemen_ListByUserID(Integer.toString(saleobj.getuserid())));

        if (salesmenlist.size() > 0) {
            if (dbhelper.getSalesmen_commission().equals("Y")) {
                saleobj.setsalesmen_id(salesmenid);
            } else
                saleobj.setsalesmen_id(salesmenlist.get(0).getsalesmen_id());
        } else
            saleobj.setsalesmen_id(0);

        // endregion

        LinearLayout itemlistlayout = (LinearLayout) findViewById(R.id.itemlistlayout);
        LinearLayout linearlayout = new LinearLayout(this);

        LinearLayout valueitem_itemlistlayout = (LinearLayout) findViewById(R.id.valueitem_itemlistlayout);
        LinearLayout valueitemlinearlayout = new LinearLayout(this);
        // //

        List<SaleItemObj> saleitemobjlist = new ArrayList<SaleItemObj>();
        SaleItemObj saleitemobj = new SaleItemObj();
        // region Sale Det
        int det_sr = 1;
        String det_srno = "";
        for (int i = 0; i < itemlistlayout.getChildCount(); i++) {
            linearlayout = (LinearLayout) itemlistlayout.getChildAt(i);
            LinearLayout mainrow = (LinearLayout) linearlayout.getChildAt(0);// Get main item row
            TextView txtsrrow = (TextView) mainrow.getChildAt(0);
            //if (!txtsrrow.getTag().toString().contains("P")) {

            String Remark = "";
            if (linearlayout.getChildCount() > 1) // get remark text
            {
                LinearLayout layout = (LinearLayout) linearlayout.getChildAt(linearlayout.getChildCount() - 1);
                TextView txtstatus = (TextView) layout.getChildAt(3);
                TextView txtitem = (TextView) layout.getChildAt(1);
                String Status = "";
                if (!txtstatus.equals(null))
                    Status = txtstatus.getText().toString();

                if (Status.equals("remark")) {
                    Remark = txtitem.getText().toString(); //added by ZYP for unicode
                }
            }

            for (int j = 0; j < linearlayout.getChildCount(); j++) {
                LinearLayout layout = (LinearLayout) linearlayout.getChildAt(j);

                TextView txtsrno = (TextView) layout.getChildAt(0);
                TextView txtitem = (TextView) layout.getChildAt(1);
                TextView txtqty = (TextView) layout.getChildAt(2);
                TextView txtamount = (TextView) layout.getChildAt(3);
                TextView txtunit = (TextView) layout.getChildAt(4);
                TextView txttaway = (TextView) layout.getChildAt(5);
                TextView txtsr = (TextView) layout.getChildAt(6);
                TextView txtisFinishedItem = (TextView) layout.getChildAt(7);// added by WaiWL on 23/07/2015
                TextView txtfiresr = (TextView) layout.getChildAt(8);

                saleitemobj = new SaleItemObj();
                if (!txtamount.getText().toString().equals("remark")) {
                    saleitemobj.setsrno(txtsrno.getText().toString());
                    saleitemobj.setcode(txtitem.getTag().toString());
                    saleitemobj.setprice(dbhelper.getItempricebyitemid(saleitemobj.getcode()));
                    saleitemobj.setqty(txtqty.getTag().toString());
                    saleitemobj.setamount(txtamount.getTag().toString());
                    saleitemobj.setstatus(txtsrno.getTag().toString());

                    if (txtsrno.getTag().toString().equals("P")) {
                        saleitemobj.setsubmitflag(true);
                    } else
                        saleitemobj.setsubmitflag(false);

                    if (txtsrno.getTag().toString().contains("C")) {
                        saleitemobj.setcancelflag(true);
                    } else
                        saleitemobj.setcancelflag(false);

                    if (txtsrno.getText().toString().trim().equals("")) // modifier and setmenu
                    {
                        if (Boolean.parseBoolean(dbhelper.getItemsbyitemid(txtitem.getTag().toString()) == null ? "true" : dbhelper.getItemsbyitemid(txtitem.getTag().toString()).getissetmenu()) == false) {

                            saleitemobj.setSetmenurowsr(txtsrrow.getText().toString().replace(".", "").trim());
                            saleitemobj.setremark("setmenu");
                        } else {
                            saleitemobj.setSetmenurowsr("0");
                            saleitemobj.setremark("modify");
                        }
                        saleitemobj.setsr(Integer.toString(det_sr++));
                        saleitemobj.setsrno(det_srno);
                        //saleitemobj.setsrno(txtsrrow.getText().toString().replace(".", "").trim());
                        //saleitemobj.setmodifiedrowsr(txtsrrow.getText().toString().replace(".", "").trim());
                        saleitemobj.setmodifiedrowsr(modifierrowsr);
                        saleitemobj.setunittype("1");

                        if (!saleitemobj.getstatus().equals("N")) {
                            saleitemobj.setsr("0");
                        }


                        saleitemobj.settaway("false");
                    } else {
//                            if (!saleitemobj.getstatus().equals("N")) {
//                                saleitemobj.setsr(txtsr.getTag().toString());
//                            }
                        saleitemobj.setsr(Integer.toString(det_sr++));
                        modifierrowsr = saleitemobj.getsr();
                        det_srno = saleitemobj.getsrno();

                        if (use_unit == true && !txtunit.getTag().toString().equals(" ")) {
                            List<UnitCodeObj> unitcodeobjlist = dbhelper.getunitcodebyunit(
                                    saleitemobj.getcode(), txtunit.getTag().toString());

                            saleitemobj.setunittype(unitcodeobjlist.get(0).getunit_type());

                        } else
                            saleitemobj.setunittype("1");

                        saleitemobj.settaway(txttaway.getTag().toString());
                        saleitemobj.setremark(Remark.replace("(", "").replace(")", "").trim());
                        saleitemobj.setisFinishedItem(Boolean.valueOf(txtisFinishedItem.getTag().toString())); // added by WaiWL

                        saleitemobj.setOrg_curr(dbhelper.getsaleCurrbyitemid(saleitemobj.getcode()));
                    }

                    saleitemobjlist.add(saleitemobj);

                }
            }
            //}

        }

        for (int i = 0; i < valueitem_itemlistlayout.getChildCount(); i++) {
            valueitemlinearlayout = (LinearLayout) valueitem_itemlistlayout
                    .getChildAt(i);
            LinearLayout mainrow = (LinearLayout) valueitemlinearlayout
                    .getChildAt(0);// Get main item row
            TextView txtsrrow = (TextView) mainrow.getChildAt(0);

            if (!txtsrrow.getTag().toString().contains("P")) {
                for (int j = 0; j < valueitemlinearlayout.getChildCount(); j++) {
                    LinearLayout layout = (LinearLayout) valueitemlinearlayout
                            .getChildAt(j);
                    TextView txtsrno = (TextView) layout.getChildAt(0);
                    TextView txtitem = (TextView) layout.getChildAt(1);
                    TextView txtqty = (TextView) layout.getChildAt(2);
                    TextView txtamount = (TextView) layout.getChildAt(3);
                    TextView txtsr = (TextView) layout.getChildAt(4);
                    TextView txtKTV_StartTime = (TextView) layout
                            .getChildAt(5);
                    TextView txtKTV_EndTime = (TextView) layout
                            .getChildAt(6);
                    saleitemobj = new SaleItemObj();
                    if (!txtamount.getTag().toString().equals("remark")) {
                        saleitemobj.setsrno(txtsr.getText().toString());
                        saleitemobj.setcode(txtitem.getTag().toString());
                        saleitemobj.setprice(new DatabaseHelper(this)
                                .getItempricebyitemid(saleitemobj.getcode()));
                        saleitemobj.setqty(txtqty.getTag().toString());
                        saleitemobj.setamount(txtamount.getTag().toString());
                        saleitemobj.setunittype("1");
                        saleitemobj.setstatus(txtsrno.getTag().toString());

                        if (!saleitemobj.getstatus().equals("N")) {
                            saleitemobj.setsr(txtsr.getTag().toString());
                        }

                        saleitemobjlist.add(saleitemobj);

                    }
                }
            }
        }

        //endregion Sale Det

        try {
            dbhelper.UpdateSaledata(Integer.parseInt(saleid), saleobj, saleitemobjlist);

            String[] Msg = {"", ""};
            Msg[0] = "Message";
            Msg[1] = "Submit Successful!";
            return Msg;
        } catch (Exception ex) {
            String[] Msg = {"", ""};
            Msg[0] = "Message";
            Msg[1] = "Error occur while saving!";
            return Msg;
        }


    }


    public void Fire_withProgressbar()
    // throws NumberFormatException, JSONException
    {
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
                    try {
                        final String[] msg = Fire_Data();
                        progressBarHandler.post(new Runnable() {
                            public void run() {
                                progressBar.dismiss();
                                showAlertDialog(ctx, msg[0], msg[1], false);
                            }
                        });
                    } catch (NumberFormatException e) {
                    } catch (JSONException e) {
                    } finally {
                    }
                }
            };
            t.start();// start thread
        } catch (Exception ex) {

        } finally {
        }
    }


    public String[] Fire_Data() throws NumberFormatException,
            JSONException {
// region Get SaleObj
        SaleObj saleobj = new SaleObj();

// endregion
        String[] Msg = {"", ""};

        LinearLayout itemlistlayout = (LinearLayout) findViewById(R.id.itemlistlayout);
        LinearLayout linearlayout = new LinearLayout(this);

// added by WaiWL on 12/08/2015 --for value item
        LinearLayout valueitem_itemlistlayout = (LinearLayout) findViewById(R.id.valueitem_itemlistlayout);
        LinearLayout valueitemlinearlayout = new LinearLayout(this);
// //

        List<SaleItemObj> saleitemobjlist = new ArrayList<SaleItemObj>();

        JSONArray salejsonarray = new JSONArray();

        JSONArray saleitemjsonarray = new JSONArray();
        JSONObject jsonobj = new JSONObject();


        JSONArray analysisjsonarray = new JSONArray();
// analysisjsonarray.put(jsonobj);
        if (GlobalClass.CheckConnectionForSubmit(ctx)) {
            if (saveddata[0] == saleobj.getinvoiceno()
                    && saveddata[1] == Integer.toString(itemlistlayout
                    .getChildCount())) {
                Msg[0] = "Warning!";
                Msg[1] = "Please Wait & Don't Click twice!";
                return Msg;
            }
            saveddata[0] = saleobj.getinvoiceno();
            saveddata[1] = Integer.toString(itemlistlayout.getChildCount());

            SaleItemObj saleitemobj = new SaleItemObj();
            String firesr_tmp = "";

            for (int i = 0; i < itemlistlayout.getChildCount(); i++) {
                linearlayout = (LinearLayout) itemlistlayout.getChildAt(i);
                LinearLayout mainrow = (LinearLayout) linearlayout
                        .getChildAt(0);// Get main item row
                TextView txtsrrow = (TextView) mainrow.getChildAt(0);
                TextView txtfire = (TextView) mainrow.getChildAt(8);
                if (txtsrrow.getTag().toString().contains("P") && txtfire.getTag().toString().contains("N")) {
                    String Remark = "";

                    for (int j = 0; j < linearlayout.getChildCount(); j++) {
                        LinearLayout layout = (LinearLayout) linearlayout
                                .getChildAt(j);
                        TextView txtsrno = (TextView) layout.getChildAt(0);
                        TextView txtitem = (TextView) layout.getChildAt(1);
                        TextView txtqty = (TextView) layout.getChildAt(2);
                        TextView txtamount = (TextView) layout.getChildAt(3);
                        TextView txtsr = (TextView) layout.getChildAt(6);

                        TextView txtModifierStatus = (TextView) layout.getChildAt(5); //added by ZYP 24-08-2020
                        TextView txtfiresr = (TextView) layout.getChildAt(8);
                        if (!txtModifierStatus.getText().toString().equals("setmenuitem") && !txtModifierStatus.getText().toString().equals("modifier")) {
                            firesr_tmp = txtfiresr.getText().toString();
                        }

                        //TextView txtfiresr = (TextView) layout.getChildAt(8);

                        saleitemobj = new SaleItemObj();
                        if (!txtamount.getTag().toString().equals("remark")) {


                            saleitemobj.setsaleitemid(saleid);
                            saleitemobj.setsrno(txtsrno.getText().toString());
                            saleitemobj.setcode(txtitem.getTag().toString());
                            //saleitemobj.setFire_sr(txtfiresr.getText().toString());
                            saleitemobj.setFire_sr(firesr_tmp);

                            saleitemobjlist.add(saleitemobj);

                            jsonobj = new JSONObject();
                            try {

                                jsonobj.put("sr", saleitemobj.getsr());
                                jsonobj.put("srno", saleitemobj.getsrno()
                                        .replace(".", "").trim());
                                jsonobj.put("code", saleitemobj.getcode());
                                jsonobj.put("firesr", saleitemobj.getFire_sr());
                                jsonobj.put("tranid", saleid);

                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            salejsonarray.put(jsonobj);
                            // saleitemjsonarray.put(jsonobj);
                        }
                    }
                }
            }

            for (int i = 0; i < valueitem_itemlistlayout.getChildCount(); i++) {
                valueitemlinearlayout = (LinearLayout) valueitem_itemlistlayout
                        .getChildAt(i);
                LinearLayout mainrow = (LinearLayout) valueitemlinearlayout
                        .getChildAt(0);// Get main item row
                TextView txtsrrow = (TextView) mainrow.getChildAt(0);
                TextView txtfire = (TextView) mainrow.getChildAt(8);

                if (txtsrrow.getTag().toString().contains("P") && txtfire.getTag().toString().contains("N")) {
                    for (int j = 0; j < valueitemlinearlayout.getChildCount(); j++) {
                        LinearLayout layout = (LinearLayout) valueitemlinearlayout
                                .getChildAt(j);
                        TextView txtsrno = (TextView) layout.getChildAt(0);
                        TextView txtitem = (TextView) layout.getChildAt(1);
                        TextView txtqty = (TextView) layout.getChildAt(2);
                        TextView txtamount = (TextView) layout.getChildAt(3);
                        TextView txtsr = (TextView) layout.getChildAt(4);

                        saleitemobj = new SaleItemObj();
                        if (!txtamount.getTag().toString().equals("remark")) {

                            saleitemobj.setsrno(txtsr.getText().toString());
                            saleitemobj.setcode(txtitem.getTag().toString());
                            saleitemobj
                                    .setprice(new DatabaseHelper(this)
                                            .getItempricebyitemid(saleitemobj
                                                    .getcode()));
                            saleitemobj.setqty(txtqty.getTag().toString());


                            saleitemobjlist.add(saleitemobj);

                            jsonobj = new JSONObject();
                            try {
                                // sale_Det
                                jsonobj.put("sr", saleitemobj.getsr());
                                jsonobj.put("srno", saleitemobj.getsrno()
                                        .replace(".", "").trim());
                                jsonobj.put("code", saleitemobj.getcode());
                                jsonobj.put("firesr", saleitemobj.getFire_sr());
                                jsonobj.put("tranid", saleid);

                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            salejsonarray.put(jsonobj);
                        }
                    }
                }
            }


            Json_class jsonclass = new Json_class();
            JSONArray jsonmessage = jsonclass.POST(
                    new DatabaseHelper(this).getServiceURL()
                            + "/Data/Fire_Print",
                    salejsonarray);

            if (jsonmessage.length() > 0) {
                if (Integer.parseInt(jsonmessage.get(0).toString()) == 0) {
                    int tranid = Integer
                            .parseInt(jsonmessage.get(2).toString());
                    dbhelper.updatefireSaledata(tranid, saleobj, saleitemobjlist);
                    // showAlertDialog(this, "Message", "Submit Successful!",
                    // false);
                    Msg[0] = "Message";
                    Msg[1] = "Submit Successful!";
                    return Msg;
                    // return new String[]{"Message","Submit Successful!"};
                }
                // added by WaiWL on 03/07/2015
                else if (Integer.parseInt(jsonmessage.get(0).toString()) == 2) {
                    Msg[0] = "Warning!";
                    Msg[1] = jsonmessage.get(2).toString();

                    saveddata[0] = "";
                    saveddata[1] = "";
                    return Msg;
                } else if (Integer.parseInt(jsonmessage.get(0).toString()) == 1) {
                    Msg[0] = "Warning!";
                    Msg[1] = jsonmessage.get(2).toString();

                    saveddata[0] = "";
                    saveddata[1] = "";
                    return Msg;
                }


            }


        } else {

            Msg[0] = "Warning!";
            Msg[1] = "No Connection with Server.";
            return Msg;
        }
        return Msg;
    }

    public void ItemTransfer() {             //added by ZYP [03-03-2020]
        final ProgressDialog progressBar; // Progress
        final Handler progressBarHandler = new Handler();
        progressBar = new ProgressDialog(ctx);
        progressBar.setCancelable(false);
        progressBar.setMessage("Please Wait ...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        progressBar.show();
        // reset progress bar status
        try {
            Thread t = new Thread() {
                public void run() {
                    try {
                        final String[] msg = Submit_ItemTransfer();
                        progressBarHandler.post(new Runnable() {
                            public void run() {
                                progressBar.dismiss();
                                showAlertDialog(ctx, msg[0], msg[1], false);
                            }
                        });
                    } catch (NumberFormatException e) {
                    } catch (JSONException e) {
                    } finally {
                    }
                }
            };
            t.start();// start thread
        } catch (Exception ex) {

        } finally {
        }
    }

    public String[] Submit_ItemTransfer() throws NumberFormatException, JSONException {

        String[] Msg = {"", ""};

        JSONArray salejsonarray = new JSONArray();
        if (GlobalClass.CheckConnectionForSubmit(ctx)) {

            for (int i = 0; i < billadapterRight.getCount(); i++) {

                SaleItemObj saleitemobj = new SaleItemObj();
                saleitemobj = arraysaleobjlistright.get(i);

                JSONObject jsonobj = new JSONObject();
                try {
                    // sale_Det
                    jsonobj.put("sr", saleitemobj.getsr());
                    //jsonobj.put("salesmenuserid",dbhelper.getwaiterid());
                    jsonobj.put("qty", saleitemobj.getqty().toString().replace(".0", ""));
                    jsonobj.put("tranid", saleid);
                    jsonobj.put("totranid", totranid);

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                salejsonarray.put(jsonobj);

            }

            Json_class jsonclass = new Json_class();
            JSONArray jsonmessage = jsonclass.POST(
                    new DatabaseHelper(this).getServiceURL()
                            + "/Data/ItemTransfer",
                    salejsonarray);

            if (jsonmessage.length() > 0) {
                if (Integer.parseInt(jsonmessage.get(0).toString()) == 0) {
                    int tranid = Integer
                            .parseInt(jsonmessage.get(2).toString());
                    Msg[0] = "Message";
                    Msg[1] = "Submit Successful!";
                    return Msg;
                    // return new String[]{"Message","Submit Successful!"};
                }
                // added by WaiWL on 03/07/2015
                else if (Integer.parseInt(jsonmessage.get(0).toString()) == 2) {
                    Msg[0] = "Warning!";
                    Msg[1] = jsonmessage.get(2).toString();

                    saveddata[0] = "";
                    saveddata[1] = "";
                    return Msg;
                } else if (Integer.parseInt(jsonmessage.get(0).toString()) == 1) {
                    Msg[0] = "Warning!";
                    Msg[1] = jsonmessage.get(2).toString();

                    saveddata[0] = "";
                    saveddata[1] = "";
                    return Msg;
                }
            }


        } else {

            Msg[0] = "Warning!";
            Msg[1] = "No Connection with Server!";
            return Msg;
        }
        return Msg;
    }

    public void SplitBill()
    // throws NumberFormatException, JSONException
    {
        final ProgressDialog progressBar; // Progress
        final Handler progressBarHandler = new Handler();
        progressBar = new ProgressDialog(ctx);
        progressBar.setCancelable(false);
        progressBar.setMessage("Please Wait ...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        progressBar.show();
        // reset progress bar status
        try {
            Thread t = new Thread() {
                public void run() {
                    try {
                        final String[] msg = Submit_SplitBill();
                        progressBarHandler.post(new Runnable() {
                            public void run() {
                                progressBar.dismiss();
                                showAlertDialog(ctx, msg[0], msg[1], false);
                            }
                        });
                    } catch (NumberFormatException e) {
                    } catch (JSONException e) {
                    } finally {
                    }
                }
            };
            t.start();// start thread
        } catch (Exception ex) {

        } finally {
        }
    }


    public String[] Submit_SplitBill() throws NumberFormatException, JSONException {


// endregion
        String[] Msg = {"", ""};

        JSONArray salejsonarray = new JSONArray();
// analysisjsonarray.put(jsonobj);
        if (GlobalClass.CheckConnectionForSubmit(ctx)) {

            for (int i = 0; i < billadapterRight.getCount(); i++) {

                SaleItemObj saleitemobj = new SaleItemObj();
                saleitemobj = arraysaleobjlistright.get(i);

                JSONObject jsonobj = new JSONObject();
                try {
                    // sale_Det
                    jsonobj.put("sr", saleitemobj.getsr());
                    //jsonobj.put("salesmenuserid",dbhelper.getwaiterid());
                    jsonobj.put("qty", saleitemobj.getqty().toString().replace(".0", ""));
                    jsonobj.put("tranid", saleid);

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                salejsonarray.put(jsonobj);

            }

            Json_class jsonclass = new Json_class();
            JSONArray jsonmessage = jsonclass.POST(
                    new DatabaseHelper(this).getServiceURL()
                            + "/Data/SplitBill",
                    salejsonarray);

            if (jsonmessage.length() > 0) {
                if (Integer.parseInt(jsonmessage.get(0).toString()) == 0) {
                    int tranid = Integer
                            .parseInt(jsonmessage.get(2).toString());
                    Msg[0] = "Message";
                    Msg[1] = "Submit Successful!";
                    return Msg;
                    // return new String[]{"Message","Submit Successful!"};
                }
                // added by WaiWL on 03/07/2015
                else if (Integer.parseInt(jsonmessage.get(0).toString()) == 2) {
                    Msg[0] = "Warning!";
                    Msg[1] = jsonmessage.get(2).toString();

                    saveddata[0] = "";
                    saveddata[1] = "";
                    return Msg;
                } else if (Integer.parseInt(jsonmessage.get(0).toString()) == 1) {
                    Msg[0] = "Warning!";
                    Msg[1] = jsonmessage.get(2).toString();

                    saveddata[0] = "";
                    saveddata[1] = "";
                    return Msg;
                }


            }


        } else {

            Msg[0] = "Warning!";
            Msg[1] = "No Connection with Server!";
            return Msg;
        }
        return Msg;
    }


    public void Billing_Data() throws NumberFormatException, JSONException {
        if (GlobalClass.CheckConnectionForSubmit(ctx)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            // Get the layout inflater
            LayoutInflater inflater = this.getLayoutInflater();

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog
            // layout
            builder.setView(inflater.inflate(R.layout.activity_customer, null));
            final Dialog dialog = builder.create();

            WindowManager.LayoutParams wmlp = dialog.getWindow()
                    .getAttributes();
            wmlp.gravity = Gravity.CENTER;
            wmlp.y = GlobalClass.getDPsize(100, this);

            dialog.show();
            Window window = dialog.getWindow();
            window.setLayout(GlobalClass.getDPsize(400, this),
                    GlobalClass.getDPsize(500, this));

            dialog.show();

            TextView txtTitle = (TextView) dialog
                    .findViewById(R.id.txtSelectCustomer);
            txtTitle.setText("Select Payment Method");

            AutoCompleteTextView txtCustomer = (AutoCompleteTextView) dialog
                    .findViewById(R.id.txtautocompletecustomer);
            txtCustomer.setVisibility(View.GONE); // Invisible

            ListView customerlistview = (ListView) dialog
                    .findViewById(R.id.custlist);

            final List<RowItem> rowItems = new ArrayList<RowItem>();


            String TableNameID = ((TextView) findViewById(R.id.txtTableNo))
                    .getTag().toString();

            //String customerid=dbhelper.getCustomerIdByTableId(TableNameID);
            String customerid = (String) findViewById(R.id.txtcustomer).getTag().toString();//modified WHM[2020-01-03]
            Boolean credit = dbhelper.checkCustomerCredit(Integer.parseInt(customerid));

            RowItem item = new RowItem(0, 1, "", "Cash Down", "");
            rowItems.add(item);
            if (credit) {
                item = new RowItem(0, 2, "", "Credit", "");
                rowItems.add(item);
            }
//			item = new RowItem(0, 2, "", "Credit", "");
//			rowItems.add(item);
            item = new RowItem(0, 4, "", "Card", "");
            rowItems.add(item);


            CustomerCustomListViewAdapter oddadapter = new CustomerCustomListViewAdapter(
                    this, R.layout.customerlist_item, rowItems);
            customerlistview.setAdapter(oddadapter);

            customerlistview
                    .setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> parent,
                                                View view, int position, long id) {
                            TextView txtheader = (TextView) ((ViewGroup) view)
                                    .getChildAt(2);
                            String PaymentTypeID = txtheader.getTag()
                                    .toString();
                            String DocID = ((TextView) findViewById(R.id.txtDocID))
                                    .getText().toString();
                            String UserID = ((TextView) findViewById(R.id.txtDocID))
                                    .getTag().toString();
                            String TableID = ((TextView) findViewById(R.id.txtTableNo))
                                    .getTag().toString();

                            String CustomerID = (String) findViewById(R.id.txtcustomer).getTag().toString();
                            ;//added WHM [2020-01-03]

                            Json_class jsonclass = new Json_class();

//							JSONArray jsonmessage = jsonclass
//									.getJson(new DatabaseHelper(ctx)
//											.getServiceURL()
//											+ "/Data/Billing_Data?DocID="
//											+ java.net.URLEncoder.encode(DocID)
//											+ "&UserID="
//											+ java.net.URLEncoder
//													.encode(UserID)
//											+ "&TableID="
//											+ java.net.URLEncoder
//													.encode(TableID)
//											+ "&paytypeid="
//											+ java.net.URLEncoder
//													.encode(PaymentTypeID)); //org

                            JSONArray jsonmessage = jsonclass
                                    .getJson(new DatabaseHelper(ctx)
                                            .getServiceURL()
                                            + "/Data/Billing_Data?DocID="
                                            + java.net.URLEncoder.encode(DocID)
                                            + "&UserID="
                                            + java.net.URLEncoder
                                            .encode(UserID)
                                            + "&TableID="
                                            + java.net.URLEncoder
                                            .encode(TableID)
                                            + "&paytypeid="
                                            + java.net.URLEncoder
                                            .encode(PaymentTypeID)
                                            + "&customerid="
                                            + java.net.URLEncoder
                                            .encode(CustomerID));//modified WHM [2020-01-03]

                            if (jsonmessage.length() > 0) {
                                try {
                                    if (Integer.parseInt(jsonmessage.get(0)
                                            .toString()) == 0) {
                                        showAlertDialog(
                                                ctx,
                                                "Message",
                                                "Complete sending Bill Message!",
                                                false);
                                    } else {
                                        showAlertDialog(ctx, "Error Message",
                                                jsonmessage.get(1).toString(),
                                                false);
                                        showAlertDialog(ctx, "Error Message",
                                                jsonmessage.get(2).toString(),
                                                false);
                                    }
                                } catch (NumberFormatException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                } catch (JSONException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            }
                            dialog.dismiss();
                        }
                    });

        } else {
            showAlertDialog(this, "Warning!", "No Connection with Server!",
                    false);

        }
    }

    public void GetTable() throws JSONException {
        // check for Internet status
        SaleObj saleobj = new SaleObj();
        saleobj.setinvoiceno(((TextView) findViewById(R.id.txtInvoiceno))
                .getText().toString());
        saleobj.setDocID(((TextView) findViewById(R.id.txtDocID)).getText()
                .toString());
        saleobj.settablenameid(((TextView) findViewById(R.id.txtTableNo))
                .getTag().toString());
        saleobj.setcustomerid(((TextView) findViewById(R.id.txtcustomer))
                .getTag().toString());
        saleobj.setamount(((TextView) findViewById(R.id.txttotalamount))
                .getText().toString());
        saleobj.setqty(((TextView) findViewById(R.id.txttotalqty)).getText()
                .toString());
        saleobj.setcustcount(((TextView) findViewById(R.id.txtcustcount))
                .getText().toString());
        // /saleobj.set(((TextView)findViewById(R.id.txtcustcount)).getText().toString());
        saleobj.setuserid(Integer.parseInt(dbhelper.getwaiterid()));
        saleobj.setsalesmen_id(Integer.parseInt(dbhelper.getwaiterid()));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date now = new Date();
        String Date = formatter.format(now);
        saleobj.setdate(Date);

        dbhelper.DeleteKitchenDataTalbe(saleobj.gettablenameid());

        // added by WaiWL on 12/06/2015
        // if(dbhelper.getOfflineFlag().equals(false))
        if (GlobalClass.tmpOffline == false) {
            JSONArray salejsonarray = new JSONArray();

            JSONObject jsonobj = new JSONObject();
            // sale_head_main
            jsonobj.put("salemanid", saleobj.getsalesmen_id());
            jsonobj.put("userid", saleobj.getuserid());
            jsonobj.put("invoiceno", saleobj.getinvoiceno());
            jsonobj.put("customerid", saleobj.getcustomerid());
            jsonobj.put("invoice_amount", saleobj.getamount());
            jsonobj.put("invoice_qty", saleobj.getqty());
            jsonobj.put("tablenameid", saleobj.gettablenameid());
            jsonobj.put("custcount", saleobj.getcustcount());
            jsonobj.put("docid", saleobj.getDocID());
            salejsonarray.put(jsonobj);

            if (GlobalClass.CheckConnectionForSubmit(ctx)) {
                Json_class jsonclass = new Json_class();
                // String URL = ( new DatabaseHelper(this).getServiceURL()+
                // "/Data/OrderTaking_Entry?orderdata="+java.net.URLEncoder.encode(salejsonarray.toString())+"&orderitemdata="+java.net.URLEncoder.encode(saleitemjsonarray.toString()));
                JSONArray jsonmessage = jsonclass.getJson(new DatabaseHelper(
                        this).getServiceURL()
                        + "/Data/Sale_Head_Main_GetTable?orderdata="
                        + java.net.URLEncoder.encode(salejsonarray.toString()));

                if (jsonmessage.length() > 0) {
                    if (Integer.parseInt(jsonmessage.get(0).toString()) == 0) {
                        String docid = jsonmessage.get(2).toString();
                        String tranid = jsonmessage.get(3).toString();
                        txtDocID = (TextView) findViewById(R.id.txtDocID);
                        txtDocID.setText(docid);
                        saleid = tranid;
                        delivery_tranid = saleid; //added WHM [2020-05-13] delivery
                        sale_head_deliverytype_id = 0;
                    } else {
                    }
                }
            } else {
                showAlertDialog(this, "Warning!", "No Connection with Server!",
                        false);
            }
        }
    }

    //added by ZYP [21-10-2020]
    public void GetTable(String invoiceNo) throws JSONException {
        SaleObj saleobj = new SaleObj();
        saleobj.setinvoiceno(invoiceNo);
        saleobj.setDocID(invoiceNo);
        saleobj.settablenameid("0");
        saleobj.setcustomerid("0");
        saleobj.setamount("0");
        saleobj.setqty("0");
        saleobj.setcustcount("1");

        saleobj.setuserid(Integer.parseInt(dbhelper.getwaiterid()));
        saleobj.setsalesmen_id(Integer.parseInt(dbhelper.getwaiterid()));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date now = new Date();
        String Date = formatter.format(now);
        saleobj.setdate(Date);

        saleobj.setSelforder_approve(0);

        if (!GlobalClass.tmpOffline) {
            JSONArray salejsonarray = new JSONArray();

            JSONObject jsonobj = new JSONObject();
            // sale_head_main
            jsonobj.put("salemanid", saleobj.getsalesmen_id());
            jsonobj.put("userid", saleobj.getuserid());
            jsonobj.put("invoiceno", saleobj.getinvoiceno());
            jsonobj.put("customerid", saleobj.getcustomerid());
            jsonobj.put("invoice_amount", saleobj.getamount());
            jsonobj.put("invoice_qty", saleobj.getqty());
            jsonobj.put("tablenameid", saleobj.gettablenameid());
            jsonobj.put("custcount", saleobj.getcustcount());
            jsonobj.put("docid", saleobj.getDocID());
            jsonobj.put("selforder_approve", saleobj.getSelforder_approve());
            salejsonarray.put(jsonobj);

            if (GlobalClass.CheckConnectionForSubmit(ctx)) {
                Json_class jsonclass = new Json_class();
                // String URL = ( new DatabaseHelper(this).getServiceURL()+
                // "/Data/OrderTaking_Entry?orderdata="+java.net.URLEncoder.encode(salejsonarray.toString())+"&orderitemdata="+java.net.URLEncoder.encode(saleitemjsonarray.toString()));
                JSONArray jsonmessage = jsonclass.getJson(new DatabaseHelper(
                        this).getServiceURL()
                        + "/Data/Sale_Head_Main_GetTable_SelfOrder?orderdata="
                        + java.net.URLEncoder.encode(salejsonarray.toString()));

                if (jsonmessage.length() > 0) {
                    if (Integer.parseInt(jsonmessage.get(0).toString()) == 0) {
                        if (Integer.parseInt(jsonmessage.get(3).toString()) != 0) {
//                            current_sale_tranid = Integer.parseInt(jsonmessage.get(3).toString());
//                            current_sale_tableid=Integer.parseInt(jsonmessage.get(4).toString());
                            //foodtruck_saleid = Integer.parseInt(jsonmessage.get(3).toString());
                            saleid = jsonmessage.get(3).toString();
                            txtinvoiceno.setText(jsonmessage.get(2).toString());

                            List<SaleItemObj> saleItemObjList = new ArrayList<SaleItemObj>();
                            saleobj.setinvoiceno(jsonmessage.get(2).toString());
                            dbhelper.UpdateSaledata(Integer.parseInt(saleid), saleobj, saleItemObjList);

                        }
                    }

                } else {
                    if (Integer.parseInt(jsonmessage.get(3).toString()) != 0) {
//                        current_sale_tranid = Integer.parseInt(jsonmessage.get(3).toString());
//                        current_sale_tableid=Integer.parseInt(jsonmessage.get(4).toString());
                    }
                }
            } else {

                showAlertDialog(this, "Warning", "No Connection with Server!",
                        false);
            }
        } else if (GlobalClass.tmpOffline && GlobalClass.use_foodtruck) {
            //List<SaleItemObj> saleItemObjList = new ArrayList<SaleItemObj>();
            //dbhelper.UpdateSaledata(foodtruck_saleid, saleobj, saleItemObjList);
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

        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        final Context ctx = this;
        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (title == "Message") {
                    if (dbhelper.getisContinuousSave().equals("Y")) {
                        Intent data = new Intent(ctx, OrderTaking.class);
                        Bundle b = new Bundle();
                        b.putString("TableID", tableID);
                        b.putString("TableName", dbhelper
                                .getTableNameByTableID(Integer
                                        .parseInt(tableID)));
                        data.putExtras(b);
                        startActivity(data);
                    } else {
                        if (GlobalClass.use_foodtruck) {
                            //added by ZYP [21-10-2020]
                            butconfirm_click();
                        } else {
                            Intent data = new Intent(ctx, TableScreenActivity.class);
                            startActivity(data);

                        }

                    }
                    finish();
                } else if (isprint_order == true && title != "Message") {
                    isprint_order = false;
                    Intent data = new Intent(ctx, TableScreenActivity.class);
                    startActivity(data);

                }
            }
        });

        // Showing Alert Message
        alertDialog.show();

        TextView textView = (TextView) alertDialog
                .findViewById(android.R.id.message);
        textView.setTypeface(font);
    }


    public void showAlertDialogBox(Context context, final String title,
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
                if (title == "Message") {
						/*Intent data = new Intent(ctx, OrderTaking.class);
						Bundle b = new Bundle();
						b.putString("TableID", tableID);
						b.putString("TableName", dbhelper
								.getTableNameByTableID(Integer
										.parseInt(tableID)));
						data.putExtras(b);
						startActivity(data);
						finish();*/

                } else {

                }
            }
        });
        // Showing Alert Message
        alertDialog.show();

        TextView textView = (TextView) alertDialog
                .findViewById(android.R.id.message);
        textView.setTypeface(font);
    }


    public void CreatingvalueitemDialog1(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        builder.setView(inflater.inflate(
                R.layout.activity_edit_selectedvalueitembox, null));
        final Dialog dialog = builder.create();
        WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.CENTER;
        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(//GlobalClass.getDPsize(800, this),
                LayoutParams.MATCH_PARENT,
                GlobalClass.getDPsize(600, this));
        dialog.setCancelable(false);
        // dialog.setCanceledOnTouchOutside(true);

        final TextView txtKTV_StartTime = (TextView) dialog
                .findViewById(R.id.txtKTV_StartTime);
        final TextView txtKTV_EndTime = (TextView) dialog
                .findViewById(R.id.txtKTV_EndTime);
        final TextView txtTotalTime = (TextView) dialog
                .findViewById(R.id.txtTotalTime);
        final TextView txtTotalAmount = (TextView) dialog
                .findViewById(R.id.txtTotalAmount);

        final Button butCalculate = (Button) dialog
                .findViewById(R.id.butCalculate);
        final Button butStCurrentGetTime = (Button) dialog
                .findViewById(R.id.butStCurrentGetTime);
        final Button butEndCurrentGetTime = (Button) dialog
                .findViewById(R.id.butEndCurrentGetTime);
        final Button butSaveKTVData = (Button) dialog
                .findViewById(R.id.butSaveKTVData);
        final Button butCancelKTVData = (Button) dialog
                .findViewById(R.id.butCancelKTVData);
        final Button butKTVRemove = (Button) dialog
                .findViewById(R.id.butKTVRemove);

        final LinearLayout itemparent = (LinearLayout) v.getParent();
        final LinearLayout mainparent = (LinearLayout) itemparent.getParent();

        final TextView itemid = (TextView) itemparent.getChildAt(1);
        final TextView valueitem_unit_qty = (TextView) itemparent.getChildAt(2);
        final TextView sr = (TextView) itemparent.getChildAt(4);
        final TextView valueitemAmt = (TextView) itemparent.getChildAt(3);
        final TextView KTV_STTime = (TextView) itemparent.getChildAt(5);
        final TextView KTV_EndTime = (TextView) itemparent.getChildAt(6);

        //	if (sr.getText().toString() != "0")
        //{
        Object[] objTime = new Object[4];
        // objTime = dbhelper.getKTVStartTime(itemid.getTag().toString(),
        // sr.getText().toString());
        objTime = dbhelper.getVaueItemAmt(saleid, sr.getText().toString(),
                itemid.getTag().toString());
        txtKTV_StartTime.setText(objTime[0].toString());
        txtKTV_StartTime.setTypeface(font);
        txtKTV_EndTime.setText(objTime[1].toString());
        txtKTV_EndTime.setTypeface(font);

        String tmpAmount;
        tmpAmount = objTime[3].toString().replace('$', ' ');
        txtTotalTime.setText(objTime[2].toString());
        txtTotalAmount.setText(getCurrencyFormat(tmpAmount));

        valueitem_unit_qty.setText(objTime[4].toString());
//		} else {
//			AlertDialog alertDialog = new AlertDialog.Builder(this).create();
//			alertDialog.setTitle("Warning");
//			alertDialog
//					.setMessage("You must submit that item,not submited yet!");
//			alertDialog.setIcon(0);
//			final Context ctx = this;
//			// Setting OK Button
//			alertDialog.setButton2("OK", new DialogInterface.OnClickListener() {
//				public void onClick(DialogInterface dialog, int which) {
//					// TODO Auto-generated method stub
//				}
//			});
//			/*
//			 * alertDialog.setButton("Cancel", new
//			 * DialogInterface.OnClickListener() { public void
//			 * onClick(DialogInterface dialog, int which) { } });
//			 */
//			alertDialog.show();
//			itemparent.setBackgroundColor(Color.TRANSPARENT);
//			dialog.cancel();
//		}

        butCalculate.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Object[] objCalculateData = new Object[2];
                String sT = txtKTV_StartTime.getText().toString()
                        .replaceAll("\n", "");
                String eT = txtKTV_EndTime.getText().toString()
                        .replaceAll("\n", "");
                String tmpAmount;

                objCalculateData = dbhelper.CalculateData(saleid, sT, eT,
                        itemid.getTag().toString());
                txtTotalTime.setText(objCalculateData[0].toString());
                txtTotalTime.setTypeface(font);

                tmpAmount = objCalculateData[1].toString().replace('$', ' ');
                txtTotalAmount.setText(getCurrencyFormat(tmpAmount));
                txtTotalAmount.setTypeface(font);
            }
        });

        butStCurrentGetTime.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                txtKTV_StartTime.setText(dbhelper.getCurrentDateTime());
                txtKTV_StartTime.setTypeface(font);
            }
        });

        butEndCurrentGetTime.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                txtKTV_EndTime.setText(dbhelper.getCurrentDateTime());
                txtKTV_EndTime.setTypeface(font);
            }
        });

        butSaveKTVData.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String sT = txtKTV_StartTime.getText().toString()
                        .replaceAll("\n", "");
                String eT = txtKTV_EndTime.getText().toString()
                        .replaceAll("\n", "");
                String tmpAmount;
                dbhelper.SaveKTVData(saleid, sT, eT,
                        itemid.getTag().toString(), sr.getText().toString());

                Object[] objRetAmt = new Object[2];
                objRetAmt = dbhelper.getVaueItemAmt(saleid, sr.getText()
                        .toString(), itemid.getTag().toString());
                tmpAmount = objRetAmt[3].toString().replace('$', ' ');
                valueitemAmt.setText(tmpAmount);
                itemparent.setBackgroundColor(Color.TRANSPARENT);
                dialog.dismiss();
            }
        });

        butCancelKTVData.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                itemparent.setBackgroundColor(Color.TRANSPARENT);
                dialog.cancel();
            }
        });

        butKTVRemove.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                dbhelper.RemoveKTVData(saleid, itemid.getTag().toString(), sr
                        .getText().toString());

                ((LinearLayout) mainparent.getParent()).removeView(mainparent);

                LinearLayout valueitemlistlayout = (LinearLayout) findViewById(R.id.valueitem_itemlistlayout);

                final TextView txtDiff = (TextView) findViewById(R.id.txtlineDifference);
                if (valueitemlistlayout.getChildCount() == 0) {
                    txtDiff.setVisibility(View.INVISIBLE);
                }
                dialog.dismiss();
            }
        });

        GlobalClass.ChangeLanguage(
                (LinearLayout) dialog.findViewById(R.id.itemeditlayout), this,
                18, font);
    }


    // added by WaiWL on 12/08/2015
    public void CreatingvalueitemDialog(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        builder.setView(inflater.inflate(
                R.layout.activity_edit_selectedvalueitembox, null));
        final Dialog dialog = builder.create();
        WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.CENTER;

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(
                //GlobalClass.getDPsize(800, this),
                LayoutParams.MATCH_PARENT,
                GlobalClass.getDPsize(600, this));
        dialog.setCancelable(false);
        // dialog.setCanceledOnTouchOutside(true);

        final TextView txtKTV_StartTime = (TextView) dialog
                .findViewById(R.id.txtKTV_StartTime);
        final TextView txtKTV_EndTime = (TextView) dialog
                .findViewById(R.id.txtKTV_EndTime);
        final TextView txtTotalTime = (TextView) dialog
                .findViewById(R.id.txtTotalTime);
        final TextView txtTotalAmount = (TextView) dialog
                .findViewById(R.id.txtTotalAmount);

        final Button butCalculate = (Button) dialog
                .findViewById(R.id.butCalculate);
        final Button butStCurrentGetTime = (Button) dialog
                .findViewById(R.id.butStCurrentGetTime);
        final Button butEndCurrentGetTime = (Button) dialog
                .findViewById(R.id.butEndCurrentGetTime);
        final Button butSaveKTVData = (Button) dialog
                .findViewById(R.id.butSaveKTVData);
        final Button butCancelKTVData = (Button) dialog
                .findViewById(R.id.butCancelKTVData);
        final Button butKTVRemove = (Button) dialog
                .findViewById(R.id.butKTVRemove);

        final LinearLayout itemparent = (LinearLayout) v.getParent();
        final LinearLayout mainparent = (LinearLayout) itemparent.getParent();

        final TextView itemid = (TextView) itemparent.getChildAt(1);
        final TextView valueitem_unit_qty = (TextView) itemparent.getChildAt(2);
        final TextView sr = (TextView) itemparent.getChildAt(4);
        final TextView valueitemAmt = (TextView) itemparent.getChildAt(3);
        final TextView KTV_STTime = (TextView) itemparent.getChildAt(5);
        final TextView KTV_EndTime = (TextView) itemparent.getChildAt(6);

        if (sr.getText().toString() != "0") {
            Object[] objTime = new Object[4];
            // objTime = dbhelper.getKTVStartTime(itemid.getTag().toString(),
            // sr.getText().toString());
            objTime = dbhelper.getVaueItemAmt(saleid, sr.getText().toString(),
                    itemid.getTag().toString());
            txtKTV_StartTime.setText(objTime[0].toString());
            txtKTV_StartTime.setTypeface(font);
            txtKTV_EndTime.setText(objTime[1].toString());
            txtKTV_EndTime.setTypeface(font);

            String tmpAmount;
            tmpAmount = objTime[3].toString().replace('$', ' ');
            txtTotalTime.setText(objTime[2].toString());
            //txtTotalAmount.setText(getCurrencyFormat(tmpAmount));
            txtTotalAmount.setText(tmpAmount);

            valueitem_unit_qty.setText(objTime[4].toString());
        } else {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Warning!");
            alertDialog.setMessage("You must submit that item, not submited yet!");
            alertDialog.setIcon(0);
            final Context ctx = this;
            // Setting OK Button
            alertDialog.setButton2("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub
                }
            });
            /*
             * alertDialog.setButton("Cancel", new
             * DialogInterface.OnClickListener() { public void
             * onClick(DialogInterface dialog, int which) { } });
             */
            alertDialog.show();
            itemparent.setBackgroundColor(Color.TRANSPARENT);
            dialog.cancel();
        }

        butCalculate.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Object[] objCalculateData = new Object[2];
                String sT = txtKTV_StartTime.getText().toString()
                        .replaceAll("\n", "");
                String eT = txtKTV_EndTime.getText().toString()
                        .replaceAll("\n", "");
                String tmpAmount;

                objCalculateData = dbhelper.CalculateData(saleid, sT, eT,
                        itemid.getTag().toString());
                txtTotalTime.setText(objCalculateData[0].toString());
                txtTotalTime.setTypeface(font);

                tmpAmount = objCalculateData[1].toString().replace('$', ' ');
                //txtTotalAmount.setText(getCurrencyFormat(tmpAmount));
                txtTotalAmount.setText(tmpAmount);
                txtTotalAmount.setTypeface(font);
            }
        });

        butStCurrentGetTime.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                txtKTV_StartTime.setText(dbhelper.getCurrentDateTime());
                txtKTV_StartTime.setTypeface(font);
            }
        });

        butEndCurrentGetTime.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                txtKTV_EndTime.setText(dbhelper.getCurrentDateTime());
                txtKTV_EndTime.setTypeface(font);
            }
        });

        butSaveKTVData.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String sT = txtKTV_StartTime.getText().toString()
                        .replaceAll("\n", "");
                String eT = txtKTV_EndTime.getText().toString()
                        .replaceAll("\n", "");
                String tmpAmount;
                dbhelper.SaveKTVData(saleid, sT, eT,
                        itemid.getTag().toString(), sr.getText().toString());

                Object[] objRetAmt = new Object[2];
                objRetAmt = dbhelper.getVaueItemAmt(saleid, sr.getText()
                        .toString(), itemid.getTag().toString());
                tmpAmount = objRetAmt[3].toString().replace('$', ' ');
                valueitemAmt.setText(tmpAmount);
                itemparent.setBackgroundColor(Color.TRANSPARENT);
                dialog.dismiss();
            }
        });

        butCancelKTVData.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                itemparent.setBackgroundColor(Color.TRANSPARENT);
                dialog.cancel();
            }
        });

        butKTVRemove.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
/*				dbhelper.RemoveKTVData(saleid, itemid.getTag().toString(), sr
						.getText().toString());*/

                ((LinearLayout) mainparent.getParent()).removeView(mainparent);

                LinearLayout valueitemlistlayout = (LinearLayout) findViewById(R.id.valueitem_itemlistlayout);

                final TextView txtDiff = (TextView) findViewById(R.id.txtlineDifference);
                if (valueitemlistlayout.getChildCount() == 0) {
                    txtDiff.setVisibility(View.INVISIBLE);
                }
                dialog.dismiss();
            }
        });

        GlobalClass.ChangeLanguage(
                (LinearLayout) dialog.findViewById(R.id.itemeditlayout), this,
                18, font);
    }

    public void CreatingedititemDialog(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);

        clickChecked = false;
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.activity_edit_selecteditembox, null));

        final Dialog dialog = builder.create();
        WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.CENTER;
        // dialog.setCanceledOnTouchOutside(true);
        if (!dialog.isShowing()) {
            dialog.show();
        }

        Window window = dialog.getWindow();
        window.setLayout(GlobalClass.getDPsize(400, this),
                //GlobalClass.getDPsize(200, this));
                LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        final TextView txtqty = (TextView) dialog.findViewById(R.id.txtqty);
        final TextView txtitem = (TextView) dialog.findViewById(R.id.txtitemname);
        final TextView txtdialogsr = (TextView) dialog.findViewById(R.id.txtsr);
        //final TextView txtremark = (TextView) dialog.findViewById(R.id.txtremark);
        txtremark = (TextView) dialog.findViewById(R.id.txtremark); //modified by MPPA on 02-09-2021
        final CheckBox chktaway = (CheckBox) dialog.findViewById(R.id.chktakeaway);
        final CheckBox chkisFinishedItem = (CheckBox) dialog.findViewById(R.id.chkFinish);// added by WaiWL on 23/07/2015

        final LinearLayout remark_lay = (LinearLayout) dialog.findViewById(R.id.remarklay); //added by EKK on 28-02-2020

        final LinearLayout modifierlayout = (LinearLayout) dialog.findViewById(R.id.modifierlayout);
        final LinearLayout itemeditlayout = (LinearLayout) dialog.findViewById(R.id.itemeditlayout);
        final LinearLayout selectedmodifieditemlist = (LinearLayout) dialog.findViewById(R.id.selectedmodifierItemlist);
        final LinearLayout modifieditemlist = (LinearLayout) dialog.findViewById(R.id.modifieditemlist);
        final Button butchangesetitem = (Button) dialog.findViewById(R.id.butChangeSetItem);

        LinearLayout layoutfire = (LinearLayout) dialog.findViewById(R.id.layoutfire);

        final LinearLayout itemparent = (LinearLayout) v.getParent();
        final LinearLayout mainparent = (LinearLayout) itemparent.getParent();
        final Button butAddRemark = (Button) dialog.findViewById(R.id.butaddRemark);
        TextView txtFireSrdialog = (TextView) dialog.findViewById(R.id.txtfiresr);
        TextView lblFireSrdialog = (TextView) dialog.findViewById(R.id.lblFiresr);

        final TextView txtsr = (TextView) itemparent.getChildAt(0);// Tag of
        // txtsr
        // /"P-Printed","N-NotPrinted","C-ItemCancel","Q-Change Qty"
        final TextView itemname = (TextView) itemparent.getChildAt(1);
        final TextView qty = (TextView) itemparent.getChildAt(2);
        final TextView amount = (TextView) itemparent.getChildAt(3);
        final TextView txtunit = (TextView) itemparent.getChildAt(4);
        final TextView txtaway = (TextView) itemparent.getChildAt(5);
        final TextView txtisFinishedItem = (TextView) itemparent.getChildAt(7);// added


        final TextView txtfire = (TextView) itemparent.getChildAt(8);
        final TextView txtfired = (TextView) itemparent.getChildAt(9);

        codeforRemark = itemname.getTag().toString();

        if ((txtsr.getTag().toString().equals("P") || txtsr.getTag().toString().equals("Q")) && (dbhelper.getuse_Fire().equals("Y"))) {

            //txtremark.setVisibility(View.GONE);
            remark_lay.setVisibility(View.GONE); //added by EKK on  28-02-2020
            butAddRemark.setVisibility(View.GONE);
            if (txtfire.getText().toString().equals("null") || txtfired.getText().toString().equals("null")) {
                txtFireSrdialog.setEnabled(true);
                txtfire.setTag("N");

                if (txtfire.getText().toString().equals("null")) {
                    txtfire.setText("");
                }


            } else {
                txtFireSrdialog.setEnabled(false);
            }

            txtFireSrdialog.setText(txtfire.getText());//aungmyotun
        } else {
            txtFireSrdialog.setVisibility(View.GONE);
            lblFireSrdialog.setVisibility(View.GONE);
            layoutfire.setVisibility(View.GONE);
        }

        if (txtsr.getTag().toString().equals("P")) {
            if (dbhelper.getHideCancelItemFlag().equals("Y")) {
                ((Button) dialog.findViewById(R.id.butremove)).setVisibility(View.GONE);
                ((Button) dialog.findViewById(R.id.butplus)).setVisibility(View.GONE);
                ((Button) dialog.findViewById(R.id.butminus)).setVisibility(View.GONE);
            } else {

                if (dbhelper.CheckPrintBill(new DatabaseHelper(ctx).getServiceURL(), saleid)
                        && dbhelper.Allow_edit_after_insert(dbhelper.getwaiterid()) == false) {
                    ((Button) dialog.findViewById(R.id.butremove)).setVisibility(View.GONE);
                    ((Button) dialog.findViewById(R.id.butplus)).setVisibility(View.GONE);
                    ((Button) dialog.findViewById(R.id.butminus)).setVisibility(View.GONE);
                } else {
                    ((Button) dialog.findViewById(R.id.butremove)).setText("Cancel Item");
                }
            }

            if (GlobalClass.use_foodtruck) {
                window.setLayout(GlobalClass.getDPsize(400, this),
                        GlobalClass.getDPsize(300, this));
                //LayoutParams.WRAP_CONTENT);
                ((LinearLayout) dialog.findViewById(R.id.layout_modify)).setVisibility(View.GONE);
                ((Button) dialog.findViewById(R.id.butaddmodifier)).setVisibility(View.GONE);
                butchangesetitem.setVisibility(View.GONE);
            }
        }

        if (GlobalClass.use_foodtruck) {
            layoutfire.setVisibility(View.GONE);
            chktaway.setVisibility(View.INVISIBLE);
            chkisFinishedItem.setVisibility(View.INVISIBLE);
            selectedmodifieditemlist.setVisibility(View.GONE);
        }

        // on
        // 23/07/2015
        String modifieritemstatus;
        if (Boolean.parseBoolean(dbhelper.getItemsbyitemid(itemname.getTag().toString()).getissetmenu()) == false) {
            butchangesetitem.setVisibility(View.GONE);
            modifieritemstatus = "modifer";
        } else {
            mainCode = itemname.getTag().toString();//Added by KLM for changing set menu item 13062022
            modifieritemstatus = "setmenuitem";
        }

        if (txtsr.getTag().toString().equals("C") || txtsr.getTag().toString().equals("CP")) {
            itemparent.setBackgroundColor(Color.TRANSPARENT);
            dialog.dismiss();
        }
        txtdialogsr.setText(txtsr.getText());
        txtdialogsr.setTag(txtsr.getTag());

        if (txtaway.getTag().equals(true)) {
            chktaway.setChecked(true);
        }

        // added by WaiWL on 23/07/2015
        if (Boolean.valueOf(txtisFinishedItem.getTag().toString())) {
            chkisFinishedItem.setChecked(true);
        }
        // ///////////

        final List<SelectedItemModifierObj> modifierobjlist = new ArrayList<SelectedItemModifierObj>();

        if (mainparent.getChildCount() > 1) // bind remark from list
        {
            LinearLayout lastlayout = (LinearLayout) mainparent
                    .getChildAt(mainparent.getChildCount() - 1);
            if (((TextView) lastlayout
                    .getChildAt(lastlayout.getChildCount() - 1)).getText()
                    .toString().equals("remark")) {
                String remark = ((TextView) lastlayout.getChildAt(1)).getText()
                        .toString();
                remark = remark.replace("(", "");
                remark = remark.replace(")", "");
                remark = remark.trim();
                txtremark.setText(remark);
            }
            for (int i = 1; i < mainparent.getChildCount(); i++) {
                LinearLayout layout = (LinearLayout) mainparent.getChildAt(i);
                if (!((TextView) layout
                        .getChildAt(lastlayout.getChildCount() - 1)).getText()
                        .toString().equals("remark")) {
                    SelectedItemModifierObj modifierobj = new SelectedItemModifierObj();
                    modifierobj.setitemid(((TextView) layout.getChildAt(1))
                            .getTag().toString());
                    modifierobj.setname(((TextView) layout.getChildAt(1))
                            .getText().toString().replace("*", "").trim());
                    modifierobj.setqty(((TextView) layout.getChildAt(2))
                            .getText().toString());
                    modifierobj.setstatus(modifieritemstatus);

                    //	modifierobj.setsr(Integer.valueOf(((TextView) layout.getChildAt(6)).getText().toString()));
                    //	String textsetmenusr=((TextView) layout.getChildAt(6)).getTag().toString();
                    modifierobjlist.add(modifierobj);
                }
            }

            BindSelectedModifiertoedititemlist(dialog, modifierobjlist);
        }
        LayoutParams contentParams = (LinearLayout.LayoutParams) modifierlayout.getLayoutParams();
        contentParams.width = 0;
        modifierlayout.setLayoutParams(contentParams);

        txtqty.setText(qty.getTag().toString().replace(".0", "").trim());
        txtitem.setTypeface(font);
        txtitem.setText(itemname.getText().toString());
        txtitem.setTag(itemname.getTag().toString());

        final List<UnitCodeObj> unitcodeobjlist = dbhelper
                .getunitcodebyitemid(itemname.getTag().toString());

        final Button btnfirst = (Button) dialog.findViewById(R.id.btnfirst);
        final Button btnsecond = (Button) dialog.findViewById(R.id.btnsecond);
        final Button btnthird = (Button) dialog.findViewById(R.id.btnthird);

        EditText txtqtyitem = (EditText) dialog.findViewById(R.id.txtqty);
        if (dbhelper.Allow_itemcancel(dbhelper.getwaiterid()) == false && txtsr.getTag().toString().equals("P")) {
            txtqtyitem.setInputType(InputType.TYPE_NULL);
        }
        //Added by KNO for printed change qty
        if (txtsr.getTag().toString().equals("P")) {
            orgqty = Double.toString(Double.parseDouble(txtqty.getText().toString()));
        }
        // final String Itemname =
        // dbhelper.getItemNamebyitemid(txtitem.getTag().toString());

        String unit = txtunit.getTag().toString();

        if (unitcodeobjlist.size() > 0 && use_unit == true) {
            btnfirst.setVisibility(View.VISIBLE);
            btnfirst.setText(unitcodeobjlist.get(0).getshortname());
            btnfirst.setTag(unitcodeobjlist.get(0).getunit());
            if (unit.equals(unitcodeobjlist.get(0).getunit())) {
                btnfirst.setBackgroundResource(R.drawable.selectedunitbutton);
            }

            btnfirst.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    btnfirst.setBackgroundResource(R.drawable.selectedunitbutton);
                    btnsecond.setBackgroundResource(R.drawable.unitbutton);
                    btnthird.setBackgroundResource(R.drawable.unitbutton);
                    // txtitem.setText(Itemname + "("+ btnfirst.getText()+")");
                    // itemname.setText(Itemname + "("+ btnfirst.getText()+")");
                    qty.setText(qty.getTag() + " " + btnfirst.getText());

                    String price = unitcodeobjlist.get(0).getsaleprice();
                    if (price.equals("null")) {
                        price = "0";
                    }

                    amount.setText(getCurrencyFormat(Double.toString(Double.parseDouble(price)
                            * Double.parseDouble(qty.getTag().toString()))));
                    amount.setTag(Double.toString(Double.parseDouble(price)
                            * Double.parseDouble(qty.getTag().toString())));
                    txtunit.setText(btnfirst.getText());
                    txtunit.setTag(btnfirst.getTag().toString());

                    current_unit = 1;//whm MDY1-20023

                }
            });
        }

        if (unitcodeobjlist.size() > 1 && use_unit == true) {
            btnsecond.setVisibility(View.VISIBLE);
            btnsecond.setText(unitcodeobjlist.get(1).getshortname());
            btnsecond.setTag(unitcodeobjlist.get(1).getunit());
            if (unit.equals(unitcodeobjlist.get(1).getunit())) {
                btnsecond.setBackgroundResource(R.drawable.selectedunitbutton);
            }
            btnsecond.setOnClickListener(new OnClickListener() {

                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    btnsecond.setBackgroundResource(R.drawable.selectedunitbutton);
                    btnfirst.setBackgroundResource(R.drawable.unitbutton);
                    btnthird.setBackgroundResource(R.drawable.unitbutton);
                    // txtitem.setText(Itemname + "("+ btnsecond.getText()+")");
                    // itemname.setText(Itemname + "("+
                    // btnsecond.getText()+")");
                    qty.setText(qty.getTag() + " " + btnsecond.getText());

                    String price = unitcodeobjlist.get(1).getsaleprice();
                    if (price.equals("null")) {
                        price = "0";
                    }

                    amount.setText(getCurrencyFormat(Double.toString(Double.parseDouble(price)
                            * Double.parseDouble(qty.getTag().toString()))));
                    amount.setTag(Double.toString(Double.parseDouble(price)
                            * Double.parseDouble(qty.getTag().toString())));
                    txtunit.setText(btnsecond.getText());
                    txtunit.setTag(btnsecond.getTag().toString());

                    current_unit = 2; //whm MDY1-20023
                }
            });
        }

        if (unitcodeobjlist.size() > 2 && use_unit == true) {
            btnthird.setVisibility(View.VISIBLE);
            btnthird.setText(unitcodeobjlist.get(2).getshortname());
            btnthird.setTag(unitcodeobjlist.get(2).getunit());
            if (unit.equals(unitcodeobjlist.get(2).getunit())) {
                btnthird.setBackgroundResource(R.drawable.selectedunitbutton);
            }

            btnthird.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    btnthird.setBackgroundResource(R.drawable.selectedunitbutton);
                    btnfirst.setBackgroundResource(R.drawable.unitbutton);
                    btnsecond.setBackgroundResource(R.drawable.unitbutton);
                    // txtitem.setText(Itemname + "("+ btnthird.getText()+")");
                    // itemname.setText(Itemname + "("+ btnthird.getText()+")");
                    qty.setText(qty.getTag() + " " + btnthird.getText());
                    String price = unitcodeobjlist.get(2).getsaleprice();
                    if (price.equals("null")) {
                        price = "0";
                    }
                    amount.setText(getCurrencyFormat(Double.toString(Double.parseDouble(price)
                            * Double.parseDouble(qty.getTag().toString()))));
                    amount.setTag(Double.toString(Double.parseDouble(price)
                            * Double.parseDouble(qty.getTag().toString())));
                    txtunit.setText(btnthird.getText());
                    txtunit.setTag(btnthird.getTag().toString());

                    current_unit = 3;//whm MDY1-20023
                }
            });
        }

        Button butaddRemark = (Button) dialog.findViewById(R.id.butaddRemark);

        butaddRemark.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                //dialog.dismiss();
                ItemRemark(txtremark);

            }
        });

/*Commented by Arkar Moe on [22/02/2017]
 * 		TextView butmodifier = (TextView) dialog.findViewById(R.id.butmodifier);
		butmodifier.setPaintFlags(butmodifier.getPaintFlags()
				| Paint.UNDERLINE_TEXT_FLAG);*/

        TextView butremarkclear = (TextView) dialog.findViewById(R.id.butremarkclear);
        butremarkclear.setPaintFlags(butremarkclear.getPaintFlags()
                | Paint.UNDERLINE_TEXT_FLAG);

        if (dbhelper.getusemonitorinterface().equals("Y") || GlobalClass.use_foodtruck) {
            if (txtsr.getTag().toString().equals("P")) {
                ((Button) dialog.findViewById(R.id.butplus)).setEnabled(false);
                ((Button) dialog.findViewById(R.id.butminus)).setEnabled(false);
                txtqtyitem.setInputType(InputType.TYPE_NULL); //added WHM [2020-01-03]
            }
        }
        //added by ZYP [15/09/2020] for sold out item qty change
        if (dbhelper.isSoldOutItem(itemname.getTag().toString())) {
            //  ((Button) dialog.findViewById(R.id.butplus)).setVisibility(View.GONE); //commeted WHM [2020-10-19]
            //  ((Button) dialog.findViewById(R.id.butminus)).setVisibility(View.GONE);//commented WHM [2020-10-19]
            txtqtyitem.setInputType(InputType.TYPE_NULL);

            txtqtyitem.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    showAlertDialog(OrderTaking.this, "Warning!", "This item is sold out!\nCannot change the Qty.", false);
                }
            });

        }


        //OK Modifier
        final Context ctx = this;
        ((Button) dialog.findViewById(R.id.butOK))
                .setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        //Added by KNO for printed change qty
                        if (txtsr.getTag().toString().equals("P") && !orgqty.equals(qtytmp)) {
                            if (dbhelper.getAppSetting("QtyChangeKitchenPrint").equals("N")) {
                                showAlertDialog(ctx, "Warning", "You need to print Modify Qty ", false);
                                dialog.dismiss();
                            } else {
                                final AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                                builder.setMessage("Qty Changed! Print to Kitchen/Bar? ");
                                builder.setTitle("Warning!");
                                builder.setPositiveButton("Ok",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface builder, int which) {
                                                reprint = true;
                                                clickChecked = true;
                                                TextView txtsr = (TextView) itemparent.getChildAt(0);
                                                TextView txtitem = (TextView) itemparent.getChildAt(1);
                                                TextView qty = (TextView) itemparent.getChildAt(2);
                                                TextView txtamount = (TextView) itemparent.getChildAt(3);
                                                TextView txtunit = (TextView) itemparent.getChildAt(4);
                                                TextView txttakeaway = (TextView) itemparent.getChildAt(5);
                                                TextView txtrealsr = (TextView) itemparent.getChildAt(6);
                                                TextView txtisFinishedItem = (TextView) itemparent.getChildAt(7);// added by WaiWL on 23/07/2015
                                                TextView txtfire = (TextView) itemparent.getChildAt(8);
                                                TextView txtfiredialog = (TextView) dialog.findViewById(R.id.txtfiresr);
                                                CheckBox chktakeaway = (CheckBox) dialog.findViewById(R.id.chktakeaway);
                                                CheckBox chkisFinishedItem = (CheckBox) dialog.findViewById(R.id.chkFinish);// added by WaiWL// on 23/07/2015
                                                TextView txtqty = (TextView) dialog.findViewById(R.id.txtqty);

                                                if (txtqty.getText().toString().equals("0") || txtqty.getText().toString().equals(""))//added WHM [2020-02-05] MDY1-20023
                                                {
                                                    txtqty.setText("1");
                                                }
                                                //Qty Decimal Place
                                                String trimString = ".";
                                                String qtydecimal = dbhelper.getqty_decimal_places();
                                                for (int i = 0; i < Integer.valueOf(qtydecimal); i++) {
                                                    trimString = trimString + "0";
                                                }

                                                //Price Decimal Place
                                                String pricetrim = ".";
                                                String pricedecimal = dbhelper.getprice_decimal_places();
                                                for (int i = 0; i < Integer.valueOf(pricedecimal); i++) {
                                                    pricetrim = pricetrim + "0";
                                                }
                                                //
//						qty.setTag(dbhelper.qtyroundto(txtqty.getText().toString()).replace(".00", "").trim());
                                                qty.setTag(dbhelper.qtyroundto(txtqty.getText().toString()).replace(trimString, "").trim());
                                                txtfire.setText(txtfiredialog.getText().toString());//added by aungmyotun

                                                if (use_unit && !txtunit.getTag().toString().equals(" ")) {
                                                    List<UnitCodeObj> unitcodelist = dbhelper.getunitcodebyunit(
                                                            txtitem.getTag().toString(), txtunit.getTag().toString());
                                                    qty.setText(qty.getTag().toString() + " " + unitcodelist.get(0).getshortname());

                                                    Double amount = Double.parseDouble(qty.getTag().toString()) *
                                                            Double.parseDouble(unitcodelist.get(0).getsaleprice().equals("null") ?
                                                                    "0" : unitcodelist.get(0).getsaleprice()
                                                            );
                                                    txtamount.setText(getCurrencyFormat(Double.toString(amount))); //comment for price_decimal place
                                                    txtamount.setTag(Double.toString(amount));                                      //added by ZYP for price with modifier
                                                    //txtamount.setText(getCurrencyFormat(dbhelper.PriceRoundTo(Double.toString(amount)).replace(pricetrim, "").trim()));

                                                } else {
                                                    qty.setText(qty.getTag().toString() + "");

                                                    String defcurrency = dbhelper.getDefCurrency(); //added by ZYP 24-08-2020
                                                    org_curr = dbhelper.getsaleCurrbyitemid(txtitem.getTag().toString());
                                                    //modified by ZYP [17/11/2020] offline exg rate
                                                    String defexgrate, orgExgRate;

                                                    if (GlobalClass.tmpOffline || GlobalClass.use_foodtruck) {
                                                        defexgrate = dbhelper.getExgRateByCurrency(defcurrency);
                                                        orgExgRate = dbhelper.getExgRateByCurrency(org_curr);
                                                    } else {
                                                        defexgrate = dbhelper.LoadExgRate(dbhelper.getServiceURL(), defcurrency);
                                                        orgExgRate = dbhelper.LoadExgRate(dbhelper.getServiceURL(), org_curr);
                                                    }

                                                    double price = (Double.parseDouble(dbhelper.getItempricebyitemid(txtitem.getTag().toString())) * Double.parseDouble(orgExgRate) / Double.parseDouble(defexgrate));

                                                    Double amount = Double.parseDouble(dbhelper.qtyroundto(qty.getTag().toString())) * price;
                                                    //Double amount = Integer.parseInt(qty.getTag().toString())* Double.parseDouble(txtamount.getText().toString());
                                                    //txtamount.setText(getCurrencyFormat(dbhelper.PriceRoundTo(Double.toString(amount)).replace(pricetrim, "").trim()));
                                                    txtamount.setText(getCurrencyFormat(Double.toString(amount)));//added by ZYP currencyformat
                                                    txtamount.setTag(Double.toString(amount));            //added by ZYP for price with modifier

                                                    //comment for price_decimal_place
                                                }

                                                List<SelectedItemModifierObj> selectedmodifierobjlist = new ArrayList<SelectedItemModifierObj>();
                                                // LinearLayout selectedlinearlayout =
                                                // (LinearLayout)dialog.findViewById(R.id.selectedmodifierItemlist);
                                                for (int i = 0; i < selectedmodifieditemlist.getChildCount(); i++) {
                                                    SelectedItemModifierObj selectedmodifierobj = new SelectedItemModifierObj();

                                                    LinearLayout layout = (LinearLayout) selectedmodifieditemlist.getChildAt(i);
                                                    TextView txtmodifiername = (TextView) layout.getChildAt(0);
                                                    TextView txtmodifierqty = (TextView) layout.getChildAt(2);
                                                    selectedmodifierobj.setitemsr(txtsr.getText().toString());
                                                    selectedmodifierobj.setitemid(txtmodifiername.getTag().toString());
                                                    selectedmodifierobj.setname(txtmodifiername.getText().toString());
                                                    selectedmodifierobj.setqty(txtmodifierqty.getText().toString());
                                                    selectedmodifierobj.set_maincode(txtitem.getTag().toString());

                                                    TextView txtModifierStatus = (TextView) layout.getChildAt(5);
                                                    String price = "";
                                                    String max_price = "";
                                                    if (txtModifierStatus.getText().equals("setmenuitem")) {
                                                        ItemsObj itemobj = dbhelper.getItemsbyitemid(layout.getChildAt(0).getTag().toString());
                                                        price = itemobj.getsale_price();
                                                        selectedmodifierobj.setstatus("setmenuitem");
                                                        //added by MPPA for setmenu item
                                                        if (dbhelper.GetSetMenuOrgQty() == true) {
                                                            txtmodifierqty.setText(txtqty.getText().toString());
                                                            selectedmodifierobj.setqty(txtqty.getText().toString());
                                                        }

                                                        if (txtsr.getTag().toString().equals("P")) {

                                                        }
                                                        max_price = dbhelper.getSetMenuMaxPricebyitemid(layout.getChildAt(0).getTag().toString(), txtitem.getTag().toString());
                                                    } else {
                                                        price = dbhelper.getModifierpricebyitemid(txtmodifiername.getTag().toString());
                                                        selectedmodifierobj.setstatus("modifier");
                                                        max_price = "0";
                                                    }


                                                    Double modifieramount = ((Double.parseDouble(txtmodifierqty.getText().toString())) *
                                                            Double.parseDouble(price == "" ? "0" : price));
                                                    selectedmodifierobj.setamount(Double.toString(modifieramount));
                                                    selectedmodifierobj.set_max_price(max_price);
                                                    selectedmodifierobjlist.add(selectedmodifierobj);


                                                }

                                                // EditText txtremark =
                                                // (EditText)dialog.findViewById(R.id.txtremark);
                                                if (!txtremark.getText().toString().equals("")) {
                                                    SelectedItemModifierObj selectedmodifierobj = new SelectedItemModifierObj();
                                                    selectedmodifierobj.setstatus("remark");
                                                    selectedmodifierobj.setname(txtremark.getText().toString());
                                                    selectedmodifierobjlist.add(selectedmodifierobj);
                                                }

                                                //if(!txtsr.getTag().toString().equals("P")){

                                                BindModifieritemtolist(itemparent, selectedmodifierobjlist, true);
                                                //}

                                                if (chktakeaway.isChecked()) {
                                                    txttakeaway.setTag(true);
                                                    itemparent.setBackgroundColor(Color.parseColor("#a10099"));
                                                    //itemparent.setBackgroundColor(Color.parseColor("#dddbdb"));
                                                    if (dbhelper.getchangeparcelprice() == true && (current_unit == 0 || current_unit == 1))  //modified WHM [2020-02-05] MDY1-20023
                                                    {
                                                        String code = itemname.getTag().toString();
                                                        String amt = dbhelper.getpriceforParcel(code);
                                                        if (!(Double.parseDouble(amt) > 0)) {
                                                            amt = dbhelper.getItempricebyitemid(code);
                                                        }
                                                        Double amount = Double.parseDouble(dbhelper.qtyroundto(qty.getTag().toString())) * Double.parseDouble(amt);
                                                        txtamount.setText(getCurrencyFormat(Double.toString(amount)));
                                                        //txtamount.setText(getCurrencyFormat(dbhelper.PriceRoundTo(Double.toString(amount)).replace(pricetrim, "").trim()));

                                                    } else //added WHM [2020-02-05] MDY1-20023
                                                    {
                                                        current_unit = 0;
                                                    }

                                                } else {
                                                    txttakeaway.setTag(false);
                                                    // itemparent.setBackgroundColor(Color.TRANSPARENT);
                                                }

                                                // added by WaiWL on 23/07/2015
                                                if (chkisFinishedItem.isChecked()) {
                                                    txtisFinishedItem.setTag(true);
                                                    itemparent.setBackgroundColor(Color
                                                            .parseColor("#dddbdb"));
                                                    dbhelper.UpdateFinishedItem(dbhelper.getServiceURL(), saleid,
                                                            txtsr.getText().toString().replace(".", "").trim(),
                                                            txtisFinishedItem.getTag().equals(true) ? "1" : "0");
                                                } else {
                                                    txtisFinishedItem.setTag(false);
                                                    if (!GlobalClass.use_foodtruck) {
                                                        dbhelper.UpdateFinishedItem(dbhelper.getServiceURL(), saleid,
                                                                txtsr.getText().toString().replace(".", "").trim(),
                                                                txtisFinishedItem.getTag().equals(true) ? "1" : "0");
                                                    }

                                                }
                                                ////////////

//                        if (chktakeaway.isChecked()) {
//                            txttakeaway.setTag(true);
//                            itemparent.setBackgroundColor(Color.parseColor("#a10099"));
//                        }

                                                if (txtsr.getTag().toString().equals("P") || txtsr.getTag().toString().equals("Q")) {

                                                    String oldqty = dbhelper.getSaleItemQtyBySaleIDandSR(saleid,
                                                            txtrealsr.getText().toString()).get(0).getqty();

                                                    LinearLayout mainparentlayout = ((LinearLayout) (txtsr.getParent().getParent()));

                                                    if (Double.parseDouble(oldqty) != Double.parseDouble(txtqty.getText().toString())) {
                                                        txtsr.setTag("Q");

                                                        if (mainparentlayout.getChildCount() > 1) {
                                                            for (int i = 1; i < mainparentlayout.getChildCount(); i++) {

                                                                ((LinearLayout) mainparentlayout.getChildAt(i)).getChildAt(0).setTag("Q");
                                                            }
                                                        }
                                                    } else {
//                                txtsr.setTag("P");
//                                if (mainparentlayout.getChildCount() > 1) {
//                                    for (int i = 1; i < mainparentlayout.getChildCount(); i++) {
//
//                                        ((LinearLayout) mainparentlayout.getChildAt(i)).getChildAt(0).setTag("Q");
//                                    }
//                                }
                                                    }
                                                }

                                                if (GlobalClass.use_foodtruck) {
                                                    Submit_Voucher(0);
                                                }

                                                itemparent.setBackgroundColor(Color.TRANSPARENT);
                                                dialog.dismiss();
                                                CalculateTotal();
                                                salesmen_dialog();
                                            }
                                        });
                                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });
                                builder.create().show();
                            }
                        } else {
                            clickChecked = true;
                            TextView txtsr = (TextView) itemparent.getChildAt(0);
                            TextView txtitem = (TextView) itemparent.getChildAt(1);
                            TextView qty = (TextView) itemparent.getChildAt(2);
                            TextView txtamount = (TextView) itemparent.getChildAt(3);
                            TextView txtunit = (TextView) itemparent.getChildAt(4);
                            TextView txttakeaway = (TextView) itemparent.getChildAt(5);
                            TextView txtrealsr = (TextView) itemparent.getChildAt(6);
                            TextView txtisFinishedItem = (TextView) itemparent.getChildAt(7);// added by WaiWL on 23/07/2015
                            TextView txtfire = (TextView) itemparent.getChildAt(8);
                            TextView txtfiredialog = (TextView) dialog.findViewById(R.id.txtfiresr);
                            CheckBox chktakeaway = (CheckBox) dialog.findViewById(R.id.chktakeaway);
                            CheckBox chkisFinishedItem = (CheckBox) dialog.findViewById(R.id.chkFinish);// added by WaiWL// on 23/07/2015
                            TextView txtqty = (TextView) dialog.findViewById(R.id.txtqty);


                            if (txtqty.getText().toString().equals("0") || txtqty.getText().toString().equals(""))//added WHM [2020-02-05] MDY1-20023
                            {
                                txtqty.setText("1");
                            }
                            //Qty Decimal Place
                            String trimString = ".";
                            String qtydecimal = dbhelper.getqty_decimal_places();
                            for (int i = 0; i < Integer.valueOf(qtydecimal); i++) {
                                trimString = trimString + "0";
                            }

                            //Price Decimal Place
                            String pricetrim = ".";
                            String pricedecimal = dbhelper.getprice_decimal_places();
                            for (int i = 0; i < Integer.valueOf(pricedecimal); i++) {
                                pricetrim = pricetrim + "0";
                            }
                            //
//						qty.setTag(dbhelper.qtyroundto(txtqty.getText().toString()).replace(".00", "").trim());
                            qty.setTag(dbhelper.qtyroundto(txtqty.getText().toString()).replace(trimString, "").trim());
                            txtfire.setText(txtfiredialog.getText().toString());//added by aungmyotun

                            if (use_unit && !txtunit.getTag().toString().equals(" ")) {
                                List<UnitCodeObj> unitcodelist = dbhelper.getunitcodebyunit(
                                        txtitem.getTag().toString(), txtunit.getTag().toString());
                                qty.setText(qty.getTag().toString() + " " + unitcodelist.get(0).getshortname());

                                Double amount = Double.parseDouble(qty.getTag().toString()) *
                                        Double.parseDouble(unitcodelist.get(0).getsaleprice().equals("null") ?
                                                "0" : unitcodelist.get(0).getsaleprice()
                                        );
                                txtamount.setText(getCurrencyFormat(Double.toString(amount))); //comment for price_decimal place
                                txtamount.setTag(Double.toString(amount));                                      //added by ZYP for price with modifier
                                //txtamount.setText(getCurrencyFormat(dbhelper.PriceRoundTo(Double.toString(amount)).replace(pricetrim, "").trim()));

                            } else {
                                qty.setText(qty.getTag().toString() + "");

                                String defcurrency = dbhelper.getDefCurrency(); //added by ZYP 24-08-2020
                                org_curr = dbhelper.getsaleCurrbyitemid(txtitem.getTag().toString());
                                //modified by ZYP [17/11/2020] offline exg rate
                                String defexgrate, orgExgRate;

                                if (GlobalClass.tmpOffline || GlobalClass.use_foodtruck) {
                                    defexgrate = dbhelper.getExgRateByCurrency(defcurrency);
                                    orgExgRate = dbhelper.getExgRateByCurrency(org_curr);
                                } else {
                                    defexgrate = dbhelper.LoadExgRate(dbhelper.getServiceURL(), defcurrency);
                                    orgExgRate = dbhelper.LoadExgRate(dbhelper.getServiceURL(), org_curr);
                                }

                                double price = (Double.parseDouble(dbhelper.getItempricebyitemid(txtitem.getTag().toString())) * Double.parseDouble(orgExgRate) / Double.parseDouble(defexgrate));

                                Double amount = Double.parseDouble(dbhelper.qtyroundto(qty.getTag().toString())) * price;
                                //Double amount = Integer.parseInt(qty.getTag().toString())* Double.parseDouble(txtamount.getText().toString());
                                //txtamount.setText(getCurrencyFormat(dbhelper.PriceRoundTo(Double.toString(amount)).replace(pricetrim, "").trim()));
                                txtamount.setText(getCurrencyFormat(Double.toString(amount)));//added by ZYP currencyformat
                                txtamount.setTag(Double.toString(amount));            //added by ZYP for price with modifier

                                //comment for price_decimal_place
                            }

                            List<SelectedItemModifierObj> selectedmodifierobjlist = new ArrayList<SelectedItemModifierObj>();
                            // LinearLayout selectedlinearlayout =
                            // (LinearLayout)dialog.findViewById(R.id.selectedmodifierItemlist);
                            for (int i = 0; i < selectedmodifieditemlist.getChildCount(); i++) {
                                SelectedItemModifierObj selectedmodifierobj = new SelectedItemModifierObj();

                                LinearLayout layout = (LinearLayout) selectedmodifieditemlist.getChildAt(i);
                                TextView txtmodifiername = (TextView) layout.getChildAt(0);
                                TextView txtmodifierqty = (TextView) layout.getChildAt(2);
                                selectedmodifierobj.setitemsr(txtsr.getText().toString());
                                selectedmodifierobj.setitemid(txtmodifiername.getTag().toString());
                                selectedmodifierobj.setname(txtmodifiername.getText().toString());
                                selectedmodifierobj.setqty(txtmodifierqty.getText().toString());
                                selectedmodifierobj.set_maincode(txtitem.getTag().toString());

                                TextView txtModifierStatus = (TextView) layout.getChildAt(5);
                                String price = "";
                                String max_price = "";
                                if (txtModifierStatus.getText().equals("setmenuitem")) {
                                    ItemsObj itemobj = dbhelper.getItemsbyitemid(layout.getChildAt(0).getTag().toString());
                                    price = itemobj.getsale_price();
                                    selectedmodifierobj.setstatus("setmenuitem");
                                    //added by MPPA for setmenu item
                                    if (dbhelper.GetSetMenuOrgQty() == true) {
                                        txtmodifierqty.setText(txtqty.getText().toString());
                                        selectedmodifierobj.setqty(txtqty.getText().toString());
                                    }

                                    if (txtsr.getTag().toString().equals("P")) {

                                    }
                                    max_price = dbhelper.getSetMenuMaxPricebyitemid(layout.getChildAt(0).getTag().toString(), txtitem.getTag().toString());
                                } else {
                                    price = dbhelper.getModifierpricebyitemid(txtmodifiername.getTag().toString());
                                    selectedmodifierobj.setstatus("modifier");
                                    max_price = "0";
                                }


                                Double modifieramount = ((Double.parseDouble(txtmodifierqty.getText().toString())) *
                                        Double.parseDouble(price == "" ? "0" : price));
                                selectedmodifierobj.setamount(Double.toString(modifieramount));
                                selectedmodifierobj.set_max_price(max_price);
                                selectedmodifierobjlist.add(selectedmodifierobj);


                            }

                            // EditText txtremark =
                            // (EditText)dialog.findViewById(R.id.txtremark);
                            if (!txtremark.getText().toString().equals("")) {
                                SelectedItemModifierObj selectedmodifierobj = new SelectedItemModifierObj();
                                selectedmodifierobj.setstatus("remark");
                                selectedmodifierobj.setname(txtremark.getText().toString());
                                selectedmodifierobjlist.add(selectedmodifierobj);
                            }

                            //if(!txtsr.getTag().toString().equals("P")){

                            BindModifieritemtolist(itemparent, selectedmodifierobjlist, true);
                            //}

                            if (chktakeaway.isChecked()) {
                                txttakeaway.setTag(true);
                                itemparent.setBackgroundColor(Color.parseColor("#a10099"));
                                //itemparent.setBackgroundColor(Color.parseColor("#dddbdb"));
                                if (dbhelper.getchangeparcelprice() == true && (current_unit == 0 || current_unit == 1))  //modified WHM [2020-02-05] MDY1-20023
                                {
                                    String code = itemname.getTag().toString();
                                    String amt = dbhelper.getpriceforParcel(code);
                                    if (!(Double.parseDouble(amt) > 0)) {
                                        amt = dbhelper.getItempricebyitemid(code);
                                    }
                                    Double amount = Double.parseDouble(dbhelper.qtyroundto(qty.getTag().toString())) * Double.parseDouble(amt);
                                    txtamount.setText(getCurrencyFormat(Double.toString(amount)));
                                    //txtamount.setText(getCurrencyFormat(dbhelper.PriceRoundTo(Double.toString(amount)).replace(pricetrim, "").trim()));

                                } else //added WHM [2020-02-05] MDY1-20023
                                {
                                    current_unit = 0;
                                }

                            } else {
                                txttakeaway.setTag(false);
                                // itemparent.setBackgroundColor(Color.TRANSPARENT);
                            }

                            // added by WaiWL on 23/07/2015
                            if (chkisFinishedItem.isChecked()) {
                                txtisFinishedItem.setTag(true);
                                itemparent.setBackgroundColor(Color
                                        .parseColor("#dddbdb"));
                                dbhelper.UpdateFinishedItem(dbhelper.getServiceURL(), saleid,
                                        txtsr.getText().toString().replace(".", "").trim(),
                                        txtisFinishedItem.getTag().equals(true) ? "1" : "0");
                            } else {
                                txtisFinishedItem.setTag(false);
                                if (!GlobalClass.use_foodtruck) {
                                    dbhelper.UpdateFinishedItem(dbhelper.getServiceURL(), saleid,
                                            txtsr.getText().toString().replace(".", "").trim(),
                                            txtisFinishedItem.getTag().equals(true) ? "1" : "0");
                                }

                            }
                            ////////////

//                        if (chktakeaway.isChecked()) {
//                            txttakeaway.setTag(true);
//                            itemparent.setBackgroundColor(Color.parseColor("#a10099"));
//                        }

                            if (txtsr.getTag().toString().equals("P") || txtsr.getTag().toString().equals("Q")) {

                                String oldqty = dbhelper.getSaleItemQtyBySaleIDandSR(saleid,
                                        txtrealsr.getText().toString()).get(0).getqty();

                                LinearLayout mainparentlayout = ((LinearLayout) (txtsr.getParent().getParent()));

                                if (Double.parseDouble(oldqty) != Double.parseDouble(txtqty.getText().toString())) {
                                    txtsr.setTag("Q");

                                    if (mainparentlayout.getChildCount() > 1) {
                                        for (int i = 1; i < mainparentlayout.getChildCount(); i++) {

                                            ((LinearLayout) mainparentlayout.getChildAt(i)).getChildAt(0).setTag("Q");
                                        }
                                    }
                                } else {
//                                txtsr.setTag("P");
//                                if (mainparentlayout.getChildCount() > 1) {
//                                    for (int i = 1; i < mainparentlayout.getChildCount(); i++) {
//
//                                        ((LinearLayout) mainparentlayout.getChildAt(i)).getChildAt(0).setTag("Q");
//                                    }
//                                }
                                }
                            }

                            if (GlobalClass.use_foodtruck) {
                                Submit_Voucher(0);
                            }

                            itemparent.setBackgroundColor(Color.TRANSPARENT);
                            dialog.dismiss();
                            CalculateTotal();
                        }
                    }
                });

        ((Button) dialog.findViewById(R.id.butCancel))
                .setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        dialog.cancel();

                        clickChecked = true;
                        itemparent.setBackgroundColor(Color.TRANSPARENT);

                        //region comment

//                        TextView txtsr = (TextView) itemparent.getChildAt(0);// added
//                        // by
//                        // WaiWL
//                        // on
//                        // 25/07/2015
//                        TextView txttakeaway = (TextView) itemparent
//                                .getChildAt(5);
//                        CheckBox chktakeaway = (CheckBox) dialog.findViewById(R.id.chktakeaway);
//                        // added by WaiWL on 24/07/2015
//                        TextView txtisFinishedItem = (TextView) itemparent
//                                .getChildAt(7);
//                        CheckBox chkisFinishedItem = (CheckBox) dialog
//                                .findViewById(R.id.chkFinish);
//                        // //////////
//                        itemparent.setBackgroundColor(Color.TRANSPARENT);
//                        if (chktakeaway.isChecked()) {
//                            txttakeaway.setTag(true);
//                            itemparent.setBackgroundColor(Color.parseColor("#a10099"));
//                        } else {
//                            txttakeaway.setTag(false);
//                            // itemparent.setBackgroundColor(Color.TRANSPARENT);
//                        }
//
//                        // added by WaiWL on 24/07/2015
//                        if (dbhelper.getStatusFinishedItem(saleid, txtsr
//                                .getText().toString().replace(".", "").trim())) {
//                            chkisFinishedItem.setChecked(true);
//                        } else {
//                            chkisFinishedItem.setChecked(false);
//                        }
//
//                        if (chkisFinishedItem.isChecked()) {
//                            txtisFinishedItem.setTag(true);
//                            itemparent.setBackgroundColor(Color.parseColor("#dddbdb"));
//                        } else {
//                            txtisFinishedItem.setTag(false);
//                        }
//                        // //////////
//                        TextView txtamount = (TextView) itemparent.getChildAt(3);
//                        TextView qty = (TextView) itemparent.getChildAt(2);
//                        TextView txtitem = (TextView) itemparent.getChildAt(1);
//                        TextView txtunit = (TextView) itemparent.getChildAt(4);
//
//
//                        //Price Decimal Place
//                        String pricetrim = ".";
//                        String pricedecimal = dbhelper
//                                .getprice_decimal_places();
//                        for (int i = 0; i < Integer.valueOf(pricedecimal); i++) {
//                            pricetrim = pricetrim + "0";
//                        }
//
//                        if (use_unit == true && !txtunit.getTag().toString().equals(" ")) {
//                            List<UnitCodeObj> unitcodelist = dbhelper
//                                    .getunitcodebyunit(txtitem.getTag()
//                                            .toString(), txtunit.getTag()
//                                            .toString());
//                            qty.setText(qty.getTag().toString() + " "
//                                    + unitcodelist.get(0).getshortname());
//
////                            Double amount = Double.parseDouble(qty.getTag()
////                                    .toString())
////                                    * Double.parseDouble(unitcodelist.get(0)
////                                    .getsaleprice());
//
//                            Double amount = Double.parseDouble(qty.getTag().toString())	* Double.parseDouble(unitcodelist.get(0)
//                                    .getsaleprice().equals("null") ? "0" :  unitcodelist.get(0)
//                                    .getsaleprice()) ;	//modified WHM [2020-02-05] MDY1-20023
//
//							txtamount.setText(getCurrencyFormat(Double.toString(amount)));
//                            //txtamount.setText(getCurrencyFormat(dbhelper.PriceRoundTo(Double.toString(amount)).replace(pricetrim, "").trim()));
//
//
//                            if (dbhelper.getchangeparcelprice() == true && (current_unit==0 || current_unit==1) )//modified WHM [2020-02-05]MDY1-20023
//                            {
//                                String code = itemname.getTag().toString();
//                                String amt = dbhelper.getpriceforParcel(code);
//                                if (!(Double.parseDouble(amt) > 0)) {
//                                    amt = dbhelper.getItempricebyitemid(code);
//                                }
//                                Double parcelamount = Double.parseDouble(dbhelper.qtyroundto(qty.getTag().toString())) * Double.parseDouble(amt);
//								txtamount.setText(getCurrencyFormat(Double.toString(parcelamount)));
//                                //txtamount.setText(dbhelper.PriceRoundTo(Double.toString(amount)).replace(pricetrim, "").trim());
//                            }
//
//                            else
//                            {
//                                current_unit = 0 ;
//                            }
//
//
//                        } else {
//                            if (Boolean.parseBoolean(dbhelper.getItemsbyitemid(itemname.getTag().toString()).getissetmenu()) == false) {
//
//                                qty.setText(qty.getTag().toString() + " ");
//                                Double amount = Double.parseDouble(dbhelper.qtyroundto(qty.getTag().toString())) * Double.parseDouble(dbhelper
//                                        .getItempricebyitemid(txtitem.getTag().toString()));
//                                //txtamount.setText(dbhelper.PriceRoundTo(Double.toString(amount)).replace(pricetrim, "").trim());
//								//txtamount.setText(getCurrencyFormat(Double.toString(amount))); //comment for price_decimal_place
//                                txtamount.setText(getCurrencyFormat(amount.toString()));
//                                txtamount.setTag(amount);
//
//                                if (chktakeaway.isChecked()) {
//                                    txttakeaway.setTag(true);
//
//                                    if (dbhelper.getchangeparcelprice() == true && (current_unit == 0 || current_unit == 1))//modified WHM [2020-02-05]MDY1-20023
//                                    {
//                                        String code = itemname.getTag().toString();
//                                        String amt = dbhelper.getpriceforParcel(code);
//                                        if (amt.equals("")) {
//                                            dbhelper.getItempricebyitemid(code);
//                                        }
//                                        Double parcelamount = Double.parseDouble(dbhelper.qtyroundto(qty.getTag().toString())) * Double.parseDouble(amt);
//                                        txtamount.setText(getCurrencyFormat(Double.toString(amount)));
//                                        //txtamount.setText(dbhelper.PriceRoundTo(Double.toString(amount)).replace(pricetrim, "").trim());
//                                    } else {
//                                        current_unit = 0;
//                                    }
//                                } else {
//                                    txttakeaway.setTag(false);
//                                }
//
//                            } else {
//
///*								qty.setText(qty.getTag().toString() + " ");
//								Double amount = Integer.parseInt(qty.getTag().toString())* Double.parseDouble(dbhelper
//												.getItempricebyitemid(txtitem.getTag().toString()));
//								txtamount.setText(Double.toString(amount));*/
//
//                                Double modifieramount = 0.0;
//
//                                List<SelectedItemModifierObj> selectedmodifierobjlist = new ArrayList<SelectedItemModifierObj>();
//
//                                for (int i = 0; i < selectedmodifieditemlist
//                                        .getChildCount(); i++) {
//                                    SelectedItemModifierObj selectedmodifierobj = new SelectedItemModifierObj();
//
//                                    LinearLayout layout = (LinearLayout) selectedmodifieditemlist
//                                            .getChildAt(i);
//                                    TextView txtmodifiername = (TextView) layout
//                                            .getChildAt(0);
//                                    TextView txtmodifierqty = (TextView) layout
//                                            .getChildAt(2);
//                                    selectedmodifierobj.setitemsr(txtsr.getText()
//                                            .toString());
//                                    selectedmodifierobj.setitemid(txtmodifiername
//                                            .getTag().toString());
//                                    selectedmodifierobj.setname(txtmodifiername
//                                            .getText().toString());
//                                    selectedmodifierobj.setqty(txtmodifierqty.getText()
//                                            .toString());
//
//                                    TextView txtModifierStatus = (TextView) layout.getChildAt(5);
//                                    String price = "";
//                                    String max_price = "";
//                                    if (txtModifierStatus.getText().equals("setmenuitem")) {
//                                        ItemsObj itemobj = dbhelper.getItemsbyitemid(layout.getChildAt(0).getTag().toString());
//                                        price = itemobj.getsale_price();
//                                        selectedmodifierobj.setstatus("setmenuitem");
//                                        max_price = dbhelper.getSetMenuMaxPricebyitemid(layout.getChildAt(0).getTag().toString(), txtitem.getTag().toString());
//                                    } else {
//                                        price = dbhelper.getModifierpricebyitemid(txtmodifiername.getTag().toString());
//                                        selectedmodifierobj.setstatus("modifier");
//                                        max_price = "0";
//                                    }
//
//
//                                    modifieramount = ((Integer.parseInt(txtmodifierqty.getText().toString())) *
//                                            Double.parseDouble(price == "" ? "0" : price));
//                                    //modifieramount = 0.0;
//                                    selectedmodifierobj.setamount(Double.toString(modifieramount));
//                                    selectedmodifierobj.set_max_price(max_price);
//                                    selectedmodifierobjlist.add(selectedmodifierobj);
//                                }
//                                BindModifieritemtolist(itemparent, selectedmodifierobjlist);
//
//                            }
//
//                        }
//
//                        CalculateTotal();
                        //endregion cmt

                    }
                });

        ((TextView) dialog.findViewById(R.id.butremarkclear))
                .setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        txtremark.setText("");
                    }
                });

        ((Button) dialog.findViewById(R.id.butplus))
                .setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {

                        //added WHM [2020-10-19]
                        if (dbhelper.isSoldOutItem(itemname.getTag().toString())) {
                            showAlertDialog(OrderTaking.this, "Warning!", "This item is sold out!\nCannot change the Qty.", false);
                        } else {
                            //end WHM [2020-10-19]


                            //txtqty.setText(Integer.toString(Integer.parseInt(txtqty.getText().toString()) + 1));
//						String tmp=Double.toString(Double.parseDouble(txtqty.getText().toString()) + 1);
//						txtqty.setText(dbhelper.qtyroundto(tmp).replace(".00", "").trim());

                            String trimString = ".";
                            String qtydecimal = dbhelper.getqty_decimal_places();
                            for (int i = 0; i < Integer.valueOf(qtydecimal); i++) {
                                trimString = trimString + "0";
                            }
                            qtytmp = Double.toString(Double.parseDouble(txtqty.getText().toString()) + 1);
                            txtqty.setText(dbhelper.qtyroundto(qtytmp).replace(trimString, "").trim());

                        }

                    }
                });

        final AlertDialog.Builder builders = new AlertDialog.Builder(this);

        // final AlertDialog.Builder dlgbuilders = new
        // AlertDialog.Builder(this);
        ((Button) dialog.findViewById(R.id.butminus))
                .setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        // String UserID = ((TextView)
                        // findViewById(R.id.txtDocID))
                        // .getTag().toString();
                        String UserID = dbhelper.getwaiterid();
                        // LinearLayout itemlistlayout = (LinearLayout)
                        // findViewById(R.id.itemlistlayout);
                        // boolean needsubmit = false;
                        // if (initialitemcount <
                        // itemlistlayout.getChildCount()|| needsubmit == true)

                        String Name = (String) ((Button) dialog
                                .findViewById(R.id.butremove)).getText();
                        if (dbhelper.Allow_itemcancel(UserID) == true || Name != "Cancel Item") {
                            if (Double.parseDouble(txtqty.getText().toString()) > 1) {

//								String tmp=Double.toString(Double.parseDouble(txtqty.getText().toString()) - 1);
//								txtqty.setText(dbhelper.qtyroundto(tmp).replace(".00", "").trim());
                                //txtqty.setText(Integer.toString(Integer.parseInt(txtqty.getText().toString()) - 1));
                                String trimString = ".";
                                String qtydecimal = dbhelper.getqty_decimal_places();
                                for (int i = 0; i < Integer.valueOf(qtydecimal); i++) {
                                    trimString = trimString + "0";
                                }
                                qtytmp = Double.toString(Double.parseDouble(txtqty.getText().toString()) - 1);
                                txtqty.setText(dbhelper.qtyroundto(qtytmp).replace(trimString, "").trim());
                            }

                        } else {

                            builders.setMessage("This user hasn't permission to cancel item.");
                            builders.setTitle("Galaxy Restaurant");
                            // showAlertDialog(null, "Galaxy Restaurant",
                            // "This user hasn't permisssion to cancel item",
                            // false);
                            builders.setPositiveButton("Ok",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(
                                                DialogInterface dialog,
                                                int which) {
                                            // dismiss the dialog
                                        }
                                    });
                            builders.create().show();
                        }

                    }
                });

        /*Commented by Arkar Moe on [22/02/2017]
        * 		if (!(txtsr.getTag().toString().equals("N"))) {
			((TextView) dialog.findViewById(R.id.butmodifier)).setVisibility(View.GONE);
			((TextView) dialog.findViewById(R.id.txtmodifier1))
					.setText("Modifier Items");
			((TextView) dialog.findViewById(R.id.txtmodifier2)).setText("");
		}*/

        ((Button) dialog.findViewById(R.id.butremove))
                .setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        // String UserID = ((TextView)
                        // findViewById(R.id.txtDocID))
                        // .getTag().toString();
                        // txtUserName = ((EditText)
                        // findViewById(R.id.txtusername));

                        clickChecked = true;
                        String UserID = dbhelper.getwaiterid();
                        btnitemcancel = (Button) dialog.findViewById(R.id.butremove);

                        if (btnitemcancel.getText() != "Cancel Item") {
                            TextView txtsr = (TextView) itemparent.getChildAt(0);
                            if (txtsr.getTag().toString().equals("P")) {
                                txtsr.setTag("C");
                                itemname.setPaintFlags(itemname.getPaintFlags()
                                        | Paint.STRIKE_THRU_TEXT_FLAG);
                                qty.setPaintFlags(qty.getPaintFlags()
                                        | Paint.STRIKE_THRU_TEXT_FLAG);
                                amount.setPaintFlags(amount.getPaintFlags()
                                        | Paint.STRIKE_THRU_TEXT_FLAG);
                                itemparent.setBackgroundColor(Color.TRANSPARENT);

                                if (mainparent.getChildCount() > 1) {
                                    for (int i = 1; i < mainparent.getChildCount(); i++) {
                                        ((LinearLayout) mainparent.getChildAt(i))
                                                .getChildAt(0).setTag("C");
                                    }
                                }

                            } else {
                                ((LinearLayout) mainparent.getParent()).removeView(mainparent);

                            }

                            if (GlobalClass.use_foodtruck) {
                                Submit_Voucher(0);
                            }

                            dialog.dismiss();
                            CalculateTotal();
                            ReserializeSR();
                        } else {
                            int itemcount = checkItemCancel(); //added by EKK on 18-02-2020

                            if (itemcount > 1) {

                                if (dbhelper.Allow_itemcancel(UserID) == true) {
                                    TextView txtsr = (TextView) itemparent.getChildAt(0);

                                    if (txtsr.getTag().toString().equals("P")) {
                                        txtsr.setTag("C");
                                        itemname.setPaintFlags(itemname
                                                .getPaintFlags()
                                                | Paint.STRIKE_THRU_TEXT_FLAG);

                                        itemname.setTextColor(Color.parseColor("#995422")); //added by ZYP for printed color

                                        qty.setPaintFlags(qty.getPaintFlags()
                                                | Paint.STRIKE_THRU_TEXT_FLAG);
                                        amount.setPaintFlags(amount.getPaintFlags()
                                                | Paint.STRIKE_THRU_TEXT_FLAG);
                                        itemparent.setBackgroundColor(Color.TRANSPARENT);

                                        if (mainparent.getChildCount() > 1) {
                                            for (int i = 1; i < mainparent
                                                    .getChildCount(); i++) {
                                                ((LinearLayout) mainparent
                                                        .getChildAt(i)).getChildAt(
                                                        0).setTag("C");
                                            }
                                        }
                                    } else {
                                        ((LinearLayout) mainparent.getParent()).removeView(mainparent);
                                    }

                                    if (GlobalClass.use_foodtruck) {
                                        Submit_Voucher(0);
                                    }

                                    dialog.dismiss();
                                    CalculateTotal();
                                    ReserializeSR();


                                } else {
                                    builders.setMessage("This user hasn't permission to cancel item.");
                                    builders.setTitle("Galaxy Restaurant");
                                    // showAlertDialog(null, "Galaxy Restaurant",
                                    // "This user hasn't permisssion to cancel item",
                                    // false);
                                    builders.setPositiveButton("Ok",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(
                                                        DialogInterface dialog,
                                                        int which) {
                                                    // dismiss the dialog
                                                }
                                            });
                                    builders.create().show();
                                }
                            } else //added by EKK on 18-02-2020
                            {
                                builders.setMessage("At least one item in voucher to confirm!");
                                builders.setTitle("Galaxy Restaurant");

                                builders.setPositiveButton("Ok",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(
                                                    DialogInterface dialog,
                                                    int which) {
                                                // dismiss the dialog
                                            }
                                        });
                                builders.create().show();
                            }

                        }
                    }

                });

		/* Commented by Arkar Moe on [22/02/2017]
		 * ((TextView) dialog.findViewById(R.id.butmodifier))
				.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						LayoutParams contentParams = (LinearLayout.LayoutParams) itemeditlayout
								.getLayoutParams();
						contentParams.width = 0;
						contentParams.height = LayoutParams.WRAP_CONTENT;
						itemeditlayout.setLayoutParams(contentParams);

						contentParams = (LinearLayout.LayoutParams) modifierlayout
								.getLayoutParams();
						// int width = (int)
						// TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
						// 550, getResources().getDisplayMetrics());
						// int height = (int)
						// TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
						// 500, getResources().getDisplayMetrics());
						contentParams.width = GlobalClass.getDPsize(550, ctx);
						contentParams.height = GlobalClass.getDPsize(400, ctx);
						modifierlayout.setLayoutParams(contentParams);

						Window window = dialog.getWindow();
						window.setLayout(GlobalClass.getDPsize(550, ctx),
								GlobalClass.getDPsize(400, ctx));

						List<ItemsObj> itemobjlist = dbhelper
								.getmodifierItemslistbyitemid(txtitem.getTag()
										.toString());
						// LinearLayout modifieditemlist =
						// (LinearLayout)dialog.findViewById(R.id.modifieditemlist);

						// width = (int)
						// TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
						// 180, getResources().getDisplayMetrics());
						LayoutParams modifieritemlayout = new LayoutParams(
								GlobalClass.getDPsize(180, ctx),
								LayoutParams.MATCH_PARENT);

						// width = (int)
						// TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
						// 50, getResources().getDisplayMetrics());
						LayoutParams modifierqtylayout = new LayoutParams(
								GlobalClass.getDPsize(50, ctx),
								LayoutParams.MATCH_PARENT);

						// width = (int)
						// TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
						// 60, getResources().getDisplayMetrics());
						LayoutParams buttonlayout = new LayoutParams(
								GlobalClass.getDPsize(60, ctx), GlobalClass
										.getDPsize(60, ctx));

						// width = (int)
						// TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
						// 80, getResources().getDisplayMetrics());
						LayoutParams pricelayout = new LayoutParams(GlobalClass
								.getDPsize(80, ctx), LayoutParams.MATCH_PARENT);

						LayoutParams checkboxlayout = new LayoutParams(
								LayoutParams.WRAP_CONTENT,
								LayoutParams.MATCH_PARENT);
						LayoutParams dividerlayout = new LayoutParams(
								LayoutParams.FILL_PARENT, 1);
						buttonlayout.setMargins(0, 0, 0, 0);
						modifieritemlayout.setMargins(3, 0, 0, 0);
						checkboxlayout.setMargins(5, 0, 0, 0);
						modifierqtylayout.gravity = Gravity.CENTER;

						// height = (int)
						// TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
						// 60, getResources().getDisplayMetrics());
						LinearLayout.LayoutParams modifieditemlayout = new LayoutParams(
								LayoutParams.MATCH_PARENT, GlobalClass
										.getDPsize(60, ctx));

						modifieditemlist.removeAllViews();

						TextView txtmodifier = (TextView) dialog
								.findViewById(R.id.txtmodifiername);
						txtmodifier.setTypeface(font);
						txtmodifier.setText("Modifier Items("
								+ itemname.getText() + ")");

						for (ItemsObj itemsObj : itemobjlist) {
							LinearLayout itemlist = new LinearLayout(ctx);

							TextView txtmodifieritem = new TextView(ctx);
							txtmodifieritem.setText(itemsObj.getdescription());
							txtmodifieritem.setTag(itemsObj.getcode());
							txtmodifieritem.setGravity(Gravity.CENTER
									| Gravity.LEFT);
							txtmodifieritem.setTextSize(
									TypedValue.COMPLEX_UNIT_SP, 15);
							txtmodifieritem.setTypeface(font);

							final TextView txtmodifierqty = new TextView(ctx);
							txtmodifierqty.setText("1");
							txtmodifierqty.setGravity(Gravity.CENTER);
							txtmodifierqty.setTextSize(
									TypedValue.COMPLEX_UNIT_SP, 20);
							txtmodifierqty.setMinWidth(30);

							Button btnplus = new Button(ctx);

							btnplus.setBackgroundResource(R.drawable.squareplus);

							btnplus.setOnClickListener(new OnClickListener() {
								public void onClick(View v) {
									int qty = Integer.parseInt(txtmodifierqty
											.getText().toString());
									qty = qty + 1;
									txtmodifierqty.setText(Integer
											.toString(qty));
								}
							});

							Button btnminus = new Button(ctx);

							btnminus.setBackgroundResource(R.drawable.squareminus);

							btnminus.setOnClickListener(new OnClickListener() {
								public void onClick(View v) {
									int qty = Integer.parseInt(txtmodifierqty
											.getText().toString());
									if (qty > 1) {
										qty = qty - 1;
										txtmodifierqty.setText(Integer
												.toString(qty));
									}
								}
							});

							CheckBox chkbox = new CheckBox(ctx);

							LinearLayout selectedmodifierlist = (LinearLayout) dialog
									.findViewById(R.id.selectedmodifierItemlist);

							for (int j = 0; j < selectedmodifierlist
									.getChildCount(); j++) {
								TextView modifieritem = (TextView) ((LinearLayout) selectedmodifierlist
										.getChildAt(j)).getChildAt(0);
								TextView modifieritemqty = (TextView) ((LinearLayout) selectedmodifierlist
										.getChildAt(j)).getChildAt(2);
								if (modifieritem.getTag().toString()
										.equals(itemsObj.getcode())) {
									txtmodifierqty.setText(modifieritemqty
											.getText());
									chkbox.setChecked(true);
								}

							}

							TextView txtprice = new TextView(ctx);
							txtprice.setPadding(5, 0, 0, 0);
							String price = itemsObj.getsale_price();

							if (price.equals("") || price.equals("null")) {
								price = "0";
							}

							price = Integer.toString((int) (Double
									.parseDouble(price)));

							txtprice.setText("price-" + price);
							txtprice.setGravity(Gravity.CENTER_VERTICAL);
							itemlist.addView(txtmodifieritem,
									modifieritemlayout);
							itemlist.addView(btnplus, buttonlayout);
							itemlist.addView(txtmodifierqty, modifierqtylayout);
							itemlist.addView(btnminus, buttonlayout);
							itemlist.addView(txtprice, pricelayout);
							itemlist.addView(chkbox, checkboxlayout);

							modifieditemlist.addView(itemlist,
									modifieditemlayout);

							TextView txtline = new TextView(ctx);
							txtline.setBackgroundColor(Color
									.parseColor("#767c78"));
							txtline.setGravity(Gravity.CENTER);
							modifieditemlist.addView(txtline, dividerlayout);
						}
					}
				});*/

        ((Button) dialog.findViewById(R.id.butcancelmodifier))
                .setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        LayoutParams contentParams = (LinearLayout.LayoutParams) itemeditlayout
                                .getLayoutParams();

                        // int width = (int)
                        // TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        // 350, getResources().getDisplayMetrics());
                        // int height = (int)
                        // TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        // 600, getResources().getDisplayMetrics());
                        contentParams.width = GlobalClass.getDPsize(370, ctx);
                        //contentParams.height = GlobalClass.getDPsize(500, ctx);
                        contentParams.height = LayoutParams.WRAP_CONTENT;
                        itemeditlayout.setLayoutParams(contentParams);

                        contentParams = (LinearLayout.LayoutParams) modifierlayout.getLayoutParams();
                        contentParams.width = 0;
                        contentParams.height = LayoutParams.WRAP_CONTENT;
                        modifierlayout.setLayoutParams(contentParams);

                        Window window = dialog.getWindow();
                        // width = (int)
                        // TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        // 400, getResources().getDisplayMetrics());
                        window.setLayout(GlobalClass.getDPsize(400, ctx),
                                //GlobalClass.getDPsize(450, ctx));
                                LayoutParams.MATCH_PARENT);
                    }
                });

        ((Button) dialog.findViewById(R.id.butOkmodifier))
                .setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        List<SelectedItemModifierObj> modifieritemlist = new ArrayList<SelectedItemModifierObj>();
                        for (int i = 0; i < modifieditemlist.getChildCount(); i++) {
                            SelectedItemModifierObj itemmodifierobj = new SelectedItemModifierObj();
                            if (modifieditemlist.getChildAt(i) instanceof LinearLayout) {
                                final LinearLayout itemlayout = (LinearLayout) modifieditemlist
                                        .getChildAt(i);
                                CheckBox chkbox = (CheckBox) itemlayout
                                        .getChildAt(5);
                                if (chkbox.isChecked()) {
                                    TextView txtitem = (TextView) itemlayout
                                            .getChildAt(0);
                                    TextView txtqty = (TextView) itemlayout
                                            .getChildAt(2);

                                    itemmodifierobj.setname(txtitem.getText().toString());
                                    itemmodifierobj.setitemid(txtitem.getTag().toString());
                                    itemmodifierobj.setqty(txtqty.getText().toString());
                                    itemmodifierobj.setstatus("modifier");

                                    modifieritemlist.add(itemmodifierobj);
                                }
                            }
                        }
                        BindSelectedModifiertoedititemlist(dialog,
                                modifieritemlist);
                    }
                });

        ((Button) dialog.findViewById(R.id.butaddmodifier))
                .setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        List<ItemsObj> itemobjlist = dbhelper.getmodifierItemslistbyitemid(txtitem.getTag().toString());

                        if (itemobjlist.size() == 0) {
                            showAlertDialog(ctx, "Information!", "This item has no modifier items!", false);
                            return;
                        }
                        LayoutParams contentParams = (LinearLayout.LayoutParams) itemeditlayout.getLayoutParams();
                        contentParams.width = 0;
                        contentParams.height = LayoutParams.WRAP_CONTENT;
                        itemeditlayout.setLayoutParams(contentParams);

                        contentParams = (LinearLayout.LayoutParams) modifierlayout.getLayoutParams();
                        // int width = (int)
                        // TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        // 550, getResources().getDisplayMetrics());
                        // int height = (int)
                        // TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        // 500, getResources().getDisplayMetrics());
                        contentParams.width = GlobalClass.getDPsize(550, ctx);
                        contentParams.height = GlobalClass.getDPsize(360, ctx);
                        modifierlayout.setLayoutParams(contentParams);

                        Window window = dialog.getWindow();
                        window.setLayout(GlobalClass.getDPsize(550, ctx),
                                GlobalClass.getDPsize(380, ctx));


                        // LinearLayout modifieditemlist =
                        // (LinearLayout)dialog.findViewById(R.id.modifieditemlist);

                        // width = (int)
                        // TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        // 180, getResources().getDisplayMetrics());
                        LayoutParams modifieritemlayout = new LayoutParams(
                                GlobalClass.getDPsize(200, ctx), LayoutParams.MATCH_PARENT);

                        // width = (int)
                        // TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        // 50, getResources().getDisplayMetrics());
                        LayoutParams modifierqtylayout = new LayoutParams(
                                GlobalClass.getDPsize(50, ctx), LayoutParams.MATCH_PARENT);

                        // width = (int)
                        // TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        // 60, getResources().getDisplayMetrics());
                        LayoutParams buttonlayout = new LayoutParams(
                                GlobalClass.getDPsize(50, ctx), GlobalClass.getDPsize(50, ctx));

                        // width = (int)
                        // TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        // 80, getResources().getDisplayMetrics());
                        LayoutParams pricelayout = new LayoutParams(GlobalClass
                                .getDPsize(100, ctx), LayoutParams.MATCH_PARENT);

                        LayoutParams checkboxlayout = new LayoutParams(
                                LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
                        LayoutParams dividerlayout = new LayoutParams(
                                LayoutParams.MATCH_PARENT, 1);
                        dividerlayout.setMargins(5, 0, 5, 0);
                        buttonlayout.setMargins(0, 0, 0, 0);
                        modifieritemlayout.setMargins(10, 0, 0, 0);
                        checkboxlayout.setMargins(5, 0, 0, 0);
                        modifierqtylayout.gravity = Gravity.CENTER;

                        // height = (int)
                        // TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        // 60, getResources().getDisplayMetrics());
                        LinearLayout.LayoutParams modifieditemlayout = new LayoutParams(
                                LayoutParams.MATCH_PARENT, GlobalClass.getDPsize(60, ctx));

                        modifieditemlist.removeAllViews();

                        TextView txtmodifier = (TextView) dialog
                                .findViewById(R.id.txtmodifiername);
                        txtmodifier.setTypeface(font);
                        txtmodifier.setText("Modifier Items ( " + itemname.getText() + " )");

                        for (ItemsObj itemsObj : itemobjlist) {
                            LinearLayout itemlist = new LinearLayout(ctx);

                            TextView txtmodifieritem = new TextView(ctx);
                            txtmodifieritem.setText(itemsObj.getdescription());
                            txtmodifieritem.setTag(itemsObj.getcode());
                            txtmodifieritem.setGravity(Gravity.CENTER | Gravity.LEFT);
                            txtmodifieritem.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                            txtmodifieritem.setTypeface(font);

                            final TextView txtmodifierqty = new TextView(ctx);
                            txtmodifierqty.setText("1");
                            txtmodifierqty.setGravity(Gravity.CENTER);
                            txtmodifierqty.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                            txtmodifierqty.setMinWidth(30);

                            Button btnplus = new Button(ctx);
                            btnplus.setBackgroundResource(R.drawable.squareplus);

                            btnplus.setOnClickListener(new OnClickListener() {
                                public void onClick(View v) {
                                    int qty = Integer.parseInt(txtmodifierqty.getText().toString());
                                    qty = qty + 1;
                                    txtmodifierqty.setText(Integer.toString(qty));

                                }
                            });

                            Button btnminus = new Button(ctx);

                            btnminus.setBackgroundResource(R.drawable.squareminus);

                            btnminus.setOnClickListener(new OnClickListener() {
                                public void onClick(View v) {
                                    int qty = Integer.parseInt(txtmodifierqty.getText().toString());
                                    if (qty > 1) {
                                        qty = qty - 1;
                                        txtmodifierqty.setText(Integer.toString(qty));
                                    }
                                }
                            });

                            CheckBox chkbox = new CheckBox(ctx);

                            LinearLayout selectedmodifierlist = (LinearLayout) dialog
                                    .findViewById(R.id.selectedmodifierItemlist);

                            for (int j = 0; j < selectedmodifierlist.getChildCount(); j++) {
                                TextView modifieritem = (TextView) ((LinearLayout) selectedmodifierlist
                                        .getChildAt(j)).getChildAt(0);
                                TextView modifieritemqty = (TextView) ((LinearLayout) selectedmodifierlist
                                        .getChildAt(j)).getChildAt(2);

                                if (modifieritem.getTag().toString().equals(itemsObj.getcode())) {
                                    txtmodifierqty.setText(modifieritemqty.getText());
                                    chkbox.setChecked(true);
                                }

                            }

                            TextView txtprice = new TextView(ctx);
                            txtprice.setPadding(5, 0, 0, 0);
                            String price = itemsObj.getsale_price();

                            if (price.equals("") || price.equals("null")) {
                                price = "0";
                            }

                            price = Integer.toString((int) (Double.parseDouble(price)));

                            txtprice.setText("Price - " + getCurrencyFormat(price));
                            txtprice.setGravity(Gravity.CENTER_VERTICAL);
                            itemlist.addView(txtmodifieritem, modifieritemlayout);
                            itemlist.addView(btnminus, buttonlayout);
                            itemlist.addView(txtmodifierqty, modifierqtylayout);
                            itemlist.addView(btnplus, buttonlayout);
                            itemlist.addView(txtprice, pricelayout);
                            itemlist.addView(chkbox, checkboxlayout);

                            modifieditemlist.addView(itemlist, modifieditemlayout);

                            TextView txtline = new TextView(ctx);
                            txtline.setBackgroundColor(Color.parseColor("#767c78"));
                            txtline.setGravity(Gravity.CENTER);
                            modifieditemlist.addView(txtline, dividerlayout);
                        }
                    }
                });

        butchangesetitem.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                setmenuitemlist_dialog(dialog, modifierobjlist);
            }
        });
        // LinearLayout
        // layout=(LinearLayout)dialog.findViewById(R.id.itemeditlayout);
        GlobalClass.ChangeLanguage(
                (LinearLayout) dialog.findViewById(R.id.itemeditlayout), this,
                18, font);
        GlobalClass.ChangeLanguage(
                (LinearLayout) dialog.findViewById(R.id.modifierlayout), this,
                18, font);


    }

    //added by EKK on 18-02-2020
    public int checkItemCancel() {
        LinearLayout itemlistlayout = (LinearLayout) findViewById(R.id.itemlistlayout);
        LinearLayout linearlayout = new LinearLayout(this);
        int totalqty = 0;

        for (int i = 0; i < itemlistlayout.getChildCount(); i++) {
            linearlayout = (LinearLayout) itemlistlayout.getChildAt(i);

            if (!((TextView) ((LinearLayout) linearlayout.getChildAt(0))
                    .getChildAt(0)).getTag().toString().equals("C")
                    && !((TextView) ((LinearLayout) linearlayout.getChildAt(0))
                    .getChildAt(0)).getTag().toString().equals("CP")) {

                totalqty += 1;
            }
        }
        //Toast.makeText(OrderTaking.this, "Check Item Cancel : " +totalqty, Toast.LENGTH_SHORT).show();
        return totalqty;
    }

    public void BindSelectedModifiertoedititemlist(Dialog
                                                           dialog, List<SelectedItemModifierObj> modifieritemobjlist) {

        final LinearLayout modifierlayout = (LinearLayout) dialog
                .findViewById(R.id.modifierlayout);
        final LinearLayout itemeditlayout = (LinearLayout) dialog
                .findViewById(R.id.itemeditlayout);
        final TextView txtsr = (TextView) dialog.findViewById(R.id.txtsr);
        final LinearLayout selectedmodifieditemlist = (LinearLayout) dialog
                .findViewById(R.id.selectedmodifierItemlist);

        LayoutParams contentParams = (LinearLayout.LayoutParams) itemeditlayout.getLayoutParams();

        // int width = (int)
        // TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 350,
        // getResources().getDisplayMetrics());
        // int height = (int)
        // TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 600,
        // getResources().getDisplayMetrics());
        contentParams.width = GlobalClass.getDPsize(370, this);
        //contentParams.height = GlobalClass.getDPsize(500, this);
        contentParams.height = LayoutParams.WRAP_CONTENT;
        itemeditlayout.setLayoutParams(contentParams);

        contentParams = (LinearLayout.LayoutParams) modifierlayout.getLayoutParams();
        contentParams.width = 0;
        contentParams.height = LayoutParams.WRAP_CONTENT;
        modifierlayout.setLayoutParams(contentParams);

        Window window = dialog.getWindow();
        // width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
        // 400, getResources().getDisplayMetrics());
        window.setLayout(GlobalClass.getDPsize(400, this),
                //GlobalClass.getDPsize(470, this));
                LayoutParams.MATCH_PARENT);
        selectedmodifieditemlist.removeAllViews();

        // height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
        // 40, getResources().getDisplayMetrics());
        LinearLayout.LayoutParams modifieditemlayout = new LayoutParams(
                LayoutParams.MATCH_PARENT, GlobalClass.getDPsize(40, this));

        // width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
        // 160, getResources().getDisplayMetrics());
        LayoutParams modifieritemlayout = new LayoutParams(
                GlobalClass.getDPsize(160, this), LayoutParams.MATCH_PARENT);

        // width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
        // 30, getResources().getDisplayMetrics());
        LayoutParams modifierqtylayout = new LayoutParams(
                GlobalClass.getDPsize(30, this), LayoutParams.MATCH_PARENT);

        // width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
        // 40, getResources().getDisplayMetrics());
        LayoutParams buttonlayout = new LayoutParams(GlobalClass.getDPsize(40,
                this), GlobalClass.getDPsize(40, this));

        LayoutParams modifierstatuslayout = new LayoutParams(
                GlobalClass.getDPsize(30, this), LayoutParams.MATCH_PARENT);

        LayoutParams checkboxlayout = new LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        buttonlayout.setMargins(10, 0, 10, 0);
        checkboxlayout.setMargins(10, 0, 0, 0);
        for (SelectedItemModifierObj selectedItemModifierObj : modifieritemobjlist) {
            LinearLayout itemlist = new LinearLayout(this);

            TextView txtmodifieritem = new TextView(this);
            txtmodifieritem.setText(selectedItemModifierObj.getname());
            txtmodifieritem.setTag(selectedItemModifierObj.getitemid());
            txtmodifieritem.setGravity(Gravity.CENTER | Gravity.LEFT);
            txtmodifieritem.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            txtmodifieritem.setTypeface(font);

            final TextView txtmodifierqty = new TextView(this);
            txtmodifierqty.setText(selectedItemModifierObj.getqty());
            txtmodifierqty.setGravity(Gravity.CENTER);
            txtmodifierqty.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            txtmodifierqty.setMinWidth(30);

            Button btnplus = new Button(this);

            btnplus.setBackgroundResource(R.drawable.squareplus);

            btnplus.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    int qty = Integer.parseInt(txtmodifierqty.getText()
                            .toString());
                    qty = qty + 1;
                    txtmodifierqty.setText(Integer.toString(qty));
                }
            });

            Button btnminus = new Button(this);

            btnminus.setBackgroundResource(R.drawable.squareminus);

            btnminus.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    int qty = Integer.parseInt(txtmodifierqty.getText()
                            .toString());
                    if (qty > 1) {
                        qty = qty - 1;
                        txtmodifierqty.setText(Integer.toString(qty));
                    }
                }
            });

            final Button btnmodifieritemremove = new Button(this);
            btnmodifieritemremove.setBackgroundResource(R.drawable.remove2);
            final LinearLayout selectedmodifierItemlist = (LinearLayout) dialog
                    .findViewById(R.id.selectedmodifierItemlist);

            btnmodifieritemremove.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    v.getParent();
                    // int i =
                    // ((LinearLayout)btnmodifieritemremove.getParent()).getId();
                    selectedmodifierItemlist.removeView((View) v.getParent());
                }
            });

            if (!txtsr.getTag().equals("N")) {
                btnplus.setVisibility(View.GONE);
                btnminus.setVisibility(View.GONE);
            }

            TextView txtmodifierstatus = new TextView(this);
            txtmodifierstatus.setTag(false);
            txtmodifierstatus.setText(selectedItemModifierObj.getstatus());
            txtmodifierstatus.setVisibility(View.GONE);

            itemlist.addView(txtmodifieritem, modifieritemlayout);
            itemlist.addView(btnminus, buttonlayout);
            itemlist.addView(txtmodifierqty, modifierqtylayout);
            itemlist.addView(btnplus, buttonlayout);
            buttonlayout.setMargins(3, 0, 3, 0);
            itemlist.addView(btnmodifieritemremove, buttonlayout);
            itemlist.addView(txtmodifierstatus, modifierstatuslayout);
            selectedmodifieditemlist.addView(itemlist, modifieditemlayout);
            if (selectedItemModifierObj.getstatus().equals("setmenuitem")) {
                selectedmodifieditemlist.setVisibility(View.GONE);
            } else {
                selectedmodifieditemlist.setVisibility(View.VISIBLE);
            }
        }
        if (modifieritemobjlist.size() > 0) {
            ViewGroup.LayoutParams selecteditemlayout = dialog.findViewById(
                    R.id.selecteditemscroll).getLayoutParams();
            //selecteditemlayout.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 150, getResources()
            //                .getDisplayMetrics());

            dialog.findViewById(R.id.selecteditemscroll).setLayoutParams(
                    selecteditemlayout);


        }
    }

    public void BindModifieritemtolist(View
                                               parent, List<SelectedItemModifierObj> modifierobjlist, boolean isnew) {
        final LinearLayout layout = (LinearLayout) parent.getParent();
        if (layout.getChildCount() > 1) {
            while (layout.getChildCount() > 1) {
                layout.removeViewAt(layout.getChildCount() - 1);
            }
        }

        TextView txtqty = (TextView) ((LinearLayout) parent).getChildAt(2);
        TextView amount = (TextView) ((LinearLayout) parent).getChildAt(3);

        final LayoutParams itemlayoutpara = new LayoutParams(
                LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);

        final LayoutParams srlayoutpara = new LayoutParams(
                GlobalClass.getDPsize(20, this), LayoutParams.WRAP_CONTENT);
        final LayoutParams itemnamelayoutpara = new LayoutParams(
                GlobalClass.getDPsize(136, this), LayoutParams.WRAP_CONTENT);

        final LayoutParams qtylayoutpara = new LayoutParams(
                GlobalClass.getDPsize(40, this), LayoutParams.WRAP_CONTENT);
        final LayoutParams amountlayoutpara = new LayoutParams(
                GlobalClass.getDPsize(90, this), LayoutParams.WRAP_CONTENT);

        Double modifieritemamount = 0.0;
        Double calculateAmount = Double.parseDouble(amount.getTag().toString());

        final Integer txtcolor = Color.parseColor("#192a93");
        for (SelectedItemModifierObj selectedItemModifierObj : modifierobjlist) {
            final LinearLayout itemlayout = new LinearLayout(this);

            if (selectedItemModifierObj.getstatus().equals("remark")) {
                TextView txtitemsr = new TextView(this);
                txtitemsr.setText(selectedItemModifierObj.getitemsr());
                txtitemsr.setPadding(0, 2, 0, 2);
                txtitemsr.setTypeface(font);
                txtitemsr.setGravity(Gravity.LEFT);
                txtitemsr.setVisibility(View.GONE);

                TextView txtstatus = new TextView(this);
                txtstatus.setText(selectedItemModifierObj.getstatus());
                txtstatus.setPadding(0, 2, 0, 2);
                txtstatus.setVisibility(View.GONE);

                TextView txtsrno = new TextView(this);
                txtsrno.setText(" ");
                txtsrno.setPadding(0, 2, 0, 2);
                txtsrno.setTypeface(font);
                txtsrno.setGravity(Gravity.LEFT);
                txtsrno.setTag("N");
                // txtsr.setVisibility(View.GONE);

                TextView txtitemname = new TextView(this);
                txtitemname.setText("  (" + selectedItemModifierObj.getname() + ")");
                txtitemname.setTag(selectedItemModifierObj.getitemid());
                txtitemname.setTypeface(font);// added by WaiWL on 26/08/2015
                txtitemname.setTextColor(Color.parseColor("#9d0f30"));
                txtitemname.setGravity(Gravity.LEFT);

                itemlayout.addView(txtsrno, srlayoutpara);
                itemlayout.addView(txtitemname, itemlayoutpara);
                itemlayout.addView(txtitemsr);
                itemlayout.addView(txtstatus);
                layout.addView(itemlayout, itemlayoutpara);

            } else {

                TextView txtsrno = new TextView(this);
                txtsrno.setText("");
                txtsrno.setTag("N");
                txtsrno.setPadding(0, 2, 0, 2);
                txtsrno.setTypeface(font, Typeface.BOLD);
                txtsrno.setGravity(Gravity.LEFT);
                // txtsr.setVisibility(View.GONE);

                TextView txtitemname = new TextView(this);
                txtitemname.setText("* " + selectedItemModifierObj.getname());
                txtitemname.setTag(selectedItemModifierObj.getitemid());
                txtitemname.setPadding(0, 2, 0, 2);
                txtitemname.setTypeface(font);// added by WaiWL on 26/08/2015
                txtitemname.setTextColor(txtcolor);
                txtitemname.setGravity(Gravity.LEFT);

                TextView txtqtyname = new TextView(this);
                // txtqtyname.setText(Integer.toString((int)(Double.parseDouble(selectedItemModifierObj.getqty())
                // / Double.parseDouble(txtqty.getTag().toString()))));
                txtqtyname.setText(selectedItemModifierObj.getqty());/*Double.parseDouble(txtqty.getTag().toString()) * */ //Double.parseDouble(selectedItemModifierObj.getqty()) + "");
                txtqtyname.setPadding(0, 2, 0, 2);
                txtqtyname.setTextColor(txtcolor);
                txtqtyname.setTypeface(font, Typeface.BOLD);
                txtqtyname.setGravity(Gravity.RIGHT);
                //Added by SMH on 2017-08-14

                if (dbhelper.GetModifierOrgQty() == true) {
                    txtqtyname.setTag((int) ((Double.parseDouble(selectedItemModifierObj.getqty()))
                            * Double.parseDouble(txtqty.getTag().toString())));
//                    txtqtyname.setText(Integer.toString(
//                            (int) ((Double.parseDouble(selectedItemModifierObj.getqty()))
//                                    * Double.parseDouble(txtqty.getTag().toString()))
//                    ));
//                    txtqty.setText(txtqtyname.getText());
                } else {
                    txtqtyname.setTag((int) (Double.parseDouble(selectedItemModifierObj.getqty())));

                }

                TextView txtamount = new TextView(this);
                txtamount.setGravity(Gravity.RIGHT);
                txtamount.setTextColor(txtcolor);
                txtamount.setTypeface(font);
                txtamount.setText("");
                txtamount.setPadding(0, 2, 0, 2);


                Double setmenuitemamount = 0.0;
                Double max_price = 0.0;

                if (selectedItemModifierObj.getstatus().equals("setmenuitem")) {

                    String max_pricetemp = dbhelper.getSetMenuMaxPricebyitemid(selectedItemModifierObj.getitemid(), selectedItemModifierObj.get_maincode());
                    max_price = Double.valueOf(max_pricetemp.equals("") ? "0.0" : max_pricetemp);

                    Double setmenuamount = Double.valueOf(selectedItemModifierObj.getamount().equals("null") ? "0" : selectedItemModifierObj.getamount());

                    if (max_price <= setmenuamount && max_price != 0.0) {
                        //txtamount.setText(String.valueOf(setmenuamount - max_price));
                        setmenuitemamount = (setmenuamount - max_price) * Integer.parseInt(txtqty.getTag().toString());
                    } else {
                        //txtamount.setText("0");
                    }

                }

                if (selectedItemModifierObj.getstatus().equals("modifier") && !isnew) {

                    modifieritemamount += Double.valueOf(selectedItemModifierObj.getamount());


                }

                //Added by SMH on 2017-08-14
                if (dbhelper.GetModifierOrgQty() == true) {
/*			txtamount.setTag((Double.toString((Double.parseDouble(selectedItemModifierObj.getamount()) *
																	Integer.parseInt(txtqty.getTag().toString())
																	+ (setmenuitemamount)))));*/
                    txtamount.setTag(Double.toString(
                            (Double.valueOf(selectedItemModifierObj.getamount()) * Double.parseDouble(txtqty.getTag().toString()))
                                    + setmenuitemamount
                    ));


                } else {
                    txtamount.setTag((Double.toString(Double.valueOf(selectedItemModifierObj.getamount()) + setmenuitemamount)));
                }

                if (selectedItemModifierObj.getstatus().equals("modifier")) {
                    double decimalamount = 0;

                    if (dbhelper.GetModifierOrgQty() == true) {
                        txtamount.setText(getCurrencyFormat(String.valueOf(
                                Double.valueOf(selectedItemModifierObj.getamount()) * Double.parseDouble(txtqty.getTag().toString()))));
                        decimalamount = Double.valueOf(selectedItemModifierObj.getamount()) * Double.parseDouble(txtqty.getTag().toString());
                    } else {
                        txtamount.setText(getCurrencyFormat(String.valueOf(selectedItemModifierObj.getamount())));
                        decimalamount = Double.valueOf(selectedItemModifierObj.getamount());
                    }
                    if (isnew) {
                        modifieritemamount += decimalamount;
                    }
                }


//                amount.setText(getCurrencyFormat(Double.toString((Double.parseDouble((amount
//                        .getTag().toString())) + Double.parseDouble(txtamount
//                        .getTag().toString())))));

//                calculateAmount = Double.parseDouble((amount
//                        .getTag().toString())) + Double.parseDouble(txtamount
//                        .getTag().toString());

                TextView txtitemsr = new TextView(this);
                txtitemsr.setText(selectedItemModifierObj.getitemsr());
                txtitemsr.setPadding(0, 2, 0, 2);
                txtitemsr.setVisibility(View.GONE);

                TextView txtstatus = new TextView(this);
                txtstatus.setText(selectedItemModifierObj.getstatus());
                txtstatus.setPadding(0, 2, 0, 2);
                txtstatus.setVisibility(View.GONE);

                TextView txtsr = new TextView(this);
                txtsr.setVisibility(View.GONE);

                itemlayout.addView(txtsrno, srlayoutpara);
                itemlayout.addView(txtitemname, itemnamelayoutpara);
                itemlayout.addView(txtqtyname, qtylayoutpara);
                itemlayout.addView(txtamount, amountlayoutpara);
                itemlayout.addView(txtitemsr);
                itemlayout.addView(txtstatus);
                itemlayout.addView(txtsr);

                layout.addView(itemlayout, itemlayoutpara);

                //added by EKK on 25-09-2020
                String code = txtitemname.getTag().toString();
                if (dbhelper.isSoldOutItem(code)) {

                    AlertDialog alertDialog = new AlertDialog.Builder(ctx).create();
                    alertDialog.setTitle("Warning");
                    alertDialog.setMessage("Set Item - " + selectedItemModifierObj.getname() + " is sold out item!");
                    alertDialog.setIcon(1);
                    //added by MPPA on 30-12-2020
                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });

                    alertDialog.show();

                }
                //end EKK

            }

            final ScrollView scrollamount = (ScrollView) findViewById(R.id.amountscroll);
            scrollamount.post(new Runnable() {
                public void run() {
                    scrollamount.fullScroll(ScrollView.FOCUS_DOWN);
                }
            });
        }

        calculateAmount += modifieritemamount;
        amount.setText(getCurrencyFormat(Double.toString(calculateAmount)));
        //amount.setTag(calculateAmount);
        // CalculateTotal();
        if (isnew && GlobalClass.use_foodtruck) {
            Submit_Voucher(0);
        }

    }

    public void CreatingqtyDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.activity_quantity_box, null));
        final Dialog dialog = builder.create();

        WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.CENTER;

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(GlobalClass.getDPsize(300, this),
                GlobalClass.getDPsize(400, this));

        dialog.show();
        final TextView txtqty = (TextView) dialog
                .findViewById(R.id.txtqtyamount);

        ((Button) dialog.findViewById(R.id.but1))
                .setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        txtqty.setText(txtqty.getText().toString()
                                + ((Button) v).getText());
                    }
                });

        ((Button) dialog.findViewById(R.id.but2))
                .setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        txtqty.setText(txtqty.getText().toString()
                                + ((Button) v).getText());
                    }
                });

        ((Button) dialog.findViewById(R.id.but3))
                .setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        txtqty.setText(txtqty.getText().toString()
                                + ((Button) v).getText());
                    }
                });

        ((Button) dialog.findViewById(R.id.but4))
                .setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        txtqty.setText(txtqty.getText().toString()
                                + ((Button) v).getText());
                    }
                });

        ((Button) dialog.findViewById(R.id.but5))
                .setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        txtqty.setText(txtqty.getText().toString()
                                + ((Button) v).getText());
                    }
                });

        ((Button) dialog.findViewById(R.id.but6))
                .setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        txtqty.setText(txtqty.getText().toString()
                                + ((Button) v).getText());

                    }
                });

        ((Button) dialog.findViewById(R.id.but7))
                .setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        txtqty.setText(txtqty.getText().toString()
                                + ((Button) v).getText());

                    }
                });

        ((Button) dialog.findViewById(R.id.but8))
                .setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        txtqty.setText(txtqty.getText().toString()
                                + ((Button) v).getText());
                    }
                });

        ((Button) dialog.findViewById(R.id.but9))
                .setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        txtqty.setText(txtqty.getText().toString()
                                + ((Button) v).getText());
                    }
                });

        ((Button) dialog.findViewById(R.id.but0))
                .setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        txtqty.setText(txtqty.getText().toString()
                                + ((Button) v).getText());
                    }
                });

        ((Button) dialog.findViewById(R.id.butok))
                .setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        if (!txtqty.getText().toString().equals(""))
                            Qty = txtqty.getText().toString();
                        dialog.dismiss();
                    }
                });

        ((Button) dialog.findViewById(R.id.butCancel))
                .setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });

        ((Button) dialog.findViewById(R.id.butce))
                .setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        txtqty.setText(null);
                    }
                });

        ((Button) dialog.findViewById(R.id.butbs))
                .setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        String qty = txtqty.getText().toString();
                        if (qty.length() > 0) {
                            qty = qty.substring(0, qty.length() - 1);
                            txtqty.setText(qty);
                        }
                    }
                });

        ((Button) dialog.findViewById(R.id.butdel))
                .setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        String qty = txtqty.getText().toString();
                        if (qty.length() > 0) {
                            qty = qty.substring(1, qty.length());
                            txtqty.setText(qty);
                        }
                    }
                });
    }

    //Added by ArkarMoe on [19/12/2016]
    public void Room_Dialog() {
        // if (saleid != "0") {
        String tranid = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.activity_room, null));
        final Dialog dialog = builder.create();

        WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.RIGHT;

        dialog.show();

        Window window = dialog.getWindow();
        window.setLayout(GlobalClass.getDPsize(970, this), GlobalClass.getDPsize(700, this));
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();


        final TextView txtRoom = (TextView) findViewById(R.id.txtRoom);
        final String dataurl = new DatabaseHelper(this).getServiceURL();

        if (GlobalClass.tmpOffline == false) {
            if (GlobalClass.CheckConnectionForSubmit(ctx)) {

                Integer iColCount = 0;
                LinearLayout row = new LinearLayout(this);

                LinearLayout Rooms = (LinearLayout) dialog
                        .findViewById(R.id.room_grid_layout);
                //	ItemRemark.removeAllViews();


                //((ScrollView)findViewById(R.id.ScrollView)).scrollTo(0, 0);
                dbhelper.LoadRooms(dataurl);
                List<Rooms> Roomslist = dbhelper.getRooms();


                for (int i = 0; i < Roomslist.size(); i++) {

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            GlobalClass.getDPsize(230, this),
                            GlobalClass.getDPsize(50, this));
                    params.setMargins(2, 6, 0, 4);
                    row.addView(CreateRooms(Roomslist.get(i), dialog, txtRoom), params);


                    iColCount++;

                    if (iColCount == 4) {
                        Rooms.addView(row);
                        row = new LinearLayout(this);
                        iColCount = 0;
                    }

                }
                Rooms.addView(row);
            } else {
                showAlertDialog(this, "No Server Connection",
                        "You don't have connection with Server", false);
                return;
            }
        }

        Button btnNotRoom;
        btnNotRoom = ((Button) dialog
                .findViewById(R.id.btnNotRoom));
        btnNotRoom.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {

                dbhelper.UpdateRoomInformation(dbhelper.getServiceURL(), saleid, "", "", "", "0");
                txtRoom.setText(null);

                dialog.dismiss();
            }

        });


    }

    public void singleimageitem() { //Added by ArkarMoe on [09/01/2016]

        RelativeLayout imageitemlayout = (RelativeLayout) findViewById(R.id.singleimagelayout);

        TextView txtcode = (TextView) findViewById(R.id.txtDescription);

        selectItemtolist(txtcode.getTag().toString(), txtcode.getText().toString(), "0", true);
    }

    public void ReserializeSR() {
        LinearLayout itemlistlayout = (LinearLayout) findViewById(R.id.itemlistlayout);
        LinearLayout linearlayout = new LinearLayout(this);
        TextView txtsr;
        int max = 0;
        for (int i = 0; i < itemlistlayout.getChildCount(); i++) {
            linearlayout = (LinearLayout) itemlistlayout.getChildAt(i);
            linearlayout = (LinearLayout) linearlayout.getChildAt(0);
            txtsr = (TextView) linearlayout.getChildAt(0);

            if (!txtsr.getText().equals("")) {
                max = max + 1;
                txtsr.setText(Integer.toString(max) + ".");
            }
        }
        maxsr = max;
    }

    public void CalculateTotal() {
        LinearLayout itemlistlayout = (LinearLayout) findViewById(R.id.itemlistlayout);
        LinearLayout linearlayout = new LinearLayout(this);
        double totalqty = 0.0;
        double totalamount = 0.0;
        double totaltax = 0.0;
        double totalnetamount = 0.0;
        double tax = dbhelper.defGovernmentTax() + dbhelper.defServiceTax();
        for (int i = 0; i < itemlistlayout.getChildCount(); i++) {
            linearlayout = (LinearLayout) itemlistlayout.getChildAt(i);

            if (!((TextView) ((LinearLayout) linearlayout.getChildAt(0))
                    .getChildAt(0)).getTag().toString().equals("C")
                    && !((TextView) ((LinearLayout) linearlayout.getChildAt(0))
                    .getChildAt(0)).getTag().toString().equals("CP")) {

                totalqty = totalqty + Double.parseDouble(((TextView) ((LinearLayout) linearlayout.getChildAt(0)).getChildAt(2)).getTag().toString());

                for (int j = 0; j < linearlayout.getChildCount(); j++) {
                    LinearLayout itemlayout = (LinearLayout) linearlayout.getChildAt(j);
                    if (!((TextView) itemlayout.getChildAt(2)).getText().toString().equals("") && !((TextView) itemlayout.getChildAt(1)).getText().toString().contains("*")) {
                        String amount = ((TextView) itemlayout.getChildAt(3)).getText().toString().replaceAll(",", "");
                        if (amount.equals(""))
                            amount = "0";
                        totalamount = totalamount + Double.parseDouble(amount);
                    }
                }
            }
        }
        //modified by ZYP [26-01-2021] Gov Tax and Ser Tax
        if (((CheckBox) findViewById(R.id.chkTaxfree)).isChecked()) {
            tax = 0.0;
        }
        totaltax = totalamount * (tax / 100);
        if (dbhelper.Tax_Exclusive()) {
            totalnetamount = totalamount + totaltax;
        } else {
            totalnetamount = totalamount;
        }
        String qtytotal = dbhelper.qtyroundto(Double.toString(totalqty));
        String taxpercent = "TAX (" + ((int) tax) + "%)";
        ((TextView) findViewById(R.id.txttotalqty)).setText(qtytotal);
        ((TextView) findViewById(R.id.txttotalamount)).setText(getCurrencyFormat(Double.toString(totalamount)));
        ((TextView) findViewById(R.id.txttax)).setText(taxpercent);
        ((TextView) findViewById(R.id.txttaxamount)).setText(getCurrencyFormat(Double.toString(totaltax)));
        ((TextView) findViewById(R.id.txtnetamount)).setText(getCurrencyFormat(Double.toString(totalnetamount)));

    }

    // added by WaiWL on 19/06/2015
    public void butLoadOrderData_OnClick(View v) {
        if (GlobalClass.CheckConnection(this)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(
                    "Your data will be replaced with the server data.Please print first if you need!")
                    .setCancelable(false)
                    .setPositiveButton("Replace",
                            new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    // TODO Auto-generated method stub

                                    GlobalClass.tmpLoadData = true;
                                    GlobalClass.tmpOffline = false;
                                    if (!tableID.equals("Parcel")) {
                                        dbhelper.LoadActivetableByTableID(
                                                dbhelper.getServiceURL(),
                                                tableID, dbhelper.getwaiterid());

                                        List<Table_Name> tablename = dbhelper
                                                .getActiveTableByTableID(tableID);

                                        saleid = "0";

                                        if (GlobalClass.CheckConnection(ctx)
                                                && tablename.size() > 0) {
                                            GetData(tablename);
                                        }

                                        if (tablename.size() < 1) {
                                            dbhelper.DeleteSaleDataByTableID(tableID);
                                        }
                                    }
                                    useitemclass = dbhelper.getitemviewstyle();

                                    Bind_Data();
                                    GlobalClass.tmpLoadData = false;
                                    GlobalClass.tmpOffline = dbhelper
                                            .getOfflineFlag();
                                    // finish();
                                }
                            })
                    .setNegativeButton("Print",
                            new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    // TODO Auto-generated method stub
                                    dialog.cancel();
                                    salesmen_dialog();
                                    /*
                                     * View butPrint =
                                     * (View)findViewById(R.id.butsubmit);
                                     * butoption_click(butPrint);
                                     */
                                }
                            });
            AlertDialog alert = builder.create();
            alert.setCanceledOnTouchOutside(true);
            alert.show();
        } else {
            showAlertDialog(this, "No Server Connection",
                    "You don't have connection with Server.", false);
        }
    }

    //added by AungMT for MultiLanguage for kitchen
    public void itemdescription_dialog(final int salemenid) {
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
                Submit_withProgressbar(salemenid, 0);
                dialog.dismiss();
            }
        });

        btndescription2.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Submit_withProgressbar(salemenid, 1);
                dialog.dismiss();
            }
        });

        btndescription3.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Submit_withProgressbar(salemenid, 2);
                dialog.dismiss();
            }
        });

    }


    public String getCurrencyFormat(String
                                            value)    //added by ZYP [19-02-2020] for currency format
    {
        DecimalFormat priceFormat = (DecimalFormat) NumberFormat.getNumberInstance(Locale.US);
        String price_parren = "#,##0";

        int pricedecimal = Integer.parseInt(dbhelper.getprice_decimal_places());
        //int pricedecimal= Integer.parseInt(dbhelper.PriceRoundTo());

        //int pricedecimal=2;
        if (pricedecimal > 0) {
            price_parren = price_parren + ".";
            for (int a = 0; a < pricedecimal; a++) {
                price_parren = price_parren + "0";
            }
        }
        priceFormat.applyPattern(price_parren);

        String formatValue = priceFormat.format(Double.parseDouble(value));

        return formatValue;
    }

    // /////////////////////////

    //region delivery entry //added WHM [2020-05-11]
    public void delivery_entry_dialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.activity_delivery_entry_detail, null));
        final Dialog dialog = builder.create();

        WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.CENTER;
        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(GlobalClass.getDPsize(600, this),
                GlobalClass.getDPsize(500, this));

        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        final TextView txt_confirm = (TextView) dialog
                .findViewById(R.id.txt_confirm);
        final TextView txt_close = (TextView) dialog
                .findViewById(R.id.txt_close);
        final Spinner spinner_township = (Spinner) dialog
                .findViewById(R.id.spinner_township);
        final Spinner spinner_agent = (Spinner) dialog
                .findViewById(R.id.spinner_agent);
        final Spinner spinner_deliveryman = (Spinner) dialog
                .findViewById(R.id.spinner_deliveryman);
        final Spinner spinner_payment = (Spinner) dialog
                .findViewById(R.id.spinner_payment);
        final EditText et_estimate_time = (EditText) dialog.findViewById(R.id.et_estimateTime);
        final EditText et_ordertime = (EditText) dialog.findViewById(R.id.et_ordertime);
        final EditText et_custdetail = (EditText) dialog.findViewById(R.id.et_custdetail);
        final EditText et_deliver_remark = (EditText) dialog.findViewById(R.id.et_reamrk);
        final Button but_timepicker = (Button) dialog.findViewById(R.id.but_timepicker);
        final EditText et_delicharges = (EditText) dialog.findViewById(R.id.et_deliCharges);

        final TextView tv_agent = (TextView) dialog.findViewById(R.id.tv_agent);
        final TextView tv_deliMan = (TextView) dialog.findViewById(R.id.tv_deliMan);
        final TextView tv_payment = (TextView) dialog.findViewById(R.id.tv_payment);

        txt_close.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        //region bind data


        //Township
        townshiplist = dbhelper.GetTownshipTable();
        if (townshiplist.size() > 0) {
            township_itemlist = new ArrayList<Delivery_StringWithTags>();
            for (int i = 0; i < townshiplist.size(); i++) {
                TownshipObj t = townshiplist.get(i);
                township_itemlist.add(new Delivery_StringWithTags(t.getName(), t.getTownshipid()));
            }

            township_adapter = new ArrayAdapter<Delivery_StringWithTags>(OrderTaking.this, android.R.layout.simple_spinner_item, township_itemlist);
            township_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_township.setAdapter(township_adapter);
            township_id = (township_itemlist.get(spinner_township.getSelectedItemPosition())).getTag();


        }

        //Agent
        if (deliverytype_id == 2) {
            spinner_agent.setVisibility(View.INVISIBLE);
            tv_agent.setVisibility(View.INVISIBLE);
        }
        //else {
        salementype_list = dbhelper.GetAgentTable();
        if (salementype_list.size() > 0) {
            agent_itemlist = new ArrayList<Delivery_StringWithTags>();
            for (int i = 0; i < salementype_list.size(); i++) {
                Salesmen_TypeObj s = salementype_list.get(i);
                agent_itemlist.add(new Delivery_StringWithTags(s.getName(), s.getSalesmen_type()));
            }

            agent_adapter = new ArrayAdapter<Delivery_StringWithTags>(OrderTaking.this, android.R.layout.simple_spinner_item, agent_itemlist);
            agent_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_agent.setAdapter(agent_adapter);
            agent_id = (agent_itemlist.get(spinner_agent.getSelectedItemPosition())).getTag();
            org_agent_id_position = spinner_agent.getSelectedItemPosition();
        }
        //}


        //DeliveryMan
        if (deliverytype_id == 2) {
            spinner_deliveryman.setVisibility(View.INVISIBLE);
            tv_deliMan.setVisibility(View.INVISIBLE);
        }
        //else{
        deliveryman_list = dbhelper.GetDeliveryMan(agent_id);
        if (deliveryman_list.size() > 0) {
            deliveryman_itemlist = new ArrayList<Delivery_StringWithTags>();
            for (int i = 0; i < deliveryman_list.size(); i++) {
                Salesmen sm = deliveryman_list.get(i);
                deliveryman_itemlist.add(new Delivery_StringWithTags(sm.getsalesmen_name(), sm.getsalesmen_id()));
            }

            deliveryman_adapter = new ArrayAdapter<Delivery_StringWithTags>(OrderTaking.this, android.R.layout.simple_spinner_item, deliveryman_itemlist);
            deliveryman_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_deliveryman.setAdapter(deliveryman_adapter);
            deliveryman_id = (deliveryman_itemlist.get(spinner_deliveryman.getSelectedItemPosition())).getTag();
            org_deliveryman_id_position = spinner_deliveryman.getSelectedItemPosition();
        }
        //}

        //Payment
        if (deliverytype_id == 2) {
            spinner_payment.setVisibility(View.INVISIBLE);
            tv_payment.setVisibility(View.INVISIBLE);
        }
        //else{
        HashMap<Integer, String> hm_deliverypayment = new HashMap<Integer, String>() {{
            put(1, "Cash On Delivery");
            put(2, "Card");
        }};


        List<Delivery_StringWithTags> itemList = new ArrayList<Delivery_StringWithTags>();
        for (int i = 1; i <= hm_deliverypayment.size(); i++) {
            Integer key = i;
            String value = hm_deliverypayment.get(i);

            /* Build the StringWithTag List using these keys and values. */
            itemList.add(new Delivery_StringWithTags(value, key));
        }
        ArrayAdapter<Delivery_StringWithTags> spinnerAdapter = new ArrayAdapter<Delivery_StringWithTags>(OrderTaking.this, android.R.layout.simple_spinner_item, itemList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_payment.setAdapter(spinnerAdapter);
        //spinner_delivery.setSelection(0);
        //spinner_delivery.setTag(0);
        spinner_payment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                delivery_payment = position + 1;
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //}


        deliveryEntryObjList = dbhelper.GetDeliveryEntryTmp(delivery_tranid);
        if (deliveryEntryObjList.size() > 0) {
            //townshipid
            township_id = deliveryEntryObjList.get(0).getTownshipid();
            for (int i = 0; i < township_itemlist.size(); i++) {
                if (township_id == township_itemlist.get(i).getTag()) {
                    spinner_township.setSelection(i);
                    break;
                }
            }

            //payment
            delivery_payment = deliveryEntryObjList.get(0).getDelivery_payment();
            spinner_payment.setSelection(delivery_payment - 1);


            //agentid
            agent_id = deliveryEntryObjList.get(0).getAgent_id();
            for (int i = 0; i < agent_itemlist.size(); i++) {
                if (agent_id == agent_itemlist.get(i).getTag()) {
                    spinner_agent.setSelection(i);
                    break;
                }
            }
            org_agent_id_position = spinner_agent.getSelectedItemPosition();

            //region deliverymanid changed //1
            deliveryman_list = dbhelper.GetDeliveryMan(agent_id);
            deliveryman_itemlist = new ArrayList<Delivery_StringWithTags>();
            if (deliveryman_list.size() > 0) {
                deliveryman_itemlist = new ArrayList<Delivery_StringWithTags>();
                for (int i = 0; i < deliveryman_list.size(); i++) {
                    Salesmen sm = deliveryman_list.get(i);
                    deliveryman_itemlist.add(new Delivery_StringWithTags(sm.getsalesmen_name(), sm.getsalesmen_id()));
                }

                deliveryman_adapter = new ArrayAdapter<Delivery_StringWithTags>(OrderTaking.this, android.R.layout.simple_spinner_item, deliveryman_itemlist);
                deliveryman_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_deliveryman.setAdapter(deliveryman_adapter);
            }
            //endregion deliverymanid changed

            tmp_deliveryman_id = deliveryman_id = deliveryEntryObjList.get(0).getDeliveryman_id();
            for (int i = 0; i < deliveryman_itemlist.size(); i++) {
                if (deliveryman_id == deliveryman_itemlist.get(i).getTag()) {
                    spinner_deliveryman.setSelection(i);
                    break;
                }
            }
            org_deliveryman_id_position = spinner_deliveryman.getSelectedItemPosition();

            //other

            delivery_cust_detail = deliveryEntryObjList.get(0).getOrder_customer_detail();
            delivery_remark = deliveryEntryObjList.get(0).getRemark();
            tmp_estimatetime = estimate_time = deliveryEntryObjList.get(0).getEstimate_time();
            order_time = deliveryEntryObjList.get(0).getOrder_datetime();


            et_custdetail.setText(delivery_cust_detail);
            et_deliver_remark.setText(delivery_remark);

            //region //estimate time to hour
            int hour = estimate_time / 60;
            int minutes = estimate_time % 60;
            String strDateTime = hour + ":" + (minutes < 10 ? "0" + minutes : "" + minutes);
            et_estimate_time.setText(strDateTime);
            et_estimate_time.setTag(estimate_time);
            //endregion estimate time to hour
            if (deliverytype_id == 2) {
                et_delicharges.setEnabled(false);
            } else {
                et_delicharges.setText(getCurrencyFormat(deliveryEntryObjList.get(0).getOrg_deliverycharges()));

            }

            if (order_time.equals("")) {
                String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
                String currentTime2 = currentTime + ":00";
                try {
                    dt_ordertime = ordertime_TimeFormat1.parse(currentTime2);
                    et_ordertime.setText(ordertime_TimeFormat2.format(dt_ordertime));
                    et_ordertime.setTag(currentTime2);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    dt_ordertime = ordertime_TimeFormat1.parse(order_time);
                    et_ordertime.setText(ordertime_TimeFormat2.format(dt_ordertime));
                    et_ordertime.setTag(order_time);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }

        } else {
            String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
            String currentTime2 = currentTime + ":00";
            try {
                dt_ordertime = ordertime_TimeFormat1.parse(currentTime2);
                et_ordertime.setText(ordertime_TimeFormat2.format(dt_ordertime));
                et_ordertime.setTag(currentTime2);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //DeliverySetup
            deliSetup_list = dbhelper.GetDeliverySetupByTownshipid(township_id);
            if (deliSetup_list.size() > 0) {
                //region //estimate time to hour
                estimate_time = deliSetup_list.get(0).getEstimate_time();
                int hour = estimate_time / 60;
                int minutes = estimate_time % 60;
                String strDateTime = hour + ":" + (minutes < 10 ? "0" + minutes : "" + minutes);
                et_estimate_time.setText(strDateTime);
                et_estimate_time.setTag(estimate_time);
                //endregion estimate time to hour
                if (deliverytype_id == 2) {
                    et_delicharges.setEnabled(false);
                } else {
                    et_delicharges.setText(getCurrencyFormat(deliSetup_list.get(0).getDelivery_charges()));

                }
            }
        }
        //endregion //bing data

        //event changed
        //spinner township
        spinner_township.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                township_id = (township_itemlist.get(spinner_township.getSelectedItemPosition())).getTag();
                delivery_anychange = true;

                deliSetup_list = dbhelper.GetDeliverySetupByTownshipid(township_id);
                if (deliSetup_list.size() > 0) {
                    if (tmp_estimatetime != 0) {
                        estimate_time = tmp_estimatetime;
                    } else {
                        estimate_time = deliSetup_list.get(0).getEstimate_time();
                    }
                    //region estimate time to hour
                    int hour = estimate_time / 60;
                    int minutes = estimate_time % 60;
                    String strDateTime = hour + ":" + (minutes < 10 ? "0" + minutes : "" + minutes);
                    et_estimate_time.setText(strDateTime);
                    et_estimate_time.setTag(estimate_time);
                    //endregion estimate time to hour
                    if (deliverytype_id != 2)
                        et_delicharges.setText(getCurrencyFormat(deliSetup_list.get(0).getDelivery_charges()));

                    tmp_estimatetime = 0;
                } else {
                    //region estimate time to hour
                    String strDateTime = "0:00";
                    et_estimate_time.setText(String.valueOf(strDateTime));
                    et_estimate_time.setTag(0);
                    //endregion estimate time to hour
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //spinner agent
        spinner_agent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                agent_id = (agent_itemlist.get(spinner_agent.getSelectedItemPosition())).getTag();
                delivery_anychange = true;

                //region deliverymanid changed
                deliveryman_list = dbhelper.GetDeliveryMan(agent_id);
                deliveryman_itemlist = new ArrayList<Delivery_StringWithTags>();
                if (deliveryman_list.size() > 0) {
                    deliveryman_itemlist = new ArrayList<Delivery_StringWithTags>();
                    for (int i = 0; i < deliveryman_list.size(); i++) {
                        Salesmen sm = deliveryman_list.get(i);
                        deliveryman_itemlist.add(new Delivery_StringWithTags(sm.getsalesmen_name(), sm.getsalesmen_id()));
                    }

                    org_agent_id_position = position;

                    deliveryman_adapter = new ArrayAdapter<Delivery_StringWithTags>(OrderTaking.this, android.R.layout.simple_spinner_item, deliveryman_itemlist);
                    deliveryman_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_deliveryman.setAdapter(deliveryman_adapter);

                    if (tmp_deliveryman_id != 0) {
                        for (int i = 0; i < deliveryman_itemlist.size(); i++) {
                            if (tmp_deliveryman_id == deliveryman_itemlist.get(i).getTag()) {
                                spinner_deliveryman.setSelection(i);
                                break;
                            }
                        }
                        tmp_deliveryman_id = 0;
                    }
                    deliveryman_id = (deliveryman_itemlist.get(spinner_deliveryman.getSelectedItemPosition())).getTag();
                    org_deliveryman_id_position = spinner_deliveryman.getSelectedItemPosition();
                    //endregion deliverymanid changed
                } else {
                    showAlertDialog(ctx, "Warning!",
                            "This agent has not delivery man!",
                            false);
                    spinner_agent.setSelection(org_agent_id_position);
                    spinner_deliveryman.setSelection(org_deliveryman_id_position);
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_deliveryman.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                deliveryman_id = (deliveryman_itemlist.get(spinner_deliveryman.getSelectedItemPosition())).getTag();
                delivery_anychange = true;
                org_deliveryman_id_position = position;
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        txt_confirm.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                //region comment org
//                try {
//                    DeliveryEntry_Confirm();
//                    dialog.dismiss();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
                //endregion comment

                final ProgressDialog progressBar; // Progress
                final Handler progressBarHandler = new Handler();
                // prepare for a progress bar dialog
                progressBar = new ProgressDialog(ctx);
                progressBar.setCancelable(false);
                progressBar.setMessage("Saving ...");
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
                            try {
                                final String[] msg = DeliveryEntry_Confirm();
                                progressBarHandler.post(new Runnable() {
                                    public void run() {
                                        progressBar.dismiss();
                                        if (msg[0].equals("0")) {
                                            dialog.dismiss();
                                        } else {
                                            showAlertDialog(ctx, msg[0], msg[1], false);
                                            dialog.dismiss();
                                        }

                                    }
                                });
                            } catch (NumberFormatException e) {
                                dialog.dismiss();
                            } catch (JSONException e) {
                                dialog.dismiss();
                            } finally {
                                dialog.dismiss();
                            }
                        }
                    };
                    t.start();// start thread
                } catch (Exception ex) {

                } finally {
                }

            }

            private String[] DeliveryEntry_Confirm() throws NumberFormatException, JSONException {

                String[] Msg = {"", ""};
                if (GlobalClass.tmpOffline == false) {

                    township_id = townshiplist.get(spinner_township.getSelectedItemPosition()).getTownshipid();
                    agent_id = salementype_list.get(spinner_agent.getSelectedItemPosition()).getSalesmen_type();
                    deliveryman_id = deliveryman_list.get(spinner_deliveryman.getSelectedItemPosition()).getsalesmen_id();
                    delivery_cust_detail = et_custdetail.getText().toString();
                    delivery_remark = et_deliver_remark.getText().toString();
                    estimate_time = Integer.parseInt(et_estimate_time.getTag().toString());
                    order_time = et_ordertime.getTag().toString();

                    deliSetup_list = dbhelper.GetDeliverySetupByTownshipid(township_id);
                    if (deliSetup_list.size() > 0) {
                        org_deliverycharges = deliSetup_list.get(0).getDelivery_charges();
                        org_delivery_freerange = deliSetup_list.get(0).getFree_range();
                    }

                    // delivery_isDeliver = salementype_list.get(spinner_agent.getSelectedItemPosition()).isDelivery_system();
                    delivery_isDeliver = true;
                    delivery_use_calc_delivery_charges = (deliverytype_id == 2) ? false : salementype_list.get(spinner_agent.getSelectedItemPosition()).isDelivery_charges();
                    delivery_updated = delivery_anychange;


                    DeliveryEntryObj deliveryEntryObj = new DeliveryEntryObj();
                    deliveryEntryObj.setTranid(Integer.parseInt(delivery_tranid));
                    deliveryEntryObj.setTownshipid(township_id);
                    deliveryEntryObj.setOrder_customer_detail(delivery_cust_detail);
                    deliveryEntryObj.setAgent_id(agent_id);
                    deliveryEntryObj.setDeliveryman_id(deliveryman_id);
                    deliveryEntryObj.setUse_calc_delivery_charges(delivery_use_calc_delivery_charges);
                    deliveryEntryObj.setOrder_datetime(order_time);
                    deliveryEntryObj.setEstimate_time(estimate_time);
                    deliveryEntryObj.setRemark(delivery_remark);
                    deliveryEntryObj.setDeliver(delivery_isDeliver);
                    deliveryEntryObj.setOrg_deliverycharges(org_deliverycharges);
                    deliveryEntryObj.setOrg_delviery_freerange(org_delivery_freerange);
                    deliveryEntryObj.setUpdated(delivery_updated);
                    deliveryEntryObj.setDelivery_payment(delivery_payment);

                    dbhelper.UpdateDeliveryEntryTmp_Table(deliveryEntryObj);

                    JSONArray deliveryentry_jsonarray = new JSONArray();

                    JSONObject jsonobj = new JSONObject();
                    // sale_head_main
                    jsonobj.put("tranid", deliveryEntryObj.getTranid());
                    jsonobj.put("townshipid", deliveryEntryObj.getTownshipid());
                    jsonobj.put("order_customer_detail", deliveryEntryObj.getOrder_customer_detail());
                    jsonobj.put("agent_id", deliveryEntryObj.getAgent_id());
                    jsonobj.put("deliveryman_id", deliveryEntryObj.getDeliveryman_id());
                    jsonobj.put("use_calc_delivery_charges", deliveryEntryObj.isUse_calc_delivery_charges());
                    jsonobj.put("order_datetime", deliveryEntryObj.getOrder_datetime());
                    jsonobj.put("estimate_time", deliveryEntryObj.getEstimate_time());
                    jsonobj.put("remark", deliveryEntryObj.getRemark());
                    jsonobj.put("isDeliver", deliveryEntryObj.isDeliver());
                    jsonobj.put("org_deliverycharges", deliveryEntryObj.getOrg_deliverycharges());
                    jsonobj.put("org_delivery_freerange", deliveryEntryObj.getOrg_delviery_freerange());
                    jsonobj.put("updated", deliveryEntryObj.isUpdated());
                    jsonobj.put("delivery_payment", deliveryEntryObj.getDelivery_payment());

                    deliveryentry_jsonarray.put(jsonobj);

                    if (GlobalClass.CheckConnectionForSubmit(ctx)) {
                        Json_class jsonclass = new Json_class();
                        // String URL = ( new DatabaseHelper(this).getServiceURL()+
                        // "/Data/OrderTaking_Entry?orderdata="+java.net.URLEncoder.encode(salejsonarray.toString())+"&orderitemdata="+java.net.URLEncoder.encode(saleitemjsonarray.toString()));
                        JSONArray jsonmessage = jsonclass.getJson(new DatabaseHelper(
                                OrderTaking.this).getServiceURL()
                                + "/Data/UpdateDeliveryEntryTmpTable?orderdata="
                                + java.net.URLEncoder.encode(deliveryentry_jsonarray.toString()));

                        if (jsonmessage.length() > 0) {
                            if (Integer.parseInt(jsonmessage.get(0).toString()) == 0) {
                                String docid = jsonmessage.get(2).toString();
                                String tranid = jsonmessage.get(3).toString();

                                Msg[0] = "0";
                                Msg[1] = "Successful!";
                                return Msg;
                            } else {
                                Msg[0] = "Error Message!";
                                Msg[1] = "Some Error Occur!";
                                return Msg;
                            }
                        }
                    } else {
                        Msg[0] = "Warning!";
                        Msg[1] = "No Connection with Server!";
                        return Msg;
                    }
                } else {
                    Msg[0] = "Warning!";
                    Msg[1] = "Take Offline Mode!";
                    return Msg;
                }
                return Msg;
            }
        });

        //region ordertime picker
        et_ordertime.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                AlertDialog.Builder builder1 = new AlertDialog.Builder(OrderTaking.this);
                // Get the layout inflater
                LayoutInflater inflater1 = OrderTaking.this.getLayoutInflater();

                // Inflate and set the layout for the dialog
                // Pass null as the parent view because its going in the dialog layout
                builder1.setView(inflater1.inflate(R.layout.activity_timepicker,
                        null));
                final Dialog dialog1 = builder1.create();

                WindowManager.LayoutParams wmlp1 = dialog1.getWindow().getAttributes();
                wmlp1.gravity = Gravity.CENTER;

                dialog1.show();
                Window window1 = dialog1.getWindow();

                window1.setLayout(GlobalClass.getDPsize(300, OrderTaking.this),
                        GlobalClass.getDPsize(320, OrderTaking.this));

                dialog1.setCanceledOnTouchOutside(true);
                dialog1.show();

                final Button but_timepicker_save = (Button) dialog1.findViewById(R.id.but_timepicker_save);
                final TimePicker timepicker_ordertime = (TimePicker) dialog1.findViewById(R.id.timepicker_ordertime);

                String[] separated = (et_ordertime.getTag().toString()).split(":");
                int hour = Integer.parseInt(separated[0]);
                int minutes = Integer.parseInt(separated[1]);

                timepicker_ordertime.setCurrentHour(hour);
                timepicker_ordertime.setCurrentMinute(minutes);


                but_timepicker_save.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        String strDateTime = timepicker_ordertime.getCurrentHour() + ":" + timepicker_ordertime.getCurrentMinute() + ":00";
                        try {
                            dt_ordertime = ordertime_TimeFormat1.parse(strDateTime);
                            et_ordertime.setText(ordertime_TimeFormat2.format(dt_ordertime));
                            et_ordertime.setTag(strDateTime);
                            dialog1.dismiss();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }
                });

            }
        });
        //endregion ordertime

        //region estimate time picker
        et_estimate_time.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                AlertDialog.Builder builder1 = new AlertDialog.Builder(OrderTaking.this);
                // Get the layout inflater
                LayoutInflater inflater1 = OrderTaking.this.getLayoutInflater();

                // Inflate and set the layout for the dialog
                // Pass null as the parent view because its going in the dialog layout
                builder1.setView(inflater1.inflate(R.layout.activity_timepicker,
                        null));
                final Dialog dialog1 = builder1.create();

                WindowManager.LayoutParams wmlp1 = dialog1.getWindow().getAttributes();
                wmlp1.gravity = Gravity.CENTER;

                dialog1.show();
                Window window1 = dialog1.getWindow();

                window1.setLayout(GlobalClass.getDPsize(220, OrderTaking.this),
                        GlobalClass.getDPsize(320, OrderTaking.this));

                dialog1.setCanceledOnTouchOutside(true);
                dialog1.show();

                final Button but_timepicker_save = (Button) dialog1.findViewById(R.id.but_timepicker_save);
                final TimePicker timepicker_estimatetime = (TimePicker) dialog1.findViewById(R.id.timepicker_ordertime);

                timepicker_estimatetime.setIs24HourView(true);


                int hour = estimate_time / 60;
                int minutes = estimate_time % 60;

                timepicker_estimatetime.setCurrentHour(hour);
                timepicker_estimatetime.setCurrentMinute(minutes);


                but_timepicker_save.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        int hh = timepicker_estimatetime.getCurrentHour();
                        int mm = timepicker_estimatetime.getCurrentMinute();
                        int estimatetime_minute = hh * 60 + mm;
                        estimate_time = estimatetime_minute;
                        String strDateTime = hh + ":" + (mm < 10 ? "0" + mm : "" + mm);

                        et_estimate_time.setText(strDateTime);
                        et_estimate_time.setTag(estimatetime_minute);

                        dialog1.dismiss();

                    }
                });

            }
        });
        //endregion estimate time picker
    }
    //end delviery entry

    //added by ZYP [16/09/2020]
    public String Get_Curr_Short(int org_curr) {
        String curr_short;
        curr_short = "";
        if (currency_exists) {
            for (int i = 0; i < currencyObjs_list.size(); i++) {
                if (org_curr == currencyObjs_list.get(i).getCurrency()) {
                    curr_short = currencyObjs_list.get(i).getCurr_short() + " ";
                    break;
                }

            }
        }

        return curr_short;
    }

    //added by ZYP [06-01-2021] Multi Invoice dialog
    public void Combobox(final View view, final List<RowItem> rowItems) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.activity_combolist, null));
        final Dialog dialog = builder.create();

        WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.LEFT | Gravity.TOP;

        //final TextView txtinvoice = (TextView) findViewById(buttonid);  // id of clicked button
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        wmlp.x = location[0] - GlobalClass.getDPsize(15, ctx);
        wmlp.y = location[1] + GlobalClass.getDPsize(20, ctx);
        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(GlobalClass.getDPsize(150, this), TableRow.LayoutParams.WRAP_CONTENT);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

        ListView customerlistview = (ListView) dialog.findViewById(R.id.custlist);


        ComboCustomListViewAdapter oddadapter = new ComboCustomListViewAdapter(this, R.layout.combolist_item, rowItems);
        customerlistview.setAdapter(oddadapter);


        customerlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                txtinvoiceno.setText(rowItems.get(position).getdetail());
                saleid = rowItems.get(position).gettranid() + "";

                LinearLayout tmpitemlistlayout = (LinearLayout) findViewById(R.id.itemlistlayout);
                tmpitemlistlayout.removeAllViews();

                Bind_Data();

                dialog.dismiss();
            }
        });
    }

    //added by ZYP [23-11-2020] foodtruck new voucher
    public void getNewVoucher() {
        final AlertDialog alertDialog = new AlertDialog.Builder(this)
                .create();
        alertDialog.setTitle("Warning!");
        alertDialog.setMessage("Are you sure to get new voucher?");
        alertDialog.setIcon(0);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //foodtruck_saleid = dbhelper.getSaleIDForfoodtruck();
                //foodtruck_invNo = dbhelper.getNewInvoice(foodtruck_saleid);
                LinearLayout tmpitemlistlayout = (LinearLayout) findViewById(R.id.itemlistlayout);
                tmpitemlistlayout.removeAllViews();

                saleid = "0";

                Bind_Data();
                CalculateTotal();
            }
        });
        alertDialog.setButton2("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //alertDialog.dismiss();
            }
        });
        alertDialog.show();

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
        final TextView txtCustomer = (TextView) findViewById(R.id.txtcustomer);

        List<SaleObj> saleobj = dbhelper.getSaledataByTableID(Integer.parseInt(saleid));
        edtMemDis.setText(saleobj.get(0).getMembercard());

        btnAddMemDis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String memDis = edtMemDis.getText().toString().trim();
                if (memDis.equals("")) {
                    return;
                }
                if (GlobalClass.CheckConnection(OrderTaking.this)) {
                    String message = dbhelper.AddMemberDiscount(memDis, Integer.parseInt(saleid));
                    if (message.equals("success")) {
                        dbhelper.LoadSaleHeader(dbhelper.getServiceURL(), tableID, saleid);
                        List<SaleObj> saleobjlist = dbhelper.getSaledataByTableID(Integer.parseInt(saleid));
                        txtCustomer.setTag(saleobjlist.get(0).getcustomerid());
                        List<CustomerObj> customerobjlist = dbhelper.getCustomerByCustomerID(saleobjlist.get(0).getcustomerid());
                        if (customerobjlist.size() > 0)
                            txtCustomer.setText(customerobjlist.get(0).getcustomercode()
                                    + " - " + customerobjlist.get(0).getcustomername());

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
