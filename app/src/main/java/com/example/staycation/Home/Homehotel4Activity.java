package com.example.staycation.Home;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.staycation.R;

public class Homehotel4Activity extends AppCompatActivity {
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeh4);
        button = findViewById(R.id.button);

        button.setOnClickListener(view -> {
            Intent intent = new Intent(Homehotel4Activity.this, HomeActivity.class);
            startActivity(intent);
        });
    }
}