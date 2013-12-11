package se.markusmaga.edaa01.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import se.markusmaga.edaa01.SudokuSolver;

public class Visualizer {
	private Cell[][] tf = new Cell[9][9];
	private SudokuSolver solver;
	
	private JButton solve, clear;
	
	public Visualizer() {
		solver = new SudokuSolver();
		
		GridLayout gbl = new GridLayout();
		gbl.setColumns(9);
		gbl.setRows(9);
		
		JPanel cellPanel = new JPanel();
		cellPanel.setLayout(gbl);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BorderLayout());
		
		solve = new JButton("Solve");
		solve.addActionListener(new SolveListener());
		
		clear = new JButton("Clear");
		clear.addActionListener(new ClearListener());
		
		buttonPanel.add(solve, BorderLayout.WEST);
		buttonPanel.add(clear, BorderLayout.EAST);
		
		JFrame frame = new JFrame();
		frame.setSize(400, 400);
		frame.setLayout(new BorderLayout());
		frame.add(cellPanel, BorderLayout.CENTER);
		frame.add(buttonPanel, BorderLayout.SOUTH);
		
		createFields(cellPanel);
		frame.setVisible(true);
	}
	
	private void createFields(JPanel panel) {
		for(int y = 0; y < 9; y++) {
			for(int x = 0; x < 9; x++) {
				tf[y][x] = new Cell(solver, y, x);

				panel.add(tf[y][x]);
			}
		}
	}
	
	private void clearCells() {
		for(int y = 0; y < 9; y++) {
			for(int x = 0; x < 9; x++) {
				tf[y][x].clearCell();
			}
		}
	}
	
	private void populateCells() {
		for(int y = 0; y < 9; y++) {
			for(int x = 0; x < 9; x++) {
				tf[y][x].refreshValue();
			}
		}
	}
	
	private class ClearListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			clearCells();
		}
	}
	
	private class SolveListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			solve.setEnabled(false);
			
			if(solver.solve()) {
				populateCells();
			} else {
				JOptionPane.showMessageDialog(null, "Dat sudoku not be solveable");
			}
			
			solve.setEnabled(true);
		}
	}
}
