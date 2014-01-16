package com.mshiffer;


public class AnsweredProblem extends Problem {

	private static final long serialVersionUID = 1L;

	public double responseTime = Double.MAX_VALUE;
	
	public int response;
	
	public AnsweredProblem(Problem p, ProblemStat stat) {
		super(p);
		this.responseTime = stat.time;
		this.response = stat.answer;
	}
	
	public boolean answeredCorrect()
	{
		return response == answer;
	}
	
	public boolean answeredInTime(double targetTime)
	{
		return responseTime <= targetTime;
	}
	
	public double answerTime()
	{
		return responseTime;
	}
	
	//TODO Make this work for what we want to output
	public String toString()
	{
		return super.toString() + " Answered in " + responseTime;
	}

}
