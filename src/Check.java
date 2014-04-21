import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import NAIVE.Naive_Main;
import SVM.SVM_Main;
import SVM.ca.uwo.csd.ai.nlp.libsvm.svm;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;
public class Check extends JFrame implements ActionListener{
	
	
	JButton jb11,jb22,jb33;
	JLabel ans1,ans2,ans3,ans4;
	JButton jb1,jb2,jb3;
	JTextArea jt;
	public static HashMap<String,String>uniqueWords=new HashMap<String,String>();
	
	SVM_Main svmm;
	Naive_Main naivem;
	
	class Slice {
		   double value;
		   Color color;
		   public Slice(double value, Color color) {  
		      this.value = value;
		      this.color = color;
		   }
		@Override
		public String toString() {
			return "Slice [color=" + color + "]";
		}
		   
		}
		class MyComponent extends JComponent {
//			Vector<Slice> slicess = new Vector<Slice>(); 
		   Slice[] slices = new Slice[4];//{ new Slice(25, Color.black),
		  // new Slice(25, Color.green),
		   //new Slice(25, Color.yellow), new Slice(25, Color.red) };
		   double a=25;Color b=Color.black;
		   Slice MySlice;
		   public void change(Slice[] up)
		   {
			   slices=up;
		   }
		   
		   MyComponent() {
//			   slices = temp;
		   }
		   MyComponent(Slice []up)
		   {
			   slices=up;
		   }
		   public void paint(Graphics g) {
//			   slicess.size();
			   drawPie((Graphics2D) g, new Rectangle(new Dimension(400,450)), slices);
			   System.out.println("in paint"+Arrays.toString(slices));
			   
		   }
		   void drawPie(Graphics2D g, Rectangle area, Slice[] slices) {
		      double total = 0.0D;
		      for (int i = 0; i < slices.length; i++) {
		         total += slices[i].value;
		      }
		      double curValue = 0.0D;
		      int startAngle = 0;
		      for (int i = 0; i < slices.length; i++) {
		         startAngle = (int) (curValue * 360 / total);
		         int arcAngle = (int) (slices[i].value * 360 / total);
		         g.setColor(slices[i].color);
		         g.fillArc(area.x, area.y, area.width, area.height, 
		         startAngle, arcAngle);
		         curValue += slices[i].value;
		      }
		   }
		}
	
	
	Check()
	{
		
		
		System.out.println("---I am in constructor-----");
		JPanel jp1,jp2,jp3,jp4,jp5;
		jp1=new JPanel();
		jp2=new JPanel();
		jp3=new JPanel();
		jp4=new JPanel();
		jp5=new JPanel();
		
	
		System.out.println("----Going to preProcess SVM----");
		svmm = new SVM_Main();
		svmm.preProcess();
		System.out.println("svm preprocessing done");
		
		System.out.println("----Going to preProcess Naive----");
		naivem = new Naive_Main();
		naivem.preProcess();
		System.out.println("Naive preprocessing done");
		
		jb1=new JButton("SVM");
		jb1.addActionListener(this);
		
		jb2=new JButton("Naive");
		jb2.addActionListener(this);
		
		jb3=new JButton("Rule");
		jb3.addActionListener(this);
		
		JLabel jl21,jl22,jl1,jl2,jl3,jl4,cat,cat1,cat2,cat3,cat4,cat5,cat6,cat7,cat8,cat9,cat10,cat11;
		
		
		jl1=new JLabel("Accuracy",JLabel.CENTER);
		jl2=new JLabel("Enter Tweet Here");
		jl3=new JLabel("Algorithm",JLabel.CENTER);
		jl4=new JLabel("west");
//		jl21=new JLabel();
//		jl22=new JLabel();
		jp1.setLayout(new GridLayout(0,4));
		jp1.add(jl1);
		jp1.add(jb1);
		jp1.add(jb2);
		jp1.add(jb3);
		
		
		jb11=new JButton("SVM");
		jb11.addActionListener(this);
		
		
	       
		jb22=new JButton("Naive");
		jb22.addActionListener(this);
		
		jb33=new JButton("Rule");
		jb33.addActionListener(this);
		
		jp1.add(jl3);jp1.add(jb11);jp1.add(jb22);jp1.add(jb33);
		
		
		cat=new JLabel("Categories");
		cat1=new JLabel("1.Business");
		cat2=new JLabel("2.Education");
		cat3=new JLabel("3.Entertainment");
		cat4=new JLabel("4.Health");
		cat5=new JLabel("5.Law");
		cat6=new JLabel("6.Lifestyle");
		cat7=new JLabel("7.Nature");
		cat8=new JLabel("8.Places");
		cat9=new JLabel("9.Politics");
		cat10=new JLabel("10.Sports");
		cat11=new JLabel("11.Technology");
		
		jp2.setLayout(new GridLayout(0,1));
		jp2.setPreferredSize(new Dimension(50,10));
		jp2.setBounds(0, 10, 100, 200);
		jp2.add(cat);jp2.add(cat1);jp2.add(cat2);jp2.add(cat3);jp2.add(cat4);jp2.add(cat5);
		jp2.add(cat6);jp2.add(cat7);jp2.add(cat8);jp2.add(cat9);jp2.add(cat10);jp2.add(cat11);
	
		String[] description = { "Business", "Education", "Entertainment",
			      "Health", "Law", "Lifestyle", "Nature", "Places" ,"Politics","Sports","Technology"};
		JComboBox c = new JComboBox();
		for (int i = 0; i < 11; i++)
		      c.addItem(description[i]);
		    
		c.setSize(100, 200);
		GridLayout lay = new GridLayout(0,1);
		jp3.setLayout(lay);
		JButton jb=new JButton("Submit");
	
		
//		Check ch=new Check();
		
//		ch.setBackground(new Color(53, 56, 64));
		getContentPane().setBackground(Color.GRAY);
//		ch.setLayout(new GridLayout(0,2));
//		add(jl1,BorderLayout.NORTH);
//		jp2.setBounds(10, 10, 100, 100);
		JLabel cate=new JLabel("Categories",JLabel.CENTER);
		jt=new JTextArea(10,36);
		jp3.add(jt);
		/*
		JScrollPane scrollbar1 = 
				  new JScrollPane(
				    jt,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		*/
		ans1=new JLabel("Ans1. Business",JLabel.CENTER);
//		ans2=new JLabel("Ans2. Footabll",JLabel.CENTER);
//		ans3=new JLabel("Ans3. Cricket",JLabel.CENTER);
//		ans4=new JLabel("Ans3. vffffft",JLabel.CENTER);
		
		jp4.add(ans1);
//		jp4.add(ans2);jp4.add(ans3);
		jp3.add(jp4);
//		jp3.add(ans4);
//		jp3.add(jb);
		jp1.add(cate);
		jp1.add(c);
	
//		ch.add(jp1,BorderLayout.EAST);
		add(jp1,BorderLayout.NORTH);
		
		
//		JPanel frame = new JPanel();
//	      frame.add(new MyComponent());
		
//	      frame.setSize(300, 200);
//	      frame.setVisible(true);
//		add(frame,BorderLayout.NORTH);
//		ch.add(jl21,BorderLayout.SOUTH);
//		ch.add(jl22,BorderLayout.SOUTH);

		JLabel ac=new JLabel("SVM");
//		add(ac,BorderLayout.WEST);
//		ch.add(c,BorderLayout.NORTH);
//		add(frame,BorderLayout.WEST);
		add(jp3,BorderLayout.EAST);
		setBounds(250,100,800,600);
		setVisible(true);
		setResizable(false);
		
//		ChartPanel.fun();
		
	}
	
//	@Override
	
	public static void main(String args[])
	{

		String fileArray[]=new String[11];
		fileArray[0]="./Sorted/Business";
		fileArray[1]="./Sorted/Education";
		fileArray[2]="./Sorted/Entertainment";
		fileArray[3]="./Sorted/Health";
		fileArray[4]="./Sorted/Law";
		fileArray[5]="./Sorted/Lifestyle";
		fileArray[6]="./Sorted/Nature";
		fileArray[7]="./Sorted/Places";
		fileArray[8]="./Sorted/Politics";
		fileArray[9]="./Sorted/Sports";
		fileArray[10]="./Sorted/Technology";
		
		try
		{
			for(int i=0;i<11;i++)
			{
				FileReader f=new FileReader(fileArray[i]);
				BufferedReader bfr=new BufferedReader(f);
				String line=null;
				while((line=bfr.readLine())!=null)
				{
				//	System.out.println(line+"="+fileArray[i].substring(9,fileArray[i].length()));
					
					String splited[]=line.split(":");
					if(uniqueWords.containsKey(splited[0]))
					{
						String value1=uniqueWords.get(splited[0]);
						String brk[]=value1.split(":");
						if(Integer.parseInt(splited[1])>Integer.parseInt(brk[0]))
							uniqueWords.put(splited[0],splited[1]+":"+fileArray[i].substring(9,fileArray[i].length()));
					}
					else
						uniqueWords.put(splited[0],splited[1]+":"+fileArray[i].substring(9,fileArray[i].length()));
					
				}
				bfr.close();
			}
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
//			for (Map.Entry<String,String> entry : uniqueWords.entrySet()) {
			  //  System.out.println("key="+entry.getKey()+"value="+entry.getValue());
//			}

		}
		new Check();
	}
	
	JPanel jp=new JPanel();
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Slice[] sl = {new Slice(25, Color.black),
				   new Slice(25, Color.green),
				   new Slice(25, Color.yellow), new Slice(25, Color.red) };
		MyComponent m1=new MyComponent();
		 String in = jt.getText() ;
		 String out=null;
		 
		 /*
		 try{
		 FileWriter fw=new FileWriter(new File("test_query"));
		 BufferedWriter bfw=new BufferedWriter(fw);
		 bfw.write(in);
		 bfw.close();fw.close();
		 }
		 catch(Exception p)
		 {
			 p.printStackTrace();
		 }
		*/
		 
		if(e.getSource() == jb11){
			
			/*

			    sl[0]= new Slice(25, Color.black);
			   sl[1] = new Slice(25, Color.green);
			   sl[2] = new Slice(25, Color.yellow); 
			   sl[3] = new Slice(25, Color.red) ;
			  */ 
			   
//			 jp.add(m1);
//			 jp.repaint();
//			 JLabel l=new JLabel("checking");
//			 jp.setSize(400, 400);
//			 jp.add(l);
//			m1.setVisible(true);
//			 m1.change(sl);
			 
			  //add(m1);
//			  remove(m1);
//			 this.setVisible(false);
//			 this.setVisible(true);
//			 add(jp,BorderLayout.CENTER);
			
			java.util.List<String> svmOut=svmm.process(in);
			StringBuilder sb_svmout = new StringBuilder();
			int plistCount = 0;
			sb_svmout.append("Tweet category : ");
			for (String s : svmOut)
			{plistCount++;
				if(s.equals("F")==true)
				{
					if(plistCount!=svmOut.size())
						sb_svmout.append("\n\n,,,Other Possibilities : ");
				}
				else
					sb_svmout.append(s+",");
			}
			out = sb_svmout.toString();
//			    in.setText("") ;
			ans1.setForeground(Color.red);
            ans1.setText(out);
		}
		if(e.getSource() == jb22){
			/*
		    sl[0]= new Slice(25, Color.CYAN);
		   sl[1] = new Slice(25, Color.magenta);
		   sl[2] = new Slice(25, Color.yellow); 
		   sl[3] = new Slice(25, Color.red) ;
		   */
			java.util.List<String> naiveOut=naivem.process(in);
			StringBuilder sb_naiveout = new StringBuilder();
		
			sb_naiveout.append("Tweet category : ");
			
			for (String s : naiveOut)
			{
					sb_naiveout.append(s+",");
			}
			out = sb_naiveout.toString();
		   
//		   out=naivem.process(in);
//		    in.setText("") ;
		ans1.setForeground(Color.red);
       ans1.setText(out);
		   
		   
//			 Slice[] sl = { new Slice(15, Color.CYAN),
//					   new Slice(5, Color.magenta),
//					   new Slice(45, Color.yellow), new Slice(25, Color.red) };
//		  System.out.println("Removing componet");
		   //removeAll();
		 // MyComponent m2=new MyComponent(sl);
//		  repaint();
       /*
		  m1.change(sl);
		  repaint();
		  add(m1);
//		   m1.setBackground(Color.blue);
		//   m2.setSize(500, 700);
		  // add(m2);
			ans2.setForeground(Color.green);
            ans2.setText("2");
            */
		}
		if(e.getSource() == jb33){
			 out=QueryProcess.process(in);
//			    in.setText("") ;
			ans1.setForeground(Color.red);
         ans1.setText(out);
//			ans3.setForeground(Color.green);
//            ans3.setText("3");
		}
		if(e.getSource() == jb1){
			BarChart.chart();
			ans1.setForeground(Color.green);
            ans1.setText("1");
		}
		if(e.getSource() == jb2){
			ans2.setForeground(Color.green);
            ans2.setText("2");
		}
		if(e.getSource() == jb3){
			ans3.setForeground(Color.green);
            ans3.setText("3");
		}
		
		
	}
	
	

}
