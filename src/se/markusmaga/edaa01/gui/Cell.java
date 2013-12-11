package se.markusmaga.edaa01.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import se.markusmaga.edaa01.SudokuSolver;

public class Cell extends JTextField {

	private SudokuSolver solver;
	private int y, x;
	private Color defaultColor;
	
	/** List of all created cells **/
	private static List<Cell> cells = new LinkedList<Cell>();
	
	private static Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 20);
	
	/**
	 * Creates a new Cell used for the Sudoku GUI.
	 * @param solver, solver to bind cell to.
	 * @param y
	 * @param x
	 */
	public Cell(SudokuSolver solver, int y, int x) {
		this.solver = solver;
		this.y = y;
		this.x = x;
		this.setHorizontalAlignment(CENTER);
		
		// Adds this cell to a list of all created cells, used for revalidating.
		cells.add(this);
		
		defaultColor = getDefaultColor();
		
		this.setBackground(defaultColor);
		this.setForeground(Color.BLUE);
		this.setFont(font);
		
		this.setDocument(new SudokuDocument());
	}
	
	/**
	 * Returns the default color for this cell based on X,Y coordinates.
	 * @return Color
	 */
	private Color getDefaultColor() {
		int cubY = y / 3;
		int cubX = x / 3;
		if(cubY==cubX || cubX+cubY==2)
			return Color.LIGHT_GRAY;
		
		return Color.WHITE;
	}
	
	/**
	 * Fetches the value for this cell from the solver.
	 */
	public void refreshValue() {
		int nbr = -1;
		int value = solver.getCell(y, x);
		
		try {
			nbr = Integer.parseInt(this.getText());
		} catch(NumberFormatException e) {
			// just an empty box.
		}
		
		/*  Set foreground color based on if the Cell already
			contained the same value as the solver. */
		if(value == nbr) {
			this.setForeground(Color.BLUE);
		} else {
			this.setForeground(Color.BLACK);
		}
		
		// Set Cell text to value.
		if(value != 0) {
			this.setText(""+value);
		} else {
			this.setText("");
		}
	}
	
	/**
	 * Resets this cell in the solver and refetches the value for the gui.
	 * Also resets background color.
	 */
	public void clearCell() {
		solver.resetCell(y, x);
		this.refreshValue();
		this.setBackground(defaultColor);
	}

	/**
	 * Revalidates all cells listed in cells.
	 * Changing their background color if they validate the Sudoku rules.
	 */
	private static void reValidateCells() {
		for(Cell c : cells) {
			if(!c.solver.validateCell(c.y, c.x)) {
				c.setBackground(Color.RED);	
			} else {
				c.setBackground(c.defaultColor);
			}
		}
	}
	
	/**
	 * Validates the current cell by putting the input text
	 * into the same cell in the solver.
	 * 
	 * Also resets solver cell if we have an invalid input. Keeping them synced.
	 */
	private void validateCell() {		
		try {
			int nbr = Integer.parseInt(this.getText());
			
			if(!solver.putCell(y, x, nbr)) {
				this.setBackground(Color.RED);	
			} else {
				this.setBackground(defaultColor);
			}
		} catch(Exception e) {
			solver.resetCell(y, x);
			this.setBackground(defaultColor);
			this.setText("");
		}
	}
	
	private class SudokuDocument extends PlainDocument {
		private static final long serialVersionUID = 8102383165323185077L;
		
	        @Override
	        public void replace(int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
	            String oldValue = Cell.this.getText();
	            super.replace(0, oldValue.length(), text, attrs);
	            String newValue = Cell.this.getText();
	            
	            if(!oldValue.equals(newValue) && !newValue.equals("")) {
	            	validateCell();
	            }
	            
	            reValidateCells();
	        }

	        @Override
	        public void remove(int offs, int len) throws BadLocationException {
	            String oldValue = Cell.this.getText();
	            super.remove(offs, len);
	            String newValue = Cell.this.getText();
	            
	            if(!oldValue.equals(newValue)) {
	            	validateCell();
	            }
	            
	            reValidateCells();
	        }
	}
}
