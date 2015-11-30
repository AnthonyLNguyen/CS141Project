package edu.cpp.cs.cs141.classproject;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import java.awt.Component;

public class IconRenderer extends JLabel implements TableCellRenderer {
	private static final long serialVersionUID = 1L;
	private java.awt.Image playerImg = new ImageIcon(this.getClass().getResource("/player.png")).getImage();
	private java.awt.Image playerinvImg = new ImageIcon(this.getClass().getResource("/playerinv.png")).getImage();
	private java.awt.Image ninjaImg = new ImageIcon(this.getClass().getResource("/ninja.png")).getImage();
	private java.awt.Image darkImg = new ImageIcon(this.getClass().getResource("/smoke.png")).getImage();
	private java.awt.Image roomImg = new ImageIcon(this.getClass().getResource("/door.png")).getImage();
	private java.awt.Image docImg = new ImageIcon(this.getClass().getResource("/paper.png")).getImage();
	private java.awt.Image bulletImg = new ImageIcon(this.getClass().getResource("/bulletplus.png")).getImage();
	private java.awt.Image radarImg = new ImageIcon(this.getClass().getResource("/radar.png")).getImage();
	private java.awt.Image invImg = new ImageIcon(this.getClass().getResource("/inv.png")).getImage();
	private java.awt.Image xImg = new ImageIcon(this.getClass().getResource("/x.png")).getImage();
	public IconRenderer() {
		setOpaque(true);
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		if (((table.getValueAt(row, column))).toString().equals("p")){
			setIcon(new ImageIcon(playerImg));
		}
		else if (( (table.getValueAt(row, column))).toString().equals("P"))
			setIcon(new ImageIcon(playerinvImg));
		else if (( (table.getValueAt(row, column))).toString().equals("N"))
			setIcon(new ImageIcon(ninjaImg));
		else if (( (table.getValueAt(row, column))).toString().equals("*"))
			setIcon(new ImageIcon(darkImg));
		else if (( (table.getValueAt(row, column))).toString().equals("$"))
			setIcon(new ImageIcon(docImg));
		else if (( (table.getValueAt(row, column))).toString().equals("X"))
			setIcon(new ImageIcon(xImg));
		else if (( (table.getValueAt(row, column))).toString().equals("I"))
			setIcon(new ImageIcon(invImg));
		else if (( (table.getValueAt(row, column))).toString().equals("R"))
			setIcon(new ImageIcon(radarImg));
		else if (( (table.getValueAt(row, column))).toString().equals("="))
			setIcon(new ImageIcon(roomImg));
		else if (( (table.getValueAt(row, column))).toString().equals("B"))
			setIcon(new ImageIcon(bulletImg));
		else if (( (table.getValueAt(row, column))).toString().equals(" "))
			setIcon(new ImageIcon(""));
		return this;
	}
}
