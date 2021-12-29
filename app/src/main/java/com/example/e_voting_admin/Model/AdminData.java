package com.example.e_voting_admin.Model;

public class AdminData {
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String Name;
    private String Mobile;
    private String CNIC;
    private String Password;

    public AdminData() {
    }

    public AdminData(String name, String mobile, String CNIC, String password) {
        Name = name;
        Mobile = mobile;
        this.CNIC = CNIC;
        Password = password;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getCNIC() {
        return CNIC;
    }

    public void setCNIC(String CNIC) {
        this.CNIC = CNIC;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
