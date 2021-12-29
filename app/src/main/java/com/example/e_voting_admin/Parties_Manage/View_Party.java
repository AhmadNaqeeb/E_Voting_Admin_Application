package com.example.e_voting_admin.Parties_Manage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.e_voting_admin.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class View_Party extends AppCompatActivity {

    RecyclerView rvParty;
    PartyAdopter partyAdopter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__party);

        rvParty = findViewById(R.id.rvParty);
        rvParty.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Parties> options =
                new FirebaseRecyclerOptions.Builder<Parties>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("EVotingDB").child("0").child("Parties"), Parties.class)
                        .build();
        Log.d("SIZE: " , String.valueOf(options.getSnapshots().size()));
        partyAdopter = new PartyAdopter(options, this);
        rvParty.setAdapter(partyAdopter);

    }
    @Override
    protected void onStart() {
        super.onStart();
        partyAdopter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        partyAdopter.stopListening();
    }
}