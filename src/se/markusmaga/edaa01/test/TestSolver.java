package se.markusmaga.edaa01.test;

import static org.junit.Assert.*;

import org.junit.Test;

import se.markusmaga.edaa01.SudokuSolver;

public class TestSolver {

	@Test
	public void testSolveEmpty() {
		int[][] emptyMatrix = new int[9][9];
		
		SudokuSolver solver = new SudokuSolver(emptyMatrix);

		assertTrue("Couldnt solve matrix", solver.solve());
		
		printMatrix(solver.getMatrix());
	}

	@Test
	public void testSolveHinted() {
		int[][] emptyMatrix = new int[9][9];
		emptyMatrix[8][7] = 9;
		emptyMatrix[8][8] = 9;
		
		SudokuSolver solver = new SudokuSolver(emptyMatrix);

		assertTrue("Couldnt solve matrix", !solver.solve());
		
		printMatrix(solver.getMatrix());
	}
	
	@Test
	public void testSolveInvalid() {
		int[][] emptyMatrix = new int[9][9];
		
		for(int y = 0; y < 6; y++) {
			for(int x = 0; x < 6; x++) {
				emptyMatrix[y][x] = 6;
			}
		}

		SudokuSolver solver = new SudokuSolver(emptyMatrix);

		assertTrue("Couldnt solve matrix", !solver.solve());
		
		printMatrix(solver.getMatrix());
	}
	
	private void printMatrix(int[][] matrix) {
		for(int y = 0; y < 9; y++) {
			for(int x = 0; x < 9; x++) {
				System.out.print(" " + matrix[y][x] + " ");
			}
			
			System.out.println("");
		}
		System.out.println("");
	}
}
