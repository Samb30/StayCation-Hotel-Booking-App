package com.example.staycation.Home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.staycation.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BookingsActivity extends AppCompatActivity {
    private List<DocumentSnapshot> bookingsList;
    private BookingsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        bookingsList = new ArrayList<>();
        adapter = new BookingsAdapter(bookingsList);

        recyclerView.setAdapter(adapter);

        fetchUserBookings();
    }
    private void fetchUserBookings() {

        String userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users")
                .document(userId)
                .collection("bookingdetails")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        bookingsList.clear();

                        bookingsList.addAll(Objects.requireNonNull(task.getResult()).getDocuments());

                        adapter.notifyDataSetChanged();
                    }
                });
    }
    private static class BookingViewHolder extends RecyclerView.ViewHolder {
        private final TextView hotelNameTextView;
        private final TextView dateTextView;
        private final TextView nightsTextView;
        private final TextView guestsTextView;
        private final TextView priceTextView;
        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);
            hotelNameTextView = itemView.findViewById(R.id.hotelNameTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            nightsTextView = itemView.findViewById(R.id.nightsTextView);
            guestsTextView = itemView.findViewById(R.id.guestsTextView);
            priceTextView = itemView.findViewById(R.id.priceTextView);
        }
    }
    private class BookingsAdapter extends RecyclerView.Adapter<BookingViewHolder> {
        private final List<DocumentSnapshot> bookingsList;

        public BookingsAdapter(List<DocumentSnapshot> bookingsList) {
            this.bookingsList = bookingsList;
        }

        @NonNull
        @Override
        public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_bookingitem, parent, false);
            return new BookingViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
            DocumentSnapshot bookingSnapshot = bookingsList.get(position);
            String hotelName = bookingSnapshot.getString("hotelName");
            String date = bookingSnapshot.getString("Dateofbooking");
            int noOfNights = Integer.parseInt(Objects.requireNonNull(bookingSnapshot.getString("days")));
            int noOfGuests = Integer.parseInt(Objects.requireNonNull(bookingSnapshot.get("guests")).toString());
            int totalPrice = Integer.parseInt(Objects.requireNonNull(bookingSnapshot.get("price")).toString());

            holder.hotelNameTextView.setText(getString(R.string.hotel_name, hotelName));
            holder.dateTextView.setText(getString(R.string.date, date));
            holder.nightsTextView.setText(getString(R.string.no_of_nights, noOfNights));
            holder.guestsTextView.setText(getString(R.string.no_of_guests, noOfGuests));
            holder.priceTextView.setText(getString(R.string.total_price, totalPrice));
        }

        @Override
        public int getItemCount() {
            return bookingsList.size();
        }
    }
}
