package com.vehicle.router.plotting;

import com.vehicle.router.model.Node;
import javafx.collections.ListChangeListener;

public class InputDataChangeListener implements ListChangeListener<Node> {

    private final GraphPlotter graphPlotter;

    public InputDataChangeListener(GraphPlotter graphPlotter) {
        this.graphPlotter = graphPlotter;
    }

    @Override
    public void onChanged(Change<? extends Node> change) {
        while (change.next()) {
            if (change.wasAdded()) {
                Node addedNode = change.getList().get(change.getFrom());
                graphPlotter.addCircle(addedNode.getX(), addedNode.getY(), String.valueOf(addedNode.getIndice()));
            }
        }
    }
}
