package ui.tabs;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.jwetherell.algorithms.data_structures.Graph;
import com.jwetherell.algorithms.data_structures.Graph.Edge;
import com.jwetherell.algorithms.data_structures.Graph.Vertex;

import javafx.geometry.HPos;
import javafx.geometry.Point2D;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import nodes.Civilian;
import nodes.CommunityPolice;
import nodes.Incident;
import nodes.Individual;
import nodes.SecurityCompany;
import ui.helper.HelperFunctions;

public class GraphTab extends Tab {
	private String pathToReadCivilian = "./data/Civilians.binary";
	private String pathToReadSecurityCompany = "./data/SecurityCompany.binary";
	private String pathToReadCommunityPolice = "./data/CommunityPolice.binary";
	private String pathToReadIncidents = "./data/Incident.binary";
	private Circle[] circleArray = new Circle[100];
	private UUID[] uuidArray = new UUID[100];
	private Integer row = 0;
	private Button showGraph = new Button("Show Graph");

	public GraphTab() {
		setText("Graph");
		setClosable(false);
		// creating the graph
		Graph<Individual> graph = new Graph<Individual>();
		// getting list of objects from civilian file
		List<Object> civilianObjects = HelperFunctions.readClassesFromFile(pathToReadCivilian);
		List<Civilian> civilians = new ArrayList<>();
		
		//Button which creates and shows graph
		Pane pane = new Pane();
		pane.setStyle("-fx-background-color: black;");
		Stage graphStage = new Stage();
		Scene scene = new Scene(pane, 800, 800);
		graphStage.setScene(scene);
		graphStage.initModality(Modality.APPLICATION_MODAL);
		graphStage.setWidth(800);
		graphStage.setHeight(800);
		showGraph.setOnAction(e -> {

			// adding civilians as vertices to graph
			for (Object civObj : civilianObjects) {
				if (civObj instanceof Civilian) {
					civilians.add((Civilian) civObj);
					graph.getVertices().add(new Vertex<Individual>((Civilian) civObj, 1));
					Integer[] coords = HelperFunctions.extractCoords(((Civilian) civObj).getLocation());

					Circle circle = new Circle(10, Color.BLUE);
					circle.setCenterX(coords[0] * 50);
					circle.setCenterY(coords[1] * 50);
					pane.getChildren().add(circle);
					circleArray[row] = circle;
					uuidArray[row] = ((Civilian) civObj).getId();
					row += 1;

				}
			}

			// getting list of objects from security file
			List<Object> securityCompanyObjects = HelperFunctions.readClassesFromFile(pathToReadSecurityCompany);
			List<SecurityCompany> securityCompanies = new ArrayList<>();

			// adding security companies as vertices to graph
			for (Object secObj : securityCompanyObjects) {
				if (secObj instanceof SecurityCompany) {
					securityCompanies.add((SecurityCompany) secObj);
					graph.getVertices().add(new Vertex<Individual>((SecurityCompany) secObj, 2));
					Integer[] coords = HelperFunctions.extractCoords(((SecurityCompany) secObj).getLocation());
					Circle circle = new Circle(10, Color.RED);
					circle.setCenterX(coords[0] * 50);
					circle.setCenterY(coords[1] * 50);
					pane.getChildren().add(circle);
					circleArray[row] = circle;
					uuidArray[row] = ((SecurityCompany) secObj).getId();
					row += 1;

				}
			}

			// getting list of objects from community police file
			List<Object> communityPoliceObjects = HelperFunctions.readClassesFromFile(pathToReadCommunityPolice);
			List<CommunityPolice> communityPolicies = new ArrayList<>();

			// adding community police as vertices to graph
			for (Object comPopoObj : communityPoliceObjects) {
				if (comPopoObj instanceof CommunityPolice) {
					communityPolicies.add((CommunityPolice) comPopoObj);
					graph.getVertices().add(new Vertex<Individual>((CommunityPolice) comPopoObj, 3));
					Integer[] coords = HelperFunctions.extractCoords(((CommunityPolice) comPopoObj).getLocation());
					Circle circle = new Circle(10, Color.ORANGE);
					circle.setCenterX(coords[0] * 50);
					circle.setCenterY(coords[1] * 50);
					pane.getChildren().add(circle);
					circleArray[row] = circle;
					uuidArray[row] = ((CommunityPolice) comPopoObj).getId();
					row += 1;

				}
			}

			// getting all incidents saved, which will be the edges between the nodes
			List<Object> incidentObjects = HelperFunctions.readClassesFromFile(pathToReadIncidents);
			List<Incident> incidents = new ArrayList<>();
			Integer[] civilianCoords = null;
			Integer[] responderCoords = null;
			// adding community police as vertices to graph
			for (Object incidentObj : incidentObjects) {
				Vertex<Individual> v1 = null;
				Vertex<Individual> v2 = null;

				if (incidentObj instanceof Incident) {
					incidents.add((Incident) incidentObj);
					System.out.println("printing: " + incidentObj);
					if (((Incident) incidentObj).getSecurityCompany() != null) {
						if (((Incident) incidentObj).getCivilian() != null) {
							List<Vertex<Individual>> theVertices = graph.getVertices();
							for (Vertex<Individual> vertex : theVertices) {
								if (vertex.getValue().getId().equals(((Incident) incidentObj).getCivilian().getId())) {
									v1 = vertex;
									civilianCoords = HelperFunctions
											.extractCoords(((Incident) incidentObj).getCivilian().getLocation());
								}
								if (vertex.getValue().getId()
										.equals(((Incident) incidentObj).getSecurityCompany().getId())) {
									v2 = vertex;
									responderCoords = HelperFunctions
											.extractCoords(((Incident) incidentObj).getSecurityCompany().getLocation());
								}
							}
							Edge<Individual> incidentEdge = new Edge<Individual>(((Incident) incidentObj).getSeverity(), v1,
									v2);
							Line line = new Line();
							// Set the line's color and width:
							line.setStroke(Color.WHITE);
							line.setStrokeWidth(5);
							for (int i = 0; i < uuidArray.length; i++) {
								Circle node1 = null;
								Circle node2 = null;
								UUID uuid = uuidArray[i];
								if (((Incident) incidentObj).getCivilian().getId().equals(uuid)) {
									node1 = circleArray[i];
									line.setStartX(node1.getCenterX());
									line.setStartY(node1.getCenterY());

								} else if (((Incident) incidentObj).getSecurityCompany().getId().equals(uuid)) {
									node2 = circleArray[i];
									line.setEndX(node2.getCenterX());
									line.setEndY(node2.getCenterY());

								}
							}
							pane.getChildren().add(line);

						}
					} else {
						if (((Incident) incidentObj).getCommunityPolice() != null) {
							if (((Incident) incidentObj).getCivilian() != null) {

							}
						}
					}

					// Edge<Incident> incidentEdge = new Edge<Incident>(((Incident)
					// incidentObj).getSeverity(), v1, v2);

				}

			}
			graphStage.showAndWait();
		});



		VBox graphContentVBox = new VBox();


		graphContentVBox.getChildren().addAll(showGraph);

		setContent(graphContentVBox);

	}

}
