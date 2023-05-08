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
		this.playerRef = playerRef;
		this.isDev = isDev;

		GridLayout gridLayout = new GridLayout(7, 1);
		this.setLayout(gridLayout);

		this.add(woodLabel);
		this.add(brickLabel);
		this.add(sheepLabel);
		this.add(wheatLabel);
		this.add(oreLabel);

		this.add(new JLabel(""));
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
