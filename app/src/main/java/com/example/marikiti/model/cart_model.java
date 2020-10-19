package com.example.marikiti.model;

public class cart_model {

    public String id;
    public String shopname;

    public String image_name;
    public String item_name;
    public String product_category;
    public String mdescription;

    public cart_model() {
    }

    public cart_model(String id, String shopname) {
        this.id = id;
        this.shopname = shopname;
    }

    public cart_model(String image_name, String item_name, String product_category, String mdescription) {
        this.image_name = image_name;
        this.item_name = item_name;
        this.product_category = product_category;
        this.mdescription = mdescription;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
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
