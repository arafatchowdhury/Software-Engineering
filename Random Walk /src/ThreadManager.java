import java.io.*;
import java.util.*;

public class ThreadManager
{    
	
	int key;
	int value;
	double keyValue;
	double dividend;
	FileWriter fw;
	
	public ThreadManager()
	{
		key = 0;
		value = 0;
		dividend = Data.M;
	}		
	
	public void storeData() 
	{
		try
		{
		    fw = new FileWriter(Try.fileName,true); //the true will append the new data
		    String header = "Key, Value";
		    fw.write(header + "\n");
		    
		    Iterator it = Try.myHash.entrySet().iterator();
		    while (it.hasNext()) 
		    {
		    	String i ="";
		        Map.Entry pair = (Map.Entry)it.next();
		        keyValue = (double) ((Integer)pair.getValue());
		        		        
		        keyValue = keyValue / dividend;
		        
		        Try.values.add(keyValue);
		        Try.keys.add((Integer) pair.getKey());
	        
		        
		        //System.out.println(pair.getKey() + " " + pair.getValue());
		        
			    String s = Integer.toString((int) pair.getKey()) + ", " + Double.toString(keyValue);
			    
			    
			    fw.write(s + "\n");//appends the string to the file
		    }
		    
		    fw.close();
		}
		catch(IOException ioe)
		{
		    System.err.println("IOException: " + ioe.getMessage());
		}	
	}
	
	public void deleteData() 
	{
		File file = new File(Try.fileName);
	    if (file.exists()) 
	    {
	    	file.delete();     
	    }
	}
}