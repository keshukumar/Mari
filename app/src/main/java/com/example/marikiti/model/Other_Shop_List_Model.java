package com.example.marikiti.model;

public class Other_Shop_List_Model {

    public String other_shop_id;
    public String other_shop_name;
    public String other_shop_image;


    public String item_id;
    public String item_name;
    public String item_image;
    public String empty;


    public Other_Shop_List_Model(String other_shop_id, String other_shop_name, String other_shop_image) {
        this.other_shop_id = other_shop_id;
        this.other_shop_name = other_shop_name;
        this.other_shop_image = other_shop_image;
    }

    public Other_Shop_List_Model(String item_id, String item_name, String item_image, String empty) {
        this.item_id = item_id;
        this.item_name = item_name;
        this.item_image = item_image;
        this.empty = empty;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
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
