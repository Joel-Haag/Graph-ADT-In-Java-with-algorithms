package ui.tabs;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import nodes.Civilian;
import nodes.CommunityPolice;
import nodes.SecurityCompany;
import ui.helper.HelperFunctions;

public class IncidentHandleTab extends Tab {
	private String pathToReadCivilian = "./data/Civilians.binary";
	private String pathToReadSecurityCompany = "./data/SecurityCompany.binary";
	private String pathToReadCommunityPolice = "./data/CommunityPolice.binary";

	private Civilian civilian = null;
	private SecurityCompany securityCompany = null;
	private CommunityPolice communityPolice = null;

	public IncidentHandleTab() {
		setText("Handle Incidents");

		VBox IncidentHandleContentVbox = new VBox();
		Label IncidentHandleHeadingLabel = new Label("Report Incident");
		Label InfoLabel = new Label(
				"1. Select A civilian involved in incident \n2. Select securty company and/or community police involved");
		IncidentHandleHeadingLabel.setFont(Font.font("System", FontWeight.BOLD, 16));
		InfoLabel.setFont(Font.font("System", FontWeight.BOLD, 16));

		IncidentHandleContentVbox.setAlignment(Pos.TOP_CENTER);
		IncidentHandleContentVbox.setSpacing(30);
		// FIRST GETTING ALL PARTIES INVOLVED BY USING COMBO BOXES WHICH USER CAN SELECT
		// FROM
		// Horizontal box to store all the select combo boxes
		HBox comboBoxes = new HBox();

		// getting list of objects from civilian file
		List<Object> civilianObjects = HelperFunctions.readClassesFromFile(pathToReadCivilian);
		List<Civilian> civilians = new ArrayList<>();

		for (Object civObj : civilianObjects) {
			if (civObj instanceof Civilian) {
				civilians.add((Civilian) civObj);
			}
		}

		ComboBox<Civilian> civilianComboBox = new ComboBox<>();
		civilianComboBox.getItems().addAll(civilians);

		civilianComboBox.setCellFactory(param -> new ListCell<Civilian>() {
			@Override
			protected void updateItem(Civilian civ, boolean empty) {
				super.updateItem(civ, empty);

				if (empty || civ == null) {
					setText(null);
				} else {
					setText(civ.getName());
				}
			}
		});

		// Set text for civilian combo box
		civilianComboBox.setPromptText("Select Cilivian");

		// Set a listener to print the selected item whenever the value changes
		civilianComboBox.valueProperty().addListener((observable, oldCiv, newCiv) -> {
			if (newCiv != null) {
				civilian = newCiv;
				System.out.println("Selected item: " + newCiv.getId());
			}
		});

		// Getting the civilian data from the civilian text file and adding each one to
		// getting list of objects from civilian file
		List<Object> securityCompObjects = HelperFunctions.readClassesFromFile(pathToReadSecurityCompany);
		List<SecurityCompany> securityCompanies = new ArrayList<>();

		for (Object secCompObj : securityCompObjects) {
			if (secCompObj instanceof SecurityCompany) {
				securityCompanies.add((SecurityCompany) secCompObj);
			}
		}

		ComboBox<SecurityCompany> securityCompComboBox = new ComboBox<>();
		securityCompComboBox.getItems().addAll(securityCompanies);

		securityCompComboBox.setCellFactory(param -> new ListCell<SecurityCompany>() {
			@Override
			protected void updateItem(SecurityCompany secComp, boolean empty) {
				super.updateItem(secComp, empty);

				if (empty || secComp == null) {
					setText(null);
				} else {
					setText(secComp.getName());
				}
			}
		});

		// Set text for security company combo box
		securityCompComboBox.setPromptText("Select Security Company");

		// Set a listener to print the selected item whenever the value changes
		securityCompComboBox.valueProperty().addListener((observable, oldSec, newSec) -> {
			if (newSec != null) {
				securityCompany = newSec;
				System.out.println("Selected item: " + newSec.getName());
			}
		});

		// getting list of objects from community police file
		List<Object> comPopoObjects = HelperFunctions.readClassesFromFile(pathToReadCommunityPolice);
		List<CommunityPolice> communityPolicies = new ArrayList<>();

		for (Object comPopoObj : comPopoObjects) {
			if (comPopoObj instanceof CommunityPolice) {
				communityPolicies.add((CommunityPolice) comPopoObj);
			}
		}

		ComboBox<CommunityPolice> comPopoComboBox = new ComboBox<>();
		comPopoComboBox.getItems().addAll(communityPolicies);

		comPopoComboBox.setCellFactory(param -> new ListCell<CommunityPolice>() {
			@Override
			protected void updateItem(CommunityPolice comPopo, boolean empty) {
				super.updateItem(comPopo, empty);

				if (empty || comPopo == null) {
					setText(null);
				} else {
					setText(comPopo.getLocation());
				}
			}
		});

		// Set text for security company combo box
		comPopoComboBox.setPromptText("Select Community Police");

		// Set a listener to print the selected item whenever the value changes
		comPopoComboBox.valueProperty().addListener((observable, oldCom, newCom) -> {
			if (newCom != null) {
				communityPolice = newCom;
				System.out.println("Selected item: " + newCom.getLocation());
			}
		});

		comboBoxes.getChildren().addAll(civilianComboBox, securityCompComboBox, comPopoComboBox);
		comboBoxes.setSpacing(10);

		IncidentHandleContentVbox.getChildren().addAll(IncidentHandleHeadingLabel, InfoLabel, comboBoxes);
		setContent(IncidentHandleContentVbox);
	}
}
