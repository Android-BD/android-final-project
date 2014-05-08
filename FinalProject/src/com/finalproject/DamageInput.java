package com.finalproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DamageInput extends Activity {
	public static final String DAMAGE_NAME_KEY = "DAMAGE_NAME";
	public static final String DAMAGE_DESCRIPTION_KEY = "DAMAGE_DESCRIPTION";
	private Button mSaveButton;
	

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    setContentView(R.layout.damage_input);
	    
	    mSaveButton = (Button)findViewById(R.id.damage_save_button);
	    mSaveButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d("LWO","Clicked save");
				EditText nameView = (EditText)findViewById(R.id.damage_name);
				EditText descriptionView = (EditText)findViewById(R.id.damage_description);
				if(nameView.getText().toString().length() == 0) {
					nameView.setError("Name is required");
				} else if (descriptionView.getText().toString().length() == 0) {
					descriptionView.setError("Description is required");
				}
				String name = nameView.getText().toString();
				String description = descriptionView.getText().toString();
				
				Intent intent = new Intent();
				intent.putExtra(DAMAGE_NAME_KEY, name);
				intent.putExtra(DAMAGE_DESCRIPTION_KEY, description);
				setResult(Activity.RESULT_OK, intent);
				finish();
			}
		});   
	}

}
