package com.example.e_voting_admin.CandidatesModel;


public class Candidates {
    private int id;
    private String name;
    private String CNIC;
    private String province;
    private String city;
    private String area;
    private String constituency;
    private String party;

    public Candidates() {
        id = 0;
        name = "";
    }

    public Candidates(String name, String CNIC,String province , String city, String area, String constituency, String party) {
        this.name = name;
        this.CNIC = CNIC;
        this.province = province;
        this.city = city;
        this.area = area;
        this.constituency = constituency;
        this.party = party;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCNIC() {
        return CNIC;
    }

    public void setCNIC(String CNIC) {
        this.CNIC = CNIC;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
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

    public String getConstituency() {
        return constituency;
    }

    public void setConstituency(String constituency) {
        this.constituency = constituency;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    @Override
    public String toString() {
        return "Candidates{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", CNIC='" + CNIC + '\'' +
                ", city='" + city + '\'' +
                ", area='" + area + '\'' +
                ", constituency='" + constituency.toString() + '\'' +
                ", party='" + party.toString() + '\'' +
                '}';
    }
}
