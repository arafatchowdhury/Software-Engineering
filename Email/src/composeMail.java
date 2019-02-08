import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.EventQueue;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class composeMail 
{
	private JFrame frame;
	private JTextField recipient;
	private JTextField mailSubject;
	composeMail compose;
	private JTextArea contentMail;
	private static String email_address;
	static String mrecipient;
	static String subjectMail;
	static String message;
	static String status = "compose";
	private static final String email_pattern = "[_A-Za-z0-9- \\+]+(\\.[_A-Za-z0-9-]+)*@" + "[_A-Za-z0-9- \\+]+(\\.[_A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
	private boolean isSent = false;

	/**
	 * Launch the application.
	 */
	//window is used to compose emails
	public static void composeMails(String email) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					email_address = email;
					composeMail window = new composeMail();
					window.frame.setVisible(true);
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}
	
	/*
	 * window is used to forward an email
	 * the subject and message is stored so that it will be displayed on the appropriate textfields
	 */
	public static void forwardedMails(String email, String mSubject, String mMessage)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					email_address = email;
					subjectMail = mSubject;
					message = mMessage;
					status = "forward";
					
					composeMail window = new composeMail();
					window.frame.setVisible(true);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}
	
	/*
	 * window is used to reply an email
	 * the recipient subject and message is stored so that it will be displayed on the appropriate textfields
	 */
	public static void replyMails(String email, String mSubject, String mMessage)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					mrecipient = email;
					subjectMail = mSubject;
					message = mMessage;
					status = "reply";
					
					composeMail window = new composeMail();
					window.frame.setVisible(true);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public composeMail() 
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() 
	{
		//creates the compose mail window
		frame = new JFrame();
		frame.setBounds(100, 100, 666, 379);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.addWindowListener(new java.awt.event.WindowAdapter()
		{
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent)
			{
				if(JOptionPane.showOptionDialog(frame, "Do you want to save this draft?", "Draft", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null) == JOptionPane.YES_OPTION)
				{
					LogIn.connect.saveDraft(recipient.getText(), mailSubject.getText(), contentMail.getText(), "d");
				}
				LogIn.email.testerMsg(email_address);
			}
		});
		frame.getContentPane().setLayout(null);
		
		//panel of buttons
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
				if(isSent == true)
				{
					frame.dispose();
					LogIn.email.testerMsg(email_address);
				}
				else
				{
					JOptionPane.showOptionDialog(frame, "Do you want to save this draft?", "Draft", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
					frame.dispose();
					LogIn.email.testerMsg(email_address);
				}
			}
		});
		
		JButton btnDraft = new JButton("Drafts");
		btnDraft.addActionListener(new DraftWindow());
		
		JButton btnSent = new JButton("Sent");
		btnSent.addActionListener(new SentWindow());
		
		JButton LogOut = new JButton("Logout");
		LogOut.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(isSent == true)
				{
					//clears the textfield in the login page so that a new person can login and not obtain the information of the previous user
					LogIn.clearTextField();
					frame.dispose();
					LogIn.frame.setVisible(true);
				}
				else
				{
					//checks if the composed message wants to be saved as a draft before the user logs out
					int temp = JOptionPane.showOptionDialog(frame, "Do you want to save this draft?", "Draft", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
					if(temp == JOptionPane.YES_NO_OPTION)
					{
						LogIn.connect.saveDraft(recipient.getText(), mailSubject.getText(), contentMail.getText(), "d");			
					}
					LogIn.clearTextField();
					frame.dispose();
					LogIn.frame.setVisible(true);
				}
			}
		});
		
		GroupLayout gl_buttonPanel = new GroupLayout(buttonPanel);
		gl_buttonPanel.setHorizontalGroup(
			gl_buttonPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_buttonPanel.createSequentialGroup()
					.addGroup(gl_buttonPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_buttonPanel.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_buttonPanel.createParallelGroup(Alignment.TRAILING)
								.addComponent(btnComposeMail, GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
								.addComponent(LogOut, GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)))
						.addGroup(gl_buttonPanel.createSequentialGroup()
							.addGap(34)
							.addGroup(gl_buttonPanel.createParallelGroup(Alignment.LEADING, false)
								.addComponent(btnDraft, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnSent, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnInbox, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE))))
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
		
		//panel for textfields needed to create an email
		JPanel messagePanel = new JPanel();
		messagePanel.setBounds(168, 11, 472, 318);
		frame.getContentPane().add(messagePanel);
		
		/*
		 * checks if the window is being used to compose an email or reply to an email
		 * if the window is not being used to compose it will fill the recipient textfield with the recipient of this email
		 */
		JLabel lblTo = new JLabel("To:");
		if(status.equals("reply"))
		{
			recipient = new JTextField(mrecipient);
			recipient.setColumns(10);
		}
		else
		{
			recipient = new JTextField();
			recipient.setColumns(10);
		}
		
		/*
		 * checks what the window is being used for
		 * if its compose the subject and content is empty
		 * if not its going to contain a prefix of FWD for forward and RE for reply in subject
		 * for the content it will contain a new space to type a message where the previous message is below the space of the new message
		 */
		JLabel lblSubject = new JLabel("Subject:");
		switch(status)
		{
			case "compose":
				mailSubject = new JTextField();
				mailSubject.setColumns(10);
				
				contentMail = new JTextArea();
				break;
				
			case "forward":
				mailSubject = new JTextField("FWD: " + subjectMail);
				mailSubject.setColumns(10);
				
				contentMail = new JTextArea("\n\n\n_______________________________________________________________" + "\nOriginal Message: \n" + message);
				break;
				
			case "reply":
				mailSubject = new JTextField("RE: " + subjectMail);
				mailSubject.setColumns(10);
				
				contentMail = new JTextArea("\n\n\n_______________________________________________________________" + "\nOriginal Message: \n" + message);
				break;
		}
		
		JButton btnSend = new JButton("Send");
		btnSend.addActionListener(new sendMail());
		
		GroupLayout gl_messagePanel = new GroupLayout(messagePanel);
		gl_messagePanel.setHorizontalGroup(
			gl_messagePanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_messagePanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_messagePanel.createParallelGroup(Alignment.LEADING)
						.addComponent(contentMail, GroupLayout.DEFAULT_SIZE, 452, Short.MAX_VALUE)
						.addComponent(btnSend, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
						.addGroup(Alignment.TRAILING, gl_messagePanel.createSequentialGroup()
							.addGroup(gl_messagePanel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblSubject)
								.addComponent(lblTo))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_messagePanel.createParallelGroup(Alignment.LEADING)
								.addComponent(recipient, GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
								.addComponent(mailSubject, GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE))))
					.addContainerGap())
		);
		gl_messagePanel.setVerticalGroup(
			gl_messagePanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_messagePanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_messagePanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTo)
						.addComponent(recipient, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_messagePanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblSubject)
						.addComponent(mailSubject, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(contentMail, GroupLayout.PREFERRED_SIZE, 190, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnSend, GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
					.addContainerGap())
		);
		messagePanel.setLayout(gl_messagePanel);
	}
	
	//opens the compose mail window and checks if the email should be saved as a draft if it is not sent
	public class createMail implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(isSent == true)
			{
				frame.dispose();
				compose = new composeMail();
				compose.composeMails(email_address);
			}
			else
			{
				int temp = JOptionPane.showOptionDialog(frame, "Do you want to save this draft?", "Draft", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
				if(temp == JOptionPane.YES_NO_OPTION)
				{
					LogIn.connect.saveDraft(recipient.getText(), mailSubject.getText(), contentMail.getText(), "d");
				}
				frame.dispose();
				compose = new composeMail();
				compose.composeMails(email_address);
			}
		}
	}

	//opens the drafts window and checks if the email should be saved as a draft if it is not sent
	public class DraftWindow implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(isSent == true)
			{
				frame.dispose();
				msgBoxx.drafts.draftsWindow(email_address);
				
			}
			else
			{
				int temp = JOptionPane.showOptionDialog(frame, "Do you want to save this draft?", "Draft", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
				if(temp == JOptionPane.YES_OPTION)
				{
					LogIn.connect.saveDraft(recipient.getText(), mailSubject.getText(), contentMail.getText(), "d");	
				}	
				
				frame.dispose();
				msgBoxx.drafts.draftsWindow(email_address);
			}
		}
	}

	//opens the outbox window and checks if the email should be saved as a draft if it is not sent
	public class SentWindow implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(isSent == true)
			{
				frame.dispose();
				msgBoxx.outbox.outboxWindow(email_address);
			}
			else
			{
				int temp = JOptionPane.showOptionDialog(frame, "Do you want to save this draft?", "Draft", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
				if(temp == JOptionPane.YES_NO_OPTION)
				{
					LogIn.connect.saveDraft(recipient.getText(), mailSubject.getText(), contentMail.getText(), "d");
				}
				frame.dispose();
				msgBoxx.outbox.outboxWindow(email_address);
			}
		}
	}

	/*
	 * checks if the recipient is not empty and has the form of an email before sending
	 * if not a warning message will appear notifying the user an error
	 */
	public class sendMail implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(!recipient.getText().equals("") && recipient.getText().matches(email_pattern))
			{
				LogIn.connect.sendsMail(recipient.getText(), email_address, mailSubject.getText(), contentMail.getText(), "s");
				isSent = true;
				frame.dispose();
				LogIn.email.testerMsg(email_address);
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Need a recipient or have a valid email before sending", "Error", JOptionPane.WARNING_MESSAGE);
			}
		}
	}
}
