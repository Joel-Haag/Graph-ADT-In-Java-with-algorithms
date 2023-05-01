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
			
			List<Vertex<Individual>> securityVertices = graph.getVertices().stream()
				    .filter(v -> v.getValue() instanceof SecurityCompany)
				    .collect(Collectors.toList());
			System.out.println(securityVertices.size());
			int counterer = 1;
//
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

		// getting all incidents saved, which will be the edges between the nodes
		List<Object> incidentObjects = HelperFunctions.readClassesFromFile(pathToReadIncidents, Incident.class);
		List<Incident> incidents = new ArrayList<>();
		Double[] civilianCoords = null;
		Double[] responderCoords = null;
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
						int weight = AlgorithmHelperFunctions.getEdgeWeight(((Incident) incidentObj).getSeverity(),
								AlgorithmHelperFunctions.getDistance(civilianCoords, responderCoords));
						Edge<Individual> incidentEdge = new Edge<Individual>(weight, v1, v2);
						Line line = new Line();
						// Set the line's color and width:
						line.setStroke(Color.GREEN);
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
				} else {
					if (((Incident) incidentObj).getCommunityPolice() != null) {
						if (((Incident) incidentObj).getCivilian() != null) {
							List<Vertex<Individual>> theVertices = graph.getVertices();
							for (Vertex<Individual> vertex : theVertices) {
								if (vertex.getValue().getId().equals(((Incident) incidentObj).getCivilian().getId())) {
									v1 = vertex;
									civilianCoords = HelperFunctions
											.extractCoords(((Incident) incidentObj).getCivilian().getLocation());
								}
								if (vertex.getValue().getId()
										.equals(((Incident) incidentObj).getCommunityPolice().getId())) {
									v2 = vertex;
									responderCoords = HelperFunctions
											.extractCoords(((Incident) incidentObj).getCommunityPolice().getLocation());
								}
							}
							int weight = AlgorithmHelperFunctions.getEdgeWeight(((Incident) incidentObj).getSeverity(),
									AlgorithmHelperFunctions.getDistance(civilianCoords, responderCoords));
							Edge<Individual> incidentEdge = new Edge<Individual>(weight, v1, v2);
							Line line = new Line();
							// Set the line's color and width:
							line.setStroke(Color.GREEN);
							line.setStrokeWidth(5);
							Circle node1 = null;
							Circle node2 = null;
							for (int i = 0; i < uuidArray.length; i++) {
								UUID uuid = uuidArray[i];
								if (((Incident) incidentObj).getCivilian().getId().equals(uuid)) {
									node1 = circleArray[i];
									line.setStartX(node1.getCenterX());
									line.setStartY(node1.getCenterY());

								} else if (((Incident) incidentObj).getCommunityPolice().getId().equals(uuid)) {
									node2 = circleArray[i];
									line.setEndX(node2.getCenterX());
									line.setEndY(node2.getCenterY());

								}
							}
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
					}
				}
				if (v1 != null && v2 != null) {
					int weight = AlgorithmHelperFunctions.getEdgeWeight(((Incident) incidentObj).getSeverity(),
							AlgorithmHelperFunctions.getDistance(civilianCoords, responderCoords));
					Edge<Individual> incidentEdge = new Edge<Individual>(weight, v1, v2);
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
					if (civilianVertix.getWeight() == 1) {

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

		for (Iterator<Edge<Individual>> iterator = communityPoliceToCivilianEdges.iterator(); iterator.hasNext();) {
			Edge<Individual> edge = iterator.next();
			if (edge.getFromVertex().getValue() instanceof SecurityCompany
					|| edge.getToVertex().getValue() instanceof SecurityCompany) {
				iterator.remove();
			}
		}

		List<Vertex<Individual>> communityPoliceToCivilianVertices = new ArrayList<>(graph.getVertices());

		for (Iterator<Vertex<Individual>> iterator = communityPoliceToCivilianVertices.iterator(); iterator
				.hasNext();) {
			Vertex<Individual> vertex = iterator.next();
			if (vertex.getValue() instanceof SecurityCompany) {
				iterator.remove();
			}
		}

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

		// creating the edges between the security companies using minimal spanning tree

		List<Edge<Individual>> securityCompanyEdges = new ArrayList<>(graph.getEdges());
		int count = 1;
		for (Iterator<Edge<Individual>> securityIterator = securityCompanyEdges.iterator(); securityIterator
				.hasNext();) {
			Edge<Individual> edge = securityIterator.next();
			if (edge.getFromVertex().getValue() instanceof SecurityCompany
					&& edge.getToVertex().getValue() instanceof SecurityCompany
					&& !edge.getToVertex().getValue().getId().equals(edge.getFromVertex().getValue().getId())) {
				count++;
			} else {
				securityIterator.remove();
			}
		}
		System.out.println(securityCompanyEdges);

		List<Vertex<Individual>> securityCompanyVertices = new ArrayList<>(graph.getVertices());

		for (Iterator<Vertex<Individual>> securityIterator = securityCompanyVertices.iterator(); securityIterator
				.hasNext();) {
			Vertex<Individual> vertex = securityIterator.next();
			if (vertex.getValue() instanceof SecurityCompany) {

			} else {
				securityIterator.remove();

			}
		}

		List<Edge<Individual>> mstSecurityCompoanyGraphEdges = KruskalAlgorithm(securityCompanyEdges,
				securityCompanyVertices);
		for (Edge<Individual> edge : mstSecurityCompoanyGraphEdges) {
			Line line = new Line();
			line.setStroke(Color.BLANCHEDALMOND);
			line.setStrokeWidth(5);

			Vertex<Individual> vertexFrom = edge.getFromVertex();
			Vertex<Individual> vertexTo = edge.getToVertex();

			Double[] vertixFromCoords = HelperFunctions.extractCoords(vertexFrom.getValue().getLocation());
			Double[] vertixToCoords = HelperFunctions.extractCoords(vertexTo.getValue().getLocation());

			for (int i = 0; i < uuidArray.length; i++) {

				Circle node1 = null;
				Circle node2 = null;
				UUID uuid = uuidArray[i];
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
			edgeWeightLabel.setTextFill(Color.RED);
			nodeLabels.add(edgeWeightLabel);
			if (!pane.getChildren().contains(line)) {
				pane.getChildren().add(line);
			}
		}

		// now making the graphs edges the original
		graph.getEdges().clear();
		graph.getEdges().addAll(mstCommunityPoliceToCivilianGraphEdges);
		graph.getEdges().addAll(mstSecurityCompoanyGraphEdges);

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
		List<Edge<Individual>> mst = new ArrayList<>();
		parent = new int[vertices.size()];
		rank = new int[vertices.size()];
		for (int i = 0; i < vertices.size(); i++) {
			parent[i] = i;
			rank[i] = 0;
		}
		Collections.sort(edges);
		for (Edge<Individual> e : edges) {
			int u = find(getIndex(vertices, e.getFromVertex()));
			int v = find(getIndex(vertices, e.getToVertex()));
			if (u != v) {
				mst.add(e);
				union(u, v);
			}
		}
		return mst;

	}

	private int find(int i) {
		if (parent[i] != i) {
			parent[i] = find(parent[i]);
		}
		return parent[i];
	}

	private void union(int i, int j) {
		int root1 = find(i);
		int root2 = find(j);
		if (root1 == root2) {
			return;
		}
		if (rank[root1] > rank[root2]) {
			parent[root2] = root1;
		} else if (rank[root1] < rank[root2]) {
			parent[root1] = root2;
		} else {
			parent[root2] = root1;
			rank[root1]++;
		}
	}

	private <T> int getIndex(List<Vertex<Individual>> vertices, Vertex<Individual> v) {
		for (int i = 0; i < vertices.size(); i++) {
			if (vertices.get(i).getValue().getId().equals(v.getValue().getId())) {
				return i;
			}
		}
		return -1;
	}

}
