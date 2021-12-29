
package com.example.e_voting_admin.Constituency_Manage;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_voting_admin.DataBase.EVotingDBContract;
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

public class ConstituencyAdopter extends FirebaseRecyclerAdapter<Constituency, ConstituencyAdopter.ConstituencyViewHolder> {

    Context context;
    String parent, city,province,area;
    public ConstituencyAdopter(@NonNull FirebaseRecyclerOptions<Constituency> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull ConstituencyViewHolder holder, int position, @NonNull Constituency model) {
        holder.showName.setText(model.getName());
        holder.showParent.setText(model.getParent());
        holder.showProvince.setText(model.getProvince());
        holder.showCity.setText(model.getCity());
        holder.showArea.setText(model.getArea());

        holder.Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogPlus dialog = DialogPlus.newDialog(context)
                        .setContentHolder(new ViewHolder(R.layout.update_constituency))
                        .setGravity(Gravity.CENTER)
                        .setMargin(0,30,0,30)
                        .setExpanded(false)  // This will enable the expand feature, (similar to android L share dialog)
                        .create();

                View view = dialog.getHolderView();

                EditText updateConstituency;
                MaterialSpinner constituencySpinner,citySpinner,provinceSpinner,areaSpinner;
                Button btnUpdateConstituency;
                updateConstituency = view.findViewById(R.id.updtConstituency);
                constituencySpinner = view.findViewById(R.id.ConstituencySpinner);
                citySpinner = view.findViewById(R.id.EditCitySpinner);
                provinceSpinner = view.findViewById(R.id.EditProvinceSpinner);
                areaSpinner = view.findViewById(R.id.EditAreaSpinner);
                btnUpdateConstituency = view.findViewById(R.id.btnUpdateConstituency);

                String [] parents = {"Select Constituency","MNA", "MPA"};
                int index = 0;
                boolean isFound = false;
                for (String s: parents)
                {
                    if (s.equalsIgnoreCase(model.getParent()))
                    {
                        parent = s;
                        isFound = true;
                        break;
                    }
                    index++;
                }
                constituencySpinner.setItems(parents);
                if (isFound){
                    constituencySpinner.setSelectedIndex(index);
                }

                constituencySpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                        parent = item.toString();
                    }
                });

                String [] provinces = {"Select Province", "Punjab", "Sindh", "KPK", "Balochistan", "Gilgit Baltistan"};
                index = 0;
                isFound = false;
                for (String s: provinces)
                {
                    if (s.equalsIgnoreCase(model.getCity()))
                    {
                        province = s;
                        isFound = true;
                        break;
                    }
                    index++;
                }
                provinceSpinner.setItems(provinces);
                if (isFound){
                    provinceSpinner.setSelectedIndex(index);
                }

                provinceSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                        province = item.toString();
                    }
                });

                String [] cities = {"Select City", "Lahore", "Islamabad", "Karachi"};
                index = 0;
                isFound = false;
                for (String s: cities)
                {
                    if (s.equalsIgnoreCase(model.getCity()))
                    {
                        city = s;
                        isFound = true;
                        break;
                    }
                    index++;
                }
                citySpinner.setItems(cities);
                if (isFound){
                    citySpinner.setSelectedIndex(index);
                }

                citySpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                        city = item.toString();
                    }
                });

                String [] areas = {"Select Area", "DHA Phase I", "DHA Phase II", "DHA Phase III", "Gulberg I","Gulberg II"};
                index = 0;
                isFound = false;
                for (String s: areas)
                {
                    if (s.equalsIgnoreCase(model.getCity()))
                    {
                        area = s;
                        isFound = true;
                        break;
                    }
                    index++;
                }
                areaSpinner.setItems(areas);
                if (isFound){
                    areaSpinner.setSelectedIndex(index);
                }

                areaSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                        area = item.toString();
                    }
                });

                updateConstituency.setText(model.getName());
                dialog.show();

                btnUpdateConstituency.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Map<String, Object> map = new HashMap<>();
                        map.put(EVotingDBContract.ConstituencyTable.CONSTITUENCY_NAME_COLUMN, updateConstituency.getText().toString().trim());
                        map.put(EVotingDBContract.ConstituencyTable.CONSTITUENCY_PARENT_COLUMN, parent);
                        map.put(EVotingDBContract.ConstituencyTable.CONSTITUENCY_CITY_COLUMN, city);
                        map.put(EVotingDBContract.ConstituencyTable.CONSTITUENCY_PROVINCE_COLUMN, province);
                        map.put(EVotingDBContract.ConstituencyTable.CONSTITUENCY_AREA_COLUMN, area);

                        FirebaseDatabase.getInstance().getReference()
                                .child("EVotingDB").child("0").child("Constituency")
                                .child(getRef(position).getKey())
                                .updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(context, "DataUpdate Successfully", Toast.LENGTH_SHORT).show();
                                        model.setName(updateConstituency.getText().toString().trim());
                                        model.setParent(parent);
                                        model.setCity(city);
                                        model.setProvince(province);
                                        model.setArea(area);
                                        dialog.dismiss();
                                    }
                                });
                    }
                });
            }
        });
        holder.Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference()
                        .child("EVotingDB").child("0").child("Constituency")
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
    public ConstituencyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.constituency_list_design, parent, false);

        return new ConstituencyViewHolder(view);
    }
    public class ConstituencyViewHolder extends RecyclerView.ViewHolder
    {
        TextView showName, showParent,showProvince,showCity,showArea;
        ImageButton Edit,Delete;

        public ConstituencyViewHolder(@NonNull View itemView) {
            super(itemView);
            showName = itemView.findViewById(R.id.showName);
            showParent = itemView.findViewById(R.id.showParent);
            showProvince = itemView.findViewById(R.id.showProvince);
            showCity = itemView.findViewById(R.id.showCity);
            showArea = itemView.findViewById(R.id.showArea);

            Edit = itemView.findViewById(R.id.Edit);
            Delete = itemView.findViewById(R.id.Delete);
        }
    }
}

