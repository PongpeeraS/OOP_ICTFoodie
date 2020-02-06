package mvc;

public class CalculatorModel {
	
	// contain data
	private int calVal;
	
	// perform the method
	public void addTwoNumbers(int num1, int num2){
		calVal = num1 + num2;
	}
	
	// provide access to the data
	public int getCalVal(){
		return calVal;
	}
	
}
