package catan.gui;

import static catan.gui.LangUtils.getString;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import catan.Coordinate;
import catan.Game;
import catan.gui.components.CoordinateButton;
import catan.gui.components.PlayerViewComponent;

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
	private JLabel currentTurnLabel;

	// private JButton startSyncTestButton = new JButton();
	private JButton cancelButton = new JButton("Cancel");

	private JPanel actionsPanel = new JPanel();
	private JButton endTurnButton = new JButton(getString("endTurn"));
	private JButton buildSettlementButton = new JButton(getString("buildSettlement"));
	private JButton buildRoadButton = new JButton(getString("buildRoad"));
	private JButton upgradeSettlementButton = new JButton(getString("upgradeSettlement"));
	private JButton requestTradeButton = new JButton(getString("requestTrade"));
	private JButton exchangeResourcesButton = new JButton(getString("exchangeResources"));

	private JPanel playerViewPanel = new JPanel();
	private List<PlayerViewComponent> playerViews = new ArrayList<PlayerViewComponent>();

	public CatanWindow() {
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
		setupActionsPanel();
		frame.add(actionsPanel);

		this.currentTurnLabel = new JLabel(getString("currentTurn", game.getTurn()));
		frame.add(currentTurnLabel);

		this.boardPanel = new BoardPanel();
		frame.add(boardPanel);
		label = new JLabel("press the button below to start the sync test");
		frame.add(label);

		cancelButton.addActionListener(e -> {
			if (gameActionThread != null) {
				gameActionThread.interrupt();
			}
		});
		frame.add(cancelButton);

		playerViewPanel = new JPanel();
		for (int i = 1; i <= Game.DEFAULT_NUM_PLAYERS; i++) {
			PlayerViewComponent playerView = new PlayerViewComponent(game.getPlayer(i), true);
			playerView.setupLayout();
			playerViews.add(playerView);
			playerViewPanel.add(playerView);
		}

		playerViewPanel.setLayout(new FlowLayout());
		frame.add(playerViewPanel);

		frame.pack();
		frame.setVisible(true);
	}

	private void setupActionsPanel() {
		actionsPanel.setLayout(new GridLayout(3, 2));

		actionsPanel.add(buildRoadButton);

		actionsPanel.add(buildSettlementButton);

		actionsPanel.add(upgradeSettlementButton);

		actionsPanel.add(requestTradeButton);

		actionsPanel.add(exchangeResourcesButton);

		this.endTurnButton.addActionListener(e -> {
			synchronized (game) {
				System.out.println("Ending turn");
				game.nextTurn();
			}
			update();
		});
		actionsPanel.add(endTurnButton);

	}

	/**
	 * Returns the game object, useful for extracting state information
	 *
	 * @return
	 */
	public Game getGame() {
		return game;
	}

	private void update() {
		for (PlayerViewComponent playerView : playerViews) {
			playerView.update();
		}

		this.currentTurnLabel.setText(getString("currentTurn", game.getTurn()));
	}

	private void updateCoordinateButtonStates(int count) {
		this.latch = new CountDownLatch(count);
		for (CoordinateButton button : boardPanel.getButtons()) {
			button.addActionListener(e -> {
				if (pos1 == null) {
					pos1 = button.getCoordinate();
					this.label.setText(getString("selectSecondCoord"));
				} else if (pos2 == null) {
					pos2 = button.getCoordinate();
				}
				latch.countDown();
			});
		}
	}

}
