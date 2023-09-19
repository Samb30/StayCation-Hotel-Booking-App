package com.example.staycation;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.staycation.Home.HomeActivity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private EditText Emailid, Password1;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseApp.initializeApp(this);

        mAuth = FirebaseAuth.getInstance();

        TextView textView2 = findViewById(R.id.textView2);
        Emailid = findViewById(R.id.Emailid);
        Password1 = findViewById(R.id.Password1);
        Button loginbutton1 = findViewById(R.id.Loginbutton1);

        textView2.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, RegisterActivity.class)));

        loginbutton1.setOnClickListener(v -> loginUserAccount());
    }

    private void loginUserAccount() {
        String email = Emailid.getText().toString().trim();
        String password = Password1.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Emailid.setError("Please enter email!!");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Password1.setError("Please enter password!!");
            return;
        }

        if (!isEmailValid(email)) {
            Emailid.setError("Enter valid email");
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Login failed!!\n If new user please register.", Toast.LENGTH_LONG).show();
                    }
                });
    }

    private boolean isEmailValid(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}