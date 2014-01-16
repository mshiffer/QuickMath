package com.mshiffer;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;

public class Statistics implements Serializable {
	private static final long serialVersionUID = 1L;

	private double targetTime;
	
	private HashMap<ProblemCollectionDefinition, LinkedList<AnsweredProblemCollection>> problemSets;
	
	public Statistics()
	{
		problemSets = new HashMap<ProblemCollectionDefinition, LinkedList<AnsweredProblemCollection>>();
	}
	
	public void addStats(StatisticsCollector statCollector)
	{
		ProblemCollectionDefinition probDef = statCollector.getAnsweredProblemCollection().collectionDefinition();
		LinkedList<AnsweredProblemCollection> list;
		
		if (!problemSets.containsKey(probDef))
		{
			list = new LinkedList<AnsweredProblemCollection>();
			problemSets.put(probDef, list);
		}
		else
		{
			list = problemSets.get(probDef);
		}
		
		list.addLast(statCollector.getAnsweredProblemCollection());
	}
	
	public void setTargetTime(double time)
	{
		targetTime = time;
	}
	
	public double getTargetTime()
	{
		return targetTime;
	}
	
	public double getLastScore(ProblemCollectionDefinition probDef)
	{
		LinkedList<AnsweredProblemCollection> list = problemSets.get(probDef);
		
		if (list.size() > 0)
		{
			return list.getLast().getGrade();
		}
		
		return 100;
	}
	
	public AnsweredProblemCollection getLastProblemSet(ProblemCollectionDefinition pcd)
	{
		LinkedList<AnsweredProblemCollection> list = problemSets.get(pcd);
		if (list == null)
		{
			return null;
		}
		
		return list.getLast();
	}
	
	public HashMap<ProblemCollectionDefinition, LinkedList<AnsweredProblemCollection>> getProblemSets()
	{
		return problemSets;
	}
	
	public String toString()
	{
		String retVal = "";
		
		for (ProblemCollectionDefinition def : this.problemSets.keySet())
		{
			retVal += def.toString() + "\n";
		}
		
		return retVal;
	}
}
