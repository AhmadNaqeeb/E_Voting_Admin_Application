package com.example.e_voting_admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_voting_admin.DataBase.EVotingDBContract;
import com.example.e_voting_admin.DataBase.FirebaseDBHandler;
import com.example.e_voting_admin.Model.AdminData;
import com.example.e_voting_admin.Model.DataRetrievalCallback;
import com.example.e_voting_admin.Parties_Manage.Manage_Party;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements DataRetrievalCallback {

    EditText  et_CNIC, et_Password;
    Button btn_Login;
    TextView tvRegisterAdmin;
    public static AdminData loggedInAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        tvRegisterAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, signupAdmin.class);
                startActivity(intent);
            }
        });

        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateCNIC() && validatePassword())
                {
                    isAuthenticUser();
                }
            }
        });
    }

    private void init() {
        et_CNIC = findViewById(R.id.etA_CNIC);
        et_Password = findViewById(R.id.etA_Password);
        btn_Login = findViewById(R.id.btn_Login);
        tvRegisterAdmin = findViewById(R.id.tvRegisterAdmin);

    }
    private Boolean validateCNIC()
    {
        String cnic = et_CNIC.getText().toString().trim();
        if (cnic.isEmpty())
        {
            et_CNIC.setError("Field can't be empty");
            return false;
        }
        else
        {
            et_CNIC.setError(null);
            return true;
        }
    }

    private Boolean validatePassword()
    {
        String password = et_Password.getText().toString().trim();
        if (password.isEmpty())
        {
            et_Password.setError("Field can't be empty");
            return false;
        }
        else
        {
            et_Password.setError(null);
            return true;
        }
    }

    private void isAuthenticUser() {
        Log.d("check: ", "hello");
        FirebaseDBHandler.mCallback = this;
        FirebaseDBHandler.getNodeAllChildren("0", EVotingDBContract.ADMIN_NODE);
    }

   /* public void loginUser(View view)
    {
        // Validate Login Info
        if (!validateCNIC() | !validatePassword())
        {
            return;
        }
        else
        {
            isUser();
        }
    }*/

    @Override
    public void onDataRetrieved(List<Map<String, String>> data, String nodeName) {
        if (data != null && data.size() > 0){
            String userEnteredCNIC = et_CNIC.getText().toString().trim();
            String userEnteredPassword = et_Password.getText().toString().trim();
            boolean isFound = false ;
            for (int i = 0 ; i < data.size(); i++) {

                Map dataMap = data.get(i);
                Set keySet = dataMap.keySet();
                Iterator iterator = keySet.iterator();
                String key = "";
                if (iterator.hasNext()){
                    key = (String) iterator.next();
                }
                String jsonString = (String) dataMap.get(key);
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    Log.d("MapKeysJSON ",jsonObject.toString());
                    if (
                            jsonObject.get(EVotingDBContract.AdminTable.ADMIN_CNIC_COLUMN).toString().equalsIgnoreCase(userEnteredCNIC) &&
                                    jsonObject.get(EVotingDBContract.AdminTable.ADMIN_PASSWORD_COLUMN).toString().equalsIgnoreCase(userEnteredPassword)
                    )
                    {
                        String name = jsonObject.get(EVotingDBContract.AdminTable.ADMIN_NAME_COLUMN).toString();
                    String mobile = jsonObject.get(EVotingDBContract.AdminTable.ADMIN_NAME_COLUMN).toString();

                    loggedInAdmin = new AdminData(name,userEnteredCNIC,mobile,userEnteredPassword);

                    Intent homeIntent = new Intent(MainActivity.this, homePage.class);
                    startActivity(homeIntent);
                    finish();
                    isFound = true;
                    break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (!isFound){
            Toast.makeText(getApplicationContext(), "Invalid credentials. Please enter valid Detail", Toast.LENGTH_LONG).show();
        }
    }else{
        Log.d("Invalid: ","Unable to login");
        Toast.makeText(getApplicationContext(), "This Admin is not registered", Toast.LENGTH_LONG).show();
    }

    }
}