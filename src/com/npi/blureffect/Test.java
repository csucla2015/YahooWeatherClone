package com.npi.blureffect;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
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

import com.npi.blureffect.EndlessListView.EndlessListener;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
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

/*
import com.google.android.apps.analytics.GoogleAnalyticsTracker;
import com.simul8.library.SlideMenuInterface.OnSlideMenuItemClickListener;
import com.simul8.util.BookSort;
import com.simul8.util.LibraryActivity;
import com.simul8.util.MiscellaneousFunctions;
import com.simul8.util.MyAdapter;
import com.simul8.util.SearchBookRecord;*/

//Shows the list of search results for user queries
//Layout Style: result.xml
public class Test extends Activity implements EndlessListView.EndlessListener {
	private JSONArray mJSONResults;
	private String mWebResult;
	private ProgressDialog pd;
//	private ArrayList<SearchBookRecord> mBookResults;
	private int mNextPageStart;
	protected boolean loadingMore;
	protected String mNextPage;
	String newlink ="";
	private boolean canLoadMore = false;
	String searchQuery ="";
  	String searchid;
	int startindex = 1;
	int endindex = 20;
	boolean done = false;
	String numresults = "";
	String issn = "";
	String isbn = "";
	private EditText edittext;
	private final static int ITEM_PER_REQUEST = 50;
	EndlessListView lv;
    String result1 ="";
    //Holds the record set after the first request
	String recordset = "";
	int start =1 ;
	int end = 20;
	boolean flag = true;
    String[] results123 = null;
    String queryType ="";
	int k=0;
	List<Articles> result44 = new ArrayList<Articles>();
	String mult = "a";
	
	String newdataset ="&startRecord=1&maxRecords=20";;
	protected final String TAG = "SearchResult";

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.articlesearch);

		// Check for Internet connection

		Bundle b1 = getIntent().getExtras();
		searchQuery = b1.getString("searchKeywords");
		searchQuery = searchQuery.replaceAll(" ", "%20");
		queryType = b1.getString("queryType");

	  //	newlink = "http://mobileapi-dev.uclalibrary.org/voyager_search/"+searchQuery+"/"+queryType+"/20";

	  	newlink = "http://mobileapi-dev.uclalibrary.org/voyager_search/ucla/"+queryType+"/20/";
	  	
		Log.v("this is my newlink",newlink);
		
		Articles a = new Articles("Search Results for " + searchQuery,"","","","","");
		result44.add(a);

		lv = (EndlessListView) findViewById(R.id.el);
		
		Log.v("Creates Endless Adapter", "Creates Endless Adapter");
		EndlessAdapter adp = new EndlessAdapter(this, createItems(result44), R.layout.row_layout);
		lv.setLoadingView(R.layout.loading_layout);
		lv.setAdapter(adp);
		
		lv.setListener(this);
		
		setUpButton(R.id.keywordButton, "GKEY");
		setUpButton(R.id.titleButton, "TKEY");
		setUpButton(R.id.authorButton, "NKEY");
		setUpButton(R.id.subjectButton, "SKEY");
		if (queryType.equals("GKEY"))
			((Button) findViewById(R.id.keywordButton))
				.setBackgroundColor(Color.parseColor("#99333333"));
		else if (queryType.equals("TKEY"))
			((Button) findViewById(R.id.titleButton))
					.setBackgroundColor(Color.parseColor("#99333333"));

		else if (queryType.equals("NEKY"))
			((Button) findViewById(R.id.authorButton))
					.setBackgroundColor(Color.parseColor("#99333333"));

		else if (queryType.equals("SKEY"))
			((Button) findViewById(R.id.subjectButton))
					.setBackgroundColor(Color.parseColor("#99333333"));


		
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
		    	String bibid ="";
		    	String source ="";
		    	String year ="";
		    	for (Iterator<Articles> iter = result44.iterator(); iter.hasNext(); ) {
				    Articles element = iter.next();
				    if(title.equals(element.getName()))
				    {	
				    	author = element.getAuthorName();
				    	url = element.getUrl();
				    	bibid = element.getdatabaseName();
				    	source = element.getpubSource();
				    	year = element.getYear();
				    	isbn = element.getpubSource();
				    	break;
				    }	
				}
		    	Log.v("author",author);
		    	Intent i = new Intent(Test.this, BookRecord.class);
		    	Bundle b = new Bundle();
		    	b.putString("title",title);
		    	b.putString("author", author);
		    	b.putString("url", url);
		    	b.putString("bibid", bibid);
		    	b.putString("source", source);
		    	b.putString("year", year);
		    	b.putString("issn", issn);
		    	b.putString("isbn", isbn);

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
				btn.setBackgroundColor(Color.parseColor("#99333333"));

				Log.v("we reach ehre", "wea reach here");
				if (!queryType.equals(query)) {
					queryType = query;

					String searchKeywords = "";

					if (searchKeywords.equals("")) {
						searchKeywords = searchQuery;
					}
					Log.v("we reach ehr1e", "wea reach here1");

					Intent i = new Intent(Test.this, Test.class);
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
		if (!queryType.equals(type)) {
			queryType = type;

			String searchKeywords = "";
			if (searchKeywords.equals("")) {
				searchKeywords = searchQuery;
			}

			Intent i = new Intent(Test.this, Test.class);
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
		Intent i = new Intent(Test.this, Main.class);
		startActivity(i);
	}

	public void OnSearchButtonClick(View v) {
		//util.OnSearchButtonClick(this, v);

	}

	public void OnNoResults() {
		TextView tv = (TextView) findViewById(R.id.no_results);
		tv.setVisibility(View.VISIBLE);
		tv.setText("No results found.\nTap to reload.");
		tv.setClickable(true);
		tv.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				String searchKeywords = "";

				if (searchKeywords.equals("")) {
					searchKeywords = searchQuery;
				}

				Intent i = new Intent(Test.this, Test.class);
				Bundle b = new Bundle();
				b.putString("searchKeywords", searchKeywords);
				b.putString("queryType", queryType);
				i.putExtras(b);

				startActivity(i);
			}
		});

		if (Test.this.pd != null) {
			Test.this.pd.dismiss();
		}
	}

	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home: // this is the app icon of the actionbar
			//slidemenu.show();
			break;
		}
		return super.onOptionsItemSelected(item);
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
	
	private class FakeNetLoader extends AsyncTask<String, Void, List<Articles>> 
	{
    	private final ProgressDialog dialog = new ProgressDialog(Test.this);

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
			if(flag == false)
			{	
				StringBuilder builder = new StringBuilder();
				for(String s : params) {
				    builder.append(s);
				}
				newlink = builder.toString();
			Log.v("final",newlink);
			}
			
			List<Articles> result = new ArrayList<Articles>();
				InputStream is1 = null;
				 Log.v("We made the more request. Yaay!!","We made the more request. Yaay!!");
			
				///////////////////////JUST MAKING THE FIRST REQUEST////////////////////////////////
			//	result.add("meet");
				 Log.v("searchQuary", searchQuery);
			  	String link = "http://mobileapi-dev.uclalibrary.org/voyager_search/"+searchQuery+"/"+queryType+"/20/";
				 InputStream is = null;
			     JSONObject jObj = null;
			     String json = "";
				try {
		            // defaultHttpClient
		            DefaultHttpClient httpClient = new DefaultHttpClient();
		            HttpGet get1;
		            Log.v("newlink",newlink);
		            get1 = new HttpGet(newlink);
		      
		            HttpResponse httpResponse = httpClient.execute(get1);
		            HttpEntity httpEntity = httpResponse.getEntity();
		            Log.v("Newlink", newlink);
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
		        String bibid="";
		        // try parse the string to a JSON object
		        try {
		                jObj = new JSONObject(json);
		                if(flag==true)
		                {
		            	searchid = jObj.getString("searchid");
		    			flag= false;
		                }
		            	Log.v("SearchId", searchid);
		            
		            	JSONArray searchResults = jObj.getJSONArray("results");
		            	Log.v("ArraySize", String.valueOf(searchResults.length()));
		            	if(searchResults.length()<18)
		            		done =true;
		            	for (int i=0; i<searchResults.length(); i++) {
		            	    JSONObject item = searchResults.getJSONObject(i);
		            	    String avail = item.getString("itemstatuscode");
		            	    String elink = item.getString("elink");
		            	    Log.v("Avail", avail );
		            	    String title = item.getString("bibtext2");
		            	    String author = item.getString("bibtext1");
		            	    issn = item.getString("issn");
		            	    isbn = item.getString("isbn");
		            	     bibid = item.getString("bibid");
		            	    if(author=="null")
		            	    	author = "anonymous";
		            	    //////////////////////////////////
		            	    //////////////////////////////////
		            	  
		    		        //////////////////////////////////////////
		    		        //////////////////////////////////////////
		    		        //////////////////////////////////////////
		            	    Articles temp = new Articles(title,author,avail,elink,bibid,isbn);
			            	result.add(temp);
		            	}
		            	Thread.sleep(1000);
		        } catch (JSONException e) {
		            Log.e("JSON Parser", "Error parsing data " + e.toString());
		        }
	///////////////////////JUST MAKING THE FIRST REQUEST////////////////////////////////
 catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        //////////////////////////////////////////////////////////////////////////////
		        ////////////////MAKING ANOTHER REQUEST////////////////////////////////////////
		        //////////////////////////////////////////////////////////////////////////////
		        
		       
		        return createItems(result);
			}	
		
		}

	private List<Articles> createItems(List<Articles> result) 
	{
		return result;
	}

	public void loadData() 
	{
		if(done != true)
		{	
		Log.v("We are loading data", "We are loading data");
		// We load more data here
		FakeNetLoader fl = new FakeNetLoader();
		startindex +=20;
		endindex +=20;
		fl.execute("http://mobileapi-dev.uclalibrary.org/voyager_search_results/"+searchid+"/"+startindex+"/"+endindex+"/");
		newdataset = "&startRecord="+String.valueOf(start)+"&maxRecords="+String.valueOf(end);
		start+=20;
		end+=20;
		InputStream inputStream1 = null;
		String result12 = null;
		}
	
	}
}
