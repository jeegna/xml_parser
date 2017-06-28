package com.esf.xmlParser.fxml;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.esf.xmlParser.entities.Asset;
import com.esf.xmlParser.entities.Audio;
import com.esf.xmlParser.entities.Video;
import com.esf.xmlParser.util.Parser;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

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

	// Sidebar buttons
	@FXML
	private Button buttonGetVideos;
	@FXML
	private Button buttonGetAudios;

	// Tables
	@FXML
	private TableView<Asset> tableAssets;
	@FXML
	private TableView<Video> tableVideos;
	@FXML
	private TableView<Audio> tableAudios;

	private final Logger logger = Logger.getLogger(this.getClass().getName());
	private Parser parser;
	private String filePath;

	@FXML
	private void initialize() {
		logger.info("Start Application");
	}

	public void getFile() {
		File file = getFileFromFileChooser();
		setFile(file);
	}

	/**
	 * @param filePath
	 *            The file's path.
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	private void createParser(String filePath) throws ParserConfigurationException, SAXException, IOException {
		parser = new Parser(filePath);
	}

	@FXML
	private void menuItemChooseFile() {
		File file = getFileFromFileChooser();
		setFile(file);
	}

	@FXML
	private void menuItemAbout() {

	}

	@FXML
	private void menuItemClose() {
		close();
	}

	@FXML
	private void buttonGetVideosClick() {
		if (parser != null) {
			List<Video> assets = parser.getVideos();
			ObservableList<Video> list = FXCollections.observableList(assets);
			populateVideoTable(list);
		}
	}

	@FXML
	private void buttonGetAudiosClick() {
		if (parser != null) {
			List<Audio> assets = parser.getAudios();
			ObservableList<Audio> list = FXCollections.observableList(assets);
			 populateAudioTable(list);
		}
	}

	@FXML
	private void buttonGetAssetsClick() {
		if (parser != null) {
			List<Asset> assets = parser.getAssets();
			ObservableList<Asset> list = FXCollections.observableList(assets);
			populateAssetTable(list);
		}
	}

	/**
	 * Populates the asset table with dynamically created columns
	 *
	 * @param list
	 *            The list of @{code Asset} objects to populate the table with
	 */
	private void populateAssetTable(ObservableList<Asset> list) {
		// Clear all previously displayed Assets
		tableAssets.getItems().clear();
		tableVideos.setVisible(false);
		tableAudios.setVisible(false);

		// Set row height
		tableAssets.setFixedCellSize(30);

		// Create columns
		TableColumn<Asset, String> columnDuration = new TableColumn<>("Duration");
		TableColumn<Asset, Boolean> columnHasVideo = new TableColumn<>("Has Video");
		TableColumn<Asset, Boolean> columnHasAudio = new TableColumn<>("Has Audio");
		TableColumn<Asset, String> columnName = new TableColumn<>("Name");
		TableColumn<Asset, String> columnSrc = new TableColumn<>("Source");
		TableColumn<Asset, String> columnStart = new TableColumn<>("Start");
		TableColumn<Asset, String> columnFormat = new TableColumn<>("Format");
		TableColumn<Asset, String> columnUID = new TableColumn<>("UID");

		// Set cell values
		columnDuration.setCellValueFactory(cellData -> cellData.getValue().durationProperty());
		columnHasVideo.setCellValueFactory(cellData -> cellData.getValue().hasVideoProperty());
		columnHasAudio.setCellValueFactory(cellData -> cellData.getValue().hasAudioProperty());
		columnName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		columnSrc.setCellValueFactory(cellData -> cellData.getValue().srcProperty());
		columnStart.setCellValueFactory(cellData -> cellData.getValue().startProperty());
		columnFormat.setCellValueFactory(cellData -> cellData.getValue().formatProperty());
		columnUID.setCellValueFactory(cellData -> cellData.getValue().uidProperty());

		tableAssets.getColumns().add(columnDuration);
		tableAssets.getColumns().add(columnHasVideo);
		tableAssets.getColumns().add(columnHasAudio);
		tableAssets.getColumns().add(columnName);
		tableAssets.getColumns().add(columnSrc);
		tableAssets.getColumns().add(columnStart);
		tableAssets.getColumns().add(columnFormat);
		tableAssets.getColumns().add(columnUID);

		if (list != null && list.size() > 0) {
			tableAssets.setItems(list);
		}
	}

	/**
	 * Populates the asset table with dynamically created columns
	 *
	 * @param list
	 *            The list of @{code Asset} objects to populate the table with
	 */
	private void populateVideoTable(ObservableList<Video> list) {
		// Clear all previously displayed Assets
		tableVideos.getItems().clear();
		tableVideos.setVisible(true);
		tableAudios.setVisible(false);

		// Set row height
		tableVideos.setFixedCellSize(30);

		// Create columns
		TableColumn<Video, String> columnName = new TableColumn<>("Name");
		TableColumn<Video, Number> columnLane = new TableColumn<>("Lane");
		TableColumn<Video, String> columnStart = new TableColumn<>("Clip start");
		TableColumn<Video, String> columnDuration = new TableColumn<>("Clip duration");
		TableColumn<Video, String> columnOffset = new TableColumn<>("Offset in clip");
		TableColumn<Video, String> columnSrc = new TableColumn<>("Source file");

		// Set cell values
		columnName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		columnLane.setCellValueFactory(cellData -> cellData.getValue().laneProperty());
		columnStart.setCellValueFactory(cellData -> cellData.getValue().startProperty());
		columnDuration.setCellValueFactory(cellData -> cellData.getValue().durationProperty());
		columnOffset.setCellValueFactory(cellData -> cellData.getValue().offsetProperty());
		columnSrc.setCellValueFactory(cellData -> cellData.getValue().refProperty());

		tableVideos.getColumns().add(columnName);
		tableVideos.getColumns().add(columnLane);
		tableVideos.getColumns().add(columnStart);
		tableVideos.getColumns().add(columnDuration);
		tableVideos.getColumns().add(columnOffset);
		tableVideos.getColumns().add(columnSrc);

		if (list != null && list.size() > 0) {
			tableVideos.setItems(list);
		}
	}

	/**
	 * Populates the asset table with dynamically created columns
	 *
	 * @param list
	 *            The list of @{code Asset} objects to populate the table with
	 */
	private void populateAudioTable(ObservableList<Audio> list) {
		// Clear all previously displayed Assets
		tableAudios.getItems().clear();
		tableVideos.setVisible(false);
		tableAudios.setVisible(true);

		// Set row height
		tableAudios.setFixedCellSize(30);

		// Create columns
		TableColumn<Audio, String> columnRole = new TableColumn<>("Role");
		TableColumn<Audio, Number> columnLane = new TableColumn<>("Lane");
		TableColumn<Audio, String> columnStart = new TableColumn<>("Clip start");
		TableColumn<Audio, String> columnDuration = new TableColumn<>("Clip duration");
		TableColumn<Audio, String> columnOffset = new TableColumn<>("Offset in clip");
		TableColumn<Audio, String> columnSrc = new TableColumn<>("Source file");
		TableColumn<Audio, String> columnSrcChannel = new TableColumn<>("Source channel");
		TableColumn<Audio, Number> columnSrcID = new TableColumn<>("Source ID");

		// Set cell values
		columnRole.setCellValueFactory(cellData -> cellData.getValue().roleProperty());
		columnLane.setCellValueFactory(cellData -> cellData.getValue().laneProperty());
		columnStart.setCellValueFactory(cellData -> cellData.getValue().startProperty());
		columnDuration.setCellValueFactory(cellData -> cellData.getValue().durationProperty());
		columnOffset.setCellValueFactory(cellData -> cellData.getValue().offsetProperty());
		columnSrc.setCellValueFactory(cellData -> cellData.getValue().refProperty());
		columnSrcChannel.setCellValueFactory(cellData -> cellData.getValue().srcChProperty());
		columnSrcID.setCellValueFactory(cellData -> cellData.getValue().idProperty());

		tableAudios.getColumns().add(columnRole);
		tableAudios.getColumns().add(columnLane);
		tableAudios.getColumns().add(columnStart);
		tableAudios.getColumns().add(columnDuration);
		tableAudios.getColumns().add(columnOffset);
		tableAudios.getColumns().add(columnSrc);
		tableAudios.getColumns().add(columnSrcChannel);
		tableAudios.getColumns().add(columnSrcID);

		if (list != null && list.size() > 0) {
			tableAudios.setItems(list);
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
		ExtensionFilter filter = new ExtensionFilter(resources.getString("fileType"), "*.fcpxml");
		fileChooser.setTitle(resources.getString("openFile"));
		fileChooser.getExtensionFilters().add(filter);
		fileChooser.setSelectedExtensionFilter(filter);

		// Set initial directory to user's home folder.
		// TODO Test this on Mac (priority) and Windows (not important)
		String home = System.getProperty("user.home");
		File file = new File(home);
		fileChooser.setInitialDirectory(file);

		return fileChooser.showOpenDialog(borderPane.getScene().getWindow());
	}

	private void setFile(File file) {
		if (file != null) {
			filePath = file.getAbsolutePath();

			logger.info("Chosen file: " + filePath);

			try {
				createParser(filePath);
			} catch (ParserConfigurationException | SAXException | IOException e) {
				logger.log(Level.SEVERE, e.getMessage());
			}
		}
	}
}
