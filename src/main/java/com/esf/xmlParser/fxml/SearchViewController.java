package com.esf.xmlParser.fxml;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Logger;

import org.controlsfx.control.CheckComboBox;

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

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

/**
 * The SearchView controller class. This class is linked to SearchContrller.fxml
 * and provides handler methods for the view.
 * 
 * @author Jeegna Patel
 * @version
 * @since 1.8
 */
public class SearchViewController {

	@FXML
	private ResourceBundle resources;

	// BorderPane sections
	@FXML
	private AnchorPane anchorPaneTop;
	@FXML
	private AnchorPane anchorPaneLeft;
	@FXML
	private AnchorPane anchorPaneRight;
	@FXML
	private AnchorPane anchorPaneBottom;

	// Search bar
	@FXML
	private ComboBox<Element> comboBoxSearch;
	@FXML
	private Button buttonSearch;
	@FXML
	private Button buttonAdvancedSearch;
	@FXML
	private CheckBox checkBoxAsset;
	@FXML
	private CheckBox checkBoxAssetClip;
	@FXML
	private CheckBox checkBoxAudio;
	@FXML
	private CheckBox checkBoxClip;
	@FXML
	private CheckBox checkBoxEffect;
	@FXML
	private CheckBox checkBoxFormat;
	@FXML
	private CheckBox checkBoxVideo;

	@FXML
	private CheckComboBox<String> checkComboBoxAssets;
	@FXML
	private CheckComboBox<String> checkComboBoxAssetClips;
	@FXML
	private CheckComboBox<String> checkComboBoxAudios;
	@FXML
	private CheckComboBox<String> checkComboBoxClips;
	@FXML
	private CheckComboBox<String> checkComboBoxEffects;
	@FXML
	private CheckComboBox<String> checkComboBoxFormats;
	@FXML
	private CheckComboBox<String> checkComboBoxVideos;

	@FXML
	private CheckBox checkBoxSelectAll;

	@FXML
	private Button buttonReset;

	private final Logger logger = Logger.getLogger(this.getClass().getName());

	private TableViewController tableViewController;

	private DatabaseController db;

	private Set<String> assetStatements;
	private Set<String> assetClipStatements;
	private Set<String> audioStatements;
	private Set<String> clipStatements;
	private Set<String> effectStatements;
	private Set<String> formatStatements;
	private Set<String> videoStatements;

	/**
	 * Initializes the search view.
	 */
	public void initializeSearchBar() {
		assetStatements = new HashSet<>();
		assetClipStatements = new HashSet<>();
		audioStatements = new HashSet<>();
		clipStatements = new HashSet<>();
		effectStatements = new HashSet<>();
		formatStatements = new HashSet<>();
		videoStatements = new HashSet<>();

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

		initializeItems();
		addEventHandlers();
		checkDefaults();
	}

	/**
	 * Initialize all check box items.
	 */
	private void initializeItems() {
		String id = resources.getString("id");
		String name = resources.getString("name");
		String lane = resources.getString("lane");
		String start = resources.getString("start");
		String duration = resources.getString("duration");
		String src = resources.getString("src");
		String role = resources.getString("role");
		String srcCh = resources.getString("srcCh");
		String srcId = resources.getString("srcId");
		String hasAudio = resources.getString("hasAudio");
		String hasVideo = resources.getString("hasVideo");
		String tcFormat = resources.getString("tcFormat");
		String uid = resources.getString("uid");
		String width = resources.getString("width");
		String height = resources.getString("height");
		String frameRate = resources.getString("frameRate");
		String offset = resources.getString("offset");
		String audioSources = resources.getString("audioSources");
		String audioChannels = resources.getString("audioChannels");
		String audioRate = resources.getString("audioRate");

		checkComboBoxAssets.getItems().addAll(id, name, duration, hasVideo, hasAudio, uid, src, start, audioSources,
				audioChannels, audioRate);
		checkComboBoxAssetClips.getItems().addAll(id, name, lane, offset, duration, start, role, tcFormat);
		checkComboBoxAudios.getItems().addAll(id, lane, role, offset, duration, start, srcCh, srcId);
		checkComboBoxClips.getItems().addAll(id, name, offset, duration, start, tcFormat);
		checkComboBoxEffects.getItems().addAll(id, name, uid, src);
		checkComboBoxFormats.getItems().addAll(id, name, width, height, frameRate);
		checkComboBoxVideos.getItems().addAll(id, name, lane, offset, duration, start);

		checkBoxAsset.setUserData(checkComboBoxAssets);
		checkBoxAssetClip.setUserData(checkComboBoxAssetClips);
		checkBoxAudio.setUserData(checkComboBoxAudios);
		checkBoxClip.setUserData(checkComboBoxClips);
		checkBoxEffect.setUserData(checkComboBoxEffects);
		checkBoxFormat.setUserData(checkComboBoxFormats);
		checkBoxVideo.setUserData(checkComboBoxVideos);
	}

	/**
	 * Adds the event handlers.
	 */
	private void addEventHandlers() {
		checkComboBoxAssets.getCheckModel().getCheckedItems().addListener(new ListChangeListener<String>() {
			@Override
			public void onChanged(Change<? extends String> c) {
				c.next();

				if (c.wasAdded()) {
					addQueryToList(assetStatements, c.getAddedSubList());
				} else if (c.wasRemoved()) {
					removeQueryFromList(assetStatements, c.getRemoved());
				}
			}
		});

		checkComboBoxAssetClips.getCheckModel().getCheckedItems().addListener(new ListChangeListener<String>() {
			@Override
			public void onChanged(Change<? extends String> c) {
				c.next();

				if (c.wasAdded()) {
					addQueryToList(assetClipStatements, c.getAddedSubList());
				} else if (c.wasRemoved()) {
					removeQueryFromList(assetClipStatements, c.getRemoved());
				}
			}
		});

		checkComboBoxAudios.getCheckModel().getCheckedItems().addListener(new ListChangeListener<String>() {
			@Override
			public void onChanged(Change<? extends String> c) {
				c.next();

				if (c.wasAdded()) {
					addQueryToList(audioStatements, c.getAddedSubList());
				} else if (c.wasRemoved()) {
					removeQueryFromList(audioStatements, c.getRemoved());
				}
			}
		});

		checkComboBoxClips.getCheckModel().getCheckedItems().addListener(new ListChangeListener<String>() {
			@Override
			public void onChanged(Change<? extends String> c) {
				c.next();

				if (c.wasAdded()) {
					addQueryToList(clipStatements, c.getAddedSubList());
				} else if (c.wasRemoved()) {
					removeQueryFromList(clipStatements, c.getRemoved());
				}
			}
		});

		checkComboBoxEffects.getCheckModel().getCheckedItems().addListener(new ListChangeListener<String>() {
			@Override
			public void onChanged(Change<? extends String> c) {
				c.next();

				if (c.wasAdded()) {
					addQueryToList(effectStatements, c.getAddedSubList());
				} else if (c.wasRemoved()) {
					removeQueryFromList(effectStatements, c.getRemoved());
				}
			}
		});

		checkComboBoxFormats.getCheckModel().getCheckedItems().addListener(new ListChangeListener<String>() {
			@Override
			public void onChanged(Change<? extends String> c) {
				c.next();

				if (c.wasAdded()) {
					addQueryToList(formatStatements, c.getAddedSubList());
				} else if (c.wasRemoved()) {
					removeQueryFromList(formatStatements, c.getRemoved());
				}
			}
		});

		checkComboBoxVideos.getCheckModel().getCheckedItems().addListener(new ListChangeListener<String>() {
			@Override
			public void onChanged(Change<? extends String> c) {
				c.next();

				if (c.wasAdded()) {
					addQueryToList(videoStatements, c.getAddedSubList());
				} else if (c.wasRemoved()) {
					removeQueryFromList(videoStatements, c.getRemoved());
				}
			}
		});
	}

	/**
	 * Converts the given column name to the proper database format, then adds
	 * it to the specified list.
	 *
	 * @param list
	 *            The list to which to add the column names.
	 * @param additions
	 *            The column names to add.
	 */
	private void addQueryToList(Set<String> list, List<? extends String> additions) {
		for (String addition : additions) {
			String column = convertString(addition);
			logger.info("Adding column to array " + column);

			list.add(column);
		}
	}

	/**
	 * Removes the given column name from the specified list.
	 *
	 * @param list
	 *            The list from which to remove the column names.
	 * @param removals
	 *            The column names to remove.
	 */
	private void removeQueryFromList(Set<String> list, List<? extends String> removals) {
		for (String removal : removals) {
			String column = convertString(removal);
			logger.info("Removing column from array " + column);

			list.remove(column);
		}
	}

	/**
	 * Handles the advanced search button click.
	 *
	 * @param event
	 *            The event.
	 */
	@FXML
	private void buttonAdvancedSearchClick(ActionEvent event) {
		boolean b = !anchorPaneBottom.isVisible();
		anchorPaneLeft.setVisible(b);
		anchorPaneRight.setVisible(b);
		anchorPaneBottom.setVisible(b);
	}

	/**
	 * Handles the reset button click.
	 *
	 * @param event
	 *            The event.
	 */
	@FXML
	private void buttonResetClick(ActionEvent event) {
		checkComboBoxAssets.getCheckModel().clearChecks();
		checkComboBoxAssetClips.getCheckModel().clearChecks();
		checkComboBoxAudios.getCheckModel().clearChecks();
		checkComboBoxClips.getCheckModel().clearChecks();
		checkComboBoxEffects.getCheckModel().clearChecks();
		checkComboBoxFormats.getCheckModel().clearChecks();
		checkComboBoxVideos.getCheckModel().clearChecks();

		checkDefaults();
	}

	/**
	 * Handles the select all check box check.
	 *
	 * @param event
	 *            The event.
	 */
	@FXML
	private void checkBoxSelectAllCheck(ActionEvent event) {
		boolean b = !checkBoxSelectAll.isSelected();
		checkBoxAsset.setSelected(b);
		checkBoxAssetClip.setSelected(b);
		checkBoxAudio.setSelected(b);
		checkBoxClip.setSelected(b);
		checkBoxEffect.setSelected(b);
		checkBoxFormat.setSelected(b);
		checkBoxVideo.setSelected(b);
		checkBoxAsset.fire();
		checkBoxAssetClip.fire();
		checkBoxAudio.fire();
		checkBoxClip.fire();
		checkBoxEffect.fire();
		checkBoxFormat.fire();
		checkBoxVideo.fire();
	}

	/**
	 * Check default values for check combo box items.
	 */
	private void checkDefaults() {
		checkComboBoxAssets.getCheckModel().check(1);
		checkComboBoxAssetClips.getCheckModel().check(1);
		checkComboBoxClips.getCheckModel().check(1);
		checkComboBoxEffects.getCheckModel().check(1);
		checkComboBoxFormats.getCheckModel().check(1);
		checkComboBoxVideos.getCheckModel().check(1);
	}

	/**
	 * Handles search button click.
	 * 
	 * @param event
	 *            The event.
	 */
	@FXML
	private void buttonSearchClick(ActionEvent event) {
		if (db != null && comboBoxSearch.getValue() != null) {
			String query = "%" + comboBoxSearch.getValue().getName() + "%";

			try {
				List<Asset> assets = db.getAssetsFromQueryInfo(assetStatements, query);
				List<AssetClip> assetClips = db.getAssetClipsFromQueryInfo(assetClipStatements, query);
				List<Audio> audios = db.getAudiosFromQueryInfo(audioStatements, query);
				List<Clip> clips = db.getClipsFromQueryInfo(clipStatements, query);
				List<Effect> effects = db.getEffectsFromQueryInfo(effectStatements, query);
				List<Format> formats = db.getFormatsFromQueryInfo(formatStatements, query);
				List<Video> videos = db.getVideosFromQueryInfo(videoStatements, query);

				ObservableList<Element> items = comboBoxSearch.getItems();
				items.addAll(assets);
				items.addAll(assetClips);
				items.addAll(audios);
				items.addAll(clips);
				items.addAll(effects);
				items.addAll(formats);
				items.addAll(videos);
			} catch (ClassNotFoundException | SQLException e) {
				new Alert(AlertType.ERROR, resources.getString("errorSearch"), ButtonType.OK).showAndWait();
				e.printStackTrace();
			}
		}
	}

	/**
	 * Handles the entity check box check.
	 *
	 * @param event
	 *            The event.
	 */
	@FXML
	private void checkBoxClick(ActionEvent event) {
		CheckBox checkBox = (CheckBox) event.getSource();

		@SuppressWarnings("unchecked")
		CheckComboBox<String> checkComboBox = (CheckComboBox<String>) checkBox.getUserData();

		if (checkBox.isSelected()) {
			checkComboBox.getCheckModel().checkAll();
		} else {
			checkComboBox.getCheckModel().clearChecks();
		}
	}

	/**
	 * Handles the entity property combo box item check.
	 *
	 * @param event
	 *            The event.
	 */
	@FXML
	private void comboBoxItemSelected(ActionEvent event) {
		Element element = comboBoxSearch.getValue();
		if (element != null) {
			tableViewController.selectItem(element);
		}
	}

	/**
	 * Sets the table view controller.
	 *
	 * @param tableViewController
	 *            The new table view controller.
	 */
	public void setTableViewController(TableViewController tableViewController) {
		this.tableViewController = tableViewController;
	}

	/**
	 * Sets the database controller.
	 *
	 * @param db
	 *            The new database controller.
	 */
	public void setDatabaseController(DatabaseController db) {
		this.db = db;
	}

	/**
	 * Convert string.
	 *
	 * @param s
	 *            The displayable column name to convert into its database
	 *            equivalent.
	 * @return The database equivalent of the given column name.
	 */
	private String convertString(String s) {
		String converted = null;

		if (s.equals(resources.getString("id"))) {
			converted = DatabaseController.ID;
		} else if (s.equals(resources.getString("name"))) {
			converted = DatabaseController.NAME;
		} else if (s.equals(resources.getString("lane"))) {
			converted = DatabaseController.LANE;
		} else if (s.equals(resources.getString("start"))) {
			converted = DatabaseController.START;
		} else if (s.equals(resources.getString("duration"))) {
			converted = DatabaseController.DURATION;
		} else if (s.equals(resources.getString("src"))) {
			converted = DatabaseController.SOURCE;
		} else if (s.equals(resources.getString("role"))) {
			converted = DatabaseController.ROLE;
		} else if (s.equals(resources.getString("srcCh"))) {
			converted = DatabaseController.SOURCE_CHANNEL;
		} else if (s.equals(resources.getString("srcId"))) {
			converted = DatabaseController.SOURCE_ID;
		} else if (s.equals(resources.getString("hasAudio"))) {
			converted = DatabaseController.HAS_AUDIO;
		} else if (s.equals(resources.getString("hasVideo"))) {
			converted = DatabaseController.HAS_VIDEO;
		} else if (s.equals(resources.getString("formatName"))) {
			converted = DatabaseController.FORMAT_NAME;
		} else if (s.equals(resources.getString("tcFormat"))) {
			converted = DatabaseController.TC_FORMAT;
		} else if (s.equals(resources.getString("uid"))) {
			converted = DatabaseController.UID;
		} else if (s.equals(resources.getString("width"))) {
			converted = DatabaseController.WIDTH;
		} else if (s.equals(resources.getString("height"))) {
			converted = DatabaseController.HEIGHT;
		} else if (s.equals(resources.getString("frameRate"))) {
			converted = DatabaseController.FRAME_RATE;
		} else if (s.equals(resources.getString("offset"))) {
			converted = DatabaseController.OFFSET;
		} else if (s.equals(resources.getString("audioSources"))) {
			converted = DatabaseController.AUDIO_SOURCES;
		} else if (s.equals(resources.getString("audioChannels"))) {
			converted = DatabaseController.AUDIO_CHANNELS;
		} else if (s.equals(resources.getString("audioRate"))) {
			converted = DatabaseController.AUDIO_RATE;
		}

		return converted;
	}
}
