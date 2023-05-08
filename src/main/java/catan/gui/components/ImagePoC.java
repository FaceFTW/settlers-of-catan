package catan.gui.components;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagePoC extends JPanel {
	private BufferedImage image;

	public ImagePoC() {
		ClassLoader classLoader = getClass().getClassLoader();
		try {
			InputStream imageStream = classLoader.getResourceAsStream("image/card_brick.png");
			image = ImageIO.read(imageStream);
			imageStream.close();
		} catch (IOException ex) {
			// handle exception...
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, this); // see javadoc for more info on the
										// parameters
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Dimension getPreferredSize() {
		if (isPreferredSizeSet()) {
			return super.getPreferredSize();
		}

		return new Dimension(image.getWidth(), image.getHeight());
	}
}
