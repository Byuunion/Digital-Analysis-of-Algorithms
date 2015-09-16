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
public class Sudoku {
	
	// (w x h)
	public static int width;
	public static int height;
	
	
	public static ArrayList<ArrayList<Integer>> twoDArray = new ArrayList<ArrayList<Integer>>();

	public static void main(String[] args) {
		readFile("test2");
		
		System.out.println("Rows are good?: " + checkRow());
		System.out.println("Columns are good?: " + checkCol());
		System.out.println("Boxs are good?: " + checkBox());
		
		if(checkRow() && checkCol() && checkBox()){
			System.out.println("Solved");
		}
		
		else{
			System.out.println("Bad");
		}
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
						// create next row arraylist
						twoDArray.add(new ArrayList<Integer>());
						for (int i = 0; i < temp.length; i++) {
							twoDArray.get(rowCounter).add(i, Integer.parseInt(temp[i]));
						}
						rowCounter++;
					}
					counter++;
				}
			}

			bufferReader.close();

			/*
			// Custom 2d Array toString
			for (int i = 0; i < twoDArray.size(); i++) {
				String toString = "";

				for (int j = 0; j < twoDArray.get(i).size(); j++) {
					toString += twoDArray.get(i).get(j);
				}

				System.out.println(toString);

			}
			*/

		} catch (IOException ex) {
			System.out.println("Error: " + fileName);
		}

	}
	
	// Checks rows top to bottom
	private static boolean checkRow(){
		// total number of rows
		int rows = twoDArray.size();
		
		// Check every row
		for (int i = 0; i < rows; i++) {

			for (int j = 0; j < twoDArray.get(i).size(); j++) {
				Set<Integer> set = new HashSet<Integer>(twoDArray.get(i));
				if (set.size() != width * height){
					return false;
				}
			}

		}
		return true;
	}
	
	// Checks columns left to right
	private static boolean checkCol(){
		// total number of columns
		int columns = twoDArray.get(0).size();
		
		// Check every col going to the right
		// i = column index
		for (int i = 0; i < columns; i++) {
			Set<Integer> set = new HashSet<Integer>();
			
			// Iterate each row downwards
			// j = row index
			for (int j = 0; j < twoDArray.size(); j++) {
				
			// populate hashset with each number for the columns
				set.add(twoDArray.get(j).get(i));
			}
			
			if (set.size() != width * height){
				return false;
			}
		}
		return true;
	}
	
	// Checks boxes from top left to bottom right (Left to right, down, left to right)
	private static boolean checkBox(){
		// i is index for a box's width from first columns to next box's first column
		for(int i = 0; i < twoDArray.get(0).size(); i += width){
			Set<Integer> set = new HashSet<Integer>();
			
			//popluate the set with numbers from the box
			//Iterates from the box's height i.e row downwards 
			for(int j = 0; j < height; j++){
				
				//add the the specified number of integers from the row
				for(int k = 0; k <= width; k++){
					set.add(twoDArray.get(j).get(k));
				}
				
			}
			
			if (set.size() != width * height){
				return false;
			}
		}
		
		return true;
	}

}