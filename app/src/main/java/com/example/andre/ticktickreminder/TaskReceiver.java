package com.example.andre.ticktickreminder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by andre on 11.04.2017.
 */

public class TaskReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
    context.getSystemService(Context.ALARM_SERVICE);
    }
}
