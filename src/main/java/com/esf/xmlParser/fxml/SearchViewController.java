package com.esf.xmlParser.fxml;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Logger;

import org.controlsfx.control.CheckComboBox;

import com.esf.xmlParser.converter.ElementConverter;
import com.esf.xmlParser.database.DatabaseController;
import com.esf.xmlParser.entities.Element;

import javafx.collections.ListChangeListener;
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
import javafx.util.Callback;

public class SearchViewController {

	@FXML
	private ResourceBundle resources;

	// Search bar
	@FXML
	private ComboBox<Element> comboBoxSearch;
	@FXML
	private Button buttonSearch;

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

	private void addEventHandlers() {
		checkComboBoxAssets.getCheckModel().getCheckedItems().addListener(new ListChangeListener<String>() {
			@Override
			public void onChanged(Change<? extends String> c) {
				c.next();

				if (c.wasAdded()) {
					addQueryToList(assetStatements, c.getAddedSubList(), DatabaseController.ASSETS);
				} else if (c.wasRemoved()) {
					removeQueryFromList(assetStatements, c.getRemoved(), DatabaseController.ASSETS);
				}
			}
		});

		checkComboBoxAssetClips.getCheckModel().getCheckedItems().addListener(new ListChangeListener<String>() {
			@Override
			public void onChanged(Change<? extends String> c) {
				c.next();

				if (c.wasAdded()) {
					addQueryToList(assetClipStatements, c.getAddedSubList(), DatabaseController.ASSET_CLIPS);
				} else if (c.wasRemoved()) {
					removeQueryFromList(assetClipStatements, c.getRemoved(), DatabaseController.ASSET_CLIPS);
				}
			}
		});

		checkComboBoxAudios.getCheckModel().getCheckedItems().addListener(new ListChangeListener<String>() {
			@Override
			public void onChanged(Change<? extends String> c) {
				c.next();

				if (c.wasAdded()) {
					addQueryToList(audioStatements, c.getAddedSubList(), DatabaseController.AUDIOS);
				} else if (c.wasRemoved()) {
					removeQueryFromList(audioStatements, c.getRemoved(), DatabaseController.AUDIOS);
				}
			}
		});

		checkComboBoxClips.getCheckModel().getCheckedItems().addListener(new ListChangeListener<String>() {
			@Override
			public void onChanged(Change<? extends String> c) {
				c.next();

				if (c.wasAdded()) {
					addQueryToList(clipStatements, c.getAddedSubList(), DatabaseController.CLIPS);
				} else if (c.wasRemoved()) {
					removeQueryFromList(clipStatements, c.getRemoved(), DatabaseController.CLIPS);
				}
			}
		});

		checkComboBoxEffects.getCheckModel().getCheckedItems().addListener(new ListChangeListener<String>() {
			@Override
			public void onChanged(Change<? extends String> c) {
				c.next();

				if (c.wasAdded()) {
					addQueryToList(effectStatements, c.getAddedSubList(), DatabaseController.EFFECTS);
				} else if (c.wasRemoved()) {
					removeQueryFromList(effectStatements, c.getRemoved(), DatabaseController.EFFECTS);
				}
			}
		});

		checkComboBoxFormats.getCheckModel().getCheckedItems().addListener(new ListChangeListener<String>() {
			@Override
			public void onChanged(Change<? extends String> c) {
				c.next();

				if (c.wasAdded()) {
					addQueryToList(formatStatements, c.getAddedSubList(), DatabaseController.FORMATS);
				} else if (c.wasRemoved()) {
					removeQueryFromList(formatStatements, c.getRemoved(), DatabaseController.FORMATS);
				}
			}
		});

		checkComboBoxVideos.getCheckModel().getCheckedItems().addListener(new ListChangeListener<String>() {
			@Override
			public void onChanged(Change<? extends String> c) {
				c.next();

				if (c.wasAdded()) {
					addQueryToList(videoStatements, c.getAddedSubList(), DatabaseController.VIDEOS);
				} else if (c.wasRemoved()) {
					removeQueryFromList(videoStatements, c.getRemoved(), DatabaseController.VIDEOS);
				}
			}
		});
	}

	private void addQueryToList(Set<String> list, List<? extends String> additions, String tableName) {
		for (String addition : additions) {
			String key = convertString(addition);
			String statement = "SELECT * FROM " + tableName + " WHERE " + key + " LIKE ?;";
			logger.info("Adding query " + statement);

			list.add(statement);
		}
		System.out.println(list);
	}

	private void removeQueryFromList(Set<String> list, List<? extends String> removals, String tableName) {
		for (String removal : removals) {
			String key = convertString(removal);
			String statement = "SELECT * FROM " + tableName + " WHERE " + key + " LIKE ?;";
			logger.info("Removing query " + statement);

			list.remove(statement);
		}
		System.out.println(list);
	}

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

	@FXML
	private void checkBoxSelectAllCheck(ActionEvent event) {
		boolean isSelected = !checkBoxSelectAll.isSelected();
		checkBoxAsset.setSelected(isSelected);
		checkBoxAssetClip.setSelected(isSelected);
		checkBoxAudio.setSelected(isSelected);
		checkBoxClip.setSelected(isSelected);
		checkBoxEffect.setSelected(isSelected);
		checkBoxFormat.setSelected(isSelected);
		checkBoxVideo.setSelected(isSelected);
		checkBoxAsset.fire();
		checkBoxAssetClip.fire();
		checkBoxAudio.fire();
		checkBoxClip.fire();
		checkBoxEffect.fire();
		checkBoxFormat.fire();
		checkBoxVideo.fire();
	}

	private void checkDefaults() {
		checkComboBoxAssets.getCheckModel().check(1);
		checkComboBoxAssetClips.getCheckModel().check(1);
		checkComboBoxClips.getCheckModel().check(1);
		checkComboBoxEffects.getCheckModel().check(1);
		checkComboBoxFormats.getCheckModel().check(1);
		checkComboBoxVideos.getCheckModel().check(1);
	}

	@FXML
	private void buttonSearchClick() {
		if (db != null && comboBoxSearch.getValue() != null) {
			String query = "%" + comboBoxSearch.getValue().getName() + "%";
			List<String> queries = new ArrayList<>();
			queries.addAll(assetStatements);
			queries.addAll(assetClipStatements);
			queries.addAll(audioStatements);
			queries.addAll(clipStatements);
			queries.addAll(effectStatements);
			queries.addAll(formatStatements);
			queries.addAll(videoStatements);
			try {
				db.executeQueries(queries, query);
			} catch (ClassNotFoundException | SQLException e) {
				new Alert(AlertType.ERROR, resources.getString("errorSearch"), ButtonType.OK).showAndWait();
				e.printStackTrace();
			}
		}
	}

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

	@FXML
	private void comboBoxItemSelected(ActionEvent event) {
		Element element = comboBoxSearch.getValue();
		if (element != null) {
			tableViewController.selectItem(element);
		}
	}

	public void setTableViewController(TableViewController tableViewController) {
		this.tableViewController = tableViewController;
	}

	public void setDatabaseController(DatabaseController db) {
		this.db = db;
	}

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
