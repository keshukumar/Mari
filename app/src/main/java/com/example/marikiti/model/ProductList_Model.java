package com.example.marikiti.model;

public class ProductList_Model {

    public String trader_shop_item_list_id;
    public String item_name;
    public String item_image;

    public ProductList_Model(String trader_shop_item_list_id, String item_name, String item_image) {
        this.trader_shop_item_list_id = trader_shop_item_list_id;
        this.item_name = item_name;
        this.item_image = item_image;
    }

    public String getTrader_shop_item_list_id() {
        return trader_shop_item_list_id;
    }

    public void setTrader_shop_item_list_id(String trader_shop_item_list_id) {
        this.trader_shop_item_list_id = trader_shop_item_list_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_image() {
        return item_image;
    }

    public void setItem_image(String item_image) {
        this.item_image = item_image;
    }
}
