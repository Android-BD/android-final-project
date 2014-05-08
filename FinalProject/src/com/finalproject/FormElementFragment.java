package com.finalproject;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.TextView;

public class FormElementFragment extends Fragment {
	public static final String TITLE_KEY = "TITLE_KEY";
	public static final String DESCRIPTION_KEY = "DESCRIPTION_KEY";
	public static final String DAMAGE_NAME_KEY = "DAMAGE_NAME";
	public static final String DAMAGE_DESCRIPTION_KEY = "DAMAGE_DESCRIPTION";
	
	public static final int DAMAGE_REQUEST_CODE = 1276;
	private static ArrayList<Damage> mDamages;
	private ArrayList<Damage> mCatDamages;
	private DamageAdapter mAdapter;
	
	public static final FormElementFragment newInstance(String title, String description) {
		mDamages = new ArrayList<Damage>();
		FormElementFragment f = new FormElementFragment();
		Bundle bdl = new Bundle(1);
		bdl.putString(TITLE_KEY, title);
		bdl.putString(DESCRIPTION_KEY, description);
		f.setArguments(bdl);
		return f;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflator, ViewGroup container, Bundle savedInstanceState) {
		String title = getArguments().getString(TITLE_KEY);
		String description = getArguments().getString(DESCRIPTION_KEY);
		View v = inflator.inflate(R.layout.rcr_form_element_fragment, container, false);
		mCatDamages = getCategoryDamages(title);
		
		//Set title
		TextView titleView = (TextView) v.findViewById(R.id.category_title);
		TextView descriptionView = (TextView) v.findViewById(R.id.category_description);
		
		//Set description
		titleView.setText(title);
		descriptionView.setText(description);
		
		//Set list adapter
		ListView damageList = (ListView) v.findViewById(R.id.damage_list);

		// Adding items to listview
		
		//mAdapter = new DamageAdapter(getActivity(), mCatDamages);
		mAdapter = new DamageAdapter(getActivity(),mDamages);
		damageList.setAdapter(mAdapter);
		
		
		//mAdapter = new DamageAdapter(getActivity(), mCatDamages);
		//ListView damagesList = (ListView)v.findViewById(R.id.damage_list);
		//damagesList.setAdapter(mAdapter);
		damageList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Log.d("LWO",""+position);
				Intent intent = new Intent(getActivity(), DamageInput.class);
				intent.putExtra(DAMAGE_NAME_KEY, mCatDamages.get(position).NAME);
				intent.putExtra(DAMAGE_DESCRIPTION_KEY, mCatDamages.get(position).DESCRIPTION);
				mCatDamages.remove(position);
				startActivityForResult(intent, DAMAGE_REQUEST_CODE);
			}
		});
		
		
		//Damage click listener
		Button damageButton = (Button)v.findViewById(R.id.damage_button);
		damageButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),DamageInput.class);
				startActivityForResult(intent, DAMAGE_REQUEST_CODE);
			}
		});
		
		//Good condition click listener
		Button goodConditionButton = (Button)v.findViewById(R.id.good_condition_button);
		goodConditionButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//NADA
			}
		});
		
		return v;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d("LWO","FormElementFragment result!");
		String category = getArguments().getString(TITLE_KEY);
		String damageName = null;
		String damageDescription = null;
		if (requestCode == DAMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
			damageName = data.getStringExtra(DamageInput.DAMAGE_NAME_KEY);
			damageDescription = data.getStringExtra(DamageInput.DAMAGE_DESCRIPTION_KEY);
			Damage temp = new Damage(category, damageName, damageDescription);
			mDamages.add(temp);
			//mCatDamages.add(temp);
			Log.d("LWO","Damage: "+temp.CATEGORY+" "+temp.NAME+" "+temp.DESCRIPTION);
			Log.d("LWO","Number of Damages: "+mDamages.size());
			//mAdapter.getFilter().filter(category);
		}
	}
	
	private ArrayList<Damage> getCategoryDamages(String category) {
		ArrayList<Damage> catDamage = new ArrayList<Damage>();
		for (int i = 0; i < mDamages.size(); i++) {
			if (mDamages.get(i).CATEGORY.equals(category)) {
				catDamage.add(mDamages.get(i));
			}
		}
		return catDamage;
	}
	
//	public class DamageAdapter extends ArrayAdapter<Damage> {
//
//		public DamageAdapter(Context context, ArrayList<Damage> damages) {
//			super(context, android.R.layout.simple_list_item_1, android.R.id.text1, damages);
//		}
//	}
	
	public class DamageAdapter extends ArrayAdapter<Damage> implements Filterable {
		private ArrayList<Damage> mCategoryDamages;	
		
		public DamageAdapter(Context context, ArrayList<Damage> damages) {
			super(context, android.R.layout.simple_list_item_1, android.R.id.text1);
			mCategoryDamages = damages;
			Log.d("LWO","adapter constructor");
		}
		
		@Override
		public int getCount() {
			Log.d("LWO", ""+mCategoryDamages.size());
			return mCategoryDamages.size();
		}
		
		@Override
		public Damage getItem(int position) {
			Log.d("LWO","getItem: " + mCategoryDamages.get(position).toString());
			return mCategoryDamages.get(position);
		}
		
		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			View view = null;
			LayoutInflater inflater = LayoutInflater.from(getActivity());
			
			if (convertView == null) {
				view = inflater.inflate(android.R.layout.simple_list_item_1, null);
			} else {
				view = convertView;
			}
			
			EditText text = (EditText) view.findViewById(android.R.id.text1);
			text.setText(mCategoryDamages.get(position).NAME);
			
			return view;
		}
		
		public Filter getFilter() {
			Log.d("LWO","getFilter");
			
			Filter filter = new Filter() {
				@SuppressWarnings("unchecked")
				@Override
				protected void publishResults(CharSequence constraint, FilterResults results) {
					Log.d("LWO","Publish results");
					mCategoryDamages = (ArrayList<Damage>) results.values;
					notifyDataSetChanged();
				}
				
				@Override
				protected FilterResults performFiltering(CharSequence constraint) {
					Log.d("LWO","Custom filter");
					
					FilterResults results = new FilterResults();
	                ArrayList<Damage> FilteredArrayNames = new ArrayList<Damage>();
					
					constraint = constraint.toString();
					for (int i = 0; i < mDamages.size(); i++) {
						if (mDamages.get(i).NAME.equals(constraint)) {
							FilteredArrayNames.add(mDamages.get(i));
							Log.d("LWO","Added "+mDamages.get(i).NAME);
						}
					}
					
					results.count = FilteredArrayNames.size();
					results.values = FilteredArrayNames;
					
					return results;
				}
			};
			
			return filter;
		}
		
		@Override
		public void notifyDataSetChanged() {
			Log.d("LWO","data set changed notification");
			super.notifyDataSetChanged();
		}
	}
		
	
	// Damage "struct"
	// Holds category, name, and description
	public static class Damage {
		String CATEGORY;
		String NAME;
		String DESCRIPTION;
		
		public Damage(String cat, String name, String desc) {
			CATEGORY = cat;
			NAME = name;
			DESCRIPTION = desc;
		}
		
		@Override
		public String toString() {
			return NAME;
		}
		
	}
}