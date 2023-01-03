package com.galaxy.restaurantpos;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NewMenuActivity extends Activity {
    public static Typeface font;
    final DatabaseHelper dbhelper = new DatabaseHelper(this);
    final Context ctx = this;
    Integer[] itemwidth = new Integer[1];
    LinearLayout categorylayout;
    LinearLayout itemlayout;
    ScrollView itemscrolllayout;

    boolean MealType1 = false;
    boolean MealType2 = false;
    boolean MealType3 = false;

    Button activecategory;
    String ClassOrCategory = "Class";
    AutoCompleteTextView txtsearch;

    View activityRootView;
    Button btnCart;

    MenuOrder menuorder;
    public static int tranid = 0;
    public static int userid = 0;

    public static TextView txtmenutoalqty;
    public static TextView txtmenutotalamount;

    public static TextView popupqty;

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String menu_tranid = "tranid";

    //region self order //added WHM [2020-05-18]
    public static boolean use_tablet_selforder_cashierapprove = false; //added WHM [2020-04-20]
    String[] saveddata = new String[2];
    static Boolean use_unit;
    public static int current_sale_tranid = 0;
    public static int current_sale_tableid = 0;
    ArrayList<MenuOrderObj> sale_menuorderlist = new ArrayList<MenuOrderObj>();
    public static int sale_menutranid = 0;
    public static int sale_menuuserid = 0;
    private boolean isunicode = false;

    //added WHM [2020-06-01] self order
    private int sr;


    //endregion //end self order

    //region tablet menu //added WHM [2020-05-21]
    public Boolean use_Tablet_Menu = false;
    //added WHM [2020-05-27]
    String orgExgRate;
    String defexgrate;
    List<CurrencyObj> currencyObjs_list = new ArrayList<CurrencyObj>();
    Boolean currency_exists = false;
    //endregion tablet menu


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_menu);

        // Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

//        if (!LoginActivity.isUnicode)
//            font = Typeface.createFromAsset(getAssets(), "fonts/Pyidaungsu.ttf");
//        else
//            font = Typeface.createFromAsset(getAssets(), "fonts/Zawgyi-One.ttf");

//        isunicode = LoginActivity.isUnicode;

        itemwidth = dbhelper.getTableSize();
        //added WHM
        sr = 0;
        use_tablet_selforder_cashierapprove = dbhelper.use_tabletSelfOrder_CashierApprove();//added WHM [2020-05-18] self order
        //use_Tablet_Menu = dbhelper.use_Tablet_Menu();//added WHM 2020-05-21 tablet_menu
        use_Tablet_Menu = dbhelper.use_Tablet_Menu(dbhelper.getwaiterid()); //modified by EKK on 18-09-2020
        currencyObjs_list = dbhelper.GetCurrency();
        if (currencyObjs_list.size() > 0) {
            currency_exists = true;
        }
        //end whm


        // bind all category buttons and items
        BindCategoryButtons();


        // Cart Button
//		btnCart = (Button) findViewById(R.id.btnCart);
        btnCart = (Button) findViewById(R.id.btnCartNoti);

        btnCart.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                selectedmenuitems_dialog();
            }
        });

        //region tablet menu //added WHM [2020-05-22] tablet menu
        RelativeLayout badge_layout1_btnCart = (RelativeLayout) findViewById(R.id.badge_layout1);
        if (use_Tablet_Menu) {
            badge_layout1_btnCart.setVisibility(View.INVISIBLE);
        }
        //endregon tablet menu


        activityRootView = findViewById(R.id.activityRoot);
        // Auto complete search
        txtsearch = (AutoCompleteTextView) findViewById(R.id.newmenusearchbox);

        List<ItemsObj> itemlist = new ArrayList<ItemsObj>();
        itemlist = dbhelper.SearchItemslistbyDescription("");
        final List<RowItem> rowItems1 = new ArrayList<RowItem>();
        for (int j = 0; j < itemlist.size(); j++) {
            RowItem item = new RowItem(j, Integer.parseInt(itemlist.get(j)
                    .getcode()), itemlist.get(j).getcode(), itemlist.get(j)
                    .getdescription(), "");
            rowItems1.add(item);
        }
        //String[] itemnamearray = new String[itemlist.size()];
        final List<Delivery_StringWithTags> itemnamearray = new ArrayList<Delivery_StringWithTags>();
        //int i = 0;
        for (ItemsObj itemobj : itemlist) {
            //itemnamearray[i] = itemobj.getdescription();
            // itemnamearray[i] = itemobj.getcustomercode() + " - "
            // + itemobj.getcustomername();
            Integer key = Integer.parseInt(itemobj.getcode());
            String value = itemobj.getdescription();
            itemnamearray.add(new Delivery_StringWithTags(value, key));
            //i++;
        }

        final ArrayAdapter<Delivery_StringWithTags> adapter = new ArrayAdapter<Delivery_StringWithTags>(this, android.R.layout.select_dialog_item, itemnamearray);
        txtsearch.setThreshold(1);
        txtsearch.setAdapter(adapter);

        txtsearch.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                // TODO Auto-generated method stub
                //String itemname = adapter.getItem(position);
                String itemcode = (adapter.getItem(position).getTag()).toString();
                // getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                //itemdetail_dialog(dbhelper.getItemidbyitemName(itemname));
                itemdetail_dialog(itemcode);
                hideKeyboard(NewMenuActivity.this);
                txtsearch.setText("");
                getCurrentFocus().clearFocus();
                // getWindow().setFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM,
                // WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);

            }
        });

        tranid = dbhelper.getMenuTranid();
        PosUser posuser = dbhelper.getPosUserByUserID(Integer.parseInt(dbhelper.getwaiterid()));
        userid = posuser.getUserId();

        //added WHM [2020-05-18] self order
        sale_menutranid = tranid;
        sale_menuuserid = userid;

//		popupqty=(TextView)findViewById(R.id.menupopuptext);
        popupqty = (TextView) findViewById(R.id.badge_notification);
        getPouupValue();


        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
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

    public void setKeyboardVisibility(boolean show) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (show) {
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        } else {
            imm.hideSoftInputFromInputMethod(
                    getCurrentFocus().getWindowToken(), 0);
        }
    }

    public void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        imm.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
        // getCurrentFocus().clearFocus();

    }

    private void BindCategoryButtons() {
        String str = GetMealType();
        itemwidth = dbhelper.getTableSize();

        categorylayout = (LinearLayout) findViewById(R.id.menucategorylayout);
        categorylayout.removeAllViews();
        List<SpecialMenuObj> specialmenuobjlist;
        specialmenuobjlist = dbhelper.getSpecialMenuItemCategorylist();
        for (SpecialMenuObj specialmenuObj : specialmenuobjlist) {
            // LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
            // GlobalClass.getDPsize(itemwidth[1], this),
            // GlobalClass.getDPsize(100, ctx));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    GlobalClass.getDPsize(180, ctx), GlobalClass.getDPsize(100, ctx));
            params.weight = 1.0f;
            params.setMargins(0, GlobalClass.getDPsize(2, ctx),
                    GlobalClass.getDPsize(3, ctx), 0);
            categorylayout.addView(CreateSpecialmenuButton(specialmenuObj), params);
        }
        List<ItemCategoryObj> classobjlist = dbhelper.getItemCategorylist(str);

        for (ItemCategoryObj itemClassObj : classobjlist) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    GlobalClass.getDPsize(180, ctx), GlobalClass.getDPsize(100, ctx)); // LayoutParams.FILL_PARENT
            params.weight = 1.0f;
            params.setMargins(0, GlobalClass.getDPsize(2, ctx),
                    GlobalClass.getDPsize(3, ctx), 0);
            categorylayout.addView(CreateCategoryButton(itemClassObj), params);
        }
        if (specialmenuobjlist.size() > 0) // Click First Category
        {
            Button btn = (Button) categorylayout.getChildAt(0);
            activecategory = new Button(this);
            BindItemButton(btn, "Specialmenu Category", str);
        } else {// /////////////
            if (classobjlist.size() > 0) // Click First Category
            {
                Button btn = (Button) categorylayout.getChildAt(0);
                activecategory = new Button(this);
                BindItemButton(btn, "Category", str);
            }
        }
    }

    private Button CreateSpecialmenuButton(SpecialMenuObj specialmenuobj) {
        Button butcategory = new Button(ctx);
        butcategory.setText(specialmenuobj.getMenuName());
        butcategory.setTag(specialmenuobj.getMenuID());
        butcategory.setTextAppearance(ctx, R.style.categorybutton_text);
        // butcategory.setBackgroundColor(Color.parseColor("#070D59"));
        // //#0a59c1 #A5AEFF
        butcategory.setBackgroundResource(R.drawable.roundcategorybutton);
        // roundspecialbutton
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

    private Button CreateCategoryButton(ItemCategoryObj itemcategoryobj) {
        final Button butcategory = new Button(ctx);
        butcategory.setText(itemcategoryobj.getcategoryname());
        butcategory.setTag(itemcategoryobj.getcategoryid());
        butcategory.setTextAppearance(ctx, R.style.categorybutton_text);
        if (itemcategoryobj.getcolorRGB().equals("null")
                || itemcategoryobj.getcolorRGB().equals(null)) {
            // butcategory.setBackgroundColor(Color.parseColor("#1F3C88"));
            // //#a10099
            butcategory.setBackgroundResource(R.drawable.roundcategorybutton);
        } else
            butcategory.setBackgroundColor(Color.parseColor(itemcategoryobj
                    .getcolorRGB()));

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

    private void BindItemButton(Button clickbutton, String classorcategory,
                                String str) {

        itemlayout = (LinearLayout) findViewById(R.id.menuitemlayout);

        //itemscrolllayout = (HorizontalScrollView) findViewById(R.id.menuitemscrollview);
        itemscrolllayout = (ScrollView) findViewById(R.id.menuitemscrollview);

        final LinearLayout.LayoutParams layoutparams = new LinearLayout.LayoutParams(
                LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
        layoutparams.weight = 1.0f;

        itemwidth = dbhelper.getTableSize();

        itemlayout.removeAllViews();

        List<ItemsObj> itemobjlist = new ArrayList<ItemsObj>();

        if (!(activecategory == null)) {
            activecategory.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            activecategory.setGravity(Gravity.CENTER);
            activecategory.setBackgroundResource(R.drawable.roundcategorybutton);
        }

        if (classorcategory == "Category") {
            itemobjlist = dbhelper.getItemslistbycategoryid(clickbutton.getTag().toString(), str);
            activecategory = clickbutton;
            ClassOrCategory = classorcategory;

        } else if (classorcategory == "Specialmenu Category") {
            itemobjlist = dbhelper.getSpecialmenuitemlistbycategoryid(clickbutton.getTag().toString());
            activecategory = clickbutton;
            ClassOrCategory = classorcategory;
        }

        Drawable top = getResources().getDrawable(R.drawable.selected);
        activecategory.setCompoundDrawablesWithIntrinsicBounds(null, top, null,
                null); // set checkmark on category button
        activecategory.setGravity(Gravity.TOP | Gravity.CENTER);
        activecategory.setBackgroundResource(R.drawable.selectedcategorybutton);

        int rowcount = 0;
        int colcount = 0;
        int k = 0;

        //modified WHM [2020-05-28] tabet menu
//		if(dbhelper.getmenuItemViewCount("row").equals("3")) {
//			rowcount = 3 ;
//		}else  if(dbhelper.getmenuItemViewCount("row").equals("4")) {
//			rowcount = 4 ;
//		}else
//			rowcount = 2 ;

        if (dbhelper.getmenuItemViewCount("column").equals("3")) {
            colcount = 3;
        } else if (dbhelper.getmenuItemViewCount("column").equals("4")) {
            colcount = 4;
        } else
            colcount = 2;


        rowcount = itemobjlist.size() / colcount;
        if (itemobjlist.size() % colcount != 0) {
            rowcount = rowcount + 1;
        }

        //LayoutParams params = (LayoutParams) itemlayout.getLayoutParams();
        //params.width = 700;
        //params.height = LayoutParams.WRAP_CONTENT;
        //itemlayout.setLayoutParams(params);


//		final LinearLayout.LayoutParams butparams = new LinearLayout.LayoutParams(
//				GlobalClass.getDPsize(274, ctx), LayoutParams.FILL_PARENT);//org 363

//		final LinearLayout.LayoutParams butparams = new LinearLayout.LayoutParams(
//				GlobalClass.getDPsize(270, ctx),GlobalClass.getDPsize(270, ctx));//org 363

        final LinearLayout.LayoutParams butparams
                = new LinearLayout.LayoutParams(0, 0);//org 363


        final LinearLayout.LayoutParams itemparams;
        //		= new LinearLayout.LayoutParams(layoutparams.WRAP_CONTENT,GlobalClass.getDPsize(198, ctx));//org 363


        if (colcount == 4)
            //itemparams.weight = 0.25f;
            itemparams = new LinearLayout.LayoutParams(GlobalClass.getDPsize(137, ctx), GlobalClass.getDPsize(198, ctx));//org 363
        else if (colcount == 3)
            //itemparams.weight = 0.33f;
            itemparams = new LinearLayout.LayoutParams(GlobalClass.getDPsize(184, ctx), GlobalClass.getDPsize(198, ctx));//org 363
        else
            //itemparams.weight = 0.5f;

            itemparams = new LinearLayout.LayoutParams(GlobalClass.getDPsize(275, ctx), GlobalClass.getDPsize(198, ctx));//org 363


        itemparams.setMargins(0, GlobalClass.getDPsize(2, ctx),
                GlobalClass.getDPsize(3, ctx), 0);
        //itemlayout.setOrientation(LinearLayout.VERTICAL);

        //layoutparams.weight = itemparams.weight;

        //region  column count //modified WHM
        for (int i = 0; i < rowcount; i++) {
            Boolean bind = false;
            LinearLayout layout = new LinearLayout(ctx);
            layout.setOrientation(LinearLayout.HORIZONTAL);
            for (int j = 0; j < colcount; j++) {
                if (k < itemobjlist.size()) {
                    ItemsObj itemobj = itemobjlist.get(k++);
                    layout.addView(CreateItemButton(itemobj), itemparams);
                    //layout.setPadding(1,1,1,1);//added WHM[2020-05-20]
                    bind = true;
                }

            }
            if (bind) {
                itemlayout.addView(layout, layoutparams);
                itemlayout.setBackgroundColor(Color.parseColor("#CEDDEF"));
            }

        }
        //endregion rowcount
        // }

    }

    private LinearLayout CreateItemDetailButton(ItemsObj itemsObj) {

        final LinearLayout.LayoutParams layoutImage = new LinearLayout.LayoutParams(
                GlobalClass.getDPsize(350, ctx), LayoutParams.FILL_PARENT);

        layoutImage.weight = 1.0f;

        final LinearLayout.LayoutParams layouttext = new LinearLayout.LayoutParams(
                GlobalClass.getDPsize(350, ctx), GlobalClass.getDPsize(35, ctx));

        final LinearLayout.LayoutParams layouttext_price = new LinearLayout.LayoutParams(
                GlobalClass.getDPsize(350, ctx), GlobalClass.getDPsize(35, ctx));

        final LinearLayout.LayoutParams layouttext_remark = new LinearLayout.LayoutParams(
                GlobalClass.getDPsize(350, ctx), GlobalClass.getDPsize(35, ctx));

        LinearLayout linlayout = new LinearLayout(ctx);

        linlayout.setOrientation(LinearLayout.VERTICAL);

        TextView txt = new TextView(ctx);
        TextView txt_price = new TextView(ctx);
        TextView txt_remark = new TextView(ctx);
        ImageView image = new ImageView(ctx);

        // For Modifier

        int layoutmodifier_height = use_Tablet_Menu && !itemsObj.getremark().trim().equals("") ? 105 : 70;//added WHM [2020-05-22] tablet menu
        final LinearLayout.LayoutParams layoutmodifier = new LinearLayout.LayoutParams(
                GlobalClass.getDPsize(350, this), GlobalClass.getDPsize(layoutmodifier_height,
                this));//org 450,70 //modidifed WHM [2020-05-22] tablet menu
        // 450 70
        final LinearLayout modifierlayout = new LinearLayout(ctx);
        modifierlayout.setOrientation(LinearLayout.VERTICAL);//org Horizontal //modified WHM [2020-05-22] tablet menu

        if (itemsObj.getusr_code() == null)
            itemsObj.setusr_code("null");


//        String photoPath = GlobalClass.GetImageStorageLocation(this) + "/"
//                + itemsObj.getusr_code().trim() + ".jpg";
//        File file = new File(photoPath);
//
//        if (file.exists()) {
//            try {
//                long length = file.length();
//                if (((length / 1024) / 1024) <= 1) {
//                    BitmapFactory.Options options = new BitmapFactory.Options();
//                    final Bitmap bitmap = BitmapFactory.decodeFile(photoPath,
//                            options);
//                    Drawable d = new BitmapDrawable(getResources(), bitmap);
//                    image.setImageDrawable(d);
//                } else {
//                    image.setVisibility(View.GONE);
//                }
//
//            } catch (Exception ex) {
//            }
//        } else {
//            // String name = "000001";
//            // String photoPath1 = GlobalClass.GetImageStorageLocation(this) +
//            // "/"
//            // + name.trim() + ".jpg";
//            //
//            // File file1 = new File(photoPath1);
//            // try {
//            // long length = file1.length();
//            // if (((length / 1024) / 1024) <= 1) {
//            // BitmapFactory.Options options = new BitmapFactory.Options();
//            // // options.inSampleSize = 8;
//            // final Bitmap bitmap = BitmapFactory.decodeFile(photoPath1,
//            // options);
//            // Drawable d = new BitmapDrawable(getResources(), bitmap);
//            // image.setImageDrawable(d);
//            // } else {
//            // image.setVisibility(8);
//            // }
//            // } catch (Exception ex) {
//            // }
//
//            //added WHM [2020-05-21] default image
//            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.default_menuimage);
//            image.setImageBitmap(bitmap);
//            //image.setVisibility(8);//org commented WHM
//
//        }

        final String dataurl = new DatabaseHelper(this).getServiceURL();
        Picasso.get().load(dataurl + "/Data/GetImage?usr_code=" + itemsObj.getusr_code().trim())
                .placeholder(R.drawable.default_menuimage)
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .error(R.drawable.default_menuimage)
                .into(image);

        image.setScaleType(ScaleType.FIT_XY); // FIT_CENTER
        txt.setText(itemsObj.getdescription());
        txt.setTag(itemsObj.getcode());
        txt.setTextAppearance(ctx, R.style.itemdetailbutton_menu_text);//org itemdetailbutton_text
        txt.setTypeface(font);
        txt.setSingleLine(false);// comment
        txt.setGravity(Gravity.LEFT);// center
        modifierlayout.addView(txt, layouttext);
        //double itemPrice = Double.parseDouble(itemsObj.getsale_price());
//		txt.append(dbhelper.PriceRoundTo(Double.toString(itemPrice))
//				.replace(".00", "").trim());
        String curr_short = Get_Curr_Short(Integer.parseInt(itemsObj.getOrg_curr()));//added WHM [2020-05-27] currency

        txt_price.append(curr_short + "" + dbhelper.getCurrencyFormat(itemsObj.getsale_price()));//modified WHM [2020-05-27] currency
        txt_price.setPadding(5, 5, 0, 0);
        txt_price.setTag(itemsObj.getcode());
        txt_price.setTextAppearance(ctx, R.style.itemdetailbutton_menu_pricetext);//org itemdetailbutton_text
        txt_price.setTypeface(font);
        txt_price.setSingleLine(false);// comment
        txt_price.setGravity(Gravity.LEFT);// center
        modifierlayout.addView(txt_price, layouttext_price);

        //added WHM [2020-05-22] tablet menu remark show
        if (use_Tablet_Menu && !itemsObj.getremark().trim().equals("")) {
            txt_remark.append("( " + itemsObj.getremark() + " )");
            txt_remark.setPadding(5, 5, 0, 0);
            //txt_remark.setTag(itemsObj.getcode());
            txt_remark.setTextAppearance(ctx, R.style.itemdetailbutton_menu_remarktext);
            txt_remark.setTypeface(font);
            txt_remark.setSingleLine(false);// comment
            txt_remark.setGravity(Gravity.LEFT);// center
            modifierlayout.addView(txt_remark, layouttext_remark);
        }
        //end whm

        LayoutParams buttonlayout = new LayoutParams(GlobalClass.getDPsize(40,
                this), GlobalClass.getDPsize(40, this));
        buttonlayout.setMargins(0, 0, 0, 0);

        Button btnplus = new Button(this);
        btnplus.setGravity(Gravity.RIGHT);
        btnplus.setPadding(0, 0, 0, 0);
        btnplus.setBackgroundResource(R.drawable.menu_more); // more_vertical
        btnplus.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                TextView txtitem = (TextView) modifierlayout.getChildAt(0);
                modifierdialog(txtitem);
            }
        });

        List<ItemsObj> extraitemlist = dbhelper
                .getmodifierItemslistbyitemid(itemsObj.getcode().toString());
        if (extraitemlist.size() > 0) {
//			modifierlayout.addView(btnplus, buttonlayout);

//			Editing Modifier Item
        }

        //linlayout.setBackgroundResource(R.drawable.item_button);//org item_button
        linlayout.setBackgroundResource(R.drawable.item_button_menu_detail);//modified WHM [2020-05-21]


        if (itemsObj.getitemcolorRGB() == null
                || itemsObj.getitemcolorRGB().equals("")
                || itemsObj.getitemcolorRGB().equals(null)) {
            linlayout.setBackgroundColor(Color.parseColor("#1F3C88")); // #132238
            // #1F3C88

            // #008000 #364E68
        } else
            linlayout.setBackgroundColor(Color.parseColor(itemsObj
                    .getitemcolorRGB()));


        modifierlayout.setBackgroundColor(Color.parseColor("#f5f5f5"));//added WHM [2020-05-21] tablet menu
        linlayout.setBackgroundColor(Color.parseColor("#f5f5f5"));//added WHM [2020-05-21] tablet menu
        linlayout.setGravity(Gravity.CENTER);

        layoutImage.setMargins(1, 2, 1, 10); //
        linlayout.addView(image, layoutImage);
        linlayout.addView(modifierlayout, layoutmodifier);

        ItemsObj object = dbhelper.getItemsbyitemid(itemsObj.getcode());
        if (Boolean.parseBoolean(object.getissetmenu())) {
            linlayout.setOnClickListener(new OnClickListener() {

                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    TextView txtitem = (TextView) modifierlayout.getChildAt(0);
                    setmenuitemdialog(txtitem);
                }
            });
        }

        return linlayout;

    }

    private LinearLayout CreateItemButton(final ItemsObj itemsObj) {

        // Image layout
        final LinearLayout.LayoutParams layoutImage = new LinearLayout.LayoutParams(
                LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);

        layoutImage.weight = 1.0f;

        // Description Layout
        LinearLayout.LayoutParams descriptionparams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, GlobalClass.getDPsize(80, ctx));//org 270,80
        descriptionparams.weight = 1f;
        // Comment by TTA 210 80
        final LinearLayout deslayout = new LinearLayout(ctx);
        deslayout.setOrientation(LinearLayout.VERTICAL);

        final LinearLayout ratinglayout = new LinearLayout(ctx);
        ratinglayout.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout.LayoutParams desparams = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        LinearLayout.LayoutParams priceparams = new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        priceparams.weight = 1.0f;

        LinearLayout.LayoutParams ratinglayoutparams = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        TextView txtdescription = new TextView(ctx);
        //added by MPPA [26-01-2021] menu sold out
        if (dbhelper.isSoldOutItem(itemsObj.getcode())) {
            txtdescription.setText(itemsObj.getdescription() + "  (Sold Out)");
            txtdescription.setTextAppearance(ctx, R.style.itembuttonmenu_text);
            txtdescription.setTextColor(Color.RED);
        } else {
            txtdescription.setText(itemsObj.getdescription());
            txtdescription.setTextAppearance(ctx, R.style.itembuttonmenu_text);
        }
        //txtdescription.setText(itemsObj.getdescription());
        txtdescription.setPadding(5, 5, 0, 0);
        txtdescription.setTag(itemsObj.getcode());
        //txtdescription.setTextAppearance(ctx, R.style.itembuttonmenu_text);
        txtdescription.setTypeface(font);
        txtdescription.setSingleLine(true);// comment
        txtdescription.setGravity(Gravity.LEFT);// org Left

        deslayout.addView(txtdescription, desparams);

        if (!dbhelper.use_foodtruck(dbhelper.getwaiterid())) {

            //added by MPPA [28-01-2021]
            ImageView star = new ImageView(ctx);
            star.setImageResource(R.drawable.ic_star);
            star.setPadding(0, 0, 5, 0);

            TextView txtrating = new TextView(ctx);
            double rating = Double.parseDouble(itemsObj.getItemrating());
            int ratingcount = Integer.parseInt(itemsObj.getRatingcount());
            ratingcount = ratingcount == 0 ? 1 : ratingcount;
            double rate = rating / ratingcount;
            txtrating.setText(String.valueOf(rate).substring(0, 3));
            txtrating.setGravity(Gravity.RIGHT);
            txtrating.setPadding(0, 0, 10, 0);
            txtrating.setTextAppearance(ctx, R.style.itempricebuttonmenu_text);

            TextView txtprice = new TextView(ctx);
            double price = Double.parseDouble(itemsObj.getsale_price());

            String curr_short = Get_Curr_Short(Integer.parseInt(itemsObj.getOrg_curr()));//added WHM [2020-05-27] currency

            txtprice.setText(curr_short + "" + NumberFormat.getNumberInstance(Locale.US).format(price)); //modifed WHM currency
            txtprice.setPadding(5, 5, 0, 0);
            txtprice.setTag(itemsObj.getcode());
            txtprice.setTextAppearance(ctx, R.style.itempricebuttonmenu_text);
            txtprice.setTypeface(font);
            txtprice.setSingleLine(true);
            txtprice.setGravity(Gravity.LEFT);//org Left
            txtprice.setLayoutParams(priceparams);
            //txtprice.setBackgroundColor(Color.RED);

            //deslayout.addView(txtdescription, desparams);
            ratinglayout.addView(txtprice);
            ratinglayout.addView(star);
            ratinglayout.addView(txtrating);
            deslayout.addView(ratinglayout, ratinglayoutparams);
        } else {

            TextView txtprice = new TextView(ctx);
            double price = Double.parseDouble(itemsObj.getsale_price());

            String curr_short = Get_Curr_Short(Integer.parseInt(itemsObj.getOrg_curr()));//added WHM [2020-05-27] currency

            txtprice.setText(curr_short + "" + NumberFormat.getNumberInstance(Locale.US).format(price)); //modifed WHM currency
            txtprice.setPadding(5, 5, 0, 0);
            txtprice.setTag(itemsObj.getcode());
            txtprice.setTextAppearance(ctx, R.style.itempricebuttonmenu_text);
            txtprice.setTypeface(font);
            txtprice.setSingleLine(true);
            txtprice.setGravity(Gravity.LEFT);//org Left

            //deslayout.addView(txtdescription, desparams);
            deslayout.addView(txtprice);
        }

        // Main Layout
        LinearLayout linlayout = new LinearLayout(ctx);
        linlayout.setOrientation(LinearLayout.VERTICAL);

        ImageView image = new ImageView(ctx);

        if (itemsObj.getusr_code() == null)
            itemsObj.setusr_code("null");
//Commented by KNO(31-05-2022)
//        String photoPath = GlobalClass.GetImageStorageLocation(this) + "/"
//                + itemsObj.getusr_code().trim() + ".jpg";
//        File file = new File(photoPath);
//
//        if (file.exists()) {
//            try {
//                long length = file.length();
//                if (((length / 1024) / 1024) <= 1) {
//                    BitmapFactory.Options options = new BitmapFactory.Options();
//                    // options.inSampleSize = 8;
//                    final Bitmap bitmap = BitmapFactory.decodeFile(photoPath,
//                            options);
//                    Drawable d = new BitmapDrawable(getResources(), bitmap);
//                    image.setImageDrawable(d);
//                } else {
//                    image.setVisibility(View.GONE);
//                }
//
//            } catch (Exception ex) {
//            }
//        } else {
//            //added WHM [2020-05-20] default image
//            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.default_menuimage);
//            image.setImageBitmap(bitmap);
//            //end whm
//            //image.setVisibility(8);
//        }
        final String dataurl = new DatabaseHelper(this).getServiceURL();
        Picasso.get().load(dataurl + "/Data/GetImage?usr_code="
                + itemsObj.getusr_code().trim())// + "&rdn=" + new Random().nextInt())
                .placeholder(R.drawable.default_menuimage)
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .error(R.drawable.default_menuimage)
                .into(image);
        image.setScaleType(ScaleType.CENTER_CROP);
        linlayout.setGravity(Gravity.CENTER);
        layoutImage.setMargins(1, 1, 1, 1);
        linlayout.addView(image, layoutImage);

        // Second layout
//		final LinearLayout.LayoutParams layoutmodifier = new LinearLayout.LayoutParams(
//				GlobalClass.getDPsize(270, this), GlobalClass.getDPsize(70,
//						this));//360,70 //org
        final LinearLayout.LayoutParams layoutmodifier = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT, GlobalClass.getDPsize(70,
                this));//360,70

        final LinearLayout modifierlayout = new LinearLayout(ctx);
        modifierlayout.setOrientation(LinearLayout.HORIZONTAL);
        // Add description and price
        modifierlayout.addView(deslayout, descriptionparams);

        //region cart layount
//		final LinearLayout.LayoutParams cartlayout = new LinearLayout.LayoutParams(
//				GlobalClass.getDPsize(40, ctx), GlobalClass.getDPsize(40, ctx));
//
//		cartlayout.setMargins(5, 10, 5, 5);
//		Button btncart = new Button(this);
//		btncart.setGravity(Gravity.CENTER);
//		btncart.setBackgroundResource(R.drawable.menu_addtocart);
//		btncart.setOnClickListener(new OnClickListener() {
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				TextView txt = (TextView) deslayout.getChildAt(1);
//				// Toast.makeText(NewMenuActivity.this, "add to cart",
//				// Toast.LENGTH_SHORT).show();
//			}
//		});

        // add cart button
        // modifierlayout.addView(btncart, cartlayout); //Comment By TTA
        //endregion cart layout

        // Modifier Button
        //region button modifier
//		LayoutParams buttonlayout = new LayoutParams(GlobalClass.getDPsize(50,
//				this), GlobalClass.getDPsize(50, this));
//		buttonlayout.setMargins(20, 5, 5, 5);
//
//		Button btnmodifier = new Button(this);
//		btnmodifier.setGravity(Gravity.RIGHT);
//		btnmodifier.setPadding(0, 10, 0, 0);
//		btnmodifier.setBackgroundResource(R.drawable.menu_more); // TTA
//																	// more_details
//		btnmodifier.setOnClickListener(new View.OnClickListener() {
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				TextView txtitem = (TextView) deslayout.getChildAt(0);
//				// modifierdialog(txtitem);
//				itemmodifier_dialog(txtitem);
//			}
//		});
//
//		List<ItemsObj> extraitemlist = dbhelper
//				.getmodifierItemslistbyitemid(itemsObj.getcode().toString());
//		if (extraitemlist.size() > 0) {
////			modifierlayout.addView(btnmodifier, buttonlayout);
//
////			Editing Modifier Items
//		}
//
//		if (itemsObj.getitemcolorRGB() == null
//				|| itemsObj.getitemcolorRGB().equals("")
//				|| itemsObj.getitemcolorRGB().equals(null)) {
//			linlayout.setBackgroundColor(Color.parseColor("#1F3C88"));
//		} else
//			linlayout.setBackgroundColor(Color.parseColor(itemsObj
//					.getitemcolorRGB()));
//
        //endregion button modifier

        // For Set Menu
        ItemsObj object = dbhelper.getItemsbyitemid(itemsObj.getcode());
        if (Boolean.parseBoolean(object.getissetmenu())) {

            LayoutParams setlayout = new LayoutParams(GlobalClass.getDPsize(30,
                    this), GlobalClass.getDPsize(30, this));
            setlayout.setMargins(20, 5, 5, 5);
            setlayout.gravity = Gravity.RIGHT;

            Button btnsetmenu = new Button(this);
            btnsetmenu.setGravity(Gravity.RIGHT);
            btnsetmenu.setPadding(0, 10, 0, 0);
            btnsetmenu.setBackgroundResource(R.drawable.list64); // TTA
            // menu_more
            btnsetmenu.setOnClickListener(new OnClickListener() {

                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    TextView txtitem = (TextView) deslayout.getChildAt(0);
                    setmenuitemdialog(txtitem);
                }
            });


            //if (Boolean.parseBoolean(object.getissetmenu())) {
            // Add set menu icon
            modifierlayout.addView(btnsetmenu, setlayout);

        }

        linlayout.addView(modifierlayout, layoutmodifier);
        linlayout.setBackgroundResource(R.drawable.item_button);
        if (itemsObj.getitemcolorRGB() == null
                || itemsObj.getitemcolorRGB().equals("")
                || itemsObj.getitemcolorRGB().equals(null)) {
            linlayout.setBackgroundColor(Color.parseColor("#f5f5f5")); // #1F3C88
        } else
            linlayout.setBackgroundColor(Color.parseColor(itemsObj
                    .getitemcolorRGB()));

        linlayout.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (dbhelper.isSoldOutItem(itemsObj.getcode())) {
                    AlertDialog alertDialog = new AlertDialog.Builder(ctx).create();
                    alertDialog.setTitle("Warning");
                    alertDialog.setMessage("This item is sold out!");
                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    alertDialog.show();
                } else {
                    TextView txtitem = (TextView) deslayout.getChildAt(0);
                    itemdetail_dialog(txtitem.getTag().toString());
                }
            }
        });
        return linlayout;


    }

    private LinearLayout CreateItemButtonWithNewImage(ItemsObj itemsObj) {
        LinearLayout linlayout = new LinearLayout(ctx);
        linlayout.setOrientation(LinearLayout.HORIZONTAL);

        final LinearLayout.LayoutParams layoutImage = new LinearLayout.LayoutParams(
                LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
        layoutImage.weight = 1.0f;

        ImageView image = new ImageView(ctx);
        image.setScaleType(ScaleType.FIT_XY);

        String photoPath = GlobalClass.GetImageStorageLocation(this) + "/"
                + itemsObj.getusr_code().trim() + ".jpg";
        File file = new File(photoPath);
        if (file.exists()) {
            try {
                long length = file.length();
                if (((length / 1024) / 1024) <= 1) {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    // options.inSampleSize = 8;
                    final Bitmap bitmap = BitmapFactory.decodeFile(photoPath,
                            options);
                    Drawable d = new BitmapDrawable(getResources(), bitmap);
                    image.setImageDrawable(d);
                } else {
                    image.setVisibility(View.GONE);
                }

            } catch (Exception ex) {
            }
        } else {
            image.setVisibility(View.GONE);
        }
        linlayout.addView(image, layoutImage);

        return linlayout;
    }

    private LinearLayout CreateItemButtonwithImage(ItemsObj itemsObj) {
        final LinearLayout.LayoutParams layoutImage = new LinearLayout.LayoutParams(
                LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
        layoutImage.weight = 1.0f;

        final LinearLayout.LayoutParams layouttext = new LinearLayout.LayoutParams(
                GlobalClass.getDPsize(210, ctx), GlobalClass.getDPsize(70, ctx));
        // 170 30/ 160 60

        LinearLayout linlayout = new LinearLayout(ctx);
        linlayout.setOrientation(LinearLayout.VERTICAL);

        TextView txt = new TextView(ctx);
        ImageView image = new ImageView(ctx);

        // For Modifier
        final LinearLayout.LayoutParams layoutmodifier = new LinearLayout.LayoutParams(
                GlobalClass.getDPsize(360, this), GlobalClass.getDPsize(70,
                this));

        // 200 60
        final LinearLayout modifierlayout = new LinearLayout(ctx);
        modifierlayout.setOrientation(LinearLayout.HORIZONTAL);

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
                    // options.inSampleSize = 8;
                    final Bitmap bitmap = BitmapFactory.decodeFile(photoPath,
                            options);
                    Drawable d = new BitmapDrawable(getResources(), bitmap);
                    image.setImageDrawable(d);
                } else {
                    image.setVisibility(View.GONE);
                }

            } catch (Exception ex) {
            }
        } else {
            // String name = "000001";
            // String photoPath1 = GlobalClass.GetImageStorageLocation(this) +
            // "/"
            // + name.trim() + ".jpg";
            // File file1 = new File(photoPath1);
            // try {
            // long length = file1.length();
            // if (((length / 1024) / 1024) <= 1) {
            // BitmapFactory.Options options = new BitmapFactory.Options();
            // // options.inSampleSize = 8;
            // final Bitmap bitmap = BitmapFactory.decodeFile(photoPath1,
            // options);
            // Drawable d = new BitmapDrawable(getResources(), bitmap);
            // image.setImageDrawable(d);
            // } else {
            // image.setVisibility(8);
            // }
            // } catch (Exception ex) {
            // }
            //added WHM [2020-05-02] default image
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.default_menuimage);
            image.setImageBitmap(bitmap);

            //image.setVisibility(8);//org commented whm
        }

        // image.setScaleType(ScaleType.FIT_XY);
        image.setScaleType(ScaleType.FIT_XY); // FIT_CENTER
        txt.setText(itemsObj.getdescription());
        txt.append("\n");
        double itemPrice = Double.parseDouble(itemsObj.getsale_price());

        // txt.append(dbhelper.PriceRoundTo(Double.toString(itemPrice))
        // .replace(".00", "").trim());

        String formatprice = NumberFormat.getNumberInstance(Locale.US).format(
                itemPrice);
        txt.append(formatprice);
        txt.setPadding(5, 5, 0, 0);
        txt.setTag(itemsObj.getcode());
        txt.setTextAppearance(ctx, R.style.itembutton_text);
        txt.setTypeface(font);
        txt.setSingleLine(false);// comment
        txt.setGravity(Gravity.LEFT);// center

        // final TextView txtmodifier=new TextView(ctx);
        // txtmodifier.setText(itemsObj.getdescription());
        // txtmodifier.setTag(itemsObj.getcode());
        // txtmodifier.setSingleLine(true);

        modifierlayout.addView(txt, layouttext);

        // cart
        final LinearLayout.LayoutParams cartlayout = new LinearLayout.LayoutParams(
                GlobalClass.getDPsize(40, ctx), GlobalClass.getDPsize(40, ctx));

        cartlayout.setMargins(5, 10, 5, 5);
        Button btncart = new Button(this);
        btncart.setGravity(Gravity.CENTER);
        btncart.setBackgroundResource(R.drawable.menu_addtocart);
        btncart.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub

            }
        });

        modifierlayout.addView(btncart, cartlayout);

        // Modifier Button
        LayoutParams buttonlayout = new LayoutParams(GlobalClass.getDPsize(50,
                this), GlobalClass.getDPsize(40, this));
        buttonlayout.setMargins(20, 5, 5, 5);

        Button btnplus = new Button(this);
        btnplus.setGravity(Gravity.RIGHT);
        btnplus.setPadding(0, 0, 0, 0);
        btnplus.setBackgroundResource(R.drawable.menu_more); // more_vertical
        btnplus.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                TextView txtitem = (TextView) modifierlayout.getChildAt(0);
                modifierdialog(txtitem);
            }
        });

        List<ItemsObj> extraitemlist = dbhelper
                .getmodifierItemslistbyitemid(itemsObj.getcode().toString());
        if (extraitemlist.size() > 0) {
            modifierlayout.addView(btnplus, buttonlayout);
        }

        linlayout.setBackgroundResource(R.drawable.item_button);

        if (itemsObj.getitemcolorRGB() == null
                || itemsObj.getitemcolorRGB().equals("")
                || itemsObj.getitemcolorRGB().equals(null)) {
            linlayout.setBackgroundColor(Color.parseColor("#1F3C88")); // #132238
            // #1F3C88
            // #008000 #364E68
        } else
            linlayout.setBackgroundColor(Color.parseColor(itemsObj
                    .getitemcolorRGB()));

        linlayout.setGravity(Gravity.CENTER);
        layoutImage.setMargins(1, 1, 1, 1); //
        linlayout.addView(image, layoutImage);
        linlayout.addView(modifierlayout, layoutmodifier);
        // linlayout.addView(txt, layouttext);

        ItemsObj object = dbhelper.getItemsbyitemid(itemsObj.getcode());
        if (Boolean.parseBoolean(object.getissetmenu())) {
            linlayout.setOnClickListener(new OnClickListener() {

                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    TextView txtitem = (TextView) modifierlayout.getChildAt(0);
                    // String code=txtitem.getText().toString();
                    // Toast.makeText(NewMenuActivity.this,code,Toast.LENGTH_LONG).show();
                    setmenuitemdialog(txtitem);
                }
            });
        }

        // if(Boolean.parseBoolean(itemsObj.getissetmenu())){
        // linlayout.setOnClickListener(new OnClickListener() {
        //
        // public void onClick(View v) {
        // // TODO Auto-generated method stub
        // Toast.makeText(NewMenuActivity.this,"Wait a minute",Toast.LENGTH_LONG).show();
        // TextView txtitem = (TextView) modifierlayout.getChildAt(0);
        // // setmenuitemdialog(txtitem);
        // }
        // });
        // }

        return linlayout;
    }

    private void itemdetail_dialog(String itemname) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setInverseBackgroundForced(true);
        LayoutInflater inflater = this.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.activity_menuitemdetails,
                null));
        final Dialog dialog = builder.create();

        WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.CENTER;
        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(GlobalClass.getDPsize(440, this),
                LayoutParams.MATCH_PARENT);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

        List<ItemsObj> extraitemlist = new ArrayList<ItemsObj>();
        // extraitemlist = dbhelper.SearchItemslistbyDescription(itemname);

        extraitemlist = dbhelper.getItemsbyCode(itemname);

        final LinearLayout searchitemlayout = (LinearLayout) dialog
                .findViewById(R.id.searchedmenulist);
        searchitemlayout.removeAllViews();

        //added WHM [2020-05-21] tablet_menu
        final RelativeLayout relativeLayout_button = (RelativeLayout) dialog
                .findViewById(R.id.relative_layout_button);


        final Button btnCancel = (Button) dialog
                .findViewById(R.id.detailbutcancelmodifier);
        btnCancel.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });

        final Button btnAddtocart = (Button) dialog.findViewById(R.id.detailbutOkmodifier);

        final ItemsObj obj = extraitemlist.get(0);
        final String code = obj.getcode();
        final String isSetMenu = obj.getissetmenu(); //added WHM [2020-06-01] self order
        final String usr_code = obj.getusr_code();//added MPPA [2021-03-12] item rating
        final boolean[] isRate = {false};
        //Added by MPPA on [02-02-2021]
        final RatingBar itemrating = (RatingBar) dialog.findViewById(R.id.itemrating);
        itemrating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                double rate = dbhelper.getItemrating(usr_code);
                rate += (Double.parseDouble(String.valueOf(rating)));
                dbhelper.updateItemrating(usr_code, rate);
                isRate[0] = true;
            }
        });

        if (!dbhelper.use_foodtruck(dbhelper.getwaiterid())) {
            itemrating.setVisibility(View.VISIBLE);
            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                public void onDismiss(final DialogInterface arg0) {
                    // do something
                    //added by MPPA [28-01-2021]
//                    double rate = dbhelper.getItemrating(code);
//                    rate += (Double.parseDouble(String.valueOf(itemrating.getRating())));
//                    dbhelper.updateItemrating(code, rate);
//                    // bind all category buttons and items
                    if (isRate[0]) {
                        BindCategoryButtons();
                    }

                }
            });
        } else {
            itemrating.setVisibility(View.GONE);
        }


        final TextView txtmodifierqty = new TextView(ctx);

        if (extraitemlist.size() > 0) {
            final LinearLayout.LayoutParams butparams = new LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.FILL_PARENT);

            //added WHM [2020-05-22] tablet menu
            int linparams_height = 250;
            if (use_Tablet_Menu) {
                linparams_height = 330;
            }
            //end tablet menu [2020-05-22]

            final LinearLayout.LayoutParams linparams;
            linparams = new LinearLayout.LayoutParams(
                    GlobalClass.getDPsize(400, ctx), GlobalClass.getDPsize(linparams_height,
                    ctx));//org 450,250 //modified WHM [2020-05-22] tablet menu
            // 500 300

            ItemsObj obj2 = dbhelper.getItemsbyitemid(code);

            TextView txtCategoryName = (TextView) dialog.findViewById(R.id.txtSetMenuItemName);
            txtCategoryName.setTypeface(font);//modified WHM [2020-05-22]
            txtCategoryName.setText(obj2.getcategoryname());
            //txtCategoryName.append("\n");
            //txtCategoryName.setTypeface(font);//org
            searchitemlayout.addView(CreateItemDetailButton(obj2), linparams);


            LinearLayout qtylayout = new LinearLayout(ctx);
            qtylayout.setOrientation(LinearLayout.HORIZONTAL);
            qtylayout.setPadding(0, 0, 0, 0); // 150 10
            qtylayout.setGravity(Gravity.CENTER);

            LinearLayout.LayoutParams qtyparams = new LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

            LayoutParams modifierqtylayout = new LayoutParams(
                    GlobalClass.getDPsize(67, ctx), LayoutParams.MATCH_PARENT);

            LayoutParams buttonlayout = new LayoutParams(GlobalClass.getDPsize(
                    50, ctx), GlobalClass.getDPsize(50, ctx));


            LayoutParams checkboxlayout = new LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);

            // final TextView txtmodifierqty = new TextView(ctx);
            txtmodifierqty.setText("1");
            txtmodifierqty.setPadding(0, 0, 0, 2);
            txtmodifierqty.setGravity(Gravity.CENTER);
            txtmodifierqty.setTextSize(TypedValue.COMPLEX_UNIT_SP, 23);
            txtmodifierqty.setMinWidth(30);
            txtmodifierqty.setTextColor(Color.parseColor("#0000FF"));

            Button btnplus = new Button(ctx);
            btnplus.setBackgroundResource(R.drawable.squareplus);
            btnplus.setPadding(0, 0, 0, 2);
            btnplus.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    int qty = Integer.parseInt(txtmodifierqty.getText()
                            .toString());
                    qty = qty + 1;
                    txtmodifierqty.setText(Integer.toString(qty));
                }
            });

            Button btnminus = new Button(ctx);
            btnminus.setBackgroundResource(R.drawable.squareminus);
            btnminus.setPadding(0, 0, 0, 2);
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

            qtylayout.addView(btnminus, buttonlayout);
            qtylayout.addView(txtmodifierqty, modifierqtylayout);
            qtylayout.addView(btnplus, buttonlayout);

            //added WHM [2020-05-21] tablet menu
            if (!use_Tablet_Menu) {
                searchitemlayout.addView(qtylayout, qtyparams);
            }


        }
        btnAddtocart.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                final MenuOrderObj menuitem = new MenuOrderObj();
                int orderid = dbhelper.getMenuOrderId();
                menuitem.setTranid(String.valueOf(tranid));
                menuitem.setUserid(String.valueOf(userid));
                menuitem.setOrderid(String.valueOf(orderid));
                menuitem.setCode(code);
                menuitem.setUsr_code(obj.getusr_code());
                menuitem.setDescription(obj.getdescription());
                menuitem.setSale_price(dbhelper.getSalePriceByItemcode(code));
                menuitem.setQty(txtmodifierqty.getText().toString());
                menuitem.setIsnew(true);
                menuitem.setIsSetMenu(isSetMenu);//added WHM [2020-06-01] self order

                final Toast toast = Toast.makeText(NewMenuActivity.this, "", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 10);

                if (dbhelper.checkOrderExist(tranid, userid, code)) {
                    int qty = dbhelper.getOrderQtyByCode(tranid, userid, code);
                    qty += Integer.valueOf(txtmodifierqty.getText().toString());
                    dbhelper.updateOrderQtyWithCode(tranid, userid, code, String.valueOf(qty));

                    toast.setText("Quantity of " + menuitem.getDescription() + " is updated.");
                    View view = toast.getView();
                    TextView text = (TextView) view.findViewById(android.R.id.message);
                    view.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
                    text.setTextColor(Color.BLACK);
                    text.setTextSize(15);
                    text.setTypeface(font);
                    toast.show();

                } else {
                    //added WHM [2020-06-01] self order
                    sr++;
                    menuitem.setSr(String.valueOf(sr));
                    //end
                    dbhelper.insert_menuorderitem(menuitem);
                    toast.setText("	" + menuitem.getDescription() + " is added to cart.");
                    View view = toast.getView();
                    TextView text = (TextView) view.findViewById(android.R.id.message);
                    view.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
                    text.setTextColor(Color.BLACK);
                    text.setTextSize(15);
                    text.setTypeface(font);
                    toast.show();
                }


//				dbhelper.insert_menuorderitem(menuitem);
//				Toast.makeText(NewMenuActivity.this,
//						menuitem.getDescription() + " is added to order list!",
//						Toast.LENGTH_SHORT).show();

                dialog.dismiss();
                getPouupValue();
            }
        });

        //added WHM [2020-05-21] tablet menu
        if (use_Tablet_Menu) {
            relativeLayout_button.removeAllViews();
        }

        // Editing Section

        // final ListView
        // menulist=(ListView)dialog.findViewById(R.id.searchresultmenulist);
        // final List<RowItem> rowItems1 = new ArrayList<RowItem>();
        // for (int j = 0; j < itemlist.size(); j++) {
        // RowItem item = new RowItem(j,
        // Integer.parseInt(itemlist.get(j)
        // .getcode()), itemlist
        // .get(j).getcode(), itemlist
        // .get(j).getdescription(),"");
        // rowItems1.add(item);
        // }
        //
        // CustomerCustomListViewAdapter allitemadapter = new
        // CustomerCustomListViewAdapter(ctx,
        // R.layout.customerlist_item,rowItems1);
        // menulist.setAdapter(allitemadapter);

        // final LinearLayout
        // searchitemlayout=(LinearLayout)dialog.findViewById(R.id.searchedmenulist);
        // searchitemlayout.removeAllViews();
        //
        // LayoutParams modifieditemlayout = new LayoutParams(
        // LayoutParams.MATCH_PARENT, GlobalClass.getDPsize(70, this));
        //
        // LayoutParams modifieritemlayout = new LayoutParams(
        // GlobalClass.getDPsize(250, this), LayoutParams.MATCH_PARENT);
        //
        // LayoutParams pricelayout = new LayoutParams(GlobalClass.getDPsize(
        // 100, this), LayoutParams.MATCH_PARENT);
        //
        // LayoutParams dividerlayout = new LayoutParams(
        // LayoutParams.FILL_PARENT, 1);

        // for (ItemsObj itemsObj : extraitemlist){
        // LinearLayout itemlist = new LinearLayout(this);
        //
        // TextView txtmodifieritem = new TextView(this);
        // txtmodifieritem.setTextColor(Color.parseColor("#0000FF"));
        // txtmodifieritem.setText(itemsObj.getdescription());
        // txtmodifieritem.setTag(itemsObj.getcode());
        // txtmodifieritem.setGravity(Gravity.CENTER | Gravity.LEFT);
        // txtmodifieritem.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        // txtmodifieritem.setTypeface(font);
        //
        // TextView txtprice = new TextView(this);
        //
        // String price = itemsObj.getsale_price();
        // if (price.equals("") || price.equals("null")) {
        // price = "0";
        // }
        // price = Integer.toString((int) (Double.parseDouble(price)));
        //
        // txtprice.setText("Price - " + price);
        // txtprice.setGravity(Gravity.CENTER_VERTICAL);
        // txtprice.setTextColor(Color.parseColor("#0000FF"));
        //
        // itemlist.addView(txtmodifieritem, modifieritemlayout);
        // itemlist.addView(txtprice, pricelayout);
        //
        // searchitemlayout.addView(itemlist, modifieditemlayout);
        //
        // TextView txtline = new TextView(this);
        // txtline.setBackgroundColor(Color.parseColor("#000000"));
        // searchitemlayout.addView(txtline, dividerlayout);
        // }
    }

    private void setmenuitemdialog(View v) {
        List<SelectedItemModifierObj> extraitemlist = dbhelper
                .getsetmenuItemslistbyitemid(((TextView) v).getTag().toString());
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setInverseBackgroundForced(true);
        LayoutInflater inflater = this.getLayoutInflater();

        if (extraitemlist.size() > 0) {
            builder.setView(inflater.inflate(
                    R.layout.activity_setmenuitemlistview, null));
            final Dialog dialog = builder.create();

            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int displayWidth = displayMetrics.widthPixels;
            int displayHeight = displayMetrics.heightPixels;
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(dialog.getWindow().getAttributes());
            int dialogWindowWidth = (int) (displayWidth * 0.7f);
            int dialogWindowHeight = (int) (displayHeight * 0.7f);
            layoutParams.width = dialogWindowWidth;
            layoutParams.height = dialogWindowHeight;
            layoutParams.gravity = Gravity.RIGHT;

            dialog.getWindow().setAttributes(layoutParams);

            // WindowManager.LayoutParams wmlp = dialog.getWindow()
            // .getAttributes();
            // wmlp.gravity = Gravity.RIGHT;//

            // Window window = dialog.getWindow();
            // window.setLayout(800, 800);
            dialog.setCanceledOnTouchOutside(true);
            dialog.show();

            final LinearLayout extraitemlayout = (LinearLayout) dialog
                    .findViewById(R.id.setmenuitemlist);

            extraitemlayout.removeAllViews();
            // extraitemlayout.setBackgroundColor(Color.parseColor("#d9522c"));
            LayoutParams modifieditemlayout = new LayoutParams(
                    LayoutParams.MATCH_PARENT, GlobalClass.getDPsize(70, this));

            LayoutParams modifieritemlayout = new LayoutParams(
                    GlobalClass.getDPsize(300, this), LayoutParams.MATCH_PARENT);

            LayoutParams pricelayout = new LayoutParams(GlobalClass.getDPsize(
                    120, this), LayoutParams.MATCH_PARENT);

            LayoutParams dividerlayout = new LayoutParams(
                    LayoutParams.FILL_PARENT, 1);

            TextView txtitemname = (TextView) dialog
                    .findViewById(R.id.txtSetMenuItemName);
            ItemsObj object = dbhelper.getItemsbyitemid(((TextView) v).getTag()
                    .toString());

            txtitemname.setText("Set Items(" + object.getdescription() + ")");
            txtitemname.setTypeface(font);

            for (SelectedItemModifierObj itemsObj : extraitemlist) {
                LinearLayout itemlist = new LinearLayout(this);

                TextView txtmodifieritem = new TextView(this);
                txtmodifieritem.setTextColor(Color.parseColor("#0000FF"));
                //txtmodifieritem.setText(Dictionary.Uniconverter.convert(itemsObj.getname()));
                txtmodifieritem.setText(itemsObj.getname()); //modified by EKK on 11-12-2020
                txtmodifieritem.setTag(itemsObj.getitemid());
                txtmodifieritem.setGravity(Gravity.CENTER | Gravity.LEFT);
                txtmodifieritem.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                txtmodifieritem.setTypeface(font);

                TextView txtprice = new TextView(this);

                String price = itemsObj.get_max_price();
                if (price.equals("") || price.equals("null")) {
                    price = "0";
                }
                // price = Integer.toString((int) (Double.parseDouble(price)));

                txtprice.setText("Price - " + price);
                txtprice.setGravity(Gravity.CENTER_VERTICAL);
                txtprice.setTextColor(Color.parseColor("#0000FF"));

                itemlist.addView(txtmodifieritem, modifieritemlayout);
                itemlist.addView(txtprice, pricelayout);

                extraitemlayout.addView(itemlist, modifieditemlayout);

                TextView txtline = new TextView(this);
                txtline.setBackgroundColor(Color.parseColor("#000000"));
                extraitemlayout.addView(txtline, dividerlayout);
            }
        } else
            Toast.makeText(NewMenuActivity.this, "No other items for this set",
                    Toast.LENGTH_SHORT).show();

    }

    private void itemmodifier_dialog(View v) {
        List<ItemsObj> extraitemlist = dbhelper
                .getmodifierItemslistbyitemid(((TextView) v).getTag()
                        .toString());

        if (extraitemlist.size() > 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setInverseBackgroundForced(true);
            // Dialog builder=new Dialog(this);

            // Get the layout inflater
            LayoutInflater inflater = this.getLayoutInflater();

            builder.setView(inflater.inflate(
                    R.layout.activity_menumodifierlistview, null));
            final Dialog dialog = builder.create();

            WindowManager.LayoutParams wmlp = dialog.getWindow()
                    .getAttributes();
            wmlp.gravity = Gravity.RIGHT;//

            Window window = dialog.getWindow();
            window.setLayout(800, 600);
            dialog.setCanceledOnTouchOutside(true);
            dialog.show();

            final LinearLayout extraitemlayout = (LinearLayout) dialog
                    .findViewById(R.id.menumodifieritemlist);
            extraitemlayout.removeAllViews();

            LayoutParams modifieditemlayout = new LayoutParams(
                    LayoutParams.MATCH_PARENT, GlobalClass.getDPsize(70, this));

            LayoutParams modifieritemlayout = new LayoutParams(
                    GlobalClass.getDPsize(250, this), LayoutParams.MATCH_PARENT);

            LayoutParams pricelayout = new LayoutParams(GlobalClass.getDPsize(
                    120, this), LayoutParams.MATCH_PARENT);

            LayoutParams dividerlayout = new LayoutParams(
                    LayoutParams.FILL_PARENT, 1);
            modifieritemlayout.setMargins(3, 0, 0, 0);

            TextView txtitemname = (TextView) dialog
                    .findViewById(R.id.txtmenumodifiername);

            ItemsObj object = dbhelper.getItemsbyitemid(((TextView) v).getTag()
                    .toString());

            txtitemname.setText("Modifier Items(" + object.getdescription()
                    + ")");
            txtitemname.setTypeface(font);

            for (ItemsObj itemsObj : extraitemlist) {
                LinearLayout itemlist = new LinearLayout(this);

                TextView txtmodifieritem = new TextView(this);
                txtmodifieritem.setTextColor(Color.parseColor("#0000FF"));
                txtmodifieritem.setText(itemsObj.getdescription());
                txtmodifieritem.setTag(itemsObj.getcode());
                txtmodifieritem.setGravity(Gravity.CENTER | Gravity.LEFT);
                txtmodifieritem.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                txtmodifieritem.setTypeface(font);

                TextView txtprice = new TextView(this);
                String price = itemsObj.getsale_price();
                if (price.equals("") || price.equals("null")) {
                    price = "0";
                }
                price = Integer.toString((int) (Double.parseDouble(price)));

                txtprice.setText("Price - " + price);
                txtprice.setPadding(5, 0, 0, 0);
                txtprice.setGravity(Gravity.CENTER_VERTICAL);
                txtprice.setTextColor(Color.parseColor("#0000FF"));

                itemlist.addView(txtmodifieritem, modifieritemlayout);
                itemlist.addView(txtprice, pricelayout);

                extraitemlayout.addView(itemlist, modifieditemlayout);

                TextView txtline = new TextView(this);
                txtline.setBackgroundColor(Color.parseColor("#000000"));
                extraitemlayout.addView(txtline, dividerlayout);
            }
        }
    }

    private void modifierdialog(View v) {
        List<ItemsObj> extraitemlist = dbhelper
                .getmodifierItemslistbyitemid(((TextView) v).getTag()
                        .toString());

        if (extraitemlist.size() > 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setInverseBackgroundForced(true);
            // Dialog builder=new Dialog(this);

            // Get the layout inflater
            LayoutInflater inflater = this.getLayoutInflater();

            builder.setView(inflater.inflate(
                    R.layout.activity_modifierlistview, null));
            final Dialog dialog = builder.create();

            WindowManager.LayoutParams wmlp = dialog.getWindow()
                    .getAttributes();
            wmlp.gravity = Gravity.CENTER;//

            Window window = dialog.getWindow();
            window.setLayout(800, 600);
            dialog.setCanceledOnTouchOutside(true);
            dialog.show();

            final LinearLayout extraitemlayout = (LinearLayout) dialog
                    .findViewById(R.id.modifieritemlist);

            extraitemlayout.removeAllViews();
            // extraitemlayout.setBackgroundColor(Color.parseColor("#d9522c"));
            LayoutParams modifieditemlayout = new LayoutParams(
                    LayoutParams.MATCH_PARENT, GlobalClass.getDPsize(70, this));

            LayoutParams modifieritemlayout = new LayoutParams(
                    GlobalClass.getDPsize(250, this), LayoutParams.MATCH_PARENT);

            LayoutParams modifierqtylayout = new LayoutParams(
                    GlobalClass.getDPsize(50, ctx), LayoutParams.MATCH_PARENT);

            LayoutParams buttonlayout = new LayoutParams(GlobalClass.getDPsize(
                    60, ctx), GlobalClass.getDPsize(60, ctx));

            LayoutParams checkboxlayout = new LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);

            LayoutParams pricelayout = new LayoutParams(GlobalClass.getDPsize(
                    120, this), LayoutParams.MATCH_PARENT);

            LayoutParams dividerlayout = new LayoutParams(
                    LayoutParams.FILL_PARENT, 1);

            buttonlayout.setMargins(0, 0, 0, 0);
            modifieritemlayout.setMargins(3, 0, 0, 0);
            checkboxlayout.setMargins(5, 0, 0, 0);
            modifierqtylayout.gravity = Gravity.CENTER;

            TextView txtitemname = (TextView) dialog
                    .findViewById(R.id.txtModifierItemName);

            ItemsObj object = dbhelper.getItemsbyitemid(((TextView) v).getTag()
                    .toString());

            txtitemname.setText("Modifier Items(" + object.getdescription()
                    + ")");
            // txtitemname.setText("Modifier Items("
            // + ((TextView) v).getText().toString() + ")"); // Button
            // txtitemname.setTag(((Button) v).getTag().toString());
            txtitemname.setTypeface(font);

            for (ItemsObj itemsObj : extraitemlist) {
                LinearLayout itemlist = new LinearLayout(this);

                TextView txtmodifieritem = new TextView(this);
                txtmodifieritem.setTextColor(Color.parseColor("#0000FF"));
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
                txtmodifierqty.setTextColor(Color.parseColor("#0000FF"));

                Button btnplus = new Button(ctx);
                btnplus.setBackgroundResource(R.drawable.squareplus);

                btnplus.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        int qty = Integer.parseInt(txtmodifierqty.getText()
                                .toString());
                        qty = qty + 1;
                        txtmodifierqty.setText(Integer.toString(qty));
                    }
                });

                Button btnminus = new Button(ctx);

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

                CheckBox chkbox = new CheckBox(ctx);

                // LinearLayout selectedmodifierlist = (LinearLayout) dialog
                // .findViewById(R.id.selectedmodifierItemlist);
                //
                // for (int j = 0; j < selectedmodifierlist
                // .getChildCount(); j++) {
                // TextView modifieritem = (TextView) ((LinearLayout)
                // selectedmodifierlist
                // .getChildAt(j)).getChildAt(0);
                // TextView modifieritemqty = (TextView) ((LinearLayout)
                // selectedmodifierlist
                // .getChildAt(j)).getChildAt(2);
                // if (modifieritem.getTag().toString()
                // .equals(itemsObj.getcode())) {
                // txtmodifierqty.setText(modifieritemqty
                // .getText());
                // chkbox.setChecked(true);
                // }
                //
                // }
                //

                TextView txtprice = new TextView(this);
                String price = itemsObj.getsale_price();
                if (price.equals("") || price.equals("null")) {
                    price = "0";
                }
                price = Integer.toString((int) (Double.parseDouble(price)));

                txtprice.setText("Price - " + price);
                txtprice.setPadding(5, 0, 0, 0);
                txtprice.setGravity(Gravity.CENTER_VERTICAL);
                txtprice.setTextColor(Color.parseColor("#0000FF"));

                itemlist.addView(txtmodifieritem, modifieritemlayout);
                itemlist.addView(btnplus, buttonlayout);
                itemlist.addView(txtmodifierqty, modifierqtylayout);
                itemlist.addView(btnminus, buttonlayout);
                itemlist.addView(txtprice, pricelayout);
                itemlist.addView(chkbox, checkboxlayout);

                extraitemlayout.addView(itemlist, modifieditemlayout);

                TextView txtline = new TextView(this);
                txtline.setBackgroundColor(Color.parseColor("#000000"));
                extraitemlayout.addView(txtline, dividerlayout);

            }

        }

    }

    public void selectedmenuitems_dialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog
        // layout
        builder.setView(inflater.inflate(R.layout.activity_menuselecteditemlist, null));
        final Dialog dialog = builder.create();

        WindowManager.LayoutParams wmlp = dialog.getWindow()
                .getAttributes();
        wmlp.gravity = Gravity.CENTER;
        //wmlp.y = GlobalClass.getDPsize(100, this);

        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        Window window = dialog.getWindow();
        window.setLayout(GlobalClass.getDPsize(550, this),
                GlobalClass.getDPsize(450, this));//org 430

        dialog.show();


        //	AlertDialog.Builder builder = new AlertDialog.Builder(this)
        //		.setInverseBackgroundForced(true);
        // Dialog builder=new Dialog(this);

        // Get the layout inflater
        //	LayoutInflater inflater = this.getLayoutInflater();

        //	builder.setView(inflater.inflate(
        //		R.layout.activity_menuselecteditemlist, null));
        //final Dialog dialog = builder.create();

        //WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
        //wmlp.gravity = Gravity.CENTER;//

        //	Window window = dialog.getWindow();
        //window.setLayout(500, 500);//800,600//org
        //	window.setLayout(GlobalClass.getDPsize(500, this),
        //			GlobalClass.getDPsize(500, this));
        //dialog.setCanceledOnTouchOutside(true);
        //dialog.show();

//		if(dialog.isShowing()){
//			dialog.dismiss();
//		}else{
//			dialog.show();
//		}


        ArrayList<MenuOrderObj> menuorderlist = new ArrayList<MenuOrderObj>();
        menuorderlist = dbhelper.getMenuOrderItems(tranid, userid);

        // final LinearLayout
        // menuorderitemlayout=(LinearLayout)dialog.findViewById(R.id.menu_itemlistlayout);
        // menuorderitemlayout.removeAllViews();
        // menuorderitemlayout.setOrientation(LinearLayout.VERTICAL);

        ListView menulist = (ListView) dialog
                .findViewById(R.id.menuordereditemlist);

        if (menuorderlist.size() > 0) {
            menuorder = new MenuOrder(this, menuorderlist);
            menulist.setAdapter(menuorder);
            menuorder.notifyDataSetChanged();
            /*
             * final LinearLayout itemlayout = new LinearLayout(this);
             * itemlayout.setOrientation(LinearLayout.HORIZONTAL);
             *
             * final LayoutParams itemlayoutpara = new LayoutParams(
             * LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
             *
             * final LayoutParams srlayoutpara = new LayoutParams(
             * GlobalClass.getDPsize(20, this), LayoutParams.WRAP_CONTENT);
             *
             * final LayoutParams itemnamelayoutpara = new LayoutParams(
             * GlobalClass.getDPsize(140, this), LayoutParams.WRAP_CONTENT);
             *
             * final LayoutParams qtylayoutpara = new LayoutParams(
             * GlobalClass.getDPsize(40, this), LayoutParams.WRAP_CONTENT);
             *
             * final LayoutParams amountlayoutpara = new LayoutParams(
             * GlobalClass.getDPsize(90, this), LayoutParams.WRAP_CONTENT);
             *
             * LayoutParams dividerlayout = new LayoutParams(
             * LayoutParams.FILL_PARENT, 1);
             *
             * for(MenuOrderObj item: menuorderlist){
             *
             * TextView txtorderid=new TextView(ctx);
             * txtorderid.setText(item.getOrderid() + ".");
             * txtorderid.setPadding(0, 2, 0, 2); txtorderid.setTypeface(font);
             * txtorderid.setGravity(Gravity.LEFT);
             * txtorderid.setTextColor(Color.parseColor("#000000"));
             *
             * TextView txtitemname = new TextView(this);
             * txtitemname.setText(item.getDescription());
             * txtitemname.setPadding(0, 15, 0, 15);
             * txtitemname.setTypeface(font);
             * txtitemname.setGravity(Gravity.LEFT);
             * txtitemname.setTextColor(Color.parseColor("#000000"));
             *
             * TextView txtqtyname = new TextView(this);
             * txtqtyname.setText(item.getQty());
             * txtqtyname.setTag(Integer.parseInt(item.getQty()));
             * txtqtyname.setPadding(0, 2, 0, 2);
             * txtqtyname.setTextColor(Color.parseColor("#000000"));
             * txtqtyname.setGravity(Gravity.RIGHT);
             * txtqtyname.setTypeface(font, Typeface.BOLD);
             *
             * TextView txtamount = new TextView(this);
             * txtamount.setText(Double.
             * toString(Double.parseDouble(item.getSale_price())
             * Double.parseDouble(txtqtyname.getTag().toString())));
             * txtamount.setTag
             * (Double.toString(Double.parseDouble(item.getSale_price())
             * Double.parseDouble(txtqtyname.getTag().toString())));
             * txtamount.setPadding(0, 2, 0, 2);
             * txtamount.setGravity(Gravity.RIGHT);
             * txtamount.setTextColor(Color.parseColor("#000000"));
             *
             * TextView txtline = new TextView(this);
             * txtline.setBackgroundColor(Color.parseColor("#000000"));
             *
             * itemlayout.addView(menuorderitemlayout, srlayoutpara);
             * itemlayout.addView(txtitemname, itemnamelayoutpara);
             * itemlayout.addView(txtqtyname, qtylayoutpara);
             * itemlayout.addView(txtamount, amountlayoutpara);
             *
             * menuorderitemlayout.addView(itemlayout,itemlayoutpara);
             * menuorderitemlayout.addView(txtline, dividerlayout); }
             */


            txtmenutoalqty = (TextView) dialog.findViewById(R.id.menu_txttotalqty);
            txtmenutotalamount = (TextView) dialog.findViewById(R.id.menu_txttotalamount);

            int totalqty = dbhelper.getTotalOrderQty(tranid, userid);
            if (totalqty > 0) {
//				((TextView) dialog.findViewById(R.id.menu_txttotalqty))
//						.setText(String.valueOf(totalqty));
                txtmenutoalqty.setText(String.valueOf(totalqty));
            }

            double totalamount = dbhelper.getTotalOrderAmount(tranid, userid);
            if (totalamount != 0.0) {
//				((TextView) dialog.findViewById(R.id.menu_txttotalamount))
//						.setText(NumberFormat.getNumberInstance(Locale.US)
//								.format(totalamount));

                txtmenutotalamount.setText(NumberFormat.getNumberInstance(Locale.US)
                        .format(totalamount));
            }

            Button btnCheckOut = (Button) dialog.findViewById(R.id.menu_btnok);
            btnCheckOut.setOnClickListener(new OnClickListener() {

                public void onClick(View v) {
                    // TODO Auto-generated method stub
//					Intent intent = new Intent(ctx, TableScreenActivity.class);
//					// Bundle b = new Bundle();
//					// b.putString("tranid", Integer.toString(tranid));
//					// intent.putExtras(b);
//
//					SharedPreferences.Editor editor = sharedpreferences.edit();
//					editor.putString(menu_tranid, Integer.toString(tranid));
//					editor.commit();
//
//					startActivity(intent);
//					finish();
                    sr = 0;
                    if (use_tablet_selforder_cashierapprove == true) //added WHM [2020-05-18] self order
                    {
                        try {
                            if (GlobalClass.CheckConnectionForSubmit(ctx)) {
                                GetTable();
                                Submit_withProgressbar(0, 0);
                            } else {
                                showAlertDialog(NewMenuActivity.this, "Warning", "No Connection with Server!",
                                        false);
                            }

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        dialog.dismiss();
                    } else {
                        password_dialog();
                    }
                }
            });

            Button btnCancel = (Button) dialog
                    .findViewById(R.id.menu_btncancel);
            btnCancel.setOnClickListener(new OnClickListener() {

                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    dialog.dismiss();
                }
            });

        } else //added WHM [2020-05-26] self order
        {
            Button btnCancel = (Button) dialog
                    .findViewById(R.id.menu_btncancel);
            btnCancel.setOnClickListener(new OnClickListener() {

                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    dialog.dismiss();
                }
            });
        }

    }

    private String GetMealType() {
        String str = "";

        if (MealType1 == true && MealType2 == false && MealType3 == false) {
            str = " And MealType1 ='true' ";
        } else if (MealType1 == true && MealType2 == true && MealType3 == false) {
            str = " And (MealType1 ='true' or MealType2 ='true') ";
        } else if (MealType1 == true && MealType2 == true && MealType3 == true) {
            str = " And (MealType1 ='true' or MealType2 ='true' or MealType3 ='true') ";
        } else if (MealType1 == false && MealType2 == true
                && MealType3 == false) {
            str = " And ( MealType2 ='true') ";
        } else if (MealType1 == false && MealType2 == true && MealType3 == true) {
            str = " And ( MealType2 ='true' or MealType3 ='true') ";
        } else if (MealType1 == false && MealType2 == false
                && MealType3 == true) {
            str = " And (MealType3 ='true') ";
        } else if (MealType1 == true && MealType2 == false && MealType3 == true) {
            str = " And (MealType1 ='true' or  MealType3 ='true') ";
        }
        return str;
    }

    private void exitpassword_dialog() {
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
        TextView txt = (TextView) dialog.findViewById(R.id.textView1exit);
        txt.setText("Enter Password : ");

        butOK.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                String strpassword = txtpassword.getText().toString();
                String posuserpwd = dbhelper.getPosUserPasswordById(userid);
                if (strpassword.equals(posuserpwd)) {
                    dbhelper.deleteOrder(tranid, userid);

                    Intent intent = new Intent(ctx, MainScreen.class);
                    startActivity(intent);
                    finish();
                }
//				else{
//					dialog.dismiss();
//				}
            }
        });

    }

    public void password_dialog() {
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
        TextView txt = (TextView) dialog.findViewById(R.id.textView1exit);
        txt.setText("Enter Password : ");

        butOK.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                String strpassword = txtpassword.getText().toString();
                //String posuserpwd=dbhelper.getPosUserPasswordById(userid);
                String posuserpwd = dbhelper.get_specialpassword();//added WHM [2020-08-18]
                if (strpassword.equals(posuserpwd)) {
                    Intent intent = new Intent(ctx, TableScreenActivity.class);

                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(menu_tranid, Integer.toString(tranid));
                    editor.commit();

                    startActivity(intent);
                    finish();
                }
//				else{
//					dialog.dismiss();
//				}
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if (popupqty.getText().toString() != "0") {  //modified WHM [2020-05-26] self order
                AlertDialog alertDialog = new AlertDialog.Builder(ctx).create();
                alertDialog.setTitle("Complete Your Order!");
                alertDialog.setMessage(" Are you sure?\n This action will clear the items in the cart.");
                alertDialog.setIcon(0);
                final Context ctx = this;

                // Setting OK Button
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub

//					Edit Exit Process
//					dbhelper.deleteOrder(tranid, userid);
//					Intent intent = new Intent(ctx, MainScreen.class);
//					startActivity(intent);
//					finish();

                        exitpassword_dialog();
                    }
                });
                alertDialog.setButton2("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                alertDialog.show();
            } else {
                exitpassword_dialog();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public void getPouupValue() {
        int qty = dbhelper.getPopupQty(tranid, userid);
        if (qty > 0) {
            popupqty.setText(String.valueOf(qty));
        } else {
            popupqty.setText("0");
        }

    }

    //region self order added WHM [2020-04-20] R&D2004001

    public void GetTable() throws JSONException {
        // check for Internet status
        SaleObj saleobj = new SaleObj();
        saleobj.setinvoiceno("");
        saleobj.setDocID("VOU-1");
        saleobj.settablenameid("1");
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
                            current_sale_tranid = Integer.parseInt(jsonmessage.get(3).toString());
                            current_sale_tableid = Integer.parseInt(jsonmessage.get(4).toString());
                        }
                    }

                } else {
                    if (Integer.parseInt(jsonmessage.get(3).toString()) != 0) {
                        current_sale_tranid = Integer.parseInt(jsonmessage.get(3).toString());
                        current_sale_tableid = Integer.parseInt(jsonmessage.get(4).toString());
                    }
                }
            } else {
                showAlertDialog(this, "Warning", "No Connection with Server!",
                        false);
            }
        }
    }

    public void Submit_withProgressbar(final int salesmenid, final int languagedesc)
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
                        final String[] msg = Submit_Data(salesmenid, languagedesc);
                        progressBarHandler.post(new Runnable() {
                            public void run() {
                                progressBar.dismiss();
                                showAlertDialog(ctx, msg[0], msg[1], false);
                                if (msg[1].equals("Submit Successful!")) {
                                    dbhelper.deleteOrder(tranid, userid);
                                    getPouupValue();
                                }
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

    public String[] Submit_Data(int salesmenid, int languagedesc) throws NumberFormatException,
            JSONException {


        ArrayList<MenuOrderObj> menuorderlist = new ArrayList<MenuOrderObj>();
        DatabaseHelper dbhelp = new DatabaseHelper(NewMenuActivity.this);
        menuorderlist = dbhelp.getMenuOrderItems(sale_menutranid, sale_menuuserid);
        //   menuorderlist=sale_menuorderlist;

        // region Get SaleObj
        SaleObj saleobj = new SaleObj();
        saleobj.setinvoiceno("");
        saleobj.setDocID("docid");
        saleobj.settablenameid("1");
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

        // endregion
        String[] Msg = {"", ""};


        List<SaleItemObj> saleitemobjlist = new ArrayList<SaleItemObj>();

        JSONArray salejsonarray = new JSONArray();

        JSONArray saleitemjsonarray = new JSONArray();
        JSONObject jsonobj = new JSONObject();
        // sale_head_main
        if (Integer.toString(tranid) != "0") {
            jsonobj.put("tranid", tranid);
        }
        jsonobj.put("tranid", current_sale_tranid);
        jsonobj.put("salemanid", saleobj.getsalesmen_id());
        jsonobj.put("userid", saleobj.getuserid());
        jsonobj.put("invoiceno", saleobj.getinvoiceno());
        jsonobj.put("customerid", saleobj.getcustomerid());
        jsonobj.put("invoice_amount", saleobj.getamount());
        jsonobj.put("invoice_qty", saleobj.getqty());
        jsonobj.put("tablenameid", current_sale_tableid);
        jsonobj.put("custcount", saleobj.getcustcount());
        jsonobj.put("docid", saleobj.getDocID());
        // salejsonarray.put(jsonobj);
        salejsonarray.put(jsonobj);

        JSONArray analysisjsonarray = new JSONArray();
        jsonobj = new JSONObject();
        jsonobj.put("PBT", "P");
        jsonobj.put("malecount", "0");
        jsonobj.put("femalecount", "0");
        jsonobj.put("adultcount", "0");
        jsonobj.put("childcount", "0");
        jsonobj.put("westerncount", "0");
        jsonobj.put("asiancount", "0");
        jsonobj.put("myanmarcount", "0");
        salejsonarray.put(jsonobj);
        // analysisjsonarray.put(jsonobj);


        if (GlobalClass.CheckConnectionForSubmit(ctx)) {

            saveddata[0] = saleobj.getinvoiceno();
            saveddata[1] = Integer.toString(menuorderlist.size());

            SaleItemObj saleitemobj = new SaleItemObj();

            for (int i = 0; i < menuorderlist.size(); i++) {
                saleitemobj.setsrno(Integer.toString(i + 1));
                saleitemobj.setcode(menuorderlist.get(i).getCode());
                saleitemobj.setprice(menuorderlist.get(i).getSale_price());

                saleitemobj.setqty(menuorderlist.get(i).getQty());
                saleitemobj.setamount("0");
                saleitemobj.setstatus("N");
                saleitemobj.setcancelflag(false);

                saleitemobj.setSetmenurowsr("0");

                saleitemobj.setmodifiedrowsr("0");
                saleitemobj.setunittype("1");

                if (!saleitemobj.getstatus().equals("N")) {
                    saleitemobj.setsr("0");
                }


                saleitemobj.setunittype("1");

                saleitemobj.settaway("false");
                saleitemobj.setremark("");
                saleitemobj.setisFinishedItem(Boolean
                        .valueOf("0")); // added by WaiWL
                // on 23/07/2015


                saleitemobj.setOrg_curr(dbhelper.getsaleCurrbyitemid(saleitemobj.getcode()));

                saleitemobjlist.add(saleitemobj);

                jsonobj = new JSONObject();
                try {
                    // sale_Det
                    jsonobj.put("sr", saleitemobj.getsr());
                    jsonobj.put("srno", saleitemobj.getsrno()
                            .replace(".", "").trim());
                    jsonobj.put("code", saleitemobj.getcode());
                    jsonobj.put("unit_qty", saleitemobj.getqty());
                    jsonobj.put("takeaway", saleitemobj.gettaway());
                    jsonobj.put("printed", false);
                    jsonobj.put("modifiedrowsr", saleitemobj.getmodifiedrowsr());
                    jsonobj.put("unittype", saleitemobj.getunittype());
                    jsonobj.put("remark", saleitemobj.getremark());
                    jsonobj.put("itemcancel", saleitemobj.getcancelflag());
                    jsonobj.put("status", saleitemobj.getstatus());
                    if (salesmenid == 0) {

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

                //region set menu //added WHM [2020-06-01] self order
                if (Boolean.parseBoolean(menuorderlist.get(i).getIsSetMenu().toString()) == true) {
                    List<SelectedItemModifierObj> setmenuitemlist = dbhelper.getsetmenuItemslistbyitemid
                            (menuorderlist.get(i).getCode());

                    Toast.makeText(NewMenuActivity.this, "" + String.valueOf(setmenuitemlist.size()), Toast.LENGTH_LONG).show();

                    int k = i + 1;
                    for (int j = 0; j < setmenuitemlist.size(); j++) {
                        saleitemobj.setsrno(Integer.toString(k));
                        saleitemobj.setcode(setmenuitemlist.get(j).getitemid());
                        saleitemobj.setprice(setmenuitemlist.get(j).getprice());

                        saleitemobj.setqty(setmenuitemlist.get(j).getqty());
                        saleitemobj.setamount("0");
                        saleitemobj.setstatus("N");
                        saleitemobj.setcancelflag(false);

                        saleitemobj.setSetmenurowsr("" + k);

                        saleitemobj.setmodifiedrowsr("" + k);
                        //if (userEmail != null && !userEmail.isEmpty() && !userEmail.equals("null"))
                        if (setmenuitemlist.get(j).getUnit_type().equals("null") ||
                                setmenuitemlist.get(j).getUnit_type() == null ||
                                setmenuitemlist.get(j).getUnit_type().isEmpty()) {
                            saleitemobj.setunittype("1");
                        } else {
                            saleitemobj.setunittype("" + setmenuitemlist.get(j).getUnit_type());
                        }

                        if (!saleitemobj.getstatus().equals("N")) {
                            saleitemobj.setsr("0");
                        }


                        //saleitemobj.setunittype("1");

                        saleitemobj.settaway("false");
                        saleitemobj.setremark("");
                        saleitemobj.setisFinishedItem(Boolean
                                .valueOf("0")); // added by WaiWL
                        // on 23/07/2015


                        saleitemobj.setOrg_curr(dbhelper.getsaleCurrbyitemid(saleitemobj.getcode()));

                        saleitemobjlist.add(saleitemobj);

                        jsonobj = new JSONObject();
                        try {
                            // sale_Det
                            jsonobj.put("sr", saleitemobj.getsr());
                            jsonobj.put("srno", saleitemobj.getsrno()
                                    .replace(".", "").trim());
                            jsonobj.put("code", saleitemobj.getcode());
                            jsonobj.put("unit_qty", saleitemobj.getqty());
                            jsonobj.put("takeaway", saleitemobj.gettaway());
                            jsonobj.put("printed", false);
                            jsonobj.put("modifiedrowsr", saleitemobj.getmodifiedrowsr());
                            jsonobj.put("unittype", saleitemobj.getunittype());
                            jsonobj.put("remark", saleitemobj.getremark());
                            jsonobj.put("itemcancel", saleitemobj.getcancelflag());
                            jsonobj.put("status", saleitemobj.getstatus());
                            if (salesmenid == 0) {

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
                    }
                }
                //endregion set menu

            }

        }

        Json_class jsonclass = new Json_class();
        JSONArray jsonmessage = jsonclass.POST(
                new DatabaseHelper(this).getServiceURL()
                        + "/Data/OrderTaking_Entrywithparameter",
                salejsonarray);


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


        } else {
            // showAlertDialog(this, "Warning", "No Connection with Server!",
            // false);
            Msg[0] = "Warning";
            Msg[1] = "No Connection with Server!";
            return Msg;
            // return new String[]{"Warning","No Connection with Server!"};
        }
        return Msg;
    }
    // check for Internet status


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


            }
        });
        // Showing Alert Message
        alertDialog.show();

        TextView textView = (TextView) alertDialog
                .findViewById(android.R.id.message);
        textView.setTypeface(font);

    }


    //endregion //end self order whm [2020-04-20]


    //added WHM Check Currey ExgRate
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
}
