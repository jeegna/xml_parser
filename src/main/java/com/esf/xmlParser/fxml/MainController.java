package com.esf.xmlParser.fxml;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.esf.xmlParser.Main;
import com.esf.xmlParser.database.DatabaseController;
import com.esf.xmlParser.entities.Asset;
import com.esf.xmlParser.entities.AssetClip;
import com.esf.xmlParser.entities.Audio;
import com.esf.xmlParser.entities.Clip;
import com.esf.xmlParser.entities.Effect;
import com.esf.xmlParser.entities.Format;
import com.esf.xmlParser.entities.Video;
import com.esf.xmlParser.parser.Parser;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
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

	private final Logger logger = Logger.getLogger(this.getClass().getName());

	private String filePath;
	private String fileName;

	private TableViewController tableViewController;

	@FXML
	private void initialize() {
		logger.info("Start application");

		// Initialize other fxml controllers
		initializeTableViewController();
	}

	/**
	 * Allows the selection of a file by the user. Once a file has been selected
	 * by the user, the file will be parsed and displayed in the tables.
	 */
	public void selectFile() {
		File file = getFileFromFileChooser();
		setFile(file);

		try {
			loadFile();
		} catch (ClassNotFoundException | SQLException | ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

		fileChooser.setTitle(resources.getString("openFile"));

		// Set file type filter to only accept .fcpxml.
		ExtensionFilter filter = new ExtensionFilter(resources.getString("fileType"), "*.fcpxml");
		fileChooser.getExtensionFilters().add(filter);
		fileChooser.setSelectedExtensionFilter(filter);

		// Set initial directory to user's home folder.
		String home = System.getProperty("user.home");
		File file = new File(home);
		fileChooser.setInitialDirectory(file);

		return fileChooser.showOpenDialog(borderPane.getScene().getWindow());
	}

	/**
	 * Initializes the table view controller and the FXML associated with it.
	 */
	private void initializeTableViewController() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setResources(resources);

			loader.setLocation(Main.class.getResource("/fxml/TableView.fxml"));
			TabPane tabPane = (TabPane) loader.load();

			tableViewController = loader.getController();

			// Add view to Main.fxml
			borderPane.setCenter(tabPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Loads the file contents into a database and populates the JavaFX tables
	 * with the data.
	 * 
	 * @throws ClassNotFoundException
	 *             If the JDBC driver is not found.
	 * @throws SQLException
	 * @throws ParserConfigurationException
	 *             If a DocumentBuilder cannot be created which satisfies the
	 *             configuration requested.
	 * @throws SAXException
	 *             If any parse errors occur.
	 * @throws IOException
	 *             If any IO errors occur.
	 */
	private void loadFile()
			throws ClassNotFoundException, SQLException, ParserConfigurationException, SAXException, IOException {
		logger.info("Loading file...");

		Parser parser = new Parser(filePath);
		DatabaseController db = new DatabaseController(fileName);

		List<Asset> assets = parser.getAssets();
		List<AssetClip> assetClips = parser.getAssetClips();
		List<Audio> audios = parser.getAudios();
		List<Clip> clips = parser.getClips();
		List<Effect> effects = parser.getEffects();
		List<Format> formats = parser.getFormats();
		List<Video> videos = parser.getVideos();

		db.populateDatabase(assets, assetClips, audios, clips, effects, formats, videos);

		assets = db.getAssets();
		assetClips = db.getAssetClips();
		audios = db.getAudios();
		clips = db.getClips();
		effects = db.getEffects();
		formats = db.getFormats();
		videos = db.getVideos();

		// Populate tables with file contents.
		tableViewController.populateTables(assets, assetClips, audios, clips, effects, formats, videos);
	}

	@FXML
	private void menuItemAbout() {

	}

	@FXML
	private void menuItemChooseFile() {
		selectFile();
	}

	@FXML
	private void menuItemClose() {
		close();
	}

	private void setFile(File file) {
		if (file != null) {
			filePath = file.getAbsolutePath();
			fileName = file.getName();

			logger.info("Chosen file: " + filePath);

			// Set file name in title bar
			((Stage) borderPane.getScene().getWindow()).setTitle(resources.getString("appName") + " - " + filePath);
		}
	}
}
