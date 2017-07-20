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
 * The Main class of the application.
 *
 * @author Jeegna Patel
 * @version 2017/04/08
 * @since 1.8
 */
public class Main extends Application {

	/** The logger. */
	private final Logger logger = Logger.getLogger(this.getClass().getName());

	/**
	 * The main method.
	 *
	 * @param args
	 *            The arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage primaryStage) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Main.fxml"));

		ResourceBundle resources = ResourceBundle.getBundle("strings", new Locale("en"));
		loader.setResources(resources);

		BorderPane root = (BorderPane) loader.load();
		Scene scene = new Scene(root);

		scene.getStylesheets().add(getClass().getResource("/css/light.css").toExternalForm());

		addEventHandlers(primaryStage, loader);

		primaryStage.setScene(scene);
		primaryStage.setTitle(resources.getString("appName"));

		primaryStage.show();
	}

	/**
	 * Add event handlers to the application. The event handlers are an onClose
	 * handler which will log a message, and an onShowing handler which will
	 * initiate the file choosing dialog box.
	 * 
	 * @param stage
	 *            The stage of the application.
	 * @param loader
	 *            The fxml loader.
	 */
	private void addEventHandlers(Stage stage, FXMLLoader loader) {
		// Get MainController.
		MainController controller = (MainController) loader.getController();

		// Say "Bye!" on exit because that's adorable
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				controller.close();
			}
		});

		// Show the file chooser window on startup.
		stage.setOnShowing(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent window) {
				/*
				 * The Platform.runLater is required, otherwise the main
				 * application will not appear in the background, and only the
				 * file chooser window will be open. If the runLater was not
				 * there, the user would have to close the file chooser window
				 * in order for the main application to appear.
				 */
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						controller.selectFile();
					}
				});
			}
		});
	}
}
