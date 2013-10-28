package se.markusmaga.edaa01;

import java.awt.Container;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Herp {
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setSize(400, 400);

		
		JPanel asd = new JPanel();
		frame.add(asd);
		
		
		GridLayout gbl = new GridLayout();
		gbl.setColumns(9);
		gbl.setRows(9);
		
		asd.setLayout(gbl);
		
		for(int i = 0; i < 9*9; i++) {
			asd.add(new JTextField("" + i));
		}

		
		frame.setVisible(true);
	}
}
