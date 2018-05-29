package com.vehicle.router.plotting;

import com.vehicle.router.model.Node;
import com.vehicle.router.model.Route;
import javafx.collections.ObservableList;
import javafx.scene.chart.NumberAxis;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;
import javafx.scene.text.Text;

import java.util.function.Consumer;

public class GraphPlotter extends Pane {

    private static final int GROWTH = 2;

    private final ObservableList<Node> nodes;
    private final double actualWidth;
    private final double actualHeight;

    private CartesianAxes axes;
    private double depotX;
    private double depotY;
    private int routesCount;

    public GraphPlotter(CartesianAxes axes, ObservableList<Node> nodes) {
        this.axes = axes;
        this.nodes = nodes;
        this.actualWidth = axes.getPrefWidth();
        this.actualHeight = axes.getPrefHeight();
        this.depotX = 0d;
        this.depotY = 0d;
        this.routesCount = 0;

        setMinSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);
        setPrefSize(axes.getPrefWidth(), axes.getPrefHeight());
        setMaxSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);

        double mappedDepotX = mapX(depotX);
        double mappedDepotY = mapY(depotY);

        getChildren().addAll(axes,
                createCircle(mappedDepotX, mappedDepotY, Color.rgb(100, 102, 136)),
                createCircleText(mappedDepotX, mappedDepotY, "D"));
    }

    public void operateOnNode(int nodeIndex, Consumer<Node> nodeConsumer) {
        Node node = nodes.get(nodeIndex);
        int absoluteX = Math.abs(node.getX());
        int absoluteY = Math.abs(node.getY());
        boolean outOfBoundsX = absoluteX > axes.getAxisX().getUpperBound();
        boolean outOfBoundsY = absoluteY > axes.getAxisY().getUpperBound();

        if (outOfBoundsX && outOfBoundsY) {
            getChildren().remove(3, getChildren().size());
            scaleAxis(axes.getAxisX(), absoluteX);
            scaleAxis(axes.getAxisY(), absoluteY);
            addAllNodesNoScaling();
        } else if (outOfBoundsX) {
            getChildren().remove(3, getChildren().size());
            scaleAxis(axes.getAxisX(), absoluteX);
            addAllNodesNoScaling();
        } else if (outOfBoundsY) {
            getChildren().remove(3, getChildren().size());
            scaleAxis(axes.getAxisY(), absoluteY);
            addAllNodesNoScaling();
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
            int actualIndex = nodeIndex * 2 + 3;
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
        int actualIndex = nodeIndex * 2 + 3;
        ObservableList<javafx.scene.Node> graphNodes = getChildren();

        graphNodes.remove(actualIndex, actualIndex + 2);

        for (int i = actualIndex + 1, n = graphNodes.size(); i < n; i += 2) {
            Text text = (Text) graphNodes.get(i);

            text.setText(String.valueOf(nodes.get(indexInNodes++).getIndice()));
        }
    }

    public void updateDepotX(int newDepotX) {
        int absoluteX = Math.abs(newDepotX);
        boolean outOfBoundsX = absoluteX > axes.getAxisX().getUpperBound();
        this.depotX = newDepotX;

        if (outOfBoundsX) {
            getChildren().remove(3, getChildren().size());
            scaleAxis(axes.getAxisX(), absoluteX);
            addAllNodesNoScaling();
        }

        updateDepot(newDepotX, depotY);
    }

    public void updateDepotY(int newDepotY) {
        int absoluteY = Math.abs(newDepotY);
        boolean outOfBoundsY = absoluteY > axes.getAxisY().getUpperBound();
        this.depotY = newDepotY;

        if (outOfBoundsY) {
            getChildren().remove(3, getChildren().size());
            scaleAxis(axes.getAxisY(), absoluteY);
            addAllNodesNoScaling();
        }

        updateDepot(depotX, newDepotY);
    }

    private void updateDepot(double depotX, double depotY) {
        Circle circle = (Circle) getChildren().get(1);
        Text circleText = (Text) getChildren().get(2);
        double mappedX = mapX(depotX);
        double mappedY = mapY(depotY);

        circle.setCenterX(mappedX);
        circle.setCenterY(mappedY);
        circleText.setX(mappedX);
        circleText.setY(mappedY);
    }

    public void plotRoutes(ObservableList<? extends Route> routes) {
        for (Route route : routes) {
            Path path = createPath();
            ObservableList<PathElement> pathElements = path.getElements();

            pathElements.add(new MoveTo(mapX(depotX), mapY(depotY)));
            for (int node : route.getNodes()) {
                Node n = nodes.get(node - 1);

                pathElements.add(new LineTo(mapX(n.getX()), mapY(n.getY())));
            }

            pathElements.add(new LineTo(mapX(depotX), mapY(depotY)));
            getChildren().add(path);
            routesCount++;
        }
    }

    public void addAllNodes() {
        for (int i = 0, n = nodes.size(); i < n; ++i) {
            operateOnNode(i, node ->
                    addCircleWithText(mapX(node.getX()), mapY(node.getY()), node.getIndice()));
        }
    }

    public void invalidateRoutes() {
        if (routesCount > 0) {
            getChildren().remove(getChildren().size() - routesCount, getChildren().size());

            routesCount = 0;
        }
    }

    private void scaleAxis(NumberAxis axis, double value) {
        axis.setUpperBound(value + GROWTH);
        axis.setLowerBound(-(value + GROWTH));
    }

    private void addAllNodesNoScaling() {
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

    private static Circle createCircle(double x, double y, Paint fill) {
        return new Circle(x, y, 5.0d, fill);
    }

    private static Text createCircleText(double x, double y, int id) {
        return new Text(x, y, String.valueOf(id));
    }

    private static Text createCircleText(double x, double y, String text) {
        return new Text(x, y, text);
    }

    private static Path createPath() {
        Path path = new Path();

        path.setStroke(Color.FIREBRICK.deriveColor(0, 1, 1, 0.6));
        path.setStrokeWidth(2);

        return path;
    }
}
