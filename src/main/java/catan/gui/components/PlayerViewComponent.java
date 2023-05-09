package catan.gui.components;

import static catan.gui.LangUtils.getString;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import catan.data.Player;
import catan.data.ResourceType;

//CHECKSTYLE:OFF: checkstyle:magicnumber
public class PlayerViewComponent extends JPanel {
	// private static boolean isCurrentPlayer = false;
	private boolean isDev = false;
	private Player playerRef; // Expect this ref to be passed by ctor

	private JLabel woodLabel;
	private JLabel brickLabel;
	private JLabel sheepLabel;
	private JLabel wheatLabel;
	private JLabel oreLabel;
	private JLabel victoryPointsLabel;

	private JButton woodPlusButton;
	private JButton woodMinusButton;
	private JButton brickPlusButton;
	private JButton brickMinusButton;
	private JButton sheepPlusButton;
	private JButton sheepMinusButton;
	private JButton wheatPlusButton;
	private JButton wheatMinusButton;
	private JButton orePlusButton;
	private JButton oreMinusButton;

	public PlayerViewComponent(Player playerRef, boolean isDev) {
		super();
		this.playerRef = playerRef;
		this.isDev = isDev;

		GridLayout gridLayout = new GridLayout(7, isDev ? 3 : 1);
		this.setLayout(gridLayout);
	}

	public void setupLayout() {
		this.woodLabel = new JLabel(getString("woodCount", playerRef.getResourceCount(ResourceType.WOOD)));
		this.add(woodLabel);
		if (isDev) {
			this.woodPlusButton = new JButton("+");
			woodPlusButton.addActionListener(e -> {
				playerRef.modifyResource(ResourceType.WOOD, 1);
				update();
			});
			this.woodMinusButton = new JButton("-");
			woodMinusButton.addActionListener(e -> {
				playerRef.modifyResource(ResourceType.WOOD, -1);
				update();
			});
			this.add(woodPlusButton);
			this.add(woodMinusButton);
		}

		this.brickLabel = new JLabel(getString("brickCount", playerRef.getResourceCount(ResourceType.BRICK)));
		this.add(brickLabel);
		if (isDev) {
			this.brickPlusButton = new JButton("+");
			brickPlusButton.addActionListener(e -> {
				playerRef.modifyResource(ResourceType.BRICK, 1);
				update();
			});
			this.brickMinusButton = new JButton("-");
			brickMinusButton.addActionListener(e -> {
				playerRef.modifyResource(ResourceType.BRICK, -1);
				update();
			});
			this.add(brickPlusButton);
			this.add(brickMinusButton);
		}

		this.sheepLabel = new JLabel(getString("sheepCount", playerRef.getResourceCount(ResourceType.SHEEP)));
		this.add(sheepLabel);
		if (isDev) {
			this.sheepPlusButton = new JButton("+");
			sheepPlusButton.addActionListener(e -> {
				playerRef.modifyResource(ResourceType.SHEEP, 1);
				update();
			});
			this.sheepMinusButton = new JButton("-");
			sheepMinusButton.addActionListener(e -> {
				playerRef.modifyResource(ResourceType.SHEEP, -1);
				update();
			});
			this.add(sheepPlusButton);
			this.add(sheepMinusButton);
		}

		this.wheatLabel = new JLabel(getString("wheatCount", playerRef.getResourceCount(ResourceType.WHEAT)));
		this.add(wheatLabel);
		if (isDev) {
			this.wheatPlusButton = new JButton("+");
			wheatPlusButton.addActionListener(e -> {
				playerRef.modifyResource(ResourceType.WHEAT, 1);
				update();
			});
			this.wheatMinusButton = new JButton("-");
			wheatMinusButton.addActionListener(e -> {
				playerRef.modifyResource(ResourceType.WHEAT, -1);
				update();
			});
			this.add(wheatPlusButton);
			this.add(wheatMinusButton);
		}

		this.oreLabel = new JLabel(getString("oreCount", playerRef.getResourceCount(ResourceType.ORE)));
		this.add(oreLabel);
		if (isDev) {
			this.orePlusButton = new JButton("+");
			orePlusButton.addActionListener(e -> {
				playerRef.modifyResource(ResourceType.ORE, 1);
				update();
			});
			this.oreMinusButton = new JButton("-");
			oreMinusButton.addActionListener(e -> {
				playerRef.modifyResource(ResourceType.ORE, -1);
				update();
			});
			this.add(orePlusButton);
			this.add(oreMinusButton);
		}

		this.add(new JLabel(""));
		if (isDev) {
			this.add(new JLabel("")); // Empty label
			this.add(new JLabel("")); // Empty label
		}

		this.victoryPointsLabel = new JLabel(getString("victoryPoints", playerRef.getVictoryPoints()));
		this.add(victoryPointsLabel);
	}

	/**
	 * Acts as a Redraw function for the player view component.
	 */
	public void update() {
		this.woodLabel.setText(getString("woodCount", playerRef.getResourceCount(ResourceType.WOOD)));
		this.brickLabel.setText(getString("brickCount", playerRef.getResourceCount(ResourceType.BRICK)));
		this.sheepLabel.setText(getString("sheepCount", playerRef.getResourceCount(ResourceType.SHEEP)));
		this.wheatLabel.setText(getString("wheatCount", playerRef.getResourceCount(ResourceType.WHEAT)));
		this.oreLabel.setText(getString("oreCount", playerRef.getResourceCount(ResourceType.ORE)));

		this.victoryPointsLabel.setText(getString("victoryPoints", playerRef.getVictoryPoints()));
	}

}
