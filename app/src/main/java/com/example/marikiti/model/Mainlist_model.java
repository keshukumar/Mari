package com.example.marikiti.model;

public class Mainlist_model {

    public String   shop_id;
    public String shop_name;
    public String image_name;


    public Mainlist_model(String shop_id, String shop_name, String image_name) {
        this.shop_id = shop_id;
        this.shop_name = shop_name;
        this.image_name = image_name;
    }

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }
}
