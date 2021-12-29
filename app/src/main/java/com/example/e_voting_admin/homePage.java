package com.example.e_voting_admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.e_voting_admin.CandidatesModel.Manage_Candidates;
import com.example.e_voting_admin.Constituency_Manage.Manage_Constituency;
import com.example.e_voting_admin.Parties_Manage.Manage_Party;

public class homePage extends AppCompatActivity {

    Button ManageParty, ManageCandidate, ManageConstituency, SignOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        ManageConstituency = findViewById(R.id.ManageConstituency);
        ManageCandidate = findViewById(R.id.ManageCandidate);
        ManageParty = findViewById(R.id.manageParty);
        SignOut = findViewById(R.id.SignOut);

        ManageParty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(homePage.this, Manage_Party.class);
                startActivity(intent);
            }
        });
        ManageConstituency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(homePage.this, Manage_Constituency.class);
                startActivity(intent);
            }
        });
        ManageCandidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(homePage.this, Manage_Candidates.class));
            }
        });
        SignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.loggedInAdmin = null;
                Intent loginIntent = new Intent(homePage.this, MainActivity.class);
                startActivity(loginIntent);
                finish();
            }
        });
    }
}