package com.example.lostfoundupdate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.Arrays;
import java.util.List;

public class CreateActivity extends AppCompatActivity {
    RadioGroup TypeRG;
    EditText NameET, PhoneET, DescriptionET, DateET, LocationET;
    Button SaveBTN;
    ManagerDB managerDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        managerDB = new ManagerDB(this);

        // Initialize Places.
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), "AIzaSyDPuqLA7Ni37BJvEfWVGCuX-3k9qYN4YyM");
        }


        InitializeView();
        SetupButton();
        setUpLocationET();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {  // Ensure this request code matches the one used in startActivityForResult()
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                LocationET.setText(place.getName());  // Set the selected place's name to the LocationET EditText
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.e("CreateActivity", "Error: " + status.getStatusMessage());
                Toast.makeText(this, "Error: " + status.getStatusMessage(), Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) {
                // User canceled the operation
                Toast.makeText(this, "Operation canceled", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void InitializeView() {
        TypeRG = findViewById(R.id.TypeRadioGroup);
        NameET = findViewById(R.id.NameEditText);
        PhoneET = findViewById(R.id.PhoneEditText);
        DescriptionET = findViewById(R.id.DescriptionEditText);
        DateET = findViewById(R.id.DateEditText);
        LocationET = findViewById(R.id.LocationEditText);
        SaveBTN = findViewById(R.id.SaveButton);
    }

    private void SetupButton() {
        SaveBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Name = NameET.getText().toString().trim();
                String Phone = PhoneET.getText().toString().trim();
                String Description = DescriptionET.getText().toString().trim();
                String Date = DateET.getText().toString().trim();
                String Location = LocationET.getText().toString().trim();

                if (Name.isEmpty() || Phone.isEmpty() || Description.isEmpty() ||
                        Date.isEmpty() || Location.isEmpty() || TypeRG.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(CreateActivity.this, "Please fill in all the information", Toast.LENGTH_SHORT).show();
                } else {
                    RadioButton radioButton = TypeRG.findViewById(TypeRG.getCheckedRadioButtonId());
                    Post post = new Post(0, Name, Phone, Description, Date, Location, radioButton.getText().toString());
                    if (managerDB.createPost(post).getId() != 0) {
                        Toast.makeText(CreateActivity.this, "Save Successfully", Toast.LENGTH_SHORT).show();
                        ClearAllET();
                    } else {
                        Toast.makeText(CreateActivity.this, "Fail to Save", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void setUpLocationET() {
        LocationET.setFocusable(false);
        LocationET.setOnClickListener(v -> {
            // Define the fields to specify which types of place data to return.
            List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);

            // Start the autocomplete intent.
            Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                    .build(this);
            startActivityForResult(intent, 1);


        });
    }


    private void ClearAllET() {
        NameET.getText().clear();
        PhoneET.getText().clear();
        DescriptionET.getText().clear();
        DateET.getText().clear();
        LocationET.getText().clear();
    }
}