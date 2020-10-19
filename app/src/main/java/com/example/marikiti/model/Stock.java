package com.example.marikiti.model;

public class Stock {

    public static String trader_shop_id;
    public static String shop_name;
    public static String image_name;
    public static String trader_shop_item_list_id;
    public static String item_name;
    public static String item_image;

    public static String getTrader_shop_item_list_id() {
        return trader_shop_item_list_id;
    }

    public static void setTrader_shop_item_list_id(String trader_shop_item_list_id) {
        Stock.trader_shop_item_list_id = trader_shop_item_list_id;
    }

    public static String getItem_name() {
        return item_name;
    }

    public static void setItem_name(String item_name) {
        Stock.item_name = item_name;
    }

    public static String getItem_image() {
        return item_image;
    }

    public static void setItem_image(String item_image) {
        Stock.item_image = item_image;
    }

    public static String getTrader_shop_id() {
        return trader_shop_id;
    }

    public static void setTrader_shop_id(String trader_shop_id) {
        Stock.trader_shop_id = trader_shop_id;
    }

    public static String getShop_name() {
        return shop_name;
    }

    public static void setShop_name(String shop_name) {
        Stock.shop_name = shop_name;
    }

    public static String getImage_name() {
        return image_name;
    }

    public static void setImage_name(String image_name) {
        Stock.image_name = image_name;
    }
}
