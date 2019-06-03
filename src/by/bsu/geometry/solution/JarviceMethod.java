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
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class JarviceMethod extends Base {

    private final String THEORY = "Также известен как метод трассировки лучом.\n" +
            "Если в 2 словах, то любой луч, проведенный из внутренней точки,\nбудет иметь нечетное " +
            "число пересечений с границами полигона, а из внешней – четное.";
    private String label;
    private FlowPane pane;

    private int pointCount;
    private List<TextField> pointsX = new ArrayList<>();
    private List<TextField> pointsY = new ArrayList<>();

    public JarviceMethod() {
    }

    @Override
    public FlowPane getPane() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText("Укажите количество точек на плоскости:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(count -> this.pointCount = Integer.valueOf(count));
        createPane();
        return pane;
    }

    @Override
    public void setLabel(String label) {
        this.label = label.replaceAll("\n", " ");
    }

    private void createPane() {
        pane = createBasePane(label, THEORY);

        for (int i = 0; i < pointCount; i++) {
            Text labelX = new Text("Введите X" + (i + 1) + ": ");
            Text labelY = new Text("Введите Y" + (i + 1) + ": ");
            TextField fieldX = new TextField();
            TextField fieldY = new TextField();
            pointsX.add(fieldX);
            pointsY.add(fieldY);
            pane.getChildren().add(createInputPare(labelX, fieldX, labelY, fieldY));
        }

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

        context.stroke();

        resultPane.getChildren().add(canvas);
        resultStage.setTitle(label);
        resultStage.setScene(resultScene);
        resultStage.show();
    }

    private String calculateString() {
        int minY = pointsY.stream().map(this::extractInt).min(Integer::compareTo).get();
        int minX = pointsX.stream().map(this::extractInt).min(Integer::compareTo).get();
        LinkedList<Integer> NewListX = new LinkedList<>();
        LinkedList<Integer> NewListY = new LinkedList<>();
        List<Integer> ListXtmp = new ArrayList<>();
        List<Integer> ListYtmp = new ArrayList<>();

        for (int i = 0; i < pointsX.size(); i++) {
            if (extractInt(pointsX.get(i)) == minX) {
                ListXtmp.add(extractInt(pointsX.get(i)));
                ListYtmp.add(extractInt(pointsY.get(i)));
            }
        }
        NewListX.add(minX);
        NewListY.add(minY);
        int tmp = 0;
        int timeToBreak = 0;
        NewListX.add(extractInt(pointsX.get(0)));
        NewListY.add(extractInt(pointsY.get(0)));
        for (int j = 0; j < pointsX.size() ; j++) {
            for (int i = 0; i < pointsX.size(); i++) {
                if ( (extractInt(pointsY.get(i)) - NewListY.get(NewListY.size()-2)) *
                        (NewListX.get(NewListY.size()-1) - NewListX.get(NewListY.size()-2)) -
                        (extractInt(pointsX.get(i)) - NewListX.get(NewListY.size()-2)) *
                        (NewListY.get(NewListY.size()-1) - NewListY.get(NewListY.size()-2)) <= 0 ) {
                    tmp++;
                }
                if (i == (pointsX.size() - 1) & tmp > 2) {
                    NewListX.removeLast();
                    NewListY.removeLast();
                    tmp = 0;
                }
                if (i == (pointsX.size() - 1) & tmp == 2) {
                    tmp = 0;
                    j = 0;
                    if (NewListX.get(0).equals(NewListX.getLast()) && NewListY.get(0).equals(NewListY.getLast())) {
                        timeToBreak = 1;
                        NewListX.removeLast();
                        NewListY.removeLast();
                        break;
                    }
                }
            }
            if (timeToBreak == 1) {
                break;
            }
            NewListX.add(extractInt(pointsX.get(j)));
            NewListY.add(extractInt(pointsY.get(j)));
        }

        return "Список точек x, являющейся МВО - минимальной выпуклой оболочкой " + NewListX +
        "Список точек y, являющейся МВО - минимальной выпуклой оболочкой " + NewListY;
    }
}
