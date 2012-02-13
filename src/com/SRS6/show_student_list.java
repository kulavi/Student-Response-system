package com.SRS6;

//Starting screen of the project which displays a login button



import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

//import com.SRS6.addtest_byprofessor.MyOnLessonSelectedListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

public class show_student_list extends Activity implements OnItemClickListener  {
	
	private static final String NAMESPACE = "http://tempuri.org/";      
	private static final String URL = "http://192.168.1.85/studentresponse/Service.asmx";
	
	
	private static final String SOAP_ACTION = "http://tempuri.org/GetStudentList_For_Lesson";      
	private static final String METHOD_NAME = "GetStudentList_For_Lesson";
	
	/*private static final String SOAP_ACTION1 = "http://tempuri.org/getlesson_id";      
	private static final String METHOD_NAME1 = "getlesson_id";*/
	
	Spinner sprLESSON;
	Button btnshowlist;
	TextView txtxlesson;
	private ArrayList<String> results = new ArrayList<String>();
	String[] firstname,lastname,lessonname; 
	String strlessonid,lesson,lessons;
	ListView lv1;
	String lessonsid;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_student_list);
        
        //txtxlesson=(TextView) findViewById(R.id.txt_lesson);
        //sprLESSON=(Spinner) findViewById(R.id.spr_lesson);
        //btnshowlist=(Button) findViewById(R.id.btn_showlist);
        lv1=(ListView)findViewById(R.id.List);
        
        Bundle bundle = getIntent().getExtras(); 
        lessons=bundle.getString("selectlesson");
        lessonsid=bundle.getString("selectlessonid");
	    lessonname=bundle.getStringArray("lessonnames"); 
        
        
       /* ArrayAdapter<String> spnrlesson = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item);
        
	     for(int i=0;i<lessonname.length;i++) 
	        {
	         	        	 
	    	 	spnrlesson.add(lessonname[i]);   
	        	System.out.println("Course is:"+lessonname[i]);
	        } 
	        
	     	spnrlesson.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	        sprLESSON.setAdapter(spnrlesson);
	        sprLESSON.setOnItemSelectedListener(new MyOnLessonSelectedListener());
        
        
	        btnshowlist.setOnClickListener(new Button.OnClickListener() {        	 
            public void onClick(View v) {
            	
            	txtxlesson.setVisibility(View.GONE);
            	sprLESSON.setVisibility(View.GONE);
                btnshowlist.setVisibility(View.GONE);
            	callstudentlist();
            	
            	
                }
            });*/
        
	    callstudentlist();
    }
    
    /*public void getlessonid(String lname)
	 { 
		
		 try {
			 		  
			 	  
	    	 	SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME1);
		 		
		 		request.addProperty("lesson",lname);  
		 		
		 			         		 
		 		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); 

		 		envelope.setOutputSoapObject(request); 
		 		envelope.dotNet=true; 
		 		envelope.encodingStyle = SoapSerializationEnvelope.XSD;
		 		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
		 	
		 		androidHttpTransport.call(SOAP_ACTION1, envelope);
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
		 	    
		 	    if(str1.contains(";"))
		 	    {
		 	    	str1=str1.replace(";","");
		 	    }
		        strlessonid=str1; 
		        
		        System.out.println("Lesson id is...."+strlessonid);
		        
		        
		 	    }   
		 }
	 	    catch (Exception e)    
	        {
			// text1.setText(e.getMessage()); 
			 System.out.println("Error msg:"+e.getMessage());
	        
	        }
	 }*/
    public void getstudentlist()
	 { 
		
		 try {
			 		  
			 	  
	    	 	SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		 		
		 		request.addProperty("lessonid",""+lessonsid);  
		 		
		 			         		 
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
		        arrauserData = ((String) str12).split("; ");
		        int temp=arrauserData.length;
		        int temp2=arrauserData.length/2;
		        
		        firstname=new String[temp2];
		        lastname=new String[temp2];   
		        
		        
		        for(int a=0,b=0;a<temp;a=a+2)
		        {
		        	firstname[b]=arrauserData[a];  
		        	b++;
	 	                	
		         }
		        
		        
		        for(int a=1,b=0;a<temp;a=a+2)
		        {
		        	lastname[b]=arrauserData[a];  
		        	b++;
	 	                	
		        }
		          
		        
		  
		  
		        
		 	    }   
		 }
	 	    catch (Exception e)     
	        {
			 
			 System.out.println("Error msg:"+e.getMessage());
	        
	        }
	 }
    
    /*public class MyOnLessonSelectedListener implements OnItemSelectedListener {

	    public void onItemSelected(AdapterView<?> parent,
	        View view, int pos, long id) {
	    	
	    	System.out.println("Position is:"+pos);
	        
	    	lesson=parent.getItemAtPosition(pos).toString();
	    	
	    	System.out.println("Lesson names are..... "+lesson);
	    	    	
	    	getlessonid(lesson);
	    	
	    	
	     }
 
	    public void onNothingSelected(AdapterView parent) {
	      // Do nothing.
	    }
    
}*/
    
    public void callstudentlist()
    {
    	getstudentlist();
    	
    	for(int x=0;x<lastname.length;x++)
        {
        results.add(firstname[x]+" "+lastname[x]);
        }
		
		CheckBox cb=new CheckBox(this); 
		lv1.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		lv1.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_multiple_choice,results));
		lv1.setTextFilterEnabled(true);
		lv1.setOnItemClickListener(this);
    }

	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}
}