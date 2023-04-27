package ui.tabs;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.jwetherell.algorithms.data_structures.Graph;
import com.jwetherell.algorithms.data_structures.Graph.Edge;
import com.jwetherell.algorithms.data_structures.Graph.Vertex;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import nodes.Civilian;
import nodes.CommunityPolice;
import nodes.Incident;
import nodes.Individual;
import nodes.SecurityCompany;
import ui.buttons.GraphButton;
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
	private ArrayList<Label> nodeLabels = new ArrayList<>();

	public GraphTab() {
		setText("Graph");
		setClosable(false);
		VBox graphTabContentVbox = new VBox();
		Label showInitialGraphLabel = new Label("View Graph made from inputed data");
		GraphButton showGraphButton = new GraphButton("View");
		
		
		Label showRecommendedGraphLabel = new Label("View Reccomended graph to organise data");
		
		graphTabContentVbox.getChildren().addAll(showInitialGraphLabel, showGraphButton, showRecommendedGraphLabel);
		graphTabContentVbox.setAlignment(Pos.TOP_CENTER);
		graphTabContentVbox.setSpacing(20);
		graphTabContentVbox.setPadding( new Insets(20));
		
		showGraphButton.setOnAction(e -> showGraphButton.createGraph()); // add an action to the button



		setContent(graphTabContentVbox);

	}

}
