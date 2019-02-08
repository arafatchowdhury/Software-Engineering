import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class Calc {

	private JFrame frame;
	private JTextField textField;
	
	Double firstNumber;
	Double secondNumber;
	String operation;
	String ans;
	Double result;
	boolean dotCounter = false;
	boolean operationCounter = false;

	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Calc window = new Calc();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Calc() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 301, 372);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(6, 6, 288, 67);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		// ------------------------------------------------- ROW 1 ----------------------------------------------------------------//

		
		JButton btn_Clear = new JButton("AC");
		btn_Clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText(null);
				dotCounter = false;
			}
		});
		btn_Clear.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		btn_Clear.setBounds(6, 74, 69, 53);
		frame.getContentPane().add(btn_Clear);
		
		//back space
		JButton btn_Back = new JButton("<-");
		btn_Back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String backS = null;
				if(textField.getText().length()>0)
				{
					StringBuilder builder = new StringBuilder(textField.getText());
					builder.deleteCharAt(textField.getText().length()-1);
					backS = builder.toString();
					textField.setText(backS);
				}
				
			}
		});
		btn_Back.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		btn_Back.setBounds(77, 74, 75, 53);
		frame.getContentPane().add(btn_Back);
		
		JButton btn_Percent = new JButton("%");
		btn_Percent.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		btn_Percent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				firstNumber = Double.parseDouble(textField.getText());
				textField.setText("");
				operation = "%";
			}
		});
		btn_Percent.setBounds(151, 74, 75, 53);
		frame.getContentPane().add(btn_Percent);
		
		
		JButton btn_Plus = new JButton("+");
		btn_Plus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				firstNumber = Double.parseDouble(textField.getText());
				textField.setText("");
				operation = "+";
				dotCounter = false;
				operationCounter = true;
			}
		});
		btn_Plus.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		btn_Plus.setBounds(225, 74, 69, 53);
		frame.getContentPane().add(btn_Plus);
		
		// ------------------------------------------------- ROW 2 ----------------------------------------------------------------//
		
		JButton btn_7 = new JButton("7");
		btn_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String Enter = textField.getText() + btn_7.getText();
				textField.setText(Enter);
				
			}
		});
		btn_7.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		btn_7.setBounds(6, 129, 69, 53);
		frame.getContentPane().add(btn_7);
		
		JButton btn_8 = new JButton("8");
		btn_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String Enter = textField.getText() + btn_8.getText();
				textField.setText(Enter);
			}
		});
		btn_8.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		btn_8.setBounds(77, 129, 75, 53);
		frame.getContentPane().add(btn_8);
		
		JButton btn_9 = new JButton("9");
		btn_9.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String Enter = textField.getText() + btn_9.getText();
					textField.setText(Enter);
				}
		});
		btn_9.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		btn_9.setBounds(151, 129, 75, 53);
		frame.getContentPane().add(btn_9);
		
		JButton btn_Minus = new JButton("-");
		btn_Minus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				firstNumber = Double.parseDouble(textField.getText());
				textField.setText("");
				operation = "-";
				dotCounter = false;
				operationCounter = true;

			}
		});
		btn_Minus.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		btn_Minus.setBounds(225, 129, 69, 53);
		frame.getContentPane().add(btn_Minus);
		
		
		// ------------------------------------------------- ROW 3 ----------------------------------------------------------------//
		
		
		JButton btn_4 = new JButton("4");
		btn_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String Enter = textField.getText() + btn_4.getText();
				textField.setText(Enter);
			}
		});
		btn_4.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		btn_4.setBounds(6, 184, 69, 53);
		frame.getContentPane().add(btn_4);
		
		JButton btn_5 = new JButton("5");
		btn_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String Enter = textField.getText() + btn_5.getText();
				textField.setText(Enter);
			}
		});
		btn_5.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		btn_5.setBounds(77, 184, 75, 53);
		frame.getContentPane().add(btn_5);
		
		JButton btn_6 = new JButton("6");
		btn_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String Enter = textField.getText() + btn_6.getText();
				textField.setText(Enter);
			}
		});
		btn_6.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		btn_6.setBounds(151, 184, 75, 53);
		frame.getContentPane().add(btn_6);
		
		
		JButton btn_Mult = new JButton("x");
		btn_Mult.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				firstNumber = Double.parseDouble(textField.getText());
				textField.setText("");
				operation = "x";
				dotCounter = false;
				operationCounter = true;

			}
		});
		btn_Mult.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		btn_Mult.setBounds(225, 184, 69, 53);
		frame.getContentPane().add(btn_Mult);
		
		// ------------------------------------------------- ROW 4 ----------------------------------------------------------------//

		
		JButton btn_1 = new JButton("1");
		btn_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String Enter = textField.getText() + btn_1.getText();
				textField.setText(Enter);
			}
		});
		btn_1.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		btn_1.setBounds(6, 233, 69, 53);
		frame.getContentPane().add(btn_1);
		
		JButton btn_2 = new JButton("2");
		btn_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String Enter = textField.getText() + btn_2.getText();
				textField.setText(Enter);
			}
		});
		btn_2.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		btn_2.setBounds(77, 233, 75, 53);
		frame.getContentPane().add(btn_2);
		
		JButton btn_3 = new JButton("3");
		btn_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String Enter = textField.getText() + btn_3.getText();
				textField.setText(Enter);
			}
		});
		btn_3.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		btn_3.setBounds(151, 233, 75, 53);
		frame.getContentPane().add(btn_3);
		
		
		JButton btn_Dvd = new JButton("÷");
		btn_Dvd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				firstNumber = Double.parseDouble(textField.getText());
				textField.setText("");
				operation = "÷";
				dotCounter = false;
				operationCounter = true;
			}
		});
		btn_Dvd.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		btn_Dvd.setBounds(225, 233, 69, 53);
		frame.getContentPane().add(btn_Dvd);
		
		
		// ------------------------------------------------- ROW 5 ----------------------------------------------------------------//



		JButton btn_0 = new JButton("0");
		btn_0.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				
				// first number
				if(operationCounter == false || textField.getText().length()==0) {}
				
				if( (dotCounter == true  && operationCounter == false) || ( operationCounter == false && textField.getText().length() > 0 )) //
				{
					String Enter = textField.getText() + btn_0.getText();
					textField.setText(Enter);
				}
				
				
				// second number 
				
				// allowing 1st zero
				if(textField.getText().length() == 0 && operationCounter == true)
				{
					String Enter = textField.getText() + btn_0.getText();
					textField.setText(Enter);
				}
				
				Double checki = Double.parseDouble(String.valueOf(textField.getText()));
				//String backS = null;
				
//				if(checki == 0.0 && textField.getText().length() == 1 && operationCounter == true)
//				{	
//					textField.setText(null);				
//				}
				
				if((dotCounter == true && operationCounter == true ) || (textField.getText().length() > 0 && operationCounter == true)) // //textField.getText().length()==0) ||  
				{
					if(textField.getText().equals("0"))
					{
						textField.setText(null);
					}
						String Enter = textField.getText() + btn_0.getText();
						textField.setText(Enter);				
				}			
					
			}
		});
		btn_0.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		btn_0.setBounds(6, 288, 69, 53);
		frame.getContentPane().add(btn_0);
		
		
		
		
		
		//dot button
		JButton btn_Decimal = new JButton(".");
		btn_Decimal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (dotCounter != true) {
					if (textField.getText().length()==0)
					{
						textField.setText( textField.getText() + "0" );
					}
				textField.setText( textField.getText() + "." );
				dotCounter = true;
			
				}}
		});
		btn_Decimal.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		btn_Decimal.setBounds(77, 288, 75, 53);
		frame.getContentPane().add(btn_Decimal);
		

		
		
		JButton btn_P_M = new JButton("+/-");
		btn_P_M.addActionListener(new ActionListener() {		
			public void actionPerformed(ActionEvent e) {

				
				String what = textField.getText();	
				char first = what.charAt(0);
				
				Double numb = Double.parseDouble(String.valueOf(textField.getText()));
				
				if (numb == 0.0 & dotCounter == true) {}	
								
				else if(first != '-')
				{
					what = "-" + what;
				}
				else 
				{
					what = what.replaceFirst("-", "");
				}
				
				textField.setText(what);
			}
		});
		btn_P_M.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		btn_P_M.setBounds(151, 288, 75, 53);
		frame.getContentPane().add(btn_P_M);
		
		JButton btn_Equal = new JButton("=");
		btn_Equal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String answer;
				secondNumber = Double.parseDouble(textField.getText());
				
				if(secondNumber == null) {}
				
				if(operation == "+")
				{
					result = firstNumber + secondNumber;
					if (result % 1 == 0 )
					{
						textField.setText(Integer.toString(result.intValue()));
					}else {
//					answer = String.format("%.2f", result);
//					textField.setText(answer);
					
						String testin = result.toString();
						textField.setText(testin);
					}
				}
				else if(operation == "-")
				{
					result = firstNumber - secondNumber;
					
					if (result % 1 == 0 )
					{
						textField.setText(Integer.toString(result.intValue()));
					}
					else {
//					answer = String.format("%.2f", result);
//					//answer = Double.toString(result);
//					textField.setText(answer);
					
					
					String testin = result.toString();
					
					textField.setText(testin);
					
					}
				}
				else if(operation == "x")
				{
					result = firstNumber * secondNumber;
					if (result % 1 == 0 )
					{
						textField.setText(Integer.toString(result.intValue()));
					}else {
//					answer = String.format("%.2f", result);
//					textField.setText(answer);
					String testin = result.toString();
					textField.setText(testin);
					}
				}
				else if(operation == "÷")
				{
					if(secondNumber == 0)
					{
						JOptionPane.showMessageDialog(null, "ERROR: Divide by ZERO.");
						    
					}
					result = firstNumber / secondNumber;
					if (result % 1 == 0 )
					{
						textField.setText(Integer.toString(result.intValue()));
					}else {
//					answer = String.format("%.2f", result);
//					textField.setText(answer);
					String testin = result.toString();
					textField.setText(testin);
					}
				}
				else if(operation == "%")
				{
					result = firstNumber % secondNumber;
					if (result % 1 == 0 )
					{
						textField.setText(Integer.toString(result.intValue()));
					}else {
//					answer = String.format("%.2f", result);
//					textField.setText(answer);
					String testin = result.toString();
					textField.setText(testin);
					}
				}

			}
		});
		btn_Equal.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		btn_Equal.setBounds(225, 288, 69, 53);
		frame.getContentPane().add(btn_Equal);
	}
}
