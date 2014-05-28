package com.npi.blureffect;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SampleListFragment extends ListFragment {

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.list, null);
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		SampleAdapter adapter = new SampleAdapter(getActivity());
		adapter.add(new SampleItem("Home", R.drawable.ic_menu_home));
		adapter.add(new SampleItem("Powell", R.drawable.ic_menu_copy_holo_light));
		adapter.add(new SampleItem("YRL", R.drawable.ic_menu_recent_history));
		adapter.add(new SampleItem("Laptop Availability", R.drawable.ic_menu_help_holo_light));
		adapter.add(new SampleItem("Contact", R.drawable.ic_menu_allfriends));
		adapter.add(new SampleItem("Feedback", R.drawable.ic_menu_notifications));
		adapter.add(new SampleItem("Credits", R.drawable.ic_menu_info_details));
		adapter.add(new SampleItem("Article Search", R.drawable.ic_menu_info_details));
		adapter.add(new SampleItem("Search", R.drawable.ic_menu_info_details));

		setListAdapter(adapter);
	}

	private class SampleItem {
		public String tag;
		public int iconRes;
		public SampleItem(String tag, int iconRes) {
			this.tag = tag; 
			this.iconRes = iconRes;
		}
	}

	public class SampleAdapter extends ArrayAdapter<SampleItem> {

		public SampleAdapter(Context context) {
			super(context, 0);
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(getContext()).inflate(R.layout.row, null);
			}
			TextView icon = (TextView) convertView.findViewById(R.id.list_value);
			icon.setText(getItem(position).tag);
			TextView title = (TextView) convertView.findViewById(R.id.book_year);
			title.setText(getItem(position).tag);

			return convertView;
		}
	}
}
