package com.workercontrol;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;


public class ReceiverSMS extends BroadcastReceiver {

	static String rememberedMessage = "";
	static String tmDevice = "";
	private boolean TEST_MODE = true;

	@Override
	public void onReceive(Context context, Intent data) 
	{
		Bundle bundle = data.getExtras();

		try {
			if (bundle != null) {
				final Object[] pdusObj = (Object[]) bundle.get("pdus");

				for (int i = 0; i < pdusObj.length; i++) 
				{
					SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
					String message = currentMessage.getDisplayMessageBody();

					if (!rememberedMessage.equals(message))
					{  
						if (MainActivity.isMailActivated && ! MainActivity.centerMailAddress.equals(""))  
							sendMail(message);
						else                
							sendSMS(message);
					}
				} 
			} 

		} catch (Exception e) {}
	}



	private void sendMail(final String message)
	{
		final Mail m = new Mail(MainActivity.userMailAddress, MainActivity.password); 

		Thread thread = new Thread(new Runnable(){
			@Override
			public void run() 
			{
				try 
				{
					String[] toArr = {MainActivity.centerMailAddress}; 
					m.setTo(toArr); 
					m.setFrom(MainActivity.userMailAddress); 
					m.setSubject("Worker Control"); 
					m.setBody(message); 

					if(m.send()) 
					{ 
						rememberedMessage = message; // success
					}
					else 
					{ 
						sendSMS(message); // failure
					} 
				} catch(Exception e) 
				{ 
					e.printStackTrace();
					sendSMS(message);
				} 
			}
		});

		thread.start();  
	}





	private void sendSMS(String message)
	{	
		if (!TEST_MODE)
		{ 
			try 
			{
				SmsManager smsManager = SmsManager.getDefault();
				smsManager.sendTextMessage(MainActivity.centralPhoneNumber, null, message, null, null);
			} 
			catch (Exception e) {}	
		}

		rememberedMessage = message;
	}

}









