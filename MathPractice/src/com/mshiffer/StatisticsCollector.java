package com.mshiffer;
import java.io.Serializable;

public class StatisticsCollector implements Serializable {
	private static final long serialVersionUID = 1L;

	private double targetTime = 0;
	
	private AnsweredProblemCollection answeredProblems;
		
	public StatisticsCollector(ProblemCollectionDefinition pcd)
	{
		this.answeredProblems = new AnsweredProblemCollection(pcd);		
		this.targetTime = pcd.targetTime;
	}
	
	public void addStat(Problem problem, ProblemStat stat)
	{
		this.answeredProblems.addAnsweredProblem(problem, stat);
	}
	
	public double getAverageTime()
	{
		return this.answeredProblems.averageTime();
	}
	
	public double getGrade()
	{
		return this.answeredProblems.getGrade();
	}
	
	public void displayDesiredTime()
	{
		IOUtils.writeString("Time you're looking for is: " + targetTime);		
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
				s = prob.toString() + " | Correct answer: " + prob.answer + " | " + "Your answer: " + prob.response + "\t\t";
			}
			else
			{
				s = prob.toString() + " | Correct answer: " + prob.answer + " | " + "You got it right!" + '\t';
			}
			s += " | " + "Your time: " + IOUtils.fd(prob.responseTime);
			if (!prob.answeredInTime(this.targetTime))
			{
				s += " (That was too slow.)";
			}
			IOUtils.writeString(s);		
		}
	}
	
	public void setTimeLimit(double limit)
	{
		targetTime = limit;
	}
	
	public AnsweredProblemCollection getAnsweredProblemCollection()
	{
		return this.answeredProblems;
	}

}
