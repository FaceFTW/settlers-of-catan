package catan.gui;

import javax.swing.JFrame;

import catan.Game;

public class CatanWindow {
	private Game game;
	public static final int DEFAULT_WIDTH = 800;
	public static final int DEFAULT_HEIGHT = 600;

	// Swing Components below here
	private JFrame frame;

	public CatanWindow() {
		frame = new JFrame("Catan");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

		game = new Game();

	}

	/**
	 * Arranges the components of the main Catan Window, then displays it.
	 */
	public void setupLayout() {
		// Add Layout Code As Necessary
		frame.setVisible(true);
	}

	/**
	 * Returns the game object, useful for extracting state information
	 *
	 * @return
	 */
	public Game getGame() {
		return game;
	}

}
