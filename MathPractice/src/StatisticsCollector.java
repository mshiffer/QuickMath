import java.io.Serializable;

public class StatisticsCollector implements Serializable {
	private static final long serialVersionUID = 1L;

	double timeLimit = 0;
	
	private AnsweredProblemCollection answeredProblems;
		
	public StatisticsCollector(eOperator op, int focusNum, double timeLimit)
	{
		this.answeredProblems = new AnsweredProblemCollection(op, focusNum, timeLimit);		
		this.timeLimit = timeLimit;
	}
	
	public void addStat(Problem problem, ProblemStat stat)
	{
		this.answeredProblems.addAnsweredProblem(problem, stat);
	}
	
	public double calculateAverageTime()
	{
		return this.answeredProblems.averageTime();
	}
	
	public double calculateGrade()
	{
		double numInTime = this.answeredProblems.numInTime();
		double numRight = this.answeredProblems.numRight();
		
		double numProbs = this.answeredProblems.numProblems() * 2;
		
		return (numInTime + numRight)/numProbs;
	}
	
	public void displayDesiredTime()
	{
		IOUtils.writeString("Time you're looking for is: " + timeLimit);		
	}
	
	public void displayAllProblems()
	{
		displayDesiredTime();
		IOUtils.writeString("");
		
		for (AnsweredProblem prob : this.answeredProblems.getAnsweredProblems())
		{
			String s;
			if (!prob.answeredCorrect())
			{
				s = prob.toString() + " " + prob.answer + " | " + "Your answer: " + prob.response + '\t';
			}
			else
			{
				s = prob.toString() + " " + prob.answer + " | " + "You got it right!" + '\t';
			}
			s += " | " + "Your time: " + IOUtils.fd(prob.responseTime);
			if (!prob.answeredInTime(this.timeLimit))
			{
				s += " (That was too slow.)";
			}
			IOUtils.writeString(s);		
		}
	}
	
	public void setTimeLimit(double limit)
	{
		timeLimit = limit;
	}
	
	public AnsweredProblemCollection getAnsweredProblemCollection()
	{
		return this.answeredProblems;
	}
	
	
}
