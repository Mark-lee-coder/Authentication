package com.example.authentication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Profile extends AppCompatActivity {
    TextView etName, etAge, etEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        etName = findViewById(R.id.etName);
        etAge = findViewById(R.id.etAge);
        etEmail = findViewById(R.id.etEmail);

        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("fullname");
        String age = bundle.getString("age");
        String email = bundle.getString("email");

        etName.setText("Name:"+name);
        etAge.setText("Age:"+age);
        etEmail.setText("Email:"+email);
    }
}