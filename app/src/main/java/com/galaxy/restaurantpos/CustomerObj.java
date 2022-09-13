package com.galaxy.restaurantpos;

import me.myatminsoe.mdetect.MDetect;

public class CustomerObj {
	private Integer _customerid;
	private String _customercode;	
	private String _customername;
	private String _company_name;
	private String _customercredit;

    private Integer _customer_pricelevel;//added WHM [2020-01-27] MDY2-200141
	
	public Integer getcustomerid() {
        return _customerid;
    }
    public void setcustomerid(Integer customerid) {
        this._customerid = customerid;
    }
    public String getcustomercode() {

        return _customercode;
//        return MDetect.INSTANCE.getText(Dictionary.Uniconverter.convert(_customercode));
	    //return _customercode;
    }
    public void setcustomercode(String customercode) {
        this._customercode = customercode;
    }

    public String getcustomername() {

	    return _customername;
//        return MDetect.INSTANCE.getText(Dictionary.Uniconverter.convert(_customername));
	    //return _customername;
    }
    public void setcustomername(String customername) {
        this._customername = customername;
    }
    
    //Added by Arkar Moe on [14/09/2016]-[Res-0398]
    public String getcompanyname() {

        return _company_name;
//        return MDetect.INSTANCE.getText(Dictionary.Uniconverter.convert(_company_name));
	    //return _company_name;
    }
    public void setcompanyname(String companyname) {
        this._company_name = companyname;
    }
    //[Res-0398]
	public String getcustomercredit() {
		return _customercredit;
	}
	public void setcustomercredit(String _customercredit) {
		this._customercredit = _customercredit;
	}


    public Integer get_customer_pricelevel() {
        return _customer_pricelevel;
    }

    public void set_customer_pricelevel(Integer _customer_pricelevel) {
        this._customer_pricelevel = _customer_pricelevel;
    }
}
