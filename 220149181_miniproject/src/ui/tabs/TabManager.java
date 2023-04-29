package ui.tabs;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Tab;
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
        
        tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> observable, Tab oldTab, Tab newTab) {
                if (newTab instanceof IncidentHandleTab) {
                    IncidentHandleTab incidentHandleTab = (IncidentHandleTab) newTab;
                    incidentHandleTab.refresh();
                }
            }
        });
        
    }



    public TabPane getTabPane() {
        return tabPane;
    }

}