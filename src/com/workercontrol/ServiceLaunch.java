package com.workercontrol;

import android.content.Context;
import android.content.Intent;

public class ServiceLaunch 
{
	public static void serviceLaunch(Context ctx)
	{
		Intent intent = new Intent(ctx, MyService.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		ctx.startService(intent);
	}
}
