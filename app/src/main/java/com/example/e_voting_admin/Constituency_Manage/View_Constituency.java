package com.example.e_voting_admin.Constituency_Manage;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_voting_admin.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class View_Constituency extends AppCompatActivity {

    RecyclerView rvConstituency;
    ConstituencyAdopter constituencyAdopter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__constituency);

        rvConstituency = findViewById(R.id.rvConstituency);
        rvConstituency.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Constituency> options =
                new FirebaseRecyclerOptions.Builder<Constituency>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("EVotingDB").child("0").child("Constituency"), Constituency.class)
                        .build();
        Log.d("SIZE: " , String.valueOf(options.getSnapshots().size()));
        constituencyAdopter = new ConstituencyAdopter(options, this);
        rvConstituency.setAdapter(constituencyAdopter);
    }
    @Override
    protected void onStart() {
        super.onStart();
        constituencyAdopter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        constituencyAdopter.stopListening();
    }
}