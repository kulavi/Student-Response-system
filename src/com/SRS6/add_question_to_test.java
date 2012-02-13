package com.SRS6;


import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.SRS6.addtest_byprofessor.MyOnConentSelectedListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class add_question_to_test extends Activity implements OnItemClickListener,OnCheckedChangeListener  
{
	private static final String NAMESPACE = "http://tempuri.org/";      
	private static final String URL = "http://192.168.1.85/studentresponse/Service.asmx";
	
	
	private static final String SOAP_ACTION = "http://tempuri.org/GetQuestionList";      
	private static final String METHOD_NAME = "GetQuestionList";
	
	private static final String SOAP_ACTION1 = "http://tempuri.org/Insert_tests_to_questions";      
	private static final String METHOD_NAME1 = "Insert_tests_to_questions";
	
	private static final String SOAP_ACTION3 = "http://tempuri.org/Delete_tests_to_questions";      
	private static final String METHOD_NAME3 = "Delete_questions_to_tests";
	
	private static final String SOAP_ACTION4 = "http://tempuri.org/gettest";      
	private static final String METHOD_NAME4 = "gettest";
	
	  
	
	
	private ArrayList<String> results = new ArrayList<String>();
	ListView lv1;
	
	DataBaseHelper data;
	String s,testid,prev_questid;
	
	int id1,questid,id2;
	int callid,temp3;
	String lessonsid;  
	String name,lessons,ans,dbtestid,testnames;
	String[] question,strtestname;
	int[] strtestid,questionid;
	String[] questionid1;
	Cursor c;
	String[] qtext,lessonname,testname;
	CheckBox chkall;
	Button btnok;
	EditText edt_random;
	AlertDialog.Builder alertbox;
	public void onCreate(Bundle savedInstanceState)
	{
		requestWindowFeature(Window.FEATURE_NO_TITLE);  
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_question_list);
		
		btnok=(Button) findViewById(R.id.btn_ok);
		chkall=(CheckBox) findViewById(R.id.chk_all);
		edt_random=(EditText) findViewById(R.id.edt_random_pool);
		
		
		
		Bundle bundle = getIntent().getExtras(); 
		testnames=bundle.getString("testname");
		
		System.out.println("Selected test is...."+testnames);
		lessonsid=bundle.getString("selectlessonid");
	    
	    data=new DataBaseHelper(this);
	    data.open();
	    
	    data.deletetest();
		gettest_details();
				 
		gettestid(testnames);
		
		 alertbox = new AlertDialog.Builder(this);
		
		
		
		lv1=(ListView)findViewById(R.id.List);
		
		getquestionlist(lessonsid);
		callquestlist();
		
		
		chkall.setOnCheckedChangeListener(new OnCheckedChangeListener()
	     {
	         

			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				
				
				if(chkall.isChecked())
				{
					makeSelections();
				}
				else
				{
					removeSelections();
					
				}
				
			}
	     });
		
		data.close();
		
		  
	}
	
	private void makeSelections() {

		// user has checked checkbox so check all the items

		int count = this.lv1.getAdapter().getCount();
		

		for (int i = 0; i < count; i++) 
		{
			
			
			this.lv1.setItemChecked(i, true);
			
			
			addtest_to_questions(dbtestid,""+questionid[i],"0");  
			
					 
			
			
		}
		
		Toast.makeText(this, "All Selected Questions Added to test succussfully",Toast.LENGTH_SHORT).show();

		

	}
	
	private void removeSelections() { 

		// user has unchecked checkbox so uncheck all the items

		int count = this.lv1.getAdapter().getCount();

		for(int i=0;i<count;i++)
		{
		this.lv1.setItemChecked(i, false);
		
		deletetest_to_questions(dbtestid,""+questionid[i],"0");
		}
		Toast.makeText(this, "All Selected Questions Deleted from test succussfully",Toast.LENGTH_SHORT).show();

	}
	
	
	
	
	
	
	
	public void onItemClick(AdapterView<?> a, View v, int position, long id) 
	{
		
		
		System.out.println("Position..."+position);
		
		
		int count=this.lv1.getAdapter().getCount();
		System.out.println("Count is...."+count);
		
		
		if(this.lv1.isItemChecked(position))
		{
			ans=(String) a.getItemAtPosition(position);
			questid=questionid[position];
			if(ans.endsWith(";"))
			{
				ans=ans.replace(";","");
			}
			System.out.println("Value is "+ans);
			
			addtest_to_questions(dbtestid, ""+questid,"0");
			
			Toast.makeText(this, "Question Added to test succussfully",Toast.LENGTH_SHORT).show();
		}
		else
		{
			ans=(String) a.getItemAtPosition(position);
			questid=questionid[position];
			if(ans.endsWith(";"))
			{
				ans=ans.replace(";","");
			}
			System.out.println("Value is "+ans);
			
			deletetest_to_questions(dbtestid, ""+questid,"0");
			
			Toast.makeText(this, "Question Deleted from test succussfully",Toast.LENGTH_SHORT).show();
			
		}
		
	}

	

	public void getquestionlist(String lessonids)
	 { 
		
		 try {
			 		  
			 	  
	    	 	SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		 		
		 		request.addProperty("lessonids",lessonids);  
		 		
		 			         		 
		 		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); 

		 		envelope.setOutputSoapObject(request); 
		 		envelope.dotNet=true; 
		 		envelope.encodingStyle = SoapSerializationEnvelope.XSD;
		 		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
		 	
 		 		androidHttpTransport.call(SOAP_ACTION, envelope);
		    	 Object result = (Object)envelope.getResponse();
		 	      String str12=result.toString();  
		 	      
		 	     System.out.println("Data:"+str12); 
		 	    if(str12.equals(null)||str12.equals("anyType{}"))
		        {
		 		   
		        	final AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
		        	//alertbox.setMessage(onlyname+" "+"you dont have any rewards earned.... ");
	    		     
		        	// set a positive/yes button and create a listener
	    			alertbox.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
	    				 
	                    // click listener on the alert box
	                    public void onClick(DialogInterface arg0, int arg1) {
	                        // the button was clicked
	                        //Toast.makeText(getApplicationContext(), "OK button clicked", Toast.LENGTH_LONG).show();
	                    }
	                });
	     
	                // show it
	                alertbox.show();
		        } 
		 	    else 
		 	    {	 	    	
		 	    	
		 	    if(str12.contains("anyType{anyType=")||str12.contains("}")||str12.contains("anyType="))
		        {
		 	    	 	     	
		    	  str12=str12.replace("anyType{anyType=", "");  
		    	  str12=str12.replace("anyType=", "");
		    	  str12=str12.replace("}", ""); 
		    	  str12=str12.trim(); 
		    	  System.out.println("String"+str12); 
		    	  
		        }
		        
		        
		       String[] arrauserData;
		        arrauserData=((String) str12).split("; ");
		        
		       int temp=arrauserData.length;
		        int temp2=arrauserData.length/2;
		        
		        
		        questionid=new int[temp2];
		        questionid1=new String[temp2];
		        question=new String[temp2];   
		        
		        
		        for(int a=0,b=0;a<temp;a=a+2)
		        {
		        	questionid1[b]=arrauserData[a];
		        	if(questionid1[b].contains("id=")||questionid1[b].contains("; ;id="))
		        	{
		        		questionid1[b]=questionid1[b].replace("id=", "");
		        		questionid1[b]=questionid1[b].replace("; ;id=", "");
		        	}
		        	
		        	questionid[b]=Integer.parseInt(questionid1[b]);
		        	b++;
	 	                	
		         } 
		        
		        
		        for(int a=1,b=0;a<temp;a=a+2)
		        {
		        	question[b]=arrauserData[a];  
		        	
		        	if(question[b].contains(",$"))
		        	{
		        		question[b]=question[b].replace(",$", ";");
		        		
		        		
		        	}
		        	if(question[b].contains("\n"))
		        	{
		        		
		        		question[b]=question[b].replace("\n", "");
		        		
		        	}
		        	b++;
	 	                	
		        }
		          
		        
		  
		  
		        
		 	    }   
		 }
	 	    catch (Exception e)     
	        {
			 
			 System.out.println("Error msg:"+e.getMessage());
	        
	        }
	 }
	
		
	public void addtest_to_questions(String testid,String questid,String prev_questid)
	
	 { 
		 try {
			 		 
			 	  
	      	 	SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME1);
	    	 	
	    	 	
	    	 	request.addProperty("testsid",testid);  
	    	 	request.addProperty("questionid",questid);
	    	 	request.addProperty("weights","1");
	    	 	request.addProperty("prev_quest_id",prev_questid);
	    	 	
	      	 	
	      	 		 	
	    	 		 			         		 
		 		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); 

		 		envelope.setOutputSoapObject(request);
		 		envelope.dotNet=true; 
		 		envelope.encodingStyle = SoapSerializationEnvelope.XSD; 
		 		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
		 	
		 	   androidHttpTransport.call(SOAP_ACTION1, envelope);
		 	      
		 	      
		 } 
	        catch (Exception e)    
	        { 
	        
	        	System.out.println("ERROR:"+e.getMessage());
	        }  
	 }
	
	public void deletetest_to_questions(String testid,String questid,String prev_questid)
	
	 { 
		 try {
			 		 
			 	  
	      	 	SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME3);
	    	 	
	    	 	
	    	 	request.addProperty("testsid",testid);  
	    	 	request.addProperty("questionid",questid);
	    	 	request.addProperty("weights","1");
	    	 	request.addProperty("prev_quest_id",prev_questid);
	    	 	
	      	 	
	      	 		 	
	    	 		 			         		 
		 		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); 

		 		envelope.setOutputSoapObject(request);
		 		envelope.dotNet=true; 
		 		envelope.encodingStyle = SoapSerializationEnvelope.XSD; 
		 		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
		 	
		 	   androidHttpTransport.call(SOAP_ACTION3, envelope);
		 	   
		 	   	 	      
		 } 
	        catch (Exception e)    
	        { 

	        	System.out.println("ERROR:"+e.getMessage());
	        }  
	 }
	
	/*
    type:function
    name:call1
    returns:void
    parameters:no parameter
    date:29-06-2011
    purpose:Retrieves the test name and id from server
    */
	public void gettest_details()
	{
	        try { 

 	        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME4);

	        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	        envelope.dotNet=true;
	        envelope.setOutputSoapObject(request);

	        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
	        androidHttpTransport.call(SOAP_ACTION4, envelope);

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
	        String[] quizdata;
	        quizdata = ((String) str1).split("; ");
            int temp2=quizdata.length;
     	     temp3=((quizdata.length)/2);
	        strtestid=new int[temp3];
	        strtestname=new String[temp3];
		        System.out.println("Length:"+temp3);
		        for(int c=1,d=0;c<temp2;c=c+2)
		        {
		        	
		        	strtestname[d]=quizdata[c];
		        	if(strtestname[d].contains(";"))
		        	{
		        		strtestname[d]=strtestname[d].replace(";","");
		        	}
		         
		        	d++;
		        }
		        for(int a=0,b=0;a<temp2;a=a+2)
		        {
		        	strtestid[b]=Integer.parseInt(quizdata[a]);
		        	b++;
		                	
		        }		        		           
		        for(int x=0;x<temp3;x++)
		        {
		        		        
		      	data.Inserttest(strtestid[x], strtestname[x]);	
		        		        		         
		        }
		       
		        
	      
	    }
	        catch (Exception e) {
	       
	        System.out.println("ERROR IS..."+e.getMessage());
	        }
	}
	
	public void gettestid(String testname)
	 {
		
		 
		 Cursor c1=data.selecttestid_fortest(testname); 
		 
		 
			while(c1.moveToNext())
			{
				
				dbtestid=c1.getString(0);
				System.out.println("Test id is...:"+dbtestid);
								
			}
		 
		
		 
	 }
		
	public void callquestlist()
	{
		for(int x=0;x<question.length;x++) 
        {
			System.out.println("Questions are..."+question[x]);
			results.add(question[x]);
        }
		
		CheckBox cb=new CheckBox(this); 
		
		lv1.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		lv1.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_multiple_choice,results));
		lv1.setTextFilterEnabled(true);
		lv1.setOnItemClickListener(this);
	}




	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	
	
}
