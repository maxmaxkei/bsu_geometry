package by.bsu.geometry.solution;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

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
    private final String compareStatement = "%s*%S + %s*%s + %s = 0   ->   %s = 0";

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

    private int calculate() {
        return Integer.valueOf(inputA.getCharacters().toString())*
                Integer.valueOf(inputX.getCharacters().toString())+
                Integer.valueOf(inputB.getCharacters().toString())*
                Integer.valueOf(inputY.getCharacters().toString())+
                Integer.valueOf(inputC.getCharacters().toString());
    }

    private void drawResult() {
        if (pane.getChildren().size() > 8) {
            pane.getChildren().remove(10);
            pane.getChildren().remove(9);
            pane.getChildren().remove(8);
        }
        int result = calculate();
        Text resultHeading = new Text();
        Text finalCompareStatement = new Text();
        finalCompareStatement.setFont(new Font(null, 12));
        resultHeading.setFont(new Font(null, 16));
        finalCompareStatement.setText(String.format(compareStatement,
                inputA.getCharacters().toString(),
                inputX.getCharacters().toString(),
                inputB.getCharacters().toString(),
                inputY.getCharacters().toString(),
                inputC.getCharacters().toString(),
                result));
        pane.getChildren().add(finalCompareStatement);
        if (result > 0) {
            resultHeading.setText("Результат: Точка лежит выше прямой");
            pane.getChildren().addAll(resultHeading, pointNotOnLine(false));
        } else if (result < 0) {
            resultHeading.setText("Результат: Точка лежит ниже прямой");
            pane.getChildren().addAll(resultHeading, pointNotOnLine(true));
        } else {
            resultHeading.setText("Результат: Точка лежит на прямой");
            pane.getChildren().addAll(resultHeading, pointOnTheLine());
        }
    }

    private FlowPane createInputPare(Text text, TextField field) {
        FlowPane pane = new FlowPane();
        pane.getChildren().addAll(text, field);
        return pane;
    }

    private FlowPane pointNotOnLine(boolean isUnderLine) {
        Canvas canvas = new Canvas(300,300);
        FlowPane pane = new FlowPane();
        GraphicsContext context = canvas.getGraphicsContext2D();
        context.setLineWidth(4.0);
        context.beginPath();
        context.moveTo(50,200);
        context.lineTo(270, 50);
        if (isUnderLine) {
            context.fillOval(200,170,7,7);
        } else {
            context.fillOval(100,100,7,7);
        }
        context.closePath();
        context.stroke();
        pane.getChildren().add(canvas);
        return pane;
    }

    private FlowPane pointOnTheLine() {
        Canvas canvas = new Canvas(300,301);
        FlowPane pane = new FlowPane();
        GraphicsContext context = canvas.getGraphicsContext2D();
        context.setLineWidth(4.0);
        context.beginPath();
        context.moveTo(50,250);
        context.lineTo(250, 50);
        context.fillOval(145,145,10,10);
        context.closePath();
        context.stroke();
        pane.getChildren().add(canvas);
        return pane;
    }
}
