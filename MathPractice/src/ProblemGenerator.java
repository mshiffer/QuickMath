import java.util.LinkedList;
import java.util.List;


public class ProblemGenerator {

	public static List<Problem> GenerateProblems(eOperator operator, int lowNum, int highNum, int targetNum, boolean shuffle)
	{
		List<Problem> retVal = new LinkedList<Problem>();
		
		for (int i = lowNum; i <= highNum; i++)
		{
			if (!shuffle || Math.random() < .5)
			{
				retVal.add(new Problem(targetNum, i, operator));
			}
			else
			{
				retVal.add(new Problem(i, targetNum, operator));
			}
		}
		
		return retVal;
	}
	
	
	
}
