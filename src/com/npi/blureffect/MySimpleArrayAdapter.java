package com.npi.blureffect;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableRow.LayoutParams;

public class MySimpleArrayAdapter extends ArrayAdapter<String> {
  private final Context context;
  private final String[] values;
  private final String[] headers;
  public int pos = 0;

  public MySimpleArrayAdapter(Context context, String[] values, String[] headers) {
    super(context, R.layout.list_item, values);
    this.context = context;
    this.values = values;
    this.headers= headers; 
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
	  pos = position;
    LayoutInflater inflater = (LayoutInflater) context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View rowView = inflater.inflate(R.layout.list_item, parent, false);
    TextView textView = (TextView) rowView.findViewById(R.id.textView1);
    TextView textView3 = (TextView) rowView.findViewById(R.id.textView3);
    TextView textView4 = (TextView) rowView.findViewById(R.id.textView4);

    if(headers[position].equalsIgnoreCase("Laptop Availibility"))
    {	
	    FakeNetLoader fl = new FakeNetLoader();
	  	fl.execute("http://dev-mobileapi.stashd.org:8000/mwf_laptops?no_server_init");
    }
    textView.setText(values[position]);
    TextView textView1 = (TextView) rowView.findViewById(R.id.textView2);

    textView1.setText(headers[position]);
    TextView test = new TextView(context, null);
  
    
    LinearLayout l1 = (LinearLayout) rowView.findViewById(R.id.linearLayout1);
   
   
    if(position==0 )
    {
    	/*textView1.setBackgroundResource(R.color.trans1);
    	textView1.setPadding(0, 0,0,0);
    	textView.setPadding(0,0,0,0);
    	textView.setBackgroundResource(R.color.trans1);*/
    	l1.setBackgroundResource(R.color.trans1);
    	l1.setPadding(10, 10, 10, 10);
    	textView1.setTextSize(42);
    	textView4.setVisibility(View.GONE);

    }
    else if(position%2==1)
    {    	
    	l1.setBackgroundResource(R.color.trans1);
    	textView.setWidth(0);
    	textView1.setWidth(0);
    	textView.setPadding(0, 0,0,0);
    	textView1.setPadding(0, 0,0,0);
    	textView.setVisibility(View.GONE);
    	textView1.setVisibility(View.GONE);
    	textView3.setBackgroundResource(R.color.trans1);
    	textView4.setBackgroundResource(R.color.trans1);
    //	textView3.setVisibility(View.GONE);

    }
    else
    {
    	l1.setPadding(10, 10, 10, 10);
    	textView4.setVisibility(View.GONE);

    	 @SuppressWarnings("deprecation")
    		LayoutParams lparams = new LayoutParams(
    	    		   LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
    	 //THIS IS HOW YOU CAN ADD A TEXT VIEW
    	    	/*	test.setLayoutParams(lparams);
    	    		test.setTextColor(Color.parseColor("#eeeeee"));
    	    		test.setBackgroundResource(R.color.trans);
    	    		test.setText("test");
    	    		l1.addView(test);*/
    }
    
    return rowView;
  }
  class FakeNetLoader extends AsyncTask<String, Void, String> {
  	private final ProgressDialog dialog = new ProgressDialog(context);

		protected void onPostExecute(String s) {			
			super.onPostExecute("meet");
			dialog.dismiss();
		}

		protected void onPreExecute(String s ) {		
			super.onPreExecute();
			Log.v("prehere","prehere");

			dialog.setMessage("Fetching Results...");
			dialog.show();			
		}
		@Override
		protected String doInBackground(String... params) {	
			
		
			  	String link = "http://www.google.com";
		

				
				 InputStream is = null;
			     JSONObject jObj = null;
			     String json = "";
				try {
		            // defaultHttpClient
		            DefaultHttpClient httpClient = new DefaultHttpClient();
		            HttpGet get1;
		          
					 get1 = new HttpGet("http://dev-mobileapi.stashd.org:8000/mwf_laptops?no_server_init");

		            HttpResponse httpResponse = httpClient.execute(get1);
		            HttpEntity httpEntity = httpResponse.getEntity();
		            is = httpEntity.getContent();           
		 
		        } catch (UnsupportedEncodingException e) {
		            e.printStackTrace();
		        } catch (ClientProtocolException e) {
		            e.printStackTrace();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		         
		        try {
		            BufferedReader reader = new BufferedReader(new InputStreamReader(
		                    is, "iso-8859-1"), 8);
		            StringBuilder sb = new StringBuilder();
		            String line = null;
		            while ((line = reader.readLine()) != null) {
		                sb.append(line + "\n");
		            }
		            is.close();
		            json = sb.toString();
		        } catch (Exception e) {
		            Log.e("Buffer Error", "Error converting result " + e.toString());
		        }
		        Log.v("JOSN",json);
		        int laptop = json.indexOf("College Library");
		        
		        String avail = json.substring(laptop, laptop+45);
		        int hyphen = avail.indexOf("-");
		        String avail1 = avail.substring(hyphen);
		        int num = avail1.indexOf("available");
		        String avail2 = avail1.substring(1,num);

		        Log.v("avail",avail2);
		        values[pos] = avail2;
		       /*
		        // try parse the string to a JSON object
		        try {
		                jObj = new JSONObject(json);
		            	String status = jObj.getString("status");
		            	if(status.equals("200"))
		            	{	
		            		Log.v("BIG TIME TESt","YO");
		            	}	
		            	
		        } catch (JSONException e) {
		            Log.e("JSON Parser", "Error parsing data " + e.toString());
		        }
		        */
	///////////////////////JUST MAKING THE FIRST REQUEST////////////////////////////////
				return "meet";
	
			}	
		
		}

} 