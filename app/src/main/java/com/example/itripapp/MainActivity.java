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

public class MainActivity extends AppCompatActivity {


    private EditText email;
    private EditText password;
    private Button loginBtn;
    private TextView registertxt;
    private FirebaseAuth mAuth;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth=FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser()!=null){

            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
        }




        mDialog = new ProgressDialog(this);

        email = findViewById(R.id.loginUser);
        password = findViewById(R.id.loginPass);
        loginBtn = findViewById(R.id.loginBtn);

        registertxt=findViewById(R.id.signuptxt);


        loginBtn.setOnClickListener(new View.OnClickListener() {
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

                mAuth.signInWithEmailAndPassword(mEmail,mPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                         if (task.isSuccessful()){
                             Toast.makeText(getApplicationContext(),"Login is successful",Toast.LENGTH_LONG).show();
                             startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                             mDialog.dismiss();

                         }else{

                             Toast.makeText(getApplicationContext(),"Problem",Toast.LENGTH_LONG).show();
                             mDialog.dismiss();
                         }
                    }
                });


            }
        });

        registertxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),RegistrationActivity.class));

            }
        });

    }
}
