package com.example.marikiti.model;

public class Shop_Trader_List_model {

    public String tra_full_name;
    public String tra_phone_no;
    public String user_code;
    public String trader_id;
    public String profile_pic;
    public String h_name;
    public String street;
    public String ward;

    public Shop_Trader_List_model(String tra_full_name, String tra_phone_no, String user_code, String trader_id, String profile_pic,String h_name,String street,String ward)
    {
        this.tra_full_name = tra_full_name;
        this.tra_phone_no = tra_phone_no;
        this.user_code = user_code;
        this.trader_id = trader_id;
        this.profile_pic = profile_pic;
        this.h_name=h_name;
        this.street=street;
        this.ward=ward;
    }

    public String getH_name() {
        return h_name;
    }

    public void setH_name(String h_name) {
        this.h_name = h_name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getTra_full_name() {
        return tra_full_name;
    }

    public void setTra_full_name(String tra_full_name) {
        this.tra_full_name = tra_full_name;
    }

    public String getTra_phone_no() {
        return tra_phone_no;
    }

    public void setTra_phone_no(String tra_phone_no) {
        this.tra_phone_no = tra_phone_no;
    }

    public String getUser_code() {
        return user_code;
    }

    public void setUser_code(String user_code) {
        this.user_code = user_code;
    }

    public String getTrader_id() {
        return trader_id;
    }

    public void setTrader_id(String trader_id) {
        this.trader_id = trader_id;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }
}
