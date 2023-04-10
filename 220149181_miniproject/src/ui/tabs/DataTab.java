package ui.tabs;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import nodes.Civilian;
import nodes.CommunityPolice;
import nodes.SecurityCompany;
import ui.helper.HelperFunctions;

public class DataTab extends Tab {
	private String pathToReadCivilian = "./data/Civilians.binary";
	private String pathToReadSecurityCompany = "./data/SecurityCompany.binary";
	private String pathToReadCommunityPolice = "./data/CommunityPolice.binary";

	public DataTab() {
		setText("Data");
		setClosable(false);
		// label informing them to choose which data they want to view
		Label choiceLabel = new Label("Choose which data you want to view");
		choiceLabel.setFont(Font.font("System", FontWeight.BOLD, 16));

		// Buttons which will show each nodes data
		Button viewCivilianButton = new Button("Civilians");
		viewCivilianButton.setPrefSize(300, 60);
		Button viewSecurityCompnayButton = new Button("Security Companies");
		viewSecurityCompnayButton.setPrefSize(300, 60);
		Button viewCommunityPoliceButton = new Button("Community Police");
		viewCommunityPoliceButton.setPrefSize(300, 60);

		// Handling viewCivilianButton button click
		viewCivilianButton.setOnAction(e -> {
			// Getting the civilian data from the civilian text file and adding each one to
			// getting list of objects from civilian file
			List<Object> objects = HelperFunctions.readClassesFromFile(pathToReadCivilian);
			List<Civilian> civilians = new ArrayList<>();

			// creating a modal where the civilian data will be displayed
			Stage CivilianModal = new Stage();
			CivilianModal.setTitle("Civilian Details");
			CivilianModal.initModality(Modality.APPLICATION_MODAL);

			Label civilianDataHeadingLabel = new Label("Civilian Data");
			civilianDataHeadingLabel.setFont(Font.font("System", FontWeight.BOLD, 16));
			// the VBox that will contain the modal data
			VBox civilianModalContent = new VBox(civilianDataHeadingLabel);
			civilianModalContent.setAlignment(Pos.TOP_CENTER);
			civilianModalContent.setSpacing(10);
			civilianModalContent.setPadding(new Insets(10));

			for (Object obj : objects) {
				if (obj instanceof Civilian) {
					civilians.add((Civilian) obj);
				}
			}

			String cssLayout = "-fx-border-color: black;\n" + "-fx-border-width: 3;\n"
					+ "-fx-border-style: solid;\n; -fx-border-width: 0 0 5 0;";

			for (Civilian civ : civilians) {
				Label civName = new Label();
				Label civGender = new Label();
				Label civStreetName = new Label();
				civName.setText("Name: " + civ.getName());
				civGender.setText("Gender: " + civ.getGender());
				civStreetName.setText("Street: " + civ.getStreetName());
				VBox oneCivData = new VBox(civName, civGender, civStreetName);
				oneCivData.setStyle(cssLayout);
				civilianModalContent.getChildren().add(oneCivData);
			}

			ScrollPane scroll = new ScrollPane();
			scroll.setPrefSize(800, 800);
			// Setting content to the scroll pane
			scroll.setContent(civilianModalContent);
			// add the vbox to the scene and set it as the content for the modal
			Scene modalScene = new Scene(scroll, 300, 400);
			CivilianModal.setScene(modalScene);

			// show the modal
			CivilianModal.show();

		});

		// Handling viewSecurityCompanyButton button click
		viewSecurityCompnayButton.setOnAction(e -> {
			// Getting the civilian data from the civilian text file and adding each one to
			// getting list of objects from civilian file
			List<Object> objects = HelperFunctions.readClassesFromFile(pathToReadSecurityCompany);
			List<SecurityCompany> securityCompanies = new ArrayList<>();

			// creating a modal where the civilian data will be displayed
			Stage SecurityCompanyModal = new Stage();
			SecurityCompanyModal.setTitle("Security Company Details");
			SecurityCompanyModal.initModality(Modality.APPLICATION_MODAL);

			Label securityCompanyDataHeadingLabel = new Label("Security Company Data");
			securityCompanyDataHeadingLabel.setFont(Font.font("System", FontWeight.BOLD, 16));
			// the VBox that will contain the modal data
			VBox securityCompanyModalContent = new VBox(securityCompanyDataHeadingLabel);
			securityCompanyModalContent.setAlignment(Pos.TOP_CENTER);
			securityCompanyModalContent.setSpacing(10);
			securityCompanyModalContent.setPadding(new Insets(10));

			for (Object obj : objects) {
				if (obj instanceof SecurityCompany) {
					securityCompanies.add((SecurityCompany) obj);
				}
			}

			String cssLayout = "-fx-border-color: black;\n" + "-fx-border-width: 3;\n"
					+ "-fx-border-style: solid;\n; -fx-border-width: 0 0 5 0;";

			for (SecurityCompany secComp : securityCompanies) {
				Label secNameLabel = new Label();
				Label secLocationLabel = new Label();
				Label secPriceLabel = new Label();
				secNameLabel.setText("Name: " + secComp.getName());
				secLocationLabel.setText("Location: " + secComp.getLocation());
				secPriceLabel.setText("Price: R" + secComp.getPrice());
				VBox oneSecData = new VBox(secNameLabel, secLocationLabel, secPriceLabel);
				oneSecData.setStyle(cssLayout);
				securityCompanyModalContent.getChildren().add(oneSecData);
			}

			ScrollPane scroll = new ScrollPane();
			scroll.setPrefSize(800, 800);
			// Setting content to the scroll pane
			scroll.setContent(securityCompanyModalContent);
			// add the vbox to the scene and set it as the content for the modal
			Scene modalScene = new Scene(scroll, 300, 400);
			SecurityCompanyModal.setScene(modalScene);

			// show the modal
			SecurityCompanyModal.show();

		});

		// Handling viewCivilianButton button click
		viewCommunityPoliceButton.setOnAction(e -> {
			// Getting the community police data from the community police text file and adding each one to
			// getting list of objects from community police file
			List<Object> objects = HelperFunctions.readClassesFromFile(pathToReadCommunityPolice);
			List<CommunityPolice> communityPolicies = new ArrayList<>();

			// creating a modal where the civilian data will be displayed
			Stage CommunityPoliceModal = new Stage();
			CommunityPoliceModal.setTitle("Community Police Details");
			CommunityPoliceModal.initModality(Modality.APPLICATION_MODAL);

			Label communityPoliceDataHeadingLabel = new Label("Community Police Data");
			communityPoliceDataHeadingLabel.setFont(Font.font("System", FontWeight.BOLD, 16));
			// the VBox that will contain the modal data
			VBox communityPoliceModalContent = new VBox(communityPoliceDataHeadingLabel);
			communityPoliceModalContent.setAlignment(Pos.TOP_CENTER);
			communityPoliceModalContent.setSpacing(10);
			communityPoliceModalContent.setPadding(new Insets(10));

			for (Object obj : objects) {
				if (obj instanceof CommunityPolice) {
					communityPolicies.add((CommunityPolice) obj);
				}
			}

			String cssLayout = "-fx-border-color: black;\n" + "-fx-border-width: 3;\n"
					+ "-fx-border-style: solid;\n; -fx-border-width: 0 0 5 0;";

			for (CommunityPolice comPopo : communityPolicies) {
				Label secLocationLabel = new Label();
				Label secNumberOfMemebersLabel = new Label();
				Label secNumberOfAvailableSpaceLabel = new Label();
				secLocationLabel.setText("Location: " + comPopo.getLocation());
				secNumberOfMemebersLabel.setText("Members no: " + comPopo.getNumberOfMemebers());
				secNumberOfAvailableSpaceLabel.setText("Available: " + comPopo.getAvailableSpace());
				VBox oneComPopoData = new VBox(secLocationLabel, secNumberOfMemebersLabel,
						secNumberOfAvailableSpaceLabel);
				oneComPopoData.setStyle(cssLayout);
				communityPoliceModalContent.getChildren().add(oneComPopoData);
			}

			ScrollPane scroll = new ScrollPane();
			scroll.setPrefSize(800, 800);
			// Setting content to the scroll pane
			scroll.setContent(communityPoliceModalContent);
			// add the vbox to the scene and set it as the content for the modal
			Scene modalScene = new Scene(scroll, 300, 400);
			CommunityPoliceModal.setScene(modalScene);

			// show the modal
			CommunityPoliceModal.show();

		});

		VBox mainContentVBox = new VBox();
		mainContentVBox.setAlignment(Pos.CENTER);
		mainContentVBox.setSpacing(30);

		mainContentVBox.getChildren().addAll(choiceLabel, viewCivilianButton, viewSecurityCompnayButton,
				viewCommunityPoliceButton);
		setContent(mainContentVBox);
	}

}
