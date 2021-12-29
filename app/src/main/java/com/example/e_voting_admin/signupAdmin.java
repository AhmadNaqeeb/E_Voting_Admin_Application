package com.example.e_voting_admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.e_voting_admin.Model.AdminData;
import com.example.e_voting_admin.Model.DataRetrievalCallback;
import com.example.e_voting_admin.DataBase.EVotingDBContract;
import com.example.e_voting_admin.DataBase.FirebaseDBHandler;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class signupAdmin extends AppCompatActivity implements DataRetrievalCallback {

    EditText etR_Name, etR_CNIC, etR_Mobile, etR_Password, etR_Conf_Password;
    Button btn_Register;
    private static int AdminRegister = 0;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_admin);

        init();

        btn_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String a_name = etR_Name.getText().toString().trim();
                String a_cnic = etR_CNIC.getText().toString().trim();
                String a_mobile = etR_Mobile.getText().toString().trim();
                String a_password = etR_Password.getText().toString().trim();
                String a_conf_password = etR_Conf_Password.getText().toString().trim();
                boolean flag = true;

                if(a_name.isEmpty())
                {
                    etR_Name.setError("Nmae is Required");
                    flag = false;
                }
                if(a_cnic.isEmpty())
                {
                    etR_CNIC.setError("CNIC is Required");
                    flag = false;
                }
                if(a_mobile.isEmpty())
                {
                    etR_Mobile.setError("Number is Required");
                    flag = false;
                }
                if(a_password.isEmpty())
                {
                    etR_Password.setError("Password is Required");
                    flag = false;
                }
                if(!a_conf_password.equalsIgnoreCase(a_password))
                {
                    etR_Conf_Password.setError("Password did not match");
                    flag=false;
                }
                if (a_conf_password.equals(a_password)){
                    etR_Conf_Password.setError(null);

                    if (flag){
                        validCNIC();
                    }
                }
            }

        });
    }

    private void init() {
        etR_Name = findViewById(R.id.etR_Name);
        etR_Mobile = findViewById(R.id.etR_Mobile);
        etR_CNIC = findViewById(R.id.etR_CNIC);
        etR_Password = findViewById(R.id.etR_Password);
        etR_Conf_Password = findViewById(R.id.etR_Conf_Password);
        btn_Register = findViewById(R.id.btn_Login);
        mAuth = FirebaseAuth.getInstance();
    }

    private void validCNIC()
    {
        FirebaseDBHandler.mCallback = this;
        FirebaseDBHandler.getNodeAllChildren("0", EVotingDBContract.ADMIN_NODE);
    }

    @Override
    public void onDataRetrieved(List<Map<String, String>> data, String nodeName) {
        boolean isError = false;
        if (data != null && data.size() > 0)
        {
            for (int i = 0; i<data.size(); i++)
            {
                Map dataMap = data.get(i);
                Set keySet = dataMap.keySet();
                Iterator iterator = keySet.iterator();
                String key = "";
                if (iterator.hasNext()){
                    key = (String) iterator.next();
                }
                String jsonString = (String) dataMap.get(key);
                String CNIC = "";
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    CNIC = jsonObject.get(EVotingDBContract.AdminTable.ADMIN_CNIC_COLUMN).toString();
                    if (CNIC.equalsIgnoreCase(etR_CNIC.toString().trim()))
                    {
                        Toast.makeText(this, "Dublicate CNIC", Toast.LENGTH_SHORT).show();
                        isError = true;
                        break;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        if (!isError)
        {
            SaveAdminRecord(data);
            Toast.makeText(getApplicationContext(),"You are Registered With Online E Voting System Successfully",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(signupAdmin.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
    private void SaveAdminRecord(List<Map<String, String>> data)
    {
        String Name = etR_Name.getText().toString().trim();
        String Mobile = etR_Mobile.getText().toString().trim();
        String CNIC = etR_CNIC.getText().toString().trim();
        String Password = etR_Password.getText().toString().trim();

        AdminData adminData = new AdminData(Name,Mobile,CNIC,Password);
        if (data != null && data.size() > 0)
        {
            if (AdminRegister == 0){  // check in case of application gets restart or reinstall....
                Map dataMap = data.get(data.size() - 1);
                Set keySet = dataMap.keySet();
                Iterator iterator = keySet.iterator();
                String key = "";
                if (iterator.hasNext()){
                    key = (String) iterator.next();
                }
                String jsonString = (String) dataMap.get(key);
                String id = "";
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    id = jsonObject.get(EVotingDBContract.AdminTable.ADMIN_ID_COLUMN).toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (id != null && !id.isEmpty()){
                    AdminRegister = Integer.parseInt(id);
                    AdminRegister++;
                }
            }
            adminData.setId(AdminRegister);
            FirebaseDBHandler.insert("0",EVotingDBContract.ADMIN_NODE,adminData,String.valueOf(AdminRegister));
            AdminRegister++;

        }else {
            AdminRegister = 0;
            adminData.setId(AdminRegister);
            FirebaseDBHandler.insert("0", EVotingDBContract.ADMIN_NODE, adminData, String.valueOf(AdminRegister));
            AdminRegister++;
        }
    }
}