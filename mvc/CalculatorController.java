package mvc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorController {
	private CalculatorView theView;
	private CalculatorModel theModel;
	public CalculatorController(CalculatorView theView, CalculatorModel theModel){
		this.theView = theView;
		this.theModel =theModel;
	
		this.theView.addCalculationListener(new CalculateListener());
	}

	class CalculateListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			int firstNum, secondNum = 0;
			
			try{
				firstNum = theView.getFirstNum();
				secondNum = theView.getSecondNum();
				theModel.addTwoNumbers(firstNum, secondNum);
				
				theView.SetCalSolution(theModel.getCalVal());
			} catch(NumberFormatException ex){
				theView.displayErrorMsg("you need to add two numbers");
			}
		}
		
	}
	
}

