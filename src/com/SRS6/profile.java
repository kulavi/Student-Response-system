//Profile page of the user containing the brief information about that user

package com.SRS6;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class profile extends Activity implements OnItemClickListener {
	String stunm,str1;
	TextView welstu,text1,tv;
	ImageView stupic;
	String[] coursenm,lessonnm,testnm,stuname,proname,firstname,lastname,pronm;
	DataBaseHelper  data;
	ListView lv1;
	ImageView img; 
	TextView review,mailbox,reward;
	String s1,test,courses,fullname,name,lessons,tests,type;  
	int qid,qcount,qid1;
	TableLayout tl,tl1,tl2,tl3,tl4,mailtl,rewardtl,reqtl;
	int i1,temp3,count,tcount;
	int[] strquizno;
	String[] strquizname;
	Dialog myDialog;
	/*private ArrayList<String> res=new ArrayList<String>();
	private ArrayList<String> res1=new ArrayList<String>(); */
	
	String[] course1,lesson1,test1;
	
	private static final String NAMESPACE = "http://tempuri.org/";      
	private static final String URL = "http://192.168.1.85/studentresponse/Service.asmx"; 
	
	private static final String SOAP_ACTION = "http://tempuri.org/user_profile";      
	private static final String METHOD_NAME = "user_profile";      
		
	private static final String SOAP_ACTION1 = "http://tempuri.org/gettest";  
	private static final String METHOD_NAME1 = "gettest";  
	
	private static final String SOAP_ACTION2 = "http://tempuri.org/user_Name";  
	private static final String METHOD_NAME2 = "user_Name";  
	
	private static final String SOAP_ACTION3 = "http://tempuri.org/LessonUser_Name";  
	private static final String METHOD_NAME3 = "LessonUser_Name";  
	
	private static final String SOAP_ACTION4 = "http://tempuri.org/getquestions";      
	private static final String METHOD_NAME4 = "getquestions"; 
	 
	
	public void onCreate(Bundle icicle)
	 {
		 requestWindowFeature(Window.FEATURE_NO_TITLE);  
	     getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
	     

	     super.onCreate(icicle);
	 setContentView(R.layout.profiles);

  	img = (ImageView) findViewById(R.id.image1);
  	Bitmap bitmap =DownloadImage("http://bpsius:Bpsi%40123@bpsi.us/propic.JPG");
  	img.setImageBitmap(bitmap);	
  	
	 
	
    
             
	 
	 data=new DataBaseHelper(this);
	 
	 welstu=(TextView)findViewById(R.id.welcome);
	 
	 /*review=(TextView)findViewById(R.id.reviews);
	 
	 mailbox=(TextView)findViewById(R.id.mailbox);*/
	 
	
	 
	 
	 tl=(TableLayout)findViewById(R.id.tbl1);
     //tl.setBackgroundResource(R.layout.shapes);
     
     tl1=(TableLayout)findViewById(R.id.tbl2);
     //tl1.setBackgroundResource(R.layout.shapes);
     
     tl2=(TableLayout)findViewById(R.id.tbl3);
     //tl2.setBackgroundResource(R.layout.shapes);
     
     tl4=(TableLayout)findViewById(R.id.tbl4);
     //tl4.setBackgroundResource(R.layout.shapes);
          
    	 
	 Bundle bundle = getIntent().getExtras(); 
	 s1=bundle.getString("wel");
	 stunm=bundle.getString("welcome");
	 type=bundle.getString("type");
	 
	 
	 
	 /*review.setMovementMethod(LinkMovementMethod.getInstance()); 
	 
	 review.setOnClickListener(new TextView.OnClickListener() 
		{ public void onClick (View v)
			{ 
				
				funreview();
			}
		
     
		});
	 
	 mailbox.setMovementMethod(LinkMovementMethod.getInstance());
	 
	 mailbox.setOnClickListener(new TextView.OnClickListener() 
		{ public void onClick (View v)
			{ 
				
				funmailbox();
			}
		
     
		});*/
	 
 
 	 welstu.setText(s1); 
	 data.deleteprofile();
	 call1();
	 call(); 
	 
	
	 }
	
	/*
    type:function
    name:DownloadImage
    returns:Bitmap
    parameters:url
    date:29-06-2011
    purpose:downloads an image from url.
    */	
	private Bitmap DownloadImage(String URL)
    {        
        Bitmap bitmap = null;
        InputStream in = null;        
        try {
            in = OpenHttpConnection(URL);
            bitmap = BitmapFactory.decodeStream(in);
            in.close();
        } catch (IOException e1) {
            
            e1.printStackTrace();
        }
        return bitmap;                
    }
	/*
    type:function
    name:OpenHttpConnection
    returns:InputStream
    parameters:url
    date:29-06-2011
    purpose:Creates and opens the http connection to download the profile picture from the url.
    */
    private InputStream OpenHttpConnection(String urlString) 
    throws IOException
    {
        InputStream in = null;
        int response = -1;
               
        URL url = new URL(urlString); 
        URLConnection conn = url.openConnection();
                 
        if (!(conn instanceof HttpURLConnection))                     
            throw new IOException("Not an HTTP connection");
        
        try{
            HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.connect(); 

            response = httpConn.getResponseCode();                 
            if (response == HttpURLConnection.HTTP_OK) {
                in = httpConn.getInputStream();                                 
            }                     
        }
        catch (Exception ex)
        {
        	
            throw new IOException("Error connecting");            
        }
        return in;     
    }
    
    //Call to the web services starts 
    
    /*
    type:function
    name:call
    returns:void
    parameters:no parameter
    date:29-06-2011
    purpose:retrieves the course name ,lesson name ,test name from datbase server
    */
	   
	 public void call()
	 { 
		
		 try {
			 		  
			 	 //data.deleteprofile(); 
 	    	 	SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		 		
		 		request.addProperty("struserid",stunm);
		 		
		 			         		 
 		 		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); 

		 		envelope.setOutputSoapObject(request); 
		 		envelope.dotNet=true; 
		 		envelope.encodingStyle = SoapSerializationEnvelope.XSD;
		 		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
		 	
		 		androidHttpTransport.call(SOAP_ACTION, envelope);
		    	 Object result = (Object)envelope.getResponse();
		 	      str1=result.toString(); 
		 	      
		 	     System.out.println("Data:"+str1);
		 	    if(str1.equals(null)||str1.equals("anyType{}"))  
		        {
		 		   tl1.setVisibility(View.GONE);
		 		   tl2.setVisibility(View.GONE);
		        	final AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
		        	alertbox.setMessage(stunm+" "+"you are yet not registered to any courses...you need to register first.... ");
	    		     
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
		         
		        int temp2=arrauserData.length;
		        int temp1=((arrauserData.length)/4);   
		        stuname=new String[temp1]; 
		        coursenm=new String[temp1];
		        lessonnm=new String[temp1];
		        testnm=new String[temp1];
		        
		        for(int a=0,b=0;a<temp2;a=a+4)
		        {
		        	stuname[b]=arrauserData[a];  
		        	b++;
	 	                	
		         }
		        
		         for(int a=1,b=0;a<temp2+1;a=a+4)
		        {
		        	coursenm[b]=arrauserData[a];
		        	b++;
		                	 
		        }
		         
		        for(int c=2,d=0;c<temp2;c=c+4) 
		        {
		        	 
		        lessonnm[d]=arrauserData[c];
		        
		        d++;
		        }
		        for(int c=3,d=0;c<temp2;c=c+4)
		        {
		        	
		        testnm[d]=arrauserData[c];
		        
		        d++;
		        }
		        
		        for(int x=0;x<temp1;x++) 
		        {
		         
                	data.Insertprofile(stuname[x],coursenm[x],lessonnm[x],testnm[x]);
                	
     
		        }
		        getcourse();
		        
		 	    }   
		 }
	 	    catch (Exception e)    
	        {
			 //text1.setText(e.getMessage()); 
			 System.out.println("Error msg:"+e.getMessage());
	        
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
		public void call1()
		{
		        try { 

	 	        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME1);

		        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		        envelope.dotNet=true;
		        envelope.setOutputSoapObject(request);

		        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
		        androidHttpTransport.call(SOAP_ACTION1, envelope);

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
		        strquizno=new int[temp3];
		        strquizname=new String[temp3];
			        System.out.println("Length:"+temp3);
			        for(int c=1,d=0;c<temp2;c=c+2)
			        {
			        	
		 	        strquizname[d]=quizdata[c];
			         
			        d++;
			        }
			        for(int a=0,b=0;a<temp2;a=a+2)
			        {
			        	strquizno[b]=Integer.parseInt(quizdata[a]);
			        	b++;
			                	
			        }		        		           
			        for(int x=0;x<temp3;x++)
			        {
			        		        
			      	data.Inserttest(strquizno[x], strquizname[x]);	
			        		        		         
			        }
			       
			        
		      
		    }
		        catch (Exception e) {
		        tv.setText(e.getMessage());
		        }
		}
		
		/*
	    type:function
	    name:callprofessor
	    returns:void
	    parameters:no parameter
	    date:29-06-2011
	    purpose:Retrieves the name and surname of the professor from server for a specific course
	    */
		
		public void callprofessor()
		 { 
			
			 try {
				 		  
				 	 //data.deleteprofile(); 
		    	 	SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME2);
			 		
			 		request.addProperty("Name",courses);
			 		
			 			         		 
			 		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); 

			 		envelope.setOutputSoapObject(request); 
			 		envelope.dotNet=true; 
			 		envelope.encodingStyle = SoapSerializationEnvelope.XSD;
			 		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
			 	
			 		androidHttpTransport.call(SOAP_ACTION2, envelope);
			    	 Object result = (Object)envelope.getResponse();
			 	      str1=result.toString(); 
			 	      
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
			        String[] arrauserData;
			        arrauserData = ((String) str1).split("; ");
			         
			        int temp2=arrauserData.length;
			        int temp1=((arrauserData.length)/2);
			        firstname=new String[temp1]; 
			        lastname=new String[temp1];
			        
			        
			        
			         
			       // System.out.println("Length:"+temp1);
			         
			        for(int a=0,b=0;a<temp2;a=a+2)
			        {
			        	firstname[b]=arrauserData[a];
			        	b++;
		 	                	
			         }
			        
			         for(int a=1,b=0;a<temp2+1;a=a+2)
			        {
			        	lastname[b]=arrauserData[a];
			        	b++;
			                	 
			        }
			         
			         int temp3=firstname.length;
			         proname=new String[temp3];
			        	        
			        for(int x=0;x<temp1;x++) 
			        {
			        	
			         proname[x]=firstname[x]+" "+lastname[x];
	               	data.Insertprofessorname(proname[x]);
	               	   
			        }
			       
			        getprofessorname();
			        showcoursedetail();
			         
			 	    }   
			 }
		 	    catch (Exception e)    
		        {
				 //text1.setText(e.getMessage()); 
				 System.out.println("Error msg:"+e.getMessage());
		        
		        }
		 }
		
		/*
	    type:function
	    name:callprofessorlesson
	    returns:void
	    parameters:no parameter
	    date:29-06-2011
	    purpose:Retrieves the name and surname of the professor from server for a specific lesson
	    */
		
		public void callprofessorlesson()
		 { 
			
			 try {
				 		  
				 	 //data.deleteprofile(); 
		    	 	SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME3);
			 		
			 		request.addProperty("LName",lessons);
			 		
			 			         		  
			 		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); 

			 		envelope.setOutputSoapObject(request); 
			 		envelope.dotNet=true; 
			 		envelope.encodingStyle = SoapSerializationEnvelope.XSD;
			 		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
			 	
			 		androidHttpTransport.call(SOAP_ACTION3, envelope);
			    	 Object result = (Object)envelope.getResponse();
			 	      str1=result.toString(); 
			 	      
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
			        String[] arrauserData;
			        arrauserData = ((String) str1).split("; ");
			         
			        int temp2=arrauserData.length;
			        int temp1=((arrauserData.length)/2);
			        firstname=new String[temp1]; 
			        lastname=new String[temp1];
			        
			        
			        
			         
			       // System.out.println("Length:"+temp1);
			         
			        for(int a=0,b=0;a<temp2;a=a+2)
			        {
			        	firstname[b]=arrauserData[a];
			        	b++;
		 	                	
			         }
			        
			         for(int a=1,b=0;a<temp2+1;a=a+2)
			        {
			        	lastname[b]=arrauserData[a];
			        	b++;
			                	 
			        }
			         
			         int temp3=firstname.length;
			         proname=new String[temp3];
			        	        
			        for(int x=0;x<temp1;x++) 
			        {
			        	
			         proname[x]=firstname[x]+" "+lastname[x];
	              	data.Insertprofessorname(proname[x]);
	              	   
			        }
			       
			        getprofessorname();
			        showlessondetail();
			         
			 	    }   
			 }
		 	    catch (Exception e)    
		        {
//				 text1.setText(e.getMessage()); 
				 System.out.println("Error msg:"+e.getMessage());
		        
		        }
		 }
		
		/*
	    type:function
	    name:callnoofquestions
	    returns:void
	    parameters:no parameter
	    date:29-06-2011
	    purpose:Retrieves the no of  questions for  a specific test
	    */
		
		public void callnoofquestions()
		 { 
			 try {
				 		 
				 	  
		    	 	SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME4);
			 		
			 		request.addProperty("test_id",qid1);
			 			         		 
			 		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); 

			 		envelope.setOutputSoapObject(request);
			 		envelope.dotNet=true; 
			 		envelope.encodingStyle = SoapSerializationEnvelope.XSD;
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
			        String[] arrauserData;
			        arrauserData = ((String) str1).split("; ");
			       
			       
			        int temp2=arrauserData.length;
			        int temp1=((arrauserData.length)/4);
			        String[] strques;
			        strques=new String[temp1];
			        
			        		        
			        
			         for(int a=1,b=0;a<temp2+1;a=a+4)
			        {
			        	strques[b]=arrauserData[a];
			        	b++;
			                	 
			        }
			         
			        qcount=strques.length;
			        System.out.println("No of Questions:"+qcount);
			    	         
		          	     
			        
		     } 
		        catch (Exception e)    
		        {
		        tv.setText(e.getMessage());
		        }
		           
		 } //Call to the web services ends
		
		
		/*
	    type:function
	    name:getcourse
	    returns:void
	    parameters:no parameter
	    date:29-06-2011
	    purpose:Retrieves courses from the local database by calling a method selectprofilecourse from database helper class.
	    */
	 
	 public void getcourse()
	 {
		 
		 
		 Cursor c1=data.selectprofilecourse(stunm);
		 course1=new String[c1.getCount()];
		 int i=0;
		
			while(c1.moveToNext())
			{
				
				String course=c1.getString(0);
				System.out.println("Course name:"+course);
				
				course1[i]=course;
				i++;
				
			}
			
		 
		 
			getreport();
	 } 
	 
	 /*
	    type:function
	    name:getlessoncount
	    returns:void
	    parameters:course name
	    date:29-06-2011
	    purpose:Retrieves the count of lessons under a selected course 
	    */
	 
	 
	 public void getlessoncount(String course2)
	 {
		
		 
		 Cursor c1=data.selectprofilelesson(course2);
		 
		 count=c1.getCount();
		 	 
	 }
	 
	 /*
	    type:function
	    name:getlesson
	    returns:void
	    parameters:course name
	    date:29-06-2011
	    purpose:Retrieves the names of lessons under a selected course 
	    */
	 public void getlesson(String course2)
	 {
		
		 
		 Cursor c1=data.selectprofilelesson(course2);
		 lesson1=new String[c1.getCount()];
		 //int count=c1.getCount();
		 int i=0;
			while(c1.moveToNext())
			{
				
				String lesson=c1.getString(0);
				System.out.println("Lesson name:"+lesson);
				lesson1[i]=lesson;
				i++;
				

				
			}
		 
		 getlessons();
		 
	 }
	 
	 /*
	    type:function
	    name:gettestcount
	    returns:void
	    parameters:lesson name
	    date:29-06-2011
	    purpose:Retrieves the count of tests under a selected lesson 
	    */
	 public void gettestcount(String lesson2)
	 {
		
		 
		 Cursor c1=data.selectprofiletest(lesson2);
		 tcount=c1.getCount();
		 	 
	 }
	 
	 /*
	    type:function
	    name:gettest
	    returns:void
	    parameters:lesson name
	    date:29-06-2011
	    purpose:Retrieves the name of tests under a selected lesson 
	    */
	 public void gettest(String lesson2)
	 {
		
		 
		 Cursor c1=data.selectprofiletest(lesson2);
		 test1=new String[c1.getCount()];
		 int i=0;
			while(c1.moveToNext())
			{
				
				String test=c1.getString(0);
				System.out.println("Lesson name:"+test);
				test1[i]=test;
				i++;
				

				
			}
		 
		 gettests();
		 
	 }
	 
	 /*
	    type:function
	    name:getprofessorname
	    returns:void
	    parameters:no parameter
	    date:29-06-2011
	    purpose:Retrieves the name of professor for the selected course 
	    */
	 public void getprofessorname()
	 {
		
		 
		 Cursor c1=data.selectprofessorname();
		 pronm=new String[c1.getCount()];
		 int i=0;
			while(c1.moveToNext())
			{
				
				String proname=c1.getString(1);
				System.out.println("Professor names are :"+proname);
				pronm[i]=proname;
				i++;
				

				
			}
			name=pronm[0];
		 for(int j=1;j<pronm.length;j++)
		 {
			 fullname=pronm[j];
			 
			name=name+","+fullname;
			
		 }
		 
		 
		 
	 }
	 
	 /*
	    type:function
	    name:getreport
	    returns:void
	    parameters:no parameter
	    date:29-06-2011
	    purpose:creates a dynamic view to show the list of courses to which the user is registered
	    */
	 
	/* public void getreport() 
	 {
    	int i=0;
    	//int flag=0;
    	
    	
    	final TableRow tr2 = new TableRow(this); 
        tr2.setId(100+i);
        tr2.layout(0, 0, 0, 0); 
        
     // Create a TextView to house the name of the province
        final TextView labelTV1 = new TextView(this);
       
               
        labelTV1.setId(200+i);
        labelTV1.setText("My Courses"); 
       // labelTV1.setBackgroundColor("#E77471");
        
        labelTV1.setTextColor(Color.RED);
        tr2.addView(labelTV1);
         // Add the TableRow to the TableLayout 
            tl1.addView(tr2, new TableLayout.LayoutParams(
                     LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT));
            final RadioButton[] radioButton = new RadioButton[course1.length];
            RadioGroup radioGroup = new RadioGroup(this);
            radioGroup.setOrientation(RadioGroup.VERTICAL);
    	
    	for( i1=0;i1<course1.length;i1++) 
    	{
    		
				try
				{
			          
				final TableRow tr = new TableRow(this); 
	            tr.setId(100+i);
	            tr.layout(0, 0, 0, 0); 
	            final String course2=course1[i1];
	            
	         
	            
	         // Create a TextView to house the name of the province
	            final TextView labelTV = new TextView(this);
	           
	                   
	            labelTV.setId(200+i);
	           SpannableString content = new SpannableString(course2);
	            content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
	            
	            labelTV.setText(content); 
	            labelTV.setText(course2);
	            System.out.println("Course namessssssss:"+course1[i1]); 
	            labelTV.setTextColor(Color.BLACK);
	            
	            tr.addView(labelTV);
					 final TableRow tr = new TableRow(this); 
			            tr.setId(100+i);
			            tr.layout(0, 0, 0, 0); 
			            final String course2=course1[i1];
		    	
		    	
		    	
		        
		            radioButton[i1]  = new RadioButton(this);
		            //Button buttonView = new Button(this);
		            //buttonView.setText("Button " + j);
		            radioGroup.addView(radioButton[i1]);
		            //radioGroup.addView(buttonView);
		            //radioButton[j].setText(""+course1[i1]);
		            radioButton[i1].setText(""+course2);
		            radioButton[i1].setTextColor(Color.BLACK);
		            tr.addView(radioGroup, new TableLayout.LayoutParams(
		                     LayoutParams.WRAP_CONTENT,
		                    LayoutParams.WRAP_CONTENT));
				}
				catch (Exception e)    
		        {
		        //text1.setText(e.getMessage()); 
		        System.out.println("Error msg:::::::::"+e.getMessage());
		        }
			}
	          
	            tl1.addView(radioGroup, new TableLayout.LayoutParams(
	                     LayoutParams.WRAP_CONTENT,
	                    LayoutParams.WRAP_CONTENT));
	            
	            
	            
	                  
	           //i++;  
	            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
	                public void onCheckedChanged(RadioGroup rg, int checkedId) {
	                     for(int i=0; i<rg.getChildCount(); i++) {
	                          RadioButton btn = (RadioButton) rg.getChildAt(i);
	                          if(btn.getId() == checkedId) {
	                               String text = btn.getText().toString();
	                               System.out.println("The Text is.."+text);
	                               
	                               
	                               getlesson(text);
	                               tl1.removeAllViews();
	                             
	                               getreport();
	                              
	                               return;
	                          }
	                     }
	                }
	           });

	            //labelTV.setMovementMethod(LinkMovementMethod.getInstance());
	            
	            labelTV.setOnClickListener(new TextView.OnClickListener()  
	        	{ 
	            	
	            	public void onClick (View v)
	        		{ 
	            		tl2.removeAllViews();
	            		
	                    
	            		
	        		  String courses=(String) labelTV.getText();
	        		  getlesson(courses);
	        		  tl1.removeAllViews();
	        		  getreport();
	        		}
	        	});
	            btndetails.setOnClickListener(new Button.OnClickListener()  
	        	{ 
	            	
	            	public void onClick (View v)
	        		{ 
	            		  
	            		RadioButton btn = (RadioButton) rg.getChildAt(i);
                          if(btn.getId() == checkedId) {
                               String text = btn.getText().toString();
                           	            		
	        		  // courses=(String) labelTV.getText();
	        		  System.out.println("Course Name is:"+text);
	        		  //getprofessors(courses);
	        		  data.deleteprofessorname();
	        		  getlessoncount(text);
	        		  callprofessor();
                          }
	        		}
	        	});
	            }
				}*/
				
	 public void getreport() 
	 {
		 
		 
		 	int i=0;
	    	final TableRow tr2 = new TableRow(this); 
	        tr2.setId(100+i);
	        tr2.layout(0, 0, 0, 0); 
	        
	     // Create a TextView to house the name of the province
	        final TextView labelTV1 = new TextView(this);
	        labelTV1.setId(200+i);
	        labelTV1.setText("My Courses"); 
	        labelTV1.setTextColor(Color.RED);
	        tr2.addView(labelTV1);
	        
	        
	         // Add the TableRow to the TableLayout 
	            tl1.addView(tr2, new TableLayout.LayoutParams(
	                     LayoutParams.WRAP_CONTENT,
	                    LayoutParams.WRAP_CONTENT));
	            
	            final RadioButton[] radioButton = new RadioButton[course1.length];
	            RadioGroup radioGroup = new RadioGroup(this);
	            radioGroup.setOrientation(RadioGroup.VERTICAL);
	    	
         for(int j=0;j<course1.length;j++) 
        {
		 try{
			 
			 
			 final TableRow tr = new TableRow(this); 
	            tr.setId(100+i);
	            tr.layout(0, 0, 0, 0); 
	            final String course2=course1[j];
    	
    	
    	
        
            radioButton[j]  = new RadioButton(this);
            //Button buttonView = new Button(this);
            //buttonView.setText("Button " + j);
            radioGroup.addView(radioButton[j]);
            //radioGroup.addView(buttonView);
            //radioButton[j].setText(""+course1[i1]);
            radioButton[j].setText(""+course2);
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
            tl1.addView(radioGroup, new TableLayout.LayoutParams(
                     LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT));
            
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                public void onCheckedChanged(RadioGroup rg, int checkedId) {
                     for(int i=0; i<rg.getChildCount(); i++) {
                          RadioButton btn = (RadioButton) rg.getChildAt(i);
                          if(btn.getId() == checkedId) {
                               String text = btn.getText().toString();
                               System.out.println("The Text is.."+text);
                               
                               
                               getlesson(text);
                               tl1.removeAllViews();
         	        		  getreport();
                              // Showlesson();
                               /*tl2.removeAllViews();
                               getlessonreport();
                               tl2.setVisibility(View.VISIBLE);*/
                               
                               
                               // do something with text
                               return;
                          }
                     }
                }
           });
            }
	 
	 
				
	
	 
	 /*
	    type:function
	    name:getreport
	    returns:void
	    parameters:no parameter
	    date:29-06-2011
	    purpose:creates a dynamic view to show the list of lessons to which the user is registered under a selected course
	    */
	 public void getlessons() 
	 {
			int i=0;
	    	final TableRow tr2 = new TableRow(this); 
	        tr2.setId(100+i);
	        tr2.layout(0, 0, 0, 0); 
	        
	     // Create a TextView to house the name of the province
	        final TextView labelTV1 = new TextView(this);
	        labelTV1.setId(200+i);
	        labelTV1.setText("My Lessons"); 
	        labelTV1.setTextColor(Color.RED);
	        tr2.addView(labelTV1);
	        
	        
	         // Add the TableRow to the TableLayout 
	      
           
           final RadioButton[] radioButton = new RadioButton[course1.length];
           RadioGroup radioGroup = new RadioGroup(this);
           radioGroup.setOrientation(RadioGroup.VERTICAL);
   	
     // Add the TableRow to the TableLayout 
        tl2.addView(tr2, new TableLayout.LayoutParams(
                 LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT));
    	for( i1=0;i1<lesson1.length;i1++) 
    	{
    		
				try
				{
				final TableRow tr = new TableRow(this); 
	            tr.setId(100+i);
	            tr.layout(0, 0, 0, 0); 
	            final String course2=lesson1[i1];
	            
	            
	            /*final TableRow tr1 = new TableRow(this);  
	        	final TextView labelmytests = new TextView(this);*/
	         // Create a TextView to house the name of the province
	            final TextView labelTV = new TextView(this);
	            
	                   

	            radioButton[i1]  = new RadioButton(this);
	            //Button buttonView = new Button(this);
	            //buttonView.setText("Button " + j);
	            radioGroup.addView(radioButton[i1]);
	            //radioGroup.addView(buttonView);
	            //radioButton[j].setText(""+course1[i1]);
	            radioButton[i1].setText(""+course2);
	            radioButton[i1].setTextColor(Color.BLACK);
	            
	          /*  
	            final Button btndetails = new Button(this);
		           
                
	           	btndetails.setText("Details");
	            btndetails.setTextColor(Color.BLACK);
	            //btndetails.setWidth(30);
*/               
            
            
        //    tr.addView(btndetails);
            
	         // Add the TableRow to the TableLayout 
	            tl2.addView(radioGroup, new TableLayout.LayoutParams(
	                     LayoutParams.WRAP_CONTENT,
	                    LayoutParams.WRAP_CONTENT));
	            
	            
				
				
	                  
	            i++;  
	           
	            
	            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
	                public void onCheckedChanged(RadioGroup rg, int checkedId) {
	                     for(int i=0; i<rg.getChildCount(); i++) {
	                          RadioButton btn = (RadioButton) rg.getChildAt(i);
	                          if(btn.getId() == checkedId) {
	                               String lesson = btn.getText().toString();
	                              // System.out.println("The Text is.."+text);
	                               
	                               
	                                                 
	         	        		  gettest(lesson);
	         	        		  tl2.removeAllViews();
	         	        		  getlessons();
	                              
	                               return;
	                          }
	                     }
	                }
	           });
	            /*labelTV.setOnClickListener(new TextView.OnClickListener()  
	        	{ 
	            	
	            	public void onClick (View v)
	        		{ 
	            	  tl4.removeAllViews();
	            	  String lesson=(String) labelTV.getText();
	        		  gettest(lesson);
	        		  tl2.removeAllViews();
	        		  getlessons();
	        		}
	        	});
	            */
	           /* btndetails.setOnClickListener(new Button.OnClickListener()  
	        	{ 
	            	
	            	public void onClick (View v)
	        		{ 
	            			            		
	        		   lessons=(String) labelTV.getText();
	        		  System.out.println("Course Name is:"+lessons);
	        		  //getprofessors(courses);
	        		  data.deleteprofessorname();
	        		  gettestcount(lessons);
	        		  callprofessorlesson();
	        		  
	        		}
	        	});*/
				} 
				catch (Exception e)    
		        {
		       // text1.setText(e.getMessage()); 
		        System.out.println("Error msg:::::::::"+e.getMessage());
		        }
			}
	 }

	 
	 /*
	    type:function
	    name:gettests
	    returns:void
	    parameters:no parameter
	    date:29-06-2011
	    purpose:creates a dynamic view to show the list of tests to which the user is registered under a selected lesson
	    */
	 public void gettests() 
	 {
    	int i=0;
    	//int flag=0;
		 
    	final TableRow tr2 = new TableRow(this);  
    	final TextView labelmylesson = new TextView(this);
    	tr2.layout(0, 0, 0, 0); 
        
        
        labelmylesson.setText("My Tests"); 
        
        labelmylesson.setTextColor(Color.RED);
        
        tr2.addView(labelmylesson);
     // Add the TableRow to the TableLayout 
        tl4.addView(tr2, new TableLayout.LayoutParams(
                 LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT));
        final RadioButton[] radioButton = new RadioButton[test1.length];
        RadioGroup radioGroup = new RadioGroup(this);
        radioGroup.setOrientation(RadioGroup.VERTICAL);
	
  // Add the TableRow to the TableLayout 
   
    	for( i1=0;i1<test1.length;i1++) 
    	{
    		
				try
				{
				final TableRow tr = new TableRow(this); 
	            tr.setId(100+i);
	            tr.layout(0, 0, 0, 0); 
	            final String test2=test1[i1];
	            
	            
	            
	         // Create a TextView to house the name of the province
	            final TextView labelTV = new TextView(this);
	            
	                   

	            radioButton[i1]  = new RadioButton(this);
	            //Button buttonView = new Button(this);
	            //buttonView.setText("Button " + j);
	            radioGroup.addView(radioButton[i1]);
	            //radioGroup.addView(buttonView);
	            //radioButton[j].setText(""+course1[i1]);
	            radioButton[i1].setText(""+test2);
	            radioButton[i1].setTextColor(Color.BLACK);
	            
	           /* final Button btndetails = new Button(this);
		           
                
	           	btndetails.setText("Details");
	            btndetails.setTextColor(Color.BLACK);*/
	            //btndetails.setWidth(30);
               
            
            
                //tr.addView(btndetails);
            
	         // Add the TableRow to the TableLayout 
	            tl4.addView(radioGroup, new TableLayout.LayoutParams(
	                     LayoutParams.WRAP_CONTENT,
	                    LayoutParams.WRAP_CONTENT));
	            
	            
				
				
	                  
	            i++;   
	            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
	                public void onCheckedChanged(RadioGroup rg, int checkedId) {
	                     for(int i=0; i<rg.getChildCount(); i++) {
	                          RadioButton btn = (RadioButton) rg.getChildAt(i);
	                          if(btn.getId() == checkedId) {
	                             
	                        
	                        	  
	                                	  
	                        	  
	                        	  
	                        	  
	                        	  
	                        	  
	                                            	  
	                               
	                               
	                               test=btn.getText().toString();
	         	            	  System.out.println("Selected Test is:"+test);
	         	            	  if(test.contains(";"))
	         	            	  {
	         	            		  test=test.replace(";", "");
	         	            	  }
	         	            	  Cursor c=data.getid(test);
	         	      			while(c.moveToNext())
	         	      			{  
	         	      			 qid=c.getInt(0);
	         	      			
	         	      			System.out.println("Quiz id:"+qid);
	         	      			}  tl4.removeAllViews();
	         	      			questions();
	         	            	  
	         	      	   
	                               return;
	                          }
	                     }
	                }
	           });
	            
	           
				}
				catch (Exception e)    
		        {
		        //text1.setText(e.getMessage()); 
		        System.out.println("Error msg:::::::::"+e.getMessage());
		        }
			}
	 }
	 
	
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		
		
	}
	
	/*
    type:function
    name:funreview
    returns:void
    parameters:no parameter
    date:29-06-2011
    purpose:Pass the values from profile activity to mainstudentreview activity and calls the mainstudentreview activity
    */
	private void funreview()
	{
		Intent i = new Intent(this,mainstudentreview.class);
		Bundle bundle = new Bundle();
		bundle.putString("wel",s1);
		bundle.putString("type",type);
		bundle.putString("welcome",stunm);
		i.putExtras(bundle);
		startActivity(i);
		

	}
	/*
    type:function
    name:funmailbox
    returns:void
    parameters:no parameter
    date:29-06-2011
    purpose:Pass the values from profile activity to mailbox activity and calls the mailbox activity
    */
	private void funmailbox()
	{
		Intent i = new Intent(this,mailbox.class);
		Bundle bundle = new Bundle();
		bundle.putString("wel",s1);
		bundle.putString("type",type);
		bundle.putString("welcome",stunm);
		i.putExtras(bundle);
		startActivity(i);
		

	}
	
	/*
    type:function
    name:funreward
    returns:void
    parameters:no parameter
    date:29-06-2011
    purpose:Pass the values from profile activity to reward activity and calls the reward activity
    */
	
	private void funreward()
	{
		Intent i = new Intent(this,reward.class);
		Bundle bundle = new Bundle();
		bundle.putString("wel",s1);
		bundle.putString("type",type);
		bundle.putString("welcome",stunm);
		i.putExtras(bundle);
		startActivity(i);
		

	}
	
	/*
    type:function
    name:questions
    returns:void
    parameters:no parameter
    date:29-06-2011
    purpose:Pass the values from profile activity to splashscreeen activity and calls the splashscreeen activity
    */
	
	
	private void questions()
	{	
		
		//String s1 =test;
		System.out.println("Quiz name:"+s1); 
	    Intent i2 = new Intent(profile.this,splashscreeen.class);
		Bundle bundle = new Bundle();
		bundle.putString("stuname",stunm);
		bundle.putInt("id",qid);
		bundle.putString("qname",test);
		i2.putExtras(bundle);  
		startActivity(i2);
		data.deletetest();  
		

	}
	
	/*
    type:function
    name:showcoursedetail
    returns:void
    parameters:no parameter
    date:29-06-2011
    purpose:Displays the details of the course selected
    */
	
	
	public void showcoursedetail()
	{
	myDialog = new Dialog(profile.this);
 	myDialog.setContentView(R.layout.coursedetails);
 	myDialog.setTitle(courses+" "+"Details");
 	myDialog.setCancelable(true); 
 	
 	 TextView name1 = (TextView) myDialog.findViewById(R.id.txtname);
 	 TextView no = (TextView) myDialog.findViewById(R.id.txtnooflesson);
 	TextView str = (TextView) myDialog.findViewById(R.id.str);
 	TextView noof = (TextView) myDialog.findViewById(R.id.noof);
     Button button = (Button) myDialog.findViewById(R.id.btnok);
     
     name1.setText(name);
     no.setText(""+count);
     str.setText("Click course to view the lesson names");
     noof.setText("No.Of Lessons:");
     button.setOnClickListener(new OnClickListener() {
     public void onClick(View v) {
     	
     	
     	myDialog.dismiss();
         }
     });

     myDialog.show();
	 
	 System.out.println("Full Names are:"+name);
	}
	
	/*
    type:function
    name:showlessondetail
    returns:void
    parameters:no parameter
    date:29-06-2011
    purpose:Displays the details of the lessons selected
    */
	public void showlessondetail()
	{
	myDialog = new Dialog(profile.this);
 	myDialog.setContentView(R.layout.coursedetails);
 	myDialog.setTitle(lessons+" "+"Details");
 	myDialog.setCancelable(true); 
 	
 	 TextView name1 = (TextView) myDialog.findViewById(R.id.txtname);
 	 TextView no = (TextView) myDialog.findViewById(R.id.txtnooflesson);
 	TextView str = (TextView) myDialog.findViewById(R.id.str);
 	TextView noof = (TextView) myDialog.findViewById(R.id.noof);
     Button button = (Button) myDialog.findViewById(R.id.btnok);
     
     name1.setText(name);
     no.setText(""+tcount);
     str.setText("Click Lesson to view the test names");
     noof.setText("No.Of Tests:");
     button.setOnClickListener(new OnClickListener() {
     public void onClick(View v) {
     	
     	
     	myDialog.dismiss();
         }
     });

     myDialog.show();
	 
	 System.out.println("Full Names are:"+name);
	}
	
	/*
    type:function
    name:showtestdetail
    returns:void
    parameters:no parameter
    date:29-06-2011
    purpose:Displays the details of the tests selected
    */
	public void showtestdetail()
	{
	myDialog = new Dialog(profile.this);
 	myDialog.setContentView(R.layout.testdetails);
 	myDialog.setTitle(tests+" "+"Details");
 	myDialog.setCancelable(true); 
 	
 	 TextView qcount1 = (TextView) myDialog.findViewById(R.id.txtnoofques);
 	 TextView mark = (TextView) myDialog.findViewById(R.id.txtmark);
 	 TextView time = (TextView) myDialog.findViewById(R.id.txttotaltime);
 	
     Button button = (Button) myDialog.findViewById(R.id.btnok);
     
     qcount1.setText(""+qcount);
     mark.setText("1");
     time.setText("180 sec");
     
     button.setOnClickListener(new OnClickListener() {
     public void onClick(View v) {
     	
     	
     	myDialog.dismiss();
         }
     });

     myDialog.show();
	 
	 
	}
	
	


}