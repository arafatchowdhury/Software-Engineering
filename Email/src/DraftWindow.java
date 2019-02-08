import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class DraftWindow 
{

	static JFrame frame;
	composeMail compose;
	public static JScrollPane scrollPane;
	private static String email_address;
	static boolean isSelected = false;

	/**
	 * Launch the application.
	 */
	public static void draftsWindow(String email) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					DraftWindow window = new DraftWindow();
					window.frame.setVisible(true);
					email_address = email;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public DraftWindow() 
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() 
	{
		//creates the drafts window
		frame = new JFrame();
		frame.setBounds(100, 100, 666, 379);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBounds(0, 11, 165, 318);
		frame.getContentPane().add(buttonPanel);
		
		JButton btnComposeMail = new JButton("Compose Mail");
		btnComposeMail.addActionListener(new createMail());
		
		JButton btnInbox = new JButton("Inbox");
		btnInbox.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				frame.dispose();
				msgBoxx.frame.setVisible(true);
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
					.addGap(37)
					.addGroup(gl_buttonPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(btnSent, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
						.addComponent(btnDraft, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
						.addComponent(btnInbox, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE))
					.addGap(33))
				.addGroup(Alignment.TRAILING, gl_buttonPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_buttonPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(btnComposeMail, GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
						.addComponent(LogOut, GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE))
					.addContainerGap())
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
		
		//creates the table where the drafts email are stored
		LogIn.connect.draftsTable();

		JScrollPane scrollPane = new JScrollPane(MySQLAccess.tableDraft);
		MySQLAccess.tableDraft.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				//opens the selected email and displays its content on the new window
				if(e.getClickCount() == 1 && isSelected == false)
				{
					String mailID;
					int row = MySQLAccess.tableDraft.getSelectedRow();
					mailID = MySQLAccess.tableDraft.getModel().getValueAt(row, 0).toString();
					LogIn.connect.retrieveDraft(mailID);
					frame.dispose();
					isSelected = true;
				}
			}
		});
		scrollPane.setBounds(175, 11, 465, 318);
		frame.getContentPane().add(scrollPane);
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
			msgBoxx.drafts.draftsWindow(email_address);
		}
	}
	
	//opens the outbox window
	public class SentWindow implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			frame.dispose();
			msgBoxx.outbox.outboxWindow(email_address);
		}
	}
}
