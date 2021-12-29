package com.example.e_voting_admin.CandidatesModel;

import android.content.Context;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CandidatesAdopter extends RecyclerView.Adapter<CandidatesAdopter.CandidatesViewHolder>{

    private Context context;
    private OnItemClick mCallback;
    String province = "", city = "", area = "";
    private List<CandidateDetails> allRegisteredCandidatesList = new ArrayList<>();
    public CandidatesAdopter(@NonNull List<CandidateDetails> allRegisteredCandidatesList , Context context, OnItemClick itemClick) {
        this.allRegisteredCandidatesList = allRegisteredCandidatesList;
        this.context = context;
        this.mCallback = itemClick;
    }

    @Override
    public void onBindViewHolder(CandidatesViewHolder holder, int position) {
        CandidateDetails model = allRegisteredCandidatesList.get(position);
        int resID = 0;
        if (model.getParty().getSymbol() != null){
            resID = context.getResources().getIdentifier(model.getParty().getSymbol(), "drawable",context.getPackageName());
        }
        holder.candidateName.setText(model.getName());
        holder.candidateParty.setText(model.getParty().getName());
        holder.candidateCity.setText(model.getProvince() + ", " + model.getCity() + ", " + model.getArea());
        holder.candidateConstituency.setText(model.getConstituency().getName());
        if (resID != 0)
        {
            holder.partySymbol.setImageResource(resID);
        }
        holder.updateBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogPlus dialog = DialogPlus.newDialog(context)
                        .setContentHolder(new ViewHolder(R.layout.update_candidates))
                        .setGravity(Gravity.CENTER)
                        .setMargin(0,30,0,30)
                        .setExpanded(false)  // This will enable the expand feature, (similar to android L share dialog)
                        .create();

                View view = dialog.getHolderView();

                EditText updateCandidate;
                //MaterialSpinner citySpinner, areaSpinner;
                Button buttonUpdate;
                updateCandidate = view.findViewById(R.id.updateCandidate);
                //citySpinner = view.findViewById(R.id.updateProvince);
                //areaSpinner = view.findViewById(R.id.updateArea);
                buttonUpdate = view.findViewById(R.id.buttonUpdate);

                updateCandidate.setText(model.getName());

//                String [] city = {"Select City", "Lahore", "Islamabad", "Karachi"};
//                int index = 0;
//                boolean isFound = false;
//                for (String s: city)
//                {
//                    if (s.equalsIgnoreCase(model.getCity()))
//                    {
//                        CandidatesAdopter.this.city = s;
//                        isFound = true;
//                        break;
//                    }
//                    index++;
//                }
//                citySpinner.setItems(city);
//                if (isFound){
//                    citySpinner.setSelectedIndex(index);
//                }
//
//                citySpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
//                        CandidatesAdopter.this.city = item.toString();
//                    }
//                });
//
//                String [] area = {"Select Area", "DHA Phase I", "DHA Phase II", "DHA Phase III", "Gulberg I","Gulberg II"};
//                index = 0;
//                isFound = false;
//                for (String s: area)
//                {
//                    if (s.equalsIgnoreCase(model.getCity()))
//                    {
//                        CandidatesAdopter.this.area = s;
//                        isFound = true;
//                        break;
//                    }
//                    index++;
//                }
//                areaSpinner.setItems(area);
//                if (isFound){
//                    areaSpinner.setSelectedIndex(index);
//                }
//
//                areaSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
//                        CandidatesAdopter.this.area = item.toString();
//                    }
//                });

                dialog.show();

                buttonUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Map<String, Object> map = new HashMap<>();
                        map.put("name", updateCandidate.getText().toString().trim());
//                        map.put("city", CandidatesAdopter.this.city);
//                        map.put("area", CandidatesAdopter.this.area);

                        FirebaseDatabase.getInstance().getReference()
                                .child("EVotingDB").child("0").child("Candidates")
                                .child(String.valueOf(model.getId()))
                                .updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(context, "DataUpdate Successfully", Toast.LENGTH_SHORT).show();
                                        allRegisteredCandidatesList.get(position).setName(updateCandidate.getText().toString().trim());
//                                        allRegisteredCandidatesList.get(position).setCity(CandidatesAdopter.this.city);
//                                        allRegisteredCandidatesList.get(position).setArea(CandidatesAdopter.this.area);
                                        dialog.dismiss();
                                        mCallback.onClick();
                                    }
                                });
                    }
                });
            }
        });
        holder.deleteBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference()
                        .child("EVotingDB").child("0").child("Candidates")
                        .child(String.valueOf(allRegisteredCandidatesList.get(position).getId()))
                        .removeValue()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(context, "Data Delete Successfully", Toast.LENGTH_SHORT).show();
                                allRegisteredCandidatesList.remove(position);
                                mCallback.onClick();
                            }
                        });
            }
        });
    }

    @Override
    public int getItemCount() {
        return allRegisteredCandidatesList.size();
    }

    @NonNull
    @Override
    public CandidatesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.candidates_list_design, parent, false);
        return new CandidatesViewHolder(view);
    }

    public class CandidatesViewHolder extends RecyclerView.ViewHolder
    {
        TextView candidateName, candidateCity, candidateConstituency, candidateParty;
        ImageButton updateBTN, deleteBTN;
        ImageView partySymbol;

        public CandidatesViewHolder(@NonNull View itemView) {
            super(itemView);
            candidateName = itemView.findViewById(R.id.candidateName);
            candidateParty = itemView.findViewById(R.id.candidateParty);
            candidateCity = itemView.findViewById(R.id.candidateCity);
            candidateConstituency = itemView.findViewById(R.id.candidateConstituency);
            updateBTN = itemView.findViewById(R.id.updateBTN);
            deleteBTN = itemView.findViewById(R.id.deleteBTN);
            partySymbol = itemView.findViewById(R.id.partySymbol);
        }
    }

    public interface OnItemClick{
        void onClick();
    }
}
