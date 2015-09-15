import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;

/*
 * To referance any part of the 2d array use this
 * System.out.println(twoDArray.get(0).get(0));
 */
public class Sudoku {

	public static int width;
	public static int height;
	public static ArrayList<ArrayList<Integer>> twoDArray = new ArrayList<ArrayList<Integer>>();

	public static void main(String[] args) {
		readFile("C:/Users/BenX/git/Digital_Analysis_of_Algorithms/DAA/test");
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

}