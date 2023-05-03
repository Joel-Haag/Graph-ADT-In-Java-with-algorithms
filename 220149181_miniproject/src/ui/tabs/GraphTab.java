package ui.tabs;

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;
import nodes.Incident;
import ui.buttons.GraphButton;
import ui.buttons.MinimumSpanningTreeGraphButton;
import ui.buttons.ShortestPathGraphButton;
import ui.helper.HelperFunctions;

public class GraphTab extends Tab {
	private ComboBox<Incident> incidentComboBox;
	private String pathToReadIncident = "./data/Incident.binary";
	private Incident incident = null;

	public GraphTab() {
		setText("Graph");
		setClosable(false);
		VBox graphTabContentVbox = new VBox();
		Label showInitialGraphLabel = new Label("View Graph made from raw entered data");
		GraphButton showGraphButton = new GraphButton("View");

		Label showRecommendedGraphLabel = new Label(
				"View Reccomended graph to connect nodes using" + "\n Prim's algorithm for minimum spanning tree");
		MinimumSpanningTreeGraphButton showMSTGraphButton = new MinimumSpanningTreeGraphButton("View");

		Label showRecommendedShortestPathLabel = new Label(
				"View shortest path from a security company to a civilian using shortest path algorithm");
		ShortestPathGraphButton showShortestPathGraphButton = new ShortestPathGraphButton("View");
		VBox comboBoxes = new VBox();
		incidentComboBox = new ComboBox<>();

		List<Object> incidentObjects = HelperFunctions.readClassesFromFile(pathToReadIncident, Incident.class);
		List<Incident> incidents = new ArrayList<>();

		for (Object incidentObj : incidentObjects) {
			if (incidentObj instanceof Incident) {
				incidents.add((Incident) incidentObj);
			}
		}

		incidentComboBox.getItems().addAll(incidents);

		incidentComboBox = new ComboBox<>();
		incidentComboBox.getItems().addAll(incidents);

		incidentComboBox.setCellFactory(param -> new ListCell<Incident>() {
			@Override
			protected void updateItem(Incident inc, boolean empty) {
				super.updateItem(inc, empty);

				if (empty || inc == null) {
					setText(null);
				} else {
					setText(inc.getCivilian().getName() + "->" + (inc.getSecurityCompany().getName()));
				}
			}
		});

		// Set text for civilian combo box
		incidentComboBox.setPromptText("Select Incident");

		// Set a listener to print the selected item whenever the value changes
		incidentComboBox.valueProperty().addListener((observable, oldInc, newInc) -> {
			if (newInc != null) {
				incident = newInc;
			}
		});

		comboBoxes.getChildren().addAll(incidentComboBox, showShortestPathGraphButton);
		comboBoxes.setSpacing(10);
		comboBoxes.setAlignment(Pos.CENTER);

		graphTabContentVbox.getChildren().addAll(showInitialGraphLabel, showGraphButton, showRecommendedGraphLabel,
				showMSTGraphButton, showRecommendedShortestPathLabel, comboBoxes);
		graphTabContentVbox.setAlignment(Pos.TOP_CENTER);
		graphTabContentVbox.setSpacing(20);
		graphTabContentVbox.setPadding(new Insets(20));

		showGraphButton.setOnAction(e -> showGraphButton.createGraph()); // add an action to the button
		showMSTGraphButton.setOnAction(e -> showMSTGraphButton.createGraph()); // add an action to the button
		showShortestPathGraphButton.setOnAction(e -> showShortestPathGraphButton.createGraph(incident)); // add an action to the
																									// button

		setContent(graphTabContentVbox);

	}

}
