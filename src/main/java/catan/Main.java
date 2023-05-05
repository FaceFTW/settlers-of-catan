package catan;

import catan.gui.CatanWindow;

/**
 * The "boostrap" class for the application. This class is responsible for
 * spawning the main window and initializing the game.
 *
 */
public class Main {
	protected Main() {
		// Do nothing
	} // Prevent instantiation

	public static void main(String[] args) {
		CatanWindow window = new CatanWindow();
		window.setupLayout();
	}
}
