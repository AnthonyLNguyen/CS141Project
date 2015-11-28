package edu.cpp.cs.cs141.classproject;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
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
import javax.swing.JTextPane;

public class MainAppFrame extends JFrame {

	private JPanel contentPane;
	private Game gameEngine = new Game(6);
	private Object columns[] = { "1", "2", "3", "4", "5", "6", "7", "8", "9" };
	private final JButton btnUp = new JButton("UP");
	private final Action controlUP = new SwingAction();
	private final JTextArea output = new JTextArea();
	private JTable table = new JTable(gameEngine.getGameMap().getGrid(), columns);
	private final JPanel gameInfo = new JPanel();
	private final JScrollPane scrollPane = new JScrollPane();
	private final JButton btnDown = new JButton("DOWN");
	private final Action controlDOWN = new SwingAction_1();
	private final JButton btnLeft = new JButton("LEFT");
	private final JButton btnRight = new JButton("RIGHT");
	private final Action controlLEFT = new SwingAction_2();
	private final Action controlRIGHT = new SwingAction_3();
	private final Action debugMode = new SwingAction_4();
	private final JRadioButton rdbtnDebugMode = new JRadioButton("Debug Mode");
	private boolean debug = false;
	private final JPanel bonuses = new JPanel();
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private final JComboBox comboBox = new JComboBox();
	private final JTextPane statsOutput = new JTextPane();

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
		initGui();
	}

	public void initGui() {

		redirectSystemStreams();
		initGame();

		table.setBounds(6, 6, 344, 330);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 636, 455);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		contentPane.add(table);
		buttonGroup.add(btnUp);
		btnUp.setToolTipText("UP");
		btnUp.setAction(controlUP);
		btnUp.setBounds(485, 332, 52, 29);

		contentPane.add(btnUp);
		buttonGroup.add(btnDown);
		btnDown.setAction(controlDOWN);
		btnDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnDown.setBounds(476, 370, 72, 29);

		contentPane.add(btnDown);
		buttonGroup.add(btnLeft);
		btnLeft.setAction(controlLEFT);
		btnLeft.setBounds(546, 370, 72, 29);

		contentPane.add(btnLeft);
		buttonGroup.add(btnRight);
		btnRight.setAction(controlRIGHT);
		btnRight.setBounds(406, 370, 72, 29);

		contentPane.add(btnRight);
		gameInfo.setBounds(362, 5, 268, 238);

		contentPane.add(gameInfo);
		gameInfo.setLayout(null);
		scrollPane.setBounds(0, 0, 268, 238);

		gameInfo.add(scrollPane);
		output.setWrapStyleWord(true);
		output.setLineWrap(true);
		scrollPane.setViewportView(output);
		rdbtnDebugMode.setAction(debugMode);
		rdbtnDebugMode.setBounds(362, 242, 141, 23);

		contentPane.add(rdbtnDebugMode);
		bonuses.setBounds(16, 351, 326, 65);

		contentPane.add(bonuses);
		bonuses.setLayout(null);
		statsOutput.setBounds(0, 0, 326, 64);
		bonuses.add(statsOutput);
		statsOutput.setText(gameEngine.stats());
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "Move", "Shoot", "Look" }));
		comboBox.setBounds(485, 242, 133, 27);

		contentPane.add(comboBox);
	}

	private void updateTextArea(final String text) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				output.append(text);

			}
		});
	}

	private void redirectSystemStreams() {
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

	private void repaintAll(){
		table.repaint();
		statsOutput.repaint();
		statsOutput.setText(gameEngine.stats());
	}
	
	private void performAction(int row, int col, int dir) {

		switch (comboBox.getSelectedIndex()+1) {
		case 1:
			gameEngine.takeTurn(row, col);
			break;
		case 2:
			gameEngine.playerShoot(dir);
			break;
		case 3:
			gameEngine.playerLook(dir);
			break;
		}
		repaintAll();
	}

	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "UP");
			putValue(SHORT_DESCRIPTION, "moves player up");
		}

		public void actionPerformed(ActionEvent e) {
			performAction(-1, 0, 1);
		}
	}

	private class SwingAction_1 extends AbstractAction {
		public SwingAction_1() {
			putValue(NAME, "DOWN");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}

		public void actionPerformed(ActionEvent e) {
			performAction(1, 0, 2);
		}
	}

	private class SwingAction_2 extends AbstractAction {
		public SwingAction_2() {
			putValue(NAME, "RIGHT");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}

		public void actionPerformed(ActionEvent e) {
			performAction(0, 1, 3);
		}
	}

	private class SwingAction_3 extends AbstractAction {
		public SwingAction_3() {
			putValue(NAME, "LEFT");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}

		public void actionPerformed(ActionEvent e) {
			performAction(0, -1, 4);
		}
	}

	private class SwingAction_4 extends AbstractAction {
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
