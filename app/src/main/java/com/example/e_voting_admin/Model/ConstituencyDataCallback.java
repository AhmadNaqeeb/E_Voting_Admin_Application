package com.example.e_voting_admin.Model;

import com.example.e_voting_admin.Constituency_Manage.Constituency;

import java.util.List;

public interface ConstituencyDataCallback {
    public void onConstituencyDataRetrieved(List<Constituency> constituencyDataList , String nodeName);
}
