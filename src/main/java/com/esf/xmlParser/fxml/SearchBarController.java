package com.esf.xmlParser.fxml;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class SearchBarController {

	private final Logger logger = Logger.getLogger(this.getClass().getName());

	@FXML
	private HBox hBox;
	@FXML
	private TextField textSearch;
	@FXML
	private Button btnSearch;
	
	@FXML
	private void onButtonSearch() {
		String query = textSearch.getText();
	}
}
