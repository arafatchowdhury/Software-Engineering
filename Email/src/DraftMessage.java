import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.EventQueue;
import javax.swing.GroupLayout.Alignment;

public class DraftMessage 
{
	private JFrame frame;
	composeMail compose;
	private static String email_address;
	static String mailSubject;
	static String sender;
	static String recipient;
	static String message;
	static int mailID;
	private boolean isSent = false;
	private JTextField fromField;
	private JTextField subjectField;
	private JTextArea textArea;

	/**
	 * Launch the application.
	 */
	public static void draftMessageWindow(String mSubject, String mrecipient, String mmessage, String mID) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					mailSubject = mSubject;
					recipient = mrecipient;
					message = mmessage;
					mailID = Integer.parseInt(mID);
					
					DraftMessage window = new DraftMessage();
					window.frame.setVisible(true);
					
					DraftWindow.isSelected = false;
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
	public DraftMessage() 
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() 
	{
		//creates the drafts message window
		frame = new JFrame();
		frame.setBounds(100, 100, 666, 379);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.addWindowListener(new java.awt.event.WindowAdapter()
		{
			/*
			 * overrides the windowClosing method of java awt
			 * so a popup will appear asking if the user wants to save the updated version of the draft or not even if there was no new updates
			 */
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent)
			{
				if(JOptionPane.showOptionDialog(frame, "Do you want to update this draft?", "Draft", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null) == JOptionPane.YES_OPTION)
				{
					LogIn.connect.updateDraft(fromField.getText(), subjectField.getText(), textArea.getText(), "s", mailID);
				}
				msgBoxx.drafts.draftsWindow(email_address);
			}
		});
		frame.getContentPane().setLayout(null);
		
		//from field and label
		JLabel lblFrom = new JLabel("To:");
		lblFrom.setBounds(175, 14, 46, 14);
		frame.getContentPane().add(lblFrom);
		
		fromField = new JTextField(recipient);
		fromField.setBounds(227, 11, 413, 20);
		frame.getContentPane().add(fromField);
		fromField.setColumns(10);
		
		//subject field and label
		JLabel lblSubject = new JLabel("Subject:");
		lblSubject.setBounds(175, 39, 46, 14);
		frame.getContentPane().add(lblSubject);
		
		subjectField = new JTextField(mailSubject);
		subjectField.setBounds(227, 36, 413, 20);
		frame.getContentPane().add(subjectField);
		subjectField.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(175, 67, 465, 213);
		frame.getContentPane().add(scrollPane);
		
		//textarea will not be blank since the message was passed from the database to this window to view/edit drafts
		textArea = new JTextArea();
		try
		{
			textArea.setText(message);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		scrollPane.setViewportView(textArea);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBounds(0, 11, 165, 318);
		frame.getContentPane().add(buttonPanel);
		
		//compose button
		JButton btnComposeMail = new JButton("Compose Mail");
		btnComposeMail.addActionListener(new createMail());
		
		//inbox button
		JButton btnInbox = new JButton("Inbox");
		btnInbox.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				frame.dispose();
				msgBoxx.frame.setVisible(true);
			}
		});
		
		//draft button
		JButton btnDraft = new JButton("Draft");
		btnDraft.addActionListener(new openDrafts());
		
		//sent button
		JButton btnSent = new JButton("Sent");
		btnSent.addActionListener(new SentWindow());
		
		//logout button
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
		
		
		GroupLayout gl_buttonPanel = new GroupLayout(buttonPanel);
		gl_buttonPanel.setHorizontalGroup(
			gl_buttonPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_buttonPanel.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(gl_buttonPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(btnComposeMail, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_buttonPanel.createSequentialGroup()
							.addGap(27)
							.addComponent(btnInbox, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_buttonPanel.createSequentialGroup()
							.addGap(27)
							.addComponent(btnDraft, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_buttonPanel.createSequentialGroup()
							.addGap(27)
							.addComponent(btnSent, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE))
						.addComponent(LogOut, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		gl_buttonPanel.setVerticalGroup(
			gl_buttonPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_buttonPanel.createSequentialGroup()
					.addContainerGap()
					.addGap(8)
					.addComponent(btnComposeMail, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnInbox, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addComponent(btnDraft, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addComponent(btnSent, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
					.addGap(71)
					.addComponent(LogOut, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		buttonPanel.setLayout(gl_buttonPanel);
		
		//send button
		JButton btnSend = new JButton("Send");
		btnSend.setBounds(531, 291, 109, 38);
		btnSend.addActionListener(new sendMail());
		frame.getContentPane().add(btnSend);
		
		//delete button
		JButton btnDelete = new JButton("Delete");
		btnDelete.setBounds(412, 291, 109, 38);
		btnDelete.addActionListener(new deleteMessage());
		frame.getContentPane().add(btnDelete);
	}

	//opens the compose mail window and checks if the email should be updated if it is not sent
	public class createMail implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			frame.dispose();
			if(JOptionPane.showOptionDialog(frame, "Do you want to update this draft?", "Draft", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null) == JOptionPane.YES_OPTION)
			{
				LogIn.connect.updateDraft(fromField.getText(), subjectField.getText(), textArea.getText(), "s", mailID);
			}
			compose = new composeMail();
			compose.composeMails(email_address);
		}
	}

	//opens the outbox window and checks if the email should be updated if it is not sent
	public class SentWindow implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			frame.dispose();
			if(JOptionPane.showOptionDialog(frame, "Do you want to update this draft?", "Draft", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null) == JOptionPane.YES_OPTION)
			{
				LogIn.connect.updateDraft(fromField.getText(), subjectField.getText(), textArea.getText(), "s", mailID);
			}
			msgBoxx.drafts.draftsWindow(email_address);
		}
	}

	//opens the drafts window and checks if the email should be updated if it is not sent
	public class openDrafts implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			frame.dispose();
			if(JOptionPane.showOptionDialog(frame, "Do you want to update this draft?", "Draft", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null) == JOptionPane.YES_OPTION)
			{
				LogIn.connect.updateDraft(fromField.getText(), subjectField.getText(), textArea.getText(), "s", mailID);
			}
			msgBoxx.drafts.draftsWindow(email_address);
		}
	}
	
	//deletes the draft email
	public class deleteMessage implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			frame.dispose();
			LogIn.connect.deleteMail(mailID);
			msgBoxx.drafts.draftsWindow(email_address);
		}
	}
	
	//sends the draft email
	public class sendMail implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(!fromField.getText().equals(""))
			{
				LogIn.connect.sendDraft(fromField.getText(), subjectField.getText(), textArea.getText(), "s", mailID);
				isSent = true;
				frame.dispose();
				msgBoxx.drafts = new DraftWindow();
				msgBoxx.drafts.draftsWindow(email_address);
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Need a recipient before sending", "Error", JOptionPane.WARNING_MESSAGE);
			}
		}
	}
}
