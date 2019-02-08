import java.sql.*;
import java.util.*;
import javax.management.Query;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.EventQueue;

public class MySQLAccess 
{
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;   //  changed to static _ arafat
	private ResultSet resultSet = null;
	static ArrayList data = new ArrayList();
    static JTable tableInbox = new JTable();
    static JTable tableDraft = new JTable();
    static JTable tableSent = new JTable();
    private String email_address;
	private static String user = "user";
	private static String password = "Shadowless12";
	private static String url = "jdbc:mysql://emailserver.cmbeixtswu4n.us-east-1.rds.amazonaws.com/email";
	
	public void connectDataBase() throws Exception
	{
		try
		{
			connect = DriverManager.getConnection(url, user, password);
			System.out.println("Connected");
		}
		catch(SQLException e)
		{
			System.out.println("Invalid");
			e.printStackTrace();
		}
	}
	
	//creates a new user in the database
	public int signUp(String email_address, String email_password, String first_name, String last_name)
	{
		try 
		{
			String query = "insert into users values (?, ?, ?, ?)";
			preparedStatement = connect.prepareStatement(query);
			
			/*
			 * variables here replaces the question marks in the query
			 * the numbers before the variables denote what question mark is the variable supposed to replace
			 * the numbers start at 1 compared to starting at 0
			 */
			preparedStatement.setString(1, email_address);
			preparedStatement.setString(2, email_password);
			preparedStatement.setString(3, first_name);
			preparedStatement.setString(4, last_name);
			preparedStatement.execute();	//execute the query above
			
			/*
			 * Created an object array which only contains the value of the button to be used in the popup message
			 * the popup message displays upon successfully registering a user
			 */
			Object[] options = {"OK"};
			int temp = JOptionPane.showOptionDialog(null, "You are now registered", "Registration", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
			return temp;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return 0;
	}
	
	//checks if the user is not already registered
	public boolean usercheck(String email_address)
	{
		try
		{
			String query = "select email_address from users where email_address = ?";
			preparedStatement = connect.prepareStatement(query);
			preparedStatement.setString(1, email_address);
			resultSet = preparedStatement.executeQuery();
			
			/*
			 * if there's a returned value for the selected column of the query would be able to be used and passed to other classes
			 * Here we display a warning message that the current username/email is already registered or taken
			 */
			if(resultSet.next())
			{
				if(resultSet.getString("email_address").equals(email_address))
				{
					JOptionPane.showMessageDialog(null, "User already exists", "Error", JOptionPane.WARNING_MESSAGE);
					return true;
				}
				else
				{
					return false;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return false;
	}
	
	//checks if the user credentials are correct
	public void logIn(String email_address, String email_password)
	{ 
		try
		{
			String query = "select count(*) as result from users where email_address = ? and email_password = ?";
			preparedStatement = connect.prepareStatement(query);
			preparedStatement.setString(1, email_address);
			preparedStatement.setString(2, email_password);
			resultSet = preparedStatement.executeQuery();
			
			/*
			 * the returned value of the query should be a boolean value since we are looking if the email address and password combination is correct
			 * if the credentials are correct their inbox should show up
			 * if not a warning message will appear informing the user that the email and/or password is incorrect
			 */
			if(resultSet.next())
			{
				if(resultSet.getBoolean("result") == true)
				{
					this.email_address = email_address;
					LogIn.frame.dispose();
					LogIn.email = new msgBoxx();
					LogIn.email.testerMsg(email_address);
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Incorrect email or password", "Error", JOptionPane.WARNING_MESSAGE);
				}
				// testing done
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	//populates the inbox of the user using JTable
	public void inboxTable()
	{
		ArrayList columnNames = new ArrayList();
		ArrayList data = new ArrayList();
		DefaultTableModel model = new DefaultTableModel(new String[]{"mailID", "From", "Subject", "Time Sent"}, 0 );
		
		try
		{
			String query = "select mailID, sender, mailSubject, date_format(timeSent, \"%b %d %a %r\") as timeSent from mails where mailType = 's' and recipient = ? order by timeSent DESC";
			preparedStatement = connect.prepareStatement(query);
			preparedStatement.setString(1, email_address);
			resultSet = preparedStatement.executeQuery();
			
			/*
			 * while the resultSet has another row returned, the columns returned from the database are stored and assigned added to a row in the table model
			 * the data from each column from the database can be stored by getting the value at the column name in the database
			 * when added to the row it will be in order of how the table model column names are shown.
			 */
			while(resultSet.next())
			{
				String col1 = resultSet.getString("mailID");
				String col2 = resultSet.getString("sender");
			    String col3 = resultSet.getString("mailSubject");
			    String col4 = resultSet.getString("timeSent");
				model.addRow(new Object[] {col1, col2, col3, col4});
			}

			/*
			 * after the table model has been populated it is then placed in the inbox table
			 * once the inbox table is populated we collapse the mailID column so that the data is not displayed but still be used to obtain data from the database
			 * 
			 */
			tableInbox.setModel(model);
			tableInbox.getColumnModel().getColumn(0).setMinWidth(0);
			tableInbox.getColumnModel().getColumn(0).setMaxWidth(0);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	//creates the drafts table
	public void draftsTable()
	{
		ArrayList columnNames = new ArrayList();
	    ArrayList data = new ArrayList();
	    DefaultTableModel model = new DefaultTableModel(new String[]{"mailID", "To", "Subject", "timeSent"}, 0);
	    
		try
		{
			String query = "select mailID, recipient, mailSubject, date_format(timeSent, \"%b %d %a %r\") as timeSent from mails where mailType = 'd' and sender = ? order by timeSent DESC";
			preparedStatement = connect.prepareStatement(query);
			preparedStatement.setString(1, email_address);
			resultSet = preparedStatement.executeQuery();
			
			/*
			 * while the resultSet has another row returned, the columns returned from the database are stored and assigned added to a row in the table model
			 * the data from each column from the database can be stored by getting the value at the column name in the database
			 * when added to the row it will be in order of how the table model column names are shown.
			 */
			while(resultSet.next())
			{
				String col1 = resultSet.getString("mailID");
				String col2 = resultSet.getString("recipient");
			    String col3 = resultSet.getString("mailSubject");
			    String col4 = resultSet.getString("timeSent");
			    model.addRow(new Object[]{col1, col2, col3, col4});
			}
			
			/*
			 * after the table model has been populated it is then placed in the draft table
			 * once the draft table is populated we collapse the mailID column so that the data is not displayed but still be used to obtain data from the database
			 * 
			 */
			tableDraft.setModel(model);
			tableDraft.getColumnModel().getColumn(0).setMinWidth(0);
			tableDraft.getColumnModel().getColumn(0).setMaxWidth(0);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	public void sentTable()
	{
		ArrayList columnNames = new ArrayList();
	    ArrayList data = new ArrayList();
	    DefaultTableModel model = new DefaultTableModel(new String[]{"mailID", "To", "Subject", "timeSent"}, 0);
	    
		try
		{
			String query = "select mailID, recipient, mailSubject, date_format(timeSent, \"%b %d %a %r\") as timeSent from mails where mailType = 's' and sender = ? order by timeSent DESC";
			preparedStatement = connect.prepareStatement(query);
			preparedStatement.setString(1, email_address);
			resultSet = preparedStatement.executeQuery();
			
			/*
			 * while the resultSet has another row returned, the columns returned from the database are stored and assigned added to a row in the table model
			 * the data from each column from the database can be stored by getting the value at the column name in the database
			 * when added to the row it will be in order of how the table model column names are shown.
			 */
			while(resultSet.next())
			{
				String col1 = resultSet.getString("mailID");
				String col2 = resultSet.getString("recipient");
			    String col3 = resultSet.getString("mailSubject");
			    String col4 = resultSet.getString("timeSent");
			    model.addRow(new Object[]{col1, col2, col3, col4});
			}
			
			/*
			 * after the table model has been populated it is then placed in the outbox table
			 * once the outbox table is populated we collapse the mailID column so that the data is not displayed but still be used to obtain data from the database
			 * 
			 */
			tableSent.setModel(model);
			tableSent.getColumnModel().getColumn(0).setMinWidth(0);
			tableSent.getColumnModel().getColumn(0).setMaxWidth(0);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	//retrieves the email data found in the inbox and displays it's content on a new email window
	public void retrieveEmail(String mailID)
	{
		try
		{
			String query = "select sender, mailSubject, message from mails where recipient = ? and mailID = ? and  mailType = 's'";
			preparedStatement = connect.prepareStatement(query);
			preparedStatement.setString(1, email_address);
			preparedStatement.setString(2, mailID);
			resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next())
			{
				MessageWindow emailView = new MessageWindow(resultSet.getString("mailSubject"), resultSet.getString("sender"), resultSet.getString("message"), mailID);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	//retrieves the email data found in the drafts and displays it's content on a new email window
	public void retrieveDraft(String mailID)
	{
		try
		{
			String query = "select recipient, mailSubject, message from mails where sender = ? and mailID = ? and  mailType = 'd'";
			preparedStatement = connect.prepareStatement(query);
			preparedStatement.setString(1, email_address);
			preparedStatement.setString(2, mailID);
			resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next())
			{
				DraftMessage draftView = new DraftMessage();
				draftView.draftMessageWindow(resultSet.getString("mailSubject"), resultSet.getString("recipient"), resultSet.getString("message"), mailID);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	//retrieves the email data found in the sent and displays it's content on a new email window
	public void retrieveSent(String mailID)
	{
		try
		{
			String query = "select recipient, mailSubject, message from mails where sender = ? and mailID = ? and  mailType = 's'";
			preparedStatement = connect.prepareStatement(query);
			preparedStatement.setString(1, email_address);
			preparedStatement.setString(2, mailID);
			resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next())
			{
				MessageWindow emailView = new MessageWindow(resultSet.getString("mailSubject"), resultSet.getString("recipient"), resultSet.getString("message"), mailID);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/*
	 * passes the values of the recipient, sender, subject, content and mailtype to the query to be stored in the database
	 * executeUpdate is used instead of executeQuery because there is no returned values, which is the main purpose of the executeQuery
	 */
	public void sendsMail(String recipient, String email_address, String subject, String contentMail, String mailType)
	{
		try
		{
			String query =  "insert into mails (sender, recipient, mailSubject, message, mailType)" + 
							"values(?, ?, ?, ?, ?)";
			preparedStatement = connect.prepareStatement(query);
			preparedStatement.setString(1, email_address);
			preparedStatement.setString(2, recipient);
			preparedStatement.setString(3, subject);
			preparedStatement.setString(4, contentMail);
			preparedStatement.setString(5, mailType);
			preparedStatement.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/*
	 * updates the values of the draft mail that is already stored in the database
	 * the most important update to this is the mailType and recipient, so the email will go to the right user and will appear in the user's inbox
	 */
	public void sendDraft(String recipient, String subject, String contentMail, String mailType, int mailID)
	{
		try
		{
			String query = "update mails set mailType = 's', recipient = ?, mailSubject = ?, message = ?, timeSent = current_timestamp where mailID = ?";
			preparedStatement = connect.prepareStatement(query);
			preparedStatement.setString(1, recipient);
			preparedStatement.setString(2, subject);
			preparedStatement.setString(3, contentMail);
			preparedStatement.setInt(4, mailID);
			preparedStatement.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/*
	 * updates the values of the draft like the content, subject or even the recipient.
	 * the draft is not sent in this method just updated and still saved as a draft
	 * method is called after a draft is opened and the user may or may not made changes, and wants to exit the window.
	 */
	public void updateDraft(String recipient, String subject, String contentMail, String mailType, int mailID)
	{
		try
		{
			String query = "update mails set recipient = ?, mailSubject = ?, message = ?, timeSent = current_timestamp where mailID = ?";
			preparedStatement = connect.prepareStatement(query);
			preparedStatement.setString(1, recipient);
			preparedStatement.setString(2, subject);
			preparedStatement.setString(3, contentMail);
			preparedStatement.setInt(4, mailID);
			preparedStatement.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/*
	 * saves the data of the email and saved as a draft
	 * emails that are saved as drafts will have the mailType == d
	 * method is called when the user is composing a message and doesn't send it
	 */
	public void saveDraft(String recipient, String subject, String contentMail, String mailType)
	{
		try
		{
			String query =  "insert into mails (sender, recipient, mailSubject, message, mailType)" + 
							"values(?, (select email_address from users where email_address = ?), ?, ?, ?)";
			preparedStatement = connect.prepareStatement(query);
			preparedStatement.setString(1, email_address);
			preparedStatement.setString(2, recipient);
			preparedStatement.setString(3, subject);
			preparedStatement.setString(4, contentMail);
			preparedStatement.setString(5, mailType);
			preparedStatement.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	//deletes the currently opened email.
	public void deleteMail(int mailID)
	{
		try 
		{
			String query = "delete from mails where mailID =?";
			preparedStatement = connect.prepareStatement(query);
			preparedStatement.setInt(1, mailID);
			preparedStatement.executeUpdate();
			
			JOptionPane.showMessageDialog(null, "Message has been deleted", "Message Deleted", JOptionPane.INFORMATION_MESSAGE);
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
	}
}