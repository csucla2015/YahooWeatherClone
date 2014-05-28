package com.npi.blureffect;

/*
 * Copyright (C) 2012 Surviving with Android (http://www.survivingwithandroid.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class EndlessAdapter extends ArrayAdapter<Articles> {
	
	private List<Articles> itemList;
	private Context ctx;
	private int layoutId;
	
	public EndlessAdapter(Context ctx, List<Articles> itemList, int layoutId) {
		super(ctx, layoutId, itemList);
		this.itemList = itemList;
		this.ctx = ctx;
		this.layoutId = layoutId;
	}

	@Override
	public int getCount() {		
		return itemList.size() ;
	}

	@Override
	public Articles getItem(int position) {		
		return itemList.get(position);
	}

	@Override
	public long getItemId(int position) {		
		return itemList.get(position).hashCode();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View result = convertView;
		
		if (result == null) {
			LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			result = inflater.inflate(layoutId, parent, false);
		}
		 

	   //     TextView iv = (TextView) result.findViewById(R.id.txt2);
	    //    iv.setOnClickListener((OnClickListener) this);
		// We should use class holder pattern
		TextView tv = (TextView) result.findViewById(R.id.txt1);
       // tv.setOnClickListener( this);
		tv.setText(itemList.get(position).getName());
		Log.v("position", String.valueOf(position));
	    TextView tv1 = (TextView) result.findViewById(R.id.txt2);
	   // tv1.setOnClickListener( this);
		tv1.setText(itemList.get(position).getAuthorName());
		//Log.v("position", String.valueOf(position));
      //  return super.getView(position, convertView, parent);

		return result;

	}
	

	public void onClick(View v) {
        switch (v.getId()) {
        case R.id.txt1:
            // Do stuff accordingly...
            break;
        default:
            break;
        }
    }

}
