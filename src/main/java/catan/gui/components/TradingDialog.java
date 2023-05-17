package catan.gui.components;

import static catan.gui.LangUtils.getString;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;

import catan.data.ResourceType;
import catan.data.ResourcesGroup;
import catan.data.TradeOffer;
import catan.logic.Game;

/**
 * @noinspection ALL
 */
// CHECKSTYLE:OFF: checkstyle:magicnumber
public class TradingDialog extends JDialog {
	private JPanel resourceAmountPanel;
	private JSpinner woodGiveSpinner;
	private JSpinner brickGiveSpinner;
	private JSpinner sheepGiveSpinner;
	private JSpinner wheatGiveSpinner;
	private JSpinner oreGiveSpinner;
	private JSpinner oreReceiveSpinner;
	private JSpinner wheatReceiveSpinner;
	private JSpinner sheepReceiveSpinner;
	private JSpinner woodReceiveSpinner;
	private JSpinner brickReceiveSpinner;
	private JPanel actionButtonPanel;
	private JButton buttonCancel;
	private JButton buttonOK;
	private JPanel contentPane;

	// Trade Target
	private JPanel tradeTargetPanel;
	private JRadioButton player1RadioTarget;
	private JRadioButton player2RadioButton;
	private JRadioButton player3RadioButton;
	private JRadioButton player4RadioButton;
	private int tradeTarget = 0;

	private Game gameRef;
	private Runnable updateCallback;

	public TradingDialog(Frame frameRef, Game game, Runnable updateCallback) {
		super(frameRef, true);
		this.gameRef = game;
		this.updateCallback = updateCallback;
		setupLayout();
		setContentPane(contentPane);
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setResizable(false);
		setTitle(getString("trade"));

		pack();

		this.setVisible(true);
	}

	private void setupLayout() {
		contentPane = new JPanel(new BorderLayout(25, 25));
		setupTradeTargetPanel();
		contentPane.add(tradeTargetPanel, BorderLayout.NORTH);
		setupResourceAmountPanel();
		contentPane.add(resourceAmountPanel, BorderLayout.CENTER);
		setupActionsButtonPanel();
		contentPane.add(actionButtonPanel, BorderLayout.SOUTH);
	}

	private void setupTradeTargetPanel() {
		tradeTargetPanel = new JPanel(new GridBagLayout());

		final JLabel label1 = new JLabel(getString("trading.with"));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.NORTHEAST;
		tradeTargetPanel.add(label1, gbc);

		player1RadioTarget = new JRadioButton(getString("player.1"));
		player1RadioTarget.setActionCommand("1");
		player1RadioTarget.addActionListener(this::radioActionListener);
		gbc.gridx = 1;
		tradeTargetPanel.add(player1RadioTarget, gbc);

		player2RadioButton = new JRadioButton(getString("player.2"));
		player2RadioButton.setActionCommand("2");
		player2RadioButton.addActionListener(this::radioActionListener);
		gbc.gridx = 2;
		tradeTargetPanel.add(player2RadioButton, gbc);

		player3RadioButton = new JRadioButton(getString("player.3"));
		player3RadioButton.setActionCommand("3");
		player3RadioButton.addActionListener(this::radioActionListener);
		gbc.gridx = 3;
		tradeTargetPanel.add(player3RadioButton, gbc);

		player4RadioButton = new JRadioButton(getString("player.4"));
		player4RadioButton.setActionCommand("4");
		player4RadioButton.addActionListener(this::radioActionListener);
		gbc.gridx = 4;
		tradeTargetPanel.add(player4RadioButton, gbc);

		ButtonGroup group = new ButtonGroup();
		group.add(player1RadioTarget);
		group.add(player2RadioButton);
		group.add(player3RadioButton);
		group.add(player4RadioButton);
	}

	private void radioActionListener(ActionEvent e) {
		this.tradeTarget = Integer.parseInt(e.getActionCommand());
	}

	private void setupResourceAmountPanel() {
		resourceAmountPanel = new JPanel(new GridBagLayout());

		final JLabel label8 = new JLabel(getString("you.give"));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 5, 5);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.NORTHEAST;
		resourceAmountPanel.add(label8, gbc);

		woodGiveSpinner = new JSpinner();
		gbc.gridx = 1;
		resourceAmountPanel.add(woodGiveSpinner, gbc);
		brickGiveSpinner = new JSpinner();
		gbc.gridx = 2;
		resourceAmountPanel.add(brickGiveSpinner, gbc);
		sheepGiveSpinner = new JSpinner();
		gbc.gridx = 3;
		resourceAmountPanel.add(sheepGiveSpinner, gbc);
		wheatGiveSpinner = new JSpinner();
		gbc.gridx = 4;
		resourceAmountPanel.add(wheatGiveSpinner, gbc);
		oreGiveSpinner = new JSpinner();
		gbc.gridx = 5;
		resourceAmountPanel.add(oreGiveSpinner, gbc);

		final JLabel woodLabel = new JLabel(getString("wood"));
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.NORTH;
		resourceAmountPanel.add(woodLabel, gbc);
		final JLabel brickLabel = new JLabel(getString("brick"));
		gbc.gridx = 2;
		resourceAmountPanel.add(brickLabel, gbc);
		final JLabel sheepLabel = new JLabel(getString("sheep"));
		gbc.gridx = 3;
		resourceAmountPanel.add(sheepLabel, gbc);
		final JLabel wheatLabel = new JLabel(getString("wheat"));
		gbc.gridx = 4;
		resourceAmountPanel.add(wheatLabel, gbc);
		final JLabel oreLabel = new JLabel(getString("ore"));
		gbc.gridx = 5;
		resourceAmountPanel.add(oreLabel, gbc);

		final JLabel label7 = new JLabel(getString("they.give"));
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.NORTHEAST;
		resourceAmountPanel.add(label7, gbc);
		woodReceiveSpinner = new JSpinner();
		gbc.gridx = 1;
		resourceAmountPanel.add(woodReceiveSpinner, gbc);
		brickReceiveSpinner = new JSpinner();
		gbc.gridx = 2;
		resourceAmountPanel.add(brickReceiveSpinner, gbc);
		sheepReceiveSpinner = new JSpinner();
		gbc.gridx = 3;
		resourceAmountPanel.add(sheepReceiveSpinner, gbc);
		wheatReceiveSpinner = new JSpinner();
		gbc.gridx = 4;
		resourceAmountPanel.add(wheatReceiveSpinner, gbc);
		oreReceiveSpinner = new JSpinner();
		gbc.gridx = 5;
		resourceAmountPanel.add(oreReceiveSpinner, gbc);
	}

	private void setupActionsButtonPanel() {
		actionButtonPanel = new JPanel(new GridBagLayout());
		buttonCancel = new JButton(getString("cancel"));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.NORTHWEST;

		buttonCancel.addActionListener(e -> {
			this.dispose();
		});
		actionButtonPanel.add(buttonCancel, gbc);

		buttonOK = new JButton(getString("ok"));
		gbc.gridx = 0;
		gbc.anchor = GridBagConstraints.NORTHEAST;
		buttonOK.addActionListener(e -> {
			if (tradeTarget == 0) {
				JOptionPane.showMessageDialog(this, getString("select.player"));
				return;
			}

			int woodGive = (int) woodGiveSpinner.getValue();
			if (gameRef.getPlayer(gameRef.getTurn()).getResourceCount(ResourceType.WOOD) < woodGive
					|| woodGive < 0) {
				JOptionPane.showMessageDialog(this, getString("not.enough.wood.player"));
				return;
			}
			int brickGive = (int) brickGiveSpinner.getValue();
			if (gameRef.getPlayer(gameRef.getTurn()).getResourceCount(ResourceType.BRICK) < brickGive
					|| brickGive < 0) {
				JOptionPane.showMessageDialog(this, getString("not.enough.brick.player"));
				return;
			}
			int sheepGive = (int) sheepGiveSpinner.getValue();
			if (gameRef.getPlayer(gameRef.getTurn()).getResourceCount(ResourceType.SHEEP) < sheepGive
					|| sheepGive < 0) {
				JOptionPane.showMessageDialog(this, getString("not.enough.sheep.player"));
				return;
			}
			int wheatGive = (int) wheatGiveSpinner.getValue();
			if (gameRef.getPlayer(gameRef.getTurn()).getResourceCount(ResourceType.WHEAT) < wheatGive
					|| wheatGive < 0) {
				JOptionPane.showMessageDialog(this, getString("not.enough.wheat.player"));
				return;
			}
			int oreGive = (int) oreGiveSpinner.getValue();
			if (gameRef.getPlayer(gameRef.getTurn()).getResourceCount(ResourceType.ORE) < oreGive
					|| oreGive < 0) {
				JOptionPane.showMessageDialog(this, getString("not.enough.ore.player"));
				return;
			}
			ResourcesGroup giveGroup = new ResourcesGroup(woodGive,
					brickGive,
					sheepGive,
					wheatGive,
					oreGive);

			int woodReceive = (int) woodReceiveSpinner.getValue();
			if (gameRef.getPlayer(tradeTarget).getResourceCount(ResourceType.WOOD) < woodReceive
					|| woodReceive < 0) {
				JOptionPane.showMessageDialog(this, getString("not.enough.wood.target"));
				return;
			}
			int brickReceive = (int) brickReceiveSpinner.getValue();
			if (gameRef.getPlayer(tradeTarget).getResourceCount(ResourceType.BRICK) < brickReceive
					|| brickReceive < 0) {
				JOptionPane.showMessageDialog(this, getString("not.enough.brick.target"));
				return;
			}
			int sheepReceive = (int) sheepReceiveSpinner.getValue();
			if (gameRef.getPlayer(tradeTarget).getResourceCount(ResourceType.SHEEP) < sheepReceive
					|| sheepReceive < 0) {
				JOptionPane.showMessageDialog(this, getString("not.enough.sheep.target"));
				return;
			}
			int wheatReceive = (int) wheatReceiveSpinner.getValue();
			if (gameRef.getPlayer(tradeTarget).getResourceCount(ResourceType.WHEAT) < wheatReceive
					|| wheatReceive < 0) {
				JOptionPane.showMessageDialog(this, getString("not.enough.wheat.target"));
				return;
			}
			int oreReceive = (int) oreReceiveSpinner.getValue();
			if (gameRef.getPlayer(tradeTarget).getResourceCount(ResourceType.ORE) < oreReceive
					|| oreReceive < 0) {
				JOptionPane.showMessageDialog(this, getString("not.enough.ore.target"));
				return;
			}
			ResourcesGroup receiveGroup = new ResourcesGroup(woodReceive,
					brickReceive,
					sheepReceive,
					wheatReceive,
					oreReceive);

			TradeOffer offer = new TradeOffer(giveGroup, receiveGroup);

			gameRef.exchangeResources(gameRef.getTurn(), tradeTarget, offer);
			JOptionPane.showMessageDialog(this, getString("trade.sent"));
			updateCallback.run();
			this.dispose();
		});
		actionButtonPanel.add(buttonOK, gbc);
	}

}
