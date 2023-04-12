package ui.buttons;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.jwetherell.algorithms.data_structures.Graph;
import com.jwetherell.algorithms.data_structures.Graph.Edge;
import com.jwetherell.algorithms.data_structures.Graph.Vertex;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import nodes.Civilian;
import nodes.CommunityPolice;
import nodes.Incident;
import nodes.Individual;
import nodes.SecurityCompany;
import ui.helper.HelperFunctions;

public class GraphButton extends Button {
	private String pathToReadCivilian = "./data/Civilians.binary";
	private String pathToReadSecurityCompany = "./data/SecurityCompany.binary";
	private String pathToReadCommunityPolice = "./data/CommunityPolice.binary";
	private String pathToReadIncidents = "./data/Incident.binary";
	private Circle[] circleArray = new Circle[30];
	private UUID[] uuidArray = new UUID[30];
	private Integer row = 0;
	private ArrayList<Label> nodeLabels = new ArrayList<>();

	public GraphButton(String Graph) {
		super(Graph);
		this.setStyle("-fx-font-size: 16pt;"); // set a custom style

	}

	public void doSomething() {
		row = 0;
		// getting list of objects from civilian file
		// Button which creates and shows graph
		Pane pane = new Pane();
		pane.setStyle("-fx-background-color: black;");
		Stage graphStage = new Stage();
		Scene scene = new Scene(pane, 800, 800);
		graphStage.setScene(scene);
		graphStage.initModality(Modality.APPLICATION_MODAL);
		graphStage.setWidth(800);
		graphStage.setHeight(800);
		List<Object> civilianObjects = HelperFunctions.readClassesFromFile(pathToReadCivilian);
		List<Civilian> civilians = new ArrayList<>();

		Graph<Individual> graph = new Graph<Individual>();
		// adding civilians as vertices to graph
		for (Object civObj : civilianObjects) {
			if (civObj instanceof Civilian) {
				civilians.add((Civilian) civObj);
				System.out.println(((Civilian) civObj).getName());
				System.out.println(((Civilian) civObj).getLocation());
				graph.getVertices().add(new Vertex<Individual>((Civilian) civObj, 1));
				Integer[] coords = HelperFunctions.extractCoords(((Civilian) civObj).getLocation());
				System.out.println(coords[0]);
				System.out.println(coords[1]);

				Circle circle = new Circle(10, Color.BLUE);
				Label civNameLabel = new Label(((Civilian) civObj).getName());
				civNameLabel.setTextFill(Color.DARKOLIVEGREEN);
				circle.setCenterX(coords[0] * 50);
				circle.setCenterY(coords[1] * 50);
				civNameLabel.setLayoutX(circle.getCenterX() - circle.getRadius());
				civNameLabel.setLayoutY(circle.getCenterY() - circle.getRadius() * 2.5);
				nodeLabels.add(civNameLabel);
				if (!pane.getChildren().contains(circle)) {
					pane.getChildren().add(circle);
				}
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
				Label secNameLabel = new Label(((SecurityCompany) secObj).getName());
				secNameLabel.setTextFill(Color.DARKOLIVEGREEN);
				circle.setCenterX(coords[0] * 50);
				circle.setCenterY(coords[1] * 50);
				secNameLabel.setLayoutX(circle.getCenterX() - circle.getRadius());
				secNameLabel.setLayoutY(circle.getCenterY() - circle.getRadius() * 2.5);
				nodeLabels.add(secNameLabel);
				if (!pane.getChildren().contains(circle)) {
					pane.getChildren().add(circle);
				}
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
				Label commLabel = new Label("COMMUNITY POLICE");
				commLabel.setTextFill(Color.DARKOLIVEGREEN);
				circle.setCenterX(coords[0] * 50);
				circle.setCenterY(coords[1] * 50);
				commLabel.setLayoutX(circle.getCenterX() - circle.getRadius());
				commLabel.setLayoutY(circle.getCenterY() - circle.getRadius() * 2.5);
				nodeLabels.add(commLabel);
				if (!pane.getChildren().contains(circle)) {
					pane.getChildren().add(circle);
				}
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
		Boolean foundEdge = false;
		// adding community police as vertices to graph
		for (Object incidentObj : incidentObjects) {
			Vertex<Individual> v1 = null;
			Vertex<Individual> v2 = null;

			if (incidentObj instanceof Incident) {
				incidents.add((Incident) incidentObj);
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
						Label edgeWeightLabel = new Label((((Incident) incidentObj).getSeverity()).toString());
						double centerX = (line.getStartX() + line.getEndX()) / 2;
						double centerY = (line.getStartY() + line.getEndY()) / 2;
						edgeWeightLabel.setLayoutX(centerX);
						edgeWeightLabel.setLayoutY(centerY);
						edgeWeightLabel.setTextFill(Color.DARKOLIVEGREEN);
						nodeLabels.add(edgeWeightLabel);
						if (!pane.getChildren().contains(line)) {
							pane.getChildren().add(line);
						}

					}
				} else {
					if (((Incident) incidentObj).getCommunityPolice() != null) {
						if (((Incident) incidentObj).getCivilian() != null) {

						}
					}
				}
				if (v1 != null && v2 != null) {
					System.out.println(v2.compareTo(v1));
					Edge<Individual> incidentEdge = new Edge<Individual>(((Incident) incidentObj).getSeverity(), v1,
							v2);
					graph.getEdges().add(incidentEdge);
					v1 = null;
					v2 = null;
				}

			}

		}
		for (Label label : nodeLabels) {
			label.setFont(Font.font("System", FontWeight.BOLD, 13));
			if (!pane.getChildren().contains(label)) {
				pane.getChildren().add(label);
			}
		}
		// System.out.println(graph.getEdges());
		System.out.println(graph);

		graphStage.showAndWait();
	}

}
