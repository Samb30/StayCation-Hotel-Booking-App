package com.example.staycation.Home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.staycation.R;
import com.example.staycation.Hotels.hoteldelhi1;
import com.example.staycation.Hotels.hoteldelhi2;
import com.example.staycation.Hotels.hoteldelhi3;
import com.example.staycation.Hotels.hotelindore1;
import com.example.staycation.Hotels.hotelindore2;
import com.example.staycation.Hotels.hotelindore3;
import com.example.staycation.Hotels.hoteljaipur1;
import com.example.staycation.Hotels.hoteljaipur2;
import com.example.staycation.Hotels.hoteljaipur3;
import com.example.staycation.Hotels.hotelmumbai1;
import com.example.staycation.Hotels.hotelmumbai2;
import com.example.staycation.Hotels.hotelmumbai3;
import com.example.staycation.Hotels.hotelpune1;
import com.example.staycation.Hotels.hotelpune2;
import com.example.staycation.Hotels.hotelpune3;

public class Home2Activity extends AppCompatActivity {
    CardView[] cardViews;
    TextView suggestionHotelText;
    static String tDays, totalGuests;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);

        tDays = getIntent().getStringExtra("days");
        totalGuests = getIntent().getStringExtra("guests");

        String state = getIntent().getStringExtra("placeId");

        suggestionHotelText = findViewById(R.id.suggestedhoteltext);
        cardViews = new CardView[]{
                findViewById(R.id.novotelaerocity_delhi_view),
                findViewById(R.id.aloft_newdelhi_view),
                findViewById(R.id.taj_palace_delhi_view),
                findViewById(R.id.radisson_blu_indore_view),
                findViewById(R.id.marrioteindore_view),
                findViewById(R.id.essentia_luxuryhotel_indore_view),
                findViewById(R.id.oberoi_mumbai_view),
                findViewById(R.id.tajhotel_mumbai_view),
                findViewById(R.id.vitshotel_mumbai_view),
                findViewById(R.id.novotelpune_view),
                findViewById(R.id.corinthians_pune_view),
                findViewById(R.id.hyatt_regency_pune_view),
                findViewById(R.id.umaidbhawan_jaipur_view),
                findViewById(R.id.trident_jaipur_view),
                findViewById(R.id.mariotte_jaipur_view)
        };

        String[] cmpStates = new String[]{"Delhi", "Indore", "Mumbai", "Pune", "Jaipur"};
        Toast.makeText(getApplicationContext(), state, Toast.LENGTH_SHORT).show();

        for (CardView cardView : cardViews) {
            cardView.setVisibility(View.GONE);
        }

        assert state != null;
        if (state.equals(cmpStates[0])) {
            // Show the appropriate CardViews for Delhi
            cardViews[0].setVisibility(View.VISIBLE); // taj_palace_delhi
            cardViews[1].setVisibility(View.VISIBLE); // aloft_newdelhi
            cardViews[2].setVisibility(View.VISIBLE); // novotelaerocity_delhi
            suggestionHotelText.setVisibility(View.GONE);
        } else if (state.equals(cmpStates[1])) {
            // Show the appropriate CardViews for Indore
            cardViews[3].setVisibility(View.VISIBLE); // radissonblu_indore
            cardViews[4].setVisibility(View.VISIBLE); // essentialuxhotel_indore
            cardViews[5].setVisibility(View.VISIBLE); // essentialuxhotel_indore
            suggestionHotelText.setVisibility(View.GONE);
        } else if (state.equals(cmpStates[2])) {
            // Show the appropriate CardViews for Mumbai
            cardViews[6].setVisibility(View.VISIBLE); // tajhotel_mumbai
            cardViews[7].setVisibility(View.VISIBLE); // oberoi_mumbai
            cardViews[8].setVisibility(View.VISIBLE); // vitshotel_mumbai
            suggestionHotelText.setVisibility(View.GONE);
        } else if (state.equals(cmpStates[3])) {
            // Show the appropriate CardViews for Pune
            cardViews[9].setVisibility(View.VISIBLE); // hyattregency_pune
            cardViews[10].setVisibility(View.VISIBLE); // corinthians_pune
            cardViews[11].setVisibility(View.VISIBLE); // novotel_pune
            suggestionHotelText.setVisibility(View.GONE);
        } else if (state.equals(cmpStates[4])) {
            // Show the appropriate CardViews for Jaipur
            cardViews[12].setVisibility(View.VISIBLE); // mariotte_jaipur
            cardViews[13].setVisibility(View.VISIBLE); // trident_jaipur
            cardViews[14].setVisibility(View.VISIBLE); // umaidbhawan_jaipur
            suggestionHotelText.setVisibility(View.GONE);
        } else {
            for (CardView cardView : cardViews) {
                cardView.setVisibility(View.GONE);
            }
            String s = "WE ARE AVAILABLE IN THAT CITY BUT WE ARE COMING SOON";
            suggestionHotelText.setText(s);
        }

        setClickListeners();
    }

    private void setClickListeners() {
        cardViews[0].setOnClickListener(v -> navigateToHotelPage(hoteldelhi1.class));
        cardViews[1].setOnClickListener(v -> navigateToHotelPage(hoteldelhi2.class));
        cardViews[2].setOnClickListener(v -> navigateToHotelPage(hoteldelhi3.class));
        cardViews[3].setOnClickListener(v -> navigateToHotelPage(hotelindore1.class));
        cardViews[4].setOnClickListener(v -> navigateToHotelPage(hotelindore2.class));
        cardViews[5].setOnClickListener(v -> navigateToHotelPage(hotelindore3.class));
        cardViews[6].setOnClickListener(v -> navigateToHotelPage(hotelmumbai1.class));
        cardViews[7].setOnClickListener(v -> navigateToHotelPage(hotelmumbai2.class));
        cardViews[8].setOnClickListener(v -> navigateToHotelPage(hotelmumbai3.class));
        cardViews[9].setOnClickListener(v -> navigateToHotelPage(hotelpune1.class));
        cardViews[10].setOnClickListener(v -> navigateToHotelPage(hotelpune2.class));
        cardViews[11].setOnClickListener(v -> navigateToHotelPage(hotelpune3.class));
        cardViews[12].setOnClickListener(v -> navigateToHotelPage(hoteljaipur1.class));
        cardViews[13].setOnClickListener(v -> navigateToHotelPage(hoteljaipur2.class));
        cardViews[14].setOnClickListener(v -> navigateToHotelPage(hoteljaipur3.class));
    }
    
    private void navigateToHotelPage(Class<?> hotelActivityClass) {

        Intent sintent = getIntent();
        String userId = sintent.getStringExtra("userId");
        String bookingDocumentId = sintent.getStringExtra("bookingDocumentId");

        Intent intent = new Intent(Home2Activity.this, hotelActivityClass);
        intent.putExtra("userId", userId);
        intent.putExtra("bookingDocumentId", bookingDocumentId);
        startActivity(intent);
    }
}
