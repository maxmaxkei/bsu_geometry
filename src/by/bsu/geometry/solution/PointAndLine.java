package by.bsu.geometry.solution;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PointAndLine extends Base {

    private final String THEORY = "Речь идёт об уравнении прямой на плоскости Ax+Bу+С=0, где A,B,C\n " +
            "— заданные целые числа, также мы имеем точку с координатами (X,Y),\n" +
            "которые и подставляем в уравнение. Получается, что точка лежит:\n" +
            "А) выше уравнения прямой, если Ax+Bу+С>0\n" +
            "Б) ниже прямой, если Ax+Bу+С<0\n" +
            "В) на прямой, если Ax+Bу+С=0";
    private final String A = "Введите число А: ";
    private final String B = "Введите число B: ";
    private final String C = "Введите число C: ";
    private final String X = "Введите координату X: ";
    private final String Y = "Введите координату Y: ";

    private String label;
    private FlowPane pane;
    private TextField inputA = new TextField();
    private TextField inputB = new TextField();
    private TextField inputC = new TextField();
    private TextField inputX = new TextField();
    private TextField inputY = new TextField();

    public PointAndLine() {
    }

    @Override
    public FlowPane getPane() {
        createPane();
        return pane;
    }

    @Override
    public void setLabel(String label) {
        this.label = label.replaceAll("\n", " ");
    }

    private void createPane() {
        pane = createBasePane(label, THEORY);
        pane.getChildren().addAll(
                createInputPare(new Text(A), inputA),
                createInputPare(new Text(B), inputB),
                createInputPare(new Text(C), inputC),
                createInputPare(new Text(X), inputX),
                createInputPare(new Text(Y), inputY)
        );

        Button button = getButton();
        button.setOnAction(actionEvent -> drawResult());
        pane.getChildren().add(button);
    }

    private void drawResult() {
        FlowPane resultPane = new FlowPane();
        Canvas canvas = getBaseCanvas();
        Scene resultScene = new Scene(resultPane);
        Stage resultStage = new Stage();
        GraphicsContext context = canvas.getGraphicsContext2D();

        context.strokeText(calculateString(), 50, 50);
        context.moveTo(0, 500 - calculateIntY("firstY"));
        context.lineTo(1000, 500 - calculateIntY("secondY"));

        context.fillOval(extractInt(inputX) - 3,extractInt(inputY) - 3, 6, 6);

        context.stroke();

        resultPane.getChildren().add(canvas);
        resultStage.setTitle(label);
        resultStage.setScene(resultScene);
        resultStage.show();
    }

    private String calculateString() {
        int result = extractInt(inputA) * extractInt(inputX) + extractInt(inputB) *
                        extractInt(inputY) + extractInt(inputC);

        if (result > 0) {
            return "Результат: Точка лежит выше прямой";
        } else if (result < 0) {
            return "Результат: Точка лежит ниже прямой";
        } else {
            return "Результат: Точка лежит на прямой";
        }
    }

    private int calculateIntY(String var) {
        if (var.equals("firstY")) {
            return (extractInt(inputA) * (-1) * (-500) - extractInt(inputC)) / extractInt(inputB);
        } else {
            return (extractInt(inputB) * (-1) * (500) - extractInt(inputC)) / extractInt(inputB);
        }
    }
}
