package catan;

import catan.gui.CatanWindow;

import java.io.FileNotFoundException;

/**
 * The "boostrap" class for the application. This class is responsible for
 * spawning the main window and initializing the game.
 *
 */
public class Main {
	protected Main() {
		// Do nothing
	} // Prevent instantiation

	public static void main(String[] args) throws FileNotFoundException {
		CatanWindow window = new CatanWindow();
		window.setupLayout();
	}
}
