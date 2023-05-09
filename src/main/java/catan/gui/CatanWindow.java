package catan.gui;

import javax.imageio.ImageIO;
import javax.swing.*;

import catan.Coordinate;
import catan.Game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.CertPath;

public class CatanWindow {
	private Game game;
	public static final int DEFAULT_WIDTH = 800;
	public static final int DEFAULT_HEIGHT = 600;


	// Swing Components below here
	private JFrame frame;
	private BoardPanel boardPanel;

	public CatanWindow() throws FileNotFoundException {
		frame = new JFrame("Catan");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

		game = new Game();
		boardPanel = new BoardPanel(game);
	}

	/**
	 * Arranges the components of the main Catan Window, then displays it.
	 */
	public void setupLayout() {
		// Add Layout Code As Necessary
		frame.add(boardPanel);
		frame.pack();
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
