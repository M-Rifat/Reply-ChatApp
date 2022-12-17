package com.example.myfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText emailText, passText;
    private Button button;
    private TextView textView;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailText = findViewById(R.id.emailId);
        passText = findViewById(R.id.passId);
        button = findViewById(R.id.loginId);
        textView = findViewById(R.id.signUpClickId);
        progressBar = findViewById(R.id.progressId);

        button.setOnClickListener(this);
        textView.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.signUpClickId) {
            Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
            startActivity(intent);
        }

        if (v.getId() == R.id.loginId) {
            progressBar.setVisibility(View.VISIBLE);

            String email = emailText.getText().toString();
            String password = passText.getText().toString();

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailText.setError("Enter a valid email!");
                emailText.requestFocus();
                return;
            }

            if (password.length() < 6) {
                passText.setError("Minimum length should be 6");
                passText.requestFocus();
                return;
            }
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressBar.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        finish();
                        Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    } else
                        Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}