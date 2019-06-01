package by.bsu.geometry.solution;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;

public abstract class Base {
    public abstract FlowPane getPane(BorderPane parent);
    public abstract void setLabel(String label);
}
