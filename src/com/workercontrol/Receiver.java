package com.workercontrol;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class Receiver extends BroadcastReceiver {

	
	@Override
	public void onReceive(Context context, Intent data) 
	{
		boolean isNumberSet = ! MainActivity.centralPhoneNumber.equals("");
		Intent intent;		
		
		if (isNumberSet)	
			intent = new Intent(context, MyService.class);
		else
			intent = new Intent(context, MainActivity.class);
		
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}
}









