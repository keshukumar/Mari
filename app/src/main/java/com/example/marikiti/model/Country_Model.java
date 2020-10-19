package com.example.marikiti.model;

public class Country_Model {

    private String county_id;
    private String country_name;

    public Country_Model(String county_id, String country_name) {
        this.county_id = county_id;
        this.country_name = country_name;
    }

    public String getCounty_id() {
        return county_id;
    }

    public void setCounty_id(String county_id) {
        this.county_id = county_id;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }
}
