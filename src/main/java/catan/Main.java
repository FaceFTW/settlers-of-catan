package catan;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The "boostrap" class for the application. This class is responsible for
 * spawning the main window and initializing the game.
 * <p>
 * Code based on
 * <a href="https://stackoverflow.com/questions/33881046/how-to-connect-fx-controller-with-main-app">...</a>
 */
public class Main extends Application {
	public static void main(String[] args) {
		launch(args);
	}


	/**
	 * This method is called by JavaFX to start the application.
	 * DO NOT TOUCH UNLESS ABSOLUTELY NECESSARY BECAUSE SOMETHING BROKE.
	 * @param primaryStage the primary stage for this application, onto which
	 * the application scene can be set.
	 * Applications may create other stages, if needed, but they will not be
	 * primary stages.
	 * @throws Exception Whatever JavaFX has a hissyfit about.
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Main.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Settlers of Catan");
		primaryStage.show();

	}
}
