package com.galaxy.restaurantpos;

public class Salesmen_TypeObj {
    private int salesmen_type;
    private String name;
    private boolean delivery_system;
    private boolean delivery_charges;

    public int getSalesmen_type() {
        return salesmen_type;
    }

    public void setSalesmen_type(int salesmen_type) {
        this.salesmen_type = salesmen_type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDelivery_system() {
        return delivery_system;
    }

    public void setDelivery_system(boolean delivery_system) {
        this.delivery_system = delivery_system;
    }

    public boolean isDelivery_charges() {
        return delivery_charges;
    }

    public void setDelivery_charges(boolean delivery_charges) {
        this.delivery_charges = delivery_charges;
    }
}
