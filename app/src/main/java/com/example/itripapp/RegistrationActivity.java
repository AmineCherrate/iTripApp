package com.example.itripapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button registerBtn;
    private TextView loginpage;


    private FirebaseAuth mAuth;

    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        mAuth = FirebaseAuth.getInstance();
        mDialog = new ProgressDialog(this);


        email = findViewById(R.id.registerUser);
        password = findViewById(R.id.registerPass);
        registerBtn = findViewById(R.id.registerBtn);
        loginpage = findViewById(R.id.logintxt);

        loginpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mEmail = email.getText().toString().trim();
                String mPassword = password.getText().toString().trim();

                if(TextUtils.isEmpty(mEmail))
                {
                    email.setError("This field is required..");
                    return;
                }
                if(TextUtils.isEmpty(mPassword))
                {
                    password.setError("this field is required..");
                    return;
                }
                mDialog.setMessage("Processing...");
                mDialog.show();
                mAuth.createUserWithEmailAndPassword(mEmail,mPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            Toast.makeText(getApplicationContext(),"Succeessful", Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                                mDialog.dismiss();

                        }else
                        {
                            Toast.makeText(getApplicationContext(),"Problem...",Toast.LENGTH_SHORT).show();
                            mDialog.dismiss();

                        }
                    }
                });

            }
        });
    }
}
