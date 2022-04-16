package com.example.todor;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.todor.database.AppDatabase;
import com.example.todor.database.Assessment;
import com.example.todor.database.Course;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class AlertWorker extends Worker {

    public static final String CHANNEL_ID = "ALERT_NOTIFICATION" ;
    AppDatabase appDatabase;

    public AlertWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        appDatabase=AppDatabase.getDbInstance(getApplicationContext());

        showAlertsForCourses();
        showAlertsForAssessments();





        return Result.success();
    }

    private void showAlertsForAssessments() {

        SimpleDateFormat sdf=new SimpleDateFormat("d/M/yyyy");
        String date=sdf.format(new Date());



        List<Assessment> assessmentList=appDatabase.assessmentDao().getAllAssessments();
        for ( int i=0;i<assessmentList.size();i++){

            if (assessmentList.get(i).endDate.equals(date)){

                showNotification(getApplicationContext(),"Assessment alert",assessmentList.get(i).title+" assessment is ending today");
            }
        }
    }

    private void showAlertsForCourses() {

        SimpleDateFormat sdf=new SimpleDateFormat("d/M/yyyy");
        String date=sdf.format(new Date());


        List<Course> courseList=appDatabase.courseDao().getAllCourses();
        for ( int i=0;i<courseList.size();i++){
            if (courseList.get(i).endDate.equals(date)){
                showNotification(getApplicationContext(),"Course alert",courseList.get(i).title+" Term is ending today");
            }
        }
        //

        for ( int i=0;i<courseList.size();i++){
            if (courseList.get(i).startDate.equals(date)){
                showNotification(getApplicationContext(),"Course alert",courseList.get(i).title+" Term is starting today");
            }
        }
    }

    private void showNotification(Context context,String title,String content) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Todo App")
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Channel_name";
            String description = "description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        final int random = new Random().nextInt(61) + 20;
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(random, mBuilder.build());
    }
}
