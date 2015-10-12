import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Scanner;

/*
 * To referance any part of the 2d array use this
 * System.out.println(twoDArray.get(0).get(0));
 */
/**
 * Solves any given Sudoku puzzle of any size.
 * @author Gianluca Bastia, Arunn Chanthirakanthan & Benson Xu
 *
 */
public class Sudoku_Brute_Force {

	// (w x h)
	public static int width;
	public static int height;
	public static int maxNum;
	public static ArrayList<ArrayList<Integer>> twoDArray = new ArrayList<ArrayList<Integer>>();
	public static ArrayList<Pair> pairs = new ArrayList<Pair>();
	public static ArrayList<Integer> solution = new ArrayList<Integer>();
	public static int zCounter = 0;
	public static int countLoop = 0;
	public static Timer time = new Timer();

	/**
	 * This is the main functions that solves any given sudoku puzzle.
	 * @param args
	 */
	public static void main(String[] args) {
		/*
		 * readFile("test2"); System.out.println("Rows are good?: " +
		 * checkRow()); System.out.println("Columns are good?: " + checkCol());
		 * System.out.println("Boxs are good?: " + checkBox());
		 * 
		 *  6 5 1 3 4 2
		 *  4 3 2 1 6 5
		 *  2 4 6 5 1 3
		 *  5 1 3 4 2 6
		 *  1 2 5 6 3 4
		 *  3 6 4 2 5 1
		 *  
		 */
		
		twoDArray.add(new ArrayList<Integer>());
		twoDArray.add(new ArrayList<Integer>());
		twoDArray.add(new ArrayList<Integer>());
		twoDArray.add(new ArrayList<Integer>());
		twoDArray.add(new ArrayList<Integer>());
		twoDArray.add(new ArrayList<Integer>());
		
		twoDArray.get(0).add(0, 6);
		twoDArray.get(0).add(1, 5);
		twoDArray.get(0).add(2, 1);
		twoDArray.get(0).add(3, 3);
		twoDArray.get(0).add(4, 4);
		twoDArray.get(0).add(5, 2);
		
		twoDArray.get(1).add(0, 4);
		twoDArray.get(1).add(1, 3);
		twoDArray.get(1).add(2, 2);
		twoDArray.get(1).add(3, 1);
		twoDArray.get(1).add(4, 6);
		twoDArray.get(1).add(5, 5);
		
		twoDArray.get(2).add(0, 2);
		twoDArray.get(2).add(1, 4);
		twoDArray.get(2).add(2, 6);
		twoDArray.get(2).add(3, 5);
		twoDArray.get(2).add(4, 1);
		twoDArray.get(2).add(5, 3);
		
		twoDArray.get(3).add(0, 5);
		twoDArray.get(3).add(1, 1);
		twoDArray.get(3).add(2, 3);
		twoDArray.get(3).add(3, 4);
		twoDArray.get(3).add(4, 2);
		twoDArray.get(3).add(5, 6);
		
		twoDArray.get(4).add(0, 1);
		twoDArray.get(4).add(1, 2);
		twoDArray.get(4).add(2, 5);
		twoDArray.get(4).add(3, 6);
		twoDArray.get(4).add(4, 3);
		twoDArray.get(4).add(5, 4);
		
		twoDArray.get(5).add(0, 3);
		twoDArray.get(5).add(1, 6);
		twoDArray.get(5).add(2, 4);
		twoDArray.get(5).add(3, 2);
		twoDArray.get(5).add(4, 5);
		twoDArray.get(5).add(5, 1);
		
		width = 3;
		height = 2;
		maxNum = width * height;
							
		System.out.println("Sudoku Puzzle Input");
		
		for (int i = 0; i < twoDArray.size(); i++) {							// print the completed sudoku
			System.out.println(twoDArray.get(i) + " ");
		}
		
		/*
		System.out.println("\n ROW CHECKERS");
		
		int rows = twoDArray.size();											// total number of rows
		for (int i = 0; i < rows; i++) {								// Check every row
			Set<Integer> set = new HashSet<Integer>(twoDArray.get(i)); 
			System.out.println(twoDArray.get(i).toString());
			if (set.size() != width * height) {							// if hashmap does not contain all values return false
				System.out.println("Row Bad");
			}
			else System.out.println("Rows good");
		}
		
		System.out.println("\n COLUMN CHECKERS");
		
		int columns = twoDArray.get(0).size();	  								// total number of columns
		for (int i = 0; i < columns; i++) {								// Check every col going to the right i = column index
			
			for(int p = 0; p < twoDArray.size(); p++){
				System.out.println("[" + twoDArray.get(p).get(i) + "]");
			}
			
			Set<Integer> set = new HashSet<Integer>();
			for (int j = 0; j < twoDArray.size(); j++) {						//Iterate each row downwards j = row index
				if (!set.add(twoDArray.get(j).get(i))){
					System.out.println("Column Bad \n");
				}
			}
			if (set.size() == width * height) {							// if hashmap does not contain all values return false
				System.out.println("Column Good \n");
			}
		}
		
	*/
		System.out.println("BOX CHECKERS");
/*
		int wOffset;
		int hOffset = 0;
		int numboxes = height*width;
		
		for(int boxIndex = 0; boxIndex < numboxes; boxIndex++){
			wOffset = 0;
			
			
			// Apply offsets for the box check
			//System.out.println(3 % 2 + "ZZZZZZZZZZZZZZZZZZZZZZ");
			//System.out.println((boxIndex + 1) + " " + height + " " + (boxIndex + 1) % height);
			//System.out.println((boxIndex + 1 % height) == 1);
			
			if(((boxIndex + 1) % height) > 0){					// Whenever the box is at the start of the row
				//System.out.println("XXXXXXXXXXXXX");
				wOffset = 0;
				hOffset += height * ((boxIndex) / width);
				//System.out.println(height + " " + (boxIndex) / height);
				
			}
			else{														// Still in the same row
				//System.out.println("YYYYYYYYYYYYYYYYYY");
				wOffset += width;
				
				hOffset = height * (boxIndex) / width;

				//System.out.println("wOffset: " + wOffset + "\n");
			}
			
			Set<Integer> set = new HashSet<Integer>();
			String boxContents = "\n";
			for(int row = hOffset; row < hOffset + height; row++){
				for(int col = wOffset; col < wOffset + width; col++){
				//System.out.println(row + " " + col);
					boxContents += twoDArray.get(row).get(col)+",";
					set.add(twoDArray.get(row).get(col));
				}
				boxContents +="\n";
			}
			
			// Change this size check to just if(set.add(xxxxx))
			if(set.size()!= width * height){
				System.out.println(boxContents);
				System.out.println("Box bad");
			}
			else{
				System.out.println(boxContents);
				System.out.println("Box good");
			}
			
			

		}
		*/
			System.out.println("\nRows are good: " + checkRow(twoDArray));
			System.out.println("Columns are good: " + checkCol(twoDArray));
			System.out.println("Boxs are good: " + checkBox(twoDArray, 0, 0));
		/*
	
		Scanner userinput = new Scanner(System.in);
		System.out.print("Enter your file path: ");
		String filename = userinput.next();
		userinput.close();
		
		time.start();
		readFile(filename);
		
		// System.out.println("Finished");
		time.stop();
		//System.out.println("The Number of Zeros were : " + solution.size());
		//System.out.println("The Final Solution is: " + solution);
		System.out.println("It took " + time.getDuration() + " milliseconds to run this Sudoku program!");
		*/
		
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
							if(Integer.parseInt(temp[i]) > width * height){
								System.out.println("Sudoku puzzle invalid");		// Check if puzzle dimensions are valid
								bufferReader.close();
								return;
							}
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
	public static ArrayList<Integer> solver() {
		boolean go;
		int chChange;
		int repsCounter;
		maxNum = height * width;

		if(twoDArray.size() != width * height){
			System.out.println("Sudoku puzzle invalid");		// Check if puzzle dimensions are valid
			return null;
		}
		
		for (int i = 0; i < zCounter; i++) { 								// create solution array with 1s
			solution.add(1);												// zCounter is the number of 0s
		}

		System.out.print("Solving...");
		System.out.print("\r");

		chChange = solution.size() - 1; 									// the digit we are changing
		repsCounter = 1; 													// count the number of times the numbers are changed

		for (int i = 0; i < pairs.size(); i++) {							// insert  first solution created in the sudoku
			twoDArray.get(pairs.get(i).getX()).set(pairs.get(i).getY(), solution.get(i));
		}
		
		go = !(checker(twoDArray)); 										// check first solution

		while (go == true) { 												// while solution hasn't been found yet
			repsCounter++; 													// increase repetitions

			if (repsCounter % (maxNum) == 0) { 								// if repetitions mod max value is 0
				if (solution.get(chChange) == maxNum && chChange != 0) { 	// and the value that is being changed is max then
					solution.set(chChange, 1); 								// reset the digit to 1
				}
				solution.set(solution.size() - 1, maxNum); 					// always set the last digit to the max value
				
			} else if (repsCounter % (maxNum) == 1) { 						// if repetition mod maxValue is 1
																			// count consecutive digits at maxValue
				int finish = 0; 											// consecutive digits that have reached the maxium value							
				int l = solution.size() - 1;								// number of zeros
				while (l > chChange) { 										// check positions for max values
					if (solution.get(l) == maxNum) { 						// if they are max value increment count
														
						l--;
						finish++;
					} else { 
						l = 0;
					}
				} 
				// increment the digit in front of the first max value
				solution.set(solution.size() - (finish + 1), solution.get(solution.size() - (finish + 1)) + 1);
				while (finish > 0) {
					solution.set(solution.size() - finish, 1); 					// set all max values to 1																
					finish--;
				}
			} else { // any other results for mod
				solution.set(solution.size() - 1, (repsCounter % (maxNum))); 	// increase last digit equal to result of mod
			}
			if (solution.get(chChange) == maxNum && chChange != 0) {
				chChange--;
			}
			
			for (int i = 0; i < pairs.size(); i++) {							// insert the solution created in the sudoku
				twoDArray.get(pairs.get(i).getX()).set(pairs.get(i).getY(), solution.get(i));
			}
			//System.out.println(solution);
			go = !(checker(twoDArray)); 										// call checker on value created
		}
		return null;
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
		if (checkRow(x) && checkCol(x) && checkBox(x, 0, 0)) {					// if all checks are true sudoku is complete
			System.out.println("The Complete Sudoku: ");					
			for (int i = 0; i < x.size(); i++) {							// print the completed sudoku
				System.out.println(x.get(i) + " ");
			}
			// System.out.println("Solved");
			return true;
		} else if (countLoop == Math.pow(maxNum, solution.size()) - 1) { 	// if all solutions have been checked UNSOLVABLE																										
			//System.out.println("Rows are good?: " + checkRow(x));
			//System.out.println("Columns are good?: " + checkCol(x));
			//System.out.println("Boxs are good?: " + checkBox(x));
			System.out.println("All Solutions Checked. Nothing Worked. UNSOLVABLE");
			return true;
		}
		countLoop++;
		return false;
	}

	// Checks rows top to bottom
	/**
	 * This function checks the rows top to bottom
	 * @param x
	 * @return
	 */
	private static boolean checkRow(ArrayList<ArrayList<Integer>> x) {
		int rows = x.size();											// total number of rows
		for (int i = 0; i < rows; i++) {								// Check every row
			Set<Integer> set = new HashSet<Integer>(x.get(i));			// No iteration needed for initializing the hashset with the row
			if (set.size() != width * height) {							// if hashset does not contain all values return false
				return false;
			}
		}
		return true;
	}

	// Checks columns left to right
	/**
	 * This function checks the columns from left to right
	 * @param x
	 * @return Returns true if the columns fit the sudoku puzzle or false if the puzzle is incorrect.
	 */
	private static boolean checkCol(ArrayList<ArrayList<Integer>> x) {
		int columns = x.get(0).size();	  								// total number of columns
		for (int i = 0; i < columns; i++) {								// Check every col going to the right i = column index
			Set<Integer> set = new HashSet<Integer>();
			for (int j = 0; j < x.size(); j++) {						//Iterate each row downwards j = row index
				if (!set.add(x.get(j).get(i))){
					return false;
				}
			}
			/*if (set.size() != width * height) {							// if hashmap does not contain all values return false
				return false;
			}*/
		}
		return true; 
	}

	// Checks boxes from top left to bottom right (Left to right, down, left to right
	/**
	 * This function checks the boxes of the sudoku puzzle from the top left to bottom right depending on the size of the sudoku puzzle.
	 * @param x The sudoku puzzle. 
	 * @return Returns true if all the boxes in the sudoku puzzle are a valid solution to the puzzle and returns false if otherwise.
	 */
	private static boolean checkBox(ArrayList<ArrayList<Integer>> x, int r, int c) {

		
		Set<Integer> set = new HashSet<Integer>();
		
		
		for (int k = c; k <= c + width-1; k++){	
			for(int j = r; j <= r + height-1; j++){
				System.out.println("Column " + k);
				System.out.println("Row " + j);
				if(!(set.add(x.get(j).get(k)))){
					return false;
				}
		}	
	}
		
		
		
		if ((r == maxNum - height) && (c == maxNum - width))
		return true;
		
		else if (r < maxNum - height) // go to box below
		return checkBox(x, r + height, c);
		
		else //start next column of boxes
		return checkBox(x, 0, c + width);
	}
}
