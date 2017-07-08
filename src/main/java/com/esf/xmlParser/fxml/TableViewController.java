package com.esf.xmlParser.fxml;

import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import com.esf.xmlParser.entities.Asset;
import com.esf.xmlParser.entities.AssetClip;
import com.esf.xmlParser.entities.Audio;
import com.esf.xmlParser.entities.Clip;
import com.esf.xmlParser.entities.Effect;
import com.esf.xmlParser.entities.Format;
import com.esf.xmlParser.entities.Video;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class TableViewController {

	@FXML
	private ResourceBundle resources;

	// Main pane
	@FXML
	private TabPane tabPane;

	// Tabs
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

	public void populateTables(List<Asset> assets, List<AssetClip> assetClips, List<Audio> audios, List<Clip> clips, List<Effect> effects, List<Format> formats, List<Video> videos) {

		createTables();

		logger.info("Populating all tables...");
		populateAssetsTable(FXCollections.observableList(assets));
		populateAssetClipsTable(FXCollections.observableList(assetClips));
		populateAudiosTable(FXCollections.observableList(audios));
		populateEffectsTable(FXCollections.observableList(effects));
		populateFormatsTable(FXCollections.observableList(formats));
		populateVideosTable(FXCollections.observableList(videos));
	}

	private void createTables() {
		logger.info("Creating all tables...");
		createAssetsTable();
		createAssetClipsTable();
		createAudiosTable();
		createEffectsTable();
		createFormatsTable();
		createVideosTable();
	}

	private void createAssetsTable() {
		logger.info("Creating assets table...");
		TableView<Asset> table = tableAssets;

		resetTable(table);

		// Create columns.
		TableColumn<Asset, String> columnId = new TableColumn<>(resources.getString("colId"));
		TableColumn<Asset, String> columnDuration = new TableColumn<>(resources.getString("colDuration"));
		TableColumn<Asset, Boolean> columnHasVideo = new TableColumn<>(resources.getString("colHasVideo"));
		TableColumn<Asset, Boolean> columnHasAudio = new TableColumn<>(resources.getString("colHasAudio"));
		TableColumn<Asset, String> columnName = new TableColumn<>(resources.getString("colName"));
		TableColumn<Asset, String> columnSrc = new TableColumn<>(resources.getString("colSrc"));
		TableColumn<Asset, String> columnStart = new TableColumn<>(resources.getString("colStart"));
		TableColumn<Asset, String> columnFormatName = new TableColumn<>(resources.getString("colFormatName"));
		TableColumn<Asset, Number> columnWidth = new TableColumn<>(resources.getString("colWidth"));
		TableColumn<Asset, Number> columnHeight = new TableColumn<>(resources.getString("colHeight"));
		TableColumn<Asset, String> columnFrameDuration = new TableColumn<>(resources.getString("colFrameRate"));
		TableColumn<Asset, String> columnUID = new TableColumn<>(resources.getString("colUID"));

		// Set cell values.
		columnId.setCellValueFactory(cellData -> cellData.getValue().idProperty());
		columnDuration.setCellValueFactory(cellData -> cellData.getValue().durationProperty());
		columnHasVideo.setCellValueFactory(cellData -> cellData.getValue().hasVideoProperty());
		columnHasAudio.setCellValueFactory(cellData -> cellData.getValue().hasAudioProperty());
		columnName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		columnSrc.setCellValueFactory(cellData -> cellData.getValue().srcProperty());
		columnStart.setCellValueFactory(cellData -> cellData.getValue().startProperty());
		columnFormatName.setCellValueFactory(cellData -> cellData.getValue().getFormat().nameProperty());
		columnWidth.setCellValueFactory(cellData -> cellData.getValue().getFormat().widthProperty());
		columnHeight.setCellValueFactory(cellData -> cellData.getValue().getFormat().heightProperty());
		columnFrameDuration.setCellValueFactory(cellData -> cellData.getValue().getFormat().frameDurationProperty());
		columnUID.setCellValueFactory(cellData -> cellData.getValue().uidProperty());

		// Override default id column sorting. TODO
		columnId.setComparator(
				(id1, id2) -> Integer.compare(Integer.parseInt(id1.substring(1)), Integer.parseInt(id2.substring(1))));

		ObservableList<TableColumn<Asset, ?>> columns = table.getColumns();
		columns.add(columnId);
		columns.add(columnDuration);
		columns.add(columnHasVideo);
		columns.add(columnHasAudio);
		columns.add(columnName);
		columns.add(columnSrc);
		columns.add(columnStart);
		columns.add(columnFormatName);
		columns.add(columnWidth);
		columns.add(columnHeight);
		columns.add(columnFrameDuration);
		columns.add(columnUID);
	}

	private void createAssetClipsTable() {
		logger.info("Creating asset clips table...");
		TableView<AssetClip> table = tableAssetClips;

		// Create columns
		TableColumn<AssetClip, String> columnRef = new TableColumn<>(resources.getString("colRef"));
		TableColumn<AssetClip, String> columnName = new TableColumn<>(resources.getString("colName"));
		TableColumn<AssetClip, Number> columnLane = new TableColumn<>(resources.getString("colLane"));
		TableColumn<AssetClip, String> columnOffset = new TableColumn<>(resources.getString("colOffset"));
		TableColumn<AssetClip, String> columnDuration = new TableColumn<>(resources.getString("colDuration"));
		TableColumn<AssetClip, String> columnStart = new TableColumn<>(resources.getString("colStart"));
		TableColumn<AssetClip, String> columnRole = new TableColumn<>(resources.getString("colRole"));
		TableColumn<AssetClip, String> columnFormatName = new TableColumn<>(resources.getString("colFormatName"));
		TableColumn<AssetClip, Number> columnWidth = new TableColumn<>(resources.getString("colWidth"));
		TableColumn<AssetClip, Number> columnHeight = new TableColumn<>(resources.getString("colHeight"));
		TableColumn<AssetClip, String> columnFrameDuration = new TableColumn<>(resources.getString("colFrameRate"));
		TableColumn<AssetClip, String> columnTcFormat = new TableColumn<>(resources.getString("colTcFormat"));

		// Set cell values
		columnRef.setCellValueFactory(cellData -> cellData.getValue().getAsset().srcProperty());
		columnName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		columnLane.setCellValueFactory(cellData -> cellData.getValue().laneProperty());
		columnOffset.setCellValueFactory(cellData -> cellData.getValue().offsetProperty());
		columnDuration.setCellValueFactory(cellData -> cellData.getValue().durationProperty());
		columnStart.setCellValueFactory(cellData -> cellData.getValue().startProperty());
		columnRole.setCellValueFactory(cellData -> cellData.getValue().roleProperty());
		columnFormatName.setCellValueFactory(cellData -> cellData.getValue().getFormat().nameProperty());
		columnWidth.setCellValueFactory(cellData -> cellData.getValue().getFormat().widthProperty());
		columnHeight.setCellValueFactory(cellData -> cellData.getValue().getFormat().heightProperty());
		columnFrameDuration.setCellValueFactory(cellData -> cellData.getValue().getFormat().frameDurationProperty());
		columnTcFormat.setCellValueFactory(cellData -> cellData.getValue().tcFormatProperty());

		ObservableList<TableColumn<AssetClip, ?>> columns = table.getColumns();
		columns.add(columnRef);
		columns.add(columnName);
		columns.add(columnLane);
		columns.add(columnOffset);
		columns.add(columnDuration);
		columns.add(columnStart);
		columns.add(columnRole);
		columns.add(columnFormatName);
		columns.add(columnWidth);
		columns.add(columnHeight);
		columns.add(columnFrameDuration);
		columns.add(columnTcFormat);
	}

	private void createAudiosTable() {
		logger.info("Creating audios table...");
		TableView<Audio> table = tableAudios;

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
		columnSrc.setCellValueFactory(cellData -> cellData.getValue().getAsset().srcProperty());
		columnSrcChannel.setCellValueFactory(cellData -> cellData.getValue().srcChProperty());
		columnSrcID.setCellValueFactory(cellData -> cellData.getValue().srcIdProperty());

		ObservableList<TableColumn<Audio, ?>> columns = table.getColumns();
		columns.add(columnRole);
		columns.add(columnLane);
		columns.add(columnStart);
		columns.add(columnDuration);
		columns.add(columnOffset);
		columns.add(columnSrc);
		columns.add(columnSrcChannel);
		columns.add(columnSrcID);
	}

	private void createEffectsTable() {
		logger.info("Creating effects table...");
		TableView<Effect> table = tableEffects;

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

		ObservableList<TableColumn<Effect, ?>> columns = table.getColumns();
		columns.add(columnId);
		columns.add(columnName);
		columns.add(columnSrc);
		columns.add(columnUid);
	}

	private void createFormatsTable() {
		logger.info("Creating formats table...");
		TableView<Format> table = tableFormats;

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

		ObservableList<TableColumn<Format, ?>> columns = table.getColumns();
		columns.add(columnId);
		columns.add(columnName);
		columns.add(columnWidth);
		columns.add(columnHeight);
		columns.add(columnFrameDuration);
	}

	private void createVideosTable() {
		logger.info("Creating videos table...");
		TableView<Video> table = tableVideos;

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
		columnSrc.setCellValueFactory(cellData -> cellData.getValue().getAsset().srcProperty());

		ObservableList<TableColumn<Video, ?>> columns = table.getColumns();
		columns.add(columnName);
		columns.add(columnLane);
		columns.add(columnStart);
		columns.add(columnDuration);
		columns.add(columnOffset);
		columns.add(columnSrc);
	}

	/**
	 * Populates the asset table
	 *
	 * @param list
	 *            The list of @{code Asset} objects with which to populate the
	 *            table
	 */
	private void populateAssetsTable(ObservableList<Asset> list) {
		logger.info("Populating assets table...");
		TableView<Asset> table = tableAssets;

		clearTable(table);

		if (list != null && list.size() > 0) {
			table.setItems(list);
		}
	}

	/**
	 * Populates the asset clip table
	 *
	 * @param list
	 *            The list of @{code AssetClip} objects with which to populate
	 *            the table
	 */
	private void populateAssetClipsTable(ObservableList<AssetClip> list) {
		logger.info("Populating asset clips table...");
		TableView<AssetClip> table = tableAssetClips;

		clearTable(table);

		if (list != null && list.size() > 0) {
			table.setItems(list);
		}
	}

	/**
	 * Populates the audio table
	 *
	 * @param list
	 *            The list of @{code Audio} objects with which to populate the
	 *            table
	 */
	private void populateAudiosTable(ObservableList<Audio> list) {
		logger.info("Populating audios table...");
		TableView<Audio> table = tableAudios;

		clearTable(table);

		if (list != null && list.size() > 0) {
			table.setItems(list);
		}
	}

	/**
	 * Populates the effect table
	 *
	 * @param list
	 *            The list of @{code Effect} objects with which to populate the
	 *            table
	 */
	private void populateEffectsTable(ObservableList<Effect> list) {
		logger.info("Populating effects table...");
		TableView<Effect> table = tableEffects;

		clearTable(table);

		if (list != null && list.size() > 0) {
			table.setItems(list);
		}
	}

	/**
	 * Populates the format table
	 *
	 * @param list
	 *            The list of @{code Format} objects with which to populate the
	 *            table
	 */
	private void populateFormatsTable(ObservableList<Format> list) {
		logger.info("Populating formats table...");
		TableView<Format> table = tableFormats;

		clearTable(table);

		if (list != null && list.size() > 0) {
			table.setItems(list);
		}
	}

	/**
	 * Populates the video table
	 *
	 * @param list
	 *            The list of @{code Video} objects with which to populate the
	 *            table
	 */
	private void populateVideosTable(ObservableList<Video> list) {
		logger.info("Populating videos table...");
		TableView<Video> table = tableVideos;

		clearTable(table);

		if (list != null && list.size() > 0) {
			table.setItems(list);
		}
	}

	/**
	 * Removes all data in the table, but leaves the columns.
	 * 
	 * @param table
	 *            The table to clear.
	 */
	private <T> void clearTable(TableView<T> table) {
		// Clear all previously displayed items.
		table.getItems().clear();
	}

	/**
	 * Removes the given table's columns and data.
	 * 
	 * @param table
	 *            The table to reset.
	 */
	private <T> void resetTable(TableView<T> table) {
		// Remove all columns from table
		table.getColumns().clear();

		clearTable(table);
	}
}
