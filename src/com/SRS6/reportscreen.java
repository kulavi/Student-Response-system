//Displays a list of Reports

package com.SRS6;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class reportscreen extends Activity implements RadioGroup.OnCheckedChangeListener {
	
	protected static RadioGroup mradiogroup;
	
	Button back;
	/** Create Object For SiteList Class */
		
	RadioButton r1,r2,r3,r4;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	 
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reportscreen);
        
        
        mradiogroup = (RadioGroup) findViewById(R.id.Group1);
        
        r1=(RadioButton)findViewById(R.id.rad1);
     
       
        
        r2=(RadioButton)findViewById(R.id.rad2);
      
        
        r3=(RadioButton)findViewById(R.id.rad3);
     
        
        r4=(RadioButton)findViewById(R.id.rad4);
        
        mradiogroup.setOnCheckedChangeListener(this);
        
        back=(Button)findViewById(R.id.bk1);
		back.setOnClickListener(new Button.OnClickListener() 
		{ public void onClick (View v)
			{ 							
			     getback();
			
			}
		
	    
		});
        
        
        
					
    }
    
    /*
    type:function
    name:onCheckedChanged
    returns:void
    date:29-06-2011
    purpose:Auto-generated method which is called when the radio button is checked
    */

	public void onCheckedChanged(RadioGroup group, int checkedId) {
		
		
		// TODO Auto-generated method stub
		if(r1.isChecked())
		{
			
			getreport1();	
			 
		}
		if(r2.isChecked())
		{
			getreport2();
			
		}
		/*if(r3.isChecked())
		{ 
			
			
		}
		if(r4.isChecked())
		{
			
		}  	  */  
		 
	}
	
	/*
    type:function
    name:getreport1
    returns:void
    parameters:no parameter
    date:29-06-2011
    purpose:Starts the activity teachertestreport
    */
	public void getreport1()
	{
		Intent i =new Intent(reportscreen.this,teachtestreport.class);
		startActivity(i);
	}
	
	/*
    type:function
    name:getreport2
    returns:void
    parameters:no parameter
    date:29-06-2011
    purpose:Starts the activity teachstudentwisereport
    */
	public void getreport2()
	{
		Intent i =new Intent(reportscreen.this,teachstudentwisereport.class);
		startActivity(i);
	}
	
	/*
    type:function
    name:getback
    returns:void
    parameters:no parameter
    date:29-06-2011
    purpose:Starts the activity SRS6
    */
	public void getback()
	{
	 	
	 	Intent i = new Intent(this,SRS6.class);
		
	    startActivity(i);

	}
}
        
      