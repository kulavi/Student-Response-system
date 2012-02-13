package com.SRS6;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class show_lesson_list extends Activity {
	
	
	private static final String NAMESPACE = "http://tempuri.org/";      
	private static final String URL = "http://192.168.1.85/studentresponse/Service.asmx";
	
	private static final String SOAP_ACTION1 = "http://tempuri.org/getlesson_id";      
	private static final String METHOD_NAME1 = "getlesson_id";
	
	TableLayout lessontable;
	String[] lessonname,testname;
	String strlessonid,selectedlesson;
	int selectedlessonid;
	
	public void onCreate(Bundle icicle)
	 {
		 requestWindowFeature(Window.FEATURE_NO_TITLE);  
	     getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
	     

	 super.onCreate(icicle);
	 setContentView(R.layout.show_lesson_list);
	
	
	Bundle bundle = getIntent().getExtras();
	 lessonname=bundle.getStringArray("lessonnames");
	 testname=bundle.getStringArray("testnames");
	 
	 lessontable=(TableLayout) findViewById(R.id.lesson_tbl);
	 getreport();
	 }
	
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.professor_menu, menu);  
	    return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        
	        case R.id.test:     Showtest();
	                            break;
	        case R.id.question: viewquestionlist();
            					break;
	        case R.id.VSL:     show_student_list();
	        					break;
	    }
	    return true;
	}
	public void getreport() 
	 { 
		 
		 
		 	int i=0;
	    	    	
	    	    final RadioButton[] radioButton = new RadioButton[lessonname.length];
	            RadioGroup radioGroup = new RadioGroup(this);
	            radioGroup.setOrientation(RadioGroup.VERTICAL);
	    	
        for(int j=0;j<lessonname.length;j++) 
       {
		 try{
			 
			 
			 final TableRow tr = new TableRow(this); 
	            tr.setId(100+i);
	            tr.layout(0, 0, 0, 0); 
	            final String lesson=lessonname[j];
   	
   	
   	
       
           radioButton[j]  = new RadioButton(this);
           
           radioGroup.addView(radioButton[j]);
          
           radioButton[j].setText(""+lesson);
           radioButton[j].setTextColor(Color.BLACK);
           
       
       
		 }
	 

	            
				
				
				catch (Exception e)    
		        {
		        //text1.setText(e.getMessage()); 
		        System.out.println("Error msg:::::::::"+e.getMessage());
		        }
			}
        
        //tr.addView(radioGroup);
        
        // Add the TableRow to the TableLayout 
           lessontable.addView(radioGroup, new TableLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                   LayoutParams.WRAP_CONTENT));
           
           radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
               public void onCheckedChanged(RadioGroup rg, int checkedId)
               {
                    for(int i=0; i<rg.getChildCount(); i++) {
                         RadioButton btn = (RadioButton) rg.getChildAt(i);
                         if(btn.getId() == checkedId) {
                              selectedlesson = btn.getText().toString();
                              System.out.println("The Text is.."+selectedlesson);
                                                            
                              getlessonid(selectedlesson);
                              return;
                         }
                    }
               }
          });

	 }
	
	public void getlessonid(String lname)
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
	 }
	
	/*Type :Function
	name:addtest
	return type:void
	date:29-6-11
	purpose:Starts the activity addtest_byprofessor*/
	private void Showtest()
	{	
		
		
        Intent i2 = new Intent(this,show_test_list.class);
        Bundle bundle = new Bundle();
        bundle.putString("selectlesson",selectedlesson);
        bundle.putString("selectlessonid",strlessonid);
		bundle.putStringArray("lessonnames",lessonname);
		bundle.putStringArray("testnames",testname);
		i2.putExtras(bundle);
		startActivity(i2);

	}
	
	/*Type :Function
	name:show_student_list
	return type:void
	date:29-6-11
	purpose:Starts the activity show_student_list*/
	private void show_student_list()
	{	
		
		
        Intent i2 = new Intent(this,show_student_list.class);
        Bundle bundle = new Bundle();
        bundle.putString("selectlesson",selectedlesson);
        bundle.putString("selectlessonid",strlessonid);
		bundle.putStringArray("lessonnames",lessonname);
		bundle.putStringArray("testnames",testname);
		i2.putExtras(bundle);
		startActivity(i2);

	}
	
	private void viewquestionlist()
	{	
		
		
        Intent i2 = new Intent(this,show_question_list.class);
        Bundle bundle = new Bundle();
        bundle.putString("selectlessonid",strlessonid);
		bundle.putStringArray("lessonnames",lessonname);
		bundle.putStringArray("testnames",testname);
		i2.putExtras(bundle);
		startActivity(i2);

	}

}
