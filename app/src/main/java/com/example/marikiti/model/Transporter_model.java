package com.example.marikiti.model;

public class Transporter_model {




    public String profile_pic;
    public String trader_id;
    public String tra_full_name;
    public String tra_phone_no;
    public String user_code;

    public Transporter_model(String profile_pic, String trader_id, String tra_full_name, String tra_phone_no, String user_code) {
        this.profile_pic = profile_pic;
        this.trader_id = trader_id;
        this.tra_full_name = tra_full_name;
        this.tra_phone_no = tra_phone_no;
        this.user_code = user_code;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public String getTrader_id() {
        return trader_id;
    }

    public void setTrader_id(String trader_id) {
        this.trader_id = trader_id;
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
}
