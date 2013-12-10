package se.markusmaga.edaa01.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import se.markusmaga.edaa01.SudokuSolver;

public class Visualizer {
	private JTextField[][] tf = new JTextField[9][9];
	private SudokuSolver solver;
	
	public Visualizer() {
		solver = new SudokuSolver();
		
		JFrame frame = new JFrame();
		frame.setSize(400, 400);
		
		frame.setLayout(new BorderLayout());
		JPanel panel = new JPanel();
		frame.add(panel);
		
		GridLayout gbl = new GridLayout();
		gbl.setColumns(9);
		gbl.setRows(9);
		
		panel.setLayout(gbl);
		
		createFields(panel);
		
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
}
