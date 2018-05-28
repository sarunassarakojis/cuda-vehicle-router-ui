package com.vehicle.router.plotting;

import com.vehicle.router.model.Node;
import javafx.collections.ObservableList;
import javafx.scene.chart.NumberAxis;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.util.function.Consumer;

public class GraphPlotter extends Pane {

    private static final int GROWTH = 2;

    private final ObservableList<Node> nodes;
    private CartesianAxes axes;
    private final double actualWidth;
    private final double actualHeight;

    public GraphPlotter(CartesianAxes axes, ObservableList<Node> nodes) {
        this.axes = axes;
        this.nodes = nodes;
        this.actualWidth = axes.getPrefWidth();
        this.actualHeight = axes.getPrefHeight();

        /*path.setStroke(Color.FIREBRICK.deriveColor(0, 1, 1, 0.6));
        path.setStrokeWidth(2);
        path.setClip(new Rectangle(0, 0, axes.getPrefWidth(), axes.getPrefHeight()));
        path.getElements().add(new MoveTo(mapX(0, axes), mapY(0, axes)));
        path.getElements().add(new LineTo(mapX(6, axes), mapY(6, axes)));
        path.getElements().add(new MoveTo(mapX(0, axes), mapY(6, axes)));
        path.getElements().add(new LineTo(mapX(6, axes), mapY(6, axes)));*/

        setMinSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);
        setPrefSize(axes.getPrefWidth(), axes.getPrefHeight());
        setMaxSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);

        getChildren().add(axes);
    }

    public void operateOnNode(int nodeIndex, Consumer<Node> nodeConsumer) {
        Node node = nodes.get(nodeIndex);
        int absoluteX = Math.abs(node.getX());
        int absoluteY = Math.abs(node.getY());
        boolean outOfBoundsX = absoluteX > axes.getAxisX().getUpperBound();
        boolean outOfBoundsY = absoluteY > axes.getAxisY().getUpperBound();

        if (outOfBoundsX && outOfBoundsY) {
            getChildren().remove(1, getChildren().size());
            scaleAxis(axes.getAxisX(), absoluteX);
            scaleAxis(axes.getAxisY(), absoluteY);
            addAllCircles();
        } else if (outOfBoundsX) {
            getChildren().remove(1, getChildren().size());
            scaleAxis(axes.getAxisX(), absoluteX);
            addAllCircles();
        } else if (outOfBoundsY) {
            getChildren().remove(1, getChildren().size());
            scaleAxis(axes.getAxisY(), absoluteY);
            addAllCircles();
        } else {
            nodeConsumer.accept(node);
        }
    }

    public void addNode(int nodeIndex) {
        operateOnNode(nodeIndex, node ->
                addCircleWithText(mapX(node.getX()), mapY(node.getY()), node.getIndice()));
    }

    public void updateNode(int nodeIndex) {
        operateOnNode(nodeIndex, node -> {
            int actualIndex = nodeIndex * 2 + 1;
            Circle circle = (Circle) getChildren().get(actualIndex);
            Text circleText = (Text) getChildren().get(actualIndex + 1);
            double mappedX = mapX(node.getX());
            double mappedY = mapY(node.getY());

            circle.setCenterX(mappedX);
            circle.setCenterY(mappedY);
            circleText.setX(mappedX);
            circleText.setY(mappedY);
        });
    }

    public void deleteNode(int nodeIndex) {
        int indexInNodes = nodeIndex;
        int actualIndex = nodeIndex * 2 + 1;
        ObservableList<javafx.scene.Node> graphNodes = getChildren();

        graphNodes.remove(actualIndex, actualIndex + 2);

        for (int i = actualIndex + 1, n = graphNodes.size(); i < n; i += 2) {
            Text text = (Text) graphNodes.get(i);

            text.setText(String.valueOf(nodes.get(indexInNodes++).getIndice()));
        }
    }

    private void scaleAxis(NumberAxis axis, double value) {
        axis.setUpperBound(value + GROWTH);
        axis.setLowerBound(-(value + GROWTH));
    }

    private void addAllCircles() {
        for (Node node : nodes) {
            addCircleWithText(mapX(node.getX()), mapY(node.getY()), node.getIndice());
        }
    }

    private void addCircleWithText(double x, double y, int id) {
        getChildren().addAll(createCircle(x, y), createCircleText(x, y, id));
    }

    private double mapX(double x) {
        double tx = actualWidth / 2;
        double sx = actualWidth / (axes.getAxisX().getUpperBound() - axes.getAxisX().getLowerBound());

        return x * sx + tx;
    }

    private double mapY(double y) {
        double ty = actualHeight / 2;
        double sy = actualHeight / (axes.getAxisY().getUpperBound() - axes.getAxisY().getLowerBound());

        return -y * sy + ty;
    }

    private static Circle createCircle(double x, double y) {
        return new Circle(x, y, 5.0d, Color.rgb(255, 102, 136));
    }

    private static Text createCircleText(double x, double y, int id) {
        return new Text(x, y, String.valueOf(id));
    }
}
