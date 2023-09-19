package com.example.staycation;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    private EditText names, lnames, email, mobile, country, Password1;
    private CheckBox box;
    boolean check;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        names = findViewById(R.id.names);
        lnames = findViewById(R.id.names2);
        email = findViewById(R.id.emailid);
        mobile = findViewById(R.id.mobile);
        country = findViewById(R.id.gender);
        Password1 = findViewById(R.id.Password1);
        box = findViewById(R.id.box);
        Button register = findViewById(R.id.registerb);

        register.setOnClickListener(v -> {
            check = checkData();
            if (check) {
                String firstName = names.getText().toString();
                String lastName = lnames.getText().toString();
                String number = mobile.getText().toString();
                String userEmail = email.getText().toString();
                String address = country.getText().toString();

                Map<String, Object> userData = new HashMap<>();
                userData.put("firstName", firstName);
                userData.put("lastName", lastName);
                userData.put("email", userEmail);
                userData.put("mobile", number);
                userData.put("country", address);

                registerNew(userData);
            }
        });
    }
    private void registerNew(Map<String, Object> userData) {
        String email, password;

        email = this.email.getText().toString();
        password = Password1.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Please enter email!!", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Please enter password!!", Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {

                            String userId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

                            userData.put("userId", userId);

                            DocumentReference userReference = db.collection("users").document(userId);

                            userReference.set(userData)
                                    .addOnSuccessListener(aVoid -> {
                                        Toast.makeText(getApplicationContext(), "Registration successful!", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                    })
                                    .addOnFailureListener(e -> Toast.makeText(getApplicationContext(), "Registration failed! Please try again later", Toast.LENGTH_LONG).show());
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Registration failed! Please try again later", Toast.LENGTH_LONG).show();
                    }
                });
    }

    boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    boolean checkData() {
        if (isEmpty(names)) {
            names.setError("First Name is required");
            return false;
        }

        if (isEmpty(lnames)) {
            lnames.setError("Last Name is required");
            return false;
        }

        if (!isEmail(email)) {
            email.setError("Enter a valid email");
            return false;
        }

        if (isEmpty(Password1)) {
            Password1.setError("Password is required");
            return false;
        }
        if (Password1.length() < 8) {
            Password1.setError("Password length must be greater than 8");
            return false;
        }

        if (mobile.length() < 10) {
            mobile.setError("Invalid mobile number");
            return false;
        }

        if (isEmpty(country)) {
            country.setError("Country is required");
            return false;
        }

        if (!box.isChecked()) {
            Toast t = Toast.makeText(this, "Check this box!", Toast.LENGTH_SHORT);
            t.show();
            return false;
        }
        return true;
    }
}
