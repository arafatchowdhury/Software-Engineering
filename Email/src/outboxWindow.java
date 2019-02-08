import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class outboxWindow 
{

	private JFrame frame;
	composeMail compose;
	static boolean isSelected = false;
	private static String email_address;

	/**
	 * Launch the application.
	 */
	public static void outboxWindow(String email) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try
				{
					email_address = email;
					outboxWindow window = new outboxWindow();
					window.frame.setVisible(true);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public outboxWindow() 
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		//creates the outbox window
		frame = new JFrame();
		frame.setBounds(100, 100, 666, 379);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBounds(0, 11, 158, 318);
		frame.getContentPane().add(buttonPanel);
		
		JButton btnComposeMail = new JButton("Compose Mail");
		btnComposeMail.addActionListener(new createMail());
		
		JButton btnInbox = new JButton("Inbox");
		btnInbox.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				LogIn.email.testerMsg(email_address);
			}
		});
		
		JButton btnDraft = new JButton("Draft");
		btnDraft.addActionListener(new openDrafts());
		
		JButton btnSent = new JButton("Sent");
		btnSent.addActionListener(new SentWindow());
		
		JButton LogOut = new JButton("Logout");
		LogOut.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				LogIn.clearTextField();
				frame.dispose();
				LogIn.frame.setVisible(true);
			}
		});
		
		//button panel layout
		GroupLayout gl_buttonPanel = new GroupLayout(buttonPanel);
		gl_buttonPanel.setHorizontalGroup(
			gl_buttonPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_buttonPanel.createSequentialGroup()
					.addGroup(gl_buttonPanel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_buttonPanel.createSequentialGroup()
							.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(btnComposeMail, GroupLayout.PREFERRED_SIZE, 138, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_buttonPanel.createSequentialGroup()
							.addContainerGap()
							.addComponent(LogOut, GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)))
					.addContainerGap())
				.addGroup(Alignment.TRAILING, gl_buttonPanel.createSequentialGroup()
					.addContainerGap(33, Short.MAX_VALUE)
					.addGroup(gl_buttonPanel.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(btnSent, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnDraft, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnInbox, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE))
					.addGap(28))
		);
		gl_buttonPanel.setVerticalGroup(
			gl_buttonPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_buttonPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnComposeMail, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnInbox, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnDraft, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnSent, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 71, Short.MAX_VALUE)
					.addComponent(LogOut, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		buttonPanel.setLayout(gl_buttonPanel);
		
		//creates the table where the sent emails are stored
		LogIn.connect.sentTable();
		
		JScrollPane scrollPane = new JScrollPane(MySQLAccess.tableSent);
		MySQLAccess.tableSent.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				//opens the selected email in a new window and displays its contents
				if(e.getClickCount() == 1 && isSelected == false)
				{
					String mailID;
					int row = MySQLAccess.tableSent.getSelectedRow();
					mailID = MySQLAccess.tableSent.getModel().getValueAt(row, 0).toString();
					LogIn.connect.retrieveSent(mailID);
					frame.dispose();
					isSelected = true;
				}
			}
		});
		scrollPane.setBounds(168, 11, 472, 318);
		frame.getContentPane().add(scrollPane);
		
		isSelected = false;
	}
	
	//opens compose mail window
	public class createMail implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			frame.dispose();
			compose = new composeMail();
			compose.composeMails(email_address);
		}
	}
	
	//opens the drafts window
	public class openDrafts implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			frame.dispose();
			msgBoxx.drafts = new DraftWindow();
			msgBoxx.drafts.draftsWindow(email_address);
		}
	}
	
	//opens the outbox 
	public class SentWindow implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			frame.dispose();
			msgBoxx.outbox.outboxWindow(email_address);
		}
	}

}
