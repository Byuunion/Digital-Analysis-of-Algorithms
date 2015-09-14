import java.io.*;

/*
 * Errors to fix: If file does not exist, fix 0 print outs
 */
public class Sudoku {

	public static int width;
	public static int height;
	public static int[][] array = new int[6][6];

	public static void main(String[] args) {
		readFile("H:/git/Digital-Analysis-of-Algorithms/DAA/test");
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
					/* ignore line */;
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
}