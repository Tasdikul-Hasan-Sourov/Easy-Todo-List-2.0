package com.example.easytodolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.util.Calendar;

public class AddEdit extends AppCompatActivity implements View.OnClickListener{
    ImageButton calenderpicker, timepicker;
    private int year, month, day, hour, minute;
    TextView txtDate,txtTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);
        calenderpicker = (ImageButton) findViewById(R.id.calendarId);
        timepicker = (ImageButton) findViewById(R.id.timeId);
        txtDate=(TextView)findViewById(R.id.test);
        txtTime=(TextView)findViewById(R.id.test2);
        calenderpicker.setOnClickListener(this);
        timepicker.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == calenderpicker) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int month, int day) {

                            txtDate.setText(day + "-" + (month + 1) + "-" + year);

                        }
                    }, year, month, day);
            datePickerDialog.show();
        }
        if (v == timepicker) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            hour = c.get(Calendar.HOUR_OF_DAY);
            minute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hour,
                                              int minute) {
                            String timeSet = "";
                            if (hour > 12) {
                                hour -= 12;
                                timeSet = "PM";
                            } else if (hour == 0) {
                                hour += 12;
                                timeSet = "AM";
                            } else if (hour == 12){
                                timeSet = "PM";
                            }else{
                                timeSet = "AM";
                            }
                            String min = "";
                            if (minute < 10)
                                min = "0" + minute ;
                            else
                                min = String.valueOf(minute);


                            txtTime.setText(hour + ":" + min+" "+timeSet);
                        }
                    }, hour, minute, false);
            timePickerDialog.show();


        }
    }
}