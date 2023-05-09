package catan.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import catan.Coordinate;
import catan.Game;
import catan.Utils;
import catan.data.ResourceType;
import catan.data.Road;
import catan.data.Settlement;
import catan.data.Tile;
import catan.gui.components.CoordinateButton;

//CHECKSTYLE:OFF: checkstyle:magicnumber
public final class BoardPanel extends JPanel {
	private final ClassLoader classLoader = getClass().getClassLoader();

	public static final int DEFAULT_WIDTH = 624;
	public static final int DEFAULT_HEIGHT = 654;
	private static final int X_OFFSET = 64;
	private static final int X_SECONDARY_OFFSET = 32;
	private static final int Y_OFFSET = 54;


	private Tile[] tiles;
	private List<Road> roads;
	private List<Settlement> settlements;
	private Game game;

	private Map<ResourceType, BufferedImage> tileImages;
	private Map<Integer, BufferedImage> puckImages;
	private Map<Integer, BufferedImage> settlementImages;
	private Map<Integer, BufferedImage> cityImages;
	private BufferedImage outlineImage;
	private BufferedImage thiefImage;

	private List<CoordinateButton> buttons = new ArrayList<CoordinateButton>();

	public BoardPanel(Game game) {
		this.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		this.setMaximumSize(getMaximumSize());

		this.tileImages = new HashMap<>();
		this.puckImages = new HashMap<>();
		this.settlementImages = new HashMap<>();
		this.cityImages = new HashMap<>();

		loadTileImages();
		loadPuckImages();
		loadPieceImages();

		this.game = game;
		this.tiles = this.game.getBoard().getTiles();
		this.roads = this.game.getBoard().getRoads();
		this.settlements = this.game.getBoard().getSettlements();

		// Allow full control of button placement
		this.setLayout(null);
		this.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		this.setMaximumSize(getMaximumSize());
		this.createCoordinateButtons();

	}

	private void loadPuckImages() {
		InputStream stream;
		try {
			for (int i = 2; i <= 12; i++) {
				if (i != 7) {
					String fileName = "image/puck_" + i + ".png";
					stream = classLoader.getResourceAsStream(fileName);
					puckImages.put(i, ImageIO.read(stream));
					stream.close();
				}
			}
		} catch (IOException e) {
			System.err.println("failed to load puck asset");
		}
	}

	private void loadPieceImages() {
		InputStream stream;
		try {
			for (int i = 1; i < 4; i++) {
				String settlementPath = "image/settlement_p" + i + ".png";
				stream = classLoader.getResourceAsStream(settlementPath);
				settlementImages.put(i, ImageIO.read(stream));
				stream.close();

				String cityPath = "image/city_p" + i + ".png";
				stream = classLoader.getResourceAsStream(cityPath);
				cityImages.put(i, ImageIO.read(stream));
				stream.close();
			}

			stream = classLoader.getResourceAsStream("image/thief.png");
			thiefImage = ImageIO.read(stream);
			stream.close();

			stream = classLoader.getResourceAsStream("image/outline.png");
			outlineImage = ImageIO.read(stream);
			stream.close();
		} catch (IOException e) {
			System.err.println("failed to load piece asset");
		}
	}

	private void loadTileImages() {
		try {
			InputStream stream = classLoader.getResourceAsStream("image/tile_forest.png");
			tileImages.put(ResourceType.WOOD, ImageIO.read(stream));
			stream.close();

			stream = classLoader.getResourceAsStream("image/tile_farm.png");
			tileImages.put(ResourceType.WHEAT, ImageIO.read(stream));
			stream.close();

			stream = classLoader.getResourceAsStream("image/tile_field.png");
			tileImages.put(ResourceType.SHEEP, ImageIO.read(stream));
			stream.close();

			stream = classLoader.getResourceAsStream("image/tile_quarry.png");
			tileImages.put(ResourceType.BRICK, ImageIO.read(stream));
			stream.close();

			stream = classLoader.getResourceAsStream("image/tile_mountain.png");
			tileImages.put(ResourceType.ORE, ImageIO.read(stream));
			stream.close();

			stream = classLoader.getResourceAsStream("image/tile_desert.png");
			tileImages.put(ResourceType.DESERT, ImageIO.read(stream));
			stream.close();
		} catch (IOException e) {
			System.err.println("failed to load tile asset");
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.drawImage(outlineImage, 0, 0, null);

		for (Tile t : this.tiles) {
			Coordinate c = t.getPosition();

			int x = DEFAULT_WIDTH / 2
					+ X_OFFSET * c.getY()
					+ X_SECONDARY_OFFSET * (c.getX() + c.getZ());
			int y = DEFAULT_HEIGHT / 2
					- c.getX() * Y_OFFSET
					+ c.getZ() * Y_OFFSET;

			int tileX = x - 64;
			int tileY = y - 64;

			int pieceX = x - 16;
			int pieceY = y - 16;

			g.drawImage(this.tileImages.get(t.getResourceType()), tileX, tileY, null);

			if (t.getDieRoll() != 7) {
				g.drawImage(this.puckImages.get(t.getDieRoll()), pieceX, pieceY, null);
			}

			if (c.equals(this.game.getBoard().getThiefPosition())) {
				g.drawImage(this.thiefImage, pieceX, pieceY, null);
			}
		}

		for (Settlement s : this.game.getBoard().getSettlements()) {
			Coordinate c = s.getLocation();
			System.out.println(c);
			int pNumber = s.getOwner();

			int x = DEFAULT_WIDTH / 2
					+ X_OFFSET * c.getY()
					+ X_SECONDARY_OFFSET * (c.getX() + c.getZ()) - 16;
			int y = DEFAULT_HEIGHT / 2
					- c.getX() * Y_OFFSET
					+ c.getZ() * Y_OFFSET - 16;

			if (s.isCity()) {
				g.drawImage(this.cityImages.get(pNumber), x, y, null);

			} else {
				g.drawImage(this.settlementImages.get(pNumber), x, y, null);
			}
		}

	}

	@Override
	public Dimension getPreferredSize() {
		if (isPreferredSizeSet()) {
			return super.getPreferredSize();
		}

		return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	/**
	 *
	 * @return
	 */
	public List<CoordinateButton> getButtons() {
		return buttons;
	}

	/**
	 * Subroutine to create all the buttons in the coordinate grid.
	 */
	private void createCoordinateButtons() {
		List<Coordinate> buttonCoords = new ArrayList<Coordinate>(Arrays.asList(Utils.REAL_LIST));
		buttonCoords.removeIf(x -> Arrays.asList(Utils.TILES_SPIRAL_LOCATION).contains(x));

		for (Coordinate pos : buttonCoords) {
			createCoordinateButton(pos);
		}
	}

	private void createCoordinateButton(Coordinate pos) {
		CoordinateButton button = new CoordinateButton(pos);
		int x = DEFAULT_WIDTH / 2
				+ X_OFFSET * pos.getY()
				+ X_SECONDARY_OFFSET * (pos.getX() + pos.getZ()) - 10;
		int y = DEFAULT_HEIGHT / 2
				- pos.getX() * Y_OFFSET
				+ pos.getZ() * Y_OFFSET - 10;
		button.setBounds(x, y, 20, 20);
		buttons.add(button);
		this.add(button);
	}

	/**
	 * Subroutine to show all the buttons in the coordinate grid.
	 */
	public void showCornerButtons() {
		for (CoordinateButton button : buttons) {
			button.setVisible(true);
		}
	}

	/**
	 * Subroutine to hide all the buttons in the coordinate grid.
	 */
	public void hideCornerButtons() {
		for (CoordinateButton button : buttons) {
			button.setVisible(false);
		}
	}
}
