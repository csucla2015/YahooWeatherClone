package com.npi.blureffect;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.npi.blureffect.BookSort;
import com.npi.blureffect.MyAdapter;
import com.npi.blureffect.SearchBookRecord;

//Shows the list of search results for user queries
//Layout Style: result.xml
public class ArticleSearchClass extends Activity implements
		 EndlessListView.EndlessListener {
	private JSONArray mJSONResults;
	private String mQuery;
	private String mWebResult;
	private ProgressDialog pd;
	private String queryType;
	private MyAdapter adapter;
	private ArrayList<SearchBookRecord> mBookResults;
	private int mNextPageStart;
	protected boolean loadingMore;
	protected String mNextPage;
	private boolean canLoadMore = false;

	private EditText edittext;
	private final static int ITEM_PER_REQUEST = 50;
	EndlessListView lv;
    String result1 ="";
    //Holds the record set after the first request
	String recordset = "";
	int start =1 ;
	int end = 20;
	boolean flag = false;
    String[] results123 = null;

	int k=0;
	List<Articles> result44 = new ArrayList<Articles>();
	String mult = "a";
	
	String newdataset ="&startRecord=1&maxRecords=20";;
	protected final String TAG = "SearchResult";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.articlesearch);

		// Check for Internet connection
		

		ImageButton b = (ImageButton) findViewById(R.id.actionbar);
		
		Articles a = new Articles("","","","","","");
		result44.add(a);

		lv = (EndlessListView) findViewById(R.id.el);
		
		Log.v("Creates Endless Adapter", "Creates Endless Adapter");
		EndlessAdapter adp = new EndlessAdapter(this, createItems(result44), R.layout.row_layout);
		lv.setLoadingView(R.layout.loading_layout);
		lv.setAdapter(adp);
		
		lv.setListener(this);
		
		
		
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
			
				Log.v("position1",String.valueOf(id));
				TextView tv = (TextView) view.findViewById(R.id.txt1);
		    	String title = tv.getText().toString();
		    	tv.setOnClickListener(new OnClickListener() {
		    	  
					@Override
					public void onClick(View v) {
						Log.v("yo","yo");
						
						
					}
		    	});
		    	String author= "";
		    	String url ="";
		    	String dname ="";
		    	String source ="";
		    	String year ="";
		    	for (Iterator<Articles> iter = result44.iterator(); iter.hasNext(); ) {
				    Articles element = iter.next();
				    if(title.equals(element.getName()))
				    {	
				    	author = element.getAuthorName();
				    	url = element.getUrl();
				    	dname = element.getdatabaseName();
				    	source = element.getpubSource();
				    	year = element.getYear();
				    	break;
				    }	
				}
		    	Log.v("author",author);
		    	Intent i = new Intent(ArticleSearchClass.this, ArticleRecord.class);
		    	Bundle b = new Bundle();
		    	b.putString("title",title);
		    	b.putString("author", author);
		    	b.putString("url", url);
		    	b.putString("dname", dname);
		    	b.putString("source", source);
		    	b.putString("year", year);
				i.putExtras(b);

				startActivity(i);
				Log.v("clicked",title);
				Log.v("clicked", "clicked");
				
			}
		});

	}

	public void instantiate() {
		
	}

	public void setUpButton(int buttonId, final String query) {
		// replace with launchSearchActivity(type)
		final Button btn = (Button) findViewById(buttonId);
		btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (!queryType.equals(query)) {
					queryType = query;

					String searchKeywords = edittext.getText().toString();

					if (searchKeywords.equals("")) {
						searchKeywords = mQuery;
					}

					Intent i = new Intent(ArticleSearchClass.this, ArticleSearchClass.class);
					Bundle b = new Bundle();
					b.putString("searchKeywords", searchKeywords);
					b.putString("queryType", queryType);
					i.putExtras(b);

					startActivity(i);
				}
			}
		});
	}

	private ListView getListView() {
		return lv;
	}

	public void launchSearchResults(String type) {
		EditText edittext = (EditText) findViewById(R.id.searchBar);
		if (!queryType.equals(type)) {
			queryType = type;

			String searchKeywords = edittext.getText().toString();

			if (searchKeywords.equals("")) {
				searchKeywords = mQuery;
			}

			Intent i = new Intent(ArticleSearchClass.this, ArticleSearchClass.class);
			Bundle b = new Bundle();
			b.putString("searchKeywords", searchKeywords);
			b.putString("queryType", queryType);
			i.putExtras(b);

			startActivity(i);
		}
	}

	// DownloadTask is a thread that downloads the information from the database
	

		// SetObjectTask gets Availability Info
		

	public void goHome(View v) {
		Intent i = new Intent(ArticleSearchClass.this, Main.class);
		startActivity(i);
	}

	public void OnSearchButtonClick(View v) {

	}

	public void OnNoResults() {
		TextView tv = (TextView) findViewById(R.id.no_results);
		tv.setVisibility(View.VISIBLE);
		tv.setText("No results found.\nTap to reload.");
		tv.setClickable(true);
		tv.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String searchKeywords = edittext.getText().toString();

				if (searchKeywords.equals("")) {
					searchKeywords = mQuery;
				}

				Intent i = new Intent(ArticleSearchClass.this, ArticleSearchClass.class);
				Bundle b = new Bundle();
				b.putString("searchKeywords", searchKeywords);
				b.putString("queryType", queryType);
				i.putExtras(b);

				startActivity(i);
			}
		});

		if (ArticleSearchClass.this.pd != null) {
			ArticleSearchClass.this.pd.dismiss();
		}
	}




	//////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////FUNCTIONS FOR CREATING AN ENDLESS SCROLLABLE LIST///////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private class FakeNetLoader extends AsyncTask<String, Void, List<Articles>> {
    	private final ProgressDialog dialog = new ProgressDialog(ArticleSearchClass.this);

		@Override
		protected void onPostExecute(List<Articles> result) {			
			super.onPostExecute(result);
			dialog.dismiss();
			lv.addNewData(result);

		}

		@Override
		protected void onPreExecute() {		
			super.onPreExecute();
			Log.v("prehere","prehere");

			dialog.setMessage("Fetching Results...");
			dialog.show();			
		}
		@Override
		protected List<Articles> doInBackground(String... params) {	
			List<Articles> result = new ArrayList<Articles>();
			
			if(flag==true)
			{
				InputStream is1 = null;
				 Log.v("We made the more request. Yaay!!","We made the more request. Yaay!!");
				try 
				{
		            // defaultHttpClient
		            DefaultHttpClient httpClient = new DefaultHttpClient();
				  	HttpGet get1 = new HttpGet("https://articleapi.uclalibrary.org/async-ready?resultSet="+recordset+"&more=True");
		 
		            HttpResponse httpResponse = httpClient.execute(get1);
		            HttpEntity httpEntity = httpResponse.getEntity();
		            is1 = httpEntity.getContent();           
		 
		        } 
				catch (UnsupportedEncodingException e) {
		            e.printStackTrace();
		        } catch (ClientProtocolException e) {
		            e.printStackTrace();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
				}
		

				
				///////////////////////JUST MAKING THE FIRST REQUEST////////////////////////////////

			//	result.add("meet");
				
			  	String link = "https://articleapi.uclalibrary.org/async-cat?key=ucla&categories=103266";
		

				
				 InputStream is = null;
			     JSONObject jObj = null;
			     String json = "";
				try {
		            // defaultHttpClient
		            DefaultHttpClient httpClient = new DefaultHttpClient();
		            HttpGet get1;
		            if(flag==false)
		            	get1 = new HttpGet(link);
		            else
					  	get1 = new HttpGet("https://articleapi.uclalibrary.org/async-ready?resultSet="+recordset+"&more=True");

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
		       
		        // try parse the string to a JSON object
		        try {
		                jObj = new JSONObject(json);
		            	String status = jObj.getString("status");
		            	if(status.equals("200"))
		            	{	
		            		if(flag==false)
		            		recordset = jObj.getString("recordSetId");
		            		else
		            		recordset = jObj.getString("newid"); 
		            		Log.v("RECORDSET",recordset);
		            		//result.add(result1);
		            	}	
		            	
		        } catch (JSONException e) {
		            Log.e("JSON Parser", "Error parsing data " + e.toString());
		        }
	///////////////////////JUST MAKING THE FIRST REQUEST////////////////////////////////
		        
	///////////////////////MAKING THE SECOND REQUEST BRO/////////////////////////////////
		        if(flag==false)
				{
					flag=true;
				}
		        try {
					Thread.sleep(1000);
					String link1 = "https://articleapi.uclalibrary.org/async-ready?resultSet="+recordset;
					InputStream inputStream1 = null;
					String result12 = null;
					 InputStream is1 = null;
				     JSONObject jObj1 = null;
				     String json1 = "";
					try {
			            // defaultHttpClient
			            DefaultHttpClient httpClient = new DefaultHttpClient();
					  	HttpGet get1 = new HttpGet(link1);
			 
			            HttpResponse httpResponse = httpClient.execute(get1);
			            HttpEntity httpEntity = httpResponse.getEntity();
			            is1 = httpEntity.getContent();           
			 
			        } catch (UnsupportedEncodingException e) {
			            e.printStackTrace();
			        } catch (ClientProtocolException e) {
			            e.printStackTrace();
			        } catch (IOException e) {
			            e.printStackTrace();
			        }
			         
			        try {
			            BufferedReader reader = new BufferedReader(new InputStreamReader(
			                    is1, "iso-8859-1"), 8);
			            StringBuilder sb = new StringBuilder();
			            String line = null;
			            while ((line = reader.readLine()) != null) {
			                sb.append(line + "\n");
			            }
			            is1.close();
			            json1 = sb.toString();
			        } catch (Exception e) {
			            Log.e("Buffer Error", "Error converting result " + e.toString());
			        }
			        // try parse the string to a JSON object
			        try {
			                jObj1 = new JSONObject(json1);
			            	String status = jObj1.getString("status");
			            	String resultcount = jObj1.getString("resultCount");
			            	Log.v(resultcount,"RESULTCOUNT");
			            	if(status.equals("200"))
			            	{	
			            		Log.v("We Found a Match in RecordSet", "We Found a RecordSet");
			            		JSONArray dbases = jObj1.getJSONArray("ready");
			            		Log.v(String.valueOf(dbases.length()), "Length of the ResultSet Array");
				            	 results123 = new String[dbases.length()];
				            	for(int i=0;i<dbases.length();i++)
				            	{	
				            		results123[i]=dbases.getString(i);
				            		Log.v("Databases", dbases.getString(i));
				            	}
				            	//requestdata(results, recordset);
			            	}	
			            	
			        } catch (JSONException e) {
			            Log.e("JSON Parser", "Error parsing data " + e.toString());
			        }
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		        //result.add(result1);
		        
			
	///////////////////////ENDING THE SECOND REQUEST BRO/////////////////////////////////
	//////////////////////STARTING THE THIRD REQUEST BRO/////////////////////////////////
			try {
				Thread.sleep(1000);
			    Log.v("Request made to final function", "Request made to final funciton");
			 	for(int i1=0;i1<results123.length;i1++)
			 	{	
			 	String link11 = "https://articleapi.uclalibrary.org/async-resultSet?resultSet="+recordset+"&database="+results123[i1]+newdataset;
			  	Log.v("link",link11);
			 	HttpGet get1 = new HttpGet(link11);
			
		
				 InputStream is11 = null;
			     JSONObject jObj11 = null;
			     String json11 = "";
				try {
		            // defaultHttpClient
		            DefaultHttpClient httpClient = new DefaultHttpClient();
				  	HttpGet get11 = new HttpGet(link11);
		 
		            HttpResponse httpResponse = httpClient.execute(get11);
		            HttpEntity httpEntity = httpResponse.getEntity();
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
		            while ((line = reader.readLine()) != null) {
		                sb.append(line + "\n");
		            }
		            is11.close();
		            json11 = sb.toString();
		        } catch (Exception e) {
		            Log.e("Buffer Error", "Error converting result " + e.toString());
		        }

		        // try parse the string to a JSON object
		        try {
		                jObj11 = new JSONObject(json11);
		                JSONObject object = new JSONObject(json11);
		                JSONObject myObject = object.getJSONObject(results123[i1]);
	                	Log.v("Length",String.valueOf(myObject.length()));
	                	Log.v("Dynamic","Dynamic");
		            	JSONObject j_obj = jObj11.getJSONObject(results123[i1]);
		            	@SuppressWarnings("unchecked")
						Iterator<String> keys = j_obj.keys();
		                while(keys.hasNext()){
		                    String key = keys.next();
		                	Log.v("key",key);

		                    JSONObject j_obj1 = j_obj.getJSONObject(String.valueOf(key));
			            	String url = j_obj1.getString("url");
			            	String author = j_obj1.getString("author");
			            	String title = j_obj1.getString("title");
			            	String year = "Date : " +j_obj1.getString("date");
			            	
			            	String dbaseName = j_obj1.getString("databaseName");
			            	String source ="";
			            	source = "Abstract : " +j_obj1.getString("abstract");
			            	Log.v("source", source);
			            	if(source != "null" && !source.isEmpty())			            		
			            		source +="\n Publication Source : " + j_obj1.getString("pubSource");
			            	else
			            		source  = "Abstract : Sorry no abstract found \n" + "Publication Source : " +j_obj1.getString("pubSource");
			            	
			                  // Put elements to the map
			            	Articles temp = new Articles(title,author,year,url,dbaseName,source);
			            	result.add(temp);
			            	result44.add(temp);
			              //  hm.put(test,title);;
		                    
		                } 
/*
		            	
		            	
*/
		        } catch (JSONException e) {
		            Log.e("JSON Parser", "Error parsing data " + e.toString());
		            
		        }
			 	}
			 /*	Set set = hm.entrySet();
	          // Get an iterator

	          Iterator ie = set.iterator();
	          // Display elements
	          int i=0;
	          while(ie.hasNext()) {
	             Map.Entry me = (Map.Entry)ie.next();
	             Articles b = new Articles((String) me.getValue());
	         	result.add(b);
	          }*/
				return createItems(result);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		    
			return createItems(result);

			}	
		
		}



		
		
	
	
	
	
	private List<Articles> createItems(List<Articles> result) {
		return result;
	}

	public void loadData() {
		Log.v("We are loading data", "We are loading data");
		// We load more data here
		FakeNetLoader fl = new FakeNetLoader();
		fl.execute("https://articleapi.uclalibrary.org/async-cat?key=ucla&categories=103266");
		newdataset = "&startRecord="+String.valueOf(start)+"&maxRecords="+String.valueOf(end);
		start+=20;
		end+=20;
		InputStream inputStream1 = null;
		String result12 = null;
	
	}
}