package se.markusmaga.edaa01;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SudokuSolver {
	
	private int[][] matrix;
	
	public SudokuSolver() {
		this(new int[9][9]);
	}
	
	public SudokuSolver(int[][] matrix) {
		this.matrix = matrix;
		
		if(matrix.length != 9 || matrix[0].length != 9 || !isValidMatrix()) {
			throw new IllegalArgumentException("Is not a valid Sudoko matrix. Should be 9x9.");
		}
	}
	
	public int[][] getMatrix() {
		return matrix;
	}

	/**
	 * Checks if entered numbers are valid.
	 * @return true if valid, else false.
	 */
	private boolean isValidMatrix() {
		 for(int y = 0; y < 9; y++) {
			 for(int x = 0; x < 9; x++) {
				 if(matrix[y][x] > 0 && !isValidNumber(y, x, matrix[y][x]) || matrix[y][x] > 9) {
					 return false;
				 }
			 }
		 }
		 
		 return true;
	}
	
	/**
	 * Attempts to solve the Sudoku.
	 * post: getMatrix() contains solution.
	 * @return true if solved, else false.
	 */
	public boolean solve() {
		if(!isValidMatrix()) {
			return false;
		}
		
		// Wont solve same matrix twice.
		return solve(0, 0);
	}
	
	/**
	 * Returns the current value of a given cell in the Sudoku.
	 * @param y
	 * @param x
	 * @return
	 */
	public int getCell(int y, int x) {
		return matrix[y][x];
	}
	
	/**
	 * Forcefully sets a number at a given coordinate. 
	 * In the Sudoku matrix.
	 * @param y
	 * @param x
	 * @param 0 < nbr <= 9
	 * @return true if valid placement, false if invalid placement.
	 */
	public boolean putCell(int y, int x, int nbr) {
		if(nbr < 0 || nbr > 9)
			throw new IllegalArgumentException("NOT A VALID SUDOKU NUMBER");
		
		matrix[y][x] = nbr;
		
		return isValidNumber(y, x, nbr);
	}
	
	public void resetCell(int y, int x) {
		matrix[y][x] = 0;
	}
	
	/**
	 * Recursively solve from given x & y until end of matrix.
	 * pre: Assumes isValidMatrix() == true.
	 * post: matrix[][] contains solution.
	 * @param y
	 * @param x
	 * @return true if solved, false if invalid.
	 */
	private boolean solve(int y, int x) {
		if(y == 9) {
			return true;
		}
		
		int nextX = x == 8 ? 0 : (x+1);
		int nextY = x == 8 ? (y+1) : y;
		
		// If cell is predefined just go to the next one.
		if(matrix[y][x] > 0) {
			return solve(nextY, nextX);
		}
		
		// Check all possible numbers (1-9) and recursively solve later columns.
		for(int nbr = 1; nbr <= 9; nbr++) {
			if(!isValidNumber(y, x, nbr))
				continue;
			
			matrix[y][x] = nbr;
			
			if(solve(nextY, nextX))
				return true;
		}
		
		// No valid number, set 0 and return false.
		matrix[y][x] = 0;
		
		return false;
	}
	
	private boolean isValidNumber(int y, int x, int nbr) {
		// Check row & col.
		for(int i = 0; i < 9; i++) {
			if(matrix[y][i] == nbr && i != x || matrix[i][x] == nbr && i != y)
				return false;
		}
				
		int cubeX = (x / 3) * 3;
		int cubeY = (y / 3) * 3;
				
		for(int cY = 0; cY < 3; cY++) {
			for(int cX = 0; cX < 3; cX++) {
				if(matrix[cubeY + cY][cubeX + cX] == nbr && (cubeY + cY) != y && (cubeX + cX) != x)
					return false;
			}
		}
		
		return true;
	}
}
