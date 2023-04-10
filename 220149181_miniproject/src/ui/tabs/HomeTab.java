package ui.tabs;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import nodes.Civilian;
import nodes.CommunityPolice;
import nodes.SecurityCompany;
import ui.helper.HelperFunctions;

public class HomeTab extends Tab {

	private String pathToSaveCivilian = "./data/Civilians.binary";
	private String pathToSaveSecurityCompany = "./data/SecurityCompany.binary";
	private String pathToSaveCommunityPolice = "./data/CommunityPolice.binary";
	private Boolean addedCorrectely = false;

	public HomeTab() {
		setText("Home");
		setClosable(false);
		// creating label for adding content to tabs
		Label crimeIncidentLabel = new Label("Add Civilian Incident");
		Label securityCompanyLabel = new Label("Add Security Company");
		Label communityPoliceLabel = new Label("Add Community Police");
		// centering the text and making it bold and bigger
		crimeIncidentLabel.setFont(Font.font("System", FontWeight.BOLD, 16));
		securityCompanyLabel.setFont(Font.font("System", FontWeight.BOLD, 16));
		communityPoliceLabel.setFont(Font.font("System", FontWeight.BOLD, 16));

		// info label for when they attempt to add civilian
		Label addedCivilianCorrectlyLabel = new Label("");
		Label addedSecurityCompanyCorrectlyLabel = new Label("");
		Label addedCommunityPoliceCorrectlyLabel = new Label("");

		// creating buttons to add civilians, security companies and community police
		Button addCivilianBtn = new Button("Add");
		addCivilianBtn.setPrefSize(60, 15);

		Button addSecurityCompanyBtn = new Button("Add");
		addSecurityCompanyBtn.setPrefSize(60, 15);

		Button addCommunityPoliceBtn = new Button("Add");
		addCommunityPoliceBtn.setPrefSize(60, 15);

		// button action to add civilian
		addCivilianBtn.setOnAction(m -> {
			addedSecurityCompanyCorrectlyLabel.setText("");
			addedCivilianCorrectlyLabel.setText("");
			addedCommunityPoliceCorrectlyLabel.setText("");
			// create a modal window
			Stage addCivilianIncidentModal = new Stage();
			addCivilianIncidentModal.setTitle("Add Civilian Details");
			addCivilianIncidentModal.initModality(Modality.APPLICATION_MODAL);

			// add content to modal window
			// adding civilian name label and text field to hbox
			Label nameLabel = new Label("Name:");
			TextField nameTextField = new TextField();
			HBox nameBox = new HBox(nameLabel, nameTextField);
			nameBox.setAlignment(Pos.CENTER_LEFT);
			nameBox.setSpacing(50);

			// adding gender label and text field to a new hbox
			Label genderLabel = new Label("Gender: (M/F)");
			TextField genderTextField = new TextField();
			HBox genderBox = new HBox(genderLabel, genderTextField);
			genderBox.setAlignment(Pos.CENTER_LEFT);
			genderBox.setSpacing(10);

			// adding streetName label and text field to a new hbox
			Label streetNameLabel = new Label("Street Name:");
			TextField streetNameTextField = new TextField();
			HBox streetNameBox = new HBox(streetNameLabel, streetNameTextField);
			streetNameBox.setAlignment(Pos.CENTER_LEFT);
			streetNameBox.setSpacing(15);

			// adding age label and text field to a new hbox
			Label ageLabel = new Label("Age:");
			TextField ageTextField = new TextField();
			HBox ageBox = new HBox(ageLabel, ageTextField);
			ageBox.setAlignment(Pos.CENTER_LEFT);
			ageBox.setSpacing(60);

			// save button
			Button saveCivilianBtn = new Button("SAVE");
			saveCivilianBtn.setPrefSize(60, 15);

			// create a vbox to hold the content in the modal
			VBox civilianModalContent = new VBox(nameBox, genderBox, streetNameBox, ageBox, saveCivilianBtn);
			civilianModalContent.setAlignment(Pos.CENTER);
			civilianModalContent.setSpacing(10);

			// add the vbox to the scene and set it as the content for the modal
			Scene modalScene = new Scene(civilianModalContent, 300, 300);
			addCivilianIncidentModal.setScene(modalScene);

			// show the modal
			addCivilianIncidentModal.show();

			saveCivilianBtn.setOnAction(e -> {
				addedCorrectely = false;
				Civilian newCivilian = new Civilian(nameTextField.getText(), genderTextField.getText(),
						streetNameTextField.getText(), Integer.parseInt(ageTextField.getText()));
				// adding civilian to file
				addedCorrectely = HelperFunctions.appendClassToFile(pathToSaveCivilian, newCivilian);
				if (addedCorrectely) {
					addedCivilianCorrectlyLabel.setText("Succesfully saved a civilian incident");
				} else {
					addedCivilianCorrectlyLabel.setText("Couldn't save civilian incident");
				}
				addCivilianIncidentModal.close();
			});
		});

		// button action to add security company
		addSecurityCompanyBtn.setOnAction(m -> {
			addedCivilianCorrectlyLabel.setText("");
			addedSecurityCompanyCorrectlyLabel.setText("");
			addedCommunityPoliceCorrectlyLabel.setText("");
			// create a modal window
			Stage addSecurityCompanyModal = new Stage();
			addSecurityCompanyModal.setTitle("Add Security Company Details");
			addSecurityCompanyModal.initModality(Modality.APPLICATION_MODAL);

			// add content to modal window
			// adding security company name label and text field to hbox
			Label nameLabel = new Label("Name:");
			TextField nameTextField = new TextField();
			HBox nameBox = new HBox(nameLabel, nameTextField);
			nameBox.setAlignment(Pos.CENTER_LEFT);
			nameBox.setSpacing(50);

			// adding location of crime label and text field to a new hbox
			Label locationOfCrimeLabel = new Label("Location:");
			TextField locationOfCrimeTextField = new TextField();
			HBox locationOfCrimeBox = new HBox(locationOfCrimeLabel, locationOfCrimeTextField);
			locationOfCrimeBox.setAlignment(Pos.CENTER_LEFT);
			locationOfCrimeBox.setSpacing(37);

			// adding age label and text field to a new hbox
			Label priceLabel = new Label("Price:");
			TextField priceTextField = new TextField();
			HBox priceBox = new HBox(priceLabel, priceTextField);
			priceBox.setAlignment(Pos.CENTER_LEFT);
			priceBox.setSpacing(55);

			// save button
			Button saveSecurityCompanyBtn = new Button("SAVE");
			saveSecurityCompanyBtn.setPrefSize(60, 15);

			// create a vbox to hold the content in the modal
			VBox SecurityCompanyModalContent = new VBox(nameBox, locationOfCrimeBox, priceBox, saveSecurityCompanyBtn);
			SecurityCompanyModalContent.setAlignment(Pos.CENTER);
			SecurityCompanyModalContent.setSpacing(10);

			// add the vbox to the scene and set it as the content for the modal
			Scene modalScene = new Scene(SecurityCompanyModalContent, 300, 300);
			addSecurityCompanyModal.setScene(modalScene);

			// show the modal
			addSecurityCompanyModal.show();

			saveSecurityCompanyBtn.setOnAction(e -> {
				addedCorrectely = false;
				SecurityCompany newSecurityCompany = new SecurityCompany(nameTextField.getText(),
						locationOfCrimeTextField.getText(), Double.parseDouble(priceTextField.getText()));
				// adding civilian to file
				addedCorrectely = HelperFunctions.appendClassToFile(pathToSaveSecurityCompany, newSecurityCompany);
				if (addedCorrectely) {
					addedSecurityCompanyCorrectlyLabel.setText("Succesfully saved a security compnay");
				} else {
					addedSecurityCompanyCorrectlyLabel.setText("Couldn't save security company");
				}
				addSecurityCompanyModal.close();
			});
		});

		// button action to add community police
		addCommunityPoliceBtn.setOnAction(m -> {
			addedCivilianCorrectlyLabel.setText("");
			addedSecurityCompanyCorrectlyLabel.setText("");
			addedCommunityPoliceCorrectlyLabel.setText("");
			// create a modal window
			Stage addCommunityPoliceModal = new Stage();
			addCommunityPoliceModal.setTitle("Add Community Police Details");
			addCommunityPoliceModal.initModality(Modality.APPLICATION_MODAL);

			// adding location of crime label and text field to a new hbox
			Label locationOfCrimeLabel = new Label("Location:");
			TextField locationOfCrimeTextField = new TextField();
			HBox locationOfCrimeBox = new HBox(locationOfCrimeLabel, locationOfCrimeTextField);
			locationOfCrimeBox.setAlignment(Pos.CENTER_LEFT);
			locationOfCrimeBox.setSpacing(50);

			// adding number of members label and text field to a new hbox
			Label numberOfMembersLabel = new Label("Memebers no:");
			TextField numberOfMembersTextField = new TextField();
			HBox numberOfMembersBox = new HBox(numberOfMembersLabel, numberOfMembersTextField);
			numberOfMembersBox.setAlignment(Pos.CENTER_LEFT);
			numberOfMembersBox.setSpacing(22);

			// adding age label and text field to a new hbox
			Label availableSpaceLabel = new Label("Available Space:");
			TextField availableSpaceTextField = new TextField();
			HBox availableSpaceBox = new HBox(availableSpaceLabel, availableSpaceTextField);
			availableSpaceBox.setAlignment(Pos.CENTER_LEFT);
			availableSpaceBox.setSpacing(14);

			// save button
			Button saveCommunityPoliceBtn = new Button("SAVE");
			saveCommunityPoliceBtn.setPrefSize(60, 15);

			// create a vbox to hold the content in the modal
			VBox CommunityPoliceModalContent = new VBox(locationOfCrimeBox, numberOfMembersBox, availableSpaceBox,
					saveCommunityPoliceBtn);
			CommunityPoliceModalContent.setAlignment(Pos.CENTER);
			CommunityPoliceModalContent.setSpacing(10);

			// add the vbox to the scene and set it as the content for the modal
			Scene modalScene = new Scene(CommunityPoliceModalContent, 300, 300);
			addCommunityPoliceModal.setScene(modalScene);

			// show the modal
			addCommunityPoliceModal.show();

			saveCommunityPoliceBtn.setOnAction(e -> {
				addedCorrectely = false;
				CommunityPolice newCommunityPolice = new CommunityPolice(locationOfCrimeTextField.getText(),
						Integer.parseInt(availableSpaceTextField.getText()),
						Integer.parseInt(availableSpaceTextField.getText()));
				// adding civilian to file
				addedCorrectely = HelperFunctions.appendClassToFile(pathToSaveCommunityPolice, newCommunityPolice);
				if (addedCorrectely) {
					addedCommunityPoliceCorrectlyLabel.setText("Succesfully saved a community police");
				} else {
					addedCommunityPoliceCorrectlyLabel.setText("Couldn't save community police");
				}

				addCommunityPoliceModal.close();
			});
		});

		// creating the vbox and centering for adding civilian label and button
		VBox mainContentVBox = new VBox();
		mainContentVBox.setAlignment(Pos.CENTER);
		mainContentVBox.setSpacing(10);

		mainContentVBox.getChildren().addAll(crimeIncidentLabel, addCivilianBtn, addedCivilianCorrectlyLabel,
				securityCompanyLabel, addSecurityCompanyBtn, addedSecurityCompanyCorrectlyLabel, communityPoliceLabel,
				addCommunityPoliceBtn, addedCommunityPoliceCorrectlyLabel);
		setContent(mainContentVBox);
	}
}