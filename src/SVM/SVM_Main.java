package SVM;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import Merger.MergeAndClean_Main;
import TweetsRefine.TweetCleaner;

import ConfSetUp.Configurations;

class MyDecreaseComp implements Comparator<Integer>{
	 
    @Override
    public int compare(Integer a, Integer b) {
        return b.compareTo(a);
    }
}
    
public class SVM_Main 
{
	Configurations tmp;
	MakeIndexSVM makeIndexObj;
	LoadModelSVM lm;
	
	public SVM_Main() 
	{
		tmp = Configurations.getInstance();
		makeIndexObj = new MakeIndexSVM();
		lm = new LoadModelSVM();
	}
	
	public void preProcess()
	{
System.out.println("i am in preprocess");
		try {
			makeIndexObj.readUniqueWord();
			lm.loadModel();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void createIndexTrain() throws IOException
	{		
		for(int i=0;i< tmp.noOfCategories;i++)
			for(int j=i+1;j< tmp.noOfCategories;j++)
				makeIndexObj.generateIndexSVM_Training(tmp.fileArray[i],i,tmp.fileArray[j],j);
	}
	

	 private List<String> findClass(int arr[]) throws IOException
	 {
		 List<String> presLt = new ArrayList<String>();
		 List<String> sresLt = new ArrayList<String>();
		 List<String> finalResult = new ArrayList<String>();

		 Map<Integer, TreeSet<String>> resTm = new TreeMap<Integer, TreeSet<String>>(new MyDecreaseComp());
		 
			 for(int j=0;j< tmp.noOfCategories;j++)
			 {
				   if(resTm.containsKey(arr[j])==false)
					   resTm.put(arr[j], new TreeSet<String>());
				   
				   resTm.get(arr[j]).add(tmp.fileArray[j]);
//					
			 }
			 

		int key,maxKey=-1,count=0;
		TreeSet<String> resultSet;
		
			for (Map.Entry<Integer, TreeSet<String>> entry : resTm.entrySet())
			{
				
				key = entry.getKey();
				resultSet = entry.getValue();
				
				if(key > maxKey) maxKey = key;
				
				if( (maxKey-key)>2  ||  count>3)	break;
				
				for(String s: resultSet)
				{	count++;
				
					if(count > 3)   break;
					
					if( maxKey==0)
					{
						presLt.add("Other");
						break;
					}
					if(maxKey==key) 	presLt.add(s);
					else	sresLt.add(s);
					System.out.println(key + " wt. for " + s);
				}
				
//			    System.out.println(key + " wt. for " + entry.getValue());
			}
			finalResult.addAll(presLt);
			finalResult.add("F");
			finalResult.addAll(sresLt);
			
		return finalResult;			 
	 }
	
	 
	public List<String> createIndexTest(String fileName) throws Exception
	{
		String modelName = "svm";

		MergeAndClean_Main vf = new MergeAndClean_Main();
		vf.cleanTweetsTest(fileName,tmp.cleanedMergedFilesPath+modelName+".txt");
				
		System.out.println("going for "+ tmp.SVMIndexPath_Test +"indexTestFile");
		makeIndexObj.generateIndexSVM_Testing(tmp.cleanedMergedFilesPath+modelName+".txt", modelName, "indexTestFile");
		
		System.out.println("Going for classification");
		int[] results = lm.classifyMe("indexTestFile");
		
		System.out.println("Result  is : ");
		List<String> liResult = findClass(results);
		for (String s : liResult)
		    System.out.print(s+",");

		/*
		for(int z=0;z<results.length;z++)
		{
	    	System.out.println("Result of "+ (z+1) + " th query is :   " +tmp.fileArray[results[z]] );
	    }
	    */
		return liResult;
	}
	
	public void createModelFile() throws Exception
	{
		MakeModelSVM mm = new MakeModelSVM();
		
		for(int i=0;i<tmp.noOfCategories;i++)
		{
			for(int j=i+1;j<tmp.noOfCategories;j++)
			{
				mm.createModel(i,j);
			}
		}	
	}
	
	public List<String> process(String query) 
	{
//		String query=null;
		List<String> result = null;
		String file_name="test_query";
		BufferedWriter bw;
		
//		SVM_Main svmm = new SVM_Main();
//		svmm.preProcess();
		
		try {
		
			/*
			 // to read query from user
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			BufferedWriter bw;
			
			System.out.println("Enter Query = ");
			while((query=br.readLine()) != null)
			{
				if(query.equalsIgnoreCase("exit"))
				{
					System.out.println("Good Bye");
					break;
				}
				
				bw = new BufferedWriter(new FileWriter(new File(file_name)));
				bw.write(query);
				bw.close();
				
				svmm.createIndexTest(file_name);
				System.out.println("Enter Query = ");
			}
			
			br.close();
			*/
			
			bw = new BufferedWriter(new FileWriter(new File(file_name)));
			bw.write(query);
			bw.close();

			result = createIndexTest(file_name);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

/*	
	public static void main(String[] args) {
		SVM_Main svmm = new SVM_Main();
		svmm.preProcess();
		
		try {
//			svmm.createIndexTrain();
			svmm.createModelFile();
		
//			String result = svmm.createIndexTest("test_query");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		svmm.createModelFile();
	}
	*/
}
