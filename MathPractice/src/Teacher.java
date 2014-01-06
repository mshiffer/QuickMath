
import java.io.File;
import java.util.List;

public class Teacher {

	private String studentName = "";
	private GradeBook gradeBook;
	
	private eOperator operator;
	private int targetNumber;
	//private int lowerNumber;
	//private int upperNumber;
	
	private StatisticsCollector currentStats;	
	public static final double BaselineTime = 3.5;
	
	private double targetTime = 0;
	
	private eLessonType lessonType;
	
	private static final String dataFileName = "math.dat";
	
	public Teacher()
	{
	}
	
	public void loadGradeBook()
	{
		File dataFile = new File(dataFileName);
		if (dataFile.exists())
		{
			gradeBook = PersistanceHandler.readGradeBookFromFile(dataFileName);
		}
		else
		{
			gradeBook = new GradeBook();
		}
	}

	public void teach()
	{
		eTopChoice choice;

		//Prepare for student
		loadGradeBook();

		//Get student
		getStudent();

		if (isNewStudent())
		{
			IOUtils.writeString("OK, " + studentName + ". Let's do a quick baseline test.");
			IOUtils.writeString("We're going to do addition with 0!");
			
			operator = eOperator.addition;
			targetNumber = 0;
			
			lessonType = eLessonType.Baseline;
			targetTime = BaselineTime;
			
			ProblemCollectionDefinition pcd = new ProblemCollectionDefinition(operator, targetNumber, 0, 9, targetTime);
			
			practice(pcd);
		}
		
		choice = promptForTopChoice();
		
		while (choice != eTopChoice.quit)
		{
			if (choice == eTopChoice.practice)
			{
				//Choose lesson parameters
				ProblemCollectionDefinition pcd = decideLesson();
				//practice
				practice(pcd);
			}
			else if (choice == eTopChoice.viewStats)
			{
				viewStats();
			}
			
			//Do it again
			choice = promptForTopChoice();
		}
	}
	

	private void practice(ProblemCollectionDefinition pcd)
	{		
		//Create something to keep track of things
		currentStats = new StatisticsCollector(pcd);
		
		//Prepare lesson
		List<Problem> problems = ProblemGenerator.GenerateProblems(pcd.operator, pcd.startNum, pcd.endNum, pcd.focusNum, true);

		//Present lesson
		ProblemPresenter presenter = new ProblemPresenter(problems, currentStats);
		promptForReady();
		presenter.shuffleProblems();
		presenter.PresentProblems();

		//Grade lesson
		//Give feedback
		giveFeedback();

		//Record lesson 
		gradeBook.recordStatsForStudent(studentName, currentStats);
		if (lessonType == eLessonType.Baseline)
		{
			gradeBook.setTimeForStudent(studentName, currentStats.calculateAverageTime()+1);
		}
		
		//Save grade book to file
		PersistanceHandler.writeObjectToFile(dataFileName, gradeBook);
	}
	
	private void viewStats()
	{
		//promptForStatsChoice();
		Statistics stats = gradeBook.getStatsForStudent(studentName);
		String s = StatisticsGenerator.displayHighLevelStats(stats);
		IOUtils.writeString(s);
	}
	
	public void getStudent()
	{
		IOUtils.writeString("Please enter your name.");
		studentName = IOUtils.getString();
		
		loadGradeBook();
	}
	
	public ProblemCollectionDefinition decideLesson()
	{
		IOUtils.writeString("What would you like to work on? +  or  -");
		String op = IOUtils.getParticularString(new String[] {"+", "-", "x", "/"});
		operator = eOperator.fromString(op);

		IOUtils.writeString("Now choose which number you'd like to work with.");
		targetNumber = IOUtils.getInteger();	
		targetTime = gradeBook.getTimeForStudent(studentName);
		
		//find out what the student did last time 
		ProblemCollectionDefinition oldPCD = gradeBook.getLastPCDforStudent(studentName, operator, targetNumber);
		double oldScore = gradeBook.getLastScoreForStudent(studentName, operator, targetNumber);
		
		//figure out the parameters this time
		ProblemCollectionDefinition newPCD = calcNewPCD(oldPCD, oldScore);
		
		//If there was no oldPCD for this setup
		if (newPCD == null)
		{
			newPCD = new ProblemCollectionDefinition(operator, targetNumber, 0, 9, BaselineTime);
		}

		return newPCD;
	}
	
	public void promptForReady()
	{
		IOUtils.writeString("Press enter when you're ready to start");
		IOUtils.getString();
	}
	
	public void giveFeedback()
	{
		double grade = currentStats.getGrade();
		double avgTime = currentStats.calculateAverageTime();
		
		IOUtils.writeString(studentName + ", your grade is: " + grade);
		IOUtils.blankLn();
		IOUtils.writeString("Your average time was: " + IOUtils.fd(avgTime));
		IOUtils.blankLn();
		
		currentStats.displayAllProblems();
	}
	
	/*private boolean promptToContinue()
	{
		IOUtils.writeString("Would you like to continue? y/n");
		String response = IOUtils.getParticularString(new String[]{"y","n"});
		return response.equals("y");
	}*/
	
	private eTopChoice promptForTopChoice()
	{
		IOUtils.writeString("What would you like to do?");
		IOUtils.writeString("1. View Your Statistics");
		IOUtils.writeString("2. Practice");
		IOUtils.writeString("3. Quit");
		String response = IOUtils.getParticularString(new String[]{"1","2","3"});

		switch (response)
		{
		case "1":
			return eTopChoice.viewStats;
		case "2":
			return eTopChoice.practice;
		case "3":
			return eTopChoice.quit;
		}
		
		return eTopChoice.quit;
	}
	
	//TODO Do the right thing when they answer no.
	private boolean isNewStudent()
	{
		if (!gradeBook.containsStudent(studentName))
		{
			IOUtils.writeString("I didn't find you.  Are you new?  y/n");
			String ans = IOUtils.getParticularString(new String[]{"y", "n"});
			if (ans.equals("y"))
			{
				return true;
			}
		}
		
		return false;
	}
	
	private ProblemCollectionDefinition calcNewPCD(ProblemCollectionDefinition oldPCD, double score)
	{
		ProblemCollectionDefinition newPCD;
		
		if (oldPCD == null)
		{
			return null;
		}
			
		newPCD = new ProblemCollectionDefinition(oldPCD);
		
		//If the score is greater than 85, add a new number to the list
		if (score >= 85)
		{
			if (newPCD.endNum < 9)
			{
				newPCD.endNum++;
			}
		}
		else
		{
			if (newPCD.endNum > 0)
			{
				newPCD.endNum--;
			}
		}
		
		return newPCD;
	}
	
	public static void main(String[] args)
	{
		Teacher teacher = new Teacher();
		teacher.teach();
	}
	
}
