package catan.gui.components;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import javax.swing.JButton;

import catan.Coordinate;

//CHECKSTYLE:OFF: checkstyle:magicnumber
public class CoordinateButton extends JButton {
	private Coordinate coordinate;
	private boolean isDev;

	public CoordinateButton(Coordinate coordinate) {
		isDev = false;
		this.coordinate = coordinate;
		this.setBorderPainted(false);
		this.setFocusPainted(false);
		this.setContentAreaFilled(false);

		this.setSize(25, 25);
		this.setBounds(0, 0, 25, 25);
	}

	public CoordinateButton(Coordinate coordinate, boolean isDev) {
		this.isDev = isDev;
		if (isDev) {
			this.coordinate = coordinate;
			this.setText(coordinate.toString());
		} else {
			this.coordinate = coordinate;
			this.setBorderPainted(false);
			this.setFocusPainted(false);
			this.setContentAreaFilled(false);

			this.setSize(25, 25);
			this.setBounds(0, 0, 25, 25);
		}
		;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if (!isDev) {
			Graphics2D g2 = (Graphics2D) g; // Always need this cast

			Shape circle = new Ellipse2D.Double(0, 0, 20, 20);
			g2.draw(circle);

		}
	}

	public Coordinate getCoordinate() {
		return coordinate;
	}
}
