import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/*
 * To referance any part of the 2d array use this
 * System.out.println(twoDArray.get(0).get(0));
 */
public class Sudoku_Brute_Force {
	
	// (w x h)
	public static int width;
	public static int height;
	public static int maxNum;
	public static ArrayList<ArrayList<Integer>> twoDArray = new ArrayList<ArrayList<Integer>>();

	public static ArrayList<Integer> solution = new ArrayList<Integer>();
	public static int zCounter = 0;
	public static int countLoop = 0;
	public static Timer time = new Timer();
	
	public static void main(String[] args) {
		/*
		readFile("test2");
		System.out.println("Rows are good?: " + checkRow());
		System.out.println("Columns are good?: " + checkCol());
		System.out.println("Boxs are good?: " + checkBox());
		
		*/
		time.start();
		readFile("test4");
		solver();
		//System.out.println("Finished");
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
			System.out.println(twoDArray);
		} catch (IOException ex) {
			System.out.println("Error: " + fileName);
		}
	}

	public static ArrayList<Integer> solver() {
		boolean go;
		int chChange;
		int repsCounter;
		maxNum = height*width;
		ArrayList<ArrayList<Integer>> clone = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < zCounter; i++) { // zCounter counts 0
			solution.add(1);
		}
		chChange = solution.size() - 1; // the digit we are changing		
		repsCounter = 1;				// count the number of times the numbers are changed
		
		int solutionCounter = 0;
		for(int a = 0; a < clone.get(0).size(); a++){
			for(int j = 0; j < clone.get(0).size(); j++){
				if(clone.get(a).get(j) == 0){
					clone.get(a).set(j, solution.get(solutionCounter));
					solutionCounter++;
				}
			}
		}
		go = !(checker(clone));		// start checking first solution

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
			
			solutionCounter = 0;
			clone = twoDArray;
			for(int i = 0; i < clone.get(0).size(); i++){
				for(int j = 0; j < clone.get(0).size(); j++){
					if(clone.get(i).get(j) == 0){
						clone.get(i).set(j, solution.get(solutionCounter));
						solutionCounter++;
					}
				}
			}
			System.out.println(solution);
			go = !(checker(clone));											// check value created
		}
		return null;
	}

	public static boolean checker(ArrayList<ArrayList<Integer>> x) {
		//System.out.println();
		//System.out.println(countLoop+1);
		if(checkRow(x) && checkCol(x) && checkBox(x)){
			
			System.out.println("Solved");
			return true;
		}
		else if (countLoop == 3) { //Math.pow(maxNum, solution.size()) - 1
			System.out.println("Rows are good?: " + checkRow(x));
			System.out.println("Columns are good?: " + checkCol(x));
			System.out.println("Boxs are good?: " + checkBox(x));
			System.out.println("All Solutions Checked. Nothing Worked. UNSOLVABLE");
			return true;
		}
		
		countLoop++;
		return false;
	}
	
	// Checks rows top to bottom
		private static boolean checkRow(ArrayList<ArrayList<Integer>> x){
			// total number of rows
			int rows = x.size();
			
			// Check every row
			for (int i = 0; i < rows; i++) {
				Set<Integer> set = new HashSet<Integer>(x.get(i));
				if (set.size() != width * height){
					return false;
				}
			}
			return true;
		}
	
	// Checks columns left to right
	private static boolean checkCol(ArrayList<ArrayList<Integer>> x){
		// total number of columns
		int columns = x.get(0).size();
		
		// Check every col going to the right
		// i = column index
		for (int i = 0; i < columns; i++) {
			Set<Integer> set = new HashSet<Integer>();
			
			// Iterate each row downwards
			// j = row index
			for (int j = 0; j < x.size(); j++) {
				
			// populate hashset with each number for the columns
				set.add(x.get(j).get(i));
			}
			
			if (set.size() != width * height){
				return false;
			}
		}
		return true;
	}
	
	// Checks boxes from top left to bottom right (Left to right, down, left to right)
	private static boolean checkBox(ArrayList<ArrayList<Integer>> x){
		// i is index for a box's width from first columns to next box's first column
		for(int i = 0; i < x.get(0).size(); i += width){
			Set<Integer> set = new HashSet<Integer>();
			
			//popluate the set with numbers from the box
			//Iterates from the box's height i.e row downwards 
			for(int j = 0; j < height; j++){
				
				//add the the specified number of integers from the row
				for(int k = 0; k < width; k++){
					set.add(x.get(j).get(k));
				}
				
			}
			
			if (set.size() != width * height){
				return false;
			}
		}
		
		return true;
	}

}