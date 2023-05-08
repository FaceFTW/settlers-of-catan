package catan.gui;
import java.awt.FlowLayout;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import catan.Coordinate;
import catan.Game;
import catan.gui.components.CoordinateButton;

//CHECKSTYLE:OFF: checkstyle:magicnumber
public class CatanWindow {
	private Game game;
	public static final int DEFAULT_WIDTH = 800;
	public static final int DEFAULT_HEIGHT = 600;
	private volatile Coordinate pos1 = null;
	private volatile Coordinate pos2 = null;

	// Synchronization Objects - BE CAREFUL HERE
	private Thread gameActionThread; // Null by default, set to the thread that is running the game action
	private CountDownLatch latch;

	// Swing Components below here
	private JFrame frame;
	private BoardPanel boardPanel;
	private List<CoordinateButton> buttons = new ArrayList<CoordinateButton>();
	private JLabel label;

	private JButton startSyncTestButton = new JButton();
	private JButton cancelButton = new JButton("Cancel");

	public CatanWindow() throws FileNotFoundException {
		frame = new JFrame("Catan");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		FlowLayout layout = new FlowLayout();

		frame.setLayout(layout);

		game = new Game();

		boardPanel = new BoardPanel(game);
	}

	/**
	 * Arranges the components of the main Catan Window, then displays it.
	 */
	public void setupLayout() {
		this.boardPanel = new BoardPanel();
		frame.add(boardPanel);
		label = new JLabel("press the button below to start the sync test");
		frame.add(label);

		startSyncTestButton.setText("Start Sync Test");
		startSyncTestButton.setSize(40, 20);
		startSyncTestButton.addActionListener(e -> {
			frame.remove(startSyncTestButton);
			cancelButton.setEnabled(true);
			this.gameActionThread = new Thread(() -> {
				coordinateButtonSyncTests();

				cancelButton.setEnabled(false);
			});
			gameActionThread.start();
		});

		frame.add(startSyncTestButton);

		cancelButton.addActionListener(e -> {
			if (gameActionThread != null) {
				gameActionThread.interrupt();
			}
		});
		frame.add(cancelButton);

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

	private void updateCoordinateButtonStates() {
		this.latch = new CountDownLatch(2);
		for (CoordinateButton button : boardPanel.getButtons()) {
			button.addActionListener(e -> {
				System.out.println("Button Pressed: " + button.getCoordinate().toString());
				if (pos1 == null) {
					pos1 = button.getCoordinate();
				} else if (pos2 == null) {
					pos2 = button.getCoordinate();
				}
				latch.countDown();
			});
		}
	}

	private void coordinateButtonSyncTests() {

		updateCoordinateButtonStates();
		label.setText("Click on two buttons to test latch synchronization.");
		while (pos1 == null || pos2 == null) {
			try {
				latch.await();
			} catch (InterruptedException e) {
				label.setText("Test Cancelled");
				Thread.currentThread().interrupt();
			}
		}

		label.setText("Coordinate 1: " + pos1.toString() + " Coordinate 2: " + pos2.toString());
	}


}
