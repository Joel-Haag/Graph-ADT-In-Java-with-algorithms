package ui.buttons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.jwetherell.algorithms.data_structures.Graph;
import com.jwetherell.algorithms.data_structures.Graph.Edge;
import com.jwetherell.algorithms.data_structures.Graph.Vertex;

import javafx.scene.Node;
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
import nodes.algorithms.AlgorithmHelperFunctions;

public class GraphButton extends Button {
	private String pathToReadCivilian = "./data/Civilians.binary";
	private String pathToReadSecurityCompany = "./data/SecurityCompany.binary";
	private String pathToReadCommunityPolice = "./data/CommunityPolice.binary";
	private String pathToReadIncidents = "./data/Incident.binary";
	private Circle[] circleArray = new Circle[50];
	private UUID[] uuidArray = new UUID[50];
	private Integer row = 0;
	private ArrayList<Label> nodeLabels = new ArrayList<>();

	public GraphButton(String Graph) {
		super(Graph);
		this.setStyle("-fx-font-size: 16pt;"); // set a custom style

	}

	public void createGraph() {
		setElementsToNothing();
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
		List<Object> civilianObjects = HelperFunctions.readClassesFromFile(pathToReadCivilian, Civilian.class);
		List<Civilian> civilians = new ArrayList<>();

		Graph<Individual> graph = new Graph<Individual>();
		// adding civilians as vertices to graph
		for (Object civObj : civilianObjects) {
			if (civObj instanceof Civilian) {
				civilians.add((Civilian) civObj);
				graph.getVertices().add(new Vertex<Individual>((Civilian) civObj, 1));
				Double[] coords = HelperFunctions.extractCoords(((Civilian) civObj).getLocation());

				Circle circle = new Circle(10, Color.BLUE);
				Label civNameLabel = new Label(((Civilian) civObj).getName());
				civNameLabel.setTextFill(Color.YELLOWGREEN);
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
		List<Object> securityCompanyObjects = HelperFunctions.readClassesFromFile(pathToReadSecurityCompany,
				SecurityCompany.class);
		List<SecurityCompany> securityCompanies = new ArrayList<>();
		Vertex<Individual> prevSecurityCompany = null;
		int counter = 0;
		SecurityCompany prevComp = null;
		// adding security companies as vertices to graph
		Circle[] tempSecurityCompanyCircleArray = new Circle[30];
		UUID[] tempSecurityCompanyUUIDArray = new UUID[30];
		for (Object secObj : securityCompanyObjects) {
			if (secObj instanceof SecurityCompany) {
				Circle node1 = null;
				Circle node2 = null;
				securityCompanies.add((SecurityCompany) secObj);
				Vertex<Individual> securityCompany = new Vertex<Individual>((SecurityCompany) secObj, 2);
				graph.getVertices().add(securityCompany);

				Double[] coords = HelperFunctions.extractCoords(((SecurityCompany) secObj).getLocation());
				Circle circle = new Circle(10, Color.RED);
				Label secNameLabel = new Label(((SecurityCompany) secObj).getName());
				secNameLabel.setTextFill(Color.YELLOWGREEN);
				circle.setCenterX(coords[0] * 50);
				circle.setCenterY(coords[1] * 50);
				secNameLabel.setLayoutX(circle.getCenterX() - circle.getRadius());
				secNameLabel.setLayoutY(circle.getCenterY() - circle.getRadius() * 2.5);
				nodeLabels.add(secNameLabel);
				if (!pane.getChildren().contains(circle)) {
					pane.getChildren().add(circle);
				}

				tempSecurityCompanyCircleArray[counter] = circle;
				tempSecurityCompanyUUIDArray[counter] = ((SecurityCompany) secObj).getId();

				circleArray[row] = circle;
				uuidArray[row] = ((SecurityCompany) secObj).getId();

				row += 1;
				counter += 1;

				if (row > 1) {
					for (int i = 0; i < counter - 1; i++) {
						node1 = tempSecurityCompanyCircleArray[counter - 1];
						node2 = tempSecurityCompanyCircleArray[i];

						Double[] coordsPrev = HelperFunctions.extractCoords(securityCompanies.get(i).getLocation());
						int securityWeight = (int) AlgorithmHelperFunctions.getDistance(coordsPrev, coords);

						Line line = new Line();
						// Set the line's color and width:
						line.setStroke(Color.GREEN);
						line.setStrokeWidth(5);

						line.setStartX(node1.getCenterX());
						line.setStartY(node1.getCenterY());
						line.setEndX(node2.getCenterX());
						line.setEndY(node2.getCenterY());

						Label edgeWeightLabel = new Label(Integer.toString(securityWeight));
						double centerX = (line.getStartX() + line.getEndX()) / 2;
						double centerY = (line.getStartY() + line.getEndY()) / 2;
						edgeWeightLabel.setLayoutX(centerX);
						edgeWeightLabel.setLayoutY(centerY);
						edgeWeightLabel.setTextFill(Color.YELLOW);
						if (securityWeight < 12) {
							nodeLabels.add(edgeWeightLabel);
							if (!pane.getChildren().contains(line)) {
								pane.getChildren().add(line);
							}
						}

					}
					// create edges for last security company
					if (counter > 1) {
						for (int i = 0; i < counter - 1; i++) {
							node1 = tempSecurityCompanyCircleArray[counter - 1];
							node2 = tempSecurityCompanyCircleArray[i];

							Double[] coordsPrev = HelperFunctions.extractCoords(securityCompanies.get(i).getLocation());
							int securityWeight = (int) AlgorithmHelperFunctions.getDistance(coordsPrev, coords);

							Line line = new Line();
							// Set the line's color and width:
							line.setStroke(Color.BLANCHEDALMOND);
							line.setStrokeWidth(5);

							line.setStartX(node1.getCenterX());
							line.setStartY(node1.getCenterY());
							line.setEndX(node2.getCenterX());
							line.setEndY(node2.getCenterY());

							Label edgeWeightLabel = new Label(Integer.toString(securityWeight));
							double centerX = (line.getStartX() + line.getEndX()) / 2;
							double centerY = (line.getStartY() + line.getEndY()) / 2;
							edgeWeightLabel.setLayoutX(centerX);
							edgeWeightLabel.setLayoutY(centerY);
							edgeWeightLabel.setTextFill(Color.RED);
							if (securityWeight < 12) {
								nodeLabels.add(edgeWeightLabel);

								if (!pane.getChildren().contains(line)) {
									pane.getChildren().add(line);
								}
							}
						}
					}
				}
			}
		}

		List<Vertex<Individual>> securityVertices = graph.getVertices().stream()
				.filter(v -> v.getValue() instanceof SecurityCompany).collect(Collectors.toList());

		for (int i = 0; i < securityVertices.size(); i++) {
			Vertex<Individual> fromVertex = securityVertices.get(i);
			for (int j = i + 1; j < securityVertices.size(); j++) {
				Vertex<Individual> toVertex = securityVertices.get(j);
				Double[] coordsFrom = HelperFunctions.extractCoords(fromVertex.getValue().getLocation());
				Double[] coordsTo = HelperFunctions.extractCoords(toVertex.getValue().getLocation());
				int securityWeight = (int) AlgorithmHelperFunctions.getDistance(coordsFrom, coordsTo);
				Edge<Individual> incidentEdge = new Edge<Individual>(securityWeight, fromVertex, toVertex);
				graph.getEdges().add(incidentEdge);
			}
		}

		// getting list of objects from community police file
		List<Object> communityPoliceObjects = HelperFunctions.readClassesFromFile(pathToReadCommunityPolice,
				CommunityPolice.class);
		List<CommunityPolice> communityPolicies = new ArrayList<>();

		// adding community police as vertices to graph
		for (Object comPopoObj : communityPoliceObjects) {
			if (comPopoObj instanceof CommunityPolice) {
				communityPolicies.add((CommunityPolice) comPopoObj);
				graph.getVertices().add(new Vertex<Individual>((CommunityPolice) comPopoObj, 3));
				Double[] coords = HelperFunctions.extractCoords(((CommunityPolice) comPopoObj).getLocation());
				Circle circle = new Circle(10, Color.ORANGE);
				Label commLabel = new Label("COMMUNITY POLICE");
				commLabel.setTextFill(Color.YELLOWGREEN);
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


		// adding the edges between community police and civilians and security
		// companies
		List<Vertex<Individual>> vertices = graph.getVertices();
		Circle communityPoliceCircle = null;
		Circle civilianCircle = null;
		for (Vertex<Individual> communityPoliceVertix : vertices) {
			if (communityPoliceVertix.getWeight() == 3) {

				Individual communityPolice = communityPoliceVertix.getValue();
				UUID communityPoliceID = communityPolice.getId();
				for (Vertex<Individual> civilianVertix : vertices) {
					Line comCivLin = new Line();
					// Set the line's color and width:
					comCivLin.setStroke(Color.GREEN);
					comCivLin.setStrokeWidth(5);
					if (civilianVertix.getWeight() == 1 || civilianVertix.getWeight() == 2) {
						Double[] communityPoliceCoords = HelperFunctions
								.extractCoords(communityPoliceVertix.getValue().getLocation());
						Double[] civCoords = HelperFunctions.extractCoords(civilianVertix.getValue().getLocation());
						int civToCom = (int) AlgorithmHelperFunctions.getDistance(communityPoliceCoords, civCoords);
						Edge<Individual> communityCivilian = new Edge<Individual>(civToCom, communityPoliceVertix,
								civilianVertix);
						graph.getEdges().add(communityCivilian);

						// now that I have both the community police ID and civilian ID I can check in
						// my UUID list for where they are and connect the lines

						Individual civilian = civilianVertix.getValue();
						if(civilian instanceof Civilian) {
							comCivLin.setStroke(Color.GREEN);
						}
						else {
							comCivLin.setStroke(Color.AQUA);
						}
						UUID civilianID = civilian.getId();
						int communityIDCounter = 0;
						for (UUID comPopoID : uuidArray) {
							if (communityPoliceID.equals(comPopoID)) {
								// System.out.println(communityIDCounter);
								communityPoliceCircle = circleArray[communityIDCounter];
								if (communityPoliceCircle != null) {
									comCivLin.setStartX(communityPoliceCircle.getCenterX());
									comCivLin.setStartY(communityPoliceCircle.getCenterY());
									break;
								}
							}
							communityIDCounter += 1;
						}

						int civIDCounter = 0;
						for (UUID civID : uuidArray) {

							if (civilianID.equals(civID)) {
								civilianCircle = circleArray[civIDCounter];
								comCivLin.setEndX(civilianCircle.getCenterX());
								comCivLin.setEndY(civilianCircle.getCenterY());
								break;
							}
							civIDCounter += 1;
						}

						Double[] vertixFromCoords = HelperFunctions
								.extractCoords(civilianVertix.getValue().getLocation());
						Double[] vertixToCoords = HelperFunctions
								.extractCoords(communityPoliceVertix.getValue().getLocation());

						int weight = (int) AlgorithmHelperFunctions.getDistance(vertixFromCoords, vertixToCoords);
						Label edgeWeightLabel = new Label(Integer.toString(weight));
						double centerX = (comCivLin.getStartX() + comCivLin.getEndX()) / 2;
						double centerY = (comCivLin.getStartY() + comCivLin.getEndY()) / 2;
						edgeWeightLabel.setLayoutX(centerX);
						edgeWeightLabel.setLayoutY(centerY);
						edgeWeightLabel.setTextFill(Color.YELLOW);
						if(weight < 12) {
						nodeLabels.add(edgeWeightLabel);

						if (!pane.getChildren().contains(comCivLin)) {
							comCivLin.toBack();

							pane.getChildren().add(comCivLin);
						}
						}

					}

				}
			}
		}

		for (Label label : nodeLabels) {
			label.setFont(Font.font("System", FontWeight.BOLD, 13));
			if (!pane.getChildren().contains(label)) {
				pane.getChildren().add(label);
			}
		}

		List<Node> nodes = new ArrayList<>(pane.getChildren());
		for (Node node : nodes) {
			if (node instanceof Circle || node instanceof Label) {
				node.toFront();
			}
		}

		graphStage.showAndWait();

	}

	public void setElementsToNothing() {
		nodeLabels.clear();
		Arrays.fill(circleArray, null);
		Arrays.fill(uuidArray, null);
	}

}
