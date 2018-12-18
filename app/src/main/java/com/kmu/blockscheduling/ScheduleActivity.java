package com.kmu.blockscheduling;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;
import java.util.Calendar;
import java.util.Date;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class ScheduleActivity extends AppCompatActivity {
    String title, date, rating;
    TextInputEditText dateText;
    TextInputEditText scheduleTitle;
    TextInputEditText scheduleRating;
    Button saveButton;
    Button deleteButton;
    Button picker;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        final DBHandler handler = new DBHandler(ScheduleActivity.this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        dateText = (TextInputEditText) findViewById(R.id.scheduleDate);
        scheduleTitle = (TextInputEditText) findViewById(R.id.scheduleTitle);
        scheduleRating = (TextInputEditText) findViewById(R.id.scheduleRating);
        saveButton = (Button) findViewById(R.id.saveButton);
        deleteButton = (Button) findViewById(R.id.deleteButton);
        picker = (Button) findViewById(R.id.picker);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int Value = extras.getInt("id");
            if (Value > 0) {
                Cursor rs = handler.getData(Value);
                int id = Value;
                rs.moveToFirst();
                title = rs.getString(rs.getColumnIndex("title"));
                rating = rs.getString(rs.getColumnIndex("rating"));
                date = rs.getString(rs.getColumnIndex("endDate"));
                if (!rs.isClosed()) {
                    rs.close();
                }

                scheduleTitle.setText((CharSequence) title);
                scheduleRating.setText((CharSequence) rating);
                dateText.setText((CharSequence) date);
            }
        }
        picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cal = Calendar.getInstance();
                DatePickerDialog dateDialog = new DatePickerDialog(ScheduleActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        date = String.format("%d/%d/%d", year, month + 1, dayOfMonth);
                        dateText.setText(date);
                    }
                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));

                dateDialog.getDatePicker().setMinDate(new Date().getTime());
                dateDialog.show();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = getIntent().getExtras();
                if (extras != null) {
                    int value = extras.getInt("id");
                    if (value < 0) {
                        Toast.makeText(getApplicationContext(), "수정되지 않았음", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    Toast.makeText(getApplicationContext(), "수정되었음", Toast.LENGTH_SHORT).show();
                    handler.updateSchedule(value, title, rating, date);
                } else {
                    if (title == null && date == null && rating == null) {
                        Toast.makeText(getApplicationContext(), "추가되지 않았음", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    handler.insertSchedule(title, rating, date);
                    Toast.makeText(getApplicationContext(), "추가되었음", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = getIntent().getExtras();
                if (extras != null) {
                    int value = extras.getInt("id");
                    if (value > 0) {
                        handler.deleteSchedule(value);
                        Toast.makeText(getApplicationContext(), "삭제되었음", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "삭제되지 않았음", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
