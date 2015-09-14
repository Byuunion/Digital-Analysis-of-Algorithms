import java.io.*;
import java.util.ArrayList;
import java.util.List;

/*
 * Errors to fix: If file does not exist, fix 0 print outs
 */
public class Sudoku {

	public static int width;
	public static int height;
	
	

	//public static int[][] array = new int[6][6];

	public static void main(String[] args) {
		// 2d arraylist of ints growing separately

		ArrayList<ArrayList<Integer>> twoDArray = new ArrayList<ArrayList<Integer>>();
		
		// Adds 2 new arraylists in the arraylist
		for(int i = 0; i < 2; i++){
		  twoDArray.add(new ArrayList<Integer>());
		}
		
		
		twoDArray.get(0).add(0, 1);
		twoDArray.get(0).add(1, 2);
		twoDArray.get(0).add(2 ,3);
		twoDArray.get(1).add(0, 4);
		twoDArray.get(1).add(1, 5);
		twoDArray.get(1).add(2, 6);

		System.out.println(twoDArray.get(0));
		System.out.println(twoDArray.get(1));
		
		//int a = twoDArray.get(2).get(1);
		
		/*
		System.out.println(twoDArray.get(0).size());
		for (int i = 0; i < twoDArray.size(); i++) {
			String toString = "";

			for (int j = 0; j < twoDArray.get(i).size(); j++) {
				toString += twoDArray.get(i).get(j);
			}

			System.out.println(toString);

		}
		
		// For storing an unknown sized employee list
		ArrayList<Integer> whatever = new ArrayList<Integer>();
		
		String[] temp = {"1", "2", "3" , "4"};
		String[] temp2 = {"5", "5", "6" , "8"};
		twoDArrayList.add(0, whatever);
		twoDArrayList.add(1, whatever);
		
		for (int i = 0; i < temp.length; i++) {
			
			
			twoDArrayList.get(0).add(i, Integer.parseInt(temp[i]));
			
		}
		
		for (int i = 0; i < temp2.length; i++) {
			
			
			
			twoDArrayList.get(1).set(i, Integer.parseInt(temp2[i]));
		}
		
		System.out.println(twoDArrayList.get(0).get(0));
		
		for (int i = 0; i < twoDArrayList.size(); i++) {
			String toString = "";

			for (int j = 0; j < twoDArrayList.get(i).size(); j++) {
				toString += twoDArrayList.get(i).get(j);
			}

			System.out.println(toString);

		}
		
		//readFile("H:/git/Digital-Analysis-of-Algorithms/DAA/test");
		 */
	}

	/*
	public static void readFile(String fileName) {
		try {
			FileReader inputFile = new FileReader(fileName);
			BufferedReader bufferReader = new BufferedReader(inputFile);
			String line;
			int counter = 0;
			int rowCounter = 0;
			while ((line = bufferReader.readLine()) != null) {

				String temp2 = line.trim();

				if (temp2.isEmpty() || temp2.startsWith("c")) {
					//ignore line;
				}

				else {

					String[] temp = line.split(" ");

					if (counter == 0) {

						width = Integer.parseInt(temp[0]);

					}

					else if (counter == 1) {
						height = Integer.parseInt(temp[0]);
					}

					else {
						for (int i = 0; i < temp.length; i++) {
							array[rowCounter][i] = Integer.parseInt(temp[i]);
						}
						rowCounter++;
					}
				}

				counter++;

			}

			bufferReader.close();
			for (int i = 0; i < array.length; i++) {
				String toString = "";

				for (int j = 0; j < array[i].length; j++) {
					toString += array[i][j];
				}

				System.out.println(toString);

			}

		} catch (IOException ex) {
			System.out.println("Error: " + fileName);
		}

	}
	*/
}