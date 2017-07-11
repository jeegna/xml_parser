package com.esf.xmlParser.fxml;

import org.controlsfx.control.CheckComboBox;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ToggleButton;

public class AdvancedSearchController {

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

	public void initializeItems() {
		checkComboBoxAssets.getItems().addAll();
		checkComboBoxAssetClips.getItems().addAll();
		checkComboBoxAudios.getItems().addAll();
		checkComboBoxClips.getItems().addAll();
		checkComboBoxEffects.getItems().addAll();
		checkComboBoxFormats.getItems().addAll();
		checkComboBoxVideos.getItems().addAll();
	}

	@FXML
	private void buttonResetClick(ActionEvent event) {

	}
}
