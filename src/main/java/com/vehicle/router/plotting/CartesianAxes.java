package com.vehicle.router.plotting;

import javafx.beans.binding.Bindings;
import javafx.geometry.Side;
import javafx.scene.chart.NumberAxis;
import javafx.scene.layout.Pane;

public class CartesianAxes extends Pane {

    private NumberAxis axisX;
    private NumberAxis axisY;

    public CartesianAxes(int width, int height) {
        this(width, height, -20, 20, 2, -20, 20, 2);
    }

    public CartesianAxes(int width, int height, double xLow, double xHi, double xTickUnit, double yLow, double yHi, double yTickUnit) {
        setMinSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);
        setPrefSize(width, height);
        setMaxSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);

        axisX = new NumberAxis(xLow, xHi, xTickUnit);
        axisX.setSide(Side.BOTTOM);
        axisX.setMinorTickVisible(false);
        axisX.setPrefWidth(width);
        axisX.setLayoutY(height / 2);
        axisX.setVisible(false);

        axisY = new NumberAxis(yLow, yHi, yTickUnit);
        axisY.setSide(Side.LEFT);
        axisY.setMinorTickVisible(false);
        axisY.setPrefHeight(height);
        axisY.layoutXProperty().bind(Bindings.subtract((width / 2) + 1, axisY.widthProperty()));
        axisY.setVisible(false);

        getChildren().setAll(axisX, axisY);
    }

    public NumberAxis getAxisX() {
        return axisX;
    }

    public NumberAxis getAxisY() {
        return axisY;
    }
}
