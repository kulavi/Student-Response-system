package com.SRS6;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.SRS6.addtest_byprofessor.MyOnConentSelectedListener;






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
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.UnderlineSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class teacherprofile extends Activity implements OnItemClickListener {
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
	
	
	String[] course1,lesson1,test1,lesson;
	
	private static final String NAMESPACE = "http://tempuri.org/";        
	private static final String URL = "http://192.168.1.85/studentresponse/Service.asmx"; 
	
	private static final String SOAP_ACTION = "http://tempuri.org/user_profile";      
	private static final String METHOD_NAME = "user_profile";      
		
	
	
	
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
	 data.open();
	 
	 welstu=(TextView)findViewById(R.id.welcome);
	 
	 /*review=(TextView)findViewById(R.id.reviews);
	 
	 mailbox=(TextView)findViewById(R.id.mailbox);
	 */
	 //reward=(TextView)findViewById(R.id.rewards);
	 
	 
	
	 
	 
	 tl=(TableLayout)findViewById(R.id.tbl1);
     tl.setBackgroundResource(R.layout.shapes);
     
     tl1=(TableLayout)findViewById(R.id.tbl2);
     tl1.setBackgroundResource(R.layout.shapes);
     //tl1.setVisibility(View.GONE);
     
     tl2=(TableLayout)findViewById(R.id.tbl3);
     tl2.setBackgroundResource(R.layout.shapes);
     //tl2.setVisibility(View.GONE);
     
     tl4=(TableLayout)findViewById(R.id.tbl4);
     tl4.setBackgroundResource(R.layout.shapes);
     tl4.setVisibility(View.GONE);
     
    	 
	 Bundle bundle = getIntent().getExtras();
	 s1=bundle.getString("wel");
	 stunm=bundle.getString("welcome");
	 type=bundle.getString("type");
	 
	 
	
	 
	 tl2.setVisibility(View.GONE);
	 
	 
	 /*
	 review.setMovementMethod(LinkMovementMethod.getInstance());
	 
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
		
     
		});
	 
 reward.setMovementMethod(LinkMovementMethod.getInstance());
	 
	 reward.setOnClickListener(new TextView.OnClickListener() 
		{ public void onClick (View v)
			{ 
				
				funreward();
			}
		 
     
		});
	 */
 	 welstu.setText(s1); 
	 //data.deleteprofile();
	 display_user_details_web();
	 
	 
	 
	data.close();
	 } 
	
	
	/*public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.professor_menu, menu);  
	    return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        
	        case R.id.test:     Showtest();
	                            break;
	        case R.id.question: addquestion();
            					break;
	        case R.id.VSL:     show_student_list();
	        					break;
	    }
	    return true;
	}*/
	
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
            // TODO Auto-generated catch block
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
	   
	 
	 /*Type :Function
		name:display_user_details_web
		return type:void
		date:29-6-11
		purpose:Retrieves name score and date from server and Inserts profile details to local database*/
	 public void display_user_details_web()
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
		        
		        
		         
		       // System.out.println("Length:"+temp1);
		         
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
		         System.out.println("Valuesssssssss."+stuname[x]+temp1+coursenm[x]+lessonnm[x]+testnm[x]);
                	data.Insertprofile(stuname[x],coursenm[x],lessonnm[x],testnm[x]);
                	
     
		        }
		        getcourse();
		        
		 	    }   
		 }
	 	    catch (Exception e)    
	        {
			 text1.setText(e.getMessage()); 
			 System.out.println("Error msg:"+e.getMessage());
	        
	        }
	 }
	 /*Type :Function
		name:getcourse
		return type:void
		date:29-6-11
		purpose:Retrieves course from local database*/
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
				
			c1.close();
			getreport();
	 } 
/*Type :Function
		name:getreport
		return type:void
		date:29-6-11
		purpose:Creates dynamic layout of the courses*/
	 /*public void getreport() 
	 {
    	int i=0;
    	int flag=0;
    	
    	
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
	           
	                   
	           
	            labelTV.setText(course2);
	            System.out.println("Course namessssssss:"+course1[i1]); 
	            labelTV.setTextColor(Color.BLACK);
	            
	            tr.addView(labelTV);
	            final Button btndetails = new Button(this);
		           
                
		           	btndetails.setText("student list");
		            btndetails.setTextColor(Color.BLACK);
		            
	               
	            
	            
		            tr.addView(btndetails);
	         // Add the TableRow to the TableLayout 
	            tl1.addView(tr, new TableLayout.LayoutParams(
	                     LayoutParams.WRAP_CONTENT,
	                    LayoutParams.WRAP_CONTENT));
	            
	            
	            
	                  
	            i++;  
	            
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
	            			            		
	        		   
	        		  
	        		  
	        		}
	        	});
	            }
				
				
				catch (Exception e)    
		        {
		        text1.setText(e.getMessage()); 
		        System.out.println("Error msg:::::::::"+e.getMessage());
		        }
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
		        text1.setText(e.getMessage()); 
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
                               Showlesson();
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
	 
	 
	 /*public void getlessonreport()
	 {int i=0;
 	
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
         tl1.addView(tr2, new TableLayout.LayoutParams(
                  LayoutParams.WRAP_CONTENT,
                 LayoutParams.WRAP_CONTENT));
         
         final RadioButton[] radioButton = new RadioButton[course1.length];
         RadioGroup radioGroup = new RadioGroup(this);
         radioGroup.setOrientation(RadioGroup.VERTICAL);
 	
  for(int j=0;j<lesson1.length;j++) 
 {
	 try{
		 
		 
		 final TableRow tr = new TableRow(this); 
         tr.setId(100+i);
         tr.layout(0, 0, 0, 0); 
         final String lesson2=lesson1[j];
	
	
	
 
     radioButton[j]  = new RadioButton(this);
     //Button buttonView = new Button(this);
     //buttonView.setText("Button " + j);
     radioGroup.addView(radioButton[j]);
     //radioGroup.addView(buttonView);
     //radioButton[j].setText(""+course1[i1]);
     radioButton[j].setText(""+lesson2);
     radioButton[j].setTextColor(Color.BLACK);
     
 
 
	 }


         
			
			
			catch (Exception e)    
	        {
	        text1.setText(e.getMessage()); 
	        System.out.println("Error msg:::::::::"+e.getMessage());
	        }
		}
  
  //tr.addView(radioGroup);
  
  // Add the TableRow to the TableLayout 
     tl2.addView(radioGroup, new TableLayout.LayoutParams(
              LayoutParams.WRAP_CONTENT,
             LayoutParams.WRAP_CONTENT));
     
     radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
         public void onCheckedChanged(RadioGroup rg, int checkedId) {
              for(int i=0; i<rg.getChildCount(); i++) {
                   RadioButton btn = (RadioButton) rg.getChildAt(i);
                   if(btn.getId() == checkedId) {
                        String text = btn.getText().toString();
                        System.out.println("The Text is.."+text);
                        
                        
                        //getlesson(text);
                        // do something with text
                        return;
                   }
              }
         }
    });

}*/
	 
	 /*Type :Function
		name:getlesson
		return type:void
		date:29-6-11
		purpose:Retrieves lesson from database*/
	 public void getlesson(String course2)
	 {
		
		 
		 Cursor c1=data.selectprofilelesson(course2);
		 lesson1=new String[c1.getCount()];
		 
		 int i=0;
			while(c1.moveToNext())
			{
				
				String lesson=c1.getString(0);
				System.out.println("Lesson name:"+lesson);
				lesson1[i]=lesson;
				i++;
				

				
			}
			c1.close();
		 
		// getlessons();
		 
	 }
/*Type :Function
		name:getlessons
		return type:void
		date:29-6-11
		purpose:Retrieves lessons from database and sets to dyamically created layout*/
	 
	 
	 /*public void getlessons() 
	 {
    	int i=0;
    	int flag=0;
    	final TableRow tr2 = new TableRow(this);  
    	final TextView labelmylesson = new TextView(this);
    	tr2.layout(0, 0, 0, 0); 
        
        
        labelmylesson.setText("My Lessons"); 
        
        labelmylesson.setTextColor(Color.RED);
        
        tr2.addView(labelmylesson);
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
	            
	            
	            
	         // Create a TextView to house the name of the province
	            final TextView labelTV = new TextView(this);
	            
	                   
	            labelTV.setId(200+i);
	            labelTV.setText(course2); 
	            System.out.println("Course namessssssss:"+course1[i1]);
	            labelTV.setTextColor(Color.BLACK);
	            
	            tr.addView(labelTV);
	            
	            
	            final Button btndetails = new Button(this);
		           
                
	           	btndetails.setText("Student list");
	            btndetails.setTextColor(Color.BLACK);
	            //btndetails.setWidth(30);
               
            
            
            tr.addView(btndetails);
            
	         // Add the TableRow to the TableLayout 
	            tl2.addView(tr, new TableLayout.LayoutParams(
	                     LayoutParams.WRAP_CONTENT,
	                    LayoutParams.WRAP_CONTENT));
	            
	            
				
				
	                  
	            i++;  
	            labelTV.setOnClickListener(new TextView.OnClickListener()  
	        	{ 
	            	
	            	public void onClick (View v)
	        		{ 
	            	  tl4.removeAllViews();
	            	  String lesson=(String) labelTV.getText();
	        		  //gettest(lesson);
	        		  tl2.removeAllViews();
	        		  getlessons();
	        		}
	        	});
	            
	            btndetails.setOnClickListener(new Button.OnClickListener()  
	        	{ 
	            	
	            	public void onClick (View v)
	        		{ 
	            			            		
	        		   
	        		  
	        		  
	        		}
	        	});
				}
				catch (Exception e)    
		        {
		        text1.setText(e.getMessage()); 
		        System.out.println("Error msg:::::::::"+e.getMessage());
		        }
			}
	 }*/
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}
/*Type :Function
		name:funreview
		return type:void
		date:29-6-11
		purpose:Sends(Pass) data toreportscreen class*/
	private void funreview()
	{
		Intent i = new Intent(this,reportscreen.class);
		Bundle bundle = new Bundle();
		bundle.putString("wel",s1);
		bundle.putString("type",type);
		bundle.putString("welcome",stunm);
		i.putExtras(bundle);
	   startActivity(i);
		

	}
/*Type :Function
		name:funmailbox
		return type:void
		date:29-6-11
		purpose:Sends data to mailbox class*/
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
	/*Type :Function
		name:funreward
		return type:void
		date:29-6-11
		purpose:Sends data to reward class*/
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
	
	/*Type :Function
	name:addtest
	return type:void
	date:29-6-11
	purpose:Starts the activity addtest_byprofessor*/
	private void Showlesson()
	{	
		
		
        Intent i2 = new Intent(teacherprofile.this,show_lesson_list.class);
        Bundle bundle = new Bundle();
		bundle.putStringArray("lessonnames",lesson1);
		bundle.putStringArray("testnames",testnm);
		i2.putExtras(bundle);
		startActivity(i2);

	}
	
	/*Type :Function
	name:addtest
	return type:void
	date:29-6-11
	purpose:Starts the activity addtest_byprofessor
	private void Showtest()
	{	
		
		
        Intent i2 = new Intent(teacherprofile.this,show_test_list.class);
        Bundle bundle = new Bundle();
		bundle.putStringArray("lessonnames",lesson1);
		bundle.putStringArray("testnames",testnm);
		i2.putExtras(bundle);
		startActivity(i2);

	}
	Type :Function
	name:addquestion
	return type:void
	date:29-6-11
	purpose:Starts the activity addquest_byprofessor
	private void addquestion()
	{	
		
		
        Intent i2 = new Intent(teacherprofile.this,addquest_byprofessor.class);
        Bundle bundle = new Bundle();
		bundle.putStringArray("lessonnames",lesson1);
		bundle.putStringArray("testnames",testnm);
		i2.putExtras(bundle);
		startActivity(i2);

	}
	Type :Function
	name:show_student_list
	return type:void
	date:29-6-11
	purpose:Starts the activity show_student_list
	private void show_student_list()
	{	
		
		
        Intent i2 = new Intent(teacherprofile.this,show_student_list.class);
        Bundle bundle = new Bundle();
		bundle.putStringArray("lessonnames",lesson1);
		bundle.putStringArray("testnames",testnm);
		i2.putExtras(bundle);
		startActivity(i2);

	}*/
	
}