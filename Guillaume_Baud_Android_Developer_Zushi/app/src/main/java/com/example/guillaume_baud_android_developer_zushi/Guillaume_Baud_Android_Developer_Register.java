package com.example.guillaume_baud_android_developer_zushi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class Guillaume_Baud_Android_Developer_Register extends AppCompatActivity {

    EditText mFullName, mEmail, mPassword, mVerifyPassword;
    Button mRegisterBtn;
    TextView mLogInBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guillaume__baud__android__developer__register);

        mFullName       = findViewById(R.id.fullName);
        mEmail          = findViewById(R.id.email);
        mPassword       = findViewById(R.id.password);
        mVerifyPassword = findViewById(R.id.verifyPassword);
        mRegisterBtn    = findViewById(R.id.registerButton);
        mLogInBtn       = findViewById(R.id.signInNavigationButton);


        fAuth           = FirebaseAuth.getInstance();
        progressBar     = findViewById(R.id.progressBar);

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email            = mEmail.getText().toString().trim();
                String password         = mPassword.getText().toString().trim();
                String verifyPassword   = mVerifyPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is required");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Password is required");
                    return;
                }

                if (TextUtils.isEmpty(verifyPassword)) {
                    mVerifyPassword.setError("Verify Password is required");
                }

                if (password.length() < 6) {
                    mPassword.setError("Password must be at least 6 characters");
                    return;
                }

                if (password.equals(verifyPassword) == false) {
                    mVerifyPassword.setError("The 2 passwords must be identical");
                }

                progressBar.setVisibility(View.VISIBLE);

                // register the user in firebase

                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Guillaume_Baud_Android_Developer_Register.this, "User Created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            Toast.makeText(Guillaume_Baud_Android_Developer_Register.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        mLogInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Guillaume_Baud_Android_Developer_Login.class));
                finish();
            }
        });
    }
}
