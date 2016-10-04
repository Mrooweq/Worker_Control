package com.workercontrol;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	public static SharedPreferences sharedpreferences;
	public static String centralPhoneNumber = "";
	public static String centerMailAddress = "";
	public static String userMailAddress = "";
	public static String password = "";
	public static MainActivity MActx;
	public static boolean isMailActivated = false;

	public static final String CENTRAL_PHONE_NUMBER = "centralPhoneNumber";
	public static String CENTRAL_MAIL_ADDRESS = "centerMailAddress";
	public static String USER_MAIL_ADDRESS = "userMailAddress";
	public static String PASSWORD = "password";
	public static String IS_MAIL_ACTIVATED = "isMailActivated";
	private final String PREF = "MyPrefs";
	private final int FLAG = 0x10200000;
	
	private boolean userLeaveHint = false;
	private boolean isActivityChanged = false;
	private boolean shouldActivityBeShown = false;

	
	@Override 
	public void setTheme(int resid) 
	{		
		boolean isNotificationLaunched = false;
		boolean isNumberSet = false;
		boolean isIconClicked = false;
		
		sharedpreferences = getSharedPreferences(PREF, Context.MODE_PRIVATE);
		centralPhoneNumber = sharedpreferences.getString(CENTRAL_PHONE_NUMBER, "");
		centerMailAddress = sharedpreferences.getString(CENTRAL_MAIL_ADDRESS, "");
		userMailAddress = sharedpreferences.getString(USER_MAIL_ADDRESS, "");
		password = sharedpreferences.getString(PASSWORD, "");
		isMailActivated = sharedpreferences.getBoolean(IS_MAIL_ACTIVATED, false);
		
		if(getIntent().getFlags() == FLAG)
			isIconClicked = true;

		try	{
			isNotificationLaunched = getIntent().getExtras().get("key").equals("value");
		} catch (Exception e){}

		isNumberSet = !centralPhoneNumber.equals("");

		if (isNotificationLaunched || !isNumberSet || isIconClicked)
			super.setTheme(android.R.style.Theme);

		if (isNumberSet)
			ServiceLaunch.serviceLaunch(this);

		shouldActivityBeShown = isNotificationLaunched || !isNumberSet || isIconClicked;
	}
	
	 
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_main);

    	MActx = this;
    	
    	TextView phoneNumberTextView = (TextView)findViewById(R.id.textView2);
    	TextView centralMailAddressTextView = (TextView)findViewById(R.id.textView4);
    	TextView userMailAddressTextView = (TextView)findViewById(R.id.textView6);
    	TextView passwordTextView = (TextView)findViewById(R.id.textView8);
    	Button setSMSButton = (Button)findViewById(R.id.button1);
    	Button setEmailButton = (Button)findViewById(R.id.button2);
    	
    	final CheckBox isMailActivatedCheckBox = (CheckBox) findViewById(R.id.checkBox1);
    	
    	if (centralPhoneNumber.equals(""))
    	{
    		phoneNumberTextView.setText("(number not chosen)");
    		phoneNumberTextView.setTextColor(Color.GRAY);
    	}
    	else
    		phoneNumberTextView.setText(centralPhoneNumber);
    	
    	if (centerMailAddress.equals(""))
    	{
    		centralMailAddressTextView.setText("(empty)");
    		centralMailAddressTextView.setTextColor(Color.GRAY);
    	}
    	else
    		centralMailAddressTextView.setText(centerMailAddress);
    	
    	if (userMailAddress.equals(""))
    	{
    		userMailAddressTextView.setText("(empty)");
    		userMailAddressTextView.setTextColor(Color.GRAY);
    	}
    	else
    		userMailAddressTextView.setText(userMailAddress);
    	
    	if (password.equals(""))
    	{
    		passwordTextView.setText("(empty)");
    		passwordTextView.setTextColor(Color.GRAY);
    	}
    	else
    	{
    		String gwiazdki = MainActivity.password.replaceAll("(?s).", "*");
    		passwordTextView.setText(gwiazdki);
    	}

    	if (isMailActivated)
    	{
    		isMailActivatedCheckBox.setChecked(true);
    		isMailActivatedCheckBox.setText("Email is active");
    	}
    	else
    	{
    		isMailActivatedCheckBox.setChecked(false);
    		isMailActivatedCheckBox.setText("Email is not active");
    		block(centralMailAddressTextView, userMailAddressTextView, passwordTextView, setEmailButton);
    	}
    	
    	setSMSButton.setOnClickListener(new View.OnClickListener() 
    	{
			@Override
			public void onClick(View v) 
			{
		        Intent intent = new Intent(MainActivity.this, SetSMSActivity.class);
		        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		        startActivity(intent);
		        isActivityChanged = true;
			}
		});
    	
    	
    	setEmailButton.setOnClickListener(new View.OnClickListener() 
    	{
			@Override
			public void onClick(View v) 
			{
		        Intent intent = new Intent(MainActivity.this, SetEmailActivity.class);
		        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		        startActivity(intent);
		        isActivityChanged = true;
			}
		}); 	
    	    	    	
    	isMailActivatedCheckBox.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) 
			{
				if (isMailActivated)
				{
					isMailActivated = false;
				}
				else
				{
					isMailActivated = true;
				}
				
				Editor editor = MainActivity.sharedpreferences.edit();						
				editor.putBoolean(IS_MAIL_ACTIVATED, isMailActivated);
				editor.commit(); 
				
				Intent refresh = new Intent(MainActivity.this, MainActivity.class);
				startActivity(refresh);
			}
		});
    	
    	
    	try
    	{
    		TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
    		ReceiverSMS.tmDevice = "" + tm.getDeviceId();
    	} catch (Exception e){}

    	

    	if (!shouldActivityBeShown)      
    	{    		
    		MainActivity.this.finish();
    		return;
        }
 	
    }	
    
    
private void block(TextView centralMailAddressTextView, TextView userMailAddressTextView, TextView passwordTextView, Button btnSetEmail)
{	
	centralMailAddressTextView.setTextColor(Color.GRAY);
	userMailAddressTextView.setTextColor(Color.GRAY);
	passwordTextView.setTextColor(Color.GRAY);
	btnSetEmail.setEnabled(false);
	
	TextView TV1 = (TextView)findViewById(R.id.textView3);
	TextView TV2 = (TextView)findViewById(R.id.textView5);
	TextView TV3 = (TextView)findViewById(R.id.textView7);
		
	TV1.setTextColor(Color.DKGRAY);
	TV2.setTextColor(Color.DKGRAY);
	TV3.setTextColor(Color.DKGRAY);
}
    
    @Override
    protected void onUserLeaveHint()
    {
    	super.onUserLeaveHint();
    	userLeaveHint = true;
    }


    @Override
    public void onPause()
    {  	
    	if (!isFinishing() && userLeaveHint && ! isActivityChanged)
    		this.finish();
    	
    	userLeaveHint = false;
    	isActivityChanged = false;
    	
    	super.onPause();
    }


    @Override
    public void onDestroy() 
    {
    	super.onDestroy();
    }
    
    
    @Override
    protected void onResume() {

       super.onResume();
       this.onCreate(null);
    }
    

}



