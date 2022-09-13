package com.galaxy.restaurantpos;

public class CurrencyObj {

    int currency;
    String name;
    String curr_short;
    String exg_rate;
    int roundTo;

    public int getCurrency() {
        return currency;
    }

    public void setCurrency(int currency) {
        this.currency = currency;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurr_short() {
        return curr_short;
    }

    public void setCurr_short(String curr_short) {
        this.curr_short = curr_short;
    }

    public String getExg_rate() {
        return exg_rate;
    }

    public void setExg_rate(String exg_rate) {
        this.exg_rate = exg_rate;
    }

    public int getRoundTo() {
        return roundTo;
    }

    public void setRoundTo(int roundTo) {
        this.roundTo = roundTo;
    }
}
