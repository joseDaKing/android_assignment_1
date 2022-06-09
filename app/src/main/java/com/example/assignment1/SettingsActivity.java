package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;

import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity {

    private ArrayList<String> list = new ArrayList<>();

    private EditText edtUserName, edtFirstName, edtLastName, edtGroupName;

    private Button btnAddUser, btnAddGroup;

    private Spinner selectGroup;

    private Intent clientIntent;

    private String groupName = "";

    private String finalGroupName;

    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initiateVars();

        adapter = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, list);

        selectGroup.setAdapter(adapter);

        selectGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                groupName = (String) adapterView.getItemAtPosition(i);
                Log.d("Group name", groupName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        createUserListener();

        createOrChooseGroup();

        clientIntent = new Intent(this, ClientService.class);

        startService(clientIntent);

        addListeners();
    }

    private void initiateVars(){
        edtUserName = findViewById(R.id.username);
        edtFirstName = findViewById(R.id.first_name);
        edtLastName = findViewById(R.id.last_name);
        btnAddUser = findViewById(R.id.btn_add_user);
        edtGroupName = findViewById(R.id.group_name);
        selectGroup = findViewById(R.id.group_spinner);
        btnAddGroup = findViewById(R.id.btn_group);
    }

    private void addListeners() {

        Callback<String[]> onGroups = groupNames -> {

            list.clear();

            for (String item : groupNames) {

                list.add(item);
            }

            adapter.notify();
        };

        clientIntent.putExtra(
                ClientManager.GROUPS_TYPE,
                onGroups
        );

        Callback<String> onError = errorMessage -> {

            CharSequence text = errorMessage;

            Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);

            toast.show();
        };

        clientIntent.putExtra(
                ClientManager.EXCEPTIONS_TYPE,
                onError
        );
    }

    private void createUserListener() {

        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtUserName.getText().toString().equals("") || edtFirstName.getText().toString().equals("") || edtLastName.getText().toString().equals("")){
                    Log.d("Add user clicked", "Clicked");
                    Toast.makeText(SettingsActivity.this, "Vänligen mata in användarnamnet, för och efternamn", Toast.LENGTH_SHORT).show();
                    return;
                }

               String username = edtUserName.getText().toString();
               String firstName = edtFirstName.getText().toString();
               String lastName = edtLastName.getText().toString();

                try {
                    clientIntent.putExtra(
                        ClientManager.MEMBERS_TYPE,
                        ClientManager.createRegistrationRequest(
                            groupName,
                            username + "-" + firstName + "-" + lastName
                        )
                    );
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void createOrChooseGroup() {

        btnAddGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean hasSelectedGroup = !groupName.equals("Välj grupp");

                boolean isCreatingANewGroup = !edtGroupName.getText().equals("");

                if(!hasSelectedGroup || !isCreatingANewGroup) {
                    Toast.makeText(SettingsActivity.this, "Antigen välj en grupp från listan nere eller skapa ny grupp", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(isCreatingANewGroup){

                    finalGroupName = edtGroupName.getText().toString();

                    return;
                }

                else if(hasSelectedGroup) {

                    finalGroupName = groupName;

                    return;
                }
            }
        });
    }

}