package core;

import java.util.Scanner;

public class CLI {
	// Internal variable
	private static FileParser ioFile = new FileParser();
	private static Scanner userInput = new Scanner(System.in);
	private static boolean tempBoolean = false;
	private static Double tempDouble = 0.0;
	private static Matrix tempMatrix;
	private static int tempInt = 0;

	// Commonly used method
	private static String stringInput() {
		String tempString = "";
		System.out.print(">> ");
		tempString = userInput.nextLine();
		return tempString;
	}


	private static Matrix matrixInput() {
		// Input type Interface
		String tempString = "", stringMemory = "";
		System.out.println("\nInput matriks");
		System.out.println("1. File");
		System.out.println("2. Keyboard");
		while (true) {
			tempString = CLI.stringInput();
			// File Input
			if (tempString.equals("1")) {
				System.out.println("Masukan nama file (termasuk ekstensi)");
				tempString = CLI.stringInput();
				if (!ioFile.readFile(tempString)) {
					stringMemory = ioFile.stringRead();
					ioFile.closeFile();
					return Matrix.stringToMatrix(stringMemory);
				}
				else
					System.out.println("File tidak ditemukan");
			}

			// String Input
			else if (tempString.equals("2")) {
				// Size Row
				while (true) {
					System.out.print("Ukuran Baris : ");
					tempString = userInput.nextLine();
					try {
						tempInt = Integer.parseInt(tempString);
						break;
					}
					catch (NumberFormatException nfe) {
						System.out.println("Masukan tidak diketahui");
					}
				}
				// User matrix element input
				tempString = ""; // Flush current tempString
				String tempConcat = "";
				for (int i = 0 ; i < tempInt ; i++) {
					System.out.print("Baris ke-" + Integer.toString(i+1) + " : ");
					tempConcat = userInput.nextLine().trim();
					tempString = tempString + (tempConcat + "\n");
				}

				// Internal parse, no regex :<
				tempBoolean = true;
				for (int i = 0 ; i < tempString.length() ; i++)
					if (!Character.toString(tempString.charAt(i)).matches("[0-9]|\\.|\n| "))
						tempBoolean = false;

				// Returning branch
				if (tempBoolean)
					return Matrix.stringToMatrix(tempString);
				else
					return new Matrix(0,0);
			}

			// Invalid Input
			else
				System.out.println("Masukan tidak diketahui");
		}
	}

	private static void dataWrite(String stream) {
		String tempString = "";
		System.out.print("Nama file : ");
		tempString = userInput.nextLine();
		ioFile.writeFile(tempString);
		ioFile.stringWrite(stream);
		ioFile.closeFile();
	}

	// Menu Method
	private static void determinantMenu() {
		// Determinant interface
		String tempString = "", stringMemory = "";
		System.out.println("\nDeterminan");
		System.out.println("1. Metode Kofaktor");
		System.out.println("2. Metode Reduksi Baris");
		while (true) {
			tempString = CLI.stringInput();
			if (tempString.equals("1") || tempString.equals("2")) {
				stringMemory = tempString;
				tempMatrix = CLI.matrixInput();
				break;
			}
			else
				System.out.println("Masukkan tidak diketahui");
		}
		// Printing matrix
		// tempMatrix.printMatrix();
		// System.out.println(Integer.toString(tempMatrix.getRow()) + " " + Integer.toString(tempMatrix.getColumn()));
		if (tempMatrix.getRow() < 11) {
			System.out.println("Matriks masukkan");
			tempMatrix.printMatrix();
		}
		// Deteminant calculation
		if (stringMemory.equals("1"))
			tempDouble = tempMatrix.cofactorDet();
		else if (stringMemory.equals("2"))
			tempDouble = tempMatrix.reducedRowDet();
		tempMatrix.printMatrix(); // very weird bug, after cofactorDet called, it causing tempmatrix to NaN

		System.out.println("Determinan : " + Double.toString(tempDouble));
		System.out.println("Simpan hasil dalam file? (y/n)");
		tempString = CLI.stringInput();

		if (tempString.equals("y") || tempString.equals("Y"))
			dataWrite(Matrix.matrixToString(tempMatrix) + "\nDeterminan : " + Double.toString(tempDouble));
	}



	// Main Method
	public static void main(String args[]) {
		String tempString = "";
		// Main menu loop
		while (true) {
			// Main Menu interface
			System.out.println("MENU");
			System.out.println("1. Sistem Persamaan Linier");
			System.out.println("2. Determinan");
			System.out.println("3. Matriks balikan");
			System.out.println("4. Interpolasi Polinom");
			System.out.println("5. Regresi linier berganda");
			System.out.println("6. Keluar");

			// Input
			tempString = CLI.stringInput();

			// Test case
			if (tempString.equals("1"))
				System.out.println("TBA");
			else if (tempString.equals("2"))
				CLI.determinantMenu();
			else if (tempString.equals("3"))
				System.out.println("TBA");
			else if (tempString.equals("4"))
				System.out.println("TBA");
			else if (tempString.equals("5"))
				System.out.println("TBA");
			else if(tempString.equals("6"))
				break;
			else
				System.out.println("Masukan tidak diketahui");
			System.out.println("");
		}

		// Exit sequence
		userInput.close();


		// ---------------- testing purpose ----------------
		// det
//		Matrix kek = new Matrix(2,2);
//		for (int i = 0 ; i < 2 ; i++)
//			for (int j = 0 ; j < 2 ; j++)
//				kek.matrix[i][j] = input.nextInt();
//
//		System.out.println(Double.toString(kek.reducedRowDet()));

		// file parser
//		boolean exceptionRaised = false;
//		System.out.println("TOPKEK");
//		FileParser readInput = new FileParser();
//		String fn = input.nextLine();
//		exceptionRaised = readInput.writeFile(fn);
//		if (!exceptionRaised) {
//			readInput.stringWrite(input.nextLine());
//			readInput.closeFile();
//			readInput.readFile(fn);
//			System.out.print(readInput.charStringRead());

//		}
		// raw string
		System.out.println("Raw string file");
		FileParser read = new FileParser();
		read.readFile("anotherone.txt");
		String lul = read.stringRead();//.replaceAll("\\d","1");//.replaceAll("\\n", "");
		System.out.print(lul);
		System.out.println();
		Matrix tp = Matrix.stringToMatrix(lul);
//		for (int i = 0 ; i < lul.length() ; i++)
//			System.out.println(Integer.toString(i) + " -> " + lul.charAt(i));

		// object
		System.out.println("Object");
		tp.printMatrix();

		// processed raw string
		String prc = Matrix.matrixToString(tp);
		System.out.println("\nRaw string object");
		System.out.print(prc);

		read.writeFile("cool.txt");
		read.stringWrite(prc);
		// ---------------- testing purpose ----------------
	}

}
