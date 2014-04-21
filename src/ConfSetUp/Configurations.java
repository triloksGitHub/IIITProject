package ConfSetUp;

import java.io.File;
import java.io.IOException;

public class Configurations {

	public String fileArray[];
	public String UnFilteredTwitterTagFiles;
//	public String FilteredTwitterTagFiles;
	public String uncleanedMergedFile,cleanedMergedFile;

	public String uncleanedMergedFilesPath,cleanedMergedFilesPath;
	public String SVMIndexPath_Train,SVMIndexPath_Test,SVMModelPath_Train;
	public String NaiveIndexPath_Train,NaiveIndexPath_Test,NaiveModelPath_Train;
//	public String unfiltered,unfilteredPath;
//	public String filtered,filteredPath;
	public int noOfCategories;
	public File theDir;
	
	private static Configurations instance = null;
	
	protected Configurations()
	{
		/*
		unfiltered = "Tweets";
		unfilteredPath="./"+ unfiltered +"/";
		
		filtered = "FilterTweets";
		filteredPath="./"+ filtered +"/";
		*/

		uncleanedMergedFile = "MergedFiles_UnClean";
		cleanedMergedFile = "MergedFiles_Clean";
		
		uncleanedMergedFilesPath = "./"+uncleanedMergedFile+"/";
		cleanedMergedFilesPath = "./"+cleanedMergedFile+"/";
		
		UnFilteredTwitterTagFiles = "./UnfilteredTagFiles/";
//		FilteredTwitterTagFiles = "./FilteredTagFiles/";
		SVMIndexPath_Train = "./svm/indexTrain/";
		SVMIndexPath_Test = "./svm/indexTest/";
		SVMModelPath_Train = "./svm/model/";
		
		NaiveIndexPath_Train = "./naive/";
		NaiveIndexPath_Test = "./naive/";
		

		noOfCategories = 11;
		
		fileArray=new String[noOfCategories];
		fileArray[0]="Business";
		fileArray[1]="Education";
		fileArray[2]="Entertainment";
		fileArray[3]="Health";
		fileArray[4]="Law";
		fileArray[5]="Lifestyle";
		fileArray[6]="Nature";
		fileArray[7]="Places";
		fileArray[8]="Politics";
		fileArray[9]="Sports";
		fileArray[10]="Technology";
	
		/**********If Dir not exists *******************/
		 /*
		 theDir = new File(unfiltered);
		 if (!theDir.exists())		theDir.mkdirs();
		 
		 theDir = new File(filteredPath);
		 if (!theDir.exists())		theDir.mkdirs();
		 */
		
		 theDir = new File(NaiveIndexPath_Train);
		 if (!theDir.exists())		theDir.mkdirs();
		 
		 theDir = new File(SVMIndexPath_Train);
		 if (!theDir.exists())		theDir.mkdirs();
		 
		 theDir = new File(SVMIndexPath_Test);
		 if (!theDir.exists())		theDir.mkdirs();

		 theDir = new File(SVMModelPath_Train);
		 if (!theDir.exists())		theDir.mkdirs();
	
		 theDir = new File(uncleanedMergedFilesPath);
		 if (!theDir.exists())		theDir.mkdirs();
		
		 theDir = new File(cleanedMergedFilesPath);
		 if (!theDir.exists())		theDir.mkdirs();
		
		 
		 for(int i=0; i<noOfCategories; i++)
		 {

			 File f = null;
				 try{
					 f=new File(uncleanedMergedFilesPath+fileArray[i]);
					 if(!f.exists())	f.createNewFile();
					 
					 f=new File(cleanedMergedFilesPath+fileArray[i]);
					 if(!f.exists())	f.createNewFile();
					 
				 	} catch(Exception e)
				 	{
					 e.printStackTrace();
				 	}		
		 }
		
	/*	
		 for(int i=0; i<noOfCategories; i++)
		 {
			 theDir = new File(UnFilteredTwitterTagFiles+fileArray[i]);
			 if (!theDir.exists())		theDir.mkdirs();
		 }
		 */
		 
		/*
		 for(int i=0; i<noOfCategories; i++)
		 {
			 theDir = new File(FilteredTwitterTagFiles+fileArray[i]);
			 if (!theDir.exists())		theDir.mkdirs();
		 }
		 */
		 /******************************************/		 

	}
	
	   public static Configurations getInstance() 
	   {
	      if(instance == null) 
	      {
	         instance = new Configurations();
	      }
	      return instance;
	   }
}
