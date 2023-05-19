package catan.gui.components;

import static catan.gui.LangUtils.getString;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import catan.data.Player;
import catan.data.ResourceType;

//CHECKSTYLE:OFF: checkstyle:magicnumber
public class PlayerViewComponent extends JPanel {
	// private static boolean isCurrentPlayer = false;
	private boolean isDev = false;
	private Player playerRef; // Expect this ref to be passed by ctor

	private JLabel woodLabel;
	private ImageIcon woodIcon;
	private JLabel brickLabel;
	private ImageIcon brickIcon;
	private JLabel sheepLabel;
	private ImageIcon sheepIcon;
	private JLabel wheatLabel;
	private ImageIcon wheatIcon;
	private JLabel oreLabel;
	private ImageIcon oreIcon;
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

		// Loading Images here to reduce coupling, feels messy otherwise
		ClassLoader cl = getClass().getClassLoader();
		try {
			BufferedImage woodImg = ImageIO.read(cl.getResourceAsStream("image/card_wood.png"));
			BufferedImage resizedWoodImg = new BufferedImage(20, 20, BufferedImage.TYPE_INT_ARGB);
			Graphics g = resizedWoodImg.createGraphics();
			g.drawImage(woodImg, 0, 0, 20, 40, null);
			g.dispose();
			woodIcon = new ImageIcon(resizedWoodImg);

			BufferedImage brickImg = ImageIO.read(cl.getResourceAsStream("image/card_brick.png"));
			BufferedImage resizedBrickImg = new BufferedImage(20, 20, BufferedImage.TYPE_INT_ARGB);
			g = resizedBrickImg.createGraphics();
			g.drawImage(brickImg, 0, 0, 20, 40, null);
			g.dispose();
			brickIcon = new ImageIcon(resizedBrickImg);

			BufferedImage sheepImg = ImageIO.read(cl.getResourceAsStream("image/card_sheep.png"));
			BufferedImage resizedSheepImg = new BufferedImage(20, 20, BufferedImage.TYPE_INT_ARGB);
			g = resizedSheepImg.createGraphics();
			g.drawImage(sheepImg, 0, 0, 20, 40, null);
			g.dispose();
			sheepIcon = new ImageIcon(resizedSheepImg);

			BufferedImage wheatImg = ImageIO.read(cl.getResourceAsStream("image/card_wheat.png"));
			BufferedImage resizedWheatImg = new BufferedImage(20, 20, BufferedImage.TYPE_INT_ARGB);
			g = resizedWheatImg.createGraphics();
			g.drawImage(wheatImg, 0, 0, 20, 40, null);
			g.dispose();
			wheatIcon = new ImageIcon(resizedWheatImg);

			BufferedImage oreImg = ImageIO.read(cl.getResourceAsStream("image/card_ore.png"));
			BufferedImage resizedOreImg = new BufferedImage(20, 20, BufferedImage.TYPE_INT_ARGB);
			g = resizedOreImg.createGraphics();
			g.drawImage(oreImg, 0, 0, 20, 40, null);
			g.dispose();
			oreIcon = new ImageIcon(resizedOreImg);
		} catch (IOException e) {
			System.out.println("Error loading resource icons");
		}

		GridLayout gridLayout = new GridLayout(6, 1);
		this.setLayout(gridLayout);
	}

	public void setupLayout() {
		this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
				getString("player" + playerRef.getPlayerId()),
				TitledBorder.CENTER, TitledBorder.TOP));

		JPanel woodPanel = new JPanel(new GridLayout(1, 4, 5, 0));
		woodPanel.add(new JLabel(woodIcon));
		this.woodLabel = new JLabel(getString("woodCount", playerRef.getResourceCount(ResourceType.WOOD)));
		woodPanel.add(woodLabel);
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
			woodPanel.add(woodPlusButton);
			woodPanel.add(woodMinusButton);
		}
		this.add(woodPanel);

		JPanel brickPanel = new JPanel(new GridLayout(1, 4, 5, 0));
		brickPanel.add(new JLabel(brickIcon));
		this.brickLabel = new JLabel(getString("brickCount", playerRef.getResourceCount(ResourceType.BRICK)));
		brickPanel.add(brickLabel);
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
			brickPanel.add(brickPlusButton);
			brickPanel.add(brickMinusButton);
		}
		this.add(brickPanel);

		JPanel sheepPanel = new JPanel(new GridLayout(1, 4, 5, 0));
		sheepPanel.add(new JLabel(sheepIcon));
		this.sheepLabel = new JLabel(getString("sheepCount", playerRef.getResourceCount(ResourceType.SHEEP)));
		sheepPanel.add(sheepLabel);
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
			sheepPanel.add(sheepPlusButton);
			sheepPanel.add(sheepMinusButton);
		}
		this.add(sheepPanel);

		JPanel wheatPanel = new JPanel(new GridLayout(1, 4, 5, 0));
		wheatPanel.add(new JLabel(wheatIcon));
		this.wheatLabel = new JLabel(getString("wheatCount", playerRef.getResourceCount(ResourceType.WHEAT)));
		wheatPanel.add(wheatLabel);
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
			wheatPanel.add(wheatPlusButton);
			wheatPanel.add(wheatMinusButton);
		}
		this.add(wheatPanel);

		JPanel orePanel = new JPanel(new GridLayout(1, 4, 5, 0));
		orePanel.add(new JLabel(oreIcon));
		this.oreLabel = new JLabel(getString("oreCount", playerRef.getResourceCount(ResourceType.ORE)));
		orePanel.add(oreLabel);
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
			orePanel.add(orePlusButton);
			orePanel.add(oreMinusButton);
		}
		this.add(orePanel);

		this.victoryPointsLabel = new JLabel(getString("victoryPoints", playerRef.getVictoryPoints()));
		victoryPointsLabel.setHorizontalAlignment(JLabel.CENTER);
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
