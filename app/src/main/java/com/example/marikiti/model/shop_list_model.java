package com.example.marikiti.model;

public class shop_list_model {

    public String other_shop_id;
    public String  other_shop_name;
    public String other_shop_image;

    public shop_list_model(String other_shop_id, String other_shop_name, String other_shop_image) {
        this.other_shop_id = other_shop_id;
        this.other_shop_name = other_shop_name;
        this.other_shop_image = other_shop_image;
    }

    public String getOther_shop_id() {
        return other_shop_id;
    }

    public void setOther_shop_id(String other_shop_id) {
        this.other_shop_id = other_shop_id;
    }

    public String getOther_shop_name() {
        return other_shop_name;
    }

    public void setOther_shop_name(String other_shop_name) {
        this.other_shop_name = other_shop_name;
    }

    public String getOther_shop_image() {
        return other_shop_image;
    }

    public void setOther_shop_image(String other_shop_image) {
        this.other_shop_image = other_shop_image;
    }
}
