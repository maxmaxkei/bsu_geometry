package by.bsu.geometry.solution;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

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
    private final String BUTTON_TEXT = "Вычислить";
    private final String compareStatement = "Z1 = A1*X3 + B1*Y3 + C1  ->  Z1 = %s*%s + %s*%s + %s\n" +
                                            "Z2 = A1*X4 + B1*Y4 + C1  ->  Z2 = %s*%s + %s*%s + %s";

    private TextField inputX1 = new TextField();
    private TextField inputX2 = new TextField();
    private TextField inputX3 = new TextField();
    private TextField inputX4 = new TextField();
    private TextField inputY1 = new TextField();
    private TextField inputY2 = new TextField();
    private TextField inputY3 = new TextField();
    private TextField inputY4 = new TextField();

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
        if (pane.getChildren().size() > 7) {
            pane.getChildren().remove(8);
            pane.getChildren().remove(7);
        }
        int Z1 = calculate(Integer.valueOf(inputX3.getCharacters().toString()),
                Integer.valueOf(inputY3.getCharacters().toString()));
        int Z2 = calculate(Integer.valueOf(inputX4.getCharacters().toString()),
                Integer.valueOf(inputY4.getCharacters().toString()));
        boolean isIntersect = Z1 * Z2 <= 0;

        Text resultHeading = new Text();
        resultHeading.setFont(new Font(null, 16));
        pane.getChildren().add(resultHeading);

        if (isIntersect) {
            resultHeading.setText("Результат: Отрезки пересекаются");
            pane.getChildren().add(drawLines(true));
        } else {
            resultHeading.setText("Результат: Отрезки не пересекаются");
            pane.getChildren().add(drawLines(false));
        }
        System.out.println(pane.getChildren().size());
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

    private FlowPane createInputPare(Text text1, TextField field1, Text text2, TextField field2) {
        FlowPane pane = new FlowPane();
        pane.getChildren().addAll(text1, field1);
        pane.getChildren().addAll(text2, field2);
        return pane;
    }

    private FlowPane drawLines(boolean isIntesect) {
        Canvas canvas = new Canvas(270,200);
        FlowPane pane = new FlowPane();
        GraphicsContext context = canvas.getGraphicsContext2D();
        context.setLineWidth(2.0);
        context.beginPath();
        context.moveTo(30,200);
        context.lineTo(270, 50);
        context.moveTo(60, 170);
        if (isIntesect) {
            context.lineTo(250, 80);
        } else {
            context.lineTo(150, 20);
        }
        context.closePath();
        context.stroke();
        pane.getChildren().add(canvas);
        return pane;
    }
}
