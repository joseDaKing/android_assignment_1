package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    private EditText edtUserName, edtFirstName, edtLastName, edtGroupName;

    private Button btnAddUser, btnAddGroup;

    private Spinner selectGroup;


    private String groupName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initiateVars();

        String [] dummyEx = new String[]{"Välj grupp", "Grupp1", "Grupp2", "Grupp3" };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, dummyEx);

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

                }

                else if(hasSelectedGroup) {

                }
            }
        });
    }

}