package NAIVE;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

import Merger.MergeAndClean_Main;
import TweetsRefine.TweetCleaner;

import ConfSetUp.Configurations;

public class Naive_Main 
{
	Configurations tmp;
	MakeIndexNaive makeIndexObj;
	ModelNaive wd;
	String arfString;
	
	public Naive_Main() 
	{
		tmp = Configurations.getInstance();
		makeIndexObj = new MakeIndexNaive();
		wd = new ModelNaive();
		arfString = null;
	}
	
	public void preProcess()
	{
		System.out.println("i am in preprocess");
		try {
			makeIndexObj.readUniqueWord();	
			arfString = makeIndexObj.getFixedArffAttributeOfNaive();
			wd.LoadModel();
			}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public void createIndexTrain() throws IOException
	{
		makeIndexObj.writeArffString(tmp.NaiveIndexPath_Train+"naive.arff",arfString);
		for(int i=0;i<tmp.noOfCategories;i++)
		{
			makeIndexObj.generateIndexNaive_Training(tmp.cleanedMergedFilesPath,tmp.fileArray[i]);
		}
	}
	
	public List<String> createIndexTest(String fileName) throws Exception
	{
		List<String> res = new ArrayList<String>();
		String modelName = "naive";

		MergeAndClean_Main vf = new MergeAndClean_Main();
		vf.cleanTweetsTest(fileName,tmp.cleanedMergedFilesPath+modelName+".txt");
				
		System.out.println("going for "+ tmp.NaiveIndexPath_Test +"indexTestFile");

		makeIndexObj.writeArffString(tmp.NaiveIndexPath_Test+"test.arff",arfString);
		makeIndexObj.generateIndexNaive_Testing(tmp.cleanedMergedFilesPath,modelName+".txt","test.arff");
		
		System.out.println("test query index generated");
				
		double percentage[];
    	percentage=wd.classifyMeNaive(tmp.NaiveIndexPath_Test+"test.arff");
    	
    	String ans=null;
    	   	
		for(int z=0;z<percentage.length;z++)
		{
//			System.out.println(percentage[z]);
			if(percentage[z]!=0.0)
			{
				System.out.println("Classified Class :" +tmp.fileArray[z]+"\n" );
				ans = tmp.fileArray[z];
				res.add(tmp.fileArray[z]);
//				break;
	    	}
		}
		if(res.isEmpty())
			res.add("Other");
		
		return res;
	}

	public void createModelFile() throws Exception
	{
		ModelNaive mm = new ModelNaive();
		mm.createModle();
	}
	
	/*
	public static void main(String[] args) {
	
		Naive_Main nm =new Naive_Main();
		nm.preProcess();
		
		try {
//			nm.createIndexTrain();
//			nm.createModelFile();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	*/
	
	
	public List<String> process(String query) 
	{
		List<String> result = null;
		String file_name="test_query";
		BufferedWriter bw;

		try {
			bw = new BufferedWriter(new FileWriter(new File(file_name)));
			bw.write(query);
			bw.close();

			result = createIndexTest(file_name);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
