
import java.io.File;
import java.util.List;

public class Teacher {

	private String studentName = "";
	private GradeBook gradeBook;
	
	private eOperator operator;
	private int targetNumber;
	
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

		choice = promptForChoice();
		
		while (choice != eTopChoice.quit)
		{
			if (choice == eTopChoice.practice)
			{
				practice();
			}
			else if (choice == eTopChoice.viewStats)
			{
				viewStats();
			}
			
			//Do it again
			choice = promptForChoice();
		}
	}
	
	private void practice()
	{
		//Choose lesson
		decideLesson();

		//Prepare lesson
		List<Problem> problems = ProblemGenerator.GenerateProblems(operator, 0, 9, targetNumber, true);

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
	
	//TODO decide Lesson before giving options (i.e. if new student that should override all)
	public void decideLesson()
	{
		if (isNewStudent())
		{
			IOUtils.writeString("OK, " + studentName + ". Let's do a quick baseline test.");
			IOUtils.writeString("We're going to do addition with 0!");
			
			operator = eOperator.addition;
			targetNumber = 0;
			
			lessonType = eLessonType.Baseline;
			targetTime = BaselineTime;
		}
		else
		{
			IOUtils.writeString("What would you like to work on? +  or  -");
			String op = IOUtils.getParticularString(new String[] {"+", "-", "x", "/"});

			operator = eOperator.fromString(op);

			IOUtils.writeString("Now choose which number you'd like to work with.");
			targetNumber = IOUtils.getInteger();	
			targetTime = gradeBook.getTimeForStudent(studentName);
		}
		
		//TODO the time should be based on previous work
		currentStats = new StatisticsCollector(operator, targetNumber, targetTime);
		
	}
	
	public void promptForReady()
	{
		IOUtils.writeString("Press enter when you're ready to start");
		IOUtils.getString();
	}
	
	public void giveFeedback()
	{
		double grade = 100*currentStats.calculateGrade();
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
	
	private eTopChoice promptForChoice()
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
	
	public static void main(String[] args)
	{
		Teacher teacher = new Teacher();
		teacher.teach();
	}
	
}
