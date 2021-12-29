package com.example.e_voting_admin.CandidatesModel;

import com.example.e_voting_admin.Constituency_Manage.Constituency;
import com.example.e_voting_admin.Parties_Manage.Parties;

public class CandidateDetails {
    private int id;
    private String name;
    private String CNIC;
    private String province;
    private String city;
    private String area;
    private Parties party;
    private Constituency constituency;

    public CandidateDetails() {
        id = 0;
        name = "";
        city = "";
        party = new Parties();
        constituency = new Constituency();
    }

    public CandidateDetails(String name, String city) {
        this.name = name;
        this.city = city;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Parties getParty() {
        return party;
    }

    public void setParty(Parties party) {
        this.party = party;
    }

    public Constituency getConstituency() {
        return constituency;
    }

    public void setConstituency(Constituency constituency) {
        this.constituency = constituency;
    }

    @Override
    public String toString() {
        return "Candidates{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", constituency='" + constituency.toString() + '\'' +
                ", party='" + party.toString() + '\'' +
                '}';
    }
}
