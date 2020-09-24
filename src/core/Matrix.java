package core;

public class Matrix {
	// Attribute
	private int row;
	private int column;
	public double matrix[][];

	// Constructor
	public Matrix(int b,int k) {
		row = b;
		column = k;
		matrix = new double[b][k];
		// Zero matrix initalisation
		for (int i = 0 ; i < b ; i++)
			for (int j = 0 ; j < k ; j++)
				matrix[i][j] = 0;
	}



	// Get & Set Method
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getColumn() {
		return column;
	}
	public void setColumn(int column) {
		this.column = column;
	}


	// Conversion method
	public static Matrix stringToMatrix(String stream) {
		// TODO, currently cannot handle newline if added on EOF and 0x0 Matrix
		// Count Size
		int rRow = 0, rCol = 0;
		boolean readingN = false, firstRow = true;
		for (int i = 0 ; i < stream.length() ; i++) { // TODO, optimize reading
			if ((Character.toString(stream.charAt(i)).matches("[0-9]|\\.")) && !readingN && firstRow) {
				rCol++;
				readingN = true;
			}
			if (Character.toString(stream.charAt(i)).matches(" "))
				readingN = false;

			if (Character.toString(stream.charAt(i)).matches("\n")) {
				firstRow = false;
				rRow++;
			}
		}
		// Initalisation
		Matrix readMatrix = new Matrix(rRow,rCol);

		// Convert
		String temporaryNumber = "";
		int currentIndex = 0;
		for (int i = 0 ; i < stream.length() ; i++) { // TODO, optimize reading
			if (Character.toString(stream.charAt(i)).matches("[0-9]|\\.")) {
				readingN = true;
				if (readingN)
					temporaryNumber = temporaryNumber + Character.toString(stream.charAt(i));
			}
			if (Character.toString(stream.charAt(i)).matches(" |\n")) {
				readingN = false;
				readMatrix.matrix[currentIndex / rCol][currentIndex % rCol] = Double.parseDouble(temporaryNumber);
				temporaryNumber = "";
				currentIndex++;
			}
		}
		return readMatrix;
	}


	// Other method
	public void printMatrix() {
		for (int i = 0 ; i < row ; i++) {
			for (int j = 0 ; j < column ; j++)
				System.out.print(Double.toString(matrix[i][j]) + " ");
			System.out.println();
		}
	}

	public double cofactorDet() {
		// Base case, 2x2 Matrix Determinant
		if ((row == 2) && (column == 2))
			return (matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0]);

		// Recursive case
		double det = 0;
		boolean skip = false;
		int p = 0;
		Matrix minor = new Matrix(row-1,column-1);
		for (int i = 0 ; i < row ; i++) {
			p = 0;
			skip = false;
			for (int j = 0 ; j < row-1 ; j++) {
				for (int k = 0 ; k < column-1 ; k++) {
					if ((i == j) && !skip) {
						p++;
						skip = true;
					}
					minor.matrix[j][k] = matrix[p][k+1];
				}
				p++;
			}
			det += (matrix[i][0] * minor.cofactorDet() * (1 - 2 * (i & 1)));
		}
		return det;
	}

	public double reducedRowDet() {
		double det = 1, multiplier = 0;
		double temp[][] = matrix;
		for (int i = 0 ; i < column ; i++) {
			// Multiplication Row Operation
			multiplier = temp[i][i]; 	// Saving multiplier
			det *= multiplier;			// Determinant will changed by multiplication factor x if multiplying with 1/x
			for (int p = 0 ; p < column ; p++)
				temp[i][p] /= multiplier;
			// Row Addition Operation
			// No change in determinant value
			for (int j = i + 1 ; j < row ; j++) {
				multiplier = temp[j][i];
				for (int q = 0 ; q < column ; q++) //TODO optimize skip when RO not needed
					temp[j][q] -= (temp[i][q] * multiplier);
			}
		}
		return det;
	}
}
