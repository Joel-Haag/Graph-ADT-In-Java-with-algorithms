package ui.buttons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
import nodes.algorithms.AlgorithmHelperFunctions;
import ui.helper.HelperFunctions;

public class ShortestPathGraphButton extends Button {

	private String pathToReadCivilian = "./data/Civilians.binary";
	private String pathToReadSecurityCompany = "./data/SecurityCompany.binary";
	private String pathToReadCommunityPolice = "./data/CommunityPolice.binary";
	private Circle[] circleArray = new Circle[50];
	private UUID[] uuidArray = new UUID[50];
	private Integer row = 0;
	private ArrayList<Label> nodeLabels = new ArrayList<>();

	public ShortestPathGraphButton(String Graph) {
		super(Graph);
		this.setStyle("-fx-font-size: 16pt;"); // set a custom style

	}

	public void createGraph(Incident incident) {
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

				circleArray[row] = circle;
				uuidArray[row] = ((SecurityCompany) secObj).getId();
				row += 1;

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
				if (securityWeight < 10) {
					Edge<Individual> incidentEdge = new Edge<Individual>(securityWeight, fromVertex, toVertex);
					graph.getEdges().add(incidentEdge);
					fromVertex.addEdge(incidentEdge);
					toVertex.addEdge(incidentEdge);
				}

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

		for (Vertex<Individual> communityPoliceVertix : vertices) {
			if (communityPoliceVertix.getWeight() == 3) {

				for (Vertex<Individual> civilianVertix : vertices) {

					// Set the line's color and width:

					if (civilianVertix.getWeight() == 1 || civilianVertix.getWeight() == 2) {
						Double[] communityPoliceCoords = HelperFunctions
								.extractCoords(communityPoliceVertix.getValue().getLocation());
						Double[] civCoords = HelperFunctions.extractCoords(civilianVertix.getValue().getLocation());
						int civToCom = (int) AlgorithmHelperFunctions.getDistance(communityPoliceCoords, civCoords);
						if (civToCom < 7) {
							Edge<Individual> communityCivilian = new Edge<Individual>(civToCom, communityPoliceVertix,
									civilianVertix);
							graph.getEdges().add(communityCivilian);
							communityPoliceVertix.addEdge(communityCivilian);
							civilianVertix.addEdge(communityCivilian);
						}

					}

				}
			}
		}

		List<Node> nodes = new ArrayList<>(pane.getChildren());
		for (Node node : nodes) {
			if (node instanceof Circle || node instanceof Label) {
				node.toFront();
			}
		}

		Vertex<Individual> securityCompany = null;

		for (int i = 0; i < uuidArray.length; i++) {
			if (uuidArray[i] != null) {
				if (uuidArray[i].equals(incident.getSecurityCompany().getId())) {
					securityCompany = graph.getVertices().get(i);
				}
			}
		}

		Vertex<Individual> civilian = null;

		for (int i = 0; i < uuidArray.length; i++) {
			if (uuidArray[i] != null) {
				if (uuidArray[i].equals(incident.getCivilian().getId())) {
					civilian = graph.getVertices().get(i);
				}
			}
		}

		List<Vertex<Individual>> path = dijkstraShortestPath(graph, securityCompany, civilian);
		Vertex<Individual> vertexFrom = null;
		Vertex<Individual> vertexTo = null;
		for (Vertex<Individual> ver : path) {

			Line line = new Line();
			line.setStroke(Color.GREEN);
			line.setStrokeWidth(5);
			vertexFrom = ver;

			if (vertexTo != null) {
				Double[] vertixFromCoords = HelperFunctions.extractCoords(vertexFrom.getValue().getLocation());
				Double[] vertixToCoords = HelperFunctions.extractCoords(vertexTo.getValue().getLocation());

				for (int i = 0; i < uuidArray.length; i++) {
					Circle node1 = null;
					Circle node2 = null;
					UUID uuid = uuidArray[i];
					if (vertexFrom.getValue() instanceof Civilian) {
						if (((Civilian) vertexFrom.getValue()).getId().equals(uuid)) {
							node1 = circleArray[i];
							line.setStartX(node1.getCenterX());
							line.setStartY(node1.getCenterY());
						}
					}
					if (vertexTo.getValue() instanceof Civilian) {
						if (((Civilian) vertexTo.getValue()).getId().equals(uuid)) {
							node2 = circleArray[i];
							line.setEndX(node2.getCenterX());
							line.setEndY(node2.getCenterY());
						}
					}
					if (vertexFrom.getValue() instanceof CommunityPolice) {
						if (((CommunityPolice) vertexFrom.getValue()).getId().equals(uuid)) {
							node1 = circleArray[i];
							line.setStartX(node1.getCenterX());
							line.setStartY(node1.getCenterY());
						}
					}
					if (vertexTo.getValue() instanceof CommunityPolice) {
						if (((CommunityPolice) vertexTo.getValue()).getId().equals(uuid)) {
							node2 = circleArray[i];
							line.setEndX(node2.getCenterX());
							line.setEndY(node2.getCenterY());
						}
					}
					if (vertexFrom.getValue() instanceof SecurityCompany) {
						if (((SecurityCompany) vertexFrom.getValue()).getId().equals(uuid)) {
							node1 = circleArray[i];
							line.setStartX(node1.getCenterX());
							line.setStartY(node1.getCenterY());
						}
					}
					if (vertexTo.getValue() instanceof SecurityCompany) {

						if (((SecurityCompany) vertexTo.getValue()).getId().equals(uuid)) {
							node2 = circleArray[i];
							line.setEndX(node2.getCenterX());
							line.setEndY(node2.getCenterY());
						}
					}
				}

				int weight = (int) AlgorithmHelperFunctions.getDistance(vertixFromCoords, vertixToCoords);
				Label edgeWeightLabel = new Label(Integer.toString(weight));
				double centerX = (line.getStartX() + line.getEndX()) / 2;
				double centerY = (line.getStartY() + line.getEndY()) / 2;
				edgeWeightLabel.setLayoutX(centerX);
				edgeWeightLabel.setLayoutY(centerY);
				edgeWeightLabel.setTextFill(Color.YELLOW);
				nodeLabels.add(edgeWeightLabel);

				if (!pane.getChildren().contains(line)) {
					pane.getChildren().add(line);
				}
			}
			vertexTo = ver;

		}

		for (Label label : nodeLabels) {
			label.setFont(Font.font("System", FontWeight.BOLD, 13));
			if (!pane.getChildren().contains(label)) {
				pane.getChildren().add(label);
			}
		}

		graphStage.showAndWait();

	}

	public void setElementsToNothing() {
		nodeLabels.clear();
		Arrays.fill(circleArray, null);
		Arrays.fill(uuidArray, null);
	}

	public List<Vertex<Individual>> dijkstraShortestPath(Graph<Individual> graph, Vertex<Individual> source,
			Vertex<Individual> target) {
		Map<Vertex<Individual>, Vertex<Individual>> prevMap = new HashMap<>();
		Map<Vertex<Individual>, Double> distMap = new HashMap<>();
		Set<Vertex<Individual>> unvisitedNodes = new HashSet<>();

		for (Vertex<Individual> v : graph.getVertices()) {
			if (v.getValue() instanceof Civilian && !v.getValue().getId().equals(target.getValue().getId())) {

			} else {
				distMap.put(v, Double.POSITIVE_INFINITY);
				unvisitedNodes.add(v);
			}
		}

		distMap.put(source, 0.0);

		while (!unvisitedNodes.isEmpty()) {
			Vertex<Individual> current = unvisitedNodes.stream().min(Comparator.comparingDouble(distMap::get)).get();
			unvisitedNodes.remove(current);

			if (current.equals(target)) {
				break;
			}

			for (Edge<Individual> edge : current.getEdges()) {
				// System.out.println("3 looping through edges" + edge);
				Vertex<Individual> neighbor;
				if (edge.getFromVertex().equals(current)) {
					neighbor = edge.getToVertex();
				} else {
					neighbor = edge.getFromVertex();
				}

				// Continue only if the neighbor has not been visited yet
				if (!unvisitedNodes.contains(neighbor)) {
					continue;
				}

				// Calculate the cost of traveling from the current vertex to the neighbor
				System.out.println(current);
				double alt = distMap.get(current) + edge.getCost();

				// Update the distance and predecessor maps if the cost is lower than the
				// current best
				if (alt < distMap.get(neighbor)) {
					distMap.put(neighbor, alt);
					prevMap.put(neighbor, current);
				}
			}

		}

		List<Vertex<Individual>> path = new ArrayList<>();
		Vertex<Individual> current = target;
		while (prevMap.containsKey(current)) {
			path.add(current);
			current = prevMap.get(current);
		}
		path.add(current);
		// path = Lists.reverse(path);
		return path;
	}

}
