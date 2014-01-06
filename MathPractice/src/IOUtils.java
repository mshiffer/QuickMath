import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;


public class IOUtils {
	
	public static String getString()
	{
		String retVal = ""; 
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try{
			retVal = br.readLine();
		}
		catch(Exception e)
		{
			
		}
		
		return retVal;
	}
	
	public static void blankLn()
	{
		System.out.println("");
	}
	
	public static String getParticularString(String[] valArray)
	{
		List<String> vals = Arrays.asList(valArray);
		String retVal = ""; 

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		while (!vals.contains(retVal))
		{

			try{
				retVal = br.readLine();
			}
			catch(Exception e)
			{

			}
			finally
			{
				/*try {
					br.close();
				}
				catch (Exception e)
				{

				}*/
			}

			if (!vals.contains(retVal))
			{
				writeString("I didn't understand.  Please type one of: ");
				for (String val : vals)
				{
					writeString(val);
				}

			}
		}
		
		return retVal;
	}
	
	public static void writeString(String string)
	{
		System.out.println(string);
	}
	
	public static int getInteger()
	{
		int retVal=0;

		boolean done = false;
		
		while (!done)
		{
			try{

				String response = (new BufferedReader(new InputStreamReader(System.in))).readLine();
				retVal = Integer.parseInt(response);
				done = true;
			}
			catch(Exception e)
			{
				writeString("That's not a valid number dingbat!");
			}
		}
		
		return retVal;
	}
	
	public static String fd(double d)
	{
		return String.format("%.1f", d);
	}
	
	public static void clearScreen()
	{
		try {
			Runtime.getRuntime().exec("cls");
		}
		catch(Exception e)
		{
			
		}
	}
	
}
