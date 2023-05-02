package ui.tabs;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import nodes.Civilian;
import nodes.CommunityPolice;
import nodes.Incident;
import nodes.SecurityCompany;
import ui.helper.HelperFunctions;

public class IncidentHandleTab extends Tab {
	private String pathToReadCivilian = "./data/Civilians.binary";
	private String pathToReadSecurityCompany = "./data/SecurityCompany.binary";
	private String pathToReadCommunityPolice = "./data/CommunityPolice.binary";
	private String pathToSaveIncident = "./data/Incident.binary";

	private Civilian civilian = null;
	private SecurityCompany securityCompany = null;
	private CommunityPolice communityPolice = null;
	private Boolean addedCorrectely = false;
	
	private ComboBox<Civilian> civilianComboBox;
	private ComboBox<SecurityCompany> securityCompComboBox;
	private ComboBox<CommunityPolice> comPopoComboBox;

	public IncidentHandleTab() {
		
		setClosable(false);
		setText("Handle Incidents");

		VBox IncidentHandleContentVbox = new VBox();
		Label IncidentHandleHeadingLabel = new Label("Report Incident");
		Label InfoLabel = new Label(
				"1. Select A civilian involved in incident \n2. Select securty company that responded to the civilian");
		IncidentHandleHeadingLabel.setFont(Font.font("System", FontWeight.BOLD, 16));
		InfoLabel.setFont(Font.font("System", FontWeight.BOLD, 16));

		IncidentHandleContentVbox.setAlignment(Pos.TOP_CENTER);
		IncidentHandleContentVbox.setSpacing(30);
		Label savedIncidentCorrectlyLabel = new Label("");
		// FIRST GETTING ALL PARTIES INVOLVED BY USING COMBO BOXES WHICH USER CAN SELECT
		// FROM
		// Horizontal box to store all the select combo boxes
		HBox comboBoxes = new HBox();

		// getting list of objects from civilian file
		List<Object> civilianObjects = HelperFunctions.readClassesFromFile(pathToReadCivilian, Civilian.class);
		List<Civilian> civilians = new ArrayList<>();

		for (Object civObj : civilianObjects) {
			if (civObj instanceof Civilian) {
				civilians.add((Civilian) civObj);
			}
		}

		civilianComboBox = new ComboBox<>();
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
			}
		});

		// Getting the civilian data from the civilian text file and adding each one to
		// getting list of objects from civilian file
		List<Object> securityCompObjects = HelperFunctions.readClassesFromFile(pathToReadSecurityCompany, SecurityCompany.class);
		List<SecurityCompany> securityCompanies = new ArrayList<>();

		for (Object secCompObj : securityCompObjects) {
			if (secCompObj instanceof SecurityCompany) {
				securityCompanies.add((SecurityCompany) secCompObj);
			}
		}

		securityCompComboBox = new ComboBox<>();
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
			}
		});

		// getting list of objects from community police file
//		List<Object> comPopoObjects = HelperFunctions.readClassesFromFile(pathToReadCommunityPolice, CommunityPolice.class);
//		List<CommunityPolice> communityPolicies = new ArrayList<>();
//
//		for (Object comPopoObj : comPopoObjects) {
//			if (comPopoObj instanceof CommunityPolice) {
//				communityPolicies.add((CommunityPolice) comPopoObj);
//			}
//		}
//
//		comPopoComboBox = new ComboBox<>();
//		comPopoComboBox.getItems().addAll(communityPolicies);
//
//		comPopoComboBox.setCellFactory(param -> new ListCell<CommunityPolice>() {
//			@Override
//			protected void updateItem(CommunityPolice comPopo, boolean empty) {
//				super.updateItem(comPopo, empty);
//
//				if (empty || comPopo == null) {
//					setText(null);
//				} else {
//					setText(comPopo.getLocation());
//				}
//			}
//		});
//
//		// Set text for security company combo box
//		comPopoComboBox.setPromptText("Select Community Police");
//
//		// Set a listener to print the selected item whenever the value changes
//		comPopoComboBox.valueProperty().addListener((observable, oldCom, newCom) -> {
//			if (newCom != null) {
//				communityPolice = newCom;
//			}
//		});

		comboBoxes.getChildren().addAll(civilianComboBox, securityCompComboBox);
		comboBoxes.setSpacing(10);

		// Labels and text areas to fill in details about the incident
		Label severityLabel = new Label("Severity: (1-10)");
		severityLabel.setFont(Font.font("System", FontWeight.BOLD, 15));
		TextField severityTextField = new TextField();
		HBox severityHBox = new HBox(severityLabel, severityTextField);
		severityHBox.setSpacing(10);

		Label descriptionLabel = new Label("Description:");
		descriptionLabel.setFont(Font.font("System", FontWeight.BOLD, 15));
		TextArea descriptionTextField = new TextArea();
		descriptionTextField.setPrefSize(300, 100);
		HBox descriptionHBox = new HBox(descriptionLabel, descriptionTextField);
		descriptionHBox.setSpacing(35);

		Button submitIncidentButton = new Button("Submit");

		submitIncidentButton.setOnAction(e -> {
			Incident newIncident = new Incident();
			if (civilian != null)
				newIncident.setCivilian(civilian);
			else {
				Alert alert = new Alert(Alert.AlertType.WARNING, "You need to choose a civilian for an incident");
				alert.setTitle("Civilian required");
				alert.setHeaderText("No Civilian");
				alert.showAndWait();
			}
			if (communityPolice == null && securityCompany == null) {
				Alert alert = new Alert(Alert.AlertType.WARNING,
						"You need either a security guard to submit an incident");
				alert.setTitle("Security or community police required");
				alert.setHeaderText("No security or community police");
				alert.showAndWait();
			}
			if (securityCompany != null)
				communityPolice = null;
				newIncident.setSecurityCompany(securityCompany);
			if (communityPolice != null)
				newIncident.setCommunityPolice(communityPolice);
			if (severityTextField.getText() != "")
				newIncident.setSeverity(Integer.parseInt(severityTextField.getText()));
			else {
				Alert alert = new Alert(Alert.AlertType.WARNING, "You need to add severity of crime");
				alert.setTitle("Severity required");
				alert.setHeaderText("No Severity");
				alert.showAndWait();
			}
			if (descriptionTextField.getText() != "")
				newIncident.setDescription(descriptionTextField.getText());
			else {
				Alert alert = new Alert(Alert.AlertType.WARNING, "You need to add a description");
				alert.setTitle("Description required");
				alert.setHeaderText("No Description");
				alert.showAndWait();
			}
			if (civilian != null && severityTextField.getText() != "" && descriptionTextField.getText() != "") {
				if (communityPolice != null || securityCompany != null)
					addedCorrectely = HelperFunctions.appendClassToFile(pathToSaveIncident, newIncident);
				if (addedCorrectely) {
					savedIncidentCorrectlyLabel.setText("Succesfully saved a a new incident");
				} else {
					savedIncidentCorrectlyLabel.setText("Couldn't save new incident");
				}
			}

		});

		IncidentHandleContentVbox.getChildren().addAll(IncidentHandleHeadingLabel, InfoLabel, comboBoxes, severityHBox,
				descriptionHBox, savedIncidentCorrectlyLabel, submitIncidentButton);
		setContent(IncidentHandleContentVbox);
	}

	public void refresh() {
		communityPolice = null;
		securityCompany = null;
	    List<Object> civilianObjects = HelperFunctions.readClassesFromFile(pathToReadCivilian, Civilian.class);
	    List<Civilian> civilians = new ArrayList<>();

	    for (Object civObj : civilianObjects) {
	        if (civObj instanceof Civilian) {
	            civilians.add((Civilian) civObj);
	        }
	    }

	    civilianComboBox.getItems().clear();
	    civilianComboBox.getItems().addAll(civilians);
	    
		List<Object> securityCompObjects = HelperFunctions.readClassesFromFile(pathToReadSecurityCompany, SecurityCompany.class);
		List<SecurityCompany> securityCompanies = new ArrayList<>();

		for (Object secCompObj : securityCompObjects) {
			if (secCompObj instanceof SecurityCompany) {
				securityCompanies.add((SecurityCompany) secCompObj);
			}
		}

		securityCompComboBox.getItems().clear();
		securityCompComboBox.getItems().addAll(securityCompanies);
		
		List<Object> comPopoObjects = HelperFunctions.readClassesFromFile(pathToReadCommunityPolice, CommunityPolice.class);
		List<CommunityPolice> communityPolicies = new ArrayList<>();

		for (Object comPopoObj : comPopoObjects) {
			if (comPopoObj instanceof CommunityPolice) {
				communityPolicies.add((CommunityPolice) comPopoObj);
			}
		}

//		comPopoComboBox.getItems().clear();
//		comPopoComboBox.getItems().addAll(communityPolicies);
		
	}
}
