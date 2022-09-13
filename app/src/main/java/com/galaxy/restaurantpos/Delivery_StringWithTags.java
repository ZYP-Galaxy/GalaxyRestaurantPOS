package com.galaxy.restaurantpos;

public class Delivery_StringWithTags {
    public String string;
    public Integer tag;

    public Delivery_StringWithTags(String string, Integer tag) {
        this.string = string;
        this.tag = tag;
    }

    @Override
    public String toString() {
        return string;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public Integer getTag() {
        return tag;
    }

    public void setTag(Integer tag) {
        this.tag = tag;
    }
}
