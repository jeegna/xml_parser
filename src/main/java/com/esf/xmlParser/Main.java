package com.esf.xmlParser;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javafx.application.Application;
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

	@Override
	public void start(Stage primaryStage) {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Main.fxml"));

			ResourceBundle bundle = ResourceBundle.getBundle("strings", new Locale("en"));
			loader.setResources(bundle);

			BorderPane root = (BorderPane) loader.load();
			Scene scene = new Scene(root);

			scene.getStylesheets().add(getClass().getResource("/css/light.css").toExternalForm());

			primaryStage.setScene(scene);
			primaryStage.setTitle(bundle.getString("appName"));

			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Say "Bye!" on exit because that's adorable
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				logger.info("Bye!");
				System.exit(0);
			}
		});
	}

	public static void main(String[] args) {
		launch(args);
	}
}
