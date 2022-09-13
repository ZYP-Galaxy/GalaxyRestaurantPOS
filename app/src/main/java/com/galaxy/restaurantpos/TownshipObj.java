package com.galaxy.restaurantpos;

public class TownshipObj {
    private int Townshipid;
    private String name;
    private int sort_id;

    public int getTownshipid() {
        return Townshipid;
    }

    public void setTownshipid(int townshipid) {
        Townshipid = townshipid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSort_id() {
        return sort_id;
    }

    public void setSort_id(int sort_id) {
        this.sort_id = sort_id;
    }
}
