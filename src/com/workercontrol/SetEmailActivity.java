package com.workercontrol;

import android.app.Activity;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SetEmailActivity extends Activity {

	boolean czyUruchumionoNotyfikacje = false;
	boolean czyUstawionoNumer;
    
	boolean czyBackPressed = false;
	boolean userLeaveHint = false;
		 
    @Override
    public void onCreate(Bundle savedInstanceState) {

    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.setemail);
    	        
    	final TextView centralMailAddressTextView = (TextView)findViewById(R.id.textView2);
    	final TextView userMailAddressTextView = (TextView)findViewById(R.id.textView4);
    	final TextView passwordTextView = (TextView)findViewById(R.id.textView6);
    	
    	final EditText centralMailAddressEditText = (EditText)findViewById(R.id.editText1);
    	final EditText userMailAddressEditText = (EditText)findViewById(R.id.editText2);
    	final EditText passwordEditText = (EditText)findViewById(R.id.editText3);
    	
    	final Button btn = (Button)findViewById(R.id.button1);
    	btn.setText("Enter");
        
        if (MainActivity.centerMailAddress.equals(""))
        {
        	centralMailAddressTextView.setText("(empty)");
        	centralMailAddressTextView.setTextColor(Color.GRAY);
        }
        else
        {
        	centralMailAddressTextView.setText(MainActivity.centerMailAddress);
        	centralMailAddressEditText.setText(MainActivity.centerMailAddress);
        }
        
        if (MainActivity.userMailAddress.equals(""))
        {
        	userMailAddressTextView.setText("(empty)");
        	userMailAddressTextView.setTextColor(Color.GRAY);
        }
        else
        {
        	String emailID = MainActivity.userMailAddress.substring(0, MainActivity.userMailAddress.indexOf("@"));
        	
        	userMailAddressEditText.setText(emailID);
        	userMailAddressTextView.setText(MainActivity.userMailAddress);
        }
        
        if (MainActivity.password.equals(""))
        {
        	passwordTextView.setText("(empty)");
        	passwordTextView.setTextColor(Color.GRAY);
        }
        else
        {
        	String stars = MainActivity.password.replaceAll("(?s).", "*");
        	passwordTextView.setText(stars);
        	passwordEditText.setText(stars);
        }
        
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v)
			{
				String str_et1 = centralMailAddressEditText.getText().toString();
				String str_et2 = userMailAddressEditText.getText().toString();
				String str_et3 = passwordEditText.getText().toString();
				
				if (str_et1.equals("") || str_et2.equals("") || str_et3.equals(""))
					Toast.makeText(SetEmailActivity.this, "Not all fields were filled", Toast.LENGTH_LONG).show();
				else
				{					
					MainActivity.centerMailAddress = str_et1;
					centralMailAddressTextView.setText(str_et1);
					centralMailAddressTextView.setTextColor(getResources().getColor(R.color.mycolor));
							
					StringBuilder SB = new StringBuilder();
					SB.append(str_et2);
					SB.append("@gmail.com");
					str_et2 = String.valueOf(SB);
					
					MainActivity.userMailAddress = str_et2;
					userMailAddressTextView.setText(str_et2);
					userMailAddressTextView.setTextColor(getResources().getColor(R.color.mycolor));
									
					MainActivity.password = str_et3;
					String stars = MainActivity.password.replaceAll("(?s).", "*");
					passwordTextView.setText(stars);
					passwordTextView.setTextColor(getResources().getColor(R.color.mycolor));
					
					Editor editor = MainActivity.sharedpreferences.edit();						
					editor.putString(MainActivity.CENTRAL_MAIL_ADDRESS, str_et1);
					editor.putString(MainActivity.USER_MAIL_ADDRESS, str_et2); 
					editor.putString(MainActivity.PASSWORD, str_et3);
					editor.commit(); 
					
					Toast.makeText(SetEmailActivity.this, "Data enter successfully", Toast.LENGTH_LONG).show();
				}
					
				

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




















