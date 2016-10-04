package com.workercontrol;

import android.app.Activity;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SetSMSActivity extends Activity {

	boolean isNotificationLaunched = false;
	boolean isNumberSet;

	boolean czyBackPressed = false;
	boolean userLeaveHint = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.setsms);

		final TextView currentNumberTextView = (TextView)findViewById(R.id.textView2);
		final EditText ET = (EditText)findViewById(R.id.editText1);
		final Button btn = (Button)findViewById(R.id.button1);

		if (MainActivity.centralPhoneNumber.equals(""))
		{
			currentNumberTextView.setText("(number not chosen)");
			currentNumberTextView.setTextColor(Color.GRAY);
			btn.setText("Set number");
		}
		else
		{
			currentNumberTextView.setText(MainActivity.centralPhoneNumber);
			btn.setText("Change number");
		}


		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v)
			{
				Editable number = ET.getText();

				String regex = "[0-9]+";
				boolean doesMatchRegex = String.valueOf(number).matches(regex);				

				if (doesMatchRegex)
				{
					String str_numer = String.valueOf(number);

					Editor editor = MainActivity.sharedpreferences.edit();						
					editor.putString(MainActivity.CENTRAL_PHONE_NUMBER, str_numer);
					editor.commit(); 					

					MainActivity.centralPhoneNumber = str_numer;
					currentNumberTextView.setText(str_numer);
					currentNumberTextView.setTextColor(getResources().getColor(R.color.mycolor));

					btn.setText("Change number");
					Toast.makeText(SetSMSActivity.this, "Number changed successfully", Toast.LENGTH_LONG).show();
					ServiceLaunch.serviceLaunch(SetSMSActivity.this);
				}
				else
					Toast.makeText(SetSMSActivity.this, "Entered number is invalid", Toast.LENGTH_LONG).show();
			}
		});
	}	

	@Override
	public void onBackPressed()
	{
		czyBackPressed = true;
		super.onBackPressed();  
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
		if (userLeaveHint && !czyBackPressed)
		{
			this.finish();	

			MainActivity MA = MainActivity.MActx;
			MA.finish();
		}

		userLeaveHint = false;
		czyBackPressed = false;

		super.onPause();
	}


	@Override
	public void onDestroy() 
	{
		super.onDestroy();
	}


}




















