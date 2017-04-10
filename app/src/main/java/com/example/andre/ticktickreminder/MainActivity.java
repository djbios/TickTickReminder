package com.example.andre.ticktickreminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    AlarmManager am;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        am = (AlarmManager)getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(this,Receiver.class);
        intent.setAction("motherfucke");
        PendingIntent pendingIntent =  PendingIntent.getBroadcast(this, 0, intent, 0);
        //am.set(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime()+4000,pendingIntent);
        am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(),100000,pendingIntent);

    }
}
