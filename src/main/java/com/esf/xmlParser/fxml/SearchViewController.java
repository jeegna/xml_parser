package com.esf.xmlParser.fxml;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import org.controlsfx.control.CheckComboBox;

import com.esf.xmlParser.converter.ElementConverter;
import com.esf.xmlParser.database.DatabaseController;
import com.esf.xmlParser.entities.Asset;
import com.esf.xmlParser.entities.AssetClip;
import com.esf.xmlParser.entities.Clip;
import com.esf.xmlParser.entities.Effect;
import com.esf.xmlParser.entities.Element;
import com.esf.xmlParser.entities.Format;
import com.esf.xmlParser.entities.Video;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Alert.AlertType;
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
	private Button buttonReset;
	@FXML
	private Button buttonApply;
	@FXML
	private Button buttonCancel;
	@FXML
	private ToggleButton buttonSelect;

	private final Logger logger = Logger.getLogger(this.getClass().getName());

	private TableViewController tableViewController;
	private DatabaseController db;

	public void initializeSearchBar() {
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
		String formatName = resources.getString("formatName");
		String tcFormat = resources.getString("tcFormat");
		String uid = resources.getString("uid");
		String width = resources.getString("width");
		String height = resources.getString("height");
		String frameRate = resources.getString("frameRate");
		String offset = resources.getString("offset");
		String audioSources = resources.getString("audioSources");
		String audioChannels = resources.getString("audioChannels");
		String audioRate = resources.getString("audioRate");

		checkComboBoxAssets.getItems().addAll(id, duration, hasVideo, hasAudio, name, uid, src, start, formatName,
				width, height, frameRate, audioSources, audioChannels, audioRate);
		checkComboBoxAssetClips.getItems().addAll(id, name, lane, offset, duration, start, role, formatName, width,
				height, frameRate, tcFormat);
		checkComboBoxAudios.getItems().addAll(id, lane, role, offset, duration, start, srcCh, srcId);
		checkComboBoxClips.getItems().addAll(id, name, offset, duration, start, tcFormat);
		checkComboBoxEffects.getItems().addAll(id, name, uid, src);
		checkComboBoxFormats.getItems().addAll(id, name, width, height, frameRate);
		checkComboBoxVideos.getItems().addAll(id, name, lane, offset, duration, start);
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

		checkComboBoxAssets.getCheckModel().check(1);
		checkComboBoxAssetClips.getCheckModel().check(1);
		checkComboBoxClips.getCheckModel().check(1);
		checkComboBoxEffects.getCheckModel().check(1);
		checkComboBoxFormats.getCheckModel().check(1);
		checkComboBoxVideos.getCheckModel().check(1);
	}

	@FXML
	private void buttonSearchClick() {
		if (db != null) {
			String query = comboBoxSearch.getValue().getName();

			List<String> checkedAssets = checkComboBoxAssets.getCheckModel().getCheckedItems();

			try {
				List<Asset> assets = db.getAssetsByName(query);
				List<AssetClip> assetClips = db.getAssetClipsByName(query);
				List<Clip> clips = db.getClipsByName(query);
				List<Effect> effects = db.getEffectsByName(query);
				List<Format> formats = db.getFormatsByName(query);
				List<Video> videos = db.getVideosByName(query);

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

	private void getResults(List<String> items) {
		for (String item : items) {
			switch (item) {
			case "id":
				break;
			}
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
}
