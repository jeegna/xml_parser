package com.esf.xmlParser.fxml;

import java.util.ResourceBundle;
import java.util.logging.Logger;

import org.controlsfx.control.CheckComboBox;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ToggleButton;

public class AdvancedSearchController {

	@FXML
	private ResourceBundle resources;

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

	public void initializeItems() {
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
		
		checkComboBoxAssets.getItems().addAll(id, duration, hasVideo, hasAudio, name, uid, src, start, formatName, width, height, frameRate, audioSources, audioChannels, audioRate);
		checkComboBoxAssetClips.getItems().addAll(id, name, lane, offset, duration, start, role, formatName, width, height, frameRate, tcFormat);
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
		checkComboBoxAudios.getCheckModel().check(1);
		checkComboBoxClips.getCheckModel().check(1);
		checkComboBoxEffects.getCheckModel().check(1);
		checkComboBoxFormats.getCheckModel().check(1);
		checkComboBoxVideos.getCheckModel().check(1);
	}
}
