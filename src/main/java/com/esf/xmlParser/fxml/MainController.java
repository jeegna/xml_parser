package com.esf.xmlParser.fxml;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.esf.xmlParser.Main;
import com.esf.xmlParser.converter.ElementConverter;
import com.esf.xmlParser.database.DatabaseController;
import com.esf.xmlParser.entities.Asset;
import com.esf.xmlParser.entities.AssetClip;
import com.esf.xmlParser.entities.Audio;
import com.esf.xmlParser.entities.Clip;
import com.esf.xmlParser.entities.Effect;
import com.esf.xmlParser.entities.Element;
import com.esf.xmlParser.entities.Format;
import com.esf.xmlParser.entities.Video;
import com.esf.xmlParser.parser.Parser;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * @author Jeegna Patel
 * @version 2017/04/08
 * @since 1.8
 */
public class MainController {

	private final Logger logger = Logger.getLogger(this.getClass().getName());

	@FXML
	private ResourceBundle resources;

	// Main border pane
	@FXML
	private BorderPane borderPane;
	@FXML
	private VBox borderPaneTop;

	// Menubar
	@FXML
	private MenuBar menubar;
	@FXML
	private MenuItem menuItemClose;
	@FXML
	private MenuItem menuItemChooseFile;
	@FXML
	private MenuItem menuItemAbout;

	// Search bar
	@FXML
	private ComboBox<Element> comboBoxSearch;
	@FXML
	private Button buttonSearch;

	private String filePath;
	private String fileName;

	private TableViewController tableViewController;
	private AdvancedSearchController advancedSearchController;
	private DatabaseController db;

	@FXML
	private void initialize() {
		logger.info("Start application");

		// Initialize fxml controllers.
		initializeTableView();
		initializeSearchBar();
		initializeAdvancedSearch();
	}

	/**
	 * Allows the selection of a file by the user. Once a file has been selected
	 * by the user, the file will be parsed and displayed in the tables.
	 */
	public void selectFile() {
		File file = getFileFromFileChooser();

		if (file != null) {
			setFile(file);

			try {
				loadFile();
			} catch (ClassNotFoundException | SQLException | ParserConfigurationException | SAXException
					| IOException e) {
				new Alert(AlertType.ERROR, resources.getString("errorLoadFile"), ButtonType.OK).showAndWait();
				e.printStackTrace();
			}
		}
	}

	@FXML
	private void buttonSearchClick() {
		if (db != null) {
			String query = comboBoxSearch.getValue().getName();

			try {
				List<Asset> assets = db.getAssets(query);
				List<AssetClip> assetClips = db.getAssetClips(query);
				List<Clip> clips = db.getClips(query);
				List<Effect> effects = db.getEffects(query);
				List<Format> formats = db.getFormats(query);
				List<Video> videos = db.getVideos(query);

				List<Element> elements = new ArrayList<Element>();
				elements.addAll(assets);
				elements.addAll(assetClips);
				elements.addAll(clips);
				elements.addAll(effects);
				elements.addAll(formats);
				elements.addAll(videos);

				comboBoxSearch.getItems().setAll(elements);

			} catch (ClassNotFoundException | SQLException e) {
				new Alert(AlertType.ERROR, resources.getString("errorSearch"), ButtonType.OK).showAndWait();
				e.printStackTrace();
			}
		}
	}

	/**
	 * Closes the application
	 */
	private void close() {
		logger.info("Bye!");
		System.exit(0);
	}

	@FXML
	private void comboBoxItemSelected(ActionEvent event) {
		Element element = comboBoxSearch.getValue();
		if (element != null) {
			tableViewController.selectItem(element);
		}
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
	
	private void initializeAdvancedSearch() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setResources(resources);
			loader.setLocation(Main.class.getResource("/fxml/AdvancedSearch.fxml"));

			// Add view to Main.fxml.
			borderPaneTop.getChildren().add(loader.load());
			// Get controller.
			advancedSearchController = loader.getController();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void initializeSearchBar() {
		// http://www.java2s.com/Code/Java/JavaFX/customcellfactory.htm
		comboBoxSearch.setCellFactory(new Callback<ListView<Element>, ListCell<Element>>() {
			@Override
			public ListCell<Element> call(ListView<Element> list) {
				return new ListCell<Element>() {
					@Override
					protected void updateItem(Element element, boolean empty) {
						super.updateItem(element, empty);

						if (element == null || empty) {
							setText(null);
						} else {
							setText(element.getClass().getSimpleName() + " " + element.getName());
						}
					}
				};
			}
		});

		comboBoxSearch.setConverter(new ElementConverter());
	}

	/**
	 * Initializes the table view controller and the FXML associated with it.
	 */
	private void initializeTableView() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setResources(resources);
			loader.setLocation(Main.class.getResource("/fxml/TableView.fxml"));

			// Add view to Main.fxml.
			borderPane.setCenter(loader.load());
			// Get controller.
			tableViewController = loader.getController();
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
		db = new DatabaseController(fileName);

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
