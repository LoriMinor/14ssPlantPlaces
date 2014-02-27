package com.plantplaces.plantplacesmobile14ss;

import java.util.ArrayList;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.plantplaces.dao.IPlantDAO;
import com.plantplaces.dao.OnlinePlantDAO;
import com.plantplaces.dto.Plant;

public class PlantResultsActivity extends ListActivity {

	public static final String PLANT_RESULT = "PLANT_RESULT";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		// retrieve any data that was passed in, and was associated with the name "SEARCH_PLANT_NAME"
		String searchTerm = getIntent().getStringExtra("SEARCH_PLANT_NAME");

		//create an instance of our plant search task that will run in a separate thread
		PlantSearchTask pst = new PlantSearchTask();
		//execute PlantSearchTask thread; pass search term
		pst.execute(searchTerm);	



	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);

		// get the item that the user clicked.
		Plant plant = (Plant) getListAdapter().getItem(position);

		// put the plant in the intent which we are about to return.
		getIntent().putExtra(PLANT_RESULT, plant);

		// everything went fine.
		setResult(RESULT_OK, getIntent());

		// finish this intent.
		finish();
	}

	class PlantSearchTask extends AsyncTask<String, Integer, ArrayList<Plant>>{
		/**
		 * The steps in this method will run in a separate (non-UI) thread.
		 */
		@Override
		protected ArrayList<Plant> doInBackground(String... searchTerms) {
			//create a collection to hold the plants
			String searchTerm = searchTerms[0];
			//make a variable that will hold the plant DAO.
			IPlantDAO plantDAO = new OnlinePlantDAO();

			//fetch the plants from the DAO.
			ArrayList<Plant> plants = plantDAO.fetchPlants(searchTerm);
			
			//return the matching plants.
			return plants;
		}

		/**
		 *  This method will be called when doInBackground completes.
		 *  The parameter result is populated from the return values of doInBackground.
		 *  This method runs on the UI thread, and can update UI components.
		 */
		@Override
		protected void onPostExecute(ArrayList<Plant> allPlants) {
			// adapt the search results returned from doInBackground for UI
			ArrayAdapter<Plant> plantAdapter = new ArrayAdapter<Plant>(PlantResultsActivity.this, android.R.layout.simple_list_item_1, allPlants);
			//show search results  in our list.
			setListAdapter(plantAdapter);
		}
	}

}
