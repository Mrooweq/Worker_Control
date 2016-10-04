package com.workercontrol;


import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

public class MyService extends Service
{
	
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    
    @Override
	public void onCreate() 
	{
		super.onCreate();

	      Intent notificationIntent = new Intent(this, MainActivity.class);
	      notificationIntent.putExtra("key", "value");
	      PendingIntent pendingIntent=PendingIntent.getActivity(this, 0,  notificationIntent, Intent.FLAG_ACTIVITY_NEW_TASK);

	      Notification notification=new NotificationCompat.Builder(this)
	                                  .setSmallIcon(R.drawable.icon)
	                                  .setContentText("Worker control")
	                                  .setContentIntent(pendingIntent).build();

	      startForeground(100, notification);
	}
    
    
    public int onStartCommand(Intent intent, int flags, int startId) 
    {   
      return Service.START_STICKY; 
    }
    
    

    

    
}