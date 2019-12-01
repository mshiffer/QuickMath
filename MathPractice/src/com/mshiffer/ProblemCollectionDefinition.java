package com.mshiffer;
import java.io.Serializable;

//import org.apache.commons.lang3.builder.HashCodeBuilder;

public class ProblemCollectionDefinition implements Serializable, Comparable<ProblemCollectionDefinition>{
	private static final long serialVersionUID = 1L;

	//These are used for the purposes of equality
	public eOperator operator;
	public int focusNum;
	
	//These are NOT used for the purposes of equality
	public int startNum;
	public int endNum;
	public double targetTime;
	
	public ProblemCollectionDefinition(ProblemCollectionDefinition oldPCD)
	{
		this.operator = oldPCD.operator;
		this.focusNum = oldPCD.focusNum;
		this.startNum = oldPCD.startNum;
		this.endNum = oldPCD.endNum;
		this.targetTime = oldPCD.targetTime;
	}
	
	public ProblemCollectionDefinition(eOperator op, int focusNum, int startNum, int endNum, double targetTime)
	{
		this.operator = op;
		this.focusNum = focusNum;
		this.startNum = startNum;
		this.endNum = endNum;
		this.targetTime = targetTime;
	}
	
	public String toString()
	{
		String retVal = "";
		
		retVal += operator + " " + focusNum;
		
		//retVal += " " + startNum + " " + endNum + " " + targetTime;
		
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
		/*return new HashCodeBuilder(17, 37).
				append(operator).
				append(focusNum).
				toHashCode();*/
		return 1;
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
