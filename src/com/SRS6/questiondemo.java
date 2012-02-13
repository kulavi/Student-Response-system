//Display questions for test

package com.SRS6;

import java.util.Calendar;
import java.util.GregorianCalendar;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class questiondemo extends Activity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {
	protected static RadioGroup mradiogroup;
	
	
	private static final String NAMESPACE = "http://tempuri.org/";      
	private static final String URL = "http://192.168.1.85/studentresponse/Service.asmx"; 
	
	private static final String SOAP_ACTION = "http://tempuri.org/getquestions";      
	private static final String METHOD_NAME = "getquestions";      
	        
	
	String[] results;  
	Cursor c;
	String a="";
	int intquesno=1;
	String[] strques, stropt,strans,st,st2;    
	String strstatus,ques1,opt1,status1;
	int temp1,no;
	DataBaseHelper data;   
	String[] st1,corans;
	int[] quesid;
	int flag;
	int counters=1;
	String[] arrauserData1;
	
	public CharSequence ch;
	String time1;
	String time2,quizvalue;
	static int currentquestion=1;
	int rquestion=0;
	int wquestion=0,queid=1;
	static int correctcount=0;  
	static int wrongcount=0;
	
	protected int brewTime = 3;
	protected CountDownTimer brewCountDownTimer;
	protected boolean isBrewing = false;
	
	int previous=0,qno1;
	int value1;
	String quizvalue1,stuname;
	int unattempted;
    int i=0,j=0,k=0,value;
	
	 	LinearLayout mlayout;
		TextView mTextField;
		TextView txtwel; 
		TextView txtquiz;
		TextView  vquestionno;
		TextView txtquenm;
		TextView vquestion;
		TextView txtque;
		TextView txtopt;
		TextView tv,txtdate; 
		int z;
		
		//Set System date
		Calendar cal = new GregorianCalendar();
		int month = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		//String sdate=(+ (month + 1) + "-" + day + "-" + year);
		String sdate=(+ day + "-" + (month + 1) + "-" + year);
	/** Create Object For SiteList Class */
		
	RadioButton r1,r2,r3,r4;


	 String st21;


	 int intoptions21;


	 String[] actual_option;


	 String st22;


	 String[] split_option;


	 String[] intloop;


	 String[] split_option1;


	 String[] split_option2;


	 String split_option3;


	 String[] st2_split;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
      
    	data=new DataBaseHelper(this); 
    	data.open();
    	Bundle bundle = getIntent().getExtras(); 
    	stuname=bundle.getString("stname");
   	    value1 = bundle.getInt("id1", 0);
   	    quizvalue1=bundle.getString("qname1");
    	
   	    final AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
   	    final AlertDialog.Builder alertbox1 = new AlertDialog.Builder(this);
    	
        setContentView(R.layout.questiondemo);
        mTextField=(TextView)findViewById(R.id.timer);
        setBrewTime(3);
        
        
        startBrew();
        time2=(String) mTextField.getText();
                
        txtwel=(TextView)findViewById(R.id.welcome);
        txtdate=(TextView)findViewById(R.id.tdate);
       
        
        txtquiz=(TextView)findViewById(R.id.quizno);
        
       
        
        txtopt=(TextView)findViewById(R.id.option);
        
        
              
        vquestionno=(TextView)findViewById(R.id.queno1);
      
        
        vquestion=(TextView)findViewById(R.id.quena);
      
        mradiogroup = (RadioGroup) findViewById(R.id.Group1);
        
        r1=(RadioButton)findViewById(R.id.rad1);
     
       
        
        r2=(RadioButton)findViewById(R.id.rad2);
      
        
        r3=(RadioButton)findViewById(R.id.rad3);
     
        
        r4=(RadioButton)findViewById(R.id.rad4);
        
        /*r1.setVisibility(View.INVISIBLE);
        r2.setVisibility(View.INVISIBLE);
        r3.setVisibility(View.INVISIBLE);
        r4.setVisibility(View.INVISIBLE);*/
      
             
   
        Button next=new Button(this);
        next=(Button)findViewById(R.id.ImageButton02);
        
        Button done=new Button(this);
        done=(Button)findViewById(R.id.imagebutton);
        
        Button skip=new Button(this);
        skip=(Button)findViewById(R.id.ImageButton03);
        
        Button quit=new Button(this);
        quit=(Button)findViewById(R.id.ImageButton04);  
        
        
        mradiogroup.setOnCheckedChangeListener(this);
        
        txtdate.setText("Date :"+sdate); 
        data.deletedb();
		data.deleteres();
        call(); 
             
		      
         next.setOnClickListener(new OnClickListener(){
    	//@Override
        public void onClick(View v) {
        	        	 
    		if(flag==0)
    		{  
    			alertbox1.setMessage("Please select one option.... ");
    		     
                // set a positive/yes button and create a listener
    			alertbox1.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
    				 
                    // click listener on the alert box
                    public void onClick(DialogInterface arg0, int arg1) {
                        // the button was clicked
                      //  Toast.makeText(getApplicationContext(), "OK button clicked", Toast.LENGTH_LONG).show();
                    }
                });
     
                // show it
                alertbox1.show();  
    		}
    		else
    		{
    			counters++;
    			System.out.println("Counter is:"+counters);
    			arrauserData1=null;
    			Cursor c=data.getquesno();
   		     while(c.moveToNext())
   		     {
   		    	 qno1=c.getInt(0);
   		    	 System.out.println("QNO:"+qno1);
   		     }
       		String i1=(String) vquestionno.getText();  
       		int i2=Integer.parseInt(i1);
    		if(r1.isChecked())  
    		{
    			ch=r1.getText();
    			String ch1=(String)ch;
    			data.Insertresult(i2,qno1,"0",ch1);
    			  
    				
    			 
    		}
    		if(r2.isChecked())
    		{
    			ch=r2.getText();
    			String ch1=(String)ch;
    			
    			data.Insertresult(i2,qno1,"1",ch1);
    			
    			
    		}
    		if(r3.isChecked())
    		{
    			ch=r3.getText();
    			String ch1=(String)ch;
    			data.Insertresult(i2,qno1,"2",ch1);
    			
    			
    		}
    		if(r4.isChecked())
    		{
    			ch=r4.getText();
    			String ch1=(String)ch; 
    			data.Insertresult(i2,qno1,"3",ch1);
    			
    			
    		}  
    		z=data.getQuesCount();
     		System.out.println("No of records:"+z);
     		System.out.println("Counter is:"+counters);
    		if(counters>z)
    		{
    			checkanswer();
    			data.deletedb();
    			data.deleteres();
    			stopBrew();
    			result1();
    			mradiogroup.clearCheck();
    		}
    		else
    		{
    		getfirst();
 		   	mradiogroup.clearCheck();
    		}
    		}
     			 
    		
        	} 
        	       	
        	
  	        });
         
        done.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				
        		   
        		
        		
            	
      	        }
        	

			});
        skip.setOnClickListener(new OnClickListener(){
        	//@Override
            public void onClick(View v) {
            	
            	       	    			
         			getfirst();
         		   	mradiogroup.clearCheck();
        		
            	} 
            	       	
            	
      	        });
        quit.setOnClickListener(new OnClickListener(){
        	//@Override
            public void onClick(View v) {
            	
            	// set the message to display
                alertbox.setMessage("Are you sure you want to submit the test?");
     
                // set a positive/yes button and create a listener
                alertbox.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
     
                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {
                       // Toast.makeText(getApplicationContext(), "'Yes' button clicked", Toast.LENGTH_SHORT).show();
                        checkanswer();
            			data.deletedb();
            			data.deleteres();
            			stopBrew();
            			result1(); 
                    }
                });
     
                // set a negative/no button and create a listener
                alertbox.setNegativeButton("No", new DialogInterface.OnClickListener() {
     
                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {
                       // Toast.makeText(getApplicationContext(), "'No' button clicked", Toast.LENGTH_SHORT).show();
                    }
                });
     
                // display box
                alertbox.show();
            	
            	  			
         			
            	} 
            	       	
            	
      	        });
        
        data.close();
       
          
        } 
	public void onCheckedChanged(RadioGroup arg0, int arg1) {
		
		flag=1;
	}
	public void onClick(View arg0) {
		
		
	}
	//Set the timer
	public void setBrewTime(int minutes) {
        
         brewTime = minutes;
        
       }
	
	/*
    type:function
    name:startBrew
    returns:void
    parameters:no parameter
    date:29-06-2011
    purpose:Start Timer
    */
	
	 public void startBrew() {
		 
		
         // Create a new CountDownTimer to track the brew time
         brewCountDownTimer = new CountDownTimer(brewTime * 60 * 1000, 1000) {
           @Override
           public void onTick(long millisUntilFinished) {
        	   mTextField.setText("Time Remaining : "+String.valueOf(millisUntilFinished / 1000) + "s");
           }
           @Override
           public void onFinish() {
             isBrewing = false;
           

             mTextField.setText("Time Up!");
             
             
           }
         };

         brewCountDownTimer.start();
         
         isBrewing = true;
       }
	 
	 /*
	    type:function
	    name:startBrew
	    returns:void
	    parameters:no parameter
	    date:29-06-2011
	    purpose:Stop the timer
	    */
	 
	 public void stopBrew() {
         if(brewCountDownTimer != null)
           brewCountDownTimer.cancel();
         time1=(String) mTextField.getText();

         isBrewing = false;
         
       }
	 
	 
	 //Retrieving data through WebService........................... 
	 
	 /*
	    type:function
	    name:call
	    returns:void
	    parameters:no parameter
	    date:29-06-2011
	    purpose:Calls the web service's method named getquestions() and inserts the details into local 
	    database which has got from the web service
	    */
	 public void call()
	 { 
		 try {
			 		 
			 	     
			 	SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		 		
		 		request.addProperty("test_id",value1);  
		 			         		 
		 		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); 

		 		envelope.setOutputSoapObject(request);
		 		envelope.dotNet=true; 
		 		envelope.encodingStyle = SoapSerializationEnvelope.XSD;
		 		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
		 	
		 		androidHttpTransport.call(SOAP_ACTION, envelope);
		    	 Object result = (Object)envelope.getResponse();
		 	      String str1=result.toString();
		        if(str1.contains("anyType{anyType=")||str1.contains("}")||str1.contains("anyType="))
		        {
		    	  str1=str1.replace("anyType{anyType=", "");
		    	  str1=str1.replace("anyType=", "");
		    	  str1=str1.replace("}", ""); 
		    	  str1=str1.trim();
		    	  System.out.println("String"+str1); 
		    	  
		        }
		        String[] arrauserData;
		        arrauserData = ((String) str1).split("; ");  
		       
		        strstatus="N";
		        int temp2=arrauserData.length;
		        temp1=((arrauserData.length)/4);
		        quesid=new int[temp1]; 
		        strques=new String[temp1];
		        corans=new String[temp1]; 
		        stropt=new String[temp1];
		        st2=new String[temp1];
		        
		       
		         
		       
		         
		        for(int a=0,b=0;a<temp2;a=a+4)
		        {
		        	quesid[b]=Integer.parseInt(arrauserData[a]);
		        	b++;
	 	                	
		         }
		        
		        for(int a=1,b=0;a<temp2+1;a=a+4)  
		        {   
		        	strques[b]=arrauserData[a];
		        	b++;
		                	 
		        }
		           
		        for(int c=2,d=0;c<temp2;c=c+4) 
		        {
		        	 
		        stropt[d]=arrauserData[c];
		        
		        d++;
		        }
		        for(int c=3,d=0;c<temp2;c=c+4)
		        {
		        	
		        corans[d]=arrauserData[c];  
		        System.out.println("Correct Answer is:"+corans[d]);
		        
		        d++;
		        }
		        int m=0;
		        
		        for(int k=0;k<stropt.length;k++)  
		        {
		        	
		        	if(stropt[k].equals(""))
		        	{
		        		st2[m]=stropt[k];
		        		m++;
		        	}
		        	else
		        	{  
		        		int j=1;
		        		split_option=stropt[k].split("\\{");
		        		intloop=split_option[0].split(":");
		        		st2_split=new String[Integer.parseInt(intloop[1])];
		        		for(int n=0;n<Integer.parseInt(intloop[1]);n++)
		         		{
		        			split_option1=split_option[1].split(";s:"); 
		        			split_option2=split_option1[j].split(":");  
		        			split_option3=split_option2[1].substring(1, Integer.parseInt(split_option2[0])+1);
		        			st2_split[n]=split_option3;
		        			j=j+1;
		        				        			  
		        		}
		        		
		        		for(int y=0;y<st2_split.length;y++)
		        		{
		        			
		        			  
				        	a =a+((String) st2_split[y]).toString() + "#"; //+((String) st2_split[1]).toString()+ "#" +((String) st2_split[2]).toString()+ "#" +((String) st2_split[3]).toString();
				        	st2[m]=a;
				        	
		        		}
		        		a="";
		        		m++;
		        		
		        	}
		        }
		        
		       
		       
		        for(int x=0;x<temp1;x++) 
		        {
		         
                	data.InsertSRS(queid,quesid[x], strques[x], st2[x],corans[x],strstatus);
                	queid++;
     
		        }
		         Cursor c=data.selectdb();
		        while(c.moveToNext()) 
		        {
		        	String s=c.getString(3);
		        	System.out.println("Options:"+s); 
		        }
		       
		        getfirst(); 
	          	     
		        
	     } 
		 
 	        catch (Exception e)    
	        {
	       
	        }
	           
	        int z=data.getQuesCount(); 
	 		System.out.println("No of records:"+z);
	        
		}//call to the web services ends 
  
	 /*
	    type:function
	    name:getfirst
	    returns:void
	    parameters:no parameter
	    date:29-06-2011
	    purpose:Retrieves the first question from the database and display it.
	    */
	 
	 public void getfirst() 
	 { 	 
		
		txtquiz.setText("Quiz Name:"+quizvalue1);  
	    Cursor c1=data.getTop(); 
       	while(c1.moveToNext())
       	{
        no=c1.getInt(0);
  		ques1=c1.getString(2);   
 		opt1=c1.getString(3);
 		//String ans=c1.getString(4);
  		status1=c1.getString(5); 
 		
 		
        arrauserData1 = ((String) opt1).split("#"); 
        
        
        
        
        /*System.out.println("ArruserData is:"+arrauserData1[0]);
        System.out.println("ArruserData is:"+arrauserData1[1]);
        System.out.println("ArruserData is:"+arrauserData1[2]);
        System.out.println("ArruserData is:"+arrauserData1[3]);*/
 		
 		vquestionno.setText(""+no);
 		vquestion.setText(ques1); 
 		System.out.println("Length of an array:"+arrauserData1.length);
 		
 		if(arrauserData1.length==4)
        {
        	
        
 		if(arrauserData1[0].equals("") || arrauserData1[0].equals(" ") || arrauserData1[0].equals("  "))
 		{
 			r1.setVisibility(View.GONE);
 		}
 		else
 		{
 			r1.setVisibility(View.VISIBLE);
 			r1.setText(arrauserData1[0]);
 		}
 		
 		
 		if(arrauserData1[1].equals("") || arrauserData1[1].equals(" ") || arrauserData1[1].equals("  "))
 		{
 			r2.setVisibility(View.GONE);
 		}
 		else
 		{
 			r2.setVisibility(View.VISIBLE);
 			r2.setText(arrauserData1[1]);
 		}
 		
 		
 		if(arrauserData1[2].equals("") || arrauserData1[2].equals(" ") || arrauserData1[2].equals("  "))
 		{
 			r3.setVisibility(View.GONE);
 		}
 		else
 		{
 			r3.setVisibility(View.VISIBLE);
 			r3.setText(arrauserData1[2]);
 		}
 		
 		//System.out.println("arruserData[3]"+arrauserData1[3]);
 		if(arrauserData1[3].equals("") || arrauserData1[3].equals(" ") || arrauserData1[3].equals("  "))
 		{
 			r4.setVisibility(View.GONE);   
 		}
 		else
 		{
 			r4.setVisibility(View.VISIBLE);
 			r4.setText(arrauserData1[3]);
 		}
        }
 		if(arrauserData1.length==3)
 		{
 			if(arrauserData1[0].equals("") || arrauserData1[0].equals(" ") || arrauserData1[0].equals("  "))
 	 		{
 	 			r1.setVisibility(View.GONE);
 	 		}
 	 		else
 	 		{
 	 			r1.setVisibility(View.VISIBLE);
 	 			r1.setText(arrauserData1[0]);
 	 		}
 	 		
 	 		
 	 		if(arrauserData1[1].equals("") || arrauserData1[1].equals(" ") || arrauserData1[1].equals("  "))
 	 		{
 	 			r2.setVisibility(View.GONE);
 	 		}
 	 		else
 	 		{
 	 			r2.setVisibility(View.VISIBLE);
 	 			r2.setText(arrauserData1[1]);
 	 		}
 	 		
 	 		
 	 		if(arrauserData1[2].equals("") || arrauserData1[2].equals(" ") || arrauserData1[2].equals("  "))
 	 		{
 	 			r3.setVisibility(View.GONE);
 	 		}
 	 		else
 	 		{
 	 			r3.setVisibility(View.VISIBLE);
 	 			r3.setText(arrauserData1[2]);
 	 		}
 	 		
 	 		r4.setVisibility(View.INVISIBLE);
 		}
 		if(arrauserData1.length==2)
 		{
 			if(arrauserData1[0].equals("") || arrauserData1[0].equals(" ") || arrauserData1[0].equals("  "))
 	 		{
 	 			r1.setVisibility(View.GONE);
 	 		}
 	 		else
 	 		{
 	 			r1.setVisibility(View.VISIBLE);
 	 			r1.setText(arrauserData1[0]);
 	 		}
 	 		
 	 		
 	 		if(arrauserData1[1].equals("") || arrauserData1[1].equals(" ") || arrauserData1[1].equals("  "))
 	 		{
 	 			r2.setVisibility(View.GONE);
 	 		}
 	 		else
 	 		{
 	 			r2.setVisibility(View.VISIBLE);
 	 			r2.setText(arrauserData1[1]);
 	 		}
 	 		r3.setVisibility(View.INVISIBLE);
 	 		r4.setVisibility(View.INVISIBLE);
 		}
 		data.updatequiz(no);
 		
 	    
		
 		
       	}
 		
	 }
	 
	 /*
	    type:function
	    name:getresult
	    returns:void
	    parameters:no parameter
	    date:29-06-2011
	    purpose:Retrieves the result from the local database.
	    */
	 public void getresult()
	 {
		 Cursor c1=data.selectres();
			while(c1.moveToNext())
			{
				int n1=c1.getInt(0);
				String n2=c1.getString(1);
				String n3=c1.getString(2);
				System.out.println("First:"+n1);
				System.out.println("Second:"+n2);
				System.out.println("Third:"+n3);
			}
	 }
	 /*
	    type:function
	    name:checkanswer
	    returns:void
	    parameters:no parameter
	    date:29-06-2011
	    purpose:Checks whether the answer selected by the user is correct or wrong.
	    */
	  
	 public void checkanswer()
	 {
		 
		 Cursor c1=data.checkans();
			while(c1.moveToNext()) 
			{
				String n1=c1.getString(0); 
				System.out.println("Correct :"+n1);
				String n2=c1.getString(1);
				System.out.println("Selected:"+n2);
				if(n1.equals(n2)) 
				{ 
					correctcount++;
				}
				else
				{
					wrongcount++;  
				} 
				
			}
			int v=correctcount+wrongcount;
			unattempted=z-v; 
			System.out.println("No of correct answer:"+correctcount);
			System.out.println("No of wrong answers:"+wrongcount);
			System.out.println("Unattempted:"+unattempted); 
			
	 }
	 
	 /*
	    type:function
	    name:checkanswer
	    returns:void
	    parameters:no parameter
	    date:29-06-2011
	    purpose:Pass the values from questiondemo activity to result activity and calls the result activity
	    */
	 
	 private void result1()
		{	
		
		 
		    Intent i2 = new Intent(this,result.class);
			Bundle bundle = new Bundle();
			bundle.putString("sysdate",sdate);
			bundle.putString("stuname",stuname);
			bundle.putInt("testid", value1);
			bundle.putInt("correctcount",correctcount);
			bundle.putInt("wrongcount",wrongcount);
			bundle.putInt("Ques",z);
			bundle.putInt("unatt", unattempted);
			bundle.putString("time",time1);
			i2.putExtras(bundle);
			startActivity(i2);
			
			

		}
}
	
	 
	
    
