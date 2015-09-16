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
	public static ArrayList<ArrayList<Integer>> twoDArray = new ArrayList<ArrayList<Integer>>();
	public static ArrayList<Integer> solution = new ArrayList<Integer>();
	public static int zCounter = 0;
	public static int countLoop = 0; 
	public static void main(String[] args) {
		readFile("H:\\git\\Digital-Analysis-of-Algorithms\\DAA\\test");
		solver();
		
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
							if (Integer.parseInt(temp[i]) == 0){
								zCounter++;
							}
						}
						rowCounter++;
					}
					counter++;
				}
			}

			bufferReader.close();


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
	
	public static ArrayList<Integer> solver(){
		boolean go;
		for(int i = 0; i < zCounter; i++){
			solution.add(1);
		}
		//NEED TO BE RECURSIVE!!!
		int changeCh = solution.size() - 1; //placeholder
		
		go = !(checker(solution));
		
		while (go == true){		
			if(solution.get(changeCh) == width*height){ //if number at placeholder = max number change placeholder
				changeCh--;
			}
			
			else{						
				solution.set(changeCh, solution.get(changeCh) + 1);
			
			}
			go = !(checker(solution));
		}
		
		
		
		return null;
		}
			
	
	
	public static boolean checker(ArrayList<Integer> x){
		System.out.println(x);
		if(countLoop == 10){
			return true;
		}
		
		countLoop++;
		return false;
		
	}
	
	
}
