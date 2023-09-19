package com.example.staycation.Payment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.staycation.R;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class Pay1Activity extends AppCompatActivity {
    EditText FirstName, LastName, emailAddress, Address, PostCode, Country, MobilePhone;
    public Map<String, Object> bookingDetails = new HashMap<>();
    Button submitBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay1);

        FirstName = findViewById(R.id.firstName);
        LastName = findViewById(R.id.lastName);
        emailAddress = findViewById(R.id.emailAddress);
        PostCode = findViewById(R.id.postCode);
        Country = findViewById(R.id.country);
        MobilePhone = findViewById(R.id.mobilePhone);
        Address = findViewById(R.id.address);
        submitBtn = findViewById(R.id.button1);

        submitBtn.setOnClickListener(view -> {
            if (TextUtils.isEmpty(FirstName.getText().toString())) {
                FirstName.setError("First name is mandatory");
                return;
            }
            if (TextUtils.isEmpty(LastName.getText().toString())) {
                LastName.setError("Last name is mandatory");
                return;
            }
            if (TextUtils.isEmpty(emailAddress.getText().toString())) {
                emailAddress.setError("Email address is mandatory");
                return;
            }
            if (!isValidEmail(emailAddress.getText().toString())) {
                emailAddress.setError("Invalid email address");
                return;
            }
            if (TextUtils.isEmpty(PostCode.getText().toString())) {
                PostCode.setError("Post Code is mandatory");
                return;
            }
            if (TextUtils.isEmpty(Country.getText().toString())) {
                Country.setError("Country is mandatory");
                return;
            }
            if (TextUtils.isEmpty(MobilePhone.getText().toString())) {
                MobilePhone.setError("Mobile Phone is mandatory");
                return;
            }
            if (!isValidPhoneNumber(MobilePhone.getText().toString())) {
                MobilePhone.setError("Invalid phone number");
                return;
            }
            if (TextUtils.isEmpty(Address.getText().toString())) {
                Address.setError("Address is mandatory");
                return;
            }

            saveRegistrationData();
        });
    }

    private void saveRegistrationData() {
        String firstName = FirstName.getText().toString();
        String lastName = LastName.getText().toString();
        String email = emailAddress.getText().toString();
        String postCode = PostCode.getText().toString();
        String country = Country.getText().toString();
        String mobilePhone = MobilePhone.getText().toString();
        String address = Address.getText().toString();

        bookingDetails.put("firstName", firstName);
        bookingDetails.put("lastName", lastName);
        bookingDetails.put("email", email);
        bookingDetails.put("postcode", postCode);
        bookingDetails.put("country", country);
        bookingDetails.put("mobile", mobilePhone);
        bookingDetails.put("address", address);

        Intent sintent = getIntent();
        String userId = sintent.getStringExtra("userId");
        String bookingDocumentId = sintent.getStringExtra("bookingDocumentId");

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        assert userId != null;
        assert bookingDocumentId != null;

        DocumentReference userDocumentRef = db.collection("users").document(userId);

        DocumentReference bookingDocumentRef = userDocumentRef.collection("bookingdetails").document(bookingDocumentId);
        bookingDocumentRef.set(bookingDetails, SetOptions.merge())
                .addOnSuccessListener(aVoid-> {
                    Log.d("Firestore", "Booking data stored successfully.");
                    Intent intent = new Intent(this, Pay2Activity.class);
                    intent.putExtra("userId", userId);
                    intent.putExtra("bookingDocumentId", bookingDocumentId);
                    startActivity(intent);
                })
                .addOnFailureListener(e -> Log.e("FirestoreError", "Error storing booking data: " + e.getMessage()));
    }
    private boolean isValidEmail(String emailAddress) {
        return !TextUtils.isEmpty(emailAddress) && android.util.Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches();
    }
    private boolean isValidPhoneNumber(String mobilePhone) {
        return !TextUtils.isEmpty(mobilePhone) && android.util.Patterns.PHONE.matcher(mobilePhone).matches() && mobilePhone.length() == 10;
    }
}
