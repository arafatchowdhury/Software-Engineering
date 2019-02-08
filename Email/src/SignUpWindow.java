import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.EventQueue;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class SignUpWindow extends JFrame
{
	private JFrame SignUpFrame;
	
	public static void signUpWindow()
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run() 
			{
				try 
				{
					SignUpWindow window = new SignUpWindow();
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}
	
	public SignUpWindow() 
	{
		initialize();
	}
	
	public void initialize()
	{
		//creates the signup window
		SignUpFrame = new JFrame("Sign Up");
		SignUpFrame.setBounds(100, 100, 666, 379);
		SignUpFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SignUpFrame.setVisible(true);
		
		JLabel emailLabel = new JLabel("Enter your email address:");
		emailLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		JTextField emailAddress = new JTextField(22);
		emailAddress.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		JLabel passLabel = new JLabel("Enter your password:");
		passLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		JPasswordField emailPass = new JPasswordField(24);
		emailPass.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		JLabel fNameLabel = new JLabel("Enter your first name:");
		fNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		JTextField firstName = new JTextField(24);
		firstName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		JLabel lNameLabel = new JLabel("Enter your last name:");
		lNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		JTextField lastName = new JTextField(24);
		lastName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		JButton SignUp = new JButton("SignUp");
		SignUp.setFont(new Font("Tahoma", Font.PLAIN, 16));
		SignUp.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				/*
				 * checks if the email address and password are empty
				 * checks if the password length is less than 4 or greater than 20
				 * checks if the firstname and last name are empty
				 */
				if(emailAddress.getText().equals("") || emailPass.getText().equals(""))
				{
					JOptionPane.showMessageDialog(null, "Missing password and/or username", "Error", JOptionPane.WARNING_MESSAGE);
				}
				else if(emailPass.getText().length() < 4 || emailAddress.getText().length() > 20)
				{
					JOptionPane.showMessageDialog(null, "Password is too short or too long", "Error", JOptionPane.WARNING_MESSAGE);
				}
				else if(firstName.getText().equals("") || lastName.getText().equals(""))
				{
					JOptionPane.showMessageDialog(null, "Please enter your name", "Error", JOptionPane.WARNING_MESSAGE);
				}
				else
				{
					try 
					{
						//checks the database if the email already exists in the database
						if(LogIn.connect.usercheck(emailAddress.getText()) == false)
						{
							int temp = LogIn.connect.signUp(emailAddress.getText(), emailPass.getText(), firstName.getText(), lastName.getText());
							
							if(temp == JOptionPane.OK_OPTION)
							{
								SignUpFrame.dispose();
								LogIn.frame.setVisible(true);
							}
						}
					} 
					catch (Exception e1) 
					{
						e1.printStackTrace();
					}
				}
				
			}
		});
		
		//layout of the window
		GroupLayout groupLayout = new GroupLayout(SignUpFrame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(265)
							.addComponent(SignUp, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(14)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(emailLabel)
										.addComponent(fNameLabel)))
								.addGroup(groupLayout.createSequentialGroup()
									.addContainerGap()
									.addComponent(lNameLabel))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(14)
									.addComponent(passLabel)))
							.addGap(5)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(emailPass, GroupLayout.DEFAULT_SIZE, 439, Short.MAX_VALUE)
								.addComponent(lastName, GroupLayout.DEFAULT_SIZE, 439, Short.MAX_VALUE)
								.addComponent(firstName, 439, 439, Short.MAX_VALUE)
								.addComponent(emailAddress, GroupLayout.DEFAULT_SIZE, 439, Short.MAX_VALUE))))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(32)
							.addComponent(emailLabel))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(29)
							.addComponent(emailAddress, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(14)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(firstName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(fNameLabel))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lNameLabel)
						.addComponent(lastName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(passLabel)
						.addComponent(emailPass, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 63, Short.MAX_VALUE)
					.addComponent(SignUp, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE)
					.addGap(44))
		);
		SignUpFrame.getContentPane().setLayout(groupLayout);
	}
}