package com.example.yawarkhan.mobileselfencryption;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.Toast;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Yawar Khan on 5/23/2018.
 */
public class MyService extends Service
{
    private static Timer timer = new Timer();
    public Boolean userAuth = false;
    private Context ctx;
    public String pActivity="";

    public IBinder onBind(Intent arg0)
    {
        return null;
    }

    public void onCreate()
    {
        super.onCreate();
        ctx = this;
        startService();
    }

    private void startService()
    {
        timer.scheduleAtFixedRate(new mainTask(), 0, 500);
    }

    private class mainTask extends TimerTask
    {
        public void run()
        {
            toastHandler.sendEmptyMessage(0);
        }
    }

    public void onDestroy()
    {
        super.onDestroy();
        Toast.makeText(this, "Service Stopped ...", Toast.LENGTH_SHORT).show();
    }

    private final Handler toastHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            String activityOnTop;
            ActivityManager mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> RunningTask = mActivityManager.getRunningTasks(1);
            ActivityManager.RunningTaskInfo ar = RunningTask.get(0);
            activityOnTop=ar.topActivity.getClassName();

            System.out.println("ksdfhkdjfhkHFKHDFKJHKJGjh");

            if(activityOnTop.equals("com.example.yawarkhan.MainActivity"))
            {
                pActivity = activityOnTop.toString();
                System.out.println("ksdfhkdjfhkHFKHDFKJHKJGjh");
            }
            else
            {
                if(activityOnTop.equals(pActivity) || activityOnTop.equals("com.example.yawarkhan.mobileselfencryption"))
                {
                    System.out.println("ksdfhkdjfhkHFKHDFKJHKJGjh");
                }
                else
                {
                    Intent i = new Intent(MyService.this, LockScreen.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    pActivity = activityOnTop.toString();

                }
            }


         /*if(!activityOnTop.equals(pActivity))
         {
           if(!activityOnTop.equals("com.javacodegeeks.android.androidserviceexample.LockScreen"))
          {
           pActivity =activityOnTop;
          }
          else
          {
           Intent i = new Intent(MyService.this, LockScreen.class);
              i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
              startActivity(i);
              Toast.makeText(MyService.this, pActivity, 1).show();

          }
         }
         else
         {
          Toast.makeText(MyService.this, "Hi", 1).show();
         }
         */


        }
    };
}