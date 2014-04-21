package TweetsRefine;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.TreeSet;

public class TweetCleaner 
{
	static public String filePath_str;
	static public String refinedTweetFile_str;
	static private BufferedReader tweetFile_BR=null;
	static private BufferedWriter tweetFile_BW=null;
	public static TreeSet<String> stemmedUniqueWord = null;
	public static TreeSet<String> unStemmedUniqueWord = null;
	
	LoadStopWord lsw;
	Stemmer stem = new Stemmer();
	
	public TweetCleaner() {	
		lsw = LoadStopWord.getInstance();
		stemmedUniqueWord = new TreeSet<String>();
		unStemmedUniqueWord = new TreeSet<String>();
	}
	 StringBuilder finalTweet= new StringBuilder("");
		 
	//String outPath="";

	public void initialize(String fileName,String outFileName) throws IOException
	{
		tweetFile_BR = new BufferedReader(new FileReader(new File(fileName)));
		tweetFile_BW = new BufferedWriter(new FileWriter(outFileName));
	}
	
	public void deInitialize() throws IOException
	{
		tweetFile_BR.close();
		tweetFile_BW.close();
	}
	
	
	public void extractTweet() throws IOException
	{
		String line_str = "";
		String word = null,subw = null;
		String[] subwords;
		String[] tweet_str;
		int lineLength,subLineLength;
		String stemWord;
		
		while((line_str = tweetFile_BR.readLine()) != null)
		{
//			System.out.println("line = "+line_str);
			tweet_str = line_str.split(" ");
			lineLength = tweet_str.length;
//			System.out.println("tweetStr =");
			
			for(int i=0; i<lineLength; i++)
			{
				word = tweet_str[i];
		
//				System.out.println("myword1 = "+word);
				if(word.contains("http")==true || (word.length() <= 2) || (lsw.stopwords.contains(word)==true))	
					continue;
				
				subwords = word.split("[^a-z]");
				subLineLength = subwords.length;
							
				for(int j=0; j<subLineLength; j++)
				{
					subw = subwords[j];
					
//					System.out.println("myword"+j+"= "+subw);
					if( (subw.length() <= 2) || (lsw.stopwords.contains(subw)==true) )
						continue;


					unStemmedUniqueWord.add(subw);
//					System.out.println("unstemmed word = "+subw);
					/*
//					unStemmedUniqueWord.add(subw);
					stem.add(subw.toCharArray(), subw.length());
					stem.stem();
					stemWord = stem.toString();
					*/
					
					stemWord = subw;
					stemWord = stemWord.replaceAll("(\\w)\\1+", "$1");
//					System.out.println("stemmed word = "+stemWord);
					
					if(stemWord.length() > 1)
					{
						stemmedUniqueWord.add(stemWord);
						finalTweet.append(stemWord);			
						if(j!=subLineLength-1)	finalTweet.append(" ");
					}
				}
				
				if(i!=lineLength-1)	 finalTweet.append(" ");
				
			}				
			if(finalTweet.length()>0)
				tweetFile_BW.write(finalTweet.toString() + "\n" );
		
			finalTweet.setLength(0);
		}		
	}
	
	
	public void writeUniqueWords()
	{
		try{
			 BufferedWriter uniquewriter,unstemmedwriter;
			 unstemmedwriter = new BufferedWriter(new FileWriter(new File("unstemmedUniqueWord")));
			 uniquewriter = new BufferedWriter(new FileWriter(new File("stemmedUniqueWord")));
				
			 
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
}
