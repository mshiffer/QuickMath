package com.mshiffer;
import java.io.Serializable;


public class ProblemStat implements Serializable {
	private static final long serialVersionUID = 1L;

	double time;
	int answer;
	
	public ProblemStat(double time, int answer)
	{
		this.time = time;
		this.answer = answer;
	}
}
