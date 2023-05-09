package catan.gui;

import catan.Coordinate;
import catan.Game;
import catan.data.ResourceType;
import catan.data.Road;
import catan.data.Settlement;
import catan.data.Tile;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class BoardPanel extends JPanel {
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


    }

    private void loadPuckImages() {
        ClassLoader classLoader = getClass().getClassLoader();

        try {
            InputStream stream = classLoader.getResourceAsStream("image/puck_2.png");
            puckImages.put(2, ImageIO.read(stream));
            stream.close();

            stream = classLoader.getResourceAsStream("image/puck_3.png");
            puckImages.put(3, ImageIO.read(stream));
            stream.close();

            stream = classLoader.getResourceAsStream("image/puck_4.png");
            puckImages.put(4, ImageIO.read(stream));
            stream.close();

            stream = classLoader.getResourceAsStream("image/puck_5.png");
            puckImages.put(5, ImageIO.read(stream));
            stream.close();

            stream = classLoader.getResourceAsStream("image/puck_6.png");
            puckImages.put(6, ImageIO.read(stream));
            stream.close();

            stream = classLoader.getResourceAsStream("image/puck_8.png");
            puckImages.put(8, ImageIO.read(stream));
            stream.close();

            stream = classLoader.getResourceAsStream("image/puck_9.png");
            puckImages.put(9, ImageIO.read(stream));
            stream.close();

            stream = classLoader.getResourceAsStream("image/puck_10.png");
            puckImages.put(10, ImageIO.read(stream));
            stream.close();

            stream = classLoader.getResourceAsStream("image/puck_11.png");
            puckImages.put(11, ImageIO.read(stream));
            stream.close();

            stream = classLoader.getResourceAsStream("image/puck_12.png");
            puckImages.put(12, ImageIO.read(stream));
            stream.close();

        } catch (IOException e) {
            System.err.println("failed to load puck asset");
        }
    }

    private void loadPieceImages() {
        ClassLoader classLoader = getClass().getClassLoader();

        try {
            InputStream stream = classLoader.getResourceAsStream("image/settlement_p1.png");
            settlementImages.put(1, ImageIO.read(stream));
            stream.close();

            stream = classLoader.getResourceAsStream("image/settlement_p2.png");
            settlementImages.put(2, ImageIO.read(stream));
            stream.close();

            stream = classLoader.getResourceAsStream("image/settlement_p3.png");
            settlementImages.put(3, ImageIO.read(stream));
            stream.close();

            stream = classLoader.getResourceAsStream("image/settlement_p4.png");
            settlementImages.put(4, ImageIO.read(stream));
            stream.close();

            stream = classLoader.getResourceAsStream("image/city_p1.png");
            cityImages.put(1, ImageIO.read(stream));
            stream.close();

            stream = classLoader.getResourceAsStream("image/city_p2.png");
            cityImages.put(2, ImageIO.read(stream));
            stream.close();

            stream = classLoader.getResourceAsStream("image/city_p3.png");
            cityImages.put(3, ImageIO.read(stream));
            stream.close();

            stream = classLoader.getResourceAsStream("image/city_p4.png");
            cityImages.put(4, ImageIO.read(stream));
            stream.close();

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
        ClassLoader classLoader = getClass().getClassLoader();

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

        for (Settlement s: this.settlements) {
            Coordinate c = s.getLocation();
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
}
