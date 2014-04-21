package TweetsRefine;

import java.io.File;
import java.io.IOException;
import ConfSetUp.*;

public class FilterTweets {

	static Configurations tmp;
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 tmp = Configurations.getInstance();
		

//------------To filter/clean tweets-----------	
		 String inSubIndexesPath,outSubIndexesPath;
		 File file_list[];
		 int no_of_files;
		 TweetCleaner tweetCollectorObj;
		 
		 System.out.println("cleaning TagFiles from UnFiltered to Filtered");
		 
//		 for(int i=0;i<tmp.noOfCategories;i++)
		{
//			 inSubIndexesPath = tmp.UnFilteredTwitterTagFiles+tmp.fileArray[i]+"/";
//			 outSubIndexesPath = tmp.FilteredTwitterTagFiles+tmp.fileArray[i]+"/";
	
			 inSubIndexesPath = tmp.UnFilteredTwitterTagFiles;
//			 outSubIndexesPath = tmp.FilteredTwitterTagFiles;
	
			 inSubIndexesPath = "./MergedFiles/";
			 outSubIndexesPath = "./CleanMergedFiles/";;
	
			 file_list=new File(inSubIndexesPath).listFiles();
			 no_of_files = file_list.length;
		//	 System.out.println(inSubIndexesPath + "has" + no_of_files);
				
			 for(int j=0;j<no_of_files;j++)
				{
					tweetCollectorObj=new TweetCleaner();
					try {
						tweetCollectorObj.initialize(file_list[j].toString(),outSubIndexesPath+file_list[j].getName());
						tweetCollectorObj.extractTweet();	//creates filtered tweets for all categories, read and writes
						tweetCollectorObj.deInitialize();
						} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						}
				}
		}
		 System.out.println("cleaning done");	
//------------*********************************-----------
	}

}
