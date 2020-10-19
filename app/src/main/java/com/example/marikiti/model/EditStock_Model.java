package com.example.marikiti.model;

public class EditStock_Model {


    public  String trader_shop_id;
    public  String shop_name;
    public  String image_name;

    public EditStock_Model() {
    }

    public EditStock_Model(String trader_shop_id, String shop_name, String image_name) {
        this.trader_shop_id = trader_shop_id;
        this.shop_name = shop_name;
        this.image_name = image_name;
    }

    public String getTrader_shop_id() {
        return trader_shop_id;
    }

    public void setTrader_shop_id(String trader_shop_id) {
        this.trader_shop_id = trader_shop_id;
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
