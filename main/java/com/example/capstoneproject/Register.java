package com.example.capstoneproject;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    EditText reg_username, reg_password, re_password;
    Button reg_btn;
    DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        reg_username = findViewById(R.id.regusername);
        reg_password = findViewById(R.id.regpassword);
        re_password = findViewById(R.id.repassword);
        reg_btn = findViewById(R.id.signupbtn);
        db=new DBHelper(this);

        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = reg_username.getText().toString();
                String pass = reg_password.getText().toString();
                String re_pass = re_password.getText().toString();

                if(user.equals("")||pass.equals("")||re_pass.equals(""))
                {
                    Toast.makeText(Register.this, "Enter all the required details",
                            Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(pass.equals(re_pass))
                    {
                        if(!db.checkUsername(user))
                        {
                            Boolean insert=db.insertData(user,pass);
                            if(insert)
                            {
                                Toast.makeText(Register.this, "Registered Successfully",
                                        Toast.LENGTH_SHORT).show();
                                Intent in = new Intent(getApplicationContext(), ChooseActivity.class);
                                startActivity(in);
                            }
                            else
                            {
                                Toast.makeText(Register.this, "User already exists...please SIGN IN",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(Register.this, "Passwords Not Matching", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }
}