import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.EventQueue;
import javax.swing.GroupLayout.Alignment;

public class MessageWindow 
{
	private JFrame messageFrame;
	composeMail compose;
	private static String email_address;
	static String mailSubject;
	static String sender;
	static String recipient;
	static String message;
	static int mailID;
	private JTextField fromField;
	private JTextField subjectField;

	/**
	 * Launch the application.
	 */
	public MessageWindow(String mSubject, String msender, String mmessage, String mID) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					mailSubject = mSubject;
					recipient = msender;
					message = mmessage;
					mailID = Integer.parseInt(mID);
					
					MessageWindow window = new MessageWindow();
					window.messageFrame.setVisible(true);
					msgBoxx.isSelected = false;
					
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
	public MessageWindow() 
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() 
	{
		//creates the message frame
		messageFrame = new JFrame("Mail");
		messageFrame.setBounds(100, 100, 666, 379);
		messageFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		messageFrame.getContentPane().setLayout(null);
		
		//from field and label
		JLabel lblFrom = new JLabel("From:");
		lblFrom.setBounds(175, 14, 46, 14);
		messageFrame.getContentPane().add(lblFrom);
		
		fromField = new JTextField(recipient);
		fromField.setBounds(227, 11, 413, 20);
		fromField.setEditable(false);
		messageFrame.getContentPane().add(fromField);
		fromField.setColumns(10);
		
		//subject field and label
		JLabel lblSubject = new JLabel("Subject:");
		lblSubject.setBounds(175, 39, 46, 14);
		messageFrame.getContentPane().add(lblSubject);
		
		subjectField = new JTextField(mailSubject);
		subjectField.setBounds(227, 36, 413, 20);
		subjectField.setEditable(false);
		messageFrame.getContentPane().add(subjectField);
		subjectField.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(175, 67, 465, 213);
		messageFrame.getContentPane().add(scrollPane);
		
		//textarea is not editable because this window is used for view emails that found in the outbox or inbox
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
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
		messageFrame.getContentPane().add(buttonPanel);
		
		//compose button
		JButton btnComposeMail = new JButton("Compose Mail");
		btnComposeMail.addActionListener(new createMail());
		
		//inbox button
		JButton btnInbox = new JButton("Inbox");
		btnInbox.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				messageFrame.dispose();
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
				messageFrame.dispose();
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
		
		//reply button
		JButton btnReply = new JButton("Reply");
		btnReply.setBounds(531, 291, 109, 38);
		btnReply.addActionListener(new replyMessage());
		messageFrame.getContentPane().add(btnReply);
		
		//forward button
		JButton btnForward = new JButton("Forward");
		btnForward.setBounds(412, 291, 109, 38);
		btnForward.addActionListener(new forwardMessage());
		messageFrame.getContentPane().add(btnForward);
		
		//delete button
		JButton btnDelete = new JButton("Delete");
		btnDelete.setBounds(293, 291, 109, 38);
		btnDelete.addActionListener(new deleteMessage());
		messageFrame.getContentPane().add(btnDelete);
	}

	//opens the compose mail window
	public class createMail implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			messageFrame.dispose();
			compose = new composeMail();
			compose.composeMails(email_address);
		}
	}

	//opens the outbox window
	public class SentWindow implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			messageFrame.dispose();
			msgBoxx.outbox.outboxWindow(email_address);
		}
	}

	//opens the drafts window
	public class openDrafts implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			messageFrame.dispose();
			msgBoxx.drafts.draftsWindow(email_address);
		}
	}

	//opens the compose mail window where it is set to use for replying
	public class replyMessage implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			messageFrame.dispose();
			compose = new composeMail();
			compose.replyMails(recipient, mailSubject, message);
		}
	}

	//opens the compose mail window where it is set to use for forwarding
	public class forwardMessage implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			messageFrame.dispose();
			compose = new composeMail();
			compose.forwardedMails(email_address, mailSubject, message);
		}
	}

	//deletes the email
	public class deleteMessage implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			messageFrame.dispose();
			LogIn.connect.deleteMail(mailID);
			LogIn.email.testerMsg(email_address);
		}
	}
}
