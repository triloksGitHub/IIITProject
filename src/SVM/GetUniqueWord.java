package SVM;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.TreeSet;

import TweetsRefine.Stemmer;

import ConfSetUp.*;


public class GetUniqueWord {

	Configurations tmp= Configurations.getInstance();
	/**
	 * @param args
	 */

	public static HashSet<String> hs = new HashSet<String>();	
	public static TreeSet<String> stemmedUniqueWord = new TreeSet<String>();
	public static TreeSet<String> unStemmedUniqueWord = new TreeSet<String>();
	
public static void enterStopWordInTreeSet(){
	
	hs.add("the");hs.add("of");	hs.add("a");hs.add("able");	hs.add("about");hs.add("across");hs.add("after");
	hs.add("all");hs.add("almost");	hs.add("also");	hs.add("am");hs.add("among");hs.add("an");hs.add("and");
	hs.add("any");hs.add("are");hs.add("as");hs.add("at");hs.add("be");hs.add("because");hs.add("been");hs.add("but");
	hs.add("by");hs.add("can");hs.add("cannot");hs.add("could");hs.add("dear");hs.add("did");hs.add("do");hs.add("does");
	hs.add("either");hs.add("else");hs.add("ever");hs.add("every");	hs.add("for");hs.add("from");hs.add("get");hs.add("got");
	hs.add("had");hs.add("has");hs.add("have");hs.add("he");hs.add("her");hs.add("hers");hs.add("him");hs.add("his");hs.add("how");
	hs.add("however");hs.add("i");hs.add("if");hs.add("in");hs.add("into");hs.add("is");hs.add("it");hs.add("its");	hs.add("just");
	hs.add("least");hs.add("let");hs.add("like");hs.add("likely");hs.add("may");hs.add("me");hs.add("might");hs.add("most");
	hs.add("must");	hs.add("my");hs.add("neither");hs.add("no");hs.add("nor");hs.add("not");hs.add("of");hs.add("off");
	hs.add("often");hs.add("on");hs.add("only");hs.add("or");hs.add("other");hs.add("our");hs.add("own");hs.add("rather");
	hs.add("said");hs.add("say");hs.add("says");hs.add("she");hs.add("should");hs.add("since");hs.add("so");hs.add("some");
	hs.add("than");	hs.add("that");	hs.add("the");hs.add("their");hs.add("them");hs.add("then");hs.add("there");hs.add("these");
	hs.add("they");hs.add("this");hs.add("tis");hs.add("to");hs.add("too");hs.add("twas");hs.add("us");hs.add("wants");
	hs.add("was");hs.add("we");hs.add("were");hs.add("what");hs.add("when");hs.add("where");hs.add("which");hs.add("while");
	hs.add("who");hs.add("whom");hs.add("why");hs.add("will");hs.add("with");hs.add("would");hs.add("yet");	hs.add("you");
	hs.add("your");
}



	
	public  static void stopWord(String str)
	{
	//	System.out.println(str);
		Stemmer stem=new Stemmer();
		
		String strBreak[]=str.split(" ");
		String token;
		for(int i=0;i<strBreak.length;i++)
		{
			
			if(!hs.contains(strBreak[i]))
			{
				unStemmedUniqueWord.add(strBreak[i]);
				stem.add(strBreak[i].toCharArray(), strBreak[i].length());
				stem.stem();
				token = stem.toString();
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
		 	}
		try{
			 BufferedWriter uniquewriter,unstemmedwriter;
			 File uf=new File("unStemmedUniqueWord");
			 unstemmedwriter = new BufferedWriter(new FileWriter(uf,true));
			 File sf=new File("stemmedUniqueWord");
			 uniquewriter = new BufferedWriter(new FileWriter(sf,true));
				
			 
			 for(String uni:stemmedUniqueWord)
				 uniquewriter.write(uni+"\n");
			
			 for(String uni:unStemmedUniqueWord)
				 unstemmedwriter.write(uni+"\n");
			 
		 }
		 catch(Exception e)
		 {
			 
			 e.printStackTrace();
		 }

	}
	
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		GetUniqueWord guw=new GetUniqueWord();
		guw.enterStopWordInTreeSet();
		try {
			guw.removeStop();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
