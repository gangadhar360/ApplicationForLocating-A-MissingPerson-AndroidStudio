package com.example.capstoneproject;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Forgot extends AppCompatActivity {

    EditText username;
    Button reset;
    DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

        username=findViewById(R.id.username);
        reset=findViewById(R.id.reset);
        db=new DBHelper(this);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user=username.getText().toString();

                if(db.checkUsername(user)==true)
                {
                    Intent in = new Intent(getApplicationContext(),Reset.class);
                    in.putExtra("username",user);
                    startActivity(in);
                }
                else
                {
                    Toast.makeText(Forgot.this, "user doesn't exist", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
