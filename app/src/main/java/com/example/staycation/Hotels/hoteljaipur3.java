package com.example.staycation.Hotels;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.staycation.Payment.Pay1Activity;
import com.example.staycation.R;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class hoteljaipur3 extends AppCompatActivity {

    public TextView hname;
    Button button;
    TextView h1price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hjai3);

        button = findViewById(R.id.button);
        hname = findViewById(R.id.textView);
        h1price = findViewById(R.id.price_h12);

        button.setOnClickListener(view -> {
            String hotelName = hname.getText().toString();
            String price = h1price.getText().toString();

            Map<String, Object> bookingData = new HashMap<>();
            bookingData.put("hotelName", hotelName);
            bookingData.put("price", price);
            // Get a Firestore instance
            Intent sintent = getIntent();
            String userId = sintent.getStringExtra("userId");
            String bookingDocumentId = sintent.getStringExtra("bookingDocumentId");

            FirebaseFirestore db = FirebaseFirestore.getInstance();

            assert userId != null;
            assert bookingDocumentId != null;

            DocumentReference userDocumentRef = db.collection("users").document(userId);

            DocumentReference bookingDocumentRef = userDocumentRef.collection("bookingdetails").document(bookingDocumentId);

            bookingDocumentRef.set(bookingData, SetOptions.merge())
                    .addOnSuccessListener(aVoid-> {
                        Log.d("Firestore", "Booking data stored successfully.");
                        Intent intent = new Intent(hoteljaipur3.this, Pay1Activity.class);
                        intent.putExtra("userId", userId);
                        intent.putExtra("bookingDocumentId", bookingDocumentId);
                        startActivity(intent);
                    })
                    .addOnFailureListener(e -> Log.e("FirestoreError", "Error storing booking data: " + e.getMessage()));
        });
    }
}
