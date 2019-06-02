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

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PointAndPolygon extends Base {

    private final String THEORY = "Выпуклым называется многоугольник, для которого выполнено требование,\n" +
            "что для любой прямой, проведенной через сторону многоугольника все вершины многоугольника лежат\n" +
            "в одной полуплоскости. Для решения нашей задачи будет использоваться положение искомой точки относительно\n" +
            "прямых, прямые были образованы путем последовательного соединения вершин. (В задаче 1 такой подход)\n" +
            "Берем уравнение прямой (Х-Х1)/(Х2-Х1) = (Y-Y1)/(Y2-Y1), где Y и X – координаты тестируемой точки,\n" +
            "а Х1 Y1 и Х2 Y2 это начало и конец нашего отрезка (прямой) относительно которого тестируем точку.\n" +
            "Если (Х2-Х1) * (Y-Y1) - (Y-Y1) * ( Х-Х1) > 0, то точка выше (левее в нашем случае) прямой, значит все ок.\n" +
            "Если точка относительно всех сторон левее, то точка внутри. Если хоть один раз не левее, то снаружи";

    private String label;
    private FlowPane pane;

    private int topCount;
    private Map<String, TextField> points = new HashMap<>();

    public PointAndPolygon() {}

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
            points.put(("X" + (i + 1)), fieldX);
            points.put(("Y" + (i + 1)), fieldY);
            pane.getChildren().add(createInputPare(labelX, fieldX, labelY, fieldY));
        }

        TextField pointX = new TextField();
        TextField pointY = new TextField();

        points.put("X", pointX);
        points.put("Y", pointY);

        pane.getChildren().add(createInputPare(
           new Text("Введите X: "), pointX,
           new Text("Введите Y: "), pointY
        ));

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

        for (int i = 0; i < topCount; i++) {
            if (i == topCount - 1) {
                context.lineTo(500 - extractInt(points.get("X1")), 500 - extractInt(points.get("Y1")));
                break;
            }
            String pointX = "X";
            String pointY = "Y";
            context.moveTo(500 + extractInt(points.get(pointX + (i + 1))), 500 - extractInt(points.get(pointY + (i + 1))));
            context.lineTo(500 + extractInt(points.get(pointX + (i + 2))), 500 - extractInt(points.get(pointY + (i + 2))));
        }

        context.fillOval(500 + extractInt(points.get("X")) - 3,500 - extractInt(points.get("Y")) - 3, 6, 6);

        context.stroke();

        resultPane.getChildren().add(canvas);
        resultStage.setTitle(label);
        resultStage.setScene(resultScene);
        resultStage.show();
    }

    private String calculateString() {
        for (int i = 0; i < topCount; i++) {
            if (i == topCount -1) {
                return "Заданная точка входит в заданный многоугольник";
            }
            int result = ((500 + extractInt(points.get("X"))) - (500 + extractInt(points.get("X" + (i + 1))))) *
                    ((500 - extractInt(points.get("Y" + (i + 2)))) - (500 - extractInt(points.get("Y" + (i + 1))))) -
                    ((500 - extractInt(points.get("Y"))) - (500 - extractInt(points.get("Y" + (i + 1))))) *
                    ((500 + extractInt(points.get("X" + (i + 2)))) - (500 + extractInt(points.get("X" + (i + 1)))));

            if (result < 0) {
                return "Заданная точка не входит в заданный многоугольник";
            }
        }
        return "";
    }
}
