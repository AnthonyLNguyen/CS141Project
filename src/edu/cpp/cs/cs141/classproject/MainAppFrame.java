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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JDesktopPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;

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
	private final JPanel gameInfo = new JPanel();
	private final JTextArea output = new JTextArea();
	private final JTextPane statsOutput = new JTextPane();
	private final JLabel label = new JLabel("");
	private java.awt.Image playerImg = new ImageIcon(this.getClass().getResource("/player.jpeg")).getImage();
	private final JScrollPane scrollPane = new JScrollPane();
	private final JMenuBar menuBar = new JMenuBar();
	private final JMenu mnNewMenu = new JMenu("File");
	private final JMenuItem mntmSave = new JMenuItem("Save");
	private final Action saveGame = new SwingAction_5();
	private GameSave gs;
	private final JMenuItem mntmLoad = new JMenuItem("Load");
	private final Action loadGame = new SwingAction_6();
	private boolean gameLoaded = false;
	private final JMenu mnEdit = new JMenu("Edit");
	private final JRadioButtonMenuItem rdbtnmntmDebugMode = new JRadioButtonMenuItem("Debug Mode");
	private final JMenuItem mntmNewGame = new JMenuItem("New Game");
	private final Action newGame = new SwingAction_7();
	private final JRadioButtonMenuItem rdbtnmntmMoreVision = new JRadioButtonMenuItem("More Vision");
	private final Action altVis = new SwingAction_8();
	private final JTextPane txtpnInstructions = new JTextPane();

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
		setFocusable(true);
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				keybinds(e);
			}
		});
		setTitle("Game");
		initGui();
	}

	public void initGui() {

		redirectSystemStreams();
		if (!gameLoaded)
			initGame();
		gameEngine.vision();
		table.setEnabled(false);
		table.setDefaultRenderer(Object.class, new IconRenderer());

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 909, 637);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		buttonGroup.add(btnUp);

		btnUp.setToolTipText("UP");
		btnUp.setAction(controlUP);
		btnUp.setBounds(735, 414, 52, 29);

		contentPane.add(btnUp);
		buttonGroup.add(btnDown);
		btnDown.setAction(controlDOWN);

		btnDown.setBounds(726, 452, 72, 29);

		contentPane.add(btnDown);
		buttonGroup.add(btnLeft);
		btnLeft.setAction(controlLEFT);
		btnLeft.setBounds(796, 452, 72, 29);

		contentPane.add(btnLeft);
		buttonGroup.add(btnRight);
		btnRight.setAction(controlRIGHT);
		btnRight.setBounds(656, 452, 72, 29);

		contentPane.add(btnRight);
		gameInfo.setBounds(621, 74, 268, 238);

		contentPane.add(gameInfo);
		gameInfo.setLayout(null);
		scrollPane.setBounds(0, 0, 264, 234);

		gameInfo.add(scrollPane);
		scrollPane.setViewportView(output);
		output.setEditable(false);
		output.setWrapStyleWord(true);
		output.setLineWrap(true);
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "Move", "Shoot", "Look" }));
		comboBox.setBounds(735, 324, 133, 27);

		contentPane.add(comboBox);
		label.setBounds(638, 365, 64, 64);

		contentPane.add(label);
		label.setIcon(new ImageIcon(playerImg));
		table.setRowSelectionAllowed(false);

		table.setRowHeight(64);
		table.setBounds(6, 30, 576, 576);

		contentPane.add(table);
		menuBar.setBounds(0, 0, 909, 22);

		contentPane.add(menuBar);

		menuBar.add(mnNewMenu);
		mntmNewGame.setAction(newGame);

		mnNewMenu.add(mntmNewGame);
		mntmSave.setAction(saveGame);

		mnNewMenu.add(mntmSave);
		mntmLoad.setAction(loadGame);

		mnNewMenu.add(mntmLoad);

		menuBar.add(mnEdit);
		rdbtnmntmDebugMode.setAction(debugMode);

		mnEdit.add(rdbtnmntmDebugMode);
		rdbtnmntmMoreVision.setAction(altVis);

		mnEdit.add(rdbtnmntmMoreVision);
		txtpnInstructions.setEditable(false);
		txtpnInstructions.setText("Arrow Keys = Move\nS = Shoot\nL = Look\nPress Tab a couple times if keys stop working");
		txtpnInstructions.setBounds(698, 515, 191, 80);
		
		contentPane.add(txtpnInstructions);
		statsOutput.setEditable(false);
		statsOutput.setBounds(621, 515, 77, 80);
		contentPane.add(statsOutput);
		statsOutput.setText(gameEngine.stats());

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

	public void refresh() {
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
			rdbtnmntmDebugMode.setSelected(true);
			rdbtnmntmMoreVision.setSelected(true);
			gameEngine.showAll();
		}
		if (!debug) {
			rdbtnmntmDebugMode.setSelected(false);
			rdbtnmntmMoreVision.setSelected(false);
		}
		refresh();
		gameEngine.vision();
	}

	public void keybinds(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			performAction(-1, 0, 1);
			break;
		case KeyEvent.VK_DOWN:
			performAction(1, 0, 2);
			break;
		case KeyEvent.VK_RIGHT:
			performAction(0, 1, 3);
			break;
		case KeyEvent.VK_LEFT:
			performAction(0, -1, 4);
			break;
		case KeyEvent.VK_S:
			comboBox.setSelectedIndex(1);
			break;
		case KeyEvent.VK_L:
			comboBox.setSelectedIndex(2);
			break;
		}

		repaint();
	}

	public void performAction(int row, int col, int dir) {

		switch (comboBox.getSelectedIndex()) {
		case 0:
			gameEngine.takeTurn(row, col);
			if (gameEngine.getLoss())
				gameOver();
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
		refresh();
	}

	private void gameOver() {
		Object[] options = { "Quit", "New Game" };
		int n = JOptionPane.showOptionDialog(contentPane, "Game Over", "Game Over", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, options, null);
		newGame();
		if (n == 1) {
			return;
		}
		if (n == 0) {
			System.exit(0);
		}

	}

	private void newGame() {
		level = 1;
		gameEngine = new Game(6);
		table = new JTable(gameEngine.getGameMap().getGrid(), columns);
		initGui();
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
			refresh();
		}
	}

	private class SwingAction_5 extends AbstractAction {
		public SwingAction_5() {
			putValue(NAME, "Save Game");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}

		public void actionPerformed(ActionEvent e) {
			String s = (String) JOptionPane.showInputDialog(contentPane, "Enter a name for the save file",
					"Customized Dialog", JOptionPane.PLAIN_MESSAGE, null, null, "");

			// If a string was returned, say so.
			if ((s != null) && (s.length() > 0)) {
				gs = new GameSave(gameEngine);
				gs.saveGame(s);
				JOptionPane.showMessageDialog(contentPane, "Saved as " + s + ".dat");
				return;
			}

			// If you're here, the return value was null/empty.
			JOptionPane.showMessageDialog(contentPane, "Save canceled");
		}
	}

	private class SwingAction_6 extends AbstractAction {
		public SwingAction_6() {
			putValue(NAME, "Load Game");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}

		public void actionPerformed(ActionEvent e) {
			String s = (String) JOptionPane.showInputDialog(contentPane,
					"What is the name of the save " + "\nyou would like to load? " + "\n(Don't include extention)",
					"Customized Dialog", JOptionPane.PLAIN_MESSAGE, null, null, "");

			// If a string was returned, say so.
			if ((s != null) && (s.length() > 0)) {
				gameLoaded = true;
				gs = new GameSave();
				gameEngine = gs.loadGame(s);
				JOptionPane.showMessageDialog(contentPane, "Loaded " + s + ".dat");
				table = new JTable(gameEngine.getGameMap().getGrid(), columns);
				initGui();
				if (debug) {
					rdbtnmntmDebugMode.setSelected(true);
					rdbtnmntmMoreVision.setSelected(true);
					gameEngine.showAll();
				}
				if (!debug)
					rdbtnmntmDebugMode.setSelected(false);
				rdbtnmntmMoreVision.setSelected(false);
				refresh();
				gameEngine.vision();
				gameLoaded = false;
				return;
			}

			// If you're here, the return value was null/empty.
			JOptionPane.showMessageDialog(contentPane, "Load canceled");
		}
	}

	private class SwingAction_7 extends AbstractAction {
		public SwingAction_7() {
			putValue(NAME, "New Game");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}

		public void actionPerformed(ActionEvent e) {
			newGame();
			System.out.println("\nNew Game Started");
		}
	}

	private class SwingAction_8 extends AbstractAction {
		public SwingAction_8() {
			putValue(NAME, "Alternate Vision");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}

		public void actionPerformed(ActionEvent e) {
			gameEngine.toggleDiagonalVision();
			gameEngine.vision();
			refresh();
		}
	}
}
