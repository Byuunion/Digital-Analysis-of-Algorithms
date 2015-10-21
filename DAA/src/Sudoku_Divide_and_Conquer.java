import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;



/*
 * To referance any part of the 2d array use this
 * System.out.println(twoDArray.get(0).get(0));
 */
/**
 * Solves any given Sudoku puzzle of any size.
 * @author Gianluca Bastia, Arunn Chanthirakanthan & Benson Xu
 *
 */
public class Sudoku_Divide_and_Conquer {

	public static int width;
	public static int height;
	public static int maxNum;
	public static ArrayList<ArrayList<Integer>> twoDArray = new ArrayList<ArrayList<Integer>>();
	public static ArrayList<Pair> pairs = new ArrayList<Pair>();
	public static ArrayList<Integer> solution = new ArrayList<Integer>();
	public static int zCounter = 0;
	public static int countLoop = 0;
	public static Timer time = new Timer();
	public static int go;
	public static HashMap<Pair, ArrayList<Integer>> possibleVals = new HashMap<Pair, ArrayList<Integer>>();
	
	/**
	 * This is the main functions that solves any given sudoku puzzle.
	 * @param args
	 */
	public static void main(String[] args) {		
		time.start();
		readFile(args[0]);
		time.stop();
		System.out.println("It took " + time.getDuration() + " milliseconds to run this Sudoku program!");
	}
	
	/**
	 * This function reads in the file input and saves the sudoku in a two dimensional ArrayList
	 * @param fileName The sudoku file that will be entered and used.  
	 */
	public static void readFile(String fileName) {
		try {
			FileReader inputFile = new FileReader(fileName);
			BufferedReader bufferReader = new BufferedReader(inputFile);
			String line;
			int counter = 0;
			while ((line = bufferReader.readLine()) != null) {					// while not EOF

				String temp2 = line.trim();										
				if (temp2.isEmpty() || temp2.startsWith("c")) {					// Ignore any empty lines or comment lines
					// ignore line;
				} else {														
					String[] temp = line.split(" ");
					if (counter == 0) {											// First line after comments is width of sudoku
						if(temp.length != 1){									// Checks if no width exists
							bufferReader.close();
							return;							
						}
						width = Integer.parseInt(temp[0]);						
					} else if (counter == 1) {									// Second line after comments is length of sudoku
						if(temp.length != 1 ){									// Checks if no height exists
							bufferReader.close();
							return;							
						}
						height = Integer.parseInt(temp[0]);
					} else {													// All lines below are the rows of the sudoku
						twoDArray.add(new ArrayList<Integer>());				// Create the ArrayList for each row
						
						if(temp.length != width * height){
							System.out.println("Sudoku puzzle invalid");		// Check if puzzle dimensions are valid
							bufferReader.close();
							return;
						}
						
						for (int i = 0; i < temp.length; i++) {
							twoDArray.get(counter - 2).add(i, Integer.parseInt(temp[i]));
							if (Integer.parseInt(temp[i]) == 0) {				// if the value in the row is a 0
								pairs.add(zCounter, new Pair(counter - 2, i));	//save the coordinates of the 0 for later replacement
								zCounter++;										// keep count of 0s
							}
						}
					}
					counter++;													// keep count of lines after comments
				}	
			}
			maxNum = height * width;
			bufferReader.close();
			
			if(checker(twoDArray)){
				return;
			}
			
			if(solver()){
				return;
			}
			
			else
				System.out.println("Sudoku is UNSOLVABLE");
			
			
		} catch (IOException | NumberFormatException ex) {
			System.out.println("Error reading: " + fileName);
		}
	}
	
	/**
	 * This function will solve the puzzle by gathering all possible values of the cells and trying one value and
	 * 			moving on to the next cell. If there is an error along the way the function will backtrack and
	 * 			try another value for the cell.
	 * @return	False is the sudoku with current values is unsolvable
	 */
	public static boolean solver() {
		if(checker(twoDArray)){
			return true;
		}
		
		if(!(getPossibleVals())){
			return false;
		}
		
		Pair p = new Pair(0,0);
		ArrayList<Integer> temp = new ArrayList<Integer>();
		ArrayList<Integer> smallest = new ArrayList<Integer>();
		
		for(Map.Entry<Pair, ArrayList<Integer>> x : possibleVals.entrySet()){
			Pair p1 = x.getKey();
			if(twoDArray.get(p1.getX()).get(p1.getY()) != 0){
				continue;
			}
			temp = x.getValue();
			
			if((temp.size() < smallest.size()) ||(smallest.size() == 0)){
				smallest = temp;
				p = x.getKey();
			}
		}
		
		for(int y : smallest){
			twoDArray.get(p.getX()).set(p.getY(), y);
			if(solver()){
				return true;
			}
			twoDArray.get(p.getX()).set(p.getY(), 0);
		}
		return false;
	}
	
	
	/**
	 * This function calls the individual box row and column checkers
	 * @param x
	 * @return if all checkers return true the sudoku is complete
	 */
	public static boolean checker(ArrayList<ArrayList<Integer>> x) {		
		if (checkRows(x) && checkCols(x) && checkBoxes(x, 0, 0)) {					// if all checks are true sudoku is complete			
			System.out.println("The Complete Sudoku: ");					
			for (int i = 0; i < x.size(); i++) {							// print the completed sudoku
				System.out.println(x.get(i) + " ");
			}
			return true;
		} 		
		return false;
	}

	/**
	 * This function populates a hashset using the coordinates of the cell as the key and an array of possible solutions
	 * 			The possible solutions are only added if they are allowed in the box row and column of the cell
	 * 
	 * @return will return false if no solution is found for the cell, making it unsolvable
	 */
	private static boolean getPossibleVals(){
		go = 0;
		
		if (checker(twoDArray)){
			return true;
		}		
		for (Pair p : pairs){		
			if(twoDArray.get(p.getX()).get(p.getY()) != 0){
				continue;
			}
			ArrayList<Integer> x_values = rowVals(twoDArray,p.getX());
			ArrayList<Integer> y_values = colVals(twoDArray,p.getY());
			ArrayList<Integer> box_values = validBoxVals(twoDArray, p.getX(), p.getY());

			if(x_values.size() == 0 || y_values.size() == 0 || box_values.size() == 0){
				return false;
			}
			
			ArrayList<Integer> common_values = new ArrayList<Integer>();
			int aa[] = new int[maxNum];

			for(int x = 0; x < x_values.size(); x++){
				aa[x_values.get(x) - 1] += 1;
			}
			
			for(int y = 0; y < y_values.size(); y++){
				aa[y_values.get(y) - 1] += 1;
			}
			
			for(int box = 0; box < box_values.size(); box++){
				aa[box_values.get(box) - 1] += 1;
			}
				
			for(int k = 0; k < aa.length; k++){
				if(aa[k] == 3){
					common_values.add(k + 1);
				}
			}
			
			if (common_values.size() == 0){
				return false;
			}
			
			else {
				possibleVals.put(p,common_values);
			}
		}
		
		return true;
	}
	
	/**
	 * This function checks the rows top to bottom utilizing an array checker instead of hashset
	 * @param x
	 * @return
	 */
	private static boolean checkRows(ArrayList<ArrayList<Integer>> x) {
		int aa[] = new int[maxNum]; 
		int rows = x.size();											// total number of rows
		for (int i = 0; i < rows; i++) {								// Check every row			
			for(int j = 0; j < x.get(i).size(); j++){
				if(x.get(i).get(j) > 0){
					if((aa[x.get(i).get(j) - 1] += 1) > 1){
						return false;
					}
				}
			}			
			for(int k = 0; k < aa.length; k++){
				if(!(aa[k] == 1)){
					return false;
				}
			}
			Arrays.fill(aa, 0);
		}
		return true;
	}
	
	/**
	 * This function will check only the specified row
	 * @param x		the 2D arrayList that contains the sudoku
	 * @param row	the row to which the cell belongs
	 * @return
	 */
	private static ArrayList<Integer> rowVals(ArrayList<ArrayList<Integer>> x, int row) {
		int aa[] = new int[maxNum]; 
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		for(int i = 0; i < x.get(row).size(); i++){
			if(x.get(row).get(i) > 0){									// Only add non-zeros to the array
				aa[x.get(row).get(i) - 1] += 1;							// Adds the non blank values to the array
			}
		}
		for(int k = 0; k < aa.length; k++){
			if(!(aa[k] == 1)){
				list.add(k + 1);
			}
		}
		
		return list;
	}

	/**
	 * This function checks the columns from left to right and checks for duplicates using Hashsets
	 * @param x
	 * @return Returns true if the columns fit the sudoku puzzle or false if the puzzle is incorrect.
	 */
	private static boolean checkCols(ArrayList<ArrayList<Integer>> x) {
		int columns = height * width;	  								// total number of columns
		Set<Integer> set = new HashSet<Integer>();
		for (int i = 0; i < columns; i++) {								// Check every col going to the right i = column index
			for (int j = 0; j < x.size(); j++) {						//Iterate each row downwards j = row index
				if(!(set.add(x.get(j).get(i)))){
					return false;
				}
			}
			set.clear();
		}
		return true; 
	}
	
	/**
	 * This function gives the possible values for the specified column that are allowed in the cell
	 * @param x		The arrayList containing the Sudoku
	 * @param col	The column to which the cell being checked belongs
	 * @return		The list containing all possible values for the cell
	 */	
	private static ArrayList<Integer> colVals(ArrayList<ArrayList<Integer>> x, int col) {
		int aa[] = new int[maxNum]; 
		ArrayList<Integer> list = new ArrayList<Integer>();

		for (int i = 0; i < x.size(); i++) {							//Iterate each row downwards j = row index
			if(x.get(i).get(col) > 0){									// Only add non-zeros to the array
				aa[x.get(i).get(col) - 1] += 1;							// Adds the non blank values to the array
			}
		}
		
		for(int k = 0; k < aa.length; k++){
			if(!(aa[k] == 1)){
				list.add(k + 1);
			}
		}
		
		return list; 
	}

	// Checks boxes from top left to bottom right (Left to right, down, left to right
	/**
	 * This function checks the boxes of the sudoku puzzle from the top left to bottom right depending 
	 * on the size of the sudoku puzzle. and checks for duplicates using Hashsets
	 * @param x The sudoku puzzle. 
	 * @return Returns true if all the boxes in the sudoku puzzle are a valid solution to the puzzle and returns false if otherwise.
	 */
	private static boolean checkBoxes(ArrayList<ArrayList<Integer>> x, int r, int c) {
	
		Set<Integer> set = new HashSet<Integer>();
		
		for (int k = c; k <= c + width-1; k++){	
			for(int j = r; j <= r + height-1; j++){
				if(!(set.add(x.get(j).get(k)))){
					return false;
				}
			}	
		}
		set.clear();
		
		
		if ((r == maxNum - height) && (c == maxNum - width))
		return true;
		
		else if (r < maxNum - height) // go to box below
		return checkBoxes(x, r + height, c);
		
		else //start next column of boxes
		return checkBoxes(x, 0, c + width);
	}
	
	/**
	 * This function gives the possible values for the specified box that are allowed in the cell
	 * @param x		The 2d ArrayList containing the Sudoku
	 * @param r		The row coordinate of the cell
	 * @param c		The column coordinate of the cell
	 * @return		The list of possible values for the cell
	 */
	private static ArrayList<Integer> validBoxVals(ArrayList<ArrayList<Integer>> x, int r, int c) {
		int aa[] = new int[maxNum]; 
		int x_cord = (r / height) * height;
		int y_cord = (c / width) * width;
		
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		for (int k = y_cord; k <= y_cord + width-1; k++){	
			for(int j = x_cord; j <= x_cord + height-1; j++){
				if(x.get(j).get(k) > 0){								
					aa[x.get(j).get(k) - 1] += 1;							
				}			
			}			
		}
		for(int i = 0; i < aa.length; i++){
			if(!(aa[i] == 1)){
				list.add(i + 1);
			}
		}
		return list;
	}
}