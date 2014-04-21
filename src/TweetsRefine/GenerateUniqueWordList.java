package TweetsRefine;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.TreeSet;

import ConfSetUp.*;


public class GenerateUniqueWordList {

	Configurations tmp= Configurations.getInstance();
	LoadStopWord lsw = LoadStopWord.getInstance();
	
	/**
	 * @param args
	 */

	public TreeSet<String> stemmedUniqueWord = new TreeSet<String>();
	public TreeSet<String> unStemmedUniqueWord = new TreeSet<String>();
		
	public  void stopWord(String str)
	{
	//	System.out.println(str);
		Stemmer stem=new Stemmer();
		
		String strBreak[]=str.split(" ");
		String token;
		String word;
		for(int i=0;i<strBreak.length;i++)
		{
			word = strBreak[i].toLowerCase();
			
			if(!lsw.stopwords.contains(word))
			{
				unStemmedUniqueWord.add(word);
				stem.add(word.toCharArray(), word.length());
				stem.stem();
				token = stem.toString();
				
				if(token.compareTo("class")==0)	
					token="classsss";

				if(token.length()>2)
					stemmedUniqueWord.add(token);
				
			}
			
		}
	
	
	}
	
	public void removeStop() throws IOException
	{
		
		for(int j=0;j<tmp.noOfCategories;j++)
		 {
			 //BufferedReader tweetReader=new BufferedReader(in)
	//		 	System.out.println("--1--"+inSubIndexesPath+file_list[j].getName());
		 		FileReader f1=new FileReader(tmp.cleanedMergedFilesPath+tmp.fileArray[j]);
		 	 	BufferedReader tweetReader=new BufferedReader(f1);	 	
		 	 	String line=null;
		 	 	//int k=0;
		 	 	while ((line = tweetReader.readLine()) != null) 
		 	 	{
		 	 		String arr[]=line.split(" ");
		 	 		for(int i=0;i<arr.length;i++)
		 	 		{
		 	 			stopWord(arr[i]);
		 	 		}
		 	 	}
		 	 	tweetReader.close();
		 	}
		try{
			 BufferedWriter uniquewriter,unstemmedwriter;
			
			 unstemmedwriter = new BufferedWriter(new FileWriter(new File("unStemmedUniqueWord"),true));
			 
			 uniquewriter = new BufferedWriter(new FileWriter(new File("stemmedUniqueWord"),true));
				
			 
			 for(String uni:stemmedUniqueWord)
				 uniquewriter.write(uni+"\n");
			 uniquewriter.close();
			
			 for(String uni:unStemmedUniqueWord)
				 unstemmedwriter.write(uni+"\n");
			 unstemmedwriter.close();
		 }
		 catch(Exception e)
		 {
			 
			 e.printStackTrace();
		 }

	}
	
	
	/*
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		GenerateUniqueWordList guw=new GenerateUniqueWordList();
		try {
			guw.removeStop();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
*/
}
