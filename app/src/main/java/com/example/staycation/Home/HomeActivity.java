package com.example.staycation.Home;

import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.staycation.LoginActivity;
import com.example.staycation.Profile.Alldetails;
import com.example.staycation.Profile.ProfileActivity;
import com.example.staycation.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class HomeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button searchButton;
    Spinner noOfNightsSpinner;
    Spinner noOfGuestsSpinner;
    static String noOfNights, noOfGuests;

    String checkInDate;
    EditText date;
    String selectedPlace;
    AutoCompleteTextView place;
    CardView marriottJaipur, tajPalaceDelhi, tajHotelMumbai, radissonBluIndore;

    private FirebaseFirestore db;

    private Map<String, Object> bookingDetails = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        db = FirebaseFirestore.getInstance();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    "channel_id",
                    "Booking Notifications",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        place = findViewById(R.id.places);
        date = findViewById(R.id.dates);
        noOfNightsSpinner = findViewById(R.id.nights_to_stay);
        noOfGuestsSpinner = findViewById(R.id.No_of_Guests);
        marriottJaipur = findViewById(R.id.mariottejaipursuggestion);
        tajPalaceDelhi = findViewById(R.id.tajpalacedelhisuggestion);
        tajHotelMumbai = findViewById(R.id.tajhotelsuggestion);
        radissonBluIndore = findViewById(R.id.radissonbluindoresuggestion);

        ArrayAdapter<CharSequence> nightsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.nights));
        nightsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        noOfNightsSpinner.setAdapter(nightsAdapter);

        ArrayAdapter<CharSequence> guestsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.Guests));
        guestsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        noOfGuestsSpinner.setAdapter(guestsAdapter);

        searchButton = findViewById(R.id.search_button);

        fetchCityNamesFromFirestore();

        place.setOnItemClickListener((parent, view, position, id) -> selectedPlace = parent.getItemAtPosition(position).toString());

        date.setOnClickListener(v -> showDatePicker());

        searchButton.setOnClickListener(v -> {
            noOfNights = noOfNightsSpinner.getSelectedItem().toString();
            noOfGuests = noOfGuestsSpinner.getSelectedItem().toString();

            Alldetails.date_det = date.getText().toString();
            Alldetails.night_det = noOfNights;
            Alldetails.guest_det = noOfGuests;

            storeBookingDetailsInFirestore();
        });

        marriottJaipur.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, Homehotel1Activity.class);
            startActivity(intent);
        });
        tajPalaceDelhi.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, Homehotel4Activity.class);
            startActivity(intent);
        });
        tajHotelMumbai.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, Homehotel2Activity.class);
            startActivity(intent);
        });
        radissonBluIndore.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, Homehotel3Activity.class);
            startActivity(intent);
        });
    }

    public void home1(View view) {
        Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    public void home2(View view) {
        Intent intent = new Intent(HomeActivity.this, BookingsActivity.class);
        startActivity(intent);
    }

    public void home3(View view) {
        Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
        startActivity(intent);
    }

    public void home4(View view) {
        FirebaseAuth.getInstance().signOut();

        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void fetchCityNamesFromFirestore() {
        db.collection("cities")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<String> cityNamesList = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String cityName = document.getString("Name");
                            if (cityName != null) {
                                cityNamesList.add(cityName);
                            }
                        }

                        ArrayAdapter<String> stateAdapter = new ArrayAdapter<>(HomeActivity.this, android.R.layout.simple_list_item_1, cityNamesList);
                        place.setAdapter(stateAdapter);
                    } else {
                        Log.e("FirestoreError", "Error fetching city names: " + task.getException());
                    }
                });
    }

    private void showDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(HomeActivity.this, (view, year1, month1, dayOfMonth) -> {
            Calendar selectedCalendar = Calendar.getInstance();
            selectedCalendar.set(year1, month1, dayOfMonth);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            checkInDate = sdf.format(selectedCalendar.getTime());
            date.setText(checkInDate);
        }, year, month, day);
        datePickerDialog.show();
    }

    private void storeBookingDetailsInFirestore() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            bookingDetails.put("City", selectedPlace);
            bookingDetails.put("Dateofbooking", checkInDate);
            bookingDetails.put("days", noOfNights);
            bookingDetails.put("guests", noOfGuests);

            FirebaseFirestore db = FirebaseFirestore.getInstance();

            String userId = currentUser.getUid();

            CollectionReference bookingDetailsCollection = db.collection("users").document(userId).collection("bookingdetails");
            bookingDetailsCollection.add(bookingDetails)
                    .addOnSuccessListener(documentReference -> {
                        String bookingDocumentId = documentReference.getId();

                        Intent intent = new Intent(HomeActivity.this, Home2Activity.class);
                        intent.putExtra("placeId", selectedPlace);
                        intent.putExtra("userId", userId);
                        intent.putExtra("bookingDocumentId", bookingDocumentId);

                        startActivity(intent);
                        Log.d("Firestore", "Booking document ID: " + bookingDocumentId);
                    })
                    .addOnFailureListener(e -> Log.e("FirestoreError", "Error storing booking details: " + e.getMessage()));
        }
    }
}
