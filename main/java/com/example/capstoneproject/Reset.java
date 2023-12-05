package com.example.capstoneproject;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Reset extends AppCompatActivity {

    EditText pass,repass;
    TextView user;
    Button confirm;
    DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);

        user=findViewById(R.id.userText);
        pass=findViewById(R.id.reset_password);
        repass=findViewById(R.id.reset_re_password);
        confirm=findViewById(R.id.confirmbtn);
        db=new DBHelper(this);

        Intent intent=getIntent();
        user.setText(intent.getStringExtra("username"));

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username=user.getText().toString();
                String password=pass.getText().toString();
                String re_pass=repass.getText().toString();

                if(password.equals("")||re_pass.equals(""))
                {
                    Toast.makeText(Reset.this, "Enter Required details", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(password.equals(re_pass))
                    {
                        if(db.passwordUpdate(username,password)==true)
                        {
                            Intent in=new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(in);
                            Toast.makeText(Reset.this, "Password Updated Successfully", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(Reset.this, "Password Update Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(Reset.this, "Passwords not matched", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
