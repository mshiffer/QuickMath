import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;


public class StatisticsGenerator {
	public static String displayHighLevelStats(Statistics stats)
	{
		String s = "";
		
		HashMap<ProblemCollectionDefinition, LinkedList<AnsweredProblemCollection>> probSets = stats.getProblemSets();
		
		HashMap<ProblemCollectionDefinition, Double> topScores = new HashMap<ProblemCollectionDefinition, Double>();
		
		Set<ProblemCollectionDefinition> keySet = probSets.keySet();
		
		ArrayList<ProblemCollectionDefinition> keyList = new ArrayList<ProblemCollectionDefinition>(keySet);
		Collections.sort(keyList);
		
		for (ProblemCollectionDefinition def : keyList)
		{
			double bestScore = 0;
			for (AnsweredProblemCollection probSet : probSets.get(def))
			{
				if (bestScore < probSet.getGrade())
				{
					bestScore = probSet.getGrade();
				}
			}
			topScores.put(def, bestScore);
			s += def + " " + bestScore + "\n";	
		}
		
		return s;
	}
}
