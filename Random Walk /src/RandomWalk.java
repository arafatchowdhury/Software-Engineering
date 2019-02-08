import java.util.HashMap;
import java.util.concurrent.*;

public class RandomWalk extends Thread implements Callable <HashMap<Integer, Integer>>
{	
	public HashMap<Integer, Integer> call()
	{
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		try 
		{
			for(int i = 0; i < Data.M/Data.T; i++) 
			{
				int n = 0;
				int x = Data.k;
				int u = Data.u;
				int d = Data.d;
				
				while(x > 0)
				{
					n++;
	
					if(ThreadLocalRandom.current().nextInt(2) == 1)
					{
						x = x + u;
					}
					else 
					{
						x = x - d;
					}
			
				}

				if(map.containsKey(n))
				{
					map.put(n, map.get(n) + 1);
				}
				else
				{
					map.put(n, 1);
				}
			}
		}
		catch (Exception e) 
		{
			System.out.println ("Exception is caught"); 
		}
		
		return map;
	}
	
	
}