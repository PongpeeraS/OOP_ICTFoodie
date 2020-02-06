package mvc;

import java.awt.List;
import java.awt.event.ActionListener;

import javax.swing.*;

public class CalculatorView extends JFrame{
	private JTextField firstNum = new JTextField(10);
	private JLabel addLabel = new JLabel("+");
	private JTextField secondNum = new JTextField(10);
	private JButton calButton = new JButton("CALCULATE");
	private JTextField calSolution = new JTextField(10);
	
	
	CalculatorView(){
		JPanel calcPanel = new JPanel();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(600, 200);
		
		calcPanel.add(firstNum);
		calcPanel.add(addLabel);
		calcPanel.add(secondNum);
		calcPanel.add(calButton);
		calcPanel.add(calSolution);
			
		//create a list with 5 visible rows
        List list = new List(5, true);
       
        //add items to a list  
        list.add("One");
        list.add("Two");
        list.add("Three");
        list.add("Four");
        list.add("Five");
        list.add("Six");
        list.add("Seven");
       
        //add list
        calcPanel.add(list);
        
		this.add(calcPanel);

	}
	
	public int getFirstNum(){
		return Integer.parseInt(firstNum.getText());
	}
	
	public int getSecondNum(){
		return Integer.parseInt(secondNum.getText());
	}
	
	public int getCalSolution(){
		return Integer.parseInt(calSolution.getText());
	}
	
	public void SetCalSolution(int solution){
		calSolution.setText(Integer.toString(solution));
	}
	
	void addCalculationListener(ActionListener listenCalButton){
		calButton.addActionListener(listenCalButton);
	}
	
	void displayErrorMsg(String msg){
		JOptionPane.showMessageDialog(this, msg);
	}
}
