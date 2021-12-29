package com.example.e_voting_admin.CandidatesModel;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.e_voting_admin.Constituency_Manage.Constituency;
import com.example.e_voting_admin.Model.CandidateDataCallback;
import com.example.e_voting_admin.Model.ConstituencyDataCallback;
import com.example.e_voting_admin.Model.DataRetrievalCallback;
import com.example.e_voting_admin.DataBase.EVotingDBContract;
import com.example.e_voting_admin.DataBase.FirebaseDBHandler;
import com.example.e_voting_admin.Parties_Manage.Parties;
import com.example.e_voting_admin.R;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Manage_Candidates extends AppCompatActivity implements DataRetrievalCallback, CandidateDataCallback, ConstituencyDataCallback {

    EditText newCandidate, candidateCNIC;
    MaterialSpinner CandidateConstituencySpinner, CandidatePartySpinner, CandidateCitySpinner, CandidateAreaSpinner, CandidateProvinceSpinner;
    Button btnSubmit, btnViewCandidate;
    private static int CandidatesRegister = 0;
    String SelectedProvince = "",  SelectedCity;
    String SelectedArea;
    Constituency SelectedConstituency;
    Parties SelectedParty;
    boolean isValid = true ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage__candidates);

        newCandidate = findViewById(R.id.newCandidate);
        candidateCNIC = findViewById(R.id.CandidateCNIC);
        CandidateConstituencySpinner = findViewById(R.id.CandidateConstituencySpinner);
        CandidatePartySpinner = findViewById(R.id.CandidatePartySpinner);
        CandidateProvinceSpinner = findViewById(R.id.CandidateProvinceSpinner);
        CandidateCitySpinner = findViewById(R.id.CandidateCitySpinner);
        CandidateAreaSpinner = findViewById(R.id.CandidateAreaSpinner);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnViewCandidate = findViewById(R.id.btnViewCandidate);

        CandidateProvinceSpinner.setItems("Select Province", "Punjab", "Sindh", "KPK", "Balochistan", "Gilgit Baltistan");

        CandidateProvinceSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                SelectedProvince= item.toString();
                Toast.makeText(Manage_Candidates.this, item.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        CandidateCitySpinner.setItems("Select City", "Lahore", "Islamabad", "Karachi");

        CandidateCitySpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                SelectedCity= item.toString();
                Toast.makeText(Manage_Candidates.this, item.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        CandidateAreaSpinner.setItems("Select Area", "DHA Phase I", "DHA Phase II", "DHA Phase III", "Gulberg I","Gulberg II");

        CandidateAreaSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                SelectedArea= item.toString();
                Toast.makeText(Manage_Candidates.this, item.toString(), Toast.LENGTH_SHORT).show();
                FirebaseDBHandler.mConstituencyDataCallback = Manage_Candidates.this;
                FirebaseDBHandler.getConstituteNodeAllChildren("0", EVotingDBContract.CONSTITUENCY_NODE);
            }
        });

        CandidateConstituencySpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                SelectedConstituency = ((Constituency) item );
                Toast.makeText(Manage_Candidates.this,  ((Constituency) item ).getName(), Toast.LENGTH_SHORT).show();
            }
        });
        FirebaseDBHandler.mCallback = Manage_Candidates.this;
        FirebaseDBHandler.getNodeAllChildren("0", EVotingDBContract.PARTIES_NODE);

        CandidatePartySpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                SelectedParty = ((Parties) item);
                Toast.makeText(Manage_Candidates.this, item.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        //SelectedConstituency = (Constituency) CandidateConstituencySpinner.getItems().get(CandidateConstituencySpinner.getSelectedIndex());

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPerson = newCandidate.getText().toString().trim();
                String CandidateCNIC = candidateCNIC.getText().toString().trim();

                if (newPerson.isEmpty() || CandidateCNIC.isEmpty()) {
                    Toast.makeText(Manage_Candidates.this, "Please Enter the Data", Toast.LENGTH_SHORT).show();
                } else {
                    Add_Candidates();
                }
            }
        });

        btnViewCandidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Manage_Candidates.this, View_Candidates.class));
            }
        });
    }

    private void Add_Candidates() {
            FirebaseDBHandler.mCandidateDataCallback = this;
            FirebaseDBHandler.getCandidateNodeAllChildren("0", EVotingDBContract.CANDIDATE_NODE);
    }

    @Override
    public void onDataRetrieved(List<Map<String, String>> data, String nodeName) {

        if(nodeName.equalsIgnoreCase(EVotingDBContract.PartiesTable.TABLE_NAME)) // Set Data in Parties Spinner
        {
            if (data != null){
                List<Parties> availableParties = new ArrayList<>();
                for (int i = 0; i < data.size(); i++){
                    Map dataMap = data.get(i);
                    Set keySet = dataMap.keySet();
                    Iterator iterator = keySet.iterator();
                    String key = "";
                    if (iterator.hasNext()){
                        key = (String) iterator.next();
                    }
                    String jsonString = (String) dataMap.get(key);
                    String name = "",symbol = "";
                    try {
                        JSONObject jsonObject = new JSONObject(jsonString);
                        name = jsonObject.get(EVotingDBContract.PartiesTable.PARTY_NAME_COLUMN).toString();
                        symbol = jsonObject.get(EVotingDBContract.PartiesTable.PARTY_SYMBOL_COLUMN).toString();
                        {
                            String partyid = jsonObject.get(EVotingDBContract.PartiesTable.PARTY_ID_COLUMN).toString();
                            int id = 0;
                            if (partyid != null && !partyid.isEmpty()){
                                id = Integer.parseInt(partyid);
                            }
                            Parties party = new Parties(name, symbol);
                            party.setId(id);
                            availableParties.add(party);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                CandidatePartySpinner.setItems(availableParties);
            }
        }
    }

    private void SaveCandidatesRecord(List<Candidates> data) {
        String name = newCandidate.getText().toString().trim();
        String CNIC = candidateCNIC.getText().toString().trim();

        try {
            String partyJsonString = new ObjectMapper().writeValueAsString(SelectedParty);
            String constituencyJsonString = new ObjectMapper().writeValueAsString(SelectedConstituency);
            Candidates candidates = new Candidates(name, CNIC, SelectedProvince, SelectedCity, SelectedArea,constituencyJsonString,partyJsonString);

            if (data != null && data.size() > 0) {
                if (CandidatesRegister == 0) {  // check in case of application gets restart or reinstall....
                    int id = data.get(data.size()-1).getId();
                    CandidatesRegister = id;
                    CandidatesRegister++;
                }
                candidates.setId(CandidatesRegister);
                FirebaseDBHandler.insert("0", EVotingDBContract.CANDIDATE_NODE, candidates, String.valueOf(CandidatesRegister));
                CandidatesRegister++;
            } else {
                CandidatesRegister = 0;
                candidates.setId(CandidatesRegister);
                FirebaseDBHandler.insert("0", EVotingDBContract.CANDIDATE_NODE, candidates, String.valueOf(CandidatesRegister));
                CandidatesRegister++;
            }

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCandidateDataRetrieved(List<Candidates> candidateDataList, String nodeName) {
        String CNIC = candidateCNIC.getText().toString().trim();
        boolean isError = false;
        for(Candidates candidates : candidateDataList)
        {
            if (candidates.getCNIC().equalsIgnoreCase(CNIC))
            {
                Toast.makeText(this, "This CNIC is Already used", Toast.LENGTH_SHORT).show();
                isError = true;
                break;
            }
        }
        if (!isError && isValid){
            SaveCandidatesRecord(candidateDataList);
        }
    }

    @Override
    public void onConstituencyDataRetrieved(List<Constituency> constituencyDataList, String nodeName) {
        if (nodeName.equalsIgnoreCase(EVotingDBContract.ConstituencyTable.TABLE_NAME)){

            if (constituencyDataList != null && SelectedProvince != null && !SelectedProvince.isEmpty() && SelectedCity != null && !SelectedCity.isEmpty() && SelectedArea != null && !SelectedArea.isEmpty()){
                List<Constituency> availableConstitutes = new ArrayList<>();
                for (Constituency newConstituency : constituencyDataList){
                    String city = "",parent = "", province = "", area = "";
                    parent = newConstituency.getParent();
                    city = newConstituency.getCity();
                    province = newConstituency.getProvince();
                    area = newConstituency.getArea();
                    if (SelectedProvince.equalsIgnoreCase(province) && SelectedCity.equalsIgnoreCase(city) && SelectedArea.equalsIgnoreCase(area)){
                        //String constituencyid =  jsonObject.get(EVotingDBContract.ConstituencyTable.CONSTITUENCY_ID_COLUMN).toString();
                        int id = newConstituency.getId();
//                            if (constituencyid != null && !constituencyid.isEmpty()){
//                                id = Integer.parseInt(constituencyid);
//                            }
                        String constituencyName = newConstituency.getName(); //jsonObject.get(EVotingDBContract.ConstituencyTable.CONSTITUENCY_NAME_COLUMN).toString();
                        Constituency constituency = new Constituency(constituencyName,parent,city,province,area);
                        constituency.setId(id);
                        availableConstitutes.add(constituency);
                    }
                }
                if (availableConstitutes.size() > 0){
                    CandidateConstituencySpinner.setItems(availableConstitutes);
                }else{
                    isValid = false;
                    Toast.makeText(this, ("Please Add Constituency for Province " + SelectedProvince + " and City " + SelectedCity + " and area " + SelectedArea ), Toast.LENGTH_SHORT).show();
                }

            }else{
                isValid = false;
                Toast.makeText(getApplicationContext(),"Please Select Province/City/Area first to get constituency details",Toast.LENGTH_LONG);
            }
        }
    }
}