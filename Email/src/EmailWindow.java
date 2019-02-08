import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.EventQueue;

public class EmailWindow extends JFrame
{
	private JFrame frame;
	private JTextField searchBox;
	private JTable inbox;
	private static String email_address;
	//public Object displayArea;

	/**
	 * Launch the application.
	 */
	public static void testerMsg(String email) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					EmailWindow window = new EmailWindow();
					window.frame.setVisible(true);
					email_address = email;
				} catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}
	
	
	
	

	/**
	 * Create the application.
	 */
	public EmailWindow() 
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 666, 379);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnComposeMail = new JButton("Compose Mail");
		btnComposeMail.setBounds(6, 26, 117, 37);
		frame.getContentPane().add(btnComposeMail);
		
		JButton btnInbox = new JButton("Inbox");
		btnInbox.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				/*JPanel panel = new JPanel();
				JScrollPane tableContainer;
				
				inbox = LogIn.connect.inbox(email_address);
				tableContainer = new JScrollPane(inbox);
				panel.add(tableContainer);
				
				frame.getContentPane().add(panel);*/
			}
		});
		btnInbox.setBounds(6, 68, 117, 29);
		frame.getContentPane().add(btnInbox);
		
		JButton btnDraft = new JButton("Draft");
		btnDraft.setBounds(6, 98, 117, 29);
		frame.getContentPane().add(btnDraft);
		
		JButton btnSent = new JButton("Sent");
		btnSent.setBounds(6, 127, 117, 29);
		frame.getContentPane().add(btnSent);
		
		searchBox = new JTextField();
		searchBox.setBounds(244, 20, 297, 26);
		frame.getContentPane().add(searchBox);
		searchBox.setColumns(10);
		
//		table = new JTable();
//		table.setBounds(172, 74, 471, 265);
//		frame.getContentPane().add(table);
		
		
		
		
		
		/*final JLabel displayArea = new JLabel("");
		displayArea.setBounds(151, 73, 497, 265);
		frame.getContentPane().add(displayArea);
		
//		MySQLAccess hel = new MySQLAccess();
//		hel.inbox();
		//displayArea.setText();

		displayArea.setText("RECEIVED E-MAIL SHOULD BE SHOWED HERE");*/
		
		
		
	}
}