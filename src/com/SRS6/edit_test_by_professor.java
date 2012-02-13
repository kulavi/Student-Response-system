//Add test by professor.

package com.SRS6;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

public class edit_test_by_professor extends Activity {    
	 
	
	private static final String NAMESPACE = "http://tempuri.org/";      
	private static final String URL = "http://192.168.1.85/studentresponse/Service.asmx";
	  
	
	private static final String SOAP_ACTION = "http://tempuri.org/UpdateTest";      
	private static final String METHOD_NAME = "UpdateTest";
	
	private static final String SOAP_ACTION1 = "http://tempuri.org/get_content";      
	private static final String METHOD_NAME1 = "get_content";    
	
	private static final String SOAP_ACTION2 = "http://tempuri.org/get_test_details";      
	private static final String METHOD_NAME2= "get_test_details";    
	
	
	
	
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
	int tid;
	private String strtid;
	private String strcontent_ids;
	private String strtname;
	private String strms;
	private String strdesc;
	private String stroptions;
	private String strpublish;
	private int pos;
	private String[] split_option;
	private String[] intloop;
	private String[] split_option1;
	private String[] split_option2;
	
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
	     testname=bundle.getStringArray("testnames");
	     tid=bundle.getInt("testid",0);
	     
	     
	     getcontent(lessonsid);
	     
	     get_test_details(""+tid);
	     
	     
	     
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
	  
	
	    
	public void updatetest(String contid,String name,String mast_score,String desc,String option1,String publish,String testid)
	
	 { 
		 try {
			 		 
			 	  
	      	 	SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
	    	 	
	    	 	
	    	 	
	    	 	request.addProperty("strcontent_id",contid);
	    	 	request.addProperty("strname",name);
	    	 	request.addProperty("strms",mast_score);
	    	 	request.addProperty("strdesc",desc);
	    	 	request.addProperty("stropt",option1);
	    	 	request.addProperty("strpub",publish);
	    	 	request.addProperty("strid",testid);
	    	 	
	      	 	
	      	 	
	      	 	
	    	 		 			         		 
		 		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); 

		 		envelope.setOutputSoapObject(request);
		 		envelope.dotNet=true; 
		 		envelope.encodingStyle = SoapSerializationEnvelope.XSD; 
		 		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
		 	
		 	   androidHttpTransport.call(SOAP_ACTION, envelope);
		 	   
		 	  
		       
		 	   Toast.makeText(edit_test_by_professor.this, "Test Updated succussfully",Toast.LENGTH_SHORT).show();
		       
		       
		 	      
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
		        	if(contentname[b].contains(";"))
		        	{
		        		contentname[b]=contentname[b].replace(";", "");
		        	}
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
	
	

	public void get_test_details(String strid)
	 { 
		
		 try {
			 		  
			 	  
	    	 	SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME2);
		 		
		 		request.addProperty("tid",strid);  
		 		
		 			         		 
		 		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); 

		 		envelope.setOutputSoapObject(request); 
		 		envelope.dotNet=true; 
		 		envelope.encodingStyle = SoapSerializationEnvelope.XSD;
		 		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
		 	
		 		androidHttpTransport.call(SOAP_ACTION2, envelope);
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
		        
		        
		        strtid=arrauserData[0];
		        strcontent_ids=arrauserData[1];
		        strtname=arrauserData[3];
		        strms=arrauserData[4];
		        strdesc=arrauserData[5];
		        stroptions=arrauserData[6];
		        strpublish=arrauserData[7];
		        
		        
		        if(strpublish.contains(";"))
		        {
		        	strpublish=strpublish.replace(";", "");
		        }
		        
		        System.out.println("1 "+strtid);
		        System.out.println("2 "+ strcontent_ids);
		        System.out.println("3 "+strtname);
		        System.out.println("4 "+strms);
		        System.out.println("5 "+strdesc);
		        System.out.println("6 "+stroptions);
		        System.out.println("7"+strpublish);
		        
		        		        
		        edtNAME.setText(strtname);
		        edtMS.setText(strms);
		        edtDESC.setText(strdesc);
		        
		        
		        
		        
		        for(int x=0;x<contentid.length;x++)
		        {
		        	
		        	System.out.println("Content id are:"+contentid[x]);
		        	if(contentid[x]==Integer.parseInt(strcontent_ids))
		        	{
		        		pos=x;
		        		System.out.println("Selected Content id is:"+contentid[x]);
		        		System.out.println("Correct Answer Position will be "+pos);
		        	}
		        }
		        
		        sprPARENT_UNIT.setSelection(pos,true);
		      
		          
		      
		        	 
		        		
		        		split_option=stroptions.split("\\{");  
		        		intloop=split_option[0].split(":");
		        		
		        		
		        		split_option1=split_option[1].split(";");
		        		for(int n=1;n<Integer.parseInt(intloop[1]);)
		         		{
		        			 			
		        			if(split_option1[n].contains("i:"))
		        			{
		        			split_option1[n]=split_option1[n].replace("i:","");
		        			}
		        			if(split_option1[n].contains("s:"))
		        			{
		        			split_option2=split_option1[n].split(":");
		        			
		        			if(split_option2[1].length()>1)
		        			{
		        				split_option1[n]=split_option2[1].substring(1, Integer.parseInt(split_option2[1]));
		        			}
		        			else
		        			{
		        			split_option1[n]=split_option2[1].substring(0, Integer.parseInt(split_option2[1]));
		        			}
		        			}
		        			  
		        			n=n+2;
		        						
		        				        			  
		        		}
		        		int sec_to_min=Integer.parseInt(split_option1[1]);
		        		int converted_value=(sec_to_min/60);
		        		
		        		edtDIM.setText(converted_value);
		        		edtTR.setText(split_option1[3]);
		        		
		        		if(split_option1[5].equals("1"))
		        		{
		        			chkSQOBO.setChecked(true);
		        		}
		        		if(split_option1[7].equals("1"))
		        		{
		        			chkSGA.setChecked(true);
		        		}
		        		if(split_option1[9].equals("1"))
		        		{
		        			chkSCA.setChecked(true);
		        		}
		        		if(split_option1[11].equals("1"))
		        		{
		        			chkSHUFA.setChecked(true);
		        		}
		        		if(split_option1[13].equals("1"))
		        		{
		        			chkSHUFQ.setChecked(true);
		        		}
		        		if(split_option1[15].equals("1"))
		        		{
		        			chkDOL.setChecked(true);
		        		}
		        		if(split_option1[17].equals("1"))
		        		{
		        			chkTCBP.setChecked(true);
		        		}
		        		if(split_option1[19].equals("1"))
		        		{
		        			chkDWDT.setChecked(true);
		        		}
		        
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
	

				updatetest(""+strcontent_id,strname,strmastery_score,strdes,option,strpub,""+tid);
				//showquestionlist();
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