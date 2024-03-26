package com.example.staycation.Profile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.staycation.R;

public class HelpActivity extends AppCompatActivity {

    Button emailButton, callButton;
    EditText subjectEditText, bodyEditText;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        subjectEditText = findViewById(R.id.editTextText);
        bodyEditText = findViewById(R.id.body1);
        emailButton = findViewById(R.id.registerb2);
        callButton = findViewById(R.id.registerb3);

        emailButton.setOnClickListener(view -> {
            String recipientEmail = "app@gmail.com";
            String emailSubject = subjectEditText.getText().toString();
            String emailBody = bodyEditText.getText().toString();

            Intent emailIntent = new Intent(Intent.ACTION_SEND);

            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{recipientEmail});
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, emailSubject);
            emailIntent.putExtra(Intent.EXTRA_TEXT, emailBody);

            emailIntent.setType("message/rfc822");

            startActivity(Intent.createChooser(emailIntent, "Choose an Email client:"));
        });

        callButton.setOnClickListener(view -> {
            String phoneNumber = "0000000000";

            Intent phoneIntent = new Intent(Intent.ACTION_CALL);

            phoneIntent.setData(Uri.parse("tel:" + phoneNumber));

            startActivity(phoneIntent);
        });
    }
}
