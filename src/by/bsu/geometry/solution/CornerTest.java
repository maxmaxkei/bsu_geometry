package by.bsu.geometry.solution;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CornerTest extends Base {

    private final String THEORY = "Если из точки провести векторы во все вершины многоугольника и найти сумму\n" +
            "углов между соседними векторами (с учетом знака), то для внутренней точки сумма получится\n" +
            "равной 2p, а для внешней – 0.";
    private String label;
    private FlowPane pane;

    private int topCount;
    private List<TextField> pointsX = new ArrayList<>();
    private List<TextField> pointsY = new ArrayList<>();

    private TextField pointX;
    private TextField pointY;

    int x;
    int y;

    public CornerTest() {}

    @Override
    public FlowPane getPane() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText("Введите количество вершин:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(count -> this.topCount = Integer.valueOf(count));
        createPane();
        return pane;
    }

    @Override
    public void setLabel(String label) {
        this.label = label.replaceAll("\n", " ");
    }

    private void createPane() {
        pane = createBasePane(label, THEORY);

        for (int i = 0; i < topCount; i++) {
            Text labelX = new Text("Введите X" + (i + 1) + ": ");
            Text labelY = new Text("Введите Y" + (i + 1) + ": ");
            TextField fieldX = new TextField();
            TextField fieldY = new TextField();
            pointsX.add(fieldX);
            pointsY.add(fieldY);
            pane.getChildren().add(createInputPare(labelX, fieldX, labelY, fieldY));
        }

        pointsX.add(pointsX.get(0));
        pointsY.add(pointsY.get(0));

        pointX = new TextField();
        pointY = new TextField();

        pane.getChildren().add(createInputPare(
                new Text("Введите координату точки X: "), pointX,
                new Text("Введите координату точки Y: "), pointY)
        );

        Button button = getButton();
        button.setOnAction(actionEvent -> drawResult());
        pane.getChildren().add(button);
    }

    private void drawResult() { // TODO: 3.6.19  доделать отрисовку
        FlowPane resultPane = new FlowPane();
        Canvas canvas = getBaseCanvas();
        Scene resultScene = new Scene(resultPane);
        Stage resultStage = new Stage();
        GraphicsContext context = canvas.getGraphicsContext2D();

        context.strokeText(calculateString(), 50, 50);

        for (int i = 0; i < topCount; i++) {
            context.moveTo(500 + extractInt(pointsX.get(i)), 500 - extractInt(pointsY.get(i)));
            context.lineTo(500 + extractInt(pointsX.get(i + 1)), 500 - extractInt(pointsY.get(i + 1)));
        }

        context.stroke();

        resultPane.getChildren().add(canvas);
        resultStage.setTitle(label);
        resultStage.setScene(resultScene);
        resultStage.show();
    }

    private String calculateString() {
        double ans = 0;
        for (int i = 0; i < topCount; i++) {
            int OAx = extractInt(pointsX.get(i)) - x;
            int OAy = extractInt(pointsY.get(i)) - y;
            int OBx = extractInt(pointsX.get(i + 1)) - x;
            int OBy = extractInt(pointsY.get(i + 1)) - y;
            int OAOB = OAx * OBx + OAy * OBy;

            double mOA = Math.pow(Math.pow(OAx, 2) + Math.pow(OAy, 2), 0.5);
            double mOB = Math.pow(Math.pow(OBx, 2) + Math.pow(OBy, 2), 0.5);
            double answer = Math.acos(OAOB / (mOA * mOB));

            if (Double.isNaN(answer) || Math.toDegrees(answer) == 180.0) {
                System.out.println("Точка лежит на многоугольнике");
                break;
            } else {
                ans = Math.toDegrees(answer) + ans;
                if (i == topCount - 1) {
                    if (ans > 355 && ans < 365) {
                        return "Точка внутри многоугольника";
                    } else {
                        return "Точка снаружи многоугольника";
                    }
                }
            }
        }
        return "";
    }
}
