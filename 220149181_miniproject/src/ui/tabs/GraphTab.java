package ui.tabs;

import java.util.ArrayList;
import java.util.List;

import com.jwetherell.algorithms.data_structures.Graph;
import com.jwetherell.algorithms.data_structures.Graph.Edge;
import com.jwetherell.algorithms.data_structures.Graph.Vertex;

import javafx.scene.control.Tab;
import nodes.Civilian;
import ui.helper.HelperFunctions;

public class GraphTab extends Tab {
	private String pathToReadCivilian = "./data/Civilians.binary";
	private String pathToReadSecurityCompany = "./data/SecurityCompany.binary";
	private String pathToReadCommunityPolice = "./data/CommunityPolice.binary";
public GraphTab() {
	setText("Graph");
	//creating the graph
//	Graph<Civilian> graph = new Graph<Civilian>();
//	// getting list of objects from civilian file
//	List<Object> civilianObjects = HelperFunctions.readClassesFromFile(pathToReadCivilian);
//	List<Civilian> civilians = new ArrayList<>();

//	for (Object civObj : civilianObjects) {
//		if (civObj instanceof Civilian) {
//			civilians.add((Civilian) civObj);
//			graph.getVertices().add(new Vertex<Civilian>((Civilian) civObj));
//			
//		}
//	}
	
	Graph<String> graph = new Graph<String>();
	Vertex<String> v1 = new Vertex<String>("A", 1);
	Vertex<String> v2 = new Vertex<String>("B", 2);
	Vertex<String> v3 = new Vertex<String>("C", 3);
	
	Edge<String> e1 = new Edge<String>(5, v1, v2);
	Edge<String> e2 = new Edge<String>(6, v2, v3);
	
	graph.getVertices().add(v1);
	graph.getVertices().add(v2);
	graph.getVertices().add(v3);
	graph.getEdges().add(e1);
	graph.getEdges().add(e2);
	
	System.out.println(e1.toString());
	

}

}
