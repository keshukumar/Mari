package com.example.marikiti.model;


public class MyCart {


    public String cart_id;
    public String quantity;
    public String size;
    public String product_name;
    public String price;
    public String image_name;
    public String product_code;

    public MyCart(String cart_id, String quantity, String size, String product_name, String price, String image_name, String product_code) {
        this.cart_id = cart_id;
        this.quantity = quantity;
        this.size = size;
        this.product_name = product_name;
        this.price = price;
        this.image_name = image_name;
        this.product_code = product_code;
    }

    public String getCart_id() {
        return cart_id;
    }

    public void setCart_id(String cart_id) {
        this.cart_id = cart_id;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }

    public String getProduct_code() {
        return product_code;
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }
}
