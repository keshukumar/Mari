package com.example.marikiti.activity.MyShops;

public class View_Product_Model {

public String trader_shop_item_category_id;
public String image_name;
public String product_category;
public String mdescription;

    public View_Product_Model(String trader_shop_item_category_id, String image_name, String product_category, String mdescription) {
        this.trader_shop_item_category_id = trader_shop_item_category_id;
        this.image_name = image_name;
        this.product_category = product_category;
        this.mdescription = mdescription;
    }

    public String getTrader_shop_item_category_id() {
        return trader_shop_item_category_id;
    }

    public void setTrader_shop_item_category_id(String trader_shop_item_category_id) {
        this.trader_shop_item_category_id = trader_shop_item_category_id;
    }

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }

    public String getProduct_category() {
        return product_category;
    }

    public void setProduct_category(String product_category) {
        this.product_category = product_category;
    }

    public String getMdescription() {
        return mdescription;
    }

    public void setMdescription(String mdescription) {
        this.mdescription = mdescription;
    }
}
