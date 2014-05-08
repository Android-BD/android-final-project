package com.finalproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DamageInput extends Activity {
	public static final String DAMAGE_NAME_KEY = "DAMAGE_NAME";
	public static final String DAMAGE_DESCRIPTION_KEY = "DAMAGE_DESCRIPTION";
	private Button mSaveButton;
	private EditText mNameView;
	private EditText mDescriptionView;
	

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    setContentView(R.layout.damage_input);
	    mNameView = (EditText)findViewById(R.id.damage_name);
		mDescriptionView = (EditText)findViewById(R.id.damage_description);
	    
	    Intent intent = getIntent();
	    if (intent.hasExtra(FormElementFragment.DAMAGE_NAME_KEY)) {
	    	String name = intent.getStringExtra(FormElementFragment.DAMAGE_NAME_KEY);
	    	mNameView.setText(name);
	    	Log.d("LWO","Received: "+name);
	    }
	    if (intent.hasExtra(FormElementFragment.DAMAGE_DESCRIPTION_KEY)) {
	    	String description = intent.getStringExtra(FormElementFragment.DAMAGE_DESCRIPTION_KEY);
	    	mDescriptionView.setText(description);
	    	Log.d("LWO","Received: "+description);
	    }
	    
	    mSaveButton = (Button)findViewById(R.id.damage_save_button);
	    mSaveButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d("LWO","Clicked save");
				if(mNameView.getText().toString().length() == 0) {
					mNameView.setError("Name is required");
				} else if (mDescriptionView.getText().toString().length() == 0) {
					mDescriptionView.setError("Description is required");
				}
				String name = mNameView.getText().toString();
				String description = mDescriptionView.getText().toString();
				
				Intent intent = new Intent();
				intent.putExtra(DAMAGE_NAME_KEY, name);
				intent.putExtra(DAMAGE_DESCRIPTION_KEY, description);
				setResult(Activity.RESULT_OK, intent);
				finish();
			}
		});   
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
