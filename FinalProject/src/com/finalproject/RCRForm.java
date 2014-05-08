package com.finalproject;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.support.v4.app.FragmentManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class RCRForm extends FragmentActivity {
	private RCRPageAdapter mPageAdapter;
	private final String URL = "http://lyle.smu.edu/~loglesbee/3345/FinalProject/formJSON";
	ArrayList<Fragment> mFormElementFragments;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.rcr_from);
	    
	    new DownloadDataTask().execute(URL);
	}
	
	//Gets form data from a static website
	private class DownloadDataTask extends AsyncTask<String, Integer, ArrayList<Fragment>> {
		@Override
		protected ArrayList<Fragment> doInBackground(String... urlString) {
			JSONArray jArray = null; //Array retrieved from url
			String tTitle = null; //Temp title
			String tDescription = null; //Temp description
			JSONObject jFormElement = null; //Temp json object for form element
			ArrayList<Fragment> fList = new ArrayList<Fragment>();
			
			HttpClient client = new DefaultHttpClient();
			HttpGet get = new HttpGet(urlString[0]);
			try {
				HttpResponse response = client.execute(get);
				String jsonString = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
				jArray = new JSONArray(jsonString);
				for (int i = 0; i < jArray.length(); i++) {
					jFormElement = jArray.getJSONObject(i);
					tTitle = jFormElement.getString("title");
					tDescription = jFormElement.getString("description");
					fList.add(FormElementFragment.newInstance(tTitle, tDescription));
					publishProgress(Integer.valueOf(i));
				} 
			} catch (ClientProtocolException e) {
				Log.e("HTTP","ClientProtocolException");
				e.printStackTrace();
			} catch (IOException e) {
				Log.e("HTTP","IOException");
				e.printStackTrace();
			} catch (JSONException e) {
				Log.e("JSON","JSONException");
				e.printStackTrace();
			}
			return fList;
		}
		
		@Override
		protected void onProgressUpdate(Integer... progress) {
			Log.d("LWO","Progress: "+progress.toString());
		}
		
		@Override
		protected void onPostExecute(ArrayList<Fragment> fList) {
			mFormElementFragments = fList;
			mPageAdapter = new RCRPageAdapter(getSupportFragmentManager(), mFormElementFragments);
		    ViewPager pager = (ViewPager) findViewById(R.id.form_viewpager);
		    pager.setAdapter(mPageAdapter);
		}
		
	}
	
	private class RCRPageAdapter extends FragmentPagerAdapter {
		private ArrayList<Fragment> mFragments;
		
		private RCRPageAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
			super(fm);
			mFragments = fragments;
		}
			
		@Override
		public Fragment getItem(int position) {
			return mFragments.get(position);
		}

		@Override
		public int getCount() {
			return mFragments.size();
		}
		
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
			case R.id.toast:
				Toast.makeText(getBaseContext(), R.string.my_name, Toast.LENGTH_LONG).show();
			  return true;
			default:
			  return super.onOptionsItemSelected(item);
		}
    }
}
