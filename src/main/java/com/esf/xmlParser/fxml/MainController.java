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
import com.esf.xmlParser.entities.AssetClip;
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
	@FXML
	private Button buttonGetAssets;
	@FXML
	private Button buttonGetAssetClips;

	// Tables
	@FXML
	private TableView<Asset> tableAssets;
	@FXML
	private TableView<AssetClip> tableAssetClips;
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
			List<Video> items = parser.getVideos();
			ObservableList<Video> list = FXCollections.observableList(items);
			populateVideoTable(list);
		}
	}

	@FXML
	private void buttonGetAudiosClick() {
		if (parser != null) {
			List<Audio> items = parser.getAudios();
			ObservableList<Audio> list = FXCollections.observableList(items);
			populateAudioTable(list);
		}
	}

	@FXML
	private void buttonGetAssetsClick() {
		if (parser != null) {
			List<Asset> items = parser.getAssets();
			ObservableList<Asset> list = FXCollections.observableList(items);
			populateAssetTable(list);
		}
	}

	@FXML
	private void buttonGetAssetClipsClick() {
		if (parser != null) {
			List<AssetClip> items = parser.getAssetClips();
			ObservableList<AssetClip> list = FXCollections.observableList(items);
			populateAssetClipTable(list);
		}
	}

	/**
	 * Populates the asset table with dynamically created columns
	 *
	 * @param list
	 *            The list of @{code Asset} objects to populate the table with
	 */
	private void populateAssetTable(ObservableList<Asset> list) {
		hideTables();
		tableAssets.setVisible(true);

		// Create columns
		TableColumn<Asset, String> columnId = new TableColumn<>(resources.getString("colId"));
		TableColumn<Asset, String> columnDuration = new TableColumn<>(resources.getString("colDuration"));
		TableColumn<Asset, Boolean> columnHasVideo = new TableColumn<>(resources.getString("colHasVideo"));
		TableColumn<Asset, Boolean> columnHasAudio = new TableColumn<>(resources.getString("colHasAudio"));
		TableColumn<Asset, String> columnName = new TableColumn<>(resources.getString("colName"));
		TableColumn<Asset, String> columnSrc = new TableColumn<>(resources.getString("colSrc"));
		TableColumn<Asset, String> columnStart = new TableColumn<>(resources.getString("colStart"));
		TableColumn<Asset, String> columnFormat = new TableColumn<>(resources.getString("colFormat"));
		TableColumn<Asset, String> columnUID = new TableColumn<>(resources.getString("colUID"));

		// Set cell values
		columnId.setCellValueFactory(cellData -> cellData.getValue().idProperty());
		columnDuration.setCellValueFactory(cellData -> cellData.getValue().durationProperty());
		columnHasVideo.setCellValueFactory(cellData -> cellData.getValue().hasVideoProperty());
		columnHasAudio.setCellValueFactory(cellData -> cellData.getValue().hasAudioProperty());
		columnName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		columnSrc.setCellValueFactory(cellData -> cellData.getValue().srcProperty());
		columnStart.setCellValueFactory(cellData -> cellData.getValue().startProperty());
		columnFormat.setCellValueFactory(cellData -> cellData.getValue().formatProperty());
		columnUID.setCellValueFactory(cellData -> cellData.getValue().uidProperty());

		tableAssets.getColumns().add(columnId);
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
	private void populateAssetClipTable(ObservableList<AssetClip> list) {
		hideTables();
		tableAssetClips.setVisible(true);

		// Create columns
		TableColumn<AssetClip, String> columnRef = new TableColumn<>(resources.getString("colRef"));
		TableColumn<AssetClip, String> columnName = new TableColumn<>(resources.getString("colName"));
		TableColumn<AssetClip, Number> columnLane = new TableColumn<>(resources.getString("colLane"));
		TableColumn<AssetClip, String> columnOffset = new TableColumn<>(resources.getString("colOffset"));
		TableColumn<AssetClip, String> columnDuration = new TableColumn<>(resources.getString("colDuration"));
		TableColumn<AssetClip, String> columnStart = new TableColumn<>(resources.getString("colStart"));
		TableColumn<AssetClip, String> columnRole = new TableColumn<>(resources.getString("colRole"));
		TableColumn<AssetClip, String> columnFormat = new TableColumn<>(resources.getString("colFormat"));
		TableColumn<AssetClip, String> columnTcFormat = new TableColumn<>(resources.getString("colTcFormat"));

		// Set cell values
		columnRef.setCellValueFactory(cellData -> cellData.getValue().refProperty());
		columnName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		columnLane.setCellValueFactory(cellData -> cellData.getValue().laneProperty());
		columnOffset.setCellValueFactory(cellData -> cellData.getValue().offsetProperty());
		columnDuration.setCellValueFactory(cellData -> cellData.getValue().durationProperty());
		columnStart.setCellValueFactory(cellData -> cellData.getValue().startProperty());
		columnRole.setCellValueFactory(cellData -> cellData.getValue().roleProperty());
		columnFormat.setCellValueFactory(cellData -> cellData.getValue().formatProperty());
		columnTcFormat.setCellValueFactory(cellData -> cellData.getValue().tcFormatProperty());

		tableAssetClips.getColumns().add(columnRef);
		tableAssetClips.getColumns().add(columnName);
		tableAssetClips.getColumns().add(columnLane);
		tableAssetClips.getColumns().add(columnOffset);
		tableAssetClips.getColumns().add(columnDuration);
		tableAssetClips.getColumns().add(columnStart);
		tableAssetClips.getColumns().add(columnRole);
		tableAssetClips.getColumns().add(columnFormat);
		tableAssetClips.getColumns().add(columnTcFormat);

		if (list != null && list.size() > 0) {
			tableAssetClips.setItems(list);
		}
	}

	/**
	 * Populates the asset table with dynamically created columns
	 *
	 * @param list
	 *            The list of @{code Asset} objects to populate the table with
	 */
	private void populateVideoTable(ObservableList<Video> list) {
		hideTables();
		tableVideos.setVisible(true);

		// Create columns
		TableColumn<Video, String> columnName = new TableColumn<>(resources.getString("colName"));
		TableColumn<Video, Number> columnLane = new TableColumn<>(resources.getString("colLane"));
		TableColumn<Video, String> columnStart = new TableColumn<>(resources.getString("colStart"));
		TableColumn<Video, String> columnDuration = new TableColumn<>(resources.getString("colDuration"));
		TableColumn<Video, String> columnOffset = new TableColumn<>(resources.getString("colOffset"));
		TableColumn<Video, String> columnSrc = new TableColumn<>(resources.getString("colRef"));

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
		hideTables();
		tableAudios.setVisible(true);

		// Create columns
		TableColumn<Audio, String> columnRole = new TableColumn<>(resources.getString("colRole"));
		TableColumn<Audio, Number> columnLane = new TableColumn<>(resources.getString("colLane"));
		TableColumn<Audio, String> columnStart = new TableColumn<>(resources.getString("colStart"));
		TableColumn<Audio, String> columnDuration = new TableColumn<>(resources.getString("colDuration"));
		TableColumn<Audio, String> columnOffset = new TableColumn<>(resources.getString("colOffset"));
		TableColumn<Audio, String> columnSrc = new TableColumn<>(resources.getString("colRef"));
		TableColumn<Audio, String> columnSrcChannel = new TableColumn<>(resources.getString("colSrcCh"));
		TableColumn<Audio, Number> columnSrcID = new TableColumn<>(resources.getString("colSrcId"));

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

	private void hideTables() {
		tableVideos.setVisible(false);
		tableAudios.setVisible(false);
		tableAssets.setVisible(false);
		tableAssetClips.setVisible(false);

		// Clear all previously displayed items
		tableVideos.getItems().clear();
		tableAudios.getItems().clear();
		tableAssets.getItems().clear();
		tableAssetClips.getItems().clear();

		// Set row height
		tableVideos.setFixedCellSize(30);
		tableAudios.setFixedCellSize(30);
		tableAssets.setFixedCellSize(30);
		tableAssetClips.setFixedCellSize(30);
		
		// Remove columns
		tableVideos.getColumns().clear();
		tableAudios.getColumns().clear();
		tableAssets.getColumns().clear();
		tableAssetClips.getColumns().clear();
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
