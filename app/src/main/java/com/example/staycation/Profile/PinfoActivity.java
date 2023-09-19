package com.example.staycation.Profile;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.staycation.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;

public class PinfoActivity extends AppCompatActivity {
    private TextView o1, o2, o3, o4;
    private ListenerRegistration userDataListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinfo);

        o1 = findViewById(R.id.textView25);
        o2 = findViewById(R.id.textView33);
        o3 = findViewById(R.id.textView29);
        o4 = findViewById(R.id.textView30);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String currentUserId = currentUser.getUid();

            userDataListener = db.collection("users")
                    .document(currentUserId)
                    .addSnapshotListener((documentSnapshot, e) -> {
                        if (e != null) {
                            return;
                        }

                        if (documentSnapshot != null && documentSnapshot.exists()) {
                            String firstName = documentSnapshot.getString("firstName");
                            String country = documentSnapshot.getString("country");
                            String email = documentSnapshot.getString("email");
                            String mobile = documentSnapshot.getString("mobile");

                            o1.setText(firstName);
                            o2.setText(country);
                            o3.setText(email);
                            o4.setText(mobile);
                        }
                    });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (userDataListener != null) {
            userDataListener.remove();
        }
    }
}
