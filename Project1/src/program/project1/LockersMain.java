package program.project1;


import java.io.File;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.regex.Pattern;

public class LockersMain {

	public static void main(String[] args) {
		
		@SuppressWarnings("resource")
		Scanner UserScanner = new Scanner(System.in);
		Scanner businessScanner = new Scanner(System.in);
		//Scanner ScannerFile = new Scanner(System.in);
		
		try
		{
			int userChoiseGM = 0;
			String fileRepository = "";
			String[] listFileName = null;
			 
			do
			{
				do
				{
					printSpace();
					display_header();
					displayMainMenu();
					System.out.println("Please select form the General Menu: 1, 2, or 3");
					
					userChoiseGM = UserScanner.nextInt();

					if(userChoiseGM < 1 || userChoiseGM > 3)
					{
						System.out.println("Your choise is not correct. Please select between 1, 2, or 3");
						System.out.println("");
					}
					
				}while(userChoiseGM < 1 || userChoiseGM > 3);
				
				
				switch (userChoiseGM)
				{
					case 1://Retrieve file names in an ascending order
						System.out.println("Please enter the repository in which the files are located");
						fileRepository = UserScanner.next();
						
						listFileName = returnFileNameAsc(fileRepository);
						
						System.out.println("List of the content of "+fileRepository+" repository:");
						System.out.println("");
						
						if(listFileName.length > 0)
						{
							for (String filename : listFileName) {
					            // Print the names of files and directories
					            System.out.println(filename);
					        }
						}
						else
						{
							System.out.println("There is no file in the repository.");
						}
						
						printSpace();
						break;
						
					case 2://Business level operation
						businessLevelOperation(businessScanner);
						printSpace();
						break;
					
						
					case 3:
						userChoiseGM = 4;
						exitApplicaiton();
						break;
				}
				
			}while(userChoiseGM >= 1 && userChoiseGM <= 3);
			
		}catch(Exception ex)
		{
			ex.getStackTrace();
		}
		
	}//end main
	
	
	//************************BUSINESS LEVEL
	private static void businessLevelOperation(Scanner blUserScanner) throws Exception {
		
		int blUserChoic;

		do
		{
			do
			{
				printSpace();
				display_header();
				display_businessSM();
				displayBusinessMenu();
				System.out.println("Please enter your choise for Business Sub menu : 1, 2, 3 or 4");
				
				blUserChoic = blUserScanner.nextInt();
				
				if(blUserChoic < 1 || blUserChoic > 4)
				{
					System.out.println("Business Level - Your choise is not correct. Please select between 1, 2, 3 or 4");
					System.out.println("");
				}
				
			}while(blUserChoic < 1 || blUserChoic > 4);
			
			
			String themessage,fileRepository,fileName = "";
			
			switch (blUserChoic)
			{
				case 1: //Add a user specified file to the application
					System.out.println("Please enter the direct repository in which the file is located");
					fileRepository = blUserScanner.next();
					
					System.out.println("Please enter the file name");
					fileName = blUserScanner.next();
					
					themessage = addUserFile(fileRepository,fileName);
					System.out.println(themessage);
					printSpace();
					break;
				
				case 2://Delete a user specified file from the application
					System.out.println("Please enter the direct repository in which the file is located");
					fileRepository = blUserScanner.next();
					
					System.out.println("Please enter the file name");
					fileName = blUserScanner.next();
					
					themessage = deleteFile(fileRepository,fileName);
					System.out.println(themessage);
					printSpace();
					break;
					
				case 3://Search a user specified file from the application
					System.out.println("Please enter the name of the file you are locking for: ");
					fileName = blUserScanner.next();
					
					boolean isfileFo = false;
					isfileFo = searchUserfile("",fileName);
					
					if(isfileFo == true)
					{
						System.out.println("Your file exist in the main repository");
					}
					else
					{
						System.out.println("Your file does not exist in the main repository");
					}
					printSpace();
					break;
					
					
				case 4://Back to General Menu
					blUserChoic = 5; //this to get it out of the loop
					System.out.println("Out of the Business sub menu, and back to General Menu");
					break;
				
			}
			
		}while(blUserChoic >= 1 && blUserChoic <= 4);
		
	}
	
	
	// Print Space
	private static void printSpace() {
		
		System.out.println("");
		System.out.println("");
	}


	//****************** END BUSINESS LEVEL ******************


	//*****************************Add to file*************************************
	private static String addUserFile(String repositoryName, String fileName) {
		
		String filePath = "";
		String themessage = "";
		
		try {
			
			boolean isDirectory = checkDirectory(repositoryName);
			
			if(isDirectory == true) //If it is a directory
			{
				filePath = getFilePath(repositoryName,fileName);
				File file = new File(filePath);
				
				if(file.exists())
				{
					themessage = "The file ("+fileName+") already exists. Impossible to create a new file.";
				}
				else
				{
					if(file.createNewFile())
					{
						themessage = "The new file ("+fileName+") is created";
					}
				}
			}
			else
			{
				System.out.println(repositoryName+" does not exist. Impossible to continue.");
			}
			
		}catch(Exception ex) {
			 ex.printStackTrace();
		}
		
		return themessage;
	}
	
	
	private static boolean checkDirectory(String theDirectory) {
		
		boolean isDirectory = false;
		
		String directoryFile = getFilePath(theDirectory,"");
		
		File myDirecto = new File(directoryFile);
		
		if(myDirecto.isDirectory())
		{
			isDirectory = true;
		}
		
		return isDirectory;
	}


	//*****************************************************************************
	
	//********************Delete User from file**********************************
	private static String deleteFile(String repositoryName, String fileName) {
		
		String filePath, themessage = "";
		
		filePath = getFilePath(repositoryName,fileName);
		
		try
		{
			
			boolean isDirectory = checkDirectory(repositoryName);
			
			if(isDirectory == true) //If it is a directory
			{
				File file = new File(filePath);
				
				
				if(file.exists())
				{
					if(file.delete())
					{
						themessage = "The file has been deleted.";
					}
					else
					{
						themessage = "The file cannot be deleted.";
					}
				}
				else
				{
					themessage = "The file does not exist. Impossible to delete.";
				}
			}
			else
			{
				themessage = "The directory ("+repositoryName+") does not exist. Impossible to delete.";
			}
			
		}catch(Exception ex){
			ex.getClass();
		}
		
		return themessage;
	}
	
	
	
	private static String getFilePath(String theRepository, String theFileName) {
		
		String filePath, currentDir = "";
		currentDir = System.getProperty("user.dir");
		
		if(theRepository.length() > 0)
		{
			if(theFileName.length() > 0)
			{
				filePath = currentDir+"\\"+theRepository+"\\"+theFileName;
			}
			else
			{
				filePath = currentDir+"\\"+theRepository;
			}
		}
		else //repository is not provided
		{
			if(theFileName.length() > 0)
			{
				filePath = currentDir+"\\"+theFileName;
			}
			else
			{
				filePath = currentDir;
			}
		}
		
		return filePath;
	}


	//*************************************************************************

	//********************search user file*************************************
	private static boolean searchUserfile(String theFileRepository, String researchFile) throws Exception
	{
		String[] fileListed = null;
		boolean itOk = false; 
		boolean filefound = false;
		
		String mainReposit = getFilePath("","");
		
		try {
			fileListed = returnFileNameAsc(mainReposit);
			
			if(fileListed.length > 0)
			{
				
				for (String myfilename : fileListed) {

					if(Pattern.matches("[a-zA-Z0-9] ?.*[a-z]", myfilename)) //regex found
					{
			            if(myfilename.equals(researchFile))
			            {
			            	filefound = true;
			            	itOk = true;
			            }
			            else
			            {
			            	filefound = false;
			            }
					}
		        }
				if (itOk == true)
				{
					filefound = itOk;
				}
			}
			else
			{
				filefound = false;
				System.out.println("There is no file available in the main directory");
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return filefound;
	}
	//*************************************************************************


	//********************returnFileNameAsc **************************
	private static String[] returnFileNameAsc(String theFileRepository) {
		
		String[] theListOfFile = null;
		
		File file = new File(theFileRepository);
		
		// Populates the array with names of files and directories
		theListOfFile = file.list();
		
		return theListOfFile;
	}

	//*****************************************************************


	private static void displayBusinessMenu()

	{
		System.out.println("");
		System.out.println("1 - Add a file to the existing directory list");
		System.out.println("");
		System.out.println("2 - Delete a user specified file from the existing directory list");
		System.out.println("");
		System.out.println("3 - Search a user specified file from the main directory");
		System.out.println("");
		System.out.println("4 - Return to the Main Menu");
		System.out.println("");
	}
	

	private static void displayMainMenu()
	{
		System.out.println("");
		System.out.println("******************************* GENERAL MENU ***************************************");
		System.out.println("");
		System.out.println("");
		System.out.println("1 - Return the current file names in ascending order.");
		System.out.println("");
		System.out.println("2 - Business Sub Memu");
		System.out.println("");
		System.out.println("3 - Exist the application");
		System.out.println("");
	}


	private static void display_header()
	{
		System.out.println("*****************************************************************************************");
		System.out.println("*******************************         *          **************************************");
		System.out.println("******************************* LOCKERS Pvt. Ltd. ***************************************");
		System.out.println("*******************************         *          **************************************");
		System.out.println("*****************************************************************************************");
		System.out.println("");
	}

	
	private static void display_businessSM()
	{
		System.out.println("******************************* BUSINESS SUB MENU ***************************************");
		System.out.println("");
	}
	
	
	private static void exitApplicaiton() {
		
		System.out.println("Thank you for using Lockers program.");
		System.exit(1);
		
	}
}
