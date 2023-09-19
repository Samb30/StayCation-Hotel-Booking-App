package com.example.staycation.Payment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.staycation.R;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Pay3Activity extends AppCompatActivity {
    TextView o1, o2, o3, o4, o5;
    private Map<String, Object> bookingDetails;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay3);

        Intent sintent = getIntent();
        String userId = sintent.getStringExtra("userId");
        String bookingDocumentId = sintent.getStringExtra("bookingDocumentId");

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        assert userId != null;
        assert bookingDocumentId != null;

        DocumentReference docRef = db.collection("users").document(userId).collection("bookingdetails").document(bookingDocumentId);

        bookingDetails = new HashMap<>();

        docRef.get()
            .addOnSuccessListener(documentSnapshot -> {
                if(documentSnapshot.exists()) {
                    bookingDetails = documentSnapshot.getData();

                    if (bookingDetails != null ) {
                        String hotelName = Objects.requireNonNull(bookingDetails.get("hotelName")).toString();
                        String date = Objects.requireNonNull(bookingDetails.get("Dateofbooking")).toString();
                        int noOfNights = Integer.parseInt(Objects.requireNonNull(bookingDetails.get("days")).toString());
                        int noOfGuests = Integer.parseInt(Objects.requireNonNull(bookingDetails.get("guests")).toString());
                        int actualPrice = Integer.parseInt(Objects.requireNonNull(bookingDetails.get("price")).toString());
                        int totalPrice = actualPrice * noOfGuests * noOfNights;

                        docRef.update("price", totalPrice);

                        o1 = findViewById(R.id.textView39);
                        o2 = findViewById(R.id.textView40);
                        o3 = findViewById(R.id.textView41);
                        o4 = findViewById(R.id.textView43);
                        o5 = findViewById(R.id.pricepay3);

                        o1.setText(getString(R.string.hotel_name, hotelName));
                        o2.setText(getString(R.string.date, date));
                        o3.setText(getString(R.string.no_of_nights, noOfNights));
                        o4.setText(getString(R.string.no_of_guests, noOfGuests));
                        o5.setText(getString(R.string.total_price, totalPrice));

                    }else {
                        Log.e("FirestoreError", "bookingDetails is null");
                    }
                }else {
                    Log.e("FirestoreError", "Document does not exist");
                }
                })
                .addOnFailureListener(e -> Log.e("FirestoreError", "Error fetching document: " + e.getMessage()));

    }

    public void button3(View view) {
        Intent sintent = getIntent();
        String userId = sintent.getStringExtra("userId");
        String bookingDocumentId = sintent.getStringExtra("bookingDocumentId");
        Intent intent = new Intent(Pay3Activity.this , ConfirmActivity.class);
        intent.putExtra("userId", userId);
        intent.putExtra("bookingDocumentId", bookingDocumentId);
        startActivity(intent);
    }

    public void backButton3(View view ) {
        Intent intent = new Intent(Pay3Activity.this , Pay2Activity.class);
        startActivity(intent);
    }

    public void button312(View view ) {
        String phoneNumber = Objects.requireNonNull(bookingDetails.get("mobile")).toString();

        String m1 = "Dear User, your booking has been confirmed.\nBelow are the details of booking:\n";
        String m3 = "1. Hotel :";
        String m4 = Objects.requireNonNull(bookingDetails.get("City")).toString();
        String m5 = "\n2. Date :";
        String m6 = Objects.requireNonNull(bookingDetails.get("Dateofbooking")).toString();
        String m7 = "\n3. Total Price : Rs: ";
        String m8 = Objects.requireNonNull(bookingDetails.get("price")).toString();
        String m9 = "\nThank you for using our services.";

        String message = m1 + m3 + m4 + m5 + m6 + m7 + m8 + m9;

        SmsManager smsManager = SmsManager.getDefault();

        ArrayList<String> parts = smsManager.divideMessage(message);

        smsManager.sendMultipartTextMessage(phoneNumber, null, parts, null, null);

        Toast.makeText(getApplicationContext(), "Message Sent", Toast.LENGTH_SHORT).show();
    }
}
