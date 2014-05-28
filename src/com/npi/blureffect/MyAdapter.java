package com.npi.blureffect;

import java.util.ArrayList;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapter extends ArrayAdapter<SearchBookRecord> { 
	private ArrayList<SearchBookRecord> items; 
    Context ctx; 
    SearchBookRecord b;
     
    public MyAdapter(Context context, int textViewResourceId, ArrayList<SearchBookRecord> items) { 
            super(context, textViewResourceId, items); 
            this.items = items; 
             
            ctx = context; 
    } 
     
    @Override 
     protected void finalize() throws Throwable { 
          super.finalize(); 
          
          // Clear out items 
         items.clear();  
         ctx = null; 
     } 
 
    @Override 
    //ViewGroup is parent, in this case ListView
    //convertView is for scrolling
    //position is the position of the view you're making
    //Note: do not do any computations in this getView(), otherwise messes up the view when loading
    public View getView(final int position, View convertView, ViewGroup parent) { 
         View v = convertView; 
         if (v == null) { 
        	 //creating new view
              LayoutInflater vi = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
              v = vi.inflate(R.layout.row, null);        
         } else { 
         } 
        try{
        	

        	b = items.get(position);

        	((TextView) v.findViewById(R.id.list_value)).setText(b.getTitle());
         	((TextView) v.findViewById(R.id.book_year)).setText(b.getAuthor() + " " +"(" + b.getYear() + ")");
         	if(!b.getAvailability()){
				ImageView iv = (ImageView) v.findViewById(R.id.availableIcon);
				iv.setImageResource(R.drawable.x_transparent);
         	}
         	else{
         		ImageView iv = (ImageView) v.findViewById(R.id.availableIcon);
         		iv.setImageResource(R.drawable.check_transparent);
         	}

        }catch(NullPointerException e){
        	Log.e("hi", "Object doesn't exist");
        }
     	 
           
        return v; 
    }
}
 