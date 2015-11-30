package edu.cpp.cs.cs141.classproject;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

//import com.sun.medialib.mlib.Image;

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
import java.awt.Font;

public class MainAppFrame extends JFrame {

	private int level = 1;
	private Game gameEngine = new Game(level + 5);
	private JPanel contentPane;
	private Object columns[] = { "1", "2", "3", "4", "5", "6", "7", "8", "9" };
	private JTable table = new JTable(gameEngine.getGameMap().getGrid(), columns);
	private boolean debug = false;
	private boolean vis = false;
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
	private final JLabel label = new JLabel("");
	private java.awt.Image playerImg = new ImageIcon(this.getClass().getResource("/player.png")).getImage();
	private java.awt.Image heartImg = new ImageIcon(this.getClass().getResource("/heart.png")).getImage();
	private java.awt.Image bulletImg = new ImageIcon(this.getClass().getResource("/bullet.png")).getImage();
	private java.awt.Image ninjaImg = new ImageIcon(this.getClass().getResource("/ninja.png")).getImage();
	private java.awt.Image noImg = new ImageIcon(this.getClass().getResource("")).getImage();
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
	private final JRadioButtonMenuItem mntmHardMode = new JRadioButtonMenuItem("Hard Mode");
	private final Action action = new SwingAction_9();
	private boolean hardMode = false;
	private final JLabel heart3 = new JLabel("");
	private final JLabel heart2 = new JLabel("");
	private final JLabel heart1 = new JLabel("");
	private final JLabel bullets = new JLabel("");
	private final JLabel bulletCount = new JLabel("x" + gameEngine.getPlayer().getNumBullets());
	private final JLabel ninja = new JLabel("");
	private final JLabel ninjaCount = new JLabel("x" + gameEngine.getNumNinjas());
	private final JLabel lbllevel = new JLabel("Level:" + (gameEngine.getAmountNinjas()-5));
	private final JLabel moveCount = new JLabel("Moves:" + gameEngine.getMoveCount());

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
		setTitle("Bob Goes To Work");
		initGui();
	}

	public void initGui() {

		redirectSystemStreams();
		if (!gameLoaded)
			initGame();
		refresh();
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
		btnUp.setBounds(732, 436, 52, 29);

		contentPane.add(btnUp);
		buttonGroup.add(btnDown);
		btnDown.setAction(controlDOWN);

		btnDown.setBounds(723, 474, 72, 29);

		contentPane.add(btnDown);
		buttonGroup.add(btnLeft);
		btnLeft.setAction(controlLEFT);
		btnLeft.setBounds(793, 474, 72, 29);

		contentPane.add(btnLeft);
		buttonGroup.add(btnRight);
		btnRight.setAction(controlRIGHT);
		btnRight.setBounds(653, 474, 72, 29);

		contentPane.add(btnRight);
		gameInfo.setBounds(621, 30, 268, 238);

		contentPane.add(gameInfo);
		gameInfo.setLayout(null);
		scrollPane.setBounds(0, 0, 264, 234);

		gameInfo.add(scrollPane);
		scrollPane.setViewportView(output);
		output.setEditable(false);
		output.setWrapStyleWord(true);
		output.setLineWrap(true);
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "Move", "Shoot", "Look" }));
		comboBox.setBounds(756, 269, 133, 27);

		contentPane.add(comboBox);
		label.setBounds(613, 349, 64, 64);

		contentPane.add(label);
		label.setIcon(new ImageIcon(playerImg));
		bullets.setIcon(new ImageIcon(bulletImg));
		ninja.setIcon(new ImageIcon(ninjaImg));
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
		mntmHardMode.setAction(action);

		mnEdit.add(mntmHardMode);
		txtpnInstructions.setFont(new Font("Prestige Elite Std", Font.PLAIN, 13));
		txtpnInstructions.setEditable(false);
		txtpnInstructions
				.setText("Arrow Keys = Move\nS = Shoot\nL = Look\nPress Tab a couple times if keys stop working");
		txtpnInstructions.setBounds(621, 504, 268, 91);

		contentPane.add(txtpnInstructions);
		heart3.setBounds(592, 345, 25, 25);

		contentPane.add(heart3);
		heart2.setBounds(592, 372, 25, 25);

		contentPane.add(heart2);
		heart1.setBounds(592, 399, 25, 25);

		contentPane.add(heart1);
		bullets.setBounds(794, 345, 64, 64);
		
		contentPane.add(bullets);
		bulletCount.setFont(new Font("Prestige Elite Std", Font.PLAIN, 13));
		bulletCount.setBounds(804, 408, 61, 16);
		
		contentPane.add(bulletCount);
		ninja.setBounds(696, 345, 64, 64);
		
		contentPane.add(ninja);
		ninjaCount.setFont(new Font("Prestige Elite Std", Font.PLAIN, 13));
		ninjaCount.setBounds(706, 408, 61, 16);
		
		contentPane.add(ninjaCount);
		lbllevel.setFont(new Font("Prestige Elite Std", Font.PLAIN, 21));
		lbllevel.setBounds(631, 280, 144, 29);
		
		contentPane.add(lbllevel);
		moveCount.setFont(new Font("Prestige Elite Std", Font.PLAIN, 21));
		moveCount.setBounds(631, 308, 144, 29);
		
		contentPane.add(moveCount);

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
		bulletCount.setText("x" + gameEngine.getPlayer().getNumBullets());
		if (debug)
			bulletCount.setText("x999");
		ninjaCount.setText("x" + gameEngine.getNumNinjas());
		lbllevel.setText("Level:" + (gameEngine.getAmountNinjas()-5));
		moveCount.setText("Moves:" + gameEngine.getMoveCount());
		lifeTracker();
		table.repaint();
	}

	public void lifeTracker() {
		switch (gameEngine.getPlayer().getNumLives()) {
		case 0:
			heart1.setIcon(new ImageIcon(noImg));
			heart2.setIcon(new ImageIcon(noImg));
			heart3.setIcon(new ImageIcon(noImg));
			break;
		case 1:
			heart1.setIcon(new ImageIcon(heartImg));
			heart2.setIcon(new ImageIcon(noImg));
			heart3.setIcon(new ImageIcon(noImg));
			break;
		case 2:
			heart1.setIcon(new ImageIcon(heartImg));
			heart2.setIcon(new ImageIcon(heartImg));
			heart3.setIcon(new ImageIcon(noImg));
			break;
		case 3:
			heart1.setIcon(new ImageIcon(heartImg));
			heart2.setIcon(new ImageIcon(heartImg));
			heart3.setIcon(new ImageIcon(heartImg));
			break;
		}
	}

	public void nextLevel() {
		level++;
		gameEngine = new Game(level + 5);
		table = new JTable(gameEngine.getGameMap().getGrid(), columns);
		initGui();
		resetTicks();
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
		case KeyEvent.VK_F5:
			newGame();
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
		System.out.println("\nNew Game Started");
		level = 1;
		gameEngine = new Game(6);
		table = new JTable(gameEngine.getGameMap().getGrid(), columns);
		initGui();
		resetTicks();
	}

	private void resetTicks() {
		if (debug) {
			rdbtnmntmDebugMode.setSelected(true);
			gameEngine.showAll();
		}
		if (vis) {
			rdbtnmntmMoreVision.setSelected(true);
			gameEngine.setDiagonalVision(true);
		}
		if (hardMode) {
			mntmHardMode.setSelected(true);
			gameEngine.setNinjaAI(true);
		}
		if (!debug)
			rdbtnmntmDebugMode.setSelected(false);
		if (!vis)
			rdbtnmntmMoreVision.setSelected(false);
		if (!hardMode)
			gameEngine.setNinjaAI(false);
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
				resetTicks();
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
			putValue(NAME, "New Game (F5)");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}

		public void actionPerformed(ActionEvent e) {
			newGame();
		}
	}

	private class SwingAction_8 extends AbstractAction {
		public SwingAction_8() {
			putValue(NAME, "Alternate Vision");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}

		public void actionPerformed(ActionEvent e) {
			if (!vis) {
				gameEngine.setDiagonalVision(true);
				vis = true;
			} else {
				gameEngine.setDiagonalVision(false);
				vis = false;
			}
			gameEngine.vision();
			refresh();
		}
	}

	private class SwingAction_9 extends AbstractAction {
		public SwingAction_9() {
			putValue(NAME, "Hard Mode");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}

		public void actionPerformed(ActionEvent e) {
			if (!hardMode) {
				gameEngine.setNinjaAI(true);
				hardMode = true;
			} else {
				gameEngine.setNinjaAI(false);
				hardMode = false;
			}
			gameEngine.vision();
			refresh();
		}
	}
}
