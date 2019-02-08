import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class msgBox extends JFrame
{
	private JFrame msgBoxFrame;
	//static MySQLAccess connect = new MySQLAccess();
	
	public static void main(String args[])//signUp()
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run() 
			{
				try 
				{
					msgBox window = new msgBox();
					//window.setVisible(true);
					
					//connect.connectDataBase();
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}
	
	
	public msgBox() 
	{
		initialize();
	}
	
	public void initialize()
	{
		msgBoxFrame = new JFrame("MAIL BOX");
		msgBoxFrame.setBounds(100, 100, 450, 300);
		msgBoxFrame.setLayout(new FlowLayout(FlowLayout.CENTER));
		msgBoxFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		msgBoxFrame.setVisible(true);
		
		JLabel emailLabel = new JLabel("Enter your email address:");
		JTextField emailAddress = new JTextField(22);
		
		JLabel passLabel = new JLabel("Enter your password:");
		JPasswordField emailPass = new JPasswordField(24);
		
		JLabel fNameLabel = new JLabel("Enter your first name:");
		JTextField firstName = new JTextField(24);
		
		JLabel lNameLabel = new JLabel("Enter your last name:");
		JTextField lastName = new JTextField(24);
		
		JButton SignUp = new JButton("SignUp");
		SignUp.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(2==0)
				{}
				else
				{
					try 
					{
						//connect.connectDataBase();
						if(LogIn.connect.usercheck(emailAddress.getText()) == false)
						{
							int temp = LogIn.connect.signUp(emailAddress.getText(), emailPass.getText(), firstName.getText(), lastName.getText());
							
							if(temp == JOptionPane.OK_OPTION)
							{
								//msgBox.DISPOSE_ON_CLOSE();
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
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}