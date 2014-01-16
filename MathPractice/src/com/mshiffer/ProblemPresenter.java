package com.mshiffer;
import java.util.Collections;
import java.util.List;


public class ProblemPresenter {
	List<Problem> problems;
	StatisticsCollector stats;
	
	public ProblemPresenter(List<Problem> probs, StatisticsCollector stats)
	{
		problems = probs;
		this.stats = stats;
	}
	
	public void PresentProblems()
	{
		int response;
		
		for (Problem problem : problems)
		{
			IOUtils.writeString(problem.toString());
			long startTime = System.nanoTime();
			response = getResponse();
			long responseTimeLong = (System.nanoTime() - startTime);
			double responseTime = ((double)responseTimeLong)/1000000000l;
			handleResponse(problem, response, responseTime);
		}
	}
	
	public void shuffleProblems()
	{
		Collections.shuffle(problems);
	}
	
	public int getResponse()
	{
		return IOUtils.getInteger();
	}
	
	public void handleResponse(Problem problem, int response, double responseTime)
	{
		//displayResponseTime(responseTime);
		
		stats.addStat(problem, new ProblemStat(responseTime, response));
	}
	
	public void displayResponseTime(double responseTime)
	{
		IOUtils.writeString("Response time was " + responseTime + " seconds");
	}
	
}
