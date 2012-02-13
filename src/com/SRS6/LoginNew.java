//Login Screen to enter into SRS

package com.SRS6;



 
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

//import com.MyActivity.MyActivity;
import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

public class LoginNew extends Activity implements Runnable {
	
	private static final String SOAP_ACTION = "http://tempuri.org/getUser";      
	private static final String METHOD_NAME = "getUser";      
	private static final String NAMESPACE = "http://tempuri.org/";      
	private static final String URL = "http://192.168.1.170/studentresponse/Service.asmx";  
	
	
	
	private EditText uname = null;
	private EditText pwd = null;
	private Button login = null;
	private TextView text1, text2;
	private ImageView loginscrn;
	private Button back = null;
	String str1,user,str,type;
	String[] str2; 
	TableLayout tl;
	
	
		
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginnew);
        login = (Button)findViewById(R.id.login);
    
        
        tl=(TableLayout)findViewById(R.id.info_table);
        //tl.setBackgroundResource(R.layout.shapes);
        
        uname = (EditText)findViewById(R.id.uname);
    	pwd = (EditText)findViewById(R.id.pwd);
    	text1=(TextView)findViewById(R.id.text1);
        loginscrn=(ImageView)findViewById(R.id.scrn);
         
       
       
        login.setOnClickListener(new Button.OnClickListener() 
		{ public void onClick (View v)
			{ 
						
			    				
				try {
					checkValidate();
					
				}    
				catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
				
				
			}
		
		    			                
		});
        
        
    }
    
      	
    /*
     type:function
     name:checkValidate
     parameter:no parameters
     returns:void
     date:29-06-2011
     purpose:Checks login validations like user name or password not entered and Invalid login
     */	
    
    
    private void checkValidate() throws InterruptedException
    {
    	
    	 
    	
    	if((uname.getText().toString()).equals(""))
    	{
    		text1.setText("Uname not entered!!");
    	}
    	else if((pwd.getText().toString()).equals(""))
    	{
    		text1.setText("Password not entered!!");
    	}
    	else
    	{
       
    		call();
        
       
    }
    	
   }

	public void run() {
		// TODO Auto-generated method stub
		
	}
	
	/*
    type:function
    name:md5
    parameter:password
    returns:encrypted password
    date:29-06-2011
    purpose:Generate an encrypted password
    */
	public String md5(String s)
    {   	
			
    	String signature;
	    try 
	    {
	    	
	        // Create MD5 Hash
	        MessageDigest md5 = MessageDigest.getInstance("MD5");
	        md5.update(s.getBytes(),0,s.length());
	        signature = new BigInteger(1,md5.digest()).toString(16);
	        return signature;
	        
	    } 
	    catch (NoSuchAlgorithmException e)
	    {
	        e.printStackTrace();
	    }
		return "";
	    
	}
	
	/*
    type:function
    name:call
    parameter:no parameters
    returns:void
    date:29-06-2011
    purpose:Authenticate the user login
    */
	public void call()
	 { 
		String pass=pwd.getText().toString()+"cDWQR#$Rcxsc";
		System.out.println("Encrypted pass:"+md5(pass));
		user=uname.getText().toString();
		String p=md5(pass);
		 try {
			 		  
			 	  
	    	 	SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		 		
		 		request.addProperty("strusername",user);
		 		request.addProperty("strpass",p);
		 			         		 
		 		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); 

		 		envelope.setOutputSoapObject(request);
		 		envelope.dotNet=true; 
		 		envelope.encodingStyle = SoapSerializationEnvelope.XSD;
		 		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
		 	
		 		androidHttpTransport.call(SOAP_ACTION, envelope);
		    	 Object result = (Object)envelope.getResponse();
		 	      str1=result.toString(); 
		 	     System.out.println("Data:"+str1);
		 	    if(str1.equals("False"))
		 	     {
		 	    	text1.setText("Invalid Login");
		 	     }
		 	     else
		 	     {  
		 	     text1.setText("Login Successful!!");      
		 	     str2=str1.split(" ");
		 	     
		 	     if(str2.length==5)
		 	     {
		 	     str=str2[0]+str2[1]+" "+str2[2]+str2[3];
		 	      	 	     
		 	     if(str2[4].equals("student"))
		 	     {
		 	    	 type="student";
		 	     student();
		 	     }
		 	     else
		 	   	 if(str2[4].equals("administrator"))
		 	     {
		 	   		type="administrator";
		 	    	 
		 	   	teacher();
		 	     }
		 	     else
		 	     if(str2[4].equals("professor"))  
		 	     {
		 	    	type="professor";
		 	    	 teacher();
		 	     }
		 	     }
		 	     else
		 	     {
		 	    	str=str2[0]+" "+str2[1]+str2[2];
	      	 	     
			 	     if(str2[3].equals("student"))
			 	     {
			 	    	 type="student";
			 	     student();
			 	     }
			 	     else
			 	   	 if(str2[3].equals("administrator"))
			 	     {
			 	   		type="administrator";
			 	    	 
			 	   	teacher();
			 	     }
			 	     else
			 	     if(str2[3].equals("professor"))    
			 	     {
			 	    	type="professor";
			 	    	 teacher();
			 	     }
		 	     }
		 	     
		 	     }
		 	    
	
		 }
		 catch (Exception e)    
	        {
			 text1.setText(e.getMessage()); 
			 System.out.println("Error msg:"+e.getMessage());
	        }
	 }
	
	/*
    type:function
    name:student
    parameter:no parameters
    returns:void
    date:29-06-2011
    purpose: Pass the values from LoginNew activity to profile activity and calls the profile activity
    */
    
	private void student()
	{
		Intent i1 = new Intent(this,profile.class);
		Bundle bundle = new Bundle();
		bundle.putString("wel",str);
		bundle.putString("type",type);
		bundle.putString("welcome",user);
		i1.putExtras(bundle);
	    startActivity(i1);

	}
	/*
    type:function
    name:backtohome
    parameter:no parameters
    returns:void
    date:29-06-2011
    purpose: Calls the SRS6 activity
    */
	private void backtohome()
	{
		Intent i1 = new Intent(this,SRS6.class);
	    startActivity(i1);

	}
	
	/*
    type:function
    name:teacher
    parameter:no parameters
    returns:void
    date:29-06-2011
    purpose: Pass the values from LoginNew activity to teacherprofile activity and calls the teacherprofile activity
    */
	public void teacher()
	 {
		 Intent i2 = new Intent(this,teacherprofile.class);
		 Bundle bundle = new Bundle();
			bundle.putString("wel",str);
			bundle.putString("type",type);
			bundle.putString("welcome",user);
			i2.putExtras(bundle);
		 startActivity(i2);
	 } 
	
	

   

    
    
     }

