import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class Extractor {

	public static void main(String[] args){
		
		try{
		File f = new File("test.trace");
		
		
		FileReader fileReader = new FileReader(f);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		StringBuffer stringBuffer = new StringBuffer();
		String line;
		while ((line = bufferedReader.readLine()) != null) {
			if(line.startsWith("test.py"))
				System.out.println(line);
			
			
			
		}
		fileReader.close();
		
		
		}
		catch (IOException e){
			
		}
	}
}
