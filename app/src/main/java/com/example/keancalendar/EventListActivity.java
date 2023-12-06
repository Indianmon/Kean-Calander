package com.example.keancalendar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EventListActivity extends AppCompatActivity {

    private ListView eventListView;
    private TextView selectedDateTextView;
    private List<Event> events;
    private ArrayAdapter<String> eventAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        eventListView = findViewById(R.id.eventListView);
        selectedDateTextView = findViewById(R.id.selectedDateTextView);

        events = new ArrayList<>();
        eventAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        eventListView.setAdapter(eventAdapter);

        // Receive selected date from MainActivity
        Intent intent = getIntent();
        if (intent.hasExtra("YEAR") && intent.hasExtra("MONTH") && intent.hasExtra("DAY_OF_MONTH")) {
            int year = intent.getIntExtra("YEAR", 0);
            int month = intent.getIntExtra("MONTH", 0);
            int dayOfMonth = intent.getIntExtra("DAY_OF_MONTH", 0);

            // Display the selected date
            String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
            selectedDateTextView.setText("Selected Date: " + selectedDate);

            // Display events for the selected date
            displayEventsForDate(year, month, dayOfMonth);
        }
    }

    private void displayEventsForDate(int year, int month, int dayOfMonth) {
        // Clear previous events
        events.clear();

        // Add sample events for testing
        events.add(new Event("Event 1", getDate(year, month, dayOfMonth, 10, 0)));
        events.add(new Event("Event 2", getDate(year, month, dayOfMonth, 14, 30)));

        // Display events
        updateEventListView();
    }

    private void updateEventListView() {
        // Clear the adapter
        eventAdapter.clear();

        // Add events to the adapter
        for (Event event : events) {
            eventAdapter.add(event.getFormattedDate() + " - " + event.getTitle());
        }

        // Notify the adapter about the data change
        eventAdapter.notifyDataSetChanged();
    }

    private Date getDate(int year, int month, int dayOfMonth, int hourOfDay, int minute) {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.set(year, month, dayOfMonth, hourOfDay, minute);
        return calendar.getTime();
    }
}
