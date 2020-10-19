package com.example.marikiti.activity.MyAccounts;

public class Product_Unit {

public String product_size_id;
public String product_qunatity;
public String product_price;
public String unit_price;
public String action_status;

    public Product_Unit(String product_size_id, String product_qunatity, String product_price, String action_status,String unit_price) {
        this.product_size_id = product_size_id;
        this.product_qunatity = product_qunatity;
        this.product_price = product_price;
        this.action_status = action_status;
        this.unit_price=unit_price;
    }

    public String getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(String unit_price) {
        this.unit_price = unit_price;
    }

    public String getProduct_size_id() {
        return product_size_id;
    }

    public void setProduct_size_id(String product_size_id) {
        this.product_size_id = product_size_id;
    }

    public String getProduct_qunatity() {
        return product_qunatity;
    }

    public void setProduct_qunatity(String product_qunatity) {
        this.product_qunatity = product_qunatity;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getAction_status() {
        return action_status;
    }

    public void setAction_status(String action_status) {
        this.action_status = action_status;
    }
}
