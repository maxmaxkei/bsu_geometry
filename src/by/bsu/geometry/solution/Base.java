package by.bsu.geometry.solution;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public abstract class Base {

    private final String BUTTON_TEXT = "Вычислить";

    public abstract FlowPane getPane();
    public abstract void setLabel(String label);

    public FlowPane createBasePane(String label, String theory) {
        FlowPane pane = new FlowPane(Orientation.VERTICAL);
        pane.setHgap(10);
        pane.setVgap(20);
        pane.setPadding(new Insets(15, 5, 5, 15));

        Text mainHeading = new Text(label);
        mainHeading.setFont(new Font(null, 25));
        pane.getChildren().add(mainHeading);

        Text mainText = new Text(theory);
        mainText.setFont(new Font(null, 16));
        pane.getChildren().add(mainText);

        return pane;
    }

    public Button getButton() {
        return new Button(BUTTON_TEXT);
    }
}
