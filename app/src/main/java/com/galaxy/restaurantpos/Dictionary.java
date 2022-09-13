package com.galaxy.restaurantpos;
import com.google.myanmartools.*;

import me.myatminsoe.mdetect.MDetect;
import me.myatminsoe.mdetect.Rabbit;

public class Dictionary { //modified by EKK on 10/07/2019
	private Integer _sr;
	private String _language1;
	private String _language2;
	private String _language3; //added by EKK
    public static final TransliterateZ2U Uniconverter = new TransliterateZ2U("Zawgyi to Unicode");
    public static final TransliterateU2Z uni2zg = new TransliterateU2Z("Unicode to Zawgyi");
    //added by ZYP for Unicode convert

	public Integer getsr() {
        return _sr;
    }
    public void setsr(Integer sr) {
        this._sr = sr;
    }
    public String getLanguage1() {
        return _language1;
    }
    public void setLanguage1(String language1) {
        this._language1 = language1;
    }

//    public String getLanguage2() {
//        return _language2;
//    }
    public String getLanguage2() {
        return /*LoginActivity.isUnicode? Rabbit.zg2uni(_language2):*/_language2;
    }
    public void setLanguage2(String language2) {
        this._language2 = language2;
    }

    //added by EKK
    public String getLanguage3() {
        return _language3;
    }
    public void setLanguage3(String language3) {
        this._language3 = language3;
    }
}
