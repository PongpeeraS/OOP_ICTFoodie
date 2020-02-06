import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Arrays;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
/** Name: Pongpeera Sukasem
 * StudentID: 5988040
 * Section: 1 (Group 1)
 */
public class SearchEngineController {

	public static int TOP_K = 3;		// Number of the top "K" similar restaurants
	private SearchEngineView theView;
	private SearchEngineModel theModel;
	
	public SearchEngineController(SearchEngineView view, SearchEngineModel model, 
			String path, String dbFileName, String simFileName, String reviewFileName){
		this.theView = view;
		this.theModel = model;
		
		// set base path of the dataset
		this.theView.setPath(path);
		this.theModel.setPath(path);
		
		
		// To add the Listener to the 'Clear' button, 'Search' button, and 'Result' table 
		// in theView object by calling addClearListener, addSearchListener, and addResultListener methods.
		// The listener for these objects are ClearListner, SearchListenere, and RowListener respectively.
		// For example, this.theView.addClearListener(new ClearListener());
		// --- YOUR CODE GOES HERE ---
		this.theView.addClearListener(new ClearListener());
		this.theView.addSearchListener(new SearchListener());
		this.theView.addResultListener(new RowListener());
		// To initialize theModel object by calling three loading methods
		// including LoadRestaurantData, LoadSimilarityData, and LoadReivewData.
		// The input files are dbFileName, simFileName, and reviewFileName respectively
		// For example, this.theModel.LoadRestaurantData(dbFileName);
		// --- YOUR CODE GOES HERE ---
		this.theModel.LoadRestaurantData(dbFileName);
		this.theModel.LoadSimilarityData(simFileName);
		this.theModel.LoadReivewData(reviewFileName);

	}
	
	class ClearListener implements ActionListener{
		/*
		 * clear search criteria and result in the view
		 * 
		 */
		public void actionPerformed(ActionEvent e) {
			theView.clearAll();
			// DONE, NO additional code is required.
		}
	}
	
	class SearchListener implements ActionListener{

		/*
		 * call an action using the model to search restaurant 
		 * based on the input criteria from the view
		 */
		public void actionPerformed(ActionEvent e) {
			// get all search criteria from the view
			// YOUR CODE GOES HERE
			String name,category,priceRange;
			boolean checkOpen;
			name = theView.getSearchName();
			category = theView.getSearchCategory();
			priceRange = theView.getSearchPriceRange();
			checkOpen = theView.getCheckOpenNow();
			Restaurant[] resArr = null;
			// get the result list after call search method in the model
			// YOUR CODE GOES HERE
			String sort = theView.getSortedBy();
			if(sort.isEmpty()){
				resArr = theModel.searchRestaurant(name, category, priceRange, checkOpen);
			}
			// if sorted by is chosen, you will have to sort the result before display as well
			// YOUR CODE GOES HERE
			else if(sort.equals("Name")||sort.equals("Rating")||sort.equals("#Reviews")){
				resArr = theModel.sortedResultBy(theModel.searchRestaurant(name, category, priceRange, checkOpen), sort);
			}
			// call method in the view to set result 
			// YOUR CODE GOES HERE
			if(resArr.length != 0){
				theView.setResult(resArr);
			}
			else{
				theView.displayAlertMsg("Restaurant Not Found!");
			}
		}
		
	}
	
	class RowListener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent event) {
            if (event.getValueIsAdjusting()) {
                return;
            }
            if(theView.getResultRowCount() > 0){
	            // get selected restaurant id from JTable
	            int selectedRestaurant = theView.getSelectedResultRow();
	            
				// call the method from the model to find the similar restaurants
				// YOUR CODE GOES HERE
	            Restaurant[] resArr = theModel.findSimilarRestaurant(selectedRestaurant, TOP_K);
	            double[] simScores = new double[resArr.length];//Create a new array of only simScores
	            for(int i=0;i<resArr.length;i++){
	            	simScores[i] = resArr[i].getSimRow();
	            }
	            // call method from the view to display restaurant profile and similar restaurant
				// YOUR CODE GOES HERE
	            theView.setRestaurantProfile(theModel.getRestaurantByID(selectedRestaurant));
	            theView.setSimilarBusinessPanel(resArr,simScores);
            }
        }
    }
}
