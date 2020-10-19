package com.example.marikiti.model;

public class Supermarket_model {

    public String id;
    public String product_category;
    public String product_name;
    public String product_code;
    public String price;
    public String description;
    public String image_name;

    public Supermarket_model(String id, String product_category, String product_name, String product_code, String price, String description, String image_name) {
        this.id = id;
        this.product_category = product_category;
        this.product_name = product_name;
        this.product_code = product_code;
        this.price = price;
        this.description = description;
        this.image_name = image_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProduct_category() {
        return product_category;
    }

    public void setProduct_category(String product_category) {
        this.product_category = product_category;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_code() {
        return product_code;
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }
}
