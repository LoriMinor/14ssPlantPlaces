package com.plantplaces.dao;

import java.util.ArrayList;

import com.plantplaces.dto.Plant;

/**
 * A stub class that returns predictable data for plants
 * @author Lori
 *
 */
public class PlantDAOStub implements IPlantDAO {

	/**
	 * Return a collection of plants that match a given search term.
	 * @param searchTerm the term we're searching against.
	 * @return a collection of plants that match search criteria
	 */
	public ArrayList<Plant> fetchPlants(String searchTerm) {
		ArrayList<Plant> allPlants = new ArrayList<Plant>();
	
		if (searchTerm.equalsIgnoreCase("redbud")){
			// create a new object from class Plant.
			Plant redbud = new Plant();
			redbud.setCommon("Redbud");
			redbud.setGenus("Cercis");
			redbud.setSpecies("canadensis");

			//add the redbud and the paw paw to this collection
			allPlants.add(redbud);
		}	


		if (searchTerm.contains("pawpaw")){
			//create a paw paw
			Plant pawpaw = new Plant();
			pawpaw.setCommon("PawPaw");
			pawpaw.setGenus("Asimina");
			pawpaw.setSpecies("triloba");
			allPlants.add(pawpaw);
		}

		//do we have an empty list?
		if (allPlants.size()== 0){
			Plant empty = new Plant();
			empty.setCommon("No plants match your result. Please try again");
			allPlants.add(empty);
		}
		return allPlants;
	}
}
