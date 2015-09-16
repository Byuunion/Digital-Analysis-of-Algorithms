import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;

/*
 * To referance any part of the 2d array use this
 * System.out.println(twoDArray.get(0).get(0));
 */
public class Sudoku_Brute_Force {

	public static int width;
	public static int height;
	public static int maxNum;
	public static ArrayList<ArrayList<Integer>> twoDArray = new ArrayList<ArrayList<Integer>>();
	public static ArrayList<Integer> solution = new ArrayList<Integer>();
	public static int zCounter = 0;
	public static int countLoop = 0;
	public static Timer time = new Timer();
	
	public static void main(String[] args) {
		
		time.start();
		readFile("test");
		solver();
		System.out.println("Finished");
		time.stop();
		System.out.println(time.getDuration());
	}

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
					// ignore line;
				} else {
					String[] temp = line.split(" ");
					if (counter == 0) {
						width = Integer.parseInt(temp[0]);
					} else if (counter == 1) {
						height = Integer.parseInt(temp[0]);
					} else {
						// create next row arraylist
						twoDArray.add(new ArrayList<Integer>());
						for (int i = 0; i < temp.length; i++) {
							twoDArray.get(rowCounter).add(i, Integer.parseInt(temp[i]));
							if (Integer.parseInt(temp[i]) == 0) {
								zCounter++;
							}
						}
						rowCounter++;
					}
					counter++;
				}
			}
			bufferReader.close();
			maxNum = width * height;
			// Custom 2d Array toString
			for (int i = 0; i < twoDArray.size(); i++) {
				String toString = "";
				for (int j = 0; j < twoDArray.get(i).size(); j++) {
					toString += twoDArray.get(i).get(j);
				}
				System.out.println(toString);
			}
		} catch (IOException ex) {
			System.out.println("Error: " + fileName);
		}
	}

	public static ArrayList<Integer> solver() {
		boolean go;
		int chChange;
		int repsCounter;

		for (int i = 0; i < zCounter; i++) { // zCounter counts 0
			solution.add(1);
		}
		chChange = solution.size() - 1; // the digit we are changing		
		repsCounter = 1;				// count the number of times the numbers are changed
		go = !(checker(solution));		// start checking first solution

		while (go == true) {			// if solution hasn't been found yet
			repsCounter++;				// increase repetitions

			if (repsCounter % (maxNum) == 0) { 									// if repetitions mod max value is 0 
					if (solution.get(chChange) == maxNum && chChange != 0) {	// and the value that is being changed is max then
						solution.set(chChange, 1); 								// reset the digit to 1
				}
				solution.set(solution.size() - 1, maxNum); 						// always set the last digit to the max value

			} else if (repsCounter % (maxNum) == 1) {							// if repetition mod max value is 1
				int finish = 0; // consecutive digits that have reached the maximum value									
				int l = solution.size() - 1;									
				while (l > chChange) {											// check all positions for max values
					if (solution.get(l) == maxNum) {							// if they are max value increment finish
						l--;													
						finish++;
					} else {													// else end loop
						l = 0;
					}
				}																// always increment the digit in front of the first max value
				solution.set(solution.size() - (finish + 1), solution.get(solution.size() - (finish + 1)) + 1);
				while (finish > 0) {											
					solution.set(solution.size() - finish, 1);					// set all max values to 1 
					finish--;
				}
			} else {															// any other results for mod
				solution.set(solution.size() - 1, (repsCounter % (maxNum)));	// simply increase last digit equal to result of mod
			}
			if (solution.get(chChange) == maxNum && chChange != 0) {
				chChange--;
			}
			go = !(checker(solution));											// check value created
		}
		return null;
	}

	public static boolean checker(ArrayList<Integer> x) {
		//System.out.println(x);
		//System.out.println(countLoop+1);
		if (countLoop == Math.pow(maxNum, solution.size()) - 1) {
			return true;
		}
		countLoop++;
		return false;
	}
}
