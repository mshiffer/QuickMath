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
	private double targetTime = Double.MAX_VALUE;
	
	private Date date = new Date(System.currentTimeMillis());
	
	private boolean cacheStale = true;
	
	public AnsweredProblemCollection(eOperator operator, int focusNum, double targetTime)
	{
		this.collectionDefinition = new ProblemCollectionDefinition(operator, focusNum);
		this.targetTime = targetTime;
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
	
	public int numInTime()
	{
		int retVal = 0;
		
		for (AnsweredProblem prob : answeredProblems)
		{
			if (prob.answerTime() <= this.targetTime)
			{
				retVal++;
			}
		}
		
		return retVal;
	}
	
	public int numInTime(double time)
	{
		int retVal = 0;
		
		for (AnsweredProblem prob : answeredProblems)
		{
			if (prob.answerTime() <= time)
			{
				retVal++;
			}
		}
		
		return retVal;
	}
	
	public double getGrade()
	{
		//TODO Fix so no points for wrong answers
		return (((double)numInTime() + numRight())/(2.0*numProblems())) * 100;
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
		return targetTime;
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
