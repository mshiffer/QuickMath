
public enum eOperator {
	
	addition
	{
		public String toString()
		{
			return "+";
		}
	},
	subtraction
	{
		public String toString()
		{
			return "-";
		}
	},
	multiplication
	{
		public String toString()
		{
			return "x";
		}
	},
	division
	{
		public String toString()
		{
			return "/";
		}
	};
	
	public static eOperator fromString(String input)
	{
		if (input.equals("+"))
		{
			return eOperator.addition;
		}
		else if (input.equals("-"))
		{
			return eOperator.subtraction;				
		}
		else if (input.equals("x"))
		{
			return eOperator.multiplication;
		}
		else
		{
			return eOperator.division;
		}
	}

}
