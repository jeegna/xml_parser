package com.esf.xmlParser.fxml;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.esf.xmlParser.Main;
import com.esf.xmlParser.entities.Asset;
import com.esf.xmlParser.entities.AssetClip;
import com.esf.xmlParser.entities.Audio;
import com.esf.xmlParser.entities.Effect;
import com.esf.xmlParser.entities.Format;
import com.esf.xmlParser.entities.Video;
import com.esf.xmlParser.parser.Parser;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 * @author Jeegna Patel
 * @version 2017/04/08
 * @since 1.8
 */
public class MainController {

	@FXML
	private ResourceBundle resources;

	// Main border pane
	@FXML
	private BorderPane borderPane;

	// Menubar
	@FXML
	private MenuBar menubar;
	@FXML
	private MenuItem menuItemClose;
	@FXML
	private MenuItem menuItemChooseFile;
	@FXML
	private MenuItem menuItemAbout;

	@FXML
	private AnchorPane tableViewPane;

	private final Logger logger = Logger.getLogger(this.getClass().getName());
	private Parser parser;
	private String filePath;
	private String fileName;

	private TableViewController tableViewController;

	@FXML
	private void initialize() {
		logger.info("Start Application");

		// Initialize other fxml controllers
		initializeTables();
	}

	/**
	 * Initializes the tabs and tables
	 */
	private void initializeTables() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setResources(resources);

			loader.setLocation(Main.class.getResource("/fxml/TableView.fxml"));
			TabPane paneDisplayEmail = (TabPane) loader.load();

			tableViewController = loader.getController();

			// Add view to Main.fxml
			tableViewPane.getChildren().add(paneDisplayEmail);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void getFirstFile() {
		getFile();
	}

	private void getFile() {
		File file = getFileFromFileChooser();
		setFile(file);
		loadFile();
	}

	private void loadFile() {
		logger.info("Loading file...");
		if (parser != null) {
			List<Video> videos = parser.getVideos();
			List<Audio> audios = parser.getAudios();
			List<Asset> assets = parser.getAssets();
			List<AssetClip> assetClips = parser.getAssetClips();
			List<Format> formats = parser.getFormats();
			List<Effect> effects = parser.getEffects();

			// Populate tables with file contents.
			tableViewController.createTables();
			tableViewController.populateTables(videos, audios, assets, assetClips, formats, effects);
		}
	}

	/**
	 * @param filePath
	 *            The file's path.
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	private void createParser(String filePath) throws ParserConfigurationException, SAXException, IOException {
		try {
			parser = new Parser(filePath);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	private void menuItemChooseFile() {
		getFile();
	}

	@FXML
	private void menuItemAbout() {

	}

	@FXML
	private void menuItemClose() {
		close();
	}

	/**
	 * Closes the application
	 */
	private void close() {
		logger.info("Bye!");
		System.exit(0);
	}

	/**
	 * Opens a file chooser window for .fcpxml files and gets the selected file
	 *
	 * @return The file the user has selected, or {@code null} if no file was
	 *         chosen
	 */
	private File getFileFromFileChooser() {
		FileChooser fileChooser = new FileChooser();
		ExtensionFilter filter = new ExtensionFilter(resources.getString("fileType"), "*.fcpxml");
		fileChooser.setTitle(resources.getString("openFile"));
		fileChooser.getExtensionFilters().add(filter);
		fileChooser.setSelectedExtensionFilter(filter);

		// Set initial directory to user's home folder.
		String home = System.getProperty("user.home");
		File file = new File(home);
		fileChooser.setInitialDirectory(file);

		return fileChooser.showOpenDialog(borderPane.getScene().getWindow());
	}

	private void setFile(File file) {
		if (file != null) {
			filePath = file.getAbsolutePath();
			fileName = file.getName();

			logger.info("Chosen file: " + filePath);

			try {
				createParser(filePath);
			} catch (ParserConfigurationException | SAXException | IOException e) {
				logger.log(Level.SEVERE, e.getMessage());
				// TODO Show an error dialog box
			}

			// Set file name in title bar
			((Stage) borderPane.getScene().getWindow()).setTitle(resources.getString("appName") + " - " + filePath);
		}
	}
}
