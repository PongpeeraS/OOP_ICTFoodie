/** Name: Pongpeera Sukasem
 * StudentID: 5988040
 * Section: 1 (Group 1)
 */
/*
 * This is the main application for the restaurant search engine
 * To run on sample dataset, call searchSampleDataset method
 * To run on full size dataset, call searchFullDataset method
 * 
 * Note: DO NOT change this file (except changing between searchSampleDataset and searchFullDataset)
 */
public class SearchEngine {
	public static void main(String[] args){
		SearchEngineView theView = new SearchEngineView();
		SearchEngineModel theModel = new SearchEngineModel();
		
		SearchEngineController theController = searchSampleDataset(theView, theModel);
		//SearchEngineController theController = searchFullDataset(theView, theModel);
		
		theView.setVisible(true);
	}
	
	public static SearchEngineController searchSampleDataset(SearchEngineView theView, SearchEngineModel theModel){
		String dbFileName = "data/restaurantdb.txt";
		String simFileName = "data/similarity.txt";
		String reviewFileName = "data/reviewdb.txt";
		String path = "sample/";
		return new SearchEngineController(theView, theModel, path,
				dbFileName, simFileName, reviewFileName);
	}
	
	public static SearchEngineController searchFullDataset(SearchEngineView theView, SearchEngineModel theModel){
		String dbFileName = "data/restaurantdb.txt";
		String simFileName = "data/similarity.txt";
		String reviewFileName = "data/reviewdb.txt";
		String path = "full/";
		return new SearchEngineController(theView, theModel, path,
				dbFileName, simFileName, reviewFileName);
	}
}
