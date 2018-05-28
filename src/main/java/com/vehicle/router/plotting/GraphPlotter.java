package com.vehicle.router.plotting;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class GraphPlotter extends Pane {

    private CartesianAxes axes;

    private final double actualWidth;
    private final double actualHeight;

    public GraphPlotter(CartesianAxes axes) {
        this.axes = axes;
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

//        getChildren().setAll(axes, path, node2);
        getChildren().add(axes);
    }

    public void addCircle(int centerX, int centerY, String text) {
        double x = mapX(centerX);
        double y = mapY(centerY);
        Circle circle = new Circle(x, y, 5.0d, Paint.valueOf("rgb(255,102,136)"));
        Text circleText = new Text(x, y, text);

        getChildren().addAll(circle, circleText);
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
}
