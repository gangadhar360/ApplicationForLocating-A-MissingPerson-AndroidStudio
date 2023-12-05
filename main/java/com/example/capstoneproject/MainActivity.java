package com.example.capstoneproject;


import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

public class MainActivity extends AppCompatActivity {

    EditText userName,password;
    Button signIn;
    TextView signUp,forgotPass;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userName = findViewById(R.id.username);
        password = findViewById(R.id.password);
        signIn = findViewById(R.id.signinbtn);
        signUp = findViewById(R.id.signUp);
        forgotPass = findViewById(R.id.forgotbtn);
        db=new DBHelper(this);

        String login_user = userName.getText().toString();

        signUp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
            }
        });

        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), Forgot.class);
                startActivity(in);
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = userName.getText().toString();
                String pass = password.getText().toString();

                if (user.equals("") || pass.equals("")) {
                    Toast.makeText(MainActivity.this, "Enter all the required details", Toast.LENGTH_SHORT).show();
                } else {
                    if (db.checkUsername(user) == true) {
                        if (db.checkUserPassword(user, pass) == false) {
                            Toast.makeText(MainActivity.this, "Invalid Credentials",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Login Success",
                                    Toast.LENGTH_SHORT).show();
                            Intent in = new Intent(getApplicationContext(), ChooseActivity.class);
                            startActivity(in);
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "User doesn't exist... please sign up",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
