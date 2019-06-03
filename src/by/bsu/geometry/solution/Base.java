package by.bsu.geometry.solution;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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

    public Canvas getBaseCanvas() {

        Canvas canvas = new Canvas(1000, 1000);
        GraphicsContext context = canvas.getGraphicsContext2D();
        context.setFont(new Font(null, 15));
        context.setLineWidth(2);
        context.beginPath();

        context.moveTo(500,0);
        context.lineTo(500,1000);
        context.moveTo(0,500);
        context.lineTo(1000,500);

        for (int i = 0; i < 9; i++) {
            int point = i*100 + 100;
            context.moveTo(point, 497);
            context.lineTo(point, 503);
        }

        for (int i = 0; i < 9; i++) {
            int point = i*100 + 100;
            context.moveTo(497, point);
            context.lineTo(503, point);
        }

        context.closePath();
        return canvas;
    }

    public int extractInt(TextField field) {
        String value = field.getCharacters().toString();
        if (value.contains("-")) {
            value = value.replaceAll("-", "");
            return (Integer.valueOf(value) * (-1));
        }
        return Integer.valueOf(value);
    }

    public FlowPane createInputPare(Text text1, TextField field1, Text text2, TextField field2) {
        FlowPane pane = new FlowPane();
        pane.getChildren().addAll(text1, field1);
        pane.getChildren().addAll(text2, field2);
        return pane;
    }

    public FlowPane createInputPare(Text text, TextField field) {
        FlowPane pane = new FlowPane();
        pane.getChildren().addAll(text, field);
        return pane;
    }

    public int straightLineEquation(int x1, int y1, int x2, int y2, int x, int y) {
        return (y1 - y2) * x + (x2 - x1) * y + (x1 * y2 - x2 * y1);
    }
}
