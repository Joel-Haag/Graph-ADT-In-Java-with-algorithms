import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ui.tabs.TabManager;

/**
 * The main class, this is where the javafx application is launched and runs
 * tabs
 * 
 * @author Joel Orryn Haag, 220149181 <joelohaag@gmail.com>
 */
public class Main extends Application {

	/**
	 * setting up the javafx application with start function
	 * 
	 * @param primaryStage the primaryStage of the javafx application
	 */
	@Override
	public void start(Stage primaryStage) {
		// setting the title of application
		primaryStage.setTitle("Graph Project");

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

	/**
	 * Running the javafx application with void main function
	 * 
	 * @param args the args which will be launched to run the javafx application
	 */
	public static void main(String[] args) {
		// invoking main method from JVM
		launch(args);
	}
}
