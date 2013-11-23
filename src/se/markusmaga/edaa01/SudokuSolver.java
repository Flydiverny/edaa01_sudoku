package se.markusmaga.edaa01;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SudokuSolver {
	
	private int[][] orgMatrix;
	private Cell[][] cellMatrix;
	
	public SudokuSolver(int[][] matrix) {
		orgMatrix = matrix;
		
		if(matrix.length != 9 || matrix[0].length != 9) {
			throw new IllegalArgumentException("Is not a valid Sudoko matrix. Should be 9x9.");
		}
		
		cellMatrix = convertMatrix(matrix);
	}
	
	public int[][] getMatrix() {
		return convertMatrix(cellMatrix);
	}
	
	public boolean solve() {
		// if unsolveable return false.
		// returns true when solved.
		
		// Validate matrix.
		if(solve(0, 0)) {
			return true;
		}
		
		
		// nuke matrix since it wasnt solveable..
		return false;
	}

	/**
	 * Recursively solve from given x & y until end of cellMatrix.
	 * @param y
	 * @param x
	 * @return true if solved, false if invalid.
	 */
	private boolean solve(int y, int x) {
		if(y == 9) {
			return true;
		}
		
		Cell cell = cellMatrix[y][x];

		int nextX = x == 8 ? 0 : (x+1);
		int nextY = x == 8 ? (y+1) : y;
		
		// If cell is predefined just go to the next one.
		if(cell.preset) {
			return solve(nextY, nextX);
		}
		
		// Check all possible numbers (1-9) and recursively solve later columns.
		for(int nbr = 1; nbr <= 9; nbr++) {
			if(!isValidNumber(y, x, nbr))
				continue;
			
			cell.value = nbr;
			
			if(solve(nextY, nextX))
				return true;
		}
		
		// No valid number, set 0 and return false.
		cell.value = 0;
		
		return false;
	}
	
	private boolean isValidNumber(int y, int x, int nbr) {
		// Check row.
		for(int col = 0; col < 9; col++) {
			if(cellMatrix[y][col].value == nbr)
				return false;
		}
				
		// Check column.
		for(int row = 0; row < 9; row++) {
			if(cellMatrix[row][x].value == nbr)
				return false;
		}
				
		int cubeX = (x / 3) * 3;
		int cubeY = (y / 3) * 3;
				
		for(int cY = 0; cY < 3; cY++) {
			for(int cX = 0; cX < 3; cX++) {
				if(cellMatrix[cubeY + cY][cubeX + cX].value == nbr)
					return false;
			}
		}
		
		return true;
	}

	/**
	 * Converts a matrix of integers to an internal Cell matrix.
	 * @param matrix
	 * @return
	 */
	private Cell[][] convertMatrix(int[][] matrix) {
		Cell[][] convertedMatrix = new Cell[matrix.length][matrix[0].length];
		
		for(int y = 0; y < matrix.length; y++) {
			for(int x = 0; x < matrix[y].length; x++) {
				if(matrix[y][x] > 0)
					convertedMatrix[y][x] = new Cell(matrix[y][x], true);
				else
					convertedMatrix[y][x] = new Cell(0);
			}
		}
		
		return convertedMatrix;
	}
	
	/**
	 * Converts an internal Cell matrix to an integer matrix.
	 * @param matrix
	 * @return
	 */
	private int[][] convertMatrix(Cell[][] matrix) {
		int[][] convertedMatrix = new int[matrix.length][matrix[0].length];
		
		for(int y = 0; y < matrix.length; y++) {
			for(int x = 0; x < matrix[y].length; x++) {
				convertedMatrix[y][x] = matrix[y][x].value;
			}
		}
		
		return convertedMatrix;
	}
	
	private class Cell {
		private int value;
		private boolean preset;
		
		public Cell(int value) {
			this(value, false);
		}
		
		public Cell(int value, boolean preset) {
			this.value = value;
			this.preset = preset;
		}
	}
}
