//Add test by professor.

package com.SRS6;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.SRS6.reward.MyOnCourseSelectedListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class addtest_byprofessor extends Activity {    
	 
	
	private static final String NAMESPACE = "http://tempuri.org/";      
	private static final String URL = "http://192.168.1.170/studentresponse/Service.asmx";
	   
	
	private static final String SOAP_ACTION = "http://tempuri.org/inserttests";      
	private static final String METHOD_NAME = "inserttests";
	
	private static final String SOAP_ACTION1 = "http://tempuri.org/get_content";      
	private static final String METHOD_NAME1 = "get_content";    
	
	
	
	DataBaseHelper data;
	
	EditText edtNAME,edtDIM,edtTR,edtMS,edtDESC;
	Spinner sprPARENT_UNIT,sprLESSON;
	CheckBox chkPUBLISH,chkSQOBO,chkSGA,chkSCA,chkSHUFA,chkSHUFQ,chkDOL,chkTCBP,chkDWDT;
	Button btnsave;
	TextView text1;
	
	String[] contentname,lessonname,testname;
	int[] contentid;
	int strlessonid,strcontentid;
	String content,lesson,strcontent_id,option,random_pool,gen_threshold;
    int temp7;
	String strpunit,strname,strdur_min,strdur_min1,strtest_rep,strtest_rep1,strmastery_score,strmastery_score1,strpub,strSQOBO,strSGA,strSCA,strSHUFA,strSHUFQ;
	String strDOL,strTCBP,strDWDT,strdes,stractive,strdes1,lessonsid;
	
	public void onCreate(Bundle icicle)
	
	
	 {
		 requestWindowFeature(Window.FEATURE_NO_TITLE);  
	     getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
	     

	     super.onCreate(icicle);
	     setContentView(R.layout.addtest_byprofessor);
	     
	     
	     //sprLESSON=(Spinner) findViewById(R.id.spr_lesson);
	     sprPARENT_UNIT=(Spinner) findViewById(R.id.spr_parent_unit); 
	     
	     
	     
	     edtNAME=(EditText) findViewById(R.id.edt_name);
	     edtDIM=(EditText) findViewById(R.id.edt_duration_in_minute);
	     edtTR=(EditText) findViewById(R.id.edt_test_repitition);
	     edtMS=(EditText) findViewById(R.id.edt_mastery_score);
	     edtDESC=(EditText) findViewById(R.id.edt_descriptions);
	     
	     
	     
	     chkPUBLISH=(CheckBox) findViewById(R.id.chk_publish);
	     chkSQOBO=(CheckBox) findViewById(R.id.chk_sqobo);
	     chkSGA=(CheckBox) findViewById(R.id.chk_show_given_answers);
	     chkSCA=(CheckBox) findViewById(R.id.chk_show_correct_answers);
	     chkSHUFA=(CheckBox) findViewById(R.id.chk_shuffle_answers);
	     chkSHUFQ=(CheckBox) findViewById(R.id.chk_shuffle_questions);
	     chkDOL=(CheckBox) findViewById(R.id.chk_display_ordered_list);
	     chkTCBP=(CheckBox) findViewById(R.id.chk_test_can_be_paused);
	     chkDWDT=(CheckBox) findViewById(R.id.chk_display_weights_during_test);
	     
	     
	     btnsave=(Button) findViewById(R.id.btn_save);
     
	     Bundle bundle = getIntent().getExtras(); 
	     lessonsid=bundle.getString("selectedlessonid");
	     lessonname=bundle.getStringArray("lessonnames");
	     testname=bundle.getStringArray("testnames");
	     
	     getcontent(lessonsid);
	     
	     
	     
	     stractive="1";
	     random_pool="0";
	     gen_threshold="50";
	    	     
	     
	     btnsave.setOnClickListener(new Button.OnClickListener() {        	 
	            public void onClick(View v) {
	            	
	            	getfields();
	            	
	            	checkvalidation();
	            	
	            	
	     			
	            	
	            	
	                }
	            });
	     
	     
	     
	    
	 }
	
	
	
	public void addtest(String active,String contid,String lessid,String name,String mast_score,String desc,String option1,String publish)
	
	 { 
		 try {
			 		 
			 	  
	      	 	SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
	    	 	
	    	 	
	    	 	request.addProperty("active1",active);  
	    	 	request.addProperty("contentid",contid);
	    	 	request.addProperty("lessonid",lessid);
	    	 	request.addProperty("name1",name);
	    	 	request.addProperty("masteryscore",mast_score);
	    	 	request.addProperty("desc",desc);
	    	 	request.addProperty("options1",option1);
	    	 	request.addProperty("publish1",publish);
	      	 	
	      	 	
	      	 	
	    	 		 			         		 
		 		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); 

		 		envelope.setOutputSoapObject(request);
		 		envelope.dotNet=true; 
		 		envelope.encodingStyle = SoapSerializationEnvelope.XSD; 
		 		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
		 	
		 	   androidHttpTransport.call(SOAP_ACTION, envelope);
		 	   
		 	  
		       
		 	   Toast.makeText(addtest_byprofessor.this, "Test Added succussfully",Toast.LENGTH_SHORT).show();
		       
		       
		 	      
		 } 
	        catch (Exception e)    
	        { 
	        
	        	System.out.println("ERROR:"+e.getMessage());
	        }  
	 }
	
	public void getcontent(String lessonids)
	 { 
		
		 try {
			 		  
			 	  
	    	 	SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME1);
		 		
		 		request.addProperty("lessonid",lessonids);  
		 		
		 			         		 
		 		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); 

		 		envelope.setOutputSoapObject(request); 
		 		envelope.dotNet=true; 
		 		envelope.encodingStyle = SoapSerializationEnvelope.XSD;
		 		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
		 	
		 		androidHttpTransport.call(SOAP_ACTION1, envelope);
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
		        
		        contentid=new int[temp2];
		        contentname=new String[temp2];   
		        
		        
		        for(int a=0,b=0;a<temp;a=a+2)
		        {
		        	contentid[b]=Integer.parseInt(arrauserData[a]);  
		        	b++;
	 	                	
		         }
		        
		        
		        for(int a=1,b=0;a<temp;a=a+2)
		        {
		        	contentname[b]=arrauserData[a];  
		        	b++;
	 	                	
		        }
		        
		        
		        ArrayAdapter<String> spnrcontent = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item);
		        
		        for(int x=0;x<temp2;x++) 
		        {
		         	        	 
		        	spnrcontent.add(contentname[x]);   
		        	System.out.println("Course is:"+contentname[x]);
		        } 
		         
		        spnrcontent.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		        sprPARENT_UNIT.setAdapter(spnrcontent);
		        sprPARENT_UNIT.setOnItemSelectedListener(new MyOnConentSelectedListener());
		        
		       
		  
		  
		        
		 	    }   
		 }
	 	    catch (Exception e)     
	        {
			 
			 System.out.println("Error msg:"+e.getMessage());
	        
	        }
	 }
	
	



	
	
	public class MyOnConentSelectedListener implements OnItemSelectedListener {

	    public void onItemSelected(AdapterView<?> parent,
	        View view, int pos, long id) {
	    	
	    	System.out.println("Position is:"+pos);
	        
	    	content=parent.getItemAtPosition(pos).toString();
	    	
	    	System.out.println("Content Units names are..... "+content);
	    	
	    	strcontent_id=Integer.toString(contentid[pos]);
	    	
	    	
	          
	       
	      
	    }
 
	    public void onNothingSelected(AdapterView parent) {
	      // Do nothing.
	    }


}
	
	
	
	public void getfields()
	{
		 //strpunit=sprPARENT_UNIT.getSelectedItem().toString();
    	 
    	 if((edtNAME.getText().toString()).equals(null)||(edtNAME.getText().toString()).equals("")||(edtNAME.getText().toString()).equals(" "))
    	 {
    		 
    	 }
    	 else
    	 {
    		 strname=edtNAME.getText().toString();
    		 
    		 
    	 }
    	 
    	 if((edtDIM.getText().toString()).equals(null)||(edtDIM.getText().toString()).equals("")||(edtDIM.getText().toString()).equals(" "))
    	 {
    		 strdur_min1="i:0";
    	 }
    	 else
    	 {
    		 strdur_min=edtDIM.getText().toString();
    		int min_to_sec=Integer.parseInt(strdur_min);
    		int converted_value=(min_to_sec*60);
    		strdur_min1="i:"+converted_value;
    		 
    	 }
    	 
    	 if((edtTR.getText().toString()).equals(null)||(edtTR.getText().toString()).equals("")||(edtTR.getText().toString()).equals(" "))
    	 {
    		 
    		 strtest_rep1="i:0";
    	 }
    	 else
    	 {
    		strtest_rep=edtTR.getText().toString();
    		int i2=strtest_rep.length();
    		strtest_rep1="s:"+i2+":"+strtest_rep;
    		 
    	 }
    	 
    	 
    	 if((edtMS.getText().toString()).equals(null)||(edtMS.getText().toString()).equals("")||(edtMS.getText().toString()).equals(" "))
    	 {
    		 
    		 strmastery_score="0";
    	 }
    	 else
    	 {
    		strmastery_score=edtMS.getText().toString();
    		   		 
    	 }
    	if(chkPUBLISH.isChecked())
    	{
    		 strpub="1";
    	}
    	else
    	{
    		 strpub="0";
    	}
    	
    	if(chkSQOBO.isChecked())
    	{
    		 strSQOBO="1";
    	}
    	else
    	{
    		 strSQOBO="0";
    	}
    	
    	if(chkSGA.isChecked())
    	{
    		 strSGA="1";
    	}
    	else
    	{
    		 strSGA="0";
    	}
    	    	   	
    	if(chkSCA.isChecked())
    	{
    		 strSCA="1";
    	}
    	else
    	{
    		 strSCA="0";
    	}
    	if(chkSHUFA.isChecked())
    	{
    		 strSHUFA="1";
    	}
    	else
    	{
    		 strSHUFA="0";
    	}
    	
    	if(chkSHUFQ.isChecked())
    	{
    		 strSHUFQ="1";
    	}
    	else
    	{
    		 strSHUFQ="0";  
    	}
    	if(chkDOL.isChecked())
    	{
    		 strDOL="1";
    	}
    	else
    	{
    		 strDOL="0";
    	}
    	if(chkTCBP.isChecked())
    	{  
    		 strTCBP="1";
    	}
    	else
    	{
    		 strTCBP="0";
    	}
    	if(chkDWDT.isChecked())
    	{
    		 strDWDT="1";
    	}
    	else
    	{
    		 strDWDT="0";
    	}
    	 
    	 if((edtDESC.getText().toString()).equals(null)||(edtDESC.getText().toString()).equals("")||(edtDESC.getText().toString()).equals("   "))
    	 {
    		 
    	 }
    	 else
    	 {
    		 strdes=edtDESC.getText().toString();
    		 
    		 
    	 }
    	 
	}
	
	public void checkvalidation()
	{
		if((edtNAME.getText().toString()).equals(null)||(edtNAME.getText().toString()).equals(""))
    	{
 				Toast.makeText(this, "Please Enter Test Name", Toast.LENGTH_LONG).show();
    	}
		
		
		else
		{
				
				option="a:12:{s:8:\"duration\";"+strdur_min1+";s:8:\"redoable\";"+strtest_rep1+";s:8:\"onebyone\";s:1:\""+strSQOBO+"\";s:13:\"given_answers\";s:1:\""+strSGA+"\";s:7:\"answers\";s:1:\""+strSCA+"\";s:17:\"shuffle_questions\";s:1:\""+strSHUFQ+"\";s:15:\"shuffle_answers\";s:1:\""+strSHUFA+"\";s:11:\"random_pool\";i:"+random_pool+";s:10:\"pause_test\";s:1:\""+strTCBP+"\";s:12:\"display_list\";s:1:\""+strDOL+"\";s:15:\"display_weights\";s:1:\""+strDWDT+"\";s:17:\"general_threshold\";i:"+gen_threshold+";}";
  	     
				System.out.println("String Format is:....."+option);
	

				addtest(stractive,""+strcontent_id,lessonsid,strname,strmastery_score,strdes,option,strpub);
				showquestionlist();
		}
	}
	/*
    type:function
    name:teacher
    parameter:no parameters
    returns:void
    date:29-06-2011
    purpose: Pass the values from LoginNew activity to teacherprofile activity and calls the teacherprofile activity
    */
	public void showquestionlist()
	 {
		 Intent i2 = new Intent(this,show_question_list.class);
		 Bundle bundle = new Bundle();
		 bundle.putStringArray("lessonnames",lessonname);
		 bundle.putStringArray("testnames",testname);
		 i2.putExtras(bundle);
		 startActivity(i2);
	 } 
	
	
}