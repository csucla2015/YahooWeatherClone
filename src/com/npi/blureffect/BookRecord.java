
	package com.npi.blureffect;

	import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

	
	//Individual Item Level (screen that shows up when user clicks on a result in the SearchResult.java page
	//Layout Style: bookrecord.xml
	public class BookRecord extends Activity {
		protected String title;
		protected String author;
		protected String year;
		protected String isbn;
		protected String descript;
		protected String[] descriptions;
		protected String availDescript;
		//protected ArrayList<ReviewObject> mReviews;
		protected boolean checkedAvail;
		protected String oclc;
		String bibid;
		String imp="";
		String imp1="";
		String imp2="";
		String imp3="";
		String avail="";
		String imp4="";
		JSONArray jObj12;
		String imp5="";
		String avail1= "No records found";
		boolean reviewsExist = false;
		protected String reviewURL;
		String revs="";
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			
			setContentView(R.layout.bookrecord);
			Bundle b = getIntent().getExtras();
			String title = b.getString("title");
			
			String author = b.getString("author");
			avail = b.getString("year");
			Log.v("avail",avail);
			bibid = b.getString("bibid");
			Log.v("avail1",bibid);

			String source = b.getString("source");
			String url = b.getString("url");
			String isbn = b.getString("isbn");
			((TextView) findViewById(R.id.bookTitle)).setText(title);
			((TextView) findViewById(R.id.bookAuthor)).setText(author);
			//((TextView) findViewById(R.id.bookYear)).setText(isbn);
			FakeNetLoader fl = new FakeNetLoader();
			
			
			fl.execute("http://mobileapi-dev.uclalibrary.org/voyager_record/"+bibid+"/");
}

		
		private class FakeNetLoader extends AsyncTask<String, Void, List<Articles>> 

		{

			@Override
			protected void onPostExecute(List<Articles> result) {	
				
				///////////////////////////////////////////////////////////////////////
				ProgressBar progressBar = (ProgressBar) findViewById(R.id.availprog);

				progressBar.setVisibility(View.GONE);
				ProgressBar progressBar1 = (ProgressBar) findViewById(R.id.reviewsprog);

				progressBar1.setVisibility(View.GONE);

				super.onPostExecute(result);
				TableLayout tl = (TableLayout) findViewById(R.id.description);

					TableRow tr = new TableRow(BookRecord.this);

					TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(
							TableLayout.LayoutParams.FILL_PARENT,
							TableLayout.LayoutParams.WRAP_CONTENT);

					tableRowParams.setMargins(10, 10, 10, 10);

					tr.setLayoutParams(tableRowParams);

					TextView tv = new TextView(BookRecord.this);
					tv.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
							LayoutParams.WRAP_CONTENT));
					tv.setText(imp+imp1+imp2+imp3+imp4+imp5);
					tv.setTextSize(16);
					tv.setMaxLines(5);
					tv.setPadding(20, 20, 20, 20);
					tr.addView(tv);
					tv.setTextColor(Color.parseColor("#FFFFFF"));


					tl.addView(tr, new TableLayout.LayoutParams(
							LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
					
					///////////////////////////////////////////////////////////////////////////////////
					/*
					TableLayout tl1 = (TableLayout) findViewById(R.id.availDescript);

					TableRow tr1 = new TableRow(BookRecord.this);

					TableLayout.LayoutParams tableRowParams1 = new TableLayout.LayoutParams(
							TableLayout.LayoutParams.FILL_PARENT,
							TableLayout.LayoutParams.WRAP_CONTENT);

					tableRowParams1.setMargins(10, 10, 10, 10);

					tr1.setLayoutParams(tableRowParams1);

					TextView tv1 = new TextView(BookRecord.this);
					tv1.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
							LayoutParams.WRAP_CONTENT));
					tv1.setText(avail1);
					tv1.setMaxLines(5000);
					tv1.setPadding(20, 20, 20, 20);
					tr1.addView(tv1);


					tl1.addView(tr1, new TableLayout.LayoutParams(
							LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
					*/
					/*
					if(reviewsExist == true && isbn!=null)
					{
						TableLayout tl11 = (TableLayout) findViewById(R.id.reviewTable);
						int count = 0;
							TableRow tr11 = new TableRow(BookRecord.this);

							TableLayout.LayoutParams tableRowParams11 = new TableLayout.LayoutParams(
									TableLayout.LayoutParams.FILL_PARENT,
									TableLayout.LayoutParams.WRAP_CONTENT);

							tableRowParams11.setMargins(10, 10, 10, 10);

							tr11.setLayoutParams(tableRowParams11);

							TextView tv11 = new TextView(BookRecord.this);
							tv11.setLayoutParams(new LayoutParams(
									LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
							String rating = "<br><font color=#B4B4B4>" + "Meet"
									+ " Stars</font>";
							tv11.setText(Html.fromHtml(revs));
							tv11.setSingleLine(false);

							tv11.setPadding(20, 20, 20, 20);
							tr11.addView(tv11);
							tl11.addView(tr11, new TableLayout.LayoutParams(
									LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

						
					}
					*/
					///////////////////////////////////////////////////////////////////
					////////////////Trying to do Shahid like format///////////////////
					  TableLayout availtl = (TableLayout) findViewById(R.id.availDescript);
						ArrayList<AvailabilityObject> mAvail = new ArrayList<AvailabilityObject>();
						try {
							((ProgressBar) findViewById(R.id.availprog))
									.setVisibility(View.GONE);
							JSONArray avail = jObj12;
							availDescript = "";
							System.out.println(avail.length());
							for (int i = 0; i != avail.length(); i++) 
							{
								String location = "";
								String callnumber = "";
								String bar ="";
								String checked ="";
								JSONObject availOb = avail.getJSONObject(i);
								JSONObject jObj11 = jObj12.getJSONObject(i);
						           
					            String isavail = jObj11.getString("itemStatus");
					            if(isavail.equals("Not Charged"))
					            	checked= "Available";
					            else
					            	checked= "Checked Out";
					            location = jObj11.getString("location")+ " \n";
					            callnumber=jObj11.getString("callNumber")+" \n";
					            bar = jObj11.getString("itemBarcode")+" \n";
								mAvail.add(new AvailabilityObject(checked,location,callnumber, bar));
							}
							int count = 0;
							System.out.println("*****" + mAvail.size());
							if (mAvail.size() == 0) 
							{
								mAvail.add(new AvailabilityObject("Not available at UCLA",
										"", "", ""));
							}
							for (AvailabilityObject r : mAvail) 
							{

								if (r.getLocation().equals("Not available at UCLA")) 
								{
									availDescript = r.getLocation();
								} 
								else 
								{
									availDescript = r.getLocation() + ", " + r.getCallNo()
											+ ", " + r.getNumber() +","+ r.getStatus();
								}
								
								TableRow tr11 = new TableRow(BookRecord.this);
								TableLayout.LayoutParams tableRowParams11 = new TableLayout.LayoutParams(
										TableLayout.LayoutParams.FILL_PARENT,
										TableLayout.LayoutParams.WRAP_CONTENT);

								tableRowParams11.setMargins(10, 10, 10, 10);

								tr11.setLayoutParams(tableRowParams11);
								LinearLayout linLayout = new LinearLayout(BookRecord.this);
								TextView tv11 = new TextView(BookRecord.this);
								tv11.setTextColor(Color.parseColor("#FFFFFF"));
								tv11.setTextSize(16);
								tv11.setSingleLine(false);
								tv11.setLayoutParams(new LayoutParams(
										LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

								if (availDescript.equals(""))
									tv11.setText("Not available at UCLA");
								else
									tv11.setText(availDescript);
								linLayout.addView(tv11);
								tv11.setLayoutParams(new LinearLayout.LayoutParams(-2, -2, 1));
								
								System.out.println(availDescript);
								tv11.setPadding(20, 20, 20, 20);
								tr11.addView(linLayout);

								tr11.setClickable(true);

								tr11.setOnClickListener(new View.OnClickListener() 
								{
									@Override
									public void onClick(View v) {
										TableRow tr = (TableRow) v;
										LinearLayout l = (LinearLayout) tr.getChildAt(0);
										TextView tv = (TextView) l.getChildAt(0);
										String text = tv.getText().toString();
										String callno = "";

										String words[] = text.split(" ");
										for (String w : words) 
										{
											if (!w.equals("College")
													&& !w.equals("Library,")) 
											{
												callno = w;
												break;
											}
										}

										System.out.println("CallNO: " + callno);
										if (words[0].equals("College")
												&& words[1].equals("Library,")) 
										{
											String url = "http://ios1.simul8group.org/callNum/libmap.php?lib=college&&num="
													+ callno
													+ "&&title="
													+ title.replaceAll(" ", "%20");
											
										}
									}
								});
								availtl.addView(tr11,
										new TableLayout.LayoutParams(
												LayoutParams.FILL_PARENT,
												LayoutParams.WRAP_CONTENT));
								if (count != mAvail.size() - 1) 
								{
									View v = new View(BookRecord.this);
									v.setLayoutParams(new TableRow.LayoutParams(
											TableRow.LayoutParams.FILL_PARENT, 1));
									v.setBackgroundColor(Color.rgb(171, 171, 171));
									availtl.addView(v);
								}
								count++;
							}

						} 
						catch (JSONException e) 
						{
							e.printStackTrace();
						}				

			}
			
			
			@Override
			protected void onPreExecute() {		
				super.onPreExecute();
				Log.v("prehere","prehere");

							
			}
			@Override
			protected List<Articles> doInBackground(String... params) {
				  String link1 = "http://mobileapi-dev.uclalibrary.org/voyager_record/"+bibid+"/";
					InputStream is11 = null;
				    JSONObject jObj1 = null;
				     String json1 = "";
					try {
				        // defaultHttpClient
				        DefaultHttpClient httpClient = new DefaultHttpClient();
				        HttpGet get1;
				        Log.v("newlink",link1);
				        get1 = new HttpGet(link1);
				  
				        HttpResponse httpResponse = httpClient.execute(get1);
				        HttpEntity httpEntity = httpResponse.getEntity();
				        Log.v("Newlink", link1);
				        is11 = httpEntity.getContent();           

				    } catch (UnsupportedEncodingException e) {
				        e.printStackTrace();
				    } catch (ClientProtocolException e) {
				        e.printStackTrace();
				    } catch (IOException e) {
				        e.printStackTrace();
				    }
					
				    try {
				        BufferedReader reader = new BufferedReader(new InputStreamReader(
				                is11, "iso-8859-1"), 8);
				        StringBuilder sb = new StringBuilder();
				        String line = null;
				        while ((line = reader.readLine()) != null) 
				        {
				            sb.append(line + "\n");
				        }
				        is11.close();
				        json1 = sb.toString();
				    } 
				    catch (Exception e) 
				    {
				        Log.e("Buffer Error", "Error converting result " + e.toString());
				    }
				    // try parse the string to a JSON object
				    try 
				    {
				            jObj1 = new JSONObject(json1);
				            imp = jObj1.getString("260");
				            if(imp.length()<8)
				            	imp="";
				            Log.v("Description",imp);
				            Log.v("DONE","DONE");    	
				    } catch (JSONException e) 
				    {
				        Log.e("JSON Parser", "Error parsing data " + e.toString());
				    }
				///////////////////////JUST MAKING THE FIRST REQUEST////////////////////////////////
				    
				    try 
				    {
				            jObj1 = new JSONObject(json1);
				            imp1 = jObj1.getString("245");
				            if(imp1.length()<8)
				            	imp1="";
				            Log.v("Description",imp1);
				            Log.v("DONE","DONE");    	
				    } catch (JSONException e) 
				    {
				        Log.e("JSON Parser", "Error parsing data " + e.toString());
				    }
				///////////////////////JUST MAKING THE FIRST REQUEST////////////////////////////////
				 
				    try 
				    {
				            jObj1 = new JSONObject(json1);
				            imp2 = jObj1.getString("300");
				            if(imp2.length()<8)
				            	imp2="";
				            Log.v("Description",imp2);
				            Log.v("DONE","DONE");    	
				        	Thread.sleep(1000);
				    } catch (JSONException e) 
				    {
				        Log.e("JSON Parser", "Error parsing data " + e.toString());
				    }
				///////////////////////JUST MAKING THE FIRST REQUEST////////////////////////////////
				    catch (InterruptedException e) 
				    {
						e.printStackTrace();
					}
				    try 
				    {
				            jObj1 = new JSONObject(json1);
				            imp3 = jObj1.getString("440");
				            if(imp3.length()<8)
				            	imp3="";
				            Log.v("Description",imp3);
				            Log.v("DONE","DONE");    	
				    } catch (JSONException e) 
				    {
				        Log.e("JSON Parser", "Error parsing data " + e.toString());
				    }
				///////////////////////JUST MAKING THE FIRST REQUEST////////////////////////////////
				
				    try 
				    {
				            jObj1 = new JSONObject(json1);
				            imp4 = jObj1.getString("500");
				            if(imp4.length()<8)
				            	imp4="";
				            Log.v("Description",imp4);
				            Log.v("DONE","DONE");    	
				    } catch (JSONException e) 
				    {
				        Log.e("JSON Parser", "Error parsing data " + e.toString());
				    }
				///////////////////////JUST MAKING THE FIRST REQUEST////////////////////////////////
				  
				    try 
				    {
				            jObj1 = new JSONObject(json1);
				            imp5 = jObj1.getString("505");
				            if(imp5.length()<8)
				            	imp5="";
				            Log.v("Description",imp5);
				            Log.v("DONE","DONE");    	
				        	Thread.sleep(1000);
				    } catch (JSONException e) 
				    {
				        Log.e("JSON Parser", "Error parsing data " + e.toString());
				    }
				    
				///////////////////////JUST MAKING THE FIRST REQUEST////////////////////////////////
				    catch (InterruptedException e) 
				    {
						e.printStackTrace();
					}	
					
				    //////////////////////////////////////////////////////////////////////////////////
				    ///////////////MAKING REQUEST FOR REVIEWS/////////////////////////////////////////
					
				    /*
				    if(isbn!=null)
				    {	
				    String link11 = "http://www.goodreads.com/book/isbn?key=WDaNRTuacYVuQG56w1ZiAQ&format=json&isbn=9788478888566";
					InputStream is111 = null;
				    JSONObject jObj11 = null;
				     String json11 = "";
					try {
				        // defaultHttpClient
				        DefaultHttpClient httpClient = new DefaultHttpClient();
				        HttpGet get1;
				        Log.v("newlink",link11);
				        get1 = new HttpGet(link11);
				  
				        HttpResponse httpResponse = httpClient.execute(get1);
				        HttpEntity httpEntity = httpResponse.getEntity();
				        Log.v("Newlink", link11);
				        is111 = httpEntity.getContent();           

				    } catch (UnsupportedEncodingException e) {
				        e.printStackTrace();
				    } catch (ClientProtocolException e) {
				        e.printStackTrace();
				    } catch (IOException e) {
				        e.printStackTrace();
				    }
				    try {
				        BufferedReader reader = new BufferedReader(new InputStreamReader(
				                is111, "iso-8859-1"), 8);
				        StringBuilder sb = new StringBuilder();
				        String line = null;
				        while ((line = reader.readLine()) != null) 
				        {
				            sb.append(line + "\n");
				        }
				        is111.close();
				        json11 = sb.toString();
				    } 
				    catch (Exception e) 
				    {
				        Log.e("Buffer Error", "Error converting result " + e.toString());
				    }
				    // try parse the string to a JSON object
				    try 
				    {
				            jObj11 = new JSONObject(json11);
				        	JSONArray dbases = jObj11.getJSONArray("reviews");
		            		Log.v(String.valueOf(dbases.length()), "Length of the ResultSet Array");
		            		String reviews ="";
			            	for(int i=0;i<2;i++)
			            	{	
			            		JSONObject j =dbases.getJSONObject(i);
			            		 reviews = j.getString("body"); 
			            		 reviewsExist = true;
			            		 revs += reviews + "\n";
			            		Log.v("Databases", reviews);
			            	}
				            Log.v("Description",reviews);
				            Log.v("DONE","DONE");    	
				    } catch (JSONException e) 
				    {
				        Log.e("JSON Parser", "Error parsing data " + e.toString());
				    }
				///////////////////////JUST MAKING THE FIRST REQUEST////////////////////////////////
				    }
				    /////////////////////////////////////////////////////////////////////////////
				    /////Link for availibility//////////////////////////////////////////////////
				    ////////////////////////////////////////////////////////////////////////////
				    ////http://articles-dev.stashd.org:8000/voyager_holdings/485064/////////////
				  
				    try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				    boolean available =true;
				    if(avail.charAt(0)!='1')
				    {
				    	available = false;
				    }
				    */
				    
				    Log.v("WHT","W");
				    String link2 = "http://mobileapi-dev.uclalibrary.org/voyager_holdings/"+bibid+"/";
					InputStream is2 = null;
				    JSONObject jObj2 = null;
				    String json2 = "";
					try {
				        // defaultHttpClient
				        DefaultHttpClient httpClient = new DefaultHttpClient();
				        HttpGet get2;
				        Log.v("newlink",link2);
				        get2 = new HttpGet(link2);
				  
				        HttpResponse httpResponse = httpClient.execute(get2);
				        HttpEntity httpEntity = httpResponse.getEntity();
				        Log.v("Newlink", link2);
				        is11 = httpEntity.getContent();           

				    } catch (UnsupportedEncodingException e) {
				        e.printStackTrace();
				    } catch (ClientProtocolException e) {
				        e.printStackTrace();
				    } catch (IOException e) {
				        e.printStackTrace();
				    }
				    try {
				        BufferedReader reader = new BufferedReader(new InputStreamReader(
				                is11, "iso-8859-1"), 8);
				        StringBuilder sb = new StringBuilder();
				        String line = null;
				        while ((line = reader.readLine()) != null) 
				        {
				            sb.append(line + "\n");
				        }
				        is11.close();
				        json1 = sb.toString();
				    } 
				    catch (Exception e) 
				    {
				        Log.e("Buffer Error", "Error converting result " + e.toString());
				    }
				    // try parse the string to a JSON object
				    try 
				    {
				            jObj12 = new JSONArray(json1);
				            Log.v("Length of array", String.valueOf(jObj12.length()));
				            for(int i = 0 ; i<jObj12.length() ; i++)
				            {	
					            
					            Log.v("Description",imp);
					            Log.v("DONE","DONE");    	
				            }	
				    } catch (JSONException e) 
				    {
				        Log.e("JSON Parser", "Error parsing data " + e.toString());
				    }
				    
				    
				  


				    
				///////////////////////JUST MAKING THE FIRST REQUEST////////////////////////////////
				 
				    
					return null;

				}
			
		}	
	
	}
	
	
	
	
	
	
	
	/*
	  //////////////////////////////////
  
	*/