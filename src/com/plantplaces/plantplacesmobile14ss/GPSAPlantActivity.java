package com.plantplaces.plantplacesmobile14ss;

import com.plantplaces.dto.Plant;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class GPSAPlantActivity extends Activity implements LocationListener {
	
	private static final String PLANT2 = "PLANT";
	private static final String LONGITUDE2 = "LONGITUDE";
	private static final String LATITUDE2 = "LATITUDE";
	private static final int CAMERA_RESULT = 5;
	// declare a variable that will hold a reference to the EditText component on the screen.
	EditText description;
	private TextView txtSelectedPlant;
	private Plant plant;
	private Bitmap plantImage;
	private ImageView imgPlant;
	private LocationManager locationManager;
	private double latitude;
	private double longitude;
	private TextView lblLatitudeValue;
	private TextView lblLongitudeValue;
	
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps_plants);
        
        description = (EditText) findViewById(R.id.edtDescription);
        
        //the label that contains the description of the selected plant
        txtSelectedPlant = (TextView) findViewById(R.id.txtSelectedPlant);
        
        //reference to image view that will display the photo
        imgPlant = (ImageView) findViewById(R.id.imgPlant);
        
        //get the LocationManager as a system service.  save it into a field.
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        
        lblLatitudeValue = (TextView) findViewById(R.id.lblLatitudeValue);
        lblLongitudeValue = (TextView) findViewById(R.id.lblLongitudeValue);
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_plants, menu);
        return true;
    }
  
    public void searchClicked(View v){
    	//create an explicit intent.
    	Intent searchIntent = new Intent(this, AdvancedSearchActivity.class);
    	
    	//start the activity
    	startActivityForResult(searchIntent, AdvancedSearchActivity.PLANT_RESULTS);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	// TODO Auto-generated method stub
    	super.onActivityResult(requestCode, resultCode, data);

    	if (resultCode == RESULT_OK) {
    		// only do this work if we received a good result.
    		if (requestCode == AdvancedSearchActivity.PLANT_RESULTS) {
    			// change the label of the text view to be the plant that was passed in.

    			// store the plant that the user selected as an attribute.
    			plant = (Plant) data.getSerializableExtra(PlantResultsActivity.PLANT_RESULT);

    			/// set this plant in the TextView on the UI.
    			txtSelectedPlant.setText(plant.toString());
    		} else if (requestCode == CAMERA_RESULT) {
    			// we are here because we have received a result from the camera.
    			plantImage = (Bitmap) data.getExtras().get("data");

    			imgPlant.setImageBitmap(plantImage);
    		}
    	}
    }
    
    
    
    //method will be invoked when Take Photo button is clicked
    public void onTakePhotoClicked(View v){
    	//use an implicit intent to invoke the camera
    	Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
    	
    	//start this intent, and anticipate a result.
    	startActivityForResult(cameraIntent, CAMERA_RESULT);
    	
    	
    	
    }
    @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	
    	//subscribe to location service
    	requestLocationUpdates();
    }
    private void requestLocationUpdates() {
		// TODO Auto-generated method stub
		if (locationManager != null) {
			// the variable locationManager has an object assigned if inside this if test.  in order to avoid a null pointer exception
			
			
			//request location
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000, 0, this);
		}
	}

    	
    
    
	@Override
    protected void onPause() {
    	// TODO Auto-generated method stub
    	super.onPause();
    	
    	//when this activity is no longer visible, we want to unsubscribe from GPS
    	removeLocationUpdates();
    }
/**
 * Unsubscribe from location services.
 */
    private void removeLocationUpdates() {
		// TODO Auto-generated method stub
		if (locationManager != null){
			locationManager.removeUpdates(this);
		}
	}


	@Override
    protected void onStart() {
    	// TODO Auto-generated method stub
    	super.onStart();
    }

    /**
     * This method will be invoked when the GPS service informs that the location has changed.
     * anything we want to do that should be updated when the GPS position of our phone has moved we 
     * must do in this method.
     */
	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		latitude = location.getLatitude();
		longitude = location.getLongitude();
		
		updateUIForLocation();
	}


	private void updateUIForLocation() {
		//Update user interface.
		lblLatitudeValue.setText("" + latitude);
		lblLongitudeValue.setText("" + longitude);
	}


	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * This method is called right before we change orientaton, so that we can preserve values when the screen
	 * is redrawn.
	 */
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
    	super.onSaveInstanceState(outState);
    	outState.putDouble(LATITUDE2, latitude);
    	outState.putDouble(LONGITUDE2, longitude);
    	outState.putSerializable(PLANT2, plant);
    	
    	
/**
 * Display values in the UI that were saved right before orientation changed.
 */
	}
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
    	super.onRestoreInstanceState(savedInstanceState);
    	//repopulate values from the bundle we created in onSaveInstanceState method.
    	latitude = savedInstanceState.getDouble(LATITUDE2);
    	longitude = savedInstanceState.getDouble(LONGITUDE2);
    	updateUIForLocation();
    	plant = (Plant) savedInstanceState.getSerializable(PLANT2);
    	txtSelectedPlant.setText(plant.toString());
	}
}
