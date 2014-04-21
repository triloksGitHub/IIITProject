//package ire_major2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;
import java.util.Scanner;
public class QueryProcess {

	public static HashSet<String> hs = new HashSet<String>();
	
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
		hs.add("your");hs.add("good");hs.add("next");hs.add("awesome");hs.add("need");hs.add("hot");hs.add("cold");
		hs.add("blue");hs.add("black");hs.add("yellow");hs.add("green");hs.add("pink");hs.add("think");hs.add("new");
		hs.add("weak");hs.add("week");hs.add("help");hs.add("http");hs.add("https");hs.add("more");hs.add("blog");
		hs.add("till");
		hs.add("buy");hs.add("prepare");hs.add("news");hs.add("form");hs.add("stop");hs.add("team");hs.add("again");
		hs.add("become");hs.add("become");hs.add("back");hs.add("come");hs.add("family");hs.add("matter");
		hs.add("day");hs.add("dai");hs.add("real");hs.add("want");hs.add("over");hs.add("give");hs.add("time");
		hs.add("make");
}
	
	public static String process(String tweet)
	{
		Stemmer stem=new Stemmer();
		enterStopWordInTreeSet();
		
			
			HashMap<String,Integer>hm=new HashMap<String,Integer>();
			HashMap<String,Integer>count=new HashMap<String,Integer>();
			tweet=tweet.toLowerCase();
			String brkTweet[]=tweet.split("[-:,.'@#!%^&*?()+=_:; ]");
			int max=1,max1=1;
			for(int i=0;i<brkTweet.length;i++)
			{
				//max=1;
				if(hs.contains(brkTweet[i]))
					continue;
				stem.add(brkTweet[i].toCharArray(),brkTweet[i].length());
				stem.stem();
				brkTweet[i] = stem.toString();
				
				
//				System.out.println("tweet="+brkTweet[i]);
				String value=Check.uniqueWords.get(brkTweet[i]);
//				System.out.println("---------"+value);
				if(value!=null)
				{
					String val[]=value.split(":");
					
//					System.out.println("val[0]"+val[0]+"val[1]"+val[1]);
					if(hm.containsKey(val[1]))
					{
						int freq=1;
						freq=hm.get(val[1]);
					//	freq=freq;
						if(Integer.parseInt(val[0])>max)
						{
							max=Integer.parseInt(val[0]);
							freq=max;
						}
//						System.out.println("max="+max+"        "+"feq="+freq);
						hm.put(val[1], freq);
						
						int freq1;
						freq1=count.get(val[1]);
						freq1=freq1+1;
						if(freq1>max1)
							max1=freq1;
						count.put(val[1],freq1);
						
					}
					else
					{
						int freq=Integer.parseInt(val[0]);
						if(max<freq)
						max=freq;
//						System.out.println("max="+max+"        "+"feq="+Integer.parseInt(val[0]));
						
						hm.put(val[1],freq);
						count.put(val[1], 1);
					}	
				}
			}
		//	int maxValueInMap=(Collections.max(hm.values()));  // This will return max value in the Hashmap
			
//			System.out.println(t++);
			StringBuffer str=new StringBuffer();
			String ans1=null;
	        for (Map.Entry<String, Integer> entry : hm.entrySet()) { 
//	        	System.out.println("max="+max);// Itrate through hashmap
	            if (entry.getValue()==max) {
	                System.out.println("Ans1 is="+entry.getKey());
//	                bfw.write(entry.getKey());
	                ans1=entry.getKey();
	                System.out.println(ans1);
//	                if(ans1!=null)
	                str.append(ans1+":");
	                // Print the key with max value
	            }
	        }
	        
	        for (Map.Entry<String, Integer> entry1 :count.entrySet()) { 
//	        	System.out.println("max="+max1);// Itrate through hashmap
	        	if(max1>1){
//	        		break;
	            if (entry1.getValue()==max1) {
	                if(ans1.compareToIgnoreCase(entry1.getKey())!=0)
	            	System.out.println("Ans2 is="+entry1.getKey());
	                str.append(entry1.getKey()+":");
//	                bfw.write(entry1.getKey());
//	                break;
	                	
	                // Print the key with max value
	            }}
	        }
//	        bfw.write("\n");
//	        count.clear();
//	        hm.clear();
//            t--;
	       
//	        }
//			 bfr.close();
//			 bfw.close();
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace();
//		}
	
		
		System.out.println(str.toString());
		
		
		
		return (str.toString());
		
	}
}

