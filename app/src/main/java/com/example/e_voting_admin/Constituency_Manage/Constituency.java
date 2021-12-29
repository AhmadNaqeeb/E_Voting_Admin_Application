package com.example.e_voting_admin.Constituency_Manage;

public class Constituency {
    private int id;
    private String name;
    private String parent;
    private String city;
    private String province;
    private String area;

    public Constituency() {
        id = 0;
        name = "";
        parent = "";
        city = "";
        province = "";
        area = "";
    }

    public Constituency(String name, String parent,String city,String province, String area) {
        this.name = name;
        this.parent = parent;
        this.city = city;
        this.province = province;
        this.area = area;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    @Override
    public String toString() {
        return this.name + " - " + this.parent ;
    }
}
