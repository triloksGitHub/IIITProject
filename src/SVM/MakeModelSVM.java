package SVM;

import java.io.IOException;

import ConfSetUp.Configurations;
import SVM.utils.DataFileReader;
import SVM.ca.uwo.csd.ai.nlp.kernel.KernelManager;
import SVM.ca.uwo.csd.ai.nlp.kernel.LinearKernel;
import SVM.ca.uwo.csd.ai.nlp.libsvm.svm_model;
import SVM.ca.uwo.csd.ai.nlp.libsvm.svm_parameter;
import SVM.ca.uwo.csd.ai.nlp.libsvm.ex.Instance;
import SVM.ca.uwo.csd.ai.nlp.libsvm.ex.SVMTrainer;


public class MakeModelSVM 
{
	Configurations tmp;
	
	public MakeModelSVM() 
	{
		tmp = Configurations.getInstance();
	}
	
	public void createModel(int i,int j)
	{
		
				Instance[] trainingInstances = null;
				try {
					trainingInstances = DataFileReader.readDataFile(tmp.SVMIndexPath_Train+"index_"+i+"_"+j);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				svm_parameter param = new svm_parameter();
				
				KernelManager.setCustomKernel(new LinearKernel());
		        
		        //Train the model
		        System.out.println("Training started...");
		        svm_model model = SVMTrainer.train(trainingInstances, param);
		        System.out.println("Training completed.");
		                
		        //Save the trained model
		        try {
					SVMTrainer.saveModel(model, tmp.SVMModelPath_Train+"index_"+i+"_"+j+".model");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} ////////////////////// change here a1a.model
			
	}
}
