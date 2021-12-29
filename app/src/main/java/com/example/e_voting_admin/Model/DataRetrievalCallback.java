package com.example.e_voting_admin.Model;

import java.util.List;
import java.util.Map;

public interface DataRetrievalCallback {

    public void onDataRetrieved(List<Map<String , String>> data , String nodeName);
}
