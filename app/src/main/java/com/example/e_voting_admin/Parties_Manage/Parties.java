package com.example.e_voting_admin.Parties_Manage;

public class Parties {
    private int id;
    private String name;
    private String symbol;

    public Parties() {
        id = 0;
        name = "";
        symbol = "";
    }

    public Parties(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
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

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return name;
    }
}
