package com.galaxy.restaurantpos;

public class DeliverySetupObj {
    private int Townshipid;
    private String delivery_charges;
    private String free_range;
    private int estimate_time;

    public int getTownshipid() {
        return Townshipid;
    }

    public void setTownshipid(int townshipid) {
        Townshipid = townshipid;
    }

    public String getDelivery_charges() {
        return delivery_charges;
    }

    public void setDelivery_charges(String delivery_charges) {
        this.delivery_charges = delivery_charges;
    }

    public String getFree_range() {
        return free_range;
    }

    public void setFree_range(String free_range) {
        this.free_range = free_range;
    }

    public int getEstimate_time() {
        return estimate_time;
    }

    public void setEstimate_time(int estimate_time) {
        this.estimate_time = estimate_time;
    }
}
