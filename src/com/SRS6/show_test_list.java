package com.SRS6;



import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.SRS6.addtest_byprofessor.MyOnConentSelectedListener;
//import com.SRS6.show_student_list.MyOnLessonSelectedListener;

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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

public class show_test_list extends Activity implements OnItemClickListener
{
	
	private static final String NAMESPACE = "http://tempuri.org/";      
	private static final String URL = "http://192.168.1.85/studentresponse/Service.asmx";
	
	private static final String SOAP_ACTION2 = "http://tempuri.org/GetTestName_for_Lesson";      
	private static final String METHOD_NAME2 = "GetTestName_for_Lesson";
	
	private static final String SOAP_ACTION3 = "http://tempuri.org/Delete_test";      
	private static final String METHOD_NAME3 = "Delete_test";
	
	ListView lv1;
	private ArrayList<String> results = new ArrayList<String>();
	String name;
	int pos;
	String lessonsid;
	String[] testname,lessonname,testnames;
	Spinner sprLESSON;
	Button btnshowlist;
	TextView txtxlesson;
	String lesson,strlessonid,ans,lessons;
	AlertDialog.Builder alertbox;
	int[] testid;
	int tid;
	
	
	public void onCreate(Bundle savedInstanceState)
	{
		requestWindowFeature(Window.FEATURE_NO_TITLE);  
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_test_list);
		
		
        lv1=(ListView)findViewById(R.id.List);
        
       alertbox = new AlertDialog.Builder(this);
		
		Bundle bundle = getIntent().getExtras(); 
		lessons=bundle.getString("selectlesson");
		lessonsid=bundle.getString("selectlessonid");
		lessonname=bundle.getStringArray("lessonnames");
	    
	    
	    
	    
	     
	    	
		gettest(""+lessonsid);
		calltestlist();
		
	}
	public void onItemClick(AdapterView<?> a, View v, int position, long id) 
	{
		
		pos=position;
		System.out.println("Position..."+position);
		ans= (String) a.getItemAtPosition(position);
		System.out.println("Value is "+ans);
		if(ans.contains(";"))
	 	{
	 		ans=ans.replace(";","");
	 	}
		
		tid=testid[pos];
		
		
		
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.test_menu, menu);  
	    return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    	case R.id.q2t:      addquestionstotest();
        						break;
	        case R.id.AT:     	addtest();
	                            break;
            case R.id.ET: 		edittest();
            					break; 
              					
	        case R.id.DT:		checkvalidate();
	        					break;
	        	
	       
			
	   }
	   return true;
	}
	private void addquestionstotest()
	{	
		
		
        Intent i2 = new Intent(this,add_question_to_test.class);
        Bundle bundle = new Bundle();
        bundle.putString("testname",ans);
        bundle.putString("selectlessonid",lessonsid);
        
		bundle.putStringArray("lessonnames",lessonname);
		bundle.putStringArray("testnames",testname);
		i2.putExtras(bundle);
		startActivity(i2);

	}

	private void addtest()
	{	
		
		
        Intent i2 = new Intent(this,addtest_byprofessor.class);
        Bundle bundle = new Bundle();
        bundle.putString("selectedlessonid",lessonsid);
		bundle.putStringArray("lessonnames",lessonname);
		bundle.putStringArray("testnames",testname);
		bundle.putString("testname",ans);
		i2.putExtras(bundle);
		startActivity(i2);

	}
	private void edittest()
	{	
		
		
        Intent i2 = new Intent(this,edit_test_by_professor.class);
        Bundle bundle = new Bundle();
        bundle.putString("selectedlessonid",lessonsid);	
		bundle.putString("testname",ans);
		bundle.putInt("testid",tid);
		i2.putExtras(bundle);
		startActivity(i2);

	}
	private void deletetest(String testname1)
	{	
		
		 try {
	 		 
		 	  
	      	 	SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME3);
	    	 	
	    	 	
	    	 	request.addProperty("testname",testname1);  
	    	 	
	      	 		
	      	 	
	    	 		 			         		 
		 		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); 

		 		envelope.setOutputSoapObject(request);
		 		envelope.dotNet=true; 
		 		envelope.encodingStyle = SoapSerializationEnvelope.XSD; 
		 		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
		 	
		 	    androidHttpTransport.call(SOAP_ACTION3, envelope);
		 	   
		 	  
		       
		 	    Toast.makeText(this, "Test Deleted succussfully",Toast.LENGTH_SHORT).show();
		       
		       
		 	      
		 } 
	        catch (Exception e)    
	        { 
	        
	        	System.out.println("ERROR:"+e.getMessage());
	        }  
        

	}
	
	public void calltestlist()
	{
		for(int x=0;x<testnames.length;x++) 
        {
			System.out.println("Questions are..."+testnames[x]);
			results.add(testnames[x]);
        }
		
		CheckBox cb=new CheckBox(this); 
		lv1.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		lv1.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_multiple_choice,results));
		lv1.setTextFilterEnabled(true);
		lv1.setOnItemClickListener(this);
	}
	
	
	 
	
	 public void gettest(String lessonid1)
	 { 
		
		 try {
			 		  
			 	  
	    	 	SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME2);
		 		
		 		request.addProperty("lessonid",lessonid1);  
		 		
		 			         		 
		 		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); 

		 		envelope.setOutputSoapObject(request); 
		 		envelope.dotNet=true; 
		 		envelope.encodingStyle = SoapSerializationEnvelope.XSD;
		 		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
		 	
		 		androidHttpTransport.call(SOAP_ACTION2, envelope);
		    	 Object result = (Object)envelope.getResponse(); 
		 	      String str1=result.toString(); 
		 	      
		 	     System.out.println("Data:"+str1);
		 	    if(str1.equals(null)||str1.equals("anyType{}"))
		        {
		 		   
		        	final AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
		        	//alertbox.setMessage(stunm+" "+"you are yet not registered to any courses...you need to register first.... ");
	    		     
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
		       int temp=arrauserData.length;
		       int temp1=arrauserData.length/2;
		       testid=new int[temp1];
		       testnames=new String[temp1];   
		        
		       
		       for(int a=0,b=0;a<temp;a=a+2)
		        {
		        	testid[b]=Integer.parseInt(arrauserData[a]);
		        
		        	b++;
	 	                	
		        }
		        for(int a=1,b=0;a<temp;a=a+2)
		        {
		        	testnames[b]=arrauserData[a];
		        	if(testnames[b].contains(";"))
		        	{
		        		testnames[b]=testnames[b].replace(";", ""); 
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
	 
	 public void checkvalidate()
	 {
		 
         alertbox.setMessage("Are you sure you want to delete the test?");
         alertbox.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

         public void onClick(DialogInterface arg0, int arg1)
         {
        	 
        	 	
             	deletetest(ans);
             	lv1.clearChoices();
             	results.clear();
             	gettest(""+lessonsid);
        		calltestlist();
					
          }
         });
        
         alertbox.setNegativeButton("No", new DialogInterface.OnClickListener() {

             public void onClick(DialogInterface arg0, int arg1) {

             }
         });


         alertbox.show();
     	
     	  			
  			
     	} 

     	       	
	 
	 
	
	
	
}
