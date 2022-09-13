package com.galaxy.restaurantpos;

public class Printer {
	private Integer _PrinterID;
	public Integer getPrinterID() {
        return _PrinterID;
    }
	public void setPrinterID(Integer PrinterID) {
        this._PrinterID = PrinterID;
    }
	
	private String _PrinterName;
	public String getPrinterName() {
        return _PrinterName;
    }
	public void setPrinterName(String PrinterName) {
        this._PrinterName = PrinterName;
    }
	
	private String _Printer;
	public String getPrinter() {
        return _Printer;
    }
	public void setPrinter(String Printer) {
        this._Printer = Printer;
    }

    private String _PrinterStatus;
    public String get_PrinterStatus() {
        return _PrinterStatus;
    }

    public void set_PrinterStatus(String _PrinterStatus) {
        this._PrinterStatus = _PrinterStatus;
    }

}
