package com.esf.xmlParser.fxml;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.esf.xmlParser.entities.Asset;
import com.esf.xmlParser.util.Parser;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
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

	@FXML
	private TableView<String> tableResults;

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
		filePath = file.getAbsolutePath();

		logger.info("Chosen file: " + filePath);

		try {
			createParser(filePath);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		logger.info("Bye!");
		System.exit(0);
	}

	@FXML
	private void buttonGetVideosClick() {
		if (parser != null) {
			List<Asset> assets = parser.getAssets();
		}
	}

	@FXML
	private void buttonGetAudiosClick() {
		if (parser != null) {
			List<Asset> assets = parser.getAssets();
		}
	}
}
