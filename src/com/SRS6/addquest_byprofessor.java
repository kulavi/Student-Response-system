package com.SRS6;

//Add test by professor.


import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.SRS6.edit_questions_by_professor.MyOnConentSelectedListener;
import com.SRS6.reward.MyOnCourseSelectedListener;
import com.SRS6.reward.MyOnItemSelectedListenerutype;

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

public class addquest_byprofessor extends Activity {    
	 
	
	private static final String NAMESPACE = "http://tempuri.org/";      
	private static final String URL = "http://192.168.1.85/studentresponse/Service.asmx";
	
	
	private static final String SOAP_ACTION = "http://tempuri.org/InsertQuestion";      
	private static final String METHOD_NAME = "InsertQuestion";
	
	private static final String SOAP_ACTION1 = "http://tempuri.org/get_content";      
	private static final String METHOD_NAME1 = "get_content";    
	
	private static final String SOAP_ACTION2 = "http://tempuri.org/getlesson_id";      
	private static final String METHOD_NAME2 = "getlesson_id"; 
	
	
	
	DataBaseHelper data;
	
	TextView text1;
	Spinner sprLESSON,sprPARENT_UNIT,sprDIFFICULTY,sprCORRECTANS;
	Button btnsave,btnaddoption,btnremoveoption1,btnremoveoption2,btnremoveoption3,btnremoveoption4,btnremoveoption5;
	EditText edtEXPLANATION,edtQTEXT,edtCHOICE1,edtCHOICE2,edtCHOICE3,edtCHOICE4,edtCHOICE5;
	
	String strlesson,strparent_unit,strdifficulty,strcorrectans,strcorrectans1,strexplanation,strqtext;
	
	
	ArrayAdapter<String> spnrcorrectans;
	String[] contentname,lessonname;
	int[] contentid;
	int strcontentid,strlessonid;
	String content,lesson,strcontent_id;
	int temp7,flag,i=1,j=5;
	String strtype="multiple_one",stroptions,lessonsid;
	String strchoice1,strchoice2,strchoice3,strchoice4,strchoice5;
	String strchoice1A,strchoice2A,strchoice3A,strchoice4A,strchoice5A;
	
		
	
	
	
	
	public void onCreate(Bundle icicle)
	 {
		 requestWindowFeature(Window.FEATURE_NO_TITLE);  
	     getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
	     

	     super.onCreate(icicle);
	     setContentView(R.layout.addquest_byprofessor);
	     
	      
	     
	     Bundle bundle = getIntent().getExtras(); 
	     lessonname=bundle.getStringArray("lessonnames");
	     lessonsid=bundle.getString("selectlessonid");
	     
	          
	     
	    // sprLESSON=(Spinner) findViewById(R.id.spr_lesson);
	     sprPARENT_UNIT=(Spinner) findViewById(R.id.spr_parent_unit); 
	     sprDIFFICULTY=(Spinner) findViewById(R.id.spr_difficulty);
	     sprCORRECTANS=(Spinner) findViewById(R.id.spr_answer);
	     
	     edtQTEXT=(EditText) findViewById(R.id.edt_qtext);
	     edtEXPLANATION=(EditText) findViewById(R.id.edt_explanation); 
	     
	     edtCHOICE1=(EditText) findViewById(R.id.edt_choice1);
	     edtCHOICE2=(EditText) findViewById(R.id.edt_choice2);
	     edtCHOICE3=(EditText) findViewById(R.id.edt_choice3);
	     edtCHOICE4=(EditText) findViewById(R.id.edt_choice4);
	     edtCHOICE5=(EditText) findViewById(R.id.edt_choice5);
	     
	     btnsave=(Button) findViewById(R.id.btn_save);
	     btnaddoption=(Button) findViewById(R.id.btn_add);
	     
	     btnremoveoption1=(Button) findViewById(R.id.btn_remove1);
	     btnremoveoption2=(Button) findViewById(R.id.btn_remove2);
	     btnremoveoption3=(Button) findViewById(R.id.btn_remove3);
	     btnremoveoption4=(Button) findViewById(R.id.btn_remove4);
	     btnremoveoption5=(Button) findViewById(R.id.btn_remove5);
	     
	     edtCHOICE1.setVisibility(View.GONE);
	     edtCHOICE2.setVisibility(View.GONE);
	     edtCHOICE3.setVisibility(View.GONE);
	     edtCHOICE4.setVisibility(View.GONE);
	     edtCHOICE5.setVisibility(View.GONE);
	     
	     btnremoveoption1.setVisibility(View.GONE);
	     btnremoveoption2.setVisibility(View.GONE);
	     btnremoveoption3.setVisibility(View.GONE);
	     btnremoveoption4.setVisibility(View.GONE);
	     btnremoveoption5.setVisibility(View.GONE);
	     
	     
	     
	     spnrcorrectans = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item);
	     
	     
	     
	     ArrayAdapter<CharSequence> spnrdifficulty = ArrayAdapter.createFromResource(
	                this, R.array.DIFFICULTY, android.R.layout.simple_spinner_item);
	     spnrdifficulty.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	     sprDIFFICULTY.setAdapter(spnrdifficulty);
	         
	     
	     getcontent(lessonsid);
	     
	     btnsave.setOnClickListener(new Button.OnClickListener() {        	 
	            public void onClick(View v) {
	            	
	            	getfields();
	            	
	            	addquestion(strqtext,strtype,strcontent_id,lessonsid,strdifficulty,stroptions,strcorrectans1,strexplanation);
	            	
	            	 
	                }
	            });  
	     
	     btnaddoption.setOnClickListener(new Button.OnClickListener() {        	 
	            public void onClick(View v) {
	            	
	            		            	
	            	if(flag==0)
					{
				    edtCHOICE1.setVisibility(View.VISIBLE);
				    
				    btnremoveoption1.setVisibility(View.VISIBLE);
				    flag=1;
				    spnrcorrectans.add("1");   
				    spnrcorrectans.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				    sprCORRECTANS.setAdapter(spnrcorrectans);
				    sprCORRECTANS.setOnItemSelectedListener(new MyOnCorrectAnsSelectedListener());
					}
					else if(flag==1)
					{
						edtCHOICE2.setVisibility(View.VISIBLE);
						btnremoveoption1.setVisibility(View.GONE);
					    btnremoveoption2.setVisibility(View.VISIBLE);
					    flag=2;
					    spnrcorrectans.add("2");   
					    spnrcorrectans.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					    sprCORRECTANS.setAdapter(spnrcorrectans);
					    sprCORRECTANS.setOnItemSelectedListener(new MyOnCorrectAnsSelectedListener());
					   
					}
					else
					if(flag==2)
					{
						edtCHOICE3.setVisibility(View.VISIBLE);
						
						btnremoveoption2.setVisibility(View.GONE);
					    
					    btnremoveoption3.setVisibility(View.VISIBLE);
					    flag=3;
					    spnrcorrectans.add("3");   
					    spnrcorrectans.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					    sprCORRECTANS.setAdapter(spnrcorrectans);
					    sprCORRECTANS.setOnItemSelectedListener(new MyOnCorrectAnsSelectedListener());
					   
					}
					else
					if(flag==3)
					{
						edtCHOICE4.setVisibility(View.VISIBLE);
						
						btnremoveoption3.setVisibility(View.GONE);
					    
					    btnremoveoption4.setVisibility(View.VISIBLE);
					    flag=4;
					    spnrcorrectans.add("4");   
					    spnrcorrectans.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					    sprCORRECTANS.setAdapter(spnrcorrectans);
					    sprCORRECTANS.setOnItemSelectedListener(new MyOnCorrectAnsSelectedListener());
					    
				    }
					else
						if(flag==4)
						{
							edtCHOICE5.setVisibility(View.VISIBLE);
							
							btnremoveoption4.setVisibility(View.GONE);
						    
						    btnremoveoption5.setVisibility(View.VISIBLE);
						    flag=5;
						    spnrcorrectans.add("5");   
						    spnrcorrectans.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						    sprCORRECTANS.setAdapter(spnrcorrectans);
						    sprCORRECTANS.setOnItemSelectedListener(new MyOnCorrectAnsSelectedListener());
						    
					    }

	            	 
	                }
	            
	            });
	     
	     
	     btnremoveoption1.setOnClickListener(new Button.OnClickListener() {        	 
	            public void onClick(View v) {
	            	
	            	            	
	            	edtCHOICE1.setVisibility(View.GONE);
				    
				    btnremoveoption1.setVisibility(View.GONE);
				    flag=0;
				    
				   
				    
				    spnrcorrectans.remove("1");   
				    spnrcorrectans.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				    sprCORRECTANS.setAdapter(spnrcorrectans);
				    sprCORRECTANS.setOnItemSelectedListener(new MyOnCorrectAnsSelectedListener());
				    
	            	//System.out.println("remove1.."+i);
	            	 
	                }
	            });
	     btnremoveoption2.setOnClickListener(new Button.OnClickListener() {        	 
	            public void onClick(View v) {
	            	
	            	edtCHOICE2.setVisibility(View.GONE);
				    
				    btnremoveoption2.setVisibility(View.GONE);  
				    btnremoveoption1.setVisibility(View.VISIBLE); 
				    
				    flag=1;
				    
				    
				    
				    spnrcorrectans.remove("2");   
				    spnrcorrectans.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				    sprCORRECTANS.setAdapter(spnrcorrectans);
				    sprCORRECTANS.setOnItemSelectedListener(new MyOnCorrectAnsSelectedListener());
				   // System.out.println("remove2.."+i);
	                }
	            });
	     btnremoveoption3.setOnClickListener(new Button.OnClickListener() {        	 
	            public void onClick(View v) {
	            	
	            	edtCHOICE3.setVisibility(View.GONE);
				    
				    btnremoveoption3.setVisibility(View.GONE);
				    btnremoveoption2.setVisibility(View.VISIBLE);
				    
				    flag=2;
				    
				    
				    
				    spnrcorrectans.remove("3");   
				    spnrcorrectans.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				    sprCORRECTANS.setAdapter(spnrcorrectans);
				    sprCORRECTANS.setOnItemSelectedListener(new MyOnCorrectAnsSelectedListener());
				    //System.out.println("remove3.."+i);
	                }
	            });
	     btnremoveoption4.setOnClickListener(new Button.OnClickListener() {        	 
	            public void onClick(View v) {
	            	
	            	edtCHOICE4.setVisibility(View.GONE);
				    
				    btnremoveoption4.setVisibility(View.GONE);
				    btnremoveoption3.setVisibility(View.VISIBLE);
				    
				    flag=3; 
				   
				    
				    spnrcorrectans.remove("4");   
				    spnrcorrectans.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				    sprCORRECTANS.setAdapter(spnrcorrectans);
				    sprCORRECTANS.setOnItemSelectedListener(new MyOnCorrectAnsSelectedListener());
				    //System.out.println("remove4.."+i);
	                }
	            });
	     
	     btnremoveoption5.setOnClickListener(new Button.OnClickListener() {        	 
	            public void onClick(View v) {
	            	
	            	edtCHOICE5.setVisibility(View.GONE);
				    
				    btnremoveoption5.setVisibility(View.GONE);
				    btnremoveoption4.setVisibility(View.VISIBLE);
				    
				    flag=4;
				   
				   
				    
				    spnrcorrectans.remove("5");   
				    spnrcorrectans.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				    sprCORRECTANS.setAdapter(spnrcorrectans);
				    sprCORRECTANS.setOnItemSelectedListener(new MyOnCorrectAnsSelectedListener());
				    //System.out.println("remove5.."+i);
	                }
	            }); 
	     
	    
	 }
	
	
	
	public void addquestion(String Strtext,String Strtype,String Strcontent_id,String Strlesson_id,String Strdifficulty,String Stroptions,String Stranswer,String Strexplanation)
	
	 { 
		 try {
			 		 
			 	  
	      	 	SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
	    	 	
	    	 	
	    	 	request.addProperty("strtext",Strtext);  
	    	 	request.addProperty("strtype",Strtype);
	    	 	request.addProperty("strcontentid",Strcontent_id);
	    	 	request.addProperty("strlessonid",Strlesson_id);
	    	 	request.addProperty("strdifficulty",Strdifficulty);
	    	 	request.addProperty("stroptions",Stroptions);
	    	 	request.addProperty("stranswer",Stranswer);
	    	 	request.addProperty("strexplanation",Strexplanation);
	    	 	
	      	 	
	      	 		 	
	    	 		 			         		 
		 		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); 

		 		envelope.setOutputSoapObject(request);
		 		envelope.dotNet=true; 
		 		envelope.encodingStyle = SoapSerializationEnvelope.XSD; 
		 		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
		 	
		 	   androidHttpTransport.call(SOAP_ACTION, envelope);
		 	   
		 	   
		       
		 	   Toast.makeText(addquest_byprofessor.this, "Question Added succussfully",Toast.LENGTH_SHORT).show();
		       
		       
		 	      
		 } 
	        catch (Exception e)    
	        { 
	        //tv.setText(e.getMessage());
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
	
	
	public void getlessonid(String lname)
	 { 
		
		 try {
			 		  
			 	 
	    	 	SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME2);
		 		
		 		request.addProperty("lesson",lname); 
		 		
		 			         		 
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
		 		   
		        	/*final AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
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
	                alertbox.show();*/
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
		        strlessonid=Integer.parseInt(str1); 
		        
		        System.out.println("Lesson id is...."+strlessonid);
		        
		        
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
	    	
	    	System.out.println("2.Now the content id is::::"+strcontent_id);
	          
	       
	      
	    }
 
	    public void onNothingSelected(AdapterView parent) {
	      // Do nothing.
	    }


}
	
	public class MyOnCorrectAnsSelectedListener implements OnItemSelectedListener {

	    public void onItemSelected(AdapterView<?> parent,
	        View view, int pos, long id) {
	    	
	    	System.out.println("Position is:"+pos);
	        
	    	
	          
	       
	      
	    }
 
	    public void onNothingSelected(AdapterView parent) {
	      // Do nothing.
	    }


}
	
	/*public class MyOnLessonSelectedListener implements OnItemSelectedListener {

	    public void onItemSelected(AdapterView<?> parent,
	        View view, int pos, long id) {
	    	
	    	System.out.println("Position is:"+pos);
	        
	    	lesson=parent.getItemAtPosition(pos).toString();
	    	
	    	System.out.println("Lesson names are..... "+lesson);
	    	    	
	    	getlessonid(lesson);
	    	getcontent(strlessonid);
	          
	       
	      
	    }
 
	    public void onNothingSelected(AdapterView parent) {
	      // Do nothing.
	    }


}*/
	
	public void getfields()
	{
			
		//strlesson=sprLESSON.getSelectedItem().toString();
		strparent_unit=sprPARENT_UNIT.getSelectedItem().toString();
		strdifficulty=sprDIFFICULTY.getSelectedItem().toString();
		strcorrectans=sprCORRECTANS.getSelectedItem().toString();
		
		
		strcorrectans1="a:1:{i:0;s:1:\""+strcorrectans+"\";}";
		System.out.println("Answer is"+strcorrectans1);
		if(edtCHOICE1.equals("")||edtCHOICE1.equals(" ")||edtCHOICE1.equals(null))
		{
			strchoice1A="#";
		}
		else
		{
			strchoice1=edtCHOICE1.getText().toString();
			if(strchoice1.equals(""))
			{
				strchoice1A="#";
			}
			else
			{
			int l=strchoice1.length();
			strchoice1A="i:0"+";"+"s:"+l+":\""+strchoice1+"\"";
			}
		}
		
		if(edtCHOICE2.equals("")||edtCHOICE2.equals(" ")||edtCHOICE2.equals(null))
		{
			strchoice2A="#";
		}
		else
		{
			strchoice2=edtCHOICE2.getText().toString();
			if(strchoice2.equals(""))
			{
				strchoice2A="#";
			}
			else
			{
			int l=strchoice2.length();
			strchoice2A="i:1"+";"+"s:"+l+":\""+strchoice2+"\"";
			}
		}
		
		if(edtCHOICE3.equals("")||edtCHOICE3.equals(" ")||edtCHOICE3.equals(null))
		{
			strchoice3A="#";
		}
		else
		{
			strchoice3=edtCHOICE3.getText().toString();
			if(strchoice3.equals(""))
			{
				strchoice3A="#";
			}
			else
			{
			int l=strchoice3.length();
			strchoice3A="i:2"+";"+"s:"+l+":\""+strchoice3+"\"";
			}
		}
		if(edtCHOICE4.equals("")||edtCHOICE4.equals(" ")||edtCHOICE4.equals(null))
		{
			strchoice4A="#";
		}
		else
		{
			strchoice4=edtCHOICE4.getText().toString();
			if(strchoice4.equals(""))
			{
				strchoice4A="#";
			}
			else
			{
			int l=strchoice4.length();
			strchoice4A="i:3"+";"+"s:"+l+":\""+strchoice4+"\"";
			}
		}
		if(edtCHOICE5.equals("")||edtCHOICE5.equals(" ")||edtCHOICE5.equals(null))
		{
			strchoice5A="#";
		}
		else
		{
			strchoice5=edtCHOICE5.getText().toString();
			if(strchoice5.equals(""))
			{
				strchoice5A="#";
			}
			else
			{
			int l=strchoice5.length();
			strchoice5A="i:4"+";"+"s:"+l+":\""+strchoice5+"\"";
			}
		}
		int count=sprCORRECTANS.getCount();
				
		if(strchoice1A.equals("#"))
		{
			stroptions="a:"+count+":{"+":}";
		}
		else
		{
			
		if(strchoice2A.equals("#"))
		{
			stroptions="a:"+count+":{"+strchoice1A+";}";
		}
		else
		{			
		if(strchoice3A.equals("#"))
		{
			stroptions="a:"+count+":{"+strchoice1A+";"+strchoice2A+";}";
		}
		else
		{
		if(strchoice4A.equals("#")) 
		{
			stroptions="a:"+count+":{"+strchoice1A+";"+strchoice2A+";"+strchoice3A+";}";
		}
		else 
		{
		 if(strchoice5A.equals("#"))
		{
			stroptions="a:"+count+":{"+strchoice1A+";"+strchoice2A+";"+strchoice3A+";"+strchoice4A+";}";
		}
		else
		{
			stroptions="a:"+count+":{"+strchoice1A+";"+strchoice2A+";"+strchoice3A+";"+strchoice4A+";"+strchoice5A+";}";
		}
		}
		}
		}
		}
		
		strexplanation=edtEXPLANATION.getText().toString();
		strqtext=edtQTEXT.getText().toString();
		System.out.println("Option String is..."+stroptions);
		
	
	}
	
}