package catan.gui.components;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import catan.Coordinate;
import catan.Game;
import catan.data.Road;
import catan.data.Settlement;

//CHECKSTYLE:OFF: checkstyle:magicnumber
/**
 * This is a developer only panel, we will not be using this in the final
 */
public class DevPanel extends JPanel {

	private Game gameRef;

	private JTextField devPlayerField = new JTextField(3);
	private JTextField devPosXField = new JTextField(3);
	private JTextField devPosYField = new JTextField(3);
	private JTextField devPosZField = new JTextField(3);
	private JButton devAddSettlementButton = new JButton("Add Settlement");

	private JLabel devSettlementLabel = new JLabel("Settlements");
	private JTextArea settlementList = new JTextArea(10, 20);
	private JLabel devRoadLabel = new JLabel("Roads");
	private JTextArea roadList = new JTextArea(10, 20);

	public DevPanel(Game game) {
		this.gameRef = game;

		GridBagConstraints constraints = new GridBagConstraints();
		this.setLayout(new GridBagLayout());

		JPanel coordsPanel = new JPanel(new GridLayout(1, 5));
		coordsPanel.add(devPlayerField);
		coordsPanel.add(devPosXField);
		coordsPanel.add(devPosYField);
		coordsPanel.add(devPosZField);
		devAddSettlementButton.addActionListener(e -> {
			gameRef.getBoard()
					.createNewSettlement(
							new Coordinate(Integer.parseInt(devPosXField.getText()),
									Integer.parseInt(devPosYField.getText()),
									Integer.parseInt(devPosZField.getText())),
							Integer.parseInt(devPlayerField.getText()));
			update();
		});
		coordsPanel.add(devAddSettlementButton);

		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 2;
		this.add(coordsPanel, constraints);

		constraints.gridy = 1;
		this.add(devSettlementLabel, constraints);
		constraints.gridx = 2;
		this.add(devRoadLabel, constraints);
		constraints.gridx = 0;
		constraints.gridy = 2;
		this.add(settlementList, constraints);
		constraints.gridx = 2;
		this.add(roadList, constraints);
	}

	public void update() {
		settlementList.setText("");
		roadList.setText("");

		for (Settlement s : gameRef.getBoard().getSettlements()) {
			settlementList.append(s.toString() + "\n");
		}

		for (Road s : gameRef.getBoard().getRoads()) {
			roadList.append(s.toString() + "\n");
		}
	}
}
