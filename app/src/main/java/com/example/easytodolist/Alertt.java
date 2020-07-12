package com.example.easytodolist;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;

public class Alertt extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alertt);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String msg = "Your Task" + getIntent().getExtras().getString(getString(R.string.app_name));
        builder.setMessage(msg).setCancelable(
                false).setPositiveButton(getString(R.string.search),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        Alertt.this.finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override

    public void onDestroy() {

        super.onDestroy();

    }
}