package com.example.easytodolist;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

    @SuppressWarnings("deprecation")
    public class NotificManager extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String Title = intent.getStringExtra("pp");
            String content = intent.getStringExtra("'cc");

            NotificationCompat.Builder builder =
                    new NotificationCompat.Builder(context)
                            .setSmallIcon(R.drawable.ic_add)
                            .setContentTitle(Title);

            // Add as notification
            NotificationManager manager=(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(0,builder.build());
        }
}
