package com.plantplaces.plantplacesmobile14ss;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AdvancedSearchActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		//associate layout with this activity
		setContentView(R.layout.advancedsearch);
	}
	
	
	public void searchForPlants(View v){
		//create an explicit intent
		Intent plantResultsIntent = new Intent(this, PlantResultsActivity.class);
		//invoke explicit intent
		startActivity(plantResultsIntent);
	}
	
}
