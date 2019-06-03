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

public class RayTest extends Base {

    private final String THEORY = "Также известен как метод трассировки лучом.\n" +
            "Если в 2 словах, то любой луч, проведенный из внутренней точки,\nбудет иметь нечетное " +
            "число пересечений с границами полигона, а из внешней – четное. ";

    private String label;
    private FlowPane pane;

    private int topCount;
    private List<TextField> pointsX = new ArrayList<>();
    private List<TextField> pointsY = new ArrayList<>();
    private TextField pointX1;
    private TextField pointY1;
    private TextField pointX2;
    private TextField pointY2;

    int x1;
    int y1;
    int x2;
    int y2;
    int maxX;
    int maxY;
    int minX;
    int minY;


    public RayTest() {}

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

        pointX1 = new TextField();
        pointY1 = new TextField();
        pointX2 = new TextField();
        pointY2 = new TextField();

        pane.getChildren().addAll(createInputPare(
                new Text("Введите координату луча X1: "), pointX1,
                new Text("Введите координату луча Y1: "), pointY1),
                                  createInputPare(
                new Text("Введите координату луча X2: "), pointX2,
                new Text("Введите координату луча Y2: "), pointY2
        ));

        Button button = getButton();
        button.setOnAction(actionEvent -> drawResult());
        pane.getChildren().add(button);
    }

    private void drawResult() {
        getLineCoordinates();
        getMaxCoordinates();
        int delX = x2 - x1;
        int delY = y2 - y1;

        do {
            if (x2 > x1 && x2 <= maxX) {
                x2 = x2 + delX; y2 = y2 + delY;
            } else if (x2 > x1 && x2 > maxX) {
                break;
            } else if (x2 < x1 && x2 >= minX) {
                x2 = x2 + delX; y2 = y2 + delY;
            } else if (x2 < x1 && x2 < minX) {
                break;
            } if (y2 > y1 && y2 <= maxY) {
                x2 = x2 + delX; y2 = y2 + delY;
            } else if (y2 > y1 && y2 > maxY) {
                break;
            } else if (y2 < y1 && y2 >= minY) {
                x2 = x2 + delX; y2 = y2 + delY;
            } else if (y2 < y1 && y2 < minY) {
                break;
            } else {
                break;
            }
        } while (true);

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

        context.moveTo(500 + extractInt(pointX1), 500 - extractInt(pointY1));
        context.lineTo(500 + extractInt(pointX2), 500 - extractInt(pointY2));
        context.moveTo(500 + extractInt(pointX2), 500 - extractInt(pointY2));
        context.lineTo(500 + extractInt(pointX2) - 5, 500 - extractInt(pointY2) + 5);
        context.moveTo(500 + extractInt(pointX2), 500 - extractInt(pointY2));
        context.lineTo(500 + extractInt(pointX2) - 5, 500 - extractInt(pointY2) - 5);
        context.fillOval(500 + extractInt(pointX1) - 3,500 - extractInt(pointY1) - 3, 6, 6);
        context.fillOval(500 + extractInt(pointX2) - 3,500 - extractInt(pointY2) - 3, 6, 6);

        context.stroke();

        resultPane.getChildren().add(canvas);
        resultStage.setTitle(label);
        resultStage.setScene(resultScene);
        resultStage.show();
    }

    private String calculateString() {
        for (int i = 0, j = 0; i < topCount; i++) {
            int Z1 = straightLineEquation(extractInt(pointsX.get(i)), extractInt(pointsY.get(i)),
                    extractInt(pointsX.get(i + 1)), extractInt(pointsY.get(i + 1)), x1, y1);
            int Z2 = straightLineEquation(extractInt(pointsX.get(i)), extractInt(pointsY.get(i))
                    , extractInt(pointsX.get(i + 1)), extractInt(pointsY.get(i + 1)), x2, y2);
            int Z3 = straightLineEquation(x1, y1, x2, y2, extractInt(pointsX.get(i)), extractInt(pointsY.get(i)));
            int Z4 = straightLineEquation(x1, y1, x2, y2, extractInt(pointsX.get(i + 1)), extractInt(pointsY.get(i + 1)));
            if (Z1 * Z2 <= 0 && Z3 * Z4 <= 0) {
                j++;
            }
            if (i == topCount - 1) {
                if (j % 2 == 0) {
                    return "Результат: Точка снаружи многоугольника";
                } else {
                    return "Результат: Точка внутри многоугольника";
                }
            }
        }
        return "";
    }

    private void getMaxCoordinates() {
        maxX = pointsX.stream().map(this::extractInt).max(Integer::compareTo).get();
        maxY = pointsY.stream().map(this::extractInt).max(Integer::compareTo).get();
        minX = pointsX.stream().map(this::extractInt).max(Integer::compareTo).get();
        minY = pointsY.stream().map(this::extractInt).max(Integer::compareTo).get();
    }

    private void getLineCoordinates() {
        x1 = extractInt(pointX1);
        x2 = extractInt(pointX2);
        y1 = extractInt(pointY1);
        y2 = extractInt(pointY2);
    }
}
