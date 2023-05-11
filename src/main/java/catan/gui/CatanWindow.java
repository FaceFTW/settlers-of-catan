package catan.gui;

import static catan.gui.LangUtils.getString;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import catan.gui.components.CoordinateButton;
import catan.gui.components.DevPanel;
import catan.gui.components.PlayerViewComponent;
import catan.logic.Coordinate;
import catan.logic.Game;

//CHECKSTYLE:OFF: checkstyle:magicnumber
public class CatanWindow {
	private Game game;
	public static final int DEFAULT_WIDTH = 800;
	public static final int DEFAULT_HEIGHT = 600;
	private Coordinate pos1 = null;
	private Coordinate pos2 = null;
	private int dieRoll = 0;

	// Synchronization Objects - BE CAREFUL HERE
	private Thread gameActionThread; // Null by default, set to the thread that is running the game action
	private CountDownLatch latch;

	// Swing Components below here
	private JFrame frame;
	private BoardPanel boardPanel;
	private JLabel label;
	private JLabel currentTurnLabel;

	private JPanel actionsPanel = new JPanel();
	private JButton endTurnButton = new JButton(getString("endTurn"));
	private JButton buildSettlementButton = new JButton(getString("buildSettlement"));
	private JButton buildRoadButton = new JButton(getString("buildRoad"));
	private JButton upgradeSettlementButton = new JButton(getString("upgradeSettlement"));
	private JButton requestTradeButton = new JButton(getString("requestTrade"));
	private JButton exchangeResourcesButton = new JButton(getString("exchangeResources"));
	private JButton rollDieButton = new JButton(getString("rollDie"));
	private JButton cancelButton = new JButton(getString("cancelButton"));

	private JPanel playerViewPanel = new JPanel();
	private List<PlayerViewComponent> playerViews = new ArrayList<PlayerViewComponent>();

	private JPanel sidebarPanel = new JPanel(new GridBagLayout());
	private GridBagConstraints constraints = new GridBagConstraints();

	private DevPanel devPanel;

	public CatanWindow() {
		frame = new JFrame("Catan");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		frame.setLayout(new BorderLayout());
		game = new Game();
		boardPanel = new BoardPanel(game);
	}

	/**
	 * Arranges the components of the main Catan Window, then displays it.
	 */
	public void setupLayout() {
		setupActionsPanel();
		constraints.anchor = GridBagConstraints.NORTH;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 0;
		sidebarPanel.add(actionsPanel, constraints);

		this.currentTurnLabel = new JLabel(getString("currentTurn", game.getTurn()));
		constraints.gridy = GridBagConstraints.RELATIVE;
		constraints.gridwidth = 1;
		constraints.ipady = 20;
		constraints.anchor = GridBagConstraints.CENTER;

		sidebarPanel.add(currentTurnLabel, constraints);

		playerViewPanel.setLayout(new GridLayout(2, 2));
		for (int i = 1; i <= Game.DEFAULT_NUM_PLAYERS; i++) {
			PlayerViewComponent playerView = new PlayerViewComponent(game.getPlayer(i), true);
			playerView.setupLayout();
			playerViews.add(playerView);
			playerViewPanel.add(playerView);
		}
		sidebarPanel.add(playerViewPanel, constraints);

		this.boardPanel = new BoardPanel(game);
		for (CoordinateButton button : boardPanel.getButtons()) {
			if (button.getActionListeners().length == 0) {
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
		this.boardPanel.hideCornerButtons();
		frame.add(boardPanel, BorderLayout.CENTER);

		label = new JLabel(getString("selectAction"));
		frame.add(label, BorderLayout.NORTH);

		frame.add(sidebarPanel, BorderLayout.EAST);

		devPanel = new DevPanel(game);
		frame.add(devPanel, BorderLayout.SOUTH);

		frame.pack();
		frame.setVisible(true);
	}

	private void setupActionsPanel() {
		actionsPanel.setLayout(new GridLayout(4, 2));

		setCoordButtonAction(buildRoadButton, this::buildRoadAction);
		actionsPanel.add(buildRoadButton);

		setCoordButtonAction(buildSettlementButton, this::buildSettlementAction);
		actionsPanel.add(buildSettlementButton);

		setCoordButtonAction(upgradeSettlementButton, this::upgradeSettlementAction);
		actionsPanel.add(upgradeSettlementButton);

		actionsPanel.add(requestTradeButton);

		actionsPanel.add(exchangeResourcesButton);

		this.endTurnButton.addActionListener(e -> {
			gameActionThread = new Thread(() -> {
				endTurnAction();
			});
			gameActionThread.start();
		});
		actionsPanel.add(endTurnButton);

		rollDieButton.addActionListener(e -> {
			dieRoll = game.rollDie();
			latch.countDown(); // Latch used to trigger remaining transition
		});
		rollDieButton.setEnabled(false);
		actionsPanel.add(rollDieButton);

		cancelButton.addActionListener(e -> {
			if (gameActionThread != null) {
				label.setText(getString("cancelledAction"));
				this.pos1 = null;
				this.pos2 = null;
				gameActionThread.interrupt();
			}
		});
		cancelButton.setEnabled(false);
		actionsPanel.add(cancelButton);
	}

	private void update() {
		for (PlayerViewComponent playerView : playerViews) {
			playerView.update();
		}
		devPanel.update();
		boardPanel.repaint();
		this.currentTurnLabel.setText(getString("currentTurn", game.getTurn()));
	}

	//////////////////////////////////////////////
	// Utilities - ActionListeners & Game Actions
	//////////////////////////////////////////////
	/**
	 * Sets the ActionListener for a button to run the given game action
	 * that uses the coordinate functionaltiy in a separate thread based on a
	 * template.
	 *
	 * @param button The button to set the action for
	 * @param action The action to run, use a method reference
	 */
	private void setCoordButtonAction(JButton button, Runnable action) {
		button.addActionListener(e -> {
			gameActionThread = new Thread(() -> {
				// Pre Action Steps;
				this.cancelButton.setEnabled(true);
				this.boardPanel.showCornerButtons();
				// Execute Action
				action.run();
				// Post Action Steps;
				update();
				this.cancelButton.setEnabled(false);
				this.boardPanel.hideCornerButtons();
			});
			gameActionThread.start();
		});
	}

	private void regenActionLatch(int count) {
		this.pos1 = null;
		this.pos2 = null;
		this.latch = new CountDownLatch(count);
	}

	//////////////////////////////////////////////
	// Game Actions - Run in separate thread
	//////////////////////////////////////////////
	private void buildRoadAction() {
		boolean success = false;
		this.label.setText(getString("selectFirstCoord"));
		while (!success) {
			regenActionLatch(2);
			try {
				latch.await();
			} catch (InterruptedException e) {
				return;
			}
			success = game.buildRoad(game.getTurn(), pos1, pos2);
			if (!success) {
				this.label.setText(getString("invalidRoad"));
			}
			this.pos1 = null;
			this.pos2 = null;
		}

		this.label.setText(getString("roadSuccessful"));
	}

	private void buildSettlementAction() {
		boolean success = false;
		this.label.setText(getString("selectFirstCoord"));
		while (!success) {
			regenActionLatch(1);
			try {
				latch.await();
			} catch (InterruptedException e) {
				return;
			}
			success = game.buildSettlement(game.getTurn(), pos1);
			if (!success) {
				this.label.setText(getString("invalidSettlement"));
				this.pos1 = null;
			}
		}

		this.label.setText(getString("settlementSuccessful"));
	}

	private void upgradeSettlementAction() {
		boolean success = false;
		this.label.setText(getString("selectFirstCoord"));
		while (!success) {
			regenActionLatch(1);
			try {
				latch.await();
			} catch (InterruptedException e) {
				return;
			}
			success = game.upgradeSettlement(game.getTurn(), pos1);
			if (!success) {
				this.label.setText(getString("invalidUpgrade"));
				this.pos1 = null;
			}
		}

		this.label.setText(getString("upgradeSuccessful"));
	}

	private void endTurnAction() {
		game.nextTurn();

		// Disable all buttons except roll die
		this.buildRoadButton.setEnabled(false);
		this.buildSettlementButton.setEnabled(false);
		this.upgradeSettlementButton.setEnabled(false);
		this.requestTradeButton.setEnabled(false);
		this.exchangeResourcesButton.setEnabled(false);
		this.endTurnButton.setEnabled(false);
		this.cancelButton.setEnabled(false);
		this.rollDieButton.setEnabled(true);
		this.label.setText(getString("rollDie", game.getTurn()));

		regenActionLatch(1);
		boolean occurred = false;
		while (!occurred) {
			try {
				latch.await();
				occurred = true;
			} catch (InterruptedException e) {
				return;
			}
		}
		this.label.setText(getString("rollResult", game.getTurn(), this.dieRoll));
		game.distributeResources(dieRoll);
		update();

		// Enable all buttons except roll die
		this.buildRoadButton.setEnabled(true);
		this.buildSettlementButton.setEnabled(true);
		this.upgradeSettlementButton.setEnabled(true);
		this.requestTradeButton.setEnabled(true);
		this.exchangeResourcesButton.setEnabled(true);
		this.endTurnButton.setEnabled(true);
		this.rollDieButton.setEnabled(false);
	}

	//////////////////////////////////////////////
	// Getters/Setters
	//////////////////////////////////////////////
	/**
	 * Returns the game object, useful for extracting state information
	 *
	 * @return
	 */
	public Game getGame() {
		return game;
	}

}
