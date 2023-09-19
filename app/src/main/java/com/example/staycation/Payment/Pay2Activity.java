package com.example.staycation.Payment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.staycation.R;

import org.jetbrains.annotations.Contract;

public class Pay2Activity extends AppCompatActivity {
    EditText CardNumber, Expiry, Cvv, Name;
    Button submitBtn2;

    public void button2(View view) {
        Intent sintent = getIntent();
        String userId = sintent.getStringExtra("userId");
        String bookingDocumentId = sintent.getStringExtra("bookingDocumentId");

        Intent intent = new Intent(this, Pay3Activity.class);
        intent.putExtra("userId", userId);
        intent.putExtra("bookingDocumentId", bookingDocumentId);
        startActivity(intent);
    }

    public void backButton2(View view) {
        Toast.makeText(this, "Going to Reservation Page", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, Pay1Activity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay2);

        CardNumber = findViewById(R.id.cardNumber);
        Cvv = findViewById(R.id.cvv);
        Expiry = findViewById(R.id.expiry);
        Name = findViewById(R.id.name);

        submitBtn2 = findViewById(R.id.button2);

        submitBtn2.setOnClickListener(view -> {
            if (TextUtils.isEmpty(CardNumber.getText().toString())) {
                CardNumber.setError("Card Number is mandatory");
                return;
            }
            if (!isValidCardNumber(CardNumber.getText().toString())) {
                CardNumber.setError("Invalid Card Number");
                return;
            }

            if (TextUtils.isEmpty(Expiry.getText().toString())) {
                Expiry.setError("Entering Expiry is mandatory");
                return;
            }

            if (TextUtils.isEmpty(Cvv.getText().toString())) {
                Cvv.setError("CVV is mandatory");
                return;
            }
            if (!isValidCvv(Cvv.getText().toString())) {
                Cvv.setError("Invalid CVV Number");
                return;
            }

            if (TextUtils.isEmpty(Name.getText().toString())) {
                Name.setError("Name of the recipient is mandatory");
                return;
            }

            button2(view);
        });
    }

    @Contract(pure = true)
    private boolean isValidCardNumber(String cardNumber) {
        return cardNumber.length() == 12;
    }

    private boolean isValidCvv(String cvv) {
        return cvv.length() == 3;
    }
}
