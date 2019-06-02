package by.bsu.geometry.solution;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LinesPosition extends Base {

    private final String THEORY = "Есть 2 отрезка: концы (концевые точки) одного отрезка имеют координаты (X1,Y1) и (X2,Y2),\n" +
            "у второго отрезка, соответственно, концы имеют координаты (X3,Y3) и (X4,Y4).\n" +
            "Пусть первая прямая имеет уравнение А1x + B1y + C1 = 0, вторая же прямая будет иметь уравнение А2x + B2y + C2 = 0.\n" +
            "Как же определить взаимное расположение отрезков? Очень просто – через расположение точек относительно прямой:\n" +
            "если они расположены по одну сторону от прямой, то отрезки не могут пересекаться\n" +
            "В данном случае мы будем рассматривать точки (концевые точки) отрезка одной прямой ((X1,Y1) и (X2,Y2), к примеру)\n" +
            "и уравнения уже другой прямой (А2x + B2y + C2 = 0). Итогом будет являться система уравнений:\n" +
            "Z1 = А2x1+ B2y1+C2;\n" +
            "Z2 = А2x2+ B2y2+C2.\n" +
            "Аналогично поступаем и с другими точками и уравнением прямой:\n" +
            "Z3=А1x3 + B1y3 + C1;\n" +
            "Z4=А1x4 + B1y4 + C1.\n" +
            "Ответ: отрезки пересекаются, если значения пары выражений Z1 и Z2 имеют разные знаки или Z1*Z2=0,\n" +
            "а также пары Z3 и Z4 имеют разные знаки или Z3*Z4=0.\n" +
            "Ответ: отрезки не пересекаются, если значения пар выражений Z1 и Z2 или Z3 и Z4 имеют одинаковые знаки.";
    private FlowPane pane;
    private String label;

    private final String X1 = "Введите число X1: ";
    private final String X2 = "Введите число X2: ";
    private final String X3 = "Введите число X3: ";
    private final String X4 = "Введите число X4: ";
    private final String Y1 = "Введите число Y1: ";
    private final String Y2 = "Введите число Y2: ";
    private final String Y3 = "Введите число Y3: ";
    private final String Y4 = "Введите число Y4: ";

    private TextField inputX1 = new TextField();
    private TextField inputX2 = new TextField();
    private TextField inputX3 = new TextField();
    private TextField inputX4 = new TextField();
    private TextField inputY1 = new TextField();
    private TextField inputY2 = new TextField();
    private TextField inputY3 = new TextField();
    private TextField inputY4 = new TextField();

    public LinesPosition() {}

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
                createInputPare(new Text(X1), inputX1, new Text(Y1), inputY1),
                createInputPare(new Text(X2), inputX2, new Text(Y2), inputY2),
                createInputPare(new Text(X3), inputX3, new Text(Y3), inputY3),
                createInputPare(new Text(X4), inputX4, new Text(Y4), inputY4)
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
        context.moveTo(extractInt(inputX1), extractInt(inputY1));
        context.lineTo(extractInt(inputX2), extractInt(inputY2));
        context.moveTo(extractInt(inputX3), extractInt(inputY3));
        context.lineTo(extractInt(inputX4), extractInt(inputY4));

        context.stroke();

        resultPane.getChildren().add(canvas);
        resultStage.setTitle(label);
        resultStage.setScene(resultScene);
        resultStage.show();
    }

    private int calculate(int x , int y) {
        return (Integer.valueOf(inputY1.getCharacters().toString()) -
                Integer.valueOf(inputY2.getCharacters().toString())) * x +
               (Integer.valueOf(inputX2.getCharacters().toString()) -
                Integer.valueOf(inputX1.getCharacters().toString())) * y +
               (Integer.valueOf(inputX1.getCharacters().toString()) *
                Integer.valueOf(inputY2.getCharacters().toString()) -
                Integer.valueOf(inputX2.getCharacters().toString()) *
                Integer.valueOf(inputY1.getCharacters().toString()));
    }

    private String calculateString() {
        int Z1 = calculate(Integer.valueOf(inputX3.getCharacters().toString()),
                Integer.valueOf(inputY3.getCharacters().toString()));
        int Z2 = calculate(Integer.valueOf(inputX4.getCharacters().toString()),
                Integer.valueOf(inputY4.getCharacters().toString()));
        boolean isIntersect = Z1 * Z2 <= 0;
        if (isIntersect) {
            return  "Результат: Отрезки пересекаются";
        } else {
            return "Результат: Отрезки не пересекаются";
        }
    }
}
