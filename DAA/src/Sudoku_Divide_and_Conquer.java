import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
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
	public boolean start = true;
	/**
	 * This is the main functions that solves any given sudoku puzzle.
	 * @param args
	 */
	public static void main(String[] args) {
		
		time.start();
		readFile("test7");
		
		// System.out.println("Finished");
		time.stop();
		//System.out.println("The Number of Zeros were : " + solution.size());
		//System.out.println("The Final Solution is: " + solution);
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
			solver();
		} catch (IOException | NumberFormatException ex) {
			System.out.println("Error: " + fileName);
		}
	}
	
	// Function creates all possible solutions for sudoku. Each solution is checked before creating the next solution
	/**
	 * This function create all possible solutions for the sudoku puzzle, and each solutions is checked before creating the next solution
	 * 
	 * @return
	 */
	public static void solver() {
		if(!(possibleVals(pairs))){
			System.out.println("All Solutions Checked. Nothing Worked. UNSOLVABLE");
		}
		/*for(int k = 0; k < twoDArray.size(); k++){
			System.out.println(twoDArray.get(k));
		}*/
	}

	//compile solutions from checkers and determine next step
	/**
	 * This function checks 
	 * @param x
	 * @return
	 */
	public static boolean checker(ArrayList<ArrayList<Integer>> x) {
		// System.out.println();
		// System.out.println(countLoop+1);
		if (checkRows(x) && checkCols(x) && checkBoxes2(x, 0, 0)) {					// if all checks are true sudoku is complete
			System.out.println("The Complete Sudoku: ");					
			for (int i = 0; i < x.size(); i++) {							// print the completed sudoku
				System.out.println(x.get(i) + " ");
			}
			// System.out.println("Solved");
			return true;
		} //else if (countLoop == Math.pow(maxNum, solution.size()) - 1) { 	// if all solutions have been checked UNSOLVABLE																										
			//System.out.println("Rows are good?: " + checkRows(x));
			//System.out.println("Columns are good?: " + checkCols(x));
			//System.out.println("Boxs are good?: " + checkBoxes(x));
			
			//return true;
		//}
		//countLoop++;
		return false;
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
	
	private static boolean possibleVals(ArrayList<Pair> p){
		
		
		if(checker(twoDArray)){
			return true;
		}
		 
		if(p == null){
			return true;
		}
		
			for(int i = 0; i < p.size(); i++){
			
			// Base Cases where x y or box has only 1 possible solution
			ArrayList<Integer> x_values = checkRows(twoDArray,p.get(i).getX());
			ArrayList<Integer> y_values = checkCols(twoDArray,p.get(i).getY());
			ArrayList<Integer> box_values = validBoxVals(twoDArray, p.get(i).getX(), p.get(i).getY());

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
			
			//ArrayList<Integer> smallest_values = compareArraySize(compareArraySize(x_values, y_values),box_values);
	
				for(int k = 0; k < common_values.size(); k++){
					twoDArray.get(p.get(i).getX()).set(p.get(i).getY(), common_values.get(k));
				
					ArrayList<Pair> nextPairs = new ArrayList<Pair>();
					
					if(p.size() > 1){
						for(int j = 1; j < p.size(); j++){
							nextPairs.add(p.get(j));
						}
						if(possibleVals(nextPairs)){
							return true;
						}
					}
					else possibleVals(null);
				}
			
	}

		//System.out.println(x_values + " " + y_values + " " + box_values);
		return false;
	}
	
	/*private static ArrayList<Integer> compareArraySize(ArrayList<Integer> a, ArrayList<Integer> b){
		if(a.size() < b.size()){
			return a;
		}
		return b;
	}
	*/
	private static ArrayList<Integer> checkRows(ArrayList<ArrayList<Integer>> x, int row) {
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

	// Checks columns left to right using 
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
	
	private static ArrayList<Integer> checkCols(ArrayList<ArrayList<Integer>> x, int col) {
		int aa[] = new int[maxNum]; 
		ArrayList<Integer> list = new ArrayList<Integer>();

		for (int i = 0; i < x.size(); i++) {						//Iterate each row downwards j = row index
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
	private static boolean checkBoxes2(ArrayList<ArrayList<Integer>> x, int r, int c) {
	
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
		return checkBoxes2(x, r + height, c);
		
		else //start next column of boxes
		return checkBoxes2(x, 0, c + width);
	}
	
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
