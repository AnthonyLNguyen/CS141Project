package edu.cpp.cs.cs141.classproject;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import java.awt.Component;

public class IconRenderer extends JLabel implements TableCellRenderer {
	private static final long serialVersionUID = 1L;

	public IconRenderer() {
		setOpaque(true);
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		if (((String) (table.getValueAt(row, column))) == "P"){
			setIcon(new ImageIcon("player.jpeg"));
			System.out.println("Hi");
		}
		else if (((String) (table.getValueAt(row, column))) == "N")
			setIcon(new ImageIcon("ninja.png"));
		else if (((String) (table.getValueAt(row, column))) == "*")
			setIcon(new ImageIcon("smoke.png"));
		else if (((String) (table.getValueAt(row, column))) == "$")
			setIcon(new ImageIcon("paper.png"));
		else if (((String) (table.getValueAt(row, column))) == "I")
			setIcon(new ImageIcon("inv.jpg"));
		else if (((String) (table.getValueAt(row, column))) == "R")
			setIcon(new ImageIcon("radar.gif"));
		else if (((String) (table.getValueAt(row, column))) == "=")
			setIcon(new ImageIcon("door.png"));
		else if (((String) (table.getValueAt(row, column))) == "B")
			setIcon(new ImageIcon("bullet.jpg"));
		else if (((String) (table.getValueAt(row, column))) == " ")
			setIcon(new ImageIcon("radar.gif"));
		return this;
	}
}