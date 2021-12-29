package com.example.e_voting_admin.CandidatesModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.e_voting_admin.Constituency_Manage.Constituency;
import com.example.e_voting_admin.DataBase.EVotingDBContract;
import com.example.e_voting_admin.DataBase.FirebaseDBHandler;
import com.example.e_voting_admin.Model.CandidateDataCallback;
import com.example.e_voting_admin.Parties_Manage.Parties;
import com.example.e_voting_admin.R;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class View_Candidates extends AppCompatActivity implements CandidateDataCallback, CandidatesAdopter.OnItemClick {
    private RecyclerView rvCandidates;
    private CandidatesAdopter candidatesAdopter;
    private List<CandidateDetails> allRegisteredCandidatesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__candidates);

        rvCandidates = findViewById(R.id.rvCandidates);
        rvCandidates.setLayoutManager(new LinearLayoutManager(this));
        initCandidateData();
    }

    private void initCandidateData(){
        Log.d("View_Candidates_Act","Init Data");
        FirebaseDBHandler.mCandidateDataCallback = this;
        FirebaseDBHandler.getCandidateNodeAllChildren("0",EVotingDBContract.CANDIDATE_NODE);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onCandidateDataRetrieved(List<Candidates> candidateDataList, String nodeName) {

        if (candidateDataList != null && candidateDataList.size() > 0){

            List<CandidateDetails> candidateDetails = new ArrayList<>();
            for (Candidates candidates : candidateDataList){
                CandidateDetails newCandidateDetails = new CandidateDetails();
                newCandidateDetails.setId(candidates.getId());
                newCandidateDetails.setCNIC(candidates.getCNIC());
                newCandidateDetails.setName(candidates.getName());
                newCandidateDetails.setCity(candidates.getCity());
                newCandidateDetails.setArea(candidates.getArea());
                newCandidateDetails.setProvince(candidates.getProvince());
                Log.d("View_Candidates: ", candidates.getConstituency());
                Log.d("View_Candidates: ", candidates.getParty());
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    Parties parties = objectMapper.readValue(candidates.getParty(), Parties.class);
                    Constituency constituency = objectMapper.readValue(candidates.getConstituency(), Constituency.class);
                    newCandidateDetails.setConstituency(constituency);
                    newCandidateDetails.setParty(parties);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                candidateDetails.add(newCandidateDetails);
            }
            Log.d("View_Candidates: ", String.valueOf(candidateDetails.size()));
             candidatesAdopter = new CandidatesAdopter(candidateDetails, this, this);

             rvCandidates.setAdapter(candidatesAdopter);
        }

    }

    @Override
    public void onClick() {
        candidatesAdopter.notifyDataSetChanged();
    }
}