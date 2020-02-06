import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/** Name: Pongpeera Sukasem
 * StudentID: 5988040
 * Section: 1 (Group 1)
 */

public class SearchEngineModel {
	
	Map<Integer, Restaurant> restaurants = new HashMap<Integer, Restaurant>(); 
	String path;
	double[][] simScore;
	
	/*
	 * set path (full/ or sample/ folder)
	 */
	public void setPath(String path){
		this.path = path;
	}
	
	/*
	 * Open the file containing restaurant data.
	 * Each row must be split into eight attributes to create a Restaurant object.
	 * Then the Restaurant will be added into restaurants HashMap.
	 * If the number of attribute != 8, the entire row will be skipped.
	 * 
	 * input: file's name
	 * output: void
	 */
	public void LoadRestaurantData(String fileName){
		// YOUR CODE GOES HERE
		try{
			File dbFile = new File(path + fileName);
			Scanner reader = new Scanner(dbFile);
			
			while(reader.hasNext()){
				String all = reader.nextLine();
				String[] dummy = all.split("\\|");
				if(dummy.length == 8){
					int resId = Integer.parseInt(dummy[0]); //dummy[0] = ID
					String resName = dummy[1]; //dummy[1] = Name
					Address resAdd = new Address(dummy[2]); //dummy[2] = Address (Splitting done in Constructor)
					String resPhone = dummy[3]; //dummy[3] = Phone
					String resDec = dummy[4]; //dummy[4] = Description
					Restaurant resObj = new Restaurant(resId, resName, resAdd, resPhone, resDec);//Instantiate object to put more info
					resObj.setCategories(dummy[5]); //dummy[5] = Categories (Splitting done in method)
					resObj.setPriceRange(dummy[6]);//dummy[6] = Enum $
					resObj.setHours(dummy[7]);//dummy[7] = Open Times (Splitting done in method)
					
					restaurants.put(resId, resObj);
				}
			}
			reader.close();
		} 
		catch(FileNotFoundException e1){
			e1.printStackTrace();
		}
		catch(IOException e1){
			e1.printStackTrace();
		}
	}
	
	
	/*
	 * Open the file containing review data. Each row in the file represent one review.
	 * This method will add a new review into the existing list of reviews of a given restaurant.
	 * Note that each restaurant object supposes to have an ArrayList of reviews.
	 * 
	 * input: file's name
	 * output: void
	 */
	public void LoadReivewData(String fileName){
		
		// YOUR CODE GOES HERE
		try{
			File reviewFile = new File(path + fileName);
			Scanner reader = new Scanner(reviewFile);
			
			while(reader.hasNext()){
				String all = reader.nextLine();
				String[] dummy = all.split("\\|");
				int revId = Integer.parseInt(dummy[0]); //dummy[0] = ID
				String revDate = dummy[1]; //dummy[1] = Date (Parsing to Date format done in Constructor)
				int revRating = Integer.parseInt(dummy[2]); //dummy[2] = Rating
				String revDetail = dummy[3]; //dummy[3] = Review details
				Review revObj = new Review(revId, revDate, revRating, revDetail);
				restaurants.get(revId).getReviews().add(revObj);
			}
			for(Integer i: restaurants.keySet()){
				restaurants.get(i).calculateAverageRating();
			}
			reader.close();
		} 
		catch(FileNotFoundException e1){
			e1.printStackTrace();
		}
		catch(IOException e1){
			e1.printStackTrace();
		}
	}
	
	/*
	 * Open the file containing similarity score data and 
	 * assign those values into simScore matrix. 
	 * 
	 * input: file's name
	 * output: void
	 */
	public void LoadSimilarityData(String fileName){
		
		// YOUR CODE GOES HERE
		try{
			File dbFile = new File(path + fileName);
			Scanner reader = new Scanner(dbFile);
			simScore = new double[restaurants.size()][restaurants.size()];
			int i=0;
			while(reader.hasNext()){
				String all = reader.nextLine();
				String[] dummy = all.split(",");
				for(int j=0;j<dummy.length;j++){
					simScore[i][j] = Double.parseDouble(dummy[j]);
				}
				i++;
			}
			reader.close();
		} 
		catch(FileNotFoundException e1){
			e1.printStackTrace();
		}
		catch(IOException e1){
			e1.printStackTrace();
		}
	}
	
	/*
	 * To return a restaurant from the HashMap according to the given id.
	 */
	public Restaurant getRestaurantByID(int id){
		// YOUR CODE GOES HERE
		return restaurants.get(id);
		//return null;
	}
	
	/* 
	 * To get the total number of restaurants in the HashMap
	 */
	public int getNumberOfRestaurants(){
		// YOUR CODE GOES HERE
		return restaurants.size();
		//return -1;
	}
	
	/*
	 * To get the total number of reviews of a specific restaurant
	 */
	public int getNumberOfReviews(int restaurantID){
		// YOUR CODE GOES HERE
		return restaurants.get(restaurantID).getReviews().size();
		//return -1;
	}
	
	/*
	 * To get a list of reviews of a specific restaurant
	 */
	public List<Review> getAllReviews(int restaurantID){
		// YOUR CODE GOES HERE
		return restaurants.get(restaurantID).getReviews();
		//return null;
	}
	
	public double getSimilarityScore(int restaurantID1, int restaurantID2){
		// YOUR CODE GOES HERE
		return simScore[restaurantID1][restaurantID2];
		//return 0.0;
	}
	
	/*
	 * To search for restaurants that satisfy ALL the given input parameters
	 * @input: 	name (String) - restaurant name (the results must "contain" the name parameter)
	 * 			category (String) - category of restaurant e.g., Cafe or Thai
	 * 			priceRange (String) - the price level must be $, $$ or $$$
	 * 			checkOpen (boolean) - if true, you must check whether the restaurant still opens or not by looking 
	 * 								  at its open hours. If false, you do not have to check its open hours.
	 * 
	 * @output: array of restaurants that satisfy ALL the input parameters
	 */
	public Restaurant[] searchRestaurant(String name, String category, String priceRange, Boolean checkOpen){
		// YOUR CODE GOES HERE
		ArrayList<Restaurant> match = new ArrayList<Restaurant>();
		Restaurant dummy = new Restaurant(99, name);
		dummy.setPriceRange(priceRange);
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

		for(Integer i: restaurants.keySet()){
			if(restaurants.get(i).getName().toLowerCase().contains(name.toLowerCase())){
				if(restaurants.get(i).getCategoriesString().contains(category)){
					if(restaurants.get(i).getPriceRange().equals(dummy.getPriceRange())){ //priceRange not ignored
						if(checkOpen == true){ 
							if(restaurants.get(i).getOpenHoursOnDay(now.getDay()) == null){
								
							}
							else if(restaurants.get(i).getOpenHoursOnDay(now.getDay()).contains(sdf.format(now))){
								match.add(restaurants.get(i));
							}
						}
						else{
							match.add(restaurants.get(i));
						}
					}
					else if(!priceRange.contains("$")){ //Case when priceRange is ignored
						if(checkOpen == true){ 
							if(restaurants.get(i).getOpenHoursOnDay(now.getDay()) == null){
								
							}
							else if(restaurants.get(i).getOpenHoursOnDay(now.getDay()).contains(sdf.format(now))){
								match.add(restaurants.get(i));
							}
						}
						else{
							match.add(restaurants.get(i));
						}
					}
				}
			}
		}
		Restaurant[] match2 = new Restaurant[match.size()];
		for(int i=0;i<match.size();i++){
			match2[i] = match.get(i);
		}
		return match2;
		//return null;
	}
	
	/*
	 * To sorted the array of restaurant according to the specific key attributes (i.e., Name, Rating, #Reviews)
	 * 
	 * @input: the array of restaurants and the key attribute 
	 * 
	 * @output: the sorted array of restaurant based on the sorted key attribute.
	 */
	public Restaurant[] sortedResultBy(Restaurant[] array, String sort){
		// YOUR CODE GOES HERE
		
		if(sort.equals("Name")){
			Arrays.sort(array, new NameComp()); //Compare by array[i].getName
		}
		else if(sort.equals("Rating")){
			Arrays.sort(array, new RatingsComp()); //Compare by array[i].getRating
		}
		else if(sort.equals("#Reviews")){
			Arrays.sort(array, new ReviewNumComp()); //Compare by array[i].getReviews.size
		}

		return array;
		//return null;
	}
	
	/*
	 * To find the N number of similar restaurants that have the most top N highest similarity scores 
	 * 
	 * @input: the restaurant id, and number of similar restaurants that will be returned
	 * 
	 * @output: the array of similar restaurants. The size of the array is numOfRestaurant input parameter 
	 */
	
	public Restaurant[] findSimilarRestaurant(int restaurantID, int numOfRestaurant){ //UNFINISHED!
		// YOUR CODE GOES HERE
		//Clone restaurants 
		ArrayList<Restaurant> temp = new ArrayList<Restaurant>(restaurants.values());
		for(int i=0;i<temp.size();i++){ //Setting simScore to the restaurant
			temp.get(i).setSimRow(simScore[restaurantID][i]);
		}
		temp.remove(restaurantID); //Remove the selected restaurant to prevent duplication
		temp.sort(new SimComp()); //Sorting simScore
		ArrayList<Restaurant> tempCut = new ArrayList<Restaurant>();
		for(int i=0;i<numOfRestaurant;i++){ //Trimming to size numOfRestaurant
			tempCut.add(temp.get(i));
		}
		Restaurant[] temp2 = new Restaurant[numOfRestaurant];
		for(int i=0;i<tempCut.size();i++){
			temp2[i] = tempCut.get(i);
		}
		return temp2;
		//return null;
	}
	
	//Additional comparator classes to be used in sortedResultBy()
	public class NameComp implements Comparator<Restaurant>{
		@Override
		public int compare(Restaurant o1, Restaurant o2) {
			return o1.getName().compareTo(o2.getName());
		}
	}
	public class RatingsComp implements Comparator<Restaurant>{
		@Override
		public int compare(Restaurant o1, Restaurant o2) {
			return Double.compare(o2.getAverageRating(), o1.getAverageRating());
		}
	}
	public class ReviewNumComp implements Comparator<Restaurant>{
		@Override
		public int compare(Restaurant o1, Restaurant o2) {
			return Integer.compare(o2.getReviews().size(), o1.getReviews().size());
		}
	}
	public class SimComp implements Comparator<Restaurant>{
		@Override
		public int compare(Restaurant o1, Restaurant o2) {
			return Double.compare(o2.getSimRow(), o1.getSimRow());
		}
	}
	//End of additional comparator classes
}
