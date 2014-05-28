
	package com.npi.blureffect;

	import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

	import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
	import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
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
	public class ArticleRecord extends Activity {
		protected String title;
		protected String author;
		protected String year;
		protected String isbn;
		protected String descript;
		protected String[] descriptions;
		protected String availDescript;
		protected ArrayList<ReviewObject> mReviews;
		protected boolean checkedAvail;
		protected String oclc;
		protected String reviewURL;

		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			
			setContentView(R.layout.articlerecord);
			Bundle b = getIntent().getExtras();
			String title = b.getString("title");
			
			String author = b.getString("author");
			String date = b.getString("year");
			String dname = b.getString("dname");
			String source = b.getString("source");
			String url = b.getString("url");
			
			((TextView) findViewById(R.id.bookTitle)).setText(title);
			((TextView) findViewById(R.id.bookAuthor)).setText(author);
			TextView t1 = ((TextView) findViewById(R.id.bookYear));
			t1.setTextSize(18);
			t1.setText( Html.fromHtml("<a href="+url+"\">Click here to access the PDF</a>"));
			t1.setMovementMethod(LinkMovementMethod.getInstance());
			TableLayout tl = (TableLayout) findViewById(R.id.description);

			TableRow tr = new TableRow(ArticleRecord.this);

			TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(
					TableLayout.LayoutParams.FILL_PARENT,
					TableLayout.LayoutParams.WRAP_CONTENT);

			tableRowParams.setMargins(10, 10, 10, 10);

			tr.setLayoutParams(tableRowParams);

			TextView tv = new TextView(ArticleRecord.this);
			tv.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
					LayoutParams.WRAP_CONTENT));
			tv.setText(source+ "\n" + date);
			tv.setMaxLines(25);
			tv.setPadding(20, 20, 20, 20);
			tr.addView(tv);


			tl.addView(tr, new TableLayout.LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
}

		
}