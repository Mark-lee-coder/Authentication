package com.example.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    EditText email, password;
    Button login, register;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        email = findViewById(R.id.etEmail);
        password = findViewById(R.id.etPassword);
        firebaseAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = email.getText().toString().trim();
                String Password = password.getText().toString().trim();

                if(Email.isEmpty()){
                    email.setError("Email is required!");
                    email.requestFocus();
                    return;
                }

                if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
                    email.setError("Enter a valid email!");
                    email.requestFocus();
                    return;
                }
                if(Password.isEmpty()){
                    password.setError("Password is required");
                    password.requestFocus();
                    return;
                }
                if(Password.length()<6){
                    password.setError("Minimum password length is 6 characters");
                    password.requestFocus();
                    return;
                }

                firebaseAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent intent = new Intent(MainActivity.this,Profile.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(MainActivity.this,"Failed to login! Please Check your credentials", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Register.class);
                startActivity(intent);
            }
        });
    }
}