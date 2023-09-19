package com.example.staycation.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.staycation.Payment.ConfirmActivity;
import com.example.staycation.LoginActivity;
import com.example.staycation.R;
import com.example.staycation.Home.HomeActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;

public class ProfileActivity extends AppCompatActivity {
    private TextView text14;
    private ListenerRegistration userListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        TextView text11 = findViewById(R.id.textView7);
        TextView text12 = findViewById(R.id.textView6);
        TextView text13 = findViewById(R.id.textView4);
        text14 = findViewById(R.id.text7);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String currentUserId = currentUser.getUid();

            userListener = firestore.collection("users").document(currentUserId)
                    .addSnapshotListener((documentSnapshot, e) -> {
                        if (e != null) {
                            return;
                        }

                        if (documentSnapshot != null && documentSnapshot.exists()) {
                            String firstName = documentSnapshot.getString("firstName");

                            if (firstName != null) {
                                text14.setText(firstName);
                            }
                        }
                    });
        }

        text11.setOnClickListener(v -> startActivity(new Intent(ProfileActivity.this, HelpActivity.class)));
        text12.setOnClickListener(v -> startActivity(new Intent(ProfileActivity.this, FeedbackActivity.class)));
        text13.setOnClickListener(v -> startActivity(new Intent(ProfileActivity.this, PinfoActivity.class)));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (userListener != null) {
            userListener.remove();
        }
    }

    public void home1(View view) {
        Intent intent = new Intent(ProfileActivity.this , HomeActivity.class);
        startActivity(intent);
    }

    public void home2(View view) {
        Intent intent = new Intent(ProfileActivity.this , ConfirmActivity.class);
        startActivity(intent);
    }

    public void home3(View view) {
        Intent intent = new Intent(ProfileActivity.this , ProfileActivity.class);
        startActivity(intent);
    }

    public void home4(View view) {
        Intent intent = new Intent(ProfileActivity.this , LoginActivity.class);
        startActivity(intent);
    }
}
