package com.example.e_voting_admin.Model;

public class VoterModel {

    String Name;
    String Age;
    String CNIC;
    String Mobile;
    String Address;
    String Password;
    String Vote;
    String Party;
    String Vote1;
    String Party1;

    public String getVote1() {
        return Vote1;
    }

    public void setVote1(String vote1) {
        Vote1 = vote1;
    }

    public String getParty1() {
        return Party1;
    }

    public void setParty1(String party1) {
        Party1 = party1;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getCNIC() {
        return CNIC;
    }

    public void setCNIC(String CNIC) {
        this.CNIC = CNIC;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getVote() {
        return Vote;
    }

    public void setVote(String vote) {
        Vote = vote;
    }

    public String getParty() {
        return Party;
    }

    public void setParty(String party) {
        Party = party;
    }

    public VoterModel() {
    }

    public VoterModel(String name, String age, String CNIC, String mobile, String address, String password, String vote, String party,String vote1,String party1) {
        Name = name;
        Age = age;
        this.CNIC = CNIC;
        Mobile = mobile;
        Address = address;
        Password = password;
        Vote = vote;
        Party = party;
        Vote1 = vote1;
        Party1 = party1;
    }
}
