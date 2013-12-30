import java.io.Serializable;

import org.apache.commons.lang3.builder.HashCodeBuilder;

public class ProblemCollectionDefinition implements Serializable, Comparable<ProblemCollectionDefinition>{
	private static final long serialVersionUID = 1L;

	public eOperator operator;
	public int focusNum;
	
	public ProblemCollectionDefinition(eOperator op, int num)
	{
		this.operator = op;
		this.focusNum = num;
	}
	
	public String toString()
	{
		String retVal = "";
		
		retVal += operator + " " + focusNum;
		
		return retVal;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof ProblemCollectionDefinition))
		{
			return false;
		}
		
		ProblemCollectionDefinition pcd = (ProblemCollectionDefinition)obj;
		
		if (pcd.operator == this.operator && pcd.focusNum == this.focusNum)
		{
			return true;
		}
		
		return false;
	}
	
	@Override
	public int hashCode() 
	{
		return new HashCodeBuilder(17, 37).
				append(operator).
				append(focusNum).
				toHashCode();
	}

	@Override
	public int compareTo(ProblemCollectionDefinition pcd) {
		if (this.equals(pcd))
		{
			return 0;
		}
		
		int retVal = this.operator.ordinal() - pcd.operator.ordinal();
		
		if (retVal == 0)
		{
			retVal = this.focusNum - pcd.focusNum;
		}
		
		return retVal;
	}
}
