import java.io.Serializable;


public class Problem implements Serializable{
	private static final long serialVersionUID = 1L;

	protected int leftNum;
	protected int rightNum;
	protected eOperator operator;
	protected int answer;
	
	public Problem (Problem pOld)
	{
		leftNum = pOld.leftNum;
		rightNum = pOld.rightNum;
		operator = pOld.operator;
		answer = pOld.answer;
	}
	
	public Problem(int leftNum, int rightNum, eOperator operator)
	{
		this.leftNum = leftNum;
		this.rightNum = rightNum;
		this.operator = operator;
		
		calcAndSetAnswer();
	}

	private void calcAndSetAnswer()
	{
		switch (operator)
		{
			case addition:
				answer = leftNum + rightNum;
				break;
			case subtraction:
				answer = leftNum - rightNum;
				break;
			case multiplication:
				answer = leftNum * rightNum;
				break;
			case division:
				answer = leftNum / rightNum;
				break;
				default:
		}			
	}
	
	public String toString()
	{
		String probString = "";
		probString += this.leftNum + " " + this.operator.toString() + " " + this.rightNum + " =";
		
		return probString;
	}
	
}
