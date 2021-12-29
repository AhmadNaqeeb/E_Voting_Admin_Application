package com.example.e_voting_admin.Parties_Manage;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.e_voting_admin.Model.DataRetrievalCallback;
import com.example.e_voting_admin.DataBase.EVotingDBContract;
import com.example.e_voting_admin.DataBase.FirebaseDBHandler;
import com.example.e_voting_admin.R;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Manage_Party extends AppCompatActivity implements DataRetrievalCallback {

    EditText partyName;
    MaterialSpinner spinner;
    private static int partyRegister = 0;
    String partSymbol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage__party);

        partyName = findViewById(R.id.partyName);
        spinner = findViewById(R.id.spinner);

        spinner.setItems("Select Symbol", "bat", "tiger", "arrow", "bicycle", "kite", "jui","independent");

        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                partSymbol = item.toString();
                Toast.makeText(Manage_Party.this, item.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void Insert(View v)
    {
        String name = partyName.getText().toString().trim();

        if (name.isEmpty())
        {
            partyName.setError("Field can't be empty");
        }
        else
        {
            RegisterPartyRecord();
        }
    }
    private void RegisterPartyRecord()
    {
        FirebaseDBHandler.mCallback = this;
        FirebaseDBHandler.getNodeAllChildren("0", EVotingDBContract.PARTIES_NODE);
    }

    @Override
    public void onDataRetrieved(List<Map<String, String>> data, String nodeName) {
        SavePartyRecord(data);
        Toast.makeText(getApplicationContext(),"Registered Party Successfully",Toast.LENGTH_LONG).show();
    }

    private void SavePartyRecord(List<Map<String, String>> data)
    {
        String name = partyName.getText().toString().trim();

        Parties parties = new Parties(name,partSymbol);
        if (data != null && data.size() > 0)
        {
            if (partyRegister == 0){  // check in case of application gets restart or reinstall....
                Map dataMap = data.get(data.size() - 1);
                Set keySet = dataMap.keySet();
                Iterator iterator = keySet.iterator();
                String key = "";
                if (iterator.hasNext()){
                    key = (String) iterator.next();
                }
                String jsonString = (String) dataMap.get(key);
                Log.d("Manage_Part: " , String.valueOf(jsonString));
                String id = "";
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    id = jsonObject.get(EVotingDBContract.PartiesTable.PARTY_ID_COLUMN).toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (id != null && !id.isEmpty()){
                    partyRegister = Integer.parseInt(id);
                    partyRegister++;
                }
            }
            parties.setId(partyRegister);
            FirebaseDBHandler.insert("0",EVotingDBContract.PARTIES_NODE,parties,String.valueOf(partyRegister));
            partyRegister++;

        }else {
            partyRegister = 0;
            parties.setId(partyRegister);
            FirebaseDBHandler.insert("0", EVotingDBContract.PARTIES_NODE, parties, String.valueOf(partyRegister));
            partyRegister++;
        }
    }
    public void viewParty(View v)
    {
        startActivity(new Intent(Manage_Party.this, View_Party.class));
    }
}