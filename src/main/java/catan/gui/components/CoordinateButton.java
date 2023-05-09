package catan.gui.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import javax.swing.JButton;

import catan.logic.Coordinate;

//CHECKSTYLE:OFF: checkstyle:magicnumber
public class CoordinateButton extends JButton {
	private Coordinate coordinate;

	public CoordinateButton(Coordinate coordinate) {
		this.coordinate = coordinate;
		this.setBorderPainted(false);
		this.setFocusPainted(false);
		this.setContentAreaFilled(false);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g; // Always need this cast

		Shape circle = new Ellipse2D.Double(0, 0, 20, 20);
		g2.setColor(Color.WHITE);
		g2.fill(circle);
		g2.setColor(Color.BLACK);
		g2.draw(circle);
	}

	@Override
	public Dimension getPreferredSize() {
		if (isPreferredSizeSet()) {
			return super.getPreferredSize();
		}
		return new Dimension(20, 20);
	}

	public Coordinate getCoordinate() {
		return coordinate;
	}
}
