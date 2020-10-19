package com.example.marikiti.activity.MyAccounts;

public class Sale_Order_Model {


    public String cart_id;
     public String full_name;
     public String phone_no;
     public String  image_name;
     public String description;
     public String price;
     public String cart_date;
     public String product_code;

 public Sale_Order_Model(String cart_id, String full_name, String phone_no, String image_name, String description, String price, String cart_date,String product_code) {
  this.cart_id = cart_id;
  this.full_name = full_name;
  this.phone_no = phone_no;
  this.image_name = image_name;
  this.description = description;
  this.price = price;
  this.cart_date = cart_date;
  this.product_code=product_code;
 }

 public String getCart_id() {
  return cart_id;
 }

 public void setCart_id(String cart_id) {
  this.cart_id = cart_id;
 }

 public String getFull_name() {
  return full_name;
 }

 public void setFull_name(String full_name) {
  this.full_name = full_name;
 }

 public String getPhone_no() {
  return phone_no;
 }

 public void setPhone_no(String phone_no) {
  this.phone_no = phone_no;
 }

 public String getImage_name() {
  return image_name;
 }

 public void setImage_name(String image_name) {
  this.image_name = image_name;
 }

 public String getDescription() {
  return description;
 }

 public void setDescription(String description) {
  this.description = description;
 }

 public String getPrice() {
  return price;
 }

 public void setPrice(String price) {
  this.price = price;
 }

 public String getCart_date() {
  return cart_date;
 }

 public void setCart_date(String cart_date) {
  this.cart_date = cart_date;
 }

 public String getProduct_code() {
  return product_code;
 }

 public void setProduct_code(String product_code) {
  this.product_code = product_code;
 }
}
