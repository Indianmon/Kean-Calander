package com.example.keancalendar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private TextView selectedDateTextView;
    private EditText eventEditText;
    private List<Event> events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calendarView = findViewById(R.id.calendarView);
        selectedDateTextView = findViewById(R.id.selectedDateTextView);
        eventEditText = findViewById(R.id.eventEditText);

        events = new ArrayList<>();

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                selectedDateTextView.setText("Selected Date: " + selectedDate);

                // Display events for the selected date
                displayEventsForDate(year, month, dayOfMonth);
            }
        });

        // Set a specific date programmatically (e.g., January 1, 2023)
        setSpecificDate(2023, 0, 1);
    }

    private void setSpecificDate(int year, int month, int dayOfMonth) {
        long dateInMillis = getDateInMillis(year, month, dayOfMonth);
        calendarView.setDate(dateInMillis, true, true);
    }

    private long getDateInMillis(int year, int month, int dayOfMonth) {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        Date date = calendar.getTime();
        return date.getTime();
    }

    public void showSelectedDate(View view) {
        long selectedDateInMillis = calendarView.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        try {
            String formattedDate = sdf.format(new Date(selectedDateInMillis));
            Toast.makeText(this, "Selected Date: " + formattedDate, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error formatting date", Toast.LENGTH_SHORT).show();
        }
    }

    public void addSampleEvent(View view) {
        // Add a sample event for testing
        Event event = new Event("Sample Event", new Date());
        events.add(event);
        Toast.makeText(this, "Sample event added for " + event.getFormattedDate(), Toast.LENGTH_SHORT).show();

        // Open EventListActivity
        Intent intent = new Intent(this, EventListActivity.class);
        startActivity(intent);
    }

    public void addCustomEvent(View view) {
        // Get the event title from the EditText
        String eventTitle = eventEditText.getText().toString().trim();

        if (!eventTitle.isEmpty()) {
            // Add a custom event with the user-provided title for testing
            Event customEvent = new Event(eventTitle, new Date());
            events.add(customEvent);

            // Display a message indicating the custom event was added
            Toast.makeText(this, "Custom event added: " + customEvent.getFormattedDate(), Toast.LENGTH_SHORT).show();

            // Open EventListActivity with the custom event details
            Intent intent = new Intent(this, EventListActivity.class);
            intent.putExtra("CUSTOM_EVENT_TITLE", customEvent.getTitle());
            intent.putExtra("CUSTOM_EVENT_DATE", customEvent.getFormattedDate());
            startActivity(intent);
        } else {
            // Display an error message if the user didn't enter a title
            Toast.makeText(this, "Please enter an event title", Toast.LENGTH_SHORT).show();
        }
    }

    private void displayEventsForDate(int year, int month, int dayOfMonth) {
        // Clear previous events
        events.clear();

        // Add sample events for testing
        events.add(new Event("Event 1", getDate(year, month, dayOfMonth, 10, 0)));
        events.add(new Event("Event 2", getDate(year, month, dayOfMonth, 14, 30)));

        // Display events
        StringBuilder eventsText = new StringBuilder("Events for " + dayOfMonth + "/" + (month + 1) + "/" + year + ":\n");
        for (Event event : events) {
            eventsText.append(event.getFormattedDate()).append(" - ").append(event.getTitle()).append("\n");
        }
        Toast.makeText(this, eventsText.toString(), Toast.LENGTH_LONG).show();
    }

    private Date getDate(int year, int month, int dayOfMonth, int hourOfDay, int minute) {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.set(year, month, dayOfMonth, hourOfDay, minute);
        return calendar.getTime();
    }
}
