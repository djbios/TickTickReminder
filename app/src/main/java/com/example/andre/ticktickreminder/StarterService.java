package com.example.andre.ticktickreminder;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

/**
 * Created by andre on 11.04.2017.
 */

public class StarterService extends Service {
    private static final String TAG = "MyService";

    /**
     * starts the AlarmManager.
     */
    AlarmManager am;
    @Override
    public void onCreate() {
        super.onCreate();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {

        am = (AlarmManager)getSystemService(ALARM_SERVICE);
        Intent intenttoRun = new Intent(this,Receiver.class);
        intenttoRun.setAction("check_tasks");
        PendingIntent pendingIntent =  PendingIntent.getBroadcast(this, 0, intenttoRun, 0);
        am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(),60000,pendingIntent);
        //startServiceForeground(intent, flags, startId);
        Log.d("tickLog","runned");
        return START_STICKY;

    }

    public int startServiceForeground(Intent intent, int flags, int startId) {

        Intent notificationIntent = new Intent(this, MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("File Observer Service")
                .setContentIntent(pendingIntent)
                .setOngoing(true)
                .build();

        startForeground(300, notification);

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        Intent in = new Intent();
        in.setAction("YouWillNeverKillMe");
        sendBroadcast(in);


    }
}