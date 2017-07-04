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
import com.esf.xmlParser.entities.Effect;
import com.esf.xmlParser.entities.Format;
import com.esf.xmlParser.entities.Video;
import com.esf.xmlParser.util.Parser;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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

	// Sidebar buttons
	@FXML
	private Tab tabVideos;
	@FXML
	private Tab tabAudios;
	@FXML
	private Tab tabAssets;
	@FXML
	private Tab tabAssetClips;
	@FXML
	private Tab tabFormats;
	@FXML
	private Tab tabEffects;

	// Tables
	@FXML
	private TableView<Asset> tableAssets;
	@FXML
	private TableView<AssetClip> tableAssetClips;
	@FXML
	private TableView<Video> tableVideos;
	@FXML
	private TableView<Audio> tableAudios;
	@FXML
	private TableView<Format> tableFormats;
	@FXML
	private TableView<Effect> tableEffects;

	private final Logger logger = Logger.getLogger(this.getClass().getName());
	private Parser parser;
	private String filePath;

	private List<Video> videos;
	private List<Audio> audios;
	private List<Asset> assets;
	private List<AssetClip> assetClips;
	private List<Format> formats;
	private List<Effect> effects;

	@FXML
	private void initialize() {
		logger.info("Start Application");
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
			videos = parser.getVideos();
			audios = parser.getAudios();
			assets = parser.getAssets();
			assetClips = parser.getAssetClips();
			formats = parser.getFormats();
			effects = parser.getEffects();

			// Populate tables with file contents.
			populateTables();
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
		parser = new Parser(filePath);
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

	private void populateTables() {
		populateVideosTable(FXCollections.observableList(videos));
		populateAudiosTable(FXCollections.observableList(audios));
		populateAssetsTable(FXCollections.observableList(assets));
		populateAssetClipsTable(FXCollections.observableList(assetClips));
		populateFormatsTable(FXCollections.observableList(formats));
		populateEffectsTable(FXCollections.observableList(effects));
	}

	/**
	 * Populates the asset table with dynamically created columns
	 *
	 * @param list
	 *            The list of @{code Asset} objects to populate the table with
	 */
	private void populateAssetsTable(ObservableList<Asset> list) {
		TableView<Asset> table = tableAssets;

		resetTable(table);

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

		table.getColumns().add(columnId);
		table.getColumns().add(columnDuration);
		table.getColumns().add(columnHasVideo);
		table.getColumns().add(columnHasAudio);
		table.getColumns().add(columnName);
		table.getColumns().add(columnSrc);
		table.getColumns().add(columnStart);
		table.getColumns().add(columnFormat);
		table.getColumns().add(columnUID);

		if (list != null && list.size() > 0) {
			table.setItems(list);
		}
	}

	/**
	 * Populates the asset table with dynamically created columns
	 *
	 * @param list
	 *            The list of @{code Asset} objects to populate the table with
	 */
	private void populateAssetClipsTable(ObservableList<AssetClip> list) {
		TableView<AssetClip> table = tableAssetClips;

		resetTable(table);

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

		table.getColumns().add(columnRef);
		table.getColumns().add(columnName);
		table.getColumns().add(columnLane);
		table.getColumns().add(columnOffset);
		table.getColumns().add(columnDuration);
		table.getColumns().add(columnStart);
		table.getColumns().add(columnRole);
		table.getColumns().add(columnFormat);
		table.getColumns().add(columnTcFormat);

		if (list != null && list.size() > 0) {
			table.setItems(list);
		}
	}

	/**
	 * Populates the asset table with dynamically created columns
	 *
	 * @param list
	 *            The list of @{code Asset} objects to populate the table with
	 */
	private void populateVideosTable(ObservableList<Video> list) {
		TableView<Video> table = tableVideos;

		resetTable(table);

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

		table.getColumns().add(columnName);
		table.getColumns().add(columnLane);
		table.getColumns().add(columnStart);
		table.getColumns().add(columnDuration);
		table.getColumns().add(columnOffset);
		table.getColumns().add(columnSrc);

		if (list != null && list.size() > 0) {
			table.setItems(list);
		}
	}

	/**
	 * Populates the asset table with dynamically created columns
	 *
	 * @param list
	 *            The list of @{code Asset} objects to populate the table with
	 */
	private void populateAudiosTable(ObservableList<Audio> list) {
		TableView<Audio> table = tableAudios;

		resetTable(table);

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

		table.getColumns().add(columnRole);
		table.getColumns().add(columnLane);
		table.getColumns().add(columnStart);
		table.getColumns().add(columnDuration);
		table.getColumns().add(columnOffset);
		table.getColumns().add(columnSrc);
		table.getColumns().add(columnSrcChannel);
		table.getColumns().add(columnSrcID);

		if (list != null && list.size() > 0) {
			table.setItems(list);
		}
	}

	/**
	 * Populates the asset table with dynamically created columns
	 *
	 * @param list
	 *            The list of @{code Asset} objects to populate the table with
	 */
	private void populateFormatsTable(ObservableList<Format> list) {
		TableView<Format> table = tableFormats;

		resetTable(table);

		// Create columns
		TableColumn<Format, String> columnId = new TableColumn<>(resources.getString("colId"));
		TableColumn<Format, String> columnName = new TableColumn<>(resources.getString("colName"));
		TableColumn<Format, Number> columnWidth = new TableColumn<>(resources.getString("colWidth"));
		TableColumn<Format, Number> columnHeight = new TableColumn<>(resources.getString("colHeight"));
		TableColumn<Format, String> columnFrameDuration = new TableColumn<>(resources.getString("colFrameRate"));

		// Set cell values
		columnId.setCellValueFactory(cellData -> cellData.getValue().idProperty());
		columnName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		columnWidth.setCellValueFactory(cellData -> cellData.getValue().widthProperty());
		columnHeight.setCellValueFactory(cellData -> cellData.getValue().heightProperty());
		columnFrameDuration.setCellValueFactory(cellData -> cellData.getValue().frameDurationProperty());

		table.getColumns().add(columnId);
		table.getColumns().add(columnName);
		table.getColumns().add(columnWidth);
		table.getColumns().add(columnHeight);
		table.getColumns().add(columnFrameDuration);

		if (list != null && list.size() > 0) {
			table.setItems(list);
		}
	}

	/**
	 * Populates the asset table with dynamically created columns
	 *
	 * @param list
	 *            The list of @{code Asset} objects to populate the table with
	 */
	private void populateEffectsTable(ObservableList<Effect> list) {
		TableView<Effect> table = tableEffects;

		resetTable(table);

		// Create columns
		TableColumn<Effect, String> columnId = new TableColumn<>(resources.getString("colId"));
		TableColumn<Effect, String> columnName = new TableColumn<>(resources.getString("colName"));
		TableColumn<Effect, String> columnSrc = new TableColumn<>(resources.getString("colSrc"));
		TableColumn<Effect, String> columnUid = new TableColumn<>(resources.getString("colUID"));

		// Set cell values
		columnId.setCellValueFactory(cellData -> cellData.getValue().idProperty());
		columnName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		columnSrc.setCellValueFactory(cellData -> cellData.getValue().srcProperty());
		columnUid.setCellValueFactory(cellData -> cellData.getValue().uidProperty());

		table.getColumns().add(columnId);
		table.getColumns().add(columnName);
		table.getColumns().add(columnSrc);
		table.getColumns().add(columnUid);

		if (list != null && list.size() > 0) {
			table.setItems(list);
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
		String home = System.getProperty("user.home");
		File file = new File(home);
		fileChooser.setInitialDirectory(file);

		return fileChooser.showOpenDialog(borderPane.getScene().getWindow());
	}

	private <T> void resetTable(TableView<T> table) {

		// Clear all previously displayed items.
		table.getItems().clear();

		// Set row height.
		table.setFixedCellSize(30);

		// Remove columns.
		table.getColumns().clear();
	}

	private void setFile(File file) {
		if (file != null) {
			filePath = file.getAbsolutePath();

			logger.info("Chosen file: " + filePath);

			try {
				createParser(filePath);
			} catch (ParserConfigurationException | SAXException | IOException e) {
				logger.log(Level.SEVERE, e.getMessage());
				// TODO Show an error dialog box
			}
			
			// Set file name in title bar
			((Stage) menubar.getScene().getWindow()).setTitle(resources.getString("appName") + " - " + filePath);
		}
	}
}
