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
import android.widget.ListView;
import android.widget.TextView;

public class FormElementFragment extends Fragment {
	public static final String TITLE_KEY = "TITLE_KEY";
	public static final String DESCRIPTION_KEY = "DESCRIPTION_KEY";
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
		mAdapter = new DamageAdapter(getActivity(), mCatDamages);
		damageList.setAdapter(mAdapter);
		
		
		//mAdapter = new DamageAdapter(getActivity(), mCatDamages);
		//ListView damagesList = (ListView)v.findViewById(R.id.damage_list);
		//damagesList.setAdapter(mAdapter);
		damageList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Log.d("LWO",""+position);
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
			mCatDamages.add(temp);
			Log.d("LWO","Damage: "+temp.CATEGORY+" "+temp.NAME+" "+temp.DESCRIPTION);
			Log.d("LWO","Number of Damages: "+mDamages.size());
			//mAdapter.getFilter().filter(category);
			mAdapter.notifyDataSetChanged();
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
	
	public class DamageAdapter extends ArrayAdapter<Damage> {

		public DamageAdapter(Context context, ArrayList<Damage> damages) {
			super(context, android.R.layout.simple_list_item_1, android.R.id.text1, damages);
		}
	}
	
//	public class DamageAdapter extends ArrayAdapter<Damage> implements Filterable {
//		//private ArrayList<Damage> mCategoryDamages;
//		//private Filter mFilter; 	
//		
//		public DamageAdapter(Context context, ArrayList<Damage> damages) {
//			super(context, android.R.layout.simple_list_item_1, android.R.id.text1, damages);
//			//mCategoryDamages = new ArrayList<Damage>();
//			Log.d("LWO","adapter constructor");
//		}
		
//		@Override
//		public int getCount() {
//			return mCategoryDamages.size();
//		}
//		
//		@Override
//		public Damage getItem(int position) {
//			return mCategoryDamages.get(position);
//		}
//		
//		public Filter getFilter() {
//			if (mFilter == null) {
//				mFilter = new CustomFilter();
//			}
//			return mFilter;
//		}
//		
//		@Override
//		public View getView(final int position, View convertView, ViewGroup parent) {
//			Log.d("LWO","getView");
//			return super.getView(position, convertView, parent);
//		}
//		
//		@Override
//		public void notifyDataSetChanged() {
//			super.notifyDataSetChanged();
//		}
//		
//		@SuppressLint("DefaultLocale")
//		private class CustomFilter extends Filter {
//			
//			@Override
//			protected FilterResults performFiltering(CharSequence constraint) {
//				
//				FilterResults results = new FilterResults();
//				
//				constraint = constraint.toString().toLowerCase();
//				for (int i = 0; i < mDamages.size(); i++) {
//					if (mDamages.get(i).toString().toLowerCase().equals(constraint)) {
//						mCategoryDamages.add(mDamages.get(i));
//					}
//				}
//				
//				results.count = mCategoryDamages.size();
//				results.values = mCategoryDamages;
//				
//				return results;
//			}
//			
//			@SuppressWarnings("unckecked")
//			@Override
//			protected void publishResults(CharSequence constraint, FilterResults results) {
//				//mCategoryDamages = (ArrayList<Damage>) results.values;
//				notifyDataSetChanged();
//			}
//		}
		
	
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