import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class Try 
{
	private static final int num_threads = Data.T;
	final static String fileName= "histogram.csv";
	static double startTime;
	static double totalTime;
	
	static ThreadManager manager = new ThreadManager();
	
	static List<Double> values = new ArrayList<Double>();
	static List<Integer> keys = new ArrayList<Integer>();
	static List<Future<HashMap<Integer, Integer>>> aList = new ArrayList<Future<HashMap<Integer, Integer>>>();
	static Map<Integer, Integer> myHash = new HashMap<Integer, Integer>();
	
    public static void main(String[] args)
    {
    	startTime = System.nanoTime();    	
    	ExecutorService executor = Executors.newFixedThreadPool(num_threads);
   	 
    	Callable<HashMap<Integer, Integer>> rng = new RandomWalk();
	   	for (int i = 0; i < num_threads; ++i) 
	   	{
	   		Future<HashMap<Integer, Integer>> submit = executor.submit(rng);
	   		aList.add(submit);
	   	}
	   	
	   	executor.shutdown();
	   	
	   	try 
	   	{
	   		executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
	   	}
	   	catch (InterruptedException e)
	   	{

	   	}
	   	
	   	totalTime = (System.nanoTime() - startTime)/1000000000;
	   	System.out.println(totalTime + " seconds");
	   	
	   	for(Future<HashMap<Integer, Integer>> fut: aList)
	   	{
		   	try 
		   	{
				fut.get().forEach((k, v) -> myHash.merge(k, v, Integer::sum));
			} 
		   	catch (Exception e) 
		   	{
				e.printStackTrace();
			}
	   	}

	   	Try.manager.deleteData();
	   	Try.manager.storeData();
    	
    	// this is to read the .csv file
    	
        String fname = "histogram.csv";
        File file= new File(fname);

        // this gives you a 2-dimensional array of strings
        List<List<String>> lines = new ArrayList<>();
        Scanner inputStream;

        try
        {
            inputStream = new Scanner(file);

            while(inputStream.hasNext())
            {
                String line= inputStream.next();
                String[] values = line.split(",");
                
                // this adds the currently parsed line to the 2-dimensional string array
                lines.add(Arrays.asList(values));
            }

            inputStream.close();
        }
        catch (FileNotFoundException e) 
        {
            e.printStackTrace();
        } 
        
        System.out.println("keys Size: " + keys.size());
        
        double mean = 0.0;
        double variance = 0.0;
        
        for(int i = 0; i <keys.size(); i++)
        {        	
        	mean += keys.get(i) * values.get(i);
        	variance += (keys.get(i) * keys.get(i)) * values.get(i);
        }
        variance = variance - (mean * mean);
        System.out.println("Mean: " + mean);
        System.out.println("Variance: " + variance);
    }
} 