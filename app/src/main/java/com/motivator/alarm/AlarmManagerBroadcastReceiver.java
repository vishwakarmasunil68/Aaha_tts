package com.motivator.alarm;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.widget.Toast;

public class AlarmManagerBroadcastReceiver extends BroadcastReceiver {
	 
	 final public static String ONE_TIME = "onetime";
	 
	 @Override
	 public void onReceive(Context context, Intent intent) {
	   PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
	         PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "YOUR TAG");
	         //Acquire the lock
	         wl.acquire();
	 
	         //You can do the processing here.
	         Bundle extras = intent.getExtras();
	         StringBuilder msgStr = new StringBuilder();
	          
	         if(extras != null && extras.getBoolean(ONE_TIME, Boolean.FALSE)){
	          //Make sure this intent has been sent by the one-time timer button.
	          msgStr.append("One time Timer : ");
	         }
	         Format formatter = new SimpleDateFormat("hh:mm:ss a");
	         msgStr.append(formatter.format(new Date()));
	 
	         Toast.makeText(context, msgStr, Toast.LENGTH_LONG).show();
	          
	         //Release the lock
	         wl.release();
	 }
	 
	 public void SetAlarm(Context context)
	    {
	        AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
	        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
	        intent.putExtra(ONE_TIME, Boolean.FALSE);
	        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
	        //After after 5 seconds
	        
	        Calendar cal=Calendar.getInstance();
            cal.set(Calendar.MONTH,11);
            cal.set(Calendar.YEAR,2015);
            cal.set(Calendar.DAY_OF_MONTH,22);
            cal.set(Calendar.HOUR_OF_DAY,11);
            cal.set(Calendar.MINUTE,04);
	        
	        am.setRepeating(AlarmManager.RTC_WAKEUP, /*System.currentTimeMillis()*/cal.getTimeInMillis(), 1000 * 5 , pi); 
	    }
	 
	    public void CancelAlarm(Context context)
	    {
	        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
	        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
	        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
	        alarmManager.cancel(sender);
	    }
	 
	    public void setOnetimeTimer(Context context){
	     AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
	        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
	        intent.putExtra(ONE_TIME, Boolean.TRUE);
	        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
	        am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pi);
	    }
	}