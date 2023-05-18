package catan.gui;

import static catan.gui.LangUtils.getString;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import catan.data.Player;
import catan.data.ResourceType;
import catan.gui.components.BankDialog;
import catan.gui.components.CoordinateButton;
import catan.gui.components.PlayerViewComponent;
import catan.gui.components.TradingDialog;
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
	private boolean gameStarted = false;
	private boolean inSetup = false;
	private int currentLongestRoad = -1;

	// Synchronization Objects - BE CAREFUL HERE
	private Thread gameActionThread;
	// Null by default, set to the thread that is running the game action
	private CountDownLatch latch;

	// Swing Components below here
	private JFrame frame;
	private BoardPanel boardPanel;
	private JLabel label;
	private JLabel currentTurnLabel;
	private JLabel longestRoadLabel;

	private JPanel actionsPanel = new JPanel(new BorderLayout());
	private JButton startGameButton = new JButton(getString("startGame"));
	private JPanel gameActionsPanel = new JPanel();
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

		JPanel turnStatusPanel = new JPanel(new GridLayout(1, 2));
		this.currentTurnLabel = new JLabel(getString("currentTurn", game.getTurn()));
		currentTurnLabel.setHorizontalAlignment(JLabel.CENTER);
		turnStatusPanel.add(currentTurnLabel);
		this.longestRoadLabel = new JLabel(
				getString("longestRoad",
						currentLongestRoad == -1
								? getString("none")
								: getString("player" + currentLongestRoad)));
		longestRoadLabel.setHorizontalAlignment(JLabel.CENTER);
		turnStatusPanel.add(longestRoadLabel);
		constraints.gridy = GridBagConstraints.RELATIVE;
		constraints.gridwidth = 1;
		constraints.ipady = 5;
		constraints.anchor = GridBagConstraints.CENTER;
		sidebarPanel.add(turnStatusPanel, constraints);


		label = new JLabel(getString("selectAction"));
		label.setHorizontalAlignment(JLabel.CENTER);

		sidebarPanel.add(label, constraints);

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

		frame.add(sidebarPanel, BorderLayout.EAST);

		frame.pack();
		frame.setVisible(true);
	}

	private void setupGameActionsPanel() {
		gameActionsPanel.setLayout(new GridLayout(4, 2));

		setCoordButtonAction(buildRoadButton, this::buildRoadAction);
		buildRoadButton.setEnabled(false);
		gameActionsPanel.add(buildRoadButton);

		setCoordButtonAction(buildSettlementButton, this::buildSettlementAction);
		buildSettlementButton.setEnabled(false);
		gameActionsPanel.add(buildSettlementButton);

		setCoordButtonAction(upgradeSettlementButton, this::upgradeSettlementAction);
		upgradeSettlementButton.setEnabled(false);
		gameActionsPanel.add(upgradeSettlementButton);

		requestTradeButton.setEnabled(false);
		requestTradeButton.addActionListener(e -> {
			Frame dialogFrame = JOptionPane.getFrameForComponent(frame);
			new TradingDialog(dialogFrame, game, this::update);
		});
		gameActionsPanel.add(requestTradeButton);

		exchangeResourcesButton.setEnabled(false);
		exchangeResourcesButton.addActionListener(e -> {
			Frame dialogFrame = JOptionPane.getFrameForComponent(frame);
			new BankDialog(dialogFrame, game, this::update);
		});
		gameActionsPanel.add(exchangeResourcesButton);

		endTurnButton.addActionListener(e -> {
			gameActionThread = new Thread(() -> {
				endTurnAction();
			});
			gameActionThread.start();
		});
		endTurnButton.setEnabled(false);
		gameActionsPanel.add(endTurnButton);

		rollDieButton.addActionListener(e -> {
			dieRoll = game.rollDie();
			latch.countDown(); // Latch used to trigger remaining transition
		});
		rollDieButton.setEnabled(false);
		gameActionsPanel.add(rollDieButton);

		cancelButton.addActionListener(e -> {
			if (gameActionThread != null) {
				label.setText(getString("cancelledAction"));
				this.pos1 = null;
				this.pos2 = null;
				gameActionThread.interrupt();
			}
		});
		cancelButton.setEnabled(false);
		gameActionsPanel.add(cancelButton);
	}

	private void setupActionsPanel() {
		startGameButton.addActionListener(e -> {
			if (gameStarted) {
				int option = JOptionPane.showConfirmDialog(frame, getString("gameAlreadyStarted"));
				if (option != JOptionPane.YES_OPTION) {
					return;
				}
			}
			gameSetupAction();
		});
		actionsPanel.add(startGameButton, BorderLayout.NORTH);

		setupGameActionsPanel();
		actionsPanel.add(gameActionsPanel, BorderLayout.SOUTH);
	}

	private void update() {
		game.getBoard().updateLongestRoad();
		int newLongestRoad = game.getBoard().getLongestRoadOwnerID();
		if (newLongestRoad != currentLongestRoad) {
			if (currentLongestRoad != -1) {
				int prevPlayerVicPoints = game.getPlayer(newLongestRoad).getVictoryPoints();
				game.getPlayer(currentLongestRoad).setVictoryPoints(prevPlayerVicPoints - 2);
			}
			int newPlayerVicPoints = game.getPlayer(newLongestRoad).getVictoryPoints();
			game.getPlayer(newLongestRoad).setVictoryPoints(newPlayerVicPoints + 2);
			this.currentLongestRoad = newLongestRoad;
			this.longestRoadLabel.setText(
					getString("longestRoad",
							currentLongestRoad == -1
									? getString("none")
									: getString("player" + currentLongestRoad)));
		}

		for (PlayerViewComponent playerView : playerViews) {
			playerView.update();
		}
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
			success = game.buildSettlement(game.getTurn(), pos1, inSetup);
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

	/**
	 * Specialized action for game setup, runs in a separate thread
	 * only local to this method
	 */
	private void gameSetupAction() {

		this.gameStarted = true;
		this.inSetup = true;
		this.game.reset();
		this.update();
		startGameButton.setText(getString("restartGame"));
		this.boardPanel.showCornerButtons();

		// first pass
		Thread startThread = new Thread(() -> {
			try {
				gameInit_FirstPass();
				gameInit_SecondPass();
			} catch (InterruptedException e) {
				JOptionPane.showMessageDialog(frame, getString("fatalError"));
				e.printStackTrace();
			}

			this.boardPanel.hideCornerButtons();
			this.inSetup = false;
			game.setTurn(Game.DEFAULT_NUM_PLAYERS); // jumps back to first in endTurnAction

			// trigger roll die
			this.gameActionThread = new Thread(() -> {
				endTurnAction();
			});
			this.gameActionThread.start();

		});
		startThread.start();
	}

	private void gameInit_FirstPass() throws InterruptedException {
		for (int i = 1; i <= Game.DEFAULT_NUM_PLAYERS; i++) {
			Player p = game.getPlayer(i);
			p.modifyResource(ResourceType.BRICK, 4);
			p.modifyResource(ResourceType.WOOD, 4);
			p.modifyResource(ResourceType.SHEEP, 2);
			p.modifyResource(ResourceType.WHEAT, 2);

			gameActionThread = new Thread(() -> {
				buildSettlementAction();
				update();
				buildRoadAction();
				update();
				game.nextTurn();
			});

			gameActionThread.start();
			gameActionThread.join();
		}
	}

	private void gameInit_SecondPass() throws InterruptedException {
		for (int i = Game.DEFAULT_NUM_PLAYERS; i >= 1; i--) {
			game.setTurn(i);
			gameActionThread = new Thread(() -> {
				buildSettlementAction();
				// TODO Give resources
				update();
				buildRoadAction();
				update();
				if (game.getTurn() != 1) {
					game.nextTurn();
				}
			});

			gameActionThread.start();
			gameActionThread.join();
		}
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
