import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;


public class AnsweredProblemCollection implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private ProblemCollectionDefinition collectionDefinition;
	
	private LinkedList<AnsweredProblem> answeredProblems = new LinkedList<AnsweredProblem>();
	
	private int numRight = -1;
	private double averageTime = -1;
	
	private Date date = new Date(System.currentTimeMillis());
	
	private boolean cacheStale = true;
	
	public AnsweredProblemCollection(ProblemCollectionDefinition pcd)
	{
		this.collectionDefinition = pcd;
	}
	
	public ProblemCollectionDefinition collectionDefinition()
	{
		return this.collectionDefinition;
	}

	public void addAnsweredProblem(AnsweredProblem prob)
	{
		this.answeredProblems.add(prob);
		cacheStale = true;
	}
	
	public void addAnsweredProblem(Problem prob, ProblemStat stat)
	{
		this.answeredProblems.add(new AnsweredProblem(prob, stat));
	}
	
	public int numRight()
	{
		if (cacheStale)
		{
			recalcStats();
		}
			
		return numRight;
	}
	
	public int numInTime(boolean creditForLongNumbers)
	{
		/*int retVal = 0;
		
		for (AnsweredProblem prob : answeredProblems)
		{
			if (prob.answerTime() <= collectionDefinition.targetTime && prob.answeredCorrect())
			{
				retVal++;
			}
			else if (creditForLongNumbers && prob.answer > 9 && prob.answerTime() <= (collectionDefinition.targetTime + 1) && prob.answeredCorrect())
			{
				retVal++;
			}
		}
		
		return retVal;*/
		return numInTime(collectionDefinition.targetTime, creditForLongNumbers);
	}
	
	/**
	 * This returns the number that were answered in time AND correct...otherwise, what's the point?
	 * @param time
	 * @return
	 */
	public int numInTime(double time, boolean creditForLongNumbers)
	{
		int retVal = 0;
		
		for (AnsweredProblem prob : answeredProblems)
		{
			if (prob.answerTime() <= time && prob.answeredCorrect())
			{
				retVal++;
			}
			else if (creditForLongNumbers && prob.answer > 9 && time <= (collectionDefinition.targetTime + 1) && prob.answeredCorrect())
			{
				retVal++;
			}
		}
		
		return retVal;
	}
	
	public double getGrade()
	{
		return (((double)numInTime(true) + numRight())/(2.0*numProblems())) * 100;
	}
	
	public int numProblems()
	{
		return this.answeredProblems.size();
	}
	
	public double averageTime()
	{
		if (cacheStale)
		{
			recalcStats();
		}
		return averageTime;
	}
	
	public double targetTime()
	{
		return collectionDefinition.targetTime;
	}
	
	private void recalcStats()
	{
		numRight = 0;
		averageTime = 0;
		
		for (AnsweredProblem prob : answeredProblems)
		{
			if (prob.answeredCorrect())
			{
				numRight++;
			}
			averageTime += prob.answerTime();
		}
		
		averageTime = averageTime/answeredProblems.size();
	}
	
	public Date dateRecorded()
	{
		return date;
	}
	
	public List<AnsweredProblem> getAnsweredProblems()
	{
		return this.answeredProblems;
	}
		
}
