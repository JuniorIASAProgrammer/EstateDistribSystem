package com.realestate.serviceestate.repo.model;

public class Description {

    private String city;
    private String district;
    private String address;

    public Description() {
    }

    public Description(String city, String district, String address) {
        this.city = city;
        this.district = district;
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isEmpty() {
        return !(this.city != null || this.district != null || this.address != null);
    }
}
