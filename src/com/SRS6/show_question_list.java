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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class show_question_list extends Activity implements OnItemClickListener  
{
	private static final String NAMESPACE = "http://tempuri.org/";      
	private static final String URL = "http://192.168.1.85/studentresponse/Service.asmx";
	
	
	private static final String SOAP_ACTION = "http://tempuri.org/GetQuestionList";      
	private static final String METHOD_NAME = "GetQuestionList";
	
	private static final String SOAP_ACTION1 = "http://tempuri.org/Insert_tests_to_questions";      
	private static final String METHOD_NAME1 = "Insert_tests_to_questions";
	
	private static final String SOAP_ACTION2 = "http://tempuri.org/Delete_question";      
	private static final String METHOD_NAME2 = "Delete_question";
	
	
	ListView lv1;
	private ArrayList<String> results = new ArrayList<String>();
	private ArrayList<String> results1 = new ArrayList<String>();  
	DataBaseHelper data;
	String s,questid,qid;
	int id1,id2;
	int callid,pos;
	String lessonsid;
	String name,lessons,ans;
	String[] question,questionid1;
	int[] questionid;
	Cursor c;
	String[] qtext,lessonname,testname;
	AlertDialog.Builder alertbox;
	Button btnok;
	CheckBox chkall;
	EditText edt_random;
	TextView label;
	public void onCreate(Bundle savedInstanceState)
	{
		requestWindowFeature(Window.FEATURE_NO_TITLE);  
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_question_list);
		
		 alertbox = new AlertDialog.Builder(this);
		 btnok=(Button) findViewById(R.id.btn_ok);
			chkall=(CheckBox) findViewById(R.id.chk_all);
			edt_random=(EditText) findViewById(R.id.edt_random_pool);
			label=(TextView) findViewById(R.id.txt_random_pool);
		
		Bundle bundle = getIntent().getExtras(); 
		lessons=bundle.getString("selectlesson");
		lessonsid=bundle.getString("selectlessonid");
	    lessonname=bundle.getStringArray("lessonnames");
	    testname=bundle.getStringArray("testnames");
	    
	    btnok.setVisibility(View.INVISIBLE);
	    edt_random.setVisibility(View.INVISIBLE);
	    
	    label.setVisibility(View.INVISIBLE);
		
		lv1=(ListView)findViewById(R.id.List);
		
		getquestionlist(lessonsid);
		
		callquestlist();
		
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.quest_menu, menu);  
	    return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        
	        case R.id.AQ:    addquestion();
	                            break;
	        case R.id.EQ: 		editquestion();
            					break;
	        case R.id.DQ:     checkvalidate();
	        					break;
	    }
	    return true;
	}
	public void onItemClick(AdapterView<?> a, View v, int position, long id) 
	{
		
		
		System.out.println("Position..."+position);
		
		
		int count=this.lv1.getAdapter().getCount();
		System.out.println("Count is...."+count);
		
		pos=position;
		if(this.lv1.isItemChecked(position))
		{
			ans=(String) a.getItemAtPosition(position);
			if(ans.contains(";"))
			{
				ans=ans.replace(";","");
			}
			System.out.println("Value is "+ans);
			
			
		}
		if(this.lv1.isItemChecked(position)==false)
		{
			String ans=(String) a.getItemAtPosition(position);
			System.out.println("Value is "+ans);
			
		}
		
		 qid=""+questionid[pos];
   		System.out.println("Qid will be "+qid);
		
		
		
		
		
		
		
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
		 	    	
		 	    	
		 	    
		 	    	//str12=str12.replace(" Table=anyType{", "");	
		 	     	
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
		 	   
		 	   
		       
		 	   Toast.makeText(this, "Questions Added to test succussfully",Toast.LENGTH_SHORT).show();
		       
		       
		 	      
		 } 
	        catch (Exception e)    
	        { 
	        //tv.setText(e.getMessage());
	        	System.out.println("ERROR:"+e.getMessage());
	        }  
	 }
	
	/*Type :Function
	name:addquestion
	return type:void
	date:29-6-11
	purpose:Starts the activity addquest_byprofessor*/
	private void addquestion()
	{	
		
		
        Intent i2 = new Intent(this,addquest_byprofessor.class);
        Bundle bundle = new Bundle();
        
        bundle.putString("selectlessonid",lessonsid);
		bundle.putStringArray("lessonnames",lessonname);
		bundle.putStringArray("testnames",testname);
		i2.putExtras(bundle);
		startActivity(i2);

	}
	private void editquestion()
	{	
		
		
        Intent i2 = new Intent(this,edit_questions_by_professor.class);
        Bundle bundle = new Bundle();
        bundle.putString("selectlessonid",lessonsid);
		bundle.putStringArray("lessonnames",lessonname);
		bundle.putStringArray("testnames",testname);
		bundle.putString("qid",qid);
		System.out.println("Qid1 will be "+qid);
		i2.putExtras(bundle);
		startActivity(i2);

	}
	private void deletequestion(String qtextid)
	{	
		
		
		 try {
	 		 
		 	  
	      	 	SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME2);
	    	 	
	    	 	
	    	 	request.addProperty("qid",qtextid);  
	    	 	
	      	 		
	      	 	
	    	 		 			         		 
		 		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); 

		 		envelope.setOutputSoapObject(request);
		 		envelope.dotNet=true; 
		 		envelope.encodingStyle = SoapSerializationEnvelope.XSD; 
		 		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
		 	
		 	    androidHttpTransport.call(SOAP_ACTION2, envelope);
		 	   
		 	  
		       
		 	    Toast.makeText(this, "Question Deleted succussfully",Toast.LENGTH_SHORT).show();
		       
		       
		 	      
		 } 
	        catch (Exception e)    
	        { 
	        
	        	System.out.println("ERROR:"+e.getMessage());
	        }  

	}
	
	public void checkvalidate()
	 {
		 
        alertbox.setMessage("Are you sure you want to delete the test?");
        alertbox.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

        public void onClick(DialogInterface arg0, int arg1)
        {
       	 
       	 	if(ans.contains(";"))
       	 	{
       	 		ans=ans.replace(";","");
       	 	}
            	deletequestion(qid);
            	lv1.clearChoices();
            	results.clear();
            	getquestionlist(lessonsid);
            	callquestlist();
            	  
					
         }
        });
       
        alertbox.setNegativeButton("No", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface arg0, int arg1) {

            }
        });


        alertbox.show();
    	
    	  			
 			
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
	
	
}
