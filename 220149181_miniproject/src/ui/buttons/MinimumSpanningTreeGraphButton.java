package ui.buttons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
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

public class MinimumSpanningTreeGraphButton extends Button {
	private String pathToReadCivilian = "./data/Civilians.binary";
	private String pathToReadSecurityCompany = "./data/SecurityCompany.binary";
	private String pathToReadCommunityPolice = "./data/CommunityPolice.binary";
	private String pathToReadIncidents = "./data/Incident.binary";
	private Circle[] circleArray = new Circle[50];
	private UUID[] uuidArray = new UUID[50];
	private Integer row = 0;
	private ArrayList<Label> nodeLabels = new ArrayList<>();
	private int[] parent;
	private int[] rank;

	public MinimumSpanningTreeGraphButton(String Graph) {
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
			}
			System.out.println(graph.getVertices().stream());

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

		for (Label label : nodeLabels) {
			label.setFont(Font.font("System", FontWeight.BOLD, 13));
			if (!pane.getChildren().contains(label)) {
				pane.getChildren().add(label);
			}
		}

		// adding the edges between community police and civilians
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

					}

				}
			}
		}

		// creating the edges for community police and civilian using minimal spanning
		// tree

		List<Edge<Individual>> communityPoliceToCivilianEdges = new ArrayList<>(graph.getEdges());

		List<Vertex<Individual>> communityPoliceToCivilianVertices = new ArrayList<>(graph.getVertices());

		List<Edge<Individual>> mstCommunityPoliceToCivilianGraphEdges = KruskalAlgorithm(communityPoliceToCivilianEdges,
				communityPoliceToCivilianVertices);

		for (Edge<Individual> edge : mstCommunityPoliceToCivilianGraphEdges) {
			Line line = new Line();
			line.setStroke(Color.GREEN);
			line.setStrokeWidth(5);

			Vertex<Individual> vertexFrom = edge.getFromVertex();
			Vertex<Individual> vertexTo = edge.getToVertex();

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

	// function to return edges of minimal spanning tree using kruskal algorithm
	private List<Edge<Individual>> KruskalAlgorithm(List<Edge<Individual>> edges, List<Vertex<Individual>> vertices) {
		//creating a new List of edges which will be the minimal spanning tree edges
		List<Edge<Individual>> minimalSpanningTreeEdges = new ArrayList<>();
		//an array which will keep track of the parent in each vertex
		parent = new int[vertices.size()];
		//an array which keeps track of every rank of every vertex
		rank = new int[vertices.size()];
		
		//making the parent of every vertex itself
		for (int i = 0; i < vertices.size(); i++) {
			parent[i] = i;
			rank[i] = 0;
		}
		//using Collections.sort to sort edges in ascending order
		Collections.sort(edges);
		//looping over the edges
		for (Edge<Individual> edge : edges) {
			//get the parent of the source / dest verts on current iteration edge
			int firstIndex = findParent(getIndex(vertices, edge.getFromVertex()));
			int secondIndex = findParent(getIndex(vertices, edge.getToVertex()));
			//two different parents = different tree, so merge trees
			if (firstIndex != secondIndex) {
				minimalSpanningTreeEdges.add(edge);
				//merging trees, root of smaller tree becomes root of larger tree
				mergeTrees(firstIndex, secondIndex);
			}
		}
	
		return minimalSpanningTreeEdges;

	}

	//helper function that gets the parent of a vertex
	private int findParent(int index) {
		//making sure parent of vertex isn't itself
		if (parent[index] != index) {
			//Recursively look for vertex parent and make the parent the root
			parent[index] = findParent(parent[index]);
		}
		return parent[index];
	}

	//helper function that merges the trees according to the larger rank
	private void mergeTrees(int indexOne, int indexTwo) {
		//getting root of both trees
		int firstNode = findParent(indexOne);
		int secondNode = findParent(indexTwo);
		//checking if the trees are already merges (same root)
		if (firstNode == secondNode) {
			return;
		}
		//make root of smaller tree a child of the larger trees root
		if (rank[firstNode] > rank[secondNode]) {
			parent[secondNode] = firstNode;
		} else if (rank[firstNode] < rank[secondNode]) {
			parent[firstNode] = secondNode;
		} else {
			parent[secondNode] = firstNode;
			rank[firstNode]++;
		}
	}

	//helper method that  finds the index of a certain vertex in the list of vertices
	private <T> int getIndex(List<Vertex<Individual>> vertices, Vertex<Individual> v) {
		//looping through vertices
		for (int i = 0; i < vertices.size(); i++) {
			//checking if they equal by their id
			if (vertices.get(i).getValue().getId().equals(v.getValue().getId())) {
				return i;
			}
		}
		return -1;
	}

}
