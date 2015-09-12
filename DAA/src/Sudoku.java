import java.io.*;

public class Sudoku {

	public static int width;
	public static int height;
	public static int[][] array = new int[6][6];

	public static void main(String[] args) {
		readFile("D:/Eclipse_Workspace/DAA/test");
	}
	
	public static void readFile(String fileName){
		try {
		FileReader inputFile = new FileReader(fileName);
		BufferedReader bufferReader = new BufferedReader(inputFile);
		String line;
		int counter = 0;
		int rowCounter = 0;
		while ( (line = bufferReader.readLine()) != null ) {
			String[] temp = line.split(" ");
			if(temp[0] == "c"){
				//System.out.println("Test");
				continue;
		}
		if(counter == 0){
			
			width = Integer.parseInt(temp[0]);
			
		}
		
		else if(counter == 1){
			height = Integer.parseInt(temp[0]);
		}
		
		
		else{
			for(int i = 0; i < temp.length; i++){
				array[rowCounter][i] = Integer.parseInt(temp[i]);
			}
		rowCounter++;
			
		}
		
		counter++;
		
		}
				
		
		bufferReader.close();
		
		}
		catch (IOException ex){
	        System.out.println("Error: " + fileName);
	    }
		
		
		for(int i = 0; i < array.length; i++) {
			String toString = "";
			
			for (int j = 0; j < array[i].length; j++) {
				toString += array[i][j];
			}
			
			System.out.println(toString);

		}

	}
}