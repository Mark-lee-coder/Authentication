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
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    Button button2, button3;
    EditText name;
    EditText age;
    EditText email;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();
        button2 = findViewById(R.id.button3);
        button3 = findViewById(R.id.button4);
        name = findViewById(R.id.etName);
        age = findViewById(R.id.etAge);
        email = findViewById(R.id.etEmail);
        password = findViewById(R.id.etPassword);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullname = name.getText().toString().trim();
                String age1 = age.getText().toString().trim();
                String email1 = email.getText().toString().trim();
                String password1 = password.getText().toString().trim();

                if (fullname.isEmpty()){
                    name.setError("Full name is required");
                    name.requestFocus();
                    return;
                }
                if (age1.isEmpty()){
                    age.setError("Age is required");
                    age.requestFocus();
                    return;
                }
                if (email1.isEmpty()){
                    email.setError("Email is required");
                    email.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email1).matches()){
                    email.setError("Please provide a valid email");
                    email.requestFocus();
                    return;
                }
                if (password1.isEmpty()){
                    password.setError("Please fill out this field");
                    password.requestFocus();
                    return;
                }
                if (password1.length()<6){
                    password.setError("The password length must be 6 characters long");
                    password.requestFocus();
                    return;
                }

                firebaseAuth.createUserWithEmailAndPassword(email1,password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            User user = new User(fullname,age1,email1);
                            FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                 if (task.isSuccessful()){
                                     Toast.makeText(Register.this,"User has been registered successfully",Toast.LENGTH_LONG).show();
                                 }
                                 else{
                                     Toast.makeText(Register.this,"User has not been registered successfully. Try again!",Toast.LENGTH_LONG).show();
                                 }
                                }
                            });
                        }
                        else {
                            Toast.makeText(Register.this,"Failed to register the user",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}