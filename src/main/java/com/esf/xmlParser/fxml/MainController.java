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
import com.esf.xmlParser.util.Parser;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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

	// Menubar
	@FXML
	private MenuBar menubar;
	@FXML
	private MenuItem menuItemClose;
	@FXML
	private MenuItem menuItemChooseFile;

	// Sidebar buttons
	@FXML
	private Button buttonTest;

	// Asset table
	@FXML
	private TableView<Asset> tableAssets;
	@FXML
	private TableColumn<Asset, String> columnDuration;
	@FXML
	private TableColumn<Asset, Boolean> columnHasVideo;
	@FXML
	private TableColumn<Asset, Boolean> columnHasAudio;
	@FXML
	private TableColumn<Asset, String> columnName;
	@FXML
	private TableColumn<Asset, String> columnSrc;
	@FXML
	private TableColumn<Asset, String> columnStart;
	@FXML
	private TableColumn<Asset, String> columnFormat;
	@FXML
	private TableColumn<Asset, String> columnUID;

	private final Logger logger = Logger.getLogger(this.getClass().getName());
	private Parser parser;
	private String filePath;

	@FXML
	private void initialize() {
		logger.info("Start Application");

		// if (filePath == null || filePath.trim().isEmpty()) {
		// File file = chooseFile();
		// filePath = file.getAbsolutePath();
		//
		// logger.info("Chosen file path: " + filePath);
		//
		// try {
		// createParser(filePath);
		// } catch (ParserConfigurationException | SAXException | IOException e)
		// {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
	}

	/**
	 * @param filePath
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	private void createParser(String filePath) throws ParserConfigurationException, SAXException, IOException {
		parser = new Parser(filePath);
	}

	@FXML
	private void menuItemChooseFile() {
		File file = chooseFile();
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

	/**
	 *
	 */
	private File chooseFile() {
		FileChooser fileChooser = new FileChooser();
		ExtensionFilter filter = new ExtensionFilter("fcpxml files", "*.fcpxml");
		// fileChooser.setTitle(resources.getString("openFile"));
		fileChooser.getExtensionFilters().add(filter);
		fileChooser.setSelectedExtensionFilter(filter);

		return fileChooser.showOpenDialog(menubar.getScene().getWindow());
	}

	@FXML
	private void menuItemClose() {
		close();
	}

	@FXML
	private void buttonGetVideosClick() {
		if (parser != null) {
			List<Asset> assets = parser.getAssets();
			ObservableList<Asset> list = FXCollections.observableList(assets);
			populateAssetTable(list);
		}
	}

	@FXML
	private void buttonGetAudiosClick() {
		if (parser != null) {
			List<Asset> assets = parser.getAssets();
			ObservableList<Asset> list = FXCollections.observableList(assets);
			populateAssetTable(list);
		}
	}

	private void populateAssetTable(ObservableList<Asset> list) {
		// Clear all previously displayed email messages
		tableAssets.getItems().clear();

		// Set row height
		tableAssets.setFixedCellSize(30);

		// Set cell factories
		columnDuration.setCellValueFactory(cellData -> cellData.getValue().durationProperty());
		columnHasVideo.setCellValueFactory(cellData -> cellData.getValue().hasVideoProperty());
		columnHasAudio.setCellValueFactory(cellData -> cellData.getValue().hasAudioProperty());
		columnName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		columnSrc.setCellValueFactory(cellData -> cellData.getValue().srcProperty());
		columnStart.setCellValueFactory(cellData -> cellData.getValue().startProperty());
		columnFormat.setCellValueFactory(cellData -> cellData.getValue().formatProperty());
		columnUID.setCellValueFactory(cellData -> cellData.getValue().uidProperty());

		if (list != null && list.size() > 0) {
			tableAssets.setItems(list);
		}
	}

	private void close() {
		logger.info("Bye!");
		System.exit(0);
	}
}
