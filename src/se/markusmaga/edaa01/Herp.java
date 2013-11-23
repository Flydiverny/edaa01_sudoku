package se.markusmaga.edaa01;

import java.awt.Color;
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
		
		JTextField[][] tf = new JTextField[9][9];
		
		for(int y = 0; y < 9; y++) {
			int modY = y / 3;
			for(int x = 0; x < 9; x++) {
				tf[y][x] = new JTextField("" + x);
				
				int modX = x / 3;
				if(modY==modX || modX+modY==2)
					tf[y][x].setBackground(Color.LIGHT_GRAY);
				
				asd.add(tf[y][x]);
			}
		}

		frame.setVisible(true);
	}
}
