package SVM;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import ConfSetUp.Configurations;
import SVM.utils.DataFileReader;
import SVM.ca.uwo.csd.ai.nlp.kernel.KernelManager;
import SVM.ca.uwo.csd.ai.nlp.kernel.LinearKernel;
import SVM.ca.uwo.csd.ai.nlp.libsvm.svm_model;
import SVM.ca.uwo.csd.ai.nlp.libsvm.ex.Instance;
import SVM.ca.uwo.csd.ai.nlp.libsvm.ex.SVMPredictor;

public class LoadModelSVM
{
	Configurations tmp;
	public int arr[][];
	static int[] classCount;
	int noOfModels;
	svm_model model[];

	public LoadModelSVM() 
	{
		tmp = Configurations.getInstance();
	}
	
	
	
	public void loadModel() throws ClassNotFoundException, IOException 
	{
		 KernelManager.setCustomKernel(new LinearKernel());
		 noOfModels = (tmp.noOfCategories * (tmp.noOfCategories-1))/2;
		 System.out.println("no of models loaded = " + noOfModels);
		 
		 model = new svm_model[noOfModels];
		
		int modelCount=0;
		
		for(int i=0;i< tmp.noOfCategories;i++)
		{
			for(int j=i+1;j< tmp.noOfCategories;j++)
			{
				 model[modelCount++]=SVMPredictor.loadModel(tmp.SVMModelPath_Train+"index_"+i+"_"+j+".model");
//				 System.out.println("Loading i="+i+ "j="+j+ "modelCount="+modelCount);
			}
		}		
	}
	
	public int[] classifyMe(String fileName) throws Exception
	{
		 Instance[] testingInstances = DataFileReader.readDataFile(tmp.SVMIndexPath_Test+fileName);
		 System.out.println("testingInstances.length = "+testingInstances.length);
		 
		 arr=new int[testingInstances.length][tmp.noOfCategories];
		 double[] predictions;
		 
		 for(int i=0;i<testingInstances.length;i++)
			 for(int j=0;j< tmp.noOfCategories;j++)
				 arr[i][j]=0;

		 int modelCount=0;
		 for(int i=0;i< tmp.noOfCategories;i++)
			{
				for(int j=i+1;j< tmp.noOfCategories;j++)
				{
//					System.out.println("predicting i="+i+ "j="+j+ "modelCount="+modelCount);
					 predictions = SVMPredictor.predict(testingInstances, model[modelCount++], true);
				     incrementClassValue(predictions,i,j);
				}
			}

		 	System.out.println("Array is : " + testingInstances.length);
			 for(int i=0;i<testingInstances.length;i++)
			 {	// System.out.println(testingInstances.toString());	
				 for(int j=0;j< tmp.noOfCategories;j++)
				 		System.out.print(arr[i][j]+" ");
			 System.out.println();
			 }
				
			
			findClass(testingInstances.length);
//			return classCount;
			return (testingInstances.length!=0) ? arr[0] : new int[tmp.noOfCategories];
	}
	
	
	 private void findClass(int length) throws IOException
	 {
		 BufferedWriter bf = new BufferedWriter(new FileWriter(new File("result")));
		 classCount=new int[length];
		/*
		 for(int i=0;i< tmp.noOfCategories;i++)
		 {
			 classCount[i]=0;
		 }
		 */
		 for(int i=0;i<length;i++)
		 {
			 int max=-1;
			 int index=-1;
			 for(int j=0;j< tmp.noOfCategories;j++)
			 {
				 if(max < arr[i][j])
				 {
					 max=arr[i][j];
					 index=j;
				 }
					 
			 }
			 classCount[i]=index;
			 bf.write(index+"\n");
		 }
		 bf.close();
	 }
	
    private void incrementClassValue(double[] predictions, int c1, int c2) 
    {
    	for(int i=0;i<predictions.length;i++)
    	{
    		if(predictions[i] == 1.0)
    		{
    			arr[i][c1]++;
    		}
    		else
    		{
    			arr[i][c2]++;
    		}
    	}
	}
    

}
