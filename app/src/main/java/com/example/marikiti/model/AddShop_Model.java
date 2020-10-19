package com.example.marikiti.model;

public class AddShop_Model {
    private String Shop_id;
    private String shop_name;
    private String shop_image_name;


    public AddShop_Model(String shop_id, String shop_name, String shop_image_name) {
        Shop_id = shop_id;
        this.shop_name = shop_name;
        this.shop_image_name = shop_image_name;
    }


    public String getShop_id() {
        return Shop_id;
    }

    public void setShop_id(String shop_id) {
        Shop_id = shop_id;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getShop_image_name() {
        return shop_image_name;
    }

    public void setShop_image_name(String shop_image_name) {
        this.shop_image_name = shop_image_name;
    }
}
