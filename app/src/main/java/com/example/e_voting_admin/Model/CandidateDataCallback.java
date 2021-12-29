package com.example.e_voting_admin.Model;

import com.example.e_voting_admin.CandidatesModel.Candidates;

import java.util.List;
import java.util.Map;

public interface CandidateDataCallback {
    public void onCandidateDataRetrieved(List<Candidates> candidateDataList , String nodeName);
}
