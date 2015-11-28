package edu.cpp.cs.cs141.classproject;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.sun.medialib.mlib.Image;

import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.Action;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JTextPane;
import javax.swing.JLabel;

public class MainAppFrame extends JFrame {

	private int level = 1;
	private Game gameEngine = new Game(level + 5);
	private JPanel contentPane;
	private Object columns[] = { "1", "2", "3", "4", "5", "6", "7", "8", "9" };
	private JTable table = new JTable(gameEngine.getGameMap().getGrid(), columns);
	private boolean debug = false;
	private final Action controlDOWN = new SwingAction_1();
	private final Action controlLEFT = new SwingAction_2();
	private final Action controlRIGHT = new SwingAction_3();
	private final Action controlUP = new SwingAction();
	private final Action debugMode = new SwingAction_4();
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private final JButton btnDown = new JButton("DOWN");
	private final JButton btnLeft = new JButton("LEFT");
	private final JButton btnRight = new JButton("RIGHT");
	private final JButton btnUp = new JButton("UP");
	private final JComboBox comboBox = new JComboBox();
	private final JPanel stats = new JPanel();
	private final JPanel gameInfo = new JPanel();
	private final JRadioButton rdbtnDebugMode = new JRadioButton("Debug Mode");
	private final JTextArea output = new JTextArea();
	private final JTextPane statsOutput = new JTextPane();
	private final JLabel label = new JLabel("");
	private java.awt.Image playerImg = new ImageIcon(this.getClass().getResource("/player.jpeg")).getImage();
	private java.awt.Image ninjaImg = new ImageIcon(this.getClass().getResource("/ninja.png")).getImage();
	private java.awt.Image darkImg = new ImageIcon(this.getClass().getResource("/smoke.png")).getImage();
	private java.awt.Image roomImg = new ImageIcon(this.getClass().getResource("/door.png")).getImage();
	private java.awt.Image docImg = new ImageIcon(this.getClass().getResource("/paper.png")).getImage();
	private java.awt.Image bulletImg = new ImageIcon(this.getClass().getResource("/bullet.jpg")).getImage();
	private java.awt.Image radarImg = new ImageIcon(this.getClass().getResource("/radar.gif")).getImage();
	private java.awt.Image invImg = new ImageIcon(this.getClass().getResource("/inv.png")).getImage();
	private final JScrollPane scrollPane = new JScrollPane();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainAppFrame frame = new MainAppFrame();
					frame.start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void start() {
		setVisible(true);
	}

	public void initGame() {
		gameEngine.generateMap();
	}

	/**
	 * Create the frame.
	 */
	public MainAppFrame() {
		setTitle("Game");
		initGui();
	}

	public void initGui() {

		redirectSystemStreams();
		initGame();
		gameEngine.vision();
		table.setDefaultRenderer(String.class, new IconRenderer());
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 909, 706);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		buttonGroup.add(btnUp);
		btnUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnUp.setToolTipText("UP");
		btnUp.setAction(controlUP);
		btnUp.setBounds(737, 346, 52, 29);

		contentPane.add(btnUp);
		buttonGroup.add(btnDown);
		btnDown.setAction(controlDOWN);
		btnDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnDown.setBounds(728, 384, 72, 29);

		contentPane.add(btnDown);
		buttonGroup.add(btnLeft);
		btnLeft.setAction(controlLEFT);
		btnLeft.setBounds(798, 384, 72, 29);

		contentPane.add(btnLeft);
		buttonGroup.add(btnRight);
		btnRight.setAction(controlRIGHT);
		btnRight.setBounds(658, 384, 72, 29);

		contentPane.add(btnRight);
		gameInfo.setBounds(623, 6, 268, 238);

		contentPane.add(gameInfo);
		gameInfo.setLayout(null);
		scrollPane.setBounds(0, 0, 264, 234);

		gameInfo.add(scrollPane);
		scrollPane.setViewportView(output);
		output.setEditable(false);
		output.setWrapStyleWord(true);
		output.setLineWrap(true);
		rdbtnDebugMode.setAction(debugMode);
		rdbtnDebugMode.setBounds(614, 256, 141, 23);

		contentPane.add(rdbtnDebugMode);
		stats.setBounds(16, 594, 326, 65);

		contentPane.add(stats);
		stats.setLayout(null);
		statsOutput.setBounds(0, 0, 326, 64);
		stats.add(statsOutput);
		statsOutput.setText(gameEngine.stats());
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "Move", "Shoot", "Look" }));
		comboBox.setBounds(737, 256, 133, 27);

		contentPane.add(comboBox);
		label.setBounds(640, 297, 64, 64);

		contentPane.add(label);
		label.setIcon(new ImageIcon(playerImg));
		table.setEnabled(false);
		table.setRowSelectionAllowed(false);

		table.setRowHeight(64);
		table.setBounds(6, 6, 576, 576);

		contentPane.add(table);
	}

	public void updateTextArea(final String text) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				output.append(text);

			}
		});
	}

	public void redirectSystemStreams() {
		OutputStream out = new OutputStream() {
			@Override
			public void write(int b) throws IOException {
				updateTextArea(String.valueOf((char) b));
			}

			@Override
			public void write(byte[] b, int off, int len) throws IOException {
				updateTextArea(new String(b, off, len));
			}

			@Override
			public void write(byte[] b) throws IOException {
				write(b, 0, b.length);
			}
		};

		System.setOut(new PrintStream(out, true));
		System.setErr(new PrintStream(out, true));
	}

	public void repaintAll() {
		table.repaint();
		statsOutput.repaint();
		statsOutput.setText(gameEngine.stats());
	}

	public void nextLevel() {
		level++;
		gameEngine = new Game(level + 5);
		table = new JTable(gameEngine.getGameMap().getGrid(), columns);
		initGui();
		if (debug) {
			rdbtnDebugMode.setSelected(true);
			gameEngine.showAll();
		}
		if (!debug)
			rdbtnDebugMode.setSelected(false);
		repaintAll();
		gameEngine.vision();
	}

	public void updateIcon() {

		ImageIcon playerIcon = new ImageIcon(playerImg);
		ImageIcon ninjaIcon = new ImageIcon(ninjaImg);
		ImageIcon roomIcon = new ImageIcon(roomImg);
		ImageIcon docIcon = new ImageIcon(docImg);
		ImageIcon darkIcon = new ImageIcon(darkImg);
		ImageIcon bulletIcon = new ImageIcon(bulletImg);
		ImageIcon radarIcon = new ImageIcon(radarImg);
		ImageIcon invIcon = new ImageIcon(invImg);

		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 9; j++) {
				if ((String) table.getValueAt(i, j) == "P") {
					table.setValueAt(playerIcon, i, j);
					System.out.println("hi");
				}
				else if ((String) table.getValueAt(i, j) == "N") {
					table.setValueAt(ninjaIcon, i, j);
				}
				else if ((String) table.getValueAt(i, j) == "R") {
					table.setValueAt(roomIcon, i, j);
				}
				else if ((String) table.getValueAt(i, j) == " ") {
					table.setValueAt(bulletIcon, i, j);
				}
				else if ((String) table.getValueAt(i, j) == "*") {
					table.setValueAt(darkIcon, i, j);
				}
				else if ((String) table.getValueAt(i, j) == "$") {
					table.setValueAt(docIcon, i, j);
				}
				else if ((String) table.getValueAt(i, j) == "B") {
					table.setValueAt(bulletIcon, i, j);
				}
				else if ((String) table.getValueAt(i, j) == "R") {
					table.setValueAt(radarIcon, i, j);
				}
				else if ((String) table.getValueAt(i, j) == "I") {
					table.setValueAt(invIcon, i, j);
				}

			}
	}

	public void performAction(int row, int col, int dir) {

		switch (comboBox.getSelectedIndex()) {
		case 0:
			gameEngine.takeTurn(row, col);
			if (gameEngine.isWon()) {
				nextLevel();
			}
			break;
		case 1:
			gameEngine.playerShoot(dir);
			comboBox.setSelectedIndex(0);
			break;
		case 2:
			gameEngine.playerLook(dir);
			comboBox.setSelectedIndex(0);
			break;
		}
		repaintAll();
	}

	public class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "UP");
			putValue(SHORT_DESCRIPTION, "moves player up");
		}

		public void actionPerformed(ActionEvent e) {
			performAction(-1, 0, 1);
		}
	}

	public class SwingAction_1 extends AbstractAction {
		public SwingAction_1() {
			putValue(NAME, "DOWN");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}

		public void actionPerformed(ActionEvent e) {
			performAction(1, 0, 2);
		}
	}

	public class SwingAction_2 extends AbstractAction {
		public SwingAction_2() {
			putValue(NAME, "RIGHT");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}

		public void actionPerformed(ActionEvent e) {
			performAction(0, 1, 3);
		}
	}

	public class SwingAction_3 extends AbstractAction {
		public SwingAction_3() {
			putValue(NAME, "LEFT");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}

		public void actionPerformed(ActionEvent e) {
			performAction(0, -1, 4);
		}
	}

	public class SwingAction_4 extends AbstractAction {
		public SwingAction_4() {
			putValue(NAME, "Debug Mode");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}

		public void actionPerformed(ActionEvent e) {
			if (!debug) {
				gameEngine.showAll();
				debug = true;
			} else {
				gameEngine.hideAll();
				gameEngine.vision();
				debug = false;
			}
			repaintAll();
		}
	}
}
