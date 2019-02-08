import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class createAcc {

	private JFrame frmSignUpPage;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void newAcc() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					createAcc window = new createAcc();
					window.frmSignUpPage.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public createAcc() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSignUpPage = new JFrame();
		frmSignUpPage.setTitle("Sign Up Page");
		frmSignUpPage.setBounds(100, 100, 450, 300);
		frmSignUpPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSignUpPage.getContentPane().setLayout(null);
		
		JLabel lblFirstName = new JLabel("First Name:");
		lblFirstName.setBounds(80, 60, 83, 16);
		frmSignUpPage.getContentPane().add(lblFirstName);
		
		JLabel lblLastName = new JLabel("Last Name:");
		lblLastName.setBounds(80, 88, 70, 16);
		frmSignUpPage.getContentPane().add(lblLastName);
		
		JLabel lblUserNameemail = new JLabel("User Name (email):");
		lblUserNameemail.setBounds(80, 146, 132, 16);
		frmSignUpPage.getContentPane().add(lblUserNameemail);
		
		JLabel lblGender = new JLabel("Gender:");
		lblGender.setBounds(80, 118, 70, 16);
		frmSignUpPage.getContentPane().add(lblGender);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(80, 179, 104, 16);
		frmSignUpPage.getContentPane().add(lblPassword);
		
		textField = new JTextField();
		textField.setBounds(208, 55, 130, 26);
		frmSignUpPage.getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(208, 83, 130, 26);
		frmSignUpPage.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(208, 141, 130, 26);
		frmSignUpPage.getContentPane().add(textField_2);
		textField_2.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(208, 174, 130, 26);
		frmSignUpPage.getContentPane().add(passwordField);
		
		JRadioButton rdbtnMale = new JRadioButton("Male");
		rdbtnMale.setBounds(208, 114, 70, 23);
		frmSignUpPage.getContentPane().add(rdbtnMale);
		
		JRadioButton rdbtnFemale = new JRadioButton("Female");
		rdbtnFemale.setBounds(293, 114, 83, 23);
		frmSignUpPage.getContentPane().add(rdbtnFemale);
		
		JButton btnSignUp = new JButton("Sign Up");
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JOptionPane.showMessageDialog(frmSignUpPage, "Account created successfully!");
			}
		});
		btnSignUp.setBounds(208, 223, 117, 29);
		frmSignUpPage.getContentPane().add(btnSignUp);
	}
}
