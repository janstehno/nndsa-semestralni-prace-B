package example.towns.visuals;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.Node;
import model.State;

public class TreapRenderer<K extends Comparable<K>, P extends Comparable<P>> {
    public void draw(State<K, P> state, TreapPanel<K, P> panel) {
        drawTreap(panel.getRoot(), state.treap().getRoot(), state.node(), (double) panel.getWidth() / 2, 50, 250);
    }

    private void drawTreap(Pane pane, Node<K, P> root, Node<K, P> current, double x, double y, double dx) {
        if (root != null) {

            boolean isCurrentNode = current != null && root.getKey().equals(current.getKey()) && root.getPriority().equals(current.getPriority());

            Color regularColor = Color.rgb(253, 216, 53);
            Color currentColor = Color.rgb(102, 187, 106);

            if (root.getLeft() != null) {
                Line leftEdge = new Line(x, y, x - dx, y + 50);
                leftEdge.setStroke(Color.LIGHTGRAY);
                pane.getChildren().add(leftEdge);
                drawTreap(pane, root.getLeft(), current, x - dx, y + 50, dx / 2);
            }

            if (root.getRight() != null) {
                Line rightEdge = new Line(x, y, x + dx, y + 65);
                rightEdge.setStroke(Color.LIGHTGRAY);
                pane.getChildren().add(rightEdge);
                drawTreap(pane, root.getRight(), current, x + dx, y + 65, dx / 2);
            }

            int NODE_RADIUS = 12;
            Circle circle = new Circle(x, y, NODE_RADIUS, isCurrentNode ? currentColor : regularColor);
            pane.getChildren().add(circle);

            Text keyText = new Text(String.valueOf(root.getKey()));
            keyText.setFont(Font.font(10));
            keyText.setX(x - (root.getKey().toString().length() * 2.7));
            keyText.setY(y + 24);
            pane.getChildren().add(keyText);

            Text priorityText = new Text(String.valueOf(root.getPriority()));
            priorityText.setFont(Font.font(14));
            priorityText.setX(x - ((root.getPriority().toString().length() == 2) ? 7 : 4));
            priorityText.setY(y + 5);
            pane.getChildren().add(priorityText);
        }
    }

}