package com.galaxy.restaurantpos;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import me.myatminsoe.mdetect.MDetect;
import me.myatminsoe.mdetect.Rabbit;

public class DatabaseHelper extends SQLiteOpenHelper {

    static final int DB_VERS = 1;

    static final String dbName = "Galaxy_Restaurant";
    private static String url = "";
    public static boolean hasShift = false;
    public static boolean tabletuserlock = false;
    public static int pricedecimal = 0;
    public static int defcurrency = 1;
    // Area Table
    static final String AreaTable = "Area";
    static final String colArea_ID = "Area_ID";
    static final String colArea_Name = "Area_Name";
    static final String colDescription = "Description";
    static final String colArea_Code = "Area_Code";
    static final String colRemark = "Remark";

    // SystemSetting
    static final String coluse_unit = "use_unit";
    static final String coluse_hotel = "use_hotel";//Added by ArkarMoe on [19/12/2016]
    static final String colspecial_password = "special_password"; //added WHM [2020-08-18]

    // Table
    static final String Table_Name = "Table_Name";
    static final String colTableArea_ID = "Area_ID";
    static final String colTable_Name_ID = "Table_Name_ID";
    static final String colTable_Name = "Table_Name";
    static final String colTableDescription = "Description";
    static final String colTable_Code = "Table_Code";
    static final String colTableRemark = "Remark";
    static final String colTableSortcode = "sort_code";

    // UnitCode
    static final String UnitCodeTable = "UnitCode";
    static final String colunitcodeid = "unitcodeid";
    static final String colunittype = "unit_type";
    static final String colfromunitqty = "fromunitqty";
    static final String colunitqty = "unitqty";
    static final String colunit = "unit";
    static final String colunitname = "unitname";
    static final String colshortname = "shortname";
    static final String colsmallest_unit_qty = "smallest_unit_qty";

    // activetablesandinvoice
    static final String ActiveTables = "Active_Table";
    static final String coltranid = "tranid";
    static final String coldocid = "docid";
    static final String colreserved = "reserved";
    static final String collongminute = "longminute";
    static final String colBillChecked = "BillChecked";
    static final String colSelfOrderTable = "selforder_table";//added WHM [2020-05-19] self order


    static final String ItemTable = "Items";
    static final String colcode = "code";
    static final String colusrcode = "usr_code";
    static final String coldescription = "description";
    static final String coldescription2 = "description2";
    static final String coldescription3 = "description3";
    static final String colsaleprice = "sale_price";
    static final String colsaleprice1 = "sale_price1";
    static final String colsaleprice2 = "sale_price2";
    static final String colsaleprice3 = "sale_price3";
    static final String colcaldiscount = "caldiscount";
    static final String colcalgtax = "calgtax";
    static final String colcalstax = "calstax";
    static final String colcategoryid = "category";
    static final String colcategoryname = "categoryname";
    static final String colclassid = "classid";
    static final String colclassname = "classname";
    static final String colcolorRGB = "colorRGB";
    static final String colitemcolorRGB = "itemcolorRGB";
    static final String colcategorysortcode = "categorysortcode";
    static final String colclasssortcode = "classsortcode";
    static final String colupd_datetime = "upd_datetime";
    static final String colIsSetMenu = "IsSetMenu";
    static final String coltmpInactive = "Inactive";// added by WaiWL on // 06/08/2015
    static final String colParcel_Price = "Parcel_Price";
    static final String coldeleted = "deleted";
    static final String colsortid = "sortid";
    static final String colOrgCurr = "org_curr"; //added by EKK on 24-02-2020
    static final String colCategory2 = "category2";//added by KLM 11022022
    static final String colItemrating = "itemrating";
    static final String colRatingcount = "ratingcount";

    static final String colvalueitem = "valueitem";// added by WaiWL on
    // 11/08/2015
    static final String colMealType1 = "MealType1";//Added by SMH on 25/5/2017
    static final String colMealType2 = "MealType2";
    static final String colMealType3 = "MealType3";


    static final String colmodifiedgroupid = "modifiergroupid";
    static final String ModifiedItemTable = "ModifiedItem";

    static final String ModifierGroupTable = "Modifier_Group";
    static final String colmodifiername = "Name";
    static final String colmodifiershort = "Short";

    static final String ItemPhototable = "ItemPhoto";
    static final String colphoto = "Photo";

    static final String SaleTable = "Sale";
    static final String colsaleid = "sale_id";
    static final String colroomid = "room_id"; //Added by ArkarMoe on [20/12/2016]
    static final String colcustomerid = "customerid";
    static final String colsaledate = "date";
    static final String colcustcount = "Cust_Num";
    static final String colsubmitflag = "submit_flag";
    static final String colRef_no = "Ref_no";
    static final String colDelivery_type = "delivery_type"; //added WHM [2020-05-08]
    static final String colTaxfree = "taxfree";
    static final String colMemberCardID = "MemberCardID";

    static final String SaleItemTable = "SaleItem";
    static final String colsaleitemid = "saleitem_id";
    static final String colinvoiceno = "saleinvoiceno";
    static final String colqty = "qty";
    static final String colprice = "price";
    static final String colamount = "amount";
    static final String colmodifiedrowsr = "modifiedrowsr";
    static final String coltakeaway = "takeaway";
    static final String colitemcancel = "itemcancel";
    static final String colreprintqty = "reprintqty";
    static final String colsr = "sr";
    static final String colisFinishedItem = "isFinishedItem";// added by WaiWL
    // on 24/07/2015
    static final String colfire_sr = "fire_sr";
    static final String colfired = "fired";
    // added by WaiWL on 12/08/2015
    static final String colKTV_StartTime = "KTV_StartTime";
    static final String colKTV_EndTime = "KTV_EndTime";


    // //////////

    // Devicename
    static final String devicetable = "DeviceName";
    static final String coldeviceowner = "Owner";

    // CustomerCountSetUpv
    static final String CustomerCountSetUp = "CustomerCountSetUp";
    static final String colPrivate = "optPrivate";
    static final String colBusiness = "optBusiness";
    static final String colTravel = "optTravel";
    static final String colMale = "lblMale";
    static final String colFemale = "lblFemale";
    static final String colAdult = "lblAdult";
    static final String colChild = "lblChild";
    static final String colWestern = "lblWestern";
    static final String colAsian = "lblAsian";
    static final String colCustCountMyanmar = "lblMyanmar";
    // ////////

    // ServiceAddress
    static final String serviceaddressTable = "SystemSetting";
    static final String coltitle = "title";
    static final String colipaddress = "ipaddress";
    static final String collanguage = "language";
    static final String colurl = "url";
    static final String colport = "port";
    static final String coluseitemimage = "useitemimage";
    static final String colusemodifierpopup = "usemodifierpopup";
    static final String coluseeditboxpopup = "useeditpopup"; //Added by ArkarMoe on [20/12/2016]
    static final String colusesingleimageitem = "usesingleimageitem"; //Added by ArkarMoe on [23/12/2016]
    static final String coltimelog = "timelog";
    static final String colremark = "remark";
    static final String colregister = "register";
    static final String colexitpassword = "exitpassword";
    static final String coluse_customer_analysis = "use_customer_analysis";
    static final String colhideitemcancel = "hide_itemcancel";
    static final String colsalesmen_commission = "salesmen_commission";
    static final String colitemviewstyle = "itemviewstyle";
    static final String colitemdescription = "itemdescription";
    static final String colbillprintfromtablet = "billprintfromtablet";
    static final String colusemonitorinterface = "usemonitorinterface";
    static final String coluseiscontinuoussave = "iscontinuoussave";
    static final String colDeviceName = "DeviceName";
    static final String colmultilanguageprint = "multilanguageprint";
    static final String colusefire = "use_fire";
    static final String colTableRowStyle = "TableRowStyle";
    static final String colItemRowStyle = "ItemRowStyle";
    static final String colqty_decimal_places = "qty_decimal_places";
    static final String colprice_decimal_places = "price_decimal_places";
    //added WHM [2020-05-28] tablet menu
    static final String colmenuitem_rowcount = "MenuItemRowCount";
    static final String colmenuitem_colcount = "MenuItemColCount";

    //end service address table


    static final String DictionaryTable = "Dictionary"; //modified by EKK on 10/07/2019
    static final String colsrno = "srno";
    static final String colEnglish = "language1";
    static final String colMyanmar = "language2";
    static final String colChinese = "language3"; //added by EKK

    static final String CustomerTable = "Customer";
    static final String colcustomercode = "customercode";
    static final String colcustomername = "name";
    static final String colcustomercredit = "credit"; // added by .. on [23-04-2019]
    static final String colcustomer_pricelevel = "pricelevel";//added WHM 2020-01-27 MDY2-200141


    static final String colisOffline = "isOffline";// added by WaiWL on
    // 10/06/2015
    static final String colisUnicode = "isUnicode"; //added by ZYP 17-12-2019

    // PosUser Table
    static final String PosUserTable = "posuser";
    static final String coluserid = "userid";
    static final String colusershort = "user_short";
    static final String colusername = "name";
    static final String colpassword = "password";
    static final String colalluser = "all_users";
    static final String coluse_touchscreen = "use_touchscreen";
    static final String coluse_tabletuser = "use_tabletuser";
    static final String colallow_itemcancel = "allow_itemcancel";
    static final String colbtnClearAll = "btnClearAll";
    static final String colbtnBill = "btnBill";
    static final String colbtnDetail = "btnDetail";
    static final String colbtnOthers = "btnOthers";
    static final String colbtnSplit = "btnSplit";
    static final String coluse_menuonly = "use_menuonly"; //added by EKK on 18-09-2020
    static final String coluse_foodtruck = "use_foodtruck";    //added by ZYP
    static final String coluse_foodtrucklocation = "use_foodtrucklocation";

    static final String colallow_edit_after_insert = "allow_edit_after_insert";
    static final String colcashierprinterid = "cashierprinterid"; // added by TTA
    static final String colbtnPrintBill = "btnPrintBill"; //added WHM
    static final String colbillnotprint = "billnotprint";
    static final String colSelectCustomer = "select_customer"; //added by EKK on 15-01-2020
    static final String colCreateCustomer = "create_customer"; //added by EKK on 15-01-2020
    static final String colallow_item_transfer = "allow_item_transfer";    //added by ZYP on 16-03-2020


    // Salemen Table
    static final String SalemenTable = "Salesmen";
    static final String colsalesmen_id = "salesmen_id";
    static final String colsalesmen_name = "salesmen_name";
    static final String colshort = "shortcode";
    static final String colposuserid = "posuserid";

    // setmenuitem Table
    static final String Item_ListTable = "Item_List";
    static final String colmaincode = "main_code";
    static final String colunit_qty = "unit_qty";
    static final String colunit_type = "unit_type";
    static final String colmax_price = "max_price";


    // setmenu changeditem Table
    static final String Brand_Item_ListTable = "Brand_Item_List";
    static final String colbrandmaincode = "main_code";
    static final String colbranditemcode = "item_code";
    static final String colbrandcode = "code";
    static final String colbrandqty = "qty";
    static final String colbrandunit_qty = "unit_qty";
    static final String colbrandunit_type = "unit_type";
    static final String colbrand = "brand";


    // setmenuitem Table
    static final String kitchenTable = "kitchen_data";
    static final String colcook_status = "Cook_Status";
    static final String colsudden_fix = "sudden_fix";
    static final String colfinishshow = "show_finish";

    // Specialmenu Table --added by WaiWL on 27/05/2015
    static final String Specialmenu_Table = "Specialmenu";
    static final String colMenuID = "MenuID";
    static final String colMenuName = "MenuName";
    static final String colspRemark = "Remark";
    static final String colActiveMenus = "ActiveMenus";
    static final String colRmt_Copy = "Rmt_Copy";
    static final String colRmt_Branch = "Rmt_Branch";
    static final String colmon = "mon";
    static final String coltue = "tue";
    static final String colwed = "wed";
    static final String colthu = "thu";
    static final String colfri = "fri";
    static final String colsat = "sat";
    static final String colsun = "sun";

    // Specialmenu_code Table --added by WaiWL on 02/03/2015
    static final String Specialmenu_code_Table = "Specialmenu_code";
    static final String colSMenuID = "MenuID";
    static final String colSCode = "Code";
    static final String colSUsr_code = "Usr_code";

    // Note table ---added by SMH 24/10/2015
    static final String Note_table = "Note";
    static final String colntranid = "tranid";
    static final String colnsr = "sr";
    static final String colnuserid = "userid";
    static final String colnDate = "Date";
    static final String colnNotes = "Notes";

    // AppSetting table ---added by SMH 03/11/2015
    static final String AppSetting_table = "AppSetting";
    static final String colSetting_No = "Setting_No";
    static final String colSetting_Name = "Setting_Name";
    static final String colSetting_Value = "Setting_Value";
    static final String colnRemark = "Remark";
    static final String colAddedOn = "AddedOn";
    static final String colAddedBy = "AddedBy";

    // created Set User for tablet --added by SMH
    static final String setuser_table = "setuser";
    static final String col_userid = "setuserid";
    static final String col_username = "setusername";
    static final String col_use_tabletuser = "use_tabletuser";

    // ItemRemark table ---added by SMH 31/05/2016
    static final String ItemRemark_table = "ItemRemark";
    static final String colID = "ID";
    static final String colItemRemark = "itemRemark";

    // ItemRemark table ---added by SMH 31/05/2016
    static final String SplitedVouchers_table = "SplitedVouchers";
    static final String colTranid = "tranid";
    static final String colDocid = "docid";
    static final String colNet_amount = "net_amount";
    static final String colQty = "qty";


    // Rooms table ---added by ArkarMoe 14/12/2016
    static final String Rooms_table = "Rooms";
    static final String colRef_TranID = "Ref_TranID";
    static final String colRoom_ID = "Room_ID";
    static final String colRoom_Code = "Room_Code";

    // MealType table ---added by SMH 29/05/2016
    static final String MealType_Table = "MealType";
    static final String colMealTypeID = "MealTypeID";
    static final String colMealType = "MealType";

    //SoldOut table --added by AMT 20/09/2018
    static final String SoldOutTable = "SoldOut_Item";
    static final String colsoldoutusr_code = "usr_code";
    static final String colsoldoutcode = "code";
    static final String colsoldoutdesc = "description";


    //	CashierPrinter table --- added by TTA [01-02-2019]
    static final String CashierPrinter_Table = "CashierPrinter";
    static final String colPrinterID = "PrinterID";
    static final String colPrinterName = "PrinterName";
    static final String colPrinter = "Printer";

    // MenuOrder table -- added by TTA 16/01/2019
    static final String MenuOrderTable = "MenuOrder";
    static final String colmenutranid = "tranid";
    static final String colmenuuserid = "userid";
    static final String colmenuusr_code = "usr_code";
    static final String colorderid = "orderid";
    static final String colmenucode = "code";
    static final String colmenudescription = "description";
    static final String colmenuqty = "qty";
    static final String colmenusaleprice = "sale_price";
    static final String colmenuisnew = "isNew";
    //added WHM [2020-06-01] self order
    static final String colMenuOrder_isSetMenu = "isSetMenu";
    static final String colMenuOrder_sr = "sr";
    static final String colMenuOrder_srno = "srno";
    static final String colMenuOrder_modifiedrowsr = "modifiedrowsr";
    static final String colMenuOrder_setmenurowsr = "setmenurowsr";
    //end whm

    static final String MenuOrderTransferTable = "MenuOrderTransfer";
    static final String colmenutablenameid = "tableid";

    //userlog_table
    static final String UserLogTable = "userlog_table";
    static final String colUser_id = "userid";
    static final String colTableNameid = "table_name_id";

    //added by EKK on 13-12-2019
    static final String PromotionTable = "Promotion";
    static final String colPromoCode = "code";
    static final String colPromoUsr_code = "usr_code";
    static final String colPromoDescription = "description";
    static final String colPromotion = "promotion";

    //added by EKK on 08-01-2020
    static final String BusyTable = "Busy_Table";
    static final String Btable_name_id = "table_name_id";
    static final String Btranid = "tranid";

    public static int cust_pricelevel = 0; //added WHM [2020-01-27] MDY2-200141

    //region delivery entry //added WHM [2020-05-11]

    //township table
    static final String TownshipTable = "Township";
    static final String Townshipid = "Townshipid";
    static final String Townshipname = "name";
    static final String Township_sortid = "sort_id";

    //salesmen type or agent table
    static final String SalesmenType_Table = "salesmen_type";
    static final String Salesmen_type = "salesmen_type";
    static final String Salesmen_type_name = "name";
    static final String salesmentype_delivery_system = "delivery_system";
    static final String salesmentype_delivery_charges = "delivery_charges";

    //salesmen or deliveryman table
    static final String DeliveryMan_Table = "DeliveryMan";
    static final String DeliveryMan_id = "salesmen_id";
    static final String DeliveryMan_name = "salesmen_name";
    static final String DeliveryMan_AgentID = "salesmen_type";

    //Delivery Setup table
    static final String DeliverySetupTable = "Delivery_Setup";
    static final String DeliverySetup_Townshipid = "Townshipid";
    static final String DeliverySetup_delivery_charges = "delivery_charges";
    static final String DeliverySetup_free_range = "free_range";
    static final String DeliverySetup_estimate_time = "estimate_time";

    //Delivery_Entry_Detail_Tmp Table
    static final String DeliveryEntry_Table = "Delivery_Entry_Detail_Tmp";
    static final String DeliveryEntry_tranid = "tranid";
    static final String DeliveryEntry_townshipid = "Townshipid";
    static final String DeliveryEntry_order_customer_detail = "order_customer_detail";
    static final String DeliveryEntry_agent_id = "agent_id";
    static final String DeliveryEntry_deliveryman_id = "deliveryman_id";
    static final String DeliveryEntry_use_calc_delivery_charges = "use_calc_delivery_charges";
    static final String DeliveryEntry_order_datetime = "order_datetime";
    static final String DeliveryEntry_estimate_time = "estimate_time";
    static final String DeliveryEntry_remark = "remark";
    static final String DeliveryEntry_isDeliver = "isDeliver";
    static final String DeliveryEntry_org_deliverycharges = "org_deliverycharges";
    static final String DeliveryEntry_org_delivery_freerange = "org_delivery_freerange";
    static final String DeliveryEntry_updated = "updated";


    //endregion delivery entry


    //region currency table //added WHM [2020-05-27]
    static final String CurrencyTable = "Currency";
    static final String colCurr_currency = "currency";
    static final String colCurr_Name = "name";
    static final String colCurr_curr_short = "curr_short";
    static final String colCurr_exg_rate = "exg_rate";
    static final String colCurr_roundTo = "roundTo";

    //endregion currency table

    //region use_menucategorygroup //added WHM [2020-09-29] YGN2-200983

    //menucategory_groupbyarea table
    static final String menucategory_groupbyareaTable = "menucategory_groupbyarea";
    static final String menucateArea_areaID = "Area_ID";
    static final String menucateArea_category = "category";

    //menucategory_groupbylocation table
    static final String menucategory_groupbyLocTable = "menucategory_groupbylocation";
    static final String menucateLoc_locationid = "locationid";
    static final String menucateLoc_category = "category";


    //endregion use_menucategorygroup

    //region userarea //added WHM [2020-11-11]
    static final String areauser_Table = "area_groupbyuser";
    static final String areauser_userid = "userid";
    static final String areauser_area_id = "area_id";
    //endregion userarea

    public DatabaseHelper(Context context) {
        super(context, dbName, null, DB_VERS);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE If Not EXISTS " + AreaTable + " ("
                + colArea_ID + " INTEGER PRIMARY KEY, " + colArea_Name
                + " TEXT, " + colDescription + " TEXT, " + colArea_Code
                + " TEXT, " + colRemark + " TEXT)");

        db.execSQL("CREATE TABLE If Not EXISTS " + SalemenTable + " ("
                + colsalesmen_id + " INTEGER , " + colsalesmen_name
                + " TEXT, " + colshort + " TEXT, " + colposuserid + " TEXT)");

        db.execSQL("CREATE TABLE If Not EXISTS " + Table_Name + " ("
                + colTableArea_ID + " INTEGER, " + colTable_Name_ID
                + " INTEGER, " + colTable_Name + " TEXT, "
                + colTableDescription + " TEXT, " + colTable_Code + " TEXT, "
                //+ colTableSortcode + " TEXT, "
                + colTableSortcode + " integer, "//modified WHM [2020-11-10]
                + colTableRemark + " TEXT)");

        db.execSQL("CREATE TABLE If Not EXISTS " + ActiveTables + " ("
                + colTableArea_ID + " INTEGER, " + colTable_Name_ID
                + " INTEGER, " + coldocid + " TEXT, " + coltranid
                + " INTEGER, " + colreserved + " INTEGER, " + coluserid
                + " INTEGER, " + collongminute + " INTEGER, "
                + colBillChecked + " INTEGER,"
                + colSelfOrderTable + " Text " //added WHM [2020-05-19] self order
                + ")");


        // db.execSQL("CREATE TABLE If Not EXISTS " + PosUserTable + " (" +
        // coluserid + " INTEGER PRIMARY KEY, " +
        // colusershort + " TEXT, " +
        // colusername + " TEXT, " +
        // colpassword + " TEXT, " +
        // colalluser + " TEXT, " +
        // coluse_touchscreen+ " TEXT)");

        db.execSQL("CREATE TABLE If Not EXISTS " + PosUserTable + " ("
                + coluserid + " INTEGER PRIMARY KEY, " + colusershort
                + " TEXT, " + colusername + " TEXT, " + colpassword + " TEXT, "
                + colalluser + " TEXT, " + coluse_touchscreen + " TEXT,"
                + coluse_tabletuser + " TEXT,"
                + colbtnBill + " TEXT,"
                + colbtnClearAll + " TEXT,"
                + colbtnDetail + " TEXT,"
                + colbtnOthers + " TEXT,"
                + colbtnSplit + " TEXT,"
                + colallow_edit_after_insert + " TEXT,"
                + colallow_itemcancel + " TEXT,"
                + colcashierprinterid + " INTEGER,"
                + colbtnPrintBill + " TEXT,"
                + colbillnotprint + " TEXT ,"
                + colCreateCustomer + " TEXT," //added by EKK on 15-01-2020
                + colSelectCustomer + " TEXT, " //added by EKK on 15-01-2020
                + colallow_item_transfer + " TEXT, " //added by ZYP on 16-03-2020
                + coluse_menuonly + " TEXT, " //added by EKK on 18-09-2020
                + coluse_foodtruck + " TEXT, " //added by ZYP on 25-01-2021
                + coluse_foodtrucklocation + " TEXT "
                + ")");

        db.execSQL("CREATE TABLE If Not EXISTS " + setuser_table + " ("
                + col_userid + " INTEGER PRIMARY KEY, " + col_username
                + " TEXT, " + col_use_tabletuser + " TEXT)");

        db.execSQL("CREATE TABLE If Not EXISTS " + ItemPhototable + " ("
                + colusrcode + " TEXT," + colphoto + " BLOB);");

        db.execSQL("CREATE TABLE If Not EXISTS " + ItemTable + " (" + colcode
                + " INTEGER PRIMARY KEY, " + colusrcode + " TEXT, "
                + coldescription + " TEXT, " + colsaleprice + " TEXT, "
                + colsaleprice1 + " TEXT, " + colsaleprice2 + " TEXT, "
                + colsaleprice3 + " TEXT, " + colcaldiscount + " TEXT, "
                + colcalgtax + " TEXT, " + colcalstax + " TEXT, "
                + colcategoryid + " TEXT, " + colcategoryname + " TEXT, "
                + colclassid + " TEXT, " + colclassname + " TEXT, " + colremark
                + " TEXT, " + colmodifiedgroupid + " TEXT, " + colcolorRGB
                + " TEXT, " + colunit + " TEXT, " + colcategorysortcode
                + " TEXT, " + colclasssortcode + " TEXT, " + colupd_datetime
                + " TEXT, " + colIsSetMenu + " TEXT, " + coltmpInactive
                + " TEXT, " // added by WaiWL on 06/08/2015
                + colitemcolorRGB + " TEXT, "
                + colvalueitem + " TEXT, "
                + colMealType1 + " TEXT, "
                + colMealType2 + " TEXT, "
                + colMealType3 + " TEXT, "
                + coldescription2 + " TEXT, "
                + coldescription3 + " TEXT, "
                + colParcel_Price + " TEXT,"
                + colsortid + " INTEGER,"
                + colOrgCurr + " TEXT," // added by WaiWL on 11/08/2015
                + colItemrating + " TEXT," //added by MPPA on 27/01/2021
                + colCategory2 + " TEXT,"//added by KLM 07062022
                + colRatingcount + " INTEGER );"); //added by MPPA on 29/01/2021

        db.execSQL("CREATE TABLE If Not EXISTS " + ModifiedItemTable + " ("
                + colcode + " INTEGER, " + colusrcode + " TEXT, "
                + coldescription + " TEXT, " + colsaleprice + " TEXT, "
                + colsaleprice1 + " TEXT, " + colsaleprice2 + " TEXT, "
                + colsaleprice3 + " TEXT, " + colcaldiscount + " TEXT, "
                + colcalgtax + " TEXT, " + colcalstax + " TEXT, "
                + colcategoryid + " TEXT, " + colcategoryname + " TEXT, "
                + colclassid + " TEXT, " + colclassname + " TEXT, "
                + colmodifiedgroupid + " TEXT, " + coltmpInactive + " TEXT ,"
                + coldescription2 + " TEXT, " + coldescription3 + " TEXT );");// added
        // by
        // WaiWL
        // on
        // 06/08/2015

        db.execSQL("CREATE TABLE If Not EXISTS " + ModifierGroupTable + " ("
                + colmodifiedgroupid + " INTEGER PRIMARY KEY , "
                + colmodifiername + " TEXT," + colmodifiershort + " TEXT);");

        db.execSQL("CREATE TABLE If Not EXISTS " + SaleTable + " ("
                + colsaleid + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + colinvoiceno + " TEXT, " + colTable_Name_ID + " TEXT, "
                + colamount + " TEXT, " + colsaledate + " TEXT, "
                + colroomid + " TEXT, " + colcustomerid + " TEXT, "
                + colcustcount + " TEXT, " + colsubmitflag + " Boolean, "
                + colsalesmen_id + " TEXT, " + coluserid + " TEXT, "
                + colRef_no + " TEXT, "
                + colDelivery_type + " INTEGER,  "  //added WHM [2020-05-08]
                + colTaxfree + " INTEGER, "
                + colMemberCardID + " TEXT "
                + ");");

        db.execSQL("CREATE TABLE If Not EXISTS " + UnitCodeTable + " ("
                + colunitcodeid + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + colcode + " INTEGER, " + colusrcode + " TEXT, " + colunittype
                + " INTEGER, " + colfromunitqty + " TEXT, " + colunitqty
                + " TEXT, " + colunit + " INTEGER, " + colunitname + " TEXT, "
                + colshortname + " TEXT, " + colsaleprice + " TEXT, "
                + colsmallest_unit_qty + " TEXT);");

        db.execSQL("CREATE TABLE If Not EXISTS " + SaleItemTable + " ("
                + colsaleitemid + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + colsaleid + " INTEGER, "
                + colsr + " INTEGER, " + colsrno + " INTEGER, "
                + colcode + " TEXT, " + colprice + " TEXT, "
                + colqty + " TEXT, " + colamount + " TEXT, " + colremark + " TEXT, "
                + colmodifiedrowsr + " TEXT, " + colunittype + " TEXT, "
                + coltakeaway + " INTEGER, " + colreprintqty + " INTEGER, "
                + colitemcancel + " TEXT, " + colsubmitflag + " Boolean, "
                + colisFinishedItem + " Boolean, "
                + colfire_sr + " TEXT, " + colfired + " TEXT, "
                + colKTV_StartTime + " TEXT, " + colKTV_EndTime + " TEXT);");// added by WaiWL on 12/08/2015

        db.execSQL("CREATE TABLE If Not EXISTS " + devicetable + " ("
                + coldeviceowner + " TEXT, " + coluserid + " TEXT);");

        db.execSQL("CREATE TABLE If Not EXISTS " + serviceaddressTable + " ("
                + coltitle + " TEXT, "
                + colipaddress + " TEXT, " + collanguage + " TEXT,"
                + colurl + " TEXT," + colport + " TEXT,"
                + coltimelog + " TEXT,"
                + coluseitemimage + " TEXT,"
                + colusemodifierpopup + " TEXT,"
                + coluseeditboxpopup + " TEXT,"
                + colusesingleimageitem + " TEXT,"
                + coluse_unit + " TEXT,"
                + coluse_hotel + " TEXT,"
                + colremark + " TEXT," + colregister + " TEXT,"
                + colexitpassword + " TEXT," + coluse_customer_analysis
                + " TEXT," + colhideitemcancel + " TEXT,"
                + colsalesmen_commission + " TEXT," + colitemviewstyle
                + " TEXT," + colbillprintfromtablet + " TEXT,"
                + colusemonitorinterface + " TEXT,"
                + coluseiscontinuoussave + " TEXT,"
                + colDeviceName + " TEXT,"
                + colitemdescription + " TEXT,"
                + colmultilanguageprint + " TEXT,"
                + colusefire + " TEXT,"
                + colisOffline + " TEXT,"
                + colisUnicode + " TEXT,"
                + colqty_decimal_places + " TEXT,"
                + colprice_decimal_places + " TEXT,"
                + colTableRowStyle + " TEXT,"
                + colItemRowStyle + " TEXT "
                + ", " + colmenuitem_rowcount + " TEXT"  //modified WHM [2020-05-28] tablet menu
                + ", " + colmenuitem_colcount + " TEXT"
                + ", " + colspecial_password + " TEXT" //added WHM [2020-08-14]
                + ");");


        // added by WaiWL on 15/09/2015
        db.execSQL("CREATE TABLE If Not EXISTS " + CustomerCountSetUp + " ("
                + colPrivate + " TEXT, " + colBusiness + " TEXT," + colTravel
                + " TEXT," + colMale + " TEXT," + colFemale + " TEXT," + colAdult + " TEXT," + colChild + " TEXT,"
                + colWestern + " TEXT," + colAsian + " TEXT,"
                + colCustCountMyanmar + " TEXT);");
        // ///////////////

        db.execSQL("CREATE TABLE If Not EXISTS " + DictionaryTable + " ("
                + colsrno + " INTEGER PRIMARY KEY AUTOINCREMENT, " + colEnglish
                + " TEXT," + colMyanmar + " TEXT, " + colChinese + " TEXT);");

//		db.execSQL("CREATE TABLE If Not EXISTS " + CustomerTable + " ("
//				+ colcustomerid + " INTEGER PRIMARY KEY AUTOINCREMENT, "
//				+ colcustomercode + " TEXT," + colcustomername + " TEXT);");

//modified WHM [2020-01-27] MDY2-200147
        db.execSQL("CREATE TABLE If Not EXISTS " + CustomerTable + " ("
                + colcustomerid + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + colcustomercode + " TEXT,"
                + colcustomername + " TEXT, "
                + colcustomercredit + " TEXT,"
                + colcustomer_pricelevel + " INTEGER "
                + ");");

        db.execSQL("CREATE TABLE If Not EXISTS " + Item_ListTable + " ("
                + colmaincode + "," + colcode + " TEXT," + colqty + " TEXT,"
                + colunit_qty + " TEXT," + colunit_type + " TEXT, " + colmax_price + " TEXT, " + colCategory2 + " TEXT );");

        db.execSQL("CREATE TABLE If Not EXISTS " + Brand_Item_ListTable + " ("
                + colbrandmaincode + "," + colbranditemcode + " TEXT," + colbrandcode + " TEXT,"
                + colbrandqty + " TEXT," + colbrandunit_qty + " TEXT," + colbrandunit_type + " TEXT, " + colbrand + " TEXT );");

        // added by WaiWL on 27/05/2015
        db.execSQL("CREATE TABLE If Not EXISTS " + Specialmenu_Table + " ("
                + colMenuID + " INTEGER PRIMARY KEY, " + colMenuName
                + " TEXT, " + colspRemark + " TEXT, " + colActiveMenus
                + " Boolean, " + colRmt_Copy + " Boolean, " + colRmt_Branch
                + " TEXT, " + colmon + " Boolean, " + coltue + " Boolean, "
                + colwed + " Boolean, " + colthu + " Boolean, " + colfri
                + " Boolean, " + colsat + " Boolean, " + colsun + " Boolean);");
        // /////////////

        // added by WaiWL on 27/05/2015
        db.execSQL("CREATE TABLE If Not EXISTS " + Specialmenu_code_Table
                + " (" + colSMenuID + " INTEGER, " + colSUsr_code + " TEXT, "
                + colSCode + " INTEGER);");
        // /////////////

        db.execSQL("CREATE TABLE If Not EXISTS " + kitchenTable + " ("
                + coltranid + " TEXT," + colsr + " TEXT," + colcode + " TEXT,"
                + colunit_qty + " TEXT," + colTable_Name_ID + " TEXT,"
                + colRef_no + " TEXT," + colsalesmen_id + " TEXT,"
                + colcook_status + " TEXT," + coluserid + " TEXT,"
                + colsudden_fix + " TEXT," + colfinishshow + " TEXT);");

        // added by SMH on 24/10/2015
        db.execSQL("CREATE TABLE If Not EXISTS " + Note_table + " ("
                + colntranid + " TEXT, " + colnsr + " TEXT, " + colnuserid
                + " TEXT," + colnDate + " TEXT," + colnNotes + " TEXT);");

        // added by SMH on 03/11/2015
        db.execSQL("CREATE TABLE If Not EXISTS " + AppSetting_table + " ("
                + colSetting_No + " TEXT, " + colSetting_Name + " TEXT, "
                + colSetting_Value + " TEXT," + colnRemark + " TEXT,"
                + colAddedOn + " TEXT," + colAddedBy + " TEXT);");


        // added by SMH on 03/11/2015
        db.execSQL("CREATE TABLE If Not EXISTS " + ItemRemark_table + " ("
                + colID + " INTEGER, " + colItemRemark + " TEXT);");

        // added by ArkarMoe on 08/06/2015
        db.execSQL("CREATE TABLE If Not EXISTS " + SplitedVouchers_table + " ("
                + colTranid + " INTEGER,"
                + colDocid + " TEXT,"
                + colNet_amount + " TEXT,"
                + colQty + " TEXT);");

        //Added by ArkarMoe on [14/12/2016]
        db.execSQL("CREATE TABLE If Not EXISTS " + Rooms_table + " ("
                + colRef_TranID + " INTEGER, "
                + colRoom_ID + " INTEGER, "
                + colRoom_Code + " TEXT);");

        //Added by SMH on [29/05/2017]
        db.execSQL("CREATE TABLE If Not EXISTS " + MealType_Table + " ("
                + colMealTypeID + " INTEGER, "
                + colMealType + " TEXT);");

        //added by EKK
        db.execSQL("CREATE TABLE If Not EXISTS " + UserLogTable + " ("
                + colUser_id + " INTEGER, "
                + colTableNameid + " INTEGER);");

        db.execSQL("CREATE TABLE If Not EXISTS " + SoldOutTable + " ("
                + colsoldoutcode + " INTEGER, "
                + colsoldoutusr_code + " TEXT, " +
                colsoldoutdesc + " TEXT);");


        //	Added by TTA on [28-01-2019]
        db.execSQL("CREATE TABLE IF NOT EXISTS " + CashierPrinter_Table + " ("
                + colPrinterID + " INTEGER, "
                + colPrinterName + " TEXT, "
                + colPrinter + " TEXT); ");

//		added by TTA for Menu
        db.execSQL("CREATE TABLE IF NOT EXISTS " + MenuOrderTable + " ("
                + colmenutranid + " INTEGER, "
                + colmenuuserid + " INTEGER, "
                + colorderid + " INTEGER, "
                + colmenucode + " TEXT, "
                + colmenuusr_code + " TEXT, "
                + colmenudescription + " TEXT, "
                + colmenuqty + " TEXT, "
                + colmenusaleprice + " TEXT, "
                + colmenuisnew + " Boolean "
                //added WHM [2020-06-01] self order
                + "," + colMenuOrder_isSetMenu + " TEXT "
                + "," + colMenuOrder_sr + " TEXT "
                + "," + colMenuOrder_srno + " TEXT "
                + "," + colMenuOrder_modifiedrowsr + " TEXT "
                + "," + colMenuOrder_setmenurowsr + " TEXT "
                + ");");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + MenuOrderTransferTable + " ("
                + colmenutranid + " INTEGER, "
                + colmenuuserid + " INTEGER, "
                + colmenutablenameid + " TEXT);");

        //added by EKK on 13-12-2019
        db.execSQL("CREATE TABLE IF NOT EXISTS " + PromotionTable + "("
                + colPromoCode + " TEXT, "
                + colPromoUsr_code + " TEXT, "
                + colPromoDescription + " TEXT, "
                + colPromotion + " TEXT );");

        //added by EKK on 08-01-2020
        db.execSQL("CREATE TABLE IF NOT EXISTS " + BusyTable + "("
                + Btable_name_id + " TEXT, "
                + Btranid + " TEXT);");

        //region delivery tables //added WHM [2020-05-11]

        //township table
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TownshipTable + "("
                + Townshipid + " Integer PRIMARY KEY, "
                + Townshipname + " Text, "
                + Township_sortid + " TEXT "
                + ");");

        //salesmenn type or agent table
        db.execSQL("CREATE TABLE IF NOT EXISTS " + SalesmenType_Table + "("
                + Salesmen_type + " Integer PRIMARY KEY, "
                + Salesmen_type_name + " Text, "
                + salesmentype_delivery_system + " boolean, "
                + salesmentype_delivery_charges + " boolean "
                + ");");

        //salesmenn or deliveryman table
        db.execSQL("CREATE TABLE IF NOT EXISTS " + DeliveryMan_Table + "("
                + DeliveryMan_id + " Integer PRIMARY KEY, "
                + DeliveryMan_name + " Text, "
                + DeliveryMan_AgentID + " Integer "
                + ");");

        //delivery setup table
        db.execSQL("CREATE TABLE IF NOT EXISTS " + DeliverySetupTable + "("
                + DeliverySetup_Townshipid + " Integer PRIMARY KEY, "
                + DeliverySetup_delivery_charges + " Text, "
                + DeliverySetup_estimate_time + " integer, "
                + DeliverySetup_free_range + " Text "
                + ");");

        //delivery Entry Table
        db.execSQL("CREATE TABLE IF NOT EXISTS " + DeliveryEntry_Table + "("
                + DeliveryEntry_tranid + " Integer PRIMARY KEY, "
                + DeliveryEntry_order_customer_detail + " Text, "
                + DeliveryEntry_townshipid + " integer, "
                + DeliveryEntry_agent_id + " integer, "
                + DeliveryEntry_deliveryman_id + " integer, "
                + DeliveryEntry_use_calc_delivery_charges + " boolean, "
                + DeliveryEntry_order_datetime + " Text, "
                + DeliveryEntry_estimate_time + " integer, "
                + DeliveryEntry_remark + " Text, "
                + DeliveryEntry_isDeliver + " boolean, "
                + DeliveryEntry_org_deliverycharges + " Text, "
                + DeliveryEntry_org_delivery_freerange + " Text, "
                + DeliveryEntry_updated + " boolean "
                + ");");


        //endregion

        //region currency table //added WHM [2020-05-27]
        db.execSQL("CREATE TABLE IF NOT EXISTS " + CurrencyTable + "("
                + colCurr_currency + " Integer PRIMARY KEY, "
                + colCurr_Name + " Text, "
                + colCurr_curr_short + " Text, "
                + colCurr_exg_rate + " Text, "
                + colCurr_roundTo + " Integer "
                + ");");
        //endregion currency table


        //region menucategorygroup //added WHM [2020-09-29] YGN2-200983

        //menucategroy_groupbyarea table
        db.execSQL("CREATE TABLE IF NOT EXISTS " + menucategory_groupbyareaTable + "("
                + menucateArea_areaID + " Integer , "
                + menucateArea_category + " Integer  "
                + ");");

        //menucategroy_groupbyLoc table
        db.execSQL("CREATE TABLE IF NOT EXISTS " + menucategory_groupbyLocTable + "("
                + menucateLoc_locationid + " Integer , "
                + menucateLoc_category + " Integer  "
                + ");");

        //endregion menucategorygroup

        //region userarea //added WHM [2020-11-11]
        //area_groupbyuser table
        db.execSQL("CREATE TABLE IF NOT EXISTS " + areauser_Table + "("
                + areauser_userid + " Integer , "
                + areauser_area_id + " Integer  "
                + ");");

        //endregion userarea

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("CREATE TABLE If Not EXISTS " + Item_ListTable + " ("
                + colmaincode + "," + colcode + " TEXT," + colqty + " TEXT,"
                + colunit_qty + " TEXT," + colunit_type + " TEXT," + colmax_price + " TEXT);");
    }

    void DeleteItemTalbe(String code) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("Delete from  " + ItemTable + " WHERE " + colcode + " = " + code);
        db.close();

    }

    void DeleteKitchenDataTalbe(String TableNameID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("Delete from  " + kitchenTable + " where "
                + colTable_Name_ID + " = " + TableNameID);
        db.close();

    }

    void AddItems(String code, String usr_code, String description,
                  String saleprice, String saleprice1, String saleprice2,
                  String saleprice3, String caldiscount, String calgtax,
                  String calstax, String categoryid, String categoryname,
                  String classid, String classname, String modifiedgroupid,
                  String remark, byte[] Photo, String colorRGB, String itemcolorRGB, String unit,
                  String categorysortcode, String classsortcode, String IsSetMenu,
                  String Inactive, String valueitem, String MealType1, String MealType2, String MealType3, String description2, String description3, String Parcel_Price, String sortid, String org_curr, String category2) {
        SQLiteDatabase db1 = this.getWritableDatabase();
        db1.execSQL("Delete from  " + ItemTable + " where " + colcode + " = "
                + code);
        db1.execSQL("Delete from  " + ItemTable + " where " + colusrcode
                + " = '" + usr_code + "'");
        db1.close();

        SQLiteDatabase db = this.getWritableDatabase();
        // SQLiteDatabase db= this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(colcode, code);
        cv.put(colusrcode, usr_code);
        cv.put(coldescription, description);
        cv.put(colsaleprice, saleprice);
        cv.put(colsaleprice1, saleprice1);
        cv.put(colsaleprice2, saleprice2);
        cv.put(colsaleprice3, saleprice3);
        cv.put(colcaldiscount, caldiscount);
        cv.put(colcalgtax, calgtax);
        cv.put(colcalstax, calstax);
        cv.put(colcategoryid, categoryid);
        cv.put(colcategoryname, categoryname);
        cv.put(colclassid, classid);
        cv.put(colclassname, classname);
        cv.put(colmodifiedgroupid, modifiedgroupid);
        cv.put(colremark, remark);
        cv.put(colcolorRGB, colorRGB);
        cv.put(colitemcolorRGB, itemcolorRGB);
        cv.put(colunit, unit);
        cv.put(colcategorysortcode, categorysortcode);
        cv.put(colclasssortcode, classsortcode);
        cv.put(colIsSetMenu, IsSetMenu);
        cv.put(coltmpInactive, Inactive); // added by WaiWL on 06/08/2015
        cv.put(colvalueitem, valueitem);// added by WaiWL on 11/08/2015
        cv.put(colMealType1, MealType1);//Added by SMH on 25/5/2017
        cv.put(colMealType2, MealType2);
        cv.put(colMealType3, MealType3);
        cv.put(coldescription2, description2);
        cv.put(coldescription3, description3);
        cv.put(colParcel_Price, Parcel_Price);
        cv.put(colsortid, sortid);
        cv.put(colOrgCurr, org_curr); //added by EKK on 24-02-2020
        cv.put(colCategory2, category2); //added by KLM on 07062022
        db.insert(ItemTable, null, cv);
        db.close();
    }

    void AddSetMenuItems(String maincode, String code, String qty,
                         String unit_qty, String unit_type, String max_price, String category2) {
//		SQLiteDatabase db1 = this.getWritableDatabase();
//		db1.execSQL("Delete from  " + Item_ListTable + " where " + colmaincode
//				+ " = '" + maincode + "' and " + colcode + " = '" + code + "'");
//		db1.close();

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(colmaincode, maincode);
        cv.put(colcode, code);
        cv.put(colqty, qty);
        cv.put(colunit_qty, unit_qty);
        cv.put(colunit_type, unit_type);
        cv.put(colmax_price, max_price);
        cv.put(colCategory2, category2);
        db.insert(Item_ListTable, null, cv);
        db.close();
    }

    void AddSetMenuChangedItems(String maincode, String itemcode, String code, String qty,
                                String unit_qty, String unit_type, String brand) {
        //SQLiteDatabase db1 = this.getWritableDatabase();
//		String sql="Delete from  " + Brand_Item_ListTable + " where " + colbrandmaincode
//				+ " = '" + maincode + "' and " + colbranditemcode + " = '" + itemcode + "'" + " and " + colbrandcode + " = '" + code + "'";
//		db1.execSQL(sql);
//		db1.close();

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(colbrandmaincode, maincode);
        cv.put(colbranditemcode, itemcode);
        cv.put(colbrandcode, code);
        cv.put(colbrandqty, qty);
        cv.put(colbrandunit_qty, unit_qty);
        cv.put(colbrandunit_type, unit_type);
        cv.put(colbrand, brand);
        db.insert(Brand_Item_ListTable, null, cv);
        db.close();
    }

    // added by WaiWL on 27/05/2015
    void AddSpecialMenu(Integer MenuID, String MenuName, String Remark,
                        Boolean ActiveMenus, Boolean Rmt_Copy, String Rmt_Branch,
                        Boolean mon, Boolean tue, Boolean wed, Boolean thu, Boolean fri,
                        Boolean sat, Boolean sun) {
        SQLiteDatabase db1 = this.getWritableDatabase();
        db1.execSQL("Delete from  " + Specialmenu_Table + " where " + colMenuID
                + " = " + MenuID + " ");
        db1.close();

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(colMenuID, MenuID);
        cv.put(colMenuName, MenuName);
        cv.put(colspRemark, Remark);
        cv.put(colActiveMenus, ActiveMenus);
        cv.put(colRmt_Copy, Rmt_Copy);
        cv.put(colRmt_Branch, Rmt_Branch);
        cv.put(colmon, mon);
        cv.put(coltue, tue);
        cv.put(colwed, wed);
        cv.put(colthu, thu);
        cv.put(colfri, fri);
        cv.put(colsat, sat);
        cv.put(colsun, sun);
        db.insert(Specialmenu_Table, null, cv);
        db.close();
    }

    // //////////////

    // added by WaiWL on 02/06/2015
    void AddSpecialMenu_code(Integer MenuID, Integer Code, String Usr_code) {
        SQLiteDatabase db1 = this.getWritableDatabase();
        db1.execSQL("Delete from  " + Specialmenu_code_Table + " where "
                + colSMenuID + " = " + MenuID + " and " + colSCode + " = "
                + Code);
        db1.close();

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(colSMenuID, MenuID);
        cv.put(colSCode, Code);
        cv.put(colSUsr_code, Usr_code);

        db.insert(Specialmenu_code_Table, null, cv);
        db.close();
    }

    // //////////////

    void AddItemsPhoto(String usr_code, byte[] Photo) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(ItemPhototable, colusrcode + " = '" + usr_code + "'", null);

        ContentValues cv = new ContentValues();
        cv.put(colusrcode, usr_code);
        cv.put(colphoto, Photo);
        // cv.put(colDept,2);
        db.insert(ItemPhototable, null, cv);
        db.close();
    }

    void updateuseitemimage(int useflag) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(coluseitemimage, useflag);
        // cv.put(colDept,2);
        db.update(serviceaddressTable, cv, null, null);
        db.close();
    }

    void updateitemviewstyle(int itemviewstyle) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(colitemviewstyle, itemviewstyle);
        // cv.put(colDept,2);
        db.update(serviceaddressTable, cv, null, null);
        db.close();
    }

    void updateitemdescriptionstyle(int itemdescription) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(colitemdescription, itemdescription);
        // cv.put(colDept,2);
        db.update(serviceaddressTable, cv, null, null);
        db.close();
    }

    int getitemimageflag() {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT " + coluseitemimage + " FROM "
                + serviceaddressTable;
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {

            String data = "";
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    data = cursor.getString(0);
                } while (cursor.moveToNext());
            }
            // return contact list
            if (data == null || data == "")
                data = "0";
            return Integer.parseInt(data);
        } finally {
            cursor.close();
        }

    }

    int geteditboxflag() //Added by ArkarMoe on [21/12/2016]
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT " + coluseeditboxpopup + " FROM " + serviceaddressTable;
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {

            String data = "";
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    data = cursor.getString(0);
                } while (cursor.moveToNext());
            }
            // return contact list
            if (data == null || data == "")
                data = "0";
            return Integer.parseInt(data);
        } finally {
            cursor.close();
        }

    }

    int getsigleimageitemflag() //Added by ArkarMoe on [23/12/2016]
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT " + colusesingleimageitem + " FROM " + serviceaddressTable;
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {

            String data = "";
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    data = cursor.getString(0);
                } while (cursor.moveToNext());
            }
            // return contact list
            if (data == null || data == "")
                data = "0";
            return Integer.parseInt(data);
        } finally {
            cursor.close();
        }

    }


    int getitemviewstyle() {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT " + colitemviewstyle + " FROM "
                + serviceaddressTable;
        Cursor cursor = db.rawQuery(selectQuery, null);
        String data = "";
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    data = cursor.getString(0);
                } while (cursor.moveToNext());
            }
            // return contact list
            if (data == null || data == "")
                data = "0";
            return Integer.parseInt(data);
        } finally {
            cursor.close();
        }

    }

    int getitemdescriptionstyle() {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT " + colitemdescription + " FROM "
                + serviceaddressTable;
        Cursor cursor = db.rawQuery(selectQuery, null);
        String data = "";
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    data = cursor.getString(0);
                } while (cursor.moveToNext());
            }
            // return contact list
            if (data == null || data == "")
                data = "0";
            return Integer.parseInt(data);
        } finally {
            cursor.close();
        }

    }

    void updateusemodifierpop(int useflag) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(colusemodifierpopup, useflag);
        // cv.put(colDept,2);
        db.update(serviceaddressTable, cv, null, null);
        db.close();
    }

    void updateuseeditboxpop(int useflag) //Added by ArkarMoe on [20/12/2016]
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(coluseeditboxpopup, useflag);
        //cv.put(colDept,2);
        db.update(serviceaddressTable, cv, null, null);
        db.close();
    }

    void updateusesingleimageitem(int useflag) //Added by ArkarMoe on [23/12/2016]
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(colusesingleimageitem, useflag);
        //cv.put(colDept,2);
        db.update(serviceaddressTable, cv, null, null);
        db.close();
    }

    void updateRegisterFlag(Boolean register, String DeviceName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(colregister, register);
        cv.put(colDeviceName, DeviceName);
        // cv.put(colDept,2);
        db.update(serviceaddressTable, cv, null, null);
        db.close();
    }

    void updateExitPassword(String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(colexitpassword, password);
        // cv.put(colDept,2);
        db.update(serviceaddressTable, cv, null, null);
        db.close();
    }

    String GetExitPassword() {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT " + colexitpassword + " FROM "
                + serviceaddressTable;
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            String data = "";
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    data = cursor.getString(0);
                } while (cursor.moveToNext());
            }
            // return contact list
            return data;
        } finally {
            cursor.close();
        }

    }

    int getmodifierpop() {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT " + colusemodifierpopup + " FROM "
                + serviceaddressTable;
        Cursor cursor = db.rawQuery(selectQuery, null);
        String data = "";
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    data = cursor.getString(0);
                } while (cursor.moveToNext());
            }
            // return contact list
            if (data == null || data == "")
                data = "0";
            return Integer.parseInt(data);
        } finally {
            cursor.close();
        }

    }

    void AddModifierItems(String code, String usr_code, String description,
                          String saleprice, String saleprice1, String saleprice2,
                          String saleprice3, String caldiscount, String calgtax,
                          String calstax, String categoryid, String categoryname,
                          String classid, String classname, String ModifiedGroupid,
                          String Inactive, String description2, String description3) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("Delete from  " + ModifiedItemTable + " where " + colcode
                + " = '" + code + "' and " + colmodifiedgroupid + " = "
                + ModifiedGroupid);
        ContentValues cv = new ContentValues();
        cv.put(colcode, code);
        cv.put(colusrcode, usr_code);
        cv.put(coldescription, description);
        cv.put(colsaleprice, saleprice);
        cv.put(colsaleprice1, saleprice1);
        cv.put(colsaleprice2, saleprice2);
        cv.put(colsaleprice3, saleprice3);
        cv.put(colcaldiscount, caldiscount);
        cv.put(colcalgtax, calgtax);
        cv.put(colcalstax, calstax);
        cv.put(colcategoryid, categoryid);
        cv.put(colcategoryname, categoryname);
        cv.put(colclassid, classid);
        cv.put(colclassname, classname);
        cv.put(colmodifiedgroupid, ModifiedGroupid);
        cv.put(coltmpInactive, Inactive);// added by WaiWL on 06/08/2015
        cv.put(coldescription2, description2);
        cv.put(coldescription3, description3);
        db.insert(ModifiedItemTable, null, cv);

        db.close();
    }

    void AddSaledata(int tranid, SaleObj saleobj,
                     List<SaleItemObj> saleitemobjlist) {
        DeleteSaleDataBySaleID(Integer.toString(tranid));
        DeleteSaleItemDataBySaleID(Integer.toString(tranid));

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(colsaleid, tranid);
        cv.put(colinvoiceno, saleobj.getinvoiceno());
        cv.put(colTable_Name_ID, saleobj.gettablenameid());
        cv.put(colroomid, saleobj.getroomid());
        cv.put(colcustomerid, saleobj.getcustomerid());
        cv.put(colamount, saleobj.getamount());
        cv.put(colsaledate, saleobj.getdate());
        cv.put(coluserid, saleobj.getuserid());
        cv.put(colsalesmen_id, saleobj.getsalesmen_id());

        if (tranid == 0)
            cv.put(colsubmitflag, false);
        else
            cv.put(colsubmitflag, true);

        db.insert(SaleTable, null, cv);
        db.close();

        for (SaleItemObj saleItemObj : saleitemobjlist) {
            SQLiteDatabase dbb = this.getWritableDatabase();
            ContentValues cvitem = new ContentValues();
            cvitem.put(colsrno, saleItemObj.getsrno().replace(".", "").trim());
            cvitem.put(colsaleid, tranid);
            cvitem.put(colcode, saleItemObj.getcode());
            cvitem.put(colprice, saleItemObj.getprice());
            cvitem.put(colqty, saleItemObj.getqty());
            cvitem.put(colamount, saleItemObj.getamount());
            cvitem.put(colmodifiedrowsr, saleItemObj.getmodifiedrowsr());
            cvitem.put(colremark, saleItemObj.getremark());

            if (tranid == 0)
                cvitem.put(colsubmitflag, false);
            else
                cvitem.put(colsubmitflag, true);

            // added by WaiWL on 17/08/2015
            cvitem.put(colsr, saleItemObj.getsr());
            cvitem.put(colKTV_StartTime, saleItemObj.getKTV_StartTime());
            cvitem.put(colKTV_EndTime, saleItemObj.getKTV_EndTime());
            // ///
            dbb.insert(SaleItemTable, null, cvitem);
            dbb.close();
        }
    }


    void updatefireSaledata(int tranid, SaleObj saleobj,
                            List<SaleItemObj> saleitemobjlist) {

        for (SaleItemObj saleItemObj : saleitemobjlist) {

            String data;
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(colfire_sr, saleItemObj.getFire_sr());
            // cv.put(colDept,2);
            db.update(SaleItemTable, cv, "sale_id=" + tranid, null);

            db.close();
            selectFire(Integer.toString(tranid));

        }
    }


    void selectFire(String SaleID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT " + colfire_sr + " FROM "
                + SaleItemTable + " Where " + colsaleid + " = " + SaleID;

        Cursor cursor = db.rawQuery(selectQuery, null);
        String data = "";
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    data = cursor.getString(0);
                } while (cursor.moveToNext());
            }

        } finally {
            cursor.close();
        }

    }


    private void AddPosUser(PosUser A) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(coluserid, A.getUserId());
        cv.put(colusershort, A.getShort());
        cv.put(colusername, A.getName());
        cv.put(colpassword, A.getPassword());
        cv.put(colalluser, A.getAlluser());
        cv.put(coluse_touchscreen, A.getuse_touchscreen());
        cv.put(colallow_itemcancel, A.getallow_itemcancel());
        cv.put(colallow_edit_after_insert, A.get_allow_edit_after_insert());
        cv.put(colbtnBill, A.get_btnBill());
        cv.put(colbtnClearAll, A.get_btnClearAll());
        cv.put(colbtnDetail, A.get_btnDetail());
        cv.put(colbtnOthers, A.get_btnOthers());
        cv.put(colbtnSplit, A.get_btnSplit());
        cv.put(colcashierprinterid, A.get_cashierprinterid());
        cv.put(colbtnPrintBill, A.get_btnPrintBill());
        cv.put(colbillnotprint, A.get_billnotprint());
        cv.put(colSelectCustomer, A.get_select_customer()); //added by EKK on 15-01-2020
        cv.put(colCreateCustomer, A.get_create_customer()); //added by EKK on 15-01-2020
        cv.put(colallow_item_transfer, A.get_allow_itemtransfer()); //added by ZYP on 16-03-2020
        cv.put(coluse_menuonly, A.get_use_menuonly()); //added by EKK on 18-09-2020
        cv.put(coluse_foodtruck, A.get_use_foodtruck()); //added by ZYP on 25-01-2021
        cv.put(coluse_foodtrucklocation, A.get_use_foodtrucklocation()); //added by ZYP on 24-02-2021
        db.insert(PosUserTable, null, cv);

    }

    private void AddSalesmen(Salesmen A) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(colsalesmen_id, A.getsalesmen_id());
        cv.put(colsalesmen_name, A.getsalesmen_name());
        cv.put(colshort, A.getshortcode());
        cv.put(colposuserid, A.getposuserid());
        db.insert(SalemenTable, null, cv);
    }

    public void AddSoldOutitem(SoldOutItem A) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(colsoldoutcode, A.getCode());
        cv.put(colsoldoutusr_code, A.getUsr_code());
        cv.put(colsoldoutdesc, A.getDescription());
        db.insert(SoldOutTable, null, cv);
    }

    //Added by SMH
    private void AddNote(Note N) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(colntranid, N.getTranid());
        cv.put(colnsr, N.getsr());
        cv.put(colnuserid, N.getuserid());
        cv.put(colnDate, N.getDate());
        cv.put(colnNotes, N.getNotes());
        db.insert(Note_table, null, cv);
    }

    //Added by SMH
    private void AddAppSetting(Appsetting As) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(colSetting_No, As.getSetting_No());
        cv.put(colSetting_Name, As.getSetting_Name());
        cv.put(colSetting_Value, As.getSetting_Value());
        cv.put(colRemark, As.getRemark());
        cv.put(colAddedOn, As.getAddedon());
        cv.put(colAddedBy, As.getAddedBy());
        db.insert(AppSetting_table, null, cv);
    }

    //Added by SMH
    private void AddItemRemark(ItemRemark IR) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(colID, IR.getID());
        cv.put(colItemRemark, IR.getItemRemark());
        db.insert(ItemRemark_table, null, cv);
    }

    //Added by Arkar Moe on [2017-06-08]
    private void AddSplitedVouchers(SplitedVouchers SV) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(colTranid, SV.get_tranid());
        cv.put(colDocid, SV.get_docid());
        cv.put(colNet_amount, SV.get_net_amount());
        cv.put(colQty, SV.get_qty()); //added by EKK on 24-03-2020
        db.insert(SplitedVouchers_table, null, cv);
    }

    //Added by SMH on 29/05/2017
    private void AddMealType(MealType MT) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(colMealTypeID, MT.getMealTypeID());
        cv.put(colMealType, MT.getMealType());
        db.insert(MealType_Table, null, cv);
    }

    //added by EKK
    private void AddUserLog(UserLog_Table ul) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(colUser_id, ul.get_userid());
        cv.put(colTableNameid, ul.get_table_name_id());
        db.insert(UserLogTable, null, cv);
    }

    //added by EKK on 13-12-2019
    private void AddPromoItem(Promotion p) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(colPromoCode, p.getCode());
        cv.put(colPromoUsr_code, p.getUsr_code());
        cv.put(colPromoDescription, p.getDescription());
        cv.put(colPromotion, p.getPromotion());
        db.insert(PromotionTable, null, cv);
    }

    //Added by ArkarMoe on [14/12/2016]
    private void AddRooms(Rooms RS) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(colRef_TranID, RS.getRef_TranID());
        cv.put(colRoom_ID, RS.getRoom_ID());
        cv.put(colRoom_Code, RS.getRoom_Code());
        db.insert(Rooms_table, null, cv);
    }
    ///////////

    void DeleteSaleDataBySaleID(String SaleID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("Delete from  " + SaleTable + " where " + colsaleid + " = "
                + SaleID);
        db.close();

    }

    void DeleteSaleItemDataBySaleID(String SaleID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("Delete from  " + SaleItemTable + " where " + colsaleid
                + " = " + SaleID);
        db.close();

    }

    void AddDeviceOwner(String deviceowner, String userid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(coldeviceowner, deviceowner);
        cv.put(coluserid, userid);
        db.insert(devicetable, coldeviceowner, cv);
        db.close();
    }

    void DeleteDeviceOwner() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("Delete from  " + devicetable);
        db.close();
    }

    String getDeviceOwner() {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT " + coldeviceowner + " FROM "
                + devicetable;
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {

                    return cursor.getString(0);

                } while (cursor.moveToNext());
            }
            return "";
        } finally {
            cursor.close();
        }

    }

    String getSalesmen_commission() {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT " + colsalesmen_commission + " FROM "
                + serviceaddressTable;
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        try {
            if (cursor.moveToFirst()) {
                do {

                    return cursor.getString(0);

                } while (cursor.moveToNext());
            }
            return "";
        } finally {
            cursor.close();
        }

    }

    String getusemonitorinterface() {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT " + colusemonitorinterface + " FROM "
                + serviceaddressTable;
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            if (cursor.moveToFirst()) {
                do {

                    return cursor.getString(0);

                } while (cursor.moveToNext());
            }
            return "";
        } finally {
            cursor.close();
        }
        // looping through all rows and adding to list

    }

    String getBillPrintFromTablet() {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT " + colbillprintfromtablet + " FROM "
                + serviceaddressTable;
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {

                    return cursor.getString(0);

                } while (cursor.moveToNext());
            }
            return "";

        } finally {
            cursor.close();
        }

    }

    String getmultiplelanguageprint() {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT " + colmultilanguageprint + " FROM "
                + serviceaddressTable;
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {

                    return cursor.getString(0);

                } while (cursor.moveToNext());
            }
            return "";

        } finally {
            cursor.close();
        }

    }


    String getuse_Fire() {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT " + colusefire + " FROM "
                + serviceaddressTable;
        Cursor cursor = db.rawQuery(selectQuery, null);
        String fire = "";
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {

                    fire = cursor.getString(0);

                } while (cursor.moveToNext());
            }
            if (fire == null)
                fire = "";
            return fire;

        } finally {
            cursor.close();
        }

    }


    String getwaiterid() {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT " + coluserid + " FROM " + devicetable;
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {

                    return cursor.getString(0);

                } while (cursor.moveToNext());
            }
            return "";
        } finally {
            cursor.close();
        }

    }

    String getwaitername() {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT " + coldeviceowner + " FROM "
                + devicetable;
        Cursor cursor = db.rawQuery(selectQuery, null);

        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {

                    return cursor.getString(0);

                } while (cursor.moveToNext());
            }
            return "";
        } finally {
            cursor.close();
        }

    }

    String getServiceURL() {
        SQLiteDatabase db = this.getWritableDatabase();
        String URL = "";
        String selectQuery = "SELECT " + colurl + " FROM "
                + serviceaddressTable;
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    // Customer contact = new Customer();
                    // contact.setID(Integer.parseInt(cursor.getString(0)));
                    // contact.setName(cursor.getString(1));
                    // contact.setPhoneNumber(cursor.getString(2));
                    // Adding contact to list
                    URL = "http://" + cursor.getString(0);
                } while (cursor.moveToNext());
            }

            // return contact list
            if (URL == null)
                URL = "";
            return URL;
        } finally {
            cursor.close();
        }

    }

    String getHideCancelItemFlag() {
        SQLiteDatabase db = this.getWritableDatabase();
        String URL = "";
        String selectQuery = "SELECT " + colhideitemcancel + " FROM "
                + serviceaddressTable;
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    // Customer contact = new Customer();
                    // contact.setID(Integer.parseInt(cursor.getString(0)));
                    // contact.setName(cursor.getString(1));
                    // contact.setPhoneNumber(cursor.getString(2));
                    // Adding contact to list
                    URL = cursor.getString(0);
                } while (cursor.moveToNext());
            }

            // return contact list
            if (URL == null)
                URL = "";
            return URL;
        } finally {
            cursor.close();
        }

    }

    String getUseCustomer_analysisFlag() {
        SQLiteDatabase db = this.getWritableDatabase();
        String URL = "";
        String selectQuery = "SELECT " + coluse_customer_analysis + " FROM "
                + serviceaddressTable;
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    // Customer contact = new Customer();
                    // contact.setID(Integer.parseInt(cursor.getString(0)));
                    // contact.setName(cursor.getString(1));
                    // contact.setPhoneNumber(cursor.getString(2));
                    // Adding contact to list
                    URL = cursor.getString(0);
                } while (cursor.moveToNext());
            }

            // return contact list
            if (URL == null)
                URL = "";
            return URL;
        } finally {
            cursor.close();
        }

    }

    String getServerIP() {
        SQLiteDatabase db = this.getWritableDatabase();
        String URL = "";
        String selectQuery = "SELECT " + colurl + " FROM "
                + serviceaddressTable;
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    // Customer contact = new Customer();
                    // contact.setID(Integer.parseInt(cursor.getString(0)));
                    // contact.setName(cursor.getString(1));
                    // contact.setPhoneNumber(cursor.getString(2));
                    // Adding contact to list
                    URL = cursor.getString(0);
                } while (cursor.moveToNext());
            }

            // return contact list
            if (URL == null)
                URL = "";
            return URL;
        } finally {
            cursor.close();
        }

    }

    // added by WaiWL on 11/08/2015
    Boolean isValueItem(String code) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT count(*) FROM " + ItemTable + " where "
                + colvalueitem + "='true' and " + colcode + " = "
                + Integer.parseInt(code);
        Cursor cursor = db.rawQuery(query, null);
        int tmpcount = 0;
        try {
            if (cursor.moveToFirst()) {
                do {
                    tmpcount = cursor.getInt(0);
                } while (cursor.moveToNext());
            }

            if (tmpcount > 0)
                return true;
            else
                return false;
        } finally {
            cursor.close();
        }
    }


    Boolean isSoldOutItem(String code) {

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT count(*) FROM " + SoldOutTable + " where "
                + colsoldoutcode + " = " + Integer.parseInt(code);
        Cursor cursor = db.rawQuery(query, null);
        int tmpcount = 0;
        try {
            if (cursor.moveToFirst()) {
                do {
                    tmpcount = cursor.getInt(0);
                } while (cursor.moveToNext());
            }

            if (tmpcount > 0)
                return true;
            else
                return false;
        } finally {
            cursor.close();
        }

    }

    //added by EKK on 14-12-2019
    Boolean isPromotionItem(String code) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT count(*) FROM " + PromotionTable + " where code = " + code;
        Cursor cursor = db.rawQuery(query, null);
        int tmpcount = 0;
        try {
            if (cursor.moveToFirst()) {
                do {
                    tmpcount = cursor.getInt(0);
                } while (cursor.moveToNext());
            }

            if (tmpcount > 0)
                return true;
            else
                return false;
        } finally {
            cursor.close();
        }
    }

    //added by EKK on 14-12-2019
    String getPromotionType(String code) {
        SQLiteDatabase db = this.getWritableDatabase();
        String type = "";
        String selectQuery = "SELECT " + colPromotion + " FROM "
                + PromotionTable + " where code = " + code;
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    // Customer contact = new Customer();
                    // contact.setID(Integer.parseInt(cursor.getString(0)));
                    // contact.setName(cursor.getString(1));
                    // contact.setPhoneNumber(cursor.getString(2));
                    // Adding contact to list
                    type = cursor.getString(0);
                } while (cursor.moveToNext());
            }

            // return contact list
            if (type == null)
                type = "";
            return type;
        } finally {
            cursor.close();
        }
    }

    // added by WaiWL on 10/06/2015
    Boolean getOfflineFlag() {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT " + colisOffline + " FROM "
                + serviceaddressTable;
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    int offlineflag = Integer
                            .parseInt(cursor.getString(0) == null ? "0"
                                    : cursor.getString(0));
                    return Boolean.parseBoolean(offlineflag == 1 ? "true"
                            : "false");
                } while (cursor.moveToNext());
            }
            return false;
        } finally {
            cursor.close();
        }
    }

    //added by ZYP 17-12-2019 (unicode flag)
    Boolean isUnicode() {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT " + colisUnicode + " FROM "
                + serviceaddressTable;
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    int offlineflag = Integer
                            .parseInt(cursor.getString(0) == null ? "0"
                                    : cursor.getString(0));
                    return Boolean.parseBoolean(offlineflag == 1 ? "true"
                            : "false");
                } while (cursor.moveToNext());
            }
            return false;
        } finally {
            cursor.close();
        }
    }


    // ////////

    Boolean getRegisterFlag() {
        SQLiteDatabase db = this.getWritableDatabase();
        Boolean register = false;
        String selectQuery = "SELECT " + colregister + " FROM "
                + serviceaddressTable;
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    // Customer contact = new Customer();
                    // contact.setID(Integer.parseInt(cursor.getString(0)));
                    // contact.setName(cursor.getString(1));
                    // contact.setPhoneNumber(cursor.getString(2));
                    // Adding contact to list
                    int registerflag = Integer
                            .parseInt(cursor.getString(0) == null ? "0"
                                    : cursor.getString(0));
                    register = Boolean.parseBoolean(registerflag == 1 ? "true"
                            : "false");
                } while (cursor.moveToNext());
            }
            return register;
        } finally {
            cursor.close();
        }

    }

    String getuse_unit() {
        SQLiteDatabase db = this.getWritableDatabase();
        String URL = "";
        String selectQuery = "SELECT " + coluse_unit + " FROM "
                + serviceaddressTable;
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    // Customer contact = new Customer();
                    // contact.setID(Integer.parseInt(cursor.getString(0)));
                    // contact.setName(cursor.getString(1));
                    // contact.setPhoneNumber(cursor.getString(2));
                    // Adding contact to list
                    URL = cursor.getString(0);
                } while (cursor.moveToNext());
            }

            // return contact list
            if (URL == null)
                URL = "";
            return URL;
        } finally {
            cursor.close();
        }

    }

    //Added by ArkarMoe on [15/12/2016]
    String getUseHotel() {
        SQLiteDatabase db = this.getWritableDatabase();
        String URL = "";
        String selectQuery = "SELECT " + coluse_hotel + " FROM "
                + serviceaddressTable;
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    // Customer contact = new Customer();
                    // contact.setID(Integer.parseInt(cursor.getString(0)));
                    // contact.setName(cursor.getString(1));
                    // contact.setPhoneNumber(cursor.getString(2));
                    // Adding contact to list
                    URL = cursor.getString(0);
                } while (cursor.moveToNext());
            }

            // return contact list
            if (URL == null)
                URL = "";
            return URL;
        } finally {
            cursor.close();
        }

    }

    //////////////
    //Added by Arkar Moe on [24/06/2016] for (Res-0085)
    String getuse_unit_type() {                                    //Adding default UnitType in Appsetting
        SQLiteDatabase db = this.getWritableDatabase();
        String URL = "";
        String sSql = "SELECT %s FROM %s where %s = '%s'";
        Cursor cursor = db.rawQuery(String.format(sSql, colSetting_Value,
                AppSetting_table, colSetting_Name, "use_unit_type"), null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    // Customer contact = new Customer();
                    // contact.setID(Integer.parseInt(cursor.getString(0)));
                    // contact.setName(cursor.getString(1));
                    // contact.setPhoneNumber(cursor.getString(2));
                    // Adding contact to list
                    URL = cursor.getString(0);
                } while (cursor.moveToNext());
            }

            // return contact list
            if (URL == null)
                URL = "";
            return URL;
        } finally {
            cursor.close();
        }

    }

    boolean getuse_Mealtype() {                                    //Added by SMH on 30/05/2017
        SQLiteDatabase db = this.getWritableDatabase();

        String sSql = "SELECT %s FROM %s where %s = '%s' and %s ='%s'";
        Cursor cursor = db.rawQuery(String.format(sSql, colSetting_Value,
                AppSetting_table, colSetting_Name, "use_mealtype", colSetting_Value, "Y"), null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    return true;
                } while (cursor.moveToNext());
            }
            // return contact list
            return false;
        } finally {
            cursor.close();
        }

    }

    //added by EKK on 14-12-2019
    boolean getuse_Promotion() {                                    //Added by SMH on 30/05/2017
        SQLiteDatabase db = this.getWritableDatabase();

        String sSql = "SELECT %s FROM %s where %s = '%s' and %s ='%s'";
        Cursor cursor = db.rawQuery(String.format(sSql, colSetting_Value,
                AppSetting_table, colSetting_Name, "use_promotion", colSetting_Value, "Y"), null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    return true;
                } while (cursor.moveToNext());
            }
            // return contact list
            return false;
        } finally {
            cursor.close();
        }

    }

    //(Res-0085)

    boolean getchangeparcelprice() {                                    //Added by SMH on 30/05/2017
        SQLiteDatabase db = this.getWritableDatabase();

        String sSql = "SELECT %s FROM %s where %s = '%s' and %s ='%s'";
        Cursor cursor = db.rawQuery(String.format(sSql, colSetting_Value,
                AppSetting_table, colSetting_Name, "change_price_parcel", colSetting_Value, "Y"), null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    return true;
                } while (cursor.moveToNext());
            }
            // return contact list
            return false;
        } finally {
            cursor.close();
        }

    }

    boolean GetModifierOrgQty() {                                    //Added by SMH on 2017-08-15
        SQLiteDatabase db = this.getWritableDatabase();

        String sSql = "SELECT %s FROM %s where %s = '%s' and %s ='%s'";
        Cursor cursor = db.rawQuery(String.format(sSql, colSetting_Value,
                AppSetting_table, colSetting_Name, "ModifierOrgQty", colSetting_Value, "Y"), null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    return true;
                } while (cursor.moveToNext());
            }
            // return contact list
            return false;
        } finally {
            cursor.close();
        }

    }


    String getDevice_Name() {                                    //Adding default UnitType in Appsetting
        SQLiteDatabase db = this.getWritableDatabase();
        String URL = "";
        String sSql = "SELECT %s FROM %s ";
        Cursor cursor = db.rawQuery(String.format(sSql, colDeviceName,
                serviceaddressTable), null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    // Customer contact = new Customer();
                    // contact.setID(Integer.parseInt(cursor.getString(0)));
                    // contact.setName(cursor.getString(1));
                    // contact.setPhoneNumber(cursor.getString(2));
                    // Adding contact to list
                    URL = cursor.getString(0);
                } while (cursor.moveToNext());
            }

            // return contact list
            if (URL == null)
                URL = "";
            return URL;
        } finally {
            cursor.close();
        }

    }

    String getpriceforParcel(String code) {
        SQLiteDatabase db = this.getWritableDatabase();
        String URL = "";
        String selectQuery = "SELECT " + colParcel_Price + " FROM "
                + ItemTable + " where " + colcode + "='" + code + "'";
        ;
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    // Customer contact = new Customer();
                    // contact.setID(Integer.parseInt(cursor.getString(0)));
                    // contact.setName(cursor.getString(1));
                    // contact.setPhoneNumber(cursor.getString(2));
                    // Adding contact to list
                    URL = cursor.getString(0);
                } while (cursor.moveToNext());
            }

            // return contact list

            if (URL == null)
                URL = "";
            return URL;
        } finally {
            cursor.close();
        }

    }

    String getLanguage() {
        SQLiteDatabase db = this.getWritableDatabase();
        String URL = "";
        String selectQuery = "SELECT " + collanguage + " FROM "
                + serviceaddressTable;
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    // Customer contact = new Customer();
                    // contact.setID(Integer.parseInt(cursor.getString(0)));
                    // contact.setName(cursor.getString(1));
                    // contact.setPhoneNumber(cursor.getString(2));
                    // Adding contact to list
                    URL = cursor.getString(0);
                } while (cursor.moveToNext());
            }

            // return contact list

            if (URL == null)
                URL = "";
            return URL;
        } finally {
            cursor.close();
        }

    }

    String getisContinuousSave() {
        SQLiteDatabase db = this.getWritableDatabase();
        String URL = "";
        String selectQuery = "SELECT " + coluseiscontinuoussave + " FROM "
                + serviceaddressTable;
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    // Customer contact = new Customer();
                    // contact.setID(Integer.parseInt(cursor.getString(0)));
                    // contact.setName(cursor.getString(1));
                    // contact.setPhoneNumber(cursor.getString(2));
                    // Adding contact to list
                    URL = cursor.getString(0);
                } while (cursor.moveToNext());
            }

            // return contact list

            if (URL == null)
                URL = "";
            return URL;
        } finally {
            cursor.close();
        }

    }

    // added by WaiWL on 10/06/2015 --for offline mode
    void AddOfflineMode(String isOffline) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(colisOffline, isOffline);

        if (getServiceURL().equals("")) {
            db.execSQL("Delete from  " + serviceaddressTable);
            db.insert(serviceaddressTable, null, cv);
        } else {
            db.update(serviceaddressTable, cv, null, null);
        }
        db.close();
    }

    //added by ZYP 17-12-2019
    void setUnicode(String isunicode) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(colisUnicode, isunicode);

        if (getServiceURL().equals("")) {
            db.execSQL("Delete from  " + serviceaddressTable);
            db.insert(serviceaddressTable, null, cv);
        } else {
            db.update(serviceaddressTable, cv, null, null);
        }
        db.close();
    }

    // /////////////////////

    void AddServiceAddress(String URL, String Language, String Remark) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        if (!URL.equals("")) {
            if (URL.contains(":")) {
                String[] strings = URL.split(":");
                //cv.put(colurl, strings[0]);
                cv.put(colport, strings[1]);
            }
            cv.put(colurl, URL);

        }

        if (!Language.equals(""))
            cv.put(collanguage, Language);
        cv.put(colremark, Remark);

        if (getServiceURL().equals("")) {
            db.execSQL("Delete from  " + serviceaddressTable);
            db.insert(serviceaddressTable, null, cv);
        } else {
            db.update(serviceaddressTable, cv, null, null);
        }
        db.close();
    }

    void UpdateTimeLog(String TimeLog) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(coltimelog, TimeLog);

        db.update(serviceaddressTable, cv, null, null);
        db.close();
    }

    public String getSaleIDbyInvoiceid(String Invoiceno) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT " + colsaleid + " FROM " + SaleTable
                + " where " + colinvoiceno + "='" + Invoiceno + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    return cursor.getString(0);
                } while (cursor.moveToNext());
            }
            return "";
        } finally {
            cursor.close();
        }

    }

    //added by EKK on 24-02-2020
    String getsaleCurrbyitemid(String itemid) {            //Adding default unit_type in Appsetting
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT " + colOrgCurr + " FROM " + ItemTable
                + " where " + colcode + " = " + itemid;

        Cursor cursor = db.rawQuery(selectQuery, null);

        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {

                return cursor.getString(0);


            } else return "";
            //return "";
        } finally {
            cursor.close();
        }

    }


    List<ItemsObj> getItemslistbyclassid(String classid, String str) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<ItemsObj> list = new ArrayList<ItemsObj>();
        /*
         * String selectQuery = "SELECT "+ ItemPhototable+"."+colphoto + ","
         * +ItemTable+".*" + " FROM " + ItemTable + " inner join " +
         * ItemPhototable + " on " + ItemTable
         * +"."+colusrcode+" = "+ItemPhototable+"."+ colusrcode+ " where "
         * +colclassid +" = " + classid + " order by "+ colusrcode;
         */
        String selectQuery = "";
        if (getAppSetting("ItemOrderStyle").equals("0")) {
            selectQuery = "SELECT " + ItemTable + ".*" + " FROM "
                    + ItemTable + " where " + colclassid + " = " + classid
                    + " and " + coltmpInactive + "='false' "
                    + str
                    + " order by " + colsortid + "," + colusrcode;
        } else {
            selectQuery = "SELECT " + ItemTable + ".*" + " FROM "
                    + ItemTable + " where " + colclassid + " = " + classid
                    + " and " + coltmpInactive + "='false' "
                    + str
                    + " order by " + coldescription + " COLLATE NOCASE ASC";
        }

        Cursor cursor = db.rawQuery(selectQuery, null);

        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    ItemsObj tablesobj = new ItemsObj();
                    tablesobj.setcode(cursor.getString(0));
                    tablesobj.setusr_code(cursor.getString(1));
                    tablesobj.setdescription(cursor.getString(2));
                    tablesobj.setsale_price(cursor.getString(3));
                    tablesobj.setsale_price1(cursor.getString(4));
                    tablesobj.setsale_price2(cursor.getString(5));
                    tablesobj.setsale_price3(cursor.getString(6));
                    tablesobj.setcaldiscount(cursor.getString(7));
                    tablesobj.setgtax(cursor.getString(8));
                    tablesobj.setstax(cursor.getString(9));
                    tablesobj.setcategoryid(cursor.getString(10));
                    tablesobj.setcategoryname(cursor.getString(11));
                    tablesobj.setclassid(cursor.getString(12));
                    tablesobj.setclassname(cursor.getString(13));
                    tablesobj.setitemcolorRGB(cursor.getString(23));
                    tablesobj.setphoto(getItemPhotoByUserCode(tablesobj
                            .getusr_code()));
                    tablesobj.setdescription2(cursor.getString(28));
                    tablesobj.setdescription3(cursor.getString(29));
                    // Adding tableobj to list
                    list.add(tablesobj);
                } while (cursor.moveToNext());
            }

            // return contact list
            return list;
        } finally {
            cursor.close();
        }

    }

    //added by KLM 11022022
    List<ItemsObj> getItemslist(String code, String maincode) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<ItemsObj> list = new ArrayList<ItemsObj>();

//		String selectQuery = "SELECT Item_List.code,Items.usr_code,Items.description,Item_List.unit_qty " +
//				"FROM Item_List inner join Items on Items.category2=Item_List.category2 where Item_List.category2='" + code + "' and Item_List.main_code='"+ maincode+
//				"' order by Items.usr_code";

//		String selectQuery = "SELECT distinct Items.code,Items.usr_code,Items.description,Item_List.unit_qty " +
//				"FROM Item_List join Items on Items.category2=Item_List.category2 where Item_List.category2=5"+
//				" order by Items.usr_code";
        String selectQuery = "select distinct code,usr_code,description,category2 from Items where category2!=0 and category2=(select distinct category2 from Item_List where " + /*code='" + code + "'*/"  main_code='" + maincode + "') order by usr_code";
        //String selectQuery = "select distinct code,usr_code,description,category2 from Items where category2='5'";/*(select category2 from Item_List where code='"+code+"' and main_code='"+maincode+"')"*/;
//		String selectQuery = "select count(*) as count from Items where category2='5'";
//		String selectQuery = "SELECT il.code,i.usr_code,i.description,il.unit_qty " +
//				"FROM Item_List il,Items i where i.category2=il.category2 and il.code=" + code + " and il.main_code="+ maincode+
//				" order by i.usr_code";

//		String selectQuery = "SELECT code,usr_code,description " +
//				"FROM Items where code="+code+
//				" order by usr_code";


//		String selectQuery = "SELECT b.code,i.usr_code,i.description,unit_qty " +
//				"FROM Brand_Item_List b inner join Items i on i.code=b.code where i.sale_price='" + Price + "' "+
//				" order by i.usr_code";


        Cursor cursor = db.rawQuery(selectQuery, null);
        try {

            for (int i = 0; i < cursor.getCount(); i++) {
                if (cursor.moveToPosition(i)) {
                    ItemsObj tablesobj = new ItemsObj();
                    tablesobj.setcode(cursor.getString(0));
                    tablesobj.setusr_code(cursor.getString(1));
                    tablesobj.setdescription(cursor.getString(2));
                    String category2 = cursor.getString(3);
                    System.out.println(cursor.getString(0) + " " + cursor.getString(1) + " " + cursor.getString(2) + " " + cursor.getString(3));
                    //tablesobj.setunitqty(cursor.getString(3));
                    list.add(tablesobj);
                }
            }


            // looping through all rows and adding to list
//			if (cursor.moveToFirst()) {
//				do {
////					String count=cursor.getString(0);
//					ItemsObj tablesobj = new ItemsObj();
//					tablesobj.setcode(cursor.getString(0));
//					tablesobj.setusr_code(cursor.getString(1));
//					tablesobj.setdescription(cursor.getString(2));
//					String category2=cursor.getString(3);
//					System.out.println(cursor.getString(0)+" "+cursor.getString(1)+" "+cursor.getString(2)+" "+cursor.getString(3));
//					//tablesobj.setunitqty(cursor.getString(3));
//					list.add(tablesobj);
//				} while (cursor.moveToNext());


            // return contact list
            return list;
        } finally {
            cursor.close();
        }

    }

    List<ItemsObj> getItemslist() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<ItemsObj> list = new ArrayList<ItemsObj>();

        String selectQuery = "SELECT b.code,i.usr_code,i.description,unit_qty " +
                "FROM Brand_Item_List b inner join Items i on i.code=b.code " +
                "order by b.code";

        Cursor cursor = db.rawQuery(selectQuery, null);

        try {// looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    ItemsObj tablesobj = new ItemsObj();
                    tablesobj.setcode(cursor.getString(0));
                    tablesobj.setusr_code(cursor.getString(1));
                    tablesobj.setdescription(cursor.getString(2));
                    tablesobj.setunitqty(cursor.getString(3));
                    list.add(tablesobj);
                } while (cursor.moveToNext());
            }

            // return contact list
            return list;
        } finally {
            cursor.close();
        }

    }

    // added by WaiWL on 16/09/2015
    public void updateMIFinishedItems(int table_name_id) {
        SQLiteDatabase db2 = this.getWritableDatabase();
        db2.execSQL("Update " + kitchenTable + " Set " + colfinishshow
                + " = 'Y'" + " where " + colfinishshow + " = 'N' and ("
                + colcook_status + " = 'F' or " + colcook_status + " ='S') and " + coluserid + " = "
                + getwaiterid() + " and " + colTable_Name_ID + "="
                + table_name_id);
        db2.close();
    }

    // ////////////

    public List<KitchendataObj> getkitchenfinishlist() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<KitchendataObj> list = new ArrayList<KitchendataObj>();

        String selectQuery = "SELECT distinct " + colTable_Name_ID + "  FROM "
                + kitchenTable + " where " + colfinishshow + " = 'N' and  ("
                + colcook_status + " = 'F' or " + colcook_status + " ='S') and " + coluserid + " = "
                + getwaiterid();
        // " order by "+ colcode;
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    KitchendataObj tablesobj = new KitchendataObj();
                    tablesobj.setTable_Name_ID(Integer.parseInt(cursor
                            .getString(0)));
                    tablesobj.setdescription(getkitchenfinishlistbytableid(
                            tablesobj.getTable_Name_ID()).getdescription());
                    //	tablesobj.setcook_status(cursor.getString(1));
                    list.add(tablesobj);
                } while (cursor.moveToNext());
            }

            /*
             * SQLiteDatabase db2= this.getWritableDatabase();
             * db2.execSQL("Update " + kitchenTable + " Set " + colfinishshow +
             * " = 'Y'" + " where " + colfinishshow +" = 'N' and "
             * +colcook_status +" = 'F' and " + coluserid + " = "+
             * getwaiterid()); db2.close();
             */

            return list;
        } finally {
            cursor.close();
        }

    }


    public KitchendataObj getkitchenfinishlistbytableid(int tableid) {
        SQLiteDatabase db = this.getWritableDatabase();

        String selectQuery = "SELECT " + colcode + "," + colunit_qty + "," + colcook_status + "  FROM "
                + kitchenTable + " where " + colfinishshow + " = 'N' and ("
                + colcook_status + " = 'F' or " + colcook_status + " ='S' ) and " + coluserid + " = "
                + getwaiterid() + " and " + colTable_Name_ID + " = " + tableid;
        // " order by "+ colcode;
        Cursor cursor = db.rawQuery(selectQuery, null);

        try {
            KitchendataObj tablesobj = new KitchendataObj();
            String itemlist = "";
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {

                    if (cursor.getString(2).toString().equals("S")) {
                        itemlist = itemlist
                                + getItemsbyitemid((cursor.getString(0)))
                                .getdescription() + " - "
                                + cursor.getString(1).replace(".0", "") + "(Sold Out) ,";
                    } else {
                        itemlist = itemlist
                                + getItemsbyitemid((cursor.getString(0)))
                                .getdescription() + " - "
                                + cursor.getString(1).replace(".0", "") + ",";
                    }

                } while (cursor.moveToNext());
            }
            tablesobj.setdescription(itemlist);
            return tablesobj;
        } finally {
            cursor.close();
        }

    }

    //Added by KLM for chaning set menu item 13062022
    List<ItemsObj> getItemslistbysaleprice(String Price, String code, String mainCode) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<ItemsObj> list = new ArrayList<ItemsObj>();

/*		String selectQuery = "SELECT " + ItemTable + ".*" + " FROM "
				+ ItemTable + " where " + colsaleprice + " = '" + Price + "' "
				+ " and " + coltmpInactive + "='false' " +
				" order by " + colusrcode;*/


//		String selectQuery = "SELECT b.code,i.usr_code,i.description,unit_qty " +
//				"FROM Item_List b inner join Items i on i.category2=b.category2 where i.sale_price='" + Price +"'"+/*+ "' and b.category2=(select category2 from Item_List where code='"+code+"' and main_code='"+mainCode+"')"+*/
//				" order by i.usr_code";
//		String selectQuery="select Items.* from Item_List inner join Items on Items.category2=Item_List.category2 where Items.sale_price='33000.0000'";

		/*String selectQuery = "select code,usr_code,description from Items where sale_price='" + Price + "' and category2=(select category2 from Item_List where code='" +
				code + "' and main_code='" + mainCode + "')";*/
        String selectQuery = "select code,usr_code,description from Items where sale_price='" + Price + "' and category2=(select category2 from Item_List where main_code='" + mainCode + "')";

        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    ItemsObj tablesobj = new ItemsObj();
                    tablesobj.setcode(cursor.getString(0));
                    tablesobj.setusr_code(cursor.getString(1));
                    tablesobj.setdescription(cursor.getString(2));
                    //tablesobj.setunitqty(cursor.getString(3));
                    list.add(tablesobj);
                } while (cursor.moveToNext());
            }

            // return contact list
            return list;
        } finally {
            cursor.close();
        }

    }

    List<ItemsObj> getItemslistbysaleprice(String Price) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<ItemsObj> list = new ArrayList<ItemsObj>();

/*		String selectQuery = "SELECT " + ItemTable + ".*" + " FROM "
				+ ItemTable + " where " + colsaleprice + " = '" + Price + "' "
				+ " and " + coltmpInactive + "='false' " +
				" order by " + colusrcode;*/


        String selectQuery = "SELECT b.code,i.usr_code,i.description,unit_qty " +
                "FROM Brand_Item_List b inner join Items i on i.code=b.code where i.sale_price='" + Price + "' " +
                " order by i.usr_code";


        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    ItemsObj tablesobj = new ItemsObj();
                    tablesobj.setcode(cursor.getString(0));
                    tablesobj.setusr_code(cursor.getString(1));
                    tablesobj.setdescription(cursor.getString(2));
                    tablesobj.setunitqty(cursor.getString(3));
                    list.add(tablesobj);
                } while (cursor.moveToNext());
            }

            // return contact list
            return list;
        } finally {
            cursor.close();
        }

    }

    //Added by KLM for chaning set menu item 13062022
    List<ItemsObj> SearchItemslistbyDescriptionandprice(String Description, String Price, String code, String mainCode) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<ItemsObj> list = new ArrayList<ItemsObj>();

//		String selectQuery = "SELECT " + ItemTable + ".*" + " FROM "
//				+ ItemTable + " where " + coldescription + " like '%" + Description + "%' and " + colsaleprice + " = '" + Price + "'"
//				+ " and " + coltmpInactive + "='false' " +
//				" order by " + colusrcode;
        String selectQuery = "";
        if (Price == "-")
            selectQuery = "select code,usr_code,description from Items where description like '%" + Description + "%' and Inactive='false' and category2=(select category2 from Item_List where code='" +
                    code + "' and main_code='" + mainCode + "') order by usr_code"
                    ;
        else {
            selectQuery = "select code,usr_code,description from Items where description like '%" + Description + "%' and Inactive='false' and sale_price='" + Price + "' and category2=(select category2 from Item_List where code='" +
                    code + "' and main_code='" + mainCode + "') order by usr_code"
            ;
        }

        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    ItemsObj tablesobj = new ItemsObj();
                    tablesobj.setcode(cursor.getString(0));
                    tablesobj.setusr_code(cursor.getString(1));
                    tablesobj.setdescription(cursor.getString(2));
                    list.add(tablesobj);
                } while (cursor.moveToNext());
            }

            // return contact list
            return list;
        } finally {
            cursor.close();
        }

    }

    List<ItemsObj> SearchItemslistbyDescription(String Description) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<ItemsObj> list = new ArrayList<ItemsObj>();

        String selectQuery = "SELECT " + ItemTable + ".*" + " FROM "
                + ItemTable + " where " + coldescription + " like '%" + Description + "%' "
                + " and " + coltmpInactive + "='false' " + " and " + colcode + " NOT IN "
                + "( SELECT " + colsoldoutcode + " FROM " + SoldOutTable + " ) " +
                GlobalClass.strmenucategory_grouptype + //added WHM [2020-10-11] YGN2-200983
                " order by " + colusrcode;
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    ItemsObj tablesobj = new ItemsObj();
                    tablesobj.setcode(cursor.getString(0));
                    tablesobj.setusr_code(cursor.getString(1));
                    tablesobj.setdescription(cursor.getString(2));
                    //tablesobj.setdescription((!LoginActivity.isUnicode)?Dictionary.Uniconverter.convert(cursor.getString(2)):cursor.getString(2));
                    tablesobj.setsale_price(cursor.getString(3));
                    list.add(tablesobj);
                } while (cursor.moveToNext());
            }

            // return contact list
            return list;
        } finally {
            cursor.close();
        }

    }

    List<ItemsObj> SearchItemslistbyDescriptionandprice(String Description, String Price) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<ItemsObj> list = new ArrayList<ItemsObj>();

        String selectQuery = "SELECT " + ItemTable + ".*" + " FROM "
                + ItemTable + " where " + coldescription + " like '%" + Description + "%' and " + colsaleprice + " = '" + Price + "'"
                + " and " + coltmpInactive + "='false' " +
                " order by " + colusrcode;
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    ItemsObj tablesobj = new ItemsObj();
                    tablesobj.setcode(cursor.getString(0));
                    tablesobj.setusr_code(cursor.getString(1));
                    tablesobj.setdescription(cursor.getString(2));
                    list.add(tablesobj);
                } while (cursor.moveToNext());
            }

            // return contact list
            return list;
        } finally {
            cursor.close();
        }

    }


    List<ItemsObj> getItemslistbycode(String code) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<ItemsObj> list = new ArrayList<ItemsObj>();

        String selectQuery = "SELECT b.code,i.usr_code,i.description,unit_qty " +
                "FROM Brand_Item_List b inner join Items i on i.code=b.code where b.item_code='" + code + "' " +
                " order by i.usr_code";

        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    ItemsObj tablesobj = new ItemsObj();
                    tablesobj.setcode(cursor.getString(0));
                    tablesobj.setusr_code(cursor.getString(1));
                    tablesobj.setdescription(cursor.getString(2));
                    tablesobj.setunitqty(cursor.getString(3));
                    list.add(tablesobj);
                } while (cursor.moveToNext());
            }

            // return contact list
            return list;
        } finally {
            cursor.close();
        }

    }


    ItemsObj getItemsbyitemid(String itemid) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<ItemsObj> list = new ArrayList<ItemsObj>();
        String selectQuery = "SELECT  " + ItemTable + ".*" + " FROM "
                + ItemTable + " where " + colcode + " = " + itemid + " and "
                + coltmpInactive + "='false' " + // added by WaiWL on 06/08/2015
                // --add checking inactive 0
                " order by " + coldescription;
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    ItemsObj tablesobj = new ItemsObj();
                    tablesobj.setcode(cursor.getString(0));
                    tablesobj.setusr_code(cursor.getString(1));
                    tablesobj.setdescription(cursor.getString(2));
                    tablesobj.setsale_price(cursor.getString(3));
                    tablesobj.setsale_price1(cursor.getString(4));
                    tablesobj.setsale_price2(cursor.getString(5));
                    tablesobj.setsale_price3(cursor.getString(6));
                    tablesobj.setcaldiscount(cursor.getString(7));
                    tablesobj.setgtax(cursor.getString(8));
                    tablesobj.setstax(cursor.getString(9));
                    tablesobj.setcategoryid(cursor.getString(10));
                    tablesobj.setcategoryname(cursor.getString(11));
                    tablesobj.setclassid(cursor.getString(12));
                    tablesobj.setclassname(cursor.getString(13));
                    tablesobj.setremark(cursor.getString(14));
                    tablesobj.setphoto(getItemPhotoByUserCode(tablesobj
                            .getusr_code()));
                    tablesobj.setissetmenu(cursor.getString(21));
                    tablesobj.setdescription2(cursor.getString(28));
                    tablesobj.setdescription3(cursor.getString(29));
                    tablesobj.setOrg_curr(cursor.getString(32));//added WHM [2020-05-27] currency


                    return tablesobj;
                } while (cursor.moveToNext());
            }

            // return contact list
            return null;
        } finally {
            cursor.close();
        }

    }

    List<ItemsObj> getItemsbydescription(String Description) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<ItemsObj> list = new ArrayList<ItemsObj>();

//		String selectQuery = "SELECT  " + ItemTable + ".*" + " FROM "
//				+ ItemTable + " where " + coldescription + " = " + itemdescription + " and "
//				+ coltmpInactive + "='false' " +
//				" order by " + coldescription;


        String selectQuery = "SELECT " + ItemTable + ".*" + " FROM "
                + ItemTable + " where " + coldescription + " like '" + Description + "' "
                + " and " + coltmpInactive + "='false' " +
                " order by " + colusrcode;

//		" like '" + Description + "%' "
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    ItemsObj tablesobj = new ItemsObj();
                    tablesobj.setcode(cursor.getString(0));
                    tablesobj.setusr_code(cursor.getString(1));
                    tablesobj.setdescription(cursor.getString(2));
                    list.add(tablesobj);
                } while (cursor.moveToNext());
            }

            // return contact list
            return list;
        } finally {
            cursor.close();
        }

    }

    String getModifiergroupidbyitemid(String itemid) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT " + colmodifiedgroupid + " FROM "
                + ItemTable + " where " + colcode + " = " + itemid + " and "
                + coltmpInactive + "='false' " + // added by WaiWL on 06/08/2015
                // --add checking inactive 0
                " order by " + colcode;
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {

                    return cursor.getString(0);
                } while (cursor.moveToNext());
            }
            return "";

        } finally {
            cursor.close();
        }

    }

    List<Salesmen> getsalemen_ListByUserID(String userid) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<Salesmen> list = new ArrayList<Salesmen>();

//		String selectQuery = "SELECT  * FROM " + SalemenTable + " where "
//				+ colposuserid + " = " + userid;

        String selectQuery = "SELECT  * FROM " + SalemenTable + " where "
                + colposuserid + " = '" + userid + "'";

        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Salesmen tablesobj = new Salesmen();
                    tablesobj.setsalesmen_id(Integer.parseInt(cursor
                            .getString(0)));
                    tablesobj.setsalesmen_name(cursor.getString(1));
                    tablesobj.setshortcode(cursor.getString(2));
                    tablesobj.setposuserid(cursor.getString(3));
                    list.add(tablesobj);
                } while (cursor.moveToNext());
            }
            // return contact list
            return list;
        } finally {
            cursor.close();
        }

    }

    List<ItemsObj> getmodifierItemslistbyitemid(String itemid) {
        SQLiteDatabase db = this.getWritableDatabase();
        String modifiedgroupid = getModifiergroupidbyitemid(itemid);
        List<ItemsObj> list = new ArrayList<ItemsObj>();
        if (!modifiedgroupid.equals("")) {
            String selectQuery = "SELECT  * FROM " + ModifiedItemTable
                    + " where " + colmodifiedgroupid + " = '" + modifiedgroupid
                    + "' " + " and " + coltmpInactive + "='false' " + // added
                    // by
                    // WaiWL
                    // on
                    // 06/08/2015
                    // --add
                    // checking
                    // inactive
                    // 0
                    "order by " + colcode;
            // String selectQuery = "SELECT  * FROM " + ModifiedItemTable
            // +" order by "+ colcode;
            Cursor cursor = db.rawQuery(selectQuery, null);
            try {
                // looping through all rows and adding to list
                if (cursor.moveToFirst()) {
                    do {
                        ItemsObj tablesobj = new ItemsObj();
                        tablesobj.setcode(cursor.getString(0));
                        tablesobj.setusr_code(cursor.getString(1));
                        tablesobj.setdescription(cursor.getString(2));
                        tablesobj.setsale_price(cursor.getString(3));
                        tablesobj.setsale_price1(cursor.getString(4));
                        tablesobj.setsale_price2(cursor.getString(5));
                        tablesobj.setsale_price3(cursor.getString(6));
                        tablesobj.setcaldiscount(cursor.getString(7));
                        tablesobj.setgtax(cursor.getString(8));
                        tablesobj.setstax(cursor.getString(9));
                        tablesobj.setcategoryid(cursor.getString(10));
                        tablesobj.setcategoryname(cursor.getString(11));
                        tablesobj.setclassid(cursor.getString(12));
                        tablesobj.setclassname(cursor.getString(13));
                        tablesobj.setmodifiergroupid(cursor.getString(14));
                        // Adding tableobj to list
                        list.add(tablesobj);
                    } while (cursor.moveToNext());
                }
            } finally {
                cursor.close();
            }

        }

        // return contact list
        return list;
    }

    List<SelectedItemModifierObj> getsetmenuItemslistbyitemid(String itemid) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<SelectedItemModifierObj> list = new ArrayList<SelectedItemModifierObj>();
        if (!itemid.equals("")) {
            String selectQuery = "SELECT " + colcode + "," + colunit_qty + " ," + colmax_price + " ," + colmaincode
                    + ", " + colunit_type
                    + " FROM " + Item_ListTable + " where " + colmaincode
                    + " = '" + itemid + "'";
            //MOdified by KNO (15-11-2022)
            //+ "' order by " + colcode;

            Cursor cursor = db.rawQuery(selectQuery, null);

            try {
                if (cursor.moveToFirst()) {
                    do {

                        ItemsObj itemobj = getItemsbyitemid(cursor.getString(0));
                        SelectedItemModifierObj tablesobj = new SelectedItemModifierObj();

                        tablesobj.setitemid(itemobj.getcode());
                        tablesobj.setname(itemobj.getdescription());
                        tablesobj.setqty(cursor.getString(1));
                        tablesobj.set_max_price(cursor.getString(2));
                        tablesobj.set_maincode(itemid);
                        // String price =
                        // getItempricebyitemid(itemobj.getcode());
						/*	String price = "0.0";
						Double amount = ((Double
								.parseDouble(tablesobj.getqty())) * Double
								.parseDouble(price == "" ? "0" : price));*/

                        Double amount = ((Double
                                .parseDouble(tablesobj.getqty())) * Double
                                .parseDouble(itemobj.getsale_price()));

                        tablesobj.setamount(String.valueOf(amount));

                        tablesobj.setstatus("setmenuitem");

                        //addded WHM [2020-06-01] self order
                        tablesobj.setprice(itemobj.getsale_price());
                        tablesobj.setUnit_type(cursor.getString(4));
                        //end whm

                        // Adding tableobj to list
                        list.add(tablesobj);
                    } while (cursor.moveToNext());
                }

            } finally {
                cursor.close();
            }
        }
        // return contact list
        return list;
    }

    // added by WaiWL on 13/08/2015
    Object[] getKTVStartTime(String itemid, String sr) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT " + colKTV_StartTime + ","
                + colKTV_EndTime + " FROM " + SaleItemTable + " where "
                + colcode + " = " + itemid + " and " + colsr + "=" + sr;

        Cursor cursor = db.rawQuery(selectQuery, null);
        Object[] objArray = new Object[2];
        try {
            if (cursor.moveToFirst()) {
                do {
                    objArray[0] = cursor.getString(0);
                    objArray[1] = cursor.getString(1);
                } while (cursor.moveToNext());
            }
            return objArray;
        } finally {
            cursor.close();
        }
    }

    // added by WaiWL on 14/08/2015

    public String getCurrentDateTime() {
        return Json_class.getString(getServiceURL()
                + "/Data/getCurrentDateTime");
    }

    public Object[] getVaueItemAmt(String tranid, String sr, String itemid) {
        Object[] objArray = new Object[5];
        JSONArray itemjsonarray = Json_class.getJson(getServiceURL()
                + "/Data/GetValueItemData?tranid="
                + java.net.URLEncoder.encode(tranid) + "&sr="
                + java.net.URLEncoder.encode(sr) + "&code="
                + java.net.URLEncoder.encode(itemid));
        JSONObject c;
        try {
            c = itemjsonarray.getJSONObject(0);

            objArray[0] = c.getString("KTV_StartTime");
            objArray[1] = c.getString("KTV_EndTime");
            objArray[2] = c.getString("TotTime");
            objArray[3] = c.getString("TotAmt");
            objArray[4] = c.get("unit_qty");

        } catch (JSONException e) {

        }
        return objArray;
    }

    public Object[] CalculateData(String tranid, String stTime, String endTime,
                                  String itemid) {
        Object[] objArray = new Object[2];

        JSONArray itemjsonarray = Json_class.getJson(getServiceURL()
                + "/Data/calculateData?tranid="
                + java.net.URLEncoder.encode(tranid) + "&stTime="
                + java.net.URLEncoder.encode(stTime) + "&endTime="
                + java.net.URLEncoder.encode(endTime) + "&code="
                + java.net.URLEncoder.encode(itemid));
        JSONObject c;
        try {
            c = itemjsonarray.getJSONObject(0);

            objArray[0] = c.getString("TotTime");
            objArray[1] = c.getString("TotAmt");

        } catch (JSONException e) {

        }
        return objArray;
    }

    public void SaveKTVData(String tranid, String stTime, String endTime,
                            String itemid, String sr) {
        JSONArray itemjsonarray = Json_class.getJson(getServiceURL()
                + "/Data/SaveKTVData?tranid="
                + java.net.URLEncoder.encode(tranid) + "&stTime="
                + java.net.URLEncoder.encode(stTime) + "&endTime="
                + java.net.URLEncoder.encode(endTime) + "&code="
                + java.net.URLEncoder.encode(itemid) + "&sr="
                + java.net.URLEncoder.encode(sr));
    }

    public void RemoveKTVData(String tranid, String itemid, String sr) {
        JSONArray itemjsonarray = Json_class.getJson(getServiceURL()
                + "/Data/RemoveKTVData?tranid="
                + java.net.URLEncoder.encode(tranid) + "&code="
                + java.net.URLEncoder.encode(itemid) + "&sr="
                + java.net.URLEncoder.encode(sr));
    }

    // /////////////////////////

    String getItempricebyitemid(String itemid) {
        SQLiteDatabase db = this.getWritableDatabase();

        //added WHM [2020-01-28] MDY2-200141
        String select_saleprice = "sale_price";
        if (cust_pricelevel == 0)
            select_saleprice = "sale_price";
        else if (cust_pricelevel == 1)
            select_saleprice = "sale_price1";
        else if (cust_pricelevel == 2)
            select_saleprice = "sale_price2";
        else if (cust_pricelevel == 3)
            select_saleprice = "sale_price3";
        //end whm

//		String selectQuery = "SELECT " + colsaleprice + " FROM " + ItemTable
//				+ " where " + colcode + " = " + itemid + " and "
//				+ coltmpInactive + "='false' " + // added by WaiWL on 06/08/2015
//													// --add checking inactive 0
//				" order by " + coldescription;

        String selectQuery = "SELECT " + select_saleprice + " FROM " + ItemTable
                + " where " + colcode + " = " + itemid + " and "
                + coltmpInactive + "='false' " + // added by WaiWL on 06/08/2015
                // --add checking inactive 0
                " order by " + coldescription;


        Cursor cursor = db.rawQuery(selectQuery, null);

        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {

                    return cursor.getString(0);

                } while (cursor.moveToNext());
            }
            return "";
        } finally {
            cursor.close();
        }

    }

    //Added by Arkar Moe on [26/06/2016] for (Res-0085)
    String getItemUnitpricebyitemid(String itemid, String unit_type) {            //Adding default unit_type in Appsetting
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT " + colsaleprice + " FROM " + UnitCodeTable
                + " where " + colcode + " = " + itemid + " and "
                + colunittype + " = " + unit_type + // added by WaiWL on 06/08/2015
                // --add checking inactive 0
                " order by " + colunittype;
        Cursor cursor = db.rawQuery(selectQuery, null);

        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {

                    return cursor.getString(0);

                } while (cursor.moveToNext());
            }
            return "";
        } finally {
            cursor.close();
        }

    }                                                                            //(Res-0085)


    List<UnitCodeObj> getunitcodebyitemid(String itemid) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<UnitCodeObj> unitcodeobjlist = new ArrayList<UnitCodeObj>();
        String selectQuery = "SELECT " + colunit + "," + colshortname + ","
                + colsaleprice + " FROM " + UnitCodeTable + " where " + colcode
                + " = " + itemid + " order by " + colunittype;
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    UnitCodeObj unitcodeobj = new UnitCodeObj();
                    unitcodeobj.setunit(cursor.getString(0));
                    unitcodeobj.setshortname(cursor.getString(1));
                    unitcodeobj.setsaleprice(cursor.getString(2));
                    unitcodeobjlist.add(unitcodeobj);
                } while (cursor.moveToNext());
            }
            return unitcodeobjlist;
        } finally {
            cursor.close();
        }

    }

    List<UnitCodeObj> getunitcodebyunit(String itemid, String unit) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<UnitCodeObj> unitcodeobjlist = new ArrayList<UnitCodeObj>();
        String selectQuery = "SELECT " + colunit + "," + colshortname + ","
                + colsaleprice + "," + colunittype + " FROM " + UnitCodeTable
                + " where " + colcode + " = " + itemid + " and " + colunit
                + " = " + unit + " order by " + colunittype + " desc";
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    UnitCodeObj unitcodeobj = new UnitCodeObj();
                    unitcodeobj.setunit(cursor.getString(0));
                    unitcodeobj.setshortname(cursor.getString(1));
                    unitcodeobj.setsaleprice(cursor.getString(2));
                    unitcodeobj.setunit_type(cursor.getString(3));
                    unitcodeobjlist.add(unitcodeobj);

                } while (cursor.moveToNext());
            }
            return unitcodeobjlist;
        } finally {
            cursor.close();
        }

    }

    List<UnitCodeObj> getunitcodebyunittype(String itemid, String unittype) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<UnitCodeObj> unitcodeobjlist = new ArrayList<UnitCodeObj>();
        String selectQuery = "SELECT " + colunit + "," + colshortname + ","
                + colsaleprice + "," + colunittype + "," + colunitname
                + " FROM " + UnitCodeTable + " where " + colcode + " = "
                + itemid + " and " + colunittype + " = " + unittype
                + " order by " + colunittype + " desc";
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    UnitCodeObj unitcodeobj = new UnitCodeObj();
                    unitcodeobj.setunit(cursor.getString(0));
                    unitcodeobj.setshortname(cursor.getString(1));
                    unitcodeobj.setsaleprice(cursor.getString(2));
                    unitcodeobj.setunit_type(cursor.getString(3));
                    unitcodeobj.setunitname(cursor.getString(4));
                    unitcodeobjlist.add(unitcodeobj);

                } while (cursor.moveToNext());
            }
            return unitcodeobjlist;
        } finally {
            cursor.close();
        }

    }

    String getItemNamebyitemid(String itemid) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT " + coldescription + " FROM " + ItemTable
                + " where " + colcode + " = " + itemid + " and "
                + coltmpInactive + "='false' " + // added by WaiWL on 06/08/2015
                // --add checking inactive 0
                " order by " + coldescription;
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {

                    return cursor.getString(0);

                } while (cursor.moveToNext());
            }
            return "";
        } finally {
            cursor.close();
        }

    }

    String getModifierItemnamebycode(String code) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT " + coldescription + " FROM "
                + ModifiedItemTable + " where " + colcode + " = '" + code
                + "' " + " and " + coltmpInactive + "='false' " + // added by
                // WaiWL on
                // 06/08/2015
                // --add
                // checking
                // inactive
                // 0
                " order by " + coldescription;
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {

                    return cursor.getString(0);

                } while (cursor.moveToNext());
            }
            return "";
        } finally {
            cursor.close();
        }

    }

    String getModifierpricebyitemid(String itemid) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT " + colsaleprice + " FROM "
                + ModifiedItemTable + " where " + colcode + " = " + itemid
                + " and " + coltmpInactive + "='false' " + // added by WaiWL on
                // 06/08/2015 --add
                // checking inactive
                // 0
                " order by " + coldescription;
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {

                    return cursor.getString(0);

                } while (cursor.moveToNext());
            }
            return "";
        } finally {
            cursor.close();
        }

    }


    String getSetMenuMaxPricebyitemid(String itemid, String maincode) {
        SQLiteDatabase db = this.getWritableDatabase();
/*		String selectQuery = "SELECT " + colsaleprice + " FROM "
				+ ModifiedItemTable + " where " + colcode + " = " + itemid
				+ " and " + coltmpInactive + "='false' " +
				" order by " + coldescription;*/

        String selectQuery = "SELECT " + colmax_price + " FROM " + Item_ListTable + " where " + colcode + " = '" + itemid + "' and " + colmaincode + "='" + maincode + "'";


        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    return cursor.getString(0);
                } while (cursor.moveToNext());
            }
            return "";
        } finally {
            cursor.close();
        }

    }


    String getlastcode_Sale() {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT max(" + colsaleid + ") FROM " + SaleTable;
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    return cursor.getString(0) == null ? "0" : cursor
                            .getString(0);
                } while (cursor.moveToNext());
            }
            return "0";
        } finally {
            cursor.close();
        }

    }

    String getLastDocID(String url, String prefix) {
        return Json_class.getString(
                url + "/Data/GetDocIDForAndroid?prefix="
                        + java.net.URLEncoder.encode(prefix)).trim();
    }

    public List<PosUser> getPosUserlist() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<PosUser> list = new ArrayList<PosUser>();
        Cursor cursor = null;

        try {

            if (getusetablefilter() == true) {

                String Sql = "SELECT userid,user_short,name,password FROM posuser p inner join setuser s on s.setuserid=p.userid where s.use_tabletuser = 1 order by %s";
                cursor = db.rawQuery(String.format(Sql, coluserid), null);

            } else {
                String Sql = "SELECT userid,user_short,name,password FROM %s where %s = %s  order by %s";
                cursor = db.rawQuery(String.format(Sql, PosUserTable,
                        coluse_touchscreen, "1", coluserid), null);

            }

            if (cursor.moveToFirst()) {
                do {
                    PosUser Userobj = new PosUser();
                    Userobj.setUserId(Integer.valueOf(cursor.getString(0)));
                    Userobj.setShort(cursor.getString(1));
                    Userobj.setName(cursor.getString(2));
                    Userobj.setPassword(cursor.getString(3));

                    list.add(Userobj);
                } while (cursor.moveToNext());

            }

            return list;

        } finally {
            cursor.close();
        }

    }

    public List<PosUser> getPosUserlistForSwitchuser() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<PosUser> list = new ArrayList<PosUser>();
        String sSql = "SELECT userid,user_short,name,password FROM %s where %s = %s and %s != %s  order by %s";
        Cursor cursor = db.rawQuery(String.format(sSql, PosUserTable,
                coluse_touchscreen, "1", coluserid, getwaiterid(), coluserid),
                null);

        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    PosUser Userobj = new PosUser();
                    Userobj.setUserId(Integer.valueOf(cursor.getString(0)));
                    Userobj.setShort(cursor.getString(1));
                    Userobj.setName(cursor.getString(2));
                    Userobj.setPassword(cursor.getString(3));

                    // Adding tableobj to list
                    list.add(Userobj);
                } while (cursor.moveToNext());
            }
            // return contact list
            return list;
        } finally {
            cursor.close();
        }

    }

    public List<PosUser> getAllPosUserlist() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<PosUser> list = new ArrayList<PosUser>();
        String sSql = "SELECT userid,user_short,name,password FROM %s  order by %s";
        Cursor cursor = db.rawQuery(String.format(sSql, PosUserTable,
                coluse_touchscreen, "1", coluserid), null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    PosUser Userobj = new PosUser();
                    Userobj.setUserId(Integer.valueOf(cursor.getString(0)));
                    Userobj.setShort(cursor.getString(1));
                    Userobj.setName(cursor.getString(2));
                    Userobj.setPassword(cursor.getString(3));

                    // Adding tableobj to list
                    list.add(Userobj);
                } while (cursor.moveToNext());
            }
            // return contact list
            return list;
        } finally {
            cursor.close();
        }

    }

    public List<Printer> getAllPrinterlist(String dataurl) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<Printer> list = new ArrayList<Printer>();

        url = dataurl;
        JSONArray itemjsonarray = Json_class.getJson(url + "/Data/GetPrinter");

        for (int i = 0; i < itemjsonarray.length(); i++) {
            JSONObject c;
            try {
                c = itemjsonarray.getJSONObject(i);
                Printer printerobj = new Printer();
                printerobj.setPrinterID(Integer.parseInt(c
                        .getString("PrinterID")));
                printerobj.setPrinterName(c.getString("PrinterName"));
                printerobj.setPrinter(c.getString("Printer"));
                printerobj.set_PrinterStatus(c.getString("PrinterStatus"));
                list.add(printerobj);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return list;
    }

    public PosUser getPosUserByUserID(int userid) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sSql = "SELECT userid,user_short,name,password,all_users,cashierprinterid FROM %s where %s = %s order by %s";
        sSql = String.format(sSql, PosUserTable, coluserid, userid, coluserid);
        Cursor cursor = db.rawQuery(sSql, null);
        PosUser Userobj = new PosUser();
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {

                    Userobj.setUserId(Integer.valueOf(cursor.getString(0)));
                    Userobj.setShort(cursor.getString(1));
                    Userobj.setName(cursor.getString(2));
                    Userobj.setPassword(cursor.getString(3));
                    Userobj.setAlluser(Boolean.parseBoolean(cursor.getString(4).equals("1") ? "True" : "False"));
                    Userobj.set_cashierprinterid(Integer.valueOf(cursor.getString(5)));
                } while (cursor.moveToNext());
            } else {
                Userobj.setAlluser(false);

            }
            // return contact list
            return Userobj;
        } finally {
            cursor.close();
        }

    }

    // added by WaiWL on 02/06/2015
    List<ItemsObj> getSpecialmenuitemlistbycategoryid(String menuid) {
        String selectQuery = "";
        Integer tmpmenuid = Integer.valueOf(menuid);

        SQLiteDatabase db = this.getWritableDatabase();
        List<ItemsObj> list = new ArrayList<ItemsObj>();
        /*
         * String selectQuery = "SELECT  "+ ItemPhototable+"."+colphoto + "," +
         * ItemTable + ".*" + " FROM " + ItemTable + " inner join " +
         * ItemPhototable + " on " + ItemTable
         * +"."+colusrcode+" = "+ItemPhototable+"."+ colusrcode+ " where " +
         * colcategoryid +" = " + categoryid + " order by "+ colusrcode;
         */

        if (tmpmenuid == 0) {
            if (getAppSetting("ItemOrderStyle").equals("0")) {
                selectQuery = "SELECT  " + ItemTable + ".*" + " FROM "
                        + Specialmenu_code_Table + " inner join " + ItemTable
                        + " on " + Specialmenu_code_Table + "." + colSUsr_code
                        + " = " + ItemTable + "." + colusrcode + " where "
                        + ItemTable + "." + coltmpInactive + "='false' "
                        + " order by " + colsortid + "," + colusrcode;// added by WaiWL on 06/08/2015 --add checking inactive 0
            } else {
                selectQuery = "SELECT  " + ItemTable + ".*" + " FROM "
                        + Specialmenu_code_Table + " inner join " + ItemTable
                        + " on " + Specialmenu_code_Table + "." + colSUsr_code
                        + " = " + ItemTable + "." + colusrcode + " where "
                        + ItemTable + "." + coltmpInactive + "='false' "
                        + " order by " + coldescription + " COLLATE NOCASE ASC";
            }

        } else {
            selectQuery = "SELECT  " + ItemTable + ".*" + " FROM "
                    + Specialmenu_code_Table + " inner join " + ItemTable
                    + " on " + Specialmenu_code_Table + "." + colSUsr_code
                    + " = " + ItemTable + "." + colusrcode + " where "
                    + colSMenuID + " = " + menuid + " and " + ItemTable + "."
                    + coltmpInactive + "='false' " +
                    " order by " + colsortid;
//					" order by " + colusrcode;// added by WaiWL on 06/08/2015 --add checking inactive 0
        }

        Cursor cursor = db.rawQuery(selectQuery, null);

        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    ItemsObj tablesobj = new ItemsObj();
                    tablesobj.setcode(cursor.getString(0));
                    tablesobj.setusr_code(cursor.getString(1));
                    tablesobj.setdescription(cursor.getString(2));
                    tablesobj.setsale_price(cursor.getString(3));
                    tablesobj.setsale_price1(cursor.getString(4));
                    tablesobj.setsale_price2(cursor.getString(5));
                    tablesobj.setsale_price3(cursor.getString(6));
                    tablesobj.setcaldiscount(cursor.getString(7));
                    tablesobj.setgtax(cursor.getString(8));
                    tablesobj.setstax(cursor.getString(9));
                    tablesobj.setcategoryid(cursor.getString(10));
                    tablesobj.setcategoryname(cursor.getString(11));
                    tablesobj.setclassid(cursor.getString(12));
                    tablesobj.setclassname(cursor.getString(13));
                    tablesobj.setitemcolorRGB(cursor.getString(23)); //Added by ArkarMoe on [20/12/2016]
                    tablesobj.setphoto(getItemPhotoByUserCode(tablesobj.getusr_code()));
                    tablesobj.setOrg_curr(cursor.getString(32));//added WHM [2020-05-27] currency
                    tablesobj.setItemrating(cursor.getString(33) == null ? "0" : cursor.getString(33));//added MPPA [04-02-2021]
                    tablesobj.setRatingcount(cursor.getString(34) == null ? "1" : cursor.getString(34));//added MPPA [04-02-2021]
                    // Adding tableobj to list
                    list.add(tablesobj);
                } while (cursor.moveToNext());
            }

            // return contact list
            return list;
        } finally {
            cursor.close();
        }
    }

    //added by EKK on 14-12-2019
    List<ItemsObj> getPromotionItem() {
        String selectQuery = "";
        //Integer tmpmenuid = Integer.valueOf(menuid);

        SQLiteDatabase db = this.getWritableDatabase();
        List<ItemsObj> list = new ArrayList<ItemsObj>();

        if (getAppSetting("ItemOrderStyle").equals("0")) {
            selectQuery = "SELECT  " + ItemTable + ".*" + " FROM "
                    + PromotionTable + " inner join " + ItemTable
                    + " on " + PromotionTable + "." + colSUsr_code
                    + " = " + ItemTable + "." + colusrcode + " where "
                    + ItemTable + "." + coltmpInactive + "='false' "
                    + " AND " + ItemTable + "." + colusrcode + " NOT IN (SELECT " + colsoldoutusr_code
                    + " FROM " + SoldOutTable + ")"
                    + " order by " + colsortid + "," + colusrcode;
        } else {
            selectQuery = "SELECT  " + ItemTable + ".*" + " FROM "
                    + PromotionTable + " inner join " + ItemTable
                    + " on " + PromotionTable + "." + colSUsr_code
                    + " = " + ItemTable + "." + colusrcode + " where "
                    + ItemTable + "." + coltmpInactive + "='false' "
                    + " AND " + ItemTable + "." + colusrcode + " NOT IN (SELECT " + colsoldoutusr_code
                    + " FROM " + SoldOutTable + ")"
                    + " order by " + coldescription + " COLLATE NOCASE ASC";
        }

        Cursor cursor = db.rawQuery(selectQuery, null);

        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    ItemsObj tablesobj = new ItemsObj();
                    tablesobj.setcode(cursor.getString(0));
                    tablesobj.setusr_code(cursor.getString(1));
                    tablesobj.setdescription(cursor.getString(2));
                    tablesobj.setsale_price(cursor.getString(3));
                    tablesobj.setsale_price1(cursor.getString(4));
                    tablesobj.setsale_price2(cursor.getString(5));
                    tablesobj.setsale_price3(cursor.getString(6));
                    tablesobj.setcaldiscount(cursor.getString(7));
                    tablesobj.setgtax(cursor.getString(8));
                    tablesobj.setstax(cursor.getString(9));
                    tablesobj.setcategoryid(cursor.getString(10));
                    tablesobj.setcategoryname(cursor.getString(11));
                    tablesobj.setclassid(cursor.getString(12));
                    tablesobj.setclassname(cursor.getString(13));
                    tablesobj.setitemcolorRGB(cursor.getString(23)); //Added by ArkarMoe on [20/12/2016]
                    tablesobj.setphoto(getItemPhotoByUserCode(tablesobj
                            .getusr_code()));
                    // Adding tableobj to list
                    list.add(tablesobj);
                } while (cursor.moveToNext());
            }

            // return contact list
            return list;
        } finally {
            cursor.close();
        }
    }

    // //

    List<ItemsObj> getItemslistbycategoryid(String categoryid, String str) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<ItemsObj> list = new ArrayList<ItemsObj>();
        /*
         * String selectQuery = "SELECT  "+ ItemPhototable+"."+colphoto + "," +
         * ItemTable + ".*" + " FROM " + ItemTable + " inner join " +
         * ItemPhototable + " on " + ItemTable
         * +"."+colusrcode+" = "+ItemPhototable+"."+ colusrcode+ " where " +
         * colcategoryid +" = " + categoryid + " order by "+ colusrcode;
         */

//		String selectQuery = "SELECT  " + ItemTable + ".*" + " FROM "
//				+ ItemTable + " where " + colcategoryid + " = " + categoryid
//				+ " and " + coltmpInactive + "='false' " + // added by WaiWL on
//															// 06/08/2015 --add
//				str +											// checking inactive
//															// 0
//				" order by " + colusrcode;
        String selectQuery = "";
        if (getAppSetting("ItemOrderStyle").equals("0")) {
            selectQuery = "SELECT  " + ItemTable + ".*" + " FROM "
                    + ItemTable + " where " + colcategoryid + " = " + categoryid
                    + " and " + coltmpInactive + "='false' " + str +
                    " order by " + colsortid + "," + colusrcode;
        } else {
            selectQuery = "SELECT  " + ItemTable + ".*" + " FROM "
                    + ItemTable + " where " + colcategoryid + " = " + categoryid
                    + " and " + coltmpInactive + "='false' " + str +
                    " order by " + coldescription + " COLLATE NOCASE ASC";
        }

        Cursor cursor = db.rawQuery(selectQuery, null);

        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    ItemsObj tablesobj = new ItemsObj();
                    tablesobj.setcode(cursor.getString(0));
                    tablesobj.setusr_code(cursor.getString(1));
                    tablesobj.setdescription(cursor.getString(2));
                    tablesobj.setsale_price(cursor.getString(3));
                    tablesobj.setsale_price1(cursor.getString(4));
                    tablesobj.setsale_price2(cursor.getString(5));
                    tablesobj.setsale_price3(cursor.getString(6));
                    tablesobj.setcaldiscount(cursor.getString(7));
                    tablesobj.setgtax(cursor.getString(8));
                    tablesobj.setstax(cursor.getString(9));
                    tablesobj.setcategoryid(cursor.getString(10));
                    tablesobj.setcategoryname(cursor.getString(11));
                    tablesobj.setclassid(cursor.getString(12));
                    tablesobj.setclassname(cursor.getString(13));
                    tablesobj.setcolorRGB(cursor.getString(16));
                    tablesobj.setitemcolorRGB(cursor.getString(23));
                    tablesobj.setphoto(getItemPhotoByUserCode(tablesobj
                            .getusr_code()));
                    tablesobj.setdescription2(cursor.getString(28));
                    tablesobj.setdescription3(cursor.getString(29));
                    tablesobj.setOrg_curr(cursor.getString(32));//added WHM [2020-05-27] currency
                    tablesobj.setItemrating(cursor.getString(33) == null ? "0" : cursor.getString(33));//added MPPA [28-01-2021]
                    tablesobj.setRatingcount(cursor.getString(34) == null ? "1" : cursor.getString(34));//added MPPA [02-02-2021]
                    list.add(tablesobj);
                } while (cursor.moveToNext());
            }

            // return contact list
            return list;
        } finally {
            cursor.close();
        }

    }

    List<ItemsObj> getAllItemList() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<ItemsObj> list = new ArrayList<ItemsObj>();

        String selectQuery = "SELECT  " + ItemTable + ".*" + " FROM "
                + ItemTable + " where " + coltmpInactive + "='false' "
                + " order by " + colusrcode;


        Cursor cursor = db.rawQuery(selectQuery, null);

        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    ItemsObj tablesobj = new ItemsObj();
                    tablesobj.setcode(cursor.getString(0));
                    tablesobj.setusr_code(cursor.getString(1));
                    tablesobj.setdescription(cursor.getString(2));
                    tablesobj.setsale_price(cursor.getString(3));
                    tablesobj.setsale_price1(cursor.getString(4));
                    tablesobj.setsale_price2(cursor.getString(5));
                    tablesobj.setsale_price3(cursor.getString(6));
                    tablesobj.setcaldiscount(cursor.getString(7));
                    tablesobj.setgtax(cursor.getString(8));
                    tablesobj.setstax(cursor.getString(9));
                    tablesobj.setcategoryid(cursor.getString(10));
                    tablesobj.setcategoryname(cursor.getString(11));
                    tablesobj.setclassid(cursor.getString(12));
                    tablesobj.setclassname(cursor.getString(13));
                    tablesobj.setcolorRGB(cursor.getString(16));
                    tablesobj.setitemcolorRGB(cursor.getString(23));
                    tablesobj.setphoto(getItemPhotoByUserCode(tablesobj
                            .getusr_code()));
                    tablesobj.setdescription2(cursor.getString(28));
                    tablesobj.setdescription3(cursor.getString(29));
                    list.add(tablesobj);
                } while (cursor.moveToNext());
            }

            // return contact list
            return list;
        } finally {
            cursor.close();
        }

    }


    Bitmap getItemPhotoByUserCode(String usrcode) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT " + colphoto + " FROM " + ItemPhototable
                + " where " + colusrcode + " = '" + usrcode + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    byte[] bytearray = cursor.getBlob(0);
                    Bitmap bm = BitmapFactory.decodeByteArray(bytearray, 0,
                            bytearray.length);
                    return bm;
                } while (cursor.moveToNext());
            }

            // return contact list
            return null;
        } finally {
            cursor.close();
        }

    }

    List<ItemsObj> getItemslistbyitemid(String code) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<ItemsObj> list = new ArrayList<ItemsObj>();
        String selectQuery = "SELECT  * FROM " + ItemTable + " where "
                + colcode + " = " + code + " and " + coltmpInactive
                + "='false' " + // added by WaiWL on 06/08/2015 --add checking
                // inactive 0
                " order by " + coldescription;
        Cursor cursor = db.rawQuery(selectQuery, null);

        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    ItemsObj tablesobj = new ItemsObj();
                    tablesobj.setcode(cursor.getString(0));
                    tablesobj.setusr_code(cursor.getString(1));
                    tablesobj.setdescription(cursor.getString(2));
                    tablesobj.setsale_price(cursor.getString(3));
                    tablesobj.setsale_price1(cursor.getString(4));
                    tablesobj.setsale_price2(cursor.getString(5));
                    tablesobj.setsale_price3(cursor.getString(6));
                    tablesobj.setcaldiscount(cursor.getString(7));
                    tablesobj.setgtax(cursor.getString(8));
                    tablesobj.setstax(cursor.getString(9));
                    tablesobj.setcategoryid(cursor.getString(10));
                    tablesobj.setcategoryname(cursor.getString(11));
                    tablesobj.setclassid(cursor.getString(12));
                    tablesobj.setclassname(cursor.getString(13));
                    tablesobj.setremark(cursor.getString(14));
                    tablesobj.setissetmenu(cursor.getString(21));
                    tablesobj.setdescription2(cursor.getString(28));
                    tablesobj.setdescription3(cursor.getString(29));
                    // Adding tableobj to list
                    list.add(tablesobj);
                } while (cursor.moveToNext());
            }

            // return contact list
            return list;

        } finally {
            cursor.close();
        }

    }

    List<ItemClassObj> getItemClasslist(String str) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<ItemClassObj> list = new ArrayList<ItemClassObj>();
        String selectQuery = "SELECT DISTINCT " + colclassid + ","
                + colclassname + " FROM " + ItemTable + " where "
                + coltmpInactive + "='false'" + // added by WaiWL on 06/08/2015
                str +                    // --add checking inactive 0
                " order by " + colclasssortcode;
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    ItemClassObj itemclassobj = new ItemClassObj();
                    itemclassobj.setclassid(cursor.getString(0));
                    itemclassobj.setclassName(cursor.getString(1));
                    // Adding tableobj to list
                    list.add(itemclassobj);
                } while (cursor.moveToNext());
            }
            // return contact list
            return list;
        } finally {
            cursor.close();
        }

    }


    // added by WaiWL on 03/06/2015
    List<ItemClassObj> getSpecialmenuItemClasslist() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<ItemClassObj> list = new ArrayList<ItemClassObj>();
        String selectQuery = "SELECT 0 classid,'Special Menu' classname ";
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    ItemClassObj itemclassobj = new ItemClassObj();
                    itemclassobj.setclassid(cursor.getString(0));
                    itemclassobj.setclassName(cursor.getString(1));
                    // Adding tableobj to list
                    list.add(itemclassobj);
                } while (cursor.moveToNext());
            }
            // return contact list
            return list;
        } finally {
            cursor.close();
        }

    }

    //added by EKK on 14-12-2019
    // added by WaiWL on 03/06/2015
    List<ItemClassObj> getPromotionItemClasslist() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<ItemClassObj> list = new ArrayList<ItemClassObj>();
        String selectQuery = "SELECT -1 classid,'Today Promotion' classname ";
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    ItemClassObj itemclassobj = new ItemClassObj();
                    itemclassobj.setclassid(cursor.getString(0));
                    itemclassobj.setclassName(cursor.getString(1));
                    // Adding tableobj to list
                    list.add(itemclassobj);
                } while (cursor.moveToNext());
            }
            // return contact list
            return list;
        } finally {
            cursor.close();
        }

    }

    // //////////////
    List<String> getusrcodelist() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<String> list = new ArrayList<String>();
        String selectQuery = "SELECT DISTINCT " + colusrcode + " FROM "
                + ItemTable + " where " + coltmpInactive + "='false' " + // added
                // by
                // WaiWL
                // on
                // 06/08/2015
                // --add
                // checking
                // inactive
                // 0
                " order by " + colusrcode;
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    list.add(cursor.getString(0));
                } while (cursor.moveToNext());
            }
            // return contact list
            return list;
        } finally {
            cursor.close();
        }

    }

    List<ItemCategoryObj> getItemCategorylistByClassid(String classid, String str) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<ItemCategoryObj> list = new ArrayList<ItemCategoryObj>();

        String selectQuery = "SELECT DISTINCT " + colcategoryid + ","
                + colcategoryname + "," + colcolorRGB + " FROM " + ItemTable
                + " where " + colclassid + " = " + classid.trim() + " and "
                + coltmpInactive + "='false' " + // added by WaiWL on 06/08/2015
                str +                        // --add checking inactive 0
                GlobalClass.strmenucategory_grouptype + //added WHM [2020-09-30] YGN2-200983
                " order by " + colcategorysortcode + "," + colcategoryname;

        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    ItemCategoryObj itemclassobj = new ItemCategoryObj();
                    itemclassobj.setcategoryid(cursor.getString(0));
                    itemclassobj.setcategoryname(cursor.getString(1));
                    itemclassobj.setcolorRGB(cursor.getString(2));
                    // Adding tableobj to list
                    list.add(itemclassobj);
                } while (cursor.moveToNext());
            }
            // return contact list
            return list;
        } finally {
            cursor.close();
        }

    }

    //added by EKK on 14-12-2019
    List<Promotion> getPromotionList() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<Promotion> list = new ArrayList<Promotion>();
        String selectQuery = "Select Distinct code,usr_code,description,promotion from " + PromotionTable;

        Cursor cursor = db.rawQuery(selectQuery, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    Promotion p = new Promotion();
                    p.setCode(cursor.getString(0));
                    p.setUsr_code(cursor.getString(1));
                    p.setDescription(cursor.getString(2));
                    p.setPromotion(cursor.getString(3));

                    list.add(p);
                } while (cursor.moveToNext());


            }
            return list;
        } finally {
            cursor.close();

        }
    }

    // added by WaiWL on 27/05/2015
    List<SpecialMenuObj> getSpecialMenuItemCategorylist() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<SpecialMenuObj> list = new ArrayList<SpecialMenuObj>();
        String selectQuery = "SELECT DISTINCT " + colMenuID + " category,"
                + colMenuName + " categoryname, null" + " colorRGB ,1 "
                + " categorysortcode FROM " + Specialmenu_Table + " order by "
                + colMenuID;
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    SpecialMenuObj specialmenuobj = new SpecialMenuObj();
                    specialmenuobj.setMenuID(cursor.getInt(0));
                    specialmenuobj.setMenuName(cursor.getString(1));

                    // Adding tableobj to list
                    list.add(specialmenuobj);
                } while (cursor.moveToNext());
            }
            // return contact list
            return list;
        } finally {
            cursor.close();
        }
    }

    // /////////


    List<ItemCategoryObj> getItemCategorylist(String str) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<ItemCategoryObj> list = new ArrayList<ItemCategoryObj>();
        String selectQuery = "SELECT DISTINCT " + colcategoryid + ","
                + colcategoryname + "," + colcolorRGB + ","
                + colcategorysortcode + " FROM " + ItemTable + " where  "
                //+ colmodifiedgroupid + "='false' "
                + coltmpInactive + "='false' " + // added by WaiWL on 06/08/2015
                str +                            // --add checking inactive 0
                GlobalClass.strmenucategory_grouptype + //added WHM [2020-09-30] YGN2-200983
                " order by " + colclasssortcode + "," + colcategorysortcode;
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    ItemCategoryObj itemclassobj = new ItemCategoryObj();
                    itemclassobj.setcategoryid(cursor.getString(0));
                    itemclassobj.setcategoryname(cursor.getString(1));
                    itemclassobj.setcolorRGB(cursor.getString(2));
                    itemclassobj.setcategorysortcode(cursor.getString(3));
                    // Adding tableobj to list
                    list.add(itemclassobj);
                } while (cursor.moveToNext());
            }
            // return contact list
            return list;
        } finally {
            cursor.close();
        }

    }

    List<ItemCategoryObj> getItemCategorylistbyMealType(String str) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<ItemCategoryObj> list = new ArrayList<ItemCategoryObj>();
        String selectQuery = "SELECT DISTINCT " + colcategoryid + ","
                + colcategoryname + "," + colcolorRGB + ","
                + colcategorysortcode + " FROM " + ItemTable + " where "
                + coltmpInactive + "='false'  " + // added by WaiWL on 06/08/2015
                str +                            // --add checking inactive 0
                GlobalClass.strmenucategory_grouptype + //added WHM [2020-09-30] YGN2-200983
                " order by " + colclasssortcode + "," + colcategorysortcode;
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    ItemCategoryObj itemclassobj = new ItemCategoryObj();
                    itemclassobj.setcategoryid(cursor.getString(0));
                    itemclassobj.setcategoryname(cursor.getString(1));
                    itemclassobj.setcolorRGB(cursor.getString(2));
                    itemclassobj.setcategorysortcode(cursor.getString(3));
                    // Adding tableobj to list
                    list.add(itemclassobj);
                } while (cursor.moveToNext());
            }
            // return contact list
            return list;
        } finally {
            cursor.close();
        }

    }

    public void LoadArea() {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(AreaTable, null, null);
        for (int i = 1; i <= 2; i++) {
            ContentValues cv = new ContentValues();
            cv.put(colArea_ID, i);
            cv.put(colArea_Name, "ေနရာ " + i);
            cv.put(colDescription, "Area " + i);
            cv.put(colArea_Code, "F" + i);
            cv.put(colRemark, "Area " + i);
            db.insert(AreaTable, null, cv);
        }

        // String sSql = "DELETE FROM " + AreaTable + ";";
        // sSql += "INSERT INTO " + AreaTable +"(" + colArea_ID + "," +
        // colArea_Name + "," + colDescription + "," + colArea_Code + "," +
        // colRemark + ")"+
        // "SELECT 1,'Floor 1','Floor 1','F1','Floor 1' ";
        // "SELECT 2,'Floor 2','Floor 2','F2','Floor 2' UNION " +
        // "SELECT 3,'Floor 3','Floor 3','F3','Floor 3' UNION " +
        // "SELECT 4,'Floor 4','Floor 4','F4','Floor 4' UNION " +
        // "SELECT 5,'Floor 5','Floor 5','F5','Floor 5' UNION " +
        // "SELECT 6,'Floor 6','Floor 6','F6','Floor 6' UNION " +
        // "SELECT 7,'Floor 7','Floor 7','F7','Floor 7' UNION " +
        // "SELECT 8,'Floor 8','Floor 8','F8','Floor 8' UNION " +
        // "SELECT 9,'Floor 9','Floor 9','F9','Floor 9' UNION " +
        // "SELECT 10,'Floor 10','Floor 10','F10','Floor 10'";

        // db.execSQL(sSql);
        // db.close();
    }

    public void LoadTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Table_Name, null, null);
        int k = 1;
        for (int j = 1; j <= 2; j++) // Area
        {
            for (int i = 1; i <= 48; i++) // Tables
            {
                ContentValues cv = new ContentValues();
                cv.put(colTableArea_ID, j);
                cv.put(colTable_Name_ID, k++);
                cv.put(colTable_Name, "စားပြဲ " + (j * 10) + i);
                cv.put(colTableDescription, "Table " + j + i);
                cv.put(colTable_Code, "T" + i);
                cv.put(colTableRemark, "Table " + i);
                db.insert(Table_Name, null, cv);
            }
        }

    }

    public void LoadSystemSetting(String Datalink) {
        url = Datalink;
        JSONArray itemjsonarray = Json_class.getJson(url
                + "/Data/GetSystemSetting");
        //Added by KLM for UniZawgyi Auto Detect 23022022
        try {
            if (!LoginActivity.isUnicode) {
                itemjsonarray = new JSONArray(Rabbit.uni2zg(itemjsonarray.toString()));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (itemjsonarray.length() > 0) {
            for (int i = 0; i < itemjsonarray.length(); i++) {
                JSONObject c;
                try {
                    c = itemjsonarray.getJSONObject(i);
                    SQLiteDatabase db = this.getWritableDatabase();
                    ContentValues cv = new ContentValues();
                    cv.put(coltitle, c.getString(coltitle));
                    cv.put(coluse_unit, c.getString(coluse_unit));
                    cv.put(coluse_hotel, c.getString(coluse_hotel)); //Added by ArkarMoe on [19/12/2016]
                    cv.put(coluse_customer_analysis, c.getString(coluse_customer_analysis));
                    cv.put(colhideitemcancel, c.getString(colhideitemcancel));
                    cv.put(colsalesmen_commission, c.getString("Salesmen_commission"));
                    cv.put(colbillprintfromtablet, c.getString("BillPrintFromTablet"));
                    cv.put(colusemonitorinterface, c.getString("usemonitorinterface"));
                    cv.put(coluseiscontinuoussave, c.getString("isContinuousSave"));
                    cv.put(colmultilanguageprint, c.getString("multilanguageprint"));
                    cv.put(colusefire, c.getString("use_fire"));
                    cv.put(colqty_decimal_places, c.getString("qty_decimal_places"));
                    cv.put(colprice_decimal_places, c.getString("price_decimal_places"));
                    cv.put(colspecial_password, c.getString("special_password"));//added WHM [2020-08-18];

                    db.update(serviceaddressTable, cv, null, null);

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        }

    }

    public List<Area> getArealist() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<Area> list = new ArrayList<Area>();
        String sSql = " SELECT Area_ID,Area_Name,Description,Area_Code,Remark " +
                " FROM %s " +
                " order by %s ";
        Cursor cursor = db.rawQuery(
                String.format(sSql, AreaTable, colArea_Code), null);

        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Area Areaobj = new Area();
                    Areaobj.setArea_ID(Integer.valueOf(cursor.getString(0)));
                    Areaobj.setArea_Name(cursor.getString(1));
                    Areaobj.setDescription(cursor.getString(2));
                    Areaobj.setArea_Code(cursor.getString(3));
                    Areaobj.setRemark(cursor.getString(4));
                    // Adding tableobj to list
                    list.add(Areaobj);
                } while (cursor.moveToNext());
            }
            // return contact list
            return list;
        } finally {
            cursor.close();
        }

    }

    //added WHM [2020-11-11] userarea
    public List<Area> getArealist_TableScreen() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<Area> list = new ArrayList<Area>();
        String sSql = " SELECT Area_ID,Area_Name,Description,Area_Code,Remark " +
                " FROM %s " +
                GlobalClass.strgetarea_groupbyuser +//added WHM [2020-11-11] userarea
                " order by %s ";
        Cursor cursor = db.rawQuery(
                String.format(sSql, AreaTable, colArea_Code), null);

        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Area Areaobj = new Area();
                    Areaobj.setArea_ID(Integer.valueOf(cursor.getString(0)));
                    Areaobj.setArea_Name(cursor.getString(1));
                    Areaobj.setDescription(cursor.getString(2));
                    Areaobj.setArea_Code(cursor.getString(3));
                    Areaobj.setRemark(cursor.getString(4));
                    // Adding tableobj to list
                    list.add(Areaobj);
                } while (cursor.moveToNext());
            }
            // return contact list
            return list;
        } finally {
            cursor.close();
        }

    }

    public List<CustomerObj> getCustomer() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<CustomerObj> list = new ArrayList<CustomerObj>();
        String sSql = "SELECT %s,%s,%s FROM %s order by %s";
        Cursor cursor = db.rawQuery(String.format(sSql, colcustomerid,
                colcustomercode, colcustomername, CustomerTable,
                colcustomername), null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    CustomerObj customerobj = new CustomerObj();
                    customerobj.setcustomerid(Integer.valueOf(cursor
                            .getString(0)));
                    customerobj.setcustomercode(cursor.getString(1));
                    customerobj.setcustomername(cursor.getString(2));

                    // Adding tableobj to list
                    list.add(customerobj);
                } while (cursor.moveToNext());
            }
            // return contact list

            return list;
        } finally {
            cursor.close();
        }

    }

    public List<CustomerObj> getCustomerByCustomerID(String CustomerID) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<CustomerObj> list = new ArrayList<CustomerObj>();
        String sSql = "SELECT %s,%s,%s FROM %s where %s = %s order by %s";
        Cursor cursor = db.rawQuery(String.format(sSql, colcustomerid,
                colcustomercode, colcustomername, CustomerTable, colcustomerid,
                CustomerID, colcustomername), null);

        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    CustomerObj customerobj = new CustomerObj();
                    customerobj.setcustomerid(Integer.valueOf(cursor
                            .getString(0)));
                    customerobj.setcustomercode(cursor.getString(1));
                    customerobj.setcustomername(cursor.getString(2));
                    // Adding tableobj to list
                    list.add(customerobj);
                } while (cursor.moveToNext());
            }
            // return contact list
            return list;
        } finally {
            cursor.close();
        }

    }

    //Added by ArkarMoe on [15/12/2016]
    public List<Rooms> getRoomCodebyRoomID(String RoomID) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<Rooms> list = new ArrayList<Rooms>();
        String sSql = "SELECT %s,%s,%s FROM %s where %s = %s order by %s";
        Cursor cursor = db.rawQuery(String.format(sSql, colRef_TranID,
                colRoom_ID, colRoom_Code, Rooms_table, colRoom_ID,
                RoomID, colRoom_Code), null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Rooms roomsobj = new Rooms();
                    roomsobj.setRef_TranID(Integer.valueOf(cursor.getString(0)));
                    roomsobj.setRoom_ID(Integer.valueOf(cursor.getString(1)));
                    roomsobj.setRoom_Code(cursor.getString(2));

                    list.add(roomsobj);
                } while (cursor.moveToNext());
            }
            // return contact list
            return list;
        } finally {
            cursor.close();
        }

    }
    //////

    public String getCustomerIDByCustomerCode(String customercode) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sSql = "SELECT %s FROM %s where %s = '%s' ";
        Cursor cursor = db.rawQuery(String.format(sSql, colcustomerid,
                CustomerTable, colcustomercode, customercode), null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {

                    return cursor.getString(0);

                } while (cursor.moveToNext());
            }
            // return contact list
            return "";
        } finally {
            cursor.close();
        }

    }

    public boolean getknockcode() {
        SQLiteDatabase db = this.getWritableDatabase();
        String sSql = "SELECT %s FROM %s where %s = '%s' and %s='%s' ";
        Cursor cursor = db.rawQuery(String.format(sSql, colSetting_Value,
                AppSetting_table, colSetting_Name, "use_knockcode_login",
                colSetting_Value, "Y"), null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    return true;
                } while (cursor.moveToNext());
            }
            // return contact list
            return false;
        } finally {
            cursor.close();
        }

    }

    public String getCustomerNameByCustomerID(String customerid) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sSql = "SELECT %s FROM %s where %s = '%s' ";
        Cursor cursor = db.rawQuery(String.format(sSql, colcustomername,
                CustomerTable, colcustomerid, customerid), null);

        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    return cursor.getString(0);
                } while (cursor.moveToNext());
            }
            // return contact list
            return "";
        } finally {
            cursor.close();
        }

    }

    public List<Table_Name> getTableListByArea(Integer TableId) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<Table_Name> list = new ArrayList<Table_Name>();
        String sSql = "SELECT Area_ID,Table_Name_ID,Table_Name,Description,Table_Code,Remark FROM %s Where Area_ID = %s  order by %s, %s "; ////cast (%s as int)";

        sSql = String.format(sSql, Table_Name, String.valueOf(TableId), colTableSortcode, colTable_Name_ID);
        // Cursor cursor = db.rawQuery(sSql, new
        // String[]{Table_Name,String.valueOf(TableId),colTableArea_ID + "," +
        // colTable_Name_ID});
        Cursor cursor = db.rawQuery(sSql, null);

        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Table_Name TableObj = new Table_Name();
                    TableObj.setTableArea_ID(Integer.valueOf(cursor
                            .getString(0)));
                    TableObj.setTable_Name_ID(Integer.valueOf(cursor
                            .getString(1)));
                    TableObj.setTable_Name(cursor.getString(2));
                    TableObj.setTableDescription(cursor.getString(3));
                    TableObj.setTable_Code(cursor.getString(4));
                    TableObj.setTableRemark(cursor.getString(5));
                    // Adding tableobj to list
                    list.add(TableObj);
                } while (cursor.moveToNext());
            }
            // return contact list
            return list;
        } finally {
            cursor.close();
        }

    }

    public List<Table_Name> getTransferableTableListByArea(Integer AreaID) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<Table_Name> list = new ArrayList<Table_Name>();
        String sSql = "SELECT Area_ID,Table_Name_ID,Table_Name,Description,Table_Code,Remark FROM %s Where Area_ID = %s and Table_Name_ID Not In (select Table_Name_ID from Active_Table) and Table_Name_ID Not In (select table_name_id from Busy_Table) order by %s";
        sSql = String.format(sSql, Table_Name, String.valueOf(AreaID),
                colTableArea_ID + "," + colTable_Code);
        Cursor cursor = db.rawQuery(sSql, null);

        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Table_Name TableObj = new Table_Name();
                    TableObj.setTableArea_ID(Integer.valueOf(cursor
                            .getString(0)));
                    TableObj.setTable_Name_ID(Integer.valueOf(cursor
                            .getString(1)));
                    TableObj.setTable_Name(cursor.getString(2));
                    TableObj.setTableDescription(cursor.getString(3));
                    TableObj.setTable_Code(cursor.getString(4));
                    TableObj.setTableRemark(cursor.getString(5));
                    // Adding tableobj to list
                    list.add(TableObj);
                } while (cursor.moveToNext());
            }
            // return contact list
            return list;
        } finally {
            cursor.close();
        }

    }

    //added by ZYP [03-03-2020] for item transfer
    public List<Table_Name> getItemTransferableTable(Integer AreaID) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<Table_Name> list = new ArrayList<Table_Name>();
        String sSql = "SELECT Area_ID,Table_Name_ID,Table_Name,Description,Table_Code,Remark FROM %s Where Area_ID = %s and Table_Name_ID In (select Table_Name_ID from Active_Table) and Table_Name_ID Not In (select table_name_id from Busy_Table) and Table_Name_ID <> " + TableScreenActivity.id + " order by %s";
        sSql = String.format(sSql, Table_Name, String.valueOf(AreaID),
                colTableArea_ID + "," + colTable_Code);
        Cursor cursor = db.rawQuery(sSql, null);

        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Table_Name TableObj = new Table_Name();
                    TableObj.setTableArea_ID(Integer.valueOf(cursor
                            .getString(0)));
                    TableObj.setTable_Name_ID(Integer.valueOf(cursor
                            .getString(1)));
                    TableObj.setTable_Name(cursor.getString(2));
                    TableObj.setTableDescription(cursor.getString(3));
                    TableObj.setTable_Code(cursor.getString(4));
                    TableObj.setTableRemark(cursor.getString(5));
                    // Adding tableobj to list
                    list.add(TableObj);
                } while (cursor.moveToNext());
            }
            // return contact list
            return list;
        } finally {
            cursor.close();
        }

    }

    public List<Table_Name> getmergeTableListByArea(Integer AreaID, Integer tranid) //Added by ArkarMoe on [20/12/2016]
    {
        SQLiteDatabase db = this.getWritableDatabase();
        List<Table_Name> list = new ArrayList<Table_Name>();
        String sSql = "SELECT Area_ID,Table_Name_ID,Table_Name,Description,Table_Code,Remark FROM %s Where Area_ID = %s and Table_Name_ID In (select Table_Name_ID from Active_Table where tranid != %s)  order by %s"; //modified by EKK on 28-01-2020
        sSql = String.format(sSql, Table_Name, String.valueOf(AreaID), tranid, colTableArea_ID + "," + colTable_Code);
        Cursor cursor = db.rawQuery(sSql, null);

        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Table_Name TableObj = new Table_Name();
                    TableObj.setTableArea_ID(Integer.valueOf(cursor.getString(0)));
                    TableObj.setTable_Name_ID(Integer.valueOf(cursor.getString(1)));
                    TableObj.setTable_Name(cursor.getString(2));
                    TableObj.setTableDescription(cursor.getString(3));
                    TableObj.setTable_Code(cursor.getString(4));
                    TableObj.setTableRemark(cursor.getString(5));
                    //Adding tableobj to list
                    list.add(TableObj);
                } while (cursor.moveToNext());
            }
            // return contact list
            return list;
        } finally {
            cursor.close();
        }

    }

    public String getTableNameByTableID(Integer TableId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sSql = "SELECT %s FROM %s Where %s = %s order by %s";
        sSql = String.format(sSql, colTable_Name, Table_Name, colTable_Name_ID,
                TableId, colTable_Name);
        // Cursor cursor = db.rawQuery(sSql, new
        // String[]{Table_Name,String.valueOf(TableId),colTableArea_ID + "," +
        // colTable_Name_ID});
        Cursor cursor = db.rawQuery(sSql, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    return cursor.getString(0);
                } while (cursor.moveToNext());
            }
            return "";
        } finally {
            cursor.close();
        }

    }

    public List<Dictionary> getMyanmarByEnglish(String English) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<Dictionary> dictionarylist = new ArrayList<Dictionary>();
        String sSql = "SELECT %s FROM %s where %s = '%s'";


        sSql = String.format(sSql, colMyanmar, DictionaryTable, colEnglish,
                English.toLowerCase());
        Cursor cursor = db.rawQuery(sSql, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Dictionary dicobj = new Dictionary();
                    dicobj.setLanguage2(cursor.getString(0));
                    // Adding dictionaryobj to list
                    dictionarylist.add(dicobj);
                } while (cursor.moveToNext());
            }
            // return contact list
            return dictionarylist;
        } finally {
            cursor.close();
        }

    }

    //added by EKK on 10/07/2019
    public List<Dictionary> getChineseByEnglish(String English) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<Dictionary> dictionarylist = new ArrayList<Dictionary>();
        String sSql = "SELECT %s FROM %s where %s = '%s'";

        //added by EKK
        sSql = String.format(sSql, colChinese, DictionaryTable, colEnglish,
                English.toLowerCase());
        Cursor cursor = db.rawQuery(sSql, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Dictionary dicobj = new Dictionary();
                    dicobj.setLanguage3(cursor.getString(0));
                    // Adding dictionaryobj to list
                    dictionarylist.add(dicobj);
                } while (cursor.moveToNext());
            }
            // return contact list
            return dictionarylist;
        } finally {
            cursor.close();
        }

    }

    public String getLastLoadingTimeLog() {
        SQLiteDatabase db = this.getWritableDatabase();
        String sSql = "SELECT %s FROM %s ";
        sSql = String.format(sSql, coltimelog, serviceaddressTable);
        Cursor cursor = db.rawQuery(sSql, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    return cursor.getString(0) == null ? "" : cursor
                            .getString(0);
                } while (cursor.moveToNext());
            }
            // return contact list
            return "";
        } finally {
            cursor.close();
        }

    }

    //Edited by ArkarMoe on [15/12/2016]
    public List<SaleObj> getSaledataByTableID(int tranid) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<SaleObj> list = new ArrayList<SaleObj>();
        String sSql = "SELECT %s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,IFNULL(%s,0),%s taxfree FROM %s where %s = %s order by %s";
        sSql = String.format(sSql, colsaleid, colinvoiceno, colTable_Name_ID,
                colamount, colsaledate, colroomid, colcustomerid, colcustcount, colRef_no,
                coluserid, colDelivery_type, colsubmitflag, colTaxfree, colMemberCardID,
                SaleTable, colsaleid, tranid, colsaleid);//modified WHM [2020-05-08]

        Cursor cursor = db.rawQuery(sSql, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    SaleObj saleobj = new SaleObj();
                    saleobj.setsaleid(cursor.getString(0));
                    saleobj.setinvoiceno(cursor.getString(1));
                    saleobj.settablenameid(cursor.getString(2));
                    saleobj.setamount(cursor.getString(3));
                    saleobj.setdate(cursor.getString(4));
                    saleobj.setroomid(cursor.getString(5));
                    saleobj.setcustomerid(cursor.getString(6));
                    saleobj.setcustcount(cursor.getString(7));
                    saleobj.setRef_no(cursor.getString(8));
                    saleobj.setuserid(Integer.parseInt(cursor.getString(9)));
                    saleobj.setDelivery_type(Integer.parseInt(cursor.getString(10)));//added WHM [2020-05-08]
                    saleobj.setsubmitflag(Boolean.parseBoolean(cursor.getString(11)));
                    saleobj.setTaxfree(cursor.getInt(12) == 1);
                    saleobj.setMembercard(cursor.getString(13));
                    // Adding tableobj to list
                    list.add(saleobj);
                } while (cursor.moveToNext());
            }
            // return contact list
            return list;
        } finally {
            cursor.close();
        }

    }

    public SaleObj getSaledataBySaleID(int SaleID) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<SaleObj> list = new ArrayList<SaleObj>();
        String sSql = "SELECT %s,%s,%s,%s,%s,%s,%s,%s,%s FROM %s where %s = %s order by %s";
        sSql = String.format(sSql, colsaleid, colinvoiceno, colTable_Name_ID,
                colamount, colsaledate, colcustomerid, colRef_no, colDelivery_type, colMemberCardID,
                SaleTable, colsaleid, SaleID, colsaleid);//modified WHM [2020-05-14] delivery type

        Cursor cursor = db.rawQuery(sSql, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    SaleObj saleobj = new SaleObj();
                    saleobj.setsaleid(cursor.getString(0));
                    saleobj.setinvoiceno(cursor.getString(1));
                    saleobj.settablenameid(cursor.getString(2));
                    saleobj.setamount(cursor.getString(3));
                    saleobj.setdate(cursor.getString(4));
                    saleobj.setcustomerid(cursor.getString(5));
                    saleobj.setRef_no(cursor.getString(6));
                    saleobj.setDelivery_type(Integer.parseInt(cursor.getString(7)));
                    saleobj.setMembercard(cursor.getString(8));
                    // Adding tableobj to list
                    list.add(saleobj);
                } while (cursor.moveToNext());
            }
            // return contact list
            return list.get(0);

        } finally {
            cursor.close();
        }

    }

    public List<SaleItemObj> getSaleItemdataBySaleID(int SaleID) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<SaleItemObj> list = new ArrayList<SaleItemObj>();
        String sSql = "SELECT %s,%s,%s,%s,%s,%s,%s,%s,%s,%s FROM %s where %s = %s and (%s = 'null' or %s = 0) order by %s"; // added by WaiWL on 09/07/2015add or condition for modifierrowsr
        sSql = String.format(sSql, colsrno, colcode, colprice, colqty,
                colamount, colremark, colmodifiedrowsr, colitemcancel,
                colunittype, colsr, SaleItemTable, colsaleid, SaleID,
                colmodifiedrowsr, colmodifiedrowsr, colsrno);

        Cursor cursor = db.rawQuery(sSql, null);

        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    SaleItemObj saleobj = new SaleItemObj();
                    saleobj.setsrno(cursor.getString(0));
                    saleobj.setcode(cursor.getString(1));
                    saleobj.setprice(cursor.getString(2));
                    saleobj.setqty(cursor.getString(3));
                    saleobj.setamount(cursor.getString(4));
                    saleobj.setremark(cursor.getString(5));
                    saleobj.setmodifiedrowsr(cursor.getString(6));
                    saleobj.setcancelflag(Boolean.parseBoolean(cursor
                            .getString(7)));
                    saleobj.setunittype(cursor.getString(8));
                    saleobj.setsr(cursor.getString(9));
                    list.add(saleobj);
                } while (cursor.moveToNext());
            }
            // return contact list
            return list;
        } finally {
            cursor.close();
        }

    }

    public List<Table_Name> getActiveTableByTableID(String TableID) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<Table_Name> list = new ArrayList<Table_Name>();
        String sSql = "SELECT %s,%s,%s,%s,%s FROM %s where %s = %s "; //modified WHM [2020-05-19] self order
        sSql = String.format(sSql, colTable_Name_ID, colArea_ID, coltranid,
                coluserid, colSelfOrderTable, ActiveTables, colTable_Name_ID, TableID);

        Cursor cursor = db.rawQuery(sSql, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Table_Name tablenameobj = new Table_Name();
                    tablenameobj.setTable_Name_ID(Integer.parseInt(cursor
                            .getString(0)));
                    tablenameobj.setTableArea_ID(Integer.parseInt(cursor
                            .getString(1)));
                    tablenameobj
                            .setTranid(Integer.parseInt(cursor.getString(2)));
                    tablenameobj
                            .setuserid(Integer.parseInt(cursor.getString(3)));
                    tablenameobj.setSelforder_table(cursor.getString(4));//modified WHM [2020-05-19] self order
                    list.add(tablenameobj);
                } while (cursor.moveToNext());
            }
            // return contact list
            return list;
        } finally {
            cursor.close();
        }

    }

    public List<Table_Name> getActiveVoucherByTableID(String TableID) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<Table_Name> list = new ArrayList<Table_Name>();
        String sSql = "SELECT %s,%s,%s,%s FROM %s where %s = %s ";
        sSql = String.format(sSql, colTable_Name_ID, colArea_ID, coltranid,
                coluserid, ActiveTables, colTable_Name_ID, TableID);

        Cursor cursor = db.rawQuery(sSql, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Table_Name tablenameobj = new Table_Name();
                    tablenameobj.setTable_Name_ID(Integer.parseInt(cursor
                            .getString(0)));
                    tablenameobj.setTableArea_ID(Integer.parseInt(cursor
                            .getString(1)));
                    tablenameobj
                            .setTranid(Integer.parseInt(cursor.getString(2)));
                    tablenameobj
                            .setuserid(Integer.parseInt(cursor.getString(3)));
                    list.add(tablenameobj);
                } while (cursor.moveToNext());
            }
            // return contact list
            return list;
        } finally {
            cursor.close();
        }

    }


    public Boolean check_isreserved(Integer TableID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sSql = "SELECT %s FROM %s where %s = %s ";
        sSql = String.format(sSql, colreserved, ActiveTables, colTable_Name_ID,
                TableID);

        Cursor cursor = db.rawQuery(sSql, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    if ((cursor.getString(0).equals("true"))) {
                        return true;
                    } else
                        return false;

                } while (cursor.moveToNext());
            }
            // return contact list
            return false;
        } finally {
            cursor.close();
        }

    }

    public Boolean check_isBusy(Integer TableID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sSql = "select count(*) from " + BusyTable + " where table_name_id = " + TableID;

        Cursor cursor = db.rawQuery(sSql, null);

        int tmpcount = 0;
        try {
            if (cursor.moveToFirst()) {
                do {
                    tmpcount = cursor.getInt(0);
                } while (cursor.moveToNext());
            }

            if (tmpcount > 0)
                return true;
            else
                return false;
        } finally {
            cursor.close();
        }

    }


    public List<Table_Name> getActiveTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<Table_Name> list = new ArrayList<Table_Name>();
        String sSql = "SELECT %s,%s,%s,%s,%s FROM %s where %s > 0  order by %s";
        sSql = String.format(sSql, colTable_Name_ID, colArea_ID, coltranid,
                coldocid, collongminute, ActiveTables, colTable_Name_ID,
                coltranid);

        Cursor cursor = db.rawQuery(sSql, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Table_Name tablenameobj = new Table_Name();
                    tablenameobj.setTable_Name_ID(Integer.parseInt(cursor
                            .getString(0)));
                    tablenameobj
                            .setTable_Name(getTableNameByTableID(tablenameobj
                                    .getTable_Name_ID()));
                    tablenameobj.setTableArea_ID(Integer.parseInt(cursor
                            .getString(1)));
                    tablenameobj
                            .setTranid(Integer.parseInt(cursor.getString(2)));
                    tablenameobj.setDocID(cursor.getString(3));
                    tablenameobj.setlongminute(Integer.parseInt(cursor
                            .getString(4)));
                    list.add(tablenameobj);
                } while (cursor.moveToNext());
            }
            // return contact list
            return list;
        } finally {
            cursor.close();
        }

    }

    public List<Table_Name> getActiveTableByFilter(String filterString) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<Table_Name> list = new ArrayList<Table_Name>();
        String sSql = "SELECT %s,%s,%s,%s,%s FROM %s where %s > 0 and %s";
        sSql = String.format(sSql, colTable_Name_ID, colArea_ID, coltranid,
                coldocid, collongminute, ActiveTables, colTable_Name_ID,
                filterString);

        Cursor cursor = db.rawQuery(sSql, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Table_Name tablenameobj = new Table_Name();
                    tablenameobj.setTable_Name_ID(Integer.parseInt(cursor
                            .getString(0)));
                    tablenameobj
                            .setTable_Name(getTableNameByTableID(tablenameobj
                                    .getTable_Name_ID()));
                    tablenameobj.setTableArea_ID(Integer.parseInt(cursor
                            .getString(1)));
                    tablenameobj
                            .setTranid(Integer.parseInt(cursor.getString(2)));
                    tablenameobj.setDocID(cursor.getString(3));
                    tablenameobj.setlongminute(Integer.parseInt(cursor
                            .getString(4)));
                    list.add(tablenameobj);
                } while (cursor.moveToNext());
            }
            // return contact list
            return list;
        } finally {
            cursor.close();
        }

    }

    public List<Table_Name> getActiveParcel() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<Table_Name> list = new ArrayList<Table_Name>();
        String sSql = "SELECT %s,%s,%s,%s FROM %s where %s < 0 order by %s";
        sSql = String.format(sSql, colTable_Name_ID, colArea_ID, coltranid,
                coldocid, ActiveTables, colTable_Name_ID, coltranid);

        Cursor cursor = db.rawQuery(sSql, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Table_Name tablenameobj = new Table_Name();
                    tablenameobj.setTable_Name_ID(Integer.parseInt(cursor
                            .getString(0)));
                    tablenameobj
                            .setTable_Name(getTableNameByTableID(tablenameobj
                                    .getTable_Name_ID()));
                    tablenameobj.setTableArea_ID(Integer.parseInt(cursor
                            .getString(1)));
                    tablenameobj
                            .setTranid(Integer.parseInt(cursor.getString(2)));
                    tablenameobj.setDocID(cursor.getString(3));
                    list.add(tablenameobj);
                } while (cursor.moveToNext());
            }
            // return contact list
            return list;
        } finally {
            cursor.close();
        }

    }

    public List<SaleItemObj> getSaleItemdataBySaleID(String SaleID) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<SaleItemObj> list = new ArrayList<SaleItemObj>();
        String sSql = "SELECT %s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s FROM %s where %s = %s and (%s IS NULL OR %s = 0 OR %s = 'null') order by %s";
        sSql = String.format(sSql, colsaleitemid, colsr, colcode, colprice,
                colqty, colamount, colmodifiedrowsr, colsrno, colunittype,
                coltakeaway, colremark, colitemcancel, colisFinishedItem,
                colKTV_StartTime, colKTV_EndTime, colfire_sr, colfired, colsubmitflag, SaleItemTable, colsaleid,
                SaleID, colmodifiedrowsr, colmodifiedrowsr, colmodifiedrowsr, colsrno);// modified by WaiWL on
        // 24/07/2015 --add
        // colisFinishedItem filed
        // //12/08/2015 --add
        // KTV_StartTime and
        // KTV_EndTime field

        Cursor cursor = db.rawQuery(sSql, null);

        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    SaleItemObj saleitemobj = new SaleItemObj();
                    saleitemobj.setsaleitemid(cursor.getString(0));
                    saleitemobj.setsr(cursor.getString(1));
                    saleitemobj.setcode(cursor.getString(2));
                    saleitemobj.setprice(cursor.getString(3));
                    saleitemobj.setqty(cursor.getString(4));
                    saleitemobj.setamount(cursor.getString(5));
                    saleitemobj.setsrno(cursor.getString(7));
                    saleitemobj.setunittype(cursor.getString(8));
                    saleitemobj.settaway(cursor.getString(9));
                    saleitemobj.setremark(cursor.getString(10));
                    saleitemobj.setcancelflag(Boolean.parseBoolean(cursor.getString(11)));
                    saleitemobj.setisFinishedItem(Boolean.parseBoolean(
                            cursor.getString(12) == null ? "0" : cursor.getString(12)));
                    saleitemobj.setKTV_StartTime(cursor.getString(13));// added
                    // by
                    // WaiWL
                    // on
                    // 12/08/2015
                    saleitemobj.setKTV_EndTime(cursor.getColumnName(14));// added
                    saleitemobj.setFire_sr(cursor.getString(15));
                    saleitemobj.setFired(cursor.getString(16));
                    saleitemobj.setsubmitflag(cursor.getString(17).equals("1"));

                    // Adding tableobj to list
                    list.add(saleitemobj);
                } while (cursor.moveToNext());
            }
            // return contact list
            return list;
        } finally {
            cursor.close();
        }

    }


    public ArrayList<SaleItemObj> getSaleItemdataBySaleIDArrayList(String SaleID) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<SaleItemObj> list = new ArrayList<SaleItemObj>();
        String sSql = "SELECT %s,%s,%s,sum(%s),%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s FROM %s where %s = %s and (%s = 'null' or %s=0 ) and %s='false' group by %s  order by %s";
        sSql = String.format(sSql, colsaleitemid, colsr, colcode, colprice,
                colqty, colamount, colmodifiedrowsr, colsrno, colunittype,
                coltakeaway, colremark, colitemcancel, colisFinishedItem,
                colKTV_StartTime, colKTV_EndTime, colfire_sr, colfired, SaleItemTable, colsaleid,
                SaleID, colmodifiedrowsr, colmodifiedrowsr, colitemcancel, colsrno, colsrno);// modified by WaiWL on
        // 24/07/2015 --add
        // colisFinishedItem filed
        // //12/08/2015 --add
        // KTV_StartTime and
        // KTV_EndTime field

        Cursor cursor = db.rawQuery(sSql, null);

        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    SaleItemObj saleitemobj = new SaleItemObj();
                    saleitemobj.setsaleitemid(cursor.getString(0));
                    saleitemobj.setsr(cursor.getString(1));
                    saleitemobj.setcode(cursor.getString(2));
                    saleitemobj.setprice(cursor.getString(3));
                    saleitemobj.setqty(cursor.getString(4));
                    saleitemobj.setamount(cursor.getString(5));
                    saleitemobj.setsrno(cursor.getString(7));
                    saleitemobj.setunittype(cursor.getString(8));
                    saleitemobj.settaway(cursor.getString(9));
                    saleitemobj.setremark(cursor.getString(10));
                    saleitemobj.setcancelflag(Boolean.parseBoolean(cursor
                            .getString(11)));
                    saleitemobj.setisFinishedItem(cursor.getString(12).equals(
                            "1") ? true : false);// added by WaiWL on 24/07/2015
                    saleitemobj.setKTV_StartTime(cursor.getString(13));// added
                    // by
                    // WaiWL
                    // on
                    // 12/08/2015
                    saleitemobj.setKTV_EndTime(cursor.getColumnName(14));// added
                    saleitemobj.setFire_sr(cursor.getString(15));
                    saleitemobj.setFired(cursor.getString(16));

                    // Adding tableobj to list
                    list.add(saleitemobj);
                } while (cursor.moveToNext());
            }
            // return contact list
            return (ArrayList<SaleItemObj>) list;
        } finally {
            cursor.close();
        }

    }


    public ArrayList<SaleItemObj> getSplitItem(String Datalink, String SaleID) {
        url = Datalink;
        List<SaleItemObj> list = new ArrayList<SaleItemObj>();
        JSONArray itemjsonarray = Json_class.getJson(url + "/Data/GetSplitItem");

        for (int i = 0; i < itemjsonarray.length(); i++) {
            JSONObject c;
            try {
                c = itemjsonarray.getJSONObject(i);
                SaleItemObj saleitemobj = new SaleItemObj();
                saleitemobj.setsaleitemid(c.getString(colsaleid));
                saleitemobj.setsr(c.getString(colsr));
                saleitemobj.setcode(c.getString(colcode));
                saleitemobj.setprice(c.getString(colsaleprice));
                saleitemobj.setqty(c.getString(colqty));
                saleitemobj.setamount(c.getString(colamount));
                saleitemobj.setsrno(c.getString(colsrno));
                saleitemobj.setunittype(c.getString(colunit_type));
                saleitemobj.settaway(c.getString(coltakeaway));
                saleitemobj.setremark(c.getString(colremark));
                saleitemobj.setcancelflag(Boolean.parseBoolean(c
                        .getString(colitemcancel)));
                saleitemobj.setisFinishedItem(c.getString(colisFinishedItem).equals(
                        "1") ? true : false);// added by WaiWL on 24/07/2015
                saleitemobj.setKTV_StartTime(c.getString(colKTV_StartTime));// added
                // by
                // WaiWL
                // on
                // 12/08/2015
                saleitemobj.setKTV_EndTime(c.getString(colKTV_EndTime));// added
                saleitemobj.setFire_sr(c.getString(colfire_sr));
                saleitemobj.setFired(c.getString(colfired));

                // Adding tableobj to list
                list.add(saleitemobj);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        return (ArrayList<SaleItemObj>) list;
    }


    public List<SaleItemObj> getSaleItemQtyBySaleIDandSRNo(String SaleID,
                                                           String srno) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<SaleItemObj> list = new ArrayList<SaleItemObj>();
        String sSql = "SELECT %s,%s FROM %s where %s = %s and %s = %s and %s = 'null' order by %s";
        sSql = String.format(sSql, colqty, colsr, SaleItemTable, colsaleid,
                SaleID, colsrno, srno, colmodifiedrowsr, colsrno);

        Cursor cursor = db.rawQuery(sSql, null);

        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    SaleItemObj saleitemobj = new SaleItemObj();
                    saleitemobj.setqty(cursor.getString(0));
                    saleitemobj.setsr(cursor.getString(1));
                    // Adding tableobj to list
                    list.add(saleitemobj);
                } while (cursor.moveToNext());
            }
            // return contact list
            return list;
        } finally {
            cursor.close();
        }

    }

    public List<SaleItemObj> getSaleItemQtyBySaleIDandSR(String SaleID,
                                                         String sr) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<SaleItemObj> list = new ArrayList<SaleItemObj>();
        String sSql = "SELECT %s,%s FROM %s where %s = %s and %s = %s and (%s IS NULL or %s = 0 or %s = 'null') order by %s";
        sSql = String.format(sSql, colqty, colsr, SaleItemTable, colsaleid,
                SaleID, colsr, sr, colmodifiedrowsr, colmodifiedrowsr, colmodifiedrowsr, colsrno);

        Cursor cursor = db.rawQuery(sSql, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    SaleItemObj saleitemobj = new SaleItemObj();
                    saleitemobj.setqty(cursor.getString(0));
                    saleitemobj.setsr(cursor.getString(1));
                    // Adding tableobj to list
                    list.add(saleitemobj);
                } while (cursor.moveToNext());
            }
            // return contact list
            return list;
        } finally {
            cursor.close();
        }

    }

    public int getSaleItemCountbyInvoiceNo(String Invoiceno) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<SaleItemObj> list = new ArrayList<SaleItemObj>();
        String SaleID = getSaleIDbyInvoiceid(Invoiceno);
        String sSql = "SELECT %s FROM %s where %s = '%s'  and %s = 'null' order by %s";
        sSql = String.format(sSql, colsaleitemid, SaleItemTable, colsaleid,
                SaleID, colmodifiedrowsr, colsrno);

        Cursor cursor = db.rawQuery(sSql, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    SaleItemObj saleitemobj = new SaleItemObj();
                    saleitemobj.setsaleitemid(cursor.getString(0));

                    // Adding tableobj to list
                    list.add(saleitemobj);
                } while (cursor.moveToNext());
            }
            // return contact list
            return list.size();
        } finally {
            cursor.close();
        }

    }

    public List<SelectedItemModifierObj> getSaleItemModifierdataBySaleID(String SaleID, String sr) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<SelectedItemModifierObj> list = new ArrayList<SelectedItemModifierObj>();
        String sSql = "SELECT %s,%s,%s,%s,%s,%s,%s,%s FROM %s where %s = %s and %s = %s order by %s";
        sSql = String.format(sSql, colsaleitemid, colcode, colprice, colqty,
                colamount, colmodifiedrowsr, colsrno, colsr, SaleItemTable,
                colsaleid, SaleID, colmodifiedrowsr, sr, colsaleitemid);

        Cursor cursor = db.rawQuery(sSql, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    SelectedItemModifierObj saleitemobj = new SelectedItemModifierObj();

                    saleitemobj.setitemid(cursor.getString(1));
                    saleitemobj.setprice(cursor.getString(2));
                    saleitemobj.setqty(cursor.getString(3));
                    saleitemobj.setamount(cursor.getString(4));
                    saleitemobj.setitemsr(cursor.getString(5));
                    saleitemobj.setname(getModifierItemnamebycode(saleitemobj.getitemid()));    //added by ZYP for unicode [06-01-2020]
                    //Added by Arkar Moe on [2016/10/04]-[Res0437]
                    if (saleitemobj.getname() == "") {
                        saleitemobj.setname(
                                getItemNamebyitemid(saleitemobj.getitemid()));
                    }
                    //[Res0437]
                    //saleitemobj.setsr(Integer.parseInt(cursor.getString(7)));
                    saleitemobj.setstatus("modifier");
                    // Adding tableobj to list
                    list.add(saleitemobj);
                } while (cursor.moveToNext());
            }
            // return contact list
            return list;
        } finally {
            cursor.close();
        }

    }

    public List<SelectedItemModifierObj> getSetMenudataBySaleID(String SaleID,
                                                                String sr) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<SelectedItemModifierObj> list = new ArrayList<SelectedItemModifierObj>();
        String sSql = "SELECT %s,%s,%s,%s,%s,%s,%s,%s FROM %s where %s = %s and %s = %s order by %s";
        sSql = String.format(sSql, colsaleitemid, colcode, colprice, colqty,
                colamount, colmodifiedrowsr, colsrno, colsr, SaleItemTable,
                colsaleid, SaleID, colmodifiedrowsr, sr, colsaleitemid);

        Cursor cursor = db.rawQuery(sSql, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    SelectedItemModifierObj saleitemobj = new SelectedItemModifierObj();
                    saleitemobj.setitemid(cursor.getString(1));
                    saleitemobj.setprice(cursor.getString(2));
                    saleitemobj.setqty(cursor.getString(3));
                    saleitemobj.setamount(cursor.getString(4));
                    saleitemobj.setitemsr(cursor.getString(5));
                    saleitemobj.setname(
                            getItemNamebyitemid(saleitemobj.getitemid()));        //added by ZYP for unicode [06-01-2020]
                    saleitemobj.setsr(Integer.parseInt(cursor.getString(7)));
                    saleitemobj.setstatus("setmenuitem");
                    // Adding tableobj to list
                    list.add(saleitemobj);
                } while (cursor.moveToNext());
            }
            // return contact list
            return list;
        } finally {
            cursor.close();
        }

    }

    public void ClearTable(String TableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TableName, null, null);
    }

    private void AddArea(Area A) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(colArea_ID, A.getArea_ID());
        cv.put(colArea_Name, A.getArea_Name());
        cv.put(colDescription, A.getDescription());
        cv.put(colArea_Code, A.getArea_Code());
        cv.put(colRemark, A.getRemark());
        db.insert(AreaTable, null, cv);

    }

    private void Addkitchendata(KitchendataObj A) {
        SQLiteDatabase db = this.getWritableDatabase();

        if (isKitchendataAlreadyexist(A.gettranid(), A.getsr())) {
            return;
        }
        try {
            ContentValues cv = new ContentValues();
            cv.put(coltranid, A.gettranid());
            cv.put(colsr, A.getsr());
            cv.put(colcode, A.getcode());
            cv.put(colunit_qty, A.getunit_qty());
            cv.put(colTable_Name_ID, A.getTable_Name_ID());
            cv.put(colRef_no, A.getRef_No());
            cv.put(colsalesmen_id, A.getSalesmen_id());
            cv.put(colcook_status, A.getcook_status());
            cv.put(coluserid, A.getuserid());
            cv.put(colsudden_fix, A.getsudden_fix());
            cv.put(colfinishshow, "N");
            db.insert(kitchenTable, null, cv);
        } catch (Exception ex) {
        }
    }

    // added by WaiWL on 25/07/2015
    public boolean getStatusFinishedItem(String tranid, String srno) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT count(*) FROM " + SaleItemTable
                + " where " + colisFinishedItem + "=1 and " + colsaleid + " = "
                + Integer.parseInt(tranid) + " and " + colsrno + " = "
                + Integer.parseInt(srno);
        Cursor cursor = db.rawQuery(selectQuery, null);
        // int tmpcount = 0;
        int tmpcount = 0;
        try {
            if (cursor.moveToFirst()) {
                do {
                    tmpcount = cursor.getInt(0);
                } while (cursor.moveToNext());
            }

            if (tmpcount > 0)
                return true;
            else
                return false;
        } finally {
            cursor.close();
        }
    }

    private Boolean isKitchendataAlreadyexist(int tranid, int sr) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT " + coltranid + " FROM " + kitchenTable
                + " where " + coltranid + " = " + tranid + " and " + colsr
                + " = " + sr;
        Cursor cursor = db.rawQuery(selectQuery, null);
        String data = "";
        // looping through all rows and adding to list
        try {
            if (cursor.moveToFirst()) {
                do {
                    data = cursor.getString(0);
                } while (cursor.moveToNext());
            }
            // return contact list
            if (data == null || data == "")
                return false;
            else
                return true;
        } finally {
            cursor.close();
        }

    }

    private void AddActiveTable(Table_Name T) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(colTableArea_ID, T.getTableArea_ID());
        cv.put(colTable_Name_ID, T.getTable_Name_ID());
        cv.put(coltranid, T.getTranid());
        cv.put(coldocid, T.getDocID());
        cv.put(colreserved, T.getreserved());
        cv.put(coluserid, T.getuserid());
        cv.put(collongminute, T.getlongminute());
        cv.put(colBillChecked, T.get_checkbill());
        cv.put(colSelfOrderTable, T.getSelforder_table());//added WHM [2020-05-19]
        db.insert(ActiveTables, null, cv);

    }


    //added by EKK on 08-01-2020
    private void AddBusyTable(BusyTable BT) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Btable_name_id, BT.getTable_name_id());
        cv.put(Btranid, BT.getTranid());
        db.insert(BusyTable, null, cv);
    }

    private void AddTable(Table_Name T) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(colTableArea_ID, T.getTableArea_ID());
        cv.put(colTable_Name_ID, T.getTable_Name_ID());
        cv.put(colTable_Name, T.getTable_Name());
        cv.put(colTableDescription, T.getTableDescription());
        cv.put(colTable_Code, T.getTable_Code());
        cv.put(colTableRemark, T.getTableRemark());
        cv.put(colTableSortcode, T.getSort_code());
        db.insert(Table_Name, null, cv);

    }

    private void AddDictionary(Dictionary T) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        //if (!LoginActivity.isUnicode) !LoginActivity.isUnicode = false;

        cv.put(colsrno, T.getsr());
        cv.put(colEnglish, T.getLanguage1());
        cv.put(colMyanmar, T.getLanguage2());    //added by ZYP for unicode [06-01-2020]
        cv.put(colChinese, T.getLanguage3());//added by EKK on 10/07
        db.insert(DictionaryTable, null, cv);
    }

    private void AddModifierGroup(ModifierGroupObj T) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(colmodifiedgroupid, T.getmodifiergroupid());
        cv.put(colmodifiername, T.getname());
        cv.put(colmodifiershort, T.getshort());
        db.insert(ModifierGroupTable, null, cv);
    }

    private void AddCustomer(CustomerObj T) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

//		if (!LoginActivity.isUnicode) !LoginActivity.isUnicode = false;

        cv.put(colcustomerid, T.getcustomerid());
        cv.put(colcustomercode, T.getcustomercode());
        cv.put(colcustomername, T.getcustomername());
        cv.put(colcustomercredit, T.getcustomercredit());
        cv.put(colcustomer_pricelevel, T.get_customer_pricelevel()); //added WHM [2020-01-27] MDY2-200141
        db.insert(CustomerTable, null, cv);
    }

    public void LoadArea(String Datalink) {
        url = Datalink;
        JSONArray itemjsonarray = Json_class.getJson(url + "/Data/GetArea");
        //Added by KLM for UniZawgyi AutoDetect 23022022
        try {
            if (!LoginActivity.isUnicode) {
                itemjsonarray = new JSONArray(Rabbit.uni2zg(itemjsonarray.toString()));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (itemjsonarray.length() > 0) {
            ClearTable(AreaTable);
        }

        for (int i = 0; i < itemjsonarray.length(); i++) {
            JSONObject c;
            try {
                c = itemjsonarray.getJSONObject(i);
                Area A = new Area();
                A.setArea_ID(Integer.parseInt(c.getString(colArea_ID)));
                A.setArea_Name(c.getString(colArea_Name));
                A.setDescription(c.getString(colDescription));
                A.setArea_Code(c.getString(colArea_Code));
                A.setRemark(c.getString(colRemark));

//				if (!LoginActivity.isUnicode) !LoginActivity.isUnicode = false;

                AddArea(A);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void LoadKitchenData(String Datalink) {
        url = Datalink;
        JSONArray itemjsonarray = Json_class.getJson(url
                + "/Data/GetKitchenData?userid="
                + java.net.URLEncoder.encode(getwaiterid()));

        //Added by KLM for UniZawgyi Auto Detect 23022022
        try {
            if (!LoginActivity.isUnicode) {
                itemjsonarray = new JSONArray(Rabbit.uni2zg(itemjsonarray.toString()));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < itemjsonarray.length(); i++) {
            JSONObject c;
            try {
                c = itemjsonarray.getJSONObject(i);
                KitchendataObj A = new KitchendataObj();
                A.settranid(Integer.parseInt(c.getString(coltranid)));
                A.setsr(Integer.parseInt(c.getString(colsr)));
                A.setcode(Integer.parseInt(c.getString(colcode)));
                A.setunit_qty(Double.parseDouble(c.getString(colunit_qty)));
                A.setTable_Name_ID(Integer.parseInt(c
                        .getString(colTable_Name_ID)));
                A.setRef_No(Integer.parseInt(c.getString(colRef_no)));
                A.setSalesmen_id((c.getString(colsalesmen_id)));
                A.setcook_status(c.getString(colcook_status));
                A.setuserid(Integer.parseInt(c.getString(coluserid)));
                A.setsudden_fix((c.getString(colsudden_fix)));
                Addkitchendata(A);
                //MDetect.INSTANCE.getStringArray()
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    // added by WaiWL on 11/06/2015
    public void deleteActivetable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ActiveTables, "", null);
    }

    // //////////////

    // added by WaiWL on 15/06/2015
    public void LoadActivetableFromLocal(String AreaID) {
        /*
         * SQLiteDatabase db = this.getWritableDatabase(); String selectQuery =
         * "SELECT " + colTableArea_ID + "," + colTable_Name_ID + "," + coldocid
         * + "," + coltranid + "," + colreserved + "," + coluserid + "," +
         * collongminute + " FROM " + ActiveTables +" where "+ colArea_ID +
         * " = " + AreaID; Cursor cursor = db.rawQuery(selectQuery, null);
         * String data = ""; // looping through all rows and adding to list try
         * { if (cursor.moveToFirst()) { do { Table_Name A = new Table_Name();
         * A.setTableArea_ID(cursor.getInt(0));
         * A.setTable_Name_ID(cursor.getInt(1));
         * A.setDocID(cursor.getString(2)); A.setTranid(cursor.getInt(3));
         * A.setreserved(cursor.getString(4)); A.setuserid(cursor.getInt(5));
         * A.setlongminute(cursor.getInt(6));
         *
         * AddActiveTable(A); } while (cursor.moveToNext()); }
         *
         * } finally { cursor.close(); }
         */
    }

    // /////////////

    public void LoadActivetable(String Datalink, String AreaID) {
        url = Datalink;
        JSONArray itemjsonarray = Json_class.getJson(url
                + "/Data/GetActiveTableByAreaID?areaid="
                + java.net.URLEncoder.encode(AreaID));

        //Added by KLM for UniZawgyi Auto Detect 23022022
        try {
            if (!LoginActivity.isUnicode) {
                itemjsonarray = new JSONArray(Rabbit.uni2zg(itemjsonarray.toString()));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ActiveTables, colArea_ID + "=" + AreaID, null);

        for (int i = 0; i < itemjsonarray.length(); i++) {
            JSONObject c;
            try {
                c = itemjsonarray.getJSONObject(i);
                Table_Name A = new Table_Name();
                A.setTable_Name_ID(Integer.valueOf(c
                        .getString(colTable_Name_ID)));
                A.setTableArea_ID(Integer.valueOf(c.getString(colTableArea_ID)));
                A.setTranid(Integer.parseInt(c.getString(coltranid)));
                A.setDocID(c.getString("Ref_no"));
                A.setreserved(c.getString(colreserved));
                A.setuserid(Integer.parseInt(c.getString(coluserid)));
                A.setlongminute(Integer.parseInt(c.getString(collongminute)));
                A.set_checkbill(c.getString(colBillChecked));
                A.setSelforder_table(c.getString(colSelfOrderTable));//added WHM [2020-05-19] self order
                AddActiveTable(A);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void LoadActivetableByTableID(String Datalink, String TableID, String Userid) {
        url = Datalink;
        JSONArray itemjsonarray = Json_class.getJson(url
                + "/Data/GetActiveTableByTableID?tableid="
                + java.net.URLEncoder.encode(TableID) + "&Userid="
                + java.net.URLEncoder.encode(Userid));

        //Added by KLM for UniZawgyi Auto Detect 23022022
        try {
            if (!LoginActivity.isUnicode) {
                itemjsonarray = new JSONArray(Rabbit.uni2zg(itemjsonarray.toString()));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ActiveTables, colTable_Name_ID + "=" + TableID, null);

        for (int i = 0; i < itemjsonarray.length(); i++) {
            JSONObject c;
            try {
                c = itemjsonarray.getJSONObject(i);
                Table_Name A = new Table_Name();
                A.setTable_Name_ID(Integer.valueOf(c
                        .getString(colTable_Name_ID)));
                A.setTableArea_ID(Integer.valueOf(c.getString(colTableArea_ID)));
                A.setTranid(Integer.parseInt(c.getString(coltranid)));
                A.setDocID(c.getString("Ref_no"));
                A.setreserved(c.getString(colreserved));
                A.setuserid(Integer.parseInt(c.getString(coluserid)));
                A.setlongminute(Integer.parseInt(c.getString(collongminute)));
                A.setSelforder_table(c.getString(colSelfOrderTable));//added WHM [2020-05-19] self order
                AddActiveTable(A);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void LoadActivetable(String Datalink) {
        url = Datalink;
        JSONArray itemjsonarray = Json_class.getJson(url
                + "/Data/GetActiveTable");

        //Added by KLM for UniZawgyi Auto Detect 23022022
        try {
            if (!LoginActivity.isUnicode) {
                itemjsonarray = new JSONArray(Rabbit.uni2zg(itemjsonarray.toString()));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ClearTable(ActiveTables);
        for (int i = 0; i < itemjsonarray.length(); i++) {
            JSONObject c;
            try {
                c = itemjsonarray.getJSONObject(i);
                Table_Name A = new Table_Name();
                A.setTable_Name_ID(Integer.valueOf(c.getString(colTable_Name_ID)));
                A.setTableArea_ID(Integer.valueOf(c.getString(colTableArea_ID)));
                A.setTranid(Integer.parseInt(c.getString(coltranid)));
                A.setDocID(c.getString("Ref_no"));
                A.setreserved(c.getString(colreserved));
                A.setlongminute(Integer.parseInt(c.getString(collongminute)));
                A.setuserid(Integer.parseInt(c.getString("userid")));
                AddActiveTable(A);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void LoadBusyTable(String Datalink) {
        url = Datalink;
        JSONArray itemjsonarray = Json_class.getJson(url
                + "/Data/GetBusyTable");

        //Added by KLM for UniZawgyi Auto Detect 23022022
        try {
            if (!LoginActivity.isUnicode) {
                itemjsonarray = new JSONArray(Rabbit.uni2zg(itemjsonarray.toString()));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ClearTable(BusyTable);
        for (int i = 0; i < itemjsonarray.length(); i++) {
            JSONObject c;
            try {
                c = itemjsonarray.getJSONObject(i);

                BusyTable BT = new BusyTable();
                BT.setTable_name_id(Integer.valueOf(c.getString(Btable_name_id)));
                BT.setTranid(Integer.valueOf(c.getString(Btranid)));

                AddBusyTable(BT);

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void Delete_Sale_Head() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(SaleTable, null, null);
    }

    public void Delete_Sale_Det() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(SaleItemTable, null, null);

    }

    public void LoadSaleHeader(String Datalink, String Tableid, String tranid) {
        url = Datalink;
        JSONArray itemjsonarray = Json_class.getJson(url
                + "/Data/GetSaleHeader?tranid="
                + java.net.URLEncoder.encode(tranid));

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(SaleTable, colsaleid + "=" + tranid, null);

        if (itemjsonarray.length() <= 0) {
            DeleteSaleDataByTableID(Tableid);
        }

        for (int i = 0; i < itemjsonarray.length(); i++) {
            JSONObject c;
            try {
                db = this.getWritableDatabase();
                c = itemjsonarray.getJSONObject(i);
                ContentValues cvalue = new ContentValues();
                String tableid = "";
                tableid = c.getString("tablename_id");

                if (tableid.equals("null"))
                    tableid = "Parcel";

                cvalue.put(colinvoiceno, c.getString("invoiceno"));
                cvalue.put(colTable_Name_ID, tableid);
                cvalue.put(colroomid, c.getString("roomid"));
                cvalue.put(colcustomerid, c.getString("customerid"));
                cvalue.put(colamount, c.getString("invoice_amount"));
                cvalue.put(colsaledate, c.getString("Date"));
                cvalue.put(colsaleid, tranid);
                cvalue.put(colcustcount, c.getString(colcustcount));
                cvalue.put(coluserid, c.getString(coluserid));
                cvalue.put(colsalesmen_id, c.getString("salemanid"));
                cvalue.put(colRef_no, c.getString(colRef_no));
                cvalue.put(colDelivery_type, c.getString("delivery_type"));//added WHM [2020-05-08]
                cvalue.put(colTaxfree, c.getString("Taxfree"));
                cvalue.put(colMemberCardID, c.getString("MemberCardID"));//added by ZYP [10-09-2021]
                db.insert(SaleTable, null, cvalue);

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public String LoadSaleHeaderCount(String Datalink, String Tableid) //Added by ArkarMoe on [2017-06-08]
    {
        url = Datalink;
/*		String ItemCount  = Json_class.getJson(url
				+ "/Data/GetSaleHeaderCount?tranid="
				+ java.net.URLEncoder.encode(Tableid));

		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(SaleTable, colsaleid + "=" + Tableid, null);

		if (itemjsonarray.length() <= 0) {
			DeleteSaleDataByTableID(Tableid);
		}*/

        String HeaderCount = Json_class.getString(url
                + "/Data/GetSaleHeaderCount?table_name_id="
                + java.net.URLEncoder.encode(Tableid));
        return HeaderCount == "" ? "0" : HeaderCount;
    }


    public Customer_analysisObj LoadCustomerAnalysis(String Datalink,
                                                     String tranid) {
        url = Datalink;
        JSONArray itemjsonarray = Json_class.getJson(url
                + "/Data/GetCustomer_Analysis_Data?tranid="
                + java.net.URLEncoder.encode(tranid));
        Customer_analysisObj analysisobj = new Customer_analysisObj();
        if (itemjsonarray.length() > 0) {
            for (int i = 0; i < itemjsonarray.length(); i++) {
                JSONObject c;
                try {

                    c = itemjsonarray.getJSONObject(i);
                    ContentValues cvalue = new ContentValues();
                    analysisobj.setcustomertype(c.getString("PBT"));
                    analysisobj.setmalecount(Integer.parseInt(c
                            .getString("male")));
                    analysisobj.setfemalecount(Integer.parseInt(c
                            .getString("female")));
                    analysisobj.setadultcount(Integer.parseInt(c
                            .getString("adult")));
                    analysisobj.setchildcount(Integer.parseInt(c
                            .getString("child")));
                    analysisobj.setwesterncount(Integer.parseInt(c
                            .getString("western")));
                    analysisobj.setasiancount(Integer.parseInt(c
                            .getString("asian")));
                    analysisobj.setmyanmarcount(Integer.parseInt(c
                            .getString("myanmar")));

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        return analysisobj;
    }

    public void DeleteSaleDataByTableID(String tableid) {
        SQLiteDatabase db = this.getWritableDatabase();
        int tranid = 0;
        String selectQuery = "SELECT  " + colsaleid + " FROM " + SaleTable
                + " where " + colTable_Name_ID + " = " + tableid;
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    tranid = Integer.parseInt(cursor.getString(0));
                    db.delete(SaleTable, colsaleid + "=" + tranid, null);
                    db.delete(SaleItemTable, colsaleid + "=" + tranid, null);
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
        }

    }

    // added by WaiWL on 09/07/2015 --For Sale Item Voucher
    public void LoadSaleItemForVoucher(String Datalink, String tranid) {
        url = Datalink;
        JSONArray itemjsonarray = Json_class.getJson(url
                + "/Data/GetSaleItemForVoucher?tranid="
                + java.net.URLEncoder.encode(tranid));

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(SaleItemTable, colsaleid + "=" + tranid, null);
        if (itemjsonarray.length() == 0) {
            return;
        }
        for (int i = 0; i < itemjsonarray.length(); i++) {
            JSONObject c;
            try {
                c = itemjsonarray.getJSONObject(i);
                db = this.getWritableDatabase();
                ContentValues cvalue = new ContentValues();

                Double amount = Double.parseDouble(c.getString("unit_qty") == "null" ? "0" : c.getString("unit_qty"))
                        * Double.parseDouble(c.getString("sale_price").equals("null") ? "0" : c.getString("sale_price"));


                cvalue.put(colsr, c.getString("sr"));
                cvalue.put(colsrno, c.getString("srno"));
                cvalue.put(colsaleid, tranid);
                cvalue.put(colcode, c.getString("code"));
                cvalue.put(colprice, c.getString("sale_price"));
                cvalue.put(colqty, c.getString("unit_qty"));
                cvalue.put(colamount, amount);
                cvalue.put(colremark, c.getString("remark"));
                cvalue.put(colmodifiedrowsr, c.getString("modifiedrowsr"));
                cvalue.put(colunittype, c.getString("unittype"));
                cvalue.put(coltakeaway, c.getString("Takeaway"));
                cvalue.put(colitemcancel, c.getString("itemcancel"));
                cvalue.put(colisFinishedItem, c.getString("isFinishedItem"));
                cvalue.put(colsubmitflag, true);
                db.insert(SaleItemTable, null, cvalue);

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    // ///////////////

    public void LoadSaleItem(String Datalink, String tranid) {
        url = Datalink;
        JSONArray itemjsonarray = Json_class.getJson(url
                + "/Data/GetSaleItem?tranid="
                + java.net.URLEncoder.encode(tranid));
        try {
            if (!LoginActivity.isUnicode) {
                itemjsonarray = new JSONArray(Rabbit.uni2zg(itemjsonarray.toString()));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(SaleItemTable, colsaleid + "=" + tranid, null);
        if (itemjsonarray.length() == 0) {
            return;
        }
        for (int i = 0; i < itemjsonarray.length(); i++) {
            JSONObject c;
            try {
                c = itemjsonarray.getJSONObject(i);
                db = this.getWritableDatabase();
                ContentValues cvalue = new ContentValues();

                Double amount = Double.parseDouble(c.getString("unit_qty") == "null" ? "0" : c.getString("unit_qty"))
                        * Double.parseDouble(c.getString("sale_price").equals("null") ? "0" : c.getString("sale_price"));

                cvalue.put(colsr, c.getString("sr"));
                cvalue.put(colsrno, c.getString("srno"));
                cvalue.put(colsaleid, tranid);
                cvalue.put(colcode, c.getString("code"));
                cvalue.put(colprice, c.getString("sale_price"));
                cvalue.put(colqty, c.getString("unit_qty"));
                cvalue.put(colamount, amount);
                cvalue.put(colremark, c.getString("remark"));
                cvalue.put(colmodifiedrowsr, c.getString("modifiedrowsr"));
                cvalue.put(colunittype, c.getString("unittype"));
                cvalue.put(coltakeaway, c.getString("Takeaway"));
                cvalue.put(colitemcancel, c.getString("itemcancel"));
                cvalue.put(colisFinishedItem, c.getBoolean("isFinishedItem"));// added
                // by
                // WaiWL
                // on
                // 24/07/2015
                cvalue.put(colKTV_StartTime, c.getString("KTV_StartTime"));// added
                // by
                // WaiWL
                // on
                // 12/08/2015
                cvalue.put(colKTV_EndTime, c.getString("KTV_EndTime"));// added
                // by
                // WaiWL
                // on
                // 12/08/2015

                cvalue.put(colfire_sr, c.getString("fire_sr"));
                cvalue.put(colfired, c.getString("fired"));
                cvalue.put(colsubmitflag, true);

                db.insert(SaleItemTable, null, cvalue);

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public VoucherDetailObj LoadSaleSummary(String Datalink, String tranid) {
        url = Datalink;
        JSONArray itemjsonarray = Json_class.getJson(url
                + "/Data/GetVoucherData?tranid="
                + java.net.URLEncoder.encode(tranid));

        SQLiteDatabase db = this.getWritableDatabase();
        VoucherDetailObj vdobj = new VoucherDetailObj();
        if (itemjsonarray == null) {
            return vdobj;
        }
        if (itemjsonarray.length() > 0) {
            for (int i = 0; i < itemjsonarray.length(); i++) {
                JSONObject c;
                try {
                    c = itemjsonarray.getJSONObject(i);

                    vdobj.settranid(Integer.parseInt(tranid));
                    vdobj.setdate(c.getString("date"));
                    vdobj.setinvoice_amount(c.getString("invoice_amount"));
                    vdobj.setpaid_amount(c.getString("paid_amount"));
                    vdobj.setdiscount(c.getString("discount"));
                    vdobj.setuserid(c.getString("userid"));
                    vdobj.setgtaxamount(c.getString("GTaxamount") == null ? "0" : c.getString("GTaxamount"));
                    vdobj.setstaxamount(c.getString("STaxamount") == null ? "0" : c.getString("STaxamount"));
                    vdobj.setrtaxamount(c.getString("RTaxamount") == null ? "0" : c.getString("RTaxamount"));  //Added by Arkar Moe on [21/07/2016] for adding Room Tax in Tablet Voucher [Res-0209]
                    vdobj.setgtaxpercent(c.getString("GovernmentTaxPercent") == null ? "0" : c.getString("GovernmentTaxPercent"));
                    vdobj.setstaxpercent(c.getString("ServiceTaxPercent") == null ? "0" : c.getString("ServiceTaxPercent"));
                    vdobj.setMemdiscount(c.getString("memdiscount") == null ? "0" : c.getString("memdiscount"));
                    vdobj.setmemamount(c.getString("memamount") == null ? "0" : c.getString("memamount"));
                    vdobj.setfocamount(c.getString("focamount") == null ? "0" : c.getString("focamount"));
                    vdobj.setnetamount(c.getString("netamount") == null ? "0" : c.getString("netamount"));
                    vdobj.setitemdiscount(c.getString("itemdiscount") == null ? "0" : c.getString("itemdiscount"));
                    vdobj.setDelivery_charges(c.getString("delivery_charges") == null ? "0" : c.getString("delivery_charges"));//added WHM [2020-05-07]

                    return vdobj;

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    return vdobj;
                }
            }
        }
        return vdobj;

    }

    public void LoadTable(String Datalink) {
        url = Datalink;
        JSONArray itemjsonarray = Json_class.getJson(url + "/Data/GetTable");
        //Added by KLM for Uni/Zawgyi Auto Detect 23022022
        try {
            if (!LoginActivity.isUnicode) {
                itemjsonarray = new JSONArray(Rabbit.uni2zg(itemjsonarray.toString()));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (itemjsonarray.length() > 0) {
            ClearTable(Table_Name);
        }

        String sortcode = "0";//added WHM [2020-11-10]
        for (int i = 0; i < itemjsonarray.length(); i++) {
            JSONObject c;
            try {
                c = itemjsonarray.getJSONObject(i);
                Table_Name T = new Table_Name();
                T.setTableArea_ID(Integer.valueOf(c.getString(colTableArea_ID)));
                T.setTable_Name_ID(Integer.valueOf(c
                        .getString(colTable_Name_ID)));
                T.setTable_Name(c.getString(colTable_Name));
                T.setTableDescription(c.getString(colTableDescription));
                T.setTable_Code(c.getString(colTable_Code));
                T.setTableRemark(c.getString(colTableRemark));
                //T.setSort_code(c.getString(colTableSortcode));

                //modified WHM [2020-11-10]
                sortcode = c.getString(colTableSortcode);
                sortcode = sortcode.equals("null") || sortcode.equals("") ? "0" : sortcode;
                T.setSort_code(Integer.valueOf(sortcode));

                AddTable(T);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }

    public void LoadNote(String Datalink, String tranid) {
        url = Datalink;
        JSONArray itemjsonarray = Json_class.getJson(url
                + "/Data/GetNote?tranid=" + java.net.URLEncoder.encode(tranid));
        if (itemjsonarray.length() > 0) {
            ClearTable(Note_table);
        }

        for (int i = 0; i < itemjsonarray.length(); i++) {
            JSONObject c;
            try {
                c = itemjsonarray.getJSONObject(i);
                Note N = new Note();
                N.setTranid(Integer.valueOf(c.getString(colntranid)));
                N.setsr(Integer.valueOf(c.getString(colnsr)));
                N.setuserid(Integer.valueOf(c.getString(colnuserid)));
                N.setDate(c.getString(colnDate));
                N.setNotes(c.getString(colnNotes));
                AddNote(N);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }

    public void LoadAppsetting(String Datalink) {
        url = Datalink;
        JSONArray itemjsonarray = Json_class.getJson(url
                + "/Data/GetAppSetting");
        //Added by KLM for UniZawgyi Auto Detect 23022022
        try {
            if (!LoginActivity.isUnicode) {
                itemjsonarray = new JSONArray(Rabbit.uni2zg(itemjsonarray.toString()));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (itemjsonarray.length() > 0) {
            ClearTable(AppSetting_table);
        }

        for (int i = 0; i < itemjsonarray.length(); i++) {
            JSONObject c;
            try {
                c = itemjsonarray.getJSONObject(i);
                Appsetting As = new Appsetting();
                As.setSetting_No(c.getString(colSetting_No));
                As.setSetting_Name(c.getString(colSetting_Name));
                As.setSetting_Value(c.getString(colSetting_Value));
                As.setRemark(c.getString(colRemark));
                As.setAddedOn(c.getString(colAddedOn));
                As.setAddedBy(c.getString(colAddedBy));
                AddAppSetting(As);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }

    public void LoadItemRemark(String Datalink, String categoryid) {
        url = Datalink;
        JSONArray itemjsonarray = Json_class.getJson(url
                + "/Data/GetItemRemark?categoryid=" + categoryid);
        //Added by KLM for UniZawgyi Auto Detect 23022022
        try {
            if (!LoginActivity.isUnicode) {
                itemjsonarray = new JSONArray(Rabbit.uni2zg(itemjsonarray.toString()));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (itemjsonarray.length() > 0) {
            ClearTable(ItemRemark_table);
        }

        for (int i = 0; i < itemjsonarray.length(); i++) {
            JSONObject c;
            try {
                c = itemjsonarray.getJSONObject(i);
                ItemRemark IR = new ItemRemark();
                IR.setID(Integer.valueOf(c.getString(colID)));
                IR.setItemRemark(c.getString(colItemRemark));
                AddItemRemark(IR);

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                //	e.printStackTrace();
                System.out.println(e.getMessage());
            }

        }
    }


    public String LoadHeaderRemark(String Datalink, String tranid) {
        url = Datalink;
        String Remark = "";
        //String itemjsonarray = Json_class.getString(url
        //	+ "/Data/GetHeaderRemark?tranid"
        //	+ java.net.URLEncoder.encode(tranid)
        //	);
        Json_class jsonclass = new Json_class();
        String changecount = jsonclass.getString(url + "/Data/GetHeaderRemark?tranid=" + java.net.URLEncoder.encode(tranid));

        Remark = !LoginActivity.isUnicode ? Rabbit.uni2zg(changecount) : changecount;
        return Remark;
    }

    public void SaveHeaderRemark(String remark, String tranid) {

        String Remark = "";
        //JSONArray itemjsonarray = Json_class.getJson(url
        //	+ "/Data/SetHeaderRemark?tranid?remark");

        String itemjsonarray = Json_class.getString(getServiceURL()
                + "/Data/SetHeaderRemark?tranid="
                + java.net.URLEncoder.encode(tranid) + "&remark="
                //+ remark);
                + java.net.URLEncoder.encode(remark));


        if (itemjsonarray.length() > 0) {

        } else {

        }

    }

    //added by ZYP [06-02-2020]
    public String SendnodeToMonitor(String msg, String table_id) {

        String jsonarray = Json_class.getString(getServiceURL()
                + "/Data/NotetoMonitor?msg="
                + java.net.URLEncoder.encode(msg) + "&table_id="
                //+ remark);
                + java.net.URLEncoder.encode(table_id));


        return jsonarray;

    }

    //added by ZYP [06-02-2020]
    public String SaveLoginUser(String userId) {

        String jsonmsg = Json_class.getString(getServiceURL()
                + "/Data/SaveLoginUser?userid="
                + java.net.URLEncoder.encode(userId));

        return jsonmsg;
    }

    //added by ZYP [06-02-2020]
    public String getLoginUser(String userId) {

        String jsonmsg = Json_class.getString(getServiceURL()
                + "/Data/GetLoginUser?userid="
                + java.net.URLEncoder.encode(userId));

        return jsonmsg;


    }

    public void LoadSplitedVouchers(String Datalink, String Tableid) { //Added by Arkar Moe on [2017-06-08]
        url = Datalink;
/*		JSONArray itemjsonarray = Json_class.getJson(url
				+ "/Data/GetSplitedVouchers");*/
/*		if (itemjsonarray.length() > 0) {
			ClearTable(SplitedVouchers_table);
		}
*/
        JSONArray itemjsonarray = Json_class.getJson(url
                + "/Data/GetSplitedVouchers?table_name_id="
                + java.net.URLEncoder.encode(Tableid));

        if (itemjsonarray.length() > 0) {
            ClearTable(SplitedVouchers_table);
        }

        for (int i = 0; i < itemjsonarray.length(); i++) {
            JSONObject c;
            try {
                c = itemjsonarray.getJSONObject(i);
                SplitedVouchers SV = new SplitedVouchers();
                SV.set_tranid(Integer.valueOf(c.getString(colTranid)));
                SV.set_docid(c.getString(colDocid));
                SV.set_net_amount(c.getString(colNet_amount));
                SV.set_qty(c.getString(colQty));
                AddSplitedVouchers(SV);

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                //	e.printStackTrace();
                System.out.println(e.getMessage());
            }

        }
    }

    //Added by SMH on 29/05/2017
    public void LoadMealType(String Datalink) {
        url = Datalink;
        JSONArray itemjsonarray = Json_class.getJson(url
                + "/Data/GetMealType");
        //Added by KLM for UniZawgyi Auto Detect 23022022
        try {
            if (!LoginActivity.isUnicode) {
                itemjsonarray = new JSONArray(Rabbit.uni2zg(itemjsonarray.toString()));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (itemjsonarray.length() > 0) {
            ClearTable(MealType_Table);
        }

        for (int i = 0; i < itemjsonarray.length(); i++) {
            JSONObject c;
            try {
                c = itemjsonarray.getJSONObject(i);
                MealType MT = new MealType();
                MT.setMealTypeID(Integer.valueOf(c.getString(colMealTypeID)));
                MT.setMealType(c.getString(colMealType));
                AddMealType(MT);

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                //	e.printStackTrace();
                System.out.println(e.getMessage());
            }

        }
    }

    //Added by EKK
    public void LoadUserLog(String Datalink) {
        url = Datalink;
        JSONArray itemjsonarray = Json_class.getJson(url
                + "/Data/GetUserLogTable");
        //Added by KLM for UniZawgyi Auto Detect 23022022
        try {
            if (!LoginActivity.isUnicode) {
                itemjsonarray = new JSONArray(Rabbit.uni2zg(itemjsonarray.toString()));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (itemjsonarray.length() > 0) {
            ClearTable(UserLogTable);
        }

        for (int i = 0; i < itemjsonarray.length(); i++) {
            JSONObject c;
            try {
                c = itemjsonarray.getJSONObject(i);
                UserLog_Table ul = new UserLog_Table();
                ul.set_userid(Integer.valueOf(c.getString(colUser_id)));
                ul.set_table_name_id(Integer.valueOf(c.getString(colTableNameid)));
                AddUserLog(ul);

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                //	e.printStackTrace();
                System.out.println(e.getMessage());
            }

        }
    }

    //added by EKK on 13-12-2019
    public void LoadPromotionItem(String Datalink) {
        url = Datalink;
        JSONArray itemjsonarray = Json_class.getJson(url
                + "/Data/GetPromotion");
        //Added by KLM for UniZawgyi Auto Detect 23022022
        try {
            if (!LoginActivity.isUnicode) {
                itemjsonarray = new JSONArray(Rabbit.uni2zg(itemjsonarray.toString()));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Modified by KNO (15-11-2022) for inactive Promotion
        //if (itemjsonarray.length() > 0) {
        ClearTable(PromotionTable);
        //}

        for (int i = 0; i < itemjsonarray.length(); i++) {
            JSONObject c;
            try {
                c = itemjsonarray.getJSONObject(i);
                Promotion p = new Promotion();
                p.setCode(c.getString(colPromoCode));
                p.setUsr_code(c.getString(colPromoUsr_code));
                p.setDescription(c.getString(colPromoDescription));
                p.setPromotion(c.getString(colPromotion));

                AddPromoItem(p);

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                //	e.printStackTrace();
                System.out.println(e.getMessage());
            }

        }
    }

    //Added by ArkarMoe on [14/12/2016]
    public void LoadRooms(String Datalink) {
        url = Datalink;
        JSONArray itemjsonarray = Json_class.getJson(url
                + "/Data/GetRooms");
        ClearTable(Rooms_table);

        for (int i = 0; i < itemjsonarray.length(); i++) {
            JSONObject c;
            try {
                c = itemjsonarray.getJSONObject(i);
                Rooms RS = new Rooms();
                RS.setRef_TranID(Integer.valueOf(c.getString(colRef_TranID)));
                RS.setRoom_ID(Integer.valueOf(c.getString(colRoom_ID)));
                RS.setRoom_Code(String.valueOf(c.getString(colRoom_Code)));
                AddRooms(RS);

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                //	e.printStackTrace();
                System.out.println(e.getMessage());
            }

        }
    }


    public void SetLoadedDate(String Datalink) {
        url = Datalink;
        String result = Json_class.getString(url + "/Data/SetLoadedDate?DeviceID="
                + java.net.URLEncoder.encode(GlobalClass.GetTabletID()) + "&DeviceName=" + java.net.URLEncoder.encode(getDevice_Name()));

    }


    public void LoadDictionary(String Datalink) {
        url = Datalink;
        JSONArray itemjsonarray = Json_class.getJson(url
                + "/Data/GetDictionary");
        //Added by KLM for UniZawgyi Auto Detect 23022022
        try {
            if (!LoginActivity.isUnicode) {
                itemjsonarray = new JSONArray(Rabbit.uni2zg(itemjsonarray.toString()));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (itemjsonarray.length() > 0) {
            ClearTable(DictionaryTable);
        }

        for (int i = 0; i < itemjsonarray.length(); i++) {
            JSONObject c;
            try {
                c = itemjsonarray.getJSONObject(i);
                Dictionary T = new Dictionary();
                T.setsr(Integer.valueOf(c.getString("sr")));
                T.setLanguage1(c.getString(colEnglish));
                T.setLanguage2(c.getString(colMyanmar));
                T.setLanguage3(c.getString(colChinese)); //added by EKK on 10/07/2019
                AddDictionary(T);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void LoadPosUser(String Datalink) {
        url = Datalink;
        JSONArray itemjsonarray = Json_class.getJson(url + "/Data/GetPosUser");
        //Added by KLM for UniZawgyi Auto Detect 23022022
        try {
            if (!LoginActivity.isUnicode) {
                itemjsonarray = new JSONArray(Rabbit.uni2zg(itemjsonarray.toString()));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (itemjsonarray.length() > 0) {
            ClearTable(PosUserTable);
        }
        for (int i = 0; i < itemjsonarray.length(); i++) {
            JSONObject c;
            try {
                c = itemjsonarray.getJSONObject(i);
                PosUser A = new PosUser();
                A.setUserId(Integer.parseInt(c.getString(coluserid)));
                A.setShort(c.getString(colusershort));
                A.setName(c.getString(colusername));
                A.setPassword(c.getString(colpassword));
                A.setAlluser(Boolean.parseBoolean(c.getString(colalluser)));
                A.setuse_touchscreen(Boolean.parseBoolean(c.getString(coluse_touchscreen)));
                A.setallow_itemcancel(Boolean.parseBoolean(c.getString(colallow_itemcancel)));
                A.set_allow_edit_after_insert(Boolean.parseBoolean(c.getString(colallow_edit_after_insert)));
                A.set_btnBill(Boolean.parseBoolean(c.getString(colbtnBill)));
                A.set_btnClearAll(Boolean.parseBoolean(c.getString(colbtnClearAll)));
                A.set_btnDetail(Boolean.parseBoolean(c.getString(colbtnDetail)));
                A.set_btnOthers(Boolean.parseBoolean(c.getString(colbtnOthers)));
                A.set_btnSplit(Boolean.parseBoolean(c.getString(colbtnSplit)));
                A.set_cashierprinterid(Integer.parseInt(c.getString(colcashierprinterid)));
                A.set_btnPrintBill(Boolean.parseBoolean(c.getString(colbtnPrintBill)));
                A.set_billnotprint(Boolean.parseBoolean(c.getString(colbillnotprint)));
                A.set_select_customer(Boolean.parseBoolean(c.getString(colSelectCustomer))); //added by EKK on 15-01-2020
                A.set_create_customer(Boolean.parseBoolean(c.getString(colCreateCustomer))); //added by EKK on 15-01-2020
                A.set_allow_itemtransfer(Boolean.parseBoolean(c.getString(colallow_item_transfer)));
                A.set_use_menuonly(Boolean.parseBoolean(c.getString(coluse_menuonly))); //added by EKK on 18-09-2020
                A.set_use_foodtruck(Boolean.parseBoolean(c.getString(coluse_foodtruck))); //added by ZYP 25-01-2021
                A.set_use_foodtrucklocation(Boolean.parseBoolean(c.getString(coluse_foodtrucklocation))); //added by ZYP 24-02-2021
                AddPosUser(A);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void LoadSalesMen(String Datalink) {
        url = Datalink;
        JSONArray itemjsonarray = Json_class.getJson(url + "/Data/GetSalesMen");
        //Added by KLM for UniZawgyi Auto Detect 23022022
        try {
            if (!LoginActivity.isUnicode) {
                itemjsonarray = new JSONArray(Rabbit.uni2zg(itemjsonarray.toString()));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (itemjsonarray.length() > 0) {
            ClearTable(SalemenTable);
        }
        for (int i = 0; i < itemjsonarray.length(); i++) {
            JSONObject c;
            try {
                c = itemjsonarray.getJSONObject(i);

                Salesmen A = new Salesmen();
                A.setsalesmen_id(Integer.parseInt(c.getString(colsalesmen_id)));
                A.setsalesmen_name(c.getString(colsalesmen_name));
                A.setshortcode(c.getString(colshort));
                A.setposuserid(c.getString(colposuserid));
                AddSalesmen(A);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public String LoadServerDate(String Datalink) {
        url = Datalink;
        String datetime = Json_class.getString(url + "/Data/GetServerDate");

        return datetime;
    }

    public void LoadItemImage(String Datalink, String usr_code) {
        url = Datalink;
        String itemphoto = Json_class.getString(url
                + "/Data/GetItemPhoto?usr_code="
                + java.net.URLEncoder.encode(usr_code));

        if (!itemphoto.equals("null")) {
            byte[] decodedString = Base64.decode(itemphoto, 0);
            AddItemsPhoto(usr_code, decodedString);
        }

    }

    public void LoadItemUnitCode(String Datalink, String usr_code) {
        url = Datalink;

        JSONArray jsonarray = Json_class.getJson(url
                + "/Data/GetUnitCode?usr_code="
                + java.net.URLEncoder.encode(usr_code));

        try {
            if (!LoginActivity.isUnicode) {
                jsonarray = new JSONArray(Rabbit.uni2zg(jsonarray.toString()));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (jsonarray.length() > 0) {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("Delete from  " + UnitCodeTable + " where " + colusrcode
                    + " = '" + usr_code + "'");
            db.close();
            for (int i = 0; i < jsonarray.length(); i++) {
                try {
                    JSONObject c = jsonarray.getJSONObject(i);
                    db = this.getWritableDatabase();
                    ContentValues cv = new ContentValues();
                    cv.put(colcode, c.getString("code"));
                    cv.put(colusrcode, c.getString("usr_code"));
                    cv.put(colunittype, c.getString("unit_type"));
                    cv.put(colfromunitqty, c.getString("from_unit_qty"));
                    cv.put(colunitqty, c.getString("unit_qty"));
                    cv.put(colunit, c.getString("unit"));
                    cv.put(colunitname, c.getString("unitname"));
                    cv.put(colshortname, c.getString("shortname"));
                    cv.put(colsaleprice, c.getString("sale_price"));
                    cv.put(colsmallest_unit_qty,
                            c.getString("smallest_unit_qty"));
                    db.insert(UnitCodeTable, null, cv);
                    db.close();
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

    }

    public void LoadModifierGroup(String Datalink) {
        url = Datalink;
        JSONArray itemjsonarray = Json_class.getJson(url
                + "/Data/GetModifierGroup");
        //Added by KLM for UniZawgyi Auto Detect 23022022
        try {
            if (!LoginActivity.isUnicode) {
                itemjsonarray = new JSONArray(Rabbit.uni2zg(itemjsonarray.toString()));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (itemjsonarray.length() > 0) {
            ClearTable(ModifierGroupTable);
        }

        for (int i = 0; i < itemjsonarray.length(); i++) {
            JSONObject c;
            try {
                c = itemjsonarray.getJSONObject(i);
                ModifierGroupObj T = new ModifierGroupObj();
                T.setmodifiergroupid(Integer.valueOf(c
                        .getString(colmodifiedgroupid)));
                T.setname(c.getString(colmodifiername));
                T.setshort(c.getString(colmodifiershort));
                AddModifierGroup(T);

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void LoadCustomer(String Datalink) {
        url = Datalink;
        JSONArray itemjsonarray = Json_class.getJson(url + "/Data/GetCustomer");
        //Added by KLM for UniZawgyi Auto Detect 23022022
        try {
            if (!LoginActivity.isUnicode) {
                itemjsonarray = new JSONArray(Rabbit. uni2zg(itemjsonarray.toString()));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (itemjsonarray.length() > 0) {
            ClearTable(CustomerTable);
        }

        for (int i = 0; i < itemjsonarray.length(); i++) {
            JSONObject c;
            try {
                c = itemjsonarray.getJSONObject(i);
                CustomerObj T = new CustomerObj();
                T.setcustomerid(Integer.valueOf(c.getString(colcustomerid)));
                T.setcustomercode(c.getString(colcustomercode));
                T.setcustomername(c.getString(colcustomername));
                T.setcustomercredit(c.getString(colcustomercredit));
                T.set_customer_pricelevel(Integer.valueOf(c.getString(colcustomer_pricelevel)));//added WHM [2020-01-27] MDY2-200141
                AddCustomer(T);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void LoadItemFirstTime(String Datalink, String RowNumber) {
        //Gson g=new Gson();
        url = Datalink;
        String timelog = getLastLoadingTimeLog();
        if (timelog.equals("")) {
            timelog = "01/01/2000";
        }
        JSONArray itemjsonarray = Json_class.getJson(url
                + "/Data/GetItemFirstTime?rownumber="
                + java.net.URLEncoder.encode(RowNumber) + "&lastdowndate="
                + java.net.URLEncoder.encode(timelog));
//		added by KLM for UniZawgyi Auto Detect 23022022
        try {
            if (!LoginActivity.isUnicode) {
                itemjsonarray = new JSONArray(Rabbit.uni2zg(itemjsonarray.toString()));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < itemjsonarray.length(); i++) {
            JSONObject c;
            try {
                c = itemjsonarray.getJSONObject(i);
//				String  unicodeString=Rabbit.uni2zg(itemjsonarray.getJSONObject(i).toString());
                //c=new Gson().fromJson(unicodeString, JsonObject.class);
//				c=unicodeString;
                ItemsObj item = new ItemsObj();
                item.setcode(c.getString(colcode));
                item.setusr_code(c.getString(colusrcode));
                item.setdescription(c.getString(coldescription));
                item.setsale_price(c.getString(colsaleprice));
                item.setsale_price1(c.getString(colsaleprice1));
                item.setsale_price2(c.getString(colsaleprice2));
                item.setsale_price3(c.getString(colsaleprice3));
                item.setcaldiscount(c.getString(colcaldiscount));
                item.setgtax(c.getString(colcalgtax));
                item.setstax(c.getString(colcalstax));
                item.setcategoryid(c.getString(colcategoryid));
                item.setcategoryname(c.getString(colcategoryname));
                item.setclassid(c.getString(colclassid));
                item.setclassname(c.getString(colclassname));
                item.setmodifiergroupid(c.getString(colmodifiedgroupid));
                item.setremark(c.getString(colremark));
                item.setcolorRGB(c.getString(colcolorRGB));
                item.setitemcolorRGB(c.getString(colitemcolorRGB));
                item.setunit(c.getString(colunit));
                item.setcategorysortcode(c.getString(colcategorysortcode));
                item.setclasssortcode(c.getString(colclasssortcode));
                item.setissetmenu(c.getString(colIsSetMenu));
                item.setInactive(c.getString(coltmpInactive));// added by WaiWL
                // on 06/08/2015
                item.setvalueitem(c.getString(colvalueitem));// added by WaiWL
                item.setMealType1(c.getString(colMealType1));
                item.setMealType2(c.getString(colMealType2));
                item.setMealType3(c.getString(colMealType3));
                item.setdescription2(c.getString(coldescription2));
                item.setdescription3(c.getString(coldescription3));
                item.setParcel_Price(c.getString(colParcel_Price));
                item.setSortid(c.getString(colsortid));
                item.setOrg_curr(c.getString(colOrgCurr)); //added by EKK on 24-02-2020
                item.setCategory2(c.getString(colCategory2));//added by KLM on 07062022
                // byte[] decodedString = Base64.decode(c.getString(colphoto),
                // 0);
                //!LoginActivity.isUnicode = false;    //added by ZYP [overwrite unicode] [06-01-2020]

                AddItems(item.getcode(), item.getusr_code(),
                        item.getdescription(), item.getsale_price(),
                        item.getsale_price1(), item.getsale_price2(),
                        item.getsale_price3(), item.getcaldiscount(),
                        item.getgtax(), item.getstax(), item.getcategoryid(),
                        item.getcategoryname(), item.getclassid(),
                        item.getclassname(), item.getmodifiergroupid(),
                        item.getremark(), null, item.getcolorRGB(), item.getitemcolorRGB(),
                        item.getunit(), item.getcategorysortcode(),
                        item.getclasssortcode(), item.getissetmenu(),
                        item.getInactive(), item.getvalueitem(), item.getMealType1(), item.getMealType2(), item.getMealType3(), item.getdescription2(), item.getdescription3(), item.getParcel_Price(), item.getSortid(), item.getOrg_curr()
                        , item.getCategory2()); //modified by EKK on24-02-2020
                // LoadItemImage(url, item.getusr_code());
                LoadItemUnitCode(url, item.getusr_code());
                // AddItemsPhoto(item.getusr_code(), decodedString);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void LoadItemSecondTime(String Datalink) {
        url = Datalink;
        String timelog = getLastLoadingTimeLog();
        if (timelog.equals("")) {
            timelog = "01/01/2000";
        }

        JSONArray itemjsonarray = Json_class.getJson(url
                + "/Data/GetItemSecondTime?DeviceName="
                + java.net.URLEncoder.encode(getDevice_Name()));
        //Added by KLM for UniZawgyi Auto Detect 23022022
        try {
            if (!LoginActivity.isUnicode) {
                itemjsonarray = new JSONArray(Rabbit.uni2zg(itemjsonarray.toString()));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (itemjsonarray.length() > 0) {

            for (int i = 0; i < itemjsonarray.length(); i++) {
                JSONObject c;
                try {

                    c = itemjsonarray.getJSONObject(i);
                    if (c.getBoolean(coldeleted) == true) {
                        DeleteItemTalbe(c.getString(colcode));
                    } else {
                        ItemsObj item = new ItemsObj();
                        item.setcode(c.getString(colcode));
                        item.setusr_code(c.getString(colusrcode));
                        item.setdescription(c.getString(coldescription));
                        item.setsale_price(c.getString(colsaleprice));
                        item.setsale_price1(c.getString(colsaleprice1));
                        item.setsale_price2(c.getString(colsaleprice2));
                        item.setsale_price3(c.getString(colsaleprice3));
                        item.setcaldiscount(c.getString(colcaldiscount));
                        item.setgtax(c.getString(colcalgtax));
                        item.setstax(c.getString(colcalstax));
                        item.setcategoryid(c.getString(colcategoryid));
                        item.setcategoryname(c.getString(colcategoryname));
                        item.setclassid(c.getString(colclassid));
                        item.setclassname(c.getString(colclassname));
                        item.setmodifiergroupid(c.getString(colmodifiedgroupid));
                        item.setremark(c.getString(colremark));
                        item.setcolorRGB(c.getString(colcolorRGB));
                        item.setitemcolorRGB(c.getString(colitemcolorRGB));
                        item.setunit(c.getString(colunit));
                        item.setcategorysortcode(c.getString(colcategorysortcode));
                        item.setclasssortcode(c.getString(colclasssortcode));
                        item.setissetmenu(c.getString(colIsSetMenu));
                        item.setInactive(c.getString(coltmpInactive));// added by
                        // WaiWL on
                        // 06/08/2015
                        item.setvalueitem(c.getString(colvalueitem));// added byb
                        item.setMealType1(c.getString(colMealType1));    //added by SMH on 25/05/2017										// WaiWL on
                        item.setMealType2(c.getString(colMealType2));
                        item.setMealType3(c.getString(colMealType3));
                        item.setdescription2(c.getString(coldescription2));
                        item.setdescription3(c.getString(coldescription3));
                        item.setParcel_Price(c.getString(colParcel_Price));
                        item.setSortid(c.getString(colsortid));
                        item.setOrg_curr(c.getString(colOrgCurr)); //added by EKK on 24-02-2020
                        item.setCategory2(c.getString(colCategory2));//added by KLM on 07062022
                        // 11/08/2015
                        // byte[] decodedString =
                        // Base64.decode(c.getString(colphoto), 0);

//						!LoginActivity.isUnicode = false;    //added by ZYP [overwrite unicode] [06-01-2020]

                        AddItems(item.getcode(), item.getusr_code(),
                                item.getdescription(), item.getsale_price(),
                                item.getsale_price1(), item.getsale_price2(),
                                item.getsale_price3(), item.getcaldiscount(),
                                item.getgtax(), item.getstax(),
                                item.getcategoryid(), item.getcategoryname(),
                                item.getclassid(), item.getclassname(),
                                item.getmodifiergroupid(), item.getremark(), null,
                                item.getcolorRGB(), item.getitemcolorRGB(), item.getunit(),
                                item.getcategorysortcode(),
                                item.getclasssortcode(), item.getissetmenu(),
                                item.getInactive(), item.getvalueitem(), item.getMealType1(), item.getMealType2(), item.getMealType3(), item.getdescription2()
                                , item.getdescription(), item.getParcel_Price(), item.getSortid(), item.getOrg_curr(), item.getCategory2()); //modified by EKK on 24-02-2020
                        // LoadItemImage(url, item.getusr_code());
                        LoadItemUnitCode(url, item.getusr_code());
                        // AddItemsPhoto(item.getusr_code(), decodedString);
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    // added by WaiWL on 27/05/2015
    public void LoadSpecialMenu(String Datalink) {
        url = Datalink;
        JSONArray itemjsonarray = Json_class.getJson(url
                + "/Data/GetSpecialMenu");

        ClearTable(Specialmenu_Table);

        if (itemjsonarray.length() > 0) {
            for (int i = 0; i < itemjsonarray.length(); i++) {
                JSONObject c;
                try {
                    c = itemjsonarray.getJSONObject(i);
                    SpecialMenuObj item = new SpecialMenuObj();

                    item.setMenuID(c.getInt(colMenuID));
                    item.setMenuName(c.getString(colMenuName));
                    item.setRemark(c.getString(colspRemark));
                    item.setActiveMenus(c.getBoolean(colActiveMenus));
                    item.setRmt_Copy(c.getBoolean(colRmt_Copy));
                    item.setRmt_Branch(c.getString(colRmt_Branch));
                    item.setmon(c.getBoolean(colmon));
                    item.settue(c.getBoolean(coltue));
                    item.setwed(c.getBoolean(colwed));
                    item.setthu(c.getBoolean(colthu));
                    item.setfri(c.getBoolean(colfri));
                    item.setsat(c.getBoolean(colsat));
                    item.setsun(c.getBoolean(colsun));
                    AddSpecialMenu(item.getMenuID(), item.getMenuName(),
                            item.getRemark(), item.getActiveMenus(),
                            item.getRmt_Copy(), item.getRmt_Branch(),
                            item.getmon(), item.gettue(), item.getwed(),
                            item.getthu(), item.getfri(), item.getsat(),
                            item.getsun());
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    // ///////////////

    // added by WaiWL on 27/05/2015
    public void LoadSpecialMenu_code(String Datalink) {
        url = Datalink;
        JSONArray itemjsonarray = Json_class.getJson(url
                + "/Data/GetSpecialMenu_code");
        //Added by KLM for UniZawgyi Auto Detect 23022022
        try {
            if (!LoginActivity.isUnicode) {
                itemjsonarray = new JSONArray(Rabbit.uni2zg(itemjsonarray.toString()));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        ClearTable(Specialmenu_code_Table);

        if (itemjsonarray.length() > 0) {
            for (int i = 0; i < itemjsonarray.length(); i++) {
                JSONObject c;
                try {
                    c = itemjsonarray.getJSONObject(i);
                    Specialmenu_codeObj item = new Specialmenu_codeObj();

                    item.setMenuID(c.getInt(colSMenuID));
                    item.setCode(c.getInt(colSCode));
                    item.setUsr_code(c.getString(colSUsr_code));

                    AddSpecialMenu_code(item.getMenuID(), item.getCode(),
                            item.getUsr_code());
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    // ///////////////

    // added by WaiWL on 15/09/2015
    public void Load_CustomerCountSetUp(String Datalink) {
        url = Datalink;
        JSONArray custcountSetuparray = Json_class.getJson(url
                + "/Data/GetCustomerCountSetup");
        //Added by KLM for UniZawgyi Auto Detect 23022022
        try {
            if (!LoginActivity.isUnicode) {
                custcountSetuparray = new JSONArray(Rabbit.uni2zg(custcountSetuparray.toString()));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        ClearTable(CustomerCountSetUp);

        if (custcountSetuparray.length() > 0) {
            for (int i = 0; i < custcountSetuparray.length(); i++) {
                JSONObject c;
                try {
                    c = custcountSetuparray.getJSONObject(i);

                    SQLiteDatabase db = this.getWritableDatabase();
                    ContentValues cv = new ContentValues();
                    cv.put(colPrivate, c.getString(colPrivate));
                    cv.put(colBusiness, c.getString(colBusiness));
                    cv.put(colTravel, c.getString(colTravel));
                    cv.put(colMale, c.getString(colMale));
                    cv.put(colFemale, c.getString(colFemale));
                    cv.put(colAdult, c.getString(colAdult));
                    cv.put(colChild, c.getString(colChild));
                    cv.put(colWestern, c.getString(colWestern));
                    cv.put(colAsian, c.getString(colAsian));
                    cv.put(colCustCountMyanmar, c.getString(colCustCountMyanmar));

                    db.execSQL("Delete from  " + CustomerCountSetUp);
                    db.insert(CustomerCountSetUp, null, cv);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // ///////
    public void LoadSetMenuItem(String Datalink) {
        url = Datalink;
        JSONArray itemjsonarray = Json_class.getJson(url
                + "/Data/GetSetmenuitem");
        //Added by KLM for UniZawgyi Auto Detect 23022022
        try {
            if (!LoginActivity.isUnicode) {
                itemjsonarray = new JSONArray(Rabbit.uni2zg(itemjsonarray.toString()));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        ClearTable(Item_ListTable);
        if (itemjsonarray.length() > 0) {
            for (int i = 0; i < itemjsonarray.length(); i++) {
                JSONObject c;
                try {
                    c = itemjsonarray.getJSONObject(i);
                    SetmenuitemObj item = new SetmenuitemObj();
                    item.setmaincode(c.getString(colmaincode));
                    item.setcode(c.getString(colcode));
                    item.setqty(c.getString(colqty));
                    item.setunitqty(c.getString(colunit_qty));
                    item.setunittype(c.getString(colunit_type));
                    item.setMax_price(c.getString(colmax_price));
                    item.setCategory2(c.getString(colCategory2));
                    AddSetMenuItems(item.getmaincode(), item.getcode(),
                            item.getqty(), item.getunitqty(),
                            item.getunittype(), item.getMax_price(), item.getCategory2());
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    public void LoadSetMenuChagedItem(String Datalink) {
        url = Datalink;
        JSONArray itemjsonarray = Json_class.getJson(url
                + "/Data/GetSetmenuChangeditem");
        //Added by KLM for UniZawgyi Auto Detect 23022022
        try {
            if (!LoginActivity.isUnicode) {
                itemjsonarray = new JSONArray(Rabbit.uni2zg(itemjsonarray.toString()));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (itemjsonarray.length() > 0) {
            for (int i = 0; i < itemjsonarray.length(); i++) {
                JSONObject c;
                try {
                    c = itemjsonarray.getJSONObject(i);
                    SetmenuChangeditemObj item = new SetmenuChangeditemObj();
                    item.setBrandmain_code(c.getString(colbrandmaincode));
                    item.setBranditemcode(c.getString(colbranditemcode));
                    item.setBrandcode(c.getString(colbrandcode));
                    item.setBrandqty(c.getString(colbrandqty));
                    item.setBrandunit_qty(c.getString(colbrandunit_qty));
                    item.setBrandunit_type(c.getString(colbrandunit_type));
                    item.setBrand(c.getString(colbrand));
                    AddSetMenuChangedItems(item.getBrandmain_code(), item.getBranditemcode(), item.getBrandcode(),
                            item.getBrandqty(), item.getBrandunit_qty(),
                            item.getBrandunit_type(), item.getBrand());
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }


    public String LoadItemCount(String Datalink) {
        url = Datalink;
        String timelog = getLastLoadingTimeLog();
        if (timelog.equals("")) {
            timelog = "01/01/2000";
        }
        String ItemCount = Json_class.getString(url
                + "/Data/GetItemCount?lastdowndate="
                + java.net.URLEncoder.encode(timelog));
        return ItemCount == "" ? "0" : ItemCount;
    }

    public String DeleteItemBysrno(String Datalink, String tranid, String srno) {
        url = Datalink;
        return Json_class.getString(url + "/Data/DeleteSaleItemBysrno?tranid="
                + java.net.URLEncoder.encode(tranid) + "&srno="
                + java.net.URLEncoder.encode(srno));
    }

    // added by WaiWL on 24/07/2015
    public void UpdateFinishedItem(String Datalink, String tranid, String srno,
                                   String isFinishedItem) {
        url = Datalink;
        Json_class.getString(url + "/Data/UpdateFinishedItem?tranid="
                + java.net.URLEncoder.encode(tranid) + "&srno="
                + java.net.URLEncoder.encode(srno) + "&isFinishedItem="
                + java.net.URLEncoder.encode(isFinishedItem));
    }

    //Added by ArkarMoe on [15/12/2016]
    public void UpdateRoomInformation(String Datalink, String tranid, String Ref_TranID, String Room_ID,
                                      String Room_Code, String IsRoom) {
        url = Datalink;
        Json_class.getString(url + "/Data/UpdateRoomInformation?tranid="
                + java.net.URLEncoder.encode(tranid) + "&Ref_TranID="
                + java.net.URLEncoder.encode(Ref_TranID) + "&Room_ID="
                + java.net.URLEncoder.encode(Room_ID) + "&Room_Code="
                + java.net.URLEncoder.encode(Room_Code) + "&IsRoom="
                + java.net.URLEncoder.encode(IsRoom)
        );
    }

    // ////////////
    // ////////////
    public String LoadModifierItemCount(String Datalink) {
        url = Datalink;
        String timelog = getLastLoadingTimeLog();
        if (timelog.equals("")) {
            timelog = "01/01/2000";
        }

        String ItemCount = Json_class.getString(url
                + "/Data/GetModifierItemcount?lastdowndate="
                + java.net.URLEncoder.encode(timelog));
        return ItemCount == "" ? "0" : ItemCount;
    }

    public void LoadModifiedItemFirstTime(String Datalink, String RowNumber) {
        url = Datalink;
        String timelog = getLastLoadingTimeLog();
        if (timelog.equals("")) {
            timelog = "01/01/2000";
        }
        JSONArray itemjsonarray = Json_class.getJson(url
                + "/Data/GetModifierItemFirstTime?Rownumber="
                + java.net.URLEncoder.encode(RowNumber) + "&lastdowndate="
                + java.net.URLEncoder.encode(timelog));
        //Added by KLM for UniZawgyi Auto Detect 23022022
        try {
            if (!LoginActivity.isUnicode) {
                itemjsonarray = new JSONArray(Rabbit.uni2zg(itemjsonarray.toString()));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (itemjsonarray.length() > 0) {
            for (int i = 0; i < itemjsonarray.length(); i++) {
                JSONObject c;
                try {
                    c = itemjsonarray.getJSONObject(i);
                    ItemsObj item = new ItemsObj();
                    item.setcode(c.getString(colcode));
                    item.setusr_code(c.getString(colusrcode));
                    item.setdescription(c.getString(coldescription));
                    item.setsale_price(c.getString(colsaleprice));
                    item.setsale_price1(c.getString(colsaleprice1));
                    item.setsale_price2(c.getString(colsaleprice2));
                    item.setsale_price3(c.getString(colsaleprice3));
                    item.setcaldiscount(c.getString(colcaldiscount));
                    item.setgtax(c.getString(colcalgtax));
                    item.setstax(c.getString(colcalstax));
                    item.setcategoryid(c.getString(colcategoryid));
                    item.setcategoryname(c.getString(colcategoryname));
                    item.setclassid(c.getString(colclassid));
                    item.setclassname(c.getString(colclassname));
                    item.setmodifiergroupid(c.getString(colmodifiedgroupid));
                    item.setInactive(c.getString(coltmpInactive));// added by
                    // WaiWL on
                    item.setdescription2(c.getString(coldescription2));
                    item.setdescription3(c.getString(coldescription3));

                    AddModifierItems(item.getcode(), item.getusr_code(),
                            item.getdescription(), item.getsale_price(),
                            item.getsale_price1(), item.getsale_price2(),
                            item.getsale_price3(), item.getcaldiscount(),
                            item.getgtax(), item.getstax(),
                            item.getcategoryid(), item.getcategoryname(),
                            item.getclassid(), item.getclassname(),
                            item.getmodifiergroupid(), item.getInactive(), item.getdescription2(), item.getdescription3());

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }

        // String sss = (url +
        // "/Data/GetModifierItem?start="+java.net.URLEncoder.encode(Start)+"&end="+java.net.URLEncoder.encode(End)+"&lastdowndate="+java.net.URLEncoder.encode(timelog));

    }

    public void LoadModifiedItemSecondTime(String Datalink) {
        url = Datalink;
        String timelog = getLastLoadingTimeLog();
        if (timelog.equals("")) {
            timelog = "01/01/2000";
        }
        JSONArray itemjsonarray = Json_class.getJson(url
                + "/Data/GetModifierItemSecondTime?DeviceName="
                + java.net.URLEncoder.encode(getDevice_Name()));
        //Added by KLM for UniZawgyi Auto Detect 23022022
        try {
            if (!LoginActivity.isUnicode) {
                itemjsonarray = new JSONArray(Rabbit.uni2zg(itemjsonarray.toString()));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (itemjsonarray.length() > 0) {
            for (int i = 0; i < itemjsonarray.length(); i++) {
                JSONObject c;
                try {
                    c = itemjsonarray.getJSONObject(i);
                    ItemsObj item = new ItemsObj();
                    item.setcode(c.getString(colcode));
                    item.setusr_code(c.getString(colusrcode));
                    item.setdescription(c.getString(coldescription));
                    item.setsale_price(c.getString(colsaleprice));
                    item.setsale_price1(c.getString(colsaleprice1));
                    item.setsale_price2(c.getString(colsaleprice2));
                    item.setsale_price3(c.getString(colsaleprice3));
                    item.setcaldiscount(c.getString(colcaldiscount));
                    item.setgtax(c.getString(colcalgtax));
                    item.setstax(c.getString(colcalstax));
                    item.setcategoryid(c.getString(colcategoryid));
                    item.setcategoryname(c.getString(colcategoryname));
                    item.setclassid(c.getString(colclassid));
                    item.setclassname(c.getString(colclassname));
                    item.setmodifiergroupid(c.getString(colmodifiedgroupid));
                    item.setInactive(c.getString(coltmpInactive));// added by
                    item.setdescription2(c.getString(coldescription2));
                    item.setdescription3(c.getString(coldescription3));

//					if (!LoginActivity.isUnicode) !LoginActivity.isUnicode = false;

                    AddModifierItems(item.getcode(), item.getusr_code(),
                            item.getdescription(), item.getsale_price(),
                            item.getsale_price1(), item.getsale_price2(),
                            item.getsale_price3(), item.getcaldiscount(),
                            item.getgtax(), item.getstax(),
                            item.getcategoryid(), item.getcategoryname(),
                            item.getclassid(), item.getclassname(),
                            item.getmodifiergroupid(), item.getInactive(), item.getdescription2(), item.getdescription3());

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }

        // String sss = (url +
        // "/Data/GetModifierItem?start="+java.net.URLEncoder.encode(Start)+"&end="+java.net.URLEncoder.encode(End)+"&lastdowndate="+java.net.URLEncoder.encode(timelog));

    }

    // added by WaiWL on 15/09/2015
    Object[] getCustomerCountSetup() {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT " + colPrivate + "," + colBusiness + ","
                + colTravel + "," + colMale + "," + colFemale + "," + colAdult + "," + colChild + ","
                + colWestern + "," + colAsian + "," + colCustCountMyanmar
                + " FROM " + CustomerCountSetUp;

        Cursor cursor = db.rawQuery(selectQuery, null);
        Object[] objArray = new Object[10];
        try {
            if (cursor.moveToFirst()) {
                do {
                    objArray[0] = cursor.getString(0);
                    objArray[1] = cursor.getString(1);
                    objArray[2] = cursor.getString(2);
                    objArray[3] = cursor.getString(3);
                    objArray[4] = cursor.getString(4);
                    objArray[5] = cursor.getString(5);
                    objArray[6] = cursor.getString(6);
                    objArray[7] = cursor.getString(7);
                    objArray[8] = cursor.getString(8);
                    objArray[9] = cursor.getString(9);
                } while (cursor.moveToNext());
            }
            return objArray;
        } finally {
            cursor.close();
        }
    }

    public List<Note> getnotelist(int tranid) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<Note> list = new ArrayList<Note>();
        String sSql = "SELECT tranid,sr,userid,Date,Notes FROM %s where tranid="
                + tranid + "";
        Cursor cursor = db.rawQuery(String.format(sSql, Note_table), null);

        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Note NoteObj = new Note();
                    NoteObj.setTranid(Integer.valueOf(cursor.getString(0)));
                    NoteObj.setsr(Integer.valueOf(cursor.getString(1)));
                    NoteObj.setuserid(Integer.valueOf(cursor.getString(2)));
                    NoteObj.setDate(cursor.getString(3));
                    NoteObj.setNotes(cursor.getString(4));

                    // Adding tableobj to list
                    list.add(NoteObj);
                } while (cursor.moveToNext());
            }
            // return contact list
            return list;

        } finally {
            cursor.close();
        }

    }

    public List<Setuser> getuserlist(int usetouchscreen) {

        SQLiteDatabase db = this.getWritableDatabase();
        List<Setuser> list = new ArrayList<Setuser>();
        String sSql = "SELECT setuserid,setusername,use_tabletuser FROM %s where %s = %s ";
        Cursor cursor = db.rawQuery(String.format(sSql, setuser_table,
                col_use_tabletuser, usetouchscreen), null);

        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Setuser SetUserobj = new Setuser();
                    SetUserobj
                            .setsetuserid(Integer.valueOf(cursor.getString(0)));
                    SetUserobj.setsetusername(cursor.getString(1));
                    SetUserobj.setuse_tabletuser(Integer.valueOf(cursor
                            .getString(2)));

                    // Adding tableobj to list
                    list.add(SetUserobj);
                } while (cursor.moveToNext());
            }
            // return contact list
            return list;

        } finally {
            cursor.close();
        }

    }

    //Added by SMH
    public List<ItemRemark> getItemRemarks() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<ItemRemark> list = new ArrayList<ItemRemark>();

        String sSql = "SELECT  ID,ItemRemark FROM %s";

        Cursor cursor = db.rawQuery(String.format(sSql, ItemRemark_table), null);

        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {

                    ItemRemark ItemRemarkobj = new ItemRemark();
                    ItemRemarkobj.setID(Integer.valueOf(cursor.getString(0)));
                    ItemRemarkobj.setItemRemark(cursor.getString(1));
                    // Adding tableobj to list
                    list.add(ItemRemarkobj);
                } while (cursor.moveToNext());
            }
            // return contact list
            return list;

        } finally {
            cursor.close();
        }
    }

    //Added by Arkar Moe on [2017-06-08]
    public List<SplitedVouchers> getSplitedVouchers() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<SplitedVouchers> list = new ArrayList<SplitedVouchers>();

        String sSql = "SELECT  tranid,docid,net_amount,qty FROM %s";

        Cursor cursor = db.rawQuery(String.format(sSql, SplitedVouchers_table), null);

        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    SplitedVouchers SplitedVouchersobj = new SplitedVouchers();
                    SplitedVouchersobj.set_tranid(Integer.valueOf(cursor.getString(0)));
                    SplitedVouchersobj.set_docid(cursor.getString(1));
                    SplitedVouchersobj.set_net_amount(cursor.getString(2));
                    SplitedVouchersobj.set_qty(cursor.getString(3));

                    list.add(SplitedVouchersobj);
                } while (cursor.moveToNext());
            }
            // return contact list
            return list;

        } finally {
            cursor.close();
        }
    }

    //Added by SMH on 30/05/2017
    public List<MealType> getMealType() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<MealType> list = new ArrayList<MealType>();

        String sSql = "SELECT  MealTypeID,MealType FROM %s";

        Cursor cursor = db.rawQuery(String.format(sSql, MealType_Table), null);

        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {

                    MealType MealTypeobj = new MealType();
                    MealTypeobj.setMealTypeID(Integer.valueOf(cursor.getString(0)));
                    MealTypeobj.setMealType(cursor.getString(1));
                    // Adding tableobj to list
                    list.add(MealTypeobj);
                } while (cursor.moveToNext());
            }
            // return contact list
            return list;

        } finally {
            cursor.close();
        }
    }

    //Added by ArkarMoe on [14/12/2016]
    public List<Rooms> getRooms() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<Rooms> list = new ArrayList<Rooms>();

        String sSql = "SELECT  Ref_TranID,Room_ID,Room_Code FROM %s";

        Cursor cursor = db.rawQuery(String.format(sSql, Rooms_table), null);

        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Rooms RoomsObj = new Rooms();
                    RoomsObj.setRef_TranID(Integer.valueOf(cursor.getString(0)));
                    RoomsObj.setRoom_ID(Integer.valueOf(cursor.getString(1)));
                    RoomsObj.setRoom_Code(cursor.getString(2));
                    list.add(RoomsObj);
                } while (cursor.moveToNext());
            }
            // return contact list
            return list;

        } finally {
            cursor.close();
        }
    }
    ///////

    Boolean updatetabletuser(int userid) {

        SQLiteDatabase db2 = this.getWritableDatabase();
        // try {
        String returnvalue = "";
        String sSql = "SELECT setuserid,setusername,use_tabletuser FROM %s where %s = %s ";
        Cursor cursor = db2.rawQuery(
                String.format(sSql, setuser_table, col_userid, userid), null);

        try {

            if (cursor.moveToFirst()) {

                db2.execSQL("Update " + setuser_table + " Set "
                        + coluse_tabletuser + " = 1" + " where " + col_userid
                        + " = " + userid + "");

                db2.close();
                returnvalue = "true";
            } else {

                if (InsertSetUser(userid) == true) {
                    returnvalue = "true";
                }

            }

        } finally {
            cursor.close();

        }
        if (returnvalue == "true") {
            return true;
        } else {
            return false;
        }

        // } catch (Exception e) {
        // TODO: handle exception
        // return false;
        // }

    }

    Boolean getusetablefilter() {
        SQLiteDatabase db = this.getWritableDatabase();
        String Sql = "SELECT Setting_Value FROM %s where %s = %s and %s = %s";
        Cursor cursor = db.rawQuery(String.format(Sql, AppSetting_table,
                colSetting_Name, "'use_tabletuser_filter'", colSetting_Value,
                "'Y'"), null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    return true;
                } while (cursor.moveToNext());
            }
            // return contact list
            return false;
        } finally {
            cursor.close();
        }

    }

    Integer[] getTableSize() {
        SQLiteDatabase db = this.getWritableDatabase();
        String Sql = "SELECT Setting_Value FROM %s where Setting_Name in ('Android_TableWidth','Android_ItemWidth' ) order by Setting_Name desc";

        Cursor cursor = db.rawQuery(String.format(Sql, AppSetting_table), null);

        Integer[] objArray = new Integer[2];
        try {
            // looping through all rows and adding to list
            int i = 0;
            if (cursor.moveToFirst()) {
                do {
                    if (cursor.getString(0).equals("null")) {
                        if (i != 1)//for Default Item width
                        {
                            objArray[i] = Integer.valueOf(90);//for Default table width
                        } else
                            objArray[i] = Integer.valueOf(173);//for Default Item width

                    } else {
                        objArray[i] = Integer.valueOf(cursor.getString(0));
                    }
                    i = i + 1;
                } while (cursor.moveToNext());
            }
            return objArray;            // return contact list
        } finally {
            cursor.close();
        }

    }

    private Boolean InsertSetUser(Integer userid) {

        SQLiteDatabase db = this.getWritableDatabase();

        String Sql = "SELECT userid,user_short,name,password FROM %s where %s =%s ";
        Cursor cursor = db.rawQuery(
                String.format(Sql, PosUserTable, coluserid, userid), null);
        try {

            if (cursor.moveToFirst()) {
                do {
                    Setuser s = new Setuser();
                    s.setsetuserid(Integer.valueOf(userid));
                    s.setsetusername(cursor.getString(2));
                    s.setuse_tabletuser(1);
                    AddSetUser(s);
                    return true;
                } while (cursor.moveToNext());
            } else {
                return false;
            }

        } finally {
            cursor.close();
        }

    }

    private void AddSetUser(Setuser s) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(col_userid, s.getsetuserid());
        cv.put(col_username, s.getsetusername());
        cv.put(col_use_tabletuser, s.getuse_tabletuser());
        db.insert(setuser_table, null, cv);
    }

    void deleteAddeduser(int userid) {
        SQLiteDatabase db2 = this.getWritableDatabase();
        db2.execSQL("Update " + setuser_table + " Set " + col_use_tabletuser
                + " = 0" + " where " + col_userid + " = " + userid + "");
        db2.close();
    }

    Boolean Allow_itemcancel(String userid) {
        SQLiteDatabase db = this.getWritableDatabase();
        String Sql = "SELECT allow_itemcancel FROM %s where userid = %s and allow_itemcancel =1";
        Cursor cursor = db.rawQuery(String.format(Sql, PosUserTable, userid), null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    return true;
                } while (cursor.moveToNext());
            }
            // return contact list
            return false;
        } finally {
            cursor.close();
        }

    }


    Boolean Allow_btnBill(String userid) {
        SQLiteDatabase db = this.getWritableDatabase();
        String Sql = "SELECT btnBill FROM %s where userid = %s and btnBill =1";
        Cursor cursor = db.rawQuery(String.format(Sql, PosUserTable, userid), null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    return true;
                } while (cursor.moveToNext());
            }
            // return contact list
            return false;
        } finally {
            cursor.close();
        }

    }


    Boolean Allow_btnDetail(String userid) {
        SQLiteDatabase db = this.getWritableDatabase();
        String Sql = "SELECT btnDetail FROM %s where userid = %s and btnDetail =1";
        Cursor cursor = db.rawQuery(String.format(Sql, PosUserTable, userid), null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    return true;
                } while (cursor.moveToNext());
            }
            // return contact list
            return false;
        } finally {
            cursor.close();
        }

    }

    Boolean Allow_btnClearAll(String userid) {
        SQLiteDatabase db = this.getWritableDatabase();
        String Sql = "SELECT btnClearAll FROM %s where userid = %s and btnClearAll =1";
        Cursor cursor = db.rawQuery(String.format(Sql, PosUserTable, userid), null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    return true;
                } while (cursor.moveToNext());
            }
            // return contact list
            return false;
        } finally {
            cursor.close();
        }

    }


    Boolean Allow_btnOthers(String userid) {
        SQLiteDatabase db = this.getWritableDatabase();
        String Sql = "SELECT btnOthers FROM %s where userid = %s and btnOthers =1";
        Cursor cursor = db.rawQuery(String.format(Sql, PosUserTable, userid), null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    return true;
                } while (cursor.moveToNext());
            }
            // return contact list
            return false;
        } finally {
            cursor.close();
        }

    }


    Boolean Allow_btnSplit(String userid) {
        SQLiteDatabase db = this.getWritableDatabase();
        String Sql = "SELECT btnSplit FROM %s where userid = %s and btnSplit =1";
        Cursor cursor = db.rawQuery(String.format(Sql, PosUserTable, userid), null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    return true;
                } while (cursor.moveToNext());
            }
            // return contact list
            return false;
        } finally {
            cursor.close();
        }

    }

    void AddTableRowStyle(String URL, String TablerowStyle) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        if (!URL.equals(""))
            cv.put(colurl, URL);
        if (!TablerowStyle.equals(""))
            cv.put(colTableRowStyle, TablerowStyle);


        if (getServiceURL().equals("")) {
            db.execSQL("Delete from  " + serviceaddressTable);
            db.insert(serviceaddressTable, null, cv);
        } else {
            db.update(serviceaddressTable, cv, null, null);
        }
        db.close();
    }

    void AddItemRowStyle(String URL, String ItemRowStyle) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        if (!URL.equals(""))
            cv.put(colurl, URL);
        if (!ItemRowStyle.equals(""))
            cv.put(colItemRowStyle, ItemRowStyle);


        if (getServiceURL().equals("")) {
            db.execSQL("Delete from  " + serviceaddressTable);
            db.insert(serviceaddressTable, null, cv);
        } else {
            db.update(serviceaddressTable, cv, null, null);
        }
        db.close();
    }

    String getTableRowStyle() {
        SQLiteDatabase db = this.getWritableDatabase();
        String URL = "";
        String selectQuery = "SELECT " + colTableRowStyle + " FROM "
                + serviceaddressTable;
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    // Customer contact = new Customer();
                    // contact.setID(Integer.parseInt(cursor.getString(0)));
                    // contact.setName(cursor.getString(1));
                    // contact.setPhoneNumber(cursor.getString(2));
                    // Adding contact to list
                    URL = cursor.getString(0);
                } while (cursor.moveToNext());
            }

            // return contact list

            if (URL == null)
                URL = "";
            return URL;
        } finally {
            cursor.close();
        }

    }

    String getItemRowStyle() {
        SQLiteDatabase db = this.getWritableDatabase();
        String URL = "";
        String selectQuery = "SELECT " + colItemRowStyle + " FROM "
                + serviceaddressTable;
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    // Customer contact = new Customer();
                    // contact.setID(Integer.parseInt(cursor.getString(0)));
                    // contact.setName(cursor.getString(1));
                    // contact.setPhoneNumber(cursor.getString(2));
                    // Adding contact to list
                    URL = cursor.getString(0);
                } while (cursor.moveToNext());
            }

            // return contact list

            if (URL == null)
                URL = "";
            return URL;
        } finally {
            cursor.close();
        }

    }

    ///AungmyoTun testing this method
    public SaleItemObj getSaleItemdataBySaleIDCode(String SaleID, String Code, String Sr, Double sale_price) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<SaleItemObj> list = new ArrayList<SaleItemObj>();
        String sSql = "SELECT %s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s FROM %s where %s = %s and (%s = 'null' or %s=0) and %s=%s and %s=%s ";
        sSql = String.format(sSql, colsaleitemid, colsr, colcode, colprice,
                colqty, colamount, colmodifiedrowsr, colsrno, colunittype,
                coltakeaway, colremark, colitemcancel, colisFinishedItem,
                colKTV_StartTime, colKTV_EndTime, colfire_sr, colfired, SaleItemTable, colsaleid,
                SaleID, colmodifiedrowsr, colmodifiedrowsr, colcode, Code, colsr, Sr);// modified by WaiWL on
        // 24/07/2015 --add
        // colisFinishedItem filed
        // //12/08/2015 --add
        // KTV_StartTime and
        // KTV_EndTime field

        Cursor cursor = db.rawQuery(sSql, null);
        SaleItemObj saleitemobj = new SaleItemObj();
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {

                    saleitemobj.setsaleitemid(cursor.getString(0));
                    saleitemobj.setsr(cursor.getString(1));
                    saleitemobj.setcode(cursor.getString(2));
                    saleitemobj.setprice(cursor.getString(3));
                    saleitemobj.setqty("1");
                    saleitemobj.setamount(String.valueOf(sale_price));
                    saleitemobj.setsrno(cursor.getString(7));
                    saleitemobj.setunittype(cursor.getString(8));
                    saleitemobj.settaway(cursor.getString(9));
                    saleitemobj.setremark(cursor.getString(10));
                    saleitemobj.setcancelflag(Boolean.parseBoolean(cursor
                            .getString(11)));
                    saleitemobj.setisFinishedItem(cursor.getString(12).equals(
                            "1") ? true : false);// added by WaiWL on 24/07/2015
                    saleitemobj.setKTV_StartTime(cursor.getString(13));// added
                    // by
                    // WaiWL
                    // on
                    // 12/08/2015
                    saleitemobj.setKTV_EndTime(cursor.getColumnName(14));// added
                    saleitemobj.setFire_sr(cursor.getString(15));
                    saleitemobj.setFired(cursor.getString(16));

                    // Adding tableobj to list
                    //list.add(saleitemobj);
                } while (cursor.moveToNext());
            }
            // return contact list
            return saleitemobj;
        } finally {
            cursor.close();
        }

    }


    String getqty_decimal_places() {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT " + colqty_decimal_places + " FROM "
                + serviceaddressTable;
        Cursor cursor = db.rawQuery(selectQuery, null);
        String qty_decimal_places = "";
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {

                    qty_decimal_places = cursor.getString(0);

                } while (cursor.moveToNext());
            }
            if (qty_decimal_places == null)
                qty_decimal_places = "";
            return qty_decimal_places;

        } finally {
            cursor.close();
        }

    }


    String getUser(int tableid) {
        SQLiteDatabase db = this.getWritableDatabase();
        //String selectQuery = "select short from "+UserLogTable+" ul join "+PosUserTable+" u on u.userid = ul.userid";
        String selectQuery = "SELECT *  FROM " + UserLogTable + " ul LEFT OUTER JOIN " + PosUserTable + " u ON u.userid = ul.userid where ul.table_name_id = " + tableid;
        //+" ul  NATURAL LEFT OUTER JOIN  "+ PosUserTable +" u where ul.Table_Name_id = 1 ";
        Cursor cursor = db.rawQuery(selectQuery, null);


        String user = "";
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {

                    user = cursor.getString(3);

                } while (cursor.moveToNext());
            }
            if (user == null)
                user = "";
            return user;

        } finally {
            cursor.close();
        }

    }


    String getprice_decimal_places() {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT " + colprice_decimal_places + " FROM "
                + serviceaddressTable;
        Cursor cursor = db.rawQuery(selectQuery, null);
        String price_decimal_places = "";
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {

                    price_decimal_places = cursor.getString(0);

                } while (cursor.moveToNext());
            }
            if (price_decimal_places == null)
                price_decimal_places = "";
            pricedecimal = Integer.parseInt(price_decimal_places);
            return price_decimal_places;

        } finally {
            cursor.close();
        }
    }

    String qtyroundto(String qty) {
        String roundTo = getqty_decimal_places();
        String format = "%." + roundTo + "f";
        return (String.format(format, Double.parseDouble(qty)));
    }

    String PriceRoundTo(String price) {
        String roundTo = getprice_decimal_places();
        String format = "%." + roundTo + "f";
        return (String.format(format, Double.parseDouble(price)));
    }

    //added by EKK on 24-02-2020
    String getDefCurrency() {
        SQLiteDatabase db = this.getWritableDatabase();
        String currency = "";
        String sSql = "SELECT %s FROM %s where %s = '%s'";
        Cursor cursor = db.rawQuery(String.format(sSql, colSetting_Value,
                AppSetting_table, colSetting_Name, "choose_defaultcurrency"), null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    currency = cursor.getString(0);
                } while (cursor.moveToNext());
            }

            // return contact list
            if (currency == null)
                currency = "1";
            return currency;
        } finally {
            cursor.close();
        }


    }

    //added by EKK on 24-02-2020
    String getExgRateByCureency() {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "select " + colSetting_Value + " from" + AppSetting_table + " where Setting_Name='choose_defaultcurrency'";

        Cursor cursor = db.rawQuery(selectQuery, null);
        String exgrate = "";
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {

                    exgrate = cursor.getString(0);

                } while (cursor.moveToNext());
            }
            if (exgrate == null)
                exgrate = "1";
            //defcurrency=Integer.parseInt(currency);
            return exgrate;

        } finally {
            cursor.close();
        }


    }

    //added by ZYP [17/11/2020] offline exg rate
    public String getExgRateByCurrency(String curr) {
        List<CurrencyObj> currencyObjs_list = new ArrayList<CurrencyObj>();
        currencyObjs_list = GetCurrency();

        String exg_rate = "1";

        for (int i = 0; i < currencyObjs_list.size(); i++) {
            if (Integer.parseInt(curr) == currencyObjs_list.get(i).getCurrency()) {
                exg_rate = currencyObjs_list.get(i).getExg_rate() + "";
                break;
            }

        }
        return exg_rate;
    }


    public String LoadExgRate(String Datalink, String id) {
        url = Datalink;
        String rate = Json_class.getString(url
                + "/Data/GetExgRate?currID="
                + java.net.URLEncoder.encode(id));

        return rate;
    }

    Boolean Allow_edit_after_insert(String userid) {
        SQLiteDatabase db = this.getWritableDatabase();
        String Sql = "SELECT allow_edit_after_insert FROM %s where userid = %s and allow_edit_after_insert =1";
        Cursor cursor = db.rawQuery(String.format(Sql, PosUserTable, userid), null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    return true;
                } while (cursor.moveToNext());
            }
            // return contact list
            return false;
        } finally {
            cursor.close();
        }

    }

    //added by EKK on 15-01-2020
    Boolean Allow_select_Customer(String userid) {
        SQLiteDatabase db = this.getWritableDatabase();
        String Sql = "SELECT select_customer FROM %s where userid = %s and select_customer =1";
        Cursor cursor = db.rawQuery(String.format(Sql, PosUserTable, userid), null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    return true;
                } while (cursor.moveToNext());
            }
            // return contact list
            return false;
        } finally {
            cursor.close();
        }

    }

    //added by EKK on 15-01-2020
    Boolean Allow_create_Customer(String userid) {
        SQLiteDatabase db = this.getWritableDatabase();
        String Sql = "SELECT select_customer FROM %s where userid = %s and create_customer =1";
        Cursor cursor = db.rawQuery(String.format(Sql, PosUserTable, userid), null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    return true;
                } while (cursor.moveToNext());
            }
            // return contact list
            return false;
        } finally {
            cursor.close();
        }

    }

    //added by ZYP for
    Boolean Allow_item_transfer(String userid) {
        SQLiteDatabase db = this.getWritableDatabase();
        String Sql = "SELECT allow_item_transfer FROM %s where userid = %s and allow_item_transfer = 1";
        Cursor cursor = db.rawQuery(String.format(Sql, PosUserTable, userid), null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    return true;
                } while (cursor.moveToNext());
            }
            // return contact list
            return false;
        } finally {
            cursor.close();
        }

    }

    public Boolean CheckPrintBill(String Datalink, String tranid) {
        url = Datalink;
        String isBillPrint = Json_class.getString(url
                + "/Data/CheckPrintBill?tranid="
                + java.net.URLEncoder.encode(tranid));

        if (isBillPrint.trim().equals("Y")) {
            return true;
        } else {
            return false;
        }

    }

    public Boolean Checkbillnotprint(String Datalink, String tranid) {
        url = Datalink;
        String isBillnotprint = Json_class.getString(url
                + "/Data/CheckPrintBill?tranid="
                + java.net.URLEncoder.encode(tranid));

        if (isBillnotprint.trim().equals("Y")) {
            return true;
        } else {
            return false;
        }

    }

    public void LoadSoldOut(String Datalink) {
        url = Datalink;
        JSONArray itemjsonarray = Json_class.getJson(url + "/Data/LoadSoldOut");
        //Added by KLM for UniZawgyi Auto Detect 23022022
        try {
            if (!LoginActivity.isUnicode) {
                itemjsonarray = new JSONArray(Rabbit.uni2zg(itemjsonarray.toString()));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
//		if (itemjsonarray.length() > 0) {
//			ClearTable(SoldOutTable);
//		}
        ClearTable(SoldOutTable);
        for (int i = 0; i < itemjsonarray.length(); i++) {
            JSONObject c;
            try {
                c = itemjsonarray.getJSONObject(i);
                SoldOutItem A = new SoldOutItem();
                A.setCode(Integer.parseInt(c.getString(colsoldoutcode)));
                A.setUsr_code(c.getString(colsoldoutusr_code));
                A.setDescription(c.getString(colsoldoutdesc));

                AddSoldOutitem(A);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public Boolean check_Bill(Integer TableID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sSql = "SELECT %s FROM %s where %s = %s ";
        sSql = String.format(sSql, colBillChecked, ActiveTables, colTable_Name_ID,
                TableID);

        Cursor cursor = db.rawQuery(sSql, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    if ((cursor.getString(0).equals("0"))) {
                        return false;
                    } else
                        return true;

                } while (cursor.moveToNext());
            }
            // return contact list
            return false;
        } finally {
            cursor.close();
        }

    }

    public List<CashierPrinter> getAllCashierPrinteList() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<CashierPrinter> list = new ArrayList<CashierPrinter>();
        String query = "SELECT " + CashierPrinter_Table + ".* FROM " + CashierPrinter_Table;
        Cursor cursor = db.rawQuery(query, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    CashierPrinter printer = new CashierPrinter();
                    printer.set_PrinterID(Integer.parseInt(cursor.getString(0)));
                    printer.set_PrinterName(cursor.getString(1));
                    printer.set_Printer(cursor.getString(2));
                    list.add(printer);
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
        }
        return list;
    }

    public void LoadCashierPrinter(String Datalink) {
        url = Datalink;
        JSONArray itemjsonarray = Json_class.getJson(url
                + "/Data/GetCashierPrinter");
        //Added by KLM for UniZawgyi Auto Detect 23022022
        try {
            if (!LoginActivity.isUnicode) {
                itemjsonarray = new JSONArray(Rabbit.uni2zg(itemjsonarray.toString()));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (itemjsonarray.length() > 0) {
            ClearTable(CashierPrinter_Table);
        }
        for (int i = 0; i < itemjsonarray.length(); i++) {
            JSONObject c;
            try {
                c = itemjsonarray.getJSONObject(i);
                CashierPrinter printer = new CashierPrinter();
                printer.set_PrinterID(Integer.valueOf(c.getString(colPrinterID)));
                printer.set_PrinterName(c.getString(colPrinterName));
                printer.set_Printer(c.getString(colPrinter));
                AddCashierPrinter(printer);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                // e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }

    }

    public void AddCashierPrinter(CashierPrinter printer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(colPrinterID, printer.get_PrinterID());
        cv.put(colPrinterName, printer.get_PrinterName());
        cv.put(colPrinter, printer.get_Printer());
        db.insert(CashierPrinter_Table, null, cv);
    }

    public String getPrinterIDByName(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT  " + CashierPrinter_Table + ".*" + " FROM "
                + CashierPrinter_Table + " where " + colPrinterName + " ='" + name + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        String id = "";
        try {
            if (cursor.moveToFirst()) {
                do {
                    id = cursor.getString(0);
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
        }
        return id;
    }

    public int getPrinterIDByUserID(int userid) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT  " + CashierPrinter_Table + ".*" + " FROM "
                + CashierPrinter_Table + " where " + coluserid + " =" + userid + "";
        Cursor cursor = db.rawQuery(selectQuery, null);
        int id = 0;
        try {
            if (cursor.moveToFirst()) {
                do {
                    id = Integer.parseInt(cursor.getString(0));
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
        }
        return id;
    }

    public void insert_menuorderitem(MenuOrderObj menuitemobject) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(colmenutranid, menuitemobject.getTranid());
        cv.put(colmenuuserid, menuitemobject.getUserid());
        cv.put(colorderid, menuitemobject.getOrderid());
        cv.put(colmenucode, menuitemobject.getCode());
        cv.put(colmenuusr_code, menuitemobject.getUsr_code());
        cv.put(colmenudescription, menuitemobject.getDescription());
        cv.put(colmenuqty, menuitemobject.getQty());
        cv.put(colmenusaleprice, menuitemobject.getSale_price());
        cv.put(colmenuisnew, menuitemobject.getIsnew());
        //added WHM [2020-06-01] self order
        cv.put(colMenuOrder_isSetMenu, menuitemobject.getIsSetMenu());
        cv.put(colMenuOrder_sr, menuitemobject.getSr());
        cv.put(colMenuOrder_srno, menuitemobject.getSrno());
        cv.put(colMenuOrder_modifiedrowsr, menuitemobject.getModifiedrowsr());
        cv.put(colMenuOrder_setmenurowsr, menuitemobject.getSetmenurowsr());

        db.insert(MenuOrderTable, null, cv);
    }

    public ArrayList<MenuOrderObj> getMenuOrderItems(int tranid, int userid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<MenuOrderObj> menulist = new ArrayList<MenuOrderObj>();

        String selectQuery = " SELECT " + MenuOrderTable + ".* FROM " + MenuOrderTable + " WHERE tranid= "
                + tranid + " and userid= " + userid;
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    MenuOrderObj menuobj = new MenuOrderObj();
                    menuobj.setTranid(cursor.getString(0));
                    menuobj.setUserid(cursor.getString(1));
                    menuobj.setOrderid(cursor.getString(2));
                    menuobj.setCode(cursor.getString(3));
                    menuobj.setUsr_code(cursor.getString(4));
                    menuobj.setDescription(cursor.getString(5));
                    menuobj.setQty(cursor.getString(6));
                    menuobj.setSale_price(cursor.getString(7));
                    menuobj.setIsnew(cursor.getString(8).equals("1") ? true : false);
                    menuobj.setIsSetMenu(cursor.getString(9));

                    menulist.add(menuobj);
                } while (cursor.moveToNext());
            }
            return menulist;
        } finally {
            cursor.close();
        }
    }

    public MenuOrderObj getMenuObjectById(int orderid) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = " SELECT " + MenuOrderTable + ".* FROM " + MenuOrderTable + " WHERE orderid=" + orderid;
        Cursor cursor = db.rawQuery(query, null);
        MenuOrderObj menuobj = new MenuOrderObj();
        try {
            if (cursor.moveToFirst()) {
                menuobj.setTranid(cursor.getString(0));
                menuobj.setUserid(cursor.getString(1));
                menuobj.setOrderid(cursor.getString(2));
                menuobj.setCode(cursor.getString(3));
                menuobj.setUsr_code(cursor.getString(4));
                menuobj.setDescription(cursor.getString(5));
                menuobj.setQty(cursor.getString(6));
                menuobj.setSale_price(cursor.getString(7));
                menuobj.setIsnew(cursor.getString(8).equals("1") ? true : false);
            }
            return menuobj;
        } finally {
            cursor.close();
        }
    }

    public void deleteOrder(int tranid, int userid) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(" DELETE FROM " + MenuOrderTable + " where tranid=" + tranid + " and userid=" + userid);
        db.close();
    }

    public void deleteOrderbyId(int orderid) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(" DELETE FROM " + MenuOrderTable + " where orderid=" + orderid);
        db.close();
    }

    public void updateOrderQty(int orderid, String qty) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(" UPDATE " + MenuOrderTable + " set " + colmenuqty + " =" + qty + " WHERE orderid=" + orderid);
        db.close();
    }

    public String getSalePriceByItemcode(String code) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT  " + ItemTable + ".*" + " FROM "
                + ItemTable + " where " + colcode + " =' " + code + " '";
        Cursor cursor = db.rawQuery(selectQuery, null);
        String price = "";
        try {
            if (cursor.moveToFirst()) {
                do {
                    price = cursor.getString(3);
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
        }
        return price;
    }

    public int getMenuTranid() {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "select ifnull(min(tranid)-1,-1) from " + MenuOrderTable + " where tranid < 0 ";
        Cursor cursor = db.rawQuery(selectQuery, null);
        int tranid = 0;
        try {
            if (cursor.moveToFirst()) {
                do {
                    tranid = Integer.parseInt(cursor.getString(0));
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
        }
        return tranid;
    }

    public int getMenuOrderId() {
        SQLiteDatabase db = this.getWritableDatabase();
        //String selectQuery="select ifnull(max(orderid)+1,1) from " + MenuOrderTable + " where tranid= "+tranid;
        String selectQuery = "select ifnull(max(orderid)+1,1) from " + MenuOrderTable;
        Cursor cursor = db.rawQuery(selectQuery, null);
        int orderid = 0;
        try {
            if (cursor.moveToFirst()) {
                do {
                    orderid = Integer.parseInt(cursor.getString(0));
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
        }
        return orderid;
    }

    public ArrayList<MenuOrderObj> checkOrder(int tranid, int userid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<MenuOrderObj> menulist = new ArrayList<MenuOrderObj>();

        String selectQuery = " SELECT " + MenuOrderTable + ".* FROM " + MenuOrderTable + " WHERE tranid= "
                + tranid + " and userid= " + userid + " and isNew=1";
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    MenuOrderObj menuobj = new MenuOrderObj();
                    menuobj.setTranid(cursor.getString(0));
                    menuobj.setUserid(cursor.getString(1));
                    menuobj.setOrderid(cursor.getString(2));
                    menuobj.setCode(cursor.getString(3));
                    menuobj.setUsr_code(cursor.getString(4));
                    menuobj.setDescription(cursor.getString(5));
                    menuobj.setQty(cursor.getString(6));
                    menuobj.setSale_price(cursor.getString(7));
                    menuobj.setIsnew(cursor.getString(8).equals("1") ? true : false);
                    menulist.add(menuobj);
                } while (cursor.moveToNext());
            }
            return menulist;
        } finally {
            cursor.close();
        }
    }

    public void updateMenuOrder(int tranid, int userid) {
        SQLiteDatabase db2 = this.getWritableDatabase();
        db2.execSQL("Update " + MenuOrderTable + " Set " + colmenuisnew
                + " = 0" + " where tranid= " + tranid + " and  userid=" + userid);
        db2.close();
    }

    public void insert_menutable(int tranid, int userid, String tablename) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(colmenutranid, tranid);
        cv.put(colmenuuserid, userid);
        cv.put(colmenutablenameid, tablename);
        db.insert(MenuOrderTransferTable, null, cv);
    }

    public int getTotalOrderQty(int tranid, int userid) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = " SELECT " + colmenuqty + " FROM " + MenuOrderTable + " WHERE tranid=" + tranid + " and userid=" + userid;
        Cursor cursor = db.rawQuery(query, null);
        int totalqty = 0;
        try {
            if (cursor.moveToFirst()) {
                do {
                    totalqty += Integer.parseInt(cursor.getString(0));
                } while (cursor.moveToNext());
            }
            return totalqty;
        } finally {
            cursor.close();
        }
    }

    public double getTotalOrderAmount(int tranid, int userid) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = " SELECT " + colmenuqty + "," + colmenusaleprice + " FROM " + MenuOrderTable
                + " WHERE tranid=" + tranid + " and userid=" + userid;
        Cursor cursor = db.rawQuery(query, null);
        double totalamount = 0.0;
        try {
            if (cursor.moveToFirst()) {
                do {
                    double qty = Double.parseDouble(cursor.getString(0));
                    double amount = Double.parseDouble(cursor.getString(1));
                    totalamount += qty * amount;
                } while (cursor.moveToNext());
            }
            return totalamount;
        } finally {
            cursor.close();
        }
    }

    public String getPosUserPasswordById(int userid) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = " SELECT " + colpassword + " FROM " + PosUserTable + " WHERE " + coluserid + "=" + userid;
        Cursor cursor = db.rawQuery(query, null);
        String password = "";
        try {
            if (cursor.moveToFirst()) {
                password = cursor.getString(0);
            }
            return password;
        } finally {
            cursor.close();
        }
    }

    public int getPopupQty(int tranid, int userid) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = " SELECT count(*) FROM " + MenuOrderTable + " WHERE tranid=" + tranid + " AND userid=" + userid;
        Cursor cursor = db.rawQuery(query, null);
        int qty = 0;
        try {
            if (cursor.moveToFirst()) {
                qty = Integer.parseInt(cursor.getString(0));
            }

            return qty;
        } finally {
            cursor.close();
        }
    }

    public Boolean checkOrderExist(int tranid, int userid, String code) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = " SELECT * FROM " + MenuOrderTable + " WHERE tranid=" + tranid + " AND userid=" + userid + " AND " + colmenucode + "='" + code + "'";
        Cursor cursor = db.rawQuery(query, null);
        try {
            if (cursor.moveToFirst()) {
                return true;
            }
            while (cursor.moveToNext()) ;
            return false;
        } finally {
            cursor.close();
        }
    }

    public int getOrderQtyByCode(int tranid, int userid, String code) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = " SELECT " + colmenuqty + " FROM " + MenuOrderTable + " WHERE tranid=" + tranid + " AND userid=" + userid + " AND " + colmenucode + "='" + code + "'";
        Cursor cursor = db.rawQuery(query, null);
        int qty = 0;
        try {
            if (cursor.moveToFirst()) {
                qty = Integer.valueOf(cursor.getString(0));
            }
            while (cursor.moveToNext()) ;
            return qty;
        } finally {
            cursor.close();
        }
    }

    public void updateOrderQtyWithCode(int tranid, int userid, String code, String qty) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(" UPDATE " + MenuOrderTable + " set " + colmenuqty + " =" + qty + " WHERE tranid=" + tranid + " AND userid=" + userid + " AND " + colmenucode + "='" + code + "'");
        db.close();
    }

    public String getCustomerIdByTableId(String tableId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String customerid = "";
        String query = "select " + colcustomerid + " from " + SaleTable + " where " + colTable_Name_ID + " = '" + tableId + "'";
        Cursor cursor = db.rawQuery(query, null);
        try {
            if (cursor.moveToFirst()) {
                customerid = cursor.getString(0);
            }
            return customerid;
        } finally {
            cursor.close();
            db.close();
        }
    }

    public Boolean checkCustomerCredit(int customerid) {
        SQLiteDatabase db = this.getWritableDatabase();
        Boolean credit = false;
        String query = "select " + colcustomercredit + " from " + CustomerTable + " where " + colcustomerid + " = " + customerid;
        Cursor cursor = db.rawQuery(query, null);
        try {
            if (cursor.moveToFirst()) {
                credit = Boolean.parseBoolean(cursor.getString(0).equals("True") ? "True" : "False");
            }
            return credit;
        } finally {
            cursor.close();
        }
    }

    Boolean Allow_btnPrintBill(String userid) {
        SQLiteDatabase db = this.getWritableDatabase();
        String Sql = "SELECT btnPrintBill FROM %s where userid = %s and btnPrintBill =1";
        Cursor cursor = db.rawQuery(String.format(Sql, PosUserTable, userid), null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    return true;
                } while (cursor.moveToNext());
            }
            // return contact list
            return false;
        } finally {
            cursor.close();
        }

    }

    Boolean show_billnotprint(String userid) {
        SQLiteDatabase db = this.getWritableDatabase();
        String Sql = "SELECT billnotprint FROM %s where userid = %s and billnotprint = 1";
        Cursor cursor = db.rawQuery(String.format(Sql, PosUserTable, userid), null);
        try {

            //looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    return true;
                } while (cursor.moveToNext());
            }
            //return contact list
            return false;
        } finally {
            cursor.close();
        }

    }

    //Added by ZYP [06-01-2020] for New menu activity with unicode
    String getItemidbyitemName(String itemName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT " + colcode + " FROM " + ItemTable
                + " where " + coldescription + " like '" + itemName + "' and "
                + coltmpInactive + "='false' " +
                " order by " + colcode;
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {

                    return cursor.getString(0);

                } while (cursor.moveToNext());
            }
            return "";
        } finally {
            cursor.close();
        }

    }

    //Added by ZYP [06-01-2020] for New menu activity with unicode
    List<ItemsObj> getItemsbyCode(String code) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<ItemsObj> list = new ArrayList<ItemsObj>();


        String selectQuery = "SELECT " + ItemTable + ".*" + " FROM "
                + ItemTable + " where " + colcode + " ='" + code + "'"
                + " and " + coltmpInactive + "='false' " +
                " order by " + colusrcode;

        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    ItemsObj tablesobj = new ItemsObj();
                    tablesobj.setcode(cursor.getString(0));
                    tablesobj.setusr_code(cursor.getString(1));
                    tablesobj.setdescription(cursor.getString(2));
                    tablesobj.setissetmenu(cursor.getString(21));//added WHM [2020-06-01] self order
                    list.add(tablesobj);
                } while (cursor.moveToNext());
            }

            // return contact list
            return list;
        } finally {
            cursor.close();
        }

    }


    //added WHM [2020-01-27] MDY2-200141
    public int checkCustomerPriceLevel(int customerid) {
        SQLiteDatabase db = this.getWritableDatabase();
        int pricelevel = 0;
        String query = "select " + colcustomer_pricelevel + " from " + CustomerTable + " where " + colcustomerid + " = " + customerid;
        Cursor cursor = db.rawQuery(query, null);
        try {
            if (cursor.moveToFirst()) {
                pricelevel = Integer.parseInt(cursor.getString(0));
            }
            return pricelevel;
        } finally {
            cursor.close();
        }
    }


    public String getCurrencyFormat(String value)    //added by ZYP [19-02-2020] for currency format
    {
        DecimalFormat priceFormat = (DecimalFormat) NumberFormat.getNumberInstance(Locale.US);
        String price_parren = "#,##0";

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

    String getCategoryidbycode(String code) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT " + colcategoryid + " FROM " + ItemTable
                + " where " + colcode + " = '" + code + "' and "
                + coltmpInactive + "='false' " +
                " order by " + colcode;
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {

                    return cursor.getString(0);

                } while (cursor.moveToNext());
            }
            return "";
        } finally {
            cursor.close();
        }

    }

    //end

    //region delivery //added WHM [2020-05-07]

    //regin method
    boolean use_deliverymanagement() {
        SQLiteDatabase db = this.getWritableDatabase();

        String sSql = "SELECT %s FROM %s where %s = '%s' and %s ='%s'";
        Cursor cursor = db.rawQuery(String.format(sSql, colSetting_Value,
                AppSetting_table, colSetting_Name, "use_deliverymanagement", colSetting_Value, "Y"), null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    return true;
                } while (cursor.moveToNext());
            }
            // return contact list
            return false;
        } finally {
            cursor.close();
        }

    }

    public List<TownshipObj> GetTownshipTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<TownshipObj> list = new ArrayList<TownshipObj>();
        String query = "SELECT townshipid,name FROM " + TownshipTable + " order by townshipid ";
        Cursor cursor = db.rawQuery(query, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    TownshipObj township = new TownshipObj();
                    township.setTownshipid(Integer.parseInt(cursor.getString(0)));
                    township.setName(cursor.getString(1));
                    list.add(township);
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
        }
        return list;
    }

    public List<Salesmen_TypeObj> GetAgentTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<Salesmen_TypeObj> list = new ArrayList<Salesmen_TypeObj>();
        String query = "SELECT salesmen_type,name,delivery_system,delivery_charges FROM " + SalesmenType_Table + " order by salesmen_type ";
        Cursor cursor = db.rawQuery(query, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Salesmen_TypeObj st = new Salesmen_TypeObj();
                    st.setSalesmen_type(Integer.parseInt(cursor.getString(0)));
                    st.setName(cursor.getString(1));

                    boolean usedelivery = cursor.getInt(2) > 0;
                    st.setDelivery_system(usedelivery);

                    boolean usedelivery_charges = cursor.getInt(3) > 0;
                    st.setDelivery_charges(usedelivery_charges);

                    list.add(st);
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
        }
        return list;
    }

    public List<Salesmen> GetDeliveryMan(int agent_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<Salesmen> list = new ArrayList<Salesmen>();
        String query = "SELECT salesmen_id,salesmen_name FROM " + DeliveryMan_Table + " where salesmen_type=" + agent_id + " order by salesmen_id ";
        //String query= "SELECT salesmen_id,salesmen_name FROM " + DeliveryMan_Table +" order by salesmen_id " ;
        Cursor cursor = db.rawQuery(query, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Salesmen sm = new Salesmen();
                    sm.setsalesmen_id(Integer.parseInt(cursor.getString(0)));
                    sm.setsalesmen_name(cursor.getString(1));

                    list.add(sm);
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
        }
        return list;
    }

    public List<DeliverySetupObj> GetDeliverySetupByTownshipid(int township_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<DeliverySetupObj> list = new ArrayList<DeliverySetupObj>();
        String query = "SELECT townshipid,delivery_charges,free_range,estimate_time FROM " + DeliverySetupTable + " where Townshipid=" + township_id + " order by Townshipid ";

        Cursor cursor = db.rawQuery(query, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    DeliverySetupObj ds = new DeliverySetupObj();
                    ds.setTownshipid(Integer.parseInt(cursor.getString(0)));
                    ds.setDelivery_charges(cursor.getString(1));
                    ds.setFree_range(cursor.getString(2));
                    ds.setEstimate_time(Integer.parseInt(cursor.getString(3)));

                    list.add(ds);
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
        }
        return list;
    }

    public List<DeliveryEntryObj> GetDeliveryEntryTmp(String tranid) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<DeliveryEntryObj> list = new ArrayList<DeliveryEntryObj>();
        //String query= "SELECT townshipid,delivery_charges,free_range,estimate_time FROM " + DeliverySetupTable +" where Townshipid="+township_id + " order by Townshipid " ;
        String query = " select tranid,townshipid,order_customer_detail,agent_id,deliveryman_id,use_calc_delivery_charges,order_datetime, " +
                " estimate_time,remark,isDeliver,org_deliverycharges,org_delivery_freerange,updated FROM " + DeliveryEntry_Table + " where tranid=" + tranid;

        Cursor cursor = db.rawQuery(query, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    DeliveryEntryObj dt = new DeliveryEntryObj();
                    dt.setTranid(Integer.parseInt(cursor.getString(0)));
                    dt.setTownshipid(Integer.parseInt(cursor.getString(1)));
                    dt.setOrder_customer_detail(cursor.getString(2));
                    dt.setAgent_id(Integer.parseInt(cursor.getString(3)));
                    dt.setDeliveryman_id(Integer.parseInt(cursor.getString(4)));
                    dt.setUse_calc_delivery_charges(Boolean.parseBoolean(cursor.getString(5)));
                    dt.setOrder_datetime(cursor.getString(6));
                    dt.setEstimate_time(Integer.parseInt(cursor.getString(7)));
                    dt.setRemark(cursor.getString(8));
                    dt.setDeliver(Boolean.parseBoolean(cursor.getString(9)));
                    dt.setOrg_deliverycharges(cursor.getString(10));
                    dt.setOrg_delviery_freerange(cursor.getString(11));
                    dt.setUpdated(Boolean.parseBoolean(cursor.getString(12)));


                    list.add(dt);
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
        }
        return list;
    }

    public void UpdateDeliveryEntryTmp_Table(DeliveryEntryObj dt) {
        ClearTable(DeliveryEntry_Table);

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DeliveryEntry_tranid, dt.getTranid());
        cv.put(DeliveryEntry_townshipid, dt.getTownshipid());
        cv.put(DeliveryEntry_order_customer_detail, dt.getOrder_customer_detail());
        cv.put(DeliveryEntry_agent_id, dt.getAgent_id());
        cv.put(DeliveryEntry_deliveryman_id, dt.getDeliveryman_id());
        cv.put(DeliveryEntry_use_calc_delivery_charges, dt.isUse_calc_delivery_charges());
        cv.put(DeliveryEntry_order_datetime, dt.getOrder_datetime());
        cv.put(DeliveryEntry_estimate_time, dt.getEstimate_time());
        cv.put(DeliveryEntry_remark, dt.getRemark());
        cv.put(DeliveryEntry_isDeliver, dt.isDeliver());
        cv.put(DeliveryEntry_org_deliverycharges, dt.getOrg_deliverycharges());
        cv.put(DeliveryEntry_org_delivery_freerange, dt.getOrg_delviery_freerange());
        cv.put(DeliveryEntry_updated, dt.isUpdated());

        db.insert(DeliveryEntry_Table, null, cv);
    }


    //endregion //method

    //region load table
    public void LoadTownship(String Datalink) {
        url = Datalink;
        JSONArray itemjsonarray = Json_class.getJson(url
                + "/Data/GetTownship");

        //Added by KLM for UniZawgyi Auto Detect 23022022
        try {
            if (!LoginActivity.isUnicode) {
                itemjsonarray = new JSONArray(Rabbit.uni2zg(itemjsonarray.toString()));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ClearTable(TownshipTable);
        for (int i = 0; i < itemjsonarray.length(); i++) {
            JSONObject c;
            try {
                c = itemjsonarray.getJSONObject(i);

                TownshipObj tw = new TownshipObj();
                tw.setTownshipid(Integer.valueOf(c.getString(Townshipid)));
                tw.setName(c.getString(Townshipname));
                tw.setSort_id(Integer.valueOf(c.getString(Township_sortid)));

                AddTownShip(tw);

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void LoadAgent(String Datalink) {
        url = Datalink;
        JSONArray itemjsonarray = Json_class.getJson(url
                + "/Data/GetAgent");

        //Added by KLM for UniZawgyi Auto Detect 23022022
        try {
            if (!LoginActivity.isUnicode) {
                itemjsonarray = new JSONArray(Rabbit.uni2zg(itemjsonarray.toString()));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ClearTable(SalesmenType_Table);
        for (int i = 0; i < itemjsonarray.length(); i++) {
            JSONObject c;
            try {
                c = itemjsonarray.getJSONObject(i);

                Salesmen_TypeObj st = new Salesmen_TypeObj();
                st.setSalesmen_type(Integer.valueOf(c.getString(Salesmen_type)));
                st.setName(c.getString(Salesmen_type_name));
                st.setDelivery_system(c.getBoolean(salesmentype_delivery_system));
                st.setDelivery_charges(c.getBoolean(salesmentype_delivery_charges));

                AddAgent(st);

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void LoadDeliveryMan(String Datalink) {
        url = Datalink;
        JSONArray itemjsonarray = Json_class.getJson(url
                + "/Data/GetDeliveryMan");

        //Added by KLM for UniZawgyi Auto Detect 23022022
        try {
            if (!LoginActivity.isUnicode) {
                itemjsonarray = new JSONArray(Rabbit.uni2zg(itemjsonarray.toString()));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ClearTable(DeliveryMan_Table);
        for (int i = 0; i < itemjsonarray.length(); i++) {
            JSONObject c;
            try {
                c = itemjsonarray.getJSONObject(i);

                Salesmen sm = new Salesmen();
                sm.setsalesmen_id(Integer.valueOf(c.getString(DeliveryMan_id)));
                sm.setsalesmen_name(c.getString(DeliveryMan_name));
                sm.setSalesmen_type(Integer.valueOf(c.getString(DeliveryMan_AgentID)));

                AddDeliveryMan(sm);

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void LoadDeliverySetup(String Datalink) {
        url = Datalink;
        JSONArray itemjsonarray = Json_class.getJson(url
                + "/Data/GetDeliverySetup");

        //Added by KLM for UniZawgyi Auto Detect 23022022
        try {
            if (!LoginActivity.isUnicode) {
                itemjsonarray = new JSONArray(Rabbit.uni2zg(itemjsonarray.toString()));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ClearTable(DeliverySetupTable);
        for (int i = 0; i < itemjsonarray.length(); i++) {
            JSONObject c;
            try {
                c = itemjsonarray.getJSONObject(i);

                DeliverySetupObj ds = new DeliverySetupObj();
                ds.setTownshipid(Integer.valueOf(c.getString(DeliverySetup_Townshipid)));
                ds.setDelivery_charges(c.getString(DeliverySetup_delivery_charges));
                ds.setFree_range(c.getString(DeliverySetup_free_range));
                ds.setEstimate_time(Integer.valueOf(c.getString(DeliverySetup_estimate_time)));

                AddDeliverySetup(ds);

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void LoadDeliveryEntryTmp(String Datalink,
                                     String tranid) {
        url = Datalink;
        JSONArray itemjsonarray = Json_class.getJson(url
                + "/Data/Get_DeliveryEntryDetailTmp?tranid="
                + java.net.URLEncoder.encode(tranid));

        ClearTable(DeliveryEntry_Table);


        if (itemjsonarray.length() > 0) {
            for (int i = 0; i < itemjsonarray.length(); i++) {
                JSONObject c;
                try {
                    DeliveryEntryObj deliveryentrytmpObj = new DeliveryEntryObj();

                    c = itemjsonarray.getJSONObject(i);
                    ContentValues cvalue = new ContentValues();
                    deliveryentrytmpObj.setTranid(Integer.parseInt(c.getString(DeliveryEntry_tranid)));
                    deliveryentrytmpObj.setTownshipid(Integer.parseInt(c.getString(DeliveryEntry_townshipid)));
                    deliveryentrytmpObj.setOrder_customer_detail(c.getString(DeliveryEntry_order_customer_detail));
                    deliveryentrytmpObj.setAgent_id(Integer.parseInt(c.getString(DeliveryEntry_agent_id)));
                    deliveryentrytmpObj.setDeliveryman_id(Integer.parseInt(c.getString(DeliveryEntry_deliveryman_id)));
                    deliveryentrytmpObj.setUse_calc_delivery_charges(Boolean.parseBoolean(c.getString(DeliveryEntry_use_calc_delivery_charges)));
                    deliveryentrytmpObj.setOrder_datetime(c.getString(DeliveryEntry_order_datetime));
                    deliveryentrytmpObj.setEstimate_time(Integer.parseInt(c.getString(DeliveryEntry_estimate_time)));
                    deliveryentrytmpObj.setRemark(c.getString(DeliveryEntry_remark));
                    deliveryentrytmpObj.setDeliver(Boolean.parseBoolean(c.getString(DeliveryEntry_isDeliver)));
                    deliveryentrytmpObj.setOrg_deliverycharges(c.getString(DeliveryEntry_org_deliverycharges));
                    deliveryentrytmpObj.setOrg_delviery_freerange(c.getString(DeliveryEntry_org_delivery_freerange));
                    deliveryentrytmpObj.setUpdated(Boolean.parseBoolean(c.getString(DeliveryEntry_updated)));

                    AddDeliveryEntryTmp(deliveryentrytmpObj);

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }


    }

    //endregion loadtable


    //region add table
    private void AddTownShip(TownshipObj tw) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Townshipid, tw.getTownshipid());
        cv.put(Townshipname, tw.getName());
        cv.put(Township_sortid, tw.getSort_id());
        db.insert(TownshipTable, null, cv);
    }

    private void AddAgent(Salesmen_TypeObj st) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Salesmen_type, st.getSalesmen_type());
        cv.put(Salesmen_type_name, st.getName());
        cv.put(salesmentype_delivery_system, st.isDelivery_system());
        cv.put(salesmentype_delivery_charges, st.isDelivery_charges());
        db.insert(SalesmenType_Table, null, cv);
    }

    private void AddDeliveryMan(Salesmen sm) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DeliveryMan_id, sm.getsalesmen_id());
        cv.put(DeliveryMan_name, sm.getsalesmen_name());
        cv.put(DeliveryMan_AgentID, sm.getSalesmen_type());

        db.insert(DeliveryMan_Table, null, cv);
    }

    private void AddDeliverySetup(DeliverySetupObj ds) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DeliverySetup_Townshipid, ds.getTownshipid());
        cv.put(DeliverySetup_delivery_charges, ds.getDelivery_charges());
        cv.put(DeliverySetup_free_range, ds.getFree_range());
        cv.put(DeliverySetup_estimate_time, ds.getEstimate_time());
        db.insert(DeliverySetupTable, null, cv);
    }

    private void AddDeliveryEntryTmp(DeliveryEntryObj dt) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DeliveryEntry_tranid, dt.getTranid());
        cv.put(DeliveryEntry_townshipid, dt.getTownshipid());
        cv.put(DeliveryEntry_order_customer_detail, dt.getOrder_customer_detail());
        cv.put(DeliveryEntry_agent_id, dt.getAgent_id());
        cv.put(DeliveryEntry_deliveryman_id, dt.getDeliveryman_id());
        cv.put(DeliveryEntry_use_calc_delivery_charges, dt.isUse_calc_delivery_charges());
        cv.put(DeliveryEntry_order_datetime, dt.getOrder_datetime());
        cv.put(DeliveryEntry_estimate_time, dt.getEstimate_time());
        cv.put(DeliveryEntry_remark, dt.getRemark());
        cv.put(DeliveryEntry_isDeliver, dt.isDeliver());
        cv.put(DeliveryEntry_org_deliverycharges, dt.getOrg_deliverycharges());
        cv.put(DeliveryEntry_org_delivery_freerange, dt.getOrg_delviery_freerange());
        cv.put(DeliveryEntry_updated, dt.isUpdated());

        db.insert(DeliveryEntry_Table, null, cv);
    }
    //endregion //add table

    //endregion  //end delivery


    //region Self Order //added WHM [2020-05-18]

    //region methods
    boolean use_tabletSelfOrder_CashierApprove() {
        SQLiteDatabase db = this.getWritableDatabase();

        String sSql = "SELECT %s FROM %s where %s = '%s' ";
        Cursor cursor = db.rawQuery(String.format(sSql, colSetting_Value,
                AppSetting_table, colSetting_Name, "Tablet_SelfOrder_CashierApprove"), null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    if (cursor.getString(0).equals("Y"))
                        return true;
                    else
                        return false;
                } while (cursor.moveToNext());
            }
            // return contact list
            return false;
        } finally {
            cursor.close();
        }

    }

    //added WHM [2020-05-19] self order
    public Boolean check_selfordertable(Integer TableID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sSql = "SELECT %s FROM %s where %s = %s ";
        sSql = String.format(sSql, colSelfOrderTable, ActiveTables, colTable_Name_ID,
                TableID);

        Cursor cursor = db.rawQuery(sSql, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    if ((cursor.getString(0).equals("1"))) {
                        return true;
                    } else
                        return false;

                } while (cursor.moveToNext());
            }
            // return contact list
            return false;
        } finally {
            cursor.close();
        }

    }

    boolean use_Tablet_Menu(String userid) {
        SQLiteDatabase db = this.getWritableDatabase();

       /* String sSql = "SELECT %s FROM %s where %s = '%s' ";
        Cursor cursor = db.rawQuery(String.format(sSql, colSetting_Value,
                AppSetting_table, colSetting_Name, "use_Tablet_Menu"), null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    if(cursor.getString(0).equals("Y") )
                        return true;
                    else
                        return false;
                } while (cursor.moveToNext());
            }
            // return contact list
            return false;
        } finally {
            cursor.close();
        }*/

        //added by EKK on 18-09-2020
        String sSql = "SELECT use_menuonly FROM %s where userid = %s and use_menuonly = 1";
        Cursor cursor = db.rawQuery(String.format(sSql,
                PosUserTable, userid), null);

        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    return true;
                } while (cursor.moveToNext());
            }
            // return contact list
            return false;
        } finally {
            cursor.close();
        }


    }

    String getmenuItemViewCount(String fields) {
        SQLiteDatabase db = this.getWritableDatabase();
        String field_count = "";
        String selectQuery;
        if (fields.equals("row"))
            selectQuery = "SELECT " + colmenuitem_rowcount + " FROM "
                    + serviceaddressTable;
        else
            selectQuery = "SELECT " + colmenuitem_colcount + " FROM "
                    + serviceaddressTable;

        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    field_count = cursor.getString(0);
                } while (cursor.moveToNext());
            }

            // return contact list

            if (field_count == null)
                field_count = "2";
            return field_count;

        } finally {
            cursor.close();
        }

    }

    void Add_MenuItemViewCount(String fields, String count) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        if (fields.equals("row"))
            cv.put(colmenuitem_rowcount, count);
        else
            cv.put(colmenuitem_colcount, count);


        if (getServiceURL().equals("")) {
            db.execSQL("Delete from  " + serviceaddressTable);
            db.insert(serviceaddressTable, null, cv);
        } else {
            db.update(serviceaddressTable, cv, null, null);
        }
        db.close();
    }


    //endregin //end methods

    //endregion


    //region currency table //added WHM [2020-05-27] currency
    public void LoadCurrencyTable(String Datalink) {
        url = Datalink;
        JSONArray itemjsonarray = Json_class.getJson(url
                + "/Data/GetCurrency");
        //Added by KLM for UniZawgyi Auto Detect 23022022
        try {
            if (!LoginActivity.isUnicode) {
                itemjsonarray = new JSONArray(Rabbit.uni2zg(itemjsonarray.toString()));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        ClearTable(CurrencyTable);
        for (int i = 0; i < itemjsonarray.length(); i++) {
            JSONObject c;
            try {
                c = itemjsonarray.getJSONObject(i);

                CurrencyObj cObj = new CurrencyObj();
                cObj.setCurrency(Integer.valueOf(c.getString(colCurr_currency)));
                cObj.setName(c.getString(colCurr_Name));
                cObj.setCurr_short(c.getString(colCurr_curr_short));
                cObj.setExg_rate(c.getString(colCurr_exg_rate));
                cObj.setRoundTo(Integer.valueOf(c.getString(colCurr_roundTo)));
                AddCurrencyTable(cObj);

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private void AddCurrencyTable(CurrencyObj cobj) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(colCurr_curr_short, cobj.getCurrency());
        cv.put(colCurr_Name, cobj.getName());
        cv.put(colCurr_curr_short, cobj.getCurr_short());
        cv.put(colCurr_exg_rate, cobj.getExg_rate());
        cv.put(colCurr_roundTo, cobj.getRoundTo());

        db.insert(CurrencyTable, null, cv);
    }

    public List<CurrencyObj> GetCurrency() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<CurrencyObj> list = new ArrayList<CurrencyObj>();
        String query = "select * from currency";

        Cursor cursor = db.rawQuery(query, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    CurrencyObj cobj = new CurrencyObj();
                    cobj.setCurrency(Integer.parseInt(cursor.getString(0)));
                    cobj.setName(cursor.getString(1));
                    cobj.setCurr_short(cursor.getString(2));
                    cobj.setExg_rate(cursor.getString(3));
                    cobj.setRoundTo(Integer.parseInt(cursor.getString(4)));

                    list.add(cobj);
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
        }
        return list;
    }
    //endregion currency table


    //added WHM [2020-08-18] special password
    String get_specialpassword() {
        SQLiteDatabase db = this.getWritableDatabase();
        String URL = "";
        String selectQuery = "SELECT " + colspecial_password + " FROM "
                + serviceaddressTable;
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    // Customer contact = new Customer();
                    // contact.setID(Integer.parseInt(cursor.getString(0)));
                    // contact.setName(cursor.getString(1));
                    // contact.setPhoneNumber(cursor.getString(2));
                    // Adding contact to list
                    URL = cursor.getString(0);
                } while (cursor.moveToNext());
            }

            // return contact list
            if (URL == null)
                URL = "";
            return URL;
        } finally {
            cursor.close();
        }

    }

    public String get_title() {
        SQLiteDatabase db = this.getWritableDatabase();
        String value = "";
        String selectQuery = "SELECT " + coltitle + " FROM " + serviceaddressTable;
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    // Customer contact = new Customer();
                    // contact.setID(Integer.parseInt(cursor.getString(0)));
                    // contact.setName(cursor.getString(1));
                    // contact.setPhoneNumber(cursor.getString(2));
                    // Adding contact to list
                    value = cursor.getString(0);
                } while (cursor.moveToNext());
            }

            // return contact list
            if (value == null)
                value = "";
            return value;
        } finally {
            cursor.close();
        }

    }

    //region menucategorygroup //added WHM [2020-09-29]

    //method
    int use_MenuCategoryGroupType() {
        SQLiteDatabase db = this.getWritableDatabase();

        String sSql = "SELECT %s FROM %s where %s = '%s'";
        Cursor cursor = db.rawQuery(String.format(sSql, colSetting_Value,
                AppSetting_table, colSetting_Name, "use_MenuCategoryGroupType"), null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    GlobalClass.use_menucategorygrouptype = cursor.getInt(0);
                    return cursor.getInt(0);

                } while (cursor.moveToNext());
            }
            // return contact list
            return 0;
        } finally {
            cursor.close();
        }

    }

    public void str_category(int areaLoc_ID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String strcategory = "";

        String sSql = "SELECT GROUP_CONCAT(%s) FROM %s where %s = %s ";
        Cursor cursor;
        if (GlobalClass.use_menucategorygrouptype == 1) {
            cursor = db.rawQuery(String.format(sSql, menucateLoc_category,
                    menucategory_groupbyLocTable, menucateLoc_locationid, areaLoc_ID), null);
        } else {
            cursor = db.rawQuery(String.format(sSql, menucateArea_category,
                    menucategory_groupbyareaTable, menucateArea_areaID, areaLoc_ID), null);
        }

        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    strcategory = cursor.getString(0);

                } while (cursor.moveToNext());
            }

            if (strcategory == null || strcategory.equals("null") || strcategory.equals("0") || strcategory.equals(""))
                GlobalClass.strcategoryIn = "";
            else
                GlobalClass.strcategoryIn = strcategory;

        } finally {
            cursor.close();
        }

    }


    //load table
    public void LoadMenuCategory_GroupByArea(String Datalink) {
        url = Datalink;
        JSONArray itemjsonarray = Json_class.getJson(url
                + "/Data/GetMenuCategoryGroupByArea");

        //Added by KLM for UniZawgyi Auto Detect 23022022
        try {
            if (!LoginActivity.isUnicode) {
                itemjsonarray = new JSONArray(Rabbit.uni2zg(itemjsonarray.toString()));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ClearTable(menucategory_groupbyareaTable);
        for (int i = 0; i < itemjsonarray.length(); i++) {
            JSONObject c;
            try {
                c = itemjsonarray.getJSONObject(i);

                MenuCategory_GroupByAreaObj areaObj = new MenuCategory_GroupByAreaObj();
                areaObj.setMenucateArea_areaId(Integer.valueOf(c.getString(menucateArea_areaID)));
                areaObj.setMenucateArea_category(Integer.valueOf(c.getString(menucateArea_category)));

                AddMenuCategory_GroupByArea(areaObj);

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void LoadMenuCategory_GroupByLoc(String Datalink) {
        url = Datalink;
        JSONArray itemjsonarray = Json_class.getJson(url
                + "/Data/GetMenuCategoryGroupByLocation");

        //Added by KLM for UniZawgyi Auto Detect 23022022
        try {
            if (!LoginActivity.isUnicode) {
                itemjsonarray = new JSONArray(Rabbit.uni2zg(itemjsonarray.toString()));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ClearTable(menucategory_groupbyLocTable);
        for (int i = 0; i < itemjsonarray.length(); i++) {
            JSONObject c;
            try {
                c = itemjsonarray.getJSONObject(i);

                MenuCategory_GroupByLocObj locObj = new MenuCategory_GroupByLocObj();
                locObj.setMenucateLoc_locationid(Integer.valueOf(c.getString(menucateLoc_locationid)));
                locObj.setMenucateLoc_category(Integer.valueOf(c.getString(menucateLoc_category)));

                AddMenuCategory_GroupByLoc(locObj);

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    //add table
    private void AddMenuCategory_GroupByArea(MenuCategory_GroupByAreaObj areaObj) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(menucateArea_areaID, areaObj.getMenucateArea_areaId());
        cv.put(menucateArea_category, areaObj.getMenucateArea_category());
        db.insert(menucategory_groupbyareaTable, null, cv);
    }

    private void AddMenuCategory_GroupByLoc(MenuCategory_GroupByLocObj locObj) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(menucateLoc_locationid, locObj.getMenucateLoc_locationid());
        cv.put(menucateLoc_category, locObj.getMenucateLoc_category());
        db.insert(menucategory_groupbyLocTable, null, cv);
    }

    //endregion menucategorygroup

    //region userarea //added WHM [2020-11-11]
    public void LoadArea_GroupByUser(String Datalink) {
        url = Datalink;
        JSONArray itemjsonarray = Json_class.getJson(url
                + "/Data/GetAreaGroupByUser");

        //Added by KLM for UniZawgyi Auto Detect 23022022
        try {
            if (!LoginActivity.isUnicode) {
                itemjsonarray = new JSONArray(Rabbit.uni2zg(itemjsonarray.toString()));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ClearTable(areauser_Table);
        for (int i = 0; i < itemjsonarray.length(); i++) {
            JSONObject c;
            try {
                c = itemjsonarray.getJSONObject(i);

                Area_GroupByUserObj areaObj = new Area_GroupByUserObj();
                areaObj.setAreauser_userid(Integer.valueOf(c.getString(areauser_userid)));
                areaObj.setAreauser_area_id(Integer.valueOf(c.getString(areauser_area_id)));

                AddArea_GroupByUser(areaObj);

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private void AddArea_GroupByUser(Area_GroupByUserObj areaObj) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(areauser_userid, areaObj.getAreauser_userid());
        cv.put(areauser_area_id, areaObj.getAreauser_area_id());
        db.insert(areauser_Table, null, cv);
    }

    public void get_strgetarea_groupbyuser(String userid) {
        SQLiteDatabase db = this.getWritableDatabase();
        String strgetarea = "";

        String sSql = "SELECT GROUP_CONCAT(%s) FROM %s where %s = %s ";

        if (use_areagroupbyuser() == true) {


            Cursor cursor;
            cursor = db.rawQuery(String.format(sSql, areauser_area_id,
                    areauser_Table, areauser_userid, userid), null);


            try {
                // looping through all rows and adding to list
                if (cursor.moveToFirst()) {
                    do {
                        strgetarea = cursor.getString(0);

                    } while (cursor.moveToNext());
                }

                if (strgetarea == null || strgetarea.equals("null") || strgetarea.equals("0") || strgetarea.equals(""))
                    GlobalClass.strgetarea_groupbyuser = " ";
                else
                    GlobalClass.strgetarea_groupbyuser = " where area_id in (" + strgetarea + ") ";

            } finally {
                cursor.close();
            }
        } else {
            GlobalClass.strgetarea_groupbyuser = " ";
        }


    }

    boolean use_areagroupbyuser() {
        SQLiteDatabase db = this.getWritableDatabase();

        String sSql = "SELECT %s FROM %s where %s = '%s' ";
        Cursor cursor = db.rawQuery(String.format(sSql, colSetting_Value,
                AppSetting_table, colSetting_Name, "use_areagroupbyuser"), null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    if (cursor.getString(0).equals("Y"))
                        return true;
                    else
                        return false;
                } while (cursor.moveToNext());
            }
            // return contact list
            return false;
        } finally {
            cursor.close();
        }
    }
    //endregion userarea

    //region FoodTruck
    // added by ZYP [26-10-2020]
    public boolean use_foodtruck(String userid) {
        SQLiteDatabase db = this.getWritableDatabase();

//		String sSql = "SELECT %s FROM %s where %s = '%s' and %s ='%s'";
//		Cursor cursor = db.rawQuery(String.format(sSql, colSetting_Value,
//				AppSetting_table, colSetting_Name, "use_foodtruck", colSetting_Value, "Y"), null);
//		try {
//			// looping through all rows and adding to list
//			if (cursor.moveToFirst()) {
//				do {
//					return true;
//				} while (cursor.moveToNext());
//			}
//
//			return false;
//		} finally {
//			cursor.close();
//		}

        String sSql = "SELECT use_foodtruck FROM %s where userid = %s and use_foodtruck = 1";
        Cursor cursor = db.rawQuery(String.format(sSql,
                PosUserTable, userid), null);

        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    return true;
                } while (cursor.moveToNext());
            }
            // return contact list
            return false;
        } finally {
            cursor.close();
        }
    }

    public boolean use_foodtrucklocation(String userid) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sSql = "SELECT use_foodtrucklocation FROM %s where userid = %s and use_foodtrucklocation = 1";
        Cursor cursor = db.rawQuery(String.format(sSql,
                PosUserTable, userid), null);

        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    return true;
                } while (cursor.moveToNext());
            }
            // return contact list
            return false;
        } finally {
            cursor.close();
        }
    }

    public List<SaleObj> LoadInvoicesList(String Datalink, String userid) {
        url = Datalink;
        JSONArray itemjsonarray = Json_class.getJson(url
                + "/Data/GetMultiInvoices?userid="
                + java.net.URLEncoder.encode(userid));

        List<SaleObj> saleObjList = new ArrayList<SaleObj>();

        for (int i = 0; i < itemjsonarray.length(); i++) {
            JSONObject c;
            try {
                c = itemjsonarray.getJSONObject(i);

                SaleObj saleObj = new SaleObj();

                String tableid = "";
                tableid = c.getString("tablename_id");

                if (tableid.equals("null"))
                    tableid = "Parcel";

                saleObj.setsaleid(c.getString("tranid"));
                saleObj.setinvoiceno(c.getString("invoiceno"));
                saleObj.settablenameid(tableid);
                saleObj.setroomid(c.getString("roomid"));
                saleObj.setcustomerid(c.getString("customerid"));
                saleObj.setamount(c.getString("invoice_amount"));
                saleObj.setdate(c.getString("Date"));
                saleObj.setcustcount(c.getString(colcustcount));
                saleObj.setuserid(Integer.parseInt(c.getString(coluserid)));
                saleObj.setsalesmen_id(Integer.parseInt(c.getString("salemanid").equals("null") ? "0" : c.getString("salemanid")));
                saleObj.setRef_no(c.getString(colRef_no));
                saleObj.setDelivery_type(Integer.parseInt(c.getString("delivery_type")));

                saleObjList.add(saleObj);

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return saleObjList;
    }

    //added by ZYP [18/11/2020] foodtruck save sale_head & sale_det
    public void UpdateSaledata(int tranid, SaleObj saleobj, List<SaleItemObj> saleitemobjlist) {

        if (tranidexist_SaleTable(tranid)) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(colinvoiceno, saleobj.getinvoiceno());
            cv.put(colTable_Name_ID, saleobj.gettablenameid());
            cv.put(colroomid, saleobj.getroomid());
            cv.put(colcustomerid, saleobj.getcustomerid());
            cv.put(colamount, saleobj.getamount());
            cv.put(colsaledate, saleobj.getdate());
            cv.put(coluserid, saleobj.getuserid());
            cv.put(colsalesmen_id, saleobj.getsalesmen_id());
            cv.put(colDelivery_type, saleobj.getDelivery_type());
            cv.put(colsubmitflag, false);
            db.update(SaleTable, cv, colsaleid + " = ? ", new String[]{String.valueOf(tranid)});
            db.close();

        } else {

            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(colsaleid, tranid);
            cv.put(colinvoiceno, saleobj.getinvoiceno());
            cv.put(colTable_Name_ID, saleobj.gettablenameid());
            cv.put(colroomid, saleobj.getroomid());
            cv.put(colcustomerid, saleobj.getcustomerid());
            cv.put(colamount, saleobj.getamount());
            cv.put(colsaledate, saleobj.getdate());
            cv.put(coluserid, saleobj.getuserid());
            cv.put(colsalesmen_id, saleobj.getsalesmen_id());
            cv.put(colDelivery_type, saleobj.getDelivery_type());
            cv.put(colsubmitflag, false);

            db.insert(SaleTable, null, cv);
            db.close();
        }

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(SaleItemTable, colsaleid + "=" + tranid, null);

        for (SaleItemObj saleItemObj : saleitemobjlist) {

            ContentValues cvitem = new ContentValues();
            cvitem.put(colsaleid, tranid);
            cvitem.put(colsr, saleItemObj.getsr());
            cvitem.put(colsrno, saleItemObj.getsrno().replace(".", "").trim());
            cvitem.put(colcode, saleItemObj.getcode());
            cvitem.put(colprice, saleItemObj.getprice());
            cvitem.put(colqty, saleItemObj.getqty());
            cvitem.put(colamount, saleItemObj.getamount());
            cvitem.put(colmodifiedrowsr, saleItemObj.getmodifiedrowsr());
            cvitem.put(colunittype, saleItemObj.getunittype());
            cvitem.put(coltakeaway, saleItemObj.gettaway());
            cvitem.put(colremark, saleItemObj.getremark());
            cvitem.put(colsubmitflag, saleItemObj.getSubmitflag());
            // ///
            db.insert(SaleItemTable, null, cvitem);

        }

    }

    boolean tranidexist_SaleTable(int tranid) {
        SQLiteDatabase db = this.getWritableDatabase();

        String sSql = "SELECT " + colsaleid + " FROM " + SaleTable
                + " where " + colsaleid + "=" + tranid + ";";
        Cursor cursor = db.rawQuery(sSql, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    return true;
                } while (cursor.moveToNext());
            }
            // return contact list
            return false;
        } finally {
            cursor.close();
        }

    }

    boolean tranidexist_SaleItemTable(int tranid) {
        SQLiteDatabase db = this.getWritableDatabase();

        String sSql = "SELECT " + colsaleid + " FROM " + SaleItemTable
                + " where " + colsaleid + "=" + tranid + ";";
        Cursor cursor = db.rawQuery(sSql, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    return true;
                } while (cursor.moveToNext());
            }
            // return contact list
            return false;
        } finally {
            cursor.close();
        }

    }

    public int getmaxsr(int tranid) {
        SQLiteDatabase db = this.getWritableDatabase();

        String sSql = "SELECT IFNULL(MAX(" + colsr + "),0) FROM " + SaleItemTable
                + " where " + colsaleid + "=" + tranid + ";";
        Cursor cursor = db.rawQuery(sSql, null);
        try {

            if (cursor.moveToFirst()) {
                do {
                    return cursor.getInt(0) + 1;
                } while (cursor.moveToNext());
            }

            return 1;
        } finally {
            cursor.close();
        }

    }

    public Boolean updateRemark(int tranid, String SaleItemId, String Remark) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(colsaleid, tranid);
            cv.put(colsaleitemid, SaleItemId);
            cv.put(colremark, Remark);
            db.update(SaleTable, cv, colsaleid + " = ? ", new String[]{String.valueOf(tranid)});
            db.close();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public Boolean ConfirmSaledata(int tranid) {

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(colsubmitflag, true);
            db.update(SaleTable, cv, colsaleid + " = ? ", new String[]{String.valueOf(tranid)});
            db.close();
            return true;
        } catch (Exception ex) {
            return false;
        }

    }

    public double defGovernmentTax() {
        SQLiteDatabase db = this.getWritableDatabase();

        String sSql = "SELECT %s FROM %s WHERE %s = '%s' AND (SELECT %s FROM %s where %s = '%s') = 'Y' ";
        Cursor cursor = db.rawQuery(String.format(sSql,
                colSetting_Value, AppSetting_table, colSetting_Name, "Default_GovermentTax_percent",
                colSetting_Value, AppSetting_table, colSetting_Name, "Government_Tax"), null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {

                    return Double.parseDouble(cursor.getString(0));
                } while (cursor.moveToNext());
            }
            // return contact list

        } finally {
            cursor.close();
        }

        return 0.0;

    }

    public double defServiceTax() {
        SQLiteDatabase db = this.getWritableDatabase();

        String sSql = "SELECT %s FROM %s WHERE %s = '%s' AND (SELECT %s FROM %s where %s = '%s') = 'Y' ";
        Cursor cursor = db.rawQuery(String.format(sSql,
                colSetting_Value, AppSetting_table, colSetting_Name, "Default_ServiceTax_Percent",
                colSetting_Value, AppSetting_table, colSetting_Name, "Service_Tax"), null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {

                    return Double.parseDouble(cursor.getString(0));
                } while (cursor.moveToNext());
            }
            // return contact list

        } finally {
            cursor.close();
        }

        return 0.0;

    }

    public boolean Tax_Exclusive() {
        SQLiteDatabase db = this.getWritableDatabase();

        String sSql = "SELECT %s FROM %s where %s = '%s' and %s ='%s'";
        Cursor cursor = db.rawQuery(String.format(sSql, colSetting_Value,
                AppSetting_table, colSetting_Name, "Tax_Exclusive", colSetting_Value, "Y"), null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    return true;
                } while (cursor.moveToNext());
            }

            return false;
        } finally {
            cursor.close();
        }
    }

    public List<String> RecipeHeaderFooter() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<String> values = new ArrayList<String>();
        String sSql = "SELECT %s FROM %s where %s = '%s' OR %s ='%s' OR %s ='%s' OR %s ='%s' OR %s ='%s' OR %s ='%s'";
        Cursor cursor = db.rawQuery(String.format(sSql, colSetting_Value, AppSetting_table,
                colSetting_Name, "RECEIPT_HEADER_LINE_1", colSetting_Name, "RECEIPT_HEADER_LINE_2", colSetting_Name, "RECEIPT_HEADER_LINE_3",
                colSetting_Name, "RECEIPT_FOOTER_LINE_1", colSetting_Name, "RECEIPT_FOOTER_LINE_2", colSetting_Name, "RECEIPT_FOOTER_LINE_3"
        ), null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    values.add(cursor.getString(0));
                } while (cursor.moveToNext());
            }

            return values;

        } finally {
            cursor.close();
        }
    }

    //endregion FoodTruck

    //added by MPPA [27-01-2021]
    public void unSoldoutItem(String code) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(SoldOutTable, colsoldoutcode + " = " + Integer.parseInt(code), null);
        db.close();
    }

    //added by MPPA 21-01-2021 SaleHistoryList
    public List<SaleObj> SaleHistoryList(String Datalink, String userid) {
        url = Datalink;

        JSONArray itemjsonarray = Json_class.getJson(url
                + "/Data/SaleHistory?userid="
                + java.net.URLEncoder.encode(userid));

        List<SaleObj> saleObjList = new ArrayList<SaleObj>();

        for (int i = 0; i < itemjsonarray.length(); i++) {
            JSONObject c;
            try {
                c = itemjsonarray.getJSONObject(i);

                SaleObj saleObj = new SaleObj();

                String tableid = "";
                tableid = c.getString("tablename_id");

                if (tableid.equals("null"))
                    tableid = "Parcel";

                saleObj.setsaleid(c.getString("tranid"));
                saleObj.setinvoiceno(c.getString("invoiceno"));
                saleObj.settablenameid(tableid);
                saleObj.setroomid(c.getString("roomid"));
                saleObj.setcustomerid(c.getString("customerid"));
                saleObj.setamount(c.getString("invoice_amount"));
                saleObj.setdate(c.getString("Date"));
                saleObj.setcustcount(c.getString(colcustcount));
                saleObj.setuserid(Integer.parseInt(c.getString(coluserid)));
                saleObj.setsalesmen_id(Integer.parseInt(c.getString("salemanid").equals("null") ? "0" : c.getString("salemanid")));
                saleObj.setRef_no(c.getString(colRef_no));
                saleObj.setDelivery_type(Integer.parseInt(c.getString("delivery_type")));

                saleObjList.add(saleObj);

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return saleObjList;
    }

    //added by MPPA [28-01-2021]
    public void updateItemrating(String usr_code, double itemrating) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(" UPDATE " + ItemTable + " set " + colItemrating + " = '" + itemrating + "' WHERE usr_code= '" + usr_code + "'");
        db.execSQL(" UPDATE " + ItemTable + " set " + colRatingcount + " = IFNULL(" + colRatingcount + ",0)+1 WHERE usr_code= '" + usr_code + "'");

        ItemsObj itemsObj = new ItemsObj();
        itemsObj = getItemsbyUsrCode(usr_code);
        Json_class jsonclass = new Json_class();
        jsonclass.getString(getServiceURL() + "/Data/GetItemRating?usr_code=" +
                java.net.URLEncoder.encode(usr_code) +
                "&itemrating=" + java.net.URLEncoder.encode(itemsObj.getItemrating()) +
                "&ratingcount=" + java.net.URLEncoder.encode(itemsObj.getRatingcount()));
        db.close();
    }

    public double getItemrating(String usr_code) {
        SQLiteDatabase db = this.getWritableDatabase();
        //String selectQuery = "SELECT IFNULL(" + colitemrating + ",0) itemrating, IfNULL("+colratingcount+",0) ratingcount FROM ItemTable";
        String selectQuery = "SELECT IFNULL(" + colItemrating + ",0) itemrating FROM " + ItemTable + " WHERE usr_code= '" + usr_code + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            String data = "";
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    data = cursor.getString(0);
                } while (cursor.moveToNext());
            }
            // return contact list
            if (data == null || data == "")
                data = "0";
            return Double.parseDouble(data);
        } finally {
            cursor.close();
        }
    }

    //added by MPPA on 28-04-2021 itemobjlist
    public ItemsObj getItemsbyUsrCode(String usr_code) {
        SQLiteDatabase db = this.getWritableDatabase();
        ItemsObj obj = new ItemsObj();
        String selectQuery = "SELECT IFNULL(" + colItemrating + ",0) itemrating, IFNULL(" + colRatingcount + ",0) ratingcount FROM " +
                ItemTable + " WHERE usr_code= '" + usr_code + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    //obj.setusr_code(cursor.getString(0));
                    obj.setItemrating(cursor.getString(0));
                    obj.setRatingcount(cursor.getString(1));
                } while (cursor.moveToNext());
            }
            return obj;
        } finally {
            cursor.close();
        }

    }

    //Added by MPPA
    boolean GetSetMenuOrgQty() {
        SQLiteDatabase db = this.getWritableDatabase();

        String sSql = "SELECT %s FROM %s where %s = '%s' and %s ='%s'";
        Cursor cursor = db.rawQuery(String.format(sSql, colSetting_Value,
                AppSetting_table, colSetting_Name, "SetMenuOrgQty", colSetting_Value, "Y"), null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    return true;
                } while (cursor.moveToNext());
            }
            return false;
        } finally {
            cursor.close();
        }
    }

    //added by MPPA 14-06-2021
    public void updatesoldoutitem(String usr_code) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            Json_class jsonclass = new Json_class();
            jsonclass.getString(getServiceURL() + "/Data/SoldOutItem?usr_code=" +
                    java.net.URLEncoder.encode(usr_code));
            db.close();
        } finally {
            // TODO Auto-generated catch block

        }

    }

    //added by MPPA [16-06-2021]
    public void updateunSoldoutItem(String usr_code) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            Json_class json_class = new Json_class();
            json_class.getString(getServiceURL() + "/Data/unSoldoutitem?usr_code=" +
                    java.net.URLEncoder.encode(usr_code));
            db.close();
        } finally {
            // TODO Auto-generated catch block
        }
    }

    public String getAppSetting(String Setting_Name) {   //Added by ZYP [04-06-2021]
        SQLiteDatabase db = this.getWritableDatabase();

        String sSql = "SELECT %s FROM %s where %s = '%s'";
        Cursor cursor = db.rawQuery(String.format(sSql, colSetting_Value,
                AppSetting_table, colSetting_Name, Setting_Name), null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    String value = cursor.getString(0);
                    return value == null ? "" : value;
                } while (cursor.moveToNext());
            }
            // return contact list
            return "";
        } finally {
            cursor.close();
        }

    }

    public String AddMemberDiscount(String membercard, int tranid) {

        String result = "";
        try {
            Json_class jsonclass = new Json_class();
            JSONArray jsonmessage = jsonclass.getJson(getServiceURL() + "/Data/AddMemberDiscount?tranid="
                    + tranid + "&membercard="
                    + java.net.URLEncoder.encode(membercard));

            if (jsonmessage != null && jsonmessage.length() > 0) {
                if (Integer.parseInt(jsonmessage.get(0).toString()) == 0) {
                    result = jsonmessage.get(1).toString();

                } else {
                    result = jsonmessage.get(2).toString();
                }
            }

        } catch (Exception e) {
            result = "Error occur while saving.";
        }

        return result;
    }
}