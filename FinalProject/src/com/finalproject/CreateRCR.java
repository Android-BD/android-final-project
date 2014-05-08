package com.finalproject;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class CreateRCR extends Activity {
	private Spinner mResHallSpinner;
	private Button mCreateButton;
	private Context mContext;
	private JSONObject mInputData;
	public static final int DAMAGE_REQUEST_CODE = 8712639;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.create_rcr);
	    mContext = this;
	    mInputData = new JSONObject();
	    
	    // Fill spinner with data
	    mResHallSpinner = (Spinner) findViewById(R.id.residence_hall_selector);
		ArrayAdapter<CharSequence> resHallAdapter = ArrayAdapter.createFromResource(this, R.array.residence_halls, android.R.layout.simple_spinner_dropdown_item);
		resHallAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
		mResHallSpinner.setAdapter(resHallAdapter);
		
		// Create button click listener
		mCreateButton = (Button) findViewById(R.id.create_button);
		mCreateButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d("LWO","New clicked");
				EditText ra_fName = (EditText)findViewById(R.id.ra_name_first_selector);
				EditText ra_lName = (EditText)findViewById(R.id.ra_name_last_selector);
				EditText res_fName = (EditText)findViewById(R.id.resident_name_first_selector);
				EditText res_lName = (EditText)findViewById(R.id.resident_name_last_selector);
				EditText roomNumber = (EditText)findViewById(R.id.room_number_selector);
				Spinner residenceHall = (Spinner)findViewById(R.id.residence_hall_selector);
				RadioGroup roomSideGroup = (RadioGroup)findViewById(R.id.room_side_group);
				
				if (ra_fName.getText().toString().length() == 0) {
					ra_fName.setError("RA first name is required");
					return;
				}
				if (ra_lName.getText().toString().length() == 0) {
					ra_lName.setError("RA last name is required");
					return;
				}
				if (res_fName.getText().toString().length() == 0) {
					res_fName.setError("Resident first name is required");
					return;
				}
				if (res_lName.getText().toString().length() == 0) {
					res_lName.setError("Resident last name is required");
					return;
				}
				if (roomNumber.getText().toString().length() == 0) {
					roomNumber.setError("Room number is required");
					return;
				}
				try {	
					mInputData.put("ra_fname",ra_fName.getText().toString());
					mInputData.put("ra_lname",ra_lName.getText().toString());
					mInputData.put(("resident_fName"),res_fName.getText().toString());
					mInputData.put("resident_lname",res_lName.getText().toString());
					mInputData.put("room_number", roomNumber.getText().toString());
					mInputData.put("residence_hall",residenceHall.getSelectedItem().toString());
					int RBID = roomSideGroup.getCheckedRadioButtonId();
					mInputData.put("room_side", ((RadioButton)findViewById(RBID)).getText());
				} catch (JSONException e) {
					Log.d("LWO","JSONException processing input");
					e.printStackTrace();
				}
				
				//Start RCRForm activity
				Intent intent = new Intent(mContext, RCRForm.class);
				CreateRCR.this.startActivityForResult(intent, DAMAGE_REQUEST_CODE);
			}
		});
	}
	
	/** Result from RCRForm */
	/** Add damages to the mInputData and submit */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d("LWO","ActivityResult");
		if(requestCode == DAMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
			Log.d("LWO","Result successful, do something with the damages");
			//TODO add the damages to the json object and send that somewhere
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
