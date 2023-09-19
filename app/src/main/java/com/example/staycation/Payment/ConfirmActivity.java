package com.example.staycation.Payment;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.example.staycation.R;
import com.example.staycation.Home.HomeActivity;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class ConfirmActivity extends AppCompatActivity {

    TextView o1, o2, o3, o4, o5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        o1 = findViewById(R.id.textView48);
        o2 = findViewById(R.id.textView49);
        o3 = findViewById(R.id.textView46);
        o4 = findViewById(R.id.textView47);
        o5 = findViewById(R.id.pricepay1);

        retrieveDataFromFirestore();
    }

    private void retrieveDataFromFirestore() {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Intent sintent = getIntent();
        String userId = sintent.getStringExtra("userId");
        String bookingDocumentId = sintent.getStringExtra("bookingDocumentId");

        assert userId != null;

        assert bookingDocumentId != null;
        DocumentReference docRef = db.collection("users").document(userId).collection("bookingdetails").document(bookingDocumentId);

        docRef.get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {

                        String hotelName = documentSnapshot.getString("City");
                        String date = documentSnapshot.getString("Dateofbooking");
                        int noOfNights = Integer.parseInt(Objects.requireNonNull(documentSnapshot.getString("days")));
                        int noOfGuests = Integer.parseInt(Objects.requireNonNull(documentSnapshot.get("guests")).toString());
                        int totalPrice = Integer.parseInt(Objects.requireNonNull(documentSnapshot.get("price")).toString());

                        o1.setText(getString(R.string.hotel_name, hotelName));
                        o2.setText(getString(R.string.date, date));
                        o3.setText(getString(R.string.no_of_nights, noOfNights));
                        o4.setText(getString(R.string.no_of_guests, noOfGuests));
                        o5.setText(getString(R.string.total_price, totalPrice));
                    }
                })
                .addOnFailureListener(e -> {
                });
    }

    public void button4(View view) {
        Intent intent = new Intent(ConfirmActivity.this , HomeActivity.class);
        startActivity(intent);
    }
}