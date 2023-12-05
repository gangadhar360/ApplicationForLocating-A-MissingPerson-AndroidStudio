package com.example.capstoneproject;

import android.content.SharedPreferences;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        // Retrieve the user's information from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String userName = sharedPreferences.getString("userName", "");

        // Find the TextView element with the ID "username"
        TextView nameTextView = findViewById(R.id.username);

        // Set the text of the TextView with the user's name
        nameTextView.setText("Name: " + userName);

        // Find the "Password Reset" button by ID
        Button passwordResetButton = findViewById(R.id.password_reset_button);

        // Set an OnClickListener for the "Password Reset" button
        passwordResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the button click by navigating to the ResetActivity
                Intent intent = new Intent(SettingsActivity.this, Reset.class);
                startActivity(intent);
            }
        });
    }
}
