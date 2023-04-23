package catan;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CatanController {
	@FXML
	private Button submiButton;

	@FXML
	private Label nameLabel;

	@FXML
	private TextField nameField;

	@FXML
	private void updateNameLabel(ActionEvent e) {
		nameLabel.setText("Hello, " + nameField.getText());
	}

}
