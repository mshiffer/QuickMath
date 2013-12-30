import java.io.Serializable;
import java.util.Hashtable;



public class GradeBook implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Hashtable<String, Statistics> studentStats;
	
	public GradeBook()
	{
		studentStats = new Hashtable<String, Statistics>();
	}
	
	public void recordStatsForStudent(String studentName, StatisticsCollector statCollector)
	{
		Statistics existingStats = getStatsForStudent(studentName);
		existingStats.addStats(statCollector);
	}
	
	public Statistics getStatsForStudent(String studentName)
	{
		if (!studentStats.containsKey(studentName))
		{
			studentStats.put(studentName, new Statistics());
		}
		return studentStats.get(studentName);
	}
	
	public boolean containsStudent(String studentName)
	{
		return studentStats.containsKey(studentName);
	}
	
	public void setTimeForStudent(String studentName, double time)
	{
		studentStats.get(studentName).setTargetTime(time);
	}
	
	public double getTimeForStudent(String studentName)
	{
		return studentStats.get(studentName).getTargetTime();
	}
		
}
