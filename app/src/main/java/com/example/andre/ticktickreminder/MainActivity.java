package com.example.andre.ticktickreminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, StarterService.class);
        this.startService(intent);
    }

    public void ButtonClick(View view)
    {
        updateTextView(Receiver.importantTasksGlobal);

    }

    public  void updateTextView(ArrayList<TickTickProviderHelper.TickTickTask> tasks)
    {
        ListView textbox = (ListView)  findViewById(R.id.list);
        ArrayList<String> lines = new ArrayList<>();
        for(TickTickProviderHelper.TickTickTask task:tasks)
        {
            lines.add(task.title +" "+DateFormat.format("dd/MM/yyyy hh:mm:ssaa",task.reminderTime).toString());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, lines);

        textbox.setAdapter(adapter);
    }


}
