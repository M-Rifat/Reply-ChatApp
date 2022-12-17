package com.example.myfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText nameEditText,emailEditText,userNameEditText,passwordEditText;
    private Button signUpButton;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        nameEditText=findViewById(R.id.nameId);
        emailEditText=findViewById(R.id.emailId);
        userNameEditText=findViewById(R.id.userNameId);
        passwordEditText=findViewById(R.id.passId);
        signUpButton=findViewById(R.id.signUpId);
        signUpButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        String name =nameEditText.getText().toString();
        String email =emailEditText.getText().toString();
        String userName =userNameEditText.getText().toString();
        String password =passwordEditText.getText().toString();

        if (password.length()<6){
            passwordEditText.setError("Minimum length should be 6");
            passwordEditText.requestFocus();
            return;
        }
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(SignUpActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(SignUpActivity.this, "Error"+task, Toast.LENGTH_SHORT).show();
            }
        });
    }
}