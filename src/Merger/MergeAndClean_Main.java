package Merger;

import java.io.BufferedReader;
import ConfSetUp.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.text.html.HTMLDocument.Iterator;

import TweetsRefine.TweetCleaner;

public class MergeAndClean_Main 
{
	
	Configurations tmp;
	BufferedReader readtweet;
	
	public MergeAndClean_Main() {
		tmp= Configurations.getInstance();
		readtweet=null;
	}
		
	
	// merge files of each category
	public void mergeFileTrain()
	{
		 String inSubIndexesPath;
		 String line;
		 File file_list[];
		 int no_of_files;
		 int counter;
	
		Set<String> uniqueTweets = new HashSet<String>();
        BufferedReader tweetReader;
		BufferedWriter[] mergewriter = new BufferedWriter[tmp.noOfCategories];
		 
		 for(int i=0;i<tmp.noOfCategories;i++)
			{

			 inSubIndexesPath = tmp.UnFilteredTwitterTagFiles+tmp.fileArray[i]+"/";
			
				 file_list=new File(inSubIndexesPath).listFiles();
				 no_of_files = file_list.length;
			
				 try{
					   File f = null;
					  
						 try{
							 f=new File(tmp.uncleanedMergedFilesPath+tmp.fileArray[i]);
//							 if(!f.exists())	f.createNewFile();
						 	} catch(Exception e)
						 	{
							 e.printStackTrace();
						 	}
					 
						 
					
						 for(int j=0;j<no_of_files;j++)
						 {
							 counter = 0;
							 
							 System.out.println("filename = "+inSubIndexesPath+file_list[j].getName());
							 
						 	 	tweetReader=new BufferedReader(new FileReader( new File( inSubIndexesPath+file_list[j].getName() ) ));	 	
						 	 	line=null;
	
						 	 	while ((line = tweetReader.readLine()) != null) 
						 	 	{
						 	 		if(uniqueTweets.contains(line.toLowerCase())==false)
						 	 			counter++;
						 	 		
						 	 		uniqueTweets.add(line.toLowerCase());
						 	 		
						 	 		if(counter > 1000)	
						 	 		{
						 	 			System.out.println(tmp.fileArray[i] + "  " + file_list[j].getName());
						 	 			break;
						 	 		}
						 	 	}
						 	 	tweetReader.close();
						 }

						 mergewriter[i] = new BufferedWriter(new FileWriter(f));
						 for(String tweet : uniqueTweets)
						 {
						 	 		mergewriter[i].write(tweet+"\n");
						 }
						 mergewriter[i].close();
				 	}
					catch(Exception e)
					{
					 		e.printStackTrace();
					}
				 uniqueTweets.clear();
			}		
		}
	

	public void cleanTweetsTrain()
	{
		TweetCleaner tweetCollectorObj=new TweetCleaner();
		
		 for(int i=0;i<tmp.noOfCategories;i++)
		 {
			 try {
				tweetCollectorObj.initialize(tmp.uncleanedMergedFilesPath+tmp.fileArray[i],tmp.cleanedMergedFilesPath+tmp.fileArray[i]);
				tweetCollectorObj.extractTweet();
				tweetCollectorObj.deInitialize(); 
				
				tweetCollectorObj.writeUniqueWords();
			 } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
	}

	public void cleanTweetsTest(String srcFile,String destFile)
	{
		TweetCleaner tweetCollectorObj=new TweetCleaner();
		
		 try {
			tweetCollectorObj.initialize(srcFile,destFile);
			tweetCollectorObj.extractTweet();
			tweetCollectorObj.deInitialize(); 			
		 } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 
	}

	public static void main(String args[]) throws IOException
	{
		MergeAndClean_Main vf = new MergeAndClean_Main();
		vf.mergeFileTrain();
		vf.cleanTweetsTrain();	
	}
}	
	

