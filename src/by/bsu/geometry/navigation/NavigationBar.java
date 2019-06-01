package by.bsu.geometry.navigation;

import by.bsu.geometry.Constants;
import by.bsu.geometry.navigation.items.NavigationItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.BorderPane;

import java.util.Arrays;
import java.util.List;

public class NavigationBar {

    private List<NavigationItem> items;
    private BorderPane parent;

    public NavigationBar(BorderPane parent) {
        this.parent = parent;
        items = Arrays.asList(
            new NavigationItem(Constants.POINT_AND_LINE),
            new NavigationItem(Constants.LINES_POSITION),
            new NavigationItem(Constants.POINT_AND_POLYGON),
            new NavigationItem(Constants.RAY_TEST),
            new NavigationItem(Constants.CORNER_TEST),
            new NavigationItem(Constants.GRAHEM_METHOD),
            new NavigationItem(Constants.JARVICE_METHOD),
            new NavigationItem(Constants.QUICK_HULL)
        );
    }

    public ListView<NavigationItem> createNavigationBar() {
        ObservableList<NavigationItem> list = FXCollections.observableArrayList(items);
        ListView<NavigationItem> stringList = new ListView<>(list);
        stringList.getSelectionModel().selectedItemProperty()
                .addListener((observableValue, navigationItem, t1) -> handleEvent(navigationItem));
        stringList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        stringList.setMaxHeight(310);
        stringList.setMinWidth(310);
        return stringList;
    }

    private void handleEvent(NavigationItem navigationItem) {

    }
}
