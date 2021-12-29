
package com.example.e_voting_admin.Parties_Manage;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_voting_admin.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;


public class PartyAdopter extends FirebaseRecyclerAdapter<Parties, PartyAdopter.PartyViewHolder> {

    String logoSymbol;
    Context context;
    public PartyAdopter(@NonNull FirebaseRecyclerOptions<Parties> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull PartyViewHolder holder, int position, @NonNull Parties model) {

        int resID = 0;
        if (model.getSymbol() != null){
            resID = context.getResources().getIdentifier(model.getSymbol(), "drawable",context.getPackageName());
        }
        if (resID != 0)
        {
            holder.imgParty.setImageResource(resID);
        }
        holder.tvName.setText(model.getName());

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogPlus dialog = DialogPlus.newDialog(context)
                        .setContentHolder(new ViewHolder(R.layout.update_party))
                        .setGravity(Gravity.CENTER)
                        .setMargin(0,30,0,30)
                        .setExpanded(false)  // This will enable the expand feature, (similar to android L share dialog)
                        .create();

                View view = dialog.getHolderView();

                EditText updateParty;
                MaterialSpinner logoSpinner;
                Button btnUpdate;
                updateParty = view.findViewById(R.id.updParty);
                logoSpinner = view.findViewById(R.id.logoSpinner);
                btnUpdate = view.findViewById(R.id.btnUpdate);

                String [] symbols = {"Select Symbol", "bat", "tiger", "arrow", "bicycle", "kite", "jui","independent"};
                int index = 0;

                for (String s: symbols)
                {
                    if (s.equalsIgnoreCase(model.getSymbol()))
                    {
                        break;
                    }
                    index++;
                }
                logoSpinner.setItems(symbols);
                logoSpinner.setSelectedIndex(index);

                logoSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                        logoSymbol = item.toString();
                    }
                });

                updateParty.setText(model.getName());
                dialog.show();

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Map<String, Object> map = new HashMap<>();
                        map.put("name", updateParty.getText().toString().trim());
                        map.put("symbol", logoSymbol);

                        FirebaseDatabase.getInstance().getReference()
                                .child("EVotingDB").child("0").child("Parties")
                                .child(getRef(position).getKey())
                                .updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(context, "DataUpdate Successfully", Toast.LENGTH_SHORT).show();
                                        model.setName(updateParty.getText().toString());
                                        model.setSymbol(logoSymbol);
                                        dialog.dismiss();
                                    }
                                });
                    }
                });
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference()
                        .child("EVotingDB").child("0").child("Parties")
                        .child(getRef(position).getKey())
                        .removeValue()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(context, "Data Delete Successfully", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
    @NonNull
    @Override
    public PartyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.party_list_design, parent, false);

        return new PartyViewHolder(view);
    }
    public class PartyViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvName;
        ImageButton btnEdit,btnDelete;
        ImageView imgParty;
        public PartyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            imgParty = itemView.findViewById(R.id.imgParty);
        }
    }
}

