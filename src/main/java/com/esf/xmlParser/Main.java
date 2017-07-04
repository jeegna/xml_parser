package com.esf.xmlParser;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import com.esf.xmlParser.fxml.MainController;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * @author Jeegna Patel
 * @version 2017/04/08
 * @since 1.8
 */
public class Main extends Application {

	private final Logger logger = Logger.getLogger(this.getClass().getName());

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Main.fxml"));

		ResourceBundle bundle = ResourceBundle.getBundle("strings", new Locale("en"));
		loader.setResources(bundle);

		BorderPane root = (BorderPane) loader.load();
		Scene scene = new Scene(root);

		scene.getStylesheets().add(getClass().getResource("/css/light.css").toExternalForm());

		addEventHandlers(primaryStage, loader);

		primaryStage.setScene(scene);
		primaryStage.setTitle(bundle.getString("appName"));

		primaryStage.show();
	}

	private void addEventHandlers(Stage stage, FXMLLoader loader) {
		// Say "Bye!" on exit because that's adorable
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				logger.info("Bye!");
				System.exit(0);
			}
		});

		// Get MainController.
		MainController controller = (MainController) loader.getController();

		// Show the file chooser window on startup.
		stage.setOnShowing(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent window) {
				/*
				 * The Platform.runLater is required, otherwise the main
				 * application will not appear in the background, and only the
				 * file chooser window will be open. If the runLater was not
				 * there. the user would have to close the file chooser window
				 * in order for the main application to appear.
				 */
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						controller.getFirstFile();
					}
				});
			}
		});
	}
}
