package com.example.andre.ticktickreminder;

import android.app.AlarmManager;
import android.app.Application;
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
import android.util.TimeFormatException;
import android.widget.TextView;

import com.example.andre.ticktickreminder.TickTickProviderHelper.TickTickTask;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.content.Context.ALARM_SERVICE;
import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class Receiver extends BroadcastReceiver {

    final String LOG_TAG = "myLogs";
    static ArrayList<TickTickTask> importantTasksGlobal = new ArrayList<>();
    @Override
    public void onReceive(Context ctx, Intent intent) { //Проверяет таски
        if(intent.getAction()=="check_tasks")
        {//Событие проверки тасков
            List<TickTickTask> tasks = TickTickProviderHelper.getAllTasks(ctx);
            ArrayList<TickTickTask> important = new ArrayList<TickTickTask>();

            for (TickTickTask task : tasks) {
                if (task.priority > 0) {
                    important.add(task);
                }
            }
            AlarmManager am = (AlarmManager) ctx.getSystemService(ALARM_SERVICE);

            for (TickTickTask task : important) {
                if(task.reminderTime>System.currentTimeMillis()) {
                    Intent taskInt = new Intent(ctx, Receiver.class);
                    intent.setAction(task.title);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(ctx, 0, intent, 0);
                    am.setExact(AlarmManager.RTC_WAKEUP, task.reminderTime, pendingIntent);
                }
            }
            importantTasksGlobal = important;
        }
        else // Время напоминания
        {
            Intent intentRunRinger = new Intent(ctx, Ringer_Act.class);
            intentRunRinger.putExtra("taskTitle",intent.getAction());
            intentRunRinger.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ctx.getApplicationContext().startActivity(intentRunRinger);

        }
    }
}