package com.galaxy.restaurantpos;

public class DeliveryEntryObj {
    private int tranid;
    private int townshipid;
    private String order_customer_detail;
    private int agent_id;
    private int deliveryman_id;
    private boolean use_calc_delivery_charges;
    private String order_datetime;
    private int estimate_time;
    private String remark;
    private boolean isDeliver;
    private String org_deliverycharges;
    private String org_delviery_freerange;
    private boolean updated;
    private int Delivery_payment;

    public int getDelivery_payment() {
        return Delivery_payment;
    }

    public void setDelivery_payment(int delivery_payment) {
        Delivery_payment = delivery_payment;
    }



    public int getTranid() {
        return tranid;
    }

    public void setTranid(int tranid) {
        this.tranid = tranid;
    }

    public int getTownshipid() {
        return townshipid;
    }

    public void setTownshipid(int townshipid) {
        this.townshipid = townshipid;
    }

    public String getOrder_customer_detail() {
        return order_customer_detail;
    }

    public void setOrder_customer_detail(String order_customer_detail) {
        this.order_customer_detail = order_customer_detail;
    }

    public int getAgent_id() {
        return agent_id;
    }

    public void setAgent_id(int agent_id) {
        this.agent_id = agent_id;
    }

    public int getDeliveryman_id() {
        return deliveryman_id;
    }

    public void setDeliveryman_id(int deliveryman_id) {
        this.deliveryman_id = deliveryman_id;
    }

    public boolean isUse_calc_delivery_charges() {
        return use_calc_delivery_charges;
    }

    public void setUse_calc_delivery_charges(boolean use_calc_delivery_charges) {
        this.use_calc_delivery_charges = use_calc_delivery_charges;
    }

    public String getOrder_datetime() {
        return order_datetime;
    }

    public void setOrder_datetime(String order_datetime) {
        this.order_datetime = order_datetime;
    }

    public int getEstimate_time() {
        return estimate_time;
    }

    public void setEstimate_time(int estimate_time) {
        this.estimate_time = estimate_time;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean isDeliver() {
        return isDeliver;
    }

    public void setDeliver(boolean deliver) {
        isDeliver = deliver;
    }

    public String getOrg_deliverycharges() {
        return org_deliverycharges;
    }

    public void setOrg_deliverycharges(String org_deliverycharges) {
        this.org_deliverycharges = org_deliverycharges;
    }

    public String getOrg_delviery_freerange() {
        return org_delviery_freerange;
    }

    public void setOrg_delviery_freerange(String org_delviery_freerange) {
        this.org_delviery_freerange = org_delviery_freerange;
    }

    public boolean isUpdated() {
        return updated;
    }

    public void setUpdated(boolean updated) {
        this.updated = updated;
    }
}
