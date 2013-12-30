import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class PersistanceHandler {

	public static void writeObjectToFile(String path, Object o)
	{
		ObjectOutputStream os=null;

		try {
			os = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(path)));
			os.writeObject(o);
		}
		catch (Exception e){
			System.out.println(e.getMessage());
			e.printStackTrace();;
		}
		finally
		{
			try {
				if (os != null)
				{
					os.close();
				}
			} catch (IOException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	public static GradeBook readGradeBookFromFile(String path)
	{
		GradeBook retVal = null;
		ObjectInputStream is = null;

		try {
			is = new ObjectInputStream(new BufferedInputStream(new FileInputStream(path)));
			retVal = (GradeBook)is.readObject();
		}
		catch (Exception e){
			System.out.println(e.getMessage());
			e.printStackTrace();;
		}
		finally
		{
			try {
				if (is != null)
				{
					is.close();
				}
			} catch (IOException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}	
		
		return retVal;
	}
	
}
