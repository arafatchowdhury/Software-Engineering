import java.awt.EventQueue;
import javax.swing.*;
import java.awt.event.*;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;


public class msgBoxx 
{
	static JFrame frame;
	private static String email_address;
	composeMail compose;
	static DraftWindow drafts;
	static outboxWindow outbox;
	static MessageWindow viewEmail;
	static boolean isSelected = false;

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
					msgBoxx window = new msgBoxx();
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
	public msgBoxx() 
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() 
	{
		//creates the inbox window
		frame = new JFrame("Inbox");
		frame.setBounds(100, 100, 666, 379);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		//container of the buttons
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
				frame.dispose();
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
		
		//layout of the button panel
		GroupLayout gl_buttonPanel = new GroupLayout(buttonPanel);
		gl_buttonPanel.setHorizontalGroup(
			gl_buttonPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_buttonPanel.createSequentialGroup()
					.addGroup(gl_buttonPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_buttonPanel.createSequentialGroup()
							.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(btnComposeMail, GroupLayout.PREFERRED_SIZE, 138, GroupLayout.PREFERRED_SIZE))
						.addGroup(Alignment.TRAILING, gl_buttonPanel.createSequentialGroup()
							.addContainerGap()
							.addComponent(LogOut, GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)))
					.addContainerGap())
				.addGroup(Alignment.TRAILING, gl_buttonPanel.createSequentialGroup()
					.addContainerGap(32, Short.MAX_VALUE)
					.addGroup(gl_buttonPanel.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(btnSent, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnDraft, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnInbox, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
					.addGap(26))
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
		
		//creates the table where the inbox emails are stored
		LogIn.connect.inboxTable();
		
		//the table is placed inside a scroll pane in case the are a lot of emails in the inbox
		JScrollPane scrollPane = new JScrollPane(MySQLAccess.tableInbox);
		
		MySQLAccess.tableInbox.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				//opens the selected email in a new window and displays the content
				if(e.getClickCount() == 1 && isSelected == false)
				{
					String mailID;
					int row = MySQLAccess.tableInbox.getSelectedRow();
					mailID = MySQLAccess.tableInbox.getModel().getValueAt(row, 0).toString();
					LogIn.connect.retrieveEmail(mailID);
					frame.dispose();
					isSelected = true;
				}
			}
		});
		scrollPane.setBounds(168, 11, 472, 318);
		frame.getContentPane().add(scrollPane);
		
		isSelected = false;
	}
	
	//opens the compose mail window
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
			drafts = new DraftWindow();
			drafts.draftsWindow(email_address);
		}
	}
	
	// opens the outbox
	public class SentWindow implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			frame.dispose();
			outbox.outboxWindow(email_address);
		}
	}
	
	
	
	
	
	
	
	
}
