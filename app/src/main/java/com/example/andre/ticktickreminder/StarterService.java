package com.example.andre.ticktickreminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;

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
        am = (AlarmManager)getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(this,Receiver.class);
        intent.setAction("check_tasks");
        PendingIntent pendingIntent =  PendingIntent.getBroadcast(this, 0, intent, 0);
        //am.set(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime()+4000,pendingIntent);
        am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(),10000,pendingIntent);
    }
    @Override
    public void onStart(Intent intent, int startid) {

        //TODO: Put your AlarmManager code here
        //TODO: you also need to add some logic to check if some previous work is pending in case of a device reboot

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        //TODO: cancel the notification

    }
}