package com.SRS6;

//Edit Question by professor.



import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class edit_questions_by_professor extends Activity {      
	 
	
	private static final String NAMESPACE = "http://tempuri.org/";      
	private static final String URL = "http://192.168.1.85/studentresponse/Service.asmx";
	
	private static final String SOAP_ACTION = "http://tempuri.org/get_question_details";      
	private static final String METHOD_NAME = "get_question_details";    
	
	private static final String SOAP_ACTION1 = "http://tempuri.org/get_content";      
	private static final String METHOD_NAME1 = "get_content";    
	
	private static final String SOAP_ACTION2 = "http://tempuri.org/getlesson_id";      
	private static final String METHOD_NAME2 = "getlesson_id"; 
	
	private static final String SOAP_ACTION3 = "http://tempuri.org/UpdateQuestions";      
	private static final String METHOD_NAME3 = "UpdateQuestions";
	
	DataBaseHelper data;
	int pos;
	String op1,op2,op3,op4,op5,op6; 
	ArrayAdapter spnrdifficulty;
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
	int temp7,flag,i=1,intoptions21;
	String strtype="multiple_one",stroptions,lessonsid;
	String strchoice1,strchoice2,strchoice3,strchoice4,strchoice5;
	String strchoice1A,strchoice2A,strchoice3A,strchoice4A,strchoice5A;
	
		
	String strqid1,strqtext1,strtype1,strcontentid1,strlessonsid1,strdifficulty1,stroptions1,stranswer1,strexplanation1;
    
    String id;
    String[] stroptions2;
    
    String  stroptions21,stroptions22;
	String[] split_option;
	String[] intloop;
	String[] st2_split;
	String[] split_option1;
	String[] split_option2;
	String split_option3;
	Object a;
	Object[] st2;
	int pos_diff;
	int pos_cor_ans;
	
	
	
	public void onCreate(Bundle icicle)
	 {
		 requestWindowFeature(Window.FEATURE_NO_TITLE);  
	     getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
	     

	     super.onCreate(icicle);
	     setContentView(R.layout.addquest_byprofessor);
	    
		 Bundle bundle = getIntent().getExtras(); 
	     lessonname=bundle.getStringArray("lessonnames");
	     lessonsid=bundle.getString("selectlessonid");
	     id=bundle.getString("qid");
	 	 System.out.println("Id will" +id);
	     
	    	    
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
	     
	         
	     spnrcorrectans = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item);
	     
	    
	     
	     spnrdifficulty = ArrayAdapter.createFromResource(this, R.array.DIFFICULTY, android.R.layout.simple_spinner_item);
	     spnrdifficulty.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	     sprDIFFICULTY.setAdapter(spnrdifficulty);
	     spnrdifficulty = (ArrayAdapter)sprDIFFICULTY.getAdapter();
	  
	     
	     getcontent(lessonsid);
	     
	     getquestion_details(id);
	          
	     
	       
	     btnsave.setOnClickListener(new Button.OnClickListener() {        	 
	            public void onClick(View v) {
	            	
	            	getfields();
	            	
	            	updatequestion(strqtext,strtype,strcontent_id,strdifficulty,stroptions,strcorrectans1,strexplanation,strqid1);
	            	
	            	  
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
		
	                }
	            }); 
	       
	    
	 }
	public void updatequestion(String Strtext,String Strtype,String Strcontent_id,String Strdifficulty,String Stroptions,String Stranswer,String Strexplanation,String Strqid)
	
	 { 
		 try {
			 		 
			 	  
	      	 	SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME3);
	    	 	
	    	 	
	    	 	request.addProperty("strtext",Strtext);  
	    	 	request.addProperty("strtype",Strtype);
	    	 	request.addProperty("strcontent_id",Strcontent_id);
	    	 	request.addProperty("strdifficulty",Strdifficulty);
	    	 	request.addProperty("stroption",Stroptions);
	    	 	request.addProperty("stranswer",Stranswer);
	    	 	request.addProperty("strexplanation",Strexplanation);
	    	 	request.addProperty("strid",Strqid);     
	    	 	
	      	 	
	      	 		 	  
	    	 		 			         		 
		 		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); 

		 		envelope.setOutputSoapObject(request);
		 		envelope.dotNet=true; 
		 		envelope.encodingStyle = SoapSerializationEnvelope.XSD; 
		 		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
		 	
		 	   androidHttpTransport.call(SOAP_ACTION3, envelope);
		 	   
		 	   
		       
		 	   Toast.makeText(this, "Question Updated succussfully",Toast.LENGTH_SHORT).show();
		       
		       
		 	      
		 } 
	        catch (Exception e)    
	        { 
	        //tv.setText(e.getMessage());
	        	System.out.println("ERROR:"+e.getMessage());
	        }  
	 }
	public void getquestion_details(String questid)
	 { 
		
		 try {
			 		  
			 	  
	    	 	SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		 		
		 		request.addProperty("qid",questid);  
		 		
		 			         		 
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
		
		        
		        strqid1=arrauserData[0];
		        strqtext1=arrauserData[1];
		        strtype1=arrauserData[2];
		        strcontentid1=arrauserData[3];
		        strlessonsid1=arrauserData[4];
		        strdifficulty1=arrauserData[5];
		        stroptions1=arrauserData[6];
		        stranswer1=arrauserData[7];
		        strexplanation1=arrauserData[8];
		        
		        System.out.println("1 "+strqtext1);
		        System.out.println("2 "+ strtype1);
		        System.out.println("3 "+strcontentid1);
		        System.out.println("4 "+strdifficulty1);
		        System.out.println("5 "+strexplanation1);
		        System.out.println("6 "+stranswer1);
		        System.out.println("67"+stroptions1);
		        
		        
		        if(strexplanation1.contains(";"))
		        {
		        	strexplanation1=strexplanation1.replace(";", "");
		        }
		        
		        
		        
		        stranswer1=stranswer1.substring(stranswer1.indexOf("\""), stranswer1.lastIndexOf("\""));
		        if(stranswer1.contains("\""))
		        {
		        	stranswer1=stranswer1.replace("\"", "");
		        
		        }
		        if(stranswer1.contains(" "))
		        {
		        
		        	stranswer1=stranswer1.replace(" ", "");
		        }
		        System.out.println("Now the answer is::::::"+stranswer1);
		        
		        
		        
		        edtQTEXT.setText(strqtext1);
		        edtEXPLANATION.setText( strexplanation1);
		        
		        
		        
		        for(int x=0;x<contentid.length;x++)
		        {
		        	
		        	System.out.println("Content id are:"+contentid[x]);
		        	if(contentid[x]==Integer.parseInt(strcontentid1))
		        	{
		        		pos=x;
		        		System.out.println("Selected Content id is:"+contentid[x]);
		        		System.out.println("Correct Answer Position will be "+pos);
		        	}
		        }
		        
		        sprPARENT_UNIT.setSelection(pos,true);
		      
		        pos_diff=spnrdifficulty.getPosition(strdifficulty1);
		        System.out.println("Difficulty Position will be "+pos_diff);
		        sprDIFFICULTY.setSelection(pos_diff,true);
		        
		            
		      
		        	 
		        		int j=1;
		        		split_option=stroptions1.split("\\{");
		        		intloop=split_option[0].split(":");
		        		st2_split=new String[Integer.parseInt(intloop[1])];
		        		int k=Integer.parseInt(intloop[1]);
		        		System.out.println("Value is "+k);
		        		for(int n=0;n<Integer.parseInt(intloop[1]);n++)
		         		{
		        			split_option1=split_option[1].split(";s:"); 
		        			split_option2=split_option1[j].split(":");  
		        			split_option3=split_option2[1].substring(1, Integer.parseInt(split_option2[0])+1);
		        			st2_split[n]=split_option3;
		        			
		        			j=j+1;
		        				        			  
		        		}
		        			        		       		    
		        		
		        		int m=0;  
		        			
		        			if(k==1)
			        		{
		        				flag=1;
		        				
		        				edtCHOICE1.setText(""+st2_split[m]);
		        				
			        			edtCHOICE1.setVisibility(View.VISIBLE);
			        			
			        			btnremoveoption1.setVisibility(View.VISIBLE);
			        			
			        			
			        			System.out.println("OPtions will be 0 "+st2_split[m]);
			        			
			        			m=1;
			        			
			        			spnrcorrectans.add(""+m);
			        			    
			        			
			        		}
		        			else if(k==2)
			        		{
			        			flag=2;
			        			
			        			edtCHOICE1.setText(""+st2_split[m]);
			        			edtCHOICE2.setText(""+st2_split[m+1]);
			        			
			        			edtCHOICE1.setVisibility(View.VISIBLE);
			        			edtCHOICE2.setVisibility(View.VISIBLE);
			        			
			        			btnremoveoption1.setVisibility(View.VISIBLE);
			        			btnremoveoption2.setVisibility(View.VISIBLE);
			        			
			        			System.out.println("OPtions will be 0 "+st2_split[m]);
				        		System.out.println("OPtions will be 1 "+st2_split[m+1]);
			        			
			        			m=1;
			        			
			        			spnrcorrectans.add(""+m);
			        			spnrcorrectans.add(""+(m+1));
			        			 
			        			
			        		}
			        		else if(k==3)
			        		{
			        			flag=3;
			        			
			        			edtCHOICE1.setText(""+st2_split[m]);
			        			edtCHOICE2.setText(""+st2_split[m+1]);
			        			edtCHOICE3.setText(""+st2_split[m+2]);
			        			
			        			edtCHOICE1.setVisibility(View.VISIBLE);
			        			edtCHOICE2.setVisibility(View.VISIBLE);
			        			edtCHOICE3.setVisibility(View.VISIBLE);
			        			
			        			btnremoveoption1.setVisibility(View.VISIBLE);
			        			btnremoveoption2.setVisibility(View.VISIBLE);
			        			btnremoveoption3.setVisibility(View.VISIBLE);
			        			
			        			System.out.println("OPtions will be 0 "+st2_split[m]);
				        		System.out.println("OPtions will be 1 "+st2_split[m+1]);
				        		System.out.println("OPtions will be 2 "+st2_split[m+2]);
			        			
			        			m=1;
			        			
			        			spnrcorrectans.add(""+m);
			        			spnrcorrectans.add(""+(m+1));
			        			spnrcorrectans.add(""+(m+2));
			        			 
			        			
			        		}
			        		else if(k==4)   
			        		{
			        			flag=4;
			        			
			        			edtCHOICE1.setText(""+st2_split[m]);
			        			edtCHOICE2.setText(""+st2_split[m+1]);
			        			edtCHOICE3.setText(""+st2_split[m+2]);
			        			edtCHOICE4.setText(""+st2_split[m+3]);
			        			
			        			edtCHOICE1.setVisibility(View.VISIBLE);
			        			edtCHOICE2.setVisibility(View.VISIBLE);
			        			edtCHOICE3.setVisibility(View.VISIBLE);
			        			edtCHOICE4.setVisibility(View.VISIBLE);
			        			
			        			btnremoveoption1.setVisibility(View.VISIBLE);
			        			btnremoveoption2.setVisibility(View.VISIBLE);
			        			
			        			
			        			btnremoveoption3.setVisibility(View.VISIBLE);
			        			btnremoveoption4.setVisibility(View.VISIBLE);
			        			
			        			System.out.println("OPtions will be 0 "+st2_split[m]);
				        		System.out.println("OPtions will be 1 "+st2_split[m+1]);
				        		System.out.println("OPtions will be 2 "+st2_split[m+2]);
				        		System.out.println("OPtions will be 3 "+st2_split[m+3]);
			        			
			        			m=1;
			        			
			        			spnrcorrectans.add(""+m);
			        			spnrcorrectans.add(""+(m+1));
			        			spnrcorrectans.add(""+(m+2));
			        			spnrcorrectans.add(""+(m+3));
			        			 
			        			
			        		}
			        		else if(k==5)
			        		{
			        			flag=5;
			        			
			        			edtCHOICE1.setText(""+st2_split[m]);
			        			edtCHOICE2.setText(""+st2_split[m+1]);
			        			edtCHOICE3.setText(""+st2_split[m+2]);
			        			edtCHOICE4.setText(""+st2_split[m+3]);
			        			edtCHOICE5.setText(""+st2_split[m+4]);
			        			
			        			
			        			edtCHOICE1.setVisibility(View.VISIBLE);
			        			edtCHOICE2.setVisibility(View.VISIBLE);
			        			edtCHOICE3.setVisibility(View.VISIBLE);
			        			edtCHOICE4.setVisibility(View.VISIBLE);
			        			edtCHOICE5.setVisibility(View.VISIBLE);
			        			
			        			btnremoveoption1.setVisibility(View.VISIBLE);
			        			btnremoveoption2.setVisibility(View.VISIBLE);
			        			btnremoveoption3.setVisibility(View.VISIBLE);
			        			btnremoveoption4.setVisibility(View.VISIBLE);
			        			btnremoveoption5.setVisibility(View.VISIBLE);
			        			
			        			System.out.println("OPtions will be 0 "+st2_split[m]);
				        		System.out.println("OPtions will be 1 "+st2_split[m+1]);
				        		System.out.println("OPtions will be 2 "+st2_split[m+2]);
				        		System.out.println("OPtions will be 3 "+st2_split[m+3]);
				        		System.out.println("OPtions will be 4 "+st2_split[m+4]);
			        			
			        			m=1;
			        			
			        			spnrcorrectans.add(""+m);
			        			spnrcorrectans.add(""+(m+1));
			        			spnrcorrectans.add(""+(m+2));
			        			spnrcorrectans.add(""+(m+3));
			        			spnrcorrectans.add(""+(m+4));
			        					        				        			
			        			
			        		}
		        			  
		        			
		        			
		        		}
		 	    
		 	    
		 	    
		 	    
		 	  sprCORRECTANS.setAdapter(spnrcorrectans);
		 	   
		 	   
		 	  pos_cor_ans=spnrcorrectans.getPosition(""+stranswer1);
		      System.out.println("Correct Answer Position will be "+pos_cor_ans);
		      sprCORRECTANS.setSelection(pos_cor_ans,true);    		
		        		
		        	
		       
		           
		 }
	 	    catch (Exception e)     
	        {
			 
			 System.out.println("Error msg:"+e.getMessage());
	        
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
	
	
	
	public void getfields()
	{
			
		
		
		
		
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