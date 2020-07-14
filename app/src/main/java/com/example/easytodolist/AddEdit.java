package com.example.easytodolist;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.DialogFragment;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddEdit extends AppCompatActivity implements View.OnClickListener{

    public static final String EXTRA_ID =
            "com.example.easytodolist.EXTRA_ID";
    public static final String EXTRA_TITLE =
            "com.example.easytodolist.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION =
            "com.example.easytodolist.EXTRA_DESCRIPTION";
    public static final String EXTRA_DATE =
            "com.example.easytodolist.EXTRA_DATE";
    public static final String EXTRA_TIME =
            "com.example.easytodolist.EXTRA_TIME ";
    ImageButton calenderpicker, timepicker;
    Button saveItem,cancelItem;
    private int year, month, day, hour, minute;
    TextView txtDate,txtTime;
    EditText addTitle,addDes;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);
        calenderpicker = (ImageButton) findViewById(R.id.calendarId);

        timepicker = (ImageButton) findViewById(R.id.timeId);
        saveItem = (Button) findViewById(R.id.saveItem);
        cancelItem = (Button) findViewById(R.id.cancelItem);
        addTitle=(EditText) findViewById(R.id.addTitle);
        addDes=(EditText) findViewById(R.id.addDes);
        txtDate=(TextView)findViewById(R.id.test);
        txtTime=(TextView)findViewById(R.id.test2);
        back=(ImageView) findViewById(R.id.back);
        TextView topText=(TextView) findViewById(R.id.topText);
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();
        calenderpicker.setOnClickListener(this);
        timepicker.setOnClickListener(this);
        saveItem.setOnClickListener(this);
        cancelItem.setOnClickListener(this);
        back.setOnClickListener(this);
        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            addTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            addDes.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            txtDate.setText(intent.getStringExtra(EXTRA_DATE));
            txtTime.setText(intent.getStringExtra(EXTRA_TIME));
            addTitle.getText().toString();
            String pp=addTitle.getText().toString();
            topText.setText(pp);
        } else {

        }
    }

    @Override
    public void onClick(View v) {
        if (v == calenderpicker) {

            // Get Current Date
             Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int month, int day) {
                            Calendar c = Calendar.getInstance();
                            c.set(Calendar.YEAR, year);
                            c.set(Calendar.MONTH, month);
                            c.set(Calendar.DAY_OF_MONTH,day);
                            updateDate(c);
                           // txtDate.setText(day + "-" + (month + 1) + "-" + year);

                        }
                    }, year, month, day);
            datePickerDialog.show();
        }
        if (v == timepicker) {

            // Get Current Time
            Calendar c = Calendar.getInstance();
            hour = c.get(Calendar.HOUR_OF_DAY);
            minute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hour,
                                              int minute) {
                            Calendar c = Calendar.getInstance();
                            c.set(Calendar.HOUR_OF_DAY, hour);
                            c.set(Calendar.MINUTE, minute);
                            updateTime(c);
                          // startAlarm(c);
                          //  String timeSet = "";
                           // if (hour > 12) {
                             //   hour -= 12;
                               // timeSet = "PM";
                           //} else if (hour == 0) {
                             //   hour += 12;
                               // timeSet = "AM";
                            //} else if (hour == 12){
                              //  timeSet = "PM";
                            //}else{
                              //  timeSet = "AM";
                           // }
                            //String min = "";
                            //if (minute < 10)
                              //  min = "0" + minute;
                            //else
                              //  min = String.valueOf(minute);
                         //   txtTime.setText(hour + ":" + min+" "+timeSet);
                           // txtTime.setText(hour + ":" + minute);
                        }
                    }, hour, minute, false);
            timePickerDialog.show();


        }
        if(v==saveItem){

            String title = addTitle.getText().toString();
            String description = addDes.getText().toString();
            String date= txtDate.getText().toString();
            String time= txtTime.getText().toString();
            if (description.trim().isEmpty()||title.trim().isEmpty()) {
                Toast.makeText(this, "Please insert Title, Description and Set date and time", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent data = new Intent();
            data.putExtra(EXTRA_TITLE, title);
            data.putExtra(EXTRA_DESCRIPTION, description);
            data.putExtra(EXTRA_DATE, date);
            data.putExtra(EXTRA_TIME,time);
            int id = getIntent().getIntExtra(EXTRA_ID, -1);
            if (id != -1) {
                data.putExtra(EXTRA_ID, id);
            }
            setResult(RESULT_OK, data);
            finish();
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();

        }
        if(v==cancelItem){
            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }
        if(v==back){
            Intent intent=new Intent(AddEdit.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void updateTime(Calendar c) {
        String timeText = DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
        txtTime.setText(timeText);
        startAlarm(c);

    }
    private void updateDate(Calendar c) {
        String dateText = DateFormat.getDateInstance(DateFormat.SHORT).format(c.getTime());
        txtDate.setText(dateText);

    }

    private void startAlarm(Calendar c) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 0);
        }
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }
}