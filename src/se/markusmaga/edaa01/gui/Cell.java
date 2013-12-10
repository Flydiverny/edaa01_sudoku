package se.markusmaga.edaa01.gui;

import java.awt.Color;
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
	private Color defaultColor = Color.WHITE;
	
	private static List<Cell> cells = new LinkedList<Cell>();
	
	public Cell(SudokuSolver solver, int y, int x) {
		this.solver = solver;
		this.y = y;
		this.x = x;
		
		cells.add(this);
		
		int cubY = y / 3;
		int cubX = x / 3;
		if(cubY==cubX || cubX+cubY==2)
			defaultColor = Color.LIGHT_GRAY;
		
		this.setBackground(defaultColor);
		
		this.setDocument(new SudokuDocument());
	}

	private static void reValidateCells() {
		for(Cell c : cells) {
			int cur = c.solver.getCell(c.y, c.x);
		
			if(cur != 0) {
				if(!c.solver.putCell(c.y, c.x, cur)) {
					c.setBackground(Color.RED);	
				} else {
					c.setBackground(c.defaultColor);
				}
			}
		}
	}
	
	private void validateCell() {
		try {
			int nbr = Integer.parseInt(this.getText());
			
			if(!solver.putCell(y, x, nbr)) {
				this.setBackground(Color.RED);	
			} else {
				this.setBackground(defaultColor);
			}
		} catch(Exception e) {
			solver.resetCell(y,x);
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
	            
	            if(!oldValue.equals(newValue) && !newValue.equals("")) {
	            	validateCell();
	            }
	            
	            reValidateCells();
	        }
	}
}
