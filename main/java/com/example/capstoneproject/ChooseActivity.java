package com.example.capstoneproject;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class ChooseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chooseactivity);

        CardView cardAboutUs = findViewById(R.id.cardAboutUs);
        CardView cardSettings = findViewById(R.id.cardSettings);
        CardView cardRegCases = findViewById(R.id.regCases);
        CardView cardContactUs = findViewById(R.id.contactUs);


        cardAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the About Us page
                Intent intent = new Intent(ChooseActivity.this, AboutUsActivity.class);
                startActivity(intent);
            }
        });

        cardSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the Settings page
                Intent intent = new Intent(ChooseActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        cardRegCases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the Register Cases page
                Intent intent = new Intent(ChooseActivity.this, RegisterCasesActivity.class);
                startActivity(intent);
            }
        });

//        cardViewCases.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Open the View Cases page
//                Intent intent = new Intent(ChooseActivity.this, ViewCases.class);
//                startActivity(intent);


        cardContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChooseActivity.this, ContactUsActivity.class);
                startActivity(intent);
            }
        });

        CardView logoutCard = findViewById(R.id.logout);

        logoutCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform the logout actions here, such as clearing session data
                // Redirect to the LoginActivity
                Intent intent = new Intent(ChooseActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish(); // Finish the ChooseActivity so the user can't go back
                Toast.makeText(ChooseActivity.this, "Logged out", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
