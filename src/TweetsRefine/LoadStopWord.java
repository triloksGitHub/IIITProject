package TweetsRefine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

class LoadStopWord {
	

	private static LoadStopWord instance = null;
	public HashSet<String> stopwords;
	
	protected LoadStopWord() {
		stopwords = new HashSet<String>();
		loadStopWords();
	}

	private void loadStopWords()
	{
		
		BufferedReader stopword_reader=null;
		try {
			stopword_reader = new BufferedReader(new FileReader(new File("stopwords")));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String stop_word;
		
		try {
			while((stop_word = stopword_reader.readLine())!= null)
			{
				stopwords.add(stop_word);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	public static LoadStopWord getInstance() 
	   {
	      if(instance == null) 
	      {
	         instance = new LoadStopWord();
	      }
	      return instance;
	   }

}
