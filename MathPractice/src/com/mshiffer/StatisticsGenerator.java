package com.mshiffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class StatisticsGenerator {
	/**
	 * Returns a hashmap of problem type to best score that used 9 as the high number
	 * 0 is the best score if that hasn't been done
	 * @param stats
	 */
	public static HashMap<ProblemCollectionDefinition, ArrayList<Double>> calcBestScoresFull(Statistics stats)
	{		
		HashMap<ProblemCollectionDefinition, ArrayList<Double>> topScores = new HashMap<ProblemCollectionDefinition, ArrayList<Double>>();

		Set<ProblemCollectionDefinition> keySet = new HashSet<ProblemCollectionDefinition>();
		
		for (eOperator op : new eOperator[]{eOperator.addition, eOperator.subtraction})
		{
			for (int i=0; i<=9; i++)
			{
				keySet.add(new ProblemCollectionDefinition(op, i, 0, 0, 0));
			}
		}
		
		ArrayList<ProblemCollectionDefinition> keyList = new ArrayList<ProblemCollectionDefinition>(keySet);
		Collections.sort(keyList);		
		
		for (ProblemCollectionDefinition def : keyList)
		{
			ProblemCollectionDefinition pcd = def;
			double bestScore = 0;
			double bestAvgTime = 100;

			List<AnsweredProblemCollection> probs = stats.getProblemSets().get(def);
			if (probs != null)
			{
				for (AnsweredProblemCollection probSet : probs)
				{
					if (probSet.collectionDefinition().endNum == 9 && bestScore <= probSet.getGrade() && bestAvgTime > probSet.averageTime())
					{
						bestScore = probSet.getGrade();
						bestAvgTime = probSet.averageTime();
						pcd = probSet.collectionDefinition();
					}
				}
			}

			ArrayList<Double> bestStuff = new ArrayList<Double>();
			bestStuff.add(0, bestScore);
			bestStuff.add(1, bestAvgTime);
			topScores.put(pcd, bestStuff);
		}
		
		return topScores;
	}
	
	/**
	 * Returns a hashmap of the the high number to best score for that particular pcd
	 * @param stats
	 */
	public static HashMap<Integer, Double> calcBestScoresForProb(Statistics stats, ProblemCollectionDefinition pcd)
	{
		HashMap<Integer, Double> topScores = new HashMap<Integer, Double>();
		
		for (int i=0; i<=9; i++)
		{
			double bestScore = 0;
			
			for (AnsweredProblemCollection probSet : stats.getProblemSets().get(pcd))
			{
				bestScore = 0;

				if (pcd.endNum == i && bestScore < probSet.getGrade())
				{
					bestScore = probSet.getGrade();
				}
			}
			
			topScores.put(i, bestScore);
		}
		
		return topScores;
	}
	
	public static String displayHighLevelStats(Statistics stats)
	{
		String s = "Your best scores:\n";
		
		HashMap<ProblemCollectionDefinition, ArrayList<Double>> topScores = calcBestScoresFull(stats);

		Set<ProblemCollectionDefinition> keySet = topScores.keySet();
		
		ArrayList<ProblemCollectionDefinition> keyList = new ArrayList<ProblemCollectionDefinition>(keySet);
		Collections.sort(keyList);
		
		for (ProblemCollectionDefinition def : keyList)
		{
			Double bestScore = topScores.get(def).get(0);
			Double bestTime = topScores.get(def).get(1);

			if (bestScore != 0.0)
			{
				s += def + "  |  " + Math.round(bestScore) + "%  |  " + String.format("%.2f", bestTime) + "s  |  ";

				if (bestScore == 100.0)
				{
					s += " -- Done!!";
				}
				s+="\n";
			}
		}
		
		return s;
	}
}
