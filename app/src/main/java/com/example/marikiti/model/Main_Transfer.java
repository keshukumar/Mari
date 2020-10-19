package com.example.marikiti.model;

public class Main_Transfer {

    static String shop_id;
    static String shop_name;
    static String image_name;
    static String trader_id;

    static String item_id;
    static String item_name;
    static String item_image;

    static String MAIN_CATEGORY_ID;


    public static String getItem_id() {
        return item_id;
    }

    public static void setItem_id(String item_id) {
        Main_Transfer.item_id = item_id;
    }

    public static String getItem_name() {
        return item_name;
    }

    public static void setItem_name(String item_name) {
        Main_Transfer.item_name = item_name;
    }

    public static String getItem_image() {
        return item_image;
    }

    public static void setItem_image(String item_image) {
        Main_Transfer.item_image = item_image;
    }

    public static String getShop_id() {
        return shop_id;
    }

    public static void setShop_id(String shop_id) {
        Main_Transfer.shop_id = shop_id;
    }

    public static String getShop_name() {
        return shop_name;
    }

    public static void setShop_name(String shop_name) {
        Main_Transfer.shop_name = shop_name;
    }

    public static String getImage_name() {
        return image_name;
    }

    public static void setImage_name(String image_name) {
        Main_Transfer.image_name = image_name;
    }

    public Main_Transfer() {
    }

    public static String getTrader_id() {
        return trader_id;
    }

    public static void setTrader_id(String trader_id) {
        Main_Transfer.trader_id = trader_id;
    }

    public static String getMainCategoryId() {
        return MAIN_CATEGORY_ID;
    }

    public static void setMainCategoryId(String mainCategoryId) {
        MAIN_CATEGORY_ID = mainCategoryId;
    }
}
