package ui.tabs;

import javafx.scene.control.TabPane;

public class TabManager {
    private TabPane tabPane;

    public TabManager() {
    	//creating tab pan
        this.tabPane = new TabPane();
        
        //getting tabs
        HomeTab home = new HomeTab();
        DataTab data = new DataTab();
        IncidentHandleTab incidentHandle = new IncidentHandleTab();
        GraphTab graph = new GraphTab();
        tabPane.getTabs().addAll(home, data, incidentHandle, graph);
    }



    public TabPane getTabPane() {
        return tabPane;
    }

}