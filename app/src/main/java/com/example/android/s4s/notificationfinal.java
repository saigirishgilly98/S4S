package com.example.android.s4s;


import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class notificationfinal extends Application {
    public static final String CHANNELid1 = "Channel 1";
    public static final String CHANNELid2 = "Channel 2";

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannels();
    }

    private void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNELid1,
                    "First Channel",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("The first notification channel");
            NotificationChannel channel2 = new NotificationChannel(
                    CHANNELid2,
                    "Second Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            channel1.setDescription("The second notification channel");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
            manager.createNotificationChannel(channel2);
        }
    }
}