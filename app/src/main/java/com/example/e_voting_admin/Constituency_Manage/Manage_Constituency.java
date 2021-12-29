package com.example.e_voting_admin.Constituency_Manage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.e_voting_admin.DataBase.EVotingDBContract;
import com.example.e_voting_admin.DataBase.FirebaseDBHandler;
import com.example.e_voting_admin.Model.ConstituencyDataCallback;
import com.example.e_voting_admin.Model.DataRetrievalCallback;
import com.example.e_voting_admin.R;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Manage_Constituency extends AppCompatActivity implements ConstituencyDataCallback {

    EditText ConstituencyName;
    MaterialSpinner constituencySpinner, CitySpinner, provinceSpinner, areaSpinner;
    private static int ConstituencyRegister = 0;
    String parent, city, province,area;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage__constituency);

        ConstituencyName = findViewById(R.id.constituencyName);
        constituencySpinner = findViewById(R.id.constituencySpinner);
        CitySpinner = findViewById(R.id.citySpinner);
        provinceSpinner = findViewById(R.id.provinceSpinner);
        areaSpinner = findViewById(R.id.areaSpinner);

        constituencySpinner.setItems("Select Constituency", "MNA", "MPA");

        constituencySpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                parent= item.toString();
                Toast.makeText(Manage_Constituency.this, item.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        provinceSpinner.setItems("Select Province", "Punjab", "Sindh", "KPK", "Balochistan", "Gilgit Baltistan");

        provinceSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                province = item.toString();
                Toast.makeText(Manage_Constituency.this, item.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        CitySpinner.setItems("Select City", "Lahore", "Islamabad", "Karachi");

        CitySpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                city = item.toString();
                Toast.makeText(Manage_Constituency.this, item.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        areaSpinner.setItems("Select Area", "DHA Phase I", "DHA Phase II", "DHA Phase III", "Gulberg I","Gulberg II");

        areaSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                area = item.toString();
                Toast.makeText(Manage_Constituency.this, item.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void Insert(View v)
    {
       String name = ConstituencyName.getText().toString().trim();

        if (name.isEmpty())
        {
            ConstituencyName.setError("Field can't be empty");
        }
        else
        {
            RegisterConstituency();
        }
    }
    private void RegisterConstituency()
    {
        FirebaseDBHandler.mConstituencyDataCallback = this;
        FirebaseDBHandler.getConstituteNodeAllChildren("0", EVotingDBContract.CONSTITUENCY_NODE);
    }

    @Override
    public void onConstituencyDataRetrieved(List<Constituency> constituencyDataList, String nodeName) {
        SaveConstituencyRecord(constituencyDataList);
        Toast.makeText(getApplicationContext(),"Registered Constituency Successfully",Toast.LENGTH_LONG).show();
    }

    private void SaveConstituencyRecord(List<Constituency> data)
    {
        String name = ConstituencyName.getText().toString().trim();

        Constituency constituency = new Constituency(name,parent, city,province,area);
        if(data != null && data.size() > 0)
        {
            if (ConstituencyRegister == 0){  // check in case of application gets restart or reinstall....
                ConstituencyRegister = data.get(data.size() - 1).getId();
                ConstituencyRegister++;
            }
            constituency.setId(ConstituencyRegister);
            FirebaseDBHandler.insert("0",EVotingDBContract.CONSTITUENCY_NODE,constituency,String.valueOf(ConstituencyRegister));
            ConstituencyRegister++;
        }else {
            ConstituencyRegister = 0;
            constituency.setId(ConstituencyRegister);
            FirebaseDBHandler.insert("0", EVotingDBContract.CONSTITUENCY_NODE, constituency, String.valueOf(ConstituencyRegister));
            ConstituencyRegister++;
        }
    }
    public void viewParty(View v)
    {
        startActivity(new Intent(Manage_Constituency.this, View_Constituency.class));
    }
}