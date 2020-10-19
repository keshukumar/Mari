package com.example.marikiti.model;


public class MyLoanStatement {
    private String pImage;
    private String traderName;
    private String traderMobile;
    private String productname;
    private String price;

    public String getTraderMobile() {
        return traderMobile;
    }

    public void setTraderMobile(String traderMobile) {
        this.traderMobile = traderMobile;
    }



    public String getpImage() {
        return pImage;
    }

    public void setpImage(String pImage) {
        this.pImage = pImage;
    }

    public String getTraderName() {
        return traderName;
    }

    public void setTraderName(String traderName) {
        this.traderName = traderName;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
