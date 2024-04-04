package com.example.structuremonitoringsystem.OtherFunction;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.structuremonitoringsystem.R;

public class Notification {
    Activity activity;
    Context context;

    public Notification(Activity activity, Context context) {
        this.activity = activity;
        this.context = context;
    }

    public void notifyDrift(String distance) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel nc = new NotificationChannel("01", "Drift", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager nm = activity.getSystemService(NotificationManager.class);
            nm.createNotificationChannel(nc);
        }

        NotificationCompat.Builder ncb = new NotificationCompat.Builder(activity.getApplicationContext(), "01");
        ncb.setSmallIcon(R.drawable.ic_warning);
        ncb.setContentTitle("WARNING: DRIFT!");
        ncb.setContentText("The attached device are currently " + distance + "m apart which exceeds the current threshold");
        NotificationManagerCompat nmc = NotificationManagerCompat.from(activity);
        Log.e("ss", "fuck");
        if ((ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) || true) {
            nmc.notify(1, ncb.build());
            Log.e("ss", "late");
        }
        Log.e("ss", "fuck");

    }
}
