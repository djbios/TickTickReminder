package com.example.andre.ticktickreminder;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

public class TickTickProviderHelper {

    private static final Uri TASK_URI = Uri.parse("content://com.ticktick.task.data/tasks");
    private static final Uri PROJECT_URI = Uri.parse("content://com.ticktick.task.data/tasklist");
    private static final String TASK_CONTENT_ITEM_TYPE = "vnd.android.cursor.item/ticktick.task.task";

    enum TaskColumns {
        ID, LIST_ID, TITLE, DUEDATE, SORT_ORDER, COMPLETED, PRIORITY, REMINDER_TIME
    }

    enum ProjectColumns {
        ID, NAME, COLOR
    }

    public static final class PriorityLevel {
        public final static int Level0 = 0;// none
        public final static int Level1 = 1;// low
        public final static int Level2 = 2;
        public final static int Level3 = 3;// medium
        public final static int Level4 = 4;
        public final static int Level5 = 5;// high

    }

    /**
     * Return all Projects of current TickTick account
     *
     * @param context
     * @return
     */
    public static List<TickTickProject> getAllProjects(Context context) {
        List<TickTickProject> projects = new ArrayList<TickTickProject>();
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = null;
        try {
            cursor = resolver.query(PROJECT_URI, null, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    TickTickProject project = cursorToProject(cursor);
                    projects.add(project);
                } while (cursor.moveToNext());
            }
            return projects;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    /**
     * Return all tasks (With completed tasks) of current TickTick account
     *
     * @param context
     * @return
     */
    public static List<TickTickTask> getAllTasks(Context context) {
        List<TickTickTask> tasks = new ArrayList<TickTickTask>();
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = null;
        for(int i=0;i<20;i++)
        {
        try {
            cursor = resolver.query(TASK_URI, null, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    TickTickTask task = cursorToTask(cursor);
                    tasks.add(task);
                } while (cursor.moveToNext());
            }
            return tasks;
        }
        catch (Exception ex)
        {
            ex.getCause();
        }

        finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        }
    return  null;
    }

    /**
     * Add a new task to TickTick
     *
     * @param projectId
     *            , the task will be add to this project
     * @param context
     */
    public static void insertTask(long projectId, Context context) {
        Intent editIntent;
        editIntent = new Intent(Intent.ACTION_INSERT);
        editIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        editIntent.setDataAndType(TASK_URI.buildUpon().appendEncodedPath(projectId + "").build(),
                TASK_CONTENT_ITEM_TYPE);
        context.startActivity(editIntent);
    }

    /**
     * View a task of TickTick
     *
     * @param projectId
     *            , the Project
     * @param taskId
     *            , the task
     * @param context
     */
    public static void viewTask(long projectId, long taskId, Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("tasklist_id", projectId);
        intent.setDataAndType(ContentUris.withAppendedId(TASK_URI, taskId), TASK_CONTENT_ITEM_TYPE);
        context.startActivity(intent);
    }

    private static TickTickTask cursorToTask(Cursor cursor) {
        TickTickTask task = new TickTickTask();
        task.id = cursor.getLong(TaskColumns.ID.ordinal());
        task.projectId = cursor.getLong(TaskColumns.LIST_ID.ordinal());
        task.title = cursor.getString(TaskColumns.TITLE.ordinal());
        task.dueDate = cursor.getLong(TaskColumns.DUEDATE.ordinal());
        task.sortOrder = cursor.getLong(TaskColumns.SORT_ORDER.ordinal());
        task.completedTime = cursor.getLong(TaskColumns.COMPLETED.ordinal());
        task.priority = cursor.getInt(TaskColumns.PRIORITY.ordinal());
        task.reminderTime = cursor.getLong(TaskColumns.REMINDER_TIME.ordinal());
        return task;
    }

    private static TickTickProject cursorToProject(Cursor cursor) {
        TickTickProject project = new TickTickProject();
        project.id = cursor.getLong(ProjectColumns.ID.ordinal());
        project.name = cursor.getString(ProjectColumns.NAME.ordinal());
        project.color = cursor.getString(ProjectColumns.COLOR.ordinal());
        return project;
    }

    public static class TickTickTask {
        public long id;
        public long projectId;
        public String title;
        public long dueDate;
        public long sortOrder;
        public long completedTime;
        public int priority;
        public long reminderTime;
    }

    public static class TickTickProject {
        public long id;
        public String name;
        public String color;
    }
}