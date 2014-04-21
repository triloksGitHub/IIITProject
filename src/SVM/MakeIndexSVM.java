package SVM;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map.Entry;
import java.util.TreeMap;

import ConfSetUp.Configurations;
import FindSynonyms.Synonyms;


public class MakeIndexSVM 
{
	Configurations tmp;
	String fileName="";
	public TreeMap<String, Integer> listOfWord;
	
	public MakeIndexSVM() 
	{
		tmp = Configurations.getInstance();
		listOfWord=new TreeMap<String, Integer>();
	}
	
	
	public void readUniqueWord() throws IOException
	{
		BufferedReader br=new BufferedReader(new FileReader(new File("stemmedUniqueWord")));
		String str;
		for(int i=0;(str=br.readLine()) != null ; i++)
		{
			listOfWord.put(str, i);
		}
		br.close();
	}
	
	public void generateIndexSVM_Training(String fileName1,int val1,String fileName2,int val2) throws IOException
	{
		int valTmm;
		String fileName=fileName1;
		String className="";
		String word ="";
		
//		System.out.println("fileName1="+fileName1+ "val1= "+val1+ "fileName2="+fileName2+ "val2="+ val2);
		BufferedWriter bw_index=new BufferedWriter(new FileWriter(new File(tmp.SVMIndexPath_Train+"index_"+val1+"_"+val2)));
		TreeMap<Integer, Integer> tmm = new TreeMap<Integer, Integer>();
		int treeIndex;
		
		className="+1";
		for(int j=0;j<2;j++)
		{
			BufferedReader br_index=new BufferedReader(new FileReader(new File(tmp.cleanedMergedFilesPath+fileName)));
			String str="";
			
			while((str=br_index.readLine()) != null)
			{
				String split[]=str.split(" ");
				for(int i=0;i<split.length;i++)
				{
					word = split[i];
					if(listOfWord.containsKey(word))
					{
						treeIndex=listOfWord.get(word);
						valTmm = (tmm.containsKey(treeIndex)==false) ? (1) : (tmm.get(treeIndex)+1) ;
						tmm.put(treeIndex, valTmm);					
					}
				}
				
				if(tmm.isEmpty()==false)
				{
					bw_index.write(className);
					for(Entry<Integer, Integer> entry : tmm.entrySet()) 
						bw_index.write(" "+entry.getKey()+":"+entry.getValue());
					bw_index.write("\n");
				}
				tmm.clear();
			}
			br_index.close();
			fileName=fileName2;
			className="-1";
		}
		bw_index.close();
	}
	
	public void generateIndexSVM_Testing(String fileName,String modelName,String outFileName) throws IOException
	{
		Synonyms sn = new Synonyms();
		String[] synonyms;	
		
		int valTmm;
		String className="";
		String word ="";
		BufferedWriter bw_index=new BufferedWriter(new FileWriter(new File(tmp.SVMIndexPath_Test+outFileName),false));
		TreeMap<Integer, Integer> tmm = new TreeMap<Integer, Integer>();
		int treeIndex;
		
		className="-1";
		
			BufferedReader br_index=new BufferedReader(new FileReader(new File(fileName)));
			String str="";
			int failedQuery = 0;
			while((str=br_index.readLine()) != null)
			{
				failedQuery++;
				String split[]=str.split(" ");
				for(int i=0;i<split.length;i++)
				{
					word = split[i];
					System.out.println("word = "+word);
					if(listOfWord.containsKey(word))
					{
						treeIndex=listOfWord.get(word);
						valTmm = (tmm.containsKey(treeIndex)==false) ? (1) : (tmm.get(treeIndex)+1) ;
						tmm.put(treeIndex, valTmm);						
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
				
				System.out.println("tmmSize = "+tmm.size());
				
				if(tmm.isEmpty()==false)
				{
				bw_index.write(className);
				for(Entry<Integer, Integer> entry : tmm.entrySet()) 
					bw_index.write(" "+entry.getKey()+":"+entry.getValue());
				bw_index.write("\n");
				}
				else
					System.out.println("Query Don't Know No = "+failedQuery);
				tmm.clear();
			}
			br_index.close();
			bw_index.close();
	}
}
