import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ui.tabs.TabManager;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		// setting the title of application
		primaryStage.setTitle("Adding Content to Tabs");

		// Create a TabPane
		TabManager tabManager = new TabManager();
		TabPane tabPane = tabManager.getTabPane();


		// creating VBox for adding tab pane
		VBox vBox = new VBox(tabPane);
		// adding scroll pane to the scene
		Scene scene = new Scene(vBox, 500, 500);
		primaryStage.setScene(scene);
		// showing the output
		primaryStage.show();
	}

	public static void main(String[] args) {
		// invoking main method from JVM
		launch(args);
	}
}
