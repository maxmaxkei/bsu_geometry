package by.bsu.geometry.solution;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class PointAndLine extends Base {

    private final String THEORY = "Речь идёт об уравнении прямой на плоскости Ax+Bу+С=0, где A,B,C\n " +
            "— заданные целые числа, также мы имеем точку с координатами (X,Y), которые и подставляем " +
            "в уравнение. Получается, что точка лежит:\n" +
            "А) выше уравнения прямой, если Ax+Bу+С>0\n" +
            "Б) ниже прямой, если Ax+Bу+С<0\n" +
            "В) на прямой, если Ax+Bу+С=0\n" +
            "В случае, когда нам не дано изначально уравнение прямой, но даны 2 точки (X1,Y1) и (X2,Y2),\n " +
            "через которые проходит прямая, то находим уравнение прямой по формуле (Х-Х1)/(Х2-Х1) = (Y-Y1)/(Y2-Y1)";
    private final String A = "Введите число А:";
    private final String B = "Введите число B:";
    private final String C = "Введите число C:";
    private final String X = "Введите координату X:";
    private final String Y = "Введите координату Y:";
    private final String BUTTON_TEXT = "Вычислить";

    private String label;
    private FlowPane pane;
    private BorderPane parent;
    private TextField inputA = new TextField();
    private TextField inputB = new TextField();
    private TextField inputC = new TextField();
    private TextField inputX = new TextField();
    private TextField inputY = new TextField();

    public PointAndLine() {
    }

    @Override
    public FlowPane getPane(BorderPane parent) {
        this.parent = parent;
        return getPane();
    }

    public FlowPane getPane() {
        createPane();
        return pane;
    }

    @Override
    public void setLabel(String label) {
        this.label = label.replaceAll("\n", " ");
    }

    private void createPane() {
        pane = new FlowPane(Orientation.VERTICAL);
        pane.setHgap(10);
        pane.setVgap(20);
        pane.setPadding(new Insets(15, 5, 5, 15));

        Text mainHeading = new Text(label);
        mainHeading.setFont(new Font(null, 25));
        pane.getChildren().add(mainHeading);

        Text mainText = new Text(THEORY);
        mainText.setFont(new Font(null, 16));
        pane.getChildren().add(mainText);

        pane.getChildren().addAll(
                createInputPare(new Text(A), inputA),
                createInputPare(new Text(B), inputB),
                createInputPare(new Text(C), inputC),
                createInputPare(new Text(X), inputX),
                createInputPare(new Text(Y), inputY)
        );

        Button button = new Button(BUTTON_TEXT);
        button.setOnAction(actionEvent -> drawResult());
        pane.getChildren().add(button);
    }

    private int calculate() {
        return Integer.valueOf(inputA.getCharacters().toString())*
                Integer.valueOf(inputX.getCharacters().toString())+
                Integer.valueOf(inputB.getCharacters().toString())*
                Integer.valueOf(inputY.getCharacters().toString())+
                Integer.valueOf(inputC.getCharacters().toString());
    }

    private void drawResult() {
        if (pane.getChildren().size() > 8) {
            pane.getChildren().remove(9);
            pane.getChildren().remove(8);
        }
        int result = calculate();
        Text resultHeading = new Text();
        resultHeading.setFont(new Font(null, 16));
        if (result > 0) {
            resultHeading.setText("Точка лежит выше прямой");
            pane.getChildren().addAll(resultHeading, pointNotOnLine(false));
        } else if (result < 0) {
            resultHeading.setText("Точка лежит ниже прямой");
            pane.getChildren().addAll(resultHeading, pointNotOnLine(true));
        } else {
            resultHeading.setText("Точка лежит на прямой");
            pane.getChildren().addAll(resultHeading, pointOnTheLine());
        }
    }

    private FlowPane createInputPare(Text text, TextField field) {
        FlowPane pane = new FlowPane();
        pane.getChildren().addAll(text, field);
        return pane;
    }

    private FlowPane pointNotOnLine(boolean isUnderLine) {
        FlowPane pane = new FlowPane();
        Line line = new Line();
        line.setStartX(100.0f);
        line.setStartY(200.0f);
        line.setEndX(300.0f);
        line.setEndY(70.0f);
        Line point = new Line();
        point.setStartX(100.0f);
        point.setStartY(250.0f);
        point.setEndX(100.0f);
        point.setEndY(250.0f);
        point.setStrokeWidth(10);
        if (isUnderLine) {
            pane.getChildren().addAll(line, point);
        } else {
            pane.getChildren().addAll(point, line);
        }
        return pane;
    }

    private FlowPane pointOnTheLine() {
        FlowPane pane = new FlowPane();
        Line line = new Line();
        line.setStartX(100.0f);
        line.setStartY(200.0f);
        line.setEndX(300.0f);
        line.setEndY(70.0f);
        Line point = new Line();
        point.setStartX(100.0f);
        point.setStartY(250.0f);
        point.setEndX(100.0f);
        point.setEndY(250.0f);
        point.setStrokeWidth(10);
        return pane;
    }
}
