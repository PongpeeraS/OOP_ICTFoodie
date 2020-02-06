import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;

/** Name: Pongpeera Sukasem
 * StudentID: 5988040
 * Section: 1 (Group 1)
 */

public class SearchEngineView extends JFrame {
	private String basePath = "sample/";
	
	// These panels are the main panels inside JFrame
	private JPanel searchPanel;
	private JPanel resultPanel;
	private JPanel detailPanel;
	private JPanel outputPanel;
	private JPanel simBizPanel;
    
	private JPanel bizProfilePanel;		// sub-panel of detailPanel
    private JPanel reviewPanel;			// sub-panel of detailPanel
    
    private JPanel simBizProfilePanel;	// sub-panel of simBizPanel
	
    
    private JTable resultTable;			// component for displaying data in table form
    private JTextArea txtOutput;		// component for showing data in text area form
    
    // Several GUI components
 	private JLabel lbName = new JLabel("Name");
 	private JTextField txtName = new JTextField(20);
 	private JLabel lbCategory = new JLabel("Category");
 	
 	private static String[] categories = new String[] {"","Cafe", "Thai", "Vietnamese", "Japanese", "Korean", "Fusion", "Shabu", "Vegetarian"};
 	private JComboBox<String> cbCategory = new JComboBox<String>(categories);
 	
 	private JLabel lbSorted = new JLabel("Sorted Result By");
 	private static String[] sortedFeature = new String[] {"","Name", "Rating", "#Reviews"};
 	private JComboBox<String> cbSortedBy = new JComboBox<String>(sortedFeature);
 	
 	private JLabel lbPrice = new JLabel("Price");
 	private ButtonGroup grPrice;
 	private JRadioButton rbPrice1;
 	private JRadioButton rbPrice2;
 	private JRadioButton rbPrice3;
 	private JCheckBox cbOpen;
 	
 	private JButton btSearch = new JButton("Search");
 	private JButton btClear = new JButton("Clear");
	
    
	/*
	 * Constructor Method
	 */
	public SearchEngineView(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1200, 1200);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setLayout(new BorderLayout());
		
		// build or initialize all five panels
		buildSearchPanel();
		buildResultPanel();
		buildDetailPanel();
		buildSimilarPanel();
		buildOutputPanel();
		
		// add panel into the JFrame
		this.add(searchPanel, BorderLayout.NORTH);
		this.add(resultPanel, BorderLayout.WEST);
		this.add(detailPanel, BorderLayout.CENTER);
		this.add(simBizPanel, BorderLayout.EAST);
		this.add(outputPanel, BorderLayout.SOUTH);
		
		pack();
	}
	
	/*
	 * set based path to access data and image files
	 */
	public void setPath(String path){
		this.basePath = path;
	}
	
	/*
	 * Design output panel using JTextArea
	 */
	public void buildOutputPanel(){
		// Create output panel and set layout
		outputPanel = new JPanel(new GridLayout(1,1));
		
		txtOutput = new JTextArea();
		txtOutput.setEditable(false);
		txtOutput.setRows(5);
		txtOutput.setLineWrap(true);
		
		outputPanel.add(new JScrollPane(txtOutput));
        outputPanel.setBorder(BorderFactory.createTitledBorder("Debugging Output"));
	}
	
	/*
	 * To append new data in to the txtOutput (inside outputPanel)
	 */
	public void appendOutputPanel(String msg){
		this.txtOutput.append(msg);
	}
	
	/*
	 * Design and build searchPanel where user can select search criteria 
	 * and click on button to trigger events
	 */
	public void buildSearchPanel(){
		// Create search panel and set layout
		searchPanel = new JPanel();
		searchPanel.setLayout(new BorderLayout());
		
		JPanel greeting = new JPanel();
		JLabel lbGreeting = new JLabel("Welcome to Restaurant Search Engine");
		greeting.add(lbGreeting);
		
		JPanel criteria = new JPanel();
		
		criteria.add(lbName);
		criteria.add(txtName);
		criteria.add(lbCategory);
		criteria.add(cbCategory);
		criteria.add(lbPrice);
		
		// Create radio buttons for price range
		rbPrice1 = new JRadioButton("$");
		rbPrice2 = new JRadioButton("$$");
		rbPrice3 = new JRadioButton("$$$");
		
		// Create a ButtonGroup object
		grPrice = new ButtonGroup();
		grPrice.add(rbPrice1);
		grPrice.add(rbPrice2);
		grPrice.add(rbPrice3);
		
		criteria.add(rbPrice1);
		criteria.add(rbPrice2);
		criteria.add(rbPrice3);
		criteria.setBorder(BorderFactory.createTitledBorder("Search Criteria"));
		
		cbOpen = new JCheckBox("Open Now");
		criteria.add(cbOpen);
		
		JPanel control = new JPanel();
		control.add(lbSorted);
		control.add(cbSortedBy);
		control.add(btSearch);
		control.add(btClear);
		
		searchPanel.add(greeting, BorderLayout.NORTH);
		searchPanel.add(criteria, BorderLayout.CENTER);
		searchPanel.add(control, BorderLayout.SOUTH);
	}
	

	/*
	 * Design and initialize resultPanel where the search result will be displayed
	 * JTable is used to show result in a table form style
	 */
	public void buildResultPanel(){
		
		resultTable = new JTable(new BizTableModel());
		resultTable.setPreferredScrollableViewportSize(new Dimension(400, 400));
		resultTable.setFillsViewportHeight(true);
		
        TableColumn column = null;
        
        for (int j = 0; j < resultTable.getColumnCount(); j++) {
            column = resultTable.getColumnModel().getColumn(j);
            if (j == 0 || j == 3 || j == 4) {
                column.setPreferredWidth(20); 
            } else {
                column.setPreferredWidth(100);
            }
        }
		
		resultPanel = new JPanel();
		resultPanel.setBorder(BorderFactory.createTitledBorder("Search Result"));
		resultPanel.add(resultTable);
	}
	
	/*
	 * Design and initialize detailPanel including two main parts: 
	 * restaurant full profile, list of all reviews
	 */
	public void buildDetailPanel(){
		
		detailPanel = new JPanel();
		detailPanel.setLayout(new GridLayout(2,1));
		detailPanel.setBorder(BorderFactory.createTitledBorder("Restaurant Profile"));
		
		bizProfilePanel = new JPanel();
		bizProfilePanel.setLayout(new GridLayout(1,2));
	
        bizProfilePanel.add(new JLabel("--- NOT Avaialble ---")); 
	    
	    reviewPanel = new JPanel();
	    reviewPanel.setBorder(BorderFactory.createTitledBorder("Reviews"));
	    reviewPanel.add(new JLabel("------------------"));
	    
	    detailPanel.add(bizProfilePanel);
	    detailPanel.add(reviewPanel);
	}
	
	/*
	 * To build a similar panel on the UI
	 * This panel will display the brief profile (or snippet profile) of the given business IDs
	 * If the list of the given business ID is equal to null, 
	 * the label 'NOT available' will be shown on this similar panel.
	 */
	public void buildSimilarPanel(){
		simBizPanel = new JPanel();
		simBizPanel.setBorder(BorderFactory.createTitledBorder("You may also like these"));
		
		simBizProfilePanel = new JPanel();
		simBizProfilePanel.add(new JLabel("-------NOT available----------"));
		
		simBizPanel.add(simBizProfilePanel);
	}
	
	/*
	 * To clear the current GUI
	 * The clearAll() consists of other four methods: clearSimilarBiz(), clearResultBiz(), clearProfileBiz(), and clearSearchSelection()
	 */
	
	public void clearAll(){
		this.clearSearchSelection();
		this.clearResultBiz();
		this.clearProfileBiz();
		this.clearSimilarBiz();
		pack();
	}
	
	public void clearSearchSelection(){
		this.txtName.setText("");
		this.cbCategory.setSelectedIndex(0);
		this.grPrice.clearSelection();
		this.cbSortedBy.setSelectedIndex(0);
		this.cbOpen.setSelected(false);
	}
	
	
	
	public void clearSimilarBiz(){
		simBizProfilePanel.removeAll();
		JLabel notAvailable = new JLabel("-------NOT available----------");
		simBizProfilePanel.add(notAvailable);	
	}
	
	public void clearResultBiz(){
		((BizTableModel)this.resultTable.getModel()).deleteAllRows();
	}
	
	public void clearProfileBiz(){
		bizProfilePanel.removeAll();
		bizProfilePanel.add(new JLabel("--- NOT Available ---"));
		reviewPanel.removeAll();
		reviewPanel.add(new JLabel("--- NOT Available ---"));
	}
	
	
	/*
	 * Get user input from the GUI (you may think of this part as getter methods in the java class)
	 */
	public String getSearchName(){
		return txtName.getText();
	}
	
	public String getSearchCategory(){
		return (String) cbCategory.getSelectedItem();
	}
	
	public String getSearchPriceRange(){
		if(rbPrice1.isSelected())
			return rbPrice1.getText();
		else if(rbPrice2.isSelected())
			return rbPrice2.getText();
		else if(rbPrice3.isSelected())
			return rbPrice3.getText();
		else
			return "";
	}
	
	public boolean getCheckOpenNow(){
		return cbOpen.isSelected();
	}
	
	public String getSortedBy(){
		return (String) cbSortedBy.getSelectedItem();
	}
	
	public int getSelectedResultRow(){
		int x = resultTable.getSelectedRow();
		System.out.println("selected restaurant id: " + resultTable.getValueAt(x, 0));
		return (int)(resultTable.getValueAt(x, 0));
	}
	
	public int getResultRowCount(){
		return this.resultTable.getRowCount();
	}
	
	/*
	 * add ActionListener to the search button
	 */
	public void addSearchListener(ActionListener listenSearchButton){
		this.btSearch.addActionListener(listenSearchButton);
	}
	
	/*
	 * add ActionListener to the clear button
	 */
	public void addClearListener(ActionListener listenClearButton){
		this.btClear.addActionListener(listenClearButton);
	}
	
	/*
	 * add ListSelectionListener to the result table
	 */
	public void addResultListener(ListSelectionListener rowListener){
		resultTable.getSelectionModel().addListSelectionListener(rowListener);
	}
	
	
	/*
	 * to display result from the array of BusinessInterface
	 */
	
	public void setResult(BusinessInterface[] result){
		System.out.println("in set result");
		this.resultPanel.removeAll();
		((BizTableModel)resultTable.getModel()).deleteAllRows();
		
		if(result == null || result.length == 0){
			System.out.println("NOT available");
			this.resultPanel.add(new JLabel("---Restaurant Not Found, Please Try Again---"));
		} else{
			for(BusinessInterface b: result){
				((BizTableModel)resultTable.getModel()).add(b);
			}
			
			//Create the scroll pane and add the table to it.
	        JScrollPane scrollPane = new JScrollPane(resultTable);
	
	        //Add the scroll pane to this panel.
	        this.resultPanel.add(scrollPane);
	        ((BizTableModel)resultTable.getModel()).fireTableDataChanged();
		}
		pack();	// update UI
	}
	
	
	/*
	 * to display restaurant profile
	 */
	public void setRestaurantProfile(Restaurant r){
		// remove existing profile in the panel
		detailPanel.removeAll();
		
		// set new similar business profile panel
		bizProfilePanel = new JPanel(new GridLayout(1,1));    	
		JPanel fullProfilePanel = new JPanel(new BorderLayout());
        String path = this.basePath + "images/" + r.getID() + ".png";
        File image = new File(path);
        if(!image.exists())
        	path = this.basePath + "images/not_available.png";
		ImageIcon imageIcon = new ImageIcon(new ImageIcon(path).getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
        JLabel lbImage = new JLabel(imageIcon);
            
        JTextArea txtFullProfile = new JTextArea();
        txtFullProfile.setEditable(false);
        txtFullProfile.setLineWrap(true);
        txtFullProfile.setText(r.getFullProfile());
        txtFullProfile.setBackground(Color.decode("#e6eeff"));
        
        fullProfilePanel.add(lbImage, BorderLayout.WEST);
        fullProfilePanel.add(new JScrollPane(txtFullProfile), BorderLayout.CENTER);
        fullProfilePanel.setBorder(new MatteBorder(1, 1, 1, 1, Color.decode("#3374ff")));
        bizProfilePanel.add(fullProfilePanel);
        
        JTextArea txtReview = new JTextArea();
        System.out.println("size of review" + r.getReviews().size() + "-------------");
        if(r.getReviews().size() == 0){
        	txtReview = new JTextArea("Average Review Score: " + r.getAverageRating() + "\n"
        					+ "---- NO review available ----");
        } else{
        	String averageReview = "Average Review Score: " + r.getAverageRating() + "\n";
        	String allReviews = "";
        	for(Review review: r.getReviews()){
        		allReviews += review.toString() + "\n";
        	}
        	txtReview = new JTextArea(averageReview + allReviews);
        }
        txtReview.setEditable(false);
        txtReview.setLineWrap(true);
        txtReview.setBackground(Color.decode("#e6eeff"));
        txtReview.setBorder(BorderFactory.createEmptyBorder());
        
        reviewPanel.removeAll();
        reviewPanel.setLayout(new GridLayout(1,1));
        reviewPanel.add(new JScrollPane(txtReview));
        
    	detailPanel.add(bizProfilePanel);
    	detailPanel.add(reviewPanel);
    
    	pack();
	}

	/*
	 * to display similar restaurants with snippet information
	 * 
	 * @input: array of BusinessInterface and array of similarity scores
	 * 
	 */
	public void setSimilarBusinessPanel(BusinessInterface[] biz, double[] simScores){
		// remove existing profile in the panel
		simBizPanel.removeAll();
		
		// set new similar business profile panel
    	simBizProfilePanel = new JPanel(new GridLayout(3,0));
    	for(int k = 0; k < biz.length; k++){
        	BusinessInterface b = biz[k];
        	JPanel snippetPanel = new JPanel(new BorderLayout());
	        String path = this.basePath + "images/" + b.getID() + ".png";
	        File image = new File(path);
	        if(!image.exists())
	        	path = this.basePath + "images/not_available.png";
			ImageIcon imageIcon = new ImageIcon(new ImageIcon(path).getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
	        JLabel lbImage = new JLabel(imageIcon);
	        
	        JTextArea txtSnippet = new JTextArea();
	        txtSnippet.setColumns(15);
	        txtSnippet.setEditable(false);
	        txtSnippet.setLineWrap(true);
	        txtSnippet.setOpaque(false);
	        txtSnippet.setBorder(BorderFactory.createEmptyBorder());
	        txtSnippet.setText(b.getSnippetProfile() + "\nSimilarity Score: " + simScores[k]);
	        snippetPanel.add(lbImage, BorderLayout.WEST);
	        snippetPanel.add(new JScrollPane(txtSnippet), BorderLayout.CENTER);
	        snippetPanel.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLUE));

	        simBizProfilePanel.add(snippetPanel);
        }
    	simBizPanel.add(simBizProfilePanel);
    	pack();
    }
	
   
	/*
	 * to display an alert message
	 */
	public void displayAlertMsg(String msg){
		JOptionPane.showMessageDialog(this, msg);
	}
	
	/*
	 * Create a new class called BizTableModel which extends from AbstractoModel
	 * This model is a custom model to custom the display of resultTable (JTable)
	 */
	class BizTableModel extends AbstractTableModel {

		private String[] header = {"ID", "Name", "Category", "Rating", "#Reviews"};
		private ArrayList<BusinessInterface> biz;
		private ArrayList<Object[]> display;
		
		public BizTableModel() {
			biz = new ArrayList<BusinessInterface>();
			display = new ArrayList<Object[]>();
		}
		
		public int getRowCount() {
			return biz.size();
		}

		public int getColumnCount() {
			return header.length;
		}

		public Object getValueAt(int rowIndex, int columnIndex) {
			if(rowIndex >= 0 && rowIndex < getRowCount())
				return display.get(rowIndex)[columnIndex];
			else
				return "";
		}

		public BusinessInterface getBusinessAt(int rowIndex){
			return biz.get(rowIndex);
		}
		
		public int getBizIdAt(int rowIndex){
			return biz.get(rowIndex).getID();
		}
		
		public String getColumnName(int index) {
			return header[index];
		}

		public void add(BusinessInterface b) {
			biz.add(b);
			Object[] content = new Object[header.length];
			if(b instanceof Restaurant){
				Restaurant r = (Restaurant) b;
				content[0] = r.getID();
				content[1] = r.getName();
				content[2] = r.getCategoriesString();
				content[3] = r.getAverageRating();
				content[4] = r.getReviews().size();
			} else{
				content[0] = b.getID();
				content[1] = b.getName();
			}
			display.add(content);
			fireTableDataChanged();
		}
		
		public void deleteAllRows(){
			biz.clear();
			display.clear();
			fireTableDataChanged();
		}
	}
}
