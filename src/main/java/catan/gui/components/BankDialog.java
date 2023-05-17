package catan.gui.components;

import static catan.gui.LangUtils.getString;

import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import catan.data.ResourceType;
import catan.logic.Game;

//CHECKSTYLE:OFF: checkstyle:magicnumber
public class BankDialog extends JDialog {
	private JPanel mainPanel;
	private JComboBox<String> resourceSelector;
	private JComboBox<String> targetSelector;
	private JButton tradeButton;
	private JButton cancelButton;

	private String resourceStrings[];

	private Game gameRef;
	private Runnable updateCallback;

	public BankDialog(Frame frameRef, Game game, Runnable updateCallback) {
		super(frameRef, true);
		this.gameRef = game;
		this.updateCallback = updateCallback;
		this.resourceStrings = new String[] {
				getString("wood"),
				getString("brick"),
				getString("sheep"),
				getString("wheat"),
				getString("ore") };

		setupLayout();
		this.setContentPane(mainPanel);
		this.setResizable(false);
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		this.setTitle(getString("exchangeWithBank"));

		this.pack();
		this.setVisible(true);
	}

	private void setupLayout() {
		mainPanel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		JLabel resourceLabel = new JLabel(getString("resourceToTradeIn"));
		mainPanel.add(resourceLabel, c);

		resourceSelector = new JComboBox<String>(resourceStrings);
		c.gridx = 1;
		mainPanel.add(resourceSelector, c);

		JLabel targetLabel = new JLabel(getString("resourceToReceive"));
		c.gridx = 0;
		c.gridy = 1;
		mainPanel.add(targetLabel, c);
		targetSelector = new JComboBox<String>(resourceStrings);
		c.gridx = 1;
		mainPanel.add(targetSelector, c);

		tradeButton = new JButton(getString("exchange"));
		tradeButton.addActionListener(e -> {
			ResourceType resource = getTypeFromSelectedIndex(resourceSelector.getSelectedIndex());
			ResourceType target = getTypeFromSelectedIndex(targetSelector.getSelectedIndex());

			boolean success = gameRef.doBankExchange(gameRef.getTurn(), resource, target);

			if (success) {
				JOptionPane.showMessageDialog(this, getString("exchangeSuccessful"));
				updateCallback.run();
				this.dispose();
			} else {
				JOptionPane.showMessageDialog(this, getString("badExchange"));
			}
		});
		c.gridx = 0;
		c.gridy = 3;
		mainPanel.add(tradeButton, c);

		cancelButton = new JButton(getString("cancel"));
		cancelButton.addActionListener(e -> this.dispose());
		c.gridx = 1;
		mainPanel.add(cancelButton, c);
	}

	private ResourceType getTypeFromSelectedIndex(int input) {
		switch (input) {
			case 0:
				return ResourceType.WOOD;
			case 1:
				return ResourceType.BRICK;
			case 2:
				return ResourceType.SHEEP;
			case 3:
				return ResourceType.WHEAT;
			case 4:
				return ResourceType.ORE;
			default:
				return null;
		}
	}
}
