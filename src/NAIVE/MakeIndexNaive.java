package NAIVE;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

//import TweetsRefine.LoadStopWord;

import ConfSetUp.Configurations;
import FindSynonyms.Synonyms;
import TweetsRefine.Stemmer;

public class MakeIndexNaive 
{

	Configurations tmp= Configurations.getInstance();
//	LoadStopWord lsw = LoadStopWord.getInstance();
	
	public TreeMap<String, Integer> listOfWord =new TreeMap<String, Integer>();;
	
	public void readUniqueWord() throws IOException
	{
		BufferedReader br=new BufferedReader(new FileReader(new File("stemmedUniqueWord")));
		String str="";
		for(int i=0;(str=br.readLine()) != null ; i++)
		{
			listOfWord.put(str, i);
		}
		br.close();
	}
		
	public String getFixedArffAttributeOfNaive() throws IOException
	{
		StringBuilder tagVals = new StringBuilder();
		
		String str = "";

		tagVals.append("\n@RELATION iris\n");

		Set<String> setOfWord=listOfWord.keySet();
		
		Iterator<String> iterator=setOfWord.iterator();

		while(iterator.hasNext())
		{
			str=iterator.next();
	
			if(str.compareTo("class")==0)	str="classsss";
			
//			bw.write("@ATTRIBUTE "+str+"	REAL\n");
			tagVals.append("@ATTRIBUTE "+str+"	REAL\n");
		}
	
//		bw.write("@ATTRIBUTE class 	{");
		tagVals.append("@ATTRIBUTE class 	{");
				
		for(int i=0;i<tmp.noOfCategories;i++)
		{
//			bw.write(tmp.fileArray[i]);
			tagVals.append(tmp.fileArray[i]);
					
			if( i < (tmp.noOfCategories-1) )
			{
//				bw.write(",");
				tagVals.append(",");
			}
		}
//		bw.write("}\n@DATA\n");
		tagVals.append("}\n@DATA\n");
				
//		bw.close();
		
		return tagVals.toString();
	}

	public void writeArffString(String fname,String arfString) throws IOException 
	{
		BufferedWriter bw=new BufferedWriter(new FileWriter(new File(fname)));
		bw.write(arfString);
		bw.close();
	}

	public void generateIndexNaive_Training(String path,String fileName) throws IOException
	{
		String word ="";
		TreeMap<Integer, Integer> tmm = new TreeMap<Integer, Integer>();
				
		BufferedWriter bw_index=new BufferedWriter(new FileWriter(new File(tmp.NaiveIndexPath_Train+"naive.arff"),true));
		BufferedReader br_index=new BufferedReader(new FileReader(new File(path+fileName)));
		String str="";
		int treeIndex;
		
		while((str=br_index.readLine()) != null)
		{
			String split[]=str.split(" ");
			for(int i=0;i<split.length;i++)
			{
				word = split[i];
				
				if(word.compareTo("class")==0)	word="classsss";
				
				if(listOfWord.containsKey(word))
				{
					treeIndex=listOfWord.get(word);
					
					if(tmm.containsKey(treeIndex)==false)
						tmm.put(treeIndex, 1);
					else
						tmm.put(treeIndex, tmm.get(treeIndex)+1);						
				}
			}
			
			if(tmm.isEmpty()==false)
			{
			bw_index.write("{");
				
			for(Entry<Integer, Integer> entry : tmm.entrySet()) 
				bw_index.write(entry.getKey()+" "+entry.getValue()+",");
			

			bw_index.write(listOfWord.size()+" "+fileName+"}"+"\n");
			
			}
			tmm.clear();
		}
		br_index.close();
		bw_index.close();
	}

	public void generateIndexNaive_Testing(String path,String srcFile,String destFile) throws IOException
	{
		Synonyms sn = new Synonyms();
		String[] synonyms;	
		
		int valTmm;
		String word ="";
		TreeMap<Integer, Integer> tmm = new TreeMap<Integer, Integer>();
				
		BufferedWriter bw_index=new BufferedWriter(new FileWriter(new File(tmp.NaiveIndexPath_Test+destFile),true));
		BufferedReader br_index=new BufferedReader(new FileReader(new File(path+srcFile)));
		String str="";
		int treeIndex;
		
		while((str=br_index.readLine()) != null)
		{
			String split[]=str.split(" ");
			for(int i=0;i<split.length;i++)
			{
				word = split[i];

				if(word.compareTo("class")==0)	word="classsss";
				
				if(listOfWord.containsKey(word))
				{
					treeIndex=listOfWord.get(word);
					
					if(tmm.containsKey(treeIndex)==false)
						tmm.put(treeIndex, 1);
					else
						tmm.put(treeIndex, tmm.get(treeIndex)+1);						
				}
				else
				{
					synonyms = sn.getSynonyms(word);
					if (synonyms != null) 
					{
						for (int si = 0; si < synonyms.length; si++) 
						{
							System.out.println("Synonym " + si + ": " + synonyms[si]);
							
							if(listOfWord.containsKey(synonyms[si]))
							{
								treeIndex=listOfWord.get(synonyms[si]);
								valTmm = (tmm.containsKey(treeIndex)==false) ? (1) : (tmm.get(treeIndex)+1) ;
								tmm.put(treeIndex, valTmm);	
								break;
							}
						}
					}
					else
						System.out.println("no synonyms for : "+word);
				}
			}
			
			if(tmm.isEmpty()==false)
			{
			bw_index.write("{");
				
			for(Entry<Integer, Integer> entry : tmm.entrySet()) 
			{
				System.out.println("key ="+entry.getKey() +  "  val = "+entry.getValue());
				bw_index.write(entry.getKey()+" "+entry.getValue()+",");
			}
			

			bw_index.write(listOfWord.size()+" "+"?"+"}"+"\n");
			
			}
			tmm.clear();
		}
		br_index.close();
		bw_index.close();
	}
}
