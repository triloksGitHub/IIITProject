package NAIVE;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instances;
import weka.core.OptionHandler;
import weka.core.Utils;
import weka.filters.Filter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import ConfSetUp.Configurations;


public class ModelNaive
{
	Configurations tmp= Configurations.getInstance();
  protected Classifier m_Classifier = null;
  protected Filter m_Filter = null;
  protected String m_TrainingFile = null;
  protected Instances m_Training = null;
  protected Evaluation m_Evaluation = null;
  protected double[] classCount=new double[tmp.noOfCategories];
  Classifier cls;
  
  public ModelNaive()
  {
    super();
  }

  public void setTraining(String name) throws Exception
  {
    m_TrainingFile = name;
    m_Training     = new Instances(new BufferedReader(new FileReader(m_TrainingFile)));
    m_Training.setClassIndex(m_Training.numAttributes() - 1);
  }
  
  public static String usage()
  {
    return
        "\nusage:\n  " + ModelNaive.class.getName() 
        + "  CLASSIFIER <classname> [options] \n"
        + "  FILTER <classname> [options]\n"
        + "  DATASET <trainingfile>\n\n"
        + "e.g., \n"
        + "  java -classpath \".:weka.jar\" WekaDemo \n"
        + "    CLASSIFIER weka.classifiers.trees.J48 -U \n"
        + "    FILTER weka.filters.unsupervised.instance.Randomize \n"
        + "    DATASET iris.arff\n";
  }
  
  public void setClassifier(String name, String[] options) throws Exception 
  {
	  m_Classifier =(Classifier)Utils.forName(Classifier.class,name,options);
  }
  
  public void setFilter(String name, String[] options) throws Exception 
  {
	    m_Filter = (Filter) Class.forName(name).newInstance();
	    if (m_Filter instanceof OptionHandler)
	      ((OptionHandler) m_Filter).setOptions(options);
  }
  public void createModle() throws Exception
  {
		System.out.println("model creation start");
	    Vector<String> classifierOptions = new Vector<String>();
	    Vector<String> filterOptions = new Vector<String>();
	    
	    String classifier = "weka.classifiers.bayes.NaiveBayes";
	    String filter = "weka.filters.unsupervised.instance.Randomize";
	    ModelNaive demo = new ModelNaive();
	    demo.setClassifier(classifier,(String[]) classifierOptions.toArray(new String[classifierOptions.size()]));
	    demo.setFilter(filter,(String[]) filterOptions.toArray(new String[filterOptions.size()]));
	    demo.setTraining("./naive/"+"naive.arff");
	    demo.m_Classifier.buildClassifier(demo.m_Training );
	    weka.core.SerializationHelper.write("./naive/"+"naive.model",demo.m_Classifier );
	    System.out.println("model creation end");
  }

  public void LoadModel() 
  {
	  try {
		cls = (Classifier) weka.core.SerializationHelper.read("./naive/"+"naive.model");
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	    
	  System.out.println("naive model file loaded");
  }
  
  public double[] classifyMeNaive(String srcFile) throws Exception
   {
	    
	    for(int i=0;i<tmp.noOfCategories;i++)
	    {
	    	classCount[i]=0.0;
	    }
		System.out.println("---Naive Step---1--");
	    Instances isTrainingSet = new Instances(new BufferedReader(new FileReader(srcFile)));
		System.out.println("---Naive Step---2--");
	    isTrainingSet.setClassIndex(isTrainingSet.numAttributes() - 1);
		System.out.println("---Naive Step---3--");
	    Evaluation eTest = new Evaluation(isTrainingSet);
//	    List<BufferedWriter> list=new ArrayList<BufferedWriter>();
/*	    
	    for(int i=0;i<tmp.noOfCategories;i++)
	    {
	    	BufferedWriter bw=new BufferedWriter(new FileWriter(new File("./naive/"+i)));
	    	list.add(bw);
	    }
	*/
	    System.out.println("Going to evaluate in model file");
	   double [] answers = eTest.evaluateModel(cls, isTrainingSet);
		System.out.println("---Naive Step---4--");
//	   int count=0;
	   
	   System.out.print("ansGot : ");
	    for(double answer :  answers)
	    {
	    	int ans = (int)answer;
	    	System.out.print(answer+" ");
	    	if(ans>=0 && ans<tmp.noOfCategories)
	    	{
	    		classCount[ans]++;
//	    		list.get(ans).write(count++ +" "+answer+"\n");
	    	}
	    	else
	    	{
	    		System.out.println("unexpected answer");
	    	}
	    }
	    
	    /*
	    for(int i=0;i<tmp.noOfCategories;i++)
	    {
	    	list.get(i).close();
	    }
	    list.clear();
	    */
	    return classCount;//
	    //return calculatePercentage();
	  }
   
   public double[] calculatePercentage()
   {
   	double[] pre = new double[tmp.noOfCategories];
   	int sum=0;
   	for(int i=0;i<tmp.noOfCategories;i++)
   	{
   		sum+=classCount[i];
   	}
   	for(int i=0;i<tmp.noOfCategories;i++)
   	{
   		pre[i]=(double)(classCount[i]*100)/sum;
   		DecimalFormat df = new DecimalFormat("#.##");
        pre[i] = Double.parseDouble(df.format(pre[i]));
   	}
   	return pre;
   }
  
}
