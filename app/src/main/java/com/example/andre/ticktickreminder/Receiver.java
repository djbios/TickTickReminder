package com.example.andre.ticktickreminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.SystemClock;
import android.text.format.DateFormat;
import android.util.Log;
import com.example.andre.ticktickreminder.TickTickProviderHelper.TickTickTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.content.Context.ALARM_SERVICE;

public class Receiver extends BroadcastReceiver {

    final String LOG_TAG = "myLogs";

    @Override
    public void onReceive(Context ctx, Intent intent) { //Проверяет таски
        if(intent.getAction()=="motherfucke") {
            List<TickTickTask> tasks = TickTickProviderHelper.getAllTasks(ctx);
            ArrayList<TickTickTask> important = new ArrayList<TickTickTask>();

            for (TickTickTask task : tasks) {
                if (task.priority > 0) {
                    important.add(task);
                }
            }
            AlarmManager am = (AlarmManager) ctx.getSystemService(ALARM_SERVICE);

            for (TickTickTask task : important) {
                Intent taskInt = new Intent(ctx, TaskReceiver.class);
                intent.setAction(task.title);
                String time = DateFormat.getDateFormat(ctx).format(new Date(task.dueDate));
                PendingIntent pendingIntent = PendingIntent.getBroadcast(ctx, 0, intent, 0);
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(task.reminderTime);
                am.setExact(AlarmManager.RTC_WAKEUP, task.reminderTime, pendingIntent);

            }

        }
        else
        {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(ctx.getApplicationContext(), notification);
            r.play();
        }

    }
}