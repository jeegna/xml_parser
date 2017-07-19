package com.esf.xmlParser.fxml;

import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import com.esf.xmlParser.entities.Asset;
import com.esf.xmlParser.entities.AssetClip;
import com.esf.xmlParser.entities.Audio;
import com.esf.xmlParser.entities.Clip;
import com.esf.xmlParser.entities.Effect;
import com.esf.xmlParser.entities.Element;
import com.esf.xmlParser.entities.Format;
import com.esf.xmlParser.entities.Video;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * The SearchView controller class. This class is linked to SearchContrller.fxml
 * and provides handler methods for the view.
 * 
 * @author Jeegna Patel
 * @version
 * @since 1.8
 */
public class TableViewController {

	@FXML
	private ResourceBundle resources;

	// Main pane
	@FXML
	private TabPane tabPane;

	// Tabs
	@FXML
	private Tab tabAssets;
	@FXML
	private Tab tabAssetClips;
	@FXML
	private Tab tabAudios;
	@FXML
	private Tab tabClips;
	@FXML
	private Tab tabEffects;
	@FXML
	private Tab tabFormats;
	@FXML
	private Tab tabVideos;

	// Tables
	@FXML
	private TableView<Asset> tableAssets;
	@FXML
	private TableView<AssetClip> tableAssetClips;
	@FXML
	private TableView<Audio> tableAudios;
	@FXML
	private TableView<Clip> tableClips;
	@FXML
	private TableView<Effect> tableEffects;
	@FXML
	private TableView<Format> tableFormats;
	@FXML
	private TableView<Video> tableVideos;

	private final Logger logger = Logger.getLogger(this.getClass().getName());

	/**
	 * Selects an element in the table. This method will display the tabs which
	 * contains the element and highlight it in its table.
	 *
	 * @param element
	 *            The element that was selected.
	 */
	public void selectItem(Element element) {
		String name = element.getClass().getSimpleName();
		switch (name) {
		case "Asset":
			tabPane.getSelectionModel().select(tabAssets);
			Asset asset = (Asset) element;
			tableAssets.getSelectionModel().select(asset);
			tableAssets.scrollTo(asset);
			break;
		case "AssetClip":
			tabPane.getSelectionModel().select(tabAssetClips);
			AssetClip assetClip = (AssetClip) element;
			tableAssetClips.getSelectionModel().select(assetClip);
			tableAssetClips.scrollTo(assetClip);
			break;
		case "Audio":
			tabPane.getSelectionModel().select(tabAudios);
			Audio audio = (Audio) element;
			tableAudios.getSelectionModel().select(audio);
			tableAudios.scrollTo(audio);
			break;
		case "Clip":
			tabPane.getSelectionModel().select(tabClips);
			Clip clip = (Clip) element;
			tableClips.getSelectionModel().select(clip);
			tableClips.scrollTo(clip);
			break;
		case "Effect":
			tabPane.getSelectionModel().select(tabEffects);
			Effect effect = (Effect) element;
			tableEffects.getSelectionModel().select(effect);
			tableEffects.scrollTo(effect);
			break;
		case "Format":
			tabPane.getSelectionModel().select(tabFormats);
			Format format = (Format) element;
			tableFormats.getSelectionModel().select(format);
			tableFormats.scrollTo(format);
			break;
		case "Video":
			tabPane.getSelectionModel().select(tabVideos);
			Video video = (Video) element;
			tableVideos.getSelectionModel().select(video);
			tableVideos.scrollTo(video);
			break;
		}
	}

	/**
	 * Populate the tables with the given lists of entities.
	 *
	 * @param assets
	 *            The assets.
	 * @param assetClips
	 *            The asset clips.
	 * @param audios
	 *            The audios.
	 * @param clips
	 *            The clips.
	 * @param effects
	 *            The effects.
	 * @param formats
	 *            The formats.
	 * @param videos
	 *            The videos.
	 */
	public void populateTables(List<Asset> assets, List<AssetClip> assetClips, List<Audio> audios, List<Clip> clips,
			List<Effect> effects, List<Format> formats, List<Video> videos) {

		createTables();

		logger.info("Populating all tables...");
		populateAssetsTable(FXCollections.observableList(assets));
		populateAssetClipsTable(FXCollections.observableList(assetClips));
		populateAudiosTable(FXCollections.observableList(audios));
		populateClipsTable(FXCollections.observableList(clips));
		populateEffectsTable(FXCollections.observableList(effects));
		populateFormatsTable(FXCollections.observableList(formats));
		populateVideosTable(FXCollections.observableList(videos));
	}

	/**
	 * Creates the tables.
	 */
	private void createTables() {
		logger.info("Creating all tables...");
		createAssetsTable();
		createAssetClipsTable();
		createAudiosTable();
		createClipsTable();
		createEffectsTable();
		createFormatsTable();
		createVideosTable();
	}

	/**
	 * Creates the assets table.
	 */
	private void createAssetsTable() {
		logger.info("Creating assets table...");
		TableView<Asset> table = tableAssets;

		resetTable(table);
		table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

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
		TableColumn<Asset, Number> columnAudioChannels = new TableColumn<>(resources.getString("colAudioChannels"));
		TableColumn<Asset, Number> columnAudioRate = new TableColumn<>(resources.getString("colAudioRate"));
		TableColumn<Asset, Number> columnAudioSources = new TableColumn<>(resources.getString("colAudioSources"));
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
		columnAudioChannels.setCellValueFactory(cellData -> cellData.getValue().audioChannelsProperty());
		columnAudioRate.setCellValueFactory(cellData -> cellData.getValue().audioRateProperty());
		columnAudioSources.setCellValueFactory(cellData -> cellData.getValue().audioSourcesProperty());
		columnUID.setCellValueFactory(cellData -> cellData.getValue().uidProperty());

		// Override default column sorting.
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
		columns.add(columnAudioChannels);
		columns.add(columnAudioRate);
		columns.add(columnAudioSources);
		columns.add(columnUID);
	}

	/**
	 * Creates the asset clips table.
	 */
	private void createAssetClipsTable() {
		logger.info("Creating asset clips table...");
		TableView<AssetClip> table = tableAssetClips;

		resetTable(table);
		table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		// Create columns.
		TableColumn<AssetClip, Number> columnId = new TableColumn<>(resources.getString("colId"));
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

		// Set cell values.
		columnId.setCellValueFactory(cellData -> cellData.getValue().idProperty());
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
		columns.add(columnId);
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
		columns.add(columnRef);
	}

	/**
	 * Creates the audios table.
	 */
	private void createAudiosTable() {
		logger.info("Creating audios table...");
		TableView<Audio> table = tableAudios;

		resetTable(table);
		table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		// Create columns.
		TableColumn<Audio, Number> columnId = new TableColumn<>(resources.getString("colId"));
		TableColumn<Audio, String> columnName = new TableColumn<>(resources.getString("colName"));
		TableColumn<Audio, String> columnRole = new TableColumn<>(resources.getString("colRole"));
		TableColumn<Audio, Number> columnLane = new TableColumn<>(resources.getString("colLane"));
		TableColumn<Audio, String> columnStart = new TableColumn<>(resources.getString("colStart"));
		TableColumn<Audio, String> columnDuration = new TableColumn<>(resources.getString("colDuration"));
		TableColumn<Audio, String> columnOffset = new TableColumn<>(resources.getString("colOffset"));
		TableColumn<Audio, String> columnSrc = new TableColumn<>(resources.getString("colRef"));
		TableColumn<Audio, String> columnSrcChannel = new TableColumn<>(resources.getString("colSrcCh"));
		TableColumn<Audio, Number> columnSrcID = new TableColumn<>(resources.getString("colSrcId"));
		TableColumn<Audio, String> columnTcFormat = new TableColumn<>(resources.getString("colTcFormat"));

		// Set cell values.
		columnId.setCellValueFactory(cellData -> cellData.getValue().idProperty());
		columnName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		columnRole.setCellValueFactory(cellData -> cellData.getValue().roleProperty());
		columnLane.setCellValueFactory(cellData -> cellData.getValue().laneProperty());
		columnStart.setCellValueFactory(cellData -> cellData.getValue().startProperty());
		columnDuration.setCellValueFactory(cellData -> cellData.getValue().durationProperty());
		columnOffset.setCellValueFactory(cellData -> cellData.getValue().offsetProperty());
		columnSrc.setCellValueFactory(cellData -> cellData.getValue().getAsset().srcProperty());
		columnSrcChannel.setCellValueFactory(cellData -> cellData.getValue().srcChProperty());
		columnSrcID.setCellValueFactory(cellData -> cellData.getValue().srcIdProperty());
		columnTcFormat.setCellValueFactory(cellData -> cellData.getValue().tcFormatProperty());

		ObservableList<TableColumn<Audio, ?>> columns = table.getColumns();
		columns.add(columnId);
		columns.add(columnName);
		columns.add(columnRole);
		columns.add(columnLane);
		columns.add(columnStart);
		columns.add(columnDuration);
		columns.add(columnOffset);
		columns.add(columnSrc);
		columns.add(columnSrcChannel);
		columns.add(columnSrcID);
		columns.add(columnTcFormat);
	}

	/**
	 * Creates the clips table.
	 */
	private void createClipsTable() {
		logger.info("Creating clips table...");
		TableView<Clip> table = tableClips;

		resetTable(table);
		table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		// Create columns.
		TableColumn<Clip, Number> columnId = new TableColumn<>(resources.getString("colId"));
		TableColumn<Clip, String> columnName = new TableColumn<>(resources.getString("colName"));
		TableColumn<Clip, String> columnOffset = new TableColumn<>(resources.getString("colOffset"));
		TableColumn<Clip, String> columnDuration = new TableColumn<>(resources.getString("colDuration"));
		TableColumn<Clip, String> columnStart = new TableColumn<>(resources.getString("colStart"));
		TableColumn<Clip, String> columnTcFormat = new TableColumn<>(resources.getString("colTcFormat"));

		// Set cell values.
		columnId.setCellValueFactory(cellData -> cellData.getValue().idProperty());
		columnName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		columnOffset.setCellValueFactory(cellData -> cellData.getValue().offsetProperty());
		columnDuration.setCellValueFactory(cellData -> cellData.getValue().durationProperty());
		columnStart.setCellValueFactory(cellData -> cellData.getValue().startProperty());
		columnTcFormat.setCellValueFactory(cellData -> cellData.getValue().tcFormatProperty());

		ObservableList<TableColumn<Clip, ?>> columns = table.getColumns();
		columns.add(columnId);
		columns.add(columnName);
		columns.add(columnOffset);
		columns.add(columnDuration);
		columns.add(columnStart);
		columns.add(columnTcFormat);
	}

	/**
	 * Creates the effects table.
	 */
	private void createEffectsTable() {
		logger.info("Creating effects table...");
		TableView<Effect> table = tableEffects;

		resetTable(table);
		table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

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

		// Override default column sorting.
		columnId.setComparator(
				(id1, id2) -> Integer.compare(Integer.parseInt(id1.substring(1)), Integer.parseInt(id2.substring(1))));

		ObservableList<TableColumn<Effect, ?>> columns = table.getColumns();
		columns.add(columnId);
		columns.add(columnName);
		columns.add(columnSrc);
		columns.add(columnUid);
	}

	/**
	 * Creates the formats table.
	 */
	private void createFormatsTable() {
		logger.info("Creating formats table...");
		TableView<Format> table = tableFormats;

		resetTable(table);
		table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		// Create columns.
		TableColumn<Format, String> columnId = new TableColumn<>(resources.getString("colId"));
		TableColumn<Format, String> columnName = new TableColumn<>(resources.getString("colName"));
		TableColumn<Format, Number> columnWidth = new TableColumn<>(resources.getString("colWidth"));
		TableColumn<Format, Number> columnHeight = new TableColumn<>(resources.getString("colHeight"));
		TableColumn<Format, String> columnFrameDuration = new TableColumn<>(resources.getString("colFrameRate"));

		// Set cell values.
		columnId.setCellValueFactory(cellData -> cellData.getValue().idProperty());
		columnName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		columnWidth.setCellValueFactory(cellData -> cellData.getValue().widthProperty());
		columnHeight.setCellValueFactory(cellData -> cellData.getValue().heightProperty());
		columnFrameDuration.setCellValueFactory(cellData -> cellData.getValue().frameDurationProperty());

		// Override default column sorting.
		columnId.setComparator(
				(id1, id2) -> Integer.compare(Integer.parseInt(id1.substring(1)), Integer.parseInt(id2.substring(1))));

		ObservableList<TableColumn<Format, ?>> columns = table.getColumns();
		columns.add(columnId);
		columns.add(columnName);
		columns.add(columnWidth);
		columns.add(columnHeight);
		columns.add(columnFrameDuration);
	}

	/**
	 * Creates the videos table.
	 */
	private void createVideosTable() {
		logger.info("Creating videos table...");
		TableView<Video> table = tableVideos;

		resetTable(table);
		table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		// Create columns.
		TableColumn<Video, Number> columnId = new TableColumn<>(resources.getString("colId"));
		TableColumn<Video, String> columnName = new TableColumn<>(resources.getString("colName"));
		TableColumn<Video, Number> columnLane = new TableColumn<>(resources.getString("colLane"));
		TableColumn<Video, String> columnStart = new TableColumn<>(resources.getString("colStart"));
		TableColumn<Video, String> columnDuration = new TableColumn<>(resources.getString("colDuration"));
		TableColumn<Video, String> columnOffset = new TableColumn<>(resources.getString("colOffset"));
		TableColumn<Video, String> columnSrc = new TableColumn<>(resources.getString("colRef"));
		TableColumn<Video, String> columnTcFormat = new TableColumn<>(resources.getString("colTcFormat"));

		// Set cell values.
		columnId.setCellValueFactory(cellData -> cellData.getValue().idProperty());
		columnName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		columnLane.setCellValueFactory(cellData -> cellData.getValue().laneProperty());
		columnStart.setCellValueFactory(cellData -> cellData.getValue().startProperty());
		columnDuration.setCellValueFactory(cellData -> cellData.getValue().durationProperty());
		columnOffset.setCellValueFactory(cellData -> cellData.getValue().offsetProperty());
		columnSrc.setCellValueFactory(cellData -> cellData.getValue().getAsset().srcProperty());
		columnTcFormat.setCellValueFactory(cellData -> cellData.getValue().tcFormatProperty());

		ObservableList<TableColumn<Video, ?>> columns = table.getColumns();
		columns.add(columnId);
		columns.add(columnName);
		columns.add(columnLane);
		columns.add(columnStart);
		columns.add(columnDuration);
		columns.add(columnOffset);
		columns.add(columnSrc);
		columns.add(columnTcFormat);
	}

	/**
	 * Populates the asset table.
	 *
	 * @param list
	 *            The list of @{code Asset} objects with which to populate the
	 *            table,
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
	 * Populates the asset clip table.
	 *
	 * @param list
	 *            The list of @{code AssetClip} objects with which to populate
	 *            the table.
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
	 * Populates the audio table.
	 *
	 * @param list
	 *            The list of @{code Audio} objects with which to populate the
	 *            table.
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
	 * Populates the audio table.
	 *
	 * @param list
	 *            The list of @{code Audio} objects with which to populate the
	 *            table.
	 */
	private void populateClipsTable(ObservableList<Clip> list) {
		logger.info("Populating clips table...");
		TableView<Clip> table = tableClips;

		clearTable(table);

		if (list != null && list.size() > 0) {
			table.setItems(list);
		}
	}

	/**
	 * Populates the effect table.
	 *
	 * @param list
	 *            The list of @{code Effect} objects with which to populate the
	 *            table.
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
	 * Populates the format table.
	 *
	 * @param list
	 *            The list of @{code Format} objects with which to populate the
	 *            table.
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
	 * Populates the video table.
	 *
	 * @param list
	 *            The list of @{code Video} objects with which to populate the
	 *            table.
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
	 * @param <T>
	 *            The type of the table.
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
	 * @param <T>
	 *            The type of the table.
	 * @param table
	 *            The table to reset.
	 */
	private <T> void resetTable(TableView<T> table) {
		// Remove all columns from table
		table.getColumns().clear();
		clearTable(table);
	}
}
